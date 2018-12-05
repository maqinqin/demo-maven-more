package com.git.cloud.handler.automation.se.po;
// default package

import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;
/**
 * StorageAssignRes entity. @author MyEclipse Persistence Tools
 */
/**
 * 
 * <p>
 * 
 * @author liyongjie
 * @version 1.0 2013-5-11 
 * @see

@Entity
@Table(name = "CM_STORAGE_ASSIGN_RES") */
public class StorageAssignResPo  extends BaseBO implements java.io.Serializable{

	// Fields

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 8891649861626392106L;
	private String storageAssignResId;
	private String hostId;
	private String hdisk;
	private String storageId;
	private String lunId;
	private String lunSize;
	private String purpose;
	private String isActive;
	
	private Date lunCreateTime;
	private Date lunUpdateTime;

	// Constructors

	/** default constructor */
	public StorageAssignResPo() {
	}

	public String getStorageAssignResId() {
		return storageAssignResId;
	}

	public String getHostId() {
		return hostId;
	}

	public String getHdisk() {
		return hdisk;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getLunId() {
		return lunId;
	}

	public String getLunSize() {
		return lunSize;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getIsActive() {
		return isActive;
	}

	public Date getLunCreateTime() {
		return lunCreateTime;
	}

	public Date getLunUpdateTime() {
		return lunUpdateTime;
	}

	public void setStorageAssignResId(String storageAssignResId) {
		this.storageAssignResId = storageAssignResId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public void setHdisk(String hdisk) {
		this.hdisk = hdisk;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public void setLunId(String lunId) {
		this.lunId = lunId;
	}

	public void setLunSize(String lunSize) {
		this.lunSize = lunSize;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setLunCreateTime(Date lunCreateTime) {
		this.lunCreateTime = lunCreateTime;
	}

	public void setLunUpdateTime(Date lunUpdateTime) {
		this.lunUpdateTime = lunUpdateTime;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	/** minimal constructor 
	public StorageAssignResPo(Long storageAssignResId, Long hostId, Long storageId) {
		this.storageAssignResId = storageAssignResId;
		this.hostId = hostId;
		this.storageId = storageId;
	}
*/
	/** full constructor 
	public StorageAssignResPo(Long storageAssignResId, Long hostId, String hdisk,
			Long storageId, String lunId, String lunSize, String purpose,
			String isActive) {
		this.storageAssignResId = storageAssignResId;
		this.hostId = hostId;
		this.hdisk = hdisk;
		this.storageId = storageId;
		this.lunId = lunId;
		this.lunSize = lunSize;
		this.purpose = purpose;
		this.isActive = isActive;
	}

	public StorageAssignResPo(String lunId, String hdisk, String purpose,
			Date lunCreateTime, Date lunUpdateTime) {
		super();
		this.lunId = lunId;
		this.hdisk = hdisk;
		this.purpose = purpose;
		this.lunCreateTime = lunCreateTime;
		this.lunUpdateTime = lunUpdateTime;
	}

	// Property accessors
	@Id
	@Column(name = "STORAGE_ASSIGN_RES_ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getStorageAssignResId() {
		return this.storageAssignResId;
	}

	public void setStorageAssignResId(Long storageAssignResId) {
		this.storageAssignResId = storageAssignResId;
	}

	@Column(name = "HOST_ID", nullable = false, precision = 18, scale = 0)
	public Long getHostId() {
		return this.hostId;
	}

	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}

	@Column(name = "HDISK", length = 20)
	public String getHdisk() {
		return this.hdisk;
	}

	public void setHdisk(String hdisk) {
		this.hdisk = hdisk;
	}

	@Column(name = "STORAGE_ID", nullable = false, precision = 18, scale = 0)
	public Long getStorageId() {
		return this.storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	@Column(name = "LUN_ID", length = 20)
	public String getLunId() {
		return this.lunId;
	}

	public void setLunId(String lunId) {
		this.lunId = lunId;
	}

	@Column(name = "LUN_SIZE", length = 20)
	public String getLunSize() {
		return this.lunSize;
	}

	public void setLunSize(String lunSize) {
		this.lunSize = lunSize;
	}

	@Column(name = "PURPOSE", length = 20)
	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Column(name = "IS_ACTIVE", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public boolean isLunInfoEqueal(StorageAssignResPo storageAssignResPo) {
		if(null == storageAssignResPo){
			return false;
		}
		
		String lunId = storageAssignResPo.getLunId();
		String purpose = storageAssignResPo.getPurpose();
		String hdisk = storageAssignResPo.getHdisk();
		
		boolean isEqueal = true;
		
		if(null == this.lunId || null == lunId
				|| !lunId.equals(this.lunId)) {
			isEqueal = false;
		}
		
		if(null == this.purpose || null == purpose
				|| !purpose.equals(this.purpose)) {
			isEqueal = false;
		}
		
		if(null == this.hdisk || null == hdisk
				|| !hdisk.equals(this.hdisk)) {
			isEqueal = false;
		}
		
		return isEqueal;
	}
	
	public String showInfo() {
		String msg = "HostID: " + this.hostId + ", HDisk: " + this.hdisk + ", LUN ID: " + this.lunId + ", Purpose: " + this.purpose;
		
		return msg;
	}

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="LUN_CREATE_TIME",length=20)
	public Date getLunCreateTime() {
		return lunCreateTime;
	}

	public void setLunCreateTime(Date lunCreateTime) {
		this.lunCreateTime = lunCreateTime;
	}

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="LUN_UPDATE_TIME",length=20)
	public Date getLunUpdateTime() {
		return lunUpdateTime;
	}

	public void setLunUpdateTime(Date lunUpdateTime) {
		this.lunUpdateTime = lunUpdateTime;
	}*/
}