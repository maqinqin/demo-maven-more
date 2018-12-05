package com.git.cloud.tenant.model.po;



import com.git.cloud.common.model.base.BaseBO;

public class TenantUsersPo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2667040728709093476L;
	private String id;
	private String tenantId;
	private String userId;
	private String firstName;
	private String lastName;
	private String loginName;
	private String roleName;//0 用户管理员；1普通用户
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public TenantUsersPo() {
		super();		
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
