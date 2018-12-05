package com.git.cloud.handler.dao;

import java.util.HashMap;
import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
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

public class FirewallAutomationDAOImpl extends CommonDAOImpl implements FirewallAutomationDAO {

	@Override
	public List<RmNwFirewallRequestPo> findRmNwFirewallRequestListBySrId(String srId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("srId", srId);
		return this.findListByParam("findRmNwFirewallRequestListBySrId", map);
	}
	
	@Override
	public RmNwFirewallRequestPo findRmNwFirewallRequestById(String id) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("firewallRequestId", id);
		List<RmNwFirewallRequestPo> list = this.findListByParam("findRmNwFirewallRequestById", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public RmNwOutsideFirewallPo findRmNwOutsideFirewallByFwId(String fwId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("fwId", fwId);
		List<RmNwOutsideFirewallPo> outsideFirewallList = this.findListByParam("findRmNwOutsideFirewallByFwId", map);
		if (outsideFirewallList != null && outsideFirewallList.size() > 0) {
			return outsideFirewallList.get(0);
		}
		return null;
	}
	
	@Override
	public List<RmNwOutsideFirewallPo> findRmNwOutsideFirewallListByNetworkArea(String networkArea) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("networkArea", networkArea);
		return this.findListByParam("findRmNwOutsideFirewallListByNetworkArea", map);
	}
	
	@Override
	public List<RmNwFirewallNatAddressPo> findRmNwFirewallNatAddressForUsed(String networkArea, String externalOrg, String operator, String natPoolId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("networkArea", networkArea);
		map.put("externalOrg", externalOrg);
		map.put("operator", operator);
		map.put("natPoolId", natPoolId);
		return this.findListByParam("findRmNwFirewallNatAddressForUsed", map);
	}
	
