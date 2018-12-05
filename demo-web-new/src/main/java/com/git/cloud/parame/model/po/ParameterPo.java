package com.git.cloud.parame.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class ParameterPo extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String paramId;
	private String paramName;
	private String paramValue;
	private String remark;
	private String isActive;
	private String isEncryption;
	private String paramLogo;

	public ParameterPo(String paramId, String paramName, String paramValue, String remark, String isActive,
			String isEncryption, String paramLogo) {
		super();
		this.paramId = paramId;
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.remark = remark;
		this.isActive = isActive;
		this.isEncryption = isEncryption;
		this.paramLogo = paramLogo;
	}

	public String getParamLogo() {
		return paramLogo;
	}

	public void setParamLogo(String paramLogo) {
		this.paramLogo = paramLogo;
	}

	public ParameterPo() {
		super();
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsEncryption() {
		return isEncryption;
	}

	public void setIsEncryption(String isEncryption) {
		this.isEncryption = isEncryption;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
