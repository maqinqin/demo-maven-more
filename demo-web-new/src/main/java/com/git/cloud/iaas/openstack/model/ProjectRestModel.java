package com.git.cloud.iaas.openstack.model;

public class ProjectRestModel {
	
	private String projectName;
	
	private String regionId;//6.3创建project需要字段

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

}
