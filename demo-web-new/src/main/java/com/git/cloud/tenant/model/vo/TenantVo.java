package com.git.cloud.tenant.model.vo;

import java.util.Date;

public class TenantVo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5572454751022278844L;
	
	private String id;
	private String name;
	private String createUser;
	private Date createTime;
	private String remark;
	private String quotaLimit;
	
	public String getQuotaLimit() {
		return quotaLimit;
	}
	public void setQuotaLimit(String quotaLimit) {
		this.quotaLimit = quotaLimit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TenantVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
	

}
