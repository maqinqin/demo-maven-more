package com.git.cloud.resmgt.common.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;


/**
 * The persistent class for the CM_STORAGE database table.
 * 
 */
public class CmStoragePo extends BaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private int cacheCapacity;

	private int diskCapacity;

	private int diskNumber;

	private int diskRpm;

	private int diskSize;

	private String mgrIp;

	private String microCode;

	private int portCount;

	private int portSpeed;
	
	private String appType;
	
	private String storageChildPoolId;
	
    public String getStorageChildPoolId() {
		return storageChildPoolId;
	}

	public void setStorageChildPoolId(String storageChildPoolId) {
		this.storageChildPoolId = storageChildPoolId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public CmStoragePo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCacheCapacity() {
		return this.cacheCapacity;
	}

	public void setCacheCapacity(int cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}

	public int getDiskCapacity() {
		return this.diskCapacity;
	}

	public void setDiskCapacity(int diskCapacity) {
		this.diskCapacity = diskCapacity;
	}

	public int getDiskNumber() {
		return this.diskNumber;
	}

	public void setDiskNumber(int diskNumber) {
		this.diskNumber = diskNumber;
	}

	public int getDiskRpm() {
		return this.diskRpm;
	}

	public void setDiskRpm(int diskRpm) {
		this.diskRpm = diskRpm;
	}

	public int getDiskSize() {
		return this.diskSize;
	}

	public void setDiskSize(int diskSize) {
		this.diskSize = diskSize;
	}

	public String getMgrIp() {
		return this.mgrIp;
	}

	public void setMgrIp(String mgrIp) {
		this.mgrIp = mgrIp;
	}

	public String getMicroCode() {
		return this.microCode;
	}

	public void setMicroCode(String microCode) {
		this.microCode = microCode;
	}

	public int getPortCount() {
		return this.portCount;
	}

	public void setPortCount(int portCount) {
		this.portCount = portCount;
	}

	public int getPortSpeed() {
		return this.portSpeed;
	}

	public void setPortSpeed(int portSpeed) {
		this.portSpeed = portSpeed;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}