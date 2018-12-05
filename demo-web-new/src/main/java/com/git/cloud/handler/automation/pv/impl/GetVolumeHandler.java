package com.git.cloud.handler.automation.pv.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;

/** 
 * 查询系统卷
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class GetVolumeHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(GetVolumeHandler.class);
	protected int executeTimes = 50; // 执行次数
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		int len = deviceIdList == null ? 0 : deviceIdList.size();
		List<String> successDeviceList = new ArrayList<String> ();
		int n = executeTimes;
		while(true) {
			if(successDeviceList.size() == len) {
				break;
			}
			if(n == 0) {
				break;
			}
			try {
				// 休眠30秒
				Thread.sleep(30000);
			} catch(Exception e) {
				logger.error("异常exception",e);
			}
			for(int i=0 ; i<deviceIdList.size() ; i++) {
				String vmId = deviceIdList.get(i);
				if(successDeviceList.indexOf(vmId) >= 0) {
					continue;
				}
				logCommon = "查询第" + (i+1) + "个系统卷";
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
				String volumeId = (String) deviceParams.get("VOLUME_ID");
				if(volumeId == null || "".equals(volumeId)) {
					throw new Exception("卷ID为空，请检查参数[VOLUME_ID].");
				}
				String state = OpenstackPowerVcServiceFactory.getVolumeServiceInstance(openstackIp,domainName, token).getVolumeStatus(projectId, volumeId);
				if(state.equals("available")){
					if(successDeviceList.indexOf(vmId) < 0) {
						successDeviceList.add(vmId);
						logger.debug(logCommon + "完成...");
					}
				} else {
					logger.debug(logCommon + "，尚未创建完成...");
				}
			}
			n--;
		}
	}

}
