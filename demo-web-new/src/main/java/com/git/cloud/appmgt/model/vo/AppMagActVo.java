package com.git.cloud.appmgt.model.vo;

import com.git.cloud.common.model.base.BaseBO;

import java.util.Date;

public class AppMagActVo extends BaseBO {
	
	private String systemNum;		//系统数
	private String deplUnitNum;	    //服务器角色数
	private String vmNum;		   //虚拟机数
	private String cloudServiceNum;//云服务数
	private String cpuNum;		   //CPU数
	private String memNum;		   //内存数
	private Date createDateTime;
	private String cvmConfig;
	
	private String typeMark;
	private String deviceId;
	private String diskNum;
	
	public AppMagActVo(String systemNum, String deplUnitNum, String vmNum,
			String cloudServiceNum,String cpuNum,String memNum,Date createDateTime,String cvmConfig) {
		
		super();
		this.systemNum = systemNum;
		this.deplUnitNum = deplUnitNum;
		this.vmNum = vmNum;
		this.cloudServiceNum = cloudServiceNum;
		this.cpuNum = cpuNum;
		this.memNum = memNum;
		this.createDateTime = createDateTime;
		this.cvmConfig = cvmConfig;
	}
	
	public String getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(String systemNum) {
		this.systemNum = systemNum;
	}
	public String getDeplUnitNum() {
		return deplUnitNum;
	}
	public void setDeplUnitNum(String deplUnitNum) {
		this.deplUnitNum = deplUnitNum;
	}
	public String getVmNum() {
		return vmNum;
	}
	public void setVmNum(String vmNum) {
		this.vmNum = vmNum;
	}
	public String getCloudServiceNum() {
		return cloudServiceNum;
	}
	public void setCloudServiceNum(String cloudServiceNum) {
		this.cloudServiceNum = cloudServiceNum;
	}
	public String getCpuNum() {
		return cpuNum;
	}
	public void setCpuNum(String cpuNum) {
		this.cpuNum = cpuNum;
	}
	public String getMemNum() {
		return memNum;
	}
	public void setMemNum(String memNum) {
		this.memNum = memNum;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getCvmConfig() {
		return cvmConfig;
	}
	public void setCvmConfig(String cvmConfig) {
		this.cvmConfig = cvmConfig;
	}
	
	public String getTypeMark() {
		return typeMark;
	}

	public void setTypeMark(String typeMark) {
		this.typeMark = typeMark;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDiskNum() {
		return diskNum;
	}

	public void setDiskNum(String diskNum) {
		this.diskNum = diskNum;
	}

	public AppMagActVo(){}

	@Override
	public String getBizId() {
		return null;
	}
}