package com.git.cloud.resmgt.common.model.bo;

import java.util.List;
import com.git.cloud.common.model.base.BaseBO;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmDeviceHostShowVo.java
 * @Package
 * @Description: 存储页面展示用的主机信息。
 * @author lizhizhong
 * @date 2014-9-12 下午2:08:10
 * @version V1.0
 */
public class CmDeviceHostShowBo extends BaseBO {

	private static final long serialVersionUID = 1L;
	/** id */
	private String id;
	/** 所属物理机名称 */
	private String device_name;
	/** 编号 */
	private String sn;
	/** cpu */
	private Integer cpu;
	/** 已使用cpu */
	private Integer cpuUsed;
	/** 内存 */
	private Integer mem;
	/** 已使用内存 */
	private Integer memUsed;
	/** 型号 */
	private String device_model;
	/** 厂商 */
	private String manufacturer;
	/** 状态 */
	private String is_active;
	/** 状态名称 */
	private String dic_name;
	/** 位置 */
	private String seat_name;
	/** 存储硬盘 */
	private Integer disk;
	/** 所属集群名称 */
	private String cluster_name;
	/** 所属cdp名称 */
	private String cdp_name;
	private String clusterId;
	private String manageServerId;
	private String cdpId;
	/** 所属资源池名称 */
	private String pool_name;
	/** 所属数据中心名称 */
	private String datacenter_cname;
	/** 所属数据中心ID */
	private String datacenter_id;
	/** IP地址列表 */
	private List<CmIpShowBo> ipList;
	/** 磁盘名称**/
	private String localDisk;
	/** 是否已vc纳管**/
	private String isInvc;
	/** 裸机状态**/
	private String isBare;
	/** 纳管时间**/
	private String control_time;
	/**	选择的datastore名称**/
	private String datastore_name;
	/** 选择的datastore_id*/
	private String datastore_id;
	/**物理机运行状态*/
	private String device_status;
	
	private String platformCode;
	private String virtualTypeCode;
	/** 存储类型*/
	private String datastore_type;
	private String running_State;  //物理机运行状态
	private String state;//物理机是否进入维护模式
	private String ipmiUser;
	private String ipmiUrl;
	private String ipmiPwd;
	private String ipmiVer;
	private String ipmiFlag;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIpmiFlag() {
		return ipmiFlag;
	}

	public void setIpmiFlag(String ipmiFlag) {
		this.ipmiFlag = ipmiFlag;
	}

	public String getIpmiVer() {
		return ipmiVer;
	}

	public void setIpmiVer(String ipmiVer) {
		this.ipmiVer = ipmiVer;
	}

	public String getIpmiUser() {
		return ipmiUser;
	}

	public void setIpmiUser(String ipmiUser) {
		this.ipmiUser = ipmiUser;
	}

	public String getIpmiUrl() {
		return ipmiUrl;
	}

	public void setIpmiUrl(String ipmiUrl) {
		this.ipmiUrl = ipmiUrl;
	}

	public String getIpmiPwd() {
		return ipmiPwd;
	}

	public void setIpmiPwd(String ipmiPwd) {
		this.ipmiPwd = ipmiPwd;
	}
	
	public String getRunning_State() {
		return running_State;
	}

	public void setRunning_State(String running_State) {
		this.running_State = running_State;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getCpuUsed() {
		return cpuUsed;
	}

	public void setCpuUsed(Integer cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	public Integer getMem() {
		return mem;
	}

	public void setMem(Integer mem) {
		this.mem = mem;
	}

	public Integer getMemUsed() {
		return memUsed;
	}

	public void setMemUsed(Integer memUsed) {
		this.memUsed = memUsed;
	}

	public String getDevice_model() {
		return device_model;
	}

	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getDic_name() {
		return dic_name;
	}

	public void setDic_name(String dic_name) {
		this.dic_name = dic_name;
	}

	public String getSeat_name() {
		return seat_name;
	}

	public void setSeat_name(String seat_name) {
		this.seat_name = seat_name;
	}

	public Integer getDisk() {
		return disk;
	}

	public void setDisk(Integer disk) {
		this.disk = disk;
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

	public List<CmIpShowBo> getIpList() {
		return ipList;
	}

	public void setIpList(List<CmIpShowBo> ipList) {
		this.ipList = ipList;
	}

	public String getIsInvc() {
		return isInvc;
	}

	public void setIsInvc(String isInvc) {
		this.isInvc = isInvc;
	}

	
	public String getControl_time() {
		return control_time;
	}

	public void setControl_time(String control_time) {
		this.control_time = control_time;
	}

	@Override
	public String getBizId() {
		return String.valueOf(this.id);
	}

	public String getDatastore_name() {
		return datastore_name;
	}

	public void setDatastore_name(String datastore_name) {
		this.datastore_name = datastore_name;
	}

	public String getIsBare() {
		return isBare;
	}

	public void setIsBare(String isBare) {
		this.isBare = isBare;
	}

	public String getlocalDisk() {
		return localDisk;
	}

	public void setData_store(String localDisk) {
		this.localDisk = localDisk;
	}

	public String getDatastore_id() {
		return datastore_id;
	}

	public void setDatastore_id(String datastore_id) {
		this.datastore_id = datastore_id;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getVirtualTypeCode() {
		return virtualTypeCode;
	}

	public void setVirtualTypeCode(String virtualTypeCode) {
		this.virtualTypeCode = virtualTypeCode;
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
	 * @return the device_status
	 */
	public String getDevice_status() {
		return device_status;
	}

	/**
	 * @param device_status the device_status to set
	 */
	public void setDevice_status(String device_status) {
		this.device_status = device_status;
	}

	public String getLocalDisk() {
		return localDisk;
	}

	public void setLocalDisk(String localDisk) {
		this.localDisk = localDisk;
	}

	public String getDatastore_type() {
		return datastore_type;
	}

	public void setDatastore_type(String datastore_type) {
		this.datastore_type = datastore_type;
	}

	public String getDatacenter_id() {
		return datacenter_id;
	}

	public void setDatacenter_id(String datacenter_id) {
		this.datacenter_id = datacenter_id;
	}

	public String getManageServerId() {
		return manageServerId;
	}

	public void setManageServerId(String manageServerId) {
		this.manageServerId = manageServerId;
	}

}