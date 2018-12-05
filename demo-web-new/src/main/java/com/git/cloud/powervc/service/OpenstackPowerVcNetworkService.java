package com.git.cloud.powervc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.git.cloud.powervc.common.OpenstackPowerVcService;
import com.git.cloud.powervc.model.NetworkModel;
import com.git.cloud.powervc.model.SecurityGroupRuleModel;
import com.git.cloud.powervc.model.SubnetModel;
import com.git.cloud.powervc.model.VlbHealthModel;
import com.git.cloud.powervc.model.VlbListenerModel;
import com.git.cloud.powervc.model.VlbMemberModel;
import com.git.cloud.powervc.model.VlbModel;
import com.git.cloud.powervc.model.VlbPoolModel;
import com.git.cloud.resmgt.openstack.model.vo.RmNwVfwPolicyRuleVo;
import com.git.support.common.MesgFlds;
import com.git.support.common.PVOperation;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Openstack网络资源接口
 * @author SunHailong
 * @version v1.0 2017-3-20
 */
public class OpenstackPowerVcNetworkService extends OpenstackPowerVcService {
	
	private static OpenstackPowerVcNetworkService openstackNetworkService;
	
	private OpenstackPowerVcNetworkService(){};
	
	public static OpenstackPowerVcNetworkService getNetworkServiceInstance(String openstackIp,String domainName, String token) {
		if(openstackNetworkService == null) {
			openstackNetworkService = new OpenstackPowerVcNetworkService();
		}
		openstackNetworkService.openstackIp = openstackIp;
		openstackNetworkService.domainName = domainName;
		openstackNetworkService.token = token;
		return openstackNetworkService;
	}
	
