package com.git.cloud.common.model.base;

import java.util.Date;

public abstract class BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// 业务主键
	private String bizId;

	private String createUser;
	private Date createDateTime;
	private String updateUser;
	private Date updateDateTime;

	// public String orderByString;

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public abstract String getBizId();

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/*public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}*/

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/*public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}*/

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
}
