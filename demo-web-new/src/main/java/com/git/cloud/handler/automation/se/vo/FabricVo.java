package com.git.cloud.handler.automation.se.vo;

import com.git.cloud.common.model.base.BaseBO;

public class FabricVo  extends BaseBO implements java.io.Serializable{

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	private String fabricId;
	private String fabricName;
	private String managerId;
	private String managerTypeCode;
	public String getFabricId() {
		return fabricId;
	}
	public String getFabricName() {
		return fabricName;
	}
	public String getManagerId() {
		return managerId;
	}
	public String getManagerTypeCode() {
		return managerTypeCode;
	}
	public void setFabricId(String fabricId) {
		this.fabricId = fabricId;
	}
	public void setFabricName(String fabricName) {
		this.fabricName = fabricName;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public void setManagerTypeCode(String managerTypeCode) {
		this.managerTypeCode = managerTypeCode;
	}
}
