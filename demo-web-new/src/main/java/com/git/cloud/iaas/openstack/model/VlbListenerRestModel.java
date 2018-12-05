package com.git.cloud.iaas.openstack.model;

public class VlbListenerRestModel {
	private String listenerName;
	private String loadbalancerId;
	private String protocol;
	private String protocolPort;
	public String getListenerName() {
		return listenerName;
	}
	public void setListenerName(String listenerName) {
		this.listenerName = listenerName;
	}
	public String getLoadbalancerId() {
		return loadbalancerId;
	}
	public void setLoadbalancerId(String loadbalancerId) {
		this.loadbalancerId = loadbalancerId;
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
	@Override
	public String toString() {
		return "VlbListenerModel [listenerName=" + listenerName + ", loadbalancerId=" + loadbalancerId + ", protocol="
				+ protocol + ", protocolPort=" + protocolPort + "]";
	}
	
	
	


}
