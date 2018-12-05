package com.git.cloud.request.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class BmSrAttrValPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String srAttrValId;
	private String rrinfoId;
	private String attrId;
	private String attrValue;

	public BmSrAttrValPo() {
	}

	public BmSrAttrValPo(String srAttrValId) {
		this.srAttrValId = srAttrValId;
	}

	public BmSrAttrValPo(String srAttrValId, String rrinfoId,
			String attrId, String attrValue) {
		this.srAttrValId = srAttrValId;
		this.rrinfoId = rrinfoId;
		this.attrId = attrId;
		this.attrValue = attrValue;
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

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}