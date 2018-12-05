package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;


/**
 * 
 * <p>
 * 
 * @author huaili
 * @version 1.0 2013-9-16
 * @see

@Entity
@Table(name = "CM_VSAN") */
public class VsanPo   extends BaseBO implements java.io.Serializable {
	
	private String id;
	private String vsanName;
	private String fabricId;
	private String isActive;
	private String activeZoneset;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getVsanName() {
		return vsanName;
	}

	public void setVsanName(String vsanName) {
		this.vsanName = vsanName;
	}

	public String getFabricId() {
		return fabricId;
	}

	public void setFabricId(String fabricId) {
		this.fabricId = fabricId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getActiveZoneset() {
		return activeZoneset;
	}

	public void setActiveZoneset(String activeZoneset) {
		this.activeZoneset = activeZoneset;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
