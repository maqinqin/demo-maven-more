package com.git.cloud.resmgt.common.model.vo;

import com.git.cloud.common.model.base.BaseBO;
/**
 * 
 * @author 王成辉
 * @Description 物理机表
 * @date 2014-12-17
 *
 */
public class CmHostVo extends BaseBO implements java.io.Serializable{
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1275281277058176675L;
	private String id;
	private String cpu;
	private String mem;
	private String disk;
	private String cpuUsed;
	private String memUsed;
	private String clusterId;
	private String isInvc ;
	private String isBare ;
	private String cmHostName;
	private String clusterName;
	private String resPoolName;
	private String hostCpuUsed;
	private String hostMemUsed;
	private String remainingCpu;
	private String remainingMem;
	private String bmSrVmNum;
	private String vmNum;
	private String resPoolId;
	private String cdpId;
	private String hostIp;
	
	// Constructors

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getBmSrVmNum() {
		return bmSrVmNum;
	}

	public void setBmSrVmNum(String bmSrVmNum) {
		this.bmSrVmNum = bmSrVmNum;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getCdpId() {
		return cdpId;
	}

	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}

	public String getVmNum() {
		return vmNum;
	}

	public void setVmNum(String vmNum) {
		this.vmNum = vmNum;
	}

	public String getCmHostName() {
		return cmHostName;
	}
	private String ipmiPwd;
	private String ipmiUser;
	private String ipmiUrl;
	private String ipmiVer;

	public void setCmHostName(String cmHostName) {
		this.cmHostName = cmHostName;
	}

	/** default constructor */
	public CmHostVo() {
	}

	/** minimal constructor */
	public CmHostVo(String id) {
		this.id = id;
	}

	/** full constructor */
	public CmHostVo(String id, String cpu, String mem,
			String disk, String cpuUsed, String memUsed, String clusterId,
			String duId,String isInvc,String isBare,String ipmiPwd,
			String ipmiUser,String ipmiUrl,String ipmiVer) {
		this.id = id;
		this.cpu = cpu;
		this.mem = mem;
		this.disk = disk;
		this.cpuUsed = cpuUsed;
		this.memUsed = memUsed;
		this.clusterId = clusterId;
		this.isBare = isBare ;
		this.isInvc = isInvc ;
		this.ipmiPwd= ipmiPwd;
		this.ipmiUser =ipmiUser;
		this.ipmiUrl=ipmiUrl;
		this.ipmiVer=ipmiVer;
		
	}

	// Property accessors

	public String getIpmiVer() {
		return ipmiVer;
	}

	public void setIpmiVer(String ipmiVer) {
		this.ipmiVer = ipmiVer;
	}

	public String getIpmiPwd() {
		return ipmiPwd;
	}

	public void setIpmiPwd(String ipmiPwd) {
		this.ipmiPwd = ipmiPwd;
	}

	public String getIpmiUser() {
		return ipmiUser;
	}

	public void setIpmiUser(String ipmiUser) {
		this.ipmiUser = ipmiUser;
	}

	public String getIpmiUrl() {
		return ipmiUrl;
	}

	public void setIpmiUrl(String ipmiUrl) {
		this.ipmiUrl = ipmiUrl;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
   
	
	
//	public Integer getCpu() {
//		return this.cpu;
//	}
//
//	public void setCpu(Integer cpu) {
//		this.cpu = cpu;
//	}
//
//	public Integer getMem() {
//		return this.mem;
//	}
//
//	public void setMem(Integer mem) {
//		this.mem = mem;
//	}
//
//	public Integer getDisk() {
//		return this.disk;
//	}
//
//	public void setDisk(Integer disk) {
//		this.disk = disk;
//	}
//
//	public Integer getCpuUsed() {
//		return this.cpuUsed;
//	}
//
//	public void setCpuUsed(Integer cpuUsed) {
//		this.cpuUsed = cpuUsed;
//	}
//
//	public Integer getMemUsed() {
//		return this.memUsed;
//	}
//
//	public void setMemUsed(Integer memUsed) {
//		this.memUsed = memUsed;
//	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getCpuUsed() {
		return cpuUsed;
	}

	public void setCpuUsed(String cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	public String getMemUsed() {
		return memUsed;
	}

	public void setMemUsed(String memUsed) {
		this.memUsed = memUsed;
	}

	public String getClusterId() {
		return this.clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIsBare() {
		return isBare;
	}

	public void setIsBare(String isBare) {
		this.isBare = isBare;
	}

	public String getIsInvc() {
		return isInvc;
	}

	public void setIsInvc(String isInvc) {
		this.isInvc = isInvc;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getResPoolName() {
		return resPoolName;
	}

	public void setResPoolName(String resPoolName) {
		this.resPoolName = resPoolName;
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


}
