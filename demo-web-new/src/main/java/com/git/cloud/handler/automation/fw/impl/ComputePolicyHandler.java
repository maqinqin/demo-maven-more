package com.git.cloud.handler.automation.fw.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.fw.FirewallCommonHandler;
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


public class ComputePolicyHandler extends FirewallCommonHandler {
	
	private static Logger logger = LoggerFactory.getLogger(ComputePolicyHandler.class);

	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		logger.info("[ComputePolicyHandler] compute policy start ...");
		String srId = (String) contextParams.get("srvReqId");
		// 根据服务申请获取云防火墙申请对象
		RmNwFirewallRequestPo firewallRequest = super.findRmNwFirewallRequest(srId);
		logger.info("[ComputePolicyHandler] the object firewallRequest is : " + JSONObject.toJSONString(firewallRequest));
		List<RmNwFirewallNatAddressPo> firewallNatAddressList = new ArrayList<RmNwFirewallNatAddressPo> ();
		List<RmNwOutsideNatPolicyPo> natPolicyList = new ArrayList<RmNwOutsideNatPolicyPo> ();
		List<RmNwOutsideNatPolicyRefPo> natPolicyRefList = new ArrayList<RmNwOutsideNatPolicyRefPo> ();
		List<RmNwOutsideFirewallPolicyPo> hsPolicyList = new ArrayList<RmNwOutsideFirewallPolicyPo> ();
		List<RmNwVfwPolicyRulePo> hwPolicyList = new ArrayList<RmNwVfwPolicyRulePo> ();
		// 计算拆分策略
		logger.info("[ComputePolicyHandler] split policy start ...");
		try {
			this.splitPolicy(firewallRequest, firewallNatAddressList, natPolicyList, natPolicyRefList, hsPolicyList, hwPolicyList);
		} catch(Exception e) {
			logger.error("[ComputePolicyHandler] split policy error, " + e.getMessage(), e);
			e.printStackTrace();
			throw new Exception("拆分策略出现异常," + e.getMessage());
		}
		logger.info("[ComputePolicyHandler] split policy end ...");
		// 保存策略
		logger.info("[ComputePolicyHandler] save policy start ...");
		try {
			this.savePolicy(firewallNatAddressList, natPolicyList, natPolicyRefList, hsPolicyList, hwPolicyList);
		} catch(Exception e) {
			logger.error("[ComputePolicyHandler] save policy error, " + e.getMessage(), e);
			e.printStackTrace();
			throw new Exception("保存拆分的策略出现异常," + e.getMessage());
		}
		logger.info("[ComputePolicyHandler] save policy end ...");
		logger.info("[ComputePolicyHandler] compute policy end ...");
	}

	/**
	 * 拆分云防火墙策略
	 * update at 20181030, 原方法备份为splitPolicyBak20181030
	 * @param firewallRequest
	 * @param natPolicyList
	 * @param hsPolicyList
	 * @param hwPolicyList
	 * @throws Exception 
	 */
	private void splitPolicy(RmNwFirewallRequestPo firewallRequest, List<RmNwFirewallNatAddressPo> firewallNatAddressList,
			List<RmNwOutsideNatPolicyPo> natPolicyList, List<RmNwOutsideNatPolicyRefPo> natPolicyRefList, 
			List<RmNwOutsideFirewallPolicyPo> hsPolicyList, List<RmNwVfwPolicyRulePo> hwPolicyList) throws Exception {
		String visitPolicy = firewallRequest.getVisitPolicy();
		logger.info("[ComputePolicyHandler] visit policy is " + visitPolicy.replace("-", "->"));
		if ("EC-DMZ".equals(visitPolicy)) {
			// 外联 -> DMZ
			String networkArea = "EC";
			// 根据网络区域获取云外防火墙
			RmNwOutsideFirewallPo outsideFirewall = super.getFirewallAutomationService().findRmNwOutsideFirewallByNetworkArea(networkArea);
			String fwId = outsideFirewall.getId();
			RmNwFirewallNatAddressPo firewallNatAddress = this.computeNatIpForDnat(firewallRequest, networkArea, null);
			if(firewallNatAddress == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForDnat the external org [" + firewallRequest.getExternalOrg() + "] has no natIp.");
			}
			String natIp = firewallNatAddress.getIp();
			if (firewallNatAddress.getExist() != null && "true".equals(firewallNatAddress.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddress.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddress);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIp, NatTypeEnum.DNAT.getValue()));
			}
			String srcIp = firewallRequest.getSrcIp();
			String dstIp = natIp;
			// 云外防火墙
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIp));
			// 云内防火墙 - 目的地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getDstIp(), hwPolicyList);
		} else if ("DMZ-EC".equals(visitPolicy)) {
			// DMZ -> 外联
			String networkArea = "EC";
			// 根据网络区域获取云外防火墙
			RmNwOutsideFirewallPo outsideFirewall = super.getFirewallAutomationService().findRmNwOutsideFirewallByNetworkArea(networkArea);
			String fwId = outsideFirewall.getId();
			RmNwFirewallNatAddressPo firewallNatAddress = this.computeNatIpForSnat(firewallRequest, networkArea, null);
			if(firewallNatAddress == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForSnat the external org [" + firewallRequest.getExternalOrg() + "] has no natIp.");
			}
			String natIp = firewallNatAddress.getIp();
			if (firewallNatAddress.getExist() != null && "true".equals(firewallNatAddress.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddress.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddress);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIp, NatTypeEnum.SNAT.getValue()));
			}
			String srcIp = firewallRequest.getSrcIp();
			String dstIp = firewallRequest.getDstIp();
			// 云外防火墙
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIp));
			// 云内防火墙 - 源地址所属墙的策略 update at 20181030
