package com.git.cloud.iaas.firewall.service;

import com.git.cloud.iaas.hillstone.model.DnatModel;
import com.git.cloud.iaas.hillstone.model.HillStoneModel;
import com.git.cloud.iaas.hillstone.model.SnatModel;

/**
 * 防火墙NAT接口
 * @author shl
 */
public interface FirewallNatService {
	
	/**
	 * 创建Snat策略
	 * @param snatModel
	 * @return
	 * @throws Exception
	 */
	String createFirewallSnat(HillStoneModel hillStoneModel, SnatModel snatModel) throws Exception;
	
	/**
	 * 根据Snat策略id集合删除Snat策略
	 * @param idArr
	 * @throws Exception
	 */
	void deleteFirewallSnatByIds(HillStoneModel hillStoneModel, String[] idArr) throws Exception;
	
	/**
	 * 创建Dnat策略
	 * @param snatModel
	 * @return
	 * @throws Exception
	 */
	String createFirewallDnat(HillStoneModel hillStoneModel, DnatModel dnatModel) throws Exception;
	
	/**
	 * 根据Dnat策略id集合删除Dnat策略
	 * @param idArr
	 * @throws Exception
	 */
	void deleteFirewallDnatByIds(HillStoneModel hillStoneModel, String[] idArr) throws Exception;
}
