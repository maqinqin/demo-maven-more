package com.git.cloud.resmgt.compute.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class RmVolumeTypePo extends BaseBO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String vmMsId;
	private String volumeType;
	private String openstackId;
	private String backStorge;
	
	
	public String getBackStorge() {
		return backStorge;
	}

	public void setBackStorge(String backStorge) {
		this.backStorge = backStorge;
	}

	public String getOpenstackId() {
		return openstackId;
	}

	public void setOpenstackId(String openstackId) {
		this.openstackId = openstackId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVmMsId() {
		return vmMsId;
	}

	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
}
