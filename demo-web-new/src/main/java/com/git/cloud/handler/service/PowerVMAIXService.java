package com.git.cloud.handler.service;

import java.util.List;

import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.sa.powervm.PowerVMAIXOracleRACContextObject;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;


/**
 * 
 * <p> power虚拟机服务
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-5-22 
 * @see
 */
public interface PowerVMAIXService {

	/**
	 * 生成虚拟机基本参数信息
	 * @param contextObj
	 * @param deviceIdList
	 */
	public void makeVmBasePara(PowerVMAIXOracleRACContextObject contextObj, List<String> deviceIdList) throws BizException;

	/**
	 * 生成路由参数信息
	 * make route para
	 * @param contextObj
	 * @param deviceIdList
	 * @throws Exception
	 */
	public void makeRoutePara(PowerVMAIXOracleRACContextObject contextObj, List<String> deviceIdList, String rrinfoId) throws Exception;

	/**
	 * make ntp para
	 * @param contextObj
	 * @throws Exception
	 */
	public void makeNtpPara(PowerVMAIXOracleRACContextObject contextObj, String rrinfoId) throws Exception;

	/**
	 * @param contextObj
	 * @param deviceIdList
	 */
	public void makeCpuPara(PowerVMAIXOracleRACContextObject contextObj, List<String> deviceIdList);

	/**
	 * @param contextObj
	 * @param deviceNetIP
	 * @param vmIPs
	 */
	public void makeProductIp(PowerVMAIXOracleRACContextObject contextObj, DeviceNetIP deviceNetIP, List<NetIPInfo> vmIPs);
	/**
	 * 读取设备ip地址信息
	 * @param deviceIdList
	 * @param ipAllocToVMService
	 * @return
	 * @throws Exception
	 */
	public List<DeviceNetIP> getDeviceNetIp(List<String> deviceIdList) throws Exception;
	
	/**
	 * @param contextObj
	 * @param deviceID
	 * @param vmIPs
	 */
	public void makeViocIp(PowerVMAIXOracleRACContextObject contextObj, String deviceID, List<NetIPInfo> vmIPs);

	/**
	 * @param contextObj
	 * @param rrinfoId
	 * @param deviceIdList
	 * @throws Exception
	 */
	void makeFlowContextObject(PowerVMAIXOracleRACContextObject contextObj, String rrinfoId, List<String> deviceIdList)
			throws Exception;

}
