package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;

/**
 * TODO 添加网卡
 * @author lixiaojiang
 */
public class AddNetworkCardTHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(AddNetworkCardTHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "添加第" + (i+1) + "张网卡";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String openstackIp = deviceParams.get("OPENSTACK_IP");
			String domainName = deviceParams.get("DOMAIN_NAME");
//			String openstackId = deviceParams.get("OPENSTACK_ID");
			String token = deviceParams.get("TOKEN");
			String projectId = deviceParams.get("PROJECT_ID");
			String serverId = deviceParams.get("TARGET_SERVER_ID");
			String newPortId = deviceParams.get("NEW_PORT_ID");
			String jsonData = null;//OpenstackServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).addNetworkCard(projectId, serverId, newPortId);
			//log.debug(logCommon + "完成，开始进行数据处理...");
//			JSONObject json = JSONObject.fromObject(jsonData);
//			serverId = json.getJSONObject("server").getString("id");
//			setHandleResultParam(deviceIdList.get(i), "TARGET_SERVER_ID", serverId);
//			AdminKeyMapUtil.getAdminKeyMapService().saveAdminKeyMapForOpenstack(
//					KeyTypeEnum.COMPUTE_BAREMETAL.getValue(), deviceIdList.get(i), openstackId, serverId);
			try {
				// 休眠10秒
				Thread.sleep(10000);
			} catch(Exception e) {
				logger.error("异常exception",e);
			}
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
//		this.saveParam(reqMap);s
	}
	
}
