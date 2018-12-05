package com.git.cloud.tenant.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class TenantResPoolPo extends BaseBO{

	private String id;
	private String tenantId;
	private String resPoolId;
	private String resPoolName;
	private String selectFlag;
	
	
	public String getSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(String selectFlag) {
		this.selectFlag = selectFlag;
	}
	public String getResPoolName() {
		return resPoolName;
	}
	public void setResPoolName(String resPoolName) {
		this.resPoolName = resPoolName;
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
	public String getResPoolId() {
		return resPoolId;
	}
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
