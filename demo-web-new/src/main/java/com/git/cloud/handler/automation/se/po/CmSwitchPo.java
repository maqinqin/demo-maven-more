package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;
// default package


/**
 * CmSwitchPo entity. @author MyEclipse Persistence Tools
 
@Entity
@Table(name = "CM_SWITCH")*/
public class CmSwitchPo  extends BaseBO implements java.io.Serializable{

	// Fields

	private String switchId;
	private String fabricId;
	private String switchName;
	private String ip;
	private String isCore;
	private String isActive;

	// Constructors

	/** default constructor */
	public CmSwitchPo() {
	}

	/** minimal constructor */
	public CmSwitchPo(String switchId, String fabricId) {
		this.switchId = switchId;
		this.fabricId = fabricId;
	}

	/** full constructor */
	public CmSwitchPo(String switchId, String fabricId, String switchName,
			String ip, String isCore) {
		this.switchId = switchId;
		this.fabricId = fabricId;
		this.switchName = switchName;
		this.ip = ip;
		this.isCore = isCore;
	}

	// Property accessors
//	@Id
//	@Column(name = "SWITCH_ID", unique = true, nullable = false, length = 20)
	public String getSwitchId() {
		return this.switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}

//	@Column(name = "FABRIC_ID", nullable = false, length = 20)
	public String getFabricId() {
		return this.fabricId;
	}

	public void setFabricId(String fabricId) {
		this.fabricId = fabricId;
	}

//	@Column(name = "SWITCH_NAME", length = 20)
	public String getSwitchName() {
		return this.switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

//	@Column(name = "IP", length = 20)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

//	@Column(name = "IS_CORE", length = 1)
	public String getIsCore() {
		return this.isCore;
	}

	public void setIsCore(String isCore) {
		this.isCore = isCore;
	}

//	@Column(name = "IS_ACTIVE", length = 1)
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}