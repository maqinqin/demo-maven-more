package com.git.cloud.resmgt.common.model.vo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 
 * @author WangJingxin
 * @date 2016年5月6日 上午10:13:05
 * 
 */
public class SyncVmInfoVo extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vmName;
	private String hostName;
	private String powerState;
	private String ips;
	private String datastore;
	private String vcenter;
	private int cpu;
	private int mem;

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPowerState() {
		return powerState;
	}

	public void setPowerState(String powerState) {
		this.powerState = powerState;
	}
	

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public String getDatastore() {
		return datastore;
	}

	public void setDatastore(String datastore) {
		this.datastore = datastore;
	}

	public String getVcenter() {
		return vcenter;
	}

	public void setVcenter(String vcenter) {
		this.vcenter = vcenter;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}

	@Override
	public String getBizId() {
		return null;
	}

}
