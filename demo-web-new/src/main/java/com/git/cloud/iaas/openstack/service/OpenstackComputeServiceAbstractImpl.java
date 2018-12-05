package com.git.cloud.iaas.openstack.service;

import java.util.HashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.iaas.openstack.OpenstackInvokeTools;
import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VmRestModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public abstract class OpenstackComputeServiceAbstractImpl extends OpenstackInvokeTools implements OpenstackComputeService {
	
	
	
	/**
	 * 查询主机组列表
	 * 
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getGroupHost(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(), model.getOpenstackIp(), model.getDomainName(),
				model.getManageOneIp()));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_HOST_GROUP + model.getVersion().replace(".", ""),
				model.getOpenstackIp(), model.getDomainName(), model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if (this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 查询主机资源列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getGroupHostRes(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_HOST_RES+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
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
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public String getGroupHostResDetail(OpenstackIdentityModel model,String hostId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_HOST_RES_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("HOST_ID", hostId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("HOST_ID", hostId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	/**
	 * 查询规格列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getFlavor(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_FLAVOR+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
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
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public String getFlavorDetail(OpenstackIdentityModel model, String flavorId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_FLAVOR_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
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
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	
	
	/**
	 * 操作虚拟机计算实例
	 * @param projectId
	 * @param serverId
	 * @param type
	 * @throws Exception
	 */
	public void operateVm(OpenstackIdentityModel model, String serverId, String type) throws Exception {
		this.operateVm(model,serverId, type, 202);
	}
	
	public void operateVm(OpenstackIdentityModel model, String serverId, String type, int resultCode) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(type+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", RmHostType.VIRTUAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != resultCode) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	public void resizeVm(OpenstackIdentityModel model, String serverId, String flavorId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.RESIZE_VM+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("FLAVOR_ID", flavorId);
		body.setString("HOST_TYPE", RmHostType.VIRTUAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 202) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	public void updateVm(OpenstackIdentityModel model, String serverId, String serverName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_VM+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("SERVER_NAME", serverName);
		body.setString("HOST_TYPE", RmHostType.VIRTUAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			//throw new Exception(this.getExecuteMessage(rspData));
		}else{
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 虚拟机迁移
	 * @param projectId
	 * @param serverId
	 * @param hostName
	 * @throws Exception
	 */
	public void moveVm(OpenstackIdentityModel model, String serverId, String hostName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.MOVE_VM+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_NAME", hostName);
		body.setString("HOST_TYPE", RmHostType.VIRTUAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 202) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public void operateHost(OpenstackIdentityModel model, String serverId, String type, int resultCode) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(type+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != resultCode) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 获取VNC登录URL
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getVNCConsole(OpenstackIdentityModel model, String serverId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VNC_CONSOLE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		String result = this.getExecuteResultData(rspData);
		JSONObject json = JSONObject.parseObject(result);
		return json.getJSONObject("console").getString("url");
	}
	
	/**
	 * 获取VNC登录URL(物理机)
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getVNCConsolePhy(OpenstackIdentityModel model, String serverId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VNC_CONSOLE_PHY+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		String result = this.getExecuteResultData(rspData);
		JSONObject json = JSONObject.parseObject(result);
		return json.getJSONObject("console").getString("url");
	}
	
	/**
	 * 创建虚拟机计算实例
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String createVm(OpenstackIdentityModel model, VmRestModel vmRestModel,String ipData) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VM+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("AVAILABILITY_ZONE", vmRestModel.getAzName());
		body.setString("FLAVOR_ID", vmRestModel.getFlavorId());
		body.setString("NETWORK_ID", vmRestModel.getNetworkId());
		body.setString("SERVER_IP", vmRestModel.getServerIp());
		body.setString("SERVER_NAME", vmRestModel.getServerName());
		body.setString("HOST_NAME", vmRestModel.getHostName());
		body.setString("VOLUME_ID", vmRestModel.getVolumeId());
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
	 * 查询计算实例详细信息
	 * @param projectId
	 * @param serverId
	 * @param isVm
	 * @return
	 * @throws Exception
	 */
	public String getServerDetail(OpenstackIdentityModel model, String serverId, boolean isVm) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VM_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public String getServerState(OpenstackIdentityModel model, String serverId, boolean isVm) throws Exception{
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VM_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		String result = this.getExecuteResultData(rspData);
		JSONObject json = JSONObject.parseObject(result);
		return json.getJSONObject("server").getString("status");
	}
	
	/**
	 * 获取计算实例列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getServerList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_SERVER_LIST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public String mountServerVolume(OpenstackIdentityModel model, String serverId, String volumeId, boolean isVm) throws Exception{
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.MOUNT_SERVER_VOLUME+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("VOLUME_ID", volumeId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public String getServerVolume(OpenstackIdentityModel model, String serverId, boolean isVm) throws Exception{
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_SERVER_VOLUME+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public void unmountServerVolume(OpenstackIdentityModel model, String serverId, String volumeId, boolean isVm) throws Exception{
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.UNMOUNT_SERVER_VOLUME+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		paramMap.put("VOLUME_ID", volumeId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("VOLUME_ID", volumeId);
		body.setString("HOST_TYPE", isVm ? RmHostType.VIRTUAL.getValue() : RmHostType.PHYSICAL.getValue());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 查询网卡
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String getNetworkCard(OpenstackIdentityModel model, String serverId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_NETWORK_CARD+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public String getNetworkCardStatus(OpenstackIdentityModel model, String serverId,String portId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_NETWORK_CARD_STATUS+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		String status = "";
		if(this.getExecuteResult(rspData) == 200) {
			String result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			status = json.getJSONObject("interfaceAttachment").getString("port_state");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public void deleteNetworkCard(OpenstackIdentityModel model, String serverId ,String portId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_NETWORK_CARD+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		paramMap.put("PORT_ID", portId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 添加网卡
	 * @param projectId
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public String addNetworkCard(OpenstackIdentityModel model, String serverId ,String portId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String result = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.ADD_NETWORK_CARD+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_ID", serverId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SERVER_ID", serverId);
		body.setString("PORT_ID", portId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		getExecuteResult(rspData);
		if(this.getExecuteResult(rspData) == 200) {
			result = this.getExecuteResultData(rspData);
			//JSONObject json = JSONObject.parseObject(result);
			//routerId = json.getJSONObject("router").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
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
	public void putComputeQuota(OpenstackIdentityModel model, String setProjectId) throws Exception{
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_COMPUTE_QUOTA+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SET_PROJECT_ID", setProjectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
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
	 * 虚拟机组正常返回码
	 */
	private final static int VM_GROUP_NORMAL_RESPONSE_CODE = 200;
	private final static int VM_GROUP_DELETE_NORMAL_RESPONSE_CODE = 204;
	private final static int VM_GROUP_NOT_FOUND = 404;

	@Override
	public String createVmGroup(OpenstackIdentityModel model, String name, String policy) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VM_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("GROUP_NAME", name);
		paramMap.put("POLICY_TYPE", policy);
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("GROUP_NAME", name);
		body.setString("POLICY_TYPE", policy);
		body.setString("PROJECT_ID", model.getProjectId());
		IDataObject rspData = this.execute(reqData);
		if (this.getExecuteResult(rspData) == VM_GROUP_NORMAL_RESPONSE_CODE) {
			return this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public String getServerGroupDetails(OpenstackIdentityModel model, String id) throws Exception {
		model.setToken(this.getToken(model.getVersion(), model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VM_GROUP_DETAILS + model.getVersion()
				.replace(".", ""), model.getOpenstackIp(), model.getDomainName(), model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("GROUP_ID", id);
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("GROUP_ID", id);
		body.setString("PROJECT_ID", model.getProjectId());
		IDataObject rspData = this.execute(reqData);
		if (this.getExecuteResult(rspData) == VM_GROUP_NORMAL_RESPONSE_CODE) {
			return this.getExecuteResultData(rspData);
		} else if (this.getExecuteResult(rspData) == VM_GROUP_NOT_FOUND) {
			return null;
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}

	@Override
	public String deleteVmGroup(OpenstackIdentityModel model, String id) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_VM_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("GROUP_ID", id);
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		reqData.setDataObject(MesgFlds.BODY, body);
		body.setString("GROUP_ID", id);
		body.setString("PROJECT_ID", model.getProjectId());
		IDataObject rspData = this.execute(reqData);
		if (this.getExecuteResult(rspData) == VM_GROUP_DELETE_NORMAL_RESPONSE_CODE) {
			return this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	public String getBaremetal(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String xmlData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_BAREMETAL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		reqData.setDataObject(MesgFlds.BODY, this.createBodyDO(model.getToken()));
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			xmlData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return xmlData;
	}
	
	public String getBaremetalDetail(OpenstackIdentityModel model,String hostId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String xmlData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_BAREMETAL_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("HOST_ID", hostId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getProjectId());
		body.setString("HOST_ID", hostId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			xmlData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return xmlData;
	}
	
	public String createVmForGroup(OpenstackIdentityModel model,VmRestModel vmRestModel,String ipData) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VM_FOR_GROUP+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("GROUP_ID", vmRestModel.getVmGroupId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getProjectId());
		body.setString("AVAILABILITY_ZONE", vmRestModel.getAzName());
		body.setString("FLAVOR_ID", vmRestModel.getFlavorId());
		body.setString("NETWORK_ID", vmRestModel.getNetworkId());
		body.setString("SERVER_IP", vmRestModel.getServerIp());
		body.setString("SERVER_NAME", vmRestModel.getServerName());
		body.setString("HOST_NAME", vmRestModel.getHostName());
		body.setString("VOLUME_ID", vmRestModel.getVolumeId());
		body.setString("IP_DATAS", ipData);
		body.setString("GROUP_ID", vmRestModel.getVmGroupId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
	public String getBuildBareMetal(OpenstackIdentityModel model, String serverName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject("getBuildBareMetal"+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SERVER_NAME", serverName);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 200) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
}
