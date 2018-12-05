package com.git.cloud.resmgt.common.model.vo;

public class ResPoolHostVmInfoVo {
	private String poolId;
	private String poolName;
	private String poolEName;
	private int hcpu;
	private int hmem;
	private int vcpu;
	private int vmem;
	public String getPoolId() {
		return poolId;
	}
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getPoolEName() {
		return poolEName;
	}
	public void setPoolEName(String poolEName) {
		this.poolEName = poolEName;
	}
	public int getHcpu() {
		return hcpu;
	}
	public void setHcpu(int hcpu) {
		this.hcpu = hcpu;
	}
	public int getHmem() {
		return hmem;
	}
	public void setHmem(int hmem) {
		this.hmem = hmem;
	}
	public int getVcpu() {
		return vcpu;
	}
	public void setVcpu(int vcpu) {
		this.vcpu = vcpu;
	}
	public int getVmem() {
		return vmem;
	}
	public void setVmem(int vmem) {
		this.vmem = vmem;
	}
}
