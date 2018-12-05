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
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.rest.client.RestClient;
import com.git.cloud.rest.client.RestModel;
import com.git.cloud.rest.client.RestResult;

/** 
 * 创建fi cluster
 */
public class FiClusterHealthCheckHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(FiClusterHealthCheckHandler.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		BmSrRrinfoPo rrinfo = getAutomationService().getRrinfo(rrinfoId);
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
		JSONObject jso = new JSONObject();
		HashMap<String, String> pa = (HashMap<String, String>) reqMap.get(deviceIdList.get(0));
		String clusterId = json.getString("serviceInstanceId");
		jso.put("serviceInstanceId", clusterId);
		jso.put("vmMsId", pa.get("OPENSTACK_ID"));
		//调用租户接口
		String url = getParameterService().getParamValueByName("CLUSTER_CHECK_URL");
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
				logger.error("集群集群健康检查",sendRestRequest);
				throw new Exception("集群集群健康检查:"+sendRestRequest.getJsonData());
			}
		} catch (Exception e) {
			logger.error("集群集群健康检查",sendRestRequest);
			throw new Exception("集群集群健康检查:"+sendRestRequest.getJsonData());
		}
	}
	
	public ParameterService getParameterService() throws Exception {
		return (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
	}
	
	public AutomationService getAutomationService() throws Exception {
		return (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
	}
	
	public ICmDeviceDAO getCmDeviceDAO() throws Exception {
		return (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
	}
	
	public static void main(String[] args) throws Exception {
		JSONObject jso = new JSONObject();
		jso.put("serviceInstanceId", "3D9A447E4ADF425F9A0F4BC79EF8685B");
		jso.put("vmMsId", "876CFEA8F57D464690BED2EEAE189B36");
		String url = "http://localhost:8080/icms-tenant/fi/healthcheck";
		String requestMethod = "POST"; 
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		RestModel restModel = new RestModel();
		restModel.setHeader(header);
		restModel.setRequestJosnData(jso.toJSONString());
		restModel.setTargetURL(url);
		restModel.setRequestMethod(requestMethod);
		RestResult sendRestRequest = new RestResult();
		try {
			sendRestRequest = RestClient.sendRestRequest(restModel);
			String jsonData = sendRestRequest.getJsonData();
			JSONObject parseObject = JSONObject.parseObject(jsonData);
			if(!"0".equals(parseObject.getString("code"))){
				logger.error("调用租户创建集群失败",sendRestRequest);
				throw new Exception("调用租户创建集群失败:"+sendRestRequest.getJsonData());
			}
		} catch (Exception e) {
			logger.error("调用租户创建集群失败",sendRestRequest);
			throw new Exception("调用租户创建集群失败:"+sendRestRequest.getJsonData());
		}
	}
}
