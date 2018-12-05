package com.git.cloud.handler.automation.fw.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.handler.automation.fw.FirewallCommonHandler;
import com.git.cloud.handler.automation.fw.model.RmNwVfwPolicyRulePo;

/**
 * 删除华为防火墙安全策略
 * @author shl
 */
public class DeleteHuaweiPolicyHandler extends FirewallCommonHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DeleteHuaweiPolicyHandler.class);

	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		logger.info("[DeleteHuaweiPolicyHandler] delete policy start ...");
		String srId = (String) contextParams.get("srvReqId");
		String firewallRequestId = super.findFirewallRequestIdBySrId(srId);
		// 根据防火墙请求ID获取NAT策略
		List<RmNwVfwPolicyRulePo> hwPolicyList = super.getFirewallAutomationService().findRmNwInsideFirewallPolicyListByFirewallRequestId(firewallRequestId);
		logger.info("[DeleteHuaweiPolicyHandler] the object hwPolicyList is : " + JSONObject.toJSONString(hwPolicyList));
		int hwPolicyLen = hwPolicyList == null ? 0 : hwPolicyList.size();
		RmNwVfwPolicyRulePo hwPolicy = null;
		String ids = "";
		for (int i = 0 ; i < hwPolicyLen ; i++) {
			hwPolicy = hwPolicyList.get(i);
			String targetId = super.getFirewallAutomationService().selectRmNwVfwPolicyRulePoById(hwPolicy.getVfwPolicyRuleId()).getIaasUuid();
			ids += "," + targetId;
		}
		if (ids.length() > 0) {
			logger.info("[DeleteHuaweiPolicyHandler] delete huawei policy start ...");
			super.getFirewallAutomationService().updateInsideFirewallPolicyDeleteStatus(hwPolicyList);
			ids = ids.substring(1);
			super.getFirewallAutoRao().deleteHuaweiPolicy(hwPolicyList.get(0), ids.split(","));
			logger.info("[DeleteHuaweiPolicyHandler] delete huawei policy end ...");
		}
		logger.info("[DeleteHuaweiPolicyHandler] delete policy end ...");
	}
}
