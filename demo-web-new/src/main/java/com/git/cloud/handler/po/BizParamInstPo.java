package com.git.cloud.handler.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 流程实例参数表实体
 * @author zhuzhaoyong
 *
 */
public class BizParamInstPo extends BaseBO {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String deviceId;
	private String flowInstId;
	private String nodeId;
	private String paramKey;
	private String paramValue;

	//为了显示，新增by wmy
	private String deviceName;
	private String instanceName;
	private Date startDate;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Override
	public String getBizId() {
		return null;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
