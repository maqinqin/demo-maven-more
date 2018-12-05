package com.git.cloud.request.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;

public class BmSrRrinfoVo extends BaseBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String rrinfoId;
	private String srId;
	private String dataCenterId;
	private String serviceId;
	private String serviceName;
	private String confParameter;
	private String duId;
	private String duName;
	private String duEname;
	private String duType;
	private String flowId;
	private Integer cpuOld;//扩容前的cpu
	private Integer memOld;//扩容前的mem
	private Integer diskOld;//扩容前的磁盘
	private Integer externalDiskSum;//外挂磁盘总和
	private Integer cpu;
	private Integer mem;
	private String diskPlacementType;//置备类型
	private Integer sysDisk;
	private Integer dataDisk;
	private String flavorId;
	private String dataDiskCode;
	private String archiveDiskCode;
	private Integer vmNum;
	private List<BmSrAttrValVo> attrValList;
	private List<BmSrRrVmRefPo> rrVmRefList;
	private String createTimeStr; // 简单时间字符串
	private Timestamp createTime; // 建单时间
	private Integer orderId;
	private String oldConfParameter;
	private String platformType;//平台类型id
	private String platformTypeCode;//平台类型编码
	
	private String deviceId;
	//计算资源池id
	private String rmHostResPoolId;
	//租户id
	private String tenantId;
	//gyrx虚拟机管理ip
	private String serverIp;
	
	private String parametersJson;
	
	
	public Integer getDataDisk() {
		return dataDisk;
	}
	public void setDataDisk(Integer dataDisk) {
		this.dataDisk = dataDisk;
	}
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
	public String getPlatformTypeCode() {
		return platformTypeCode;
	}
	public void setPlatformTypeCode(String platformTypeCode) {
		this.platformTypeCode = platformTypeCode;
	}
	public Integer getDiskOld() {
		return diskOld;
	}
	public void setDiskOld(Integer diskOld) {
		this.diskOld = diskOld;
	}
	public Integer getCpuOld() {
		return cpuOld;
	}
	public void setCpuOld(Integer cpuOld) {
		this.cpuOld = cpuOld;
	}
	public Integer getMemOld() {
		return memOld;
	}
	public void setMemOld(Integer memOld) {
		this.memOld = memOld;
	}
	
	public Integer getExternalDiskSum() {
		return externalDiskSum;
	}
	public void setExternalDiskSum(Integer externalDiskSum) {
		this.externalDiskSum = externalDiskSum;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getOldConfParameter() {
		return oldConfParameter;
	}
	public void setOldConfParameter(String oldConfParameter) {
		this.oldConfParameter = oldConfParameter;
	}
	public BmSrRrinfoVo() {
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
	public String getDataCenterId() {
		return dataCenterId;
	}
	public void setDataCenterId(String dataCenterId) {
		this.dataCenterId = dataCenterId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getConfParameter() {
		return confParameter;
	}
	public void setConfParameter(String confParameter) {
		this.confParameter = confParameter;
	}
	public String getDuId() {
		return duId;
	}
	public void setDuId(String duId) {
		this.duId = duId;
	}
	public String getDuName() {
		return duName;
	}
	public void setDuName(String duName) {
		this.duName = duName;
	}
	public String getDuType() {
		return duType;
	}
	public void setDuType(String duType) {
		this.duType = duType;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
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
	public List<BmSrAttrValVo> getAttrValList() {
		return attrValList;
	}
	public void setAttrValList(List<BmSrAttrValVo> attrValList) {
		this.attrValList = attrValList;
	}
	public List<BmSrRrVmRefPo> getRrVmRefList() {
		return rrVmRefList;
	}
	public void setRrVmRefList(List<BmSrRrVmRefPo> rrVmRefList) {
		this.rrVmRefList = rrVmRefList;
	}
	
	@Override
	public String getBizId() {
		return null;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		if(createTime != null) {
			this.setCreateTimeStr(createTime.toString().substring(0, 19));
		}
		this.createTime = createTime;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getDuEname() {
		return duEname;
	}
	public void setDuEname(String duEname) {
		this.duEname = duEname;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getRmHostResPoolId() {
		return rmHostResPoolId;
	}
	public void setRmHostResPoolId(String rmHostResPoolId) {
		this.rmHostResPoolId = rmHostResPoolId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDiskPlacementType() {
		return diskPlacementType;
	}
	public void setDiskPlacementType(String diskPlacementType) {
		this.diskPlacementType = diskPlacementType;
	}
	
}