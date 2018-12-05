package com.git.cloud.resmgt.common.model.po;

import java.io.Serializable;
import java.sql.Timestamp;

import com.git.cloud.common.model.base.BaseBO;

public class CmPasswordPo extends BaseBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -72802723152481793L;
	private String id;
	private String resourceId;
	private String userName;
	private String password;
	private Timestamp lastModifyTime;

	/** default constructor */
	public CmPasswordPo() {
	}

	/** minimal constructor */
	public CmPasswordPo(String id) {
		this.id = id;
	}

	/** full constructor */
	public CmPasswordPo(String id, String resourceId, String userName,
			String password, Timestamp lastModifyTime) {
		this.id = id;
		this.resourceId = resourceId;
		this.userName = userName;
		this.password = password;
		this.lastModifyTime = lastModifyTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
