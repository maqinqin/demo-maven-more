package com.git.cloud.request.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.git.cloud.common.model.base.BaseBO;

public class BmSrVo extends BaseBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String srId; // 服务申请Id
	private String srCode; // 服务申请单号
	private String appId; // 应用系统Id
	private String appName; // 应用系统名称
	private String datacenterId; // 数据中心Id
	private String datacenterName; // 数据中心名称
	private String srTypeMark; // 申请类型编码
	private String srTypeName; // 申请类型名称
	private String srStatusCode; // 状态编码
	private String srStatus; // 状态名
	private String startTimeStr; // 申请期限开始时间字符串
	private String endTimeStr; // 申请期限结束时间字符串
	private Timestamp srStartTime; // 申请期限开始时间
	private Timestamp srEndTime; // 申请期限结束时间
	private String urgencyCode; // 紧急程度编码
	private String urgency; // 紧急程度
	private String summary; // 概要
	private String remark; // 详细描述
	private String creatorId; // 申请人Id
	private String creator; // 申请人名
	private Timestamp createTime; // 建单时间
	private Timestamp closeTime; // 关单时间
	private String createTimeStr; // 简单时间字符串
	private String closeTimeStr; // 关单时间字符串
	private String approveMark;
	private String assignResult; // 分配结果
	//add by chengbin 添加应用系统英文名称，为拼装虚拟机名称使用
	private String appEName; //应用系统英文名
	//存放有服务参数的资源请求Id
	private String confParameter;
	private String attrRrinfoIds;
	private String tenantId;
	
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAppEName() {
		return appEName;
	}

	public void setAppEName(String appEName) {
		this.appEName = appEName;
	}

	public BmSrVo() {
		
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

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public Timestamp getSrStartTime() {
		return srStartTime;
	}

	public void setSrStartTime(Timestamp srStartTime) {
		if(srStartTime != null) {
			this.setStartTimeStr(srStartTime.toString().substring(0, 10));
		}
		this.srStartTime = srStartTime;
	}

	public Timestamp getSrEndTime() {
		return srEndTime;
	}

	public void setSrEndTime(Timestamp srEndTime) {
		if(srEndTime != null) {
			this.setEndTimeStr(srEndTime.toString().substring(0, 10));
		}
		this.srEndTime = srEndTime;
	}

	public String getUrgencyCode() {
		return urgencyCode;
	}

	public void setUrgencyCode(String urgencyCode) {
		this.urgencyCode = urgencyCode;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		if(createTime != null) {
			this.setCreateTimeStr(createTime.toString().substring(0, 19));
		}
		this.createTime = createTime;
	}

	public Timestamp getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Timestamp closeTime) {
		if(closeTime != null) {
			this.setEndTimeStr(closeTime.toString().substring(0, 19));
		}
		this.closeTime = closeTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getCloseTimeStr() {
		return closeTimeStr;
	}

	public void setCloseTimeStr(String closeTimeStr) {
		this.closeTimeStr = closeTimeStr;
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
	public String getAttrRrinfoIds() {
		return attrRrinfoIds;
	}
	public void setAttrRrinfoIds(String attrRrinfoIds) {
		this.attrRrinfoIds = attrRrinfoIds;
	}
	public String getConfParameter() {
		return confParameter;
	}
	public void setConfParameter(String confParameter) {
		this.confParameter = confParameter;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}