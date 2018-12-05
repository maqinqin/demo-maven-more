package com.git.cloud.resmgt.compute.model.vo;

/**
 * 
 * @author renxinlei	
 * vm的vo类
 *
 */
public class VmVo {
	//Vm_Manage_Server
	private String serverName;
	private String userName;
	private String password;
	private String url;
	private String hostName;
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	//CM_VM
	private String 	Id;
	private String	hostId;
	private String  hostType;
	private String	cpu;
	private String	memory;
	private String	disk;
	private String	createTime;
	private String	onlineTime;
	private String	datastoreId;
	private String 	duId;
	private String 	orderNum;
	private String	createDatetime;
	private String 	createUser;
	private String 	updateUser;
	private String 	updateDatetime;
	private String 	datastoreType;
	
	//CM_DEVICE
	private String	vmName;
	private String	sn;
	private String	deviceModelId;
	private String	seatId;
	private String	resPoolId;
	private String	isActive;
	private String	description;
	private String	deviceStatus;
	private String	isInvc;
	private String	runningState;
	
	//RM_NW_IP_ADDRESS
	private String	cClassId;
	private String	seq;
	private String	useRelCode;
	private String	deviceId;
	private String	appDuId;
	private String	nwResPoolId;
	private String	nwRuleListId;
	private String	allocedStatusCode;
	private String	allocedTime;
	
	private String	cloudService;
	private String	ipList;
	
	
	private String datastoreName;
	private String platFormTypeCode;
	
	public String getPlatFormTypeCode() {
		return platFormTypeCode;
	}
	public void setPlatFormTypeCode(String platFormTypeCode) {
		this.platFormTypeCode = platFormTypeCode;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDatastoreName() {
		return datastoreName;
	}
	public void setDatastoreName(String datastoreName) {
		this.datastoreName = datastoreName;
	}
	public String getDisk() {
		return disk;
	}
	public void setDisk(String disk) {
		this.disk = disk;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getDatastoreId() {
		return datastoreId;
	}
	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getDatastoreType() {
		return datastoreType;
	}
	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getDeviceModelId() {
		return deviceModelId;
	}
	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}
	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
	public String getResPoolId() {
		return resPoolId;
	}
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getIsInvc() {
		return isInvc;
	}
	public void setIsInvc(String isInvc) {
		this.isInvc = isInvc;
	}
	public String getRunningState() {
		return runningState;
	}
	public void setRunningState(String runningState) {
		this.runningState = runningState;
	}
	public String getcClassId() {
		return cClassId;
	}
	public void setcClassId(String cClassId) {
		this.cClassId = cClassId;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUseRelCode() {
		return useRelCode;
	}
	public void setUseRelCode(String useRelCode) {
		this.useRelCode = useRelCode;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getAppDuId() {
		return appDuId;
	}
	public void setAppDuId(String appDuId) {
		this.appDuId = appDuId;
	}
	public String getNwResPoolId() {
		return nwResPoolId;
	}
	public void setNwResPoolId(String nwResPoolId) {
		this.nwResPoolId = nwResPoolId;
	}
	public String getNwRuleListId() {
		return nwRuleListId;
	}
	public void setNwRuleListId(String nwRuleListId) {
		this.nwRuleListId = nwRuleListId;
	}
	public String getAllocedStatusCode() {
		return allocedStatusCode;
	}
	public void setAllocedStatusCode(String allocedStatusCode) {
		this.allocedStatusCode = allocedStatusCode;
	}
	public String getAllocedTime() {
		return allocedTime;
	}
	public void setAllocedTime(String allocedTime) {
		this.allocedTime = allocedTime;
	}
	public String getDuId() {
		return duId;
	}
	public void setDuId(String duId) {
		this.duId = duId;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getCloudService() {
		return cloudService;
	}
	public void setCloudService(String cloudService) {
		this.cloudService = cloudService;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getIpList() {
		return ipList;
	}
	public void setIpList(String ipList) {
		this.ipList = ipList;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getHostType() {
		return hostType;
	}
	public void setHostType(String hostType) {
		this.hostType = hostType;
	}

}
