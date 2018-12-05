package com.git.cloud.handler.automation.os.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.resmgt.openstack.dao.impl.OpenstackVolumeDaoImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 删除卷
 * @author SunHailong 
 * @version 版本号 2017-4-4
 */
public class DeleteVolumeHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(DeleteVolumeHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		List<String> deleteList = new ArrayList<String>();
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "为第" + (i+1) + "台计算实例删除数据盘";
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
			String volumeIds = deviceParams.get("DATA_VOLUME_ID");
			if(volumeIds == null || "".equals(volumeIds)) {
				throw new Exception("删除的卷为空，请检查参数[DATA_VOLUME_ID].");
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
			OpenstackVolumeDaoImpl openstackVolumeDaoImpl = (OpenstackVolumeDaoImpl) WebApplicationManager.getBean("openstackVolumeDaoImpl");
			String [] volumeArr = volumeIds.toString().split(",");
			for(int j=0 ; j<volumeArr.length ; j++) {
				if(!deleteList.contains(volumeArr[j])){
					String volumeDetail = IaasInstanceFactory.storageInstance(version).getVolumeDetail(model, volumeArr[j]);
					String shareable = JSONObject.fromObject(volumeDetail).getJSONObject("volume").get("shareable").toString();
					if(shareable != null && !"".equals(shareable) && shareable.equals("false")){//若非共享卷直接删除
						IaasInstanceFactory.storageInstance(version).deleteVolume(model, volumeArr[j]);
						String volumeId = openstackVolumeDaoImpl.selectCloudVolumeIdByIaasUuid(volumeArr[j]).getId();
						openstackVolumeDaoImpl.deleteVolume(volumeId);
					}else if(!deleteList.contains(volumeArr[j]) && shareable != null && !"".equals(shareable) && shareable.equals("true")){//若是共享卷需判断该卷是否被其他设备使用
						JSONArray vms = JSONObject.fromObject(volumeDetail).getJSONObject("volume").getJSONArray("attachments");
						if(vms.size() == 0){
							IaasInstanceFactory.storageInstance(version).deleteVolume(model, volumeArr[j]);
							String volumeId = openstackVolumeDaoImpl.selectCloudVolumeIdByIaasUuid(volumeArr[j]).getId();
							openstackVolumeDaoImpl.deleteVolume(volumeId);
							//isShareFlag = true;
							deleteList.add(volumeArr[j]);
						}
					}
				}
			}
			logger.debug(logCommon + "结束...");
		}
	}
	
}
