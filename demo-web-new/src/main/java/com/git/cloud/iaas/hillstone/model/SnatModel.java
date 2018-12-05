package com.git.cloud.iaas.hillstone.model;

public class SnatModel {
	// 路由名称
	private String vrName;
	// fromIP
	private String fromIp;
	// from是否为IP
	private String fromIsIp;
	// toIP
	private String toIp;
	// to是否为IP
	private String toIsIp;
	// 转换IP
	private String transToIp;
	// transTo是否为IP
	private String transToIsIp;
	// 源端端口
	private String srcPort;
	// 描述
	private String description;
	
	public String getVrName() {
		return vrName;
	}
	public void setVrName(String vrName) {
		this.vrName = vrName;
	}
	public String getFromIp() {
		return fromIp;
	}
	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}
	public String getToIp() {
		return toIp;
	}
	public void setToIp(String toIp) {
		this.toIp = toIp;
	}
	public String getTransToIp() {
		return transToIp;
	}
	public void setTransToIp(String transToIp) {
		this.transToIp = transToIp;
	}
	public String getSrcPort() {
		return srcPort;
	}
	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFromIsIp() {
		return fromIsIp;
	}
	public void setFromIsIp(String fromIsIp) {
		this.fromIsIp = fromIsIp;
	}
	public String getToIsIp() {
		return toIsIp;
	}
	public void setToIsIp(String toIsIp) {
		this.toIsIp = toIsIp;
	}
	public String getTransToIsIp() {
		return transToIsIp;
	}
	public void setTransToIsIp(String transToIsIp) {
		this.transToIsIp = transToIsIp;
	}
}
