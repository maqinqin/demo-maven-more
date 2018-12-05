package com.git.cloud.appmgt.model.po;

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
  * 应用履历PO类
  * @author WangJingxin
  * @date 2016年4月11日 下午2:08:52
  *
 */
public class AppStatPo extends BaseBO {

	/**
	  * @Fields serialVersionUID
	  */
	private static final long serialVersionUID = 2405765464217308143L;
	/**
	 * ID
	 */
	private String statID;
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
	private int cpu;
	/**
	 * 内存
	 */
	private int mem;
	/**
	 * 额外磁盘空间
	 */
	private int disk;
	/**
	 * 此数据创建时间
	 */
	private Date createTime;
	/**
	 * 云服务ID
	 */
	private String serviceID;

	public String getStatID() {
		return statID;
	}
	public void setStatID(String statID) {
		this.statID = statID;
	}



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



	public int getCpu() {
		return cpu;
	}



	public void setCpu(int cpu) {
		this.cpu = cpu;
	}



	public int getMem() {
		return mem;
	}



	public void setMem(int mem) {
		this.mem = mem;
	}



	public int getDisk() {
		return disk;
	}



	public void setDisk(int disk) {
		this.disk = disk;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
