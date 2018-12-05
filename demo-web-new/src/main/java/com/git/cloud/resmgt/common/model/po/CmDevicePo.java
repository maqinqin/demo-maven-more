package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.excel.model.vo.HostVo;

public class CmDevicePo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = 6761667612666827884L;
	private String id;
	private String seatId;
	private String deviceName;
	private String sn;
	private String deviceModelId;
	private String resPoolId;
	private String isActive;
	private String description;
	private String deviceStatus;
	private String seatCode;
	private String orderNum;
	private String clusterId;
	private String ip;
	
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	//设备运行状态
	private String runningState;
	public String getRunningState() {
		return runningState;
	}
	public void setRunningState(String runningState) {
		this.runningState = runningState;
	}
	//设备类型（是物理机还是虚拟机）
	private String hostType;
	//平台类型
	private String platformType;
	//虚拟化类型
	private String virtualType;
	//缺省datastore的id
	private String datastoreId;
	
	private String datastoreType;
	private String isInvc;
	private String lparId;
	private String lparName;
	private String lparNamePrefix;
	private String profileName;
	// openstack服务器ID
	private String serverId;
	//安全组ID
	private String securityGroupId;
	
	
	public String getSecurityGroupId() {
		return securityGroupId;
	}
	public void setSecurityGroupId(String securityGroupId) {
		this.securityGroupId = securityGroupId;
	}
	public CmDevicePo() {
		super();
	}
	public CmDevicePo(HostVo host){
		this.id = host.getId();
		this.deviceName = host.getHostName();
		this.isActive = "Y";
		this.isInvc = "Y";
		this.resPoolId = host.getResPoolId();
	}
	
	public String getIsInvc() {
		return isInvc;
	}
	public void setIsInvc(String isInvc) {
		this.isInvc = isInvc;
	}
	public String getDatastoreType() {
		return datastoreType;
	}
	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}
	public String getDatastoreId() {
		return datastoreId;
	}
	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
	public String getSeatCode() {
		return seatCode;
	}
	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}
	public String getHostType() {
		return hostType;
	}
	public void setHostType(String hostType) {
		this.hostType = hostType;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getVirtualType() {
		return virtualType;
	}
	public void setVirtualType(String virtualType) {
		this.virtualType = virtualType;
	}
	public String getLparId() {
		return lparId;
	}
	public void setLparId(String lparId) {
		this.lparId = lparId;
	}
	public String getLparName() {
		return lparName;
	}
	public void setLparName(String lparName) {
		this.lparName = lparName;
	}
	public String getProfileName() {
		return profileName;
	}
	public String getLparNamePrefix() {
		return lparNamePrefix;
	}
	public void setLparNamePrefix(String lparNamePrefix) {
		this.lparNamePrefix = lparNamePrefix;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "CmDevicePo [id=" + id + ", seatId=" + seatId + ", deviceName=" + deviceName + ", sn=" + sn
				+ ", deviceModelId=" + deviceModelId + ", resPoolId=" + resPoolId + ", isActive=" + isActive
				+ ", description=" + description + ", deviceStatus=" + deviceStatus + ", seatCode=" + seatCode
				+ ", orderNum=" + orderNum + ", clusterId=" + clusterId + ", runningState=" + runningState
				+ ", hostType=" + hostType + ", platformType=" + platformType + ", virtualType=" + virtualType
				+ ", datastoreId=" + datastoreId + ", datastoreType=" + datastoreType + ", isInvc=" + isInvc
				+ ", lparId=" + lparId + ", lparName=" + lparName + ", lparNamePrefix=" + lparNamePrefix
				+ ", profileName=" + profileName + ", serverId=" + serverId + ", securityGroupId=" + securityGroupId
				+ "]";
	}
	
}
