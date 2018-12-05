package com.git.cloud.resmgt.storage.model.vo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:StoragePoolVo
 * @Description: 存储资源池VO对象
 * @author mijia
 */
public class StoragePoolVo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = 6997663773772673432L;
	private String id;
	private String datacenterId;
	private String poolName;
	private String ename;
	private String status;
	private String poolType;
	private String isActive;
	private String remark;
	private String serviceLevelCode;
	private String storageResPoolCode;
	private String dataCenterName;

	public String getDataCenterName() {
		return dataCenterName;
	}

	public void setDataCenterName(String dataCenterName) {
		this.dataCenterName = dataCenterName;
	}

	/** default constructor */
	public StoragePoolVo() {
	}

	/** minimal constructor */
	public StoragePoolVo(String id) {
		this.id = id;
	}

	/** full constructor */
	public StoragePoolVo(String id, String datacenterId, String poolName,
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

	public String getServiceLevelCode() {
		return serviceLevelCode;
	}

	public void setServiceLevelCode(String serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}

	public String getStorageResPoolCode() {
		return storageResPoolCode;
	}

	public void setStorageResPoolCode(String storageResPoolCode) {
		this.storageResPoolCode = storageResPoolCode;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
