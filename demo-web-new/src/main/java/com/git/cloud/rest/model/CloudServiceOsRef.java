package com.git.cloud.rest.model;

import com.git.cloud.common.model.base.BaseBO;

public class CloudServiceOsRef extends BaseBO{
	
	private String id;
	private String osType;
	private String softWareType;
	private String cloudServiceId;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getSoftWareType() {
		return softWareType;
	}
	public void setSoftWareType(String softWareType) {
		this.softWareType = softWareType;
	}
	public String getCloudServiceId() {
		return cloudServiceId;
	}
	public void setCloudServiceId(String cloudServiceId) {
		this.cloudServiceId = cloudServiceId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
