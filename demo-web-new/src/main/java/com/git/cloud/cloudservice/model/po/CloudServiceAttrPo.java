package com.git.cloud.cloudservice.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-17
 */
public class CloudServiceAttrPo extends BaseBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String attrId;
	private String serviceId;
	private String attrType;
	private String attrClass;
	private String attrName;
	private String attrCname;
	private String defVal;
	private String isRequire;
	private String isActive;
	private String remark;
	private String isVisible;//wmy，添加是否可见字段
	private String attrListSql;
	// Constructors

	/** default constructor */
	public CloudServiceAttrPo() {
	}

	public String getAttrClass() {
		return attrClass;
	}

	public void setAttrClass(String attrClass) {
		this.attrClass = attrClass;
	}

	/** full constructor */
	public CloudServiceAttrPo(String attrId, String serviceId,
			String attrType, String attrClass, String attrName, String attrCname, String defVal,
			String isRequire, String isActive, String remark) {
		this.attrId = attrId;
		this.serviceId = serviceId;
		this.attrType = attrType;
		this.attrClass = attrClass;
		this.attrName = attrName;
		this.attrCname = attrCname;
		this.defVal = defVal;
		this.isRequire = isRequire;
		this.isActive = isActive;
		this.remark = remark;
	}

	// Property accessors

	public String getAttrId() {
		return this.attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getAttrType() {
		return this.attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrCname() {
		return this.attrCname;
	}

	public void setAttrCname(String attrCname) {
		this.attrCname = attrCname;
	}

	public String getDefVal() {
		return this.defVal;
	}

	public void setDefVal(String defVal) {
		this.defVal = defVal;
	}

	public String getIsRequire() {
		return this.isRequire;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
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

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public String getAttrListSql() {
		return attrListSql;
	}

	public void setAttrListSql(String attrListSql) {
		this.attrListSql = attrListSql;
	}

}
