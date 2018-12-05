package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;
// default package



/**
 * StorageSu entity. @author MyEclipse Persistence Tools
 */
public class StorageSuPo  extends BaseBO implements java.io.Serializable {


    // Fields    

     /**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 8254328345324140053L;
	
	
	private String suId;
     private String storageChildResPoolId;
//     private String suName;
//     private String remark;
     private String isActive;
     private String fabricId1;
	private String fabricId2;
     private String storageMgrId;

    // Constructors

    public String getStorageMgrId() {
		return storageMgrId;
	}

	public void setStorageMgrId(String storageMgrId) {
		this.storageMgrId = storageMgrId;
	}

	/** default constructor */
    public StorageSuPo() {
    }
    
    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSuId() {
		return suId;
	}

	public String getStorageChildResPoolId() {
		return storageChildResPoolId;
	}

	public void setSuId(String suId) {
		this.suId = suId;
	}

	public void setStorageChildResPoolId(String storageChildResPoolId) {
		this.storageChildResPoolId = storageChildResPoolId;
	}

    public String getFabricId1() {
		return fabricId1;
	}

	public String getFabricId2() {
		return fabricId2;
	}

	public void setFabricId1(String fabricId1) {
		this.fabricId1 = fabricId1;
	}

	public void setFabricId2(String fabricId2) {
		this.fabricId2 = fabricId2;
	}

}