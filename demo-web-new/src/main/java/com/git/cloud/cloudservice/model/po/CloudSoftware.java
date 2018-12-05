package com.git.cloud.cloudservice.model.po;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CloudSoftware entity. @author MyEclipse Persistence Tools
 */

public class CloudSoftware extends BaseBO implements java.io.Serializable {

	// Fields

	private String softwareId;
	private String softwareName;
	private String softwareType;
	private String softwarePath;
	private String remark;
	private String isActive;
	private List<CloudSoftwareVer> cloudSoftwareVers;

	// Constructors

	/** default constructor */
	public CloudSoftware() {
	}

	/** minimal constructor */
	public CloudSoftware(String softwareId) {
		this.softwareId = softwareId;
	}

	/** full constructor */
	

	// Property accessors

	public String getSoftwareId() {
		return this.softwareId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public CloudSoftware(String softwareId, String softwareName,
			String softwareType, String softwarePath, String remark,
			String isActive, List<CloudSoftwareVer> cloudSoftwareVers) {
		super();
		this.softwareId = softwareId;
		this.softwareName = softwareName;
		this.softwareType = softwareType;
		this.softwarePath = softwarePath;
		this.remark = remark;
		this.isActive = isActive;
		this.cloudSoftwareVers = cloudSoftwareVers;
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

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}