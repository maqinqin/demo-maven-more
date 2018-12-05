package com.git.cloud.resmgt.storage.model.vo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:StorageChildPoolVo
 * @Description: 存储资源子池VO对象
 * @author mijia
 */
public class StorageChildPoolVo  extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = 7018502539465230576L;
	private String storageChildResPoolId;
	private String name;
	private String resPoolId;
	private String storageDevModel;
	private String storageAppTypeCode;
	private String remark;
	private String isActive;
	public String getStorageChildResPoolId() {
		return storageChildResPoolId;
	}
	public void setStorageChildResPoolId(String storageChildResPoolId) {
		this.storageChildResPoolId = storageChildResPoolId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResPoolId() {
		return resPoolId;
	}
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}
	public String getStorageDevModel() {
		return storageDevModel;
	}
	public void setStorageDevModel(String storageDevModel) {
		this.storageDevModel = storageDevModel;
	}
	public String getStorageAppTypeCode() {
		return storageAppTypeCode;
	}
	public void setStorageAppTypeCode(String storageAppTypeCode) {
		this.storageAppTypeCode = storageAppTypeCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
