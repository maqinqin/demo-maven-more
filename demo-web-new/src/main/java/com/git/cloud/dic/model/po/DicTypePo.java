package com.git.cloud.dic.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class DicTypePo   extends BaseBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public DicTypePo(){}
	
	private String dicTypeCode;
	private String dicTypeName;
	private String remark;
	private String beforeUpdateName;//保存修改之前，字典类型的名称
	
	public String getBeforeUpdateName() {
		return beforeUpdateName;
	}


	public void setBeforeUpdateName(String beforeUpdateName) {
		this.beforeUpdateName = beforeUpdateName;
	}


	public String getDicTypeCode() {
		return dicTypeCode;
	}


	public void setDicTypeCode(String dicTypeCode) {
		this.dicTypeCode = dicTypeCode;
	}


	public String getDicTypeName() {
		return dicTypeName;
	}


	public void setDicTypeName(String dicTypeName) {
		this.dicTypeName = dicTypeName;
	}




	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
