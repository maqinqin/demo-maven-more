package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 业务参数实体类
 * <p>
 * 
 * @author huaili
 * @version 1.0 2013-4-28
 * @see
 */
//@Table(name = "CM_STORAGE_FCPORT")
public class StorageFCPortGroupPo extends BaseBO implements java.io.Serializable{
	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = -1733348100842560606L;
	private String id;
	private String storageName;
	private String sn;
	private String fcport;
	private String pwwn;
	private String fcportGroupNum;
	private String isActive;
	private String deviceId;
	public String getId() {
		return id;
	}
	public String getStorageName() {
		return storageName;
	}
	public String getSn() {
		return sn;
	}
	public String getFcport() {
		return fcport;
	}
	public String getPwwn() {
		return pwwn;
	}
	public String getFcportGroupNum() {
		return fcportGroupNum;
	}
	public String getIsActive() {
		return isActive;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public void setFcport(String fcport) {
		this.fcport = fcport;
	}
	public void setPwwn(String pwwn) {
		this.pwwn = pwwn;
	}
	public void setFcportGroupNum(String fcportGroupNum) {
		this.fcportGroupNum = fcportGroupNum;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
