package com.git.cloud.iaas.openstack.model;

/**
 * Token所需属性对象
 * @author SunHailong
 * @version v1.0 2017-3-20
 */
public class TokenModel {
	private String openstackIp;
	private String fsUserName;
	private String fsPassword;
	private String projectName;
	private String projectId;
	private String userDomain;
	private String projectDomain;
	
	private String scopeInfo;//6.3认证用
	
	private String domainName;
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getScopeInfo() {
		return scopeInfo;
	}
	public void setScopeInfo(String scopeInfo) {
		this.scopeInfo = scopeInfo;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getOpenstackIp() {
		return openstackIp;
	}
	public void setOpenstackIp(String openstackIp) {
		this.openstackIp = openstackIp;
	}
	public String getFsUserName() {
		return fsUserName;
	}
	public void setFsUserName(String fsUserName) {
		this.fsUserName = fsUserName;
	}
	public String getFsPassword() {
		return fsPassword;
	}
	public void setFsPassword(String fsPassword) {
		this.fsPassword = fsPassword;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUserDomain() {
		return userDomain;
	}
	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}
	public String getProjectDomain() {
		return projectDomain;
	}
	public void setProjectDomain(String projectDomain) {
		this.projectDomain = projectDomain;
	}
	@Override
	public String toString() {
		return "TokenModel [openstackIp=" + openstackIp + ", fsUserName=" + fsUserName + ", fsPassword=" + fsPassword
				+ ", projectName=" + projectName + ", userDomain=" + userDomain + ", projectDomain=" + projectDomain
				+ "]";
	}
	
	
}
