package com.git.cloud.resmgt.common.model.po;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;

public class RmResPoolPo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6997663773772673432L;
	private String id;
	private String datacenterId;
	private String poolName;
	private String ename;
	private String status;
	private String poolType;
	private String isActive;
	private String remark;

	// Constructors

	/** default constructor */
	public RmResPoolPo() {
	}

	/** minimal constructor */
	public RmResPoolPo(String id) {
		this.id = id;
	}

	/** full constructor */
	public RmResPoolPo(String id, String datacenterId, String poolName,
			String ename, String status, String poolType, String isActive,
			String remark) {
		this.id = id;
		this.datacenterId = datacenterId;
		this.poolName = poolName;
		this.ename = ename;
		this.status = status;
		this.poolType = poolType;
		this.isActive = isActive;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPoolName() {
		return this.poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPoolType() {
		return this.poolType;
	}

	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RmClusterPo> getClusterList() {
		return clusterList;
	}

	public void setClusterList(List<RmClusterPo> clusterList) {
		this.clusterList = clusterList;
	}

	private List<RmClusterPo> clusterList;
}
