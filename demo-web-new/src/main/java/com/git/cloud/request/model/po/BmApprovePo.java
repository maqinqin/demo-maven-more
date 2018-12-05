package com.git.cloud.request.model.po;

import java.sql.Timestamp;

import com.git.cloud.common.model.base.BaseBO;

public class BmApprovePo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String approveId;
	private String todoId;
	private String srId;
	private String approveResult;
	private String approveRemark;
	private String approverId;
	private Timestamp apporveTime;
	private String approveMark;

	public BmApprovePo() {
	}

	public BmApprovePo(String approveId) {
		this.approveId = approveId;
	}

	public BmApprovePo(String approveId, String todoId, String srId, 
			String approveResult, String approveRemark, String approverId,
			Timestamp apporveTime, String approveMark) {
		this.approveId = approveId;
		this.todoId = todoId;
		this.srId = srId;
		this.approveResult = approveResult;
		this.approveRemark = approveRemark;
		this.approverId = approverId;
		this.apporveTime = apporveTime;
		this.approveMark = approveMark;
	}

	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
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

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public Timestamp getApporveTime() {
		return apporveTime;
	}

	public void setApporveTime(Timestamp apporveTime) {
		this.apporveTime = apporveTime;
	}

	public String getApproveMark() {
		return approveMark;
	}

	public void setApproveMark(String approveMark) {
		this.approveMark = approveMark;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}