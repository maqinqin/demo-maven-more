package com.git.cloud.resmgt.common.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class RmDeviceVolumesRefPo extends BaseBO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键id*/
	private String id;
	/** 磁盘大小*/
	private String diskSize;
	/** 磁盘类型*/
	private String diskType;
	/** 设备id*/
	private String deviceId;
	/** 状态 未挂载：unmount,已挂载：mount*/
	private String mountStatus;
	/** Openstack端的卷Id */
	private String targetVolumeId;
	/** 卷名称 */
	private String volumeName;
	
	/** 云平台端的卷Id */
	private String volumeId;
	
	
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
	public String getTargetVolumeId() {
		return targetVolumeId;
	}
	public void setTargetVolumeId(String targetVolumeId) {
		this.targetVolumeId = targetVolumeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}
	public String getDiskType() {
		return diskType;
	}
	public void setDiskType(String diskType) {
		this.diskType = diskType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getMountStatus() {
		return mountStatus;
	}
	public void setMountStatus(String mountStatus) {
		this.mountStatus = mountStatus;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
