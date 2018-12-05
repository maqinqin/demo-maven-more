package com.git.cloud.cloudservice.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 脚本模块
 * @ClassName: ModelModel
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class ModelModel extends BaseBO implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 8785900664834471518L;

	private java.lang.String id;
	private java.lang.String name;
	private java.lang.String remark;
	private java.lang.String filePath;
	private java.util.Collection scriptModels = new java.util.HashSet();
	private com.git.cloud.cloudservice.model.po.PackageModel packageModel;

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

	/**
     * 
     */
	public com.git.cloud.cloudservice.model.po.PackageModel getPackageModel() {
		return this.packageModel;
	}

	/**
     * 
     */
	public void setPackageModel(com.git.cloud.cloudservice.model.po.PackageModel packageModel) {
		this.packageModel = packageModel;
	}

	/**
     * 
     */
	public java.util.Collection getScriptModels() {
		return this.scriptModels;
	}

	/**
     * 
     */
	public void setScriptModels(java.util.Collection scriptModels) {
		this.scriptModels = scriptModels;
	}

	@Override
	public String getBizId() {
		return null;
	}

	private String isActive;
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}