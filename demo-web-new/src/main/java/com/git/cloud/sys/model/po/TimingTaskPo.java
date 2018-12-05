package com.git.cloud.sys.model.po;

import com.git.cloud.common.model.base.BaseBO;

import java.io.Serializable;
import java.util.Date;

public class TimingTaskPo extends BaseBO implements Serializable {

    private String id;

    private String targetBean;

    private String targetMethod;

    private Date execTime;

    private String status;

    private String createUser;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String parameterJson;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTargetBean() {
        return targetBean;
    }

    public void setTargetBean(String targetBean) {
        this.targetBean = targetBean == null ? null : targetBean.trim();
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod == null ? null : targetMethod.trim();
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
    public String getBizId() {
        return null;
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

    public String getParameterJson() {
        return parameterJson;
    }

    public void setParameterJson(String parameterJson) {
        this.parameterJson = parameterJson;
    }
}