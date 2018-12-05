package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 
 * <p>
 * 
 * @author huaili
 * @version 1.0 2013-5-2
 * @see
 */
public class FabricPo  extends BaseBO implements java.io.Serializable{
	
	private String fabricId;
	
	private String fabricName;
	
	private String storageMgrId;
	
	private String isActive; 
	
	private String seatCode;
	
	public String getFabricId() {
		return fabricId;
	}

	public void setFabricId(String fabricId) {
		this.fabricId = fabricId;
	}

	public String getFabricName() {
		return fabricName;
	}

	public void setFabricName(String fabricName) {
		this.fabricName = fabricName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getStorageMgrId() {
		return storageMgrId;
	}

	public void setStorageMgrId(String storageMgrId) {
		this.storageMgrId = storageMgrId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
