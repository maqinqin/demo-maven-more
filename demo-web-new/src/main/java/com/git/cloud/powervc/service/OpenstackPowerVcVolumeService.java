package com.git.cloud.powervc.service;

import java.util.HashMap;

import com.git.cloud.powervc.common.OpenstackPowerVcService;
import com.git.cloud.powervc.model.VolumeModel;
import com.git.support.common.MesgFlds;
import com.git.support.common.PVOperation;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.ParamDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * Openstack存储资源接口
 * @author SunHailong
 * @version v1.0 2017-3-20
 */
public class OpenstackPowerVcVolumeService extends OpenstackPowerVcService {

	private static OpenstackPowerVcVolumeService openstackVolumeService;
	
	private OpenstackPowerVcVolumeService(){};
	
	public static OpenstackPowerVcVolumeService getVolumeServiceInstance(String openstackIp,String domainName, String token) {
		if(openstackVolumeService == null) {
			openstackVolumeService = new OpenstackPowerVcVolumeService();
		}
		openstackVolumeService.openstackIp = openstackIp;
		openstackVolumeService.domainName = domainName;
		openstackVolumeService.token = token;
		return openstackVolumeService;
	}
	
	/**
	 * 查询卷类型列表
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public String getVolumeTypes(String projectId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VOLUME_TYPE);
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
	 * 创建卷
	 * @param projectId
	 * @param volumeModel
	 * @param type 系统盘或数据盘
	 * @return
	 * @throws Exception
	 */
	public String createVolume(String projectId, VolumeModel volumeModel, String type) throws Exception {
		String volumeId = "";
		IDataObject reqData = this.getIDataObject(type);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("TENANT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("TENANT_ID", projectId); // 接口定义参数为TENANT_ID，实际是projectId
		body.setString("AVAILABILITY_ZONE", volumeModel.getAzName());
		body.setString("VOLUME_TYPE", volumeModel.getVolumeType());
		body.setString("DISK_SIZE", volumeModel.getDiskSize());
		body.setString("IMAGE_ID", volumeModel.getImageId()); // 只有系统卷才需要此参数
		body.setString("IS_SHARE", volumeModel.getIsShare());
		body.setString("VOLUME_NAME", volumeModel.getName());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) == 202) {
			volumeId = this.getExecuteResultData(rspData, "VOLUME_ID");
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return volumeId;
	}
	
	/**
	 * 删除磁盘
	 * @param projectId
	 * @param volumeId
	 * @throws Exception
	 */
	public void deleteVolume(String projectId, String volumeId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_VOLUME);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("TENANT_ID", projectId);
		paramMap.put("VOLUME_ID", volumeId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("TENANT_ID", projectId);
		body.setString("VOLUME_ID", volumeId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 202) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 查询卷状态
	 * @param projectId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	public String getVolumeStatus(String projectId, String volumeId) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VOLUME_DETAIL);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("TENANT_ID", projectId);
		paramMap.put("VOLUME_ID", volumeId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("TENANT_ID", projectId);
		body.setString("VOLUME_ID", volumeId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) != 200) {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return this.getExecuteResultData(rspData, "VOLUME_STATUS");
	}
	public String putVolume(String projectId, String volumeId, String volumeName) throws Exception {
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_VOLUME);
		BodyDO body = this.createBodyDO();
		body.setString("TENANT_ID", projectId);
		body.setString("VOLUME_ID", volumeId);
		body.setString("VOLUME_NAME", volumeName);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
//		if(this.getExecuteResult(rspData) != 200) {
//			throw new Exception(this.getExecuteMessage(rspData));
//		}
		return this.getExecuteResultData(rspData);
	}
	
	/**
	 * 查询卷列表
	 * @param projectId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	public String getVolumeList(String token,String projectId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VOLUME_LIST);
		BodyDO body = this.createBodyDO();
		body.setString("TOKEN", token);
		body.setString("TENANT_ID", projectId);
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
	 * 查询卷详情
	 * @param projectId
	 * @param volumeId
	 * @return
	 * @throws Exception
	 */
	public String getVolumeDetail(String token,String projectId,String volumeId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VOLUME_DETAIL);
		BodyDO body = this.createBodyDO();
		body.setString("TOKEN", token);
		body.setString("TENANT_ID", projectId);
		body.setString("VOLUME_ID", volumeId);
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
	 * 更新存储配额
	 * @param projectId
	 * @param serverId
	 * @param volumeId
	 * @param isVm
	 * @throws Exception
	 */
	public void putVolumeQuota(String projectId, String setProjectId) throws Exception{
		IDataObject reqData = this.getIDataObject(PVOperation.PUT_VOLUME_QUOTA);
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
	 * 创建磁盘快照
	 * @return
	 * @throws Exception
	 */
	public String  createVolumeSnapshot(String projectId, String snapshotName,String volumeId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.CREATE_VOLUME_SNAPSHOT);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SNAPSHOT_NAME", snapshotName);
		body.setString("VOLUME_ID", volumeId);
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
	 * 快照创建磁盘
	 * @return
	 * @throws Exception
	 */
	public String  snapshotCreateVolume(String projectId, String snapshotId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.SNAPSHOT_CREATE_VOLUME);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SNAPSHOT_ID", snapshotId);
		body.setString("VOLUME_NAME", "dataDisk"+System.currentTimeMillis());
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
	 * 磁盘快照列表
	 * @return
	 * @throws Exception
	 */
	public String  getVolumeSnapshotList(String projectId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VOLUME_SNAPSHOT_LIST);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
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
	 * 磁盘快照详情
	 * @return
	 * @throws Exception
	 */
	public String  getVolumeSnapshot(String projectId, String snapshotId) throws Exception {
		String jsonData="";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_VOLUME_SNAPSHOT);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SNAPSHOT_ID", snapshotId);
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
	 * 删除磁盘快照
	 * @return
	 * @throws Exception
	 */
	public void  deleteVolumeSnapshot(String projectId, String snapshotId) throws Exception {
		
		IDataObject reqData = this.getIDataObject(PVOperation.DELETE_VOLUME_SNAPSHOT);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		body.setString("SNAPSHOT_ID", snapshotId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
	}
	
	/**
	 * 查询后端存储（StoragePool）
	 * @return
	 * @throws Exception
	 */
	public String  getStoragePool(String projectId) throws Exception {
		String jsonData = "";
		IDataObject reqData = this.getIDataObject(PVOperation.GET_STORAGE_POOL);
		ParamDO param = ParamDO.CreateParamDO();
		HashMap<String, Object> paramMap = this.createParamDOMap();
		paramMap.put("PROJECT_ID", projectId);
		param.setContainer(paramMap);
		reqData.setDataObject(MesgFlds.PARAM, param);
		BodyDO body = this.createBodyDO();
		body.setString("PROJECT_ID", projectId);
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rspData = this.execute(reqData);
		if(this.getExecuteResult(rspData) >= 200 && this.getExecuteResult(rspData) < 300) {
			jsonData = this.getExecuteResultData(rspData);
		} else {
			throw new Exception(this.getExecuteMessage(rspData));
		}
		return jsonData;
	}
	
}
