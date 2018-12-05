package com.git.cloud.request.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class BmSrTypePo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String srTypeId;
	private String srTypeName;
	private String srTypeMark;
	private String parentId;
	private String srTypeCode;
	private String isActive;
	private String remark;

	public BmSrTypePo() {
	}

	public BmSrTypePo(String srTypeId) {
		this.srTypeId = srTypeId;
	}

	public BmSrTypePo(String srTypeId, String srTypeName, String srTypeCode,
			String parentId, String srTypeMark, String isActive, String remark) {
		this.srTypeId = srTypeId;
		this.srTypeName = srTypeName;
		this.srTypeCode = srTypeCode;
		this.parentId = parentId;
		this.srTypeMark = srTypeMark;
		this.isActive = isActive;
		this.remark = remark;
	}

	public String getSrTypeId() {
		return this.srTypeId;
	}

	public void setSrTypeId(String srTypeId) {
		this.srTypeId = srTypeId;
	}

	public String getSrTypeName() {
		return this.srTypeName;
	}

	public void setSrTypeName(String srTypeName) {
		this.srTypeName = srTypeName;
	}

	public String getSrTypeMark() {
		return this.srTypeMark;
	}

	public void setSrTypeMark(String srTypeMark) {
		this.srTypeMark = srTypeMark;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getSrTypeCode() {
		return this.srTypeCode;
	}

	public void setSrTypeCode(String srTypeCode) {
		this.srTypeCode = srTypeCode;
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

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}