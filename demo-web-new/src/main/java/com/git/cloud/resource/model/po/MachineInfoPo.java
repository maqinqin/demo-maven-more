/**
 * @Title:MachineInfo.java
 * @Package:com.git.cloud.resource.model.po
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午03:47:24
 * @version V1.0
 */
package com.git.cloud.resource.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:MachineInfo
 * @Description:TODO
 * @author LINZI
 * @date 2015-1-7 下午03:47:24
 *
 *
 */
public class MachineInfoPo extends BaseBO {
	private String dataCenter;
	private String poolName;
	private String cdpName;
	private String clusterName;
	private String hostName;
	private String sn;
	private String platForm;
	private String virtualName;
	private String cpu;
	private String mem;
	private String controlTime;
	private String ips;
	private String manufacturer;
	private String model;
	private String hostId;
	private String status;
	
	public String getHostId() {
		return hostId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
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

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public String getControlTime() {
		return controlTime;
	}

	public void setControlTime(String controlTime) {
		this.controlTime = controlTime;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1L;

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