//			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getSrcIp(), hwPolicyList);
		} else if ("IC-DMZ".equals(visitPolicy)) {
			// 互联 -> DMZ
			String networkArea = "IC";
			// 根据网络区域获取云外防火墙
			RmNwOutsideFirewallPo outsideFirewall = super.getFirewallAutomationService().findRmNwOutsideFirewallByNetworkArea(networkArea);
			String fwId = outsideFirewall.getId();
			RmNwFirewallNatAddressPo firewallNatAddressYD = this.computeNatIpForDnat(firewallRequest, networkArea, "YD");
			if(firewallNatAddressYD == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForDnat the operator [移动] has no natIp for YD.");
			}
			RmNwFirewallNatAddressPo firewallNatAddressDX = this.computeNatIpForDnat(firewallRequest, networkArea, "DX");
			if(firewallNatAddressDX == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForDnat the operator [电信] has no natIp for DX.");
			}
			RmNwFirewallNatAddressPo firewallNatAddressLT = this.computeNatIpForDnat(firewallRequest, networkArea, "LT");
			if(firewallNatAddressLT == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForDnat the operator [联通] has no natIp for LT.");
			}
			String natIpYD = firewallNatAddressYD.getIp();
			String natIpDX = firewallNatAddressDX.getIp();
			String natIpLT = firewallNatAddressLT.getIp();
			if (firewallNatAddressYD.getExist() != null && "true".equals(firewallNatAddressYD.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressYD.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressYD);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpYD, NatTypeEnum.DNAT.getValue()));
			}
			if (firewallNatAddressDX.getExist() != null && "true".equals(firewallNatAddressDX.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressDX.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressDX);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpDX, NatTypeEnum.DNAT.getValue()));
			}
			if (firewallNatAddressLT.getExist() != null && "true".equals(firewallNatAddressLT.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressLT.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressLT);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpLT, NatTypeEnum.DNAT.getValue()));
			}
			String srcIp = firewallRequest.getSrcIp();
			String dstIpYD = natIpYD;
			String dstIpDX = natIpDX;
			String dstIpLT = natIpLT;
			// 云外防火墙
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIpYD));
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIpDX));
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIpLT));
			// 云内防火墙 - 目的地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getDstIp(), hwPolicyList);
		} else if ("DMZ-IC".equals(visitPolicy)) {
			// DMZ -> 互联
			String networkArea = "IC";
			// 根据网络区域获取云外防火墙
			RmNwOutsideFirewallPo outsideFirewall = super.getFirewallAutomationService().findRmNwOutsideFirewallByNetworkArea(networkArea);
			String fwId = outsideFirewall.getId();
			RmNwFirewallNatAddressPo firewallNatAddressYD = this.computeNatIpForSnat(firewallRequest, networkArea, "YD");
			if(firewallNatAddressYD == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForSnat the operator [移动] has no natIp for YD.");
			}
			RmNwFirewallNatAddressPo firewallNatAddressDX = this.computeNatIpForSnat(firewallRequest, networkArea, "DX");
			if(firewallNatAddressDX == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForSnat the operator [电信] has no natIp for DX.");
			}
			RmNwFirewallNatAddressPo firewallNatAddressLT = this.computeNatIpForSnat(firewallRequest, networkArea, "LT");
			if(firewallNatAddressLT == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForSnat the operator [联通] has no natIp for LT.");
			}
			String natIpYD = firewallNatAddressYD.getIp();
			String natIpDX = firewallNatAddressDX.getIp();
			String natIpLT = firewallNatAddressLT.getIp();
			if (firewallNatAddressYD.getExist() != null && "true".equals(firewallNatAddressYD.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressYD.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressYD);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpYD, NatTypeEnum.SNAT.getValue()));
			}
			if (firewallNatAddressDX.getExist() != null && "true".equals(firewallNatAddressDX.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressDX.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressDX);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpDX, NatTypeEnum.SNAT.getValue()));
			}
			if (firewallNatAddressLT.getExist() != null && "true".equals(firewallNatAddressLT.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressLT.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressLT);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpLT, NatTypeEnum.SNAT.getValue()));
			}
			String srcIp = firewallRequest.getSrcIp();
			String dstIp = firewallRequest.getDstIp();
			// 云外防火墙
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIp));
			// 云内防火墙 - 源地址所属墙的策略
