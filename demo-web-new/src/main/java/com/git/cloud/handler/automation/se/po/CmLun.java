package com.git.cloud.handler.automation.se.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class CmLun  extends BaseBO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String lunId;
	private String storageId;
	private String lunStatus;//0.未分配;1.已分配;
	private String lunName;
	private String lunSn;
	private String lunPath;
	private String lunType; // LUN类型（DATA,ARCH,SYS）
	private Integer lunSize;
	private Date lunAssignTime;
	private Date lunSelectTime;
	private String usedResourceId; // 使用该LUN的资源ID，这里存放的是rrinfoId资源申请ID

	public String getLunId() {
		return lunId;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setLunId(String lunId) {
		this.lunId = lunId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getLunStatus() {
		return lunStatus;
	}

	public void setLunStatus(String lunStatus) {
		this.lunStatus = lunStatus;
	}

	public String getLunName() {
		return lunName;
	}

	public void setLunName(String lunName) {
		this.lunName = lunName;
	}

	public String getLunSn() {
		return lunSn;
	}

	public void setLunSn(String lunSn) {
		this.lunSn = lunSn;
	}

	public String getLunPath() {
		return lunPath;
	}

	public void setLunPath(String lunPath) {
		this.lunPath = lunPath;
	}

	public String getLunType() {
		return lunType;
	}

	public void setLunType(String lunType) {
		this.lunType = lunType;
	}

	public Integer getLunSize() {
		return lunSize;
	}

	public void setLunSize(Integer lunSize) {
		this.lunSize = lunSize;
	}

	public Date getLunAssignTime() {
		return lunAssignTime;
	}

	public void setLunAssignTime(Date lunAssignTime) {
		this.lunAssignTime = lunAssignTime;
	}

	public Date getLunSelectTime() {
		return lunSelectTime;
	}

	public void setLunSelectTime(Date lunSelectTime) {
		this.lunSelectTime = lunSelectTime;
	}

	public String getUsedResourceId() {
		return usedResourceId;
	}

	public void setUsedResourceId(String usedResourceId) {
		this.usedResourceId = usedResourceId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
