package com.git.cloud.handler.automation.fw.model;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwOutsideNatPolicyPo extends BaseBO implements Serializable {
    private String id;
    
    private String firewallRequestId;

    private String natType;

    private String fwId;

    private String srcIp;

    private String dstIp;
    
    private String srcPort;
    
    private String dstPort;
    
    private String natIp;
    
    private String flag;

    private String status;

    private String description;

    private String isActive;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    
    private String targetNatId;

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

	public String getNatType() {
        return natType;
    }

    public void setNatType(String natType) {
        this.natType = natType == null ? null : natType.trim();
    }

    public String getFwId() {
        return fwId;
    }

    public void setFwId(String fwId) {
        this.fwId = fwId == null ? null : fwId.trim();
    }

    public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
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

	public String getNatIp() {
		return natIp;
	}

	public void setNatIp(String natIp) {
		this.natIp = natIp;
	}

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
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

	public String getTargetNatId() {
		return targetNatId;
	}

	public void setTargetNatId(String targetNatId) {
		this.targetNatId = targetNatId;
	}

	@Override
	public String getBizId() {
		return this.id;
	}
}