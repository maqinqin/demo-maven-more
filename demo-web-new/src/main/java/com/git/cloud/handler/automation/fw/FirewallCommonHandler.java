package com.git.cloud.handler.automation.fw;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.fw.model.RmNwFirewallRequestPo;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.handler.service.FirewallAutomationService;
import com.git.cloud.network.service.FirewallRequestService;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.support.common.MesgRetCode;
import com.google.common.collect.Maps;

public class FirewallCommonHandler {
	
	private static Logger logger = LoggerFactory.getLogger(FirewallCommonHandler.class);
	
	private FirewallAutoRao firewallAutoRao;
	private FirewallAutomationService firewallAutomationServiceImpl;
	private FirewallRequestService firewallRequestServiceImpl;
	
	public String execute(HashMap<String, Object> contextParams) {
		Map<String, Object> resultMap = Maps.newHashMap();
		if (contextParams!=null) {
			String className = (String) contextParams.get("CLASS_NAME");
			String srvReqId = (String) contextParams.get(SeConstants.SRV_REQ_ID);
			String commonInfo = "云防火墙申请ID:" + srvReqId;
			try {
				Class<?> clazz = Class.forName("com.git.cloud.handler.automation.fw.impl." + className);
				Method method = clazz.getMethod("executeOperate", new Class[] {contextParams.getClass()});
				method.invoke(clazz.newInstance(), new Object[] {contextParams});
				resultMap.put("checkCode", MesgRetCode.SUCCESS);
				logger.info("[FirewallCommonHandler]执行类["+className+"]正常结束" + commonInfo);
			} catch(Exception e) {
				resultMap.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				resultMap.put("exeMsg", e.getCause().getMessage());
				logger.error("[FirewallCommonHandler]执行类["+className+"]异常结束" + e.getCause().getMessage() + commonInfo);
				e.printStackTrace();
			} finally {
				if (contextParams != null)
					contextParams.clear();
			}
		} else {
			resultMap.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			resultMap.put("exeMsg", "ERR_PARAMETER_WRONG; contextParams is null!");	
		}
		return JSON.toJSONString(resultMap);
	}
	
	protected FirewallAutoRao getFirewallAutoRao() {
		if (firewallAutoRao == null) {
			firewallAutoRao = new FirewallAutoRao();
		}
		return firewallAutoRao;
	}
	
	protected FirewallAutomationService getFirewallAutomationService() {
		if (firewallAutomationServiceImpl == null) {
			firewallAutomationServiceImpl = (FirewallAutomationService) WebApplicationManager.getBean("firewallAutomationServiceImpl");
		}
		return firewallAutomationServiceImpl;
	}
	
	protected FirewallRequestService getFirewallRequestService() {
		if (firewallRequestServiceImpl == null) {
			firewallRequestServiceImpl = (FirewallRequestService) WebApplicationManager.getBean("firewallRequestServiceImpl");
		}
		return firewallRequestServiceImpl;
	}
	
	protected RmNwFirewallRequestPo findRmNwFirewallRequest(String srId) {
		return this.getFirewallAutomationService().findRmNwFirewallRequestBySrId(srId);
	}
	
	protected String findFirewallRequestIdBySrId(String srId) {
		BmSrRrinfoPo rrinfo = this.getFirewallAutomationService().findBmSrRrinfoBySrId(srId);
		String parametersJson = rrinfo.getParametersJson();
		logger.info("[FirewallCommonHandler] parametersJson : " + parametersJson);
		JSONObject json = JSONObject.parseObject(parametersJson);
		return json.getString("firewallRequestId");
	}
}
