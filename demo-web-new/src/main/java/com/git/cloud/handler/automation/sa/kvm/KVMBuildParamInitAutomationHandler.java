package com.git.cloud.handler.automation.sa.kvm;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.enums.CUseType;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.IAutomationHandler;
import com.git.cloud.handler.automation.sa.common.CommonParamInitAutomationHandler;
import com.git.cloud.handler.automation.sa.common.FlowInstanceContextObject;
import com.git.cloud.handler.po.CmRoutePo;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.common.VMFlds;
import com.git.support.common.VmGlobalConstants;
import com.git.support.constants.PubConstants;
import com.git.support.constants.SAConstants;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.general.field.GeneralValueField;
import com.google.common.collect.Lists;

/**
 * @ClassName:KVMBuildParamInitAutomationHandler
 * @Description:kvm流程初始化信息
 * @author chengbin
 * @date 2014-11-4 下午1:46:43
 */
public class KVMBuildParamInitAutomationHandler extends
		CommonParamInitAutomationHandler implements IAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(KVMBuildParamInitAutomationHandler.class);

	private static final String PORT_GROUP_KEY = "VMWARE_PGNAM";// 获取端口组名称的key值
	protected void makeAutomationParameter(List<String> deviceIdList,
			FlowInstanceContextObject contextObj, String rrinfoId)
			throws Exception {
		// 服务套餐信息
		BmSrRrinfoPo bmSrRrinfo = getAutomationService().getRrinfo(
				rrinfoId);
		// 云服务信息
		CloudServicePo servicePo = getAutomationService().getService(
				bmSrRrinfo.getServiceId());

		// 获取云服务定义参数
		Map<String, String> srvAttrInfoMap = getAutomationService()
				.getServiceAttr(rrinfoId);
		
		RmDatacenterPo datacenter = getAutomationService().getDatacenter(rrinfoId);
		String dataCenterId = datacenter.getId();
		
//		RmDatacenterPo datacenter = getAutomationService().getDatacenter(
//				rrinfoId);

		// 获取DNS、NTP信息
		//Map<String, String> dnsInfoMap = Maps.newHashMap();
		//Map<String, String> ntpInfoMap = Maps.newHashMap();
//		List<RmGaneralServerPo> dnsPoList = getAutomationService()
//				.getGaneralServers(datacenter.getId(), "DNS_SERVER");
//		List<RmGaneralServerPo> ntpPoList = getAutomationService()
//				.getGaneralServers(datacenter.getId(), "NTP_SERVER");

//		if (dnsPoList != null) {
//			int i = 1;
//			for (RmGaneralServerPo dnsPo : dnsPoList) {
//				dnsInfoMap.put("V_DNS_SERVER" + i, dnsPo.getServerIp()
//						.toString());
//				i++;
//			}
//		} else
//			log.debug("创建虚机参数初始化，读取dnsPoList为空！");

//		if (ntpPoList != null) {
//			// 对NTP进行随机排序
//			Collections.shuffle(ntpPoList);
//			int i = 1;
//			for (RmGaneralServerPo ntpPo : ntpPoList) {
//				ntpInfoMap.put("V_NTP_SERVER" + i, ntpPo.getServerIp()
//						.toString());
//				i++;
//			}
//		} else {
//			log.debug("创建虚机参数初始化，读取ntpPoList为空！");
//		}

		// 虚拟机信息
		List<CmVmPo> vmInfoList = getAutomationService().getVms(deviceIdList);
		if(vmInfoList.isEmpty()||vmInfoList.size()==0){
			throw new Exception("获取虚拟机:"+deviceIdList.toString()+"的主机信息失败！");
		}
		
		// 网络地址信息接口
		IIpAllocToDeviceNewService ipAllocToDevice = (IIpAllocToDeviceNewService) WebApplicationManager
				.getBean("ipAllocToDeviceImpl");

		//镜像信息
		CloudImage imagePo = getAutomationService().getImage(rrinfoId);
		contextObj.setContextPara(GeneralKeyField.VM.VM_IMAGE_NAME, imagePo.getImageUrl());
		
		//虚拟机类型
		if(servicePo.getVmType().equals("2")){
			contextObj.setContextPara(GeneralKeyField.VM.VM_TYPE, GeneralValueField.VMType.KVM.getValue());			
		}else if(servicePo.getVmType().equals("3")){
			contextObj.setContextPara(GeneralKeyField.VM.VM_TYPE, GeneralValueField.VMType.XEN.getValue());		
		}
		
		// cpu,mem信息
		int cpu = bmSrRrinfo.getCpu();
		int mem = bmSrRrinfo.getMem();
		//创建卷大小  VM_VOL_CAPACITY_SIZE(虚机卷允许扩展大小) ,VM_VOL_ALLOCATION_SIZE(创建虚机卷大小)
		int sysDisk = bmSrRrinfo.getSysDisk();
		// 虚拟机的名称集合
		StringBuilder vmServerNames = new StringBuilder("");

		// 虚拟机默认用户名
		//String vmUserName = getAutomationService().getAppParameter("VM.DEFAULT_USER_NAME");
		//change by wmy 2016/03/21 改为镜像的用户名和密码
		String vmUserName = imagePo.getManager();
		String resourceId = imagePo.getImageId();
		int deviceSeq = 0;// 设备循环变量

		for (String deviceId : deviceIdList) {
			log.info("设备ID号为[" + deviceId + "]的虚拟机自动化流程参数初始化开始.");
			if (CollectionUtils.isEmpty(vmInfoList)) {
				String msg = "参数初始化失败,找不到虚拟机相关的数据!";
				throw new Exception(msg);
			}

			CmVmPo vmInfo = vmInfoList.get(deviceSeq);

			if (vmInfo == null) {
				String msg = "找不到设备ID号为[" + deviceId + "]的虚拟机配置信息!";
				throw new Exception(msg);
			}
			// 添加所有云服务属性中定义的参数
			for (String attrName : srvAttrInfoMap.keySet()) {
				contextObj.setDevicePara(deviceId, attrName,
						srvAttrInfoMap.get(attrName));
			}

			String hostId = vmInfo.getHostId();
			// 获取物理机信息 Add by  wjx 2016/2/3
			CmHostDatastorePo datastoreInfo = getCmDeviceServiceImpl().selectCmHostDeafultDatastore(hostId);
			CmDevicePo hostDev = getAutomationService().getDevice(hostId);
			CmDevicePo vmDev = getAutomationService().getDevice(vmInfo.getId());

			CmHostPo hostDevPo = getAutomationService().getCmHostPo(hostId);
			int max_mem = hostDevPo.getMem();
			int max_cpu = hostDevPo.getCpu();
			// 获取主机ip地址信息
			List<String> hostDeviceIdList = Lists.newArrayList();
			hostDeviceIdList.add(hostId);
			List<DeviceNetIP> netIpList = null;
			try {
				netIpList = null;//(需要重新改造)ipAllocToDevice.qryAllocedIP(hostDeviceIdList);
			} catch (Exception e) {
				logger.error("异常exception",e);
				throw new Exception("获取虚拟机所属主机的网络地址信息出现异常!");
			}
			String pmNetType ="";
			if(servicePo.getVmType().equals("2")){
			pmNetType=RmPlatForm.X86.getValue()
					+ RmVirtualType.KVM.getValue()
					+ RmHostType.PHYSICAL.getValue() + CUseType.MGMT.getValue();
			}else if(servicePo.getVmType().equals("3")){
				pmNetType=RmPlatForm.X86.getValue()
						+ RmVirtualType.XEN.getValue()
						+ RmHostType.PHYSICAL.getValue() + CUseType.MGMT.getValue();
			}

			if (CollectionUtils.isEmpty(netIpList)
					|| CollectionUtils.isEmpty(netIpList.get(0).getNetIPs())
					|| CollectionUtils.isEmpty(netIpList.get(0).getNetIPs()
							.get(pmNetType))) {
				String msg = "找不到虚拟机ID[vmId:" + vmInfo.getId()
						+ "]设备ID号为[hostId:" + deviceId + "]的服务器对应的网络地址信息!";
				throw new Exception(msg);
			}

			NetIPInfo netIPInfo = netIpList.get(0).getNetIPs().get(pmNetType)
					.get(0);
			String hostIp = netIPInfo.getIp();
			contextObj.setDevicePara(deviceId, GeneralKeyField.KVM.URL, hostIp);

			contextObj.setDevicePara(deviceId, VMFlds.HOST_NAME,hostDev.getDeviceName());// 主机名
			String vmHostName = vmDev.getDeviceName();
			contextObj.setDevicePara(deviceId, VMFlds.VM_HOST_NAME, vmHostName);// 虚拟机名称
			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.VAPP_NAME, vmHostName);// 虚拟机名称
//			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.CPU_CORE_VALUE,srvAttrInfoMap.get("CPU_CORE_VALUE"));// 每个差插槽的CPU核数
			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.CPU_CORE_VALUE,String.valueOf(cpu));// cpu的的总核数信息
			//contextObj.setDevicePara(deviceId, GeneralKeyField.VM.MEMORY_VALUE, String.valueOf(mem * 1024)+".0");// 内存的信息
			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.MEMORY_VALUE, String.valueOf(mem)+".0");// 内存的信息
			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.MAX_CPU_VALUE, String.valueOf(max_cpu));// 最大CPU
