package com.git.cloud.workflow.webservice;

import javax.jws.WebService;

@WebService(name = "IWorkFlowService", targetNamespace = "http://com.git.workflow.webservice")
public interface IWorkFlowService {
	/**
	 * 流程部署接口
	 * 
	 * @param id
	 *            流程模型id
	 * @param path
	 *            流程文件位置
	 * @return
	 */
	public String deployProcessDefinition(String path);

	/**
	 * 显示流程图接口
	 * 
	 * @param processDefinitionID
	 *            流程模板id
	 * @return
	 */
	public byte[] getProcessPicture(long processDefinitionID);

	/**
	 * 显示流程图片中流程元素位置
	 * 
	 * @param processDefinitionID
	 *            流程模板id
	 * @return
	 */
	public String getProcessNodePosition(long processDefinitionID);

	/**
	 * 创建流程实例
	 * 
	 * @param processDefinitionId
	 *            流程实例ID
	 * @param uid
	 *            当前用户ID
	 * @return
	 */
	public long buildInstance(long processDefinitionId, String uid);

	/**
	 * 启动流程实例
	 * 
	 * @param processInstanceID
	 *            流程实例ID
	 * @param businessInstanceID
	 *            业务流程实例ID
	 * @return
	 */
	public boolean startInstance(long processInstanceID, String businessInstanceID);

	/**
	 * 暂停流程实例
	 * 
	 * @param processInstanceID
	 *            流程实例ID
	 * @return
	 */
	public boolean pauseInstance(long processInstanceID);

	/**
	 * 激活流程实例
	 * 
	 * @param processInstanceID
	 * @return
	 */
	public boolean resumeInstance(long processInstanceID);

	/**
	 * 强制结束流程实例
	 * 
	 * @param processInstanceID
	 *            流程实例ID
	 * @return
	 */
	public boolean destroyInstance(long processInstanceID);
	/**
	 * 显示流程图片中流程元素位置
	 * 
	 * @param id,str
	 * @return
	 */
	public String getProcessNodePositionByParam(long id,String str);

	public String signalInstance(long tokenId);
}
