package com.git.cloud.handler.automation.pv.init;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.cloudservice.dao.impl.CloudServiceDaoImpl;
import com.git.cloud.cloudservice.dao.impl.ImageDaoImpl;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudServiceVo;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.vo.OpenstackVmParamVo;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.impl.BmSrRrinfoDaoImpl;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;
import com.git.cloud.resmgt.network.dao.IVirtualNetworkDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;
import com.git.cloud.resmgt.network.model.po.VirtualNetworkPo;
import com.git.support.constants.PubConstants;
import com.google.common.collect.Maps;



public class OpenstackPowerVcVSInitParam extends LocalAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(OpenstackPowerVcVSInitParam.class);
	
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
		String targetFlavorId = "";
		
		String volumeType = "";
		BmSrRrinfoPo rrinfo = getAutomationService().getRrinfo(rrinfoId);
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
		//targetFlavorId = rrinfo.getFlavorId();
		targetFlavorId = json.getString("flavorId");
//		if("".equals(targetFlavorId)|| targetFlavorId == null){
//			CloudFlavorPo flavor = new CloudFlavorPo();
//			String flavorName = rrinfo.getCpu() + "C" + rrinfo.getMem() +"M" + rrinfo.getSysDisk() + "G";
//			flavorName = "flavorV_" + flavorName;
//			flavor.setName(flavorName);
//			flavor.setCpu(rrinfo.getCpu());			
//			flavor.setDisk(rrinfo.getSysDisk());
//			flavor.setMem(rrinfo.getMem()==null?0:rrinfo.getMem());
//			flavor.setType(RmHostType.VIRTUAL.getValue());
//			String token = OpenstackPowerVcServiceFactory.getTokenServiceInstance(openstackIp,domainName).getToken();
//			String projectId = OpenstackPowerVcServiceFactory.getIdentityServiceInstance(openstackIp,domainName, token).getManageProject();
//			JSONArray flavors = JSONObject.parseObject(OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getFlavor(projectId)).getJSONArray("flavors");
//			for (Object object : flavors) {
//				JSONObject fla = JSONObject.parseObject(JSON.toJSONString(object));
//				int cpu = fla.getIntValue("vcpus");
//				int mem = fla.getIntValue("ram");
//				int disk = fla.getIntValue("disk");
//				if(flavor.getCpu() == cpu && flavor.getMem() == mem && flavor.getDisk() == disk) {
//					targetFlavorId = (String)fla.get("id");
//					break;
//				}
//			}
//		}
		try {
			IBmSrRrVmRefDao bmSrRrVmRefDao = (IBmSrRrVmRefDao) WebApplicationManager.getBean("bmSrRrVmRefDaoImpl");
			BmSrRrVmRefPo bmSrRrVmRefPo = bmSrRrVmRefDao.findBmSrRrVmVolumeTypeByDeviceId(deviceId);
			if(bmSrRrVmRefPo != null){
				volumeType = bmSrRrVmRefPo.getVolumeType();
			}else{
				logger.error("[OpenstackInitParam]获取设备卷信息失败，deviceId：" + deviceId);
				throw new Exception("[OpenstackInitParam]获取设备卷信息失败，deviceId：" + deviceId);
			}
			//获取配额
			//CloudFlavorPo flavor = cloudFlavorDao.findFlavor(rrinfo.getCpu(), rrinfo.getMem(), rrinfo.getSysDisk(), RmHostType.VIRTUAL.getValue());
			
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		BmSrRrinfoDaoImpl bmSrRrinfoDaoImpl = (BmSrRrinfoDaoImpl) WebApplicationManager.getBean("bmSrRrinfoDaoImpl");
		BmSrRrinfoPo srrInfo = bmSrRrinfoDaoImpl.findBmSrRrinfoById(rrinfoId);
		CloudServiceDaoImpl cloudServiceDaoImpl = (CloudServiceDaoImpl) WebApplicationManager.getBean("cloudServiceDaoImpl");
		CloudServiceVo cloudService= cloudServiceDaoImpl.findAllById(json.getString("serviceId"));
		String imageId = cloudService.getImageId();
		ImageDaoImpl imageDao = (ImageDaoImpl) WebApplicationManager.getBean("imageDao");
		if(imageId == null || "".equals(imageId)){
			throw new Exception("[OpenstackInitParam]初始化镜像ID失败，ID为空");
		}
		//获取镜像的iaasUuid 
		CloudImage cloudImage = imageDao.findImage(imageId);
		String imagesIaasUuid = cloudImage.getIaasUuid();
		//获取网络资源池的 iaasUuid
		IVirtualNetworkDao virtualNetworkDaoImpl = (IVirtualNetworkDao) WebApplicationManager.getBean("virtualNetworkDao");
		VirtualNetworkPo virtualNetworkPo = virtualNetworkDaoImpl.selectVirtualNetwork(openstackVmParamVo.getNetworkId());
		String virturlNetWorkIaasUuid = virtualNetworkPo.getIaasUuid();
		//获取projectId iaasUuid;
		IProjectVpcDao projectVpcDaoImpl = (IProjectVpcDao) WebApplicationManager.getBean("projectVpcDaoImpl");
		CloudProjectPo projectVpcPo = projectVpcDaoImpl.findProjectByProjectId(openstackVmParamVo.getProjectId());
		String projectIaasUuid = projectVpcPo.getIaasUuid();
		
		oaParamMap.put("OPENSTACK_IP", openstackVmParamVo.getOpenstackIp());
		oaParamMap.put("DOMAIN_NAME", openstackVmParamVo.getDomainName());
		oaParamMap.put("OPENSTACK_ID", openstackId);
		
		oaParamMap.put("MY_PROJECT_ID", openstackVmParamVo.getProjectId());
		oaParamMap.put("NETWORK_ID", virturlNetWorkIaasUuid);
		oaParamMap.put("TENANT_ID", projectIaasUuid);
		oaParamMap.put("PROJECT_ID", projectIaasUuid);
		oaParamMap.put("PROJECT_NAME", projectVpcPo.getVpcName());
		oaParamMap.put("IMAGE_ID", imagesIaasUuid);
		oaParamMap.put("FLAVOR_ID", targetFlavorId);
		oaParamMap.put("AVAILABILITY_ZONE", openstackVmParamVo.getAzName());
		oaParamMap.put("VOLUME_TYPE", volumeType);
		oaParamMap.put("CPU", json.getIntValue("cpu") + "");
		oaParamMap.put("MEM", json.getIntValue("mem") + "");
		oaParamMap.put("SYS_DISK", json.getIntValue("sysDisk") + "");
		
		IVirtualNetworkDao virtualNetworkDao = (IVirtualNetworkDao) WebApplicationManager.getBean("virtualNetworkDao");
		List<OpenstackIpAddressPo> ipList = virtualNetworkDao.selectIpAddressByDeviceId(deviceId);
		if(ipList == null|| ipList.size() == 0){
			throw new Exception("[OpenstackInitParam]初始化获取IP失败，IP为空");
		}
		
		String ipData="";
		for (OpenstackIpAddressPo ip : ipList) {
			ipData+=",{\"uuid\":\""+ip.getIaasUuid()+"\",\"fixed_ip\":"+"\""+ip.getIp()+"\""+"}";
			oaParamMap.put("SERVER_IP",ip.getIp());
		}
		ipData = ipData.substring(1);
		oaParamMap.put("IP_DATAS", ipData);
		oaParamMap.put("SERVER_NAME", openstackVmParamVo.getVmName());
		oaParamMap.put("HOST_NAME", ":"+openstackVmParamVo.getHostName());
		oaParamMap.put("USER_NAME", openstackVmParamVo.getUserName());
		oaParamMap.put("USER_PASSWORD", PwdUtil.decryption(openstackVmParamVo.getPassword()));
		oaParamMap.put("HOST_TYPE", RmHostType.VIRTUAL.getValue());
	}
}
