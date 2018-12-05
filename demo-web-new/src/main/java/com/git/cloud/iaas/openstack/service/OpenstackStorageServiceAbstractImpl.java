package com.git.cloud.iaas.openstack.service;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.iaas.openstack.OpenstackInvokeTools;
import com.git.cloud.iaas.openstack.model.OpenstackException;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.iaas.openstack.model.VolumeRestModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.OSOperation_bak;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

public abstract class OpenstackStorageServiceAbstractImpl extends OpenstackInvokeTools implements OpenstackStorageService {
	
	/**
	 * 查询卷类型列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getVolumeTypes(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VOLUME_TYPE+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
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
	 * 创建卷
	 * @param projectId
	 * @param volumeModel
	 * @param type 系统盘或数据盘
	 * @return
	 * @throws Exception
	 */
	public String createVolume(OpenstackIdentityModel model, VolumeRestModel volumeModel, String type) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String result = "";
		String volumeId = "";
		IDataObject reqData = this.getIDataObject(type+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("TENANT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("TENANT_ID", model.getProjectId()); // 接口定义参数为TENANT_ID，实际是projectId
		body.setString("AVAILABILITY_ZONE", volumeModel.getAzName());
		body.setString("VOLUME_TYPE", volumeModel.getVolumeType());
		body.setString("DISK_SIZE", volumeModel.getDiskSize());
		if(type ==  OSOperation_bak.CREATE_DATA_VOLUME) {
			body.setString("IMAGE_ID", volumeModel.getTargetImageId()); // 数据盘 从targetimageid获取openstack需要的镜像id
			body.setString("IS_PASSTHROUGH", volumeModel.getPassthrough()); // 裸设备磁盘标识
		}else {
			body.setString("IMAGE_ID", volumeModel.getImageId()); // 只有系统卷才需要此参数
		}
		body.setString("IS_SHARE", volumeModel.getIsShare());
		body.setString("VOLUME_NAME", volumeModel.getName());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			result = this.getExecuteResultData(rspData);
			JSONObject json = JSONObject.parseObject(result);
			volumeId = json.getJSONObject("volume").getString("id");
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return volumeId;
	}
	
	/**
	 * 删除磁盘
	 * @param projectId
	 * @param volumeId
	 * @throws Exception
	 */
	public void deleteVolume(OpenstackIdentityModel model, String volumeId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_VOLUME+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("TENANT_ID", model.getProjectId());
		paramMap.put("VOLUME_ID", volumeId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("VOLUME_ID", volumeId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 202) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 查询卷状态
	 * @param projectId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	public String getVolumeStatus(OpenstackIdentityModel model, String volumeId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VOLUME_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("TENANT_ID", model.getProjectId());
		paramMap.put("VOLUME_ID", volumeId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("VOLUME_ID", volumeId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		String result = this.getExecuteResultData(rspData);
		JSONObject json = JSONObject.parseObject(result);
		return json.getJSONObject("volume").getString("status");
	}
	
	public String updateVolume(OpenstackIdentityModel model, String volumeId, String volumeName) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_VOLUME+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("TENANT_ID", model.getProjectId());
		paramMap.put("VOLUME_ID", volumeId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("VOLUME_ID", volumeId);
		body.setString("VOLUME_NAME", volumeName);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		return this.getExecuteResultData(rspData);
	}
	
	/**
	 * 查询卷列表
	 * @param projectId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	public String getVolumeList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VOLUME_LIST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("TENANT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("TENANT_ID", model.getProjectId());
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
	 * 查询卷详情
	 * @param projectId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	public String getVolumeDetail(OpenstackIdentityModel model,String volumeId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VOLUME_DETAIL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("TENANT_ID", model.getProjectId());
		paramMap.put("VOLUME_ID", volumeId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("TENANT_ID", model.getProjectId());
		body.setString("VOLUME_ID", volumeId);
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
	 * 更新存储配额
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param isVm
	 * @throws Exception
	 */
	public void putVolumeQuota(OpenstackIdentityModel model, String setProjectId) throws Exception{
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.PUT_VOLUME_QUOTA+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
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
	 * 创建磁盘快照
	 * @return
	 * @throws Exception
	 */
	public String  createVolumeSnapshot(OpenstackIdentityModel model, String snapshotName,String volumeId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.CREATE_VOLUME_SNAPSHOT+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SNAPSHOT_NAME", snapshotName);
		body.setString("VOLUME_ID", volumeId);
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
	 * 快照创建磁盘
	 * @return
	 * @throws Exception
	 */
	public String  snapshotCreateVolume(OpenstackIdentityModel model, String volumeName, String snapshotId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.SNAPSHOT_CREATE_VOLUME+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SNAPSHOT_ID", snapshotId);
		body.setString("VOLUME_NAME", volumeName);
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
	 * 快照创建磁盘
	 * @return
	 * @throws Exception
	 */
	public String  snapshotCreateVolume(OpenstackIdentityModel model, String snapshotId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.SNAPSHOT_CREATE_VOLUME+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SNAPSHOT_ID", snapshotId);
		body.setString("VOLUME_NAME", "dataDisk"+System.currentTimeMillis());
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
	 * 磁盘快照列表
	 * @return
	 * @throws Exception
	 */
	public String  getVolumeSnapshotList(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VOLUME_SNAPSHOT_LIST+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
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
	 * 磁盘快照详情
	 * @return
	 * @throws Exception
	 */
	public String  getVolumeSnapshot(OpenstackIdentityModel model, String snapshotId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		String jsonData="";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_VOLUME_SNAPSHOT+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SNAPSHOT_ID", snapshotId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SNAPSHOT_ID", snapshotId);
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
	 * 删除磁盘快照
	 * @return
	 * @throws Exception
	 */
	public void  deleteVolumeSnapshot(OpenstackIdentityModel model, String snapshotId) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model));
		IDataObject reqData = this.getIDataObject(OSOperation_bak.DELETE_VOLUME_SNAPSHOT+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		paramMap.put("SNAPSHOT_ID", snapshotId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		body.setString("SNAPSHOT_ID", snapshotId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 查询后端存储（StoragePool）
	 * @return
	 * @throws Exception
	 */
	public String  getStoragePool(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_STORAGE_POOL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getToken());
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
	 * 查询后端存储（StorageVirtualPool）
	 * @return
	 * @throws Exception
	 */
	public String  getStorageVirtualPool(OpenstackIdentityModel model) throws Exception {
		model.setToken(this.getToken(model.getVersion(),model.getOpenstackIp(),model.getDomainName(),model.getManageOneIp()));
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(OSOperation_bak.GET_STORAGE_VIRTUAL_POOL+model.getVersion().replace(".", ""),model.getOpenstackIp(),model.getDomainName(),model.getToken());
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap(model.getDomainName());
		paramMap.put("PROJECT_ID", model.getProjectId());
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO(model.getToken());
		body.setString("PROJECT_ID", model.getProjectId());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new OpenstackException(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}

}
