package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class CmLocalDiskPo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5280569006675546750L;
	private String id;
	private String deviceId;
	private String name;
	private Integer size;
	private String isActive;

	// Constructors

	/** default constructor */
	public CmLocalDiskPo() {
	}

	/** minimal constructor */
	public CmLocalDiskPo(String id) {
		this.id = id;
	}

	/** full constructor */
	public CmLocalDiskPo(String id, String deviceId, String name, Integer size,
			String isActive) {
		this.id = id;
		this.deviceId = deviceId;
		this.name = name;
		this.size = size;
		this.isActive = isActive;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSize() {
		return this.size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getIsActive() {
		return this.isActive;
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
