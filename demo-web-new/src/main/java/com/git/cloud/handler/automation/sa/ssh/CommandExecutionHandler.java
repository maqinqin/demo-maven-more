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

import com.git.cloud.handler.automation.AbstractAutomationHandler;
import com.git.cloud.handler.automation.IAutomationHandler;
import com.git.cloud.handler.automation.sa.common.HandlerThread;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.support.common.MesgRetCode;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;
/**
 * 
 * <p> 在目标服务器执行命令行
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-5-22 
 * @see
 */
public class CommandExecutionHandler extends AbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(CommandExecutionHandler.class);
	private static final String VM_ALL = "VM-ALL";
	private static final String VM1 = "VM1";
	/* 操作的对象,key：EXEC_PROCESSING_OBJECT
	选择：NIM Server(NIM-Server),HMC Server(HMC-Server), 虚拟机1(VM1)，所有虚拟机(VM-ALL)，括号中的内容为key的值*/
	private static final String EXEC_PROCESSING_OBJECT = "EXEC_PROCESSING_OBJECT";
	private String deviceLogStr = "";

	/* (non-Javadoc)
	 * @see com.ccb.iomp.cloud.core.automation.handler.IAutomationHandler#execute(java.util.HashMap)
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) {
		
		String rrinfoId = null;
		String srInfoId = null;
		String instanceId = null;
		String nodeId = null;
		try {
			// 资源请求ID
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			instanceId = getContextStringPara(contextParams, FLOW_INST_ID);
			nodeId = getContextStringPara(contextParams, NODE_ID);
			
			// 读取命令操作的目标服务器类型
			String execProcessingObject = null;
			execProcessingObject = contextParams.get(EXEC_PROCESSING_OBJECT) == null ? "" : contextParams.get(EXEC_PROCESSING_OBJECT).toString();
			
			// 读取虚拟机列表，用于取得参数信息
			String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
			log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
			List<String> devs = getDeviceIdList(instanceId, nodeId, rrinfoId, redoErrorDev);
			
			List<String> vmIds = getProcessingVms(execProcessingObject, devs);
			
			for (String vmid : vmIds) {
				deviceLogStr += "[" + vmid + "]";
			}
			log.debug("Command execute devices: " + deviceLogStr);
			
			/**
			 *  因为读取lpar id需要单线程执行，否则容易引起lpar id重复
			 *  判断如果为读取lparid的命令则指开启单线程，其它命令为多线程
			 */
			ExecutorService exec = null;
			String execComm = null;
			execComm = contextParams.get(CommandExecutionInstance.EXEC_COMMAND_STR) == null ? "" : contextParams.get(CommandExecutionInstance.EXEC_COMMAND_STR).toString();
			log.debug("Command is: " + execComm);
			// 如果本节点为创建虚拟机则，只能开启一个线程
			if (execComm.trim().indexOf("mksyscfg ") > -1) {
				log.debug("Command thread count: " + 1);
				exec = Executors.newFixedThreadPool(1);
			} else {
				int threadNum = getHandlerThreadNum(contextParams);
				log.debug("Command thread count: " + threadNum);
				exec = Executors.newFixedThreadPool(threadNum);
			}
			
			Map<String, StringBuffer> results = new HashMap<String, StringBuffer>();
			for (String vmId : vmIds) {
				results.put(vmId, new StringBuffer());
			}
			Map<String,Future<String>> futureMap = Maps.newHashMap();
			for (String vmId : vmIds) {
				IAutomationHandler instance = getCommandExecutionInstance();//new CommandExecutionInstance();
				
				@SuppressWarnings("unchecked")
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
			
			BmSrPo srpo = getAutomationService().getSrById(srInfoId);
			String handlerReturn = null;
			if (srpo != null && srpo.getSrTypeMark() != null && srpo.getSrTypeMark().equals("2")) {
				log.debug("Sr No: " + srpo.getSrTypeMark());
				for (StringBuffer sb : results.values()) {
					log.debug("CommandExecuteInstance exec result: " + sb.toString());
				}
				// 供给服务申请 ，构造工作流新版本的返回值
				handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						rrinfoId, srInfoId, results, instanceId, nodeId);
			} else {
				handlerReturn = getHandlerAutomationReturnForReco(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						rrinfoId, srInfoId, results, instanceId, nodeId);
				return handlerReturn;
			}
			log.debug("CommandExecuteHandler return str: " + handlerReturn);
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

	/**
	 * 读取需要操作的虚拟机列表
	 * @param execProcessingObject
	 * @param vmIds
	 * @return
	 * @throws Exception 
	 */
	private List<String> getProcessingVms(String execProcessingObject, List<String> vmIds) throws Exception {
		
		List<String> processingVmIds = new ArrayList<String>();
		if (vmIds == null || vmIds.size() == 0) {
			throw new Exception("服务所关联的虚拟机数量为空。");
		}
		// 读取需要操作的虚拟机列表，如：有的命令只操作一个vioc1，有的需要在两个vioc上执行
		if (execProcessingObject.equalsIgnoreCase(VM1)) {
			processingVmIds.add(vmIds.get(0));
		} else if (execProcessingObject.equalsIgnoreCase(VM_ALL)) {
			processingVmIds = vmIds;
		} else {
			throw new Exception("流程节点操作的目标错误（应该为VM1或VM-ALL），实际操作对象为：" + execProcessingObject);
		}
		return processingVmIds;
	}
	
	/**
	 * 读取instance类
	 * @return
	 */
	protected IAutomationHandler getCommandExecutionInstance() {
		IAutomationHandler instance = new CommandExecutionInstance();
		return instance;
	}
}
