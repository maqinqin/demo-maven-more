package com.git.cloud.request.model.vo;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;

public class BmSrAttrValVo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String srAttrValId;
	private String rrinfoId;
	private String attrId;
	private String attrValue;
	private String attrType;
	private String attrName;
	private String attrCname;
	private String defVal;
    private String isRequire;
    private String remark;
    private String attrClass;
    //add by wmy
    private String deviceId;
    private List<CloudServiceAttrSelPo> attrSelList;
    
	public BmSrAttrValVo() {
		
	}

	public String getSrAttrValId() {
		return srAttrValId;
	}

	public void setSrAttrValId(String srAttrValId) {
		this.srAttrValId = srAttrValId;
	}

	public String getRrinfoId() {
		return rrinfoId;
	}

	public void setRrinfoId(String rrinfoId) {
		this.rrinfoId = rrinfoId;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrCname() {
		return attrCname;
	}

	public void setAttrCname(String attrCname) {
		this.attrCname = attrCname;
	}

	public String getDefVal() {
		return defVal;
	}

	public void setDefVal(String defVal) {
		this.defVal = defVal;
	}

	public String getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<CloudServiceAttrSelPo> getAttrSelList() {
		return attrSelList;
	}

	public void setAttrSelList(List<CloudServiceAttrSelPo> attrSelList) {
		this.attrSelList = attrSelList;
	}

	public String getAttrClass() {
		return attrClass;
	}

	public void setAttrClass(String attrClass) {
		this.attrClass = attrClass;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}