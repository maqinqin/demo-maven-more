package com.git.cloud.resmgt.openstack.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class SecurityGroupsRulesVo extends BaseBO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*主键ID*/
	private String id;
	private String securityGroupsId;
	private String isActive;
	private String remark;
	private String projectId;
	private String direction;
	private String etherType;
	private String protocol;		//IP 协议类型
	private String portRangeMax ;	//安全组规则可匹配的最大端口
	private String portRangeMin;	//安全组规则可匹配的最小端口
	private String remoteIpPrefix;	//对端IP地址段
	private String remoteGroupId;	//对端安全组Id
	
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getEtherType() {
		return etherType;
	}
	public void setEtherType(String etherType) {
		this.etherType = etherType;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSecurityGroupsId() {
		return securityGroupsId;
	}
	public void setSecurityGroupsId(String securityGroupsId) {
		this.securityGroupsId = securityGroupsId;
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
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getPortRangeMax() {
		return portRangeMax;
	}
	public void setPortRangeMax(String portRangeMax) {
		this.portRangeMax = portRangeMax;
	}
	public String getPortRangeMin() {
		return portRangeMin;
	}
	public void setPortRangeMin(String portRangeMin) {
		this.portRangeMin = portRangeMin;
	}
	public String getRemoteIpPrefix() {
		return remoteIpPrefix;
	}
	public void setRemoteIpPrefix(String remoteIpPrefix) {
		this.remoteIpPrefix = remoteIpPrefix;
	}
	public String getRemoteGroupId() {
		return remoteGroupId;
	}
	public void setRemoteGroupId(String remoteGroupId) {
		this.remoteGroupId = remoteGroupId;
	}
}
