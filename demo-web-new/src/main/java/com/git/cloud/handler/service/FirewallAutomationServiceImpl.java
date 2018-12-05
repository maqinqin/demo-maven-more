package com.git.cloud.handler.service;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.automation.fw.model.NatAddressStatusEnum;
import com.git.cloud.handler.automation.fw.model.NatStatusEnum;
import com.git.cloud.handler.automation.fw.model.NatTypeEnum;
import com.git.cloud.handler.automation.fw.model.PolicyStatusEnum;
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
import com.git.cloud.handler.dao.FirewallAutomationDAO;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.openstack.model.vo.RmNwVfwPolicyVo;

public class FirewallAutomationServiceImpl implements FirewallAutomationService {

	private FirewallAutomationDAO firewallAutomationDAOImpl;
	private IBmSrDao bmSrDaoImpl;
	
	public void setBmSrDaoImpl(IBmSrDao bmSrDaoImpl) {
		this.bmSrDaoImpl = bmSrDaoImpl;
	}

	public void setFirewallAutomationDAOImpl(FirewallAutomationDAO firewallAutomationDAOImpl) {
		this.firewallAutomationDAOImpl = firewallAutomationDAOImpl;
	}

	@Override
	public RmNwFirewallRequestPo findRmNwFirewallRequestBySrId(String srId) {
		List<RmNwFirewallRequestPo> rmNwFirewallRequestList = firewallAutomationDAOImpl.findRmNwFirewallRequestListBySrId(srId);
		if (rmNwFirewallRequestList != null && rmNwFirewallRequestList.size() > 0) {
			return rmNwFirewallRequestList.get(0);
		}
		return null;
	}
	
	@Override
	public RmNwFirewallRequestPo findRmNwFirewallRequestById(String id) {
		return firewallAutomationDAOImpl.findRmNwFirewallRequestById(id);
	}
	
	@Override
	public RmNwOutsideFirewallPo findRmNwOutsideFirewallByFwId(String fwId) {
		return firewallAutomationDAOImpl.findRmNwOutsideFirewallByFwId(fwId);
	}
	
	@Override
	public RmNwOutsideFirewallPo findRmNwOutsideFirewallByNetworkArea(String networkArea) {
		List<RmNwOutsideFirewallPo> outsideFirewallList = firewallAutomationDAOImpl.findRmNwOutsideFirewallListByNetworkArea(networkArea);
		if (outsideFirewallList != null && outsideFirewallList.size() > 0) {
			return outsideFirewallList.get(0);
		}
		return null;
	}
	
	@Override
	public List<RmNwFirewallNatAddressPo> findRmNwFirewallNatAddressForUsed(String networkArea, String externalOrg, String operator, String natPoolId) {
		return firewallAutomationDAOImpl.findRmNwFirewallNatAddressForUsed(networkArea, externalOrg, operator, natPoolId);
	}
	
	@Override
	public List<RmNwOutsideNatPolicyPo> findRmNwOutsideNatPolicyByNatIp(String natIp, String natType) {
		return firewallAutomationDAOImpl.findRmNwOutsideNatPolicyByNatIp(natIp, natType);
	}

	@Override
	public List<RmNwFirewallPolicyNatPo> findRmNwFirewallPolicyNatList(String networkArea, String externalOrg, String operator) {
		return firewallAutomationDAOImpl.findRmNwFirewallPolicyNatList(networkArea, externalOrg, operator);
	}
	
	@Override
	public RmNwFirewallNatAddressPo findRmNwFirewallNatAddressByNatPoolId(String natPoolId) {
		List<RmNwFirewallNatAddressPo> firewallNatAddressList = firewallAutomationDAOImpl.findRmNwFirewallNatAddressByNatPoolId(natPoolId);
		if (firewallNatAddressList != null && firewallNatAddressList.size() > 0) {
			return firewallNatAddressList.get(0);
		}
		return null;
	}
	
	@Override
	public List<RmNwFirewallNatPoolPo> findRmNwFirewallNatPoolList(String networkArea, String externalOrg, String operator) {
		return firewallAutomationDAOImpl.findRmNwFirewallNatPoolList(networkArea, externalOrg, operator);
	}
	
	@Override
	public RmNwVfwPo findRmNwVfwByIp(String ip) {
		return firewallAutomationDAOImpl.findRmNwVfwByIp(ip);
	}
	
