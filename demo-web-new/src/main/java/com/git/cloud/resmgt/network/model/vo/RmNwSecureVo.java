package com.git.cloud.resmgt.network.model.vo;

import com.git.cloud.common.model.base.BaseBO;

public class RmNwSecureVo extends BaseBO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	/* secure area */
	private String secureAreaId;
	private String secureAreaName;
	private String isActive;
	private String secureAreaType;
	private String poolType;
	private String secureAreaCode;
	private String sevureTierCode;
	private String errorMsg;
	
	/* secure tier*/
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	private String secureTierId;
	private String secureTierName;

	public String getSecureAreaId() {
		return secureAreaId;
	}
   public void setSecureAreaId(String secureAreaId) {
		this.secureAreaId = secureAreaId;
	}
  public String getSecureAreaName() {
		return secureAreaName;
	}
  public void setSecureAreaName(String secureAreaName) {
		this.secureAreaName = secureAreaName;
	}
 public String getIsActive() {
		return isActive;
	}

  public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
  public String getSecureAreaType() {
		return secureAreaType;
	}

   public void setSecureAreaType(String secureAreaType) {
		this.secureAreaType = secureAreaType;
	}

  public String getPoolType() {
		return poolType;
	}
  public void setPoolType(String poolType) {
		this.poolType = poolType;
	}

  public String getSecureAreaCode() {
		return secureAreaCode;
	}

   public void setSecureAreaCode(String secureAreaCode) {
		this.secureAreaCode = secureAreaCode;
	}
   public String getSevureTierCode() {
		return sevureTierCode;
	}

	public void setSevureTierCode(String sevureTierCode) {
		this.sevureTierCode = sevureTierCode;
	}
  public String getSecureTierId() {
		return secureTierId;
	}

 public void setSecureTierId(String secureTierId) {
		this.secureTierId = secureTierId;
	}

public String getSecureTierName() {
		return secureTierName;
	}

  public void setSecureTierName(String secureTierName) {
		this.secureTierName = secureTierName;
	}

  public RmNwSecureVo(){
	
  }

  public RmNwSecureVo(String secureAreaId, String secureAreaName,String isActive,String secureAreaType,String poolType, String secureAreaCode,
	 String  sevureTierCode,String secureTierId,String secureTierName ){
	          this.secureAreaId=secureAreaId;
			  this.secureAreaName=secureAreaName;
			  this.isActive=isActive;
			  this.secureAreaType=secureAreaType;
			  this.poolType=poolType;
			  this.secureAreaCode=secureAreaCode;
			  this.sevureTierCode=sevureTierCode;
			  this.secureTierId=secureTierId;
			  this.secureTierName=secureTierName;
		
  }

	@Override
	public String getBizId() {
		return null;
	}
}
