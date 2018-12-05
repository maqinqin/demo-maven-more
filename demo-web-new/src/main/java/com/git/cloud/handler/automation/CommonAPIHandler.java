package com.git.cloud.handler.automation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.sa.common.HandlerThread;
import com.git.cloud.handler.common.ScriptUtil;
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgRetCode;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;

/**
 * @author zhuzhaoyong
 * 
 */
public class CommonAPIHandler extends AbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(CommonAPIHandler.class);
	private static String OPERATE_FILETER = "OPERATE_FILETER";
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
			log.info("【CommonAPIHandler，使用线程池】执行自动化操作开始,流程实例ID:{},节点ID:{}",
					instanceId, nodeId);
			// 允许由前台指定部分设备执行动作
			String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
			log.info("REDO_ERROR_DEV_KEY: " + redoErrorDev);
			List<String> allDevId = getDeviceIdList(instanceId, nodeId, rrinfoId, redoErrorDev);
			// 读取执行方式01,1;02,2-N;03,all
			String exeWayCode = ScriptUtil.getExeWayCode(contextParams);
			// 如果用户选择执行出错设备，则不需要重新过滤所需执行的设备
			List<String> devIdList = null;
			if (! (redoErrorDev != null && redoErrorDev.trim().equalsIgnoreCase("true"))) {
				devIdList = ScriptUtil.getProcessingVms(exeWayCode, allDevId);
			} else {
				devIdList = allDevId;
			}
			log.info("devIdList = " + devIdList.toString());
			for (String vmid : devIdList) {
				deviceLogStr += "[" + vmid + "]";
			}
			log.info("【CommonAPIHandler】execute devices: " + deviceLogStr);

			ExecutorService exec = null;
			int threadNum = getHandlerThreadNum(contextParams);
			log.debug("Command thread count: " + threadNum);
			exec = Executors.newFixedThreadPool(threadNum);
			Map<String, StringBuffer> resultsMap = new ConcurrentHashMap<String, StringBuffer>();
			// 过滤不需要操作的设备
			String operateFilter = getContextStringPara(contextParams, OPERATE_FILETER);
			if (!StringUtils.isBlank(operateFilter.trim())) {
				log.info("operateFilter:" + operateFilter);
				Map<String, Map<String, String>> devFlowParams = this.getHandleParams(instanceId);
				ScriptEngineManager factory = new ScriptEngineManager();
				ScriptEngine engine = factory.getEngineByName("JavaScript");
				List<String> filterList = new ArrayList<String>();
				for (String vmId : devIdList) {
					String filter = Utils.replaceString(operateFilter, devFlowParams.get(vmId.toString()));
					log.info("filter:" + filter);
					if ((Boolean) engine.eval(filter)) {
						filterList.add(vmId);
					}
				}
				devIdList = filterList;
			} else {
//				devIdList = new ArrayList<String>();
			}
			
			for (String vmId : devIdList) {
				resultsMap.put(vmId, new StringBuffer());
			}
			
			Map<String,Future<String>> futureMap = Maps.newHashMap();
			for (String deviceId : devIdList) {
				CommonAPIInstance instance = new CommonAPIInstance();
				@SuppressWarnings("unchecked")
				HashMap<String, Object> params = (HashMap<String, Object>) Utils.depthClone(contextParams);
				List<String> ids = new ArrayList<String>();
				ids.add(deviceId);
				params.put("destVmIds", ids);
				log.info("Start execute for device: " + deviceId);
				
				
				Future<String> future = exec.submit(new HandlerThread(instance, params, resultsMap));
				futureMap.put(deviceId, future);
			}
			
			putFutureResult(futureMap,resultsMap);
			
			exec.shutdown();
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			log.info("Command execute finish. Devices: " + deviceLogStr);
			log.info("【CommonAPIHandler，使用线程池】执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。",
					new Object[] { instanceId, nodeId, new Long((System.currentTimeMillis() - startTime)) });

			// 供给服务申请 ，构造工作流新版本的返回值
			String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams,
							CloudGlobalConstants.BUS_VERSION), rrinfoId, srInfoId, resultsMap, instanceId, nodeId);
			log.debug("【CommonAPIHandler】 return str: " + handlerReturn);
			return handlerReturn;
		} catch (Exception e) {
			String errorMsg = "错误：{" + e.getMessage() + "}" + deviceLogStr;
			log.error(errorMsg,e);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			String handlerStringReturn = getHandlerStringReturn(getContextStringPara(contextParams,
							CloudGlobalConstants.BUS_VERSION), errorMsg, MesgRetCode.ERR_OTHER);
			return handlerStringReturn;
		} finally {
			if (contextParams != null) {
				contextParams.clear();
			}
		}
	}

}