//			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.MAX_MEMORY_VALUE, String.valueOf(mem * 1024));//最大内存
			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.MAX_MEMORY_VALUE, String.valueOf(mem));//最大内存
			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.VM_VOL_CAPACITY_SIZE, String.valueOf(sysDisk*1024));//虚机卷允许扩展大小
			contextObj.setDevicePara(deviceId, GeneralKeyField.VM.VM_VOL_ALLOCATION_SIZE, String.valueOf(sysDisk*1024));//创建虚机卷大小
			// 物理机默认Datastore 名称 
			contextObj.setDevicePara(deviceId, GeneralKeyField.KVM.STORAGEPOOL_NAME, datastoreInfo.getDatastoreName());
			//获取主机用户名密码信息
			ICmPasswordDAO cmPasswordDAO = (ICmPasswordDAO) WebApplicationManager.getBean("cmPasswordDAO");
			CmPasswordPo cmpo = cmPasswordDAO.findCmPasswordByResourceId(hostId);
			contextObj.setDevicePara(deviceId, GeneralKeyField.KVM.USERNAME, cmpo.getUserName());
			contextObj.setDevicePara(deviceId, GeneralKeyField.KVM.PASSWORD, PwdUtil.decryption(cmpo.getPassword()));

			if (vmServerNames.length() != 0) {
				vmServerNames.append(PubConstants.SEPARATOR_COLON);
			}
			vmServerNames.append(vmHostName);

			// 虚拟机的网络地址信息
			List<DeviceNetIP> vmIPInfoList = null;
			try {
				vmIPInfoList = null;//(需要重新改造)ipAllocToDevice.qryAllocedIP(Lists.newArrayList(deviceId));
			} catch (Exception e) {
				throw new Exception("获取虚拟机网络地址信息出现异常!", e);
			}
			String vmNetType ="";
			String vpNetType ="";
			if(servicePo.getVmType().equals("2")){
			vmNetType=RmPlatForm.X86.getValue()
					+ RmVirtualType.KVM.getValue()
					+ RmHostType.VIRTUAL.getValue() + CUseType.MGMT.getValue();
			vpNetType=RmPlatForm.X86.getValue()
					+ RmVirtualType.KVM.getValue()
					+ RmHostType.VIRTUAL.getValue() + CUseType.PROD.getValue();
			}else if(servicePo.getVmType().equals("3")){
				vmNetType=RmPlatForm.X86.getValue()
						+ RmVirtualType.XEN.getValue()
						+ RmHostType.VIRTUAL.getValue() + CUseType.MGMT.getValue();
				vpNetType=RmPlatForm.X86.getValue()
						+ RmVirtualType.XEN.getValue()
						+ RmHostType.VIRTUAL.getValue() + CUseType.PROD.getValue();
				
			}
			if (CollectionUtils.isEmpty(vmIPInfoList)
					|| CollectionUtils.isEmpty(vmIPInfoList.get(0).getNetIPs())
					|| CollectionUtils.isEmpty(vmIPInfoList.get(0).getNetIPs()
							.get(vpNetType))
					|| CollectionUtils.isEmpty(vmIPInfoList.get(0).getNetIPs()
							.get(vmNetType))) {
				String msg = "找不到设备ID号为[" + deviceId + "]的虚拟机对应的网络地址信息!";
				throw new Exception(msg);
			}
			//虚拟机生产IP信息
			DeviceNetIP devNetIP = vmIPInfoList.get(0);
			Map<String,List<NetIPInfo>> devNetIPMap = devNetIP.getNetIPs();
			List<NetIPInfo> devNetIPInfoList_p = devNetIPMap.get(vpNetType);

			String nicTypeNameProd = getAutomationService().getAppParameter(
					"VM_PROD_NIC_TYPE_NAME");// 生产网卡
			
			String nicPortGroupProd = "";
			String nicIpProd = "";
			String nicMaskProd = "";
			String nicGateWayProd = "";
			String nicVlanIDProd = "";
			for(NetIPInfo ipInfo :devNetIPInfoList_p){
				nicPortGroupProd = ipInfo.getExtendInfo().get(PORT_GROUP_KEY);// 生产端口组
				nicIpProd += ","+ipInfo.getIp();
				nicMaskProd +=","+ipInfo.getIpMask();
				nicGateWayProd +=","+ipInfo.getGateWay();
				nicVlanIDProd+=","+ipInfo.getVlanID();
			}
			//虚拟机管理IP信息

			List<NetIPInfo> devNetIPInfoList_m = devNetIPMap.get(vmNetType);
