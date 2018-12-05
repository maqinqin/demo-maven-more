package com.git.cloud.handler.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.automation.fw.model.RmNwFirewallNatAddressPo;
import com.git.cloud.handler.automation.fw.model.RmNwFirewallNatPoolPo;
import com.git.cloud.handler.automation.fw.model.RmNwFirewallPolicyNatPo;
import com.git.cloud.handler.automation.fw.model.RmNwFirewallRequestPo;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideFirewallPo;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideFirewallPolicyPo;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideNatPolicyPo;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideNatPolicyRefPo;
import com.git.cloud.handler.automation.fw.model.RmNwVfwPo;
import com.git.cloud.handler.automation.fw.model.RmNwVfwPolicyRulePo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.openstack.model.vo.RmNwVfwPolicyVo;

public interface FirewallAutomationDAO extends ICommonDAO {
	
	/**
	 * 根据服务申请ID获取云防火墙
	 * @param srId
	 * @return
	 */
	List<RmNwFirewallRequestPo> findRmNwFirewallRequestListBySrId(String srId);
	/**
	 * 根据id,获取云防火墙申请对象
	 * @param srId
	 * @return
	 */
	RmNwFirewallRequestPo findRmNwFirewallRequestById(String id);
	
	
	/**
	 * 根据防火墙ID获取云外防火墙
	 * @param fwId
	 * @return
	 */
	RmNwOutsideFirewallPo findRmNwOutsideFirewallByFwId(String fwId);
	
	/**
	 * 根据网络区域获取云外防火墙
	 * @param networkArea
	 * @return
	 */
	List<RmNwOutsideFirewallPo> findRmNwOutsideFirewallListByNetworkArea(String networkArea);
	
	/**
	 * 查询已用的Nat地址
	 * @param networkArea
	 * @param externalOrg
	 * @param operator
	 * @param natPoolId
	 * @return
	 */
	List<RmNwFirewallNatAddressPo> findRmNwFirewallNatAddressForUsed(String networkArea, String externalOrg, String operator, String natPoolId);
	
	/**
	 * 根据NatIp查询下发此NatIp的策略
	 * @param natIp
	 * @param natType
	 * @return
	 */
	List<RmNwOutsideNatPolicyPo> findRmNwOutsideNatPolicyByNatIp(String natIp, String natType);

	/**
	 * 查询指定网络区域、外部机构、运营商的NAT池对应策略NAT
	 * @param networkArea
	 * @param externalOrg
	 * @param operator
	 * @return
	 */
	List<RmNwFirewallPolicyNatPo> findRmNwFirewallPolicyNatList(String networkArea, String externalOrg, String operator);
	
	/**
	 * 根据NAT池ID查询可用的Nat地址对象
	 * @param natPoolId
	 * @return
	 */
	List<RmNwFirewallNatAddressPo> findRmNwFirewallNatAddressByNatPoolId(String natPoolId);

	/**
	 * 查询匹配的Nat池
	 * @param networkArea
	 * @param externalOrg
	 * @param operator
	 * @return
	 */
	List<RmNwFirewallNatPoolPo> findRmNwFirewallNatPoolList(String networkArea, String externalOrg, String operator);
	
	/**
	 * 根据IP地址获取OpenStack防火墙
	 * @param ip
	 * @return
	 */
	RmNwVfwPo findRmNwVfwByIp(String ip);
	
	/**
	 * 更新NAT池地址使用信息
	 * @param firewallNatAddress
	 * @throws RollbackableBizException
	 */
	void updateRmNwFirewallNatAddress(RmNwFirewallNatAddressPo firewallNatAddress) throws RollbackableBizException;

	/**
	 * 插入山石NAT策略
	 * @param natPolicyList
	 * @throws RollbackableBizException 
	 */
	void insertRmNwOutsideNatPolicy(List<RmNwOutsideNatPolicyPo> natPolicyList) throws RollbackableBizException;
	
	/**
	 * 保存山石NAT关联防火墙服务
	 * @param natPolicyRefList
	 * @throws RollbackableBizException
	 */
	void insertRmNwOutsideNatPolicyRef(List<RmNwOutsideNatPolicyRefPo> natPolicyRefList) throws RollbackableBizException;
	
	/**
	 * 插入山石防火墙策略
	 * @param hsPolicyList
	 * @throws RollbackableBizException
	 */
	void insertRmNwOutsideFirewallPolicy(List<RmNwOutsideFirewallPolicyPo> hsPolicyList) throws RollbackableBizException;
	
