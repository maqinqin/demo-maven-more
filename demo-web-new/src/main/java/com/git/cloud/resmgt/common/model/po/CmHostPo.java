package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class CmHostPo extends BaseBO implements java.io.Serializable{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1275281277058176675L;
	private String id;
	private Integer cpu;
	private Integer mem;
	private Integer disk;
	private Integer diskUsed;
	private Integer cpuUsed;
	private Integer memUsed;
	private String clusterId;
	private Integer  lastCpuSum;
	private Integer lastCpuUsedSum;
	private String resPoolId;
	
	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getDatastoreId() {
		return datastoreId;
	}

	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}

	private Integer lastMemSum;
	private Integer lastMemUsedSum;
	private String datastoreType;
	private String datastoreId;
	private String datastoreName;
	private String localDatastoreName;
	

	// Constructors
	/** default constructor */
	public CmHostPo() {
	}

	/** minimal constructor */
	public CmHostPo(String id) {
		this.id = id;
	}

	/** full constructor */
	public CmHostPo(String id, Integer cpu, Integer mem,
			Integer disk,Integer diskUsed, Integer cpuUsed, Integer memUsed,Integer lastMemSum,Integer lastMemUsedSum, String clusterId,Integer lastCpuSum,Integer lastCpuUsedSum,String datastoreType,String datastoreName,String localDatastoreName) {
		this.id = id;
		this.cpu = cpu;
		this.mem = mem;
		this.disk = disk;
		this.diskUsed = diskUsed;
		this.cpuUsed = cpuUsed;
		this.memUsed = memUsed;
		this.clusterId = clusterId;
		this.lastCpuSum = lastCpuSum;
		this.lastCpuUsedSum = lastCpuUsedSum;
		this.lastMemSum = lastMemSum;
		this.lastMemUsedSum = lastMemUsedSum;
		this.datastoreType = datastoreType;
		this.datastoreName = datastoreName;
		this.localDatastoreName = localDatastoreName;
	}
	
	/**
	 * 工银瑞信vc扫描
	 * @param cpu
	 * @param mem
	 */
	public CmHostPo(Integer cpu, Integer mem) {
		this.cpu = cpu;
		this.mem = mem;
	}

	// Property accessors
	
	public String getDatastoreType() {
		return datastoreType;
	}
	
	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}

	public String getDatastoreName() {
		return datastoreName;
	}

	public void setDatastoreName(String datastoreName) {
		this.datastoreName = datastoreName;
	}

	public String getLocalDatastoreName() {
		return localDatastoreName;
	}

	public void setLocalDatastoreName(String localDatastoreName) {
		this.localDatastoreName = localDatastoreName;
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCpu() {
		return this.cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getMem() {
		return this.mem;
	}

	public void setMem(Integer mem) {
		this.mem = mem;
	}

	public Integer getDisk() {
		return this.disk;
	}

	public void setDisk(Integer disk) {
		this.disk = disk;
	}
	
	public Integer getDiskUsed() {
		return diskUsed;
	}

	public void setDiskUsed(Integer diskUsed) {
		this.diskUsed = diskUsed;
	}

	public Integer getCpuUsed() {
		return this.cpuUsed;
	}

	public void setCpuUsed(Integer cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	public Integer getMemUsed() {
		return this.memUsed;
	}

	public void setMemUsed(Integer memUsed) {
		this.memUsed = memUsed;
	}

	public String getClusterId() {
		return this.clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public Integer getLastCpuSum() {
		return lastCpuSum;
	}

	public void setLastCpuSum(Integer lastCpuSum) {
		this.lastCpuSum = lastCpuSum;
	}

	public Integer getLastCpuUsedSum() {
		return lastCpuUsedSum;
	}

	public void setLastCpuUsedSum(Integer lastCpuUsedSum) {
		this.lastCpuUsedSum = lastCpuUsedSum;
	}
	
	public Integer getLastMemSum() {
		return lastMemSum;
	}

	public void setLastMemSum(Integer lastMemSum) {
		this.lastMemSum = lastMemSum;
	}

	public Integer getLastMemUsedSum() {
		return lastMemUsedSum;
	}

	public void setLastMemUsedSum(Integer lastMemUsedSum) {
		this.lastMemUsedSum = lastMemUsedSum;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}


}