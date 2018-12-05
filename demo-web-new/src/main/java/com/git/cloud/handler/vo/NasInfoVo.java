package com.git.cloud.handler.vo;

import com.git.cloud.common.model.base.BaseBO;

public class NasInfoVo extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	private String sn;
	private String seatCode;
	private String storageAppTypeCode;
	private String userName;
	private String pwd;
	private String serverIp;
	
	public NasInfoVo() {
		
	}
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSeatCode() {
		return seatCode;
	}
	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}
	public String getStorageAppTypeCode() {
		return storageAppTypeCode;
	}
	public void setStorageAppTypeCode(String storageAppTypeCode) {
		this.storageAppTypeCode = storageAppTypeCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	@Override
	public String getBizId() {
		return this.sn;
	}
}
