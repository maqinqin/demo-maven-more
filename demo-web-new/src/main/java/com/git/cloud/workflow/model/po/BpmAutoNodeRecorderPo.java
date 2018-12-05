/**
 * @Title:BpmAutoNodeRecorderPo.java
 * @Package:com.git.cloud.workflow.model.po
 * @Description:TODO
 * @author libin
 * @date 2014-10-17 上午11:04:14
 * @version V1.0
 */
package com.git.cloud.workflow.model.po;

import java.io.Serializable;
import java.util.Date;

import com.git.cloud.common.model.base.BaseBO;

/**
 * @ClassName:BpmAutoNodeRecorderPo
 * @Description:TODO
 * @author libin
 * @date 2014-10-17 上午11:04:14
 *
 *
 */
public class BpmAutoNodeRecorderPo extends BaseBO implements Serializable{

	/**
	 * @Fields serialVersionUID:long TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String recorderId;
	private String instanceId;
	private Long nodeId;
	private Date execTime;
	
	public String getRecorderId() {
		return recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Date getExecTime() {
		return execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
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
