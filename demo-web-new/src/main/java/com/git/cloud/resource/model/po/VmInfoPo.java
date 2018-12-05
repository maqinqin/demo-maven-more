/**
 * @Title:VmInfoPo.java
 * @Package:com.git.cloud.resource.model.po
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:02:53
 * @version V1.0
 */
package com.git.cloud.resource.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:VmInfoPo
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午04:02:53
 *
 *
 */
public class VmInfoPo extends BaseBO {
	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1L;
	private String dataCenter;
	private String poolName;
	private String cdpName;
	private String clusterName;
	private String appSystem;
	private String deployUnit;
	private String vmName;
	private String hostId;
	private String tenantName;
	
	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	private String vmId;
	private String hostName;
	private String platForm;
	private String virtualName;
	private String cpu;
	private String mem;
	private String onlineTimme;
	private String ips;
	private String cStatus;
	
	public String getcStatus() {
		return cStatus;
	}

	public void setcStatus(String cStatus) {
		this.cStatus = cStatus;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	
	public String getDataCenter() {
		return dataCenter;
	}

	public void setDataCenter(String dataCenter) {
		this.dataCenter = dataCenter;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getCdpName() {
		return cdpName;
	}

	public void setCdpName(String cdpName) {
		this.cdpName = cdpName;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getAppSystem() {
		return appSystem;
	}

	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}

	public String getDeployUnit() {
		return deployUnit;
	}

	public void setDeployUnit(String deployUnit) {
		this.deployUnit = deployUnit;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getPlatForm() {
		return platForm;
	}

	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}

	public String getVirtualName() {
		return virtualName;
	}

	public void setVirtualName(String virtualName) {
		this.virtualName = virtualName;
	}

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

	public String getOnlineTimme() {
		return onlineTimme;
	}

	public void setOnlineTimme(String onlineTimme) {
		this.onlineTimme = onlineTimme;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	/* (non-Javadoc)
	 * <p>Title:getBizId</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
