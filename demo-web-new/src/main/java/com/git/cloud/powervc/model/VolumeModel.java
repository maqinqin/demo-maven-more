package com.git.cloud.powervc.model;
/** 
 * 类说明 
 * @author SunHailong 
 * @version 版本号 2017-4-1
 */
public class VolumeModel {
	private String azName;
	private String volumeType;
	private String diskSize;
	private String imageId;
	private String isShare;
	private String name;
	private String id;
	private String status;
	
	
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
}
