package com.git.cloud.resmgt.common.model.bo;

import com.git.cloud.common.model.base.BaseBO;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmClusterHostShowBo.java
 * @Package com.git.cloud.resmgt.common.model.bo
 * @Description: 存储页面展示用的指定集群下的主机信息。
 * @author lizhizhong
 * @date 2014-9-16 上午11:30:52
 * @version V1.0
 */
public class CmClusterHostShowBo extends BaseBO {

	private static final long serialVersionUID = 1L;
	
	/** 选择的datastore名称*/
	private String datastore_name;
	/** datastore*/
	private String datastore_id;
	/** 设备id */
	private String id;
	/** 所属物理机名称 */
	private String device_name;
	/** 编号 */
	private String sn;
	/** 位置 */
	private String seat_name;
	/** 存储资源 */
	private String res_pool_id;
	/** 顺序号 */
	private String order_num;
	/** ip地址串 */
	private StringBuffer ip_str = new StringBuffer();

	/** IP地址 */
	private String ip;
	
	private String isInvc;
	
	private String isBare;
	
	private String bareV ;

	public String getIsInvc() {
		return isInvc;
	}

	public void setIsInvc(String isInvc) {
		this.isInvc = isInvc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSeat_name() {
		return seat_name;
	}

	public void setSeat_name(String seat_name) {
		this.seat_name = seat_name;
	}

	public String getRes_pool_id() {
		return res_pool_id;
	}

	public void setRes_pool_id(String res_pool_id) {
		this.res_pool_id = res_pool_id;
	}

	public StringBuffer getIp_str() {
		return ip_str;
	}

	public void setIp_str(StringBuffer ip_str) {
		this.ip_str = ip_str;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	@Override
	public String getBizId() {
		return String.valueOf(this.id);
	}

	public String getIsBare() {
		return isBare;
	}

	public void setIsBare(String isBare) {
		this.isBare = isBare;
	}

	public String getBareV() {
		return bareV;
	}

	public void setBareV(String bareV) {
		this.bareV = bareV;
	}

	public String getDatastore_name() {
		return datastore_name;
	}

	public void setDatastore_name(String datastore_name) {
		this.datastore_name = datastore_name;
	}

	public String getDatastore_id() {
		return datastore_id;
	}

	public void setDatastore_id(String datastore_id) {
		this.datastore_id = datastore_id;
	}

	
}