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
 * 下发山石防火墙NAT
 * @author shl
 */
public class DistributeHillstoneNatHandler extends FirewallCommonHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DistributeHillstoneNatHandler.class);

	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		logger.info("[DistributeHillstoneNatHandler] distribute nat start ...");
		String srId = (String) contextParams.get("srvReqId");
		String firewallRequestId = super.findFirewallRequestIdBySrId(srId);
		// 根据防火墙请求ID获取NAT策略
		List<RmNwOutsideNatPolicyPo> natPolicyList = super.getFirewallAutomationService().findRmNwOutsideNatPolicyListByFirewallRequestId(firewallRequestId);
		logger.info("[DistributeHillstoneNatHandler] the object natPolicyList is : " + JSONObject.toJSONString(natPolicyList));
		int natPolicyLen = natPolicyList == null ? 0 : natPolicyList.size();
		RmNwOutsideNatPolicyPo natPolicy;
		for (int i = 0 ; i < natPolicyLen ; i++) {
			logger.info("[DistributeHillstoneNatHandler] distribute natPolicy [" + i + "] start ...");
			String targetNatId = null;
			natPolicy = natPolicyList.get(i);
			if(natPolicy == null || natPolicy.getNatType() == null) {
				continue;
			}
			if(natPolicy.getTargetNatId() != null && !"".equals(natPolicy.getTargetNatId())) {
				continue;
			}
			try {
				if (natPolicy.getNatType().equals(NatTypeEnum.DNAT.getValue())) {
					targetNatId = super.getFirewallAutoRao().createHillstoneDnat(natPolicy);
				} else if (natPolicy.getNatType().equals(NatTypeEnum.SNAT.getValue())) {
					targetNatId = super.getFirewallAutoRao().createHillstoneSnat(natPolicy);
				}
			} catch(Exception e) {
				e.printStackTrace();
				logger.error("[DistributeHillstoneNatHandler] distribute natPolicy [" + i + "] error, " + e.getMessage(), e);
				throw new Exception (" distribute natPolicy has error, " + e.getMessage());
			}
			if (targetNatId != null && !"".equals(targetNatId)) {
				logger.info("[DistributeHillstoneNatHandler] update targetNatId is : " + targetNatId);
				natPolicy.setTargetNatId(targetNatId);
				natPolicy.setStatus(NatStatusEnum.VALID.getValue());
				super.getFirewallAutomationService().updateOutsideNatPolicyValidStatus(natPolicy);
				logger.info("[DistributeHillstoneNatHandler] distribute natPolicy [" + i + "] end ...");
			}
		}
		logger.info("[DistributeHillstoneNatHandler] distribute nat end ...");
	}
}
