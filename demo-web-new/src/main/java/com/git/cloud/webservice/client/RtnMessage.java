package com.git.cloud.webservice.client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.git.cloud.resmgt.common.util.mapxml.MyHashMapObject;

@XmlRootElement(name = "ReturnMessage")
public class RtnMessage<U, T> {
	
	private String errNo = "";
	
	private String errMsg = "";
	
	private MyHashMapObject<T, U> map = null;

	public String getErrNo() {
		return errNo;
	}

	public void setErrNo(String errNo) {
		this.errNo = errNo;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	@XmlElement
	public MyHashMapObject<T, U> getMap() {
		return map;
	}

	public void setMap(MyHashMapObject<T, U> map) {
		this.map = map;
	}
	
	
}
