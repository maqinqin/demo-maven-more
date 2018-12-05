package com.git.cloud.resmgt.compute.handler;

import java.util.List;

import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.ScanResult;

public interface ScanDeviceStatusService {
	/**
	 * 扫描设备运行状态，并将其更新到数据库中
	 * @author 王明月
	 * @param deviceId 设备ID
	 * @return 返回值为设备运行状态
	 * @throws Exception
	 */
	public ScanResult scanAndUpdateDeviceIndicator(String deviceId) throws Exception;
	
	/**
	 * 
	 * @author 王明月
	 * @param deviceIds
	 * @return
	 * @throws Exception
	 */
	public List<ScanResult> scanAndUpdateHostIndicator(List<CmDevicePo> hostIds) throws Exception;
	/**
	 * 根据虚拟化类型virtualTypeCode，扫描并且更新虚拟机状态
	 * @author 王明月
	 * @param vmPoList
	 * @param virtualTypeCode
	 * @return
	 * @throws Exception
	 */
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> vmPoList,String virtualTypeCode) throws Exception;
}
