package com.git.cloud.cloudservice.model.po;

import java.io.Serializable;
import java.util.List;
import com.git.cloud.common.model.base.BaseBO;

@SuppressWarnings("serial")
public class CloudImage extends BaseBO implements Serializable {

	private String imageId;
	
	private String imageName;
	
	private String imagePath;
	
	private String imageUrl;
	
	private String imageSize;
	
	private String diskCapacity;
	
	private String remark;
	
	private String manager;
	
	private String password;
	
	private String isActive;
	
	private String manageType;
	
	private String imageType;
	
	private String manageTypeName;
	/**
	 * 判断是否为同步取到的镜像
	 */
	private String isSync;
	private String iaasUuid;

	/**
	 * 是否输入管理员用户信息
	 */
	private String isAdminUserEntered;
	
	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getManageTypeName() {
		return manageTypeName;
	}

	public void setManageTypeName(String manageTypeName) {
		this.manageTypeName = manageTypeName;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;
	

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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CloudImage(String imageId, String imageName, String imagePath,
			String imageUrl, String imageSize, String diskCapacity, String remark, String manager,
			String password, String isActive,String manageType,
			List<CloudImageSoftWareRef> cloudImageSoftWareRefs) {
		super();
		this.imageId = imageId;
		this.imageName = imageName;
		this.imagePath = imagePath;
		this.imageUrl = imageUrl;
		this.imageSize = imageSize;
		this.diskCapacity = diskCapacity;
		this.remark = remark;
		this.manager = manager;
		this.password = password;
		this.isActive = isActive;
		this.manageType = manageType;
		this.cloudImageSoftWareRefs = cloudImageSoftWareRefs;
	}

	public CloudImage() {
		super();
	}

	@Override
	public String getBizId() {
		return null;
	}

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	public String getIsAdminUserEntered() {
		return isAdminUserEntered;
	}

	public void setIsAdminUserEntered(String isAdminUserEntered) {
		this.isAdminUserEntered = isAdminUserEntered;
	}

	public String getIaasUuid() {
		return iaasUuid;
	}

	public void setIaasUuid(String iaasUuid) {
		this.iaasUuid = iaasUuid;
	}
	
}