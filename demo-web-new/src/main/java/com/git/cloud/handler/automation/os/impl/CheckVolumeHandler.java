package com.git.cloud.handler.automation.os.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;

/** 
 * 查询数据卷
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class CheckVolumeHandler extends OpenstackCommonHandler {

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
				String version = deviceParams.get("VERSION");
				if(version == null || "".equals(version)) {
					throw new Exception("VERSION为空，请检查参数[VERSION].");
				}
				String manageOneIp = deviceParams.get("MANAGE_ONE_IP");
				if(manageOneIp == null || "".equals(manageOneIp)) {
					throw new Exception("MANAGE_ONE_IP为空，请检查参数[MANAGE_ONE_IP].");
				}
				String openstackIp = deviceParams.get("OPENSTACK_IP");
				if(openstackIp == null || "".equals(openstackIp)) {
					throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
				}
				String projectId = deviceParams.get("PROJECT_ID");
				if(projectId == null || "".equals(projectId)) {
					throw new Exception("ProjectId为空，请检查参数[PROJECT_ID].");
				}
				String volumeIds = (String) deviceParams.get("DATA_VOLUME_ID");
				if(volumeIds == null || "".equals(volumeIds)) {
					throw new Exception("卷ID为空，请检查参数[DATA_VOLUME_ID].");
				}
				String projectName = deviceParams.get("PROJECT_NAME");
	            if(projectName == null || "".equals(projectName)) {
	                 throw new Exception("ProjectName为空，请检查参数[PROJECT_NAME].");
	            }
				String [] volumeArr = volumeIds.split(",");
				OpenstackIdentityModel model = new OpenstackIdentityModel();
	            model.setVersion(version);
	            model.setOpenstackIp(openstackIp);
	            model.setDomainName(domainName);
	            model.setManageOneIp(manageOneIp);
	            model.setProjectId(projectId);
	            model.setProjectName(projectName);
				for (String volumeId : volumeArr) {
					String state = IaasInstanceFactory.storageInstance(version).getVolumeStatus(model, volumeId);
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
//				String state = OpenstackServiceFactory.getVolumeServiceInstance(openstackIp,domainName, token).getVolumeStatus(projectId, volumeIds);
//				if(state.equals("available")){
//					log.debug(logCommon + "完成...");
//				} else {
//					log.debug(logCommon + "，尚未创建完成...");
//				}
			}
			n--;
		}
	}

}
