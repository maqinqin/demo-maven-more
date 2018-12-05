package com.git.cloud.handler.automation.fw.model;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwFirewallNatAddressPo extends BaseBO implements Serializable {
    private String id;

    private String natId;

    private String ip;

    private String status;

    private String snatIp;

    private String dnatIp;

    private String externalIp;

    private String isActive;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    
    private String exist;
    
    private String natPolicyId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNatId() {
        return natId;
    }

    public void setNatId(String natId) {
        this.natId = natId == null ? null : natId.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSnatIp() {
        return snatIp;
    }

    public void setSnatIp(String snatIp) {
        this.snatIp = snatIp == null ? null : snatIp.trim();
    }

    public String getDnatIp() {
        return dnatIp;
    }

    public void setDnatIp(String dnatIp) {
        this.dnatIp = dnatIp == null ? null : dnatIp.trim();
    }

    public String getExternalIp() {
        return externalIp;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp == null ? null : externalIp.trim();
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

	public String getExist() {
		return exist;
	}

	public void setExist(String exist) {
		this.exist = exist;
	}

	public String getNatPolicyId() {
		return natPolicyId;
	}

	public void setNatPolicyId(String natPolicyId) {
		this.natPolicyId = natPolicyId;
	}

	@Override
	public String getBizId() {
		return this.id;
	}
}