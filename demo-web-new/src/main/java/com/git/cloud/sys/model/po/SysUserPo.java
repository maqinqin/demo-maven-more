package com.git.cloud.sys.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class SysUserPo   extends BaseBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SysUserPo(){}
	
	private String userId;
	private String orgId;
	private String firstName;
	private String lastName;
	private String loginName;
	private String loginPassword;
	private String email;
	private String phone;
	private String ipAddress;
	private String lastLogin;
	private String userType;
	private String isActive;
	private String roleId;
	private Boolean isManager;// 是否是超级管理员标识
	private String platUser;// 是否是超级管理员标识
	public String getPlatUser() {
		return platUser;
	}
	public void setPlatUser(String platUser) {
		this.platUser = platUser;
	}
	public String toString(){
		return firstName+lastName;
	}
	public SysUserPo(String userId, String orgId, String firstName, String lastName, String loginName,
			String loginPassword, String email, String phone, String ipAddress, String lastLogin, String userType,
			String isActive, String roleId, Boolean isManager, String platUser) {
		super();
		this.userId = userId;
		this.orgId = orgId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.email = email;
		this.phone = phone;
		this.ipAddress = ipAddress;
		this.lastLogin = lastLogin;
		this.userType = userType;
		this.isActive = isActive;
		this.roleId = roleId;
		this.isManager = isManager;
		this.platUser = platUser;
	}
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getLoginPassword() {
		return loginPassword;
	}


	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Boolean getIsManager() {
		return isManager;
	}
	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
