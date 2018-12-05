package com.git.cloud.resmgt.storage.model.vo;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:StorageDeviceVo
 * @Description: 存储设备VO对象
 * @author mijia
 */
public class StorageDeviceVo extends BaseBO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/*CM_Device*/
	private String id;
	private String seatCode;
	private String seatId;
	private String seatName;
	//private String deviceType;//9.24日删除
	private String deviceName;
	private String sn;
	private String deviceModelId;//关联CM_DEVICE_MODEL表
	//private String deviceModel;//9.24日删除
	//private String manufacturer;//9.24日删除
	private String resPoolId;
	private String isActive;
	private String description;
	private String createUser;
	private Date createDateTime;
	private String updateUser;
	private Date updateDateTime;
	private String deviceStatus;
	/*CM_STORAGE*/
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
	private String storageType;
	private String storageChildPoolId;
	private String storageChildPoolName;//2015-12-16 wmy加
	private String poolName;//2015-12-16 wmy加
	private String flag;//标识datastore是否被选择，1：选择，0：未被选择
	private String defaultDataStore;//
	/*CM_DATASTORE*/
	private String name;
	private int orderNum;
	private String path;
	private String storageId;
	/*CM_DEVICE_MODEL*/
	private String deviceModel;
	private String deviceType;
	private String deviceManufacturer;
	private String remark;
	
	public String getDefaultDataStore() {
		return defaultDataStore;
	}
	public void setDefaultDataStore(String defaultDataStore) {
		this.defaultDataStore = defaultDataStore;
	}
	public String getStorageChildPoolName() {
		return storageChildPoolName;
	}
	public void setStorageChildPoolName(String storageChildPoolName) {
		this.storageChildPoolName = storageChildPoolName;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getSeatName() {
		return seatName;
	}
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeviceModelId() {
		return deviceModelId;
	}
	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}
	public String getStorageChildPoolId() {
		return storageChildPoolId;
	}
	public void setStorageChildPoolId(String storageChildPoolId) {
		this.storageChildPoolId = storageChildPoolId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeatCode() {
		return seatCode;
	}
	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
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
	public String getResPoolId() {
		return resPoolId;
	}
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCacheCapacity() {
		return cacheCapacity;
	}
	public void setCacheCapacity(int cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}
	public int getDiskCapacity() {
		return diskCapacity;
	}
	public void setDiskCapacity(int diskCapacity) {
		this.diskCapacity = diskCapacity;
	}
	public int getDiskNumber() {
		return diskNumber;
	}
	public void setDiskNumber(int diskNumber) {
		this.diskNumber = diskNumber;
	}
	public int getDiskRpm() {
		return diskRpm;
	}
	public void setDiskRpm(int diskRpm) {
		this.diskRpm = diskRpm;
	}
	public int getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(int diskSize) {
		this.diskSize = diskSize;
	}
	public String getMgrIp() {
		return mgrIp;
	}
	public void setMgrIp(String mgrIp) {
		this.mgrIp = mgrIp;
	}
	public String getMicroCode() {
		return microCode;
	}
	public void setMicroCode(String microCode) {
		this.microCode = microCode;
	}
	public int getPortCount() {
		return portCount;
	}
	public void setPortCount(int portCount) {
		this.portCount = portCount;
	}
	public int getPortSpeed() {
		return portSpeed;
	}
	public void setPortSpeed(int portSpeed) {
		this.portSpeed = portSpeed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStorageId() {
		return storageId;
	}
	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
