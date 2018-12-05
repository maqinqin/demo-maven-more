package com.git.cloud.resmgt.common.model.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;


/**
 * The persistent class for the CM_DATASTORE database table.
 * 
 */
public class CmDatastoreVo extends BaseBO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;

	private String isActive;

	private String name;

	private Integer orderNum;

	private String path;

	private String storageId;
	//Nas存储Ip
	private String mgrIp;
	//挂载SAN时用到的标识符
	private String identifier;
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public CmDatastoreVo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public int getOrderNum() {
//		return this.orderNum;
//	}
//
//	public void setOrderNum(int orderNum) {
//		this.orderNum = orderNum;
//	}

	
	
	public String getPath() {
		return this.path;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStorageId() {
		return this.storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMgrIp() {
		return mgrIp;
	}

	public void setMgrIp(String mgrIp) {
		this.mgrIp = mgrIp;
	}
	
}