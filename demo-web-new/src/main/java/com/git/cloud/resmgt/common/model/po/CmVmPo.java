package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

import java.sql.Timestamp;

public class CmVmPo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2708253817664310763L;
	private String id;
	private String hostId;
	private Integer cpu;
	private Integer mem;
	private Integer disk;
	private Timestamp createTime;
	private Timestamp onlineTime;
	private String datastoreId;
	private String datastoreType;
	private String duId;
	private Integer orderNum;
	private String appId;
	//新增
	private String ip;
	private String projectId;
	private String platformCode;
	private String tenantId;
	private String serviceId;
	private Integer externalDiskSum;//外挂磁盘总和
	//用户id
	private String userId;
	private String iaasUuid;
	/**
	 * 虚拟机组ID
	 */
	private String vmGroupId;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getExternalDiskSum() {
		return externalDiskSum;
	}

	public void setExternalDiskSum(Integer externalDiskSum) {
		this.externalDiskSum = externalDiskSum;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getPlatformCode() {
		return platformCode;
	}


	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	//power
	private String deviceName;
	private String lparName;
	private String hostName;
	private String manageIp;
	private String userName;
	private String password;
	private String lparId;
	private String lparNamePrefix;
	private String serverId; // openstack虚机ID
	

	// Constructors
	
	public String getDeviceName() {
		return deviceName;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getLparName() {
		return lparName;
	}

	public void setLparName(String lparName) {
		this.lparName = lparName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getManageIp() {
		return manageIp;
	}

	public void setManageIp(String manageIp) {
		this.manageIp = manageIp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/** default constructor */
	public CmVmPo() {
	}

	/** minimal constructor */
	public CmVmPo(String id) {
		this.id = id;
	}

	/** full constructor */
	public CmVmPo(String id, String hostId, Integer cpu, Integer mem,
			Integer disk, Timestamp createTime, Timestamp onlineTime, String datastoreId,String duId) {
		this.id = id;
		this.hostId = hostId;
		this.cpu = cpu;
		this.mem = mem;
		this.disk = disk;
		this.createTime = createTime;
		this.onlineTime = onlineTime;
		this.datastoreId = datastoreId;
		this.duId = duId;
	}

	// Property accessors
	public String getDatastoreType() {
		return datastoreType;
	}
	
	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}

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

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Timestamp onlineTime) {
		this.onlineTime = onlineTime;
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

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getLparId() {
		return lparId;
	}

	public void setLparId(String lparId) {
		this.lparId = lparId;
	}

	public String getLparNamePrefix() {
		return lparNamePrefix;
	}

	public void setLparNamePrefix(String lparNamePrefix) {
		this.lparNamePrefix = lparNamePrefix;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getVmGroupId() {
		return vmGroupId;
	}

	public void setVmGroupId(String vmGroupId) {
		this.vmGroupId = vmGroupId;
	}

	public String getIaasUuid() {
		return iaasUuid;
	}

	public void setIaasUuid(String iaasUuid) {
		this.iaasUuid = iaasUuid;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}


}