	/**
	 * 插入华为防火墙策略
	 * @param hwPolicyList
	 * @throws RollbackableBizException
	 */
	void insertRmNwVfwPolicyRule(List<RmNwVfwPolicyRulePo> hwPolicyList) throws RollbackableBizException;
	
	/**
	 * 根据服务请求ID获取资源请求信息
	 * @param srId
	 * @return
	 */
	List<BmSrRrinfoPo> findBmSrRrinfoBySrId(String srId);
	
	/**
	 * 根据防火墙申请ID删除关联的Nat策略关系表
	 * @param firewallRequestId
	 * @throws RollbackableBizException 
	 */
	void deleteRmNwOutsideNatPolicyRefByFirewallRequestId(String firewallRequestId) throws RollbackableBizException;

	/**
	 * 根据防火墙请求ID获取云外防火墙NAT策略
	 * @param firewallRequestId
	 * @return
	 */
	List<RmNwOutsideNatPolicyPo> findRmNwOutsideNatPolicyListByFirewallRequestId(String firewallRequestId);
	
	/**
	 * 根据防火墙请求ID获取云外防火墙安全策略
	 * @param firewallRequestId
	 * @return
	 */
	List<RmNwOutsideFirewallPolicyPo> findRmNwOutsideFirewallPolicyListByFirewallRequestId(String firewallRequestId);

	/**
	 * 根据防火墙请求ID获取云内防火墙安全策略
	 * @param firewallRequestId
	 * @return
	 */
	List<RmNwVfwPolicyRulePo> findRmNwInsideFirewallPolicyListByFirewallRequestId(String firewallRequestId);
	
	/**
	 * 更新云外Nat策略的状态为有效的
	 * 同时修改外系统策略ID
	 * @param natPolicy
	 * @throws RollbackableBizException
	 */
	void updateOutsideNatPolicyValidStatus(RmNwOutsideNatPolicyPo natPolicy) throws RollbackableBizException;
	
	/**
	 * 根据IP查询NAT池IP信息
	 * @param natIp
	 * @return
	 */
	RmNwFirewallNatAddressPo findRmNwFirewallNatAddressByIp(String natIp);
	
	/**
	 * 更新云外Nat策略的状态为已删除
	 * 同时修改外系统策略ID
	 * @param natPolicy
	 * @throws RollbackableBizException
	 */
	void updateOutsideNatPolicyDeleteStatus(RmNwOutsideNatPolicyPo natPolicy) throws RollbackableBizException;
	
	/**
	 * 更新云外策略的状态为有效的
	 * 同时修改外系统策略ID
	 * @param hsPolicy
	 * @throws RollbackableBizException
	 */
	void updateOutsideFirewallPolicyValidStatus(RmNwOutsideFirewallPolicyPo hsPolicy) throws RollbackableBizException;
	
	/**
	 * 更新云外策略的状态为已删除
	 * 同时修改外系统策略ID
	 * @param hsPolicy
	 * @throws RollbackableBizException
	 */
	void updateOutsideFirewallPolicyDeleteStatus(RmNwOutsideFirewallPolicyPo hsPolicy) throws RollbackableBizException;

	/**
	 * 更新云内策略的状态
	 * @param hwPolicy
	 * @throws RollbackableBizException
	 */
	void updateInsideFirewallPolicyValidStatus(RmNwVfwPolicyRulePo hwPolicy) throws RollbackableBizException;
	
	/**
	 * 更新云内策略的状态为已删除
	 * @param hsPolicy
	 * @throws RollbackableBizException
	 */
	void updateInsideFirewallPolicyDeleteStatus(RmNwVfwPolicyRulePo hwPolicy) throws RollbackableBizException;
	/**
	 * 虚拟防火墙策略规则保存targetId
	 * @throws RollbackableBizException
	 */
	public void updateInsideFirewallPolicyRuleIaasUuid(RmNwVfwPolicyRulePo hwPolicy) throws RollbackableBizException;
	/**
	 * 根据主键，查询华为防火墙策略规则信息
	 * @param vfwPolicyRuleId
	 * @return
	 * @throws RollbackableBizException
	 */
	public RmNwVfwPolicyRulePo selectRmNwVfwPolicyRulePoById(String vfwPolicyRuleId) throws RollbackableBizException;
	
	/**
	 * 根据主键，查询华为防火墙策略信息
	 * @param vfwPolicyId
	 * @return
	 * @throws RollbackableBizException
	 */
	RmNwVfwPolicyVo selectRmNwVfwPolicyVoById(String vfwPolicyId) throws RollbackableBizException;
}
