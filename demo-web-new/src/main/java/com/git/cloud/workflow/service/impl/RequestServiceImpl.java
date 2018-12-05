/**
 * @Title:RequestServiceImpl.java
 * @Package:com.git.cloud.workflow.service.impl
 * @Description:TODO
 * @author libin
 * @date 2014-10-11 下午7:07:46
 * @version V1.0
 */
package com.git.cloud.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.request.dao.IBmToDoDao;
import com.git.cloud.request.model.TodoStatusCodeEnum;
import com.git.cloud.request.model.po.BmToDoPo;
import com.git.cloud.workflow.service.IRequestService;
import com.gitcloud.tankflow.dao.IBpmInstanceDao;
import com.gitcloud.tankflow.dao.IBpmModelDao;
import com.gitcloud.tankflow.model.po.BpmInstancePo;
import com.gitcloud.tankflow.service.IBpmInstanceService;
import com.gitcloud.tankflow.service.IBpmModelService;
import com.gitcloud.tankflow.service.IProcessInstanceService;
import com.gitcloud.tankflow.util.Constants;
import com.gitcloud.tankflow.webservice.IBpmWebService;

/**
 * @ClassName:RequestServiceImpl
 * @Description:TODO
 * @author libin
 * @date 2014-10-11 下午7:07:46
 *
 *
 */
public class RequestServiceImpl implements IRequestService {
	private IBpmModelService bpmModelService;
	private IBpmInstanceService bpmInstanceService;
	private IProcessInstanceService processInstanceService;
	private IBpmModelDao	bpmModelDao;
	private IBpmInstanceDao bpmInstanceDao;
	private IBmToDoDao bmToDoDao;
	private ParameterService  parameterServiceImpl;
	
	public IBpmModelService getBpmModelService() {
		return bpmModelService;
	}
	public void setBpmModelService(IBpmModelService bpmModelService) {
		this.bpmModelService = bpmModelService;
	}


	public IBpmInstanceService getBpmInstanceService() {
		return bpmInstanceService;
	}
	public void setBpmInstanceService(IBpmInstanceService bpmInstanceService) {
		this.bpmInstanceService = bpmInstanceService;
	}

	public IProcessInstanceService getProcessInstanceService() {
		return processInstanceService;
	}
	public void setProcessInstanceService(
			IProcessInstanceService processInstanceService) {
		this.processInstanceService = processInstanceService;
	}

	public IBpmModelDao getBpmModelDao() {
		return bpmModelDao;
	}
	public void setBpmModelDao(IBpmModelDao bpmModelDao) {
		this.bpmModelDao = bpmModelDao;
	}


	public IBpmInstanceDao getBpmInstanceDao() {
		return bpmInstanceDao;
	}
	public void setBpmInstanceDao(IBpmInstanceDao bpmInstanceDao) {
		this.bpmInstanceDao = bpmInstanceDao;
	}

	public IBmToDoDao getBmToDoDao() {
		return bmToDoDao;
	}
	public void setBmToDoDao(IBmToDoDao bmToDoDao) {
		this.bmToDoDao = bmToDoDao;
	}


	public ParameterService getParameterServiceImpl() {
		return parameterServiceImpl;
	}
	public void setParameterServiceImpl(ParameterService parameterServiceImpl) {
		this.parameterServiceImpl = parameterServiceImpl;
	}

