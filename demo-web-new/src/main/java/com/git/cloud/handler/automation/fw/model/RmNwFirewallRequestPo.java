package com.git.cloud.handler.automation.fw.model;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwFirewallRequestPo extends BaseBO implements Serializable {
    private String id;

    private String requestCode;

    private String visitPolicy;

    private String externalOrg;

    private String protocol;

    private Date dueDate;

    private String reason;

    private String srcIp;

    private String srcPort;

    private String srcDesc;

    private String dstIp;

    private String dstPort;

    private String dstDesc;

    private String status;

    private String recycleUser;

    private Date recycleTime;

    private String isActive;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    
    private String openExecuteType;
    
    private Date openExecuteTime;
    
    private String closeExecuteType;
    
    private Date closeExecuteTime;

    public String getOpenExecuteType() {
		return openExecuteType;
	}

	public void setOpenExecuteType(String openExecuteType) {
		this.openExecuteType = openExecuteType;
	}

	public Date getOpenExecuteTime() {
		return openExecuteTime;
	}

	public void setOpenExecuteTime(Date openExecuteTime) {
		this.openExecuteTime = openExecuteTime;
	}

	public String getCloseExecuteType() {
		return closeExecuteType;
	}

	public void setCloseExecuteType(String closeExecuteType) {
		this.closeExecuteType = closeExecuteType;
	}

	public Date getCloseExecuteTime() {
		return closeExecuteTime;
	}

	public void setCloseExecuteTime(Date closeExecuteTime) {
		this.closeExecuteTime = closeExecuteTime;
	}

	private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode == null ? null : requestCode.trim();
    }

    public String getVisitPolicy() {
        return visitPolicy;
    }

    public void setVisitPolicy(String visitPolicy) {
        this.visitPolicy = visitPolicy == null ? null : visitPolicy.trim();
    }

    public String getExternalOrg() {
        return externalOrg;
    }

    public void setExternalOrg(String externalOrg) {
        this.externalOrg = externalOrg == null ? null : externalOrg.trim();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol == null ? null : protocol.trim();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp == null ? null : srcIp.trim();
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort == null ? null : srcPort.trim();
    }

    public String getSrcDesc() {
        return srcDesc;
    }

    public void setSrcDesc(String srcDesc) {
        this.srcDesc = srcDesc == null ? null : srcDesc.trim();
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp == null ? null : dstIp.trim();
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort == null ? null : dstPort.trim();
    }

    public String getDstDesc() {
        return dstDesc;
    }

    public void setDstDesc(String dstDesc) {
        this.dstDesc = dstDesc == null ? null : dstDesc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRecycleUser() {
        return recycleUser;
    }

    public void setRecycleUser(String recycleUser) {
        this.recycleUser = recycleUser == null ? null : recycleUser.trim();
    }

    public Date getRecycleTime() {
        return recycleTime;
    }

    public void setRecycleTime(Date recycleTime) {
        this.recycleTime = recycleTime;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive == null ? null : isActive.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

	@Override
	public String getBizId() {
		return this.id;
	}
}