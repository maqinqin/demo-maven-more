package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;

/**
 * TODO 设置弹性ip
 * @author 
 */
public class SetFloatingIpHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(SetFloatingIpHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "设置第" + (i+1) + "个弹性ip";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String openstackIp = deviceParams.get("OPENSTACK_IP");
			String domainName = deviceParams.get("DOMAIN_NAME");
//			String openstackId = deviceParams.get("OPENSTACK_ID");
			String token = deviceParams.get("TOKEN");
			String projectId = deviceParams.get("PROJECT_ID");
			String serverId = deviceParams.get("TARGET_SERVER_ID");
			String floatingIp = deviceParams.get("FLOATING_IP");
			OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).addFloatingIp(projectId, serverId, floatingIp);
			//log.debug(logCommon + "完成，开始进行数据处理...");
//			JSONObject json = JSONObject.fromObject(jsonData);
//			serverId = json.getJSONObject("server").getString("id");
			setHandleResultParam(deviceIdList.get(i), "SERVER_IP", floatingIp);
//			AdminKeyMapUtil.getAdminKeyMapService().saveAdminKeyMapForOpenstack(
//					KeyTypeEnum.COMPUTE_BAREMETAL.getValue(), deviceIdList.get(i), openstackId, serverId);
			try {
				// 休眠5秒
				Thread.sleep(5000);
			} catch(Exception e) {
				logger.error("异常exception",e);
			}
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
	
}
