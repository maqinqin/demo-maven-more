package com.git.cloud.handler.automation.sa.kvm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.enums.CUseType;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.IAutomationHandler;
import com.git.cloud.handler.automation.sa.common.CommonParamInitAutomationHandler;
import com.git.cloud.handler.automation.sa.common.FlowInstanceContextObject;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmVmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.general.field.GeneralValueField;
import com.google.common.collect.Lists;

/**
 * @ClassName:KVMDestoryParamInitAutomationHandler
 * @Description:kvm虚拟机回收
 * @author chengbin
 * @date 2014-12-17 上午11:42:30
 */
public class KVMDestoryParamInitAutomationHandler extends
		CommonParamInitAutomationHandler implements IAutomationHandler {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(KVMDestoryParamInitAutomationHandler.class);
	protected void makeAutomationParameter(List<String> deviceIdList,
			FlowInstanceContextObject contextObj, String rrinfoId)
			throws Exception {
		IIpAllocToDeviceNewService ipAllocToDevice = (IIpAllocToDeviceNewService) WebApplicationManager
				.getBean("ipAllocToDeviceImpl");
		
		BmSrRrinfoPo bmSrRrinfo = getAutomationService().getRrinfo(
				rrinfoId);
		// 云服务信息
		CloudServicePo servicePo = getAutomationService().getService(
				bmSrRrinfo.getServiceId());
		// 虚拟机信息
		List<CmVmPo> vmInfoList = getAutomationService().getVms(deviceIdList);
		
		List<String> vmNameList = Lists.newArrayList();
		for(CmVmPo vmPo : vmInfoList){
			String hosId = vmPo.getHostId();
			// 获取物理机信息 Add by  wjx 2016/2/3
			CmVmDatastorePo datastorePo = getCmDeviceServiceImpl().selectCmVmDatastore(vmPo.getId());
			// 获取主机ip地址信息
			List<String> hostDeviceIdList = Lists.newArrayList();
			hostDeviceIdList.add(hosId);
			List<DeviceNetIP> netIpList = null;
			try {
				netIpList = ipAllocToDevice.qryAllocedIPForDevices(hosId);
			} catch (Exception e) {
				logger.error("异常exception",e);
				throw new Exception("获取虚拟机所属主机的网络地址信息出现异常!");
			}
			String pmNetType="";
			String VM_TYPE="";
			if(servicePo.getVmType().equals("2")){
				VM_TYPE=GeneralValueField.VMType.KVM.getValue();
				 pmNetType = RmPlatForm.X86.getValue()
						+ RmVirtualType.KVM.getValue()
						+ RmHostType.PHYSICAL.getValue() + CUseType.MGMT.getValue();
			}else if(servicePo.getVmType().equals("3")){
				VM_TYPE=GeneralValueField.VMType.XEN.getValue();
				 pmNetType = RmPlatForm.X86.getValue()
							+ RmVirtualType.XEN.getValue()
							+ RmHostType.PHYSICAL.getValue() + CUseType.MGMT.getValue();
			}
			

			if (CollectionUtils.isEmpty(netIpList)
					|| CollectionUtils.isEmpty(netIpList.get(0).getNetIPs())
					|| CollectionUtils.isEmpty(netIpList.get(0).getNetIPs()
							.get(pmNetType))) {
				String msg = "找不到设备ID号为[hostId:" + hosId + "]的服务器对应的网络地址信息!";
				throw new Exception(msg);
			}
			contextObj.setDevicePara(vmPo.getId(), GeneralKeyField.VM.VM_TYPE, VM_TYPE);
			NetIPInfo netIPInfo = netIpList.get(0).getNetIPs().get(pmNetType)
					.get(0);
			String hostIp = netIPInfo.getIp();
			contextObj.setDevicePara(vmPo.getId(), GeneralKeyField.KVM.URL, hostIp);
			CmDevicePo vmDev = getAutomationService().getDevice(vmPo.getId());
			vmNameList.add(vmDev.getDeviceName()+","+hostIp+","+datastorePo.getDatastoreName());
			// 物理机默认Datastore 名称 
			//contextObj.setDevicePara(vmPo.getId(), GeneralKeyField.KVM.STORAGEPOOL_NAME, datastorePo.getDatastoreName());
		}
		contextObj.setContextPara(GeneralKeyField.Recycling.DESTORY_RESOURCE_RECS, JSONObject.toJSONString(vmNameList));
	}
}