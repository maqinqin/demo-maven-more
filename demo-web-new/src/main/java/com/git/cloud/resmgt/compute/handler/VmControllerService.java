package com.git.cloud.resmgt.compute.handler;

import java.util.List;

import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.compute.model.vo.VmMonitorVo;

/**
 * 获取虚机状态，操作虚机的接口
 * @author 王明月
 *
 */
public interface VmControllerService {
	/**
	 * 获取虚机状态的接口
	 * @param vmId
	 * @return poweredOn（运行中），poweredOff（已关闭），suspended（已挂起）
	 * @throws Exception
	 */
	public String vmRunningState(String vmId) throws Exception;
	/**
	 * 关机（VmGlobalConstants.VM_SHUTDOWN）
	 * @param vmId
	 * @throws Exception
	 */
	/**
	 * 扫描虚拟机状态
	 * @param vmPoList
	 * @return
	 * @throws Exception
	 */
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> vmPoList,String virtualTypeCode)throws Exception;
}