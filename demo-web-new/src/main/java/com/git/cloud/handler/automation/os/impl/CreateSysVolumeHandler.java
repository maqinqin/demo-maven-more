package com.git.cloud.handler.automation.os.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VolumeRestModel;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.openstack.model.vo.VolumeDetailVo;
import com.git.support.common.OSOperation_bak;

/** 
 * 创建系统卷
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class CreateSysVolumeHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CreateSysVolumeHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "创建第" + (i+1) + "个系统盘";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String version = deviceParams.get("VERSION");
			if(version == null || "".equals(version)) {
				throw new Exception("VERSION为空，请检查参数[VERSION].");
			}
			String manageOneIp = deviceParams.get("MANAGE_ONE_IP");
			if(manageOneIp == null || "".equals(manageOneIp)) {
				throw new Exception("MANAGE_ONE_IP为空，请检查参数[MANAGE_ONE_IP].");
			}
			String domainName = deviceParams.get("DOMAIN_NAME");
			String openstackIp = deviceParams.get("OPENSTACK_IP");
			if(openstackIp == null || "".equals(openstackIp)) {
				throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
			}
			String projectId = deviceParams.get("PROJECT_ID");
			if(projectId == null || "".equals(projectId)) {
				throw new Exception("ProjectId为空，请检查参数[PROJECT_ID].");
			}
			String azName = deviceParams.get("AVAILABILITY_ZONE");
			if(azName == null || "".equals(azName)) {
				throw new Exception("可用分区为空，请检查参数[AVAILABILITY_ZONE].");
			}
			String deviceName = deviceParams.get("SERVER_NAME");
			if(deviceName == null || "".equals(deviceName)) {
				throw new Exception("设备名称为空，请检查参数[SERVER_NAME].");
			}
			String sysDisk = deviceParams.get("SYS_DISK");
			if(sysDisk == null || "".equals(sysDisk)) {
				throw new Exception("卷大小为空，请检查参数[SYS_DISK].");
			}
			String imageId = deviceParams.get("IMAGE_ID");
			if(imageId == null || "".equals(imageId)) {
				throw new Exception("镜像Id为空，请检查参数[IMAGE_ID].");
			}
			String projectName = deviceParams.get("PROJECT_NAME");
			if(projectName == null || "".equals(projectName)) {
				throw new Exception("ProjectName为空，请检查参数[PROJECT_NAME].");
			}
			VolumeRestModel volumeRestModel = new VolumeRestModel();
			String volumeName = deviceName+"_disk_"+System.currentTimeMillis();
			volumeRestModel.setName(volumeName);
			volumeRestModel.setAzName(azName);
			volumeRestModel.setVolumeType(deviceParams.get("VOLUME_TYPE"));
			volumeRestModel.setDiskSize(sysDisk);
			volumeRestModel.setImageId(imageId);
			volumeRestModel.setIsShare("");
			OpenstackIdentityModel model = new OpenstackIdentityModel();
			model.setVersion(version);
			model.setDomainName(domainName);
			model.setProjectId(projectId);
			model.setOpenstackIp(openstackIp);
			model.setProjectId(projectId);
			model.setProjectName(projectName);
			model.setManageOneIp(manageOneIp);
			String volumeId = UUIDGenerator.getUUID();
			String iaasUuid = IaasInstanceFactory.storageInstance(version).createVolume(model,volumeRestModel,OSOperation_bak.CREATE_VOLUME);
			logger.debug(logCommon + "完成，开始进行系统处理...");
			CmDeviceDAO cmDeviceDAO = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
			VolumeDetailVo volumeVo = new VolumeDetailVo();
			volumeVo.setVolumeId(volumeId);
			logger.info("卷id："+ volumeId);
			volumeVo.setAzName(azName);
			String myProjectId = deviceParams.get("MY_PROJECT_ID");
			volumeVo.setProjectId(myProjectId);
			volumeVo.setVolumeName(volumeName);
			volumeVo.setVolumeSize(sysDisk);
			volumeVo.setVolumeType(deviceParams.get("VOLUME_TYPE"));
			volumeVo.setIsShareVol("false");
			volumeVo.setSysVolumeVal("0");
			volumeVo.setIaasUuid(iaasUuid);
			cmDeviceDAO.saveOpenstackVolume(volumeVo);
			setHandleResultParam(deviceIdList.get(i), "VOLUME_ID", iaasUuid);
			setHandleResultParam(deviceIdList.get(i), "MY_VOLUME_ID", volumeId);
			setHandleResultParam(deviceIdList.get(i), "SYS_VOLUME_NAME", volumeName);
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
}
