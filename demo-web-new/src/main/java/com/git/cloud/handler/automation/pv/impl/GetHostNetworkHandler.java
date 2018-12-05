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
 * TODO 查询网络信息
 */
public class GetHostNetworkHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(GetHostNetworkHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "查询";
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
			String macAddr = "";
			String oldIp_address =  deviceParams.get("OLD_IP_ADDRESS");
			while (iterator.hasNext()) {
				Object next = iterator.next();
				JSONObject nextObject = JSONObject.fromObject(next);
				JSONArray fixed_ips = nextObject.getJSONArray("fixed_ips");
				Iterator iterator2 = fixed_ips.iterator();
				while (iterator2.hasNext()) {
					Object next2 = iterator2.next();
					JSONObject fromObject = JSONObject.fromObject(next2);
					String ipAddress = fromObject.get("ip_address").toString();
					if(oldIp_address.equals(ipAddress)){
						portId = nextObject.get("port_id").toString();
						macAddr = nextObject.get("mac_addr").toString();
						break;
					}
				}
			}
			if (!"".equals(portId)) {
				setHandleResultParam(deviceIdList.get(i), "PORT_ID", portId);
				setHandleResultParam(deviceIdList.get(i), "MAC_ADDR", macAddr);
			}else{
				throw new Exception("未找到匹配的资源");
			}
			logger.debug(logCommon + "结束...");
		}
		
		this.saveParam(reqMap);
	}


}
