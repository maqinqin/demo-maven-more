package com.git.cloud.resmgt.compute.handler;

import java.util.ArrayList;
import java.util.List;

import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.common.service.ICmDeviceService;

public class VmControllerServiceImpl implements VmControllerService{
	private ICmDeviceService iCmDeviceService;
	private VmControllerServiceVMWareImpl vmwareControllerService;
	private VmControllerServiceKVMImpl kvmControllerService;
	private VmControllerServicePOWERVMImpl powerControlService;
	private INotificationService notiServiceImpl;
    private ICmVmDAO cmVmDAO;
    private VmControllerServiceOpenstackImpl openstackControllerService;
    
    private VmControllerServicePowerVcImpl powerVcControllerService;
    
    
	public VmControllerServicePowerVcImpl getPowerVcControllerService() {
		return powerVcControllerService;
	}
	public void setPowerVcControllerService(VmControllerServicePowerVcImpl powerVcControllerService) {
		this.powerVcControllerService = powerVcControllerService;
	}
	public VmControllerServiceOpenstackImpl getOpenstackControllerService() {
		return openstackControllerService;
	}
	public void setOpenstackControllerService(
			VmControllerServiceOpenstackImpl openstackControllerService) {
		this.openstackControllerService = openstackControllerService;
	}


	public ICmVmDAO getCmVmDAO() {
		return cmVmDAO;
	}


	public void setCmVmDAO(ICmVmDAO cmVmDAO) {
		this.cmVmDAO = cmVmDAO;
	}


	public INotificationService getNotiServiceImpl() {
		return notiServiceImpl;
	}


	public void setNotiServiceImpl(INotificationService notiServiceImpl) {
		this.notiServiceImpl = notiServiceImpl;
	}
	public VmControllerServicePOWERVMImpl getPowerControlService() {
		return powerControlService;
	}

	public void setPowerControlService(VmControllerServicePOWERVMImpl powerControlService) {
		this.powerControlService = powerControlService;
	}

	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}

	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}

	public VmControllerServiceKVMImpl getKvmControllerService() {
		return kvmControllerService;
	}

	public void setKvmControllerService(VmControllerServiceKVMImpl kvmControllerService) {
		this.kvmControllerService = kvmControllerService;
	}

	public VmControllerServiceVMWareImpl getVmwareControllerService() {
		return vmwareControllerService;
	}

	public void setVmwareControllerService(
			VmControllerServiceVMWareImpl vmwareControllerService) {
		this.vmwareControllerService = vmwareControllerService;
	}

	public String vmRunningState(String vmId) throws Exception{
		String result = "";
		CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(vmId);
		CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(cmDeviceVMShowBo.getHostId());
		RmVirtualTypePo vmType = iCmDeviceService.findVirtualTypeById(cmDeviceHostShowBo.getClusterId());
		String virtualTypeCode = vmType.getVirtualTypeCode();
		if(RmVirtualType.VMWARE.getValue().equalsIgnoreCase(virtualTypeCode)){
			result = vmwareControllerService.vmRunningState(vmId);
		}else if(RmVirtualType.KVM.getValue().equalsIgnoreCase(virtualTypeCode)){
			result = kvmControllerService.vmRunningState(vmId);
		}else if("4".equals(vmType.getPlatformId())){//OKV
			result = openstackControllerService.vmRunningState(vmId);
		}else if("5".equals(vmType.getPlatformId())){//powervc
			result = powerVcControllerService.vmRunningState(vmId);
		}
		return result;
	}

	@Override
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> vmPoList,
			String virtualTypeCode) throws Exception {
		List<ScanResult> scanResultList = new ArrayList<ScanResult>();
		/*if(virtualTypeCode.equals("VM")){
			scanResultList =  vmwareControllerService.scanAndUpdateVmIndicator(vmPoList, virtualTypeCode);
		}else */if(virtualTypeCode.equals("KV")){
			scanResultList =  kvmControllerService.scanAndUpdateVmIndicator(vmPoList, virtualTypeCode);
		}else if(virtualTypeCode.equals("PV")){
			scanResultList =  powerControlService.scanAndUpdateVmIndicator(vmPoList, virtualTypeCode);
		}
		return scanResultList;
	}
}