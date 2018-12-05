package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwRulePo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rmNwRuleId;
	private String hostTypeId;
	private String platFormId;
	private String virtualTypeId;
	private String ruleName;
	private String isActive;
	private String hsotTypeName;
	private String platformName;
	private String virtualTypeName;
	private String haType;
	private String haTypeName;
	private Integer rmNwRuleLIstCount;

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRmNwRuleId() {
		return rmNwRuleId;
	}

	public void setRmNwRuleId(String rmNwRuleId) {
		this.rmNwRuleId = rmNwRuleId;
	}

	public String getHostTypeId() {
		return hostTypeId;
	}

	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}

	public String getPlatFormId() {
		return platFormId;
	}

	public void setPlatFormId(String platFormId) {
		this.platFormId = platFormId;
	}

	public String getVirtualTypeId() {
		return virtualTypeId;
	}

	public void setVirtualTypeId(String virtualTypeId) {
		this.virtualTypeId = virtualTypeId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getHsotTypeName() {
		return hsotTypeName;
	}

	public void setHsotTypeName(String hsotTypeName) {
		this.hsotTypeName = hsotTypeName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getVirtualTypeName() {
		return virtualTypeName;
	}

	public void setVirtualTypeName(String virtualTypeName) {
		this.virtualTypeName = virtualTypeName;
	}

	public Integer getRmNwRuleLIstCount() {
		return rmNwRuleLIstCount;
	}

	public void setRmNwRuleLIstCount(Integer rmNwRuleLIstCount) {
		this.rmNwRuleLIstCount = rmNwRuleLIstCount;
	}

	public String getHaType() {
		return haType;
	}
	public void setHaType(String haType) {
		this.haType = haType;
	}

	public String getHaTypeName() {
		return haTypeName;
	}

	public void setHaTypeName(String haTypeName) {
		this.haTypeName = haTypeName;
	}
	
}