package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;

/** 
 * 查询数据卷
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class CheckVolumeHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CheckVolumeHandler.class);
	protected int executeTimes = 50; // 执行次数
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		boolean flag = false;
		int n = executeTimes;
		while(true) {
			int temp = 0;
			if(n == 0) {
				break;
			}
			if(flag == true) {
				break;
			}
			try {
				// 休眠30秒
				Thread.sleep(30000);
			} catch(Exception e) {
				logger.error("异常exception",e);
			}
			for(int i=0 ; i<deviceIdList.size() ; i++) {
				logCommon = "查询第" + (i+1) + "个数据卷";
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
				String volumeIds = (String) deviceParams.get("DATA_VOLUME_ID");
				if(volumeIds == null || "".equals(volumeIds)) {
					throw new Exception("卷ID为空，请检查参数[DATA_VOLUME_ID].");
				}
				String [] volumeArr = volumeIds.split(",");
				for (String volumeId : volumeArr) {
					String state = null;//OpenstackServiceFactory.getVolumeServiceInstance(openstackIp,domainName, token).getVolumeStatus(projectId, volumeId);
					if(state.equals("available") || state.equals("in-use")){
						temp = temp + 1;
					}
				}
				if(temp == volumeArr.length){
					flag = true;
					logger.debug(logCommon + "完成...");
				} else {
					logger.debug(logCommon + "，尚未卸载完成...");
				}
			}
			n--;
		}
	}

}
