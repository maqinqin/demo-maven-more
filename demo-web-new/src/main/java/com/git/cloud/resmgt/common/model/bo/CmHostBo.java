package com.git.cloud.resmgt.common.model.bo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmHostBo.java
 * @Package com.git.cloud.resmgt.common.model.bo
 * @Description: 存储物理机信息，更新物理机数据库信息。
 * @author lizhizhong
 * @date 2014-9-18 下午2:42:06
 * @version V1.0
 */
public class CmHostBo extends BaseBO {

	private static final long serialVersionUID = 1L;
	/** 设备id */
	private String id_str;
	/** cpu */
	private String cpu;
	/** 内存 */
	private String men;
	/** 已使用cpu */
	private String cpu_used;
	/** 已使用内存 */
	private String men_used;
	/** 存储 */
	private String disk;
	/** 所属集群id */
	private String cluster_id;
	private String hostIp;
	
	

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getId_str() {
		return id_str;
	}

	public void setId_str(String id_str) {
		this.id_str = id_str;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMen() {
		return men;
	}

	public void setMen(String men) {
		this.men = men;
	}

	public String getCpu_used() {
		return cpu_used;
	}

	public void setCpu_used(String cpu_used) {
		this.cpu_used = cpu_used;
	}

	public String getMen_used() {
		return men_used;
	}

	public void setMen_used(String men_used) {
		this.men_used = men_used;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getCluster_id() {
		return cluster_id;
	}

	public void setCluster_id(String cluster_id) {
		this.cluster_id = cluster_id;
	}

	/*@Override
	public String getBizId() {
		return this.getBizId();
	}*/
	@Override
	public String getBizId() {
		return getId_str();
	}
}