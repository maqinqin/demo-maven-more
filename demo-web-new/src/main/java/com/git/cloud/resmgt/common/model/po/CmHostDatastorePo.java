package com.git.cloud.resmgt.common.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 物理机的Datastore PO
  * @author WangJingxin
  * @date 2016年2月3日 下午5:04:05
  *
 */
public class CmHostDatastorePo extends BaseBO implements Serializable {

	private static final long serialVersionUID = -3169481826108489116L;
	/**
	 * 物理机ID
	 */
	private String id;
	/**
	 * Datastore的ID
	 */
	private String datastoreId;
	/**
	 * Datastore的类型
	 */
	private String datastoreType;
	/**
	 * Datastore的名称
	 */
	private String datastoreName;
	
	private String hostId;

    private Integer totalSize;

    private Integer freeSize;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDatastoreId() {
		return datastoreId;
	}

	public void setDatastoreId(String datastoreId) {
		this.datastoreId = datastoreId;
	}

	public String getDatastoreType() {
		return datastoreType;
	}

	public void setDatastoreType(String datastoreType) {
		this.datastoreType = datastoreType;
	}

	public String getDatastoreName() {
		return datastoreName;
	}

	public void setDatastoreName(String datastoreName) {
		this.datastoreName = datastoreName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getFreeSize() {
		return freeSize;
	}

	public void setFreeSize(Integer freeSize) {
		this.freeSize = freeSize;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
