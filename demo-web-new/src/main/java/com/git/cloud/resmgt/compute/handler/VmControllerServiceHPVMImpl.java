package com.git.cloud.resmgt.compute.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.compute.model.vo.VmMonitorVo;
import com.git.support.common.VmGlobalConstants;

public class VmControllerServiceHPVMImpl implements VmControllerService{
	private static Logger log = LoggerFactory
			.getLogger(VmControllerServiceHPVMImpl.class);

	@Override
	public String vmRunningState(String vmId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> vmPoList, String virtualTypeCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}