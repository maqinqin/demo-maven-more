package com.git.cloud.common.model;

import com.git.cloud.common.model.base.BaseBO;

public class AdminKeyMapPo extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String type;
	private String myId;
	private String sysType;
	private String resourceId;
	private String targetId;
	
	public AdminKeyMapPo() {
		
	}
	
	public AdminKeyMapPo(String id) {
		this.id = id;
	}
	
	public AdminKeyMapPo(String id, String type, String myId, String sysType,
			String resourceId, String targetId) {
		super();
		this.id = id;
		this.type = type;
		this.myId = myId;
		this.sysType = sysType;
		this.resourceId = resourceId;
		this.targetId = targetId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMyId() {
		return myId;
	}
	public void setMyId(String myId) {
		this.myId = myId;
	}
	public String getSysType() {
		return sysType;
	}
	public void setSysType(String sysType) {
		this.sysType = sysType;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	@Override
	public String getBizId() {
		return null;
	}
}
