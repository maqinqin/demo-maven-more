package com.git.cloud.iaas.openstack.service;

import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.cloudservice.model.po.CloudFlavorPo;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.FlavorRestModel;
import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VmRestModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public class OpenstackComputeService6Impl extends OpenstackComputeServiceAbstractImpl implements OpenstackComputeService {

	@Override
	public String createHost(OpenstackIdentityModel model, VmRestModel vmRestModel, String ipData)
			throws Exception {
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
		body.setString("SUBNET_ID", vmRestModel.getNetworkId());
		body.setString("SERVER_IP", vmRestModel.getServerIp());
		body.setString("SERVER_NAME", vmRestModel.getServerName());
		body.setString("VPC_ID", vmRestModel.getRouterId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	@Override
	public String deleteHost(OpenstackIdentityModel model,String serverId)
			throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject("deleteHost"+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}

	@Override
	public void addSecurityGroup(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		// TODO Auto-generated method stub
		String networkCard = IaasInstanceFactory.computeInstance(model.getVersion()).getNetworkCard(model, vmModel.getServerId());
		String portId = JSONObject.parseObject(networkCard).getJSONArray("interfaceAttachments").getJSONObject(0).getString("port_id");
		String portDetail = IaasInstanceFactory.networkInstance(model.getVersion()).getPortDetail(model, portId);
		JSONArray sgJson = JSONObject.parseObject(portDetail).getJSONObject("port").getJSONArray("security_groups");
		sgJson.add(vmModel.getSecurityGroupId());
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.ADD_SECURITY_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("SECURITY_INFO", sgJson.toJSONString());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	

	@Override
	public void removeSecurityGroup(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		// TODO Auto-generated method stub
		String networkCard = IaasInstanceFactory.computeInstance(model.getVersion()).getNetworkCard(model, vmModel.getServerId());
		String portId = JSONObject.parseObject(networkCard).getJSONArray("interfaceAttachments").getJSONObject(0).getString("port_id");
		String portDetail = IaasInstanceFactory.networkInstance(model.getVersion()).getPortDetail(model, portId);
		JSONArray sgJson = JSONObject.parseObject(portDetail).getJSONObject("port").getJSONArray("security_groups");
		sgJson.remove(vmModel.getSecurityGroupId());
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.REMOVE_SECURITY_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PORT_ID", vmModel.getPortId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("SECURITY_INFO", vmModel.getSecurityGroupIdInfo());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	

	@Override
	public String createFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		// TODO Auto-generated method stub
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData ="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_FLOATING_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("FLOATING_NETWORK_ID", vmModel.getFloatingIpPoolId());
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}

	@Override
	public void deleteFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		// TODO Auto-generated method stub
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_FLOATING_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FLOATING_IP_ID", vmModel.getFloatingIpId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public void addFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		// TODO Auto-generated method stub
		String networkCard = IaasInstanceFactory.computeInstance(model.getVersion()).getNetworkCard(model, vmModel.getServerId());
		String portId = JSONObject.parseObject(networkCard).getJSONArray("interfaceAttachments").getJSONObject(0).getString("port_id");
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.ADD_FLOATING_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FLOATING_IP_ID", vmModel.getFloatingIpId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public void removeFloatingIp(OpenstackIdentityModel model, VmRestModel vmModel) throws Exception {
		// TODO Auto-generated method stub
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.REMOVE_FLOATING_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FLOATING_IP_ID", vmModel.getFloatingIpId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public String createFlavor(OpenstackIdentityModel model, CloudFlavorPo flavor) throws Exception {
		JSONObject jso = this.getToken(model.getManageOneIp());
		model.setToken(jso.getString("token"));
		model.setProjectId(jso.getString("projectId"));
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

	@Override
	public void configFlavorVm(OpenstackIdentityModel model, String flavorId, FlavorRestModel flavor) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jso = this.getToken(model.getManageOneIp());
		model.setToken(jso.getString("token"));
		model.setProjectId(jso.getString("projectId"));
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

	@Override
	public void configFlavorHost(OpenstackIdentityModel model, String flavorId, FlavorRestModel flavor)
			throws Exception {
		// TODO Auto-generated method stub
		JSONObject jso = this.getToken(model.getManageOneIp());
		model.setToken(jso.getString("token"));
		model.setProjectId(jso.getString("projectId"));
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
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
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

}
