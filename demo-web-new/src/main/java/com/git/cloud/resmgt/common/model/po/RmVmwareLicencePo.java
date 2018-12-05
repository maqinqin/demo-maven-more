package com.git.cloud.resmgt.common.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmVmwareLicencePo  extends BaseBO implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	private String id;
	private String license;
	private int cpuAllNum;
	private int cpuAvaNum;
	private String isActive;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public int getCpuAllNum() {
		return cpuAllNum;
	}
	public void setCpuAllNum(int cpuAllNum) {
		this.cpuAllNum = cpuAllNum;
	}
	public int getCpuAvaNum() {
		return cpuAvaNum;
	}
	public void setCpuAvaNum(int cpuAvaNum) {
		this.cpuAvaNum = cpuAvaNum;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
}
