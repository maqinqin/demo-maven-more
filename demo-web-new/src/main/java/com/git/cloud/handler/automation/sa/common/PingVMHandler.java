package com.git.cloud.handler.automation.sa.common;

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
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgRetCode;
import com.google.common.collect.Maps;
/**
 * 
 * <p> ping 待分配的虚拟机ip以判断待分配的ip是否可用
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-5-22 
 * @see
 */
public class PingVMHandler extends AbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(PingVMHandler.class);
	
	String deviceLogStr = "";
	
	/* (non-Javadoc)
	 * @see com.ccb.iomp.cloud.core.automation.handler.IAutomationHandler#execute(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute(HashMap<String, Object> contextParams) {
		
		try {
			// 资源请求ID
			String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			
			// 读取虚拟机列表，用于取得参数信息
			List<String> vmIds = getAutomationService().getDeviceIdsSort(rrinfoId);
			
			for (String vmid : vmIds) {
				deviceLogStr += "[" + vmid + "]";
			}
			log.debug("Command execute devices: " + deviceLogStr);
			
			ExecutorService exec = Executors.newFixedThreadPool(6);
			Map<String, StringBuffer> results = new HashMap<String, StringBuffer>();
			for (String vmId : vmIds) {
				results.put(vmId, new StringBuffer());
			}
			Map<String,Future<String>> futureMap = Maps.newHashMap();
			for (String vmId : vmIds) {
				PingVMInstance instance = new PingVMInstance();
				
				HashMap<String, Object> params = (HashMap<String, Object>) Utils.depthClone(contextParams);
				List<String> ids = new ArrayList<String>();
				ids.add(vmId);
				params.put("destVmIds", ids);
				
				log.debug("Start execute for device: " + vmId);
				Future<String> future = exec.submit(new HandlerThread(instance, params, results));
				futureMap.put(vmId, future);
			}
			
			putFutureResult(futureMap,results);
			exec.shutdown();
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			log.debug("Command execute finish. Devices: " + deviceLogStr);
			
			String errorResult = "";
			for (String key : results.keySet()) {
				log.info(results.get(key).toString());
				if (!(results.get(key).toString().equals(MesgRetCode.SUCCESS))) {
					errorResult += "[" + results.get(key) + "]";
				}
			}
			if (!errorResult.equals("")) {
				log.error("Command execute error: " + errorResult);
				return errorResult;
			}
			return MesgRetCode.SUCCESS;
		} catch (Exception e) {
			String errorMsg = "错误：{" + e + "}";
			log.error(errorMsg);
			printExceptionStack(e);
			return errorMsg;
		} finally {
			if (contextParams != null) {
				contextParams.clear();
			}
		}
	}
	
}
