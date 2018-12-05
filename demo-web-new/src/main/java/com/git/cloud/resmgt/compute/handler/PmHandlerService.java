package com.git.cloud.resmgt.compute.handler;

import java.util.HashMap;
import java.util.List;

/**
 * 获取物理机状态，操作物理机的接口
 * @author 
 *
 */
public interface PmHandlerService {
	/**
	 * 获取物理机状态的接口
	 * @param pmIp
	 * @return 
	 * @throws Exception
	 */
	public String pmRunningState(String pmId) throws Exception;
	/**
	 * 物理机进入维护模式
	 * @param pmId
	 * @return
	 * @throws Exception
	 */
	public String maintenanceMode(String pmId)throws Exception;
	/**
	 * 物理机退出维护模式
	 * @param pmId
	 * @return
	 * @throws Exception
	 */
	public String exitMaintenanceMode(String pmId)throws Exception;
	/**
	 * 关机
	 * @param pmIp
	 * @throws Exception
	 */
	public String pmCloseByVc(String pmId) throws Exception ;
	/**
	 * 重启
	 * @param pmIp
	 * @throws Exception
	 */
	public String pmRebootByVc(String pmId) throws Exception ;
	/**未输入用户名密码的情况
	 * IPMI物理机传感器信息获取接口
	 */
	public List<String[]> getPmSensorInfo(String id,String pmIp,String ipmi_ver,String ipmi_Name,String ipmi_Pword) throws Exception;
	/**输入用户名和密码的情况
	 * IPMI物理机传感器信息获取接口
	 * @param hostId物理机ID
	 * @return
	 * @throws Exception
	 */
	public List<String[]> getPmSensorInfoById(String hostId) throws Exception;
	/**
	 * 对物理机在vc上进行控制
	 * @param pmId
	 * @return
	 * @throws Exception
	 */
	public String pmPowerOperationByVc(String pmId,String operation) throws Exception ;
}
