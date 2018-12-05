package com.git.cloud.cloudservice.model.vo;

public class ImageSynchInstanceVo {
	
	private String instanceId;
	private String instanceName;
	private String vmMsId;
	private String vmMsName;
	private String imageId;
	private String serverName;
	private String imageName;
	private String synchStatus;
	public String getSynchStatus() {
		return synchStatus;
	}
	public void setSynchStatus(String synchStatus) {
		this.synchStatus = synchStatus;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getVmMsId() {
		return vmMsId;
	}
	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}
	public String getVmMsName() {
		return vmMsName;
	}
	public void setVmMsName(String vmMsName) {
		this.vmMsName = vmMsName;
	}

}
