package com.git.cloud.appmgt.model.vo;

import com.git.cloud.common.model.base.BaseVO;

/**
 * 应用履历VO类
  * @author WangJingxin
  * @date 2016年4月11日 下午2:56:10
  *
 */
public class AppStatVo extends BaseVO {

	/**
	  * @Fields serialVersionUID 
	  */
	private static final long serialVersionUID = 3486598392262630761L;

	/**
	 * 应用系统ID
	 */
	private String appID;
	/**
	 * 服务器角色ID
	 */
	private String duID;
	/**
	 * 数据中心ID
	 */
	private String dataCenterID;
	/**
	 * 服务类型
	 */
	private String srStatusCode;
	/**
	 * 申请类型
	 */
	private String srTypeMark;
	/**
	 * 设备ID
	 */
	private String diviceID;
	/**
	 * CPU
	 */
	private Integer cpu;
	/**
	 * 内存
	 */
	private Integer mem;
	/**
	 * 额外磁盘空间
	 */
	private Integer disk;
	/**
	 * CPU原值
	 */
	private Integer cpuOld;
	/**
	 * 内存原值
	 */
	private Integer memOld;
	/**
	 * 磁盘原值
	 */
	private Integer diskOld;
	/**
	 * 云服务ID
	 */
	private String serviceID;
	
	private String param;
	private String creatorId;
	private String tenantId;
	
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	/**
	 * 资源请求ID，非APP_STAT表字段
	 */
	private String rrinfoId;
	
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getDuID() {
		return duID;
	}
	public void setDuID(String duID) {
		this.duID = duID;
	}
	public String getDataCenterID() {
		return dataCenterID;
	}
	public void setDataCenterID(String dataCenterID) {
		this.dataCenterID = dataCenterID;
	}
	public String getSrStatusCode() {
		return srStatusCode;
	}
	public void setSrStatusCode(String srStatusCode) {
		this.srStatusCode = srStatusCode;
	}
	public String getSrTypeMark() {
		return srTypeMark;
	}
	public void setSrTypeMark(String srTypeMark) {
		this.srTypeMark = srTypeMark;
	}
	public String getDiviceID() {
		return diviceID;
	}
	public void setDiviceID(String diviceID) {
		this.diviceID = diviceID;
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
	public Integer getDisk() {
		return disk;
	}
	public void setDisk(Integer disk) {
		this.disk = disk;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
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
	public Integer getDiskOld() {
		return diskOld;
	}
	public void setDiskOld(Integer diskOld) {
		this.diskOld = diskOld;
	}
	public String getRrinfoId() {
		return rrinfoId;
	}
	public void setRrinfoId(String rrinfoId) {
		this.rrinfoId = rrinfoId;
	}
}
