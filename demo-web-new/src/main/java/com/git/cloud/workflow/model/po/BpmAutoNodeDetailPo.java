/**
 * @Title:BpmAutoNodeDetailPo.java
 * @Package:com.git.cloud.workflow.model.po
 * @Description:TODO
 * @author libin
 * @date 2014-10-17 上午10:27:24
 * @version V1.0
 */
package com.git.cloud.workflow.model.po;

import java.io.Serializable;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:BpmAutoNodeDetailPo
 * @Description:TODO
 * @author libin
 * @date 2014-10-17 上午10:27:24
 *
 *
 */
public class BpmAutoNodeDetailPo extends BaseBO implements Serializable{

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Long detailId;
	private Long recorderId;
	private Long deviceId;
	private String deviceName;
	private Integer resultStatus;	// 1：成功，2：失败
	private String resultMesg;
	
	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getRecorderId() {
		return recorderId;
	}

	public void setRecorderId(Long recorderId) {
		this.recorderId = recorderId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getResultMesg() {
		return resultMesg;
	}

	public void setResultMesg(String resultMesg) {
		this.resultMesg = resultMesg;
	}

	/* (non-Javadoc)
	 * <p>Title:getBizId</p>
	 * <p>Description:</p>
	 * @return
	 * @see com.git.cloud.common.model.base.BaseBO#getBizId()
	 */
	@Override
	public String getBizId() {
		// TODO Auto-generated method stub
		return null;
	}

}
