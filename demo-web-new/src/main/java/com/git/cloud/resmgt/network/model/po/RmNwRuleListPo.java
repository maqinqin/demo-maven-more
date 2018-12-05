package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwRuleListPo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rmNwRuleListId;
	private String rmNwRuleId;
	private String useCode;
	private String actNum;
	private String occNum;
	private String occSite;
	private String isActive;
	private String rmIpTypeName;
	private String vmwarePgPefix;
	private String useId;

	
	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUseCode() {
		return useCode;
	}

	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}

	public String getActNum() {
		return actNum;
	}

	public void setActNum(String actNum) {
		this.actNum = actNum;
	}

	public String getOccNum() {
		return occNum;
	}

	public void setOccNum(String occNum) {
		this.occNum = occNum;
	}

	public String getOccSite() {
		return occSite;
	}

	public void setOccSite(String occSite) {
		this.occSite = occSite;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRmIpTypeName() {
		return rmIpTypeName;
	}

	public void setRmIpTypeName(String rmIpTypeName) {
		this.rmIpTypeName = rmIpTypeName;
	}

	public String getVmwarePgPefix() {
		return vmwarePgPefix;
	}

	public void setVmwarePgPefix(String vmwarePgPefix) {
		this.vmwarePgPefix = vmwarePgPefix;
	}

	public String getRmNwRuleListId() {
		return rmNwRuleListId;
	}

	public void setRmNwRuleListId(String rmNwRuleListId) {
		this.rmNwRuleListId = rmNwRuleListId;
	}

	public String getRmNwRuleId() {
		return rmNwRuleId;
	}

	public void setRmNwRuleId(String rmNwRuleId) {
		this.rmNwRuleId = rmNwRuleId;
	}
   
	

}
