package com.git.cloud.resmgt.openstack.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwVfwRouteVo extends BaseBO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String vfwId;
	private String routeId;
	private String routeName;
	private String projectId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVfwId() {
		return vfwId;
	}
	public void setVfwId(String vfwId) {
		this.vfwId = vfwId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
