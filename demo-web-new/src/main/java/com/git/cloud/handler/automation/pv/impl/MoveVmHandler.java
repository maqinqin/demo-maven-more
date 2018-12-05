package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.common.dao.impl.CmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmVmPo;

/** 
 * 虚拟机迁移
 * @author  
 * @version 
 */
public class MoveVmHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(MoveVmHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		CmDeviceDAO cmDeviceDAO = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
		CmVmDAO cmVmDAO = (CmVmDAO) WebApplicationManager.getBean("cmVmDAO");
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "迁移第" + (i+1) + "台机器";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String domainName = deviceParams.get("DOMAIN_NAME");
			String openstackIp = deviceParams.get("OPENSTACK_IP");
//			String openstackId = deviceParams.get("OPENSTACK_ID");
			String token = deviceParams.get("TOKEN");
			String projectId = deviceParams.get("PROJECT_ID");
			String serverId = deviceParams.get("TARGET_SERVER_ID");
			String hostName = deviceParams.get("HOST_NAME");
			OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).moveVm(projectId, serverId, hostName);
			String hostId = cmDeviceDAO.findVmIdByName(hostName);
			CmVmPo vm = cmVmDAO.findCmVmById(deviceIdList.get(i));
			vm.setHostId(hostId);
			cmVmDAO.updateCmVmHostId(vm);
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
//		this.saveParam(reqMap);
	}
}
