package com.git.cloud.handler.automation.os.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.os.OpenstackCommonHandler;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.rest.client.RestClient;
import com.git.cloud.rest.client.RestModel;
import com.git.cloud.rest.client.RestResult;

/** 
 * 创建fi cluster
 */
public class UpdateFmPasswordHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(UpdateFmPasswordHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		JSONObject jso = new JSONObject();
		String fmClientPassword = getParameterService().getParamValueByName("FM_PASSWORD");
		String fmIp = "";
		String fmIpMap = "";
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			logger.debug(logCommon + "开始...");
			deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
			String type = deviceParams.get("NODE_TYPE");
			if("Y".equals(type)){
				fmIp = deviceParams.get("NEW_FM_IP");
				fmIpMap = deviceParams.get("FM_IP_MAP");
				break;
			}
		
		}
		if(fmIpMap == null || "".equals(fmIpMap)){
			jso.put("fmIp", fmIp);
		}else{
			jso.put("fmIp", fmIpMap);
		}
		jso.put("newPassword", fmClientPassword);
		//调用租户接口
		String url = getParameterService().getParamValueByName("UPDATE_FM_FIPASSWORD_URL");
		String requestMethod = "POST"; 
		String requestJosnData = jso.toJSONString();
		logger.info("requestJosnData:{}",requestJosnData);
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		RestModel restModel = new RestModel();
		restModel.setHeader(header);
		restModel.setRequestJosnData(requestJosnData);
		restModel.setTargetURL(url);
		restModel.setRequestMethod(requestMethod);
		RestResult sendRestRequest = new RestResult();
		try {
			sendRestRequest = RestClient.sendRestRequest(restModel);
			String jsonData = sendRestRequest.getJsonData();
			JSONObject parseObject = JSONObject.parseObject(jsonData);
			if(!"0".equals(parseObject.getString("code"))){
				logger.error("修改FM密码失败",sendRestRequest);
				throw new Exception("修改FM密码失败:"+sendRestRequest.getJsonData());
			}
		} catch (Exception e) {
			logger.error("修改FM密码失败",sendRestRequest.getJsonData());
			throw new Exception("修改FM密码失败:"+sendRestRequest.getJsonData());
		}
	}
	
	public ParameterService getParameterService() throws Exception {
		return (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
	}
	
	public static void main(String[] args) throws Exception {
		//调用租户接口
		JSONObject jso = new JSONObject();
		jso.put("fmIp", "172.22.58.16");
		jso.put("newPassword", "Huawei12#$");
				String url = "http://localhost:8080/icms-tenant/fi/modify/password";
				String requestMethod = "POST"; 
				String requestJosnData = jso.toJSONString();
				logger.info("requestJosnData:{}",requestJosnData);
				Map<String, String> header = new HashMap<String,String>();
				header.put("Content-Type", "application/json");
				RestModel restModel = new RestModel();
				restModel.setHeader(header);
				restModel.setRequestJosnData(requestJosnData);
				restModel.setTargetURL(url);
				restModel.setRequestMethod(requestMethod);
				RestResult sendRestRequest = new RestResult();
				try {
					sendRestRequest = RestClient.sendRestRequest(restModel);
					String jsonData = sendRestRequest.getJsonData();
					JSONObject parseObject = JSONObject.parseObject(jsonData);
					if(!"0".equals(parseObject.getString("code"))){
						logger.error("修改FM密码失败",sendRestRequest);
						throw new Exception("修改FM密码失败:"+sendRestRequest.getJsonData());
					}
				} catch (Exception e) {
					logger.error("修改FM密码失败",sendRestRequest.getJsonData());
					throw new Exception("修改FM密码失败:"+sendRestRequest.getJsonData());
				}
	}
	
	
}
