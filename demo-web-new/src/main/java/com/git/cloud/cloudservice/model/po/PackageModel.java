package com.git.cloud.cloudservice.model.po;

import com.git.cloud.common.model.base.BaseBO;
/**
 * 脚本包
 * @ClassName: PackageModel
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class PackageModel extends BaseBO implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 8697234716246419731L;

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

	private java.lang.String filePath;

	/**
     * 
     */
	public java.lang.String getFilePath() {
		return this.filePath;
	}

	/**
     * 
     */
	public void setFilePath(java.lang.String filePath) {
		this.filePath = filePath;
	}

	private java.lang.String fzr;

	/**
     * 
     */
	public java.lang.String getFzr() {
		return this.fzr;
	}

	/**
     * 
     */
	public void setFzr(java.lang.String fzr) {
		this.fzr = fzr;
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

	private java.util.Collection modelModels = new java.util.HashSet();

	/**
     * 
     */
	public java.util.Collection getModelModels() {
		return this.modelModels;
	}

	/**
     * 
     */
	public void setModelModels(java.util.Collection modelModels) {
		this.modelModels = modelModels;
	}

	/**
	 * Returns <code>true</code> if the argument is an PackageModel instance and
	 * all identifiers for this entity equal the identifiers of the argument
	 * entity. Returns <code>false</code> otherwise.
	 */
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof PackageModel)) {
			return false;
		}
		final PackageModel that = (PackageModel) object;
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