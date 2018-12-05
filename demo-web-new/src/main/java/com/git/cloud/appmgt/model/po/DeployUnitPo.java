package com.git.cloud.appmgt.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 服务器角色表PO
 * @author liangshuang
 * @date 2014-9-17 下午4:15:11
 * @version v1.0
 *
 */
public class DeployUnitPo extends BaseBO{
	
	private static final long serialVersionUID = -7060299614947779818L;

	private String duId;
	private String datacenterId;
	private String appId;
	private String cname;
	private String fullCname;
	private String ename;
	private String fullEname;
	private String serviceTypeCode;
	private String status;
	private String secureAreaCode;
	private String sevureTierCode;
	private String remark;
	private Date createDateTime;
	private String createUser;
	private Date updateDateTime;
	private String updateUser;
	private String isActive;
	private String serviceId;
	private String rrinfoId;
	private boolean updateServiceFlag;//清空字段时标识
  
	public DeployUnitPo() {
		super();
	}
	
	public DeployUnitPo(String duId, String datacenterId, String appId,
			String cname, String fullCname, String ename, String fullEname,
			String serviceTypeCode, String status, String secureAreaCode,
			String sevureTierCode, String remark, Date createDateTime,
			String createUser, Date updateDateTime, String updateUser,
			String isActive, String serviceId,String rrinfoId) {
		super();
		this.duId = duId;
		this.datacenterId = datacenterId;
		this.appId = appId;
		this.cname = cname;
		this.fullCname = fullCname;
		this.ename = ename;
		this.fullEname = fullEname;
		this.serviceTypeCode = serviceTypeCode;
		this.status = status;
		this.secureAreaCode = secureAreaCode;
		this.sevureTierCode = sevureTierCode;
		this.remark = remark;
		this.createDateTime = createDateTime;
		this.createUser = createUser;
		this.updateDateTime = updateDateTime;
		this.updateUser = updateUser;
		this.isActive = isActive;
		this.serviceId = serviceId;
		this.rrinfoId = rrinfoId;
	}

	public String getDuId() {
		return duId;
	}
	public void setDuId(String duId) {
		this.duId = duId;
	}
	public String getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getFullCname() {
		return fullCname;
	}
	public void setFullCname(String fullCname) {
		this.fullCname = fullCname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getFullEname() {
		return fullEname;
	}
	public void setFullEname(String fullEname) {
		this.fullEname = fullEname;
	}
	public String getServiceTypeCode() {
		return serviceTypeCode;
	}
	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSecureAreaCode() {
		return secureAreaCode;
	}
	public void setSecureAreaCode(String secureAreaCode) {
		this.secureAreaCode = secureAreaCode;
	}
	public String getSevureTierCode() {
		return sevureTierCode;
	}
	public void setSevureTierCode(String sevureTierCode) {
		this.sevureTierCode = sevureTierCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public boolean isUpdateServiceFlag() {
		return updateServiceFlag;
	}
	public void setUpdateServiceFlag(boolean updateServiceFlag) {
		this.updateServiceFlag = updateServiceFlag;
	}
	public String getRrinfoId() {
		return rrinfoId;
	}
	public void setRrinfoId(String rrinfoId) {
		this.rrinfoId = rrinfoId;
	}

	@Override
	public String getBizId() {
		return null;
	}
	}