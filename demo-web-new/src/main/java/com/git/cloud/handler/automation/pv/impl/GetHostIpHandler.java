package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 查询端口详细信息
 */
public class GetHostIpHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(GetHostIpHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "查询端口信息";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String domainName = deviceParams.get("DOMAIN_NAME");
			String openstackIp = deviceParams.get("OPENSTACK_IP");
			String token = deviceParams.get("TOKEN");
			String portId = deviceParams.get("PORT_ID");
			String json = OpenstackPowerVcServiceFactory.getNetworkServiceInstance(openstackIp,domainName, token).getPortDetail(portId);
			String hostName = JSONObject.fromObject(json).getJSONObject("port").get("binding:host_id").toString();
			String macAddr = JSONObject.fromObject(json).getJSONObject("port").get("mac_address").toString();
			JSONObject info = JSONObject.fromObject(json).getJSONObject("port").getJSONObject("binding:profile");
			JSONArray temp = info.getJSONArray("local_link_information");
			JSONObject jso = temp.getJSONObject(0);
			String leaf = jso.get("switch_info").toString();
			String pportId = jso.get("port_id").toString();
			String switchId = jso.get("switch_id").toString();
			setHandleResultParam(deviceIdList.get(i), "HOST_NAME", hostName);
			setHandleResultParam(deviceIdList.get(i), "MAC_ADDR", macAddr);
			setHandleResultParam(deviceIdList.get(i), "LEAF", leaf);
			setHandleResultParam(deviceIdList.get(i), "PPORT_ID", pportId);
			setHandleResultParam(deviceIdList.get(i), "SWITCH_ID", switchId);
			logger.debug(logCommon + "结束...");
		}
		
		this.saveParam(reqMap);
	}

}
