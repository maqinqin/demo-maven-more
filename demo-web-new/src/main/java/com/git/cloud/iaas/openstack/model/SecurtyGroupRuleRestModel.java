package com.git.cloud.iaas.openstack.model;

public class SecurtyGroupRuleRestModel {
	
	private String securityGroupId;
	private String etherType;
	private String direction;
	private String protocol;		//IP 协议类型
	private String portRangeMax ;	//安全组规则可匹配的最大端口
	private String portRangeMin;	//安全组规则可匹配的最小端口
	private String remoteIpPrefix;	//对端IP地址段
	private String remoteGroupId;	//对端的安全组ID
	public String getSecurityGroupId() {
		return securityGroupId;
	}
	public void setSecurityGroupId(String securityGroupId) {
		this.securityGroupId = securityGroupId;
	}
	public String getEtherType() {
		return etherType;
	}
	public void setEtherType(String etherType) {
		this.etherType = etherType;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getPortRangeMax() {
		return portRangeMax;
	}
	public void setPortRangeMax(String portRangeMax) {
		this.portRangeMax = portRangeMax;
	}
	public String getPortRangeMin() {
		return portRangeMin;
	}
	public void setPortRangeMin(String portRangeMin) {
		this.portRangeMin = portRangeMin;
	}
	public String getRemoteIpPrefix() {
		return remoteIpPrefix;
	}
	public void setRemoteIpPrefix(String remoteIpPrefix) {
		this.remoteIpPrefix = remoteIpPrefix;
	}
	public String getRemoteGroupId() {
		return remoteGroupId;
	}
	public void setRemoteGroupId(String remoteGroupId) {
		this.remoteGroupId = remoteGroupId;
	}
	@Override
	public String toString() {
		return "SecurityGroupRuleModel [securityGroupId=" + securityGroupId + ", etherType=" + etherType
				+ ", direction=" + direction + ", protocol=" + protocol + ", portRangeMax=" + portRangeMax
				+ ", portRangeMin=" + portRangeMin + ", remoteIpPrefix=" + remoteIpPrefix + ", remoteGroupId="
				+ remoteGroupId + "]";
	}
	


}
