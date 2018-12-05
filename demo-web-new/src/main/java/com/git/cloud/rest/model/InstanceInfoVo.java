package com.git.cloud.rest.model;

import com.git.cloud.common.model.base.BaseBO;

public class InstanceInfoVo  extends BaseBO{

	private static final long serialVersionUID = 1L;
	private String instanceId;		//实例id
	private String instanceName;	//实例名称
	private String nodeId;			//节点id
	private String nodeName;		//节点名称
	private String errorInfo;		//异常信息
	private String userId;		//创建人id
	private String userName;		//创建人名称
	private String tenantId;		//租户id
	private String tenantName;		//租户名称
	

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "InstanceInfoVo [instanceId=" + instanceId + ", instanceName=" + instanceName + ", nodeId=" + nodeId
				+ ", nodeName=" + nodeName + ", errorInfo=" + errorInfo + ", userId=" + userId + ", userName="
				+ userName + ", tenantId=" + tenantId + ", tenantName=" + tenantName + "]";
	}

}
