package com.git.cloud.resmgt.network.model.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//设备与地址关联信息
public class DeviceNetIP {
	private String hostIp;
	
	
	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public DeviceNetIP() {
	}
	
	public DeviceNetIP(String deviceID, String clusterID) {
		super();
		this.deviceID = deviceID;
		this.clusterID = clusterID;
	}
	// 设备ID
	private String deviceID;

	// 设备归属的集群ID
	private String clusterID;

	/**
	 * 分配的各种网络地址类型地址列表
	 * HashMap Key取值范围：
	 * HP	物理机生产地址
	 * HM	物理机管理地址
	 * HV	物理机管理-vMontion地址
	 * HL	物理机管理-ILO地址
	 * HF1	物理机管理-FSP1地址
	 * HF2	物理机管理-FSP2地址
	 * NP	NAS存储生产地址
	 * NM	NAS存储管理地址
	 * VP	虚拟机生产地址
	 * VM	虚拟机管理地址
	 * VR	虚拟机RAC心跳地址
	 */
	private Map<String, List<NetIPInfo>> netIPs = new HashMap<String, List<NetIPInfo>>();

	/**
	 * @return deviceID 设备ID
	 */
	public String getDeviceID() {
		return deviceID;
	}

	/**
	 * @param deviceID 设置的设备ID值
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * @return clusterID 设备的归属集群ID
	 */
	public String getClusterID() {
		return clusterID;
	}

	/**
	 * @param clusterID
	 *            设置的设备归属集群ID值
	 */
	public void setClusterID(String clusterID) {
		this.clusterID = clusterID;
	}

	/**
	 * @return netIPs 分配的各种网络地址类型地址列表
	 */
	public Map<String, List<NetIPInfo>> getNetIPs() {
		return netIPs;
	}

	/**
	 * @param netIPs
	 *            设置分配的各种网络地址类型地址列表
	 */
	public void setNetIPs(Map<String, List<NetIPInfo>> netIPs) {
		this.netIPs = netIPs;
	}
	
}
