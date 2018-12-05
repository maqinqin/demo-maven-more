package com.git.cloud.handler.automation.os.init;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.cloudservice.dao.impl.CloudServiceDaoImpl;
import com.git.cloud.cloudservice.model.po.CloudFlavorPo;
import com.git.cloud.cloudservice.model.po.CloudServiceVo;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.handler.vo.OpenstackVmParamVo;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;
import com.git.cloud.resmgt.network.dao.IVirtualNetworkDao;
import com.git.cloud.resmgt.network.dao.IVirtualSubnetDao;
import com.git.cloud.resmgt.network.dao.VirtualRouterDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.support.constants.PubConstants;
import com.google.common.collect.Maps;


public class OpenstackPSInitParam extends LocalAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(OpenstackPSInitParam.class);
	
	@Override
	public String service(Map<String, Object> contenxtParams) throws Exception {
		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);
		// 服务请求Id
		String srvReqId = (String) contenxtParams.get(SRV_REQ_ID);
		// 资源请求ID
		String rrinfoId = (String) contenxtParams.get(RRINFO_ID);
		String commonInfo = ",服务请求ID:" + srvReqId + ",资源请求ID:" + rrinfoId + ",流程实例ID:" + flowInstId;
		logger.info("[OpenstackInitParam]构建所需参数实例化开始" + commonInfo);
		//参数清理
		getBizParamInstService().deleteParamInstsOfFlow(flowInstId);
		// 获取设备ID
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		int len = deviceIdList == null ? 0 : deviceIdList.size();
		if(len == 0) {
			logger.error("[OpenstackInitParam]获取设备信息失败" + commonInfo);
			throw new Exception("[OpenstackInitParam]获取设备信息失败" + commonInfo);
		}
		Map<String, String> srvAttrInfoMap;
		for(int i=0 ; i<len ; i++) {
			// 获取申请填写的参数
			srvAttrInfoMap = getAutomationService().getSrAttrInfoByRrinfoId(rrinfoId);
			// 初始化服务自动化参数信息
			try {
				this.buildOpenstackParam(rrinfoId, deviceIdList.get(i), contenxtParams, srvAttrInfoMap, commonInfo);
			} catch (Exception e) {
				logger.error(e + commonInfo);
				return PubConstants.EXEC_RESULT_FAIL;
			}
		}
		
		logger.info("[OpenstackInitParam]构建所需参数实例化结束" + commonInfo);
		
		return PubConstants.EXEC_RESULT_SUCC;
	}

	private void buildOpenstackParam(String rrinfoId, String deviceId, Map<String, Object> contenxtParams, Map<String, String> srvAttrInfoMap, String commonInfo) throws Exception {
		Map<String, String> oaParamMap = Maps.newHashMap();
		contenxtParams.put(deviceId, oaParamMap);
		this.setHandleResultParam(deviceId, oaParamMap);
		// 添加所有云服务属性中定义的参数
		for (String attrName : srvAttrInfoMap.keySet()) {
			oaParamMap.put(attrName, srvAttrInfoMap.get(attrName));
		}
		
		OpenstackVmParamVo openstackVmParamVo = null;
		try {
//			server = getAutomationService().findDeviceServerInfo(deviceId);
			openstackVmParamVo = getAutomationService().findOpenstackVmParamByVmId(deviceId);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		if(openstackVmParamVo == null) {
			logger.error("[OpenstackInitParam]获取服务器信息失败,deviceID:" + deviceId);
			throw new Exception("[OpenstackInitParam]获取服务器信息失败,deviceID:" + deviceId);
		}
		String openstackId = openstackVmParamVo.getOpenstackId();
		String openstackIp = openstackVmParamVo.getOpenstackIp();
		String domainName =  openstackVmParamVo.getDomainName();
		String version = openstackVmParamVo.getVersion();
		String targetFlavorId = "";
		String volumeType = "";
		BmSrRrinfoPo rrinfo = getAutomationService().getRrinfo(rrinfoId);
		CloudFlavorPo flavor = new CloudFlavorPo();
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
		String flavorName = json.getIntValue("cpu") + "C" + json.getIntValue("mem") +"M" + json.getIntValue("sysDisk") + "G";
		flavorName = "flavorH_" + flavorName;
		flavor.setName(flavorName);
		flavor.setCpu(rrinfo.getCpu());			
		flavor.setDisk(rrinfo.getSysDisk());
		flavor.setMem(rrinfo.getMem());
		OpenstackIdentityModel model = new OpenstackIdentityModel();
		model.setVmMsId(openstackId);
		model.setOpenstackIp(openstackIp);
		model.setDomainName(domainName);
		model.setManageOneIp(openstackVmParamVo.getManageOneIp());
		model.setVersion(version);
		logger.info("OpenstackIdentityModel model:" + model.toString());
		String projectId = IaasInstanceFactory.identityInstance(version).getManageProject(model);
		model.setProjectId(projectId);
		JSONArray flavors = JSONObject.parseObject(IaasInstanceFactory.computeInstance(version).getFlavor(model)).getJSONArray("flavors");
		for (Object object : flavors) {
			JSONObject fla = JSONObject.parseObject(JSON.toJSONString(object));
			String name = (String)fla.get("name");
			if(flavorName.equals(name)) {
				targetFlavorId = (String)fla.get("id");
				break;
			}
		}
		try {
			//ICloudFlavorDao cloudFlavorDao = (ICloudFlavorDao) WebApplicationManager.getBean("cloudFlavorDao");
			IBmSrRrVmRefDao bmSrRrVmRefDao = (IBmSrRrVmRefDao) WebApplicationManager.getBean("bmSrRrVmRefDaoImpl");
			BmSrRrVmRefPo bmSrRrVmRefPo = bmSrRrVmRefDao.findBmSrRrVmVolumeTypeByDeviceId(deviceId);
			if(bmSrRrVmRefPo != null){
				volumeType = bmSrRrVmRefPo.getVolumeType();
			}else{
				logger.error("[OpenstackInitParam]获取设备卷信息失败，deviceId：" + deviceId);
				throw new Exception("[OpenstackInitParam]获取设备卷信息失败，deviceId：" + deviceId);
			}
			//CloudFlavorPo flavor = cloudFlavorDao.findFlavor(rrinfo.getCpu(), rrinfo.getMem(), rrinfo.getSysDisk(), RmHostType.PHYSICAL.getValue());
			
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		CloudServiceDaoImpl cloudServiceDaoImpl = (CloudServiceDaoImpl) WebApplicationManager.getBean("cloudServiceDaoImpl");
		CloudServiceVo cloudService= cloudServiceDaoImpl.findAllById(json.getString("serviceId"));
		String imageIaasUuid = cloudService.getIaasUuid();
		if(imageIaasUuid == null || "".equals(imageIaasUuid)){
			throw new Exception("[OpenstackInitParam]初始化镜像ID失败，ID为空");
		}
	    IProjectVpcDao projectVpcDao = (IProjectVpcDao) WebApplicationManager.getBean("projectVpcDao");
	    String targetProjectId = projectVpcDao.findProjectByProjectId(openstackVmParamVo.getProjectId()).getIaasUuid();
	    
	    IVirtualNetworkDao virtualNetworkDaoImpl = (IVirtualNetworkDao) WebApplicationManager.getBean("virtualNetworkDao");
		String targetNetworkId =   virtualNetworkDaoImpl.selectVirtualNetwork(openstackVmParamVo.getNetworkId()).getIaasUuid();
		
		IVirtualSubnetDao virtualSubnetDaoImpl = (IVirtualSubnetDao) WebApplicationManager.getBean("virtualSubnetDao");
		String targetSubnetId = virtualSubnetDaoImpl.selectVirtualSubnetPoById(openstackVmParamVo.getSubnetId()).getIaasUuid();
		
		if(CommonUtil.isEmpty(targetSubnetId)) {
			throw new Exception("[OpenstackInitParam]初始化内部网络子网失败，ID为空");
		}
		VirtualRouterDao virtualRouterDao = (VirtualRouterDao) WebApplicationManager.getBean("virtualRouterDao");
		String targetRouterId = virtualRouterDao.selectVirtualRouterPrimaryKey(openstackVmParamVo.getRouterId()).getIaasUuid();
				
		if(CommonUtil.isEmpty(targetRouterId)) {
			throw new Exception("[OpenstackInitParam]初始化内部网络关联的路由失败，ID为空");
		}
		oaParamMap.put("OPENSTACK_IP", openstackVmParamVo.getOpenstackIp());
		oaParamMap.put("DOMAIN_NAME", openstackVmParamVo.getDomainName());
		oaParamMap.put("OPENSTACK_ID", openstackId);
		oaParamMap.put("VERSION", version);
		oaParamMap.put("MANAGE_ONE_IP", openstackVmParamVo.getManageOneIp());
		
		oaParamMap.put("NETWORK_ID", targetNetworkId);
		oaParamMap.put("TENANT_ID", targetProjectId);
		oaParamMap.put("PROJECT_ID", targetProjectId);
		oaParamMap.put("PROJECT_NAME", openstackVmParamVo.getProjectName());
		oaParamMap.put("IMAGE_ID", imageIaasUuid);
		oaParamMap.put("FLAVOR_ID", targetFlavorId);
		oaParamMap.put("VOLUME_TYPE", volumeType);
		oaParamMap.put("AVAILABILITY_ZONE", openstackVmParamVo.getAzName());
		oaParamMap.put("CPU", json.getIntValue("cpu") + "");
		oaParamMap.put("MEM", json.getIntValue("mem") + "");
		oaParamMap.put("SYS_DISK", json.getIntValue("sysDisk") + "");
		
		oaParamMap.put("SERVER_IP", openstackVmParamVo.getServerIp());
		oaParamMap.put("SERVER_NAME", openstackVmParamVo.getVmName());
		oaParamMap.put("HOST_NAME", openstackVmParamVo.getHostName());
		oaParamMap.put("USER_NAME", openstackVmParamVo.getUserName());
		oaParamMap.put("USER_PASSWORD", PwdUtil.decryption(openstackVmParamVo.getPassword()));
		
		oaParamMap.put("SUBNET_ID", targetSubnetId);
		oaParamMap.put("ROUTER_ID", targetRouterId);
		
		IVirtualNetworkDao virtualNetworkDao = (IVirtualNetworkDao) WebApplicationManager.getBean("virtualNetworkDao");
		List<OpenstackIpAddressPo> ipList = virtualNetworkDao.selectIpAddressByDeviceId(deviceId);
		if(ipList == null|| ipList.size() == 0){
			throw new Exception("[OpenstackInitParam]初始化获取IP失败，IP为空");
		}
		String ipData = "";
		for (OpenstackIpAddressPo ip : ipList) {
			if(ipData.equals("")){
				oaParamMap.put("SERVER_IP",ip.getIp());
			}
			ipData+=",{\"uuid\":\""+targetNetworkId+"\",\"fixed_ip\":"+"\""+ip.getIp()+"\""+"}";
		}
		
//		RmNwIpAddrDAO rmNwIpAddrDAO = (RmNwIpAddrDAO) WebApplicationManager.getBean("rmNwIpAddrDAO");
//		List<RmNwIpAddressPo> ips = rmNwIpAddrDAO.findIpAddrs(deviceId);
//		if(ips == null|| ips.size() == 0){
//			throw new Exception("[OpenstackInitParam]初始化获取IP失败，IP为空");
//		}
//		String ipData="";
//		for (RmNwIpAddressPo ip : ips) {
//			String netId = AdminKeyMapUtil.getAdminKeyMapService().getTargetIdByMyIdForOpenstack(
//					KeyTypeEnum.NETWORK_PRIVATE.getValue(), ip.getNwResPoolId(), openstackId);
//			ipData+=",{\"uuid\":\""+netId+"\",\"fixed_ip\":"+"\""+ip.getIp()+"\""+"}";
//		}
		ipData = ipData.substring(1);
		oaParamMap.put("IP_DATAS", ipData);
		oaParamMap.put("HOST_TYPE", RmHostType.PHYSICAL.getValue());
	}
}
