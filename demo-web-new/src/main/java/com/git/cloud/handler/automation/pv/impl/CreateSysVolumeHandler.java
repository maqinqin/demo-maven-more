package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;
import com.git.cloud.powervc.model.VolumeModel;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.openstack.model.vo.VolumeDetailVo;
import com.git.support.common.PVOperation;

/** 
 * 创建系统卷
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class CreateSysVolumeHandler extends OpenstackPowerVcCommonHandler {

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
			String azName = deviceParams.get("AVAILABILITY_ZONE");
			if(azName == null || "".equals(azName)) {
				throw new Exception("可用分区为空，请检查参数[AVAILABILITY_ZONE].");
			}
			String sysDisk = deviceParams.get("SYS_DISK");
			if(sysDisk == null || "".equals(sysDisk)) {
				throw new Exception("卷大小为空，请检查参数[SYS_DISK].");
			}
			String imageId = deviceParams.get("IMAGE_ID");
			if(azName == null || "".equals(azName)) {
				throw new Exception("镜像Id为空，请检查参数[IMAGE_ID].");
			}
			VolumeModel volumeModel = new VolumeModel();
			String volumeName = "sysDisk"+System.currentTimeMillis();
			volumeModel.setName(volumeName);
			volumeModel.setAzName(azName);
			volumeModel.setVolumeType(deviceParams.get("VOLUME_TYPE"));
			volumeModel.setDiskSize(sysDisk);
			volumeModel.setImageId(imageId);
			volumeModel.setIsShare("");
			String volumeId = UUIDGenerator.getUUID();
			String iaasUuid = OpenstackPowerVcServiceFactory.getVolumeServiceInstance(openstackIp,domainName, token).createVolume(projectId, volumeModel, PVOperation.CREATE_VOLUME);
			logger.debug(logCommon + "完成，开始进行系统处理...");
			CmDeviceDAO cmDeviceDAO = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
			VolumeDetailVo volumeVo = new VolumeDetailVo();
			volumeVo.setVolumeId(volumeId);
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
			setHandleResultParam(deviceIdList.get(i), "VOLUME_ID", volumeId);
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
}
