package com.git.cloud.iaas.openstack.model;

public class VolumeRestModel {
	private String azName;
	private String volumeType;//系统卷,非系统卷
	private String diskSize;
	private String imageId;//系统卷时需要 
	private String isShare;//是否共享 字符串的,如："true" "false"
	private String name;
	private String id;
	private String status;
	private String projectId;
	private String tenantId;
	private String creatorId;
	private String targetImageId; //数据卷使用
	private String passthrough;//裸设备磁盘 ："true"，"false"
	
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAzName() {
		return azName;
	}
	public void setAzName(String azName) {
		this.azName = azName;
	}
	public String getVolumeType() {
		return volumeType;
	}
	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}
	public String getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getTargetImageId() {
		return targetImageId;
	}
	public void setTargetImageId(String targetImageId) {
		this.targetImageId = targetImageId;
	}
	
	public String getPassthrough() {
		return passthrough;
	}
	public void setPassthrough(String passthrough) {
		this.passthrough = passthrough;
	}
	@Override
	public String toString() {
		return "VolumeModel [azName=" + azName + ", volumeType=" + volumeType + ", diskSize=" + diskSize + ", imageId="
				+ imageId + ", isShare=" + isShare + ", name=" + name + ", id=" + id + ", status=" + status
				+ ", projectId=" + projectId + ", tenantId=" + tenantId + ", creatorId=" + creatorId + "]";
	}
	


}