	@Override
	public List<RmNwOutsideNatPolicyPo> findRmNwOutsideNatPolicyByNatIp(String natIp, String natType) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("natIp", natIp);
		map.put("natType", natType);
		return this.findListByParam("findRmNwOutsideNatPolicyByNatIp", map);
	}
	
	@Override
	public List<RmNwFirewallPolicyNatPo> findRmNwFirewallPolicyNatList(String networkArea, String externalOrg, String operator) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("networkArea", networkArea);
		map.put("externalOrg", externalOrg);
		map.put("operator", operator);
		return this.findListByParam("findRmNwFirewallPolicyNatList", map);
	}
	
	@Override
	public List<RmNwFirewallNatAddressPo> findRmNwFirewallNatAddressByNatPoolId(String natPoolId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("natPoolId", natPoolId);
		return this.findListByParam("findRmNwFirewallNatAddressByNatPoolId", map);
	}
	
	@Override
	public List<RmNwFirewallNatPoolPo> findRmNwFirewallNatPoolList(String networkArea, String externalOrg, String operator) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("networkArea", networkArea);
		map.put("externalOrg", externalOrg);
		map.put("operator", operator);
		return this.findListByParam("findRmNwFirewallNatPoolList", map);
	}
	
	@Override
	public RmNwVfwPo findRmNwVfwByIp(String ip) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("ip", ip);
		List<RmNwVfwPo> vfwList = this.findListByParam("findRmNwVfwByIp", map);
		if(vfwList != null && vfwList.size() > 0) {
			return vfwList.get(0);
		}
		return null;
	}
	
	@Override
	public void updateRmNwFirewallNatAddress(RmNwFirewallNatAddressPo firewallNatAddress) throws RollbackableBizException {
		this.update("updateRmNwFirewallNatAddress", firewallNatAddress);
	}
	
	@Override
	public void insertRmNwOutsideNatPolicy(List<RmNwOutsideNatPolicyPo> natPolicyList) throws RollbackableBizException {
		this.batchInsert("insertRmNwOutsideNatPolicy", natPolicyList);
	}
	
	@Override
	public void insertRmNwOutsideNatPolicyRef(List<RmNwOutsideNatPolicyRefPo> natPolicyRefList) throws RollbackableBizException {
		this.batchInsert("insertRmNwOutsideNatPolicyRef", natPolicyRefList);
	}
	
	@Override
	public void insertRmNwOutsideFirewallPolicy(List<RmNwOutsideFirewallPolicyPo> hsPolicyList) throws RollbackableBizException {
		this.batchInsert("insertRmNwOutsideFirewallPolicy", hsPolicyList);
	}
	
	@Override
	public void insertRmNwVfwPolicyRule(List<RmNwVfwPolicyRulePo> hwPolicyList) throws RollbackableBizException {
		this.batchInsert("insertRmNwVfwPolicyRule", hwPolicyList);
	}
	
	@Override
	public List<BmSrRrinfoPo> findBmSrRrinfoBySrId(String srId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("srId", srId);
		return this.findListByParam("findBmSrRrinfoBySrId", map);
	}
	
	@Override
	public void deleteRmNwOutsideNatPolicyRefByFirewallRequestId(String firewallRequestId) throws RollbackableBizException {
		this.delete("deleteRmNwOutsideNatPolicyRefByFirewallRequestId", firewallRequestId);
	}
	
	@Override
	public List<RmNwOutsideNatPolicyPo> findRmNwOutsideNatPolicyListByFirewallRequestId(String firewallRequestId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("firewallRequestId", firewallRequestId);
		return this.findListByParam("findRmNwOutsideNatPolicyListByFirewallRequestId", map);
	}
	
	@Override
	public List<RmNwOutsideFirewallPolicyPo> findRmNwOutsideFirewallPolicyListByFirewallRequestId(String firewallRequestId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("firewallRequestId", firewallRequestId);
		return this.findListByParam("findRmNwOutsideFirewallPolicyListByFirewallRequestId", map);
	}
	
	@Override
	public List<RmNwVfwPolicyRulePo> findRmNwInsideFirewallPolicyListByFirewallRequestId(String firewallRequestId) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("firewallRequestId", firewallRequestId);
		return this.findListByParam("findRmNwInsideFirewallPolicyListByFirewallRequestId", map);
	}
	
	@Override
	public void updateOutsideNatPolicyValidStatus(RmNwOutsideNatPolicyPo natPolicy) throws RollbackableBizException {
		this.update("updateOutsideNatPolicyValidStatus", natPolicy);
	}
	
	@Override
	public RmNwFirewallNatAddressPo findRmNwFirewallNatAddressByIp(String natIp) {
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("natIp", natIp);
		List<RmNwFirewallNatAddressPo> firewallNatAddressList = this.findListByParam("findRmNwFirewallNatAddressByIp", map);
		if (firewallNatAddressList != null && firewallNatAddressList.size() > 0) {
			return firewallNatAddressList.get(0);
		}
		return null;
	}
	
	@Override
	public void updateOutsideNatPolicyDeleteStatus(RmNwOutsideNatPolicyPo natPolicy) throws RollbackableBizException {
		this.update("updateOutsideNatPolicyDeleteStatus", natPolicy);
	}
	
	@Override
	public void updateOutsideFirewallPolicyValidStatus(RmNwOutsideFirewallPolicyPo hsPolicy) throws RollbackableBizException {
		this.update("updateOutsideFirewallPolicyValidStatus", hsPolicy);
	}
	
	@Override
	public void updateOutsideFirewallPolicyDeleteStatus(RmNwOutsideFirewallPolicyPo hsPolicy) throws RollbackableBizException {
		this.update("updateOutsideFirewallPolicyDeleteStatus", hsPolicy);
	}

	@Override
	public void updateInsideFirewallPolicyValidStatus(RmNwVfwPolicyRulePo firewallPolicy) throws RollbackableBizException {
		this.update("updateInsideFirewallPolicyValidStatus", firewallPolicy);
	}
	
	@Override
	public void updateInsideFirewallPolicyDeleteStatus(RmNwVfwPolicyRulePo hwPolicy) throws RollbackableBizException {
		this.update("updateInsideFirewallPolicyDeleteStatus", hwPolicy);
	}

	@Override
	public void updateInsideFirewallPolicyRuleIaasUuid(RmNwVfwPolicyRulePo hwPolicy) throws RollbackableBizException {
		this.update("updateInsideFirewallPolicyRuleIaasUuid", hwPolicy);
		
	}

	@Override
	public RmNwVfwPolicyRulePo selectRmNwVfwPolicyRulePoById(String vfwPolicyRuleId) throws RollbackableBizException {
		return (RmNwVfwPolicyRulePo) super.findByID("selectRmNwVfwPolicyRulePoById", vfwPolicyRuleId);
	}

	@Override
	public RmNwVfwPolicyVo selectRmNwVfwPolicyVoById(String vfwPolicyId) throws RollbackableBizException {
		return (RmNwVfwPolicyVo) super.findByID("selectRmNwVfwPolicyVoById", vfwPolicyId);
	}
	
}
