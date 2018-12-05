package com.git.cloud.resmgt.compute.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;

@Service
public class ScanDeviceStatusServiceImpl implements ScanDeviceStatusService{
	private static Logger log = LoggerFactory.getLogger(ScanDeviceStatusServiceImpl.class);
	private ICmDeviceService iCmDeviceService;
	private VmControllerServiceVMWareImpl vmwareControllerService;
	private VmControllerServiceKVMImpl kvmControllerService;
	private VmControllerServicePOWERVMImpl powerControllerService;
	
	private IRmVmManageServerDAO rmVmMgServerDAO;
	private AutomationService automationService;
	private ICmHostDAO cmHostDAO;
	private IRmDatacenterDAO rmDatacenterDAO;
	private ResAdptInvokerFactory resInvokerFactory;
	private ICmPasswordDAO cmPasswordDAO;
	private VmControllerServiceImpl vmControllers;
	
	
	public VmControllerServiceImpl getVmControllers() {
		return vmControllers;
	}

	public void setVmControllers(VmControllerServiceImpl vmControllers) {
		this.vmControllers = vmControllers;
	}

	public ICmPasswordDAO getCmPasswordDAO() {
		return cmPasswordDAO;
	}

	public void setCmPasswordDAO(ICmPasswordDAO cmPasswordDAO) {
		this.cmPasswordDAO = cmPasswordDAO;
	}

	public ResAdptInvokerFactory getResInvokerFactory() {
		return resInvokerFactory;
	}

	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}

	public IRmDatacenterDAO getRmDatacenterDAO() {
		return rmDatacenterDAO;
	}

	public void setRmDatacenterDAO(IRmDatacenterDAO rmDatacenterDAO) {
		this.rmDatacenterDAO = rmDatacenterDAO;
	}

	public ICmHostDAO getCmHostDAO() {
		return cmHostDAO;
	}

	public void setCmHostDAO(ICmHostDAO cmHostDAO) {
		this.cmHostDAO = cmHostDAO;
	}

	public AutomationService getAutomationService() {
		return automationService;
	}

	public void setAutomationService(AutomationService automationService) {
		this.automationService = automationService;
	}

	public IRmVmManageServerDAO getRmVmMgServerDAO() {
		return rmVmMgServerDAO;
	}

	public void setRmVmMgServerDAO(IRmVmManageServerDAO rmVmMgServerDAO) {
		this.rmVmMgServerDAO = rmVmMgServerDAO;
	}

	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}

	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}

	@Override
	public ScanResult scanAndUpdateDeviceIndicator(String deviceId)	throws Exception {
		CmDevicePo cmDevicePo = iCmDeviceService.selectCmDevicePoById(deviceId);
		//首先判断设备类型
		String hostType = cmDevicePo.getHostType();
		String virtualType = cmDevicePo.getVirtualType();
		if(hostType.equals(RmHostType.PHYSICAL.getValue())){
			//物理机，调用获取物理机状态的方法
		}
		else if(hostType.equals(RmHostType.VIRTUAL.getValue())){
			String state = null;
			//虚拟机，根据虚拟化类型，调用获取电源状态的接口
			if(RmVirtualType.VMWARE.getValue().equalsIgnoreCase(virtualType)){
				state = vmwareControllerService.vmRunningState(deviceId);
			}else if(RmVirtualType.KVM.getValue().equalsIgnoreCase(virtualType)){
				state = kvmControllerService.vmRunningState(deviceId);
			} else if(RmVirtualType.POWERVM.getValue().equalsIgnoreCase(virtualType)){
				state = powerControllerService.vmRunningState(deviceId);
			}
			cmDevicePo.setRunningState(state);
			iCmDeviceService.updateCmdeviceRunningState(cmDevicePo);
		}
		
		//将设备状态进行保存
		return null;
	}

	public VmControllerServiceVMWareImpl getVmwareControllerService() {
		return vmwareControllerService;
	}
	public void setVmwareControllerService(VmControllerServiceVMWareImpl vmwareControllerService) {
		this.vmwareControllerService = vmwareControllerService;
	}
	public VmControllerServiceKVMImpl getKvmControllerService() {
		return kvmControllerService;
	}
	public void setKvmControllerService(VmControllerServiceKVMImpl kvmControllerService) {
		this.kvmControllerService = kvmControllerService;
	}
	public VmControllerServicePOWERVMImpl getPowerControllerService() {
		return powerControllerService;
	}
	public void setPowerControllerService(VmControllerServicePOWERVMImpl powerControllerService) {
		this.powerControllerService = powerControllerService;
	}

	@Override
	public List<ScanResult> scanAndUpdateHostIndicator(List<CmDevicePo> hostIds)
			throws Exception {
		// TODO Auto-generated method stub
		//获取虚拟机状态的接口正在开发
		return null;
	}

	@Override
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> vmPoList,String virtualTypeCode)
			throws Exception {
		log.info("扫描并更新设备状态");
		return vmControllers.scanAndUpdateVmIndicator(vmPoList,virtualTypeCode);
	}

}
