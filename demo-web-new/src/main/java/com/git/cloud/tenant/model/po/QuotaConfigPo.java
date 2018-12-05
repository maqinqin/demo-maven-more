package com.git.cloud.tenant.model.po;



import com.git.cloud.common.model.base.BaseBO;

public class QuotaConfigPo extends BaseBO implements java.io.Serializable{

	
	private static final long serialVersionUID = 2340230961459163220L;	
	
	
	private String id;	
	private String platformTypeCode;
	private String name;
	private String code;
	private String ordernum;
	private String unit;
	
	public QuotaConfigPo(String id, String platformTypeCode, String name, String code, String ordernum, String unit) {
		super();
		this.id = id;
		this.platformTypeCode = platformTypeCode;
		this.name = name;
		this.code = code;
		this.ordernum = ordernum;
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformTypeCode() {
		return platformTypeCode;
	}

	public void setPlatformTypeCode(String platformTypeCode) {
		this.platformTypeCode = platformTypeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public QuotaConfigPo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	

}
