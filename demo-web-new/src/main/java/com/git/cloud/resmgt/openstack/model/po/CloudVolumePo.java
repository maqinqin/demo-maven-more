package com.git.cloud.resmgt.openstack.model.po;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

public class CloudVolumePo extends BaseBO implements Serializable {
    private String id;

    private String availabilityZone;

    private Date updateDate;

    private String size;

    private String createUser;

    private String projectId;

    private String storageType;

    private String remark;

    private String volumeType;

    private String volumeName;

    private String isShare;

    private String sysVolume;

    private String isActive;

    private Date createTime;

    private Date deleteTime;

    private String imageId;

    private String passthrough;

    private String iaasUuid;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone == null ? null : availabilityZone.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType == null ? null : storageType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(String volumeType) {
        this.volumeType = volumeType == null ? null : volumeType.trim();
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName == null ? null : volumeName.trim();
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare == null ? null : isShare.trim();
    }

    public String getSysVolume() {
        return sysVolume;
    }

    public void setSysVolume(String sysVolume) {
        this.sysVolume = sysVolume == null ? null : sysVolume.trim();
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

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId == null ? null : imageId.trim();
    }

    public String getPassthrough() {
        return passthrough;
    }

    public void setPassthrough(String passthrough) {
        this.passthrough = passthrough == null ? null : passthrough.trim();
    }

    public String getIaasUuid() {
        return iaasUuid;
    }

    public void setIaasUuid(String iaasUuid) {
        this.iaasUuid = iaasUuid == null ? null : iaasUuid.trim();
    }

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}