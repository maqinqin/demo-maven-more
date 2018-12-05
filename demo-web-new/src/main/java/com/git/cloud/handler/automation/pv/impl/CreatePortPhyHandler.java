package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;

import net.sf.json.JSONObject;

/**
 * TODO 创建port(虚拟机)
 * @author 
 */
public class CreatePortPhyHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CreatePortPhyHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "创建端口";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String domainName = deviceParams.get("DOMAIN_NAME");
			String openstackIp = deviceParams.get("OPENSTACK_IP");
//			String openstackId = deviceParams.get("OPENSTACK_ID");
			String token = deviceParams.get("TOKEN");
			String projectId = deviceParams.get("PROJECT_ID");
			String serverId = deviceParams.get("TARGET_SERVER_ID");
			String networkId = deviceParams.get("NETWORK_ID");
			String subnetId = deviceParams.get("SUBNET_ID");
			String ipAddress = deviceParams.get("IP_ADDRESS");
			String hostName = deviceParams.get("HOST_NAME");
			String macAddr = deviceParams.get("MAC_ADDR");
			String leaf = deviceParams.get("LEAF");
			String pportId = deviceParams.get("PPORT_ID");
			String switchId = deviceParams.get("SWITCH_ID");
//			String name = "";
			String jsonData = OpenstackPowerVcServiceFactory.getNetworkServiceInstance(openstackIp,domainName, token).createPortPhy(networkId, subnetId, ipAddress, hostName, projectId, serverId,macAddr,leaf,pportId,switchId);
			String portId = JSONObject.fromObject(jsonData).getJSONObject("port").get("id").toString();
			JSONObject.fromObject(jsonData).getJSONObject("port").get("id").toString();
			reqMap.put("NEW_PORT_ID", portId);
//			log.debug(logCommon + "完成，开始进行数据处理...");
//			JSONObject json = JSONObject.fromObject(jsonData);
//			serverId = json.getJSONObject("server").getString("id");
			setHandleResultParam(deviceIdList.get(i), "NEW_PORT_ID", portId);
//			AdminKeyMapUtil.getAdminKeyMapService().saveAdminKeyMapForOpenstack(
//					KeyTypeEnum.COMPUTE_BAREMETAL.getValue(), deviceIdList.get(i), openstackId, serverId);
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
}
