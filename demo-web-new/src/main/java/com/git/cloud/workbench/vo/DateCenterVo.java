package com.git.cloud.workbench.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class DateCenterVo extends BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dname;
	
	private String cpu;
	
	private String ram;
	
	private String id;

	private String cpuUsed;
	
	private String ramUsed;


	

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

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
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





	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DateCenterVo() {
		super();
	}


	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public DateCenterVo(String dname, String cpu, String ram, String id,
			String cpuUsed, String ramUsed) {
		super();
		this.dname = dname;
		this.cpu = cpu;
		this.ram = ram;
		this.id = id;
		this.cpuUsed = cpuUsed;
		this.ramUsed = ramUsed;
	}



	
}
