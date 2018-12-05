package com.git.cloud.log.model.po;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class NotificationPo extends BaseBO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//通知id
	private String id;
	//对象id
	private String resourceId;
	private String resourceName;
	//对象类型
	private String resourceType;
	//来源
	private String source;
	//类型
	private String type;
	//操作类型
	private String  operationType;
	//内容
	private String operationContent;
	//状态
	private String state;
	//创建人
	private String creator;
	//创建时间
	private Date createTime;
	//处理时间
	private Date dealTime;
	//处理人
	private String operator;
	//警报KEY
	private String alarmKey;
	//报警次数
	private int alarmTotal;
	//租户id
	private String tenantId;
	//用户id
	private String userId;
	
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationContent() {
		return operationContent;
	}
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getAlarmKey() {
		return alarmKey;
	}
	public void setAlarmKey(String alarmKey) {
		this.alarmKey = alarmKey;
	}
	public int getAlarmTotal() {
		return alarmTotal;
	}
	public void setAlarmTotal(int alarmTotal) {
		this.alarmTotal = alarmTotal;
	}
	
	
}
