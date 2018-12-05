package com.git.cloud.iaas.firewall.service;

import com.git.cloud.iaas.firewall.model.FirewallPolicyModel;

/**
 * 防火墙策略接口
 * @author shl
 */
public interface FirewallPolicyService {
	
	/**
	 * 创建防火墙安全策略
	 * @param firewallPolicyModel 防火墙安全策略对象
	 * @return 返回防火墙策略ID
	 */
	String createFirewallPolicy(FirewallPolicyModel firewallPolicyModel) throws Exception;
	
	/**
	 * 根据防火墙安全策略id集合删除安全策略
	 * @param idArr 要删除的策略ID集合
	 */
	void deleteFirewallPolicyByIds(FirewallPolicyModel firewallPolicyModel) throws Exception;
}
