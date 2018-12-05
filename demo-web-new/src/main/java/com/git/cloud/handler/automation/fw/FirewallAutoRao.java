package com.git.cloud.handler.automation.fw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideFirewallPo;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideFirewallPolicyPo;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideNatPolicyPo;
import com.git.cloud.handler.automation.fw.model.RmNwVfwPolicyRulePo;
import com.git.cloud.handler.service.FirewallAutomationService;
import com.git.cloud.iaas.firewall.FirewallServiceFactory;
import com.git.cloud.iaas.firewall.common.FirewallConstants;
import com.git.cloud.iaas.firewall.model.FirewallPolicyModel;
import com.git.cloud.iaas.hillstone.model.DnatModel;
import com.git.cloud.iaas.hillstone.model.HillStoneModel;
import com.git.cloud.iaas.hillstone.model.SnatModel;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;

import sun.misc.BASE64Encoder;

public class FirewallAutoRao {
	IProjectVpcDao projectVpcDao = (IProjectVpcDao) WebApplicationManager.getBean("projectVpcDao");
	FirewallAutomationService firewallAutomationServiceImpl = (FirewallAutomationService) WebApplicationManager.getBean("firewallAutomationServiceImpl");
	public String createHillstoneDnat(RmNwOutsideNatPolicyPo natPolicy) throws Exception {
		if (this.isOperateHS()) {
			// DNAT fromIp:源IP;toIp:池IP;transToIp:目的IP
			DnatModel dnatModel = new DnatModel();
			dnatModel.setFromIp(natPolicy.getSrcIp());
			dnatModel.setToIp(natPolicy.getNatIp());
			dnatModel.setTransToIp(natPolicy.getDstIp());
			dnatModel.setSrcPort(natPolicy.getSrcPort());
			dnatModel.setDstPort(natPolicy.getDstPort());
			dnatModel.setDescription(natPolicy.getDescription());
			return FirewallServiceFactory.getFirewallNatServiceOutsideHs().createFirewallDnat(this.getHillStoneModelByFwId(natPolicy.getFwId()), dnatModel);
		} else {
			return this.generatorVirtualID("HSD");
		}
	}
	
	public void deleteHillstoneDnat(String fwId, String[] idArr) throws Exception {
		if (this.isOperateHS()) {
			FirewallServiceFactory.getFirewallNatServiceOutsideHs().deleteFirewallDnatByIds(this.getHillStoneModelByFwId(fwId), idArr);
		}
	}
	
	public String createHillstoneSnat(RmNwOutsideNatPolicyPo natPolicy) throws Exception {
		if (this.isOperateHS()) {
			// SNAT fromIp:源IP;toIp:目的IP;transToIp:池IP
			SnatModel snatModel = new SnatModel();
			snatModel.setFromIp(natPolicy.getSrcIp());
			snatModel.setToIp(natPolicy.getDstIp());
			snatModel.setTransToIp(natPolicy.getNatIp());
			snatModel.setDescription(natPolicy.getDescription());
			return FirewallServiceFactory.getFirewallNatServiceOutsideHs().createFirewallSnat(this.getHillStoneModelByFwId(natPolicy.getFwId()), snatModel);
		} else {
			return this.generatorVirtualID("HSS");
		}
	}
	
	public void deleteHillstoneSnat(String fwId, String[] idArr) throws Exception {
		if (this.isOperateHS()) {
			FirewallServiceFactory.getFirewallNatServiceOutsideHs().deleteFirewallSnatByIds(this.getHillStoneModelByFwId(fwId), idArr);
		}
	}
	
	public String createHillstonePolicy(RmNwOutsideFirewallPolicyPo hsPolicy) throws Exception {
		if (this.isOperateHS()) {
			FirewallPolicyModel firewallPolicyModel = new FirewallPolicyModel();
			firewallPolicyModel.setProtocol(hsPolicy.getProtocol());
			firewallPolicyModel.setRequestCode(hsPolicy.getRequestCode());
			firewallPolicyModel.setSourceIp(hsPolicy.getSrcIp());
			firewallPolicyModel.setSourcePort(hsPolicy.getSrcPort());
			firewallPolicyModel.setDestinationIp(hsPolicy.getDstIp());
			firewallPolicyModel.setDestinationPort(hsPolicy.getDstPort());
			firewallPolicyModel.setHillStoneModel(this.getHillStoneModelByFwId(hsPolicy.getFwId()));
			return FirewallServiceFactory.getFirewallPolicyServiceOutsideHs().createFirewallPolicy(firewallPolicyModel);
		} else {
			return this.generatorVirtualID("HSP");
		}
	}
	
