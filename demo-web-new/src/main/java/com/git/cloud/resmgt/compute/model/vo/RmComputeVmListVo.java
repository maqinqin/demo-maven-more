package com.git.cloud.resmgt.compute.model.vo;

import java.io.Serializable;
import com.git.cloud.common.model.base.BaseBO;

public class RmComputeVmListVo extends BaseBO implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	//名称
	private String pname;
	//CPU
	private String cpu;
	//内存
	private String ram;
	//已用CPU
	private String cpuUsed;
	//已用内存
	private String ramUsed;
	//IP
	private String ip;
	//vmname
	private String vmName;
	//磁盘空间
	private String diskSpace;
	//数据中心ID
	private String did;
	//资源池ID
	private String rid;
	//虚拟机ID
	private String vid;
	//物理机ID
	private String cid;
	//服务器角色
	private String apGroup;
	//云服务名称
	private String cloudServiceName;
	//所属应用系统
	private String cname;
	//名称：VmName+Ip
	private String ipName;
	//类型
	private String vmType;
	//虚拟化类型编码
	private String vmTypeCode;
	//类型
	private String rmRedPoolType;
	//类型
	private String dataDeTreType;
	//状态：构建状态
	private String deviceStatus;
	//运行状态
	private String runningState;
	
	private String platFormCode;
	
	//登录姓
	private String firstName;
	
	//登录名
	private String lastName;
	
	//登录账号
	private String loginName;
	
	//所属租户
	private String tenantName;
	
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getRunningState() {
		return runningState;
	}

	public void setRunningState(String runningState) {
		this.runningState = runningState;
	}
	
	public RmComputeVmListVo(String pname, String cpu, String ram,String vmType,
			String cpuUsed, String ramUsed, String ip, String diskSpace,String rmRedPoolType,
			String did, String rid, String vid,String cid, String apGroup,String dataDeTreType,
			String cloudServiceName,String cname,String vmName,String ipName,String deviceStatus) {
		
		super();
		
		this.pname = pname;
		this.cpu = cpu;
		this.ram = ram;
		this.cpuUsed = cpuUsed;
		this.ramUsed = ramUsed;
		this.ip = ip;
		this.diskSpace = diskSpace;
		this.did = did;
		this.rid = rid;
		this.cid = cid;
		this.vid = vid;
		this.apGroup = apGroup;
		this.cloudServiceName = cloudServiceName;
		this.cname = cname;
		this.vmName=vmName;
		this.ipName=ipName;
		this.vmType=vmType;
		this.rmRedPoolType=rmRedPoolType;
		this.dataDeTreType=dataDeTreType;
		this.deviceStatus=deviceStatus;
	}
	
	
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getIpName() {
		return ipName;
	}
	public void setIpName(String ipName) {
		this.ipName = ipName;
	}
	public String getCloudServiceName() {
		return cloudServiceName;
	}
	public void setCloudServiceName(String cloudServiceName) {
		this.cloudServiceName = cloudServiceName;
	}
	public String getDiskSpace() {
		return diskSpace;
	}
	public void setDiskSpace(String diskSpace) {
		this.diskSpace = diskSpace;
	}
	public String getApGroup() {
		return apGroup;
	}
	public void setApGroup(String apGroup) {
		this.apGroup = apGroup;
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
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
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
	public String getVmType() {
		return vmType;
	}
	public void setVmType(String vmType) {
		this.vmType = vmType;
	}
	public String getRmRedPoolType() {
		return rmRedPoolType;
	}
	public void setRmRedPoolType(String rmRedPoolType) {
		this.rmRedPoolType = rmRedPoolType;
	}
	public String getDataDeTreType() {
		return dataDeTreType;
	}
	public void setDataDeTreType(String dataDeTreType) {
		this.dataDeTreType = dataDeTreType;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public RmComputeVmListVo() {
		super();
	}
	
	@Override
	public String getBizId() {
		return null;
	}

	public String getVmTypeCode() {
		return vmTypeCode;
	}

	public void setVmTypeCode(String vmTypeCode) {
		this.vmTypeCode = vmTypeCode;
	}

	public String getPlatFormCode() {
		return platFormCode;
	}

	public void setPlatFormCode(String platFormCode) {
		this.platFormCode = platFormCode;
	}
}