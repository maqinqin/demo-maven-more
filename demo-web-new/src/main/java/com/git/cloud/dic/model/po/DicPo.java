package com.git.cloud.dic.model.po;
import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class DicPo extends BaseBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public DicPo(){}
	
	private String dicId;
	private String dicName;
	private String remark;
	private String dicCode;
	private String dicTypeCode;
	private String attr;
	private String orderNum;
	

	public String getDicId() {
		return dicId;
	}




	public void setDicId(String dicId) {
		this.dicId = dicId;
	}




	public String getDicName() {
		return dicName;
	}




	public void setDicName(String dicName) {
		this.dicName = dicName;
	}




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public String getDicCode() {
		return dicCode;
	}




	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}




	public String getDicTypeCode() {
		return dicTypeCode;
	}




	public void setDicTypeCode(String dicTypeCode) {
		this.dicTypeCode = dicTypeCode;
	}




	public String getAttr() {
		return attr;
	}




	public void setAttr(String attr) {
		this.attr = attr;
	}




	public String getOrderNum() {
		return orderNum;
	}




	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}




	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
