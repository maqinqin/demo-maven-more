package com.git.cloud.cloudservice.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 脚本
 * @ClassName: ScriptModel
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class ScriptModel extends BaseBO implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = -6506349216675948744L;

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

	/**
     * 
     */
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

	/**
     * 
     */
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

	/**
     * 
     */
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

	/**
     * 
     */
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

	/**
     * 
     */
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

	/**
     * 
     */
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

	/**
     * 
     */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	private com.git.cloud.cloudservice.model.po.ModelModel modelModel;

	/**
     * 
     */
	public com.git.cloud.cloudservice.model.po.ModelModel getModelModel() {
		return this.modelModel;
	}

	/**
     * 
     */
	public void setModelModel(
			com.git.cloud.cloudservice.model.po.ModelModel modelModel) {
		this.modelModel = modelModel;
	}

	private java.util.Collection scriptParamModels = new java.util.HashSet();

	/**
     * 
     */
	public java.util.Collection getScriptParamModels() {
		return this.scriptParamModels;
	}

	/**
     * 
     */
	public void setScriptParamModels(java.util.Collection scriptParamModels) {
		this.scriptParamModels = scriptParamModels;
	}

	/**
	 * Returns <code>true</code> if the argument is an ScriptModel instance and
	 * all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ScriptModel)) {
			return false;
		}
		final ScriptModel that = (ScriptModel) object;
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