	public void deleteHillstonePolicy(String fwId, String[] idArr) throws Exception {
		if (this.isOperateHS()) {
			FirewallPolicyModel firewallPolicyModel = new FirewallPolicyModel();
			firewallPolicyModel.setIdArr(idArr);
			firewallPolicyModel.setHillStoneModel(this.getHillStoneModelByFwId(fwId));
			FirewallServiceFactory.getFirewallPolicyServiceOutsideHs().deleteFirewallPolicyByIds(firewallPolicyModel);
		}
	}
	
	public String createHuaweiPolicy(RmNwVfwPolicyRulePo hwPolicy) throws Exception {
		if (this.isOperateHW()) {
			OpenstackIdentityModel openstackIdentity = new OpenstackIdentityModel();
			String projectId = projectVpcDao.findProjectByProjectId(hwPolicy.getProjectId()).getIaasUuid();
			openstackIdentity.setProjectId(projectId);
			openstackIdentity.setProjectName(hwPolicy.getProjectName());
			openstackIdentity.setOpenstackIp(hwPolicy.getOpenstackIp());
			openstackIdentity.setDomainName(hwPolicy.getDomainName());
			openstackIdentity.setVersion(hwPolicy.getVersion());
			openstackIdentity.setManageOneIp(hwPolicy.getManageOneIp());
			FirewallPolicyModel firewallPolicyModel = new FirewallPolicyModel();
			firewallPolicyModel.setPolicyName(hwPolicy.getVfwPolicyRuleName());
			firewallPolicyModel.setAction(hwPolicy.getRuleAction());
			firewallPolicyModel.setProtocol(hwPolicy.getProtocolType());
			firewallPolicyModel.setSourceIp(hwPolicy.getSourceIpAddress());
			firewallPolicyModel.setSourcePort(hwPolicy.getSourcePort());
			firewallPolicyModel.setDestinationIp(hwPolicy.getDestIpAddress());
			firewallPolicyModel.setDestinationPort(hwPolicy.getDescPort());
			firewallPolicyModel.setIpVersion(hwPolicy.getIpVersion());
			firewallPolicyModel.setIsShared(hwPolicy.getIsShare());
			firewallPolicyModel.setEnabled(hwPolicy.getEnabled());
			String targetPolicyId = firewallAutomationServiceImpl.selectRmNwVfwPolicyVoById(hwPolicy.getVfwPolicyId()).getIaasUuid();
			firewallPolicyModel.setTargetPolicyId(targetPolicyId);
			firewallPolicyModel.setOpenstackIdentity(openstackIdentity);
			String policyRuleId = FirewallServiceFactory.getFirewallPolicyServiceInsideHw().createFirewallPolicy(firewallPolicyModel);
			return policyRuleId;
		} else {
			return this.generatorVirtualID("HWP");
		}
	}
	
	public void deleteHuaweiPolicy(RmNwVfwPolicyRulePo hwPolicy, String[] idArr) throws Exception {
		if (this.isOperateHW()) {
			String projectId = projectVpcDao.findProjectByProjectId(hwPolicy.getProjectId()).getIaasUuid();
			OpenstackIdentityModel openstackIdentity = new OpenstackIdentityModel();
			openstackIdentity.setProjectId(projectId);
			openstackIdentity.setProjectName(hwPolicy.getProjectName());
			openstackIdentity.setOpenstackIp(hwPolicy.getOpenstackIp());
			openstackIdentity.setDomainName(hwPolicy.getDomainName());
			openstackIdentity.setVersion(hwPolicy.getVersion());
			openstackIdentity.setManageOneIp(hwPolicy.getManageOneIp());
			FirewallPolicyModel firewallPolicyModel = new FirewallPolicyModel();
			firewallPolicyModel.setIdArr(idArr);
			String targetPolicyId = firewallAutomationServiceImpl.selectRmNwVfwPolicyVoById(hwPolicy.getVfwPolicyId()).getIaasUuid();
			firewallPolicyModel.setTargetPolicyId(targetPolicyId);
			firewallPolicyModel.setOpenstackIdentity(openstackIdentity);
			FirewallServiceFactory.getFirewallPolicyServiceInsideHw().deleteFirewallPolicyByIds(firewallPolicyModel);
		}
	}

