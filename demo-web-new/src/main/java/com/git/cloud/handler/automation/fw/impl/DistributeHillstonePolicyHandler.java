package com.git.cloud.handler.automation.fw.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.handler.automation.fw.FirewallCommonHandler;
import com.git.cloud.handler.automation.fw.model.PolicyStatusEnum;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideFirewallPolicyPo;

public class DistributeHillstonePolicyHandler extends FirewallCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(DistributeHillstonePolicyHandler.class);
	
	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		logger.info("[DistributeHillstonePolicyHandler] distribute policy start ...");
		String srId = (String) contextParams.get("srvReqId");
		String firewallRequestId = super.findFirewallRequestIdBySrId(srId);
		// 根据防火墙请求ID获取policy策略
		List<RmNwOutsideFirewallPolicyPo> hsPolicyList = super.getFirewallAutomationService().findRmNwOutsideFirewallPolicyListByFirewallRequestId(firewallRequestId);
		logger.info("[DistributeHillstonePolicyHandler] the object hsPolicyList is : " + JSONObject.toJSONString(hsPolicyList));
		int hsPolicyLen = hsPolicyList == null ? 0 : hsPolicyList.size();
		RmNwOutsideFirewallPolicyPo hsPolicy;
		for (int i = 0 ; i < hsPolicyLen ; i++) {
			logger.info("[DistributeHillstonePolicyHandler] distribute hsPolicy [" + i + "] start ...");
			hsPolicy = hsPolicyList.get(i);
			String targetPolicyId = super.getFirewallAutoRao().createHillstonePolicy(hsPolicy);
			if (targetPolicyId != null && !"".equals(targetPolicyId)) {
				logger.info("[DistributeHillstonePolicyHandler] update targetPolicyId is : " + targetPolicyId);
				hsPolicy.setTargetPolicyId(targetPolicyId);
				hsPolicy.setStatus(PolicyStatusEnum.VALID.getValue());
				super.getFirewallAutomationService().updateOutsideFirewallPolicyValidStatus(hsPolicy);
				logger.info("[DistributeHillstonePolicyHandler] distribute hsPolicy [" + i + "] end ...");
			}
		}
		logger.info("[DistributeHillstonePolicyHandler] distribute nat end ...");
	}
}
