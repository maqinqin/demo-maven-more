package com.git.cloud.resmgt.common.model.vo;


import com.git.cloud.common.model.base.BaseBO;
import java.util.Date;
/**
 * 
 * @author 王成辉
 * @Description 快照表
 * @date 2014-12-17
 *
 */
public class CmSnapshotVo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = 6761667612676827884L;
	private String snapshotId;
	private String snapshotName;
	private String vmId;
	private String vmName;
	private String createUser;
	private Date createDate;
	private String isSucc;
	private String isActive;
	private String snapshotMemory;
	private String snapshotSilence;
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	

	public String getVmName() {
		return vmName;
	}


	public void setVmName(String vmName) {
		this.vmName = vmName;
	}


	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public String getSnapshotName() {
		return snapshotName;
	}

	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIsSucc() {
		return isSucc;
	}

	public void setIsSucc(String isSucc) {
		this.isSucc = isSucc;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getSnapshotMemory() {
		return snapshotMemory;
	}


	public void setSnapshotMemory(String snapshotMemory) {
		this.snapshotMemory = snapshotMemory;
	}


	public String getSnapshotSilence() {
		return snapshotSilence;
	}


	public void setSnapshotSilence(String snapshotSilence) {
		this.snapshotSilence = snapshotSilence;
	}

}
