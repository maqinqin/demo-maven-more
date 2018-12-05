package com.git.cloud.tenant.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class QuotaCountVo extends BaseBO{
	private String quotaCode;
	private String quotaCnName;
	private String value;
	private String usedValue;
	
	public QuotaCountVo() {
		super();
	}
	public QuotaCountVo(String quotaCode, String quotaCnName, String value) {
		super();
		this.quotaCode = quotaCode;
		this.quotaCnName = quotaCnName;
		this.value = value;
	}
	public String getQuotaCode() {
		return quotaCode;
	}
	public void setQuotaCode(String quotaCode) {
		this.quotaCode = quotaCode;
	}
	public String getQuotaCnName() {
		return quotaCnName;
	}
	public void setQuotaCnName(String quotaCnName) {
		this.quotaCnName = quotaCnName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUsedValue() {
		return usedValue;
	}
	public void setUsedValue(String usedValue) {
		this.usedValue = usedValue;
	}
	@Override
	public String toString() {
		return "QuotaCountVo [quotaCode=" + quotaCode + ", quotaCnName=" + quotaCnName + ", value=" + value+ ", usedValue=" + usedValue + "]";
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
