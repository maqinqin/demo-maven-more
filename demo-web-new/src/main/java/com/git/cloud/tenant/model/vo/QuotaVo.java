package com.git.cloud.tenant.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class QuotaVo extends BaseBO{
	
	 private String id;
	 private String projectId;
	 private String projectName;
	 private String cloudQuotaConfigId;
	 private String val;
	 private String datacenterId;
	 private String platformTypeCode; 
	 private String name; 
	 private String code;
	 private String orderNum;
	 private String unit;//单位
	 private String tenantId;
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
	public String getCloudQuotaConfigId() {
		return cloudQuotaConfigId;
	}
	public void setCloudQuotaConfigId(String cloudQuotaConfigId) {
		this.cloudQuotaConfigId = cloudQuotaConfigId;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}
	public String getPlatformTypeCode() {
		return platformTypeCode;
	}
	public void setPlatformTypeCode(String platformTypeCode) {
		this.platformTypeCode = platformTypeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Override
	public String toString() {
		return "QuotaVo [id=" + id + ", projectId=" + projectId + ", cloudQuotaConfigId=" + cloudQuotaConfigId
				+ ", val=" + val + ", datacenterId=" + datacenterId + ", platformTypeCode=" + platformTypeCode
				+ ", name=" + name + ", code=" + code + ", orderNum=" + orderNum + ", unit=" + unit + ", tenantId="
				+ tenantId + ", projectName=" + projectName+ "]";
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	 
}
