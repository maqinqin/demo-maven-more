package com.git.cloud.handler.automation;

import java.util.ArrayList;
import java.util.List;

import com.git.support.common.MesgRetCode;

/**
 * handler执行结果对象
 * @author zhuzhaoyong.co
 *
 */
public class HandlerReturnObject {
	
	private String rrInfoId;
	private String srInfoId;
	private String instanceId;
	private String nodeId;
	private String returnCode;
	private List<HandlerReturnObjectForOneDev> devObjects = new ArrayList<HandlerReturnObjectForOneDev>();
	
	
	public HandlerReturnObject() {
		super();
	}

	public HandlerReturnObject(String returnCode,
			List<HandlerReturnObjectForOneDev> devObjects) {
		super();
		this.returnCode = returnCode;
		this.devObjects = devObjects;
	}
	
	public void addOneObject(HandlerReturnObjectForOneDev newObj) {
		this.devObjects.add(newObj);
	}
	
	@Override
	public String toString() {
		return "HandlerReturnObject [returnCode=" + returnCode
				+ ", devObjects=" + devObjects + "]";
	}
	public String getReturnCode() {
		// 生成code
		if (this.returnCode != null) {
			return returnCode;
		} else {
			returnCode = MesgRetCode.SUCCESS;
			for (HandlerReturnObjectForOneDev oneObj : this.getDevObjects()) {
				if (oneObj.getReturnCode() == null) {
					returnCode = null;
					break;
				} else if (!oneObj.getReturnCode().equals(MesgRetCode.SUCCESS)) {
					returnCode = oneObj.getReturnCode();
					break;
				}
			}
		}
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public List<HandlerReturnObjectForOneDev> getDevObjects() {
		return devObjects;
	}

	public void setDevObjects(List<HandlerReturnObjectForOneDev> devObjects) {
		this.devObjects = devObjects;
	}

	public String getRrInfoId() {
		return rrInfoId;
	}

	public void setRrInfoId(String rrInfoId) {
		this.rrInfoId = rrInfoId;
	}

	public String getSrInfoId() {
		return srInfoId;
	}

	public void setSrInfoId(String srInfoId) {
		this.srInfoId = srInfoId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
}
