/**
 * Copyright (c) 2014, Git Co., Ltd. All rights reserved.
 *
 * 审核人：
 */
package com.git.cloud.handler.automation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.BizParamInstService;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.common.service.IRmDatacenterService;
import com.git.support.common.MesgRetCode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 云平台自动化操作抽象实现类
 * <p>
 * 
 * @author 
 * @version 1.0 2013-4-24
 * @see
 * 
 * 修改记录
 * 		2015-11-04 修改makepara方法，设置uuid
 */
public abstract class AbstractAutomationHandler implements IAutomationHandler {

	/**
	 * 记录工作流参数被拆分后的个数的后参数Key的后缀
	 */
	private static final String _COUNT = "_COUNT";
	private static Logger log = LoggerFactory.getLogger(AbstractAutomationHandler.class);
	// 常量定义
	public static final String FLOW_INST_ID = "flowInstId";// 流程实例参数key
	public static final String NODE_ID = "nodeId";// 节点Id参数key
	public static final String SRV_REQ_ID = "srvReqId";// 服务请求ID
	public static final String RRINFO_ID = "rrinfoId";// 资源请求ID
	public static final String DEVICE_ID_LIST = "deviceIdList";// 设备Id列表
	public static final String EXEC_RESULT = "execResult";// 执行结果参数key
	public static final String DATA_CENTER_ID = "dataCenterId";//  数据中心id
	public static final String TIME_OUT_STR = "TIME_OUT";//超时时间
	/* 工作流参数表参数值最大长度 */
	public static final int FLOW_PARAMS_MAX_LENGTH = 3500;
	/* handler开启的线程数key */
	private static final String HANDLER_THREAD_NUM_KEY = "HANDLER_THREAD_NUM";
	private static final int DEFAULT_THREAD_NUM = 1;
	/* 重新执行错误设备标识，如果为空则执行全部设备，否则执行上一次出错的设备*/
	protected static final String REDO_ERROR_DEV_KEY = "REDO_ERROR_DEV";
	// 需要保存到参数实例表的参数
	private Map<String, Map<String, String>> needToSaveParams = Maps.newHashMap();
	
	BizParamInstService bizParamInstService;
	AutomationService automationService;
	
