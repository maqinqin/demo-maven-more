package com.git.cloud.cloudservice.model.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * CloudSoftwareVer entity. @author MyEclipse Persistence Tools
 */

public class CloudSoftwareVerVo implements java.io.Serializable {

	// Fields

	private String softwareVerId;
	private String softwareId;
	private String verName;
	private String isActive;
	private Set cloudImageSoftwareRefs = new HashSet(0);

	// Constructors

	/** default constructor */
	public CloudSoftwareVerVo() {
	}

	/** minimal constructor */
	public CloudSoftwareVerVo(String softwareVerId, String softwareId) {
		this.softwareVerId = softwareVerId;
		this.softwareId = softwareId;
	}

	/** full constructor */
	public CloudSoftwareVerVo(String softwareVerId, String softwareId,
			String verName, String isActive, Set cloudImageSoftwareRefs) {
		this.softwareVerId = softwareVerId;
		this.softwareId = softwareId;
		this.verName = verName;
		this.isActive = isActive;
		this.cloudImageSoftwareRefs = cloudImageSoftwareRefs;
	}

	// Property accessors

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

}