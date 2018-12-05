package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;

/** 
 * 获取token
 * @author SunHailong 
 * @version 版本号 2017-3-30
 */
public class GetTokenHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(GetTokenHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		HashMap<String, String> deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(0));
		String domainName = (String) deviceParams.get("DOMAIN_NAME");
		String openstackIp = deviceParams.get("OPENSTACK_IP");
		if(openstackIp == null || "".equals(openstackIp)) {
			throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
		}
		String projectName = deviceParams.get("PROJECT_NAME");
		if(projectName == null || "".equals(projectName)) {
			throw new Exception("Project名称为空，请检查参数[PROJECT_NAME].");
		}
		logger.info("获取token开始...");
		String token = OpenstackPowerVcServiceFactory.getTokenServiceInstance(openstackIp,domainName).getToken(projectName);
		if(token == null || "".equals(token)) {
			logger.error("获取token失败，openstackIp:" + openstackIp + ";projectName:" + projectName);
			throw new Exception("获取token失败，openstackIp:" + openstackIp + ";projectName:" + projectName);
		}
		logger.info("获取token结束...");
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			setHandleResultParam(String.valueOf(deviceIdList.get(i)), "TOKEN", token);
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
}
