package com.git.cloud.iaas.firewall.model;

import com.git.cloud.iaas.hillstone.model.HillStoneModel;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;

/**
 * 防火墙安全策略参数
 */
public class FirewallPolicyModel {
	
	/**
	 * 公共参数
	 */
	// 安全策略名字
	private String policyName;
	// 华为:流量匹配规则时所执行的动作;山石:对命中此策略的包采取的动作(必须)
	private String action;
	// 协议(必须)
	private String protocol;
	// 源端IP(必须)
	private String sourceIp;
	// 源端端口
	private String sourcePort;
	// 目的IP(必须)
	private String destinationIp;
	// 目的端口
	private String destinationPort;
	
	/**
	 * 华为防火墙安全策略特殊参数
	 */
	// 引用OpenStack类中的属性
	private OpenstackIdentityModel openstackIdentity;
	// 协议版本
	private String ipVersion;
	// 是否共享
	private String isShared;
	// 是否可用
	private String enabled;
	/**
	 * 山石防火墙安全策略特殊参数
	 */
	private HillStoneModel hillStoneModel;
	// 规则编码(必须)
	private String ruleCode;
	// 请求编码
	private String requestCode;
	// 策略ID
	private String targetPolicyId;
	// 描述
	private String description;
	
	private String[] idArr;
	
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	public String getSourcePort() {
		return sourcePort;
	}
	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}
	public String getDestinationIp() {
		return destinationIp;
	}
	public void setDestinationIp(String destinationIp) {
		this.destinationIp = destinationIp;
	}
	public String getDestinationPort() {
		return destinationPort;
	}
	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}
	public OpenstackIdentityModel getOpenstackIdentity() {
		return openstackIdentity;
	}
	public void setOpenstackIdentity(OpenstackIdentityModel openstackIdentity) {
		this.openstackIdentity = openstackIdentity;
	}
	public String getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}
	public String getIsShared() {
		return isShared;
	}
	public void setIsShared(String isShared) {
		this.isShared = isShared;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public HillStoneModel getHillStoneModel() {
		return hillStoneModel;
	}
	public void setHillStoneModel(HillStoneModel hillStoneModel) {
		this.hillStoneModel = hillStoneModel;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public String getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getIdArr() {
		return idArr;
	}
	public void setIdArr(String[] idArr) {
		this.idArr = idArr;
	}
	public String getTargetPolicyId() {
		return targetPolicyId;
	}
	public void setTargetPolicyId(String targetPolicyId) {
		this.targetPolicyId = targetPolicyId;
	}
}