	public BizParamInstService getBizParamInstService() throws Exception {
		return (BizParamInstService) WebApplicationManager.getBean("bizParamInstServiceImpl");
	}
	public AutomationService getAutomationService() throws Exception {
		return (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
	}
	public IIpAllocToDeviceNewService getIIpAllocToDeviceService() throws Exception {
		return (IIpAllocToDeviceNewService) WebApplicationManager.getBean("ipAllocToDeviceImpl");
	}
	public ICmDeviceService getCmDeviceServiceImpl() throws Exception {
		return (ICmDeviceService) WebApplicationManager.getBean("cmDeviceServiceImpl");
	}
	
	protected String getDatacenterId(String rrinfoId) throws Exception {
		String dcId = null;
		//获取json串
		IBmSrRrinfoDao bmSrRrinfoDao = (IBmSrRrinfoDao) WebApplicationManager.getBean("bmSrRrinfoDaoImpl");
		BmSrRrinfoPo bmSrRrinfoPo = bmSrRrinfoDao.findBmSrRrinfoById(rrinfoId);
		if(!CommonUtil.isEmpty(bmSrRrinfoPo)) {
			String jsonStr = bmSrRrinfoPo.getParametersJson();
			if(jsonStr!=null){
				JSONObject jsonObj = JSON.parseObject(jsonStr);
				dcId = jsonObj.getString("datacenterId");
			}else{
				dcId = bmSrRrinfoPo.getDataCenterId();
			}
			log.info("datacenterId:"+dcId);
		}else {
			log.error("bmSrRrinfoPo is null");
		}
		
		if(CommonUtil.isEmpty(dcId))
			throw new Exception("datacenterId is null");
		
		return dcId;
	}
	
	protected String getQueueIdent(String rrinfoId) throws Exception {
		String dcId = this.getDatacenterId(rrinfoId);
		IRmDatacenterService rmDataCenterServiceImpl = (IRmDatacenterService) WebApplicationManager.getBean("rmDataCenterService");
		//获取数据中心
		RmDatacenterPo  rmDatacenterPo = rmDataCenterServiceImpl.getDataCenterById(dcId);	
		
		if(rmDatacenterPo == null)
			throw new Exception("datacenter is null");
		return rmDatacenterPo.getQueueIden();
	}
	
	/**
	 * 线程执行结果统一获取
	 * @param rrinfoId
	 * @return
	 * @throws Exception
	 */
	protected void putFutureResult(Map<String,Future<String>> futureMap,Map<String, StringBuffer> resultsMap) throws Exception {
		if(futureMap == null)
			return;
		Iterator<String> futureKeys = futureMap.keySet().iterator();
		while(futureKeys.hasNext()) {
			String vmId = futureKeys.next();
			Future<String> future = futureMap.get(vmId);
			String result = future.get();
			resultsMap.put(vmId, resultsMap.get(vmId).append(result));
		}
	}
	
	
	
	/**
	 * 获取全局业务参数
	 * 
	 * @param srvReqId
	 *            服务请求ID
	 * @param flowInstId
	 *            流程实例ID
	 * @return
	 */
	protected Map<String, Map<String, String>> getHandleParams(String flowInstId) throws Exception {
		log.debug("开始获取业务全局参数,流程实例ID:{}", flowInstId);
		List<BizParamInstPo> paramInsts = getBizParamInstService().getInstanceParas(flowInstId);
		// 所有设备的参数集合
		Map<String, Map<String, String>> handleParams = Maps.newHashMap();
		if (paramInsts != null) {
			for (BizParamInstPo paramInstPo : paramInsts) {
				// 单台设备的参数集合
				Map<String, String> deviceParamsMap = handleParams .get(paramInstPo.getDeviceId().toString());
				if (deviceParamsMap == null) {
					deviceParamsMap = Maps.newHashMap();
					handleParams.put(paramInstPo.getDeviceId().toString(), deviceParamsMap);
				}
				deviceParamsMap.put(paramInstPo.getParamKey(), paramInstPo.getParamValue());
			}
		}
		log.debug("完成获取业务全局参数,流程实例ID:{}", flowInstId);
		return handleParams;
	}
	
	/**
	 * 读取设备的工作流参数
	 * @param flowInstId
	 * @param deviceIds
	 * @return
	 */
	protected Map<String, Map<String, String>> getHandleParams(String flowInstId, List<String> deviceIds) throws Exception {
		Map<String, Map<String, String>> params = new HashMap<String, Map<String,String>>();
		for (String devId : deviceIds) {
			List<BizParamInstPo> bizParams = getBizParamInstService().getInstanceParas(flowInstId, devId);
			Map<String, String> deviceMap = new HashMap<String, String>();
			for (BizParamInstPo bizParamInstPo : bizParams) {
				deviceMap.put(bizParamInstPo.getParamKey(), bizParamInstPo.getParamValue());
			}
			params.put(devId.toString(), deviceMap);
		}
		return params;
	}

	/**
	 * 保存参数实例信息
	 * 
	 * @param flowInstId
	 * @param nodeId
	 */
	protected void saveParamInsts(String flowInstId, String nodeId) throws Exception {
		if (this.needToSaveParams != null) {
			final List<BizParamInstPo> unSavedParamInsts = Lists.newArrayList();
			Iterator<String> iterator = needToSaveParams.keySet().iterator();
			while (iterator.hasNext()) {
				String deviceId = iterator.next();
				Map<String, String> deviceParamsMap = needToSaveParams.get(deviceId);
				if (deviceParamsMap != null) {
					Iterator<String> inIterator = deviceParamsMap.keySet().iterator();
					while (inIterator.hasNext()) {
						String key = inIterator.next();
						if(key.length() > 100) {
							continue;
						}
						String value = deviceParamsMap.get(key);
						// 如果value的长度大于指定的值，则需要将参数拆分成多个参数保存
						if (StringUtils.isNotEmpty(value) && value.length() > FLOW_PARAMS_MAX_LENGTH) {
							log.debug("Param Length > {}, Key: {}", FLOW_PARAMS_MAX_LENGTH, key);
							String countKey = key + _COUNT;
							List<String> splitValues = Utils.splitString(value, FLOW_PARAMS_MAX_LENGTH);
							int count = splitValues.size();
							log.debug("Create New Count Param, Key: {}, Value: {}", countKey, count);
							// Save count param
							BizParamInstPo bizParamInstPo = makePara(flowInstId, nodeId, deviceId, countKey, String.valueOf(count));
							//bizParamInstPo.setParamInstId(DBSeqUtils.getDefaultSeq());
							unSavedParamInsts.add(bizParamInstPo);
							// 拆分参数并保存参数
							log.debug("Split Param, Key: {}", key);
							int i = 0;
							for (String v : splitValues) {
								i++;
								bizParamInstPo = makePara(flowInstId, nodeId, deviceId, key + "_" + i, v);
								//bizParamInstPo.setParamInstId(DBSeqUtils.getDefaultSeq());
								unSavedParamInsts.add(bizParamInstPo);
							}
						} else {
							BizParamInstPo bizParamInstPo = new BizParamInstPo();
							bizParamInstPo.setFlowInstId(flowInstId);
							bizParamInstPo.setNodeId(nodeId);
							bizParamInstPo.setDeviceId(deviceId);
							bizParamInstPo.setParamKey(key);
							if (!StringUtils.isEmpty(value)) {
								value = value.trim();
							}
							bizParamInstPo.setParamValue(value);
							unSavedParamInsts.add(bizParamInstPo);
						}
					}
				}
			}
			for(int i=0 ; i<unSavedParamInsts.size() ; i++) {
				unSavedParamInsts.get(i).setId(UUIDGenerator.getUUID());
			}
			getBizParamInstService().saveOrUpdateParas(unSavedParamInsts);
			String lparName, lparId, profileName;
			CmDevicePo device = new CmDevicePo();
			for (BizParamInstPo bizParamInstPo : unSavedParamInsts) {
				device.setId(bizParamInstPo.getDeviceId());
				if(bizParamInstPo.getParamKey().trim().equalsIgnoreCase("lpar_id_is")) {
					lparId = bizParamInstPo.getParamValue();
					device.setLparId(lparId);
					getCmDeviceServiceImpl().updateCmDeviceLparId(device);
				}
				if(bizParamInstPo.getParamKey().trim().equalsIgnoreCase("lparname")) {
					lparName = bizParamInstPo.getParamValue();
					device.setLparName(lparName);
					getCmDeviceServiceImpl().updateCmDeviceLparName(device);
				}
				if(bizParamInstPo.getParamKey().trim().equalsIgnoreCase("profilename")) {
					profileName = bizParamInstPo.getParamValue();
					device.setProfileName(profileName);
					getCmDeviceServiceImpl().updateCmDeviceProfileName(device);
				}
			}
			this.needToSaveParams.clear();
		}
	}

	/**
	 * 获取扩展业务参数，如果自动化组件所需的业务参数在全局参数表中不存在，需重写此此方法返回相应的业务参数
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @return
	 */
	protected Map<String, Object> getExtHandleParams(
			Map<String, Object> contenxtParmas) {

		return Maps.newHashMap();
	}

	/**
	 * 增加需要保持到参数实例表中的参数
	 * @param deviceId
	 *            设备Id
	 * @param key
	 *            参数关键字
	 * @param value
	 *            参数值
	 */
	public void setHandleResultParam(String deviceId, String key, String value) {
		Map<String, String> params = this.needToSaveParams.get(deviceId);
		if (params == null) {
			params = Maps.newHashMap();
			this.needToSaveParams.put(deviceId, params);
		}
		params.put(key, value);
	}

	/**
	 * 增加需要保持到参数实例表中的参数
	 * @param deviceId
	 *            设备Id
	 * @param params
	 *            设备脚本相关的参数
	 */
	public void setHandleResultParam(String deviceId, Map<String, String> params) {
		if (params != null) {
			this.needToSaveParams.put(deviceId, params);
		}
	}

	/**
	 * 读取需要执行的设备id列表，如果redoErrorDev为空则执行所有的设备，否则执行上一次出错的设备
	 * @param instanceId
	 * @param nodeId
	 * @param rrinfoId
	 * @param redoErrorDev
	 * @return
	 */
	public List<String> getDeviceIdList(String instanceId, String nodeId, String rrinfoId, String redoErrorDev) throws Exception {
		/*List<Long> devIds = null;
		if (redoErrorDev != null && redoErrorDev.trim().equalsIgnoreCase("true")) {
			devIds = getAutomationService().findNewlyErrorDevIds(instanceId, nodeId);
			if (devIds == null || devIds.size() <= 0) {
				throw new Exception("找不到需要操作的设备。你选择的是操作上一次出错的设备，但系统没有读取到相应设备。");
			} else {
				return devIds;
			}
		} else {
			return automationService.getDeviceIdsSortByIdAsc(rrinfoId);
		}*/
		return getAutomationService().getDeviceIdsSort(rrinfoId);
	}

	/**
	 * 输出异常堆栈信息
	 * 
	 * @param e
	 */
	protected void printExceptionStack(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		for (StackTraceElement stackTraceElement : ste) {
			log.error(stackTraceElement.toString());
		}
		log.error("异常exception",e);
	}

	/**
	 * 新建参数
	 * 
	 * @param flowInstId
	 * @param nodeId
	 * @param vmId
	 * @param key
	 * @param value
	 * @return
	 */
	protected BizParamInstPo makePara(String flowInstId, String nodeId, String vmId,
			String key, String value) {
		log.debug("Create new parameter. key=value, " + key + "=" + value);
		BizParamInstPo para = new BizParamInstPo();
		para.setId(UUIDGenerator.getUUID());
		para.setDeviceId(vmId);
		para.setFlowInstId(flowInstId);
		para.setNodeId(nodeId);
		para.setParamKey(key);
		para.setParamValue(value);
		return para;
	}

	/**
	 * 读取上下文map中的变量
	 * 
	 * @param contextParams
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected Long getContextLongPara(Map<String, Object> contextParams,
			String key) throws Exception {
		Object obj = contextParams.get(key);
		if (obj == null || StringUtils.isEmpty(obj.toString())) {
			throw new Exception("Context params error. Null or empty value: "
					+ key);
		}
		Long value = Long.parseLong(obj.toString());
		return value;
	}
	
	/**
	 * 读取上下问map中的变量
	 * 
	 * @param contextParams
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected String getContextStringPara(Map<String, Object> contextParams,
			String key) {
		log.info("Get Context String, key:" + key);
		String value = key.trim();
		if (value.startsWith("[$") && value.endsWith("]")) {
			log.info("Get Context String for [$xx], key:" + key);
			String subkey = value.substring(value.indexOf("[$") + 2);
			subkey = subkey.substring(0, subkey.length() - 1);
			String subvalue = getContextStringPara(contextParams, subkey);
			log.info("Get Context String for, key:" + subvalue);
			return subvalue;
		} else {
			log.info("Get Context String for no [$xx], key:" + key);
			Object obj = contextParams.get(value);
			if (obj == null) {
				return null;
			} else { 
				return obj.toString();
			}
		}
	}

	protected void setNeedSaveParams(Map<String, Map<String, String>> map) {
		this.needToSaveParams = map;
	}
	
	/**
	 * 拼装大字段参数Value值
	 * @param contextParams
	 * @param paramKey
	 * @return 
	 * @throws Exception
	 * */
	protected String getBigParamValue(Map<String, ?> contextParams,String paramKey) throws Exception{
		StringBuffer keyValue = new StringBuffer();
		String paramKeyCount = (String)contextParams.get(paramKey+_COUNT);
		if(null == paramKeyCount || "".equals(paramKeyCount)){
			//未到达分割阀值3500
			keyValue.append(String.valueOf(contextParams.get(paramKey)));
		}else{
			int keyCount = Integer.valueOf(paramKeyCount);
			for(int i=0;i<keyCount;i++){
				String childValue = new String();
				childValue = (String)contextParams.get(paramKey+"_"+(i+1));
				if(null == childValue || "".equals(childValue)){
					throw new Exception("Context params error. Null or empty value: " + paramKey+(i+1));
				}
				keyValue.append(childValue);
			}
		}
		return keyValue.toString();
	}
	

	/**
	 * 构造一个节点中一个设备的返回结果
	 * @param busVersion
	 * @param devId
	 * @param errorMsg
	 * @return
	 */
	protected String getInstanceStringReturn(String busVersion, String devId,
			String errorMsg) {
		String ret = null;
		if (busVersion == null) {
			ret = errorMsg;
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add(errorMsg);
			HandlerReturnObjectForOneDev retObj = new HandlerReturnObjectForOneDev(devId, 
					MesgRetCode.ERR_OTHER, null, errors);
			ret = retObj.toJsonString();
		}
		log.info("InstanceStringReturn is: " + ret);
		return ret;
	}
	
	/**
	 * 供给类型
	 * 构造一个节点的若干设备执行自动化操作的返回结果
	 * @param busVersion
	 * @param rrinfoId
	 * @param srInfoId
	 * @param results
	 * @return
	 */
	protected String getHandlerAutomationReturn(String busVersion,
			String rrinfoId, String srInfoId, Map<String, StringBuffer> results, String instanceId, String nodeId) throws Exception {
		log.debug("Make automation return, busVersion = " + busVersion);
		log.debug("Make automation return, rrinfoId = " + rrinfoId);
		log.debug("Make automation return, srInfoId = " + srInfoId);
		for (String key : results.keySet()) {
			String returnStr = results.get(key).toString();
			log.debug("Make automation return, device id = " + key);
			log.debug("Make automation return, result = " + returnStr);
		}
		try {
			for (String key : results.keySet()) {
				String returnStr = results.get(key).toString();
				HandlerReturnObjectForOneDev returnObj = HandlerReturnObjectForOneDev.parse(returnStr);
				if (returnObj == null) {
					log.info("Automation result is old version.");
					busVersion = null;
					break;
				}
			}
		} catch (Exception e) {
			busVersion = null;
			log.info("Automation result is old version.");
			Utils.printExceptionStack(e);
		}
		String haRet = null;
		if (busVersion == null) {
			haRet = getOldVersionReturn(results);
		} else {
			HandlerReturnObject returnObjs = new HandlerReturnObject();
			returnObjs.setSrInfoId(srInfoId);
			returnObjs.setRrInfoId(rrinfoId);
			returnObjs.setInstanceId(instanceId);
			returnObjs.setNodeId(nodeId);
			for (String key : results.keySet()) {
				String returnStr = results.get(key).toString();
				HandlerReturnObjectForOneDev returnObj = HandlerReturnObjectForOneDev.parse(returnStr);
				returnObjs.addOneObject(returnObj);
			}
			// 保存出错的设备执行命令
			try {
//				getAutomationService().saveBpmRecorder(returnObjs);
			} catch (Exception e) {
				log.error("Save bpm recorder error.");
				log.error("异常exception",e);
				Utils.printExceptionStack(e);
			}
			haRet = getAutomationService().makeInstanceReturnString(returnObjs);
		}
		log.info("HandlerAutomationReturn is: " + haRet);
		return haRet;
	}

	private String getOldVersionReturn(Map<String, StringBuffer> results) {
		String result = "";
		String ret = "";
		for (String key : results.keySet()) {
			result = results.get(key).toString();
			if (result != null && result.equals(MesgRetCode.SUCCESS)) {
				continue;
			} else {
				if (! ret.equals("")) {
					ret += ", ";
				}
				ret += "deviceid=" + key.toString() + "|" + "result=" + result;
			}
		}
		String oldRet = null;
		if (ret.equals("")) {
			oldRet = MesgRetCode.SUCCESS;
		} else {
			oldRet = ret;
		}
		log.info("OldVersionReturn is: " + oldRet);
		return oldRet;
	}
	
	/**
	 * 回收类型
	 * 构造一个节点的若干设备执行自动化操作的返回结果
	 * @param busVersion
	 * @param rrinfoId
	 * @param srInfoId
	 * @param results
	 * @return
	 */
	protected String getHandlerAutomationReturnForReco(String busVersion,
			String rrinfoId, String srInfoId, Map<String, StringBuffer> results, String instanceId, String nodeId) {
		HandlerReturnObject returnObjs = new HandlerReturnObject();
		returnObjs.setSrInfoId(srInfoId);
		returnObjs.setRrInfoId(rrinfoId);
		returnObjs.setInstanceId(instanceId);
		returnObjs.setNodeId(nodeId);
		String ret = null;
		if (busVersion == null) {
			ret = getOldVersionReturn(results);
		} else {
			for (String key : results.keySet()) {
				String returnStr = results.get(key).toString();
				HandlerReturnObjectForOneDev returnObj = HandlerReturnObjectForOneDev.parse(returnStr);
				returnObjs.addOneObject(returnObj);
			}
			String retStr = "";
			for (HandlerReturnObjectForOneDev retObj : returnObjs.getDevObjects()) {
				retStr += retObj.toString();
				retStr += ":::";
			}
			// 保存出错的设备执行命令
			try {
//				getAutomationService().saveBpmRecorder(returnObjs);
			} catch (Exception e) {
				log.error("Save bpm recorder error.");
				log.error("异常exception",e);
				Utils.printExceptionStack(e);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("checkCode", returnObjs.getReturnCode());
			map.put("exeMsg", retStr);
			String jsonDATA = JSON.toJSONString(map);
			ret = jsonDATA;
		}
		log.info("HandlerAutomationReturnForReco is: " + ret);
		return ret;
	}
	
	/**
	 * 构造一个节点的简单返回结果
	 * @param busVersion	工作流版本
	 * @param returnMsg		返回字符串
	 * @param returnCode	返回码
	 * @return
	 */
	protected String getHandlerStringReturn(String busVersion, String returnMsg, String returnCode) {
		String ret = null;
		if (busVersion == null) {
			ret = returnCode;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("checkCode", returnCode);
			map.put("exeMsg", returnMsg);
			String jsonDATA = JSON.toJSONString(map);
			ret = jsonDATA;
		}
		log.info("HandlerStringReturn is: " + ret);
		return ret;
	}
	
	/**
	 * 构造一个节点的简单返回结果
	 * @param returnMsg		返回字符串
	 * @param returnCode	返回码
	 * @return
	 */
	protected String getHandlerStringReturn(String returnMsg, String returnCode) {
		String ret = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkCode", returnCode);
		map.put("exeMsg", returnMsg);
		String jsonDATA = JSON.toJSONString(map);
		ret = jsonDATA;
		log.info("HandlerStringReturn is: " + ret);
		return ret;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	protected String getSQLHandlerStringReturn(String returnMsg, String returnCode,List<?> listXml) throws DocumentException {
		String ret = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkCode", returnCode);
		map.put("exeMsg", returnMsg);
		Document doc = null;
		if(listXml!=null && listXml.size()>0){
			Map<String, Object> hashMap = new HashMap<String, Object>();
			Map<String, String> exeParams = new HashMap<String, String>();
			for(int i = 0; i<listXml.size();i++){
				String xml = (String)listXml.get(i);
				doc = DocumentHelper.parseText(xml);
				Element rootElt = doc.getRootElement();
				List<Element> iter = rootElt.elements();
			    for (Element e :iter) {
            	   String paramKey = e.getName().trim();
            	   String paramValue = (String) e.getData();
	       		   exeParams.put(paramKey, paramValue);
	       		   map.put("exeParams", exeParams);
	            }
			}
		}
		String jsonDATA = JSON.toJSONString(map);
		ret = jsonDATA;
		log.info("HandlerStringReturn is: " + ret);
		return ret;
	}

	/**
	 * 读取工作流引擎传过来的线程数
	 * @param contextParams
	 * @return
	 */
	protected int getHandlerThreadNum(HashMap<String, Object> contextParams) {
		try {
			Long threadNum = getContextLongPara(contextParams, HANDLER_THREAD_NUM_KEY);
			if (threadNum <= 0) {
				log.warn("Handler thread num parameter is " + threadNum);
				return DEFAULT_THREAD_NUM;
			} else {
				log.debug("Handler thread num parameter is " + threadNum);
				return threadNum.intValue();
			}
		} catch (Exception e) {
			log.warn("Handler thread num parameter is null.");
			return DEFAULT_THREAD_NUM;
		}
		
	}
	/**
	 * 合并一个设备的多个结果对象
	 * @param allResults
	 */
	protected Map<String, StringBuffer> mergeResults(Map<String, List<StringBuffer>> allResults) {
		Map<String, StringBuffer> mergedResult = new HashMap<String, StringBuffer>();
		for (String devId : allResults.keySet()) {
			// 每个设备的结果
			HandlerReturnObjectForOneDev devResult = new HandlerReturnObjectForOneDev();
			devResult.setDeviceId(devId);
			devResult.setReturnCode(MesgRetCode.SUCCESS);
			devResult.setReturnMesg("");
			List<StringBuffer> results = allResults.get(devId);
			for (StringBuffer sb : results) {
				String string = sb.toString();
				log.info("one return str:" + string);
				// 每次请求的结果
				HandlerReturnObjectForOneDev resultForOnePack = HandlerReturnObjectForOneDev.parse(string);
				if (devResult.getReturnCode().equals(MesgRetCode.SUCCESS) && 
						!resultForOnePack.getReturnCode().equals(MesgRetCode.SUCCESS)) {
					devResult.setReturnCode(resultForOnePack.getReturnCode());
				}
				if (!resultForOnePack.getReturnCode().equals(MesgRetCode.SUCCESS)) {
					devResult.getErrors().addAll(resultForOnePack.getErrors());
					devResult.setReturnMesg(devResult.getReturnMesg() + resultForOnePack.getReturnMesg() + Utils.BR);
				}
				devResult.getEchos().addAll(resultForOnePack.getEchos());
			}
			mergedResult.put(devId, new StringBuffer(devResult.toJsonString()));
		}
		return mergedResult;
	}
}
