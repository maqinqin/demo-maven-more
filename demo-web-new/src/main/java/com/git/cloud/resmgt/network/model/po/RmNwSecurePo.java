package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwSecurePo extends BaseBO implements java.io.Serializable {
	private String secureAreaId;
	private String secureAreaName;
	private String secureAreaType;
	private String isActive;
	
	public String getSecureAreaId() {
		return secureAreaId;
	}

	public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
	}

	public String getSecureAreaName() {
		return secureAreaName;
	}

	public void setSecureAreaName(String secureAreaName) {
		this.secureAreaName = secureAreaName;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getSecureAreaType() {
		return secureAreaType;
	}

	public void setSecureAreaType(String secureAreaType) {
		this.secureAreaType = secureAreaType;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
    public RmNwSecurePo(){
    	super();
    }
    public RmNwSecurePo(String secureAreaId,String secureAreaName, String isActive){
    	this.secureAreaId=secureAreaId;
    	this.secureAreaName=secureAreaName;
    	this.isActive=isActive;
    }
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
