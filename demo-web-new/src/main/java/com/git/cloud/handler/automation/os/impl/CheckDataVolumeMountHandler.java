package com.git.cloud.handler.automation.os.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;

/** 
 * 验证数据卷挂载结果
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class CheckDataVolumeMountHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CheckDataVolumeMountHandler.class);
	protected int executeTimes = 10; // 执行次数
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		if(deviceIdList == null)
			throw new Exception("deviceIdList is null,find by rrinfoId:"+rrinfoId);
		
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
				String volumeId = (String) deviceParams.get("DATA_VOLUME_ID");
				if(volumeId == null || "".equals(volumeId)) {
					throw new Exception("卷ID为空，请检查参数[DATA_VOLUME_ID].");
				}
				String projectName = deviceParams.get("PROJECT_NAME");
	              if(projectName == null || "".equals(projectName)) {
	                   throw new Exception("ProjectName为空，请检查参数[PROJECT_NAME].");
	            }
	            OpenstackIdentityModel model = new OpenstackIdentityModel();
	  		    model.setVersion(version);
	            model.setOpenstackIp(openstackIp);
	            model.setDomainName(domainName);
	            model.setManageOneIp(manageOneIp);
	            model.setProjectId(projectId);
	            model.setProjectName(projectName);
				String state = IaasInstanceFactory.storageInstance(version).getVolumeStatus(model, volumeId);
				if(state.equals("in-use")){
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
		if(successDeviceList.size() != deviceIdList.size()) {
			throw new Exception("挂载未成功，卷状态仍为可用.");
		}
	}

}
