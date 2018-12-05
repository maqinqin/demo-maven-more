package com.git.cloud.common.model;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 参数表PO
 * @author liangshuang
 * @date 2014-9-17 上午11:18:41
 * @version v1.0
 *
 */
public class AdminParamPo extends BaseBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paramId;
	private String paramName;
	private String paramValue;
	private String remark;
	private String isActive;
	private String isEncryption;
	private Date createDateTime;
	private String createUser;
	private Date updateDateTime;
	private String updateUser;
	
	public AdminParamPo() {
		super();
	}
	
	public AdminParamPo(String paramId, String paramName, String paramValue,
			String remark, String isActive, String isEncryption,
			Date createDateTime, String createUser, Date updateDateTime,
			String updateUser) {
		super();
		this.paramId = paramId;
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.remark = remark;
		this.isActive = isActive;
		this.isEncryption = isEncryption;
		this.createDateTime = createDateTime;
		this.createUser = createUser;
		this.updateDateTime = updateDateTime;
		this.updateUser = updateUser;
	}
	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
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

	public String getIsEncryption() {
		return isEncryption;
	}

	public void setIsEncryption(String isEncryption) {
		this.isEncryption = isEncryption;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
