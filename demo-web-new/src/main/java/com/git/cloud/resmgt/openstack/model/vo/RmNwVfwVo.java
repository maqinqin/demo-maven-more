package com.git.cloud.resmgt.openstack.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwVfwVo extends BaseBO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vfwId;			/*虚拟防火墙ID*/
	private String vfwPolicyId;		/*虚拟防火墙策略ID*/
	private String vfwPolicyName;	/*虚拟防火墙策略名称*/
	private String projectId;		/*所属projectID*/
	private String vfwName;			/*虚拟防火墙名称*/
	private String remark;
	private String isActive;
	private String routeId;			/*路由id*/ 
	public String getVfwId() {
		return vfwId;
	}
	public void setVfwId(String vfwId) {
		this.vfwId = vfwId;
	}
	public String getVfwPolicyId() {
		return vfwPolicyId;
	}
	public void setVfwPolicyId(String vfwPolicyId) {
		this.vfwPolicyId = vfwPolicyId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getVfwName() {
		return vfwName;
	}
	public void setVfwName(String vfwName) {
		this.vfwName = vfwName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getVfwPolicyName() {
		return vfwPolicyName;
	}
	public void setVfwPolicyName(String vfwPolicyName) {
		this.vfwPolicyName = vfwPolicyName;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
}
