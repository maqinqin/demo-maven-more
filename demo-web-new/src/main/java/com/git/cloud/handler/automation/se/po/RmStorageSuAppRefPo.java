package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;


/**
 * <p>
 * @author huaili
 * @version 1.0 2013-8-20
 * @see
 */
public class RmStorageSuAppRefPo  extends BaseBO implements java.io.Serializable{
private String appStorageId; 
	
	private String appId;
	
	private String suId;
	
	public String getAppStorageId() {
		return appStorageId;
	}

	public String getAppId() {
		return appId;
	}

	public String getSuId() {
		return suId;
	}

	public void setAppStorageId(String appStorageId) {
		this.appStorageId = appStorageId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setSuId(String suId) {
		this.suId = suId;
	}

	private String duType;

	public String getDuType() {
		return duType;
	}

	public void setDuType(String duType) {
		this.duType = duType;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
