package com.git.cloud.handler.automation.os.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmMountStatusEnum;
import com.git.cloud.common.enums.RmVolumeTypeEnum;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;

/** 
 * 挂载卷
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class MountServerVolumeHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(MountServerVolumeHandler.class);
	protected int executeTimes = 50; // 执行次数
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		BmSrRrinfoPo rrinfo = getAutomationService().getRrinfo(rrinfoId);
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "为第" + (i+1) + "台计算实例挂载数据盘";
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
			String volumeId = deviceParams.get("DATA_VOLUME_ID");
			if(volumeId == null || "".equals(volumeId)) {
				throw new Exception("挂载的卷为空，请检查参数[DATA_VOLUME_ID].");
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
			IaasInstanceFactory.computeInstance(version).mountServerVolume(model, targetServerId, volumeId, isVm);
			int n = executeTimes;
			boolean mountFlag = false;
			while(true){
				if(n == 0) {
					break;
				}
				try {
					// 休眠5秒
					Thread.sleep(5000);
				} catch(Exception e) {
					logger.error("异常exception",e);
				}
				
				String state = IaasInstanceFactory.storageInstance(version).getVolumeStatus(model, volumeId);
			    if(state != null && !"".endsWith(state) && state.equals("in-use")){
			    	mountFlag = true;
			    	break;
			    }
			    n--;
			}
			logger.debug(logCommon + "结束...");
			//找到未挂载设备的数据
			String dataDisk = json.getInteger("dataDisk")+"";
			if(dataDisk == null || "".equals(dataDisk)) {
				throw new Exception("数据卷大小为空，请检查参数[DATA_DISK].");
			}
			String targetVolumeId = deviceParams.get("DATA_VOLUME_ID");
			if(targetVolumeId == null || "".equals(targetVolumeId)) {
				throw new Exception("数据卷ID为空，请检查参数[DATA_VOLUME_ID].");
			}
			String myVolumeId = deviceParams.get("MY_DATA_VOLUME_ID");
			if(myVolumeId == null || "".equals(myVolumeId)) {
				throw new Exception("数据卷ID为空，请检查参数[MY_DATA_VOLUME_ID].");
			}
			if(mountFlag) {
				RmDeviceVolumesRefPo ref = new RmDeviceVolumesRefPo();
				ref.setTargetVolumeId(targetVolumeId);
				ref.setVolumeId(myVolumeId);
				ref.setId(UUIDGenerator.getUUID());
				ref.setDiskSize(dataDisk);
				ref.setDeviceId(deviceIdList.get(i));
				ref.setVolumeName(deviceParams.get("DATA_VOLUME_NAME"));
				ref.setMountStatus(RmMountStatusEnum.MOUNT.getValue());
				ref.setDiskType(RmVolumeTypeEnum.EXTERNAL_DISK.getValue());
				cmDeviceDAOImpl().saveRmDeviceVolumesRefPo(ref);
			} else {
				throw new Exception("挂载卷失败.查询卷状态仍然未更新为in-use.");
			}
		}
	}
	public ICmDeviceDAO cmDeviceDAOImpl() throws Exception {
		return (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
	} 
	
	public AutomationService getAutomationService() throws Exception {
		return (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
	}
	
}
