package com.git.cloud.tenant.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class TenantPo extends BaseBO implements java.io.Serializable{

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
	private String balance;		//账户余额
	private String totalInvoice;//可开发票总额
	
	public String getTotalInvoice() {
		return totalInvoice;
	}
	public void setTotalInvoice(String totalInvoice) {
		this.totalInvoice = totalInvoice;
	}
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
	public TenantPo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
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
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
		
	

}
