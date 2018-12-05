// license-header java merge-point
//
// Attention: Generated code! Do not modify by hand!
// Generated by: ValueObject.vsl in andromda-java-cartridge.
//
package com.git.cloud.cloudservice.model.vo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 
 */
public class ScriptParamModelVO extends BaseBO implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -1238882292144838606L;

	public ScriptParamModelVO() {
	}

	private String isActive;

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	private java.lang.String id;

	/**
     * 
     */
	public java.lang.String getId() {
		return this.id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	private java.lang.String code;

	/**
     * 
     */
	public java.lang.String getCode() {
		return this.code;
	}

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

	public void setOrders(java.lang.Integer orders) {
		this.orders = orders;
	}

	private com.git.cloud.cloudservice.model.vo.ScriptModelVO scriptModelVO;

	/**
	 * Get the scriptModelVO
	 * 
	 */
	public com.git.cloud.cloudservice.model.vo.ScriptModelVO getScriptModelVO() {
		return this.scriptModelVO;
	}

	/**
	 * Sets the scriptModelVO
	 */
	public void setScriptModelVO(com.git.cloud.cloudservice.model.vo.ScriptModelVO scriptModelVO) {
		this.scriptModelVO = scriptModelVO;
	}

	/**
	 * Constructs new instances of
	 * {@link com.git.cloud.cloudservice.model.vo.ScriptParamModelVO}.
	 */
	public static final class Factory {
		/**
		 * Constructs a new instance of
		 * {@link com.git.cloud.cloudservice.model.vo.ScriptParamModelVO}.
		 */
		public static com.git.cloud.cloudservice.model.vo.ScriptParamModelVO newInstance() {
			return new ScriptParamModelVO();
		}
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}