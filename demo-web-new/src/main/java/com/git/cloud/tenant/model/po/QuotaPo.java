package com.git.cloud.tenant.model.po;



import com.git.cloud.common.model.base.BaseBO;

/**
 * 租户配额
 * @author
 */
public class QuotaPo extends BaseBO implements java.io.Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6519577748840786940L;
	
	private String id;
	private String tenantId;
	private String quotaConfigId;
	private String projectId;
	private String dataCnterId;
	private String value;
	private String code;
	/**
	 * 已使用的值
	 */
	private String usedValue;
	/**
	 * 平台code
	 */
	private String platformCode;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getQuotaConfigId() {
		return quotaConfigId;
	}

	public void setQuotaConfigId(String quotaConfigId) {
		this.quotaConfigId = quotaConfigId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDataCnterId() {
		return dataCnterId;
	}

	public void setDataCnterId(String dataCnterId) {
		this.dataCnterId = dataCnterId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUsedValue() {
		return usedValue;
	}

	public void setUsedValue(String usedValue) {
		this.usedValue = usedValue;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
