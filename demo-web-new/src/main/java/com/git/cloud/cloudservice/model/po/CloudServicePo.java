package com.git.cloud.cloudservice.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-17
 */
public class CloudServicePo extends BaseBO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	// Fields
	private String serviceId;
	private String imageId;
	private String serviceName;
	private String managerId;
	private String serviceStatus;
	private String serviceType;
	private String haType;
	private String platformType;
	private String platformTypeCode;
	private String isVm;
	private Integer vmBase;
	private String funcRemark;
	private String unfuncRemark;
	private String vmType;//虚机类型
	private String hostType;//主机类型
	private String isActive;
	private String systemType;
	private String storageDataType;
	private String rrinfoId;
	private String cloudType;
	private String cloudTypeCode;

	public String getPlatformTypeCode() {
		return platformTypeCode;
	}
	public void setPlatformTypeCode(String platformTypeCode) {
		this.platformTypeCode = platformTypeCode;
	}
	public String getCloudType() {
		return cloudType;
	}
	public void setCloudType(String cloudType) {
		this.cloudType = cloudType;
	}
	public String getCloudTypeCode() {
		return cloudTypeCode;
	}
	public void setCloudTypeCode(String cloudTypeCode) {
		this.cloudTypeCode = cloudTypeCode;
	}
	// Constructors
	public String getHostType() {
		return hostType;
	}
	public void setHostType(String hostType) {
		this.hostType = hostType;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getStorageDataType() {
		return storageDataType;
	}
	public void setStorageDataType(String storageDataType) {
		this.storageDataType = storageDataType;
	}
	/** default constructor */
	public CloudServicePo() {
		
	}	
	public CloudServicePo(String imageId, String serviceName, String managerId, String serviceStatus,
			String serviceType, String haType, String platformType, String isVm, Integer vmBase, String funcRemark,
			String unfuncRemark, String vmType, String hostType, String isActive, String systemType,
			String storageDataType, String rrinfoId, String cloudType, String cloudTypeCode) {
		super();
		this.imageId = imageId;
		this.serviceName = serviceName;
		this.managerId = managerId;
		this.serviceStatus = serviceStatus;
		this.serviceType = serviceType;
		this.haType = haType;
		this.platformType = platformType;
		this.isVm = isVm;
		this.vmBase = vmBase;
		this.funcRemark = funcRemark;
		this.unfuncRemark = unfuncRemark;
		this.vmType = vmType;
		this.hostType = hostType;
		this.isActive = isActive;
		this.systemType = systemType;
		this.storageDataType = storageDataType;
		this.rrinfoId = rrinfoId;
		this.cloudType = cloudType;
		this.cloudTypeCode = cloudTypeCode;
	}
	public String getServiceId() {
		return this.serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getServiceName() {
		return this.serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getManagerId() {
		return this.managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getServiceStatus() {
		return this.serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getServiceType() {
		return this.serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getHaType() {
		return this.haType;
	}
	public void setHaType(String haType) {
		this.haType = haType;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getIsVm() {
		return this.isVm;
	}
	public void setIsVm(String isVm) {
		this.isVm = isVm;
	}
	public Integer getVmBase() {
		return vmBase;
	}
	public void setVmBase(Integer vmBase) {
		this.vmBase = vmBase;
	}
	public String getFuncRemark() {
		return this.funcRemark;
	}
	public void setFuncRemark(String funcRemark) {
		this.funcRemark = funcRemark;
	}
	public String getUnfuncRemark() {
		return this.unfuncRemark;
	}
	public void setUnfuncRemark(String unfuncRemark) {
		this.unfuncRemark = unfuncRemark;
	}
	public String getIsActive() {
		return this.isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getVmType() {
		return vmType;
	}
	public void setVmType(String vmType) {
		this.vmType = vmType;
	}
	
	public String getRrinfoId() {
		return rrinfoId;
	}
	public void setRrinfoId(String rrinfoId) {
		this.rrinfoId = rrinfoId;
	}
	@Override
	public String getBizId() {
		return null;
	}
}