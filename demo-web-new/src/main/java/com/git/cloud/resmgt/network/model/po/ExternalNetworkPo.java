package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class ExternalNetworkPo extends BaseBO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	               
	private String externalNetworkId;
	private String externalNetworkName;
	private String networkType;
	private String physicalNetwork;
	private String vlanId;
	private String isActive;
	
	private String datacenterId;
	private String vmMsId;
	
	private String remark;
	
	public String getExternalNetworkName() {
		return externalNetworkName;
	}
	public void setExternalNetworkName(String externalNetworkName) {
		this.externalNetworkName = externalNetworkName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExternalNetworkId() {
		return externalNetworkId;
	}
	public void setExternalNetworkId(String externalNetworkId) {
		this.externalNetworkId = externalNetworkId;
	}

	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getPhysicalNetwork() {
		return physicalNetwork;
	}
	public void setPhysicalNetwork(String physicalNetwork) {
		this.physicalNetwork = physicalNetwork;
	}
	public String getVlanId() {
		return vlanId;
	}
	public void setVlanId(String vlanId) {
		this.vlanId = vlanId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}
	public String getVmMsId() {
		return vmMsId;
	}
	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
