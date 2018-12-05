package com.git.cloud.rest.model;

import java.util.List;
import java.util.Map;

import com.git.cloud.request.model.vo.BmSrAttrValVo;

public class ServiceRequestParam implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 926065561853273720L;
	// 服务供给申请:应用系统、数据中心、申请类型、服务器角色、CPU、MEM、DISK、云服务目录、服务套数
	private String paramJson;
	private String platformCode;
	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getParamJson() {
		return paramJson;
	}

	public void setParamJson(String paramJson) {
		this.paramJson = paramJson;
	}

	private String appId;
	private String dataCenterId;
	private String operModelType;
	private String duId;
	private String cpu;
	private String mem;
	private String disk;
	private String flavorId;
	private String serviceId;
	private String serviceNum;
	private Integer sysDisk;
	private Integer dataDisk;
	private boolean floatIpFlag;
	private boolean dataDiskShareFlag;
	// 计算资源池id
	private String rmHostResPoolId;
	// 租户ID
	private String tenantId;
	// 用户id
	private String creatorId;
	// 设备id
	private String deviceId;
	private List<BmSrAttrValVo> attrValList;
	private String rmNwResPoolId;// 网络资源池id
	private String deleteIpAddress;
	private String addIpAddress;
	
	public String getAddIpAddress() {
		return addIpAddress;
	}

	public void setAddIpAddress(String addIpAddress) {
		this.addIpAddress = addIpAddress;
	}

	/**
	 * 平台类型编码
	 * O:X86openstack平台
	 * X：x86平台
	 * P：power平台
	 * H：惠普平台
	 */
	private String platformTypeCode;
	/**
	 * 配额编码
	 */
	private String quotaCode;
	
	
	public Integer getSysDisk() {
		return sysDisk;
	}

	public void setSysDisk(Integer sysDisk) {
		this.sysDisk = sysDisk;
	}

	public Integer getDataDisk() {
		return dataDisk;
	}

	public void setDataDisk(Integer dataDisk) {
		this.dataDisk = dataDisk;
	}

	public boolean isFloatIpFlag() {
		return floatIpFlag;
	}

	public void setFloatIpFlag(boolean floatIpFlag) {
		this.floatIpFlag = floatIpFlag;
	}

	public boolean isDataDiskShareFlag() {
		return dataDiskShareFlag;
	}

	public void setDataDiskShareFlag(boolean dataDiskShareFlag) {
		this.dataDiskShareFlag = dataDiskShareFlag;
	}

	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

	public String getQuotaCode() {
		return quotaCode;
	}

	public void setQuotaCode(String quotaCode) {
		this.quotaCode = quotaCode;
	}

	public String getPlatformTypeCode() {
		return platformTypeCode;
	}

	public void setPlatformTypeCode(String platformTypeCode) {
		this.platformTypeCode = platformTypeCode;
	}

	public String getDeleteIpAddress() {
		return deleteIpAddress;
	}

	public void setDeleteIpAddress(String deleteIpAddress) {
		this.deleteIpAddress = deleteIpAddress;
	}

	public String getRmNwResPoolId() {
		return rmNwResPoolId;
	}

	public void setRmNwResPoolId(String rmNwResPoolId) {
		this.rmNwResPoolId = rmNwResPoolId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDataCenterId() {
		return dataCenterId;
	}

	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}

	public String getOperModelType() {
		return operModelType;
	}

	public void setOperModelType(String operModelType) {
		this.operModelType = operModelType;
	}

	public String getDuId() {
		return duId;
	}

	public void setDuId(String duId) {
		this.duId = duId;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public List<BmSrAttrValVo> getAttrValList() {
		return attrValList;
	}

	public void setAttrValList(List<BmSrAttrValVo> attrValList) {
		this.attrValList = attrValList;
	}

	public String getRmHostResPoolId() {
		return rmHostResPoolId;
	}

	public void setRmHostResPoolId(String rmHostResPoolId) {
		this.rmHostResPoolId = rmHostResPoolId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Override
	public String toString() {
		return "ServiceRequestParam [appId=" + appId + ", dataCenterId=" + dataCenterId + ", operModelType="
				+ operModelType + ", duId=" + duId + ", cpu=" + cpu + ", mem=" + mem + ", disk=" + disk + ", serviceId="
				+ serviceId + ", serviceNum=" + serviceNum + ", rmHostResPoolId=" + rmHostResPoolId + ", tenantId="
				+ tenantId + ", creatorId=" + creatorId + ", deviceId=" + deviceId + ", attrValList=" + attrValList
				+ ", rmNwResPoolId=" + rmNwResPoolId + ", deleteIpAddress=" + deleteIpAddress + "]";
	}
}
