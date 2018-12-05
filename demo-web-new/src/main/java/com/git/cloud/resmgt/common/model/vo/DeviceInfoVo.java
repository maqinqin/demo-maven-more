package com.git.cloud.resmgt.common.model.vo;

public class DeviceInfoVo {
	private String cdpName;
	private String clusterName;
	private String deviceId;	//物理机ID
	private String deviceName;	//物理机
	private int usedCpu;		//已分配CPU
	private int usedMem;		//已分配内存
	private int cpu;			//CPU
	private int mem;			//内存
	private Long yfpVm;			//已分配虚拟机
	
	public DeviceInfoVo() {
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public int getUsedCpu() {
		return usedCpu;
	}
	public void setUsedCpu(int usedCpu) {
		this.usedCpu = usedCpu;
	}
	public int getUsedMem() {
		return usedMem;
	}
	public void setUsedMem(int usedMem) {
		this.usedMem = usedMem;
	}
	public int getCpu() {
		return cpu;
	}
	public void setCpu(int cpu) {
		this.cpu = cpu;
	}
	public int getMem() {
		return mem;
	}
	public void setMem(int mem) {
		this.mem = mem;
	}
	public Long getYfpVm() {
		return yfpVm;
	}
	public void setYfpVm(Long yfpVm) {
		this.yfpVm = yfpVm;
	}
	public String getCdpName() {
		return cdpName;
	}
	public void setCdpName(String cdpName) {
		this.cdpName = cdpName;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
}
