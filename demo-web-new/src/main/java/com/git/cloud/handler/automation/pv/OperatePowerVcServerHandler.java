package com.git.cloud.handler.automation.pv;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;
import com.git.cloud.powervc.service.OpenstackPowerVcComputeService;

/** 
 * 操作计算实例
 * 包括：关机、开机、重启、删除、调整规格、确认规格
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public abstract class OperatePowerVcServerHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(OperatePowerVcServerHandler.class);
	
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
			String openstackIp = deviceParams.get("OPENSTACK_IP");
			if(openstackIp == null || "".equals(openstackIp)) {
				throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
			}
			String token = deviceParams.get("TOKEN");
			if(token == null || "".equals(token)) {
				throw new Exception("认证TOKEN为空，请检查参数[TOKEN].");
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
			OpenstackPowerVcComputeService openstackComputeService = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token);
			if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
				openstackComputeService.operateHost(projectId, serverId, this.getOperateCode(), this.getResultCode());
			} else if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
				openstackComputeService.operateVm(projectId, serverId, this.getOperateCode(), this.getResultCode());
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