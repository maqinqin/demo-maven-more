package com.git.cloud.handler.automation.sa.weblogic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.AbstractAutomationHandler;
import com.git.cloud.handler.automation.sa.common.HandlerThread;
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgRetCode;
import com.git.support.constants.SAConstants;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;

/**
 * 
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 2013-12-9 
 * @see
 */
public  class WeblogicDomainDistrHandler  extends AbstractAutomationHandler{
	private static Logger log = LoggerFactory
			.getLogger(WeblogicDomainDistrHandler.class);


	String deviceLogStr = "";
	
			
	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 
	 * @param contenxtParams
	 *            上下文参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String execute(HashMap<String, Object> contextParams) {
			
		String rrinfoId = null;
		String srInfoId = null;
		String flowInstId = null;
		String nodeId = null;
		
		try {
			
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			nodeId = getContextStringPara(contextParams, NODE_ID);

			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

			// 获取全局业务参数  99000031
			Map<String, Map<String, String>> handleParams = this
					.getHandleParams(flowInstId);

			// 将工作流相关参数和业务参数合并
			contextParams.putAll(handleParams);
			
			ExecutorService exec = null;			
			int threadNum = getHandlerThreadNum(contextParams);
			log.debug("Command thread count: " + threadNum);
			exec = Executors.newFixedThreadPool(threadNum);
			
			Map<String, StringBuffer> resultsMap = new HashMap<String, StringBuffer>();

			//允许由前台指定部分设备执行动作
			String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
			log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
			List<String> devIdList = getDeviceIdList(flowInstId, nodeId, rrinfoId, redoErrorDev);
			log.debug("*******************20140324 debuging : devIdList = "+ devIdList.toString() +"*********************************");
			
			String domainTarPath = "";
			String nodemanagerTarPath ="";
			String targetDir = "";
			String script_deviceIp  = "";//= deviceParamInfo.get(SAConstants.SERVER_IP);
			String script_userName = "";// = deviceParamInfo.get(SAConstants.USER_NAME);
			String script_pwd = "";//= deviceParamInfo.get(SAConstants.USER_PASSWORD);
			
			Map<String,Future<String>> futureMap = Maps.newHashMap();
			for(int count=0; count<devIdList.size(); count++){
				
				String vmId = devIdList.get(count);				
		
				log.debug("Start execute for device: " + vmId);
				
				//第一台虚机
				if(count == 0){
					HashMap<String, Object> deviceParamInfo = (HashMap<String, Object>) contextParams.get(vmId.toString());
					script_deviceIp = deviceParamInfo.get(SAConstants.SERVER_IP).toString();
					script_userName = deviceParamInfo.get(SAConstants.USER_NAME).toString();
					script_pwd = deviceParamInfo.get(SAConstants.USER_PASSWORD).toString();				
					domainTarPath = deviceParamInfo.get(SAConstants.DOMAIN_TAR).toString();
					nodemanagerTarPath = deviceParamInfo.get(SAConstants.NODEMANAGER_TAR).toString();
					targetDir = deviceParamInfo.get(SAConstants.TARGET_DIR).toString();
				}
				//第2-N台虚机
				else{
					WeblogicDomainDistrInstance instance = new WeblogicDomainDistrInstance();				

					HashMap<String, Object> params = (HashMap<String, Object>) Utils.depthClone(contextParams);
					
					List<String> ids = new ArrayList<String>();
					ids.add(vmId);
					resultsMap.put(vmId, new StringBuffer());
					params.put("destVmIds", ids);
					
					params.put(SAConstants.TARGET_SERVER_IP, script_deviceIp);
					params.put(SAConstants.TARGET_USER_NAME, script_userName);
					params.put(SAConstants.TARGET_USER_PWD, script_pwd);
					params.put(SAConstants.PACKAGE_DISTR_PATH, targetDir);
					params.put(SAConstants.DOMAIN_TAR, domainTarPath);
					params.put(SAConstants.NODEMANAGER_TAR, nodemanagerTarPath);
					
					Future<String> future = exec.submit(new HandlerThread(instance, params, resultsMap));
					futureMap.put(vmId, future);
				}
					
			}

			putFutureResult(futureMap,resultsMap);	
			exec.shutdown();
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			log.debug("下发weblogic Domain tar包 finish. Devices: " + deviceLogStr);
			
			// 构造工作流新版本的返回值
			String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
					rrinfoId, srInfoId, resultsMap, flowInstId, nodeId);
			return handlerReturn;
		} catch (Exception e) {
			String errorMsg = "【WeblogicDomainDistrHandler】错误：{" + e + "}" + deviceLogStr;
			log.error(errorMsg);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			String handlerStringReturn = getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), errorMsg, MesgRetCode.ERR_OTHER);
			return handlerStringReturn;
		} finally {
			if (contextParams != null) {
				contextParams.clear();
			}
		}
	}
	
	
	
}
