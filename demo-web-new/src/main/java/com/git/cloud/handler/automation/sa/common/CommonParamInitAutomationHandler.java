
package com.git.cloud.handler.automation.sa.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.support.common.MesgRetCode;
import com.git.support.util.CloudGlobalConstants;


/**
 * 
 * <p>
 * 初始化通用的云服务的参数，只生成云服务配置的参数
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-5-17
 * @see
 */
public class CommonParamInitAutomationHandler extends
		LocalAbstractAutomationHandler {

	protected static Logger log = LoggerFactory
			.getLogger(CommonParamInitAutomationHandler.class);

	private String logMsg = null;
	
	public static final String DEST_SERVER_IP = "DEST_SERVER_IP";
	public static final String DEST_SERVER_USER = "DEST_SERVER_USER";
	public static final String DEST_SERVER_PW = "DEST_SERVER_PW";
	
	/**
	 * 初始化所需的dao类
	 */
	protected void initDao() {
		
	}
	
	@Override
	public String execute(HashMap<String, Object> contextParams) {
		return service(contextParams);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.iomp.cloud.core.automation.handler.LocalAbstractAutomationHandler
	 * #service(java.util.Map)
	 */
	@Override
	public String service(Map<String, Object> contextParams) {
		initDao();
		String busVer = getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION);
		try {
			// 定义参数对象
			FlowInstanceContextObject contextObj = new FlowInstanceContextObject();
			// 流程实例Id
			String flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			// 节点ID
			String nodeId = getContextStringPara(contextParams, NODE_ID);
			// 资源请求ID
			String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			String srinfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			boolean isCloudService = true;
			// 如果服务请求id和资源请求id相同则为void申请流程
			if (rrinfoId.equals(srinfoId)) {
				isCloudService = false;
			}
			//参数清理
			getBizParamInstService().deleteParamInstsOfFlow(flowInstId);
			logMsg = "创建通用云服务服务，初始化流程参数，流程实例ID：" + flowInstId + ",节点ID：" + nodeId + "。";
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			
			List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
			if (deviceIdList == null || deviceIdList.size() == 0) {
				throw new Exception("没有查询到虚拟机信息。");
			}
			for (String devId : deviceIdList) {
				contextObj.setDevicePara(devId.toString(), "this-rrinfo-id", rrinfoId.toString());
			}
			if (isCloudService) {
				log.debug("Init cloud attr parameter.");
				Map<String, String> cloudAttr = getAutomationService().getServiceAttr(rrinfoId);
				for (String devId : deviceIdList) {
					for (String attrName : cloudAttr.keySet()) {
						contextObj.setDevicePara(devId.toString(), attrName, cloudAttr.get(attrName));
					}
				}
			}
			log.debug("Init other parameter.");
			makeParameter(deviceIdList, contextObj, rrinfoId);
			makeAutomationParameter(deviceIdList, contextObj, rrinfoId);
			log.debug("初始化的工作流参数信息为：");
			log.debug(contextObj.toString());
			Map<String, Map<String, String>> deviceParaMap = contextObj.getContextDeviceMap();
			log.debug("保存初始化参数");
			log.debug(deviceParaMap.toString());
			setNeedSaveParams(deviceParaMap);
			saveParamInsts(flowInstId, nodeId);
			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
			log.debug("构造工作流新版本的返回值");
			String handlerStringReturn = getHandlerStringReturn(busVer, logMsg, MesgRetCode.SUCCESS);
			handlerStringReturn = getHandlerReturnWithExeParams(deviceIdList, contextObj, handlerStringReturn);
			return handlerStringReturn;
		} catch (Exception e) {
			String errorMsg = logMsg + "错误：{" + e + "}";
			log.error(errorMsg);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(busVer, 
					errorMsg, MesgRetCode.ERR_OTHER);
		}
	}

	/**
	 * 初始化物理机参数
	 * @param deviceIdList
	 * @param contextObj
	 * @param rrinfoId
	 * @throws Exception
	 */
	protected void makeParameter(List<String> deviceIdList,
			FlowInstanceContextObject contextObj, String rrinfoId) throws Exception {
	}
	
	/**
	 * 初始化其它参数
	 * @param deviceIdList
	 * @param contextObj
	 * @param rrinfoId
	 * @throws Exception
	 */
	protected void makeAutomationParameter(List<String> deviceIdList,
			FlowInstanceContextObject contextObj, String rrinfoId) throws Exception {
	}
	
	/**
	 * 为返回的信息增加全局参数
	 * @param deviceIdList
	 * @param contextObj
	 * @param jsonReturnStr
	 * @return
	 * @throws Exception
	 */
	protected String getHandlerReturnWithExeParams(List<String> deviceIdList, FlowInstanceContextObject contextObj, String jsonReturnStr) throws Exception {
		return jsonReturnStr;
	}
}
