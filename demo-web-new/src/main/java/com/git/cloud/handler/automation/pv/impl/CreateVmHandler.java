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
import com.git.cloud.powervc.model.ServerModel;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmVmPo;

import net.sf.json.JSONObject;

/** 
 * 虚拟机计算实例创建，并挂载系统卷
 * @author SunHailong 
 * @version 版本号 2017-3-30
 */
public class CreateVmHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CreateVmHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logCommon = "创建第" + (i+1) + "台机器";
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String domainName = deviceParams.get("DOMAIN_NAME");
			String openstackIp = (String) deviceParams.get("OPENSTACK_IP");
			String openstackId = (String) deviceParams.get("OPENSTACK_ID");
			String token = (String) deviceParams.get("TOKEN");
			String projectId = deviceParams.get("PROJECT_ID");
			ServerModel serverModel = new ServerModel();
			serverModel.setAzName(deviceParams.get("AVAILABILITY_ZONE"));
			serverModel.setFlavorId(deviceParams.get("FLAVOR_ID"));
			serverModel.setNetworkId(deviceParams.get("NETWORK_ID"));
			serverModel.setServerIp(deviceParams.get("SERVER_IP"));
			String imageId = deviceParams.get("IMAGE_ID");
			String ipData = deviceParams.get("IP_DATAS");
			serverModel.setServerName(deviceParams.get("SERVER_NAME"));
			serverModel.setHostName(deviceParams.get("HOST_NAME"));
			serverModel.setVolumeId(deviceParams.get("VOLUME_ID"));
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
			String jsonData = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).createVm(projectId, serverModel,ipData,imageId);
			logger.debug(logCommon + "完成，开始进行数据处理...");
			//添加默认安全组
//			SecurityGroupsDaoImpl securityGroupsDao = (SecurityGroupsDaoImpl) WebApplicationManager.getBean("securityGroupsDao");
//			String myProjectId = AdminKeyMapUtil.getAdminKeyMapService().getMyIdByTargetIdForOpenstack(KeyTypeEnum.IDENTITY_PROJECT.getValue(), openstackId, projectId);
//			SecurityGroupsVo vo = securityGroupsDao.getSecurityGroupsVoByProjectIdAndName(myProjectId);
//			if(vo != null){
//				SecurityGroupsDeviceRefVo v = new SecurityGroupsDeviceRefVo();
//				v.setId(UUIDGenerator.getUUID());
//				v.setDeviceId(deviceIdList.get(i));
//				v.setSecurityGroupId(vo.getId());
//				securityGroupsDao.saveVmRef(v);
//			}
			JSONObject json = JSONObject.fromObject(jsonData);
			String iaasUuid = json.getJSONObject("server").getString("id");
			try {
				// 休眠30秒
				Thread.sleep(15000);
			} catch(Exception e) {
				e.printStackTrace();
			}
//			String serverDetail = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getServerDetail(projectId, serverId, isVm);
//			json = JSONObject.fromObject(serverDetail);
//			String hostId = json.getJSONObject("server").getString("OS-EXT-SRV-ATTR:hypervisor_hostname");
//			CmDeviceDAO cmDeviceDao = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
//			CmVmDAO cmVmDao = (CmVmDAO) WebApplicationManager.getBean("cmVmDAO");
//			hostId = cmDeviceDao.findVmIdByName(hostId);
//			CmVmPo cmVm = new CmVmPo();
//			cmVm.setHostId(hostId);
//			cmVm.setId(deviceIdList.get(i));
//			cmVmDao.updateCmVmHostId(cmVm);
			setHandleResultParam(deviceIdList.get(i), "TARGET_SERVER_ID", iaasUuid);
			CmVmPo cmvm = new CmVmPo();
			cmvm.setId(deviceIdList.get(i));
			cmvm.setIaasUuid(iaasUuid);
			ICmVmDAO cmVmDAO = (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
			cmVmDAO.updateCmVm(cmvm);
			//更新虚拟机对应的iaasUuid信息
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
}
