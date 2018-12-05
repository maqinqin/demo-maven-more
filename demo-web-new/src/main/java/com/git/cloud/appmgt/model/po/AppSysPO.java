/**
 * 应用系统表的PO模型
 * @author qiupj
 * @version v1.0 2014-09-18
 */
package com.git.cloud.appmgt.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class AppSysPO extends BaseBO {
	private static final long serialVersionUID = -7060299614947779818L;
	private String appId;
	private String fatherId;
	private String cname;
	private String fullCname;
	private String ename;
	private String fullEname;
	private String manager;
	private String sysTypeCode;//类型
	private String sysLevelCode;//级别
	private String remark;
	private Date createDateTime;
	private String createUser;
	private Date updateDateTime;
	private String updateUser;
	private String isActive;
	
	public AppSysPO(){
		  
	  }
	  
	  public AppSysPO(String appId, String fatherId, String cname, String fullCname, 
			  String ename, String fullEname, String manager, String sysTypeCode, 
			  String sysLevelCode, String remark, Date createDateTime, String createUser, 
			  Date updateDateTime, String updateUser, String isActive) {
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

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
