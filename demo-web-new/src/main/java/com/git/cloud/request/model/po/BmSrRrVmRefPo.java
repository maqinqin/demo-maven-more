package com.git.cloud.request.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class BmSrRrVmRefPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String refId;
	private String rrinfoId;
	private String srId;
	private String deviceId;
	private String isEnough;
	private String moveHostId;
	private Integer cpuOld;
	private Integer memOld;
	private Integer diskOld;
	private String volumeType;//卷类型
	private String volumeTypeId;//卷ID
	

	public Integer getCpuOld() {
		return cpuOld;
	}

	public void setCpuOld(Integer cpuOld) {
		this.cpuOld = cpuOld;
	}

	public Integer getMemOld() {
		return memOld;
	}

	public void setMemOld(Integer memOld) {
		this.memOld = memOld;
	}

	public Integer getDiskOld() {
		return diskOld;
	}

	public void setDiskOld(Integer diskOld) {
		this.diskOld = diskOld;
	}

	public BmSrRrVmRefPo() {
	}

	public BmSrRrVmRefPo(String refId) {
		this.refId = refId;
	}

	public BmSrRrVmRefPo(String refId, String rrinfoId, String srId,
			String deviceId, String isEnough, String moveHostId) {
		this.refId = refId;
		this.rrinfoId = rrinfoId;
		this.srId = srId;
		this.deviceId = deviceId;
		this.isEnough = isEnough;
		this.moveHostId = moveHostId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRrinfoId() {
		return rrinfoId;
	}

	public void setRrinfoId(String rrinfoId) {
		this.rrinfoId = rrinfoId;
	}

	public String getSrId() {
		return srId;
	}

	public void setSrId(String srId) {
		this.srId = srId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIsEnough() {
		return isEnough;
	}

	public void setIsEnough(String isEnough) {
		this.isEnough = isEnough;
	}

	public String getMoveHostId() {
		return moveHostId;
	}

	public void setMoveHostId(String moveHostId) {
		this.moveHostId = moveHostId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public String getVolumeTypeId() {
		return volumeTypeId;
	}

	public void setVolumeTypeId(String volumeTypeId) {
		this.volumeTypeId = volumeTypeId;
	}
	
}