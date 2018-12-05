/**
 * @Title:ParamInitBySqlHandler.java
 * @Package:com.git.cloud.handler.automation.sa.common
 * @Description:TODO
 * @author zhuzy
 * @date 2015-7-7 上午10:03:46
 * @version V1.0
 */
package com.git.cloud.handler.automation.sa.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.handler.service.BizParamInstService;
import com.git.support.common.MesgRetCode;

/**
 * 通过sql语句初始化流程参数表
 * @ClassName:ParamInitBySqlHandler
 * @Description:TODO
 * @author zhuzy
 * @date 2015-7-7 上午10:03:33
 */

public class ParamInitBySqlHandler extends LocalAbstractAutomationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(ParamInitBySqlHandler.class);
	
	 /*     缺省包含的参数
	 * 		流程实例id：flowInstId
	 * 		资源自动化类型：AUTOMATION_TYPE		范围：（缺省）CLOUD_REQUEST:云服务申请、RESOURCE_MANAGEMENT：资源管理
	 * 		RESOURCE_MANAGEMENT
	 * 				参数：DEVICE_IDS:设备id集合,例如：["111111","2222"]
	 *		CLOUD_REQUEST
	 *				参数：服务请求id：srvReqId、资源请求id：rrinfoId
	 *		本服务策略的参数：流程变量Sql:PARAM_SQL，例：select t.DEVICE_NAME as device_name, t.SN as sn from CM_DEVICE t where t.ID = '[$DEVICE_ID]'
	 *									select t.DEVICE_NAME as device_name, t.SN as sn from CM_DEVICE t where t.ID = '[$rrinfoId]'
	 *		遍历DEVICE_IDS，执行sql，将DEVICE_IDS中每个元素替换[$DEVICE_ID]
	 *			非$[DEVICE_ID]的变量，如：[$rrinfoId]，需要读取截取变量名，如：“rrinfoId”，从contextParams直接获取
	 *			执行查询，将查询结果存入BIZ_PARAM_INST，查询结果的列名作为param_key，列值作为param_value
	 *			如果查询结果为多行，列名 + "_序号"作为param_key，序号从1开始，列值作为param_value
	 *			同一流程实例，同一设备param_key字段不能重复，使用新值覆盖旧值
	 *
	 *		如果AUTOMATION_TYPE为CLOUD_REQUEST，使用getAutomationService().getDeviceIdsSort(rrinfoId)查询设备id列表
	 *			否则直接从DEVICE_IDS读取设备id列表
	 *
	 * @throws Exception
	 * @see com.git.cloud.handler.automation.LocalAbstractAutomationHandler#execute(java.util.HashMap)
	 */
	
	private static final String DEVICE_ID = "DEVICE_ID";
	private static final String DEVICE_IDS = "DEVICE_IDS";
	private static final String AUTOMATION_TYPE = "AUTOMATION_TYPE";
	private static final String PARAM_SQL = "PARAM_SQL";
	private static final String IS_OUT_VALUE = "IS_OUT_VALUE";
	private String logMsg = null;
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public String execute(HashMap<String, Object> contextParams) throws Exception {
		List<String> devs = null;
		List<?> xmlList = null;
		String srInfoId = null;
		String rrinfoId = null;
		String returnStr = "";
		String flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
		String isOutValue = getContextStringPara(contextParams, IS_OUT_VALUE);
		String nodeId = getContextStringPara(contextParams, NODE_ID);
		String automationType = getContextStringPara(contextParams, AUTOMATION_TYPE);
		logMsg = "初始化流程参数，流程实例ID：" + flowInstId + ",节点ID：" + nodeId + "。";
		long startTime = System.currentTimeMillis();
		logger.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
		try {
			if (automationType != null && automationType.equalsIgnoreCase("RESOURCE_MANAGEMENT")) {
				String devIds = getContextStringPara(contextParams, DEVICE_IDS);
				devs = (List<String>)JSON.parse(devIds);
				for(String r :devs){
					xmlList = paramInst(contextParams,r);
				}
			} else if (automationType == null || automationType.equalsIgnoreCase("CLOUD_REQUEST")) {
				srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
				rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
				devs = getAutomationService().getDeviceIdsSort(rrinfoId);
				if(devs==null || devs.size() == 0) {
					xmlList = paramInst(contextParams,"0");
				}else {
					for(String r :devs){
						xmlList = paramInst(contextParams,r);
					}
				}
				
			} else {
				throw new Exception("Error para automationType: " + automationType);
			}
		} catch (Exception e) {
			String errorMsg = logMsg + "错误：{" + e + "}";
			logger.error(errorMsg);
			printExceptionStack(e);
			return getHandlerStringReturn(errorMsg, MesgRetCode.ERR_OTHER);
		}
		if(isOutValue == "1" || isOutValue != null){
			returnStr = getSQLHandlerStringReturn("Success", MesgRetCode.SUCCESS, xmlList);
		} else {
			returnStr = getHandlerStringReturn("Success", MesgRetCode.SUCCESS);
		}
		return returnStr;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> paramInst(HashMap<String, Object> contextParams,String params) throws Exception{
		Map<String, String> param = new HashMap<String ,String >();
//		param.put("DEVICE_ID", params);
		List<?> xmlList = null;
		contextParams.put("DEVICE_ID", params);
		if(getContextStringPara(contextParams,PARAM_SQL) != null && params != null) {
			try {
	        Document doc = null;
			String sql = getContextStringPara(contextParams,PARAM_SQL);
			String paramSql = sql.trim().substring(0, sql.trim().length());
			String contextParamSql = null;
			if("0".equals(params)) {
				// contextParamSql = paramSql;
				contextParamSql = Utils.getRealStr(paramSql,contextParams,param);
			}else {
				contextParamSql = Utils.getRealStr(paramSql,contextParams,param);
			}
			
			logger.debug("当前的执行SQL:{}",contextParamSql);
			xmlList = getParamInitBySql().getParamInitBySql(contextParamSql);//如无查询结果则XML无法解析
			if(xmlList!=null && xmlList.size()>0){
				for(int i = 0; i<xmlList.size();i++){
					String xml = (String)xmlList.get(i);
					doc = DocumentHelper.parseText(xml);
					Element rootElt = doc.getRootElement();
					List<Element> iter = rootElt.elements();
				    for (Element e :iter) {
	            	   String paramKey = e.getName().trim();
	            	   String paramValue = (String) e.getData();
	            	   saveBizParamInstPo(contextParams , xmlList.size() > 1 ? paramKey+"_"+(i+1) : paramKey , paramValue);
		            }
				}
			}
	        } catch (DocumentException e) {
	            logger.error("异常exception",e);
	            logger.error(logMsg + "没有查询结果！");
	        } catch (Exception e) {
	            logger.error("异常exception",e);
	            logger.error(logMsg + "没有查询结果！");
	        }
		}
		return xmlList;
	}
	
	//保存到BIZ_PARAM_INST表中
	public void saveBizParamInstPo(HashMap<String, Object> contextParams,String paramKey,String paramValue) throws Exception{
		String deviceId = getContextStringPara(contextParams, DEVICE_ID);
		String flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
		String nodeId = getContextStringPara(contextParams, NODE_ID);
		List<BizParamInstPo> bizParamInstList = getParamInitBySql().getBizParamInst(deviceId,flowInstId,paramKey);
        if(bizParamInstList!=null && bizParamInstList.size()>0){
        	for(int i=0;i<bizParamInstList.size();i++){
        		getParamInitBySql().deleteParamInstPo(deviceId,flowInstId,nodeId,paramKey);
        	}
            }
	        BizParamInstPo bizParamInstPo = new BizParamInstPo();
	        bizParamInstPo.setId(UUIDGenerator.getUUID());
	        bizParamInstPo.setDeviceId(deviceId);
	        bizParamInstPo.setFlowInstId(flowInstId);
	        bizParamInstPo.setNodeId(nodeId);
	        bizParamInstPo.setParamKey(paramKey);
	        bizParamInstPo.setParamValue(paramValue);
	        getParamInitBySql().savePara(bizParamInstPo);
	}
	
	/* 
	 * (non-Javadoc)
	 * @param  contenxtParams
	 * @throws Exception
	 * @see com.git.cloud.handler.automation.LocalAbstractAutomationHandler#service(java.util.Map)
	 */
	
	
	
	@Override
	public String service(Map<String, Object> contextParams) throws Exception {
		return null;
	}
	public BizParamInstService getParamInitBySql() throws Exception {
		return (BizParamInstService) WebApplicationManager.getBean("bizParamInstServiceImpl");
	}
}