//			NetIPInfo mgmtNetIPInfo = vmIPInfoList.get(0).getNetIPs()
//					.get(vmNetType).get(0);

			String nicTypeNameMgmt = getAutomationService().getAppParameter(
					"VM_MGR_NIC_TYPE_NAME");// 管理网卡
			
			String nicPortGroupMgmt = "";
			String nicIpMgmt ="";
			String nicMaskMgmt = "";
			String nicGateWayMgmt = "";
			String nicVlanIdMgmt="";
			for(NetIPInfo ipInfo:devNetIPInfoList_m){
				nicPortGroupMgmt = ipInfo.getExtendInfo().get(PORT_GROUP_KEY);// 管理
				nicIpMgmt +=","+ipInfo.getIp();
				nicMaskMgmt +=","+ipInfo.getIpMask();
				nicGateWayMgmt +=","+ipInfo.getGateWay();
				nicVlanIdMgmt+=","+ipInfo.getVlanID();
			}

			contextObj.setDevicePara(deviceId, SAConstants.NIC_TYPE_NAME_MGMT,
					nicTypeNameMgmt);
			contextObj.setDevicePara(deviceId, SAConstants.NIC_PORTGROUP_MGMT,
					nicPortGroupMgmt);
			contextObj.setDevicePara(deviceId, SAConstants.NIC_IP_MGMT,
					nicIpMgmt.replaceFirst(",", ""));
			contextObj.setDevicePara(deviceId, SAConstants.NIC_MASK_MGMT,
					nicMaskMgmt.replaceFirst(",", ""));
			contextObj.setDevicePara(deviceId, SAConstants.NIC_GATEWAY_MGMT,
					nicGateWayMgmt.replaceFirst(",", ""));
			
			contextObj.setDevicePara(deviceId, SAConstants.NIC_TYPE_NAME_PROD,
					nicTypeNameProd);
			contextObj.setDevicePara(deviceId, SAConstants.NIC_PORTGROUP_PROD,
					nicPortGroupProd);
			contextObj.setDevicePara(deviceId, SAConstants.NIC_IP_PROD,
					nicIpProd.replaceFirst(",", ""));
			contextObj.setDevicePara(deviceId, SAConstants.NIC_MASK_PROD,
					nicMaskProd.replaceFirst(",", ""));
			contextObj.setDevicePara(deviceId, SAConstants.NIC_GATEWAY_PROD,
					nicGateWayProd.replaceFirst(",", ""));

			
			//设置配置kvm虚拟机所需ip信息
			contextObj.setDevicePara(deviceId, VMFlds.VLAN_ID, nicVlanIdMgmt.replaceFirst(",", "")+nicVlanIDProd);
			contextObj.setDevicePara(deviceId, VMFlds.NIC_IP, nicIpMgmt.replaceFirst(",", "")+nicIpProd);
			contextObj.setDevicePara(deviceId, VMFlds.NIC_MASK, nicMaskMgmt.replaceFirst(",", "")+nicMaskProd);
			contextObj.setDevicePara(deviceId, VMFlds.NIC_GATEWAY, nicGateWayMgmt.replaceFirst(",", "")+nicGateWayProd);
			
			// String vmMgrIp = "192.168.35.10";// 管理IP地址
			contextObj.setDevicePara(deviceId, VMFlds.VM_TYPE, VmGlobalConstants.VM_TYPE_LINUX);// 操作系统类型，固定值

			// 虚拟机的IP、用户信息,供脚本执行使用
			String vmPassword = getAutomationService().getPassword(
					resourceId, vmUserName);
			System.out.println("resourceId:"+resourceId+",vmUserName:"+vmUserName+",vmPassword:"+vmPassword);
			contextObj
					.setDevicePara(deviceId, SAConstants.SERVER_IP, nicIpMgmt.replaceFirst(",", ""));
			contextObj.setDevicePara(deviceId, SAConstants.USER_NAME,
					vmUserName);
			contextObj.setDevicePara(deviceId, SAConstants.USER_PASSWORD,
					vmPassword);

			// DNS相关的信息
			//String vDnsServer1 = dnsInfoMap.get(SAConstants.V_DNS_SERVER1);// 第一个DNS服务器IP地址
			//String vDnsServer2 = dnsInfoMap.get(SAConstants.V_DNS_SERVER2);// 第二个DNS服务器IP地址
