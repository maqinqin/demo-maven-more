package com.git.cloud.rest.model;

public class CmHostAndCmVm {
	
	private String hostId;
	private String hostCpu;
	private String hostMem;
	private String hostCpuUsed;
	private String hostMemUsed;
	
	private String vmId;
	private String vmCpu;
	private String vmMem;
	private String ip;
	private String appId;
	private String disk;
	
	public String getDisk() {
		return disk;
	}
	public void setDisk(String disk) {
		this.disk = disk;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getHostCpu() {
		return hostCpu;
	}
	public void setHostCpu(String hostCpu) {
		this.hostCpu = hostCpu;
	}
	public String getHostMem() {
		return hostMem;
	}
	public void setHostMem(String hostMem) {
		this.hostMem = hostMem;
	}
	public String getHostCpuUsed() {
		return hostCpuUsed;
	}
	public void setHostCpuUsed(String hostCpuUsed) {
		this.hostCpuUsed = hostCpuUsed;
	}
	public String getHostMemUsed() {
		return hostMemUsed;
	}
	public void setHostMemUsed(String hostMemUsed) {
		this.hostMemUsed = hostMemUsed;
	}
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	public String getVmCpu() {
		return vmCpu;
	}
	public void setVmCpu(String vmCpu) {
		this.vmCpu = vmCpu;
	}
	public String getVmMem() {
		return vmMem;
	}
	public void setVmMem(String vmMem) {
		this.vmMem = vmMem;
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
	
	
	
}
