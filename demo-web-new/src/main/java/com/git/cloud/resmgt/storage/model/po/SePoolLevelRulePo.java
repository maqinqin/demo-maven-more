package com.git.cloud.resmgt.storage.model.po;

import java.io.Serializable;


/**
 * The persistent class for the SE_POOL_LEVEL_RULE database table.
 * 
 */
public class SePoolLevelRulePo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String cellValue;

	private String conditionOne;

	private String conditionTwo;

	private String isActive;

	private String remark;

	private String ruleType;
	
	private String trId;
	
	private String cellId;
	
	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getTrId() {
		return trId;
	}

	public void setTrId(String trId) {
		this.trId = trId;
	}

	public String getTdId() {
		return tdId;
	}

	public void setTdId(String tdId) {
		this.tdId = tdId;
	}

	private String tdId;

    public SePoolLevelRulePo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCellValue() {
		return this.cellValue;
	}

	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}

	public String getConditionOne() {
		return this.conditionOne;
	}

	public void setConditionOne(String conditionOne) {
		this.conditionOne = conditionOne;
	}

	public String getConditionTwo() {
		return this.conditionTwo;
	}

	public void setConditionTwo(String conditionTwo) {
		this.conditionTwo = conditionTwo;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRuleType() {
		return this.ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

}