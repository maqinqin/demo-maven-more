package com.git.cloud.rest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.OperationType;
import com.git.cloud.common.enums.ResourceType;
import com.git.cloud.common.enums.Source;
import com.git.cloud.common.enums.Type;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.rest.model.InstanceInfoVo;
import com.git.cloud.rest.model.ReturnMeta;
import com.git.cloud.workflow.service.IWorkFlowService;

@Controller
@RequestMapping("/v1/notification/actions")
public class NotificationApi {
	private static String SUCCESS="0000";
	private static String FAIL = "9999";
	static Logger logger = LoggerFactory.getLogger(NotificationApi.class);
	
	@RequestMapping(method = RequestMethod.POST, value = "/insert", produces= MediaType.APPLICATION_JSON )
	@ResponseBody
	public Map<String,ReturnMeta> create(HttpServletRequest request){
		Map<String,ReturnMeta> map = new HashMap<String,ReturnMeta>();
		logger.debug("节点告警调用web接口开始");
		ReturnMeta result = new ReturnMeta();
		result.setCode(SUCCESS);
		InstanceInfoVo instanceNodeVoJson = null;
		try { 
			instanceNodeVoJson = this.thansToJsonData(request);
			String instancedId = instanceNodeVoJson.getInstanceId();
			if(CommonUtil.isEmpty(instancedId)) {
				throw new RollbackableBizException ("instancedId is null");
			}
			INotificationService notiService = (INotificationService)WebApplicationManager.getBean("noti_service");
			IWorkFlowService workFlowService = (IWorkFlowService)WebApplicationManager.getBean("workFlowServiceImpl");
			InstanceInfoVo instanceNodeVo = workFlowService.getInstanceInfoById(instanceNodeVoJson.getInstanceId());
			NotificationPo po = this.buildRequestDate(instanceNodeVo, instanceNodeVoJson);
			notiService.insertNotification(po);
		} catch (RollbackableBizException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
			logger.error("异常" + e);
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
			logger.error("异常" + e);
		}
		logger.debug("节点告警调用web接口结束");
		map.put("meta", result);
		return map;
	}
	//jsonData中主要包括实例id，实例名称，节点ID，节点名称,告警信息
	//vo中主要包括 创建用户id，创建用户名称，所属租户 id，所属租户名称
	public NotificationPo buildRequestDate(InstanceInfoVo vo, InstanceInfoVo jsonData)throws RollbackableBizException {
		if(CommonUtil.isEmpty(vo)) {
			throw new RollbackableBizException("instanceInfoVo is null");
		}
		if(CommonUtil.isEmpty(jsonData)) {
			throw new RollbackableBizException("jsonData is null");
		}
		String userName= vo.getUserName();
		if(CommonUtil.isEmpty(userName)) {
			throw new RollbackableBizException("userName is null");
		}
		String tenantName= vo.getTenantName();
		if(CommonUtil.isEmpty(tenantName)) {
			throw new RollbackableBizException("tenantName is null");
		}
		String instanceName = jsonData.getInstanceName();
		if(CommonUtil.isEmpty(instanceName)) {
			throw new RollbackableBizException("instanceName is null");
		}
		String nodeName = jsonData.getNodeName();
		if(CommonUtil.isEmpty(nodeName)) {
			throw new RollbackableBizException("nodeName is null");
		}
		String nodeId = jsonData.getNodeId();
		if(CommonUtil.isEmpty(nodeId)) {
			throw new RollbackableBizException("nodeId is null");
		}
		String errorInfo = jsonData.getErrorInfo();
		String operationContent = "用户："+userName+"创建的流程实例："+instanceName+",在节点："+nodeName+"发生告警，告警内容："+errorInfo;
		NotificationPo notiPo = new NotificationPo();
		notiPo.setUserId(vo.getUserId());
		notiPo.setTenantId(vo.getTenantId());
		notiPo.setOperationType(OperationType.NODE_ALARM.getValue());
		notiPo.setResourceId(nodeId);
		notiPo.setResourceType(ResourceType.WORKFLOW_NODE.getValue());
		notiPo.setSource(Source.AUTORECOGNITION.getValue());
		notiPo.setType(Type.WARNING.getValue());
		notiPo.setOperationContent(operationContent);
		return notiPo;
	}
	
	private InstanceInfoVo thansToJsonData(HttpServletRequest request) throws Exception {
		StringBuilder buffer = new StringBuilder();
		request.setCharacterEncoding("UTF-8");
		BufferedReader reader = null;
		InstanceInfoVo instanceNodeVo = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = null;
			boolean flag = false;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				flag = true;
			}
			if (flag) {
				logger.info("buffer is: {}", buffer.toString());
				logger.info("传递的参数信息：" + JSONObject.parse(buffer.toString()));
				instanceNodeVo = (InstanceInfoVo)JSONObject.parseObject(buffer.toString(), InstanceInfoVo.class);
				logger.info(instanceNodeVo.toString());
			}
		} catch (Exception e) {
			throw new Exception("参数转换有误",e);
		}
		return instanceNodeVo;
		
	}
}
