package com.git.cloud.handler.automation.pv.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.pv.OpenstackPowerVcCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;

/** 
 * 卸载卷
 * @author SunHailong 
 * @version 版本号 2017-4-4
 */
public class UnmountServerVolumeHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(UnmountServerVolumeHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "为第" + (i+1) + "台计算实例卸载数据盘";
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
			String targetServerId = deviceParams.get("TARGET_SERVER_ID");
			if(targetServerId == null || "".equals(targetServerId)) {
				throw new Exception("计算实例Id为空，请检查参数[TARGET_SERVER_ID].");
			}
			String volumeIds = deviceParams.get("DATA_VOLUME_ID");
			if(volumeIds == null || "".equals(volumeIds)) {
				throw new Exception("卸载的卷为空，请检查参数[DATA_VOLUME_ID].");
			}
			String hostType = deviceParams.get("HOST_TYPE");
			if(hostType == null || "".equals(hostType)) {
				throw new Exception("主机类型为空，请检查参数[HOST_TYPE].");
			}
			Boolean isVm = null;
			if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
				isVm = true;
			} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
				isVm = false;
			} else {
				throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
			}
			String [] volumeArr = volumeIds.split(",");
			for(int j=0 ; j<volumeArr.length ; j++) {
				OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).unmountServerVolume(projectId, targetServerId, volumeArr[j], isVm);
				RmDeviceVolumesRefPo ref = new RmDeviceVolumesRefPo();
				ref.setTargetVolumeId(volumeArr[j]);
				ref.setDeviceId(deviceIdList.get(i));
				cmDeviceDAOImpl().deleteRmDeviceVolumesRef(ref);
			}
			logger.debug(logCommon + "结束...");
		}
	}
	public ICmDeviceDAO cmDeviceDAOImpl() throws Exception {
		return (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
	}
}
