/**
 * Copyright (c) 2014, Git Co., Ltd. All rights reserved.
 *
 * 审核人：
 */
package com.git.cloud.handler.automation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.BizException;





/**
 * 本地自动化操作，直接执行相应的操作，不通过适配器执行
 * <p>
 * 
 * @author mengzx
 * @version 1.0 2013-4-27
 * @see
 */
public abstract class LocalAbstractAutomationHandler extends
		AbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(LocalAbstractAutomationHandler.class);

	/**
	 * 执行自动化操作业务逻辑
	 * 
	 * @param contenxtParams
	 *            上下文参数
	 * @return
	 */
	public String execute(HashMap<String, Object> contenxtParams) throws Exception {
		String result = null;
		if(contenxtParams!=null){
			// 流程实例Id
			String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contenxtParams.get(NODE_ID);
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			try {
				// 获取全局业务参数
				Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);
				// 将工作流相关参数和业务参数合并
				contenxtParams.putAll(handleParams);
				// 扩展业务参数
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParams);
				if (extHandleParams != null)
					contenxtParams.putAll(extHandleParams);
				// 获取操作执行结果状态
				result = service(contenxtParams);
				// 保存新产生的参数实例
				saveParamInsts(flowInstId, nodeId);
			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:"
						+ nodeId + ".错误信息:" + e.getMessage();
				result = errorMsg;
				log.error("异常exception",e);
				log.error(errorMsg, e);
			} finally {
				// 尽快的释放内存
				if (contenxtParams != null){
					contenxtParams.clear();
				}
			}
			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId, new Long((System.currentTimeMillis() - startTime)) });
		}else{
			result = "ERR_PARAMETER_WRONG;contextParams is null";
		}
		return result;
	}

	/**
	 * 抽象方法，由子类重写该方法执行具体的业务操作
	 * 
	 * @param contenxtParams
	 *            上下文参数
	 * @return
	 * @throws Exception 
	 */
	public abstract String service(Map<String, Object> contenxtParams) throws Exception;
	
	protected void checkDeviceParaNotNull(List<String> deviceIdList, Map<String, Map<String, String>> deviceParaMap, 
			List<String> notNullParamList) throws Exception {
		boolean checkSuccess = true;
		for (String vmId : deviceIdList) {
			Map<String, String> oneDeviceParaMap = deviceParaMap.get(vmId.toString());
			for (String string : notNullParamList) {
				String value = oneDeviceParaMap.get(string);
				if (StringUtils.isEmpty(value)) {
					log.error("没有读取到参数{}的参数值。", new Object[] {string});
					checkSuccess = false;
				}
			}
		}
		if (! checkSuccess) {
			throw new Exception("非空参数检查失败。");
		}
	}

}
