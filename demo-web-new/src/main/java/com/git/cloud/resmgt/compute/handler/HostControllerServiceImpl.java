package com.git.cloud.resmgt.compute.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.resmgt.compute.model.vo.ScanVmResultVo;
import com.git.cloud.resmgt.compute.model.vo.VmVo;

public class HostControllerServiceImpl implements HostControllerService{
	@Autowired
	private HostControllerServiceVMWareImpl hostControllerServiceVMWareImpl;
	@Autowired
	private HostControllerServicePowerImpl hostControllerServicePowerImpl;

	@Override
	public List<ScanVmResultVo> scanVmFromHost(VmVo vm) throws Exception {
		List<ScanVmResultVo> scanVmResultVoList = null;
		String hostType = vm.getHostType();
		
		if(hostType.equals("VM")){
			scanVmResultVoList = hostControllerServiceVMWareImpl.scanVmFromHost(vm);
		}else if(hostType.equals("KVM")){
			 
		}else if(hostType.equals("PV")){
			scanVmResultVoList = hostControllerServicePowerImpl.scanVmFromHost(vm);
		}
		return scanVmResultVoList;
	}

}
