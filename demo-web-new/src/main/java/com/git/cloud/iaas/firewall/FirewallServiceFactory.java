package com.git.cloud.iaas.firewall;

import com.git.cloud.iaas.firewall.service.FirewallNatService;
import com.git.cloud.iaas.firewall.service.FirewallPolicyService;
import com.git.cloud.iaas.firewall.service.FirewallServiceInsideHwImpl;
import com.git.cloud.iaas.firewall.service.FirewallServiceOutsideHsImpl;

public class FirewallServiceFactory {
	
	/**
	 * 华为云内防火墙策略实例
	 */
	private static FirewallPolicyService firewallPolicyServiceInsideHwImpl;
	/**
	 * 山石云外防火墙策略实例
	 */
	private static FirewallPolicyService firewallPolicyServiceOutsideHsImpl;
	/**
	 * 山石云外防火墙Nat实例
	 */
	private static FirewallNatService firewallNatServiceOutsideHsImpl;
	
	public static FirewallPolicyService getFirewallPolicyServiceInsideHw() {
		if(firewallPolicyServiceInsideHwImpl == null) {
			firewallPolicyServiceInsideHwImpl = new FirewallServiceInsideHwImpl();
		}
		return firewallPolicyServiceInsideHwImpl;
	}
	
	public static FirewallPolicyService getFirewallPolicyServiceOutsideHs() {
		if(firewallPolicyServiceOutsideHsImpl == null) {
			firewallPolicyServiceOutsideHsImpl = new FirewallServiceOutsideHsImpl();
		}
		return firewallPolicyServiceOutsideHsImpl;
	}
	
	public static FirewallNatService getFirewallNatServiceOutsideHs() {
		if(firewallNatServiceOutsideHsImpl == null) {
			firewallNatServiceOutsideHsImpl = new FirewallServiceOutsideHsImpl();
		}
		return firewallNatServiceOutsideHsImpl;
	}
}
