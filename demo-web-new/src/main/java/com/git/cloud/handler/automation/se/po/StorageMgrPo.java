package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 
 * <p>
 * 
 * @author huaili
 * @version 1.0 2013-4-28
 * @see
 */
public class StorageMgrPo  extends BaseBO implements java.io.Serializable{
	private String id;	
	private String managerIp;
	private String managerTypeCode;
	private String typeCode;
	private String namespace;
	private String storageMgrName;
	private String deviceId;
	private String port;
	private String seatCode;
	private String isActive;
	
	public String getId() {
		return id;
	}

	public String getManagerIp() {
		return managerIp;
	}

	public String getManagerTypeCode() {
		return managerTypeCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getStorageMgrName() {
		return storageMgrName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getPort() {
		return port;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setManagerIp(String managerIp) {
		this.managerIp = managerIp;
	}

	public void setManagerTypeCode(String managerTypeCode) {
		this.managerTypeCode = managerTypeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setStorageMgrName(String storageMgrName) {
		this.storageMgrName = storageMgrName;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
