package com.git.cloud.resmgt.common.model.vo;

import com.git.cloud.common.model.base.BaseBO;
/**
 * 
 * @author 王成辉
 * @Description 设备信息PO
 * @date 2014-12-17
 *
 */
public class CmDeviceAndModelVo extends BaseBO implements java.io.Serializable{


	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 3158591318252406392L;
	
	private String id;
	private String seatId;
	//private String deviceType;
	private String deviceName;
	private String sn;
	private String deviceModelId;
	//private String manufacturer;
	private String resPoolId;
	private String isActive;
	private String description;
	private String deviceStatus;
	private String deviceType;
	private String deviceTypeName;
	private String deviceManufacturer;
	private String deviceModel;
	private String poolName;
	private String datacenterName;
	
	

	public String getDatacenterName() {
		return datacenterName;
	}


	public void setDatacenterName(String datacenterName) {
		this.datacenterName = datacenterName;
	}


	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSeatId() {
		return seatId;
	}


	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}


	public String getDeviceName() {
		return deviceName;
	}


	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}


	public String getSn() {
		return sn;
	}


	public void setSn(String sn) {
		this.sn = sn;
	}


	public String getDeviceModelId() {
		return deviceModelId;
	}


	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}


	public String getResPoolId() {
		return resPoolId;
	}


	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getDeviceStatus() {
		return deviceStatus;
	}


	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}


	public String getDeviceModel() {
		return deviceModel;
	}


	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}


	public String getPoolName() {
		return poolName;
	}


	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}


	public String getDeviceTypeName() {
		return deviceTypeName;
	}


	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	
	

}
