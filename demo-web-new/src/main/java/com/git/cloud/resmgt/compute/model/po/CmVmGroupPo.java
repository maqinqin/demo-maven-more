package com.git.cloud.resmgt.compute.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class CmVmGroupPo extends BaseBO implements java.io.Serializable {
    private String id;

    private String name;

    private String policies;

    private String projectId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String isActive;

    private String datacenterId;

    private String vmMsId;
    
    private String iaasUuid;
    
    private static final long serialVersionUID = 1L;

    public String getIaasUuid() {
         return iaasUuid;
    }
    public void setIaasUuid(String iaasUuid) {
         this.iaasUuid = iaasUuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies == null ? null : policies.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive == null ? null : isActive.trim();
    }

    public String getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(String datacenterId) {
        this.datacenterId = datacenterId == null ? null : datacenterId.trim();
    }

    public String getVmMsId() {
        return vmMsId;
    }

    public void setVmMsId(String vmMsId) {
        this.vmMsId = vmMsId == null ? null : vmMsId.trim();
    }

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}