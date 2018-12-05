package com.git.cloud.request.service;

import java.util.HashMap;

import com.git.cloud.common.exception.RollbackableBizException;

/**
 * 云服务申请的工作流统一接口
 * @ClassName:IRequestWorkflowService
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public interface IRequestWorkflowService {

	/**
	 * @throws Exception 
	 * 创建主流程实例
	 * @Title: createMainWorkflowInstance
	 * @Description: TODO
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public String createMainWorkflowInstance(String srId, String srCode, HashMap<String, String> wfParam) throws Exception;
	
	/**
	 * @throws Exception 
	 * 启动主流程实例
	 * @Title: startMainWorkflowInstance
	 * @Description: TODO
	 * @field: @param instanceId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void startMainWorkflowInstance(String instanceId) throws Exception;
	
	/**
	 * @throws Exception 
	 * 创建子流程实例
	 * @Title: createSubWorkflowInstance
	 * @Description: TODO
	 * @field: @param rrinfoId
	 * @field: @param wfParam
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public String createSubWorkflowInstance(String rrinfoId, HashMap<String, String> wfParam) throws Exception;
	
	/**
	 * @throws Exception 
	 * 创建子流程实例
	 * @Title: createSubWorkflowInstance
	 * @Description: TODO
	 * @field: @param flowId
	 * @field: @param srId
	 * @field: @param rrinfoId
	 * @field: @param wfParam
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public String createSubWorkflowInstance(String srId, String rrinfoId, String flowId, HashMap<String, String> wfParam) throws Exception;
	
	/**
	 * @throws Exception 
	 * 启动子流程实例
	 * @Title: startSubWorkflowInstance
	 * @Description: TODO
	 * @field: @param instanceId
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void startSubWorkflowInstance(String instanceId) throws Exception;
	
	/**
	 * @throws Exception 
	 * 驱动流程
	 * @Title: driveWorkflow
	 * @Description: TODO
	 * @field: @param todoId
	 * @field: @param driveWfType
	 * @field: @throws RollbackableBizException
	 * @return void
	 * @throws
	 */
	public void driveWorkflow(String todoId, String driveWfType) throws Exception;
}
