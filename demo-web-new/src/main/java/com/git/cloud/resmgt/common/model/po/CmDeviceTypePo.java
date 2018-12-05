package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class CmDeviceTypePo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2133235874366212873L;
	private String id;
	private String typeCode;
	private String typeName;
	private String parentCode;
	private String deviceType;
	private String isActive;
	private String remark;

	// Constructors

	/** default constructor */
	public CmDeviceTypePo() {
	}

	/** minimal constructor */
	public CmDeviceTypePo(String id) {
		this.id = id;
	}

	/** full constructor */
	public CmDeviceTypePo(String id, String typeCode, String typeName,
			String parentCode, String deviceType, String isActive,
			String remark) {
		this.id = id;
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.parentCode = parentCode;
		this.deviceType = deviceType;
		this.isActive = isActive;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
