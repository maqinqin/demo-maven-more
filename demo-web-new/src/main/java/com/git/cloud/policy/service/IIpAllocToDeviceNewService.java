package com.git.cloud.policy.service;

import java.util.List;

import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
/**
 * @Title 		IIpAllocToDeviceService.java 
 * @Package 	com.git.cloud.policy.service 
 * @author 		sunyp,wxg
 * @date 		2014-9-19下午2:24:21
 * @version 	1.0.0
 * @Description  
 *
 */
public interface IIpAllocToDeviceNewService {
	/**
	 * 获取虚拟机ip信息
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	List<DeviceNetIP> qryAllocedIPForDevices(String deviceId) throws Exception;
	/**
	 * 获取物理机ip
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	DeviceNetIP qryAllocedIPForHost(String deviceId) throws Exception;
}

