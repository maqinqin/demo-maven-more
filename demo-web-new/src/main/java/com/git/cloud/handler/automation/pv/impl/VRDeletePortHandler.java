package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 查询网卡状态
 * @author lixiaojiang
 */
public class VRDeletePortHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(VRDeletePortHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "查询第" + (i+1) + "张网卡";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String domainName = deviceParams.get("DOMAIN_NAME");
			String openstackIp = deviceParams.get("OPENSTACK_IP");
//			String openstackId = deviceParams.get("OPENSTACK_ID");
			String token = deviceParams.get("TOKEN");
			String projectId = deviceParams.get("PROJECT_ID");
			String serverId = deviceParams.get("TARGET_SERVER_ID");
			String networkCard = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getNetworkCard(projectId, serverId);
			logger.debug(logCommon + "完成，开始进行数据处理...");
			JSONObject json = JSONObject.fromObject(networkCard);
			JSONArray jsa = json.getJSONArray("interfaceAttachments");
			Iterator<Object> iterator = jsa.iterator();
			String portId = "";
			while (iterator.hasNext()) {
				Object next = iterator.next();
				JSONObject nextObject = JSONObject.fromObject(next);
				portId = nextObject.get("port_id").toString();
				OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).deleteNetworkCard(projectId, serverId, portId);
				OpenstackPowerVcServiceFactory.getNetworkServiceInstance(openstackIp, domainName, token).deletePort(portId);
			}
			logger.debug("删除端口结束...");
		}
		
		//this.saveParam(reqMap);
	}

}
