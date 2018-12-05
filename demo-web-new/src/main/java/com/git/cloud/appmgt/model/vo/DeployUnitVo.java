package com.git.cloud.appmgt.model.vo;

import java.util.Date;

/**
 * 服务器角色VO
 * @version v1.0
 */
public class DeployUnitVo {
	
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
	private Date createDatetime;
	private String createUser;
	private Date updateDatetime;
	private String updateUser;
	private String isActive;
	private String oldAppId;
	private String datacenterName;
	private String appCname;
	private String serviceTypeName;
	private String secureAreaName;
	private String sevureTierName;
	private String statusName;
	private String serviceId;
	private String rrinfoId;
	private String appEname;
	public String getAppEname() {
		return appEname;
	}
	public void setAppEname(String appEname) {
		this.appEname = appEname;
	}
	public DeployUnitVo() {
		super();
	}
	public DeployUnitVo(String duId, String datacenterId, String appId,
			String cname, String fullCname, String ename, String fullEname,
			String serviceTypeCode, String status, String secureAreaCode,
			String sevureTierCode, String remark, Date createDatetime,
			String createUser, Date updateDatetime, String updateUser,
			String isActive, String oldAppId, String datacenterName,
			String appCname, String serviceTypeName, String secureAreaName,
			String sevureTierName, String statusName, String serviceId,String rrinfoId) {
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
		this.createDatetime = createDatetime;
		this.createUser = createUser;
		this.updateDatetime = updateDatetime;
		this.updateUser = updateUser;
		this.isActive = isActive;
		this.oldAppId = oldAppId;
		this.datacenterName = datacenterName;
		this.appCname = appCname;
		this.serviceTypeName = serviceTypeName;
		this.secureAreaName = secureAreaName;
		this.sevureTierName = sevureTierName;
		this.statusName = statusName;
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
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
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
	public String getOldAppId() {
		return oldAppId;
	}
	public void setOldAppId(String oldAppId) {
		this.oldAppId = oldAppId;
	}
	public String getDatacenterName() {
		return datacenterName;
	}
	public void setDatacenterName(String datacenterName) {
		this.datacenterName = datacenterName;
	}
	public String getAppCname() {
		return appCname;
	}
	public void setAppCname(String appCname) {
		this.appCname = appCname;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getSecureAreaName() {
		return secureAreaName;
	}
	public void setSecureAreaName(String secureAreaName) {
		this.secureAreaName = secureAreaName;
	}
	public String getSevureTierName() {
		return sevureTierName;
	}
	public void setSevureTierName(String sevureTierName) {
		this.sevureTierName = sevureTierName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getRrinfoId() {
		return rrinfoId;
	}
	public void setRrinfoId(String rrinfoId) {
		this.rrinfoId = rrinfoId;
	}
}