package com.git.cloud.request.model.po;

import java.io.Serializable;
import java.sql.Timestamp;

import com.git.cloud.common.model.base.BaseBO;

public class BmSrPo extends BaseBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String srId;
	private String srCode;
	private String appId;
	private String datacenterId;
	private String srTypeMark;
	private String srStatusCode;
	private Timestamp srStartTime;
	private Timestamp srEndTime;
	private String urgencyCode;
	private String summary;
	private String remark;
	private String creatorId;
	private Timestamp createTime;
	private Timestamp closeTime;
	private String approveMark;
	private String confParameter;
	private String assignResult;
	private String tenantId;
	
	public BmSrPo() {
	}
	
	public BmSrPo(String srId) {
		this.srId = srId;
	}
	
	public BmSrPo(String srId, String srCode, String appId,
			String datacenterId, String srTypeMark, String srStatusCode,
			Timestamp srStartTime, Timestamp srEndTime, String urgencyCode,
			String summary, String remark, String creatorId,
			Timestamp createTime, Timestamp closeTime, String approveMark, String assignResult,String confParameter) {
		this.srId = srId;
		this.srCode = srCode;
		this.appId = appId;
		this.datacenterId = datacenterId;
		this.srTypeMark = srTypeMark;
		this.srStatusCode = srStatusCode;
		this.srStartTime = srStartTime;
		this.srEndTime = srEndTime;
		this.urgencyCode = urgencyCode;
		this.summary = summary;
		this.remark = remark;
		this.creatorId = creatorId;
		this.createTime = createTime;
		this.closeTime = closeTime;
		this.approveMark = approveMark;
		this.assignResult = assignResult;
		this.confParameter = confParameter;
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

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getSrTypeMark() {
		return srTypeMark;
	}

	public void setSrTypeMark(String srTypeMark) {
		this.srTypeMark = srTypeMark;
	}

	public String getSrStatusCode() {
		return srStatusCode;
	}

	public void setSrStatusCode(String srStatusCode) {
		this.srStatusCode = srStatusCode;
	}

	public Timestamp getSrStartTime() {
		return srStartTime;
	}

	public void setSrStartTime(Timestamp srStartTime) {
		this.srStartTime = srStartTime;
	}

	public Timestamp getSrEndTime() {
		return srEndTime;
	}

	public void setSrEndTime(Timestamp srEndTime) {
		this.srEndTime = srEndTime;
	}

	public String getUrgencyCode() {
		return urgencyCode;
	}

	public void setUrgencyCode(String urgencyCode) {
		this.urgencyCode = urgencyCode;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}

	public String getApproveMark() {
		return approveMark;
	}

	public void setApproveMark(String approveMark) {
		this.approveMark = approveMark;
	}

	public String getAssignResult() {
		return assignResult;
	}

	public void setAssignResult(String assignResult) {
		this.assignResult = assignResult;
	}

	public String getConfParameter() {
		return confParameter;
	}
	public void setConfParameter(String confParameter) {
		this.confParameter = confParameter;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String getBizId() {
		return null;
	}
}