package com.git.cloud.handler.automation.fw.model;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwOutsideNatPolicyRefPo extends BaseBO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
    private String natPolicyId;
    private String firewallRequestId;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNatPolicyId() {
		return natPolicyId;
	}
	public void setNatPolicyId(String netPolicyId) {
		this.natPolicyId = netPolicyId;
	}
	public String getFirewallRequestId() {
		return firewallRequestId;
	}
	public void setFirewallRequestId(String firewallRequestId) {
		this.firewallRequestId = firewallRequestId;
	}
	@Override
	public String getBizId() {
		return this.id;
	}
}