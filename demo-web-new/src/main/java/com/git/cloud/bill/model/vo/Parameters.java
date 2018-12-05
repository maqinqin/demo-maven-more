
package com.git.cloud.bill.model.vo;

/**
计费参数
 */
public class Parameters {

	Integer	cpu;
	Integer	mem;
	Integer	disk;
	Integer	floatIp;
	String serType;		//传递云平台的云服务id
	String diskType;	//约定好传：git_disk
	String floatIpType;	//约定好传：git_floatIp
	Integer DiskNumber;
	
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	public Integer getMem() {
		return mem;
	}
	public void setMem(Integer mem) {
		this.mem = mem;
	}
	public Integer getDisk() {
		return disk;
	}
	public void setDisk(Integer disk) {
		this.disk = disk;
	}
	public Integer getFloatIp() {
		return floatIp;
	}
	public void setFloatIp(Integer floatIp) {
		this.floatIp = floatIp;
	}
	public String getSerType() {
		return serType;
	}
	public void setSerType(String serType) {
		this.serType = serType;
	}
	public String getDiskType() {
		return diskType;
	}
	public void setDiskType(String diskType) {
		this.diskType = diskType;
	}
	public String getFloatIpType() {
		return floatIpType;
	}
	public void setFloatIpType(String floatIpType) {
		this.floatIpType = floatIpType;
	}
	public Integer getDiskNumber() {
		return DiskNumber;
	}
	public void setDiskNumber(Integer diskNumber) {
		DiskNumber = diskNumber;
	}
	@Override
	public String toString() {
		return "Parameters [cpu=" + cpu + ", mem=" + mem + ", disk=" + disk + ", floatIp=" + floatIp + ", serType="
				+ serType + ", diskType=" + diskType + ", floatIpType=" + floatIpType + ", DiskNumber=" + DiskNumber
				+ "]";
	}
	
}