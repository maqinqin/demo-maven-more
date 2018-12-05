package com.git.cloud.cloudservice.model.vo;

import java.io.Serializable;
import java.util.List;
import com.git.cloud.cloudservice.model.po.CloudImageSoftWareRef;

@SuppressWarnings("serial")
public class CloudImageVo implements Serializable {

	private String imageId;
	
	private String imageName;
	
	private String imagePath;
	
	private String imageSize;
	
	private String diskCapacity;
	
	private String manager;
	
	private String password;
	
	private String isActive;
	
	private String synchStatus;

	

	public String getSynchStatus() {
		return synchStatus;
	}

	public void setSynchStatus(String synchStatus) {
		this.synchStatus = synchStatus;
	}

	private List<CloudImageSoftWareRef> cloudImageSoftWareRefs;
	
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDiskCapacity() {
		return diskCapacity;
	}

	public void setDiskCapacity(String diskCapacity) {
		this.diskCapacity = diskCapacity;
	}

	public List<CloudImageSoftWareRef> getCloudImageSoftWareRefs() {
		return cloudImageSoftWareRefs;
	}

	public void setCloudImageSoftWareRefs(
			List<CloudImageSoftWareRef> cloudImageSoftWareRefs) {
		this.cloudImageSoftWareRefs = cloudImageSoftWareRefs;
	}

	public CloudImageVo(String imageId, String imageName, String imagePath,
			String imageSize, String diskCapacity, String manager, String password, String isActive,
			List<CloudImageSoftWareRef> cloudImageSoftWareRefs) {
		super();
		this.imageId = imageId;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.imageSize = imageSize;
		this.diskCapacity = diskCapacity;
		this.manager = manager;
		this.password = password;
		this.isActive = isActive;
		this.cloudImageSoftWareRefs = cloudImageSoftWareRefs;
	}

	public CloudImageVo() {
		super();
	}
}