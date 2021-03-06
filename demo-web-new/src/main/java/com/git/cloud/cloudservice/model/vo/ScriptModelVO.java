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
public class ScriptModelVO extends BaseBO implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -8802164763236061679L;

	public ScriptModelVO() {
	}

	private String isActive;

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	private java.lang.String fileName;

	/**
     * 
     */
	public java.lang.String getFileName() {
		return this.fileName;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}

	private java.lang.String hadFz;

	/**
     * 
     */
	public java.lang.String getHadFz() {
		return this.hadFz;
	}

	public void setHadFz(java.lang.String hadFz) {
		this.hadFz = hadFz;
	}

	private java.lang.String checkCode;

	/**
     * 
     */
	public java.lang.String getCheckCode() {
		return this.checkCode;
	}

	public void setCheckCode(java.lang.String checkCode) {
		this.checkCode = checkCode;
	}

	private java.lang.String runUser;

	/**
     * 
     */
	public java.lang.String getRunUser() {
		return this.runUser;
	}

	public void setRunUser(java.lang.String runUser) {
		this.runUser = runUser;
	}

	private java.lang.String remark;

	/**
     * 
     */
	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
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

	private com.git.cloud.cloudservice.model.vo.ModelModelVO modelModelVO;

	/**
	 * Get the modelModelVO
	 * 
	 */
	public com.git.cloud.cloudservice.model.vo.ModelModelVO getModelModelVO() {
		return this.modelModelVO;
	}

	/**
	 * Sets the modelModelVO
	 */
	public void setModelModelVO(com.git.cloud.cloudservice.model.vo.ModelModelVO modelModelVO) {
		this.modelModelVO = modelModelVO;
	}

	private java.util.Collection scriptParamModelVOs;

	/**
	 * Get the scriptParamModelVOs
	 * 
	 */
	public java.util.Collection getScriptParamModelVOs() {
		return this.scriptParamModelVOs;
	}

	/**
	 * Sets the scriptParamModelVOs
	 */
	public void setScriptParamModelVOs(java.util.Collection scriptParamModelVOs) {
		this.scriptParamModelVOs = scriptParamModelVOs;
	}

	/**
	 * Constructs new instances of
	 * {@link com.git.cloud.cloudservice.model.vo.ScriptModelVO}.
	 */
	public static final class Factory {
		/**
		 * Constructs a new instance of
		 * {@link com.git.cloud.cloudservice.model.vo.ScriptModelVO}.
		 */
		public static com.git.cloud.cloudservice.model.vo.ScriptModelVO newInstance() {
			return new ScriptModelVO();
		}
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}