package com.git.cloud.resmgt.common.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class CmDeviceModelVo extends BaseBO implements java.io.Serializable{


	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 3158591318252406392L;
	
	private String Id;
	private String deviceModel;
	private String deviceType;
	private String deviceManufacturer;
	private String isActive;
	private String remark;

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