//			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getSrcIp(), hwPolicyList);
		} else if ("*-YN".equals(visitPolicy)) {
			// 云内防火墙 - 目的地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getDstIp(), hwPolicyList);
		}
	}
	
	/**
	 * 拆分云防火墙策略
	 * @param firewallRequest
	 * @param natPolicyList
	 * @param hsPolicyList
	 * @param hwPolicyList
	 * @throws Exception 
	 */
	private void splitPolicyBak20181030(RmNwFirewallRequestPo firewallRequest, List<RmNwFirewallNatAddressPo> firewallNatAddressList,
			List<RmNwOutsideNatPolicyPo> natPolicyList, List<RmNwOutsideNatPolicyRefPo> natPolicyRefList, 
			List<RmNwOutsideFirewallPolicyPo> hsPolicyList, List<RmNwVfwPolicyRulePo> hwPolicyList) throws Exception {
		String visitPolicy = firewallRequest.getVisitPolicy();
		logger.info("[ComputePolicyHandler] visit policy is " + visitPolicy.replace("-", "->"));
		if ("EC-DMZ".equals(visitPolicy)) {
			// 外联 -> DMZ
			String networkArea = "EC";
			// 根据网络区域获取云外防火墙
			RmNwOutsideFirewallPo outsideFirewall = super.getFirewallAutomationService().findRmNwOutsideFirewallByNetworkArea(networkArea);
			String fwId = outsideFirewall.getId();
			RmNwFirewallNatAddressPo firewallNatAddress = this.computeNatIpForDnat(firewallRequest, networkArea, null);
			if(firewallNatAddress == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForDnat the external org [" + firewallRequest.getExternalOrg() + "] has no natIp.");
			}
			String natIp = firewallNatAddress.getIp();
			if (firewallNatAddress.getExist() != null && "true".equals(firewallNatAddress.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddress.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddress);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIp, NatTypeEnum.DNAT.getValue()));
			}
			String srcIp = firewallRequest.getSrcIp();
			String dstIp = natIp;
			// 云外防火墙
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIp));
			// 云内防火墙 - 目的地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getDstIp(), hwPolicyList);
		} else if ("DMZ-EC".equals(visitPolicy)) {
			// DMZ -> 外联
			String networkArea = "EC";
			// 根据网络区域获取云外防火墙
			RmNwOutsideFirewallPo outsideFirewall = super.getFirewallAutomationService().findRmNwOutsideFirewallByNetworkArea(networkArea);
			String fwId = outsideFirewall.getId();
			RmNwFirewallNatAddressPo firewallNatAddress = this.computeNatIpForSnat(firewallRequest, networkArea, null);
			if(firewallNatAddress == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForSnat the external org [" + firewallRequest.getExternalOrg() + "] has no natIp.");
			}
			String natIp = firewallNatAddress.getIp();
			if (firewallNatAddress.getExist() != null && "true".equals(firewallNatAddress.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddress.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddress);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIp, NatTypeEnum.SNAT.getValue()));
			}
			String srcIp = firewallRequest.getSrcIp();
			String dstIp = firewallRequest.getDstIp();
			// 云外防火墙
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIp));
			// 云内防火墙 - 源地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getSrcIp(), hwPolicyList);
		} else if ("IC-DMZ".equals(visitPolicy)) {
			// 互联 -> DMZ
			String networkArea = "IC";
			// 根据网络区域获取云外防火墙
			RmNwOutsideFirewallPo outsideFirewall = super.getFirewallAutomationService().findRmNwOutsideFirewallByNetworkArea(networkArea);
			String fwId = outsideFirewall.getId();
			RmNwFirewallNatAddressPo firewallNatAddressYD = this.computeNatIpForDnat(firewallRequest, networkArea, "YD");
			if(firewallNatAddressYD == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForDnat the operator [移动] has no natIp for YD.");
			}
			RmNwFirewallNatAddressPo firewallNatAddressDX = this.computeNatIpForDnat(firewallRequest, networkArea, "DX");
			if(firewallNatAddressDX == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForDnat the operator [电信] has no natIp for DX.");
			}
			RmNwFirewallNatAddressPo firewallNatAddressLT = this.computeNatIpForDnat(firewallRequest, networkArea, "LT");
			if(firewallNatAddressLT == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForDnat the operator [联通] has no natIp for LT.");
			}
			String natIpYD = firewallNatAddressYD.getIp();
			String natIpDX = firewallNatAddressDX.getIp();
			String natIpLT = firewallNatAddressLT.getIp();
			if (firewallNatAddressYD.getExist() != null && "true".equals(firewallNatAddressYD.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressYD.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressYD);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpYD, NatTypeEnum.DNAT.getValue()));
			}
			if (firewallNatAddressDX.getExist() != null && "true".equals(firewallNatAddressDX.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressDX.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressDX);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpDX, NatTypeEnum.DNAT.getValue()));
			}
			if (firewallNatAddressLT.getExist() != null && "true".equals(firewallNatAddressLT.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressLT.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressLT);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpLT, NatTypeEnum.DNAT.getValue()));
			}
			String srcIp = firewallRequest.getSrcIp();
			String dstIpYD = natIpYD;
			String dstIpDX = natIpDX;
			String dstIpLT = natIpLT;
			// 云外防火墙
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIpYD));
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIpDX));
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIpLT));
			// 云内防火墙 - 目的地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getDstIp(), hwPolicyList);
		} else if ("DMZ-IC".equals(visitPolicy)) {
			// DMZ -> 互联
			String networkArea = "IC";
			// 根据网络区域获取云外防火墙
			RmNwOutsideFirewallPo outsideFirewall = super.getFirewallAutomationService().findRmNwOutsideFirewallByNetworkArea(networkArea);
			String fwId = outsideFirewall.getId();
			RmNwFirewallNatAddressPo firewallNatAddressYD = this.computeNatIpForSnat(firewallRequest, networkArea, "YD");
			if(firewallNatAddressYD == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForSnat the operator [移动] has no natIp for YD.");
			}
			RmNwFirewallNatAddressPo firewallNatAddressDX = this.computeNatIpForSnat(firewallRequest, networkArea, "DX");
			if(firewallNatAddressDX == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForSnat the operator [电信] has no natIp for DX.");
			}
			RmNwFirewallNatAddressPo firewallNatAddressLT = this.computeNatIpForSnat(firewallRequest, networkArea, "LT");
			if(firewallNatAddressLT == null) {
				throw new Exception("[ComputePolicyHandler] computeNatIpForSnat the operator [联通] has no natIp for LT.");
			}
			String natIpYD = firewallNatAddressYD.getIp();
			String natIpDX = firewallNatAddressDX.getIp();
			String natIpLT = firewallNatAddressLT.getIp();
			if (firewallNatAddressYD.getExist() != null && "true".equals(firewallNatAddressYD.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressYD.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressYD);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpYD, NatTypeEnum.SNAT.getValue()));
			}
			if (firewallNatAddressDX.getExist() != null && "true".equals(firewallNatAddressDX.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressDX.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressDX);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpDX, NatTypeEnum.SNAT.getValue()));
			}
			if (firewallNatAddressLT.getExist() != null && "true".equals(firewallNatAddressLT.getExist())) {
				natPolicyRefList.add(this.initRmNwOutsideNatPolicyRefPo(firewallNatAddressLT.getNatPolicyId(), firewallRequest.getId()));
			} else {
				firewallNatAddressList.add(firewallNatAddressLT);
				natPolicyList.add(this.initRmNwOutsideNatPolicyPo(firewallRequest, fwId, natIpLT, NatTypeEnum.SNAT.getValue()));
			}
			String srcIp = firewallRequest.getSrcIp();
			String dstIp = firewallRequest.getDstIp();
			// 云外防火墙
			hsPolicyList.add(this.initRmNwOutsideFirewallPolicyPo(firewallRequest, fwId, srcIp, dstIp));
			// 云内防火墙 - 源地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getSrcIp(), hwPolicyList);
		} else if ("NW-DMZ".equals(visitPolicy) || "DMZ-NW".equals(visitPolicy) || "DMZ-DMZ".equals(visitPolicy) || "NW-NW".equals(visitPolicy)) {
			// 内网 -> DMZ || DMZ -> 内网 || DMZ -> DMZ || 内网 -> 内网
			// 云内防火墙 - 源地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getSrcIp(), hwPolicyList);
			// 云内防火墙 - 目的地址所属墙的策略
			this.initRmNwVfwPolicyRulePo(firewallRequest, firewallRequest.getDstIp(), hwPolicyList);
		}
	}
	
	/**
	 * 计算DNAT策略的NAT地址
	 * 匹配顺序：找是否已经存在此DNAT、找是否存在匹配的SNAT、找是否存在策略NAT配置、找对应NAT池
	 * @param firewallRequest
	 * @param networkArea 网络区域
	 * @param operator 运营商（如果网络区域为互联时，此属性有效）
	 * @return
	 * @throws Exception 
	 */
	private RmNwFirewallNatAddressPo computeNatIpForDnat(RmNwFirewallRequestPo firewallRequest, String networkArea, String operator) throws Exception {
		String dnatIp = firewallRequest.getDstIp();
		// 查找策略NAT对应的池
		List<RmNwFirewallPolicyNatPo> firewallPolicyNatList = super.getFirewallAutomationService().findRmNwFirewallPolicyNatList(
				networkArea, firewallRequest.getExternalOrg(), operator);
		String natPoolId = "";
		if (firewallPolicyNatList != null && firewallPolicyNatList.size() > 0) {
			String externalIp = firewallRequest.getSrcIp();
			RmNwFirewallPolicyNatPo firewallPolicyNat;
			for (int i = 0 ; i < firewallPolicyNatList.size() ; i++) {
				firewallPolicyNat = firewallPolicyNatList.get(i);
				// 外部IP是否存在于策略NAT地址段中
				if (this.isIpInIps(externalIp, firewallPolicyNat.getIpAddress())) {
					// 如果外部IP在指定的策略NAT配置中，则在此NAT池中分配natIp
					natPoolId = firewallPolicyNat.getNatId();
					break;
				}
			}
		}
		// 1.根据“目的地址”去FIREWALL_NAT_ADDRESS表查DNAT_IP是否存在，如存在，则不需要再进行下发DNAT；
		logger.info("[ComputePolicyHandler] computeNatIpForDnat find dnat is exist.");
		List<RmNwFirewallNatAddressPo> firewallNatAddressList = super.getFirewallAutomationService().findRmNwFirewallNatAddressForUsed(
				networkArea, firewallRequest.getExternalOrg(), operator, natPoolId);
		logger.info("[ComputePolicyHandler] the firewallNatAddressList is : " + JSONObject.toJSONString(firewallNatAddressList));
		RmNwFirewallNatAddressPo firewallNatAddress = null;
		int firewallNatAddressLen = firewallNatAddressList == null ? 0 : firewallNatAddressList.size();
		for (int i = 0 ; i < firewallNatAddressLen ; i++) {
			firewallNatAddress = firewallNatAddressList.get(i);
			// 判断是否此DNAT是否已下发
			if (firewallNatAddress.getDnatIp() != null && dnatIp.equals(firewallNatAddress.getDnatIp())) {
				firewallNatAddress.setNatPolicyId(this.getNatPolicyId(firewallNatAddress.getIp(), NatTypeEnum.DNAT.getValue(), dnatIp, ""));
				firewallNatAddress.setExist("true");
				return firewallNatAddress;
			}
		}
		// 2.根据“目的地址”去FIREWALL_NAT_ADDRESS表查SNAT_IP，如果SNAT_IP包含“目的地址”，则需要使用此NAT地址来做为DNAT下发；
		logger.info("[ComputePolicyHandler] computeNatIpForDnat find dst_ip mapping snat start.");
		for (int i = 0 ; i < firewallNatAddressLen ; i++) {
			firewallNatAddress = firewallNatAddressList.get(i);
			if (firewallNatAddress.getStatus().equals(NatAddressStatusEnum.SNAT.getValue())) {
				String [] snatArr = firewallNatAddress.getSnatIp().split(",");
				if (snatArr.length > 0) {
					for (int j = 0 ; j < snatArr.length ; j++) {
						if (!"".equals(snatArr[j]) && !"null".equals(snatArr[j])) {
							if (this.isIpInIps(dnatIp, snatArr[j])) {
								firewallNatAddress.setStatus(NatAddressStatusEnum.DNAT_SNAT.getValue());
								firewallNatAddress.setDnatIp(dnatIp);
								return firewallNatAddress;
							}
						}
					}
				}
			}
		}
		// 3.根据“源地址的起始地址”去对比指定外联机构或者运营商的策略NAT，如果策略NAT包含“源地址的起始地址”，则使用该策略NAT对应的NAT池，分配一个可用地址；
		logger.info("[ComputePolicyHandler] computeNatIpForDnat find external ip mapping policy nat start.");
		if (!"".equals(natPoolId)) {
			firewallNatAddress = super.getFirewallAutomationService().findRmNwFirewallNatAddressByNatPoolId(natPoolId);
			if (firewallNatAddress != null) {
				firewallNatAddress.setStatus(NatAddressStatusEnum.DNAT.getValue());
				firewallNatAddress.setDnatIp(dnatIp);
				return firewallNatAddress;
			}
		}
		// 4.指定外联机构或者运营商按顺序分配一个可用地址；
		logger.info("[ComputePolicyHandler] computeNatIpForDnat find nat pool address start.");
		List<RmNwFirewallNatPoolPo> firewallNatPoolList = super.getFirewallAutomationService().findRmNwFirewallNatPoolList(
				networkArea, firewallRequest.getExternalOrg(), operator);
		for (int i = 0 ; i < firewallNatPoolList.size() ; i++) {
			firewallNatAddress = super.getFirewallAutomationService().findRmNwFirewallNatAddressByNatPoolId(firewallNatPoolList.get(i).getId());
			if (firewallNatAddress != null) {
				firewallNatAddress.setStatus(NatAddressStatusEnum.DNAT.getValue());
				firewallNatAddress.setDnatIp(dnatIp);
				return firewallNatAddress;
			}
		}
		return null;
	}
	
	/**
	 * 计算SNAT策略的NAT地址
	 * 匹配顺序：找是否存在匹配的DNAT、找是否存在策略NAT配置、找对应NAT池
	 * @param firewallRequest
	 * @param networkArea 网络区域
	 * @param operator 运营商（如果网络区域为互联时，此属性有效）
	 * @return
	 * @throws Exception 
	 */
	private RmNwFirewallNatAddressPo computeNatIpForSnat(RmNwFirewallRequestPo firewallRequest, String networkArea, String operator) throws Exception {
		String snatIp = firewallRequest.getSrcIp();
		// 查找策略NAT对应的池
		List<RmNwFirewallPolicyNatPo> firewallPolicyNatList = super.getFirewallAutomationService().findRmNwFirewallPolicyNatList(
				networkArea, firewallRequest.getExternalOrg(), operator);
		String natPoolId = "";
		if (firewallPolicyNatList != null && firewallPolicyNatList.size() > 0) {
			String externalIp = firewallRequest.getDstIp();
			RmNwFirewallPolicyNatPo firewallPolicyNat;
			for (int i = 0 ; i < firewallPolicyNatList.size() ; i++) {
				firewallPolicyNat = firewallPolicyNatList.get(i);
				// 外部IP是否存在于策略NAT地址段中
				if (this.isIpInIps(externalIp, firewallPolicyNat.getIpAddress())) {
					// 如果外部IP在指定的策略NAT配置中，则在此NAT池中分配natIp
					natPoolId = firewallPolicyNat.getNatId();
					break;
				}
			}
		}
		// 1.根据“源地址”去FIREWALL_NAT_ADDRESS表查SNAT_IP，如果SNAT_IP包含“源地址”，则不需要再进行下发SNAT；
		logger.info("[ComputePolicyHandler] computeNatIpForSnat find snat is exist.");
		List<RmNwFirewallNatAddressPo> firewallNatAddressList = super.getFirewallAutomationService().findRmNwFirewallNatAddressForUsed(
				networkArea, firewallRequest.getExternalOrg(), operator, natPoolId);
		RmNwFirewallNatAddressPo firewallNatAddress = null;
		int firewallNatAddressLen = firewallNatAddressList == null ? 0 : firewallNatAddressList.size();
		// snatIp范围的第一个可用IP
		String firstIp = this.getIpsFirstIp(snatIp);
		// snatIp范围的最后一个可用IP
		String lastIp = this.getIpsLastIp(snatIp);
		for (int i = 0 ; i < firewallNatAddressLen ; i++) {
			firewallNatAddress = firewallNatAddressList.get(i);
			if (firewallNatAddress.getSnatIp() != null && !"".equals(firewallNatAddress.getSnatIp())) {
				String [] snatArr = firewallNatAddress.getSnatIp().split(",");
				if (snatArr.length > 0) {
					for (int j = 0 ; j < snatArr.length ; j++) {
						if (!"".equals(snatArr[j]) && !"null".equals(snatArr[j])) {
							// 如果snat第一个可用IP和最后一个可用IP都在已下发的SNAT策略中，那么说明该NAT策略已存在，无需再次下发
							if (this.isIpInIps(firstIp, snatArr[j]) && this.isIpInIps(lastIp, snatArr[j])) {
								firewallNatAddress.setExist("true");
								firewallNatAddress.setNatPolicyId(this.getNatPolicyId(firewallNatAddress.getIp(), NatTypeEnum.SNAT.getValue(), "", snatArr[j]));
								return firewallNatAddress;
							}
						}
					}
				}
			}
		}
		// 2.根据“源地址”去FIREWALL_NAT_ADDRESS表查DNAT_IP，如果“源地址”包含DNAT_IP，则需要使用此NAT地址来做为SNAT下发；
		logger.info("[ComputePolicyHandler] computeNatIpForSnat find src_ip mapping snat start.");
		for (int i = 0 ; i < firewallNatAddressLen ; i++) {
			firewallNatAddress = firewallNatAddressList.get(i);
			if (firewallNatAddress.getDnatIp() != null && !"".equals(firewallNatAddress.getDnatIp())) {
				if (this.isIpInIps(firewallNatAddress.getDnatIp(), snatIp)) {
					if (firewallNatAddress.getStatus().equals(NatAddressStatusEnum.DNAT_SNAT.getValue())) {
						firewallNatAddress.setSnatIp(firewallNatAddress.getSnatIp() + "," + snatIp);
					} else {
						firewallNatAddress.setSnatIp(snatIp);
					}
					firewallNatAddress.setStatus(NatAddressStatusEnum.DNAT_SNAT.getValue());
					return firewallNatAddress;
				}
			}
		}
		// 3.根据“目的地址的起始地址”去对比指定外联机构或者运营商的策略NAT，如果策略NAT包含“目的地址的起始地址”，则使用该策略NAT对应的NAT池，分配一个可用地址；
		logger.info("[ComputePolicyHandler] computeNatIpForSnat find external ip mapping policy nat start.");
		if (!"".equals(natPoolId)) {
			firewallNatAddress = super.getFirewallAutomationService().findRmNwFirewallNatAddressByNatPoolId(natPoolId);
			if (firewallNatAddress != null) {
				firewallNatAddress.setStatus(NatAddressStatusEnum.SNAT.getValue());
				firewallNatAddress.setSnatIp(snatIp);
				return firewallNatAddress;
			}
		}
		// 4.指定外联机构或者运营商按顺序分配一个可用地址；
		logger.info("[ComputePolicyHandler] computeNatIpForSnat find nat pool address start.");
		List<RmNwFirewallNatPoolPo> firewallNatPoolList = super.getFirewallAutomationService().findRmNwFirewallNatPoolList(
				networkArea, firewallRequest.getExternalOrg(), operator);
		for (int i = 0 ; i < firewallNatPoolList.size() ; i++) {
			firewallNatAddress = super.getFirewallAutomationService().findRmNwFirewallNatAddressByNatPoolId(firewallNatPoolList.get(i).getId());
			if (firewallNatAddress != null) {
				firewallNatAddress.setStatus(NatAddressStatusEnum.SNAT.getValue());
				firewallNatAddress.setSnatIp(snatIp);
				return firewallNatAddress;
			}
		}
		return null;
	}
	
	private String getNatPolicyId(String natIp, String natType, String dnatIp, String snatIp) {
		List<RmNwOutsideNatPolicyPo> natPolicyList = super.getFirewallAutomationService().findRmNwOutsideNatPolicyByNatIp(natIp, natType);
		RmNwOutsideNatPolicyPo natPolicy;
		int natPolicyLen = natPolicyList == null ? 0 : natPolicyList.size();
		for (int i = 0 ; i < natPolicyLen ; i ++) {
			natPolicy = natPolicyList.get(i);
			if (natType.equals(NatTypeEnum.DNAT.getValue())) {
				if (dnatIp.equals(natPolicy.getDstIp())) {
					return natPolicy.getId();
				}
			} else if (natType.equals(NatTypeEnum.SNAT.getValue())) {
				if (snatIp.equals(natPolicy.getSrcIp())) {
					return natPolicy.getId();
				}
			}
		}
		return "";
	}
	
	/**
	 * 获取ips段中第一个可用IP地址
	 * ips格式为三种：1.1.1.1或1.1.1.1/24或1.1.1.1-255
	 * @param ips
	 * @return
	 */
	private String getIpsFirstIp(String ips) {
		if (ips.indexOf("/") > 0) {
			int mask = Integer.valueOf(ips.split("/")[1]);
			String [] ipArr = ips.split("/")[0].split("\\.");
			if (mask < 8) {
				return "0.0.0.1";
			} else if (mask < 16) {
				return ipArr[0] + ".0.0.1";
			} else if (mask < 24) {
				return ipArr[0] + "." + ipArr[1] + ".0.1";
			} else if (mask < 32) {
				return ipArr[0] + "." + ipArr[1] + "." + ipArr[2] + ".1";
			} else {
				return ipArr[0] + "." + ipArr[1] + "." + ipArr[2] + "." + ipArr[3];
			}
		} else if (ips.indexOf("-") > 0) {
			return ips.split("-")[0];
		} else {
			return ips;
		}
	}
	
	/**
	 * 获取ips段中最后一个可用IP地址
	 * ips格式为三种：1.1.1.1或1.1.1.1/24或1.1.1.1-255
	 * @param ips
	 * @return
	 */
	private String getIpsLastIp(String ips) {
		if (ips.indexOf("/") > 0) {
			int mask = Integer.valueOf(ips.split("/")[1]);
			String [] ipArr = ips.split("/")[0].split("\\.");
			if (mask < 8) {
				return (2 * Math.round(Math.pow(2, (8 - 1) - mask)) - 1) + ".255.255.254";
			} else if (mask < 16) {
				return ipArr[0] + "." + (2 * Math.round(Math.pow(2, (16 - 1) - mask)) - 1) + ".255.254";
			} else if (mask < 24) {
				return ipArr[0] + "." + ipArr[1] + "." + (2 * Math.round(Math.pow(2, (24 - 1) - mask)) - 1) + ".254";
			} else if (mask < 32) {
				int lastNum = (int) (2 * Math.round(Math.pow(2, (32 - 1) - mask)) - 1);
				return ipArr[0] + "." + ipArr[1] + "." + ipArr[2] + "." + (lastNum > 1 ? lastNum - 1 : lastNum);
			} else {
				return ipArr[0] + "." + ipArr[1] + "." + ipArr[2] + "." + ipArr[3];
			}
		} else if (ips.indexOf("-") > 0) {
			return ips.substring(0, ips.lastIndexOf(".") + 1) + ips.split("-")[1];
		} else {
			return ips;
		}
	}
	
	/**
	 * ip是否存在于ips中
	 * ip为单个ip地址
	 * ips为ip或ip段，格式为：1.1.1.1或1.1.1.1/24或1.1.1.1-255
	 * @param ip
	 * @param ips
	 * @return
	 * @throws Exception 
	 */
	private boolean isIpInIps(String ip, String ips) throws Exception {
		try {
			ip = this.getIpsFirstIp(ip);
			String ipC = ip.substring(0, ip.lastIndexOf("."));
			String ipsC = ips.substring(0, ips.lastIndexOf("."));
			if (ipC.equals(ipsC)) {
				String ipD = ip.substring(ip.lastIndexOf(".") + 1);
				if (ips.indexOf("/") > 0) {
					String firstIp = this.getIpsFirstIp(ips);
					String lastIp = this.getIpsLastIp(ips);
					String [] firstArr = firstIp.split("\\.");
					String [] lastArr = lastIp.split("\\.");
					String [] ipArr = ip.split("\\.");
					return Integer.valueOf(ipArr[0]) >= Integer.valueOf(firstArr[0]) && Integer.valueOf(ipArr[0]) <= Integer.valueOf(lastArr[0]) &&
						   Integer.valueOf(ipArr[1]) >= Integer.valueOf(firstArr[1]) && Integer.valueOf(ipArr[1]) <= Integer.valueOf(lastArr[1]) &&
						   Integer.valueOf(ipArr[2]) >= Integer.valueOf(firstArr[2]) && Integer.valueOf(ipArr[2]) <= Integer.valueOf(lastArr[2]) &&
						   Integer.valueOf(ipArr[3]) >= Integer.valueOf(firstArr[3]) && Integer.valueOf(ipArr[3]) <= Integer.valueOf(lastArr[3]);
				} else if (ips.indexOf("-") > 0) {
					String ipsD = ips.substring(ips.lastIndexOf(".") + 1);
					String [] dArr = ipsD.split("-");
					if (dArr.length == 2) {
						return Integer.valueOf(ipD) >= Integer.valueOf(dArr[0]) && Integer.valueOf(ipD) <= Integer.valueOf(dArr[1]);
					}
				} else {
					String ipsD = ips.substring(ips.lastIndexOf(".") + 1);
					return ipD.equals(ipsD);
				}
			}
		} catch (Exception e) {
			String errorLog = "[ComputePolicyHandler] at isIpInIps has error, the ip is " + ip + ", the ips is " + ips;
			logger.error(errorLog);
			throw new Exception (errorLog);
		}
		return false;
	}
	
	/**
	 * 初始化NAT策略对象
	 * DNAT fromIp:源IP;toIp:池IP;transToIp:目的IP
	 * SNAT fromIp:源IP;toIp:目的IP;transToIp:池IP
	 * @param firewallRequest
	 * @param fwId 云外防火墙ID
	 * @param fromIp
	 * @param toIp
	 * @param transToIp 转换地址
	 * @param natType NAT类型（DNAT/SNAT）
	 * @return
	 */
	private RmNwOutsideNatPolicyPo initRmNwOutsideNatPolicyPo(
			RmNwFirewallRequestPo firewallRequest, String fwId, String natIp, String natType) {
		RmNwOutsideNatPolicyPo outsideNatPolicy = new RmNwOutsideNatPolicyPo();
		outsideNatPolicy.setId(UUIDGenerator.getUUID());
		outsideNatPolicy.setFirewallRequestId(firewallRequest.getId());
		outsideNatPolicy.setNatType(natType);
		outsideNatPolicy.setFwId(fwId);
		outsideNatPolicy.setSrcIp(firewallRequest.getSrcIp());
		outsideNatPolicy.setDstIp(firewallRequest.getDstIp());
		outsideNatPolicy.setNatIp(natIp);
//		outsideNatPolicy.setSrcPort(firewallRequest.getSrcPort());
//		outsideNatPolicy.setDstPort(firewallRequest.getDstPort());
		outsideNatPolicy.setStatus(NatStatusEnum.INVALID.getValue());
		outsideNatPolicy.setIsActive("Y");
		outsideNatPolicy.setCreateTime(firewallRequest.getCreateTime());
		outsideNatPolicy.setCreateUser(firewallRequest.getCreateUser());
		logger.info("[ComputePolicyHandler] outside nat policy is " + JSONObject.toJSONString(outsideNatPolicy));
		return outsideNatPolicy;
	}
	
	private RmNwOutsideNatPolicyRefPo initRmNwOutsideNatPolicyRefPo(String natPolicyId, String firewallRequestId) {
		RmNwOutsideNatPolicyRefPo natPolicyRef = new RmNwOutsideNatPolicyRefPo();
		natPolicyRef.setId(UUIDGenerator.getUUID());
		natPolicyRef.setNatPolicyId(natPolicyId);
		natPolicyRef.setFirewallRequestId(firewallRequestId);
		return natPolicyRef;
	}
	
	/**
	 * 生成云外防火墙策略对象
	 * @param firewallRequest
	 * @param fwId
	 * @param srcIp
	 * @param dstIp
	 * @return
	 * @throws Exception
	 */
	private RmNwOutsideFirewallPolicyPo initRmNwOutsideFirewallPolicyPo(
			RmNwFirewallRequestPo firewallRequest, String fwId, String srcIp, String dstIp) throws Exception {
		logger.info("[ComputePolicyHandler] firewall id is " + fwId + ", source ip is " + srcIp + ", destination ip is " + dstIp);
		RmNwOutsideFirewallPolicyPo outsideFirewallPolicy = new RmNwOutsideFirewallPolicyPo();
		outsideFirewallPolicy.setId(UUIDGenerator.getUUID());
		outsideFirewallPolicy.setFirewallRequestId(firewallRequest.getId());
		outsideFirewallPolicy.setRequestCode(firewallRequest.getRequestCode());
		outsideFirewallPolicy.setFwId(fwId);
		outsideFirewallPolicy.setProtocol(firewallRequest.getProtocol());
		outsideFirewallPolicy.setAction("2");
		outsideFirewallPolicy.setSrcIp(srcIp);
		outsideFirewallPolicy.setDstIp(dstIp);
		outsideFirewallPolicy.setSrcPort(firewallRequest.getSrcPort());
		outsideFirewallPolicy.setDstPort(firewallRequest.getDstPort());
		outsideFirewallPolicy.setSrcIpDetail(srcIp);
		outsideFirewallPolicy.setDstIpDetail(dstIp);
		outsideFirewallPolicy.setStatus(PolicyStatusEnum.INVALID.getValue());
		outsideFirewallPolicy.setDescription(firewallRequest.getRequestCode());
		outsideFirewallPolicy.setIsActive("Y");
		outsideFirewallPolicy.setCreateTime(firewallRequest.getCreateTime());
		outsideFirewallPolicy.setCreateUser(firewallRequest.getCreateUser());
		logger.info("[ComputePolicyHandler] outside firewall policy is " + JSONObject.toJSONString(outsideFirewallPolicy));
		return outsideFirewallPolicy;
	}
	
	/**
	 * 生成云内防火墙策略对象
	 * @param firewallRequest
	 * @param fixIp
	 * @return
	 * @throws Exception 
	 */
	private void initRmNwVfwPolicyRulePo(RmNwFirewallRequestPo firewallRequest, String fixIp, List<RmNwVfwPolicyRulePo> hwPolicyList) throws Exception {
		// update by shl at 2018-11-14 由于需求变更，不通过cmp下发华为防火墙策略
		return;
		/*logger.info("[ComputePolicyHandler] fix ip is " + fixIp);
		fixIp = this.getIpsFirstIp(fixIp);
		RmNwVfwPo vfw = super.getFirewallAutomationService().findRmNwVfwByIp(fixIp);
		if (vfw == null) {
			throw new Exception("not found firewall for ip " + fixIp + ".");
		}
		logger.info("[ComputePolicyHandler] fix ip mapped the inside firewall is " + JSONObject.toJSONString(vfw));
		String srcIp = firewallRequest.getSrcIp();
		String dstIp = firewallRequest.getDstIp();
		List<String> srcIpList = this.getIpList(srcIp);
		List<String> dstIpList = this.getIpList(dstIp);
		String protocol = firewallRequest.getProtocol();
		String srcPort = ("any".equals(protocol) || "icmp".equals(protocol)) ? "any" : firewallRequest.getSrcPort().replaceAll(" ", "");
		String [] srcPortArr = srcPort.split(",");
		String dstPort = ("any".equals(protocol) || "icmp".equals(protocol)) ? "any" : firewallRequest.getDstPort().replaceAll(" ", "");
		String [] dstPortArr = dstPort.split(",");
		RmNwVfwPolicyRulePo vfwPolicyRule;
		for (int i = 0 ; i < srcIpList.size() ; i ++) {
			if (srcIpList.get(i).equals("")) {
				continue;
			}
			for (int j = 0 ; j < dstIpList.size() ; j ++) {
				if (dstIpList.get(j).equals("")) {
					continue;
				}
				List<String> srcPortExist = new ArrayList<String> ();
				List<String> dstPortExist = new ArrayList<String> ();
				for (int k = 0 ; k < srcPortArr.length ; k ++) {
					if (srcPortArr[k].equals("")) {
						continue;
					}
					if (srcPortExist.indexOf(srcPortArr[k]) >= 0) {
						continue;
					}
					for (int l = 0 ; l < dstPortArr.length ; l ++) {
						if (dstPortArr[l].equals("")) {
							continue;
						}
						if (dstPortExist.indexOf(dstPortArr[l]) >= 0) {
							continue;
						}
						srcPortExist.add(srcPortArr[k]);
						dstPortExist.add(dstPortArr[l]);
						vfwPolicyRule = new RmNwVfwPolicyRulePo();
						vfwPolicyRule.setVfwPolicyRuleId(UUIDGenerator.getUUID());
						vfwPolicyRule.setFirewallRequestId(firewallRequest.getId());
						vfwPolicyRule.setVfwPolicyId(vfw.getVfwPolicyId());
						vfwPolicyRule.setVfwPolicyRuleName(firewallRequest.getRequestCode());
						vfwPolicyRule.setProjectId(vfw.getProjectId());
						vfwPolicyRule.setProtocolType(firewallRequest.getProtocol());
						vfwPolicyRule.setSourceIpAddress(srcIpList.get(i));
						vfwPolicyRule.setDestIpAddress(dstIpList.get(j));
						if (!"any".equals(protocol) && !"icmp".equals(protocol)) {
							vfwPolicyRule.setSourcePort(srcPortArr[k].replace("-", ":"));
							vfwPolicyRule.setDescPort(dstPortArr[l].replace("-", ":"));
						}
						vfwPolicyRule.setRuleAction("allow");
						vfwPolicyRule.setIpVersion("4");
						vfwPolicyRule.setEnabled("true");
						vfwPolicyRule.setIsShare("false");
						vfwPolicyRule.setStatus(PolicyStatusEnum.INVALID.getValue());
						vfwPolicyRule.setIsActive("Y");
						vfwPolicyRule.setRemark("");
						logger.info("[ComputePolicyHandler] inside firewall policy is " + JSONObject.toJSONString(vfwPolicyRule));
						hwPolicyList.add(vfwPolicyRule);
					}
				}
			}
		}*/
	}
	
	/**
	 * 将被-分割的ip解析成ip地址集合
	 * @param ip
	 * @return
	 */
	private List<String> getIpList(String ip) {
		List<String> ipList = new ArrayList<String> ();
		if (ip.indexOf("-") > 0) {
			String preIp = ip.substring(0, ip.lastIndexOf(".") + 1);
			String firstIpD = ip.substring(ip.lastIndexOf(".") + 1).split("-")[0];
			String lastIpD = ip.substring(ip.lastIndexOf(".") + 1).split("-")[1];
			if (Integer.valueOf(firstIpD) >= Integer.valueOf(lastIpD)) {
				return ipList;
			}
			for (int i = Integer.valueOf(firstIpD) ; i <= Integer.valueOf(lastIpD) ; i++) {
				ipList.add(preIp + i);
			}
		} else {
			ipList.add(ip);
		}
		return ipList;
	}
	
	/**
	 * 保存云防火墙拆分后的策略
	 * @param natPolicyList
	 * @param hsPolicyList
	 * @param hwPolicyList
	 * @throws RollbackableBizException
	 */
	private void savePolicy(List<RmNwFirewallNatAddressPo> firewallNatAddressList, List<RmNwOutsideNatPolicyPo> natPolicyList,
			List<RmNwOutsideNatPolicyRefPo> natPolicyRefList, List<RmNwOutsideFirewallPolicyPo> hsPolicyList, List<RmNwVfwPolicyRulePo> hwPolicyList) throws RollbackableBizException {
		super.getFirewallAutomationService().savePolicy(firewallNatAddressList, natPolicyList, natPolicyRefList, hsPolicyList, hwPolicyList);
	}
	
	public static void main(String[] args) {
		ComputePolicyHandler computePolicyHandler = new ComputePolicyHandler();
		/*for (int i = 32 ; i >= 0 ; i --) {
			System.out.println(i + ":" + computePolicyHandler.getIpsLastIp("1.1.1.1/" + i));
		}*/
//		String ips = "1.1.1.1-222";
//		System.out.println(ips.substring(0, ips.lastIndexOf(".") + 1) + ips.split("-")[1]);
//		
		/*try {
			System.out.println(computePolicyHandler.isIpInIps("192.200.11.20/32", "192.200.11.20/32"));;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		System.out.println(computePolicyHandler.getIpList("10.10.1.34-35").toString());;
	}
}
