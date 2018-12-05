package com.git.cloud.request.model.po;

import com.git.cloud.common.model.base.BaseBO;

public class BmSrRrinfoPo extends BaseBO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String rrinfoId;
	private String srId;
	private String serviceId;
	private String duId;
	private String appId;
	private String flowId;
	private String dataCenterId;
	private Integer cpu;
	private Integer mem;
	private String flavorId;
	private Integer sysDisk;
	private String dataDiskCode;
	private String archiveDiskCode;
	private Integer vmNum;
	private Integer orderId;
	private String serverId;
	//gyrx
	private String serverIp;
	private String dbParam;
	
	//计算资源池id
	private String rmHostResPoolId;
	
	private String tenantId;
	private String userId;//用户id
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String parametersJson;
	public String getParametersJson() {
		return parametersJson;
	}
	public void setParametersJson(String parametersJson) {
		this.parametersJson = parametersJson;
	}
	public String getFlavorId() {
		return flavorId;
	}
	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	private String platformType;
	
	public BmSrRrinfoPo() {
	}
	public BmSrRrinfoPo(String rrinfoId) {
		this.rrinfoId = rrinfoId;
	}
	public BmSrRrinfoPo(String rrinfoId, String srId, String serviceId,
		    String duId, String flowId, String dataCenterId,Integer cpu,
			Integer mem, Integer sysDisk, String dataDiskCode,
			String archiveDiskCode, Integer vmNum, String platformType) {
		this.rrinfoId = rrinfoId;
		this.srId = srId;
		this.serviceId = serviceId;
		this.duId = duId;
		this.flowId = flowId;
		this.dataCenterId = dataCenterId;
		this.cpu = cpu;
		this.mem = mem;
		this.sysDisk = sysDisk;
		this.dataDiskCode = dataDiskCode;
		this.archiveDiskCode = archiveDiskCode;
		this.vmNum = vmNum;
		this.platformType = platformType;
	}
	
	public String getRrinfoId() {
		return rrinfoId;
	}
	
	public void setRrinfoId(String rrinfoId) {
		this.rrinfoId = rrinfoId;
	}
	
	public String getSrId() {
		return srId;
	}
	
	public void setSrId(String srId) {
		this.srId = srId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getDuId() {
		return duId;
	}
	public void setDuId(String duId) {
		this.duId = duId;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getDataCenterId() {
		return dataCenterId;
	}
	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	public Integer getMem() {
		return mem;
	}
	public void setMem(Integer mem) {
		this.mem = mem;
	}
	public Integer getSysDisk() {
		return sysDisk;
	}
	public void setSysDisk(Integer sysDisk) {
		this.sysDisk = sysDisk;
	}
	public String getDataDiskCode() {
		return dataDiskCode;
	}
	public void setDataDiskCode(String dataDiskCode) {
		this.dataDiskCode = dataDiskCode;
	}
	public String getArchiveDiskCode() {
		return archiveDiskCode;
	}
	public void setArchiveDiskCode(String archiveDiskCode) {
		this.archiveDiskCode = archiveDiskCode;
	}
	public Integer getVmNum() {
		return vmNum;
	}
	public void setVmNum(Integer vmNum) {
		this.vmNum = vmNum;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	@Override
	public String getBizId() {
		return null;
	}
	public String getDbParam() {
		return dbParam;
	}
	public void setDbParam(String dbParam) {
		this.dbParam = dbParam;
	}
	public String getRmHostResPoolId() {
		return rmHostResPoolId;
	}
	public void setRmHostResPoolId(String rmHostResPoolId) {
		this.rmHostResPoolId = rmHostResPoolId;
	}
	
}