	private boolean isOperateHW() {
		if (FirewallConstants.HS_TEST_FLAG) {
			return true;
		} else {
			ParameterService parameterServiceImpl = (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
			try {
				String operateHW = parameterServiceImpl.getParamValueByName("FIREWALL_OPERATE_HW");
				if (operateHW != null && "true".equals(operateHW.toLowerCase())) {
					return true;
				}
			} catch (RollbackableBizException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
	
	private boolean isOperateHS() {
		if (FirewallConstants.HS_TEST_FLAG) {
			return true;
		} else {
			ParameterService parameterServiceImpl = (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
			try {
				String operateHS = parameterServiceImpl.getParamValueByName("FIREWALL_OPERATE_HS");
				if (operateHS != null && "true".equals(operateHS.toLowerCase())) {
					return true;
				}
			} catch (RollbackableBizException e) {
				e.printStackTrace();
			}
			return false;
		}
	}
	
	private HillStoneModel getHillStoneModelByFwId(String fwId) throws Exception {
		if (FirewallConstants.HS_TEST_FLAG) {
			return new HillStoneModel();
		} else {
			FirewallAutomationService firewallAutomationServiceImpl = (FirewallAutomationService) WebApplicationManager.getBean("firewallAutomationServiceImpl");
			RmNwOutsideFirewallPo outsideFirewall = firewallAutomationServiceImpl.findRmNwOutsideFirewallByFwId(fwId);
			if (outsideFirewall == null) {
				throw new Exception (" there is no outside firewall for fwId : " + fwId);
			}
			HillStoneModel hillStoneModel = new HillStoneModel();
			hillStoneModel.setRestIp(outsideFirewall.getIp());
			hillStoneModel.setRestPort(outsideFirewall.getPort());
			hillStoneModel.setUsername(outsideFirewall.getLoginUser());
//			hillStoneModel.setPassword(outsideFirewall.getLoginPwd());
			// base64加密
			String str = (new BASE64Encoder()).encodeBuffer(outsideFirewall.getLoginPwd().getBytes());
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			String pwd = m.replaceAll("");
			hillStoneModel.setPassword(pwd);
			return hillStoneModel;
		}
	}
	
	private String generatorVirtualID(String type) {
		return type + "-" + UUIDGenerator.getUUID();
	}
	
	public static void main(String[] args) {
		FirewallAutoRao firewallAutoRao = new FirewallAutoRao();
		String [] idArr = new String [] {"1", "2"};
		String fwId = "1";
		RmNwOutsideNatPolicyPo dnatPolicy = new RmNwOutsideNatPolicyPo();
		// 10.10.1.1;10.10.1.1-3;10.10.1.1/32
		dnatPolicy.setSrcIp("10.10.1.1/32");
		dnatPolicy.setNatIp("26.86.1.1");
		dnatPolicy.setDstIp("21.86.1.1");
		dnatPolicy.setSrcPort("any");
		dnatPolicy.setDstPort("80");
		dnatPolicy.setDescription("test");
		RmNwOutsideNatPolicyPo snatPolicy = new RmNwOutsideNatPolicyPo();
		// 21.86.1.1;21.86.1.1-3;21.86.1.1/32
		snatPolicy.setSrcIp("21.86.1.1");
		snatPolicy.setNatIp("26.86.1.1");
		// 10.10.1.1;10.10.1.1-3;10.10.1.1/32
		snatPolicy.setDstIp("10.10.1.1");
		snatPolicy.setDescription("test");
		RmNwOutsideFirewallPolicyPo hsPolicy = new RmNwOutsideFirewallPolicyPo();
		// any;tcp;udp;icmp
		hsPolicy.setProtocol("any");
		hsPolicy.setRequestCode("test001");
		// 10.10.1.1;10.10.1.1-3;10.10.1.1/32
		hsPolicy.setSrcIp("10.10.1.1-3");
		// 21.86.1.1;21.86.1.1-3;21.86.1.1/32
		hsPolicy.setDstIp("21.86.1.1-2");
		hsPolicy.setSrcPort("any");
		hsPolicy.setDstPort("80");
		hsPolicy.setFwId(fwId);
		try {
//			firewallAutoRao.createHillstoneDnat(dnatPolicy);
//			firewallAutoRao.createHillstoneSnat(snatPolicy);
			firewallAutoRao.createHillstonePolicy(hsPolicy);
//			firewallAutoRao.deleteHillstoneDnat(fwId, idArr);
//			firewallAutoRao.deleteHillstoneSnat(fwId, idArr);
			firewallAutoRao.deleteHillstonePolicy(fwId, idArr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
