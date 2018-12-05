package com.git.cloud.resmgt.compute.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class ScanHmcHostPo extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2967184936139785524L;
	
	private String url;
	private String userName;
	private String password;
	private String hostName;
	private String queueIden;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getQueueIden() {
		return queueIden;
	}
	public void setQueueIden(String queueIden) {
		this.queueIden = queueIden;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
