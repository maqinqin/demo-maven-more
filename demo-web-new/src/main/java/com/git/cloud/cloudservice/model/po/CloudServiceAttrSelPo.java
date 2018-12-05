package com.git.cloud.cloudservice.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Description 
 * @author 		 yangzhenhai
 * @version 	 v1.0  2014-9-17
 */
public class CloudServiceAttrSelPo extends BaseBO implements Serializable {

	// Fields

	private String attrSelId;
	private String attrId;
	private String attrKey;
	private String attrValue;
	private String isActive;

	// Constructors

	/** default constructor */
	public CloudServiceAttrSelPo() {
	}

	/** minimal constructor */
	public CloudServiceAttrSelPo(String attrSelId, String attrId) {
		this.attrSelId = attrSelId;
		this.attrId = attrId;
	}

	/** full constructor */
	

	// Property accessors

	public String getAttrSelId() {
		return this.attrSelId;
	}

	public CloudServiceAttrSelPo(String attrSelId, String attrId,
			String attrKey, String attrValue, String isActive) {
		super();
		this.attrSelId = attrSelId;
		this.attrId = attrId;
		this.attrKey = attrKey;
		this.attrValue = attrValue;
		this.isActive = isActive;
	}

	public void setAttrSelId(String attrSelId) {
		this.attrSelId = attrSelId;
	}

	public String getAttrId() {
		return this.attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getAttrKey() {
		return attrKey;
	}

	public void setAttrKey(String attrKey) {
		this.attrKey = attrKey;
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
