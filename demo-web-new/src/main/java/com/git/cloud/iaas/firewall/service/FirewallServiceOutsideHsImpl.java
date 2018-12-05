package com.git.cloud.iaas.firewall.service;

import com.git.cloud.iaas.firewall.model.FirewallPolicyModel;
import com.git.cloud.iaas.hillstone.model.AddrBookModel;
import com.git.cloud.iaas.hillstone.model.DnatModel;
import com.git.cloud.iaas.hillstone.model.HillStoneModel;
import com.git.cloud.iaas.hillstone.model.PolicyModel;
import com.git.cloud.iaas.hillstone.model.ServiceBookModel;
import com.git.cloud.iaas.hillstone.model.SnatModel;
import com.git.cloud.iaas.hillstone.service.HillStoneFirewallService;
import com.git.cloud.iaas.hillstone.service.HillStoneFirewallServiceImpl;

/**
 * 山石云外防火墙
 * @author shl
 */
public class FirewallServiceOutsideHsImpl implements FirewallPolicyService, FirewallNatService {

	private HillStoneFirewallService hillStoneFirewallServiceImpl;
	
	private HillStoneFirewallService getHillStoneFirewallService() {
		if (hillStoneFirewallServiceImpl == null) {
			hillStoneFirewallServiceImpl = new HillStoneFirewallServiceImpl();
		}
		return hillStoneFirewallServiceImpl;
	}
	
	@Override
	public String createFirewallPolicy(FirewallPolicyModel firewallPolicyModel) throws Exception {
		StringBuffer servicebookName = new StringBuffer();
		String protocol = firewallPolicyModel.getProtocol();
		String srcPort = ("any".equals(protocol) || "icmp".equals(protocol)) ? "any" : firewallPolicyModel.getSourcePort().replaceAll(" ", "");
		String [] srcPortArr = srcPort.split(",");
		String dstPort = ("any".equals(protocol) || "icmp".equals(protocol)) ? "any" : firewallPolicyModel.getDestinationPort().replaceAll(" ", "");
		String [] dstPortArr = dstPort.split(",");
		for (int i = 0 ; i < srcPortArr.length ; i ++) {
			for (int j = 0 ; j < dstPortArr.length ; j ++) {
				String sbName = this.initServicebookName(protocol, srcPortArr[i], dstPortArr[j]);
				ServiceBookModel serviceBookModel = new ServiceBookModel();
				serviceBookModel.setServicebookName(sbName);
				serviceBookModel.setProtocol(protocol);
				serviceBookModel.setDescription(sbName + "-" + firewallPolicyModel.getRequestCode());
				serviceBookModel.setSrcPort(srcPortArr[i]);
				serviceBookModel.setDstPort(dstPortArr[j]);
				servicebookName.append(",{\"member\":\""+sbName+"\"}");
				try {
					this.getHillStoneFirewallService().createServiceBook(firewallPolicyModel.getHillStoneModel(), serviceBookModel);
				} catch(Exception e) {
					String message = e.getMessage();
					if (message.indexOf("has been created!") < 0) {
						throw new Exception ("create servicebook has error, " + e.getMessage());
					}
				}
			}
		}
		if (servicebookName.length() == 0) {
			throw new Exception ("there is no servicebook for firewall policy.");
		}
		// 服务簿不存在则创建服务簿
		PolicyModel policyModel = new PolicyModel();
		policyModel.setPolicyName(this.initPolicyName(firewallPolicyModel));
		policyModel.setServerbookName(servicebookName.toString().substring(1));
		policyModel.setPolicyDesc(policyModel.getPolicyName());
		// 解析原IP地址
		String srcIp = firewallPolicyModel.getSourceIp();
		if (srcIp.indexOf("/") > 0) {
			String [] subArr = srcIp.split("/");
			policyModel.setSrcIp(subArr[0]);
			policyModel.setSrcNetmask(subArr.length > 1 ? subArr[1] : "32");
		} else if (srcIp.indexOf("-") > 0) {
			String [] subArr = srcIp.split("-");
			policyModel.setSrcIpMin(subArr[0]);
			policyModel.setSrcIpMax(srcIp.substring(0, srcIp.lastIndexOf(".") + 1) + subArr[1]);
		} else {
			policyModel.setSrcIp(srcIp);
			policyModel.setSrcNetmask("32");
		}
		// 解析目的IP地址
		String dstIp = firewallPolicyModel.getDestinationIp();
		if (dstIp.indexOf("/") > 0) {
			String [] subArr = dstIp.split("/");
			policyModel.setDstIp(subArr[0]);
			policyModel.setDstNetmask(subArr.length > 1 ? subArr[1] : "32");
		} else if (dstIp.indexOf("-") > 0) {
			String [] subArr = dstIp.split("-");
			policyModel.setDstIpMin(subArr[0]);
			policyModel.setDstIpMax(dstIp.substring(0, dstIp.lastIndexOf(".") + 1) + subArr[1]);
		} else {
			policyModel.setDstIp(dstIp);
			policyModel.setDstNetmask("32");
		}
		return this.getHillStoneFirewallService().createPolicy(firewallPolicyModel.getHillStoneModel(), policyModel);
	}
	
	@Override
	public void deleteFirewallPolicyByIds(FirewallPolicyModel firewallPolicyModel) throws Exception {
		this.getHillStoneFirewallService().deletePolicy(firewallPolicyModel.getHillStoneModel(), firewallPolicyModel.getIdArr());
	}
	
