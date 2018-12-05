package com.git.cloud.workflow.model.po;


public class BpmModelInfnodePo implements java.io.Serializable {

	private static final long serialVersionUID = -3895381090850433834L;
	private Long infnodeId;
	private String infnodeName;
	private Long infnodeType;
	private String reqType;
	private String reqKey;
	private Long modelId;
	private Long wfNodeId;
	public Long getInfnodeId() {
		return infnodeId;
	}
	public void setInfnodeId(Long infnodeId) {
		this.infnodeId = infnodeId;
	}
	public String getInfnodeName() {
		return infnodeName;
	}
	public void setInfnodeName(String infnodeName) {
		this.infnodeName = infnodeName;
	}
	public Long getInfnodeType() {
		return infnodeType;
	}
	public void setInfnodeType(Long infnodeType) {
		this.infnodeType = infnodeType;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getReqKey() {
		return reqKey;
	}
	public void setReqKey(String reqKey) {
		this.reqKey = reqKey;
	}
	public Long getModelId() {
		return modelId;
	}
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	public Long getWfNodeId() {
		return wfNodeId;
	}
	public void setWfNodeId(Long wfNodeId) {
		this.wfNodeId = wfNodeId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}