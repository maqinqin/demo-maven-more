package com.git.cloud.iaas.openstack.model;

public class NetworkRestModel {
	private String networkName;
	private String physicalNetwork;
	private String networkType;
	private String vlanId;
	private boolean isExternal;
	
	public String getPhysicalNetwork() {
		return physicalNetwork;
	}
	public void setPhysicalNetwork(String physicalNetwork) {
		this.physicalNetwork = physicalNetwork;
	}
	public String getNetworkName() {
		return networkName;
	}
	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getVlanId() {
		return vlanId;
	}
	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}
	public boolean isExternal() {
		return isExternal;
	}
	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}
	@Override
	public String toString() {
		return "NetworkModel [networkName=" + networkName + ", physicalNetwork=" + physicalNetwork + ", networkType="
				+ networkType + ", vlanId=" + vlanId + ", isExternal=" + isExternal + "]";
	}
	
	


}
