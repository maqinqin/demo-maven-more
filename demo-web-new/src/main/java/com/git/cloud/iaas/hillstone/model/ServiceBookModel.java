package com.git.cloud.iaas.hillstone.model;

public class ServiceBookModel {
	private String servicebookName;
	private String description;
	private String protocol;
	private String dstPort;
	private String srcPort;
	
	public String getServicebookName() {
		return servicebookName;
	}
	public void setServicebookName(String servicebookName) {
		this.servicebookName = servicebookName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getDstPort() {
		return dstPort;
	}
	public void setDstPort(String dstPort) {
		this.dstPort = dstPort;
	}
	public String getSrcPort() {
		return srcPort;
	}
	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}
}
