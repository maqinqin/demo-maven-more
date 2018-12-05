package com.git.cloud.handler.vo;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.foundation.util.PwdUtil;

public class VmDeviceVo extends BaseBO {

	private static final long serialVersionUID = 1L;
	// 虚拟机id
    private String deviceId;
	 //物理机id
    private String hostId;
     // 虚机部署集群id
    private String clusterId;
     // 虚拟服务器角色
	private String cdpId;
	 // 资源池
	private String resPoolId;
	private String vcenterUrl;//虚拟机所在vcenterUrl
	private String vcenterName;//虚拟机所在vcenter Name
	private String vcenterPwd;//虚拟机所在vcenter pwd
	private String  datacenterId;// vcenter所在数据中心
	private String esxiUrl; //虚拟机所在vcenterUrl
	private String esxiName;//虚拟机所在vcenter Name
	private String exsiPwd;//虚拟机所在vcenter pwd
	
	private String vmName;//虚拟机名
	private String platformTypeCode;// 虚拟机类型

	public VmDeviceVo() {
		
	}
	
	public VmDeviceVo(String deviceId, String vmName, String platformTypeCode) {
		super();
		this.deviceId = deviceId;
		this.vmName = vmName;
		this.platformTypeCode = platformTypeCode;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @return the hostId
	 */
	public String getHostId() {
		return hostId;
	}
	/**
	 * @param hostId the hostId to set
	 */
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	/**
	 * @return the clusterId
	 */
	public String getClusterId() {
		return clusterId;
	}
	/**
	 * @param clusterId the clusterId to set
	 */
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	/**
	 * @return the cdpId
	 */
	public String getCdpId() {
		return cdpId;
	}
	/**
	 * @param cdpId the cdpId to set
	 */
	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}
	/**
	 * @return the resPoolId
	 */
	public String getResPoolId() {
		return resPoolId;
	}
	/**
	 * @param resPoolId the resPoolId to set
	 */
	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}
	
	
	public String getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}
	
	public String getVcenterUrl() {
		return vcenterUrl;
	}
	public void setVcenterUrl(String vcenterUrl) {
		
		this.vcenterUrl = vcenterUrl;
	}
	public String getVcenterName() {
		return vcenterName;
	}
	public void setVcenterName(String vcenterName) {
		
		this.vcenterName = vcenterName;
	}
	public String getVcenterPwd() {
		return vcenterPwd;
	}
	public void setVcenterPwd(String vcenterPwd) {
		this.vcenterPwd = vcenterPwd;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getPlatformTypeCode() {
		return platformTypeCode;
	}
	public void setPlatformTypeCode(String platformTypeCode) {
		this.platformTypeCode = platformTypeCode;
	}
	
	public String getEsxiUrl() {
		return esxiUrl;
	}
	public void setEsxiUrl(String esxiUrl) {
		this.esxiUrl = esxiUrl;
	}
	public String getEsxiName() {
		return esxiName;
	}
	public void setEsxiName(String esxiName) {
		this.esxiName = esxiName;
	}
	public String getExsiPwd() {
		return exsiPwd;
	}
	public void setExsiPwd(String exsiPwd) {
		//this.exsiPwd =  PwdUtil.decryption(exsiPwd);
		this.exsiPwd = PwdUtil.decryption(exsiPwd);
	}

	/* (non-Javadoc)
	 * <p>Title:getBizId</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	

    
}
