package com.git.cloud.resmgt.compute.model.po;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.git.cloud.common.model.base.BaseBO;

public class RmCdpPo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = -1;
	private String id;
	private String resPoolId;
	private String cdpName;
	private String ename;
	// 启用状态Y/N，未使用
	private String status;
	// 逻辑删除标识Y/N
	private String isActive;
	private String remark;
	// 虚拟机管理机id列表，用于更新vcenter中的目录
	private Set<String> vmManagedServerIds = new HashSet<String>();

	/** default constructor */
	public RmCdpPo() {
	}

	/** minimal constructor */
	public RmCdpPo(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RmCdpPo [id=" + id + ", resPoolId=" + resPoolId + ", cdpName=" + cdpName + ", ename=" + ename + ", status=" + status + ", isActive=" + isActive + ", remark=" + remark + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getCdpName() {
		return cdpName;
	}

	public void setCdpName(String cdpName) {
		this.cdpName = cdpName;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<String> getVmManagedServerIds() {
		return vmManagedServerIds;
	}

	public void setVmManagedServerIds(Set<String> vmManagedServerId) {
		this.vmManagedServerIds = vmManagedServerId;
	}

	@Override
	public String getBizId() {
		return id;
	}

}
