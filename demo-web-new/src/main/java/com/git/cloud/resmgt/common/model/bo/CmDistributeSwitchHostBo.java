package com.git.cloud.resmgt.common.model.bo;

import com.git.cloud.common.model.base.BaseBO;
/**
 * 分布式交换机下添加物理主机
 * @author git
 *
 */
public class CmDistributeSwitchHostBo extends BaseBO{


	private static final long serialVersionUID = 1L;
	private String switchId;
	private String hostId;
	private String id;
	
	
	
	public String getSwitchId() {
		return switchId;
	}



	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}



	public String getHostId() {
		return hostId;
	}



	public void setHostId(String hostId) {
		this.hostId = hostId;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	@Override
	public String getBizId() {
		return null;
	}


}
