package com.git.cloud.cloudservice.model.po;

import java.util.HashSet;
import java.util.Set;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CloudSoftwareVer entity. @author MyEclipse Persistence Tools
 */

public class CloudSoftwareVer extends BaseBO implements java.io.Serializable {

	// Fields

	private String softwareVerId;
	private String softwareId;
	private String verName;
	private String isActive;
	private String remark;
	private Set cloudImageSoftwareRefs = new HashSet(0);

	// Constructors

	/** default constructor */
	public CloudSoftwareVer() {
	}

	/** minimal constructor */
	public CloudSoftwareVer(String softwareVerId, String softwareId) {
		this.softwareVerId = softwareVerId;
		this.softwareId = softwareId;
	}

	/** full constructor */


	// Property accessors

	public String getRemark() {
		return remark;
	}

	public CloudSoftwareVer(String softwareVerId, String softwareId,
			String verName, String isActive, String remark,
			Set cloudImageSoftwareRefs) {
		super();
		this.softwareVerId = softwareVerId;
		this.softwareId = softwareId;
		this.verName = verName;
		this.isActive = isActive;
		this.remark = remark;
		this.cloudImageSoftwareRefs = cloudImageSoftwareRefs;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSoftwareVerId() {
		return this.softwareVerId;
	}

	public void setSoftwareVerId(String softwareVerId) {
		this.softwareVerId = softwareVerId;
	}

	public String getSoftwareId() {
		return this.softwareId;
	}

	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	public String getVerName() {
		return this.verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Set getCloudImageSoftwareRefs() {
		return this.cloudImageSoftwareRefs;
	}

	public void setCloudImageSoftwareRefs(Set cloudImageSoftwareRefs) {
		this.cloudImageSoftwareRefs = cloudImageSoftwareRefs;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}