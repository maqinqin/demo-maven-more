package com.git.cloud.workflow.webservice;

import javax.jws.WebService;
/**
 * 调用工作流对应web service接口类
 * @author yangtao
 * @version 1.0 2014-09-17 
 */
@WebService(name = "IBpmWebService", targetNamespace = "http://com.git.workflow.webservice")
public interface IBpmWebService {
	/**
	 * 流程发布
	 * @param fileXML 流程 xml 定义
	 * @return 流程定义的ID和节点的ID集合
	 */
	public String deployProcessDefinition(byte[] processDefinitionXML);
	/**
	 * 创建流程实例
	 * @param processDefinitionId 流程定义的ID
	 * @param uid 用户ID
	 * @return 流程实例ID
	 */
	public long buildProcessInstance(long processDefinitionId,String uid);
	
	/**
	 * 根据服务请求信息创建流程实例
	 * @param processDefinitionId 流程模板ID
	 * @param uid 用户ID
	 * @param params 参数key-value
	 * @return
	 */
	public long buildProcessInstanceFromReq(long processDefinitionId,String uid,String params);
	/**
	 * 启动流程实例
	 * @param processInstanceID 流程实例ID
	 * @param businessInstanceID 业务流程实例ID
	 * @return
	 */
	public boolean startProcessInstance(long processInstanceID,String instanceID,String params);
	/**
	 * 启动流程实例
	 * @param processDefinitionId 工作流定义的流程实例ID
	 * @param instanceId 业务定义的流程实例ID
	 * @param params 流程实例参数
	 * @return 创建成功与否的标识
	 */
	public String startProcessInstanceInConsole(long processInstanceId,String instanceId,String params);
	/**
	 * 根据TokenId 驱动流程向下流转
	 * @param tokenId 流程实例令牌ID
	 * @return
	 */
	public String signalAutoTask(long tokenId);
	/**
	 * 强制结束流程实例
	 * @param instanceId 流程实例Id
	 * @return
	 */
	public String forceEndProcessInstance(long instanceId);
	/**
	 * 根据工作流节点id获取 token id
	 * @param nodeId 工作流节点Id
	 * @param instanceId 工作流实例Id
	 * @return
	 */
	public Long getTokenIdByNode(long nodeId,long instanceId);
	/**
	 * 添加流程全部变量
	 * @param jsonData
	 * @param processInstanceId 流程实例Id
	 * @return
	 */
	public String setWorkFlowGlobalParams(String jsonData,long processInstanceId);
	/**
	 * 根据工作流节点id获取 token id 并向下流转
	 * @param nodeId 工作流节点Id
	 * @param processInstanceId 工作流实例Id
	 * @return
	 */
	public String getTokenIdByNodeAndSignal(long nodeId,long processInstanceId);
	/**
	 * 提交人工任务
	 * @param taskId 任务ID
	 * @return
	 */
	public String commitTaskById(long taskId);
	/**
	 * 同意人工任务
	 * @param taskId 任务ID
	 * @param nodeId 节点ID
	 * @return
	 */
	public String agreeTask(long taskId,long nodeId);
	/**
	 * 驳回人工任务
	 * @param taskId 任务ID
	 * @param nodeId 节点ID
	 * @return
	 */
	public String disagreeTask(long taskId,long nodeId);
	/**
	 * 同意自动任务
	 * @param taskId 任务ID
	 * @param nodeId 节点ID
	 * @return
	 */
	public String agreeAutoNode(long tokenId,long nodeId);
	/**
	 * 驳回自动任务
	 * @param taskId 任务ID
	 * @param nodeId 节点ID
	 * @return
	 */
	public String disagreeAutoNode(long tokenId,long nodeId);
	/**
	 * 插入单个工作流全局参数
	 * @param processInstanceId 工作流实例id
	 * @param keyName 参数key
	 * @param keyValue 参数值
	 * @return
	 */
	public String setProcessGlobalParams(String keyName,String keyValue,long processInstanceId);
	/**
	 * 任意流转
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @param wfNodeName
	 * @return
	 */
	public String getUnlimitedTurn(Long processDefinitionId,Long processInstanceId,Long wfNodeId,Long sourceNodeId);
	public String getUnlimitedTurnTokenId(Long processDefinitionId,Long processInstanceId,Long wfNodeId,Long sourceNodeId,Long tokenId);
	/**
	 * 聚合任务取消
	 * @param instanceId
	 * @param wfNodeId
	 * @param tokenId
	 * @return
	 */
	public String joinTaskCancle(long instanceId,long wfNodeId,long tokenId);
}
