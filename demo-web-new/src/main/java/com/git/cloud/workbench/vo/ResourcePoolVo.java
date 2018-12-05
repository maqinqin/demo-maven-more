package com.git.cloud.workbench.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class ResourcePoolVo extends BaseBO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pname;
	
	private String cpu;
	
	private String ram;
	
	private long did;

	private String cpuUsed;
	
	private String ramUsed;
	
	private String pid;
	
	private String dname;
	
	private String vcpu;
	
	private String vram;

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




	public long getDid() {
		return did;
	}

	public void setDid(long did) {
		this.did = did;
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










	public ResourcePoolVo(String pname, String cpu, String ram, long did,
			String cpuUsed, String ramUsed, String pid, String dname,
			String vcpu, String vram) {
		super();
		this.pname = pname;
		this.cpu = cpu;
		this.ram = ram;
		this.did = did;
		this.cpuUsed = cpuUsed;
		this.ramUsed = ramUsed;
		this.pid = pid;
		this.dname = dname;
		this.vcpu = vcpu;
		this.vram = vram;
	}

	public ResourcePoolVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
