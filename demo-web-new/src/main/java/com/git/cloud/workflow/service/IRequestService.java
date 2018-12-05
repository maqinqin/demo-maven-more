/**
 * @Title:IRequestService.java
 * @Package:com.git.cloud.workflow.service
 * @Description:TODO
 * @author libin
 * @date 2014-10-11 下午7:00:52
 * @version V1.0
 */
package com.git.cloud.workflow.service;

import java.util.HashMap;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;


/**
 * @ClassName:IRequestService
 * @Description:TODO
 * @author libin
 * @date 2014-10-11 下午7:00:52
 *
 *
 */
public interface IRequestService extends IService{
	/** 
	 * 无判定的提交人工任务
	 * @param taskId
	 * @param nodeId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String commitTask(String taskId,String nodeId) throws RollbackableBizException;
	/**
	 * 同意有判定的人工任务
	 * @param taskId
	 * @param nodeId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String agreeTask(String taskId,String nodeId) throws RollbackableBizException;
	/**
	 * 不同意有判定的人工任务
	 * @param taskId
	 * @param nodeId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String disagreeTask(String taskId,String nodeId) throws RollbackableBizException;
	 
	/**
	 * 同意有判定的外部接口
	 * @param reqId
	 * @param resId
	 * @param typeCode
	 * @return
	 */
	public String agreeAutoNode(String reqId, String resId, String typeCode);
	/**
	 * 不同意有判定的外部接口
	 * @param reqId
	 * @param resId
	 * @param typeCode
	 * @return
	 */
	public String disagreeAutoNode(String reqId, String resId, String typeCode);
	
	/**
	 * 服务请求创建流程实例
	 * @param processDefinitionId
	 * @param creatorId
	 * @param instanceMapParams 服务请求ID/DCM-ID/判定条件ID transRoute
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public String newProcessInstanceFromReq(String processDefinitionId,String creatorId,HashMap instanceMapParams) throws Exception;
	
	/**
	 * 服务请求启动流程实例
	 * @param processInstanceId
	 * @param instanceId
	 * @return
	 * @throws Exception 
	 */
	public String startProcessInstanceFromReq(String instanceId) throws Exception;

	/**
	 * 通过serviceId获取procesInstanceId
	 * @param serviceId
	 * @return
	 * @throws Exception 
	 */
	public String getProcessInstanceId(String srId) throws Exception;

	/**
	 * 根据TokenId驱动流程向下流转
	 * @param tokenId
	 * @return
	 * @throws RollbackableBizException 
	 */
	public String signalAutoTask(long tokenId) throws RollbackableBizException;

	
	/**
	 * 根据流程模板id创建并启动流程实例
	 * @param processDefinitionId 模板id
	 * @param creatorId 创建者id
	 * @param instanceMapParams 参数
	 * @return 执行结果
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public String newAndStartProcessInstanceromReq(String modelId,String creatorId,HashMap instanceParams) throws Exception;
	/**
	 * 代办提交流程驱动接口
	 * @param todoId 代办id
	 * @throws RollbackableBizException 
	 * @msg 预置信息
	 */
	public String transCommit(String todoId,String msg) throws RollbackableBizException;
	/**
	 * 代办同意流程驱动接口
	 * @param todoId 代办id
	 * @msg 预置信息
	 */
	public String transAgree(String todoId,String msg) throws RollbackableBizException;
	/**
	 * 代办驳回流程驱动接口
	 * @param todoId 代办id
	 * @msg 预置信息
	 */
	public String transDisagree(String todoId,String msg) throws RollbackableBizException;
}
