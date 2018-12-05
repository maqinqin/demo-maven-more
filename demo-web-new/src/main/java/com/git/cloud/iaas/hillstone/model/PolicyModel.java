package com.git.cloud.iaas.hillstone.model;

public class PolicyModel {
	
	private String policyName;
	private String srcIp;
	private String srcNetmask;
	private String dstIp;
	private String dstNetmask;
	private String srcIpMin;
	private String srcIpMax;
	private String dstIpMin;
	private String dstIpMax;
	private String serverbookName;
	private String policyDesc;
	
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getSrcIp() {
		return srcIp;
	}
	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}
	public String getSrcNetmask() {
		return srcNetmask;
	}
	public void setSrcNetmask(String srcNetmask) {
		this.srcNetmask = srcNetmask;
	}
	public String getDstIp() {
		return dstIp;
	}
	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}
	public String getDstNetmask() {
		return dstNetmask;
	}
	public void setDstNetmask(String dstNetmask) {
		this.dstNetmask = dstNetmask;
	}
	public String getSrcIpMin() {
		return srcIpMin;
	}
	public void setSrcIpMin(String srcIpMin) {
		this.srcIpMin = srcIpMin;
	}
	public String getSrcIpMax() {
		return srcIpMax;
	}
	public void setSrcIpMax(String srcIpMax) {
		this.srcIpMax = srcIpMax;
	}
	public String getDstIpMin() {
		return dstIpMin;
	}
	public void setDstIpMin(String dstIpMin) {
		this.dstIpMin = dstIpMin;
	}
	public String getDstIpMax() {
		return dstIpMax;
	}
	public void setDstIpMax(String dstIpMax) {
		this.dstIpMax = dstIpMax;
	}
	public String getServerbookName() {
		return serverbookName;
	}
	public void setServerbookName(String serverbookName) {
		this.serverbookName = serverbookName;
	}
	public String getPolicyDesc() {
		return policyDesc;
	}
	public void setPolicyDesc(String policyDesc) {
		this.policyDesc = policyDesc;
	}
}
