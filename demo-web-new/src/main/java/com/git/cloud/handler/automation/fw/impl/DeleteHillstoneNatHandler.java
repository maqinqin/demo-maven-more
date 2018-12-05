package com.git.cloud.handler.automation.fw.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.handler.automation.fw.FirewallCommonHandler;
import com.git.cloud.handler.automation.fw.model.NatStatusEnum;
import com.git.cloud.handler.automation.fw.model.NatTypeEnum;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideNatPolicyPo;

/**
 * 删除山石防火墙NAT
 * @author shl
 */
public class DeleteHillstoneNatHandler extends FirewallCommonHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DeleteHillstoneNatHandler.class);

	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		logger.info("[DeleteHillstoneNatHandler] delete nat start ...");
		String srId = (String) contextParams.get("srvReqId");
		String firewallRequestId = super.findFirewallRequestIdBySrId(srId);
		// 根据防火墙请求ID获取NAT策略
		List<RmNwOutsideNatPolicyPo> natPolicyList = super.getFirewallAutomationService().findRmNwOutsideNatPolicyListByFirewallRequestId(firewallRequestId);
		logger.info("[DeleteHillstoneNatHandler] the object natPolicyList is : " + JSONObject.toJSONString(natPolicyList));
		int natPolicyLen = natPolicyList == null ? 0 : natPolicyList.size();
		RmNwOutsideNatPolicyPo natPolicy = null;
		String ids = "";
		for (int i = 0 ; i < natPolicyLen ; i++) {
			natPolicy = natPolicyList.get(i);
			ids += "," + natPolicy.getTargetNatId();
		}
		if (ids.length() > 0) {
			logger.info("[DeleteHillstoneNatHandler] delete hillstone nat start ...");
			super.getFirewallAutomationService().updateOutsideNatPolicyDeleteStatus(natPolicyList);
			ids = ids.substring(1);
			if (natPolicy.getNatType().equals(NatTypeEnum.DNAT.getValue())) {
				super.getFirewallAutoRao().deleteHillstoneDnat(natPolicy.getFwId(), ids.split(","));
			} else if (natPolicy.getNatType().equals(NatTypeEnum.SNAT.getValue())) {
				super.getFirewallAutoRao().deleteHillstoneSnat(natPolicy.getFwId(), ids.split(","));
			}
			logger.info("[DeleteHillstoneNatHandler] delete hillstone nat end ...");
		}
		// 根据防火墙申请ID删除关联的Nat策略关系表
		super.getFirewallAutomationService().deleteRmNwOutsideNatPolicyRefByFirewallRequestId(firewallRequestId);
		logger.info("[DeleteHillstoneNatHandler] delete nat end ...");
	}
}
