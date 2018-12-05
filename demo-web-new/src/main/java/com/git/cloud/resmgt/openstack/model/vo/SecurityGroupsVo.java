package com.git.cloud.resmgt.openstack.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class SecurityGroupsVo extends BaseBO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String projectId;
	private String securityGroupsName;
	private String remark;
	private String isActive;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getSecurityGroupsName() {
		return securityGroupsName;
	}
	public void setSecurityGroupsName(String securityGroupsName) {
		this.securityGroupsName = securityGroupsName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
