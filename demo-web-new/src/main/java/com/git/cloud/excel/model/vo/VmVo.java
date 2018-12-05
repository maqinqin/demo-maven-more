package com.git.cloud.excel.model.vo;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class VmVo extends BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String dataCenterCode;
	private String clusterCode;
	private String hostName;
	private String vmName;
	private String vmMem;
	private String vmCpu;
	private String duId;
	private String vmIp;
	
	
	
	
	public String getDataCenterCode() {
		return dataCenterCode;
	}




	public void setDataCenterCode(String dataCenterCode) {
		this.dataCenterCode = dataCenterCode;
	}




	public String getClusterCode() {
		return clusterCode;
	}




	public void setClusterCode(String clusterCode) {
		this.clusterCode = clusterCode;
	}




	public String getHostName() {
		return hostName;
	}




	public void setHostName(String hostName) {
		this.hostName = hostName;
	}








	public String getVmName() {
		return vmName;
	}




	public void setVmName(String vmName) {
		this.vmName = vmName;
	}




	public String getVmMem() {
		return vmMem;
	}




	public void setVmMem(String vmMem) {
		this.vmMem = vmMem;
	}




	public String getVmCpu() {
		return vmCpu;
	}




	public void setVmCpu(String vmCpu) {
		this.vmCpu = vmCpu;
	}




	public String getDuId() {
		return duId;
	}




	public void setDuId(String duId) {
		this.duId = duId;
	}




	public String getVmIp() {
		return vmIp;
	}




	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public VmVo() {
		super();
		// TODO Auto-generated constructor stub
	}




	public VmVo(String id, String dataCenterCode, String clusterCode,
			String hostName, String vmName, String vmMem, String vmCpu,
			String duId, String vmIp) {
		super();
		this.id = id;
		this.dataCenterCode = dataCenterCode;
		this.clusterCode = clusterCode;
		this.hostName = hostName;
		this.vmName = vmName;
		this.vmMem = vmMem;
		this.vmCpu = vmCpu;
		this.duId = duId;
		this.vmIp = vmIp;
	}




	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
