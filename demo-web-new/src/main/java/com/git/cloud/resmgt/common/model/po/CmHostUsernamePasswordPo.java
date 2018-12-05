package com.git.cloud.resmgt.common.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class CmHostUsernamePasswordPo extends BaseBO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7264873956767654713L;
	private String id;
	private String username;
	private String password;
	private String primaryId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getPrimaryId() {
		return primaryId;
	}
	
	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

}
