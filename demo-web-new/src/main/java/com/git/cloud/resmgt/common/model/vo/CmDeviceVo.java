package com.git.cloud.resmgt.common.model.vo;


import com.git.cloud.common.model.base.BaseBO;
/**
 * 
 * @author 王成辉
 * @Description 设备表
 * @date 2014-12-17
 *
 */
public class CmDeviceVo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6761667612666827884L;
	private String id;
	private String seatId;
	//private String deviceType;
	private String deviceName;
	private String sn;
	private String deviceModelId;
	//private String manufacturer;
	private String deviceIp;
	private String resPoolId;
	private String isActive;
	private String description;
	private String deviceStatus;
	private String vmCountByClusterDu;
	private String vmCountByDuId;
	private String countByDeviceName;
	private String clusterId;
	private String duId;
	private String vmCpu;
	private String vmMem;
	private String vmDisk;
	private String localDiskName;//本地磁盘名称
	private String datastoreType;
	
	// Constructors

	public String getDatastoreType() {
		return datastoreType;
	}

	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}

	/** default constructor */
	public CmDeviceVo() {
	}

	/** minimal constructor */
	public CmDeviceVo(String id) {
		this.id = id;
	}

	/** full constructor */
	public CmDeviceVo(String id, String seatId, String deviceType,
			String deviceName, String sn, String deviceModelId,
			String manufacturer, String resPoolId, String isActive,
			String description,String deviceStatus,String vmCountByClusterDu,
			String vmCountByDuId,String countByDeviceName,String clusterId,String duId,String isBare,String isInvc) {
		this.id = id;
		this.seatId = seatId;
		//this.deviceType = deviceType;
		this.deviceName = deviceName;
		this.sn = sn;
		this.deviceModelId = deviceModelId;
		//this.manufacturer = manufacturer;
		this.resPoolId = resPoolId;
		this.isActive = isActive;
		this.description = description;
		this.deviceStatus = deviceStatus;
		this.vmCountByClusterDu=vmCountByClusterDu;
		this.vmCountByDuId=vmCountByDuId;
		this.countByDeviceName=countByDeviceName;
		this.duId=duId;
		this.clusterId=clusterId;
	}

	// Property accessors

	public String getId() {
		return this.id;
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
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getResPoolId() {
		return this.resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getVmCountByClusterDu() {
		return vmCountByClusterDu;
	}

	public void setVmCountByClusterDu(String vmCountByClusterDu) {
		this.vmCountByClusterDu = vmCountByClusterDu;
	}

	public String getVmCountByDuId() {
		return vmCountByDuId;
	}

	public void setVmCountByDuId(String vmCountByDuId) {
		this.vmCountByDuId = vmCountByDuId;
	}

	public String getCountByDeviceName() {
		return countByDeviceName;
	}

	public void setCountByDeviceName(String countByDeviceName) {
		this.countByDeviceName = countByDeviceName;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getDuId() {
		return duId;
	}

	public void setDuId(String duId) {
		this.duId = duId;
	}

	public String getVmCpu() {
		return vmCpu;
	}

	public void setVmCpu(String vmCpu) {
		this.vmCpu = vmCpu;
	}

	public String getVmMem() {
		return vmMem;
	}

	public void setVmMem(String vmMem) {
		this.vmMem = vmMem;
	}

	public String getVmDisk() {
		return vmDisk;
	}

	public void setVmDisk(String vmDisk) {
		this.vmDisk = vmDisk;
	}

	public String getLocalDiskName() {
		return localDiskName;
	}

	public void setLocalDiskName(String localDiskName) {
		this.localDiskName = localDiskName;
	}


}
