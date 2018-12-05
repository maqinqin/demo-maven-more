package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;

/**
 * TODO 查询网卡是否添加成功
 * @author lixiaojiang
 */
public class CheckNetworkCardStatusHandler extends OpenstackPowerVcCommonHandler{

	@SuppressWarnings("unchecked")
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String networkCardStatus = null;
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String openstackIp = deviceParams.get("OPENSTACK_IP");
			String domainName = deviceParams.get("DOMAIN_NAME");
//			String openstackId = deviceParams.get("OPENSTACK_ID");
			String token = deviceParams.get("TOKEN");
			String projectId = deviceParams.get("PROJECT_ID");
			String serverId = deviceParams.get("TARGET_SERVER_ID");
			String portId = deviceParams.get("NEW_PORT_ID");
			networkCardStatus = null;//OpenstackServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getNetworkCardStatus(projectId, serverId, portId);
			//networkCardStatus = "ACTIVE";
			if(!networkCardStatus.equals("ACTIVE")){
				throw new Exception("错误");
			}
			
		}
		// 保存流程参数
//		this.saveParam(reqMap);
		//return networkCardStatus.equals("ACTIVE");
	}
}

