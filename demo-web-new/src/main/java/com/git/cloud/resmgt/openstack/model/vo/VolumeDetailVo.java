package com.git.cloud.resmgt.openstack.model.vo;

import java.util.List;

import com.git.cloud.resmgt.common.model.po.CmDevicePo;

public class VolumeDetailVo {
	private String volumeId;
	private String volumeName;
	private String volumeSize;
	private String volumeType;
	private String volumeStatus;
	private String createTime;
	private String updateTime;
	private String describe;
	private String userId;
	private String isEntry;		//是否加密
	private String isShareVol;
	private String azName;
	private String projectId;
	private String storageType;
	private String sysVolumeVal;
	private String iaasUuid;

    public String getIaasUuid() {
         return iaasUuid;
    }
    public void setIaasUuid(String iaasUuid) {
         this.iaasUuid = iaasUuid;
    }

	
	public String getSysVolumeVal() {
		return sysVolumeVal;
	}
	public void setSysVolumeVal(String sysVolumeVal) {
		this.sysVolumeVal = sysVolumeVal;
	}
	public String getAzName() {
		return azName;
	}
	public void setAzName(String azName) {
		this.azName = azName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	private List<String> serverId;
	
	private List<CmDevicePo> device;
	
	public List<CmDevicePo> getDevice() {
		return device;
	}
	public void setDevice(List<CmDevicePo> device) {
		this.device = device;
	}
	public String getVolumeId() {
		return volumeId;
	}
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}
	public String getVolumeName() {
		return volumeName;
	}
	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}
	public String getVolumeSize() {
		return volumeSize;
	}
	public void setVolumeSize(String volumeSize) {
		this.volumeSize = volumeSize;
	}
	public String getVolumeType() {
		return volumeType;
	}
	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}
	public String getVolumeStatus() {
		return volumeStatus;
	}
	public void setVolumeStatus(String volumeStatus) {
		this.volumeStatus = volumeStatus;
	}
	public String getIsShareVol() {
		return isShareVol;
	}
	public void setIsShareVol(String isShareVol) {
		this.isShareVol = isShareVol;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getIsEntry() {
		return isEntry;
	}
	public void setIsEntry(String isEntry) {
		this.isEntry = isEntry;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getServerId() {
		return serverId;
	}
	public void setServerId(List<String> serverId) {
		this.serverId = serverId;
	}
}
