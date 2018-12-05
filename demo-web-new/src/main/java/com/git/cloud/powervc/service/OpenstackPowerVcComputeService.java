package com.git.cloud.powervc.service;

import java.util.HashMap;

import com.git.cloud.cloudservice.model.po.CloudFlavorPo;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.powervc.common.OpenstackPowerVcService;
import com.git.cloud.powervc.model.ServerModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.PVOperation;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

import net.sf.json.JSONObject;


/**
 * Openstack计算资源接口
 * @author SunHailong
 * @version v1.0 2017-3-20
 */
public class OpenstackPowerVcComputeService extends OpenstackPowerVcService {
	
	private static OpenstackPowerVcComputeService openstackComputeService;
	
	private OpenstackPowerVcComputeService(){};
	
	public static OpenstackPowerVcComputeService getComputeServiceInstance(String openstackIp, String domainName,String token) {
		if(openstackComputeService == null) {
			openstackComputeService = new OpenstackPowerVcComputeService();
		}
		openstackComputeService.openstackIp = openstackIp;
		openstackComputeService.domainName = domainName;
		openstackComputeService.token = token;
		return openstackComputeService;
	}
	
	/**
	 * 查询主机组列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getGroupHost(String projectId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_HOST_GROUP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 查询主机资源列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getGroupHostRes(String projectId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_HOST_RES);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 查询主机资源详细信息
	 * @param projectId
	 * @param hostId
	 * @return
	 * @throws Exception
	 */
	public String getGroupHostResDetail(String projectId,String hostId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_HOST_RES_DETAIL);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("HOST_ID", hostId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("HOST_ID", hostId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 查询规格列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getFlavor(String projectId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_FLAVOR);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 查询规格详情
	 * @param projectId
	 * @param flavorId
	 * @return
	 * @throws Exception
	 */
	public String getFlavorDetail(String projectId, String flavorId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_FLAVOR_DETAIL);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("FLAVOR_ID", flavorId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("FLAVOR_ID", flavorId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 创建规格
	 * @param projectId
	 * @param flavor
	 * @return
	 * @throws Exception
	 */
	public String createFlavor(String projectId, CloudFlavorPo flavor) throws Exception {
		String flavorId = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_FLAVOR);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("FLAVOR_NAME", flavor.getName());
		body.setString("CPU", flavor.getCpu() + "");
//		String mem = flavor.getType().equals(RmHostType.VIRTUAL.getValue()) ? flavor.getMem() * 1024 + "" : flavor.getMem()+"";
		body.setString("MEM", flavor.getMem()+"");
		body.setString("DISK", flavor.getDisk() + "");
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			flavorId = json.getJSONObject("flavor").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
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
	public void configFlavorVm(String projectId, String flavorId, CloudFlavorPo flavor) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.CONFIG_FLAVOR_VM);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("FLAVOR_ID", flavorId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("FLAVOR_ID", flavorId);
		body.setString("DISK", flavor.getDisk() + "");
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		int code = this.getExecuteResult(rspData);
		System.out.println(code);
	}
	
	/**
	 * 配置物理机规格
	 * @param projectId
	 * @param flavorId
	 * @param flavor
	 * @throws Exception
	 */
	public void configFlavorHost(String projectId, String flavorId, CloudFlavorPo flavor) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.CONFIG_FLAVOR_HOST);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("FLAVOR_ID", flavorId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("FLAVOR_ID", flavorId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		int code = this.getExecuteResult(rspData);
		System.out.println(code);
	}
	
	/**
	 * 操作虚拟机计算实例
	 * @param projectId
	 * @param serverId
	 * @param type
	 * @throws Exception
	 */
	public void operateVm(String projectId, String serverId, String type) throws Exception {
		this.operateVm(projectId, serverId, type, 202);
	}
	
	/**
	 * 操作虚拟机计算实例
	 * @param projectId
	 * @param serverId
	 * @param type
	 * @param resultCode
	 * @throws Exception
	 */
	public void operateVm(String projectId, String serverId, String type, int resultCode) throws Exception {
		IDataObject reqData = this.getIDataObject(type);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", RmHostType.VIRTUAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != resultCode) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	public void resizeVm(String projectId, String serverId, String flavorId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.RESIZE_VM);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("FLAVOR_ID", flavorId);
		body.setString("HOST_TYPE", RmHostType.VIRTUAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 202) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	public void putVm(String projectId, String serverId, String serverName) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_VM);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("SERVER_NAME", serverName);
		body.setString("HOST_TYPE", RmHostType.VIRTUAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			//throw new Exception(this.getExecuteMessage(rspData));
		}else{
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 虚拟机迁移
	 * @param projectId
	 * @param serverId
	 * @param hostName
	 * @throws Exception
	 */
	public void moveVm(String projectId, String serverId, String hostName) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.MOVE_VM);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_NAME", hostName);
		body.setString("HOST_TYPE", RmHostType.VIRTUAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 202) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 操作物理机计算实例
	 * @param projectId
	 * @param serverId
	 * @param type
	 * @param resultCode
	 * @throws Exception
	 */
	public void operateHost(String projectId, String serverId, String type, int resultCode) throws Exception {
		IDataObject reqData = this.getIDataObject(type);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != resultCode) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 删除服务器（回收）
	 * @param projectId
	 * @param serverId
	 * @param isVm
	 * @throws Exception
	 
	public void deleteServer(String projectId, String serverId, boolean isVm) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_VM);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 204) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}*/
	
