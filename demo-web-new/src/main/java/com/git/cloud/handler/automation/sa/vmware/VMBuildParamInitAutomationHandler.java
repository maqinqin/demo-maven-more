package com.git.cloud.handler.automation.sa.vmware;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.CUseType;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.po.CmRoutePo;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmHostDatastoreRefDAO;
import com.git.cloud.resmgt.common.model.po.CmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastoreRefPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.service.IRmDatacenterService;
import com.git.cloud.resmgt.compute.service.ICmVirtualSwitchService;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.common.VMFlds;
import com.git.support.common.VmGlobalConstants;
import com.git.support.constants.PubConstants;
import com.git.support.constants.SAConstants;
import com.google.common.collect.Maps;

/**
 * 通用的虚拟机构建所需要的参数
 * <p>
 * 
 * @author zhuzy
 * @version 1.0 2013-5-7
 * @see
 * 
 * 修改
 * 		2015-11-04 修改判断vmNetType和vpNetType类型的ip地址都不允许为空的检查
 */
public class VMBuildParamInitAutomationHandler extends LocalAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(VMBuildParamInitAutomationHandler.class);

	private static final String PORT_GROUP_KEY = "VMWARE_PGNAM";// 获取端口组名称的key值
	
	private static final String PORT_GROUP_ID_KEY = "PORT_GROUP_ID";// 获取端口组前缀id的key值
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.iomp.cloud.core.automation.handler.LocalAbstractAutomationHandler
	 * #service(java.util.Map)
	 */
	public String service(Map<String, Object> contenxtParams) throws Exception {

		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);

		// 服务请求Id
		String srvReqId = (String) contenxtParams.get(SRV_REQ_ID);

		// 资源请求ID
		String rrinfoId = (String) contenxtParams.get(RRINFO_ID);

		logger.info("vmware虚拟机构建参数实例化开始,服务请求ID:" + srvReqId + ",流程实例ID:" + flowInstId);

		// 参数清理
		getBizParamInstService().deleteParamInstsOfFlow(flowInstId);

		// 服务套餐信息
		BmSrRrinfoPo bmSrRrinfo = getAutomationService().getRrinfo(rrinfoId);

		// 获取云服务定义参数
		Map<String, String> srvAttrInfoMap = getAutomationService().getServiceAttr(rrinfoId);
				
		Map<String, String> dnsInfoMap = Maps.newHashMap();
		Map<String, String> ntpInfoMap = Maps.newHashMap();

		// 两地三中心，新的dns、ntp信息读取方法
		try {
//			List<DnsPo> dnsPoList = automationService.findDnsByRrinfo(rrinfoId);
//			List<NtpPo> ntpPoList = automationService.findNtpsByRrinfo(rrinfoId);
			String dataCenterId = this.getDatacenterId(rrinfoId);
			List<RmGeneralServerPo> dnsPoList = getAutomationService().getGeneralServers(dataCenterId, "DNS_SERVER");
			List<RmGeneralServerPo> ntpPoList = getAutomationService().getGeneralServers(dataCenterId, "NTP_SERVER");

			if (dnsPoList != null) {
				int i = 1;
				for (RmGeneralServerPo dnsPo : dnsPoList) {
					dnsInfoMap.put("V_DNS_SERVER" + i, dnsPo.getServerIp().toString());
					i++;
				}
			} else
				logger.debug("创建虚机参数初始化，读取dnsPoList为空！");

			if (ntpPoList != null) {
				// 对NTP进行随机排序
				Collections.shuffle(ntpPoList);
				int i = 1;
				for (RmGeneralServerPo ntpPo : ntpPoList) {
					ntpInfoMap.put("V_NTP_SERVER" + i, ntpPo.getServerIp().toString());
					i++;
				}
			} else
				logger.debug("创建虚机参数初始化，读取ntpPoList为空！");

		} catch (Exception e) {
			logger.error("异常exception",e);
		}

		// 根据服务请求Id获取设备列表(本次服务请求需要创建的虚拟机)初始化每台设备对应的参数值
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		if (deviceIdList != null) {
			/**
			 * 为每台设备准备自动化流程的初始化参数
			 */
			this.buildInitParam(contenxtParams, deviceIdList, bmSrRrinfo, dnsInfoMap, ntpInfoMap,
					srvAttrInfoMap);

		}

		logger.info("vmware虚拟机构建参数实例化结束,服务请求ID:" + srvReqId + ",流程实例ID:" + flowInstId);

		return PubConstants.EXEC_RESULT_SUCC;
	}

	/**
	 * 构造初始化参数
	 * 
	 * @param contenxtParams
	 *            上下文参数集合
	 * @param deviceIdList
	 *            设备Id列表
	 * @param imageInfo
	 *            镜像信息
	 * @param dnsInfoMap
	 *            dns信息
	 * @param ntpInfoMap
	 *            ntp信息
	 * @param srvAttrInfoMap
	 *            云服务属性信息
	 * @throws Exception
	 */
	private void buildInitParam(Map<String, Object> contenxtParams, List<String> deviceIdList,
			BmSrRrinfoPo bmSrRrinfo, Map<String, String> dnsInfoMap,
			Map<String, String> ntpInfoMap, Map<String, String> srvAttrInfoMap) throws Exception {

		// 资源请求ID
		String rrinfoId = (String) contenxtParams.get(RRINFO_ID);
		JSONObject json = JSONObject.parseObject(bmSrRrinfo.getParametersJson());

		// 数据中心ID，根据资源请求ID获得
		IRmDatacenterService rmDataCenterServiceImpl = (IRmDatacenterService) WebApplicationManager.getBean("rmDataCenterService");
		ICmHostDatastoreRefDAO cmHostDatastoreRefDAO = (ICmHostDatastoreRefDAO) WebApplicationManager.getBean("cmHostDatastoreRefDAO");

		String dataCenterId = this.getDatacenterId(rrinfoId);
		
		//获取数据中心
		RmDatacenterPo  datacenter = rmDataCenterServiceImpl.getDataCenterById(dataCenterId);

//		List<Map<String, ?>> vmInfoList = automationCommonDAO.findVmInfoByDeviceId(deviceIdList);
		List<CmVmPo> vmInfoList = getAutomationService().getVms(deviceIdList);

		// vCenter管理服务器的信息
//		List<Map<String, ?>> vCenterInfoList = automationCommonDAO.findMgrServerInfoByDeviceId(deviceIdList);
		List<RmVmManageServerPo> vCenterInfoList = getAutomationService().getMgrServerInfoByDeviceId(deviceIdList);

		// 网络地址信息接口
		IIpAllocToDeviceNewService ipAllocToDevice = (IIpAllocToDeviceNewService) WebApplicationManager.getBean("ipAllocToDeviceImpl");
		
		ICmVirtualSwitchService cmVirtualSwitchService = (ICmVirtualSwitchService) WebApplicationManager.getBean("cmVirtualSwitchService");
		// 虚拟交换机接口 
		
		//改造日期：20170518 wmy
		//镜像端口
		String image_port = parameterService().getParamValueByName("IMAGE_PORT");
		if(image_port == null || "".equals(image_port.trim())){
			throw new Exception("获取镜像端口失败，未在系统参数中找到IMAGE_PORT设置值");
		}
		// 镜像临时路径（从镜像管理那输入的地址)
		String imagePathTmp = getAutomationService().getImage(rrinfoId).getImageUrl().trim();
		logger.info("镜像管理中配置的地址："+imagePathTmp);
		//模板名称（即镜像名称，用模板部署的方式，vc中的模板需要和镜像名称相同）
		String templateName = getAutomationService().getImage(rrinfoId).getImageName().trim();
		if(templateName == null || "".equals(templateName.trim())){
			throw new Exception("获取镜像名称失败");
		}
		//镜像最终路径
		String imagePath = "";
		//镜像服务器IP
        String image_ip = "";
        //tmpIp
        String tmpIp = "";
		List<RmGeneralServerPo> poList = getAutomationService().getGeneralServers(dataCenterId, "IMAGE_SERVER");
        if(poList !=null && poList.size()>0){
        	 image_ip = poList.get(0).getServerIp();
        	 //例如："http://10.33.4.3:8080/image/vmware/REDHAT6.4_WLS/REDHAT6.4_WLS_20130730.ovf"
        	 if(imagePathTmp.startsWith("http://")){
        		 Pattern p = Pattern.compile("http://(.*):");  
        			Matcher m = p.matcher(imagePathTmp); 
        			while(m.find()){  
        			tmpIp = m.group(1); 
        			}	
					if(tmpIp != null && !tmpIp.equals("")){
						imagePath = imagePathTmp.replace(tmpIp,image_ip);//替换ip
						Pattern p2 = Pattern.compile(":([0-9][0-9][0-9][0-9])/");  
	        			Matcher m2 = p2.matcher(imagePath); 
	        			String port = "";
	        			while(m2.find()){  
	        			port = m2.group(1); 
	        			 }
	        			if(port != null && !port.equals("")){
	        				imagePath = imagePathTmp.replace(port,image_port);//替换端口号
	        			}
	        			logger.info("资源请求rrinfoId："+rrinfoId+",1镜像地址为："+imagePath);
					}
             }else{
            	 if(imagePathTmp.startsWith("/")){
            		 //拼接地址，例如：/image/vmware/REDHAT6.4_WLS/REDHAT6.4_WLS_20130730.ovf
            		 imagePath = "http://" +image_ip+ ":"+ image_port + imagePathTmp;
            		 logger.info("资源请求rrinfoId："+rrinfoId+",2镜像地址为："+imagePath);
            	 }else{
            		 //拼接地址，例如：image/vmware/REDHAT6.4_WLS/REDHAT6.4_WLS_20130730.ovf
            		 imagePath = "http://" + image_ip + ":"+ image_port + "/" + imagePathTmp;
            		 logger.info("资源请求rrinfoId："+rrinfoId+",3镜像地址为："+imagePath);
            	 }
            	 
             }
        }
       
        if(imagePath == null || "".equals(imagePath.trim())){
        	throw new Exception("获取镜像地址失败，请检查镜像管理中地址配置");
        }
		        

//		int cpu = bmSrRrinfo.getCpu();
//		int mem = bmSrRrinfo.getMem();
		int cpu = json.getIntValue("cpu");
		int mem = json.getIntValue("mem");

		// 虚拟机的名称集合
		StringBuilder vmServerNames = new StringBuilder("");

		int deviceSeq = 0;// 设备循环变量

		// esxi/vCenter 接口URL的前缀和后缀
		String vshpereUrlPrefix = getAutomationService().getAppParameter("VSPHERE.URL_PREFIX");
		String vshpereUrlSuffix = getAutomationService().getAppParameter("VSPHERE.URL_SUFFIX");
		// #连接vCenter/esxI所用的用户名
		String esxiUserName = getAutomationService().getAppParameter("VSPHERE.ESXI_USER_NAME");

		String vmUserName = this.getVmUserName();

		// 每个设备公有参数的map，这些参数是每个设备所共有的，对应于同一个资源请求id的。
		// 先读取出来放在这里，然后整个放进循环体内的deviceParamMap中
		// TODO 可以把更多循环体中的重复参数移至此处
		Map<String, String> commonDeviceParamMap = Maps.newHashMap();
		// 循环准备每台设备的信息
		for (String deviceId : deviceIdList) {

			logger.info("设备ID号为[" + deviceId + "]的虚拟机自动化流程参数初始化开始.");

			if (CollectionUtils.isEmpty(vmInfoList) || CollectionUtils.isEmpty(vCenterInfoList)) {
				String msg = "参数初始化失败,找不到虚拟机相关的数据!";
				throw new Exception(msg);
			}

			// ================虚拟机相关参数 begin =================

			CmVmPo vmInfo = vmInfoList.get(deviceSeq);

			if (vmInfo == null) {
				String msg = "找不到设备ID号为[" + deviceId + "]的虚拟机配置信息!";
				throw new Exception(msg);
			}

			RmVmManageServerPo vCenterInfo = vCenterInfoList.get(deviceSeq);

			if (vCenterInfo == null) {
				String msg = "找不到设备ID号为[" + deviceId + "]的虚拟机对应的vCenter服务器!";
				throw new Exception(msg);
			}

			// 单台设备的参数集合
			Map<String, String> deviceParamMap = Maps.newHashMap();
			String deviceIdStr = deviceId.toString();
			contenxtParams.put(deviceIdStr, deviceParamMap);
			this.setHandleResultParam(deviceIdStr, deviceParamMap);

			// 添加所有云服务属性中定义的参数
			for (String attrName : srvAttrInfoMap.keySet()) {
				deviceParamMap.put(attrName, srvAttrInfoMap.get(attrName));
			}
			// 将公有参数与单台设备参数合并
			deviceParamMap.putAll(commonDeviceParamMap);

			// vCenter相关的信息 begin
			String vCenterHostIp = vCenterInfo.getManageIp();

			String vCenterUrl = vshpereUrlPrefix + vCenterHostIp + vshpereUrlSuffix;// vCenterUrl地址
			deviceParamMap.put(VMFlds.VCENTER_URL, vCenterUrl);

			// vCenter用户名称和密码
			deviceParamMap.put(VMFlds.VCENTER_USERNAME, vCenterInfo.getUserName());
			String vcPassword = getAutomationService().getPassword(vCenterInfo.getId(), vCenterInfo.getUserName());
			deviceParamMap.put(VMFlds.VCENTER_PASSWORD, vcPassword);
			// vCenter相关的信息 end

			// Esxi相关的信息 begin
//			BigDecimal esxiDeviceId = (BigDecimal) vmInfo.get("HOST_ID");
			String esxiDeviceId = vmInfo.getHostId();
			CmDevicePo hostDev = getAutomationService().getDevice(esxiDeviceId);
			CmDevicePo vmDev = getAutomationService().getDevice(vmInfo.getId());
			// 获取Esxi主机的IP地址
			/**
			List<String> esxiDeviceIdList = Lists.newArrayList();
			esxiDeviceIdList.add(esxiDeviceId);
			List<DeviceNetIP> netIpList = null;
			try {
				netIpList = ipAllocToDevice.qryAllocedIP(esxiDeviceIdList);
			} catch (Exception e) {
				throw new Exception("获取虚拟机所属主机的网络地址信息出现异常!");
			}

			String pmNetType = RmPlatForm.X86.getValue() + RmVirtualType.VMWARE.getValue() + RmHostType.PHYSICAL.getValue() + CUseType.MGMT.getValue();
			if (CollectionUtils.isEmpty(netIpList) || CollectionUtils.isEmpty(netIpList.get(0).getNetIPs())
					|| CollectionUtils.isEmpty(netIpList.get(0).getNetIPs().get(pmNetType))) {

				String msg = "找不到设备ID号为[" + deviceId + "]的Esxi服务器对应的网络地址信息!";
				throw new Exception(msg);
			}
			NetIPInfo netIPInfo = netIpList.get(0).getNetIPs().get(pmNetType).get(0);
			String esxiIp = netIPInfo.getIp();**/

			String esxiIp = hostDev.getIp();
			String esxiUrl = vshpereUrlPrefix + esxiIp + vshpereUrlSuffix;// esxi地址
			deviceParamMap.put(VMFlds.ESXI_URL, esxiUrl);
			// esxi的用户名称和密码及ip
			//String esxiPassword = getAutomationService().getPassword(esxiDeviceId, esxiUserName);
			deviceParamMap.put(VMFlds.ESXI_USERNAME, esxiUserName);
			deviceParamMap.put(VMFlds.ESXI_PASSWORD, esxiUserName);//esxiPassword
			deviceParamMap.put(VMFlds.ESXI_IP, esxiIp);
			// Esxi相关的信息 end
			deviceParamMap.put(VMFlds.HOST_NAME, hostDev.getDeviceName());// exi主机名
			String vmHostName = vmDev.getDeviceName();
			deviceParamMap.put(VMFlds.VM_HOST_NAME, vmHostName);// 虚拟机名称
			deviceParamMap.put(VMFlds.VAPP_NAME, vmHostName);// 虚拟机名称
			
//			String dsName = getAutomationService().getHostDatastore(esxiDeviceId);
			//根据虚拟机上绑定的datastore，查询datastore名称
			String dataStoreId = vmInfo.getDatastoreId();
			CmDatastorePo cmDatastorePo = cmHostDatastoreRefDAO.findDataStoreById(dataStoreId);
			if ( cmDatastorePo == null) {
				String msg = "找不到设备ID号为[" + deviceId + "]的Esxi服务器对应的datastore信息!";
				throw new Exception(msg);
			}
			deviceParamMap.put(VMFlds.DATASTORE_NAME, cmDatastorePo.getName());// datastore名称
			deviceParamMap.put(VMFlds.OVF_URL, imagePath);// ovf的路径
			deviceParamMap.put(VMFlds.TEMPLATE_NAME, templateName);//模板名称
			deviceParamMap.put(VMFlds.DATACENTER_NAME, datacenter.getDatacenterCode());
//			deviceParamMap.put(VMFlds.CPU_CORE_VALUE, srvAttrInfoMap.get("CPU_CORE_VALUE"));// 每个差插槽的CPU核数
			deviceParamMap.put(VMFlds.CPU_CORE_VALUE, getAutomationService().getCpuCoreNum(vmInfo.getCpu())+"");// 每个差插槽的CPU核数
			deviceParamMap.put(VMFlds.CPU_VALUE, String.valueOf(cpu));// cpu的的总核数信息
			// 将内存的大小转换为M
			deviceParamMap.put(VMFlds.MEMORY_VALUE, "" + String.valueOf(mem) + ".0");// 内存的信息
			deviceParamMap.put(VMFlds.NIC_DOMAIN_NAME, " ");// 要创建的域名
			if (vmServerNames.length() != 0) {
				vmServerNames.append(PubConstants.SEPARATOR_COLON);
			}
			vmServerNames.append(vmHostName);

			// 虚拟机的网络地址信息
			List<DeviceNetIP> vmIPInfoList = null;
			try {
				//vmIPInfoList = ipAllocToDevice.qryAllocedIP(Lists.newArrayList(deviceId));
				vmIPInfoList = ipAllocToDevice.qryAllocedIPForDevices(deviceId);
			} catch (Exception e) {
				throw new Exception("获取虚拟机网络地址信息出现异常!", e);
			}
			String vmNetType = RmPlatForm.X86.getValue() + RmVirtualType.VMWARE.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.MGMT.getValue();
			//XVMVM
			String vpNetType = RmPlatForm.X86.getValue() + RmVirtualType.VMWARE.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.PROD.getValue();
			if (CollectionUtils.isEmpty(vmIPInfoList) || CollectionUtils.isEmpty(vmIPInfoList.get(0).getNetIPs())/*
					|| CollectionUtils.isEmpty(vmIPInfoList.get(0).getNetIPs().get(vpNetType))
					|| CollectionUtils.isEmpty(vmIPInfoList.get(0).getNetIPs().get(vmNetType))*/) {
				String msg = "找不到设备ID号为[" + deviceId + "]的虚拟机对应的网络地址信息!";
				throw new Exception(msg);
			}
			
			// 生成虚拟机所有的生产ip参数 zhuzy
			List<NetIPInfo> productIps = vmIPInfoList.get(0).getNetIPs().get(vpNetType);
			if (productIps != null) {
				for (int i = 0; i < productIps.size(); i++) {
					String pip = productIps.get(i).getIp();
					String mask = productIps.get(i).getIpMask();
					String gw = productIps.get(i).getGateWay();
					deviceParamMap.put("PRODUCT_IP_" + (i + 1), pip);
					deviceParamMap.put("PRODUCT_MASK_" + (i + 1), mask);
					deviceParamMap.put("PRODUCT_GATEWAY_" + (i + 1), gw);
				}
			}
			// 生成虚拟机所有的私有ip参数 zhuzy
			String vpriNetType = RmPlatForm.X86.getValue() + RmVirtualType.VMWARE.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.PRI.getValue();
			List<NetIPInfo> privateIps = vmIPInfoList.get(0).getNetIPs().get(vpriNetType);
			if (privateIps != null) {
				for (int i = 0; i < privateIps.size(); i++) {
					String pip = privateIps.get(i).getIp();
					String mask = privateIps.get(i).getIpMask();
					String gw = privateIps.get(i).getGateWay();
					deviceParamMap.put("PRIVATE_IP_" + (i + 1), pip);
					deviceParamMap.put("PRIVATE_MASK_" + (i + 1), mask);
					deviceParamMap.put("PRIVATE_GATEWAY_" + (i + 1), gw);
				}
			}


			NetIPInfo prodNetIPInfo = null;
			if (vmIPInfoList.get(0).getNetIPs().get(vpNetType) != null) {
				prodNetIPInfo = vmIPInfoList.get(0).getNetIPs().get(vpNetType).get(0);
				String nicTypeNameProd = getAutomationService().getAppParameter("VM_PROD_NIC_TYPE_NAME");// 生产网卡
				String nicPortGroupProd = prodNetIPInfo.getExtendInfo().get(PORT_GROUP_KEY);// 生产端口组
				String nicPortGroupProdId = prodNetIPInfo.getExtendInfo().get(PORT_GROUP_ID_KEY);
				if(nicPortGroupProdId==null || nicPortGroupProdId.equals("")){
					String msg = "请先检查是否已将虚拟交换机信息导入到云平台,或检查该虚拟机IP所在的网络资源池，VLANID是否配置正确";
					logger.info(msg);
					throw new Exception(msg);
				}
				String nicIpProd = prodNetIPInfo.getIp();
				String nicMaskProd = prodNetIPInfo.getIpMask();
				String nicGateWayProd = prodNetIPInfo.getGateWay();
				deviceParamMap.put(SAConstants.NIC_TYPE_NAME_PROD, nicTypeNameProd);
				deviceParamMap.put(SAConstants.NIC_PORTGROUP_PROD, nicPortGroupProd);
				deviceParamMap.put(SAConstants.NIC_IP_PROD, nicIpProd);
				deviceParamMap.put(SAConstants.NIC_MASK_PROD, nicMaskProd);
				deviceParamMap.put(SAConstants.NIC_GATEWAY_PROD, nicGateWayProd);
				
				//更新虚拟机 ip对应的端口组id
				OpenstackIpAddressPo openstackIpAddressPo  = new OpenstackIpAddressPo();
				openstackIpAddressPo.setId(prodNetIPInfo.getId());
				openstackIpAddressPo.setPortGroupId(nicPortGroupProdId);
				cmVirtualSwitchService.updateRmNwOpstackIpAddress(openstackIpAddressPo);
			} else {
				logger.warn("!!!!!! 没有配置生产ip !!!!!!");
			}

			NetIPInfo mgmtNetIPInfo = null;
			String nicIpMgmt = null;
			if (vmIPInfoList.get(0).getNetIPs().get(vmNetType) != null) {
				mgmtNetIPInfo = vmIPInfoList.get(0).getNetIPs().get(vmNetType).get(0);
				String nicTypeNameMgmt = getAutomationService().getAppParameter("VM_MGR_NIC_TYPE_NAME");// 管理网卡
				String nicPortGroupMgmt = mgmtNetIPInfo.getExtendInfo().get(PORT_GROUP_KEY);// 管理
				String nicPortGroupMgtId = mgmtNetIPInfo.getExtendInfo().get(PORT_GROUP_ID_KEY);
				if(nicPortGroupMgtId==null || nicPortGroupMgtId.equals("")){
					String msg = "请先检查是否已将虚拟交换机信息导入到云平台,或检查该虚拟机IP所在的网络资源池，VLANID是否配置正确";
					logger.info(msg);
					throw new Exception(msg);
				}
				nicIpMgmt = mgmtNetIPInfo.getIp();
				String nicMaskMgmt = mgmtNetIPInfo.getIpMask();
				String nicGateWayMgmt = mgmtNetIPInfo.getGateWay();
				deviceParamMap.put(SAConstants.NIC_TYPE_NAME_MGMT, nicTypeNameMgmt);
				deviceParamMap.put(SAConstants.NIC_PORTGROUP_MGMT, nicPortGroupMgmt);
				deviceParamMap.put(SAConstants.NIC_IP_MGMT, nicIpMgmt);
				deviceParamMap.put(SAConstants.NIC_MASK_MGMT, nicMaskMgmt);
				deviceParamMap.put(SAConstants.NIC_GATEWAY_MGMT, nicGateWayMgmt);
				
				//更新虚拟机 ip对应的端口组id
//				RmNwIpAddressVo rmNwIpAddressVo = new RmNwIpAddressVo();
//				rmNwIpAddressVo.setIp(mgmtNetIPInfo.getIp());
//				rmNwIpAddressVo.setPortGroupId(nicPortGroupMgtId);
//				cmVirtualSwitchService.updateVmNetworkTag(rmNwIpAddressVo);
				OpenstackIpAddressPo openstackIpAddressPo  = new OpenstackIpAddressPo();
				openstackIpAddressPo.setId(mgmtNetIPInfo.getId());
				openstackIpAddressPo.setPortGroupId(nicPortGroupMgtId);
				cmVirtualSwitchService.updateRmNwOpstackIpAddress(openstackIpAddressPo);
				
			} else {
				logger.warn("!!!!!! 没有配置管理ip !!!!!!");
			}
			
			// String vmMgrIp = "192.168.35.10";// 管理IP地址
			deviceParamMap.put(VMFlds.VM_TYPE, getVmType());// 操作系统类型，固定值

			// 虚拟机的IP、用户信息,供脚本执行使用
			String vmPassword = getAutomationService().getPassword(vmInfo.getId(), vmUserName);
			deviceParamMap.put(SAConstants.SERVER_IP, nicIpMgmt);
			deviceParamMap.put(SAConstants.USER_NAME, vmUserName);
			deviceParamMap.put(SAConstants.USER_PASSWORD, vmPassword);

			// DNS相关的信息
			String vDnsServer1 = dnsInfoMap.get(SAConstants.V_DNS_SERVER1);// 第一个DNS服务器IP地址
			String vDnsServer2 = dnsInfoMap.get(SAConstants.V_DNS_SERVER2);// 第二个DNS服务器IP地址
			String dns = vDnsServer1 + PubConstants.SEPARATOR_COMMAS + vDnsServer2;// 域名服务器列表

			deviceParamMap.put(SAConstants.V_DNS_SERVER1, vDnsServer1);
			deviceParamMap.put(SAConstants.V_DNS_SERVER2, vDnsServer2);
			deviceParamMap.put(VMFlds.NIC_DNS, dns);
			// NTP相关的属性
			deviceParamMap.put(SAConstants.V_NTP_SERVER1, ntpInfoMap.get(SAConstants.V_NTP_SERVER1));// 第一个NTP服务器IP地址或域名（主）
			deviceParamMap.put(SAConstants.V_NTP_SERVER2, ntpInfoMap.get(SAConstants.V_NTP_SERVER2));// 第二个NTP服务器IP地址或域名
			deviceParamMap.put(SAConstants.V_NTP_SERVER3, ntpInfoMap.get(SAConstants.V_NTP_SERVER3));// 第三个NTP服务器IP地址或域名

			// 临时添加路由信息，保证云平台能够连上虚拟机，两地三中心根据数据中心读取路由
			List<CmRoutePo> routeList = null;
			if (null != dataCenterId) {
				routeList = getAutomationService().getRoutes(dataCenterId);
			} else {
				logger.debug("数据中心编号dataCenterId为空，无法查询路由信息，路由信息为空！");
			}
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
				deviceParamMap.put(VMFlds.ROUTE_IPS, ips.toString());// 路由Ip集合
				deviceParamMap.put(VMFlds.ROUTE_GWS, gateWays.toString());// 路由网关集合
				deviceParamMap.put(VMFlds.ROUTE_MASKS, masks.toString());// 路由子网掩码集合
			} else {
				String msg = "缺少路由配置信息，请维护CM_ROUTE表!";
				throw new java.lang.Exception(msg);
			}
			// ==================虚拟机相关参数 end ======================
			deviceSeq++;
			logger.info("设备ID号为[" + deviceId + "]的虚拟机自动化流程参数初始化 结束.");
		}
	}

	/**
	 * 获取操作系统类型
	 * @return
	 */
	public String getVmType() {

		return VmGlobalConstants.VM_TYPE_LINUX;
	}

	/**
	 * 获取操作系统的默认用户名称
	 * @throws Exception 
	 * @throws BizException 
	 */
	public String getVmUserName() throws BizException, Exception {
		return getAutomationService().getAppParameter("VM.DEFAULT_USER_NAME");
	}
	public ParameterService parameterService() throws Exception {
		return (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
	}
}