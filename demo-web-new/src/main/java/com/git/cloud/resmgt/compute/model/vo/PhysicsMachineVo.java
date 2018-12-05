package com.git.cloud.resmgt.compute.model.vo;

import java.io.Serializable;
import com.git.cloud.common.model.base.BaseBO;

public class PhysicsMachineVo extends BaseBO implements Serializable  {

	private static final long serialVersionUID = 1L;
	//名称
	private String pname;
	//CPU数量
	private String cpu;
	//内存数量
	private String ram;

	//已使用CPU
	private String cpuUsed;
	
	//已使用内存
	private String ramUsed;
	
	//IP地址
	private String ip;
	
	//纳管
	private String isNano;
	
	//数据中心ID
	private String did;
	
	//资源池ID
	private String rid;
	
	//虚拟机ID
	private String vid;
	
	//物理机ID
	private String devceId;
	
	//物理机名称
	private String deviceName;
	
	//虚拟机数量
	private String cmvmCount;
	
	//剩余CPU
	private String remainingCpu;
	
	//剩余MEM
	private String remainingMem;
	
	//已用CPU
	private String cmCpuUsed;
	
	//已用MEM
	private String cmMemUsed;
	
	//dataStoreId
	private String dataStoreId;
	
	private String dataStoreName;
	
	private String virtTypeCode;
	
	public String getVirtTypeCode() {
		return virtTypeCode;
	}
	public void setVirtTypeCode(String virtTypeCode) {
		this.virtTypeCode = virtTypeCode;
	}
	public String getDataStoreName() {
		return dataStoreName;
	}
	public void setDataStoreName(String dataStoreName) {
		this.dataStoreName = dataStoreName;
	}
	private String cdpId;
	
	
	public String getCdpId() {
		return cdpId;
	}
	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIsNano() {
		return isNano;
	}
	public void setIsNano(String isNano) {
		this.isNano = isNano;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
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
	public String getDevceId() {
		return devceId;
	}
	public void setDevceId(String devceId) {
		this.devceId = devceId;
	}
	public PhysicsMachineVo() {
		super();
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getCmvmCount() {
		return cmvmCount;
	}
	public void setCmvmCount(String cmvmCount) {
		this.cmvmCount = cmvmCount;
	}
	
	
	public String getRemainingCpu() {
		return remainingCpu;
	}
	public void setRemainingCpu(String remainingCpu) {
		this.remainingCpu = remainingCpu;
	}
	public String getRemainingMem() {
		return remainingMem;
	}
	public void setRemainingMem(String remainingMem) {
		this.remainingMem = remainingMem;
	}
	public String getCmCpuUsed() {
		return cmCpuUsed;
	}
	public void setCmCpuUsed(String cmCpuUsed) {
		this.cmCpuUsed = cmCpuUsed;
	}
	public String getCmMemUsed() {
		return cmMemUsed;
	}
	public void setCmMemUsed(String cmMemUsed) {
		this.cmMemUsed = cmMemUsed;
	}
	public String getDataStoreId() {
		return dataStoreId;
	}
	public void setDataStoreId(String dataStoreId) {
		this.dataStoreId = dataStoreId;
	}
	@Override
	public String getBizId() {
		return null;
	}
	public PhysicsMachineVo(String pname, String cpu, String ram,String cpuUsed, String ramUsed,
			String ip, String isNano,String devceId,String cmCpuUsed,String cmMemUsed,String did, String rid,
			String vid,String deviceName,String cmvmCount,String remainingCpu,String remainingMem,String dataStoreId) {
		super();
		this.pname = pname;
		this.cpu = cpu;
		this.ram = ram;
		this.cpuUsed = cpuUsed;
		this.ramUsed = ramUsed;
		this.ip = ip;
		this.isNano = isNano;
		this.did = did;
		this.rid = rid;
		this.vid = vid;
		this.devceId = devceId;
		this.deviceName = deviceName;
		this.cmvmCount = cmvmCount;
		this.remainingCpu = remainingCpu;
		this.remainingMem = remainingMem;
		this.cmCpuUsed = cmCpuUsed;
		this.remainingMem = remainingMem;
		this.dataStoreId = dataStoreId;
	}
}