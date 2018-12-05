package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class OpenstackIpAddressPo extends BaseBO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String networkId;
	private String networkType;
	private String ip;
	private String instanceId;
	private String useRelCode;
	private String originalIp;//原始ip地址
	private String nwRuleListId;
	private String ruleListCode;
	private String vlanId;
	private String networkLable;
	private String ipMask;
	private String gateWay;
	private String portGroupId;
	private String platformType;
	private String iaasUuid;
	
	public String getOriginalIp() {
		return originalIp;
	}

	public void setOriginalIp(String originalIp) {
		this.originalIp = originalIp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getUseRelCode() {
		return useRelCode;
	}

	public void setUseRelCode(String useRelCode) {
		this.useRelCode = useRelCode;
	}

	public String getNwRuleListId() {
		return nwRuleListId;
	}

	public void setNwRuleListId(String nwRuleListId) {
		this.nwRuleListId = nwRuleListId;
	}


	public String getVlanId() {
		return vlanId;
	}

	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}

	public String getNetworkLable() {
		return networkLable;
	}

	public void setNetworkLable(String networkLable) {
		this.networkLable = networkLable;
	}

	public String getRuleListCode() {
		return ruleListCode;
	}

	public void setRuleListCode(String ruleListCode) {
		this.ruleListCode = ruleListCode;
	}

	public String getIpMask() {
		return ipMask;
	}

	public void setIpMask(String ipMask) {
		this.ipMask = ipMask;
	}

	public String getGateWay() {
		return gateWay;
	}

	public void setGateWay(String gateWay) {
		this.gateWay = gateWay;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPortGroupId() {
		return portGroupId;
	}

	public void setPortGroupId(String portGroupId) {
		this.portGroupId = portGroupId;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getIaasUuid() {
		return iaasUuid;
	}

	public void setIaasUuid(String iaasUuid) {
		this.iaasUuid = iaasUuid;
	}

}
