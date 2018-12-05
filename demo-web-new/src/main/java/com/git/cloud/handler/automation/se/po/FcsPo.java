package com.git.cloud.handler.automation.se.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * 
 * <p>
 * 
 * @author huaili
 * @version 1.0 2013-5-2
 * @see
 
@Entity
@Table(name = "CM_HBAFCRULE")*/
public class FcsPo  extends BaseBO implements java.io.Serializable{
	
//	@Id
//	@Column(name = "HBAFCRULE_ID", unique = true, nullable = false)
//	@Column(name = "FCS", length = 20)
//	@Column(name = "FCPORTNAME", length = 20)
//	@Column(name = "PORTGROUP", length = 20)
//	@Column(name = "FABRICNAME", length = 20)
//	@Column(name = "FABRIC_ID", length = 20)
	private String hbaFCRuleId;	
	private String fcs;	
	private String fcPortName;	
	private String portGroup;	
	private String fabricName;	
	private String fabricId;//


	public String getHbaFCRuleId() {
		return hbaFCRuleId;
	}

	public void setHbaFCRuleId(String hbaFCRuleId) {
		this.hbaFCRuleId = hbaFCRuleId;
	}

	public String getFcs() {
		return fcs;
	}

	public void setFcs(String fcs) {
		this.fcs = fcs;
	}

	public String getFcPortName() {
		return fcPortName;
	}

	public void setFcPortName(String fcPortName) {
		this.fcPortName = fcPortName;
	}

	public String getPortGroup() {
		return portGroup;
	}

	public void setPortGroup(String portGroup) {
		this.portGroup = portGroup;
	}

	public String getFabricName() {
		return fabricName;
	}

	public void setFabricName(String fabricName) {
		this.fabricName = fabricName;
	}

	public String getFabricId() {
		return fabricId;
	}

	public void setFabricId(String fabricId) {
		this.fabricId = fabricId;
	}

	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
