package com.git.cloud.common.model;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 字典表PO
 * @author liangshuang
 * @date 2014-9-17 上午11:17:54
 * @version v1.0
 *
 */
public class AdminDicPo extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dicId;
	private String dicName;
	private String remark;
	private String dicCode;
	private String dicTypeCode;
	private String attr;
	private int orderNum;
	private Date createDateTime;
	private String createUser;
	private Date updateDateTime;
	private String updateUser;
	
	public AdminDicPo() {
		super();
	}
	
	public AdminDicPo(String dicId, String dicName, String remark,
			String dicCode, String dicTypeCode, String attr, int orderNum,
			Date createDateTime, String createUser, Date updateDateTime,
			String updateUser) {
		super();
		this.dicId = dicId;
		this.dicName = dicName;
		this.remark = remark;
		this.dicCode = dicCode;
		this.dicTypeCode = dicTypeCode;
		this.attr = attr;
		this.orderNum = orderNum;
		this.createDateTime = createDateTime;
		this.createUser = createUser;
		this.updateDateTime = updateDateTime;
		this.updateUser = updateUser;
	}
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

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
