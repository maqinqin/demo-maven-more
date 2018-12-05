package com.git.cloud.handler.automation.sa.ssh;

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
import org.springframework.stereotype.Component;

import com.git.cloud.handler.automation.AbstractAutomationHandler;
import com.git.cloud.handler.automation.sa.common.HandlerThread;
import com.git.cloud.handler.common.ScriptUtil;
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgRetCode;
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
@Component("scriptExecutionHandler")
public class ScriptExecutionHandler extends AbstractAutomationHandler {
	
	private static Logger log = LoggerFactory
			.getLogger(ScriptExecutionHandler.class);
	
	
	// 输出的日志中的公用信息
		private String logMsg = null;
		
		String deviceLogStr = "";

	static final String EXCUTE_PARAMS = "EXCUTE_PARAMS"; // 自定义脚本执行参数的key
	static final String SCRIPT_PATH_AND_NAME = "SCRIPT_PATH_AND_NAME"; // 自定义脚本绝对路径和文件名的key

	public static final String SSH_SA_RESULT = "SSH_SA_RESULT";// ssh适配器返回的执行结果的key
	public static final String ECHO_INFO = "ECHO_INFO"; //
	public static final String EXIT_CODE = "EXIT_CODE";
	public static final String ERROR_INFO = "ERROR_INFO";
	public static final int EXECUTE_SUCCESS_CODE = 0;
	public static final int EXECUTE_FAIL_CODE = 1;
	public static final String IS_SUCCESS = "IS_SUCCESS"; // 自己定义的要保存至数据库中的表示脚本是否执行成功的key
	

	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 从contextParams取到设备Id列表，再根据设备id到表里取到所有跟设备相关的参数 循环设备id表执行操作
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute(HashMap<String, Object> contextParams) {
		if(contextParams!=null){
			log.debug("*******************20140324 debuging : contextParams = "+contextParams.toString() +"*********************************");

			String rrinfoId = null;
			String srInfoId = null;
			String instanceId = null;
			String nodeId = null;	

			try {		
				
				// 资源请求ID
				rrinfoId = (String) contextParams.get(RRINFO_ID);				
				// 节点ID
				nodeId = (String) contextParams.get(NODE_ID); 
				//服务请求ID
				srInfoId = (String) contextParams.get(SRV_REQ_ID);
				//流程实例ID
				instanceId = getContextStringPara(contextParams, FLOW_INST_ID);
				
				// 根据服务请求Id获取设备列表(本次服务请求需要创建的虚拟机)初始化每台设备对应的参数值
				String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
				log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
				List<String> devs = getDeviceIdList(instanceId, nodeId, rrinfoId, redoErrorDev);
				log.debug("*******************20140324 debuging : devIdList = "+ devs.toString() +"*********************************");

//				String exeWayCode = scriptExecutionDAO.findExeWayCodeByNodeId(nodeId);
				//获取脚本执行范围
				String exeWayCode = ScriptUtil.getExeWayCode(contextParams);
				log.debug("*******************20140324 debuging : exeWayCode = "+ exeWayCode +"*********************************");
				
				// 读取虚拟机列表，用于取得参数信息
				List<String> vmIds = devs;	//ScriptUtil.getProcessingVms(exeWayCode, devs);
				// 如果用户选择执行出错设备，则不需要重新过滤所需执行的设备
				if (! (redoErrorDev != null && redoErrorDev.trim().equalsIgnoreCase("true"))) {
					vmIds = ScriptUtil.getProcessingVms(exeWayCode, devs);
				}
				
				for (String vmid : vmIds) {
					deviceLogStr += "[" + vmid + "]";
				}
				log.debug("Command execute devices: " + deviceLogStr);
				
				int threadNum = getHandlerThreadNum(contextParams);
				log.debug("Command thread count: " + threadNum);
				
				ExecutorService exec = Executors.newFixedThreadPool(threadNum);
				Map<String, StringBuffer> resultsMap = new HashMap<String, StringBuffer>();
				for (String vmId : vmIds) {
					resultsMap.put(vmId, new StringBuffer());
				}
				Map<String,Future<String>> futureMap = Maps.newHashMap();
				for (String vmId : vmIds) {
					ScriptExecutionInstance instance = new ScriptExecutionInstance();
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
				
				// 构造工作流新版本的返回值
				String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						rrinfoId, srInfoId, resultsMap, instanceId, nodeId);
				return handlerReturn;
			} catch (Exception e) {
				String errorMsg = logMsg + "错误：{" + e + "}";
				log.error(errorMsg);
				printExceptionStack(e);
//				return errorMsg;
				// 构造工作流新版本的返回值
				return getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						errorMsg, MesgRetCode.ERR_OTHER);
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
