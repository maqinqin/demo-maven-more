package com.git.cloud.handler.vo;

import com.git.cloud.common.model.base.BaseBO;

/** 
 * 类说明 
 * @author SunHailong 
 * @version 版本号 2017-3-23
 */
public class OpenstackVmParamVo extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String vmName;
	private String userName;
	private String password;
	private String hostName;
	private String serverIp;
	private String floatingIp;
	private String projectId;
	private String projectName;
	private String networkId;
	private String openstackId;
	private String openstackIp;
	private String domainName;
	private String azName;
	/**
	 * 虚拟机组
	 */
	private String vmGroupId;
	// 版本号
	private String version;
	private String manageOneIp;
	// 内部网络子网id
	private String subnetId;
	// 内部网络关联的路由id
	private String routerId;
	// 内部网络的targetId
	private String networkIaasUuid;

	
	public String getNetworkIaasUuid() {
		return networkIaasUuid;
	}
	public void setNetworkIaasUuid(String networkIaasUuid) {
		this.networkIaasUuid = networkIaasUuid;
	}
	public String getSubnetId() {
		return subnetId;
	}
	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}
	public String getRouterId() {
		return routerId;
	}
	public void setRouterId(String routerId) {
		this.routerId = routerId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getManageOneIp() {
		return manageOneIp;
	}
	public void setManageOneIp(String manageOneIp) {
		this.manageOneIp = manageOneIp;
	}
	public String getFloatingIp() {
		return floatingIp;
	}
	public void setFloatingIp(String floatingIp) {
		this.floatingIp = floatingIp;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getOpenstackId() {
		return openstackId;
	}
	public void setOpenstackId(String openstackId) {
		this.openstackId = openstackId;
	}
	public String getOpenstackIp() {
		return openstackIp;
	}
	public void setOpenstackIp(String openstackIp) {
		this.openstackIp = openstackIp;
	}
	public String getAzName() {
		return azName;
	}
	public void setAzName(String azName) {
		this.azName = azName;
	}

	public String getVmGroupId() {
		return vmGroupId;
	}

	public void setVmGroupId(String vmGroupId) {
		this.vmGroupId = vmGroupId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
