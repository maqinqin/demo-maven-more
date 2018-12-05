package com.git.cloud.iaas.openstack.model;

public class SubnetRestModel {
	private String subnetName;
	private String networkId;
	private String startIp;
	private String endIp;
	private String cidr;
	private String gatewayIp;
	private String ipVersion;
	
	public String getSubnetName() {
		return subnetName;
	}
	public void setSubnetName(String subnetName) {
		this.subnetName = subnetName;
	}
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getStartIp() {
		return startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	public String getEndIp() {
		return endIp;
	}
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}
	public String getCidr() {
		return cidr;
	}
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}
	public String getGatewayIp() {
		return gatewayIp;
	}
	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}
	public String getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}
	@Override
	public String toString() {
		return "SubnetModel [subnetName=" + subnetName + ", networkId=" + networkId + ", startIp=" + startIp
				+ ", endIp=" + endIp + ", cidr=" + cidr + ", gatewayIp=" + gatewayIp + ", ipVersion=" + ipVersion + "]";
	}
	
	


}