	/**
	 * 获取VNC登录URL
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getVNCConsole(String projectId, String serverId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VNC_CONSOLE);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		String result = this.getExecuteResultData(rspData);
		JSONObject json = JSONObject.fromObject(result);
		return json.getJSONObject("console").getString("url");
	}
	
	/**
	 * 获取VNC登录URL(物理机)
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getVNCConsolePhy(String projectId, String serverId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VNC_CONSOLE_PHY);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		String result = this.getExecuteResultData(rspData);
		JSONObject json = JSONObject.fromObject(result);
		return json.getJSONObject("console").getString("url");
	}
	
	/**
	 * 创建虚拟机计算实例
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String createVm(String projectId, ServerModel serverModel,String ipData,String imageId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_VM);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("AVAILABILITY_ZONE", serverModel.getAzName());
		body.setString("FLAVOR_ID", serverModel.getFlavorId());
		body.setString("NETWORK_ID", serverModel.getNetworkId());
		body.setString("SERVER_IP", serverModel.getServerIp());
		body.setString("SERVER_NAME", serverModel.getServerName());
		body.setString("HOST_NAME", serverModel.getHostName());
		body.setString("VOLUME_ID", serverModel.getVolumeId());
		body.setString("IMAGE_ID", imageId);
		body.setString("IP_DATAS", ipData);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 创建物理机计算实例
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String createHost(String projectId, ServerModel serverModel,String ipData) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_HOST);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("AVAILABILITY_ZONE", serverModel.getAzName());
		body.setString("FLAVOR_ID", serverModel.getFlavorId());
		body.setString("IMAGE_ID", serverModel.getImageId());
		body.setString("NETWORK_ID", serverModel.getNetworkId());
		body.setString("SERVER_IP", serverModel.getServerIp());
		body.setString("SERVER_NAME", serverModel.getServerName());
		body.setString("IP_DATAS", ipData);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 查询计算实例详细信息
	 * @param projectId
	 * @param serverId
	 * @param isVm
	 * @return
	 * @throws Exception
	 */
	public String getServerDetail(String projectId, String serverId, boolean isVm) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VM_DETAIL);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return this.getExecuteResultData(rspData);
	}
	
	/**
	 * 查询计算实例状态
	 * @param projectId
	 * @param serverId
	 * @param isVm
	 * @return
	 * @throws Exception
	 */
	public String getServerState(String projectId, String serverId, boolean isVm) throws Exception{
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VM_DETAIL);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		String result = this.getExecuteResultData(rspData);
		JSONObject json = JSONObject.fromObject(result);
		return json.getJSONObject("server").getString("status");
	}
	
	/**
	 * 获取计算实例列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getServerList(String projectId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.GET_SERVER_LIST);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return this.getExecuteResultData(rspData);
	}
	
	/**
	 * 挂载服务器盘
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param isVm
	 * @return
	 * @throws Exception
	 */
	public String mountServerVolume(String projectId, String serverId, String volumeId, boolean isVm) throws Exception{
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.MOUNT_SERVER_VOLUME);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("VOLUME_ID", volumeId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 获取服务器卷列表
	 * @param projectId
	 * @param serverId
	 * @param isVm
	 * @return
	 * @throws Exception
	 */
	public String getServerVolume(String projectId, String serverId, boolean isVm) throws Exception{
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_SERVER_VOLUME);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 卸载服务器卷
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param isVm
	 * @throws Exception
	 */
	public void unmountServerVolume(String projectId, String serverId, String volumeId, boolean isVm) throws Exception{
		IDataObject reqData = this.getIDataObject(PVOperation.UNMOUNT_SERVER_VOLUME);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		paramMap.put("VOLUME_ID", volumeId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("VOLUME_ID", volumeId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 查询网卡
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getNetworkCard(String projectId, String serverId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_NETWORK_CARD);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
			//JSONObject json = JSONObject.fromObject(result);
			//routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 查询网卡状态
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getNetworkCardStatus(String projectId, String serverId,String portId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.GET_NETWORK_CARD_STATUS);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		String status = "";
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.fromObject(result);
			status = json.getJSONObject("interfaceAttachment").getString("port_state");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return status;
	}
	
	/**
	 * 删除网卡
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void deleteNetworkCard(String projectId, String serverId ,String portId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_NETWORK_CARD);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			//String result = this.getExecuteResultData(rspData);
			//JSONObject json = JSONObject.fromObject(result);
			//routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 添加网卡
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String addNetworkCard(String projectId, String serverId ,String portId) throws Exception {
		String result = "";
		IDataObject reqData = this.getIDataObject(PVOperation.ADD_NETWORK_CARD);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		int n = getExecuteResult(rspData);
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
	 * 更新计算配额
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param isVm
	 * @throws Exception
	 */
	public void putComputeQuota(String projectId, String setProjectId) throws Exception{
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_COMPUTE_QUOTA);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SET_PROJECT_ID", setProjectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
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
	 * 虚拟机添加安全组
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void addSecurityGroup(String projectId, String serverId ,String securityGroupId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.ADD_SECURITY_GROUP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("SECURITY_GROUP_ID", securityGroupId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 虚拟机移除安全组
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void removeSecurityGroup(String projectId, String serverId ,String securityGroupId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.REMOVE_SECURITY_GROUP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("SECURITY_GROUP_ID", securityGroupId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 
	 * 创建浮动IP
	 * @return
	 * @throws Exception
	 */
	public String  createFloatingIp(String projectId,String poolName) throws Exception {
		String jsonData ="";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_FLOATING_IP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("PROJECT_ID", projectId);
		body.setString("POOL_NAME", poolName);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
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
	public void deleteFloatingIp(String projectId, String floatingIpId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_FLOATING_IP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("FLOATING_IP_ID", floatingIpId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("FLOATING_IP_ID", floatingIpId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 虚拟机绑定浮动IP
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void addFloatingIp(String projectId, String serverId ,String ipAddress) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.ADD_FLOATING_IP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("IP_ADDRESS", ipAddress);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 虚拟机解绑定浮动IP
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public void removeFloatingIp(String projectId, String serverId ,String ipAddress) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.REMOVE_FLOATING_IP);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SERVER_ID", serverId);
		body.setString("IP_ADDRESS", ipAddress);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
}
