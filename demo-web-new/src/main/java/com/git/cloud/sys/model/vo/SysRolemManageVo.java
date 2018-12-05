package com.git.cloud.sys.model.vo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * AppInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SysRolemManageVo extends BaseBO implements java.io.Serializable {

	// Fields
	private String id;
	private String userId;
	private String appInfoId;
	private String cloudServiceId;
	
	public String getCloudServiceId() {
		return cloudServiceId;
	}

	public void setCloudServiceId(String cloudServiceId) {
		this.cloudServiceId = cloudServiceId;
	}

	// Constructors
	/** default constructor */
	public SysRolemManageVo() {
	}

	/** minimal constructor */
	public SysRolemManageVo(String id,String userId, String appInfoId) {
		this.id = id;
		this.userId = userId;
		this.appInfoId = appInfoId;
	}

	// Property accessors
	@Override
	
	public String getBizId() {
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppInfoId() {
		return appInfoId;
	}

	public void setAppInfoId(String appInfoId) {
		this.appInfoId = appInfoId;
	}
}