package com.git.cloud.resmgt.common.model.vo;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;

public class RmResPoolVo extends BaseBO implements java.io.Serializable{


	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6997663773772673432L;
	private String id;
	private String datacenterId;
	private String datacenterCName;
	private String poolName;
	private String ename;
	private String status;
	private String poolType;
	private String isActive;
	private String remark;
	
	private String platformType;
	private String serviceType;
	private String secureAreaType;
	private String secureLayer;
	//前台查寻显示中文名称
	private String platformTypeName;
	private String serviceTypeName;
	private String secureAreaTypeName;
	private String secureLayerName;
	private String cclassid;
	
	private List<RmClusterPo> clusterList;
	
	//可用分区Id
	private String availableZoneId;
	
	private String availableName;
	
	//资源池类型
	private String hostTypeId;
	private String hostTypeName;
	// Constructors

	/** default constructor */
	public RmResPoolVo() {
	}

	/** minimal constructor */
	public RmResPoolVo(String id) {
		this.id = id;
	}

	/** full constructor */
	public RmResPoolVo(String id, String datacenterId, String poolName,String cclassid,
			String ename, String status, String poolType, String isActive,
			String remark,String platformType,String serviceType,String secureAreaType,
			String secureLayer,String platformTypeName,String serviceTypeName,String secureAreaTypeName,String secureLayerName) {
		this.id = id;
		this.datacenterId = datacenterId;
		this.poolName = poolName;
		this.ename = ename;
		this.status = status;
		this.poolType = poolType;
		this.isActive = isActive;
		this.remark = remark;
		this.platformType = platformType;
		this.serviceType = serviceType;
		this.secureAreaType = secureAreaType;
		this.secureLayer = secureLayer;
		this.platformTypeName = platformTypeName;
		this.serviceTypeName = serviceTypeName;
		this.secureAreaTypeName = secureAreaTypeName;
		this.secureLayerName = secureLayerName;
		this.cclassid = cclassid;
	}
	
	/**
	 */
	public RmResPoolVo(String id,String poolName) {
		this.id = id;
		this.poolName = poolName;
		this.ename = poolName;
		this.status = "Y";
		this.poolType = "COMPUTE";
		this.isActive = "Y";
		this.platformType = "1";
		this.serviceType = "WB";
		this.platformTypeName = "X86平台";
		this.secureAreaTypeName = "管理区";
	}

	// Property accessors

	public String getCclassid() {
		return cclassid;
	}

	public void setCclassid(String cclassid) {
		this.cclassid = cclassid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPoolName() {
		return this.poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
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

	public String getPoolType() {
		return this.poolType;
	}

	public void setPoolType(String poolType) {
		this.poolType = poolType;
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

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSecureAreaType() {
		return secureAreaType;
	}

	public void setSecureAreaType(String secureAreaType) {
		this.secureAreaType = secureAreaType;
	}

	public String getSecureLayer() {
		return secureLayer;
	}

	public void setSecureLayer(String secureLayer) {
		this.secureLayer = secureLayer;
	}

	public String getPlatformTypeName() {
		return platformTypeName;
	}

	public void setPlatformTypeName(String platformTypeName) {
		this.platformTypeName = platformTypeName;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getSecureAreaTypeName() {
		return secureAreaTypeName;
	}

	public void setSecureAreaTypeName(String secureAreaTypeName) {
		this.secureAreaTypeName = secureAreaTypeName;
	}

	public String getSecureLayerName() {
		return secureLayerName;
	}

	public void setSecureLayerName(String secureLayerName) {
		this.secureLayerName = secureLayerName;
	}

	public String getDatacenterCName() {
		return datacenterCName;
	}

	public void setDatacenterCName(String datacenterCName) {
		this.datacenterCName = datacenterCName;
	}
	
	public List<RmClusterPo> getClusterList() {
		return clusterList;
	}

	public void setClusterList(List<RmClusterPo> clusterList) {
		this.clusterList = clusterList;
	}
	
	public String getAvailableZoneId() {
		return availableZoneId;
	}


	public void setAvailableZoneId(String availableZoneId) {
		this.availableZoneId = availableZoneId;
	}

	public String getAvailableName() {
		return availableName;
	}

	public void setAvailableName(String availableName) {
		this.availableName = availableName;
	}

	public String getHostTypeId() {
		return hostTypeId;
	}

	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}

	public String getHostTypeName() {
		return hostTypeName;
	}

	public void setHostTypeName(String hostTypeName) {
		this.hostTypeName = hostTypeName;
	}
	
}
