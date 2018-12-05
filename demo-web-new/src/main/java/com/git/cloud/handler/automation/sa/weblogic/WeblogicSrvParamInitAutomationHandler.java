package com.git.cloud.handler.automation.sa.weblogic;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.automation.sa.vmware.VMBuildParamInitAutomationHandler;
import com.git.support.constants.PubConstants;
import com.google.common.collect.Maps;

/**
 * weblogic云服务自动化参数初始化
 * <p>
 * 
 * @author mengzx
 * @version 1.0 2013-5-7
 * @see
 */
public class WeblogicSrvParamInitAutomationHandler extends
		LocalAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(WeblogicSrvParamInitAutomationHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.iomp.cloud.core.automation.handler.LocalAbstractAutomationHandler
	 * #service(java.util.Map)
	 */
	public String service(Map<String, Object> contenxtParams) throws Exception {

		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);

		// 服务请求Id
		String srvReqId = (String) contenxtParams.get(SRV_REQ_ID);

		logger.info("weblogic云服务参数实例化开始,服务请求ID:" + srvReqId + ",流程实例ID:"
				+ flowInstId);

		Map<String, Object> originalContenxtParams = Maps
				.newHashMap(contenxtParams);

		String result = PubConstants.EXEC_RESULT_SUCC;
		
		//参数清理
		getBizParamInstService().deleteParamInstsOfFlow(flowInstId);

		// 虚拟机参数初始化
		LocalAbstractAutomationHandler vmBuildParamInitAutomationHandler = new VMBuildParamInitAutomationHandler();
		result = vmBuildParamInitAutomationHandler
				.execute((HashMap<String, Object>) contenxtParams);

		// weblogic参数初始化
		if (PubConstants.EXEC_RESULT_SUCC.equals(result)) {

			LocalAbstractAutomationHandler weblogicBuildParamInitAutomationHandler = new WeblogicBuildParamInitAutomationHandler();
			result = weblogicBuildParamInitAutomationHandler
					.execute((HashMap<String, Object>) originalContenxtParams);
		}

		logger.info("weblogic云服务参数实例化结束,服务请求ID:" + srvReqId + ",流程实例ID:"
				+ flowInstId);

		return result;
	}

}
