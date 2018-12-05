package com.git.cloud.workflow.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class BpmInstanceInfnodePo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long infnodeId;
	private String infnodeName;
	private Long infnodeType;
	private String reqType;
	private String reqKey;
	private String reqValue;
	private String serviceId;
	private Long instanceId;
	private Long wfNodeId;
	private Long taskId;
	private Date startDate;
	private Date endDate;
	private String stateId;
	private String attr;


	public Long getInfnodeId() {
		return this.infnodeId;
	}

	public void setInfnodeId(Long infnodeId) {
		this.infnodeId = infnodeId;
	}

	public String getInfnodeName() {
		return this.infnodeName;
	}

	public void setInfnodeName(String infnodeName) {
		this.infnodeName = infnodeName;
	}

	public Long getInfnodeType() {
		return this.infnodeType;
	}

	public void setInfnodeType(Long infnodeType) {
		this.infnodeType = infnodeType;
	}

	public String getReqType() {
		return this.reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqKey() {
		return this.reqKey;
	}

	public void setReqKey(String reqKey) {
		this.reqKey = reqKey;
	}

	public String getReqValue() {
		return this.reqValue;
	}

	public void setReqValue(String reqValue) {
		this.reqValue = reqValue;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Long getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public Long getWfNodeId() {
		return this.wfNodeId;
	}

	public void setWfNodeId(Long wfNodeId) {
		this.wfNodeId = wfNodeId;
	}

	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStateId() {
		return this.stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getAttr() {
		return this.attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	/* (non-Javadoc)
	 * <p>Title:getBizId</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}