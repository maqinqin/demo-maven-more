package com.git.cloud.iaas.openstack.model;

public class VlbMemberRestModel {
	private String tenantId;
	private String poolId;
	private String address;
	private String protocolPort;
	private String weight;
	private String subnetId;
	
	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getPoolId() {
		return poolId;
	}
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProtocolPort() {
		return protocolPort;
	}
	public void setProtocolPort(String protocolPort) {
		this.protocolPort = protocolPort;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}


}
