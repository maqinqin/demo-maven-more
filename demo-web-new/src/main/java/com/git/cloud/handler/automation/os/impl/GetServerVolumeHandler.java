package com.git.cloud.handler.automation.os.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 查询服务器挂载的卷
 * @author SunHailong 
 * @version 版本号 2017-4-4
 */
public class GetServerVolumeHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(GetServerVolumeHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "创建第" + (i+1) + "个数据盘";
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
			String targetServerId = deviceParams.get("TARGET_SERVER_ID");
			if(targetServerId == null || "".equals(targetServerId)) {
				throw new Exception("计算实例Id为空，请检查参数[TARGET_SERVER_ID].");
			}
			String hostType = deviceParams.get("HOST_TYPE");
			if(hostType == null || "".equals(hostType)) {
				throw new Exception("主机类型为空，请检查参数[HOST_TYPE].");
			}
			String projectName = deviceParams.get("PROJECT_NAME");
            if(projectName == null || "".equals(projectName)) {
                 throw new Exception("ProjectName为空，请检查参数[PROJECT_NAME].");
            }
			Boolean isVm = null;
			if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
				isVm = true;
			} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
				isVm = false;
			} else {
				throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
			}
			OpenstackIdentityModel model = new OpenstackIdentityModel();
			model.setVersion(version);
            model.setOpenstackIp(openstackIp);
            model.setDomainName(domainName);
            model.setManageOneIp(manageOneIp);
            model.setProjectId(projectId);
            model.setProjectName(projectName);
            String jsonData = IaasInstanceFactory.computeInstance(version).getServerVolume(model, targetServerId, isVm);
			logger.debug("服务器[" + deviceIdList.get(i) + "]挂载的卷：" + jsonData);
			logger.debug(logCommon + "完成，开始进行数据处理...");
			JSONObject json = JSONObject.fromObject(jsonData);
			JSONObject subJson;
			JSONArray array = json.getJSONArray("volumeAttachments");
			StringBuffer volumeIds = new StringBuffer();
			for(int j=0 ; j<array.size() ; j++) {
				subJson = array.getJSONObject(j);
				if(j > 0) {
					volumeIds.append(",");
				}
				volumeIds.append(subJson.getString("volumeId"));
			}
			setHandleResultParam(deviceIdList.get(i), "DATA_VOLUME_ID", volumeIds.toString());
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}

}
