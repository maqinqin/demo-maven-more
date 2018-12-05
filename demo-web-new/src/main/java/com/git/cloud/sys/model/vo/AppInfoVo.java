package com.git.cloud.sys.model.vo;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * AppInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class AppInfoVo extends BaseBO implements java.io.Serializable {

	// Fields
	private String appId;
	private String fatherId;
	private String cname;
	private String fullCname;
	private String ename;
	private String fullEname;
	private String manager;
	private String managerName; // 系统管理员的名称;
	private String sysTypeCode;
	private String sysTypeName; // 分类名称;
	private String sysLevelCode;
	private String remark;
	private String isActive;
	private String status;
	private String createUser;
	private Date createDateTime;
	private String updateUser;
	private Date updateDateTime;
    
	private String appInfoDevices; // 应用关联的设备;

	// Constructors

	/** default constructor */
	public AppInfoVo() {
	}

	/** minimal constructor */
	public AppInfoVo(String cname, String fullCname, String ename,
			String sysTypeCode, String sysLevelCode, String isActive) {
		this.cname = cname;
		this.fullCname = fullCname;
		this.ename = ename;
		this.sysTypeCode = sysTypeCode;
		this.sysLevelCode = sysLevelCode;
		this.isActive = isActive;
	}

	/** full constructor */
	public AppInfoVo(String fatherId, String cname, String fullCname,
			String ename, String fullEname, String manager, String sysTypeCode,
			String sysLevelCode, String remark, String isActive,
			String createUser, Date createDateTime, String updateUser,
			Date updateDateTime) {
		this.fatherId = fatherId;
		this.cname = cname;
		this.fullCname = fullCname;
		this.ename = ename;
		this.fullEname = fullEname;
		this.manager = manager;
		this.sysTypeCode = sysTypeCode;
		this.sysLevelCode = sysLevelCode;
		this.remark = remark;
		this.isActive = isActive;
		this.createUser = createUser;
		this.createDateTime = createDateTime;
		this.updateUser = updateUser;
		this.updateDateTime = updateDateTime;
	}

	// Property accessors

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getFullCname() {
		return this.fullCname;
	}

	public void setFullCname(String fullCname) {
		this.fullCname = fullCname;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getFullEname() {
		return this.fullEname;
	}

	public void setFullEname(String fullEname) {
		this.fullEname = fullEname;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getSysTypeCode() {
		return this.sysTypeCode;
	}

	public void setSysTypeCode(String sysTypeCode) {
		this.sysTypeCode = sysTypeCode;
	}

	public String getSysLevelCode() {
		return this.sysLevelCode;
	}

	public void setSysLevelCode(String sysLevelCode) {
		this.sysLevelCode = sysLevelCode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDateTime() {
		return this.createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getUpdateUser() {
		return this.updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateDateTime() {
		return this.updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getSysTypeName() {
		return sysTypeName;
	}
	public void setSysTypeName(String sysTypeName) {
		this.sysTypeName = sysTypeName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getAppInfoDevices() {
		return appInfoDevices;
	}
	public void setAppInfoDevices(String appInfoDevices) {
		this.appInfoDevices = appInfoDevices;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}