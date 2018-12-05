package com.git.cloud.resmgt.common.model.bo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmClusterHostShowBo.java
 * @Package com.git.cloud.resmgt.common.model.bo
 * @Description: 存储页面展示用的指定集群下的主机对应的网络信息。
 * @author lizhizhong
 * @date 2014-9-16 上午11:30:52
 * @version V1.0
 */
public class CmClusterHostNetShowBo extends BaseBO {

	private static final long serialVersionUID = 1L;
	/** 设备id */
	private String id;
	/** 网络地址类型 */
	private String nettype_name;
	/** ip地址 */
	private String ip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNettype_name() {
		return nettype_name;
	}

	public void setNettype_name(String nettype_name) {
		this.nettype_name = nettype_name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String getBizId() {
		return String.valueOf(this.id);
	}

}