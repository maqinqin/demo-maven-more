package com.git.cloud.cloudservice.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class CloudImageSoftWareRef extends BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String imageSoftwareId;
	
	private String imageId;
	
	private String softWareVerId;
	
	private String isActive;
	
	
	public CloudImageSoftWareRef() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public CloudImageSoftWareRef(String imageSoftwareId, String imageId,
			String softWareVerId, String isActive) {
		super();
		this.imageSoftwareId = imageSoftwareId;
		this.imageId = imageId;
		this.softWareVerId = softWareVerId;
		this.isActive = isActive;
	}


	public String getImageSoftwareId() {
		return imageSoftwareId;
	}

	public void setImageSoftwareId(String imageSoftwareId) {
		this.imageSoftwareId = imageSoftwareId;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getSoftWareVerId() {
		return softWareVerId;
	}

	public void setSoftWareVerId(String softWareVerId) {
		this.softWareVerId = softWareVerId;
	}

	
	public CloudImageSoftWareRef(String imageId) {
		this.imageId = imageId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
