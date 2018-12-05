package com.git.cloud.resmgt.compute.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class RmCdpVo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1054771076756157750L;
	private String id;
	private String resPoolId;
	private String cdpName;
	private String ename;
	private String status;
	private String isActive;
	private String remark;
	
	//获取查询页面下拉菜单中文名称
	private String vmTypeName;
	private String vmDistriTypeName;
	private String datastoreTypeName;
	private String networkConvergenceName;
	private String manageServerName;
	private String manageServerBakName;
	private String datacenterId;
	//资源池的平台类型
	private String platformType;
	private String vmTypeCode;
	// Constructors

	public String getVmTypeCode() {
		return vmTypeCode;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public void setVmTypeCode(String vmTypeCode) {
		this.vmTypeCode = vmTypeCode;
	}

	/** default constructor */
	public RmCdpVo() {
	}

	/** minimal constructor */
	public RmCdpVo(String id) {
		this.id = id;
	}

	/** full constructor */
	public RmCdpVo(String id, String resPoolId, String cdpName, String ename,
			String platformType, String vmType, String vmDistriType,
			String manageServer, String manageServerBak, String status,
			String datastoreType, String networkConvergence, String isActive,
			String remark,String vmTypeName,String vmDistriTypeName,String datastoreTypeName,String networkConvergenceName,
			String manageServerName,String manageServerBakName,String datacenterId) {
		this.id = id;
		this.resPoolId = resPoolId;
		this.cdpName = cdpName;
		this.ename = ename;
		this.status = status;
		this.isActive = isActive;
		this.remark = remark;
		this.vmTypeName = vmTypeName;
		this.vmDistriTypeName = vmDistriTypeName;
		this.datastoreTypeName = datastoreTypeName;
		this.networkConvergenceName = networkConvergenceName;
		this.manageServerName = manageServerName;
		this.manageServerBakName = manageServerBakName;
		this.datacenterId = datacenterId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCdpName() {
		return this.cdpName;
	}

	public void setCdpName(String cdpName) {
		this.cdpName = cdpName;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVmTypeName() {
		return vmTypeName;
	}

	public void setVmTypeName(String vmTypeName) {
		this.vmTypeName = vmTypeName;
	}

	public String getVmDistriTypeName() {
		return vmDistriTypeName;
	}

	public void setVmDistriTypeName(String vmDistriTypeName) {
		this.vmDistriTypeName = vmDistriTypeName;
	}

	public String getDatastoreTypeName() {
		return datastoreTypeName;
	}

	public void setDatastoreTypeName(String datastoreTypeName) {
		this.datastoreTypeName = datastoreTypeName;
	}

	public String getNetworkConvergenceName() {
		return networkConvergenceName;
	}

	public void setNetworkConvergenceName(String networkConvergenceName) {
		this.networkConvergenceName = networkConvergenceName;
	}

	public String getManageServerName() {
		return manageServerName;
	}

	public void setManageServerName(String manageServerName) {
		this.manageServerName = manageServerName;
	}

	public String getManageServerBakName() {
		return manageServerBakName;
	}

	public void setManageServerBakName(String manageServerBakName) {
		this.manageServerBakName = manageServerBakName;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

}
