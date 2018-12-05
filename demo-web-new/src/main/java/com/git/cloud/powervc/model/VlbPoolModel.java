package com.git.cloud.powervc.model;

public class VlbPoolModel {
	private String tenantId;
	private String poolName;
	private String subnetId;
	private String protocol;
	private String lbMethod;
	private String listenerId;
	
	public VlbPoolModel() {
		
	}
	
	public String getListenerId() {
		return listenerId;
	}

	public void setListenerId(String listenerId) {
		this.listenerId = listenerId;
	}

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getSubnetId() {
		return subnetId;
	}
	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getLbMethod() {
		return lbMethod;
	}
	public void setLbMethod(String lbMethod) {
		this.lbMethod = lbMethod;
	}
}
