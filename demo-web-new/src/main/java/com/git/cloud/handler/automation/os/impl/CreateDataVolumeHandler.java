package com.git.cloud.handler.automation.os.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VolumeRestModel;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.openstack.model.vo.VolumeDetailVo;
import com.git.support.common.OSOperation_bak;

/** 
 * 创建数据卷
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class CreateDataVolumeHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CreateDataVolumeHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		BmSrRrinfoPo rrinfo = getAutomationService().getRrinfo(rrinfoId);
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
		String logCommon = "";
		String volumeId = "";
		String iaasUuid = "";
		String volumeName = "";
		boolean isShare = false;
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			if(!isShare) { // 非共享卷时
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
				String azName = deviceParams.get("AVAILABILITY_ZONE");
				if(azName == null || "".equals(azName)) {
					throw new Exception("可用分区为空，请检查参数[AVAILABILITY_ZONE].");
				}
				String deviceName = deviceParams.get("SERVER_NAME");
				if(deviceName == null || "".equals(deviceName)) {
					throw new Exception("设备名称为空，请检查参数[SERVER_NAME].");
				}
				String dataDisk = json.getInteger("dataDisk")+"";
				if(dataDisk == null || "".equals(dataDisk)) {
					throw new Exception("数据卷大小为空，请检查参数[DATA_DISK].");
				}
				String hostType = deviceParams.get("HOST_TYPE");
				if(hostType == null || "".equals(hostType)) {
					throw new Exception("主机类型为空，请检查参数[HOST_TYPE].");
				}
				String projectName = deviceParams.get("PROJECT_NAME");
				if(projectName == null || "".equals(projectName)) {
					throw new Exception("ProjectName为空，请检查参数[PROJECT_NAME].");
				}
				if(json.getBooleanValue("diskShareFlag")) {
					isShare = true;
				}
				VolumeRestModel volumeModel = new VolumeRestModel();
				if(isShare) {
					String firstDevice = "";
					String lastDevice = "";
					for(int k=0 ; k<deviceIdList.size() ; k++) {
						HashMap<String, String> deviceMap = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
						if(k == 0) {
							firstDevice = deviceMap.get("SERVER_NAME");
						}
						if(k == deviceIdList.size() - 1) {
							lastDevice = deviceMap.get("SERVER_NAME");
							try {
								lastDevice = lastDevice.substring(lastDevice.length() - 2);
							} catch(Exception e) {
								e.printStackTrace();
							}
						}
					}
					volumeName = firstDevice + "-" + lastDevice + "_sharedisk1_"+System.currentTimeMillis();
				} else {
					volumeName = deviceName+"_disk1_"+System.currentTimeMillis();
				}
				volumeModel.setName(volumeName);
				volumeModel.setAzName(azName);
				volumeModel.setVolumeType(deviceParams.get("VOLUME_TYPE"));
				volumeModel.setDiskSize(dataDisk);
				//volumeModel.setIsShare("H".equals(hostType) ? "false" : "true");
				volumeModel.setIsShare(json.getBooleanValue("diskShareFlag")+"");
				OpenstackIdentityModel model = new OpenstackIdentityModel();
				model.setVersion(version);
	            model.setOpenstackIp(openstackIp);
	            model.setDomainName(domainName);
	            model.setManageOneIp(manageOneIp);
	            model.setProjectId(projectId);
	            model.setProjectName(projectName);
	            volumeId = UUIDGenerator.getUUID();
	            iaasUuid = IaasInstanceFactory.storageInstance(version).createVolume(model, volumeModel, OSOperation_bak.CREATE_DATA_VOLUME);
				logger.debug(logCommon + "完成，开始进行数据处理...");
				CmDeviceDAO cmDeviceDAO = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
				VolumeDetailVo volumeVo = new VolumeDetailVo();
				volumeVo.setVolumeId(volumeId);
				volumeVo.setAzName(azName);
				String myProjectId = deviceParams.get("MY_PROJECT_ID");
				volumeVo.setProjectId(myProjectId);
				volumeVo.setVolumeName(volumeName);
				volumeVo.setVolumeSize(dataDisk);
				volumeVo.setVolumeType(deviceParams.get("VOLUME_TYPE"));
				volumeVo.setIsShareVol(json.getBooleanValue("diskShareFlag")+"");
				volumeVo.setSysVolumeVal("1");
				volumeVo.setIaasUuid(iaasUuid);
				cmDeviceDAO.saveOpenstackVolume(volumeVo);
				logger.debug(logCommon + "结束...");
			}
			setHandleResultParam(deviceIdList.get(i), "DATA_VOLUME_ID", iaasUuid);
			setHandleResultParam(deviceIdList.get(i), "MY_DATA_VOLUME_ID", volumeId);
			setHandleResultParam(deviceIdList.get(i), "DATA_VOLUME_NAME", volumeName);
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
	
	public AutomationService getAutomationService() throws Exception {
		return (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
	}
}
