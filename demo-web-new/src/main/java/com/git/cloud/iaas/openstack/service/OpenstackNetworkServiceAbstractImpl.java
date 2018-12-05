package com.git.cloud.iaas.openstack.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.iaas.openstack.OpenstackInvokeTools;
import com.git.cloud.iaas.openstack.model.FwRuleRestModel;
import com.git.cloud.iaas.openstack.model.NetworkRestModel;
import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.SecurtyGroupRuleRestModel;
import com.git.cloud.iaas.openstack.model.SubnetRestModel;
import com.git.cloud.iaas.openstack.model.TapFlowsRestModel;
import com.git.cloud.iaas.openstack.model.TapServiceRestModel;
import com.git.cloud.iaas.openstack.model.VlbHeathRestModel;
import com.git.cloud.iaas.openstack.model.VlbListenerRestModel;
import com.git.cloud.iaas.openstack.model.VlbMemberRestModel;
import com.git.cloud.iaas.openstack.model.VlbPoolRestModel;
import com.git.cloud.iaas.openstack.model.VlbRestModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public abstract class OpenstackNetworkServiceAbstractImpl extends OpenstackInvokeTools implements OpenstackNetworkService {
	
	public String createNetwork(OpenstackIdentityModel model, NetworkRestModel networkModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String networkId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_NETWORK+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("NETWORK_NAME", networkModel.getNetworkName());
		body.setString("PHYNET_NAME", networkModel.getPhysicalNetwork());
		body.setString("NETWORK_TYPE", networkModel.getNetworkType());
		body.setString("VLAN_ID", networkModel.getVlanId());
		body.setString("IS_EXTERNAL", networkModel.isExternal()+""); // 是否外部网络
		body.setString("IS_SHARED", networkModel.isExternal()+""); // 是否共享，外部网络共享，内部网络不共享
		reqData.setDataObject(MesgFlds.BODY, body);
		
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			networkId = json.getJSONObject("network").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return networkId;
	}
	
	/**
	 * 创建外部网络
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public String createExtNetwork(OpenstackIdentityModel model, NetworkRestModel networkModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String networkId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_EXT_NETWORK+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("NETWORK_NAME", networkModel.getNetworkName());
		body.setString("PHYNET_NAME", networkModel.getPhysicalNetwork());
		body.setString("NETWORK_TYPE", networkModel.getNetworkType());
		body.setString("VLAN_ID", networkModel.getVlanId());
		body.setString("IS_EXTERNAL", networkModel.isExternal()+""); // 是否外部网络
		body.setString("IS_SHARED", networkModel.isExternal()+""); // 是否共享，外部网络共享，内部网络不共享
		reqData.setDataObject(MesgFlds.BODY, body);
		
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			networkId = json.getJSONObject("network").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return networkId;
	}
	
	/**
	 * 修改内部/外部网络
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void putExtNetwork(OpenstackIdentityModel model,String networkId, NetworkRestModel networkModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_EXT_NETWORK+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("NETWORK_ID", networkId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("NETWORK_NAME", networkModel.getNetworkName());
		body.setString("NETWORK_ID", networkId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除外部/内部网络
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void deleteExtNetwork(OpenstackIdentityModel model,String networkId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_EXT_NETWORK+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("NETWORK_ID", networkId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("NETWORK_ID", networkId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	public String createSubnet(OpenstackIdentityModel model, SubnetRestModel subnetModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String subnetId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_SUBNET+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("NETWORK_ID", subnetModel.getNetworkId());
		body.setString("SUBNET_NAME", subnetModel.getSubnetName());
		List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>> ();
		HashMap<String, String> map = new HashMap<String, String> ();
		map.put("start", subnetModel.getStartIp());
		map.put("end", subnetModel.getEndIp());
		mapList.add(map);
		body.setString("ALLOCATION_POOLS", JSONObject.toJSONString(mapList));
		body.setString("CIDR", subnetModel.getCidr());
		body.setString("GATEWAY_IP", subnetModel.getGatewayIp());
		body.setString("IP_VERSION", subnetModel.getIpVersion());
		reqData.setDataObject(MesgFlds.BODY, body);
		
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			subnetId = json.getJSONObject("subnet").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return subnetId;
	}
	
	/**
	 * 修改外部网络子网
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void putSubnet( OpenstackIdentityModel model,String subnetId, SubnetRestModel subnetModel) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_SUBNET+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("SUBNET_ID", subnetId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("SUBNET_ID", subnetId);
		body.setString("SUBNET_NAME", subnetModel.getSubnetName());
		body.setString("GATEWAY_IP", subnetModel.getGatewayIp());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除网络子网
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void deleteSubnet(OpenstackIdentityModel model,String subnetId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_SUBNET+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("SUBNET_ID", subnetId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("SUBNET_ID", subnetId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	public String createRouter(OpenstackIdentityModel model,String networkId, String routerName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String routerId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_ROUTER+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("NETWORK_ID", networkId);
		body.setString("ROUTER_NAME", routerName);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return routerId;
	}
	
	/**
	 * 更新虚拟路由器
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void updateRouter(OpenstackIdentityModel model,String routerId,String enableSnat,String networkId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.UPDATE_ROUTER+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("ROUTER_ID", routerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("ROUTER_ID", routerId);
		body.setString("ENABLE_SNAT", enableSnat);
		body.setString("NETWORK_ID", networkId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除虚拟路由器
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void deleteRouter(OpenstackIdentityModel model,String routerId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_ROUTER+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("ROUTER_ID", routerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("ROUTER_ID", routerId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 添加路由网络
	 * @param subnetId
	 * @param routerId
	 * @throws Exception
	 */
	public void putRouterNetwork(OpenstackIdentityModel model,String subnetId, String routerId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_ROUTER_NETWORK+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("ROUTER_ID", routerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("SUBNET_ID", subnetId);
		body.setString("ROUTER_ID", routerId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 移除路由网络
	 * @param subnetId
	 * @param routerId
	 * @throws Exception
	 */
	public void removeRouterNetwork(OpenstackIdentityModel model,String subnetId, String routerId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.REMOVE_ROUTER_NETWORK+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("ROUTER_ID", routerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("SUBNET_ID", subnetId);
		body.setString("ROUTER_ID", routerId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建端口
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String createPort(OpenstackIdentityModel model,String networkId, String subnetId,String ipAddress,String name) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_PORT+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("NETWORK_ID", networkId);
		body.setString("SUBNET_ID", subnetId);
		body.setString("IP_ADDRESS", ipAddress);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	/**
	 * 查询端口详细
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getPortDetail(OpenstackIdentityModel model,String portId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_PORT_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	@Override
	public String getPortList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_PORT_LIST + model.getVersion().replace(".", "")
				,model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	/**
	 * 查询端口IP
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getPortIp(OpenstackIdentityModel model,String portName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_PORT_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PORT_NAME", portName);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PORT_NAME", portName);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	/**
	 * 查询网络的端口
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getNetworkPort(OpenstackIdentityModel model,String networkId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_NETWORK_PORT+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("NETWORK_ID", networkId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("NETWORK_ID", networkId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return result;
	}

	/**
	 * 查询网络列表
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getNetwork(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(), model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_NETWORK + model.getVersion()
				.replace(".", ""), model.getOpenstackIp(), model.getDomainName(), model.getToken());

		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);

		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);

		IDataObject rspData = this.execute(reqData);
		if (this.getExecuteResult(rspData) == 200) {
			return this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除端口
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void deletePort(OpenstackIdentityModel model,String portId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_PORT+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 204) {
			return;
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 大数据放通浮动ip
	 * @param portId
	 * @param wsIp
	 * @param omIp
	 * @param mac
	 * @throws Exception
	 */
	public void putPort(OpenstackIdentityModel model,String portId,String wsIp,String omIp,String mac) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject("putPort"+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PORT_ID", portId);
		body.setString("WS_IP", wsIp);
		body.setString("OM_IP", omIp);
		body.setString("MAC1", mac);
		body.setString("MAC2", mac);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 更新网络配额
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param isVm
	 * @throws Exception
	 */
	public void putNetworkQuota(OpenstackIdentityModel model, String setProjectId) throws Exception{
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_NETWORK_QUOTA+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("SET_PROJECT_ID", setProjectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("SET_PROJECT_ID", setProjectId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建防火墙策略
	 * @return
	 * @throws Exception
	 */
	public String  createFwp(OpenstackIdentityModel model,String policyName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String firewallPolicyId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_FWP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("POLICY_NAME", policyName);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
            String result = this.getExecuteResultData(rspData);
            JSONObject json = JSONObject.parseObject(result);
            firewallPolicyId = json.getJSONObject("firewall_policy").getString("id");
        } else {
            throw new OpenstackException(this.getExecuteMessage(rspData));
        }
		return firewallPolicyId;
	}
	
	/**
	 * 删除防火墙策略
	 * @return
	 * @throws Exception
	 */
	public void deleteFwp(OpenstackIdentityModel model,String firewallPolicyId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_FWP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FWP_ID", firewallPolicyId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("FWP_ID", firewallPolicyId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 204) {
        } else {
            throw new OpenstackException(this.getExecuteMessage(rspData));
        }
	}
	
	/**
	 * 创建防火墙
	 * @return
	 * @throws Exception
	 */
	public String  createFw(OpenstackIdentityModel model,String routerId,String fwName,String fwpId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String firewallId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_FW+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("ROUTER_ID", routerId);
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("FW_NAME", fwName);
		body.setString("FWP_ID", fwpId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
            String result = this.getExecuteResultData(rspData);
            JSONObject json = JSONObject.parseObject(result);
            firewallId = json.getJSONObject("firewall").getString("id");
        } else {
            throw new OpenstackException(this.getExecuteMessage(rspData));
        }
		return firewallId;
	}
	
	/**
	 * 删除防火墙
	 * @return
	 * @throws Exception
	 */
	public void deleteFw(OpenstackIdentityModel model,String fwId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_FW+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FW_ID", fwId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("FW_ID", fwId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 204) {
        } else {
            throw new OpenstackException(this.getExecuteMessage(rspData));
        }
	}
	
	/**
	 * 修改防火墙规则
	 * @return
	 * @throws Exception
	 */
	public String  putFwr(OpenstackIdentityModel model,FwRuleRestModel fwr,String targetFwrId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_FWR+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FWR_ID", targetFwrId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("FWR_ID", targetFwrId);
		body.setString("FWR_NAME", fwr.getVfwPolicyRuleName());
		body.setString("ACTION", fwr.getRuleAction());
		if(fwr.getProtocolType() != null && !"any".equals(fwr.getProtocolType())) {
            body.setString("PROTOCOL", fwr.getProtocolType());
            body.setString("SOURCE_PORT", fwr.getSourcePort());
            body.setString("DESTINATION_PORT", fwr.getDescPort());
		}
		else {
            body.set("PROTOCOL", null);
            body.set("SOURCE_PORT", null);
            body.set("DESTINATION_PORT", null);
		}
        body.setString("SOURCE_IP_ADDRESS", fwr.getSourceIpAddress());
		body.setString("DESTINATION_IP_ADDRESS", fwr.getDestIpAddress());

		body.setString("IP_VERSION", fwr.getIpVersion());
		body.setString("IS_SHARED", fwr.getIsShare());
		body.setString("ENABLED", fwr.getEnabled());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		String jsonData = this.getExecuteResultData(rspData);
		return jsonData;
	}
	
	
	public String createFwr(OpenstackIdentityModel model,FwRuleRestModel fwr) throws Exception{
		model.setToken(this.getToken(model.getVersion(),model));
		String vfwPolicyRuleId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_FWR+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("FWR_NAME", fwr.getVfwPolicyRuleName());
		body.setString("ACTION", fwr.getRuleAction());
		String protocol = "";
		if(fwr.getProtocolType() != null && !"any".equals(fwr.getProtocolType().toLowerCase())) {
			protocol = fwr.getProtocolType().toLowerCase();
		}
		body.setString("PROTOCOL", protocol);
		if(!"".equals(protocol) && !"icmp".equals(protocol)) {
			body.setString("SOURCE_PORT", (fwr.getSourcePort() == null || "any".equals(fwr.getSourcePort().toLowerCase())? "" : fwr.getSourcePort()));
			body.setString("DESTINATION_PORT", (fwr.getDescPort() == null || "any".equals(fwr.getDescPort().toLowerCase())? "" : fwr.getDescPort()));
		} else {
			body.setString("SOURCE_PORT", "");
			body.setString("DESTINATION_PORT", "");
		}
//		body.setString("SOURCE_PORT", fwr.getSourcePort());
		body.setString("SOURCE_IP_ADDRESS", fwr.getSourceIpAddress());
		body.setString("DESTINATION_IP_ADDRESS", fwr.getDestIpAddress());
//		body.setString("DESTINATION_PORT", fwr.getDescPort());
		body.setString("IP_VERSION", fwr.getIpVersion());
		body.setString("IS_SHARED", fwr.getIsShare());
		body.setString("ENABLED", fwr.getEnabled());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
            String result = this.getExecuteResultData(rspData);
            JSONObject json = JSONObject.parseObject(result);
            vfwPolicyRuleId = json.getJSONObject("firewall_rule").getString("id");
        } else {
            throw new OpenstackException(this.getExecuteMessage(rspData));
        }

		return vfwPolicyRuleId;
	}
	
	/**
	 * 删除防火墙规则
	 * @return
	 * @throws Exception
	 */
	public void  deleteFwr(OpenstackIdentityModel model,String fwrId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_FWR+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FWR_ID", fwrId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("FWR_ID", fwrId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 在虚拟防火墙中更新防火墙策略
	 * @return
	 * @throws Exception
	 */
	public String  putFwpInFw(OpenstackIdentityModel model,String fwId,String fwpId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_FWP_IN_FW+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FW_ID", fwId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("FW_ID", fwId);
		body.setString("FWP_ID", fwpId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		String jsonData = this.getExecuteResultData(rspData);
		return jsonData;
	}
	
	/**
	 * 防火墙策略中添加防火墙规则
	 * @return
	 * @throws Exception
	 */
	public String  addFwrInFwp(OpenstackIdentityModel model,String fwpId,String fwrId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.ADD_FWR_IN_FWP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FWP_ID", fwpId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("FWP_ID", fwpId);
		body.setString("FWR_ID", fwrId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 防火墙策略中移除防火墙规则
	 * @return
	 * @throws Exception
	 */
	public String  removeFwrInFwp(OpenstackIdentityModel model,String fwpId,String fwrId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.REMOVE_FWR_IN_FWP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("FWP_ID", fwpId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("FWP_ID", fwpId);
		body.setString("FWR_ID", fwrId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 创建负载均衡池
	 * @return
	 * @throws Exception
	 */
	public String createVlbPool(OpenstackIdentityModel model,VlbPoolRestModel vlbPool) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VLB_POOL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("LISTENER_ID", vlbPool.getListenerId());
		body.setString("POOL_NAME", vlbPool.getPoolName());
		body.setString("PROTOCOL", vlbPool.getProtocol());
		body.setString("LB_METHOD", vlbPool.getLbMethod());
		IDataObject rspData = this.execute(reqData);
		String vlbPoolId = "";
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			vlbPoolId = json.getJSONObject("pool").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return vlbPoolId;
	}
	
	/**
	 * 删除负载均衡池
	 * @param poolId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlbPool(OpenstackIdentityModel model,String poolId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_VLB_POOL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("POOL_ID", poolId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("POOL_ID", poolId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 204) {
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 获取负载均衡池列表
	 * @return
	 * @throws Exception
	 */
	public String  getVLbPoolList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VLB_POOL_LIST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		String jsonData = this.getExecuteResultData(rspData);
		return jsonData;
	}
	
	public String createPortNoIp(OpenstackIdentityModel model,String networkId,String portName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_PORT_NO_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("NETWORK_ID", networkId);
		body.setString("PORT_NAME", portName);
		IDataObject rspData = this.execute(reqData);
		String vip = "";
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			vip = json.getJSONObject("port").getJSONArray("fixed_ips").getJSONObject(0).getString("ip_address");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return vip;
	}
	
	/**
	 * 
	 * 创建负载均衡
	 * @return
	 * @throws Exception
	 */
	public String createVlb(OpenstackIdentityModel model,VlbRestModel vlb) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VLB+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("PROJECT_ID", vlb.getTenantId());
		body.setString("SUBNET_ID", vlb.getSubnetId());
		body.setString("VLB_NAME", vlb.getVlbName());
		body.setString("IP_ADDRESS", vlb.getIpAddress());
		IDataObject rspData = this.execute(reqData);
		String vlbId = "";
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			vlbId = json.getJSONObject("loadbalancer").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return vlbId;
	}
	
	/**
	 * 
	 * 创建负载均衡监控
	 * @return
	 * @throws Exception
	 */
	public String createVlbListener(OpenstackIdentityModel model,VlbListenerRestModel listener) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VLB_LISTENER+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("PORT", listener.getProtocolPort());
		body.setString("PROTOCOL", listener.getProtocol());
		body.setString("LISTENER_NAME", listener.getListenerName());
		body.setString("LOADBALANCER_ID", listener.getLoadbalancerId());
		IDataObject rspData = this.execute(reqData);
		String listenerId = "";
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			listenerId = json.getJSONObject("listener").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return listenerId;
	}
	
	/**
	 * 删除负载均衡监控
	 * @param vipId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlbListener(OpenstackIdentityModel model,String listenerId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_VLB_LISTENER+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("LISTENER_ID", listenerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("LISTENER_ID", listenerId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除负载均衡
	 * @param vipId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlb(OpenstackIdentityModel model,String vlbId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_VLB+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("VLB_ID", vlbId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("VLB_ID", vlbId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 
	 * 创建成员
	 * @return
	 * @throws Exception
	 */
	public String createVlbMember(OpenstackIdentityModel model,VlbMemberRestModel vlbMember) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VLB_MEMBER+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("POOL_ID", vlbMember.getPoolId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SUBNET_ID", vlbMember.getSubnetId());
		body.setString("POOL_ID", vlbMember.getPoolId());
		body.setString("IP_ADDRESS", vlbMember.getAddress());
		body.setString("PROTOCOL_PORT", vlbMember.getProtocolPort());
		body.setString("WEIGHT", vlbMember.getWeight());
		IDataObject rspData = this.execute(reqData);
		String memberId = "";
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			memberId = json.getJSONObject("member").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return memberId;
	}
	
	/**
	 * 删除成员
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlbMember(OpenstackIdentityModel model,String memberId,String poolId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_VLB_MEMBER+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("MEMBER_ID", memberId);
		paramMap.put("POOL_ID", poolId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("MEMBER_ID", memberId);
		body.setString("POOL_ID", poolId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建健康检查
	 * @param vlbHealth
	 * @return
	 * @throws Exception
	 */
	public String createVlbHealth(OpenstackIdentityModel model,VlbHeathRestModel vlbHealth) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VLB_HEALTH+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("VLB_POOL_ID", vlbHealth.getPoolId());
		body.setString("DELAY", vlbHealth.getDelay());
		body.setString("EXPECTED_CODES", vlbHealth.getExpectedCodes());
		body.setString("MAX_RETRIES", vlbHealth.getMaxRetries());
		body.setString("HTTP_METHOD", vlbHealth.getHttpMethod());
		body.setString("TIMEOUT", vlbHealth.getTimeout());
		body.setString("URL_PATH", vlbHealth.getUrlPath());
		body.setString("HEALTH_TYPE", vlbHealth.getHealthType());
		IDataObject rspData = this.execute(reqData);
		String healthId = "";
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			healthId = json.getJSONObject("healthmonitor").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return healthId;
	}
	
	/**
	 * 查询健康检查
	 * @param vlbHealth
	 * @return
	 * @throws Exception
	 */
	public String getVlbHealthDetail(OpenstackIdentityModel model,String healthId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VLB_HEALTH_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("HEALTH_MONITOR_ID", healthId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			return this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除健康检查
	 * @param healthMonitorId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlbHealth(OpenstackIdentityModel model,String healthMonitorId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_VLB_HEALTH+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("HEALTH_MONITOR_ID", healthMonitorId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("HEALTH_MONITOR_ID", healthMonitorId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 204) {
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 
	 * 查询安全组列表
	 * @return
	 * @throws Exception
	 */
	public String  getSecurityGroupList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_SECURITY_GROUP_LIST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("PROJECT_ID", model.getProjectId());
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 查询安全组
	 * @return
	 * @throws Exception
	 */
	public String  getSecurityGroup(OpenstackIdentityModel model,String securityId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_SECURITY_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("SECURITY_ID", securityId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_ID", securityId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 查询安全组规则列表
	 * @return
	 * @throws Exception
	 */
	public String  getSecurityGroupRuleList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_SECURITY_GROUP_RULE_LIST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 查询安全组规则
	 * @return
	 * @throws Exception
	 */
	public String  getSecurityGroupRule(OpenstackIdentityModel model,String securityRuleId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_SECURITY_GROUP_RULE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("SECURITY_RULE_ID", securityRuleId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_RULE_ID", securityRuleId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 创建安全组规则
	 * @return
	 * @throws Exception
	 */
	public String  createSecurityGroupRule(OpenstackIdentityModel model,SecurtyGroupRuleRestModel sgr) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String sgRuleId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_SECURITY_GROUP_RULE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("SECURITY_GROUP_ID", sgr.getSecurityGroupId());
		body.setString("ETHER_TYPE", sgr.getEtherType());
		body.setString("DIRECTION", sgr.getDirection());
		body.setString("REMOTE_IP_PREFIX", sgr.getRemoteIpPrefix());
		body.setString("REMOTE_GROUP_ID", sgr.getRemoteGroupId()==null?"":sgr.getRemoteGroupId());
		body.setString("PROTOCOL", sgr.getProtocol());
		body.setString("PORT_RANGE_MAX", sgr.getPortRangeMax());
		body.setString("PORT_RANGE_MIN", sgr.getPortRangeMin());
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			sgRuleId = json.getJSONObject("security_group_rule").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return sgRuleId;
	}
	
	/**
	 * 
	 * 删除安全组规则
	 * @return
	 * @throws Exception
	 */
	public void  deleteSecurityGroupRule(OpenstackIdentityModel model,String securityRuleId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_SECURITY_GROUP_RULE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("SECURITY_RULE_ID", securityRuleId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_RULE_ID", securityRuleId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 
	 * 创建安全组
	 * @return
	 * @throws Exception
	 */
	public String  createSecurityGroup(OpenstackIdentityModel model,String securityGroupName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String securityGroupId = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_SECURITY_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("SECURITY_GROUP_NAME", securityGroupName);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			securityGroupId = json.getJSONObject("security_group").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return securityGroupId;
	}
	
	/**
	 * 
	 * 更新安全组
	 * @return
	 * @throws Exception
	 */
	public String  updateSecurityGroup(OpenstackIdentityModel model,String securityId,String securityGroupName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.UPDATE_SECURITY_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("SECURITY_ID", securityId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_ID", securityId);
		body.setString("SECURITY_GROUP_NAME", securityGroupName);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 删除安全组
	 * @return
	 * @throws Exception
	 */
	public void  deleteSecurityGroup(OpenstackIdentityModel model,String securityId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_SECURITY_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("SECURITY_ID", securityId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_ID", securityId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建端口镜像服务
	 * @param tapServicePo
	 * @return
	 * @throws Exception
	 */
	public String createTapServices(OpenstackIdentityModel model,TapServiceRestModel tapServicePo) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_TAP_SERVICES+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("name", tapServicePo.getName());
		paramMap.put("port_id", tapServicePo.getPortId());
		paramMap.put("segmentation_id", tapServicePo.getSegmentId());
		paramMap.put("tenant_id", tapServicePo.getTenantId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("name", tapServicePo.getName());
		body.setString("port_id", tapServicePo.getPortId());
		body.setInt("segmentation_id", tapServicePo.getSegmentId());
		body.setString("tenant_id", tapServicePo.getTenantId());
		IDataObject rspData = this.execute(reqData);
		String jsonData="";
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 创建端口镜像源端口
	 * @param tapFlows
	 * @return
	 * @throws Exception
	 */
	public String createTapFlow(OpenstackIdentityModel model,TapFlowsRestModel tapFlows) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_TAP_FLOW+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("direction", tapFlows.getDirection());
		paramMap.put("name", tapFlows.getName());
		paramMap.put("source_port", tapFlows.getPortId());
		paramMap.put("tap_service_id", tapFlows.getTapServiceId());
		paramMap.put("tenant_id", tapFlows.getTenantId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("direction", tapFlows.getDirection());
		body.setString("name", tapFlows.getName());
		body.setString("source_port", tapFlows.getPortId());
		body.setString("tap_service_id", tapFlows.getTapServiceId());
		body.setString("tenant_id", tapFlows.getTenantId());
		IDataObject rspData = this.execute(reqData);
		String jsonData="";
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 删除端口镜像的源端口
	 *
	 * @param id
	 * @throws Exception
	 */
	public void deleteTapFlow(OpenstackIdentityModel model,String id) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_TAP_FLOW+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("tap_flow_uuid", id);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("tap_flow_uuid", id);
		IDataObject rspData = this.execute(reqData);
		if (this.getExecuteResult(rspData) == 204 || this.getExecuteResult(rspData) == 404) {
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除端口镜像的目标端口服务
	 * @param id
	 * @throws Exception
	 */
	public void deleteTapService(OpenstackIdentityModel model,String id) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_TAP_SERVICE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("tap_service_uuid", id);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("tap_service_uuid", id);
		IDataObject rspData = this.execute(reqData);
		if (this.getExecuteResult(rspData) == 204 || this.getExecuteResult(rspData) == 404) {
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 修改端口ip
	 * @param networkId
	 * @throws Exception
	 */
	public void putPortIp(OpenstackIdentityModel model,String portId,String ip) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_PORT_IP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("IP", ip);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 绑定解除Vip
	 * @param portId
	 * @param vipList
	 * @param method
	 * @throws Exception
	 */
	public void updatePortS(OpenstackIdentityModel model,String portId,List<String> vipList,String method) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_PORT_S+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		String ipInfo = "";
		if(method.equals("bind")) {
			if(vipList.size() > 0){
				for(String vip : vipList) {
					ipInfo = "{\"ip_address\":\""+vip+"\"}," + ipInfo;
				}
				ipInfo = ipInfo.substring(0,ipInfo.length()-1);
				ipInfo = "[" + ipInfo + "]";
				body.setString("IP_INFO", ipInfo);
			}
		}else if(method.equals("unbind")) {
			body.setString("IP_INFO", "[]");
		}
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

}
