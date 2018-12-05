package com.git.cloud.resmgt.common.model.bo;

import java.util.List;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmDeviceHostVMBo.java
 * @Package com.git.cloud.resmgt.common.model.bo
 * @Description: 存储页面展示用的主机信息。
 * @author lizhizhong
 * @date 2014-9-15 下午5:08:15
 * @version V1.0
 */
public class CmDeviceVMShowBo extends BaseBO {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** id */
	private String id;
	/** 虚拟机名称 */
	private String vm_name;
	/** 编号 */
	private String sn;
	/** CPU */
	private String cpu;
	/** 内存 */
	private String mem;
	/** 所属物理机名称 */
	private String device_name;
	private String clusterId;
	/** 所属集群名称 */
	private String cluster_name;
	/** 管理服务器ID */
	private String manageServerId;
	/** 所属cdp名称 */
	private String cdp_name;
	/** 所属cdp的ID*/
	private String cdpId;
	/** 所属资源池名称 */
	private String pool_name;
	/** 所属数据中心名称 */
	private String datacenter_cname;
	/** 状态名称 */
	private String dic_name;
	/** IP地址列表 */
	private List<CmIpShowBo> ipList;
	/** 状态名称 */
	private String du_name;
	/** 状态名称 */
	private String appInfo_name;
	/** 上线时间 */
	private String online_time;
	/**wmy, 物理机ID*/
	private String hostId;
	
	private String azName;
	private String vmMsId;
	private String iaasUuid;

    public String getIaasUuid() {
         return iaasUuid;
    }
    public void setIaasUuid(String iaasUuid) {
         this.iaasUuid = iaasUuid;
    }

	public String getAzName() {
		return azName;
	}

	public void setAzName(String azName) {
		this.azName = azName;
	}

	public String getVmMsId() {
		return vmMsId;
	}

	public void setVmMsId(String vmMsId) {
		this.vmMsId = vmMsId;
	}

	//虚机状态
	private String vmState;
	private String appID;
	private Integer disk;
	private String  duID;
	private String datacenterID;
	private String virtualTypeCode;
	private String serviceId;
	private String platFormCode;
	private String tenantName;
	private String projectId;
	/** 挂载磁盘大小 */
	private String mountDiskSize;
	/** 挂载磁盘数量*/
	private String mountDiskNumber;
	/* 登录名*/
	private String loginName;
	/* 所属租户名*/
	private String userName;
	/* 登录姓*/
	private String firstName;
	/* 登录名*/
	private String lastName;
	
	private String availableZoneId;
	
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getMountDiskSize() {
		return mountDiskSize;
	}

	public void setMountDiskSize(String mountDiskSize) {
		this.mountDiskSize = mountDiskSize;
	}

	public String getMountDiskNumber() {
		return mountDiskNumber;
	}

	public void setMountDiskNumber(String mountDiskNumber) {
		this.mountDiskNumber = mountDiskNumber;
	}

	public String getPlatFormCode() {
		return platFormCode;
	}

	public void setPlatFormCode(String platFormCode) {
		this.platFormCode = platFormCode;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getManageServerId() {
		return manageServerId;
	}

	public void setManageServerId(String manageServerId) {
		this.manageServerId = manageServerId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getVirtualTypeCode() {
		return virtualTypeCode;
	}

	public void setVirtualTypeCode(String virtualTypeCode) {
		this.virtualTypeCode = virtualTypeCode;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public Integer getDisk() {
		return disk;
	}

	public void setDisk(Integer disk) {
		this.disk = disk;
	}

	public String getDuID() {
		return duID;
	}

	public void setDuID(String duID) {
		this.duID = duID;
	}

	public String getDatacenterID() {
		return datacenterID;
	}

	public void setDatacenterID(String datacenterID) {
		this.datacenterID = datacenterID;
	}

	public String getCdpId() {
		return cdpId;
	}

	public void setCdpId(String cdpId) {
		this.cdpId = cdpId;
	}

	public String getVmState() {
		return vmState;
	}

	public void setVmState(String vmState) {
		this.vmState = vmState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVm_name() {
		return vm_name;
	}

	public void setVm_name(String vm_name) {
		this.vm_name = vm_name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getCluster_name() {
		return cluster_name;
	}

	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}

	public String getCdp_name() {
		return cdp_name;
	}

	public void setCdp_name(String cdp_name) {
		this.cdp_name = cdp_name;
	}

	public String getPool_name() {
		return pool_name;
	}

	public void setPool_name(String pool_name) {
		this.pool_name = pool_name;
	}

	public String getDatacenter_cname() {
		return datacenter_cname;
	}

	public void setDatacenter_cname(String datacenter_cname) {
		this.datacenter_cname = datacenter_cname;
	}

	public String getDic_name() {
		return dic_name;
	}

	public void setDic_name(String dic_name) {
		this.dic_name = dic_name;
	}

	public List<CmIpShowBo> getIpList() {
		return ipList;
	}

	public void setIpList(List<CmIpShowBo> ipList) {
		this.ipList = ipList;
	}

	public String getDu_name() {
		return du_name;
	}

	public void setDu_name(String du_name) {
		this.du_name = du_name;
	}

	public String getAppInfo_name() {
		return appInfo_name;
	}

	public void setAppInfo_name(String appInfo_name) {
		this.appInfo_name = appInfo_name;
	}
 
	public String getOnline_time() {
		return online_time;
	}

	public void setOnline_time(String online_time) {
		this.online_time = online_time;
	}

	@Override
	public String getBizId() {
		return String.valueOf(this.id);
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getAvailableZoneId() {
		return availableZoneId;
	}

	public void setAvailableZoneId(String availableZoneId) {
		this.availableZoneId = availableZoneId;
	}


}