//			String dns = vDnsServer1 + PubConstants.SEPARATOR_COMMAS
//					+ vDnsServer2;// 域名服务器列表

//			contextObj.setDevicePara(deviceId, SAConstants.V_DNS_SERVER1,
//					vDnsServer1);
//			contextObj.setDevicePara(deviceId, SAConstants.V_DNS_SERVER2,
//					vDnsServer2);
//			contextObj.setDevicePara(deviceId, VMFlds.NIC_DNS, dns);
//			// NTP相关的属性
//			contextObj.setDevicePara(deviceId, SAConstants.V_NTP_SERVER1,
//					ntpInfoMap.get(SAConstants.V_NTP_SERVER1));// 第一个NTP服务器IP地址或域名（主）
//			contextObj.setDevicePara(deviceId, SAConstants.V_NTP_SERVER2,
//					ntpInfoMap.get(SAConstants.V_NTP_SERVER2));// 第二个NTP服务器IP地址或域名
//			contextObj.setDevicePara(deviceId, SAConstants.V_NTP_SERVER3,
//					ntpInfoMap.get(SAConstants.V_NTP_SERVER3));// 第三个NTP服务器IP地址或域名

			// 临时添加路由信息，保证云平台能够连上虚拟机，两地三中心根据数据中心读取路由
			// 临时添加路由信息，保证云平台能够连上虚拟机，两地三中心根据数据中心读取路由
			List<CmRoutePo> routeList = null;
			if (null != dataCenterId) {
				routeList = getAutomationService().getRoutes(dataCenterId);
			} else
				log.debug("数据中心编号dataCenterId为空，无法查询路由信息，路由信息为空！");
			if (null != routeList && !CollectionUtils.isEmpty(routeList)) {
				StringBuilder ips = new StringBuilder();
				StringBuilder gateWays = new StringBuilder();
				StringBuilder masks = new StringBuilder();
				int index = 0;
				for (CmRoutePo route : routeList) {

					if (index != 0) {
						ips.append(PubConstants.SEPARATOR_COMMAS);
						gateWays.append(PubConstants.SEPARATOR_COMMAS);
						masks.append(PubConstants.SEPARATOR_COMMAS);
					}

					String ip = route.getIp();
					String mask = route.getMask();
					String gateWay = route.getGateway();
					ips.append(ip);
					gateWays.append(gateWay);
					masks.append(mask);
					index++;
				}
				contextObj.setDevicePara(deviceId, VMFlds.ROUTE_IPS, ips.toString());// 路由Ip集合
				contextObj.setDevicePara(deviceId, VMFlds.ROUTE_GWS, gateWays.toString());// 路由网关集合
				contextObj.setDevicePara(deviceId, VMFlds.ROUTE_MASKS, masks.toString());// 路由子网掩码集合
			} else {
				String msg = "缺少路由配置信息，请维护CM_ROUTE表!";
				throw new java.lang.Exception(msg);
			}
			// ==================虚拟机相关参数 end ======================
			deviceSeq++;
			log.info("设备ID号为[" + deviceId + "]的虚拟机自动化流程参数初始化 结束.");
		}
	}
}