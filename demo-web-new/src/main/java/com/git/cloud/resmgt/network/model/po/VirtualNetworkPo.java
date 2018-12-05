package com.git.cloud.resmgt.network.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class VirtualNetworkPo extends BaseBO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String networkName;
	private String datacenterId; 
	private String platformId; 
	private String virtualTypeId; 
	private String vmMsId; 
	private String projectId; 
	private String vrouterId; 
	private String physicalNetwork; 
	private String secureAreaId; 
	private String vlanId; 
	private String isActive; 
	private String useId; 
	private String isEnable; 
	private String networkType;
	private String isDirectConnect;
	private String hostTypeId;
	private String iaasUuid;
	
	
	public String getHostTypeId() {
		return hostTypeId;
	}

	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}

	public String getIsDirectConnect() {
		return isDirectConnect;
	}

	public void setIsDirectConnect(String isDirectConnect) {
		this.isDirectConnect = isDirectConnect;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getVirtualTypeId() {
		return virtualTypeId;
	}

	public void setVirtualTypeId(String virtualTypeId) {
		this.virtualTypeId = virtualTypeId;
	}

	public String getVmMsId() {
		return vmMsId;
	}

	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getVrouterId() {
		return vrouterId;
	}

	public void setVrouterId(String vrouterId) {
		this.vrouterId = vrouterId;
	}

	public String getPhysicalNetwork() {
		return physicalNetwork;
	}

	public void setPhysicalNetwork(String physicalNetwork) {
		this.physicalNetwork = physicalNetwork;
	}

	public String getSecureAreaId() {
		return secureAreaId;
	}

	public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
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

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public VirtualNetworkPo() {
		// TODO Auto-generated constructor stub
	}

	public String getIaasUuid() {
		return iaasUuid;
	}

	public void setIaasUuid(String iaasUuid) {
		this.iaasUuid = iaasUuid;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