	@Override
	public String createFirewallSnat(HillStoneModel hillStoneModel, SnatModel snatModel) throws Exception {
		// SNAT fromIp:源IP;toIp:目的IP;transToIp:池IP
		// 创建地址簿
		String fromIps = snatModel.getFromIp();
		if (fromIps.indexOf("-") > 0) {
			AddrBookModel fromAddrBook = new AddrBookModel();
			fromAddrBook.setAddrbookName(fromIps);
			fromAddrBook.setIpMin(fromIps.split("-")[0]);
			fromAddrBook.setIpMax(fromIps.substring(0, fromIps.lastIndexOf(".") + 1) + fromIps.split("-")[1]);
			try {
				this.getHillStoneFirewallService().createAddrBook(hillStoneModel, fromAddrBook);
			} catch(Exception e) {
				String message = e.getMessage();
				if (message.indexOf("has already been created") < 0) {
					throw new Exception ("create servicebook has error, " + e.getMessage());
				}
			}
			snatModel.setFromIsIp("0");
		} else {
			snatModel.setFromIsIp("1");
		}
		String toIps = snatModel.getToIp();
		if (toIps.indexOf("-") > 0) {
			AddrBookModel fromAddrBook = new AddrBookModel();
			fromAddrBook.setAddrbookName(toIps);
			fromAddrBook.setIpMin(toIps.split("-")[0]);
			fromAddrBook.setIpMax(toIps.substring(0, toIps.lastIndexOf(".") + 1) + toIps.split("-")[1]);
			try {
				this.getHillStoneFirewallService().createAddrBook(hillStoneModel, fromAddrBook);
			} catch(Exception e) {
				String message = e.getMessage();
				if (message.indexOf("has already been created") < 0) {
					throw new Exception ("create servicebook has error, " + e.getMessage());
				}
			}
			snatModel.setToIsIp("0");
		} else {
			snatModel.setToIsIp("1");
		}
		snatModel.setTransToIsIp("1");
		return this.getHillStoneFirewallService().createSnat(hillStoneModel, snatModel);
	}

	@Override
	public void deleteFirewallSnatByIds(HillStoneModel hillStoneModel, String[] idArr) throws Exception {
		this.getHillStoneFirewallService().deleteSnat(hillStoneModel, idArr);
	}

	@Override
	public String createFirewallDnat(HillStoneModel hillStoneModel, DnatModel dnatModel) throws Exception {
		// DNAT fromIp:源IP;toIp:池IP;transToIp:目的IP
		// 创建地址簿
		String fromIps = dnatModel.getFromIp();
		if (fromIps.indexOf("-") > 0) {
			AddrBookModel fromAddrBook = new AddrBookModel();
			fromAddrBook.setAddrbookName(fromIps);
			fromAddrBook.setIpMin(fromIps.split("-")[0]);
			fromAddrBook.setIpMax(fromIps.substring(0, fromIps.lastIndexOf(".") + 1) + fromIps.split("-")[1]);
			try {
				this.getHillStoneFirewallService().createAddrBook(hillStoneModel, fromAddrBook);
			} catch(Exception e) {
				String message = e.getMessage();
				if (message.indexOf("has already been created") < 0) {
					throw new Exception ("create servicebook has error, " + e.getMessage());
				}
			}
			dnatModel.setFromIsIp("0");
		} else {
			dnatModel.setFromIsIp("1");
		}
		String transToIps = dnatModel.getTransToIp();
		if (transToIps.indexOf("-") > 0) {
			AddrBookModel fromAddrBook = new AddrBookModel();
			fromAddrBook.setAddrbookName(transToIps);
			fromAddrBook.setIpMin(transToIps.split("-")[0]);
			fromAddrBook.setIpMax(transToIps.substring(0, transToIps.lastIndexOf(".") + 1) + transToIps.split("-")[1]);
			try {
				this.getHillStoneFirewallService().createAddrBook(hillStoneModel, fromAddrBook);
			} catch(Exception e) {
				String message = e.getMessage();
				if (message.indexOf("has already been created") < 0) {
					throw new Exception ("create servicebook has error, " + e.getMessage());
				}
			}
			dnatModel.setTransToIsIp("0");
		} else {
			dnatModel.setTransToIsIp("1");
		}
		dnatModel.setToIsIp("1");
		return this.getHillStoneFirewallService().createDnat(hillStoneModel, dnatModel);
	}

	@Override
	public void deleteFirewallDnatByIds(HillStoneModel hillStoneModel, String[] idArr) throws Exception {
		this.getHillStoneFirewallService().deleteDnat(hillStoneModel, idArr);
	}
	
	private String initServicebookName(String portocol, String srcPort, String dstPort) {
		// 服务簿名字格式：FinCMP-协议类型-S源端口号D目的端口号；如：FinCMP-TCP-S0D80
		String servicebookName = "FinCMP-" + portocol.toUpperCase() + "-S" + srcPort + "D" + dstPort;
		return servicebookName;
	}
	
	private String initPolicyName(FirewallPolicyModel firewallPolicyModel) {
		// 安全策略名称格式：FinCMP-需求编号；如：FinCMP-SRFW201809130001
		String policyName = "FinCMP-" + firewallPolicyModel.getRequestCode();
		return policyName;
	}
}
