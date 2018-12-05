package com.git.cloud.rest.model;

import com.git.cloud.common.model.base.BaseBO;

public class DbParam{
	
	//实例名-instance, 用户名-userName, 密码-pass,表空间名-spaceName, 表空间大小M -spaceSize, 连接数-connectionNum ,字符集- character
	private String character;
	private String connectionNum;
	private String instance;
	private String pass;
	private String spaceName;
	private String spaceSize;
	private String userName;
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getConnectionNum() {
		return connectionNum;
	}
	public void setConnectionNum(String connectionNum) {
		this.connectionNum = connectionNum;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getSpaceName() {
		return spaceName;
	}
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	public String getSpaceSize() {
		return spaceSize;
	}
	public void setSpaceSize(String spaceSize) {
		this.spaceSize = spaceSize;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
