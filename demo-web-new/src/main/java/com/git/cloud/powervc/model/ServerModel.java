package com.git.cloud.powervc.model;

/** 
 * 服务器
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public class ServerModel {
	private String azName;
	private String flavorId;
	private String networkId;
	private String serverIp;
	private String serverName;
	private String hostName; // 所属主机名称
	private String volumeId; // 系统盘ID（虚拟机需要此属性）
	private String imageId; // 镜像ID（物理服务器需要此属性）
	
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
}
