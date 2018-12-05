package com.git.cloud.handler.automation.os.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;

/** 
 * 计算实例修改规格
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public class ResizeServerHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(ResizeServerHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "第" + (i+1) + "台计算实例关机";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String domainName = deviceParams.get("DOMAIN_NAME");
			String version = deviceParams.get("VERSION");
			if(version == null || "".equals(version)) {
				throw new Exception("VERSION为空，请检查参数[VERSION].");
			}
			String manageOneIp = deviceParams.get("MANAGE_ONE_IP");
			if(manageOneIp == null || "".equals(manageOneIp)) {
				throw new Exception("MANAGE_ONE_IP为空，请检查参数[MANAGE_ONE_IP].");
			}
			String openstackIp = deviceParams.get("OPENSTACK_IP");
			if(openstackIp == null || "".equals(openstackIp)) {
				throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
			}
			String projectId = deviceParams.get("PROJECT_ID");
			if(projectId == null || "".equals(projectId)) {
				throw new Exception("ProjectId为空，请检查参数[PROJECT_ID].");
			}
			String serverId = deviceParams.get("TARGET_SERVER_ID");
			if(serverId == null || "".equals(serverId)) {
				throw new Exception("计算实例ID为空，请检查参数[TARGET_SERVER_ID].");
			}
			String flavorId = deviceParams.get("FLAVOR_ID");
			if(flavorId == null || "".equals(flavorId)) {
				throw new Exception("规格Id为空，请检查参数[FLAVOR_ID].");
			}
			String projectName = deviceParams.get("PROJECT_NAME");
            if(projectName == null || "".equals(projectName)) {
                throw new Exception("ProjectName为空，请检查参数[PROJECT_NAME].");
            }
			OpenstackIdentityModel model = new OpenstackIdentityModel();
            model.setVersion(version);
            model.setOpenstackIp(openstackIp);
            model.setDomainName(domainName);
            model.setManageOneIp(manageOneIp);
            model.setProjectName(projectName);
            model.setProjectId(projectId);
			IaasInstanceFactory.computeInstance(version).resizeVm(model, serverId, flavorId);
			logger.debug(logCommon + "结束...");
		}
	}
}
