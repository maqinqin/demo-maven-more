package com.git.cloud.handler.automation.fw.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.handler.automation.fw.FirewallCommonHandler;
import com.git.cloud.handler.automation.fw.model.FwRequestStatusEnum;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.request.model.po.BmSrRrinfoPo;

public class CloudFirewallCloseHandler extends FirewallCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(CloudFirewallCloseHandler.class);

	public void executeOperate(HashMap<String, Object> contextParams) throws Exception {
		logger.info("[FirewallCloseHandler] distribute policy start ...");
		String srId = (String) contextParams.get("srvReqId");
		BmSrRrinfoPo rrinfo = super.getFirewallAutomationService().findBmSrRrinfoBySrId(srId);
		String parametersJson = rrinfo.getParametersJson();
		logger.info("[FirewallCloseHandler] parametersJson : " + parametersJson);
		JSONObject json = JSONObject.parseObject(parametersJson);
		String firewallRequestId = json.getString("firewallRequestId");
		String srTypeMark = json.getString("srTypeMark");
		if (srTypeMark.equals(SrTypeMarkEnum.FIREWALL.getValue())) {
			// 云防火墙申请
			super.getFirewallRequestService().updateFirewallRequestStatus(firewallRequestId, FwRequestStatusEnum.OPENED.getValue(),IsActiveEnum.YES.getValue());
		} else {
			// 云防火墙回收
			super.getFirewallRequestService().updateFirewallRequestStatus(firewallRequestId, FwRequestStatusEnum.CLOSED.getValue(),IsActiveEnum.NO.getValue());
		}
		super.getFirewallAutomationService().updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_CLOSED.getValue());
		logger.info("[FirewallCloseHandler] distribute nat end ...");
	}
}
