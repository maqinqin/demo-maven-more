package com.git.cloud.resmgt.common.model.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;


/**
 * The persistent class for the CM_STORAGE database table.
 * 
 */
public class CmStorageVo extends BaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

//	private int cacheCapacity;
//
//	private int diskCapacity;
//
//	private int diskNumber;
//
//	private int diskRpm;
//
//	private int diskSize;
	private Integer cacheCapacity;

	private Integer diskCapacity;

	private Integer diskNumber;

	private Integer diskRpm;

	private Integer diskSize;

	private String mgrIp;

	private String microCode;

//	private int portCount;
//
//	private int portSpeed;
	private Integer portCount;

	private Integer portSpeed;
	
	private String appType;
	
	private String storageType;
	
	private String guId;
	
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

	public CmStorageVo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public int getCacheCapacity() {
//		return this.cacheCapacity;
//	}
//
//	public void setCacheCapacity(int cacheCapacity) {
//		this.cacheCapacity = cacheCapacity;
//	}
//
//	public int getDiskCapacity() {
//		return this.diskCapacity;
//	}
//
//	public void setDiskCapacity(int diskCapacity) {
//		this.diskCapacity = diskCapacity;
//	}
//
//	public int getDiskNumber() {
//		return this.diskNumber;
//	}
//
//	public void setDiskNumber(int diskNumber) {
//		this.diskNumber = diskNumber;
//	}
//
//	public int getDiskRpm() {
//		return this.diskRpm;
//	}
//
//	public void setDiskRpm(int diskRpm) {
//		this.diskRpm = diskRpm;
//	}
//
//	public int getDiskSize() {
//		return this.diskSize;
//	}
//
//	public void setDiskSize(int diskSize) {
//		this.diskSize = diskSize;
//	}

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

//	public int getPortCount() {
//		return this.portCount;
//	}
//
//	public void setPortCount(int portCount) {
//		this.portCount = portCount;
//	}
//
//	public int getPortSpeed() {
//		return this.portSpeed;
//	}
//
//	public void setPortSpeed(int portSpeed) {
//		this.portSpeed = portSpeed;
//	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	
	public Integer getCacheCapacity() {
		return cacheCapacity;
	}

	public void setCacheCapacity(Integer cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}

	public Integer getDiskCapacity() {
		return diskCapacity;
	}

	public void setDiskCapacity(Integer diskCapacity) {
		this.diskCapacity = diskCapacity;
	}

	public Integer getDiskNumber() {
		return diskNumber;
	}

	public void setDiskNumber(Integer diskNumber) {
		this.diskNumber = diskNumber;
	}

	public Integer getDiskRpm() {
		return diskRpm;
	}

	public void setDiskRpm(Integer diskRpm) {
		this.diskRpm = diskRpm;
	}

	public Integer getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}

	public Integer getPortCount() {
		return portCount;
	}

	public void setPortCount(Integer portCount) {
		this.portCount = portCount;
	}

	public Integer getPortSpeed() {
		return portSpeed;
	}

	public void setPortSpeed(Integer portSpeed) {
		this.portSpeed = portSpeed;
	}
	public String getGuId() {
		return guId;
	}

	public void setGuId(String guId) {
		this.guId = guId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}