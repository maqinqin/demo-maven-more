package com.git.cloud.request.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.git.cloud.common.model.base.BaseBO;

public class BmApproveVo extends BaseBO implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private String approveId;
	private String todoId;
	private String srId;
	private String approveResult;
	private String approveRemark;
	private String approverId;
	private Timestamp approveTime;
	private String approveMark;
	private String currentStep;

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


	public Timestamp getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveMark() {
		return approveMark;
	}

	public void setApproveMark(String approveMark) {
		this.approveMark = approveMark;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
