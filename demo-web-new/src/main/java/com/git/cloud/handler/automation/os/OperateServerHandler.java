package com.git.cloud.handler.automation.os;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.enums.VersionEnum;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.support.common.OSOperation_bak;

/** 
 * 操作计算实例
 * 包括：关机、开机、重启、删除、调整规格、确认规格
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public abstract class OperateServerHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(OperateServerHandler.class);
	
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
			String hostType = deviceParams.get("HOST_TYPE");
			if(hostType == null || "".equals(hostType)) {
				throw new Exception("主机类型为空，请检查参数[HOST_TYPE].");
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
            model.setProjectId(projectId);
            model.setProjectName(projectName);
			if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
				if(version.equals(VersionEnum.HW_VSERSION_63.getValue()) && this.getOperateCode().equals(OSOperation_bak.DELETE_VM)) {
					IaasInstanceFactory.computeInstance(version).deleteHost(model, serverId);
				}else {
					IaasInstanceFactory.computeInstance(version).operateHost(model, serverId, this.getOperateCode(), this.getResultCode());
				}
			} else if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
				IaasInstanceFactory.computeInstance(version).operateVm(model, serverId, this.getOperateCode(),this.getResultCode());
			} else {
				throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
			}
			logger.debug(logCommon + "结束...");
		}
	}

	/**
	 * 获取操作编码
	 * @return
	 */
	protected abstract String getOperateCode();
	
	/**
	 * 获取返回编码
	 * @return
	 */
	protected abstract int getResultCode();
}
