package com.git.cloud.iaas.openstack.model;

public class VmRestModel {
	private String azName;
	private String flavorId;
	private String networkId;
	private String serverIp;
	private String serverName;
	private String hostName; // 所属主机名称
	private String volumeId; // 系统盘ID（虚拟机需要此属性）
	private String imageId; // 镜像ID（物理服务器需要此属性）
	private String portId;//虚拟机端口ID,6.3绑定、解绑弹性IP必传参数；6.3添加移除vm安全组必传参数
	private String serverId;//2.06绑定、解绑弹性IP必传参数;添加移除vm安全组必传参数
	private String securityGroupIdInfo;//6.3添加移除vm安全组必传参数，格式"/"123234345/",/"234325345/""。
	private String securityGroupId;//2.06添加移除vm安全组必传参数
	private String floatingIpId;
	private String floatingIp;
	private String floatingIpPoolId;//6.3创建浮动IP使用
	private String floatingIpPoolName;//2.06创建浮动IP使用
	private String vmGroupId; //虚拟机组ID
	//6.3裸金属需要
	private String routerId;
	private String subnetId;
	
	public String getRouterId() {
		return routerId;
	}
	public void setRouterId(String routerId) {
		this.routerId = routerId;
	}
	public String getSubnetId() {
		return subnetId;
	}
	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}
	public String getFloatingIpId() {
		return floatingIpId;
	}
	public void setFloatingIpId(String floatingIpId) {
		this.floatingIpId = floatingIpId;
	}
	public String getFloatingIp() {
		return floatingIp;
	}
	public void setFloatingIp(String floatingIp) {
		this.floatingIp = floatingIp;
	}
	public String getFloatingIpPoolId() {
		return floatingIpPoolId;
	}
	public void setFloatingIpPoolId(String floatingIpPoolId) {
		this.floatingIpPoolId = floatingIpPoolId;
	}
	public String getFloatingIpPoolName() {
		return floatingIpPoolName;
	}
	public void setFloatingIpPoolName(String floatingIpPoolName) {
		this.floatingIpPoolName = floatingIpPoolName;
	}
	public String getSecurityGroupIdInfo() {
		return securityGroupIdInfo;
	}
	public void setSecurityGroupIdInfo(String securityGroupIdInfo) {
		this.securityGroupIdInfo = securityGroupIdInfo;
	}
	public String getSecurityGroupId() {
		return securityGroupId;
	}
	public void setSecurityGroupId(String securityGroupId) {
		this.securityGroupId = securityGroupId;
	}
	public String getPortId() {
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getAzName() {
		return azName;
	}
	public void setAzName(String azName) {
		this.azName = azName;
	}
	public String getFlavorId() {
		return flavorId;
	}
	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getVolumeId() {
		return volumeId;
	}
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	public String getVmGroupId() {
		return vmGroupId;
	}
	public void setVmGroupId(String vmGroupId) {
		this.vmGroupId = vmGroupId;
	}
	@Override
	public String toString() {
		return "VmRestModel [azName=" + azName + ", flavorId=" + flavorId + ", networkId=" + networkId + ", serverIp="
				+ serverIp + ", serverName=" + serverName + ", hostName=" + hostName + ", volumeId=" + volumeId
				+ ", imageId=" + imageId + ", portId=" + portId + ", serverId=" + serverId + ", securityGroupIdInfo="
				+ securityGroupIdInfo + ", securityGroupId=" + securityGroupId + ", floatingIpId=" + floatingIpId
				+ ", floatingIp=" + floatingIp + ", floatingIpPoolId=" + floatingIpPoolId + ", floatingIpPoolName="
				+ floatingIpPoolName + ", vmGroupId=" + vmGroupId + "]";
	}

}
