package com.git.cloud.resmgt.compute.model.po;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;


public class AvailableZonePo extends BaseBO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String availableZoneId;
	private String availableZoneName;
	private String isActive;
	private String remark;
	private String vmServerId;  //虚拟服务器id
	private String serverName;
	private String dataCenterId;
	
/*	private String serverName; //虚拟服务器名称
*/	//封装rm_vm_manage_server表   虚拟管理服务器 
	private RmVmManageServerPo rmVmManageServerPo;
	
	//private int vmServerId;
	
	
	public RmVmManageServerPo getRmVmManageServerPo() {
		return rmVmManageServerPo;
	}

	public String getVmServerId() {
		return vmServerId;
	}
	public void setVmServerId(String vmServerId) {
		this.vmServerId = vmServerId;
	}
	public void setRmVmManageServerPo(RmVmManageServerPo rmVmManageServerPo) {
		this.rmVmManageServerPo = rmVmManageServerPo;
	}


	public String getAvailableZoneId() {
		return availableZoneId;
	}
	public void setAvailableZoneId(String availableZoneId) {
		this.availableZoneId = availableZoneId;
	}
	public String getAvailableZoneName() {
		return availableZoneName;
	}
	public void setAvailableZoneName(String availableZoneName) {
		this.availableZoneName = availableZoneName;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	
	
	
}
