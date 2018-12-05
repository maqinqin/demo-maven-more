package com.git.cloud.handler.automation.fw.model;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwVfwPolicyRulePo extends BaseBO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String vfwPolicyRuleId;	/*虚拟防火墙策略规则ID*/
	private String firewallRequestId;
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
	private String status;
	private String isActive;
	private String projectId;
	private String projectName;
	private String vmMsId;
	private String openstackIp;
	private String domainName;
	private String version;
	private String manageOneIp;
	private String iaasUuid;
	
	public String getIaasUuid() {
		return iaasUuid;
	}
	public void setIaasUuid(String iaasUuid) {
		this.iaasUuid = iaasUuid;
	}
	public String getVfwPolicyRuleId() {
		return vfwPolicyRuleId;
	}
	public void setVfwPolicyRuleId(String vfwPolicyRuleId) {
		this.vfwPolicyRuleId = vfwPolicyRuleId;
	}
	public String getFirewallRequestId() {
		return firewallRequestId;
	}
	public void setFirewallRequestId(String firewallRequestId) {
		this.firewallRequestId = firewallRequestId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getVmMsId() {
		return vmMsId;
	}
	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getOpenstackIp() {
		return openstackIp;
	}
	public void setOpenstackIp(String openstackIp) {
		this.openstackIp = openstackIp;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getManageOneIp() {
		return manageOneIp;
	}
	public void setManageOneIp(String manageOneIp) {
		this.manageOneIp = manageOneIp;
	}
	
}
