package com.git.cloud.resmgt.compute.handler;

import java.util.List;

import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.resmgt.compute.model.vo.ScanVmResultVo;
import com.git.cloud.resmgt.compute.model.vo.VmVo;

public interface HostControllerService {
	/**
	 * 扫描物理机下的所有虚拟机
	 * @param hostId
	 * @return
	 * @throws Exception
	 */

	public List<ScanVmResultVo> scanVmFromHost(VmVo vm) throws Exception;
}
