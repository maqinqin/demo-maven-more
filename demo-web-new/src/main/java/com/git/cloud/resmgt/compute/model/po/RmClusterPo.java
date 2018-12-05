package com.git.cloud.resmgt.compute.model.po;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.excel.model.vo.HostVo;
/**
 * 
 * @author 王成辉
 * @Description 集群PO
 * @date 2014-12-17
 *
 */
public class RmClusterPo extends BaseBO implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3227212197907994986L;
	// Fields

	private String id;
	private String cdpId;
	private String clusterName;
	private String ename;
	private String status;
	private String isActive;
	private String remark;
	//存储设备storage_id
	private String storage_id;
	//存储设备名称
	private String storage_device_name;
	//虚拟机类型
	private String vmType;
	//虚机分配类型
	private String vmDistriType;
	//管理服务器
	private String manageServer;
	//备管服务器
	private String manageServerBak;
	//存储类型
	private String datastoreType;
	//网络汇聚
	private String networkConvergence;
	//资源池ID
	private String resPoolId;
	//datacenterId
	private String datacenterId;
	//平台類型
	private String platformType;
	//虚拟化类型
	private String virtualTypeCode;
	
	
	//同步计算资源池 用到的字段
	private String openstackId;//openstack  Id 
	private String hosts;  //主机  （内有多个主机）
	private String hostTypeId; //所属的资源池类型
	
	
	public String getHostTypeId() {
		return hostTypeId;
	}

	public void setHostTypeId(String hostTypeId) {
		this.hostTypeId = hostTypeId;
	}

	public String getOpenstackId() {
		return openstackId;
	}

	public void setOpenstackId(String openstackId) {
		this.openstackId = openstackId;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public String getVirtualTypeCode() {
		return virtualTypeCode;
	}

	public void setVirtualTypeCode(String virtualTypeCode) {
		this.virtualTypeCode = virtualTypeCode;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}

	//获取查询页面下拉菜单中文名称
	private String vmTypeName;
	private String vmDistriTypeName;
	private String datastoreTypeName;
	private String networkConvergenceName;
	private String manageServerName;
	private String manageServerBakName;
	private String manageServerHost;
	private String domainName;
	
	
	
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
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

	public String getManageServerHost() {
		return manageServerHost;
	}

	public void setManageServerHost(String manageServerHost) {
		this.manageServerHost = manageServerHost;
	}

	public String getResPoolId() {
		return resPoolId;
	}

	public void setResPoolId(String resPoolId) {
		this.resPoolId = resPoolId;
	}

	public String getVmType() {
		return vmType;
	}

	public void setVmType(String vmType) {
		this.vmType = vmType;
	}

	public String getVmDistriType() {
		return vmDistriType;
	}

	public void setVmDistriType(String vmDistriType) {
		this.vmDistriType = vmDistriType;
	}

	public String getManageServer() {
		return manageServer;
	}

	public void setManageServer(String manageServer) {
		this.manageServer = manageServer;
	}

	public String getManageServerBak() {
		return manageServerBak;
	}

	public void setManageServerBak(String manageServerBak) {
		this.manageServerBak = manageServerBak;
	}

	public String getDatastoreType() {
		return datastoreType;
	}

	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}

	public String getNetworkConvergence() {
		return networkConvergence;
	}

	public void setNetworkConvergence(String networkConvergence) {
		this.networkConvergence = networkConvergence;
	}

	/** default constructor */
	public RmClusterPo() {
	}

	/** minimal constructor */
	public RmClusterPo(String id) {
		this.id = id;
	}

	/** full constructor */
	public RmClusterPo(String id, String cdpId, String clusterName, String ename,
			String status, String isActive, String remark) {
		this.id = id;
		this.cdpId = cdpId;
		this.clusterName = clusterName;
		this.ename = ename;
		this.status = status;
		this.isActive = isActive;
		this.remark = remark;
	}
	/**
	 * 工银瑞信vc扫描
	 * @param clusterName
	 * @param manageServer
	 */
	public RmClusterPo(String id,String clusterName, String poolId) {
		this.id = id;
		this.clusterName = clusterName;
		this.ename = clusterName;
		this.isActive = "Y";
		this.vmType = "1";
		this.vmDistriType = "SINGLE";
		this.platformType = "1";
		this.resPoolId = poolId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCdpId() {
		return cdpId;
	}

	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}

	public String getClusterName() {
		return this.clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
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

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(String storage_id) {
		this.storage_id = storage_id;
	}

	public String getStorage_device_name() {
		return storage_device_name;
	}

	public void setStorage_device_name(String storage_device_name) {
		this.storage_device_name = storage_device_name;
	}

	public List<HostVo> getHostList() {
		return hostList;
	}

	public void setHostList(List<HostVo> hostList) {
		this.hostList = hostList;
	}

	private List<HostVo> hostList;

}
