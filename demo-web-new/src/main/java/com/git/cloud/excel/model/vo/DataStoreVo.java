package com.git.cloud.excel.model.vo;

import java.io.Serializable;
import com.git.cloud.common.model.base.BaseBO;

public class DataStoreVo extends BaseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String dsName;
	private String path;
	private String hostName;
	private String drivceName;
	private String datastoreType;
	private String deviceId;
	private String hostId;
	private String hostIp;
	private String remoteIp;
	private String freeSize;
	

	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public String getDatastoreType() {
		return datastoreType;
	}


	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}


	public String getDsName() {
		return dsName;
	}


	public void setDsName(String dsName) {
		this.dsName = dsName;
	}


	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}

	public String getHostName() {
		return hostName;
	}



	public void setHostName(String hostName) {
		this.hostName = hostName;
	}



	public String getDrivceName() {
		return drivceName;
	}


	public void setDrivceName(String drivceName) {
		this.drivceName = drivceName;
	}


	public String getId() {
		return id;
	}


	public String getHostId() {
		return hostId;
	}


	public void setHostId(String hostId) {
		this.hostId = hostId;
	}


	public String getHostIp() {
		return hostIp;
	}


	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}


	public void setId(String id) {
		this.id = id;
	}


	public DataStoreVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataStoreVo(String id, String dsName, String path, String hostName,
			String freeSize) {
		super();
		this.id = id;
		this.dsName = dsName;
		this.path = path;
		this.hostName = hostName;
		this.freeSize = freeSize;
	}


	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getRemoteIp() {
		return remoteIp;
	}


	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}


	public String getFreeSize() {
		return freeSize;
	}


	public void setFreeSize(String freeSize) {
		this.freeSize = freeSize;
	}

}
