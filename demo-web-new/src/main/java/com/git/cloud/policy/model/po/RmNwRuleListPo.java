package com.git.cloud.policy.model.po;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @Title 		RmNwRuleListPo.java 
 * @Package 	com.git.cloud.policy.model.po 
 * @author 		wxg
 * @date 		2014-9-25 上午午10:00:09
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwRuleListPo  extends BaseBO  implements java.io.Serializable{

	private String rmNwRuleListId;//主键
	private String rmNwRuleId;
	private String useCode;
	private int actNum;
	private int occNum;//占位个数
	private String occSite;//占位规则
	private String rmIpTypeName;
	private String isActive;//是否有效
	private String vmwarePGPEFIX;
	private int totalNum;//总占位个数
	private String secureAreaType;//安全区域编码
	private String secureLayer;//安全分层编码
	private String convergeId;//网络汇聚ID
	private String datacenterId;//数据中心ID
	private String useId;
	

	// Constructors

	/** default constructor */
	public RmNwRuleListPo() {
	}



	public RmNwRuleListPo(String rmNwRuleListId, String rmNwRuleId,
			String useCode, int actNum, int occNum, String occSite,
			String rmIpTypeName, String isActive, String vmwarePGPEFIX,
			int totalNum, String secureAreaType, String secureLayer,
			String convergeId, String datacenterId) {
		super();
		this.rmNwRuleListId = rmNwRuleListId;
		this.rmNwRuleId = rmNwRuleId;
		this.useCode = useCode;
		this.actNum = actNum;
		this.occNum = occNum;
		this.occSite = occSite;
		this.rmIpTypeName = rmIpTypeName;
		this.isActive = isActive;
		this.vmwarePGPEFIX = vmwarePGPEFIX;
		this.totalNum = totalNum;
		this.secureAreaType = secureAreaType;
		this.secureLayer = secureLayer;
		this.convergeId = convergeId;
		this.datacenterId = datacenterId;
	}



	public String getUseId() {
		return useId;
	}



	public void setUseId(String useId) {
		this.useId = useId;
	}



	public String getOccSite() {
		return occSite;
	}



	public void setOccSite(String occSite) {
		this.occSite = occSite;
	}



	public String getRmNwRuleListId() {
		return rmNwRuleListId;
	}


	public void setRmNwRuleListId(String rmNwRuleListId) {
		this.rmNwRuleListId = rmNwRuleListId;
	}


	public String getRmNwRuleId() {
		return rmNwRuleId;
	}


	public void setRmNwRuleId(String rmNwRuleId) {
		this.rmNwRuleId = rmNwRuleId;
	}


	public String getUseCode() {
		return useCode;
	}


	public void setUseCode(String useCode) {
		this.useCode = useCode;
	}


	public int getActNum() {
		return actNum;
	}


	public void setActNum(int actNum) {
		this.actNum = actNum;
	}


	public int getOccNum() {
		return occNum;
	}


	public void setOccNum(int occNum) {
		this.occNum = occNum;
	}


	public String getRmIpTypeName() {
		return rmIpTypeName;
	}


	public void setRmIpTypeName(String rmIpTypeName) {
		this.rmIpTypeName = rmIpTypeName;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public int getTotalNum() {
		return totalNum;
	}


	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}


	public String getSecureAreaType() {
		return secureAreaType;
	}


	public void setSecureAreaType(String secureAreaType) {
		this.secureAreaType = secureAreaType;
	}


	public String getSecureLayer() {
		return secureLayer;
	}


	public void setSecureLayer(String secureLayer) {
		this.secureLayer = secureLayer;
	}


	public String getConvergeId() {
		return convergeId;
	}


	public void setConvergeId(String convergeId) {
		this.convergeId = convergeId;
	}

	public String getVmwarePGPEFIX() {
		return vmwarePGPEFIX;
	}


	public void setVmwarePGPEFIX(String vmwarePGPEFIX) {
		this.vmwarePGPEFIX = vmwarePGPEFIX;
	}

	public String getDatacenterId() {
		return datacenterId;
	}


	public void setDatacenterId(String datacenterId) {
		this.datacenterId = datacenterId;
	}


	@Override
	public String toString() {
		return "RmNwRuleListPo [rmNwRuleListId=" + rmNwRuleListId
				+ ", rmNwRuleId=" + rmNwRuleId + ", useCode=" + useCode
				+ ", actNum=" + actNum + ", occNum=" + occNum
				+ ", rmIpTypeName=" + rmIpTypeName + ", isActive=" + isActive
				+ ", totalNum=" + totalNum + "]";
	}


	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
