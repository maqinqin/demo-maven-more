package com.git.cloud.reports.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class PropertyParamPo extends BaseBO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8278966852397844870L;
	//报表条件从属条件类
	private String id;//ID
	private String propertyKey;//报表条件从属条件KEY
	private String propertyValue;//报表条件从属条件VALUE
	private String conditionId;//表示自己所属于哪个条件（本身是那个条件的从属条件）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPropertyKey() {
		return propertyKey;
	}
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getConditionId() {
		return conditionId;
	}
	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
