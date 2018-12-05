package com.git.cloud.resmgt.openstack.model.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class VlbListenerVo  extends BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8670991487969651260L;

	private String id;
	private String name;
	private String protocalPort;
	private String protocal;
	private String vlbId;
	private String projectId;
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocalPort() {
		return protocalPort;
	}

	public void setProtocalPort(String protocalPort) {
		this.protocalPort = protocalPort;
	}

	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getVlbId() {
		return vlbId;
	}

	public void setVlbId(String vlbId) {
		this.vlbId = vlbId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
