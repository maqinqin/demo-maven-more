package com.git.cloud.resmgt.compute.model.vo;

import java.util.Date;

/**
 * 服务实例节点
 *
 */
public class ServiceInstanceNode {
	private String id;                 //主键
	private String name;               //节点名称
	private Date createDt;             //创建时间
	private Date deleteDt;             //删除时间
	private String creator;            //创建者
	private String deviceId;           //设备id
	private String isActive;           //是否可用(删除)  Y:可用   N:不可用 默认Y
	private String flavorId;           //规格id
	private String serviceInstanceId;  //服务实例id
	private String manager;
	private String control;
	private String data;
	private int orderNum;
	private Integer cpu;  //cpu
	private Integer mem;  //mem
	private Integer sysDisk;  //系统盘
	private Integer dataDisk;  //数据盘（单 ）
	private Integer dataDiskNum;  //数据盘数
	private String completeStatus; //完成状态
	private String manageIp;//管理IP
	private String businessIp;//业务IP
	private String hostName;
	
	
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getBusinessIp() {
		return businessIp;
	}
	public void setBusinessIp(String businessIp) {
		this.businessIp = businessIp;
	}
	public String getManageIp() {
		return manageIp;
	}
	public void setManageIp(String manageIp) {
		this.manageIp = manageIp;
	}
	

	public String getCompleteStatus() {
		return completeStatus;
	}
	public void setCompleteStatus(String completeStatus) {
		this.completeStatus = completeStatus;
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
	public Integer getDataDisk() {
		return dataDisk;
	}
	public void setDataDisk(Integer dataDisk) {
		this.dataDisk = dataDisk;
	}
	public Integer getDataDiskNum() {
		return dataDiskNum;
	}
	public void setDataDiskNum(Integer dataDiskNum) {
		this.dataDiskNum = dataDiskNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public Date getDeleteDt() {
		return deleteDt;
	}
	public void setDeleteDt(Date deleteDt) {
		this.deleteDt = deleteDt;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getFlavorId() {
		return flavorId;
	}
	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}
	public String getServiceInstanceId() {
		return serviceInstanceId;
	}
	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}
	@Override
	public String toString() {
		return "ServiceInstanceNode [id=" + id + ", name=" + name + ", createDt=" + createDt + ", deleteDt=" + deleteDt
				+ ", creator=" + creator + ", deviceId=" + deviceId + ", isActive=" + isActive + ", flavorId="
				+ flavorId + ", serviceInstanceId=" + serviceInstanceId + "]";
	}
}
