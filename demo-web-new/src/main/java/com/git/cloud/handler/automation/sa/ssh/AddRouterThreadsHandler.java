package com.git.cloud.handler.automation.sa.ssh;

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
import org.springframework.stereotype.Component;

import com.git.cloud.handler.automation.AbstractAutomationHandler;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.sa.common.HandlerThread;
import com.git.cloud.handler.automation.sa.vmware.HandlerInstance;
import com.git.cloud.handler.common.ScriptUtil;
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgRetCode;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;

/**
 * x86虚机添加路由自动化执行组件
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 April 11, 2014
 * @see
 */
@Component("addRouterThreadsHandler")
public class AddRouterThreadsHandler extends AbstractAutomationHandler implements HandlerInstance {

	private static Logger log = LoggerFactory.getLogger(AddRouterThreadsHandler.class);

	String deviceLogStr = "";
	
	@Override
	public String execute(HashMap<String, Object> contextParams) {

		long startTime = System.currentTimeMillis();
		
		String rrinfoId = null;
		String srInfoId = null;
		String instanceId = null;
		String nodeId = null;
		
		try {
			//获取脚本执行范围
			String exeWayCode = ScriptUtil.getExeWayCode(contextParams);
			
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			instanceId = getContextStringPara(contextParams, FLOW_INST_ID);
			nodeId = getContextStringPara(contextParams, NODE_ID);
			
			log.debug("【AddRouterHandler，使用线程池】执行自动化操作开始,流程实例ID:{},节点ID:{}", instanceId, nodeId);

			//允许由前台指定部分设备执行动作
			String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
			log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
			List<String> devIdList = getDeviceIdList(instanceId, nodeId, rrinfoId, redoErrorDev);
			log.debug("*******************20140324 debuging : devIdList = "+ devIdList.toString() +"*********************************");
			
			// 读取虚拟机列表，用于取得参数信息
			List<String> vmIds = ScriptUtil.getProcessingVms(exeWayCode, devIdList);

			for (String vmid : vmIds) {
				deviceLogStr += "[" + vmid + "]";
			}
			log.debug("【AddRouterHandler】execute devices: " + deviceLogStr);

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

				BaseInstance instance = this.getInstance();

				@SuppressWarnings("unchecked")
				HashMap<String, Object> params = (HashMap<String, Object>) Utils
						.depthClone(contextParams);
				List<String> ids = new ArrayList<String>();
				ids.add(deviceId);
				params.put("destVmIds", ids);

				log.debug("Start execute for device: " + deviceId);
				Future<String> future = exec.submit(new HandlerThread(instance, params, resultsMap));
				futureMap.put(deviceId, future);
			}
			
			putFutureResult(futureMap,resultsMap);
			
			exec.shutdown();
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			log.debug("Command execute finish. Devices: "
					+ deviceLogStr);
			

			log.debug("【AddRouterHandler，使用线程池】执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					instanceId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });

			// 供给服务申请 ，构造工作流新版本的返回值
			String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
							rrinfoId, srInfoId, resultsMap, instanceId, nodeId);
			log.debug("【AddRouterHandler】 return str: " + handlerReturn);
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


	@Override
	public BaseInstance getInstance() throws Exception {
		return new AddRouterInstance();
	}

	

}
