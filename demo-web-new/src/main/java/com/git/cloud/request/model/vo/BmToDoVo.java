package com.git.cloud.request.model.vo;

import java.sql.Timestamp;

import com.git.cloud.common.model.base.BaseBO;

public class BmToDoVo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String todoId;
	private String srId;
	private String srCode;
	private String appId;
	private String appName;
	private String datacenterId;
	private String datacenterName;
	private String srTypeMark;
	private String srTypeName;
	private String srStatusCode;
	private String srStatus;
	private String creator;
	private Timestamp requestTime;
	private String currentStep;
	private String currentGroupId;
	private String currentGroupName;
	private String currentUserId;
	private String currentUserName;
	private String todoStatus;
	private String todoStatusName;
	private String pageUrl;
	private String taskId;
	private String nodeId;
	private String instanceId;
	private Timestamp createTime;
	private Timestamp operateTime;
	private Timestamp dealTime;

	public BmToDoVo() {
	}

	public BmToDoVo(String todoId) {
		this.todoId = todoId;
	}

	/**
	 * <p>Title:</p>
	 * <p>Description:</p>
	 * @param todoId
	 * @param srId
	 * @param srCode
	 * @param appId
	 * @param appName
	 * @param datacenterId
	 * @param datacenterName
	 * @param srTypeMark
	 * @param srTypeName
	 * @param srStatusCode
	 * @param srStatus
	 * @param currentStep
	 * @param currentGroupId
	 * @param currentGroupName
	 * @param currentUserId
	 * @param currentUserName
	 * @param todoStatus
	 * @param todoStatusName
	 * @param pageUrl
	 * @param taskId
	 * @param nodeId
	 * @param instanceId
	 * @param createTime
	 * @param operateTime
	 * @param dealTime
	 */
	public BmToDoVo(String todoId, String srId, String srCode, String appId,
			String appName, String datacenterId, String datacenterName,
			String srTypeMark, String srTypeName, String srStatusCode,
			String srStatus, String creator, Timestamp requestTime, String currentStep, 
			String currentGroupId, String currentGroupName, String currentUserId,
			String currentUserName, String todoStatus, String todoStatusName,
			String pageUrl, String taskId, String nodeId, String instanceId,
			Timestamp createTime, Timestamp operateTime, Timestamp dealTime) {
		super();
		this.todoId = todoId;
		this.srId = srId;
		this.srCode = srCode;
		this.appId = appId;
		this.appName = appName;
		this.datacenterId = datacenterId;
		this.datacenterName = datacenterName;
		this.srTypeMark = srTypeMark;
		this.srTypeName = srTypeName;
		this.srStatusCode = srStatusCode;
		this.srStatus = srStatus;
		this.creator = creator;
		this.requestTime = requestTime;
		this.currentStep = currentStep;
		this.currentGroupId = currentGroupId;
		this.currentGroupName = currentGroupName;
		this.currentUserId = currentUserId;
		this.currentUserName = currentUserName;
		this.todoStatus = todoStatus;
		this.todoStatusName = todoStatusName;
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

	public String getSrCode() {
		return srCode;
	}

	public void setSrCode(String srCode) {
		this.srCode = srCode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getDatacenterName() {
		return datacenterName;
	}

	public void setDatacenterName(String datacenterName) {
		this.datacenterName = datacenterName;
	}

	public String getSrTypeMark() {
		return srTypeMark;
	}

	public void setSrTypeMark(String srTypeMark) {
		this.srTypeMark = srTypeMark;
	}

	public String getSrTypeName() {
		return srTypeName;
	}

	public void setSrTypeName(String srTypeName) {
		this.srTypeName = srTypeName;
	}

	public String getSrStatusCode() {
		return srStatusCode;
	}

	public void setSrStatusCode(String srStatusCode) {
		this.srStatusCode = srStatusCode;
	}

	public String getSrStatus() {
		return srStatus;
	}

	public void setSrStatus(String srStatus) {
		this.srStatus = srStatus;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
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

	public String getCurrentGroupName() {
		return currentGroupName;
	}

	public void setCurrentGroupName(String currentGroupName) {
		this.currentGroupName = currentGroupName;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(String todoStatus) {
		this.todoStatus = todoStatus;
	}

	public String getTodoStatusName() {
		return todoStatusName;
	}

	public void setTodoStatusName(String todoStatusName) {
		this.todoStatusName = todoStatusName;
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