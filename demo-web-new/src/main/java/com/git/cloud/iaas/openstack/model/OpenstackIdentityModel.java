package com.git.cloud.iaas.openstack.model;

public class OpenstackIdentityModel {
	
	// 虚机管理服务器ID
	private String vmMsId;
		
	private String openstackIp;
	
	private String manageOneIp;//字段配置在虚机管理服务器中
	
	private String domainName;
	
	private String version;//目前有两种，2.0.6和6.3，字段配置在虚机管理服务器中
	
	private String token;
	
	private String projectId;
	
	private String projectName;
	

	public String getVmMsId() {
		return vmMsId;
	}

	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}

	public String getManageOneIp() {
		return manageOneIp;
	}

	public void setManageOneIp(String manageOneIp) {
		this.manageOneIp = manageOneIp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOpenstackIp() {
		return openstackIp;
	}

	public void setOpenstackIp(String openstackIp) {
		this.openstackIp = openstackIp;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "OpenstackRestCommonModel [openstackIp=" + openstackIp + ", domainName=" + domainName + ", version="
				+ version + ", token=" + token + ", projectId=" + projectId + ", projectName=" + projectName + "]";
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	

}
