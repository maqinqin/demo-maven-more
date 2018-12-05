package com.git.cloud.iaas.openstack.service;

import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.cloudservice.model.po.CloudFlavorPo;
import com.git.cloud.iaas.openstack.model.FlavorRestModel;
import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VmRestModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public class OpenstackComputeService2Impl extends OpenstackComputeServiceAbstractImpl implements OpenstackComputeService {
	
	/**
	 * 创建物理机计算实例
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String createHost(OpenstackIdentityModel model, VmRestModel vmRestModel,String ipData) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_HOST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("AVAILABILITY_ZONE", vmRestModel.getAzName());
		body.setString("FLAVOR_ID", vmRestModel.getFlavorId());
		body.setString("IMAGE_ID", vmRestModel.getImageId());
		body.setString("NETWORK_ID", vmRestModel.getNetworkId());
		body.setString("SERVER_IP", vmRestModel.getServerIp());
		body.setString("SERVER_NAME", vmRestModel.getServerName());
		body.setString("IP_DATAS", ipData);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 虚拟机添加安全组
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void addSecurityGroup(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.ADD_SECURITY_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", vmModel.getServerId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", vmModel.getServerId());
		body.setString("SECURITY_GROUP_ID", vmModel.getSecurityGroupId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 虚拟机移除安全组
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void removeSecurityGroup(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.REMOVE_SECURITY_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", vmModel.getServerId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", vmModel.getServerId());
		body.setString("SECURITY_GROUP_ID", vmModel.getSecurityGroupId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 
	 * 创建浮动IP
	 * @return
	 * @throws Exception
	 */
	public String  createFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData ="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_FLOATING_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("POOL_NAME", vmModel.getFloatingIpPoolName());
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 删除浮动IP
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void deleteFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_FLOATING_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("FLOATING_IP_ID", vmModel.getFloatingIpId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("FLOATING_IP_ID", vmModel.getFloatingIpId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 虚拟机绑定浮动IP
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void addFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.ADD_FLOATING_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", vmModel.getServerId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", vmModel.getServerId());
		body.setString("IP_ADDRESS", vmModel.getFloatingIp());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 虚拟机解绑定浮动IP
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void removeFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.REMOVE_FLOATING_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", vmModel.getServerId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", vmModel.getServerId());
		body.setString("IP_ADDRESS", vmModel.getFloatingIp());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建规格
	 * @param projectId
	 * @param flavor
	 * @return
	 * @throws Exception
	 */
	public String createFlavor(OpenstackIdentityModel model, CloudFlavorPo flavor) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String flavorId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_FLAVOR+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("FLAVOR_NAME", flavor.getName());
		body.setString("CPU", flavor.getCpu() + "");
		body.setString("MEM", flavor.getMem()+"");
		body.setString("DISK", flavor.getDisk() + "");
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			flavorId = json.getJSONObject("flavor").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return flavorId;
	}
	
	/**
	 * 配置虚拟机规格
	 * @param projectId
	 * @param flavorId
	 * @param flavor
	 * @throws Exception
	 */
	public void configFlavorVm(OpenstackIdentityModel model, String flavorId, FlavorRestModel flavor) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CONFIG_FLAVOR_VM+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("FLAVOR_ID", flavorId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("FLAVOR_ID", flavorId);
		body.setString("DISK", flavor.getDisk() + "");
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		int code = this.getExecuteResult(rspData);
	}
	
	/**
	 * 配置物理机规格
	 * @param projectId
	 * @param flavorId
	 * @param flavor
	 * @throws Exception
	 */
	public void configFlavorHost(OpenstackIdentityModel model, String flavorId, FlavorRestModel flavor) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CONFIG_FLAVOR_HOST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("FLAVOR_ID", flavorId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("FLAVOR_ID", flavorId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		int code = this.getExecuteResult(rspData);
	}
	/**
	 * 获取可用分区列表
	 * @throws Exception
	 */
	public JSONArray getAvailabilityZoneList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_AVAILABILITY_ZONE_LIST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		String jsonData = "";
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
			JSONObject jsonObj = JSONObject.parseObject(jsonData);
			JSONArray jsonArr = jsonObj.getJSONArray("availabilityZoneInfo");
			return jsonArr;
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public String deleteHost(OpenstackIdentityModel model, String serverId) {
		// TODO Auto-generated method stub
		return null;
	}

}
