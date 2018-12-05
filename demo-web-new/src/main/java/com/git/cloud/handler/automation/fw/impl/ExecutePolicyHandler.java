package com.git.cloud.handler.automation.fw.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.handler.automation.fw.FirewallCommonHandler;
import com.git.cloud.handler.automation.fw.model.ExecuteTypeEnum;
import com.git.cloud.handler.automation.fw.model.RmNwFirewallRequestPo;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.request.model.po.BmSrRrinfoPo;

public class ExecutePolicyHandler extends FirewallCommonHandler{

	private static Logger logger = LoggerFactory.getLogger(ExecutePolicyHandler.class);

	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		String srId = (String) contextParams.get("srvReqId");
		BmSrRrinfoPo rrinfo = super.getFirewallAutomationService().findBmSrRrinfoBySrId(srId);
		String parametersJson = rrinfo.getParametersJson();
		logger.info("[ExecutePolicyHandler] parametersJson : " + parametersJson);
		JSONObject json = JSONObject.parseObject(parametersJson);
		String firewallRequestId = json.getString("firewallRequestId");
		String srTypeMark = json.getString("srTypeMark");
		RmNwFirewallRequestPo rmNwFirewallRequestPo = super.getFirewallAutomationService()
				.findRmNwFirewallRequestById(firewallRequestId);
		if (srTypeMark.equals(SrTypeMarkEnum.FIREWALL.getValue())) {
			String openExecuteType = rmNwFirewallRequestPo.getOpenExecuteType();
			if (openExecuteType.equals(ExecuteTypeEnum.IMMEDIATE.getValue())) {
			} else {
				throw new Exception("当前开通申请为定时执行，定时执行时间为："+rmNwFirewallRequestPo.getOpenExecuteTime());
			}
		} else {
			String closeExecuteType = rmNwFirewallRequestPo.getCloseExecuteType();
			if (closeExecuteType.equals(ExecuteTypeEnum.IMMEDIATE.getValue())) {
			} else {
				throw new Exception("当前回收申请为定时执行，定时执行时间为："+rmNwFirewallRequestPo.getCloseExecuteTime());
			}
		}
	}
}
