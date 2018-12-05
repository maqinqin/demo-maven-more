package com.git.cloud.common.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class RequestParamPo {
	//返回:是否成功、虚拟机列表、失败信息、IP、用户、密码
	private String isSuccess;
	@SuppressWarnings("rawtypes")
	private List vMList;
	private String failMessage;
	@SuppressWarnings("rawtypes")
	private List iPAddress;
	private String userName;
	private String pwd;
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	@SuppressWarnings("rawtypes")
	public List getvMList() {
		return vMList;
	}
	@SuppressWarnings("rawtypes")
	public void setvMList(List vMList) {
		this.vMList = vMList;
	}
	public String getFailMessage() {
		return failMessage;
	}
	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
	
	@SuppressWarnings("rawtypes")
	public List getiPAddress() {
		return iPAddress;
	}
	
	@SuppressWarnings("rawtypes")
	public void setiPAddress(List iPAddress) {
		this.iPAddress = iPAddress;
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
}