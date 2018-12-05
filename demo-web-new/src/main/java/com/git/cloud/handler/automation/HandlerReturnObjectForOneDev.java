package com.git.cloud.handler.automation;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.git.cloud.handler.common.Utils;

/**
 * 一个device执行handler的日志信息
 * @author zhuzhaoyong.co
 *
 */
public class HandlerReturnObjectForOneDev {

	private String deviceId;
	private String returnCode;
	private List<String> echos;
	private List<String> errors;
	private String returnMesg;
	
	@Override
	public String toString() {
		return "HandlerReturnObjectForOneDev [deviceId=" + deviceId
				+ ", returnCode=" + returnCode + ", echos=" + echos
				+ ", errors=" + errors + "]";
	}
	
	public String toJsonString() {
		String jsonString = null;
		jsonString = JSON.toJSONString(this);
		return jsonString;
	}
	
	public static HandlerReturnObjectForOneDev parse(String str) {
		JSON json = (JSON) JSON.parse(str);
		HandlerReturnObjectForOneDev returnObj = JSON.toJavaObject(json, HandlerReturnObjectForOneDev.class);
		return returnObj;
	}
	
	public String getMesg() {
		StringBuffer sbMesg = new StringBuffer();
		sbMesg.append("RET_MESG:" + returnMesg);
		sbMesg.append(Utils.BR);
		sbMesg.append("Errors: ").append(Utils.BR);
		if (errors != null) {
			for (int i = 0; i < errors.size(); i++) {
				if (i != 0) {
					sbMesg.append(Utils.BR);
				}
				sbMesg.append(Utils.TAB).append(errors.get(i));
			}
		}
		sbMesg.append(Utils.BR);
		sbMesg.append("Echos: ").append(Utils.BR);
		if (echos != null) {
			for (int i = 0; i < echos.size(); i++) {
				if (i != 0) {
					sbMesg.append(Utils.BR);
				}
				sbMesg.append(Utils.TAB).append(echos.get(i)).append(Utils.BR);
			}
		}
		return sbMesg.toString();
	}
	public String getErrorMesg() {
		StringBuffer resultsMesg = new StringBuffer();
		resultsMesg.append("RET_MESG:" + returnMesg);
		if (errors != null && errors.size() > 0) {
			for (String string : errors) {
				if (resultsMesg.length() > 0) {
					resultsMesg.append(Utils.BR);
				}
				resultsMesg.append(string);
			}
		}
		return resultsMesg.toString();
	}
	public HandlerReturnObjectForOneDev() {
		super();
		// TODO Auto-generated constructor stub
	}


	public HandlerReturnObjectForOneDev(String deviceId, String returnCode,
			List<String> echos, List<String> errors) {
		super();
		this.deviceId = deviceId;
		this.returnCode = returnCode;
		this.echos = echos;
		this.errors = errors;
	}


	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public List<String> getEchos() {
		if (echos == null) {
			echos = new ArrayList<String>();
		}
		return echos;
	}
	public void setEchos(List<String> echos) {
		this.echos = echos;
	}
	public List<String> getErrors() {
		if (errors == null) {
			errors = new ArrayList<String>();
		}
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getReturnMesg() {
		return returnMesg;
	}

	public void setReturnMesg(String returnMesg) {
		this.returnMesg = returnMesg;
	}

	public static void main(String[] args) {
		System.out.println(HandlerReturnObjectForOneDev.parse("dddd"));
	}
}
