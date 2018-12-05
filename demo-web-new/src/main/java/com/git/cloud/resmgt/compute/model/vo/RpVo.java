package com.git.cloud.resmgt.compute.model.vo;

import java.io.Serializable;
import com.git.cloud.common.model.base.BaseBO;

public class RpVo extends BaseBO implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private String pname;
	private String cpu;
	private String ram;
	private String rmdatacenterId;
	private String cpuUsed;
	private String ramUsed;
	private String cId;
	private String vId;
	private String pid;
	private String dname;
	private String vcpu;
	private String vram;
	private String rmResPoolCount;
	private String rmCdpCount;
	private String rmRmClusterCount;
	private String cmDeviceCount;
	private String rmVmCount;
	private String vchvmType;
	private String vcmPoolType;
	private String dataDeTreType;
	
	public String getCpuUsed() {
		return cpuUsed;
	}
	public void setCpuUsed(String cpuUsed) {
		this.cpuUsed = cpuUsed;
	}
	public String getRamUsed() {
		return ramUsed;
	}
	public void setRamUsed(String ramUsed) {
		this.ramUsed = ramUsed;
	}
	public String getRmdatacenterId() {
		return rmdatacenterId;
	}
	public void setRmdatacenterId(String rmdatacenterId) {
		this.rmdatacenterId = rmdatacenterId;
	}
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getvId() {
		return vId;
	}
	public void setvId(String vId) {
		this.vId = vId;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getVcpu() {
		return vcpu;
	}
	public void setVcpu(String vcpu) {
		this.vcpu = vcpu;
	}
	public String getVram() {
		return vram;
	}
	public void setVram(String vram) {
		this.vram = vram;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getRam() {
		return ram;
	}
	public void setRam(String ram) {
		this.ram = ram;
	}
	public String getRmResPoolCount() {
		return rmResPoolCount;
	}
	public void setRmResPoolCount(String rmResPoolCount) {
		this.rmResPoolCount = rmResPoolCount;
	}
	public String getRmCdpCount() {
		return rmCdpCount;
	}
	public void setRmCdpCount(String rmCdpCount) {
		this.rmCdpCount = rmCdpCount;
	}
	public String getRmRmClusterCount() {
		return rmRmClusterCount;
	}
	public void setRmRmClusterCount(String rmRmClusterCount) {
		this.rmRmClusterCount = rmRmClusterCount;
	}
	public String getCmDeviceCount() {
		return cmDeviceCount;
	}
	public void setCmDeviceCount(String cmDeviceCount) {
		this.cmDeviceCount = cmDeviceCount;
	}
	public String getRmVmCount() {
		return rmVmCount;
	}
	public void setRmVmCount(String rmVmCount) {
		this.rmVmCount = rmVmCount;
	}
	public String getVchvmType() {
		return vchvmType;
	}
	public void setVchvmType(String vchvmType) {
		this.vchvmType = vchvmType;
	}
	
	public String getVcmPoolType() {
		return vcmPoolType;
	}
	
	public void setVcmPoolType(String vcmPoolType) {
		this.vcmPoolType = vcmPoolType;
	}
	
	public String getDataDeTreType() {
		return dataDeTreType;
	}
	
	public void setDataDeTreType(String dataDeTreType) {
		this.dataDeTreType = dataDeTreType;
	}
	
	public RpVo(String pname, String cpu, String ram, String rmdatacenterId,
			String cpuUsed, String ramUsed, String pid, String dname,String cId,
			String vId,String vcpu, String vram,String rmResPoolCount,String rmCdpCount,
			String rmRmClusterCount,String cmDeviceCount,String rmVmCount,
			String cvmCpu,String cvmMem,String vchvmType,String dataDeTreType,
			String vcmPoolType) {
		super();
		this.pname = pname;
		this.cpu = cpu;
		this.ram = ram;
		this.rmdatacenterId = rmdatacenterId;
		this.cpuUsed = cpuUsed;
		this.ramUsed = ramUsed;
		this.cId = cId;
		this.vId = vId;
		this.pid = pid;
		this.dname = dname;
		this.vcpu = vcpu;
		this.vram = vram;
		this.rmResPoolCount = rmResPoolCount;
		this.rmCdpCount = rmCdpCount;
		this.rmRmClusterCount = rmRmClusterCount;
		this.cmDeviceCount = cmDeviceCount;
		this.rmVmCount = rmVmCount;
		this.vchvmType = vchvmType;
		this.vcmPoolType = vcmPoolType;
		this.dataDeTreType = dataDeTreType;
	}
	public RpVo() {
		super();
	}
	@Override
	public String getBizId() {
		return null;
	}
}