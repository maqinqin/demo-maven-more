package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CmStorage entity. @author MyEclipse Persistence Tools

@Entity
@Table(name = "CM_STORAGE") */
public class StoragePo  extends BaseBO implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = -312620776484753032L;
	
	
	private String deviceId;
	private String microCode;
	private String mgrIp;
	private String cacheCapacity;
	private String diskCapacity;
	private String diskSize;
	private String diskNum;
	private String diskRpm;
	private String portCount;
	private String portSpeed;
	private String appType;
	private String storageChildPoolId;
	private String storageType;
//	private String storageGroupNum;
	private String foreportProtocolTypeCode;
	private String storageFuncCode;
	private String processorUnitNum;
	private String capactiy;
	private String fcportGroupNum;

	// Constructors

	/** default constructor */
	public StoragePo() {
	}

//	/** minimal constructor */
//	public StoragePo(Long deviceId, String foreportProtocolTypeCode,
//			String storageFuncCode) {
//		this.deviceId = deviceId;
//		this.foreportProtocolTypeCode = foreportProtocolTypeCode;
//		this.storageFuncCode = storageFuncCode;
//	}
//
//	/** full constructor */
//	public StoragePo(Long deviceId, Long cache,
//			String foreportProtocolTypeCode, String storageFuncCode,
//			Long processorUnitNum, Long capactiy) {
//		this.deviceId = deviceId;
//		this.cache = cache;
//		this.foreportProtocolTypeCode = foreportProtocolTypeCode;
//		this.storageFuncCode = storageFuncCode;
//		this.processorUnitNum = processorUnitNum;
//		this.capactiy = capactiy;
//	}
//
//	// Property accessors
//	@Id
//	@Column(name = "DEVICE_ID", unique = true, nullable = false, precision = 18, scale = 0)
//	public Long getDeviceId() {
//		return this.deviceId;
//	}
//
//	public void setDeviceId(Long deviceId) {
//		this.deviceId = deviceId;
//	}
//
//	@Column(name = "CACHE", precision = 18, scale = 0)
//	public Long getCache() {
//		return this.cache;
//	}
//
//	public void setCache(Long cache) {
//		this.cache = cache;
//	}
//
//	@Column(name = "FOREPORT_PROTOCOL_TYPE_CODE", nullable = false, length = 32)
//	public String getForeportProtocolTypeCode() {
//		return this.foreportProtocolTypeCode;
//	}
//
//	public void setForeportProtocolTypeCode(String foreportProtocolTypeCode) {
//		this.foreportProtocolTypeCode = foreportProtocolTypeCode;
//	}
//
//	@Column(name = "STORAGE_FUNC_CODE", nullable = false, length = 32)
//	public String getStorageFuncCode() {
//		return this.storageFuncCode;
//	}
//
//	public void setStorageFuncCode(String storageFuncCode) {
//		this.storageFuncCode = storageFuncCode;
//	}
//
//	@Column(name = "PROCESSOR_UNIT_NUM", precision = 18, scale = 0)
//	public Long getProcessorUnitNum() {
//		return this.processorUnitNum;
//	}
//
//	public void setProcessorUnitNum(Long processorUnitNum) {
//		this.processorUnitNum = processorUnitNum;
//	}
//
//	@Column(name = "CAPACTIY", precision = 18, scale = 0)
//	public Long getCapactiy() {
//		return this.capactiy;
//	}
//
//	public void setCapactiy(Long capactiy) {
//		this.capactiy = capactiy;
//	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getMicroCode() {
		return microCode;
	}

	public String getMgrIp() {
		return mgrIp;
	}

	public String getCacheCapacity() {
		return cacheCapacity;
	}

	public String getDiskCapacity() {
		return diskCapacity;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public String getDiskNum() {
		return diskNum;
	}

	public String getDiskRpm() {
		return diskRpm;
	}

	public String getPortCount() {
		return portCount;
	}

	public String getPortSpeed() {
		return portSpeed;
	}

	public String getAppType() {
		return appType;
	}

	public String getStorageChildPoolId() {
		return storageChildPoolId;
	}

	public String getStorageType() {
		return storageType;
	}

//	public String getStorageGroupNum() {
//		return storageGroupNum;
//	}

	public String getForeportProtocolTypeCode() {
		return foreportProtocolTypeCode;
	}

	public String getStorageFuncCode() {
		return storageFuncCode;
	}

	public String getProcessorUnitNum() {
		return processorUnitNum;
	}

	public String getCapactiy() {
		return capactiy;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setMicroCode(String microCode) {
		this.microCode = microCode;
	}

	public void setMgrIp(String mgrIp) {
		this.mgrIp = mgrIp;
	}

	public void setCacheCapacity(String cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}

	public void setDiskCapacity(String diskCapacity) {
		this.diskCapacity = diskCapacity;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public void setDiskNum(String diskNum) {
		this.diskNum = diskNum;
	}

	public void setDiskRpm(String diskRpm) {
		this.diskRpm = diskRpm;
	}

	public void setPortCount(String portCount) {
		this.portCount = portCount;
	}

	public void setPortSpeed(String portSpeed) {
		this.portSpeed = portSpeed;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public void setStorageChildPoolId(String storageChildPoolId) {
		this.storageChildPoolId = storageChildPoolId;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

//	public void setStorageGroupNum(String storageGroupNum) {
//		this.storageGroupNum = storageGroupNum;
//	}

	public void setForeportProtocolTypeCode(String foreportProtocolTypeCode) {
		this.foreportProtocolTypeCode = foreportProtocolTypeCode;
	}

	public void setStorageFuncCode(String storageFuncCode) {
		this.storageFuncCode = storageFuncCode;
	}

	public void setProcessorUnitNum(String processorUnitNum) {
		this.processorUnitNum = processorUnitNum;
	}

	public void setCapactiy(String capactiy) {
		this.capactiy = capactiy;
	}

	public String getFcportGroupNum() {
		return fcportGroupNum;
	}

	public void setFcportGroupNum(String fcportGroupNum) {
		this.fcportGroupNum = fcportGroupNum;
	}

}