/**
 * Copyright (c) 2014, Git Co., Ltd. All rights reserved.
 *
 * 审核人：
 */
package com.git.cloud.handler.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.support.common.MesgRetCode;
import com.git.support.util.CloudGlobalConstants;


/**
 * 
 * <p>
 * 脚本执行所需变量（如脚本Id，脚本执行范围）的查询工具类，
 * 提供一些可复用的查询方法。
 * @author gaoyang
 * @version 1.0 2013-11-6
 * @see
 */
public class ScriptUtil {
	
	private static final String EXE_WAY = "EXE_WAY";
	// 定义脚本执行范围的code，根据ADMIN_DIC字典表
	static final String EXE_ONLY_FIRST = "01";
	static final String EXE_ONLY_SECOND = "D2";
	static final String EXE_ONLY_THIRD = "D3";
	static final String EXE_ONLY_FOURTH = "D4";
	static final String EXE_SECOND_TO_LAST = "02";
	static final String EXE_ALL = "03";
	private static Logger logger = LoggerFactory
			.getLogger(ScriptUtil.class);
	
	/**
	 * 获取脚本Id列表
	 * @param contextParams
	 * @return
	 */
	public static List<String> getScriptIdList(HashMap<String, Object> contextParams){
		List<String> scriptIdList = new ArrayList<String>();
		Object flag = contextParams.get(CloudGlobalConstants.BUS_VERSION);
		logger.info("flat = " + flag);
		//直接从上下文获取scriptId
		logger.info("contextParams.get(CloudGlobalConstants.EXEC_SCRIPT_ID) = "
				+ contextParams.get(CloudGlobalConstants.EXEC_SCRIPT_ID).toString());
//			Long scriptId = Long.getLong(contextParams.get(CloudGlobalConstants.EXEC_SCRIPT_ID).toString());
		String scriptId = contextParams.get(CloudGlobalConstants.EXEC_SCRIPT_ID).toString();
		logger.info("scriptId new = " + scriptId);
		scriptIdList.add(scriptId);
		return scriptIdList;
	}
	
	/**
	 * 获取脚本执行范围
	 * @param contextParams
	 * @return
	 */
	public static String getExeWayCode(HashMap<String, Object> contextParams){
		String exeWayCode;
		Object flag = contextParams.get(CloudGlobalConstants.BUS_VERSION);
		//直接从上下文获取脚本执行范围
		exeWayCode = (String) contextParams.get(EXE_WAY);
		return exeWayCode;
	}
	
	/**
	 * 读取需要操作的虚拟机列表
	 * @param execProcessingObject
	 * @param vmIds
	 * @return
	 * @throws Exception 
	 */
	public static List<String> getProcessingVms(String exeWayCode, List<String> vmIds) throws Exception {
		
		List<String> processingVmIds = new ArrayList<String>();
		if (vmIds == null || vmIds.size() == 0) {
			throw new Exception("服务所关联的虚拟机数量为空。");
		}
		// 读取需要操作的虚拟机列表，如：有的命令只操作一个vioc1，有的需要在两个vioc上执行
		if (exeWayCode == null) {
			exeWayCode = EXE_ALL;
		}
		if (exeWayCode.equalsIgnoreCase(EXE_ONLY_FIRST)) {
			processingVmIds.add(vmIds.get(0));
		} else if (exeWayCode.equalsIgnoreCase(EXE_ONLY_SECOND)) {
			if(vmIds.size() < 2) {
				throw new Exception("虚拟机数量不足，获取不到第2台虚机");
			}
			processingVmIds.add(vmIds.get(1));
		} else if (exeWayCode.equalsIgnoreCase(EXE_ONLY_THIRD)) {
			if(vmIds.size() < 3) {
				throw new Exception("虚拟机数量不足，获取不到第3台虚机");
			}
			processingVmIds.add(vmIds.get(2));
		} else if (exeWayCode.equalsIgnoreCase(EXE_ONLY_FOURTH)) {
			if(vmIds.size() < 4) {
				throw new Exception("虚拟机数量不足，获取不到第4台虚机");
			}
			processingVmIds.add(vmIds.get(3));
		} else if (exeWayCode.equalsIgnoreCase(EXE_SECOND_TO_LAST)) {
			processingVmIds = vmIds.subList(1, vmIds.size());
		} else if (exeWayCode.equalsIgnoreCase(EXE_ALL)) {
			processingVmIds = vmIds;
		}else {
			logger.warn("流程节点操作的目标错误（应该为01或02或03），实际操作对象为：" + exeWayCode);
			exeWayCode = EXE_ALL;
		}
		return processingVmIds;
	}
	
	
	/**
	 * 生成最终的脚本执行结果
	 * @param resultsMap
	 * @param log
	 * @return
	 */
	public static String getFinalResult(Map<Long,String> resultsMap, Logger log){
		String finalResult = MesgRetCode.SUCCESS;
		for (Long key : resultsMap.keySet()){
			String result = resultsMap.get(key);
			if(!result.equals(MesgRetCode.SUCCESS)){
				log.debug("脚本在设备Id【" + key +"】上返回码为【" + result + "】，执行失败！");
				finalResult = MesgRetCode.ERR_PROCESS_FAILED;
			}else 	
				log.debug("脚本在设备Id【" + key +"】上返回码为【" + result + "】，执行成功！");
		}
		return finalResult;
	}
	
	/**
	 * 将适配器返回的脚本执行结果用其中第一个“=”隔开
	 * 
	 * @param info
	 * @return
	 */
	public static String[] parseReturnedInfoByFirstEqual(String info) {
		return info.split("=", 2);
	}

	/**
	 * 验证脚本返回的结果参数与SCRIPT_PARAMETER表里预定义的结果参数是否匹配。 若不匹配，返回false；若匹配，返回true
	 * 
	 * @param echoParamName
	 * @param outParamNameList
	 * @return
	 */
	public static boolean validate(String echoParamName,
			List<String> outParamNameList) {
		if (outParamNameList.contains(echoParamName)) {
			return true;
		}
		return false;
	}

	/**
	 * 将适配器返回的脚本执行结果用其中所有的“=”隔开
	 * 
	 * @param info
	 * @return
	 */
	public static String[] parseReturnedInfoByEveryEqual(String info) {
		return info.split("=");
	}

	
	
	
	

}
