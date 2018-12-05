package com.git.cloud.request.model.po;

import java.sql.Timestamp;

import com.git.cloud.common.model.base.BaseBO;

public class BmToDoPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String todoId;
	private String srId;
	private String currentStep;
	private String currentGroupId;
	private String currentUserId;
	private String todoStatus;
	private String pageUrl;
	private String taskId;
	private String nodeId;
	private String instanceId;
	private Timestamp createTime;
	private Timestamp operateTime;
	private Timestamp dealTime;

	public BmToDoPo() {
	}

	public BmToDoPo(String todoId) {
		this.todoId = todoId;
	}

	public BmToDoPo(String todoId, String srId, String currentStep,
			String currentGroupId, String currentUserId, String todoStatus, String pageUrl,
			String taskId, String nodeId, String instanceId,
			Timestamp createTime, Timestamp operateTime, Timestamp dealTime) {
		this.todoId = todoId;
		this.srId = srId;
		this.currentStep = currentStep;
		this.currentGroupId = currentGroupId;
		this.currentUserId = currentUserId;
		this.todoStatus = todoStatus;
		this.pageUrl = pageUrl;
		this.taskId = taskId;
		this.nodeId = nodeId;
		this.instanceId = instanceId;
		this.createTime = createTime;
		this.operateTime = operateTime;
		this.dealTime = dealTime;
	}

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public String getSrId() {
		return srId;
	}

	public void setSrId(String srId) {
		this.srId = srId;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public String getCurrentGroupId() {
		return currentGroupId;
	}

	public void setCurrentGroupId(String currentGroupId) {
		this.currentGroupId = currentGroupId;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(String todoStatus) {
		this.todoStatus = todoStatus;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	public Timestamp getDealTime() {
		return dealTime;
	}

	public void setDealTime(Timestamp dealTime) {
		this.dealTime = dealTime;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}