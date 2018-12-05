package com.git.cloud.handler.automation.pv.init;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.vo.OpenstackVmParamVo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;
import com.git.cloud.resmgt.network.model.po.CloudProjectPo;
import com.git.support.constants.PubConstants;
import com.google.common.collect.Maps;


public class OpenstackPowerVcVEInitParam extends LocalAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(OpenstackPowerVcVEInitParam.class);
	
	@Override
	public String service(Map<String, Object> contenxtParams) throws Exception {
		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);
		// 服务请求Id
		String srvReqId = (String) contenxtParams.get(SRV_REQ_ID);
		// 资源请求ID
		String rrinfoId = (String) contenxtParams.get(RRINFO_ID);
		String commonInfo = ",服务请求ID:" + srvReqId + ",资源请求ID:" + rrinfoId + ",流程实例ID:" + flowInstId;
		logger.info("[OpenstackVEInitParam]构建所需参数实例化开始" + commonInfo);
		//参数清理
		getBizParamInstService().deleteParamInstsOfFlow(flowInstId);
		// 获取设备ID
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		int len = deviceIdList == null ? 0 : deviceIdList.size();
		if(len == 0) {
			logger.error("[OpenstackVEInitParam]获取设备信息失败" + commonInfo);
			throw new Exception("[OpenstackVEInitParam]获取设备信息失败" + commonInfo);
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
		
		logger.info("[OpenstackVEInitParam]构建所需参数实例化结束" + commonInfo);
		
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
		// 初始化特殊参数
		String deviceName = "";
		String hostName = "";
//		String serverId = "";
		try {
			CmVmPo vm = getAutomationService().getVm(deviceId);
			deviceName = vm.getDeviceName();
			hostName = vm.getHostName();
//			serverId = vm.getServerId();
		} catch (Exception e) {
			logger.error("[OpenstackVEInitParam]获取设备信息失败,deviceID:" + deviceId);
		}
		RmGeneralServerVo server = null;
		try {
			server = getAutomationService().findDeviceServerInfo(deviceId);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		if(server == null) {
			logger.error("[OpenstackVEInitParam]获取服务器信息失败,deviceID:" + deviceId);
			throw new Exception("[OpenstackVEInitParam]获取服务器信息失败,deviceID:" + deviceId);
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
		String targetFlavorId = "";
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
//				JSONObject fla = JSONObject.fromObject(object);
//				Integer cpu = fla.getInt("vcpus");
//				Integer mem = fla.getInt("ram");
//				Integer disk = fla.getInt("disk");
//				if(flavor.getCpu() == cpu && flavor.getMem() == mem && flavor.getDisk() == disk) {
//					targetFlavorId = (String)fla.get("id");
//					break;
//				}
//			}
//		}
		IProjectVpcDao projectVpcDaoImpl = (IProjectVpcDao) WebApplicationManager.getBean("projectVpcDaoImpl");
		CloudProjectPo projectVpcPo = projectVpcDaoImpl.findProjectByProjectId(openstackVmParamVo.getProjectId());
		String projectIaasUuid = projectVpcPo.getIaasUuid();
		
		ICmVmDAO cmVmDAO = (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
		CmVmPo cmVmPo = cmVmDAO.findCmVmById(deviceId);
		String serviceIassUuid = cmVmPo.getIaasUuid();
		
		oaParamMap.put("OPENSTACK_IP", openstackVmParamVo.getOpenstackIp());
		oaParamMap.put("DOMAIN_NAME", openstackVmParamVo.getDomainName());
		oaParamMap.put("OPENSTACK_ID", openstackId);
		oaParamMap.put("TENANT_ID", projectIaasUuid);
		oaParamMap.put("PROJECT_ID", projectIaasUuid);
		oaParamMap.put("PROJECT_NAME", openstackVmParamVo.getProjectName());
		oaParamMap.put("TARGET_SERVER_ID", serviceIassUuid);
		oaParamMap.put("SERVER_IP", server.getServerIp());
		oaParamMap.put("SERVER_NAME", deviceName);
		oaParamMap.put("HOST_NAME", hostName);

		oaParamMap.put("FLAVOR_ID", targetFlavorId); // 8c8g
		
		oaParamMap.put("AVAILABILITY_ZONE", openstackVmParamVo.getAzName());
		oaParamMap.put("VOLUME_TYPE", "");
		oaParamMap.put("CPU", json.getIntValue("cpu") + "");
		oaParamMap.put("MEM", json.getIntValue("mem") + "");
		oaParamMap.put("SYS_DISK", json.getIntValue("sysDisk") + "");
		oaParamMap.put("HOST_TYPE", RmHostType.VIRTUAL.getValue());
	}
}
