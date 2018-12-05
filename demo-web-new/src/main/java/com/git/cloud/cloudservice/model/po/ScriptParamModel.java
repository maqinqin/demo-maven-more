package com.git.cloud.cloudservice.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 脚本参数
 * @ClassName: ScriptParamModel
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class ScriptParamModel extends BaseBO implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -3047685519228073667L;

	private String isActive;
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	private java.lang.String code;

	/**
     * 
     */
	public java.lang.String getCode() {
		return this.code;
	}

	/**
     * 
     */
	public void setCode(java.lang.String code) {
		this.code = code;
	}

	private java.lang.String name;

	/**
     * 
     */
	public java.lang.String getName() {
		return this.name;
	}

	/**
     * 
     */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	private java.lang.String splitChar;

	/**
     * 
     */
	public java.lang.String getSplitChar() {
		return this.splitChar;
	}

	/**
     * 
     */
	public void setSplitChar(java.lang.String splitChar) {
		this.splitChar = splitChar;
	}

	private java.lang.String paramType;

	/**
     * 
     */
	public java.lang.String getParamType() {
		return this.paramType;
	}

	/**
     * 
     */
	public void setParamType(java.lang.String paramType) {
		this.paramType = paramType;
	}

	private java.lang.String paramValType;

	/**
     * 
     */
	public java.lang.String getParamValType() {
		return this.paramValType;
	}

	/**
     * 
     */
	public void setParamValType(java.lang.String paramValType) {
		this.paramValType = paramValType;
	}

	private java.lang.Integer orders;

	/**
     * 
     */
	public java.lang.Integer getOrders() {
		return this.orders;
	}

	/**
     * 
     */
	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	private java.lang.String id;

	/**
     * 
     */
	public java.lang.String getId() {
		return this.id;
	}

	/**
     * 
     */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	private com.git.cloud.cloudservice.model.po.ScriptModel scriptModel;

	/**
     * 
     */
	public com.git.cloud.cloudservice.model.po.ScriptModel getScriptModel() {
		return this.scriptModel;
	}

	/**
     * 
     */
	public void setScriptModel(
			com.git.cloud.cloudservice.model.po.ScriptModel scriptModel) {
		this.scriptModel = scriptModel;
	}

	/**
	 * Returns <code>true</code> if the argument is an ScriptParamModel instance
	 * and all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ScriptParamModel)) {
			return false;
		}
		final ScriptParamModel that = (ScriptParamModel) object;
		if (this.id == null || that.getId() == null
				|| !this.id.equals(that.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code based on this entity's identifiers.
	 */
	public int hashCode() {
		int hashCode = 0;
		hashCode = 29 * hashCode + (id == null ? 0 : id.hashCode());

		return hashCode;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}