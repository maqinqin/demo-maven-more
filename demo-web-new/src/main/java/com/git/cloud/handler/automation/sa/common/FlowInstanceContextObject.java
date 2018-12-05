package com.git.cloud.handler.automation.sa.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <p>
 * power vm + aix + oracle + rac 流程执行的上下文参数对象
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-5-17
 * @see
 */
public class FlowInstanceContextObject {

	// 流程的全局参数
	private Map<String, String> contextParaMap = new HashMap<String, String>();

	/*
	 * 单个设备的参数 Map<设备id, Map<参数名称, 参数值>>
	 */
	private Map<String, Map<String, String>> deviceParaMap = new HashMap<String, Map<String, String>>();
	

	@Override
	public String toString() {
		return "PowerVMAIXOracleRACContextObject [contextParaMap="
				+ contextParaMap + ", deviceParaMap=" + deviceParaMap + "]";
	}

	/**
	 * 生产一个上下文参数map，格式为： map<device1, map<key, value>>, map<device2, map<key,
	 * value>>, ...
	 * 
	 * @return
	 */
	public Map<String, Map<String, String>> getContextDeviceMap() {

		for (String key : contextParaMap.keySet()) {
			putToAllDeviceMap(key, contextParaMap.get(key));
		}
		return deviceParaMap;
	}

	/**
	 * 将一个key, value 分别加入每个设备的map
	 * 
	 * @param key
	 * @param value
	 */
	private void putToAllDeviceMap(String key, String value) {
		for (String deviceId : deviceParaMap.keySet()) {
			deviceParaMap.get(deviceId).put(key, value);
		}
	}

	/**
	 * 设置context参数
	 * 
	 * @param key
	 * @param value
	 */
	public void setContextPara(String key, String value) {
		contextParaMap.put(key, value);
	}

	/**
	 * 获取全局参数
	 * 
	 * @param key
	 * @return
	 */
	public String getContextPara(String key) {
		return contextParaMap.get(key);
	}

	/**
	 * 设置设备的参数
	 * 
	 * @param deviceID
	 * @param key
	 * @param value
	 */
	public void setDevicePara(String deviceID, String key, String value) {
		Map<String, String> oneDeviceMap = deviceParaMap.get(deviceID);
		if (oneDeviceMap == null) {
			oneDeviceMap = new HashMap<String, String>();
			deviceParaMap.put(deviceID, oneDeviceMap);
		}
		oneDeviceMap.put(key, value);
	}

	/**
	 * 读取一个设备的一个参数
	 * 
	 * @param deviceID
	 * @param key
	 * @return
	 */
	public String getDevicePara(String deviceID, String key) {
		Map<String, String> oneDeviceMap = deviceParaMap.get(deviceID);
		return oneDeviceMap.get(key);
	}

	/**
	 * @return the contextParaMap
	 */
	public Map<String, String> getContextParaMap() {
		return contextParaMap;
	}

	/**
	 * @param contextParaMap
	 *            the contextParaMap to set
	 */
	public void setContextParaMap(Map<String, String> contextParaMap) {
		this.contextParaMap = contextParaMap;
	}

	/**
	 * @return the deviceParaMap
	 */
	public Map<String, Map<String, String>> getDeviceParaMap() {
		return deviceParaMap;
	}

	/**
	 * @param deviceParaMap
	 *            the deviceParaMap to set
	 */
	public void setDeviceParaMap(Map<String, Map<String, String>> deviceParaMap) {
		this.deviceParaMap = deviceParaMap;
	}

}
