package com.git.cloud.iaas.firewall.service;

import com.git.cloud.iaas.firewall.FirewallServiceFactory;
import com.git.cloud.iaas.firewall.model.FirewallPolicyModel;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.FwRuleRestModel;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;

/**
 * 华为云内防火墙
 * @author shl
 */
public class FirewallServiceInsideHwImpl implements FirewallPolicyService {

	@Override
	public String createFirewallPolicy(FirewallPolicyModel firewallPolicyModel) throws Exception {
		String policyRuleId = "";
		FwRuleRestModel fwr = new FwRuleRestModel();
		fwr.setVfwPolicyRuleName(firewallPolicyModel.getPolicyName());
		fwr.setRuleAction(firewallPolicyModel.getAction());
		fwr.setProtocolType(firewallPolicyModel.getProtocol());
		fwr.setSourcePort(firewallPolicyModel.getSourcePort());
		fwr.setDescPort(firewallPolicyModel.getDestinationPort());
		fwr.setSourceIpAddress(firewallPolicyModel.getSourceIp());
		fwr.setDestIpAddress(firewallPolicyModel.getDestinationIp());
		fwr.setIpVersion(firewallPolicyModel.getIpVersion());
		fwr.setIsShare(firewallPolicyModel.getIsShared());
		fwr.setEnabled(firewallPolicyModel.getEnabled());
		OpenstackIdentityModel openstackIdentity = firewallPolicyModel.getOpenstackIdentity();
		policyRuleId = IaasInstanceFactory.networkInstance(openstackIdentity.getVersion()).createFwr(openstackIdentity, fwr);
		IaasInstanceFactory.networkInstance(openstackIdentity.getVersion()).addFwrInFwp(openstackIdentity, firewallPolicyModel.getTargetPolicyId(), policyRuleId);
		return policyRuleId;
	}
	
	@Override
	public void deleteFirewallPolicyByIds(FirewallPolicyModel firewallPolicyModel) throws Exception {
		String [] idArr = firewallPolicyModel.getIdArr();
		OpenstackIdentityModel openstackIdentity = firewallPolicyModel.getOpenstackIdentity();
		for (int i = 0 ; i < idArr.length ; i ++) {
			IaasInstanceFactory.networkInstance(openstackIdentity.getVersion()).removeFwrInFwp(openstackIdentity, firewallPolicyModel.getTargetPolicyId(), idArr[i]);
			IaasInstanceFactory.networkInstance(openstackIdentity.getVersion()).deleteFwr(openstackIdentity, idArr[i]);
		}
	}
}
