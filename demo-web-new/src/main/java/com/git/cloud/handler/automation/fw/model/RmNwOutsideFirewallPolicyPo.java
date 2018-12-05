package com.git.cloud.handler.automation.fw.model;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwOutsideFirewallPolicyPo extends BaseBO implements Serializable {
    private String id;
    
    private String firewallRequestId;

    private String requestCode;

    private String fwType;

    private String fwId;
    
    private String protocol;

    private String action;

    private String srcIp;

    private String dstIp;
    
    private String srcPort;
    
    private String dstPort;

    private String srcIpDetail;

    private String dstIpDetail;

    private String status;

    private String description;

    private String isActive;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    
    private String targetPolicyId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFirewallRequestId() {
		return firewallRequestId;
	}

	public void setFirewallRequestId(String firewallRequestId) {
		this.firewallRequestId = firewallRequestId;
	}

	public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode == null ? null : requestCode.trim();
    }

    public String getFwType() {
        return fwType;
    }

    public void setFwType(String fwType) {
        this.fwType = fwType == null ? null : fwType.trim();
    }

    public String getFwId() {
        return fwId;
    }

    public void setFwId(String fwId) {
        this.fwId = fwId == null ? null : fwId.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp == null ? null : srcIp.trim();
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp == null ? null : dstIp.trim();
    }

    public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}

	public String getDstPort() {
		return dstPort;
	}

	public void setDstPort(String dstPort) {
		this.dstPort = dstPort;
	}

	public String getSrcIpDetail() {
        return srcIpDetail;
    }

    public void setSrcIpDetail(String srcIpDetail) {
        this.srcIpDetail = srcIpDetail == null ? null : srcIpDetail.trim();
    }

    public String getDstIpDetail() {
        return dstIpDetail;
    }

    public void setDstIpDetail(String dstIpDetail) {
        this.dstIpDetail = dstIpDetail == null ? null : dstIpDetail.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

	public String getTargetPolicyId() {
		return targetPolicyId;
	}

	public void setTargetPolicyId(String targetPolicyId) {
		this.targetPolicyId = targetPolicyId;
	}

	@Override
	public String getBizId() {
		return this.id;
	}
}