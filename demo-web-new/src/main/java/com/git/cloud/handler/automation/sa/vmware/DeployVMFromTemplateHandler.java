package com.git.cloud.handler.automation.sa.vmware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;


/**
 * @author 
 * 
 */
public class DeployVMFromTemplateHandler extends AbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(DeployVMFromTemplateHandler.class);

	String deviceLogStr = "";

	@Override
	public String execute(HashMap<String, Object> contextParams) {

		long startTime = System.currentTimeMillis();
		
		String rrinfoId = null;
		String srInfoId = null;
		String instanceId = null;
		String nodeId = null;
		
		try {
			
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			instanceId = getContextStringPara(contextParams, FLOW_INST_ID);
			nodeId = getContextStringPara(contextParams, NODE_ID);
			
			log.debug("【DeployVMFromTemplateHandler，使用线程池】执行自动化操作开始,流程实例ID:{},节点ID:{}", instanceId, nodeId);

			//允许由前台指定部分设备执行动作
			String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
			log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
			List<String> devIdList = getDeviceIdList(instanceId, nodeId, rrinfoId, redoErrorDev);
			log.debug("*******************20171020 debuging : devIdList = "+ devIdList.toString() +"*********************************");

			for (String vmid : devIdList) {
				deviceLogStr += "[" + vmid + "]";
			}
			log.debug("【DeployVMFromTemplateHandler】execute devices: " + deviceLogStr);

			ExecutorService exec = null;
			
			int threadNum = getHandlerThreadNum(contextParams);
			log.debug("Command thread count: " + threadNum);
			exec = Executors.newFixedThreadPool(threadNum);
			
			Map<String, StringBuffer> resultsMap = new ConcurrentHashMap<String, StringBuffer>();

			for (String vmId : devIdList) {
				resultsMap.put(vmId, new StringBuffer());
			}
			Map<String,Future<String>> futureMap = Maps.newHashMap();
			for (String deviceId : devIdList) {
				DeployVMFromTemplateInstance deployVMFromTemplate = new DeployVMFromTemplateInstance();

				@SuppressWarnings("unchecked")
				HashMap<String, Object> params = (HashMap<String, Object>) Utils
						.depthClone(contextParams);
				List<String> ids = new ArrayList<String>();
				ids.add(deviceId);
				params.put("destVmIds", ids);

				log.debug("Start execute for device: " + deviceId);
				Future<String> future = exec.submit(new HandlerThread(deployVMFromTemplate, params, resultsMap));
				futureMap.put(deviceId, future);
			}
			
			putFutureResult(futureMap,resultsMap);
			exec.shutdown();
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			log.debug("Command execute finish. Devices: "
					+ deviceLogStr);
			

			log.debug("【DeployVMFromTemplateHandler，使用线程池】执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					instanceId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });

			// 供给服务申请 ，构造工作流新版本的返回值
			String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
							rrinfoId, srInfoId, resultsMap, instanceId, nodeId);
			log.debug("【DeployVMFromTemplateHandler】 return str: " + handlerReturn);
			return handlerReturn;
		} catch (Exception e) {
			String errorMsg = "错误：{" + e + "}" + deviceLogStr;
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
