package com.git.cloud.resmgt.openstack.model.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class VlbPoolVo  extends BaseBO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String poolName;
	private String vmMsId;
	private String serverName;
	private String projectId;
	private String vpcName;
	private String subnetId;
	private String netName;
	private String protocol;
	private String tenantId;
	private String lbMethod;
	private String netType;
	private String remark;
	private String listenerId;
	
	
	// vip
	private String vipId;
	private String vipName;
	private String vipSubnet;		//子网
	private String vipProject;
	private String vipPool;
	private String vipIsact;
	private String vipAddress;
	private String vipRemark;
	private String isExternal;
	private String protocolVip;
	private String protocolVipPort;
	private String netWorkId;		//外网
	
	// member
	private String memberId;
	private String memberIp;
	private String deviceId;
	private String deviceName;
	private String memberPoolId;
	private String memberDevice;
	private String memberProtocolPort;
	private String memberWeight;
	
	// 健康检查
	private String healthId;
	private String healthProjectId;
	private String healthDelay;
	private String healthExpeCodes;
	private String healthTimeOut;
	private String healthType;
	private String healthIsAct;
	private String healthPoolId;
	private String maxRetries;
	private String httpMethod;
	private String urlPath;
	
	public String getListenerId() {
		return listenerId;
	}
	public void setListenerId(String listenerId) {
		this.listenerId = listenerId;
	}
	public String getHealthPoolId() {
		return healthPoolId;
	}
	public void setHealthPoolId(String healthPoolId) {
		this.healthPoolId = healthPoolId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getVmMsId() {
		return vmMsId;
	}
	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}
	public String getSubnetId() {
		return subnetId;
	}
	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}
	public String getProtocol() {
		return protocol;
	}
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getLbMethod() {
		return lbMethod;
	}
	public void setLbMethod(String lbMethod) {
		this.lbMethod = lbMethod;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getVpcName() {
		return vpcName;
	}
	public String getNetType() {
		return netType;
	}
	public void setNetType(String netType) {
		this.netType = netType;
	}
	public void setVpcName(String vpcName) {
		this.vpcName = vpcName;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	// vip
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public String getVipName() {
		return vipName;
	}
	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	public String getVipSubnet() {
		return vipSubnet;
	}
	public void setVipSubnet(String vipSubnet) {
		this.vipSubnet = vipSubnet;
	}
	public String getVipProject() {
		return vipProject;
	}
	public void setVipProject(String vipProject) {
		this.vipProject = vipProject;
	}
	public String getVipPool() {
		return vipPool;
	}
	public void setVipPool(String vipPool) {
		this.vipPool = vipPool;
	}
	public String getVipIsact() {
		return vipIsact;
	}
	public void setVipIsact(String vipIsact) {
		this.vipIsact = vipIsact;
	}
	public String getVipAddress() {
		return vipAddress;
	}
	public void setVipAddress(String vipAddress) {
		this.vipAddress = vipAddress;
	}
	public String getVipRemark() {
		return vipRemark;
	}
	public void setVipRemark(String vipRemark) {
		this.vipRemark = vipRemark;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberIp() {
		return memberIp;
	}
	public void setMemberIp(String memberIp) {
		this.memberIp = memberIp;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getMemberPoolId() {
		return memberPoolId;
	}
	public void setMemberPoolId(String memberPoolId) {
		this.memberPoolId = memberPoolId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getMemberDevice() {
		return memberDevice;
	}
	public void setMemberDevice(String memberDevice) {
		this.memberDevice = memberDevice;
	}
	public String getMemberProtocolPort() {
		return memberProtocolPort;
	}
	public void setMemberProtocolPort(String memberProtocolPort) {
		this.memberProtocolPort = memberProtocolPort;
	}
	public String getMemberWeight() {
		return memberWeight;
	}
	public void setMemberWeight(String memberWeight) {
		this.memberWeight = memberWeight;
	}
	public String getHealthId() {
		return healthId;
	}
	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}
	public String getHealthProjectId() {
		return healthProjectId;
	}
	public void setHealthProjectId(String healthProjectId) {
		this.healthProjectId = healthProjectId;
	}
	public String getHealthDelay() {
		return healthDelay;
	}
	public void setHealthDelay(String healthDelay) {
		this.healthDelay = healthDelay;
	}
	public String getHealthExpeCodes() {
		return healthExpeCodes;
	}
	public void setHealthExpeCodes(String healthExpeCodes) {
		this.healthExpeCodes = healthExpeCodes;
	}
	public String getHealthTimeOut() {
		return healthTimeOut;
	}
	public void setHealthTimeOut(String healthTimeOut) {
		this.healthTimeOut = healthTimeOut;
	}
	public String getHealthType() {
		return healthType;
	}
	public void setHealthType(String healthType) {
		this.healthType = healthType;
	}
	public String getHealthIsAct() {
		return healthIsAct;
	}
	public void setHealthIsAct(String healthIsAct) {
		this.healthIsAct = healthIsAct;
	}
	public String getIsExternal() {
		return isExternal;
	}
	public void setIsExternal(String isExternal) {
		this.isExternal = isExternal;
	}
	public String getProtocolVip() {
		return protocolVip;
	}
	public void setProtocolVip(String protocolVip) {
		this.protocolVip = protocolVip;
	}
	public String getProtocolVipPort() {
		return protocolVipPort;
	}
	public void setProtocolVipPort(String protocolVipPort) {
		this.protocolVipPort = protocolVipPort;
	}
	public String getNetWorkId() {
		return netWorkId;
	}
	public void setNetWorkId(String netWorkId) {
		this.netWorkId = netWorkId;
	}
	public String getMaxRetries() {
		return maxRetries;
	}
	public void setMaxRetries(String maxRetries) {
		this.maxRetries = maxRetries;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

}