	public void savePolicy(List<RmNwFirewallNatAddressPo> firewallNatAddressList, List<RmNwOutsideNatPolicyPo> natPolicyList,
			List<RmNwOutsideNatPolicyRefPo> natPolicyRefList, List<RmNwOutsideFirewallPolicyPo> hsPolicyList, List<RmNwVfwPolicyRulePo> hwPolicyList) throws RollbackableBizException {
		if (firewallNatAddressList != null && firewallNatAddressList.size() > 0) {
			// 更新NAT池的IP地址使用详情
			this.updateRmNwFirewallNatAddress(firewallNatAddressList);
		}
		if (natPolicyList != null && natPolicyList.size() > 0) {
			// 保存山石NAT策略
			this.insertRmNwOutsideNatPolicy(natPolicyList);
		}
		if (natPolicyRefList != null && natPolicyRefList.size() > 0) {
			// 保存山石NAT关联防火墙服务
			this.insertRmNwOutsideNatPolicyRef(natPolicyRefList);
		}
		if (hsPolicyList != null && hsPolicyList.size() > 0) {
			// 保存山石安全策略
			this.insertRmNwOutsideFirewallPolicy(hsPolicyList);
		}
		if (hwPolicyList != null && hwPolicyList.size() > 0) {
			// 保存华为安全策略
			this.insertRmNwVfwPolicyRule(hwPolicyList);
		}
	}
	
	@Override
	public void updateRmNwFirewallNatAddress(List<RmNwFirewallNatAddressPo> firewallNatAddressList) throws RollbackableBizException {
		if (firewallNatAddressList != null && firewallNatAddressList.size() > 0) {
			for (int i = 0 ; i < firewallNatAddressList.size() ; i ++) {
				firewallAutomationDAOImpl.updateRmNwFirewallNatAddress(firewallNatAddressList.get(i));
			}
		}
	}
	
	@Override
	public void insertRmNwOutsideNatPolicy(List<RmNwOutsideNatPolicyPo> natPolicyList) throws RollbackableBizException {
		firewallAutomationDAOImpl.insertRmNwOutsideNatPolicy(natPolicyList);
	}
	
	@Override
	public void insertRmNwOutsideNatPolicyRef(List<RmNwOutsideNatPolicyRefPo> natPolicyRefList) throws RollbackableBizException {
		firewallAutomationDAOImpl.insertRmNwOutsideNatPolicyRef(natPolicyRefList);
	}
	
	@Override
	public void insertRmNwOutsideFirewallPolicy(List<RmNwOutsideFirewallPolicyPo> hsPolicyList) throws RollbackableBizException {
		firewallAutomationDAOImpl.insertRmNwOutsideFirewallPolicy(hsPolicyList);
	}
	
	@Override
	public void insertRmNwVfwPolicyRule(List<RmNwVfwPolicyRulePo> hwPolicyList) throws RollbackableBizException {
		firewallAutomationDAOImpl.insertRmNwVfwPolicyRule(hwPolicyList);
	}
	
	@Override
	public BmSrRrinfoPo findBmSrRrinfoBySrId(String srId) {
		List<BmSrRrinfoPo> rrinfoList = firewallAutomationDAOImpl.findBmSrRrinfoBySrId(srId);
		if (rrinfoList != null && rrinfoList.size() > 0) {
			return rrinfoList.get(0);
		}
		return null;
	}
	
	@Override
	public void deleteRmNwOutsideNatPolicyRefByFirewallRequestId(String firewallRequestId) throws RollbackableBizException {
		firewallAutomationDAOImpl.deleteRmNwOutsideNatPolicyRefByFirewallRequestId(firewallRequestId);
	}
	
	@Override
	public List<RmNwOutsideNatPolicyPo> findRmNwOutsideNatPolicyListByFirewallRequestId(String firewallRequestId) {
		return firewallAutomationDAOImpl.findRmNwOutsideNatPolicyListByFirewallRequestId(firewallRequestId);
	}
	
	@Override
	public List<RmNwOutsideFirewallPolicyPo> findRmNwOutsideFirewallPolicyListByFirewallRequestId(String firewallRequestId) {
		return firewallAutomationDAOImpl.findRmNwOutsideFirewallPolicyListByFirewallRequestId(firewallRequestId);
	}
	
	@Override
	public List<RmNwVfwPolicyRulePo> findRmNwInsideFirewallPolicyListByFirewallRequestId(String firewallRequestId) {
		return firewallAutomationDAOImpl.findRmNwInsideFirewallPolicyListByFirewallRequestId(firewallRequestId);
	}
	
	@Override
	public void updateOutsideNatPolicyValidStatus(RmNwOutsideNatPolicyPo natPolicy) throws RollbackableBizException {
		firewallAutomationDAOImpl.updateOutsideNatPolicyValidStatus(natPolicy);
	}

