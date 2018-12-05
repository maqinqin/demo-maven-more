package com.git.cloud.handler.automation.sa.vmware;

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
import com.git.cloud.handler.common.ScriptUtil;
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;


/**
 * 脚本自动化执行组件
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 May 8, 2013
 * @see
 */

public class WinScriptsExecutionHandler extends AbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(WinScriptsExecutionHandler.class);

	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 从contextParams取到设备Id列表，再根据设备id到表里取到所有跟设备相关的参数 循环设备id表执行操作
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) {	
		if(contextParams!=null){
			//获取脚本执行范围
			String exeWayCode = ScriptUtil.getExeWayCode(contextParams);
			String deviceLogStr = null;
			
			String rrinfoId = null;
			String srInfoId = null;
			String instanceId = null;
			String nodeId = null;
			
			try {
				
				rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
				srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
				instanceId = getContextStringPara(contextParams, FLOW_INST_ID);
				nodeId = getContextStringPara(contextParams, NODE_ID);
				
				//允许由前台指定部分设备执行动作
				String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
				log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
				List<String> devIdList = getDeviceIdList(instanceId, nodeId, rrinfoId, redoErrorDev);
				deviceLogStr = devIdList.toString();
				log.debug("*******************20140324 debuging : devIdList = "+ deviceLogStr +"*********************************");
				
				// 读取虚拟机列表，用于取得参数信息
				List<String> vmIds = ScriptUtil.getProcessingVms(exeWayCode, devIdList);
				
				log.debug("Command execute devices: " + deviceLogStr);
				
				ExecutorService exec = null;			
				int threadNum = getHandlerThreadNum(contextParams);
				log.debug("Command thread count: " + threadNum);
				exec = Executors.newFixedThreadPool(threadNum);
				
				Map<String, StringBuffer> resultsMap = new HashMap<String, StringBuffer>();
				for (String vmId : vmIds) {
					resultsMap.put(vmId, new StringBuffer());
				}
				Map<String,Future<String>> futureMap = Maps.newHashMap();
				for (String vmId : vmIds) {
					
					WinScriptsExecutionInstance instance = new WinScriptsExecutionInstance();
					
					@SuppressWarnings("unchecked")
					HashMap<String, Object> params = (HashMap<String, Object>) Utils.depthClone(contextParams);
					List<String> ids = new ArrayList<String>();
					ids.add(vmId);
					params.put("destVmIds", ids);
					
					log.debug("Start execute for device: " + vmId);
					Future<String> future = exec.submit(new HandlerThread(instance, params, resultsMap));
					futureMap.put(vmId, future);
				}
				
				putFutureResult(futureMap,resultsMap);
				exec.shutdown();
				exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
				log.debug("Command execute finish. Devices: " + deviceLogStr);
				
				// 供给服务申请 ，构造工作流新版本的返回值
				String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
								rrinfoId, srInfoId, resultsMap, instanceId, nodeId);
				log.debug("【WinScriptsExecutionHandler】 return str: " + handlerReturn);
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
		}else{
			return "ERR_PARAMETER_WRONG;contextParams is null";
		}
	}
}
