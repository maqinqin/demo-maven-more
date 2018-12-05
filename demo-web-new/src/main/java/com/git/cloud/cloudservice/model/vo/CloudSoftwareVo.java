package com.git.cloud.cloudservice.model.vo;

import java.util.List;

import com.git.cloud.cloudservice.model.po.CloudSoftwareVer;

/**
 * CloudSoftware entity. @author MyEclipse Persistence Tools
 */

public class CloudSoftwareVo implements java.io.Serializable {

	// Fields

	private String softwareId;
	private String softwareName;
	private String softwareType;
	private String softwarePath;
	private String isActive;
	private String softwareTypeName;
	private String remark;
	private List<CloudSoftwareVer> cloudSoftwareVers;

	// Constructors

	/** default constructor */
	public CloudSoftwareVo() {
	}

	/** minimal constructor */
	public CloudSoftwareVo(String softwareId) {
		this.softwareId = softwareId;
	}

	/** full constructor */
	

	// Property accessors

	public String getSoftwareId() {
		return this.softwareId;
	}

	public CloudSoftwareVo(String softwareId, String softwareName,
			String softwareType, String softwarePath, String isActive,
			List<CloudSoftwareVer> cloudSoftwareVers) {
		super();
		this.softwareId = softwareId;
		this.softwareName = softwareName;
		this.softwareType = softwareType;
		this.softwarePath = softwarePath;
		this.isActive = isActive;
		this.cloudSoftwareVers = cloudSoftwareVers;
	}

	/**
	 * @return the softwareTypeName
	 */
	public String getSoftwareTypeName() {
		return softwareTypeName;
	}

	/**
	 * @param softwareTypeName the softwareTypeName to set
	 */
	public void setSoftwareTypeName(String softwareTypeName) {
		this.softwareTypeName = softwareTypeName;
	}

	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	public String getSoftwareName() {
		return this.softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getSoftwareType() {
		return this.softwareType;
	}

	public void setSoftwareType(String softwareType) {
		this.softwareType = softwareType;
	}

	public String getSoftwarePath() {
		return this.softwarePath;
	}

	public void setSoftwarePath(String softwarePath) {
		this.softwarePath = softwarePath;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<CloudSoftwareVer> getCloudSoftwareVers() {
		return cloudSoftwareVers;
	}

	public void setCloudSoftwareVers(List<CloudSoftwareVer> cloudSoftwareVers) {
		this.cloudSoftwareVers = cloudSoftwareVers;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}