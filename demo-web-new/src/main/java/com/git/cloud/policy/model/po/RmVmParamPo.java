package com.git.cloud.policy.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmVmParamPo.java 
 * @Package 	com.git.cloud.policy.model.po 
 * @author 		yangzhenhai
 * @date 		2014-9-11下午4:32:26
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVmParamPo extends BaseBO implements java.io.Serializable{

	// Fields

	private static final long serialVersionUID = 1L;
	private String paramId;
	private String objectType;//参数对象类型
	private String objectId;//参数对象
	private String paramType;//参数类别
	private String value;//参数值
	private String isActive;//是否有效

	// Constructors

	/** default constructor */
	public RmVmParamPo() {
	}

	/** full constructor */

	public RmVmParamPo(String paramId, String objectType, String objectId,
			String paramType, String value, String isActive) {
		this.paramId = paramId;
		this.objectType = objectType;
		this.objectId = objectId;
		this.paramType = paramType;
		this.value = value;
		this.isActive = isActive;
	}

	// Property accessors

	public String getParamId() {
		return this.paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/* (non-Javadoc)
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
