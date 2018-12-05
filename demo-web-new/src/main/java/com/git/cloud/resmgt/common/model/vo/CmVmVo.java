package com.git.cloud.resmgt.common.model.vo;

import java.sql.Timestamp;

import com.git.cloud.common.model.base.BaseBO;

public class CmVmVo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2708253817664310763L;
	private String id;
	private String hostId;
//	private Integer cpu;
//	private Integer mem;
//	private Integer disk;
	private String cpu;
	private String mem;
	private String disk;
	private Timestamp createTime;
	private String datastoreId;
	private String duId;
	private String duName;
	private String duEname;
	private String cmVmName;
	private String cmVmIps;
	private String deviceName;
	private String platformType;
	private String vmType;
	private Integer orderNum;
	private String resPoolId;

	// Constructors

	/** default constructor */
	public CmVmVo() {
	}

	/** minimal constructor */
	public CmVmVo(String id) {
		this.id = id;
	}

	/** full constructor */
	public CmVmVo(String id, String hostId, String cpu, String mem,
			String disk, Timestamp createTime, String datastoreId,String duId) {
		this.id = id;
		this.hostId = hostId;
		this.cpu = cpu;
		this.mem = mem;
		this.disk = disk;
		this.createTime = createTime;
		this.datastoreId = datastoreId;
		this.duId = duId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

//	public Integer getCpu() {
//		return this.cpu;
//	}
//
//	public void setCpu(Integer cpu) {
//		this.cpu = cpu;
//	}
//
//	public Integer getMem() {
//		return this.mem;
//	}
//
//	public void setMem(Integer mem) {
//		this.mem = mem;
//	}
//
//	public Integer getDisk() {
//		return this.disk;
//	}
//
//	public void setDisk(Integer disk) {
//		this.disk = disk;
//	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDatastoreId() {
		return this.datastoreId;
	}

	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}

	public String getDuId() {
		return duId;
	}

	public void setDuId(String duId) {
		this.duId = duId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getDuName() {
		return duName;
	}

	public void setDuName(String duName) {
		this.duName = duName;
	}

	public String getCmVmName() {
		return cmVmName;
	}

	public void setCmVmName(String cmVmName) {
		this.cmVmName = cmVmName;
	}

	public String getCmVmIps() {
		return cmVmIps;
	}

	public void setCmVmIps(String cmVmIps) {
		this.cmVmIps = cmVmIps;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getVmType() {
		return vmType;
	}

	public void setVmType(String vmType) {
		this.vmType = vmType;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getDuEname() {
		return duEname;
	}

	public void setDuEname(String duEname) {
		this.duEname = duEname;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}
	

}
