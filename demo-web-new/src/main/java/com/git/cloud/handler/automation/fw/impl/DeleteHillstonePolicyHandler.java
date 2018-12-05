package com.git.cloud.handler.automation.fw.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.handler.automation.fw.FirewallCommonHandler;
import com.git.cloud.handler.automation.fw.model.NatStatusEnum;
import com.git.cloud.handler.automation.fw.model.RmNwOutsideFirewallPolicyPo;

/**
 * 删除山石防火墙安全策略
 * @author shl
 */
public class DeleteHillstonePolicyHandler extends FirewallCommonHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DeleteHillstonePolicyHandler.class);

	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		logger.info("[DeleteHillstonePolicyHandler] delete policy start ...");
		String srId = (String) contextParams.get("srvReqId");
		String firewallRequestId = super.findFirewallRequestIdBySrId(srId);
		// 根据防火墙请求ID获取NAT策略
		List<RmNwOutsideFirewallPolicyPo> hsPolicyList = super.getFirewallAutomationService().findRmNwOutsideFirewallPolicyListByFirewallRequestId(firewallRequestId);
		logger.info("[DeleteHillstonePolicyHandler] the object hsPolicyList is : " + JSONObject.toJSONString(hsPolicyList));
		int hsPolicyLen = hsPolicyList == null ? 0 : hsPolicyList.size();
		RmNwOutsideFirewallPolicyPo hsPolicy = null;
		String ids = "";
		for (int i = 0 ; i < hsPolicyLen ; i++) {
			hsPolicy = hsPolicyList.get(i);
			ids += "," + hsPolicy.getTargetPolicyId();
		}
		if (ids.length() > 0) {
			logger.info("[DeleteHillstonePolicyHandler] delete hillstone policy start ...");
			super.getFirewallAutomationService().updateOutsideFirewallPolicyDeleteStatus(hsPolicyList);
			ids = ids.substring(1);
			super.getFirewallAutoRao().deleteHillstonePolicy(hsPolicy.getFwId(), ids.split(","));
			logger.info("[DeleteHillstonePolicyHandler] delete hillstone policy end ...");
		}
		logger.info("[DeleteHillstonePolicyHandler] delete policy end ...");
	}
}
