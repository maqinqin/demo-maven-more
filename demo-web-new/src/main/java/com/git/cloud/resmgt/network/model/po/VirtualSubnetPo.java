package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class VirtualSubnetPo extends BaseBO implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	private String virtualSubnetId;
	private String virtualNetworkId;
	private String virtualSubnetName;
	private String ipVersion;
	private String allocationPools;
	private String subnetMask;
	private String gateway;
	private String isActive;
	private String remark;
	private String startIp;
	private String endIp;
	private String iaasUuid;

    public String getIaasUuid() {
         return iaasUuid;
    }
    public void setIaasUuid(String iaasUuid) {
         this.iaasUuid = iaasUuid;
    }

	public String getStartIp() {
		return startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}



	public String getEndIp() {
		return endIp;
	}



	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getVirtualSubnetId() {
		return virtualSubnetId;
	}
	public void setVirtualSubnetId(String virtualSubnetId) {
		this.virtualSubnetId = virtualSubnetId;
	}
	public String getVirtualNetworkId() {
		return virtualNetworkId;
	}
	public void setVirtualNetworkId(String virtualNetworkId) {
		this.virtualNetworkId = virtualNetworkId;
	}
	public String getVirtualSubnetName() {
		return virtualSubnetName;
	}
	public void setVirtualSubnetName(String virtualSubnetName) {
		this.virtualSubnetName = virtualSubnetName;
	}
	public String getIpVersion() {
		return ipVersion;
	}



	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}



	public String getAllocationPools() {
		return allocationPools;
	}



	public void setAllocationPools(String allocationPools) {
		this.allocationPools = allocationPools;
	}



	public String getSubnetMask() {
		return subnetMask;
	}



	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}



	public String getGateway() {
		return gateway;
	}



	public void setGateway(String gateway) {
		this.gateway = gateway;
	}



	public String getIsActive() {
		return isActive;
	}



	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}



	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
