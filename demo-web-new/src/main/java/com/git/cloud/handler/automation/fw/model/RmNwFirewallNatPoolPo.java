package com.git.cloud.handler.automation.fw.model;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwFirewallNatPoolPo extends BaseBO implements Serializable {
    private String id;

    private String networkArea;

    private String externalOrg;

    private String operator;

    private String ipAddress;

    private Integer assignOrder;

    private String isActive;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNetworkArea() {
        return networkArea;
    }

    public void setNetworkArea(String networkArea) {
        this.networkArea = networkArea == null ? null : networkArea.trim();
    }

    public String getExternalOrg() {
        return externalOrg;
    }

    public void setExternalOrg(String externalOrg) {
        this.externalOrg = externalOrg == null ? null : externalOrg.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public Integer getAssignOrder() {
        return assignOrder;
    }

    public void setAssignOrder(Integer assignOrder) {
        this.assignOrder = assignOrder;
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