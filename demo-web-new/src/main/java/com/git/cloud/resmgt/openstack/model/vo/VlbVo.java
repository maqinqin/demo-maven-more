package com.git.cloud.resmgt.openstack.model.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class VlbVo  extends BaseBO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -825624755577401961L;
	
	private String id;
	private String name;
	private String subnetId;
	private String subnetName;
	private String ip;
	private String projectId;
	
	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public String getSubnetName() {
		return subnetName;
	}

	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
}
