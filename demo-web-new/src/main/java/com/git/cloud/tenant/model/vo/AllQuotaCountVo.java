package com.git.cloud.tenant.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class AllQuotaCountVo extends BaseBO{
	private Integer cpu;
	private Integer cpuUsed;
	private Integer mem;
	private Integer memUsed;
	private Integer storage;
	private Integer storageUsed;
	private Integer  floatingIpNum ; // 弹性IP个数
	private Integer  floatingIpNumUsed ; // 已用弹性IP个数
	private Integer  vlbNum ;  		// 虚拟负载均衡个数
	private Integer  vlbNumUsed ;  		// 虚拟负载均衡个数
	private Integer  vpcNum ;  		// VPC个数
	private Integer  vpcNumUsed ;  		// VPC个数
	private Integer  vmNum ;  		// 虚拟机个数
	private Integer  vmNumUsed ;  		// 虚拟机个数
	private Integer  networkNum ; 
	private Integer  networkNumUsed ; 
	private Integer  securityGroupNum ;// 安全组个数
	private Integer  securityGroupNumUsed ;// 安全组个数
	private String platformTypeCode;
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	public Integer getCpuUsed() {
		return cpuUsed;
	}
	public void setCpuUsed(Integer cpuUsed) {
		this.cpuUsed = cpuUsed;
	}
	public Integer getMem() {
		return mem;
	}
	public void setMem(Integer mem) {
		this.mem = mem;
	}
	public Integer getMemUsed() {
		return memUsed;
	}
	public void setMemUsed(Integer memUsed) {
		this.memUsed = memUsed;
	}
	public Integer getStorage() {
		return storage;
	}
	public void setStorage(Integer storage) {
		this.storage = storage;
	}
	public Integer getStorageUsed() {
		return storageUsed;
	}
	public void setStorageUsed(Integer storageUsed) {
		this.storageUsed = storageUsed;
	}
	public Integer getFloatingIpNum() {
		return floatingIpNum;
	}
	public void setFloatingIpNum(Integer floatingIpNum) {
		this.floatingIpNum = floatingIpNum;
	}
	public Integer getFloatingIpNumUsed() {
		return floatingIpNumUsed;
	}
	public void setFloatingIpNumUsed(Integer floatingIpNumUsed) {
		this.floatingIpNumUsed = floatingIpNumUsed;
	}
	public Integer getVlbNum() {
		return vlbNum;
	}
	public void setVlbNum(Integer vlbNum) {
		this.vlbNum = vlbNum;
	}
	public Integer getVlbNumUsed() {
		return vlbNumUsed;
	}
	public void setVlbNumUsed(Integer vlbNumUsed) {
		this.vlbNumUsed = vlbNumUsed;
	}
	public Integer getVpcNum() {
		return vpcNum;
	}
	public void setVpcNum(Integer vpcNum) {
		this.vpcNum = vpcNum;
	}
	public Integer getVpcNumUsed() {
		return vpcNumUsed;
	}
	public void setVpcNumUsed(Integer vpcNumUsed) {
		this.vpcNumUsed = vpcNumUsed;
	}
	public Integer getVmNum() {
		return vmNum;
	}
	public void setVmNum(Integer vmNum) {
		this.vmNum = vmNum;
	}
	public Integer getVmNumUsed() {
		return vmNumUsed;
	}
	public void setVmNumUsed(Integer vmNumUsed) {
		this.vmNumUsed = vmNumUsed;
	}
	public Integer getNetworkNum() {
		return networkNum;
	}
	public void setNetworkNum(Integer networkNum) {
		this.networkNum = networkNum;
	}
	public Integer getNetworkNumUsed() {
		return networkNumUsed;
	}
	public void setNetworkNumUsed(Integer networkNumUsed) {
		this.networkNumUsed = networkNumUsed;
	}
	public Integer getSecurityGroupNum() {
		return securityGroupNum;
	}
	public void setSecurityGroupNum(Integer securityGroupNum) {
		this.securityGroupNum = securityGroupNum;
	}
	public Integer getSecurityGroupNumUsed() {
		return securityGroupNumUsed;
	}
	public void setSecurityGroupNumUsed(Integer securityGroupNumUsed) {
		this.securityGroupNumUsed = securityGroupNumUsed;
	}
	public String getPlatformTypeCode() {
		return platformTypeCode;
	}
	public void setPlatformTypeCode(String platformTypeCode) {
		this.platformTypeCode = platformTypeCode;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