	@Override
	public void updateOutsideNatPolicyDeleteStatus(List<RmNwOutsideNatPolicyPo> natPolicyList) throws RollbackableBizException {
		int natPolicyLen = natPolicyList == null ? 0 : natPolicyList.size();
		List<RmNwFirewallNatAddressPo> firewallNatAddressList = new ArrayList<RmNwFirewallNatAddressPo> ();
		RmNwOutsideNatPolicyPo natPolicy;
		RmNwFirewallNatAddressPo firewallNatAddress;
		for (int i = 0 ; i < natPolicyLen ; i ++) {
			natPolicy = natPolicyList.get(i);
			firewallNatAddress = firewallAutomationDAOImpl.findRmNwFirewallNatAddressByIp(natPolicy.getNatIp());
			if (firewallNatAddress != null) {
				if (natPolicy.getNatType().equals(NatTypeEnum.DNAT.getValue())) {
					firewallNatAddress.setDnatIp("");
					if (firewallNatAddress.getSnatIp() != null && !"".equals(firewallNatAddress.getSnatIp())) {
						firewallNatAddress.setStatus(NatAddressStatusEnum.SNAT.getValue());
					} else {
						firewallNatAddress.setStatus(NatAddressStatusEnum.AVAILABLE.getValue());
					}
					firewallNatAddressList.add(firewallNatAddress);
				} else if (natPolicy.getNatType().equals(NatTypeEnum.SNAT.getValue())) {
					if (firewallNatAddress.getSnatIp().equals(natPolicy.getSrcIp())) {
						firewallNatAddress.setSnatIp("");
					} else {
						String newSnatIp = "," + firewallNatAddress.getSnatIp() + ",";
						newSnatIp = newSnatIp.replace("," + natPolicy.getSrcIp() + ",", ",");
						firewallNatAddress.setSnatIp(newSnatIp.substring(1, newSnatIp.length() - 1));
					}
					if (firewallNatAddress.getSnatIp() != null && !"".equals(firewallNatAddress.getSnatIp())) {
						firewallNatAddress.setStatus(NatAddressStatusEnum.SNAT.getValue());
					} else {
						firewallNatAddress.setStatus(NatAddressStatusEnum.AVAILABLE.getValue());
					}
					firewallNatAddressList.add(firewallNatAddress);
				}
			}
			natPolicy.setStatus(NatStatusEnum.DELETE.getValue());
			firewallAutomationDAOImpl.updateOutsideNatPolicyDeleteStatus(natPolicy);
		}
		this.updateRmNwFirewallNatAddress(firewallNatAddressList);
	}
	
	@Override
	public void updateOutsideFirewallPolicyValidStatus(RmNwOutsideFirewallPolicyPo hsPolicy) throws RollbackableBizException {
		firewallAutomationDAOImpl.updateOutsideFirewallPolicyValidStatus(hsPolicy);
	}
	
	@Override
	public void updateOutsideFirewallPolicyDeleteStatus(List<RmNwOutsideFirewallPolicyPo> hsPolicyList) throws RollbackableBizException {
		int hsPolicyLen = hsPolicyList == null ? 0 : hsPolicyList.size();
		for (int i = 0 ; i < hsPolicyLen ; i ++) {
			hsPolicyList.get(i).setStatus(PolicyStatusEnum.DELETE.getValue());
			firewallAutomationDAOImpl.updateOutsideFirewallPolicyDeleteStatus(hsPolicyList.get(i));
		}
	}
	
	@Override
	public void updateInsideFirewallPolicyValidStatus(RmNwVfwPolicyRulePo hsPolicy) throws RollbackableBizException {
		firewallAutomationDAOImpl.updateInsideFirewallPolicyValidStatus(hsPolicy);
	}
	
	@Override
	public void updateInsideFirewallPolicyDeleteStatus(List<RmNwVfwPolicyRulePo> hwPolicyList) throws RollbackableBizException {
		int hwPolicyLen = hwPolicyList == null ? 0 : hwPolicyList.size();
		for (int i = 0 ; i < hwPolicyLen ; i ++) {
			hwPolicyList.get(i).setStatus(PolicyStatusEnum.DELETE.getValue());
			firewallAutomationDAOImpl.updateInsideFirewallPolicyDeleteStatus(hwPolicyList.get(i));
		}
	}
	
	@Override
	public void updateBmSrStatus(String srId, String srStatusCode) throws RollbackableBizException {
		bmSrDaoImpl.updateBmSrStatus(srId, srStatusCode);
	}

	@Override
	public void updateInsideFirewallPolicyRuleIaasUuid(RmNwVfwPolicyRulePo hwPolicy) throws RollbackableBizException {
		firewallAutomationDAOImpl.updateInsideFirewallPolicyRuleIaasUuid(hwPolicy);
		
	}

	@Override
	public RmNwVfwPolicyRulePo selectRmNwVfwPolicyRulePoById(String vfwPolicyRuleId) throws RollbackableBizException {
		return firewallAutomationDAOImpl.selectRmNwVfwPolicyRulePoById(vfwPolicyRuleId);
	}

	@Override
	public RmNwVfwPolicyVo selectRmNwVfwPolicyVoById(String vfwPolicyId) throws RollbackableBizException {
		return firewallAutomationDAOImpl.selectRmNwVfwPolicyVoById(vfwPolicyId);
	}

}
