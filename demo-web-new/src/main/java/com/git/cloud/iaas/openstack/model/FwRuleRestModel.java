package com.git.cloud.iaas.openstack.model;

public class FwRuleRestModel {
	

	
	private String vfwPolicyRuleId;	/*虚拟防火墙策略规则ID*/
	private String vfwPolicyId;		/*虚拟防火墙策略ID*/
	private String vfwPolicyRuleName;/*虚拟防火墙策略名称*/
	private String remark;
	private String isShare;			/*是否共享*/
	private String protocolType;	/*协议类型*/
	private String ipVersion;		/*协议版本*/
	private String sourceIpAddress;	/*原地址*/
	private String destIpAddress;	/*目标地址*/
	private String sourcePort;		/*源端口*/
	private String descPort;		/*目标端口*/
	private String ruleAction;		/*流量匹配规则时所执行的动作*/
	private String enabled;			/*规则的有效性*/
	private String isActive;
	private String projectId;
	public String getVfwPolicyRuleId() {
		return vfwPolicyRuleId;
	}
	public void setVfwPolicyRuleId(String vfwPolicyRuleId) {
		this.vfwPolicyRuleId = vfwPolicyRuleId;
	}
	public String getVfwPolicyId() {
		return vfwPolicyId;
	}
	public void setVfwPolicyId(String vfwPolicyId) {
		this.vfwPolicyId = vfwPolicyId;
	}
	public String getVfwPolicyRuleName() {
		return vfwPolicyRuleName;
	}
	public void setVfwPolicyRuleName(String vfwPolicyRuleName) {
		this.vfwPolicyRuleName = vfwPolicyRuleName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
	public String getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}
	public String getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}
	public String getSourceIpAddress() {
		return sourceIpAddress;
	}
	public void setSourceIpAddress(String sourceIpAddress) {
		this.sourceIpAddress = sourceIpAddress;
	}
	public String getDestIpAddress() {
		return destIpAddress;
	}
	public void setDestIpAddress(String destIpAddress) {
		this.destIpAddress = destIpAddress;
	}
	public String getSourcePort() {
		return sourcePort;
	}
	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}
	public String getDescPort() {
		return descPort;
	}
	public void setDescPort(String descPort) {
		this.descPort = descPort;
	}
	public String getRuleAction() {
		return ruleAction;
	}
	public void setRuleAction(String ruleAction) {
		this.ruleAction = ruleAction;
	}
	
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Override
	public String toString() {
		return "RmNwVfwPolicyRuleVo [vfwPolicyRuleId=" + vfwPolicyRuleId + ", vfwPolicyId=" + vfwPolicyId
				+ ", vfwPolicyRuleName=" + vfwPolicyRuleName + ", remark=" + remark + ", isShare=" + isShare
				+ ", protocolType=" + protocolType + ", ipVersion=" + ipVersion + ", sourceIpAddress=" + sourceIpAddress
				+ ", destIpAddress=" + destIpAddress + ", sourcePort=" + sourcePort + ", descPort=" + descPort
				+ ", ruleAction=" + ruleAction + ", enabled=" + enabled + ", isActive=" + isActive + ", projectId="
				+ projectId + "]";
	}
	




}
