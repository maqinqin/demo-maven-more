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
public class ModelModelVO extends BaseBO implements java.io.Serializable {
	/**
	 * The serial version UID of this class. Needed for serialization.
	 */
	private static final long serialVersionUID = 4373011493289399031L;

	public ModelModelVO() {
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

	private java.lang.String filePath;

	/**
     * 
     */
	public java.lang.String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(java.lang.String filePath) {
		this.filePath = filePath;
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

	private com.git.cloud.cloudservice.model.vo.PackageModelVO packageModelVO;

	/**
	 * Get the packageModelVO
	 * 
	 */
	public com.git.cloud.cloudservice.model.vo.PackageModelVO getPackageModelVO() {
		return this.packageModelVO;
	}

	/**
	 * Sets the packageModelVO
	 */
	public void setPackageModelVO(com.git.cloud.cloudservice.model.vo.PackageModelVO packageModelVO) {
		this.packageModelVO = packageModelVO;
	}

	private java.util.Collection scriptModelVOs;

	/**
	 * Get the scriptModelVOs
	 * 
	 */
	public java.util.Collection getScriptModelVOs() {
		return this.scriptModelVOs;
	}

	/**
	 * Sets the scriptModelVOs
	 */
	public void setScriptModelVOs(java.util.Collection scriptModelVOs) {
		this.scriptModelVOs = scriptModelVOs;
	}

	/**
	 * Constructs new instances of
	 * {@link com.git.cloud.cloudservice.model.vo.ModelModelVO}.
	 */
	public static final class Factory {
		/**
		 * Constructs a new instance of
		 * {@link com.git.cloud.cloudservice.model.vo.ModelModelVO}.
		 */
		public static com.git.cloud.cloudservice.model.vo.ModelModelVO newInstance() {
			return new ModelModelVO();
		}
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}