package com.git.cloud.resmgt.common.model.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class CmHostDatastoreRefVo extends BaseBO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 776551769832814794L;
	private String id;
	private String hostId;
	private String datastoreId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getDatastoreId() {
		return datastoreId;
	}
	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