	public String createNetwork(String projectId, NetworkModel networkModel) throws Exception {
		String networkId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_NETWORK);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
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
			JSONObject json = JSONObject.fromObject(result);
			networkId = json.getJSONObject("network").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
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
	public String createExtNetwork(String projectId, NetworkModel networkModel) throws Exception {
		String networkId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_EXT_NETWORK);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
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
			JSONObject json = JSONObject.fromObject(result);
			networkId = json.getJSONObject("network").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return networkId;
	}
	
	/**
	 * 修改外部网络
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void putExtNetwork(String networkId, NetworkModel networkModel) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_EXT_NETWORK);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("NETWORK_ID", networkId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("NETWORK_ID", networkId);
		body.setString("NETWORK_NAME", networkModel.getNetworkName());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除外部网络
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void deleteExtNetwork(String networkId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_EXT_NETWORK);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("NETWORK_ID", networkId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("NETWORK_ID", networkId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	public String createSubnet(String projectId, SubnetModel subnetModel) throws Exception {
		String subnetId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_SUBNET);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("NETWORK_ID", subnetModel.getNetworkId());
		body.setString("SUBNET_NAME", subnetModel.getSubnetName());
		List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>> ();
		HashMap<String, String> map = new HashMap<String, String> ();
		map.put("start", subnetModel.getStartIp());
		map.put("end", subnetModel.getEndIp());
		mapList.add(map);
		body.setString("ALLOCATION_POOLS", JSONArray.fromObject(mapList).toString());
		body.setString("CIDR", subnetModel.getCidr());
		body.setString("GATEWAY_IP", subnetModel.getGatewayIp());
		body.setString("IP_VERSION", subnetModel.getIpVersion());
		reqData.setDataObject(MesgFlds.BODY, body);
		
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			subnetId = json.getJSONObject("subnet").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
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
	public void putSubnet(String subnetId, SubnetModel subnetModel) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_SUBNET);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("SUBNET_ID", subnetId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("SUBNET_ID", subnetId);
		body.setString("SUBNET_NAME", subnetModel.getSubnetName());
		body.setString("GATEWAY_IP", subnetModel.getGatewayIp());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除外部网络子网
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void deleteSubnet(String subnetId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_SUBNET);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("SUBNET_ID", subnetId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("SUBNET_ID", subnetId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	public String createRouter(String networkId, String routerName) throws Exception {
		String routerId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_ROUTER);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("NETWORK_ID", networkId);
		body.setString("ROUTER_NAME", routerName);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
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
	public void updateRouter(String routerId,String enableSnat,String networkId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.UPDATE_ROUTER);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("ROUTER_ID", routerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("ROUTER_ID", routerId);
		body.setString("ENABLE_SNAT", enableSnat);
		body.setString("NETWORK_ID", networkId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除虚拟路由器
	 * @param projectId
	 * @param networkModel
	 * @return
	 * @throws Exception
	 */
	public void deleteRouter(String routerId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_ROUTER);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("ROUTER_ID", routerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("ROUTER_ID", routerId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 添加路由网络
	 * @param subnetId
	 * @param routerId
	 * @throws Exception
	 */
	public void putRouterNetwork(String subnetId, String routerId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_ROUTER_NETWORK);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("ROUTER_ID", routerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("SUBNET_ID", subnetId);
		body.setString("ROUTER_ID", routerId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 移除路由网络
	 * @param subnetId
	 * @param routerId
	 * @throws Exception
	 */
	public void removeRouterNetwork(String subnetId, String routerId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.REMOVE_ROUTER_NETWORK);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("ROUTER_ID", routerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("SUBNET_ID", subnetId);
		body.setString("ROUTER_ID", routerId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建端口
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String createPort(String networkId, String subnetId,String ipAddress,String name) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_PORT);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("NETWORK_ID", networkId);
		body.setString("SUBNET_ID", subnetId);
		body.setString("IP_ADDRESS", ipAddress);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			result = this.getExecuteResultData(rspData);
			//JSONObject json = JSONObject.fromObject(result);
			//routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
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
	public String getPortDetail(String portId) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_PORT_DETAIL);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			result = this.getExecuteResultData(rspData);
			//JSONObject json = JSONObject.fromObject(result);
			//routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
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
	public String getPortIp(String portName) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_PORT_IP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PORT_NAME", portName);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PORT_NAME", portName);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
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
	public String getNetworkPort(String networkId) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_NETWORK_PORT);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("NETWORK_ID", networkId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("NETWORK_ID", networkId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	
	/**
	 * 删除端口
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void deletePort(String portId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_PORT);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建端口(物理机)
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String createPortPhy(String networkId, String subnetId,String ipAddress,String hostName,String projectId,String serverId,String macAddr,String leaf,String pportId,String switchId) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_PORT_PHY);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("NETWORK_ID", networkId);
		body.setString("SUBNET_ID", subnetId);
		body.setString("IP_ADDRESS", ipAddress);
		body.setString("HOST_NAME", hostName);
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("MAC_ADDR", macAddr);
		body.setString("LEAF", leaf);
		body.setString("PPORT_ID", pportId);
		body.setString("SWITCH_ID", switchId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			result = this.getExecuteResultData(rspData);
			//JSONObject json = JSONObject.fromObject(result);
			//routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	public String getPhyNet(String token) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject("");
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	public String getOverSplit(String token) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject("");
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			result = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return result;
	}
	
	/**
	 * 更新网络配额
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param isVm
	 * @throws Exception
	 */
	public void putNetworkQuota(String projectId, String setProjectId) throws Exception{
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_NETWORK_QUOTA);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("SET_PROJECT_ID", setProjectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		//body.setString("PROJECT_ID", projectId);
		body.setString("SET_PROJECT_ID", setProjectId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建防火墙策略
	 * @return
	 * @throws Exception
	 */
	public String  createFwp(String policyName) throws Exception {
		String firewallPolicyId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_FWP);
		BodyDO body = this.createBodyDO();
		body.setString("POLICY_NAME", policyName);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
            String result = this.getExecuteResultData(rspData);
            JSONObject json = JSONObject.fromObject(result);
            firewallPolicyId = json.getJSONObject("firewall_policy").getString("id");
        } else {
            throw new Exception(this.getExecuteMessage(rspData));
        }
		return firewallPolicyId;
	}
	
	/**
	 * 创建防火墙
	 * @return
	 * @throws Exception
	 */
	public String  createFw(String routerId,String tenantId,String fwName,String fwpId) throws Exception {
		String firewallId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_FW);
		BodyDO body = this.createBodyDO();
		body.setString("ROUTER_ID", routerId);
		body.setString("TENANT_ID", tenantId);
		body.setString("FW_NAME", fwName);
		body.setString("FWP_ID", fwpId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
            String result = this.getExecuteResultData(rspData);
            JSONObject json = JSONObject.fromObject(result);
            firewallId = json.getJSONObject("firewall").getString("id");
        } else {
            throw new Exception(this.getExecuteMessage(rspData));
        }
		return firewallId;
	}
	
	/**
	 * 删除防火墙
	 * @return
	 * @throws Exception
	 */
	public void deleteFw(String fwId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_FW);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("FW_ID", fwId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("FW_ID", fwId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 204) {
        } else {
            throw new Exception(this.getExecuteMessage(rspData));
        }
	}
	
	/**
	 * 修改防火墙规则
	 * @return
	 * @throws Exception
	 */
	public String  putFwr(String tenantId,RmNwVfwPolicyRuleVo fwr,String targetFwrId) throws Exception {
		
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_FWR);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("FWR_ID", targetFwrId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("TENANT_ID", tenantId);
		body.setString("FWR_ID", targetFwrId);
		body.setString("FWR_NAME", fwr.getVfwPolicyRuleName());
		body.setString("ACTION", fwr.getRuleAction());
		body.setString("PROTOCOL", fwr.getProtocolType());
		body.setString("SOURCE_PORT", fwr.getSourcePort());
		body.setString("SOURCE_IP_ADDRESS", fwr.getSourceIpAddress());
		body.setString("DESTINATION_IP_ADDRESS", fwr.getDestIpAddress());
		body.setString("DESTINATION_PORT", fwr.getDescPort());
		body.setString("IP_VERSION", fwr.getIpVersion());
		body.setString("IS_SHARED", fwr.getIsShare());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		String jsonData = this.getExecuteResultData(rspData);
		return jsonData;
	}
	//创建防火墙规则
	public String createFwr(String tenantId,RmNwVfwPolicyRuleVo fwr) throws Exception{
		String vfwPolicyRuleId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_FWR);
		BodyDO body = this.createBodyDO();
		body.setString("TENANT_ID", tenantId);
		body.setString("FWR_NAME", fwr.getVfwPolicyRuleName());
		body.setString("ACTION", fwr.getRuleAction());
		String protocol = "";
		if(fwr.getProtocolType() != null && !"any".equals(fwr.getProtocolType())) {
			protocol = fwr.getProtocolType();
		}
		body.setString("PROTOCOL", protocol);
		body.setString("SOURCE_PORT", fwr.getSourcePort());
		body.setString("SOURCE_IP_ADDRESS", fwr.getSourceIpAddress());
		body.setString("DESTINATION_IP_ADDRESS", fwr.getDestIpAddress());
		body.setString("DESTINATION_PORT", fwr.getDescPort());
		body.setString("IP_VERSION", fwr.getIpVersion());
		body.setString("IS_SHARED", fwr.getIsShare());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
            String result = this.getExecuteResultData(rspData);
            JSONObject json = JSONObject.fromObject(result);
            vfwPolicyRuleId = json.getJSONObject("firewall_rule").getString("id");
        } else {
            throw new Exception(this.getExecuteMessage(rspData));
        }

		return vfwPolicyRuleId;
	}
	/**
	 * 删除防火墙规则
	 * @return
	 * @throws Exception
	 */
	public void  deleteFwr(String fwrId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_FWR);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("FWR_ID", fwrId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("FWR_ID", fwrId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 在虚拟防火墙中更新防火墙策略
	 * @return
	 * @throws Exception
	 */
	public String  putFwpInFw(String fwId,String fwpId) throws Exception {
		
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_FWP_IN_FW);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("FW_ID", fwId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
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
	public String  addFwrInFwp(String fwpId,String fwrId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.ADD_FWR_IN_FWP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("FWP_ID", fwpId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("FWP_ID", fwpId);
		body.setString("FWR_ID", fwrId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 防火墙策略中移除防火墙规则
	 * @return
	 * @throws Exception
	 */
	public String  removeFwrInFwp(String fwpId,String fwrId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.REMOVE_FWR_IN_FWP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("FWP_ID", fwpId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("FWP_ID", fwpId);
		body.setString("FWR_ID", fwrId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 创建负载均衡池
	 * @return
	 * @throws Exception
	 */
	public String createVlbPool(VlbPoolModel vlbPool) throws Exception {
		
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_VLB_POOL);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("LISTENER_ID", vlbPool.getListenerId());
		body.setString("POOL_NAME", vlbPool.getPoolName());
		body.setString("PROTOCOL", vlbPool.getProtocol());
		body.setString("LB_METHOD", vlbPool.getLbMethod());
		IDataObject rspData = this.execute(reqData);
		String vlbPoolId = "";
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			vlbPoolId = json.getJSONObject("pool").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return vlbPoolId;
	}

	/**
	 * 删除负载均衡池
	 * @param poolId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlbPool(String poolId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_VLB_POOL);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("POOL_ID", poolId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("POOL_ID", poolId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 204) {
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 获取负载均衡池列表
	 * @return
	 * @throws Exception
	 */
	public String  getVLbPoolList() throws Exception {
		
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VLB_POOL_LIST);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		String jsonData = this.getExecuteResultData(rspData);
		return jsonData;
	}
	
	
	public String createPortNoIp(String networkId,String portName) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_PORT_NO_IP);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("NETWORK_ID", networkId);
		body.setString("PORT_NAME", portName);
		IDataObject rspData = this.execute(reqData);
		String vip = "";
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			vip = json.getJSONObject("port").getJSONArray("fixed_ips").getJSONObject(0).getString("ip_address");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return vip;
	}
	
	/**
	 * 
	 * 创建负载均衡
	 * @return
	 * @throws Exception
	 */
	public String createVlb(VlbModel vlb) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_VLB);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("PROJECT_ID", vlb.getTenantId());
		body.setString("SUBNET_ID", vlb.getSubnetId());
		body.setString("VLB_NAME", vlb.getVlbName());
		body.setString("IP_ADDRESS", vlb.getIpAddress());
		IDataObject rspData = this.execute(reqData);
		String vlbId = "";
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			vlbId = json.getJSONObject("loadbalancer").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return vlbId;
	}
	
	/**
	 * 
	 * 创建负载均衡监控
	 * @return
	 * @throws Exception
	 */
	public String createVlbListener(VlbListenerModel listener) throws Exception {
		IDataObject reqData = this.getIDataObject("");
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("PORT", listener.getProtocolPort());
		body.setString("PROTOCOL", listener.getProtocol());
		body.setString("LISTENER_NAME", listener.getListenerName());
		body.setString("LOADBALANCER_ID", listener.getLoadbalancerId());
		IDataObject rspData = this.execute(reqData);
		String listenerId = "";
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			listenerId = json.getJSONObject("listener").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return listenerId;
	}
	
	/**
	 * 删除负载均衡监控
	 * @param vipId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlbListener(String listenerId) throws Exception {
		IDataObject reqData = this.getIDataObject("");
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("LISTENER_ID", listenerId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("LISTENER_ID", listenerId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除负载均衡
	 * @param vipId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlb(String vlbId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_VLB);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("VLB_ID", vlbId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("VLB_ID", vlbId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 
	 * 创建成员
	 * @return
	 * @throws Exception
	 */
	public String createVlbMember(VlbMemberModel vlbMember) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_VLB_MEMBER);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("POOL_ID", vlbMember.getPoolId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
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
			JSONObject json = JSONObject.fromObject(result);
			memberId = json.getJSONObject("member").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return memberId;
	}
	
	/**
	 * 删除成员
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlbMember(String memberId,String poolId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_VLB_MEMBER);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("MEMBER_ID", memberId);
		paramMap.put("POOL_ID", poolId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("MEMBER_ID", memberId);
		body.setString("POOL_ID", poolId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 创建健康检查
	 * @param vlbHealth
	 * @return
	 * @throws Exception
	 */
	public String createVlbHealth(VlbHealthModel vlbHealth) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_VLB_HEALTH);
		BodyDO body = this.createBodyDO();
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
			JSONObject json = JSONObject.fromObject(result);
			healthId = json.getJSONObject("healthmonitor").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return healthId;
	}
	
	/**
	 * 查询健康检查
	 * @param vlbHealth
	 * @return
	 * @throws Exception
	 */
	public String getVlbHealthDetail(String healthId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VLB_HEALTH_DETAIL);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("HEALTH_MONITOR_ID", healthId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			return this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除健康检查
	 * @param healthMonitorId
	 * @return
	 * @throws Exception
	 */
	public void deleteVlbHealth(String healthMonitorId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_VLB_HEALTH);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("HEALTH_MONITOR_ID", healthMonitorId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("HEALTH_MONITOR_ID", healthMonitorId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 204) {
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 设置负载均衡池的健康检查
	 * @param vlbPoolId
	 * @param healthId
	 * @throws Exception
	 */
	public void setPoolHealth(String vlbPoolId, String healthId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.SET_POOL_HEALTH);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("POOL_ID", vlbPoolId);
		body.setString("HEALTH_ID", healthId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 解关联负载均衡池的健康检查
	 * @param vlbPoolId
	 * @param healthId
	 * @throws Exception
	 */
	public void dissetPoolHealth(String vlbPoolId, String healthId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DISSET_POOL_HEALTH);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("POOL_ID", vlbPoolId);
		body.setString("HEALTH_MONITOR_ID", healthId);
		IDataObject rspData = this.execute(reqData);
		// 204为解除关联成功；404为原本没有关联
		if(this.getExecuteResult(rspData) == 204 || this.getExecuteResult(rspData) == 404) {
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	
	/**
	 * 
	 * 查询安全组列表
	 * @return
	 * @throws Exception
	 */
	public String  getSecurityGroupList(String projectId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_SECURITY_GROUP_LIST);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("PROJECT_ID", projectId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 查询安全组
	 * @return
	 * @throws Exception
	 */
	public String  getSecurityGroup(String securityId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_SECURITY_GROUP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("SECURITY_ID", securityId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_ID", securityId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 查询安全组规则列表
	 * @return
	 * @throws Exception
	 */
	public String  getSecurityGroupRuleList() throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_SECURITY_GROUP_RULE_LIST);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 查询安全组规则
	 * @return
	 * @throws Exception
	 */
	public String  getSecurityGroupRule(String securityRuleId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_SECURITY_GROUP_RULE);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("SECURITY_RULE_ID", securityRuleId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_RULE_ID", securityRuleId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 创建安全组规则
	 * @return
	 * @throws Exception
	 */
	public String  createSecurityGroupRule(String tenantId,SecurityGroupRuleModel sgr) throws Exception {
		String sgRuleId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_SECURITY_GROUP_RULE);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("TENANT_ID", tenantId);
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
			JSONObject json = JSONObject.fromObject(result);
			sgRuleId = json.getJSONObject("security_group_rule").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return sgRuleId;
	}
	
	/**
	 * 
	 * 删除安全组规则
	 * @return
	 * @throws Exception
	 */
	public void  deleteSecurityGroupRule(String securityRuleId) throws Exception {
		
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_SECURITY_GROUP_RULE);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("SECURITY_RULE_ID", securityRuleId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_RULE_ID", securityRuleId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 
	 * 创建安全组
	 * @return
	 * @throws Exception
	 */
	public String  createSecurityGroup(String tenantId,String securityGroupName) throws Exception {
		String securityGroupId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_SECURITY_GROUP);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("TENANT_ID", tenantId);
		body.setString("SECURITY_GROUP_NAME", securityGroupName);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 201) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			securityGroupId = json.getJSONObject("security_group").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return securityGroupId;
	}
	
	/**
	 * 
	 * 更新安全组
	 * @return
	 * @throws Exception
	 */
	public String  updateSecurityGroup(String securityId,String securityGroupName) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.UPDATE_SECURITY_GROUP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("SECURITY_ID", securityId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_ID", securityId);
		body.setString("SECURITY_GROUP_NAME", securityGroupName);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 
	 * 删除安全组
	 * @return
	 * @throws Exception
	 */
	public void  deleteSecurityGroup(String securityId) throws Exception {
		
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_SECURITY_GROUP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("SECURITY_ID", securityId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("SECURITY_ID", securityId);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
}
