/**
 * 应用系统表的VO模型
 * @author qiupj
 * @version v1.0 2014-09-18
 */
package com.git.cloud.appmgt.model.vo;

import java.sql.Date;


public class AppSysVo {
	
	private String appId;
	private String fatherId;
	private String cname;
	private String fullCname;
	private String ename;
	private String fullEname;
	private String manager;
	private String sysTypeCode;
	private String sysLevelCode;
	private String remark;
	private Date createDateTime;
	private String createUser;
	private Date updateDateTime;
	private String updateUser;
	private String isActive;
	//业务需要，新增字段
	private String fatherCname;//父节点名称
	private String isSubAppSys;//是否有所属子系统 0-无 1-有
	private String isAppDu;//是否有所属服务器角色 0-无 1-有
	private String isBeUse;//是否被其他模块使用 0-无 1-有
	
	public AppSysVo() {
		super();
	}

	public AppSysVo(String appId, String fatherId, String cname,
			String fullCname, String ename, String fullEname, String manager,
			String sysTypeCode, String sysLevelCode, String remark,
			Date createDateTime, String createUser, Date updateDateTime,
			String updateUser, String isActive, String fatherCname,
			String isSubAppSys, String isAppDu, String isBeUse) {
		super();
		this.appId = appId;
		this.fatherId = fatherId;
		this.cname = cname;
		this.fullCname = fullCname;
		this.ename = ename;
		this.fullEname = fullEname;
		this.manager = manager;
		this.sysTypeCode = sysTypeCode;
		this.sysLevelCode = sysLevelCode;
		this.remark = remark;
		this.createDateTime = createDateTime;
		this.createUser = createUser;
		this.updateDateTime = updateDateTime;
		this.updateUser = updateUser;
		this.isActive = isActive;
		this.fatherCname = fatherCname;
		this.isSubAppSys = isSubAppSys;
		this.isAppDu = isAppDu;
		this.isBeUse = isBeUse;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getSysTypeCode() {
		return sysTypeCode;
	}

	public void setSysTypeCode(String sysTypeCode) {
		this.sysTypeCode = sysTypeCode;
	}

	public String getSysLevelCode() {
		return sysLevelCode;
	}

	public void setSysLevelCode(String sysLevelCode) {
		this.sysLevelCode = sysLevelCode;
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

	public String getFatherCname() {
		return fatherCname;
	}

	public void setFatherCname(String fatherCname) {
		this.fatherCname = fatherCname;
	}

	public String getIsSubAppSys() {
		return isSubAppSys;
	}

	public void setIsSubAppSys(String isSubAppSys) {
		this.isSubAppSys = isSubAppSys;
	}

	public String getIsAppDu() {
		return isAppDu;
	}

	public void setIsAppDu(String isAppDu) {
		this.isAppDu = isAppDu;
	}

	public String getIsBeUse() {
		return isBeUse;
	}

	public void setIsBeUse(String isBeUse) {
		this.isBeUse = isBeUse;
	}	
}
