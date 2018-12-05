package com.git.cloud.parame.model.vo;

public class ParameterVo {
	 private String paramId;
	  private String paramName;
	  private String paramValue;
	  private String remark;
	  private String isActive;
	  private String isEncryption;
	  public ParameterVo(){
		  super();
	  }
	  public ParameterVo(String paramId,String paramName,String paramValue,
			  String remark,String isActive,String isEncryption){
		  super();
		  this.paramId=paramId;
		  this.paramName=paramName;
		  this.paramValue=paramValue;
		  this.remark=remark;
		  this.isActive=isActive;
		  this.isEncryption=isEncryption;
				  
		  
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
	
}