	/** 
	 * 无判定的提交人工任务
	 * @param taskId
	 * @param nodeId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String commitTask(String taskId,String nodeId) throws RollbackableBizException{
		/** 构造web-service方法.创建流程实例.设置MAX响应时间为10s,MAX等待结果时常为60s */
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
		factory.setServiceClass(IBpmWebService.class);     
		String path = parameterServiceImpl.getParamValueByName(Constants.bpmWebServiceAddress);
		factory.setAddress(path); 
	    IBpmWebService bpmService = (IBpmWebService)factory.create();  
        Client proxy = ClientProxy.getClient(bpmService);   
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();    
        HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();      
        httpClientPolicy.setConnectionTimeout(100000L);      
        httpClientPolicy.setAllowChunking( false );      
        httpClientPolicy.setReceiveTimeout(600000L);      
        conduit.setClient(httpClientPolicy); 
		return bpmService.commitTaskById(Long.parseLong(taskId));
	}
	/**
	 * 同意有判定的人工任务
	 * @param taskId
	 * @param nodeId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String agreeTask(String taskId,String nodeId) throws RollbackableBizException{
		/** 构造web-service方法.创建流程实例.设置MAX响应时间为10s,MAX等待结果时常为60s */
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
		factory.setServiceClass(IBpmWebService.class);     
		String path = parameterServiceImpl.getParamValueByName(Constants.bpmWebServiceAddress);
		factory.setAddress(path);   
	    IBpmWebService bpmService = (IBpmWebService)factory.create();  
        Client proxy = ClientProxy.getClient(bpmService);   
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();    
        HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();      
        httpClientPolicy.setConnectionTimeout(100000L);      
        httpClientPolicy.setAllowChunking( false );      
        httpClientPolicy.setReceiveTimeout(600000L);      
        conduit.setClient(httpClientPolicy); 
		return bpmService.agreeTask(Long.parseLong(taskId),Long.parseLong(nodeId));
	}
	/**
	 * 不同意有判定的人工任务
	 * @param taskId
	 * @param nodeId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String disagreeTask(String taskId,String nodeId) throws RollbackableBizException{
		/** 构造web-service方法.创建流程实例.设置MAX响应时间为10s,MAX等待结果时常为60s */
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
		factory.setServiceClass(IBpmWebService.class);     
		String path = parameterServiceImpl.getParamValueByName(Constants.bpmWebServiceAddress);
		factory.setAddress(path);  
	    IBpmWebService bpmService = (IBpmWebService)factory.create();  
        Client proxy = ClientProxy.getClient(bpmService);   
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();    
        HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();      
        httpClientPolicy.setConnectionTimeout(100000L);      
        httpClientPolicy.setAllowChunking( false );      
        httpClientPolicy.setReceiveTimeout(600000L);      
        conduit.setClient(httpClientPolicy); 
		return bpmService.disagreeTask(Long.parseLong(taskId),Long.parseLong(nodeId));
	}
	 
	/**
	 * 同意有判定的外部接口
	 * @param reqId
	 * @param resId
	 * @param typeCode
	 * @return
	 */
	public String agreeAutoNode(String reqId, String resId, String typeCode){
//		Map<String,Object> paramsInfo = Maps.newHashMap();
//		paramsInfo.put("serviceId", String.valueOf(reqId));
//		paramsInfo.put("reqValue", resId);
//		paramsInfo.put("reqType", typeCode);
//		paramsInfo.put("stateId", "0");
//		BpmInstanceInfnodePo infoNode = bpmInstanceInfnodeDAO.findObjectByFields(paramsInfo);
//		/** 构造web-service方法.创建流程实例.设置MAX响应时间为10s,MAX等待结果时常为60s */
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
//		factory.setServiceClass(IBpmWebService.class);     
//		factory.setAddress(parameterService.getParamValueByName(Constants.bpmWebServiceAddress));   
//	    IBpmWebService bpmService = (IBpmWebService)factory.create();  
//        Client proxy = ClientProxy.getClient(bpmService);   
//        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();    
//        HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();      
//        httpClientPolicy.setConnectionTimeout(100000L);      
//        httpClientPolicy.setAllowChunking( false );      
//        httpClientPolicy.setReceiveTimeout(600000L);      
//        conduit.setClient(httpClientPolicy); 
//        String msg = bpmService.agreeAutoNode(infoNode.getTaskId(),infoNode.getWfNodeId());
//        infoNode.setEndDate(new Timestamp(System.currentTimeMillis()));
//        infoNode.setStateId("1");
//        bpmInstanceInfnodeDAO.merge(infoNode);
		return "" ;
	}
	/**
	 * 不同意有判定的外部接口
	 * @param reqId
	 * @param resId
	 * @param typeCode
	 * @return
	 */
	public String disagreeAutoNode(String reqId, String resId, String typeCode){
//		Map<String,Object> paramsInfo = Maps.newHashMap();
//		paramsInfo.put("serviceId", String.valueOf(reqId));
//		paramsInfo.put("reqValue", resId);
//		paramsInfo.put("reqType", typeCode);
//		paramsInfo.put("stateId", "0");
//		BpmInstanceInfnodePo infoNode = bpmInstanceInfnodeDAO.findObjectByFields(paramsInfo);
//		/** 构造web-service方法.创建流程实例.设置MAX响应时间为10s,MAX等待结果时常为60s */
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
//		factory.setServiceClass(IBpmWebService.class);     
//		factory.setAddress(parameterService.getParamValueByName(Constants.bpmWebServiceAddress));   
//	    IBpmWebService bpmService = (IBpmWebService)factory.create();  
//        Client proxy = ClientProxy.getClient(bpmService);   
//        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();    
//        HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();      
//        httpClientPolicy.setConnectionTimeout(100000L);      
//        httpClientPolicy.setAllowChunking( false );      
//        httpClientPolicy.setReceiveTimeout(600000L);      
//        conduit.setClient(httpClientPolicy); 
//        String msg = bpmService.disagreeAutoNode(infoNode.getTaskId(),infoNode.getWfNodeId());
//        infoNode.setEndDate(new Timestamp(System.currentTimeMillis()));
//        infoNode.setStateId("2");
//        bpmInstanceInfnodeDAO.merge(infoNode);
		return "" ;
	}
	
	/**
	 * 服务请求创建流程实例
	 * @param processDefinitionId
	 * @param creatorId
	 * @param instanceMapParams 服务请求ID/DCM-ID/判定条件ID transRoute
	 * @return
	 * @throws RollbackableBizException 
	 * @throws com.gitcloud.tankflow.common.exception.RollbackableBizException 
	 */
	@SuppressWarnings("rawtypes")
	public String newProcessInstanceFromReq(String processDefinitionId,String creatorId,HashMap instanceMapParams) throws Exception {
		return processInstanceService.newProcessInstanceFromReq(processDefinitionId, creatorId, instanceMapParams);
	}
	
	/**
	 * 服务请求启动流程实例
	 * @param processInstanceId
	 * @param instanceId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String startProcessInstanceFromReq(String instanceId) throws Exception{
		return processInstanceService.startProcessInstanceFromReq(instanceId);
	}
	
	/**
	 * 获取instanceId
	 * @param serviceId 服务请求Id
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String getProcessInstanceId(String srId) throws Exception{
		List<BpmInstancePo> resultList = new ArrayList<BpmInstancePo>();
		Map<String ,String> map = new HashMap<String, String>();
		map.put("serviceReqId", srId);
		map.put("orderFile", "instanceId");
		map.put("orderType", "asc");
		resultList = bpmInstanceDao.findInstanceListByParam(map);
		if(resultList.get(0).getInstanceId() == null){
			return "instanceId不存在";
		}else{
			return String.valueOf(resultList.get(0).getInstanceId());
		}
	}
	
	public String signalAutoTask(long tokenId) throws RollbackableBizException{
		/** 构造web-service方法.创建流程实例.设置MAX响应时间为10s,MAX等待结果时常为60s */
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();     
		factory.setServiceClass(IBpmWebService.class);     
		String path = parameterServiceImpl.getParamValueByName(Constants.bpmWebServiceAddress);
		factory.setAddress(path);  
	    IBpmWebService bpmService = (IBpmWebService)factory.create();  
        Client proxy = ClientProxy.getClient(bpmService);   
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();    
        HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();      
        httpClientPolicy.setConnectionTimeout(100000L);      
        httpClientPolicy.setAllowChunking( false );      
        httpClientPolicy.setReceiveTimeout(600000L);      
        conduit.setClient(httpClientPolicy); 
        return bpmService.signalAutoTask(tokenId);
	}
	/**
	 * 代办提交流程驱动接口
	 * @param todoId 代办id
	 * @throws RollbackableBizException 
	 * @msg 预置信息
	 */
	public String transCommit(String todoId,String msg) throws RollbackableBizException{
		BmToDoPo todo = bmToDoDao.findBmToDoById(todoId);
		if(todo != null){
			/**流程驱动**/
			String result = commitTask(todo.getTaskId(),todo.getNodeId());
			if(result.equals("success")){
				bmToDoDao.updateBmToDoStatus(todoId, TodoStatusCodeEnum.TODO_STATUS_DEAL.getValue());
			}
			return result;
		}
		return "代办不存在，提交失败！";
	}
	
	/**
	 * 代办同意流程驱动接口
	 * @param todoId 代办id
	 * @throws RollbackableBizException 
	 * @msg 预置信息
	 */
	public String transAgree(String todoId,String msg) throws RollbackableBizException{
		BmToDoPo todo = bmToDoDao.findBmToDoById(todoId);
		if(todo != null){
			String result = agreeTask(todo.getTaskId(),todo.getNodeId());
			if(result.equals("success")){
				bmToDoDao.updateBmToDoStatus(todoId, TodoStatusCodeEnum.TODO_STATUS_DEAL.getValue());
			}
			/**流程驱动**/
			return result;
		}
		return "代办不存在，提交失败！";
	}
	
	/**
	 * 代办驳回流程驱动接口
	 * @param todoId 代办id
	 * @throws RollbackableBizException 
	 * @msg 预置信息
	 */
	public String transDisagree(String todoId,String msg) throws RollbackableBizException{
		BmToDoPo todo = bmToDoDao.findBmToDoById(todoId);
		if(todo != null){
			String result = disagreeTask(todo.getTaskId(),todo.getNodeId());
			if(result.equals("success")){
				bmToDoDao.updateBmToDoStatus(todoId, TodoStatusCodeEnum.TODO_STATUS_DEAL.getValue());
			}
			/**流程驱动**/
			return result;
		}
		return "代办不存在，提交失败！";
	}
	
	
	/**
	 * 根据流程模板id创建并启动流程实例
	 * @param processDefinitionId 模板id
	 * @param creatorId 创建者id
	 * @param instanceMapParams 参数
	 * @return 执行结果
	 * @throws RollbackableBizException 
	 */
	@SuppressWarnings("rawtypes")
	public String newAndStartProcessInstanceromReq(String modelId,String creatorId,HashMap instanceParams) throws Exception{
		String processInstanceId = processInstanceService.newProcessInstanceFromReq(modelId, creatorId, instanceParams);
		return processInstanceService.startProcessInstanceFromReq(processInstanceId);
	}
}
