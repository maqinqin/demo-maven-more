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
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.service.IRequestWorkflowService;
import com.git.cloud.rest.model.ReturnMeta;
import com.git.cloud.rest.model.ServiceRequestParam;
import com.git.cloud.rest.service.impl.ServiceRequestImpl;

@Controller
@RequestMapping("/v1/cloudImplement")
public class ServiceRequestApi {
	private static String SUCCESS="0000";
	private static String FAIL = "9999";
	static Logger logger = LoggerFactory.getLogger(ServiceRequestApi.class);
	
	@RequestMapping(method = RequestMethod.POST, value = "/createMainWorkflowInstance", produces= MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String, ReturnMeta> createWorkflowInstance(HttpServletRequest request) {
		Map<String ,ReturnMeta> resultMap = new HashMap<String, ReturnMeta> ();
		logger.info("创建主流程实例接口开始");
		ReturnMeta result = new ReturnMeta();
		result.setCode(SUCCESS);
		String instanceId = "";
		ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
		try {
			JSONObject serviceRequestParam = this.thansToJsonData(request);
			Object srId = serviceRequestParam.get("srId");
			Object srCode = serviceRequestParam.get("srCode");
			Object srTypeMark = serviceRequestParam.get("srTypeMark");
			if(!CommonUtil.isEmpty(srId) && !CommonUtil.isEmpty(srCode) && !CommonUtil.isEmpty(srTypeMark)) {
				// 创建主流程实例
				instanceId = resourseService.createMainInstance(srId.toString(), srCode.toString(),srTypeMark.toString());
			}else {
				result.setCode(FAIL);
				result.setMessage("创建主流程传递参数有误,srId:["+ srId +"],srCode:"+"["+ srCode +"],srTypeMark:"+"["+ srTypeMark +"]");
				logger.error("创建主流程传递参数有误,srId:["+ srId +"],srCode:"+"["+ srCode +"],srTypeMark:"+"["+ srTypeMark +"]");
			}
			if(instanceId != null && instanceId != "") {
				// 启动主流程实例
				resourseService.startMainInstance(srId.toString(), instanceId);;
				result.setInstanceId(instanceId);
			}else {
				result.setCode(FAIL);
				result.setMessage("启动主流程实例");
				logger.error("启动主流程实例");
			}
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
			logger.error("创建主流程实例失败");
		}
		logger.info("创建主流程实例接口结束");
		resultMap.put("meta", result);
		return resultMap;
	}
	/**
	 * 驱动流程
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/driveWorkflow", produces= MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String, ReturnMeta> driveWorkflow(HttpServletRequest request) {
		Map<String ,ReturnMeta> resultMap = new HashMap<String, ReturnMeta> ();
		logger.info("驱动流程接口开始");
		ReturnMeta result = new ReturnMeta();
		result.setCode(SUCCESS);
		ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
		try {
			JSONObject serviceRequestParam = this.thansToJsonData(request);
			Object todoId = serviceRequestParam.get("todoId");
			Object driveWfType = serviceRequestParam.get("driveWfType");
			if(!CommonUtil.isEmpty(todoId) && !CommonUtil.isEmpty(driveWfType)) {
				resourseService.startDriveWorkflow(todoId.toString(), driveWfType.toString());
			}else {
				result.setCode(FAIL);
				result.setMessage("驱动流程传递参数有误,todoId:["+ todoId +"],driveWfType:"+"["+ driveWfType +"]");
				logger.error("驱动流程传递参数有误,todoId:["+ todoId +"],driveWfType:"+"["+ driveWfType +"]");
			}
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
			logger.error("驱动流程失败",e);
		}
		logger.info("驱动流程接口结束");
		resultMap.put("meta", result);
		return resultMap;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/resourceAssign", produces= MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String, ReturnMeta> resourceAssign(HttpServletRequest request) {
		Map<String ,ReturnMeta> resultMap = new HashMap<String, ReturnMeta> ();
		logger.info("[ServiceRequestApi]供给分配资源接口开始");
		ReturnMeta result = new ReturnMeta();
		result.setCode(SUCCESS);
		ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
		try {
			JSONObject serviceRequestParam = this.thansToJsonData(request);
			String srId = serviceRequestParam.getString("srId");
			if(!CommonUtil.isEmpty(srId) ) {
				String msg = resourseService.startResourceAssign(srId);
				result.setMessage(msg);
			}else {
				result.setCode(FAIL);
				result.setMessage("创建主流程传递参数有误,srId:["+ srId +"]");
				logger.error("创建主流程传递参数有误,srId:["+ srId +"]");
			}
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
			logger.error("创建主流程实例失败");
		}
		logger.info("[ServiceRequestApi]供给分配资源接口结束");
		resultMap.put("meta", result);
		return resultMap;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/supply", produces= MediaType.APPLICATION_JSON )
	@ResponseBody
	public Map<String,ReturnMeta> supply(HttpServletRequest request){
		Map<String,ReturnMeta> map = new HashMap<String,ReturnMeta>();
		logger.info("供给调用web接口开始");
		ReturnMeta result = new ReturnMeta();
		BmSrVo bmSr = null;
		String instanceId = ""; 
		result.setCode(SUCCESS);
		JSONObject serviceRequestParam = null;
		try {
			serviceRequestParam = this.thansToJsonData(request);
			ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
			bmSr = resourseService.startService(serviceRequestParam.getString("operModelType"), serviceRequestParam);
			if(bmSr != null ){
				instanceId = resourseService.createMainInstance(bmSr);
				if(instanceId != null && !instanceId.equals("")){
					resourseService.startMainInstance(instanceId);
					result.setSrCode(bmSr.getSrCode());
					result.setInstanceId(instanceId);
				}else{
					logger.error("启动审批流程失败");
					result.setCode(FAIL);
					throw new RollbackableBizException("启动审批流程失败");
				}
			}else{
				logger.error("初始化供给信息失败,请检查参数信息");
				result.setCode(FAIL);
				throw new RollbackableBizException("初始化供给信息失败，请检查参数信息");
			}
		} catch (IOException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (InterruptedException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (RollbackableBizException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		}
		logger.info("供给调用web接口结束");
		map.put("meta", result);
		return map;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/alteration", produces= MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String,ReturnMeta> alteration(HttpServletRequest request) {
		Map<String,ReturnMeta> map = new HashMap<String,ReturnMeta>();
		logger.info("扩容调用web接口开始");
		ReturnMeta result = new ReturnMeta();
		result.setCode(SUCCESS);
		BmSrVo bmSr = null;
		String instanceId = ""; 
		JSONObject serviceRequestParam = null;
		try {
			serviceRequestParam = this.thansToJsonData(request);
			ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
			bmSr = resourseService.startService("VE", serviceRequestParam);
			if(bmSr != null ){
				instanceId = resourseService.createMainInstance(bmSr);
				if(instanceId != null && !instanceId.equals("")){
					resourseService.startMainInstance(instanceId);
					result.setSrCode(bmSr.getSrCode());
					result.setInstanceId(instanceId);
				}
			}else{
				logger.error("初始化扩容信息失败,请检查参数信息");
				result.setCode(FAIL);
				throw new RollbackableBizException("初始化扩容信息失败，请检查参数信息");
			}
		} catch (IOException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (InterruptedException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (RollbackableBizException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		}
		logger.info("扩容调用web接口结束");
		map.put("meta", result);
		return map;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/recycle", produces= MediaType.APPLICATION_JSON)
	@ResponseBody
	public Map<String,ReturnMeta> recycle(HttpServletRequest request){
		Map<String,ReturnMeta> map = new HashMap<String,ReturnMeta>();
		logger.info("回收调用web接口开始");
		ReturnMeta result = new ReturnMeta();
		result.setCode(SUCCESS);
		BmSrVo bmSr = null;
		String instanceId = ""; 
		JSONObject serviceRequestParam = null;
		try {
			serviceRequestParam = this.thansToJsonData(request);
			ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
			bmSr = resourseService.startService(SrTypeMarkEnum.VIRTUAL_RECYCLE.getValue(), serviceRequestParam);
			if(bmSr != null ){
				instanceId = resourseService.createMainInstance(bmSr);
				if(instanceId != null && !instanceId.equals("")){
					resourseService.startMainInstance(instanceId);
					result.setSrCode(bmSr.getSrCode());
					result.setInstanceId(instanceId);
				}else{
					logger.error("启动审批流程失败");
					result.setCode(FAIL);
					throw new RollbackableBizException("启动审批流程失败");
				}
			}else{
				logger.error("初始化回收信息失败,请检查参数信息");
				result.setCode(FAIL);
				throw new RollbackableBizException("初始化回收信息失败，请检查参数信息");
			}
			logger.info("回收流程结束");
		}catch (IOException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (InterruptedException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (RollbackableBizException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		}
		logger.info("回收调用web接口结束");
		map.put("meta", result);
		return map;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/addNetworkCard", produces= MediaType.APPLICATION_JSON )
	@ResponseBody
	public Map<String,ReturnMeta> addNetworkCard(HttpServletRequest request){
		Map<String,ReturnMeta> map = new HashMap<String,ReturnMeta>();
		logger.info("添加网卡，调用addNetworkCard开始...");
		ReturnMeta result = new ReturnMeta();
		ServiceRequestParam serviceRequestParam = null;
		try {
			serviceRequestParam = this.thansToJsonData(request).toJavaObject(ServiceRequestParam.class);
			ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
			result = resourseService.startServiceAuto("ADD_NETWORKCARD",serviceRequestParam);
		} catch (RollbackableBizException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		}
		logger.info("添加网卡，调用addNetworkCard结束...");
		map.put("meta", result);
		return map;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/deleteNetworkCard", produces= MediaType.APPLICATION_JSON )
	@ResponseBody
	public Map<String,ReturnMeta> deleteNetworkCard(HttpServletRequest request){
		Map<String,ReturnMeta> map = new HashMap<String,ReturnMeta>();
		logger.info("删除网卡，调用addNetworkCard开始...");
		ReturnMeta result = new ReturnMeta();
		ServiceRequestParam serviceRequestParam = null;
		try {
			serviceRequestParam = this.thansToJsonData(request).toJavaObject(ServiceRequestParam.class);
			ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
			result = resourseService.startServiceAuto("DELETE_NETWORKCARD",serviceRequestParam);
		} catch (RollbackableBizException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		}
		logger.info("删除网卡，调用deleteNetworkCard结束...");
		map.put("meta", result);
		return map;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/addVolume", produces= MediaType.APPLICATION_JSON )
	@ResponseBody
	public Map<String,ReturnMeta> addVolume(HttpServletRequest request){
		Map<String,ReturnMeta> map = new HashMap<String,ReturnMeta>();
		logger.info("添加卷，调用addVolume开始...");
		ReturnMeta result = new ReturnMeta();
		result.setCode(SUCCESS);
		BmSrVo bmSr = null;
		String instanceId = ""; 
		JSONObject serviceRequestParam = null;
		try {
			serviceRequestParam = this.thansToJsonData(request);
			ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
			bmSr = resourseService.startService(SrTypeMarkEnum.SERVICE_AUTO.getValue(),serviceRequestParam);
			if(bmSr != null ){
				instanceId = resourseService.createMainInstance(bmSr);
				if(instanceId != null && !instanceId.equals("")){
					resourseService.startMainInstance(instanceId);
					result.setSrCode(bmSr.getSrCode());
					result.setInstanceId(instanceId);
				}else{
					logger.error("启动审批流程失败");
					result.setCode(FAIL);
					throw new RollbackableBizException("启动审批流程失败");
				}
			}else{
				logger.error("请检查参数信息");
				result.setCode(FAIL);
				throw new RollbackableBizException("请检查参数信息");
			}
		} catch (RollbackableBizException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		}
		logger.info("添加卷，调用addVolume结束...");
		map.put("meta", result);
		return map;
	}
	
	
	
	private JSONObject thansToJsonData(HttpServletRequest request) throws Exception {
		StringBuilder buffer = new StringBuilder();
		request.setCharacterEncoding("UTF-8");
		BufferedReader reader = null;
		JSONObject serviceRequestParam = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = null;
			boolean flag = false;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				flag = true;
			}
			if (flag) {
				logger.info("传递的参数信息："+JSONObject.parse(buffer.toString()));
				serviceRequestParam = JSONObject.parseObject(buffer.toString());
			}
		} catch (Exception e) {
			throw new Exception("参数转换有误",e);
		}
		return serviceRequestParam;
		
	}
	
	//定价审批接口
	@RequestMapping(method = RequestMethod.POST, value = "/pricingApproval", produces= MediaType.APPLICATION_JSON )
	@ResponseBody
	public Map<String,ReturnMeta> pricingApproval(HttpServletRequest request){
		Map<String,ReturnMeta> map = new HashMap<String,ReturnMeta>();
		logger.debug("定价审批调用接口开始...");
		ReturnMeta result = new ReturnMeta();
		result.setCode(SUCCESS);
		BmSrVo bmSr = null;
		String instanceId = ""; 
		JSONObject serviceRequestParam = null;
		try {
			serviceRequestParam = this.thansToJsonData(request);
			ServiceRequestImpl resourseService = (ServiceRequestImpl)WebApplicationManager.getBean("serviceRequestImpl");
			bmSr = resourseService.startService(SrTypeMarkEnum.PRICE_EXAMINE_APPROVE.getValue(),serviceRequestParam);
			if(bmSr != null ){
				instanceId = resourseService.createMainInstance(bmSr);
				if(instanceId != null && !instanceId.equals("")){
					resourseService.startMainInstance(instanceId);
					result.setSrCode(bmSr.getSrCode());
					result.setInstanceId(instanceId);
				}else{
					logger.error("启动审批流程失败");
					result.setCode(FAIL);
					throw new RollbackableBizException("启动审批流程失败");
				}
			}else{
				logger.error("请检查参数信息");
				result.setCode(FAIL);
				throw new RollbackableBizException("请检查参数信息");
			}
		} catch (RollbackableBizException e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(FAIL);
			result.setMessage(e.getMessage());
		}
		logger.debug("定价审批调用接口结束...");
		map.put("meta", result);
		return map;
	}
	
	public ParameterService paraServiceImpl() throws Exception {
		return (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
	}
}
