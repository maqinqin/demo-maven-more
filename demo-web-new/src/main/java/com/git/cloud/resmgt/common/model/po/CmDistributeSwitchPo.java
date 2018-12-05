package com.git.cloud.resmgt.common.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

public class CmDistributeSwitchPo extends BaseBO implements Serializable {

	private String switch_id;
	private String dataCenterName;
	private String switchName;
	private String isActive;
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSwitch_id() {
		return switch_id;
	}

	public void setSwitch_id(String switch_id) {
		this.switch_id = switch_id;
	}

	public String getSwitchName() {
		return switchName;
	}

	public String getDataCenterName() {
		return dataCenterName;
	}

	public void setDataCenterName(String dataCenterName) {
		this.dataCenterName = dataCenterName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String getBizId() {
		return null;
	}

}
