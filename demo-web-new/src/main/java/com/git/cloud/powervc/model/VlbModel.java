package com.git.cloud.powervc.model;

public class VlbModel {
	private String poolId;
	private String tenantId;
	private String subnetId;
	private String vlbName;
	private String ipAddress;
	private String protocol;
	private String protocolPort;
	
	
	
	public String getPoolId() {
		return poolId;
	}
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getSubnetId() {
		return subnetId;
	}
	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}
	
	public String getVlbName() {
		return vlbName;
	}
	public void setVlbName(String vlbName) {
		this.vlbName = vlbName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getProtocolPort() {
		return protocolPort;
	}
	public void setProtocolPort(String protocolPort) {
		this.protocolPort = protocolPort;
	}
}
