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
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VmRestModel;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.openstack.dao.impl.SecurityGroupsDaoImpl;
import com.git.cloud.resmgt.openstack.model.vo.SecurityGroupsDeviceRefVo;
import com.git.cloud.resmgt.openstack.model.vo.SecurityGroupsVo;


/** 
 * 虚拟机计算实例创建，并挂载系统卷
 * @author SunHailong 
 * @version 版本号 2017-3-30
 */
public class CreateVmHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CreateVmHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		BmSrRrinfoPo rrinfo = getAutomationService().getRrinfo(rrinfoId);
		JSONObject parameterJson = JSONObject.parseObject(rrinfo.getParametersJson());
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
			String projectId = deviceParams.get("PROJECT_ID");
			String vmGroupId = deviceParams.get("VM_GROUP_ID");
			String appointHostId = parameterJson.getString("appointHostId");
			String azName = deviceParams.get("AVAILABILITY_ZONE");
			String version = deviceParams.get("VERSION");
			if(version == null || "".equals(version)) {
				throw new Exception("VERSION为空，请检查参数[VERSION].");
			}
			String manageOneIp = deviceParams.get("MANAGE_ONE_IP");
			if(manageOneIp == null || "".equals(manageOneIp)) {
				throw new Exception("MANAGE_ONE_IP为空，请检查参数[MANAGE_ONE_IP].");
			}
			VmRestModel vmRestModel = new VmRestModel();
			if (appointHostId != null && !"".equals(appointHostId)) {
				// 指定物理机下发虚拟机
				azName = deviceParams.get("AVAILABILITY_ZONE")+ ":" + parameterJson.getString("appointHostName");
			}
			vmRestModel.setAzName(azName);
			vmRestModel.setFlavorId(deviceParams.get("FLAVOR_ID"));
			vmRestModel.setNetworkId(deviceParams.get("NETWORK_ID"));
			vmRestModel.setServerIp(deviceParams.get("SERVER_IP"));
			vmRestModel.setVmGroupId(vmGroupId);
			String ipData = deviceParams.get("IP_DATAS");
			vmRestModel.setServerName(deviceParams.get("SERVER_NAME"));
			vmRestModel.setHostName(deviceParams.get("HOST_NAME"));
			vmRestModel.setVolumeId(deviceParams.get("VOLUME_ID"));
			String hostType = deviceParams.get("HOST_TYPE");
			if(hostType == null || "".equals(hostType)) {
				throw new Exception("主机类型为空，请检查参数[HOST_TYPE].");
			}
			String projectName = deviceParams.get("PROJECT_NAME");
			if(projectName == null || "".equals(projectName)) {
				throw new Exception("ProjectName为空，请检查参数[PROJECT_NAME].");
			}
			/*Boolean isVm = null;
			if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
				isVm = true;
			} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
				isVm = false;
			} else {
				throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
			}*/
			String jsonData;
			OpenstackIdentityModel model = new OpenstackIdentityModel();
			model.setVersion(version);
            model.setOpenstackIp(openstackIp);
            model.setDomainName(domainName);
            model.setManageOneIp(manageOneIp);
            model.setProjectId(projectId);
            model.setProjectName(projectName);
			if(vmGroupId != null && !"".equals(vmGroupId)) {
				jsonData = IaasInstanceFactory.computeInstance(version).createVmForGroup(model, vmRestModel, ipData);
			}
			else {
				jsonData = IaasInstanceFactory.computeInstance(version).createVm(model, vmRestModel, ipData);
			}
			logger.debug(logCommon + "完成，开始进行数据处理...");
			//添加默认安全组
			SecurityGroupsDaoImpl securityGroupsDao = (SecurityGroupsDaoImpl) WebApplicationManager.getBean("securityGroupsDao");
			String myProjectId = deviceParams.get("MY_PROJECT_ID");
			SecurityGroupsVo vo = securityGroupsDao.getSecurityGroupsVoByProjectIdAndName(myProjectId);
			if(vo != null){
				SecurityGroupsDeviceRefVo v = new SecurityGroupsDeviceRefVo();
				v.setId(UUIDGenerator.getUUID());
				v.setDeviceId(deviceIdList.get(i));
				v.setSecurityGroupId(vo.getId());
				securityGroupsDao.saveVmRef(v);
			}
			JSONObject json = JSONObject.parseObject(jsonData);
			String serverId = json.getJSONObject("server").getString("id");
			/*try {
				// 休眠30秒
				Thread.sleep(30000);
			} catch(Exception e) {
				e.printStackTrace();
			}
			String serverDetail = OpenstackServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getServerDetail(projectId, serverId, isVm);
			json = JSONObject.fromObject(serverDetail);
			String status = json.getJSONObject("server").getString("status");
			if("error".equals(status)||"ERROR".equals(status)){
				OpenstackServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).operateVm(projectId, serverId, OSOperation_bak.DELETE_VM, 204);
				throw new RollbackableBizException("虚机创建失败,状态："+status);
			}
			String hostId = json.getJSONObject("server").getString("OS-EXT-SRV-ATTR:hypervisor_hostname");
			logger.info("OS-EXT-SRV-ATTR:hypervisor_hostname=" + hostId);
			CmDeviceDAO cmDeviceDao = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
			CmVmDAO cmVmDao = (CmVmDAO) WebApplicationManager.getBean("cmVmDAO");
			hostId = cmDeviceDao.findVmIdByName(hostId);
			logger.info("cmDeviceDao.findVmIdByName(hostId)=" + hostId);
			CmVmPo cmVm = new CmVmPo();
			cmVm.setHostId(hostId);
			cmVm.setId(deviceIdList.get(i));
			logger.info("deviceIdList.get(i)=" + deviceIdList.get(i));
			cmVmDao.updateCmVmHostId(cmVm);*/
			setHandleResultParam(deviceIdList.get(i), "TARGET_SERVER_ID", serverId);
			
			ICmVmDAO cmVmDAO = (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
			CmVmPo cmvm = new CmVmPo();
			cmvm.setId(deviceIdList.get(i));
			cmvm.setIaasUuid(serverId);
			cmVmDAO.updateCmVm(cmvm);
			logger.debug(logCommon + "结束...");
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
}
