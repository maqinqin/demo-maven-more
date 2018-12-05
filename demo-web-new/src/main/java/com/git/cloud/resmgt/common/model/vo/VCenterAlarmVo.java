package com.git.cloud.resmgt.common.model.vo;

import java.util.Date;
import java.util.List;

import com.git.cloud.common.model.base.BaseBO;

/**
 * Created by WangJingxin on 2016/4/29.
 */
public class VCenterAlarmVo extends BaseBO {

	private static final long serialVersionUID = 6157787808420218325L;
	
	private String key;
    private Date triggeredTime;
    private String entityType;
    private String entityName;
    private String alarmKey;
    private String name;
    private String description;
    private String systemName;
    private Boolean acknowledged;
    private String acknowledgedUser;
    private Date acknowledgedTime;
    private Date lastModifiedTime;
    private String lastModifiedUser;
    private String id;
    private Date insertTime;
    private List<String> hostNameList;
    private List<String> vmNameList;
    private String hostNames;
    private String vmNames;
    private String vCenterUrl;

    
    public String getHostNames() {
		return hostNames;
	}

	public void setHostNames(String hostNames) {
		this.hostNames = hostNames;
	}

	public String getVmNames() {
		return vmNames;
	}

	public void setVmNames(String vmNames) {
		this.vmNames = vmNames;
	}

	public List<String> getVmNameList() {
		return vmNameList;
	}

	public void setVmNameList(List<String> vmNameList) {
		this.vmNameList = vmNameList;
	}

	public List<String> getHostNameList() {
		return hostNameList;
	}

	public void setHostNameList(List<String> hostNameList) {
		this.hostNameList = hostNameList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getTriggeredTime() {
        return triggeredTime;
    }

    public void setTriggeredTime(Date triggeredTime) {
        this.triggeredTime = triggeredTime;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getAlarmKey() {
        return alarmKey;
    }

    public void setAlarmKey(String alarmKey) {
        this.alarmKey = alarmKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Boolean getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(Boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public String getAcknowledgedUser() {
        return acknowledgedUser;
    }

    public void setAcknowledgedUser(String acknowledgedUser) {
        this.acknowledgedUser = acknowledgedUser;
    }

    public Date getAcknowledgedTime() {
        return acknowledgedTime;
    }

    public void setAcknowledgedTime(Date acknowledgedTime) {
        this.acknowledgedTime = acknowledgedTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Override
	public String getBizId() {
		return null;
	}

	public String getvCenterUrl() {
		return vCenterUrl;
	}

	public void setvCenterUrl(String vCenterUrl) {
		this.vCenterUrl = vCenterUrl;
	}
    
    
}
