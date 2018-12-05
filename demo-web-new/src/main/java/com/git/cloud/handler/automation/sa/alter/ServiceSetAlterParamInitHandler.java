package com.git.cloud.handler.automation.sa.alter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.enums.CUseType;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.IAutomationHandler;
import com.git.cloud.handler.automation.Server;
import com.git.cloud.handler.automation.sa.common.CommonParamInitAutomationHandler;
import com.git.cloud.handler.automation.sa.common.FlowInstanceContextObject;
import com.git.cloud.handler.automation.sa.powervm.PowerVMAIXVariable;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.common.VMFlds;
import com.git.support.common.VmGlobalConstants;
import com.git.support.general.field.GeneralKeyField;
import com.google.common.collect.Lists;

/**
 * 变更服务套餐参数初始化
 * 
 * @author zhuzhaoyong.co
 * 
 */
public class ServiceSetAlterParamInitHandler extends
		CommonParamInitAutomationHandler implements IAutomationHandler {

	public IIpAllocToDeviceNewService ipAllocToDevice;
	protected void makeAutomationParameter(List<String> deviceIdList,
			FlowInstanceContextObject contextObj, String rrinfoId) throws Exception {
		System.out.println("makeAutomationParameter");
		BmSrRrinfoPo rrinfo = getAutomationService().getRrinfo(rrinfoId);
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
		//CloudServicePo servicePo = getAutomationService().getService(rrinfo.getServiceId());
		CloudServicePo servicePo = getAutomationService().getService(json.getString("serviceId"));
		boolean isX86 = false;
		System.out.println("servicePo.getPlatformType()" + servicePo.getPlatformType());
		System.out.println("RmPlatForm.X86.getValue()" + RmPlatForm.X86.getValue());
		log.info("servicePo.getPlatformType()" + servicePo.getPlatformType());
		log.info("RmPlatForm.X86.getValue()" + RmPlatForm.X86.getValue());
		if (servicePo.getPlatformType().trim().equals("1")) {
			isX86 = true;
		}
		System.out.println("isX86: " + isX86);
		log.info("isX86: " + isX86);
		boolean isKvm = false;
		boolean isXen = false;
		if(servicePo.getVmType().equals("2")){
			isKvm = true;
		} else
		if(servicePo.getVmType().equals("3")){
			isXen = true;
		}
		String esxiUserName = null;
		String vshpereUrlPrefix = null;
		String vshpereUrlSuffix = null;
		if (isX86) {
			if(isKvm){

				// 网络地址信息接口
				ipAllocToDevice = (IIpAllocToDeviceNewService) WebApplicationManager
						.getBean("ipAllocToDeviceImpl");
				contextObj.setContextPara(VMFlds.VM_TYPE, VmGlobalConstants.VM_TYPE_KVM);
			}else if(isXen){
				// 网络地址信息接口
				ipAllocToDevice = (IIpAllocToDeviceNewService) WebApplicationManager
						.getBean("ipAllocToDeviceImpl");
				contextObj.setContextPara(VMFlds.VM_TYPE, VmGlobalConstants.VM_TYPE_XEN);
			}else{
				esxiUserName = getAutomationService().getAppParameter("VSPHERE.ESXI_USER_NAME");
				contextObj.setContextPara(VMFlds.ESXI_USERNAME, esxiUserName);
				contextObj.setContextPara(VMFlds.ESXI_USERNAME + "_TARGET", esxiUserName);
				vshpereUrlPrefix = getAutomationService().getAppParameter("VSPHERE.URL_PREFIX");
				vshpereUrlSuffix = getAutomationService().getAppParameter("VSPHERE.URL_SUFFIX");
				// 需要先关机然后再开机
				contextObj.setContextPara(VMFlds.VM_POWER_OPER_TYPE, VmGlobalConstants.VM_POWEROFF + "|" + VmGlobalConstants.VM_POWERON);
				// 优先级为高
				contextObj.setContextPara(VMFlds.VMOTION_PRIORITY, VmGlobalConstants.VMOTION_HIGH_PRIORITY);
				// 迁移物理机和datastore
				contextObj.setContextPara(VMFlds.VMOTION_TYPE, VmGlobalConstants.VMOTION_TYPE_RELOCATE);
				// 迁移电源状态
				contextObj.setContextPara(VMFlds.VMOTION_POWER_STATE, VmGlobalConstants.VM_POWER_STATE_POWERED_ON);
				// VMFlds.RECONFIG_TYPE
				Map<String, String> reconfigTypeMap = new HashMap<String, String>();
				reconfigTypeMap.put(VMFlds.RECONFIG_TYPE_CPU, "1");
				reconfigTypeMap.put(VMFlds.RECONFIG_TYPE_MEMORY, "1");
				String reconfigType = JSON.toJSONString(reconfigTypeMap);
				log.debug("reconfigType: " + reconfigType);
				contextObj.setContextPara(VMFlds.RECONFIG_TYPE, reconfigType);
				// 增加打开和关闭电源参数
				contextObj.setContextPara(VMFlds.VM_POWER_OPER_TYPE + "_POWEROFF", VmGlobalConstants.VM_POWEROFF);
				contextObj.setContextPara(VMFlds.VM_POWER_OPER_TYPE + "_POWERON", VmGlobalConstants.VM_POWERON);
			}
		}
		for (String devId : deviceIdList) {
			CmVmPo vm = getAutomationService().getVm(devId);
			CmDevicePo vmDevice = getAutomationService().getDevice(devId);
			// 获取虚拟机datastore信息 Add by  wjx 2016/2/3
			CmVmDatastorePo datastorePo = getCmDeviceServiceImpl().selectCmVmDatastore(vm.getId());
			String vmName = vmDevice.getDeviceName();
			List<String> vmNames = new ArrayList<String>();
			vmNames.add(vmName);
			contextObj.setDevicePara(devId.toString(), VMFlds.VAPP_NAME, vmName);
			contextObj.setDevicePara(devId.toString(), PowerVMAIXVariable.LPARNAME, vmDevice.getLparName());
			contextObj.setDevicePara(devId.toString(), "VAPP_NAME_LIST", JSON.toJSONString(vmNames));
			if (isX86) {
				if(isKvm||isXen){
					String hostId = vm.getHostId();
					//根据hostId获取主机ip地址
					// 获取主机ip地址信息
//					List<String> hostDeviceIdList = Lists.newArrayList();
//					hostDeviceIdList.add(hostId);
//					List<DeviceNetIP> netIpList = null;
//					try {
//						netIpList = ipAllocToDevice.qryAllocedIP(hostDeviceIdList);
//					} catch (Exception e) {
//						throw new Exception("获取虚拟机所属主机的网络地址信息出现异常!");
//					}
//					String pmNetType ="";
//					if(isKvm){pmNetType =RmPlatForm.X86.getValue()
//							+ RmVirtualType.KVM.getValue()
//							+ RmHostType.PHYSICAL.getValue() + CUseType.MGMT.getValue();
//					}else if(isXen){
//						pmNetType =RmPlatForm.X86.getValue()
//								+ RmVirtualType.XEN.getValue()
//								+ RmHostType.PHYSICAL.getValue() + CUseType.MGMT.getValue();
//					}
//
//					if (CollectionUtils.isEmpty(netIpList)
//							|| CollectionUtils.isEmpty(netIpList.get(0).getNetIPs())
//							|| CollectionUtils.isEmpty(netIpList.get(0).getNetIPs()
//									.get(pmNetType))) {
//						String msg = "找不到设备ID号为[hostId:" + hostId + "]的服务器对应的网络地址信息!";
//						throw new Exception(msg);
//					}
//					NetIPInfo netIPInfo = netIpList.get(0).getNetIPs().get(pmNetType)
//							.get(0);
//					String hostIp = netIPInfo.getIp();
//					contextObj.setDevicePara(devId.toString(), VMFlds.URL, hostIp);
//					//获取主机用户名密码信息
//					ICmPasswordDAO cmPasswordDAO = (ICmPasswordDAO) WebApplicationManager.getBean("cmPasswordDAO");
//					CmPasswordPo cmpo = cmPasswordDAO.findCmPasswordByResourceId(vm.getHostId());
//					contextObj.setDevicePara(devId.toString(), GeneralKeyField.KVM.USERNAME, cmpo.getUserName());
//					contextObj.setDevicePara(devId.toString(), GeneralKeyField.KVM.PASSWORD, PwdUtil.decryption(cmpo.getPassword()));
				}else{
					// 初始化esxiurl和密码
					//String esxiIp = getAutomationService().getDeviceManagementIp(vm.getHostId());
					CmDevicePo hostDev = getAutomationService().getDevice(vm.getHostId());
					String esxiIp = hostDev.getIp();
					String esxiUrl = vshpereUrlPrefix + esxiIp + vshpereUrlSuffix;
					contextObj.setDevicePara(devId.toString(), VMFlds.ESXI_URL, esxiUrl);
					contextObj.setDevicePara(devId.toString(), VMFlds.ESXI_URL + "_TARGET", esxiUrl);
					//不在查询exi 物理机密码
					//String esxiPassword = getAutomationService().getPassword(vm.getHostId(), esxiUserName);
					//contextObj.setDevicePara(devId.toString(), VMFlds.ESXI_PASSWORD, esxiPassword);
//					contextObj.setDevicePara(devId.toString(), VMFlds.ESXI_PASSWORD + "_TARGET", esxiPassword);
					// 初始化vc参数
					RmVmManageServerPo vc = getAutomationService().getMgrServerInfoByDeviceId(devId);
					String vcUrl = vshpereUrlPrefix + vc.getManageIp() + vshpereUrlSuffix;
					contextObj.setDevicePara(devId.toString(), VMFlds.URL, vcUrl);
					contextObj.setDevicePara(devId.toString(), VMFlds.USERNAME, vc.getUserName());
					String password = getAutomationService().getPassword(vc.getId(), vc.getUserName());
					contextObj.setDevicePara(devId.toString(), VMFlds.PASSWORD, password);
				}
			}
			// 初始化扩容后的cpu和内存
//			int cpuNumNew = rrinfo.getCpu().intValue();
//			int memNew = rrinfo.getMem().intValue();
			int cpuNumNew = json.getIntValue("cpu");
			int memNew = json.getIntValue("mem");
			int cpuCoreNew = getAutomationService().getCpuCoreNum(cpuNumNew);
			contextObj.setDevicePara(devId.toString(), VMFlds.CPU_VALUE, String.valueOf(cpuNumNew));
			//contextObj.setDevicePara(devId.toString(), VMFlds.MEMORY_VALUE, String.valueOf(memNew *1024) + ".0");
			contextObj.setDevicePara(devId.toString(), VMFlds.MEMORY_VALUE, String.valueOf(memNew) + ".0");
			//contextObj.setDevicePara(devId.toString(), VMFlds.MEMORY_VALUE + "_NEW", String.valueOf(memNew *1024));
			contextObj.setDevicePara(devId.toString(), VMFlds.MEMORY_VALUE + "_NEW", String.valueOf(memNew));
			contextObj.setDevicePara(devId.toString(), VMFlds.CPU_CORE_VALUE, String.valueOf(cpuCoreNew));
			// 扩容前的cpu和内存
			int cpuNumOld = vm.getCpu().intValue();
			int memOld = vm.getMem().intValue();
			contextObj.setDevicePara(devId.toString(), VMFlds.MEMORY_VALUE + "_OLD", String.valueOf(memOld));
			int cpuNumChanged = cpuNumNew - cpuNumOld;
			int memChanged = memNew - memOld;
			// 作为全局参数
			if (cpuNumChanged > 0) {
				contextObj.setContextPara(GeneralKeyField.VM.CPU_ALTER_TYPE, "1");
			} else if (cpuNumChanged < 0) {
				contextObj.setContextPara(GeneralKeyField.VM.CPU_ALTER_TYPE, "2");
			} else {
				contextObj.setContextPara(GeneralKeyField.VM.CPU_ALTER_TYPE, "0");
			}
			if (memChanged > 0) {
				contextObj.setContextPara(GeneralKeyField.VM.MEMORY_ALTER_TYPE, "1");
			} else if (memChanged < 0) {
				contextObj.setContextPara(GeneralKeyField.VM.MEMORY_ALTER_TYPE, "2");
			} else {
				contextObj.setContextPara(GeneralKeyField.VM.MEMORY_ALTER_TYPE, "0");
			}
			//扩容参数
			contextObj.setDevicePara(devId.toString(), GeneralKeyField.VM.CPU_VALUE_CHANGED, String.valueOf(Math.abs(cpuNumChanged)));
			contextObj.setDevicePara(devId.toString(), GeneralKeyField.VM.MEMORY_VALUE_CHANGED, String.valueOf(Math.abs(memChanged)));
			//存储资源池名称
			contextObj.setDevicePara(devId.toString(), GeneralKeyField.KVM.STORAGEPOOL_NAME,datastorePo.getDatastoreName());
			
		}
		// 此流程是否需要迁移某台虚拟机
		boolean ifNeedMoveVm = false;
		if(isKvm){
		}else{
			List<BmSrRrVmRefPo> deviceList = getAutomationService().getSrRrVmRefs(rrinfoId);
			for (Object obj : deviceList) {
				boolean ifMoveThisVm = false;
				BmSrRrVmRefPo bmSrRefPo = (BmSrRrVmRefPo) obj;
				String vmId = bmSrRefPo.getDeviceId();
				CmVmPo vm = getAutomationService().getVm(vmId);
				CmDevicePo sourceHost = getAutomationService().getDevice(vm.getHostId());
				contextObj.setDevicePara(vmId.toString(), VMFlds.VMOTION_SOURCE_HOST, sourceHost.getDeviceName());
				String targetHostId = bmSrRefPo.getMoveHostId();
				CmDevicePo targetHost = null;
				if (targetHostId != null && StringUtils.isNotBlank(targetHostId)) {
					// 需要移动此虚拟机
					ifNeedMoveVm = true;
					ifMoveThisVm = true;
					targetHost = getAutomationService().getDevice(targetHostId);
					String password = getAutomationService().getPassword(targetHostId, esxiUserName);
					String esxiIp = this.getServerIp(targetHostId);
					Server newEsxi = new Server(esxiIp, esxiUserName, password);
					String esxiUrl = vshpereUrlPrefix + newEsxi.getServerIp() + vshpereUrlSuffix;
					contextObj.setDevicePara(vmId.toString(), VMFlds.ESXI_URL + "_TARGET", esxiUrl);
					contextObj.setDevicePara(vmId.toString(), VMFlds.ESXI_USERNAME + "_TARGET", newEsxi.user);
					contextObj.setDevicePara(vmId.toString(), VMFlds.ESXI_PASSWORD + "_TARGET", newEsxi.password);
					String datastoreName = getAutomationService().getHostDatastore(targetHostId);
					contextObj.setDevicePara(vmId.toString(), VMFlds.VMOTION_TARGET_DATASTORE, datastoreName);
				}
				contextObj.setDevicePara(vmId.toString(), VMFlds.VMOTION_TARGET_HOST, ifMoveThisVm ? targetHost.getDeviceName() : "");
				contextObj.setDevicePara(vmId.toString(), VMFlds.IF_MOVE_THIS_VM, ifMoveThisVm ? "1" : "0");
			}
		}
		contextObj.setContextPara(VMFlds.IF_NEED_MOVE_VM, ifNeedMoveVm ? "1" : "0");
	}

	private String getServerIp(String hostId) throws Exception {
		IIpAllocToDeviceNewService ipAllocToDevice = (IIpAllocToDeviceNewService) WebApplicationManager.getBean("ipAllocToDeviceImpl");
		List<DeviceNetIP> netIpList = null;
		try {
			List<String> esxiDeviceIdList = Lists.newArrayList();
			esxiDeviceIdList.add(hostId);
//			netIpList = ipAllocToDevice.qryAllocedIP(esxiDeviceIdList);
		} catch (Exception e) {
			throw new Exception("获取虚拟机所属主机的网络地址信息出现异常!");
		}
		String pmNetType = RmPlatForm.X86.getValue() + RmVirtualType.VMWARE.getValue() + RmHostType.PHYSICAL.getValue() + CUseType.MGMT.getValue();
		if (CollectionUtils.isEmpty(netIpList) || CollectionUtils.isEmpty(netIpList.get(0).getNetIPs())
				|| CollectionUtils.isEmpty(netIpList.get(0).getNetIPs().get(pmNetType))) {
			String msg = "找不到设备ID号为[" + hostId + "]的Esxi服务器对应的网络地址信息!";
			throw new Exception(msg);
		}
		NetIPInfo netIPInfo = netIpList.get(0).getNetIPs().get(pmNetType).get(0);
		return netIPInfo.getIp();
	}
	@Override
	protected String getHandlerReturnWithExeParams(List<String> deviceIdList,
			FlowInstanceContextObject contextObj, String jsonReturnStr)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map = JSON.parseObject(jsonReturnStr);
		Map<String, String> exeParams = new HashMap<String, String>();
		if (contextObj.getContextPara(VMFlds.IF_NEED_MOVE_VM) == null) {
			throw new Exception("缺少参数：" + VMFlds.IF_NEED_MOVE_VM);
		}
		if (contextObj.getContextPara(VMFlds.CPU_ALTER_TYPE) == null) {
			throw new Exception("缺少参数：" + VMFlds.CPU_ALTER_TYPE);
		}
		if (contextObj.getContextPara(VMFlds.MEMORY_ALTER_TYPE) == null) {
			throw new Exception("缺少参数：" + VMFlds.MEMORY_ALTER_TYPE);
		}
		exeParams.put(VMFlds.IF_NEED_MOVE_VM, contextObj.getContextPara(VMFlds.IF_NEED_MOVE_VM));
		exeParams.put(VMFlds.CPU_ALTER_TYPE, contextObj.getContextPara(VMFlds.CPU_ALTER_TYPE));
		exeParams.put(VMFlds.MEMORY_ALTER_TYPE, contextObj.getContextPara(VMFlds.MEMORY_ALTER_TYPE));
		map.put("exeParams", exeParams);
		return JSON.toJSONString(map);
	}
}