package com.git.cloud.handler.automation.fw.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.handler.automation.fw.FirewallCommonHandler;
import com.git.cloud.handler.automation.fw.model.PolicyStatusEnum;
import com.git.cloud.handler.automation.fw.model.RmNwVfwPolicyRulePo;

public class DistributeHuaweiPolicyHandler extends FirewallCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(DistributeHuaweiPolicyHandler.class);

	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		logger.info("[DistributeHuaweiPolicyHandler] distribute policy start ...");
		String srId = (String) contextParams.get("srvReqId");
		String firewallRequestId = super.findFirewallRequestIdBySrId(srId);
		// 根据防火墙请求ID获取policy策略
		List<RmNwVfwPolicyRulePo> hwPolicyList = super.getFirewallAutomationService().findRmNwInsideFirewallPolicyListByFirewallRequestId(firewallRequestId);
		logger.info("[DistributeHuaweiPolicyHandler] the object hwPolicyList is : " + JSONObject.toJSONString(hwPolicyList));
		int hwPolicyLen = hwPolicyList == null ? 0 : hwPolicyList.size();
		RmNwVfwPolicyRulePo hwPolicy;
		for (int i = 0 ; i < hwPolicyLen ; i++) {
			logger.info("[DistributeHuaweiPolicyHandler] distribute hwPolicy [" + i + "] start ...");
			hwPolicy = hwPolicyList.get(i);
			if (hwPolicy.getStatus().equals(PolicyStatusEnum.VALID.getValue())) {
				continue;
			}
			// 和海龙确认,此id为防火墙策略规则id
			String targetPolicyId = super.getFirewallAutoRao().createHuaweiPolicy(hwPolicy);
			if (targetPolicyId != null && !"".equals(targetPolicyId)) {
				logger.info("[DistributeHuaweiPolicyHandler] update targetPolicyId is : " + targetPolicyId);
				hwPolicy.setStatus(PolicyStatusEnum.VALID.getValue());
				super.getFirewallAutomationService().updateInsideFirewallPolicyValidStatus(hwPolicy);
				hwPolicy.setIaasUuid(targetPolicyId);
				super.getFirewallAutomationService().updateInsideFirewallPolicyRuleIaasUuid(hwPolicy);
				logger.info("[DistributeHuaweiPolicyHandler] distribute hwPolicy [" + i + "] end ...");
			}
		}
		logger.info("[DistributeHuaweiPolicyHandler] distribute nat end ...");
	}
}
