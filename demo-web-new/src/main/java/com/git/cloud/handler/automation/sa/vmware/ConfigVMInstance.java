package com.git.cloud.handler.automation.sa.vmware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.CUseType;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.common.MesgFlds;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmGlobalConstants;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;

/**
 * 配置虚机单进程
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 Aug 21, 2013
 * @see
 * 
 * 修改
 * 		2015-11-04 修改NIC_IP和NIC_MASK的内容，修改内容中管理和生产信息的顺序错误
 * 		2015-11-19 增加List类型的NIC_INFO参数，支持配置一个或多个网卡
 */
public class ConfigVMInstance extends BaseInstance {

	private static Logger log = LoggerFactory.getLogger(ConfigVMInstance.class);

//	private final static int TIME_OUT = 120 * 60 * 1000;
//
//	private boolean hasFailed = false;
//
//	@Override
//	protected int getTimeOut() {
//		return TIME_OUT;
//	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contextParams) throws Exception {

		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.CONFIG_VM);
		
		String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();
		
		String vCenterurl = contextParams.get(VMFlds.VCENTER_URL).toString();
		
		body.setString(VMFlds.VCENTER_URL,
				(String) contextParams.get(VMFlds.VCENTER_URL));
		body.setString(VMFlds.VCENTER_USERNAME,
				(String) contextParams.get(VMFlds.VCENTER_USERNAME));
		body.setString(VMFlds.VCENTER_PASSWORD,
				(String) contextParams.get(VMFlds.VCENTER_PASSWORD));
		body.setString(VMFlds.ESXI_URL,
				(String) contextParams.get(VMFlds.ESXI_URL));
		body.setString(VMFlds.ESXI_USERNAME,
				(String) contextParams.get(VMFlds.ESXI_USERNAME));
		body.setString(VMFlds.ESXI_PASSWORD,
				(String) contextParams.get(VMFlds.ESXI_PASSWORD));
		body.setString(VMFlds.HOST_NAME,
				(String) contextParams.get(VMFlds.HOST_NAME));
		body.setString(VMFlds.VAPP_NAME,
				(String) contextParams.get(VMFlds.VAPP_NAME));
		body.setString(VMFlds.OVF_URL,
				(String) contextParams.get(VMFlds.OVF_URL));
		body.setString(VMFlds.DATASTORE_NAME,
				(String) contextParams.get(VMFlds.DATASTORE_NAME));
		
//		================================导入和配置的分割线===============================================================
		
		body.setString(VMFlds.CPU_VALUE,
				(String) contextParams.get(VMFlds.CPU_VALUE));
		body.setString(VMFlds.CPU_CORE_VALUE,
				(String) contextParams.get(VMFlds.CPU_CORE_VALUE));
		body.setString(VMFlds.MEMORY_VALUE,
				(String) contextParams.get(VMFlds.MEMORY_VALUE));

		IDataObject nic1_obj = DataObject.CreateDataObject();
		IDataObject nic2_obj = DataObject.CreateDataObject();

		nic1_obj.setString(VMFlds.ADD_NIC_TYPE_NAME,
				(String) contextParams.get(SAConstants.NIC_TYPE_NAME_MGMT));
		nic2_obj.setString(VMFlds.ADD_NIC_TYPE_NAME,
				(String) contextParams.get(SAConstants.NIC_TYPE_NAME_PROD));

		nic1_obj.setString(VMFlds.ADD_NIC_PORTGROUP,
				(String) contextParams.get(SAConstants.NIC_PORTGROUP_MGMT));
		nic2_obj.setString(VMFlds.ADD_NIC_PORTGROUP,
				(String) contextParams.get(SAConstants.NIC_PORTGROUP_PROD));

		body.set(VMFlds.ADD_NIC1_OBJ, nic1_obj);
		body.set(VMFlds.ADD_NIC2_OBJ, nic2_obj);

		body.setString(VMFlds.VM_TYPE,
				(String) contextParams.get(VMFlds.VM_TYPE));
		body.setString(VMFlds.VM_HOST_NAME,
				(String) contextParams.get(VMFlds.VM_HOST_NAME));
		body.setString(VMFlds.NIC_DOMAIN_NAME,
				(String) contextParams.get(VMFlds.NIC_DOMAIN_NAME));

		String nic_ips = (String) contextParams.get(SAConstants.NIC_IP_MGMT)
				+ "," + (String) contextParams.get(SAConstants.NIC_IP_PROD);
		body.setString(VMFlds.NIC_IP, nic_ips);

		String nic_masks = (String) contextParams.get(SAConstants.NIC_MASK_MGMT)
				+ "," + (String) contextParams.get(SAConstants.NIC_MASK_PROD);
		body.setString(VMFlds.NIC_MASK, nic_masks);
		
		//windows虚机添加网关和dns
		if(VmGlobalConstants.VM_TYPE_WIN.equals(contextParams.get(VMFlds.VM_TYPE))){
			String nic_gws = null + "," + (String) contextParams
					.get(SAConstants.NIC_GATEWAY_PROD);
			body.setString(VMFlds.NIC_GATEWAY, nic_gws);
			String nic_dns = null + "," + (String)contextParams.get(SAConstants.V_DNS_SERVER1) + VmGlobalConstants.SPLIT_FLAG + 
					(String)contextParams.get(SAConstants.V_DNS_SERVER2);
			body.setString(VMFlds.NIC_DNS,nic_dns);
		}
		//linux虚机添加网关和dns
		else{
			body.setString(VMFlds.NIC_GATEWAY, null);
			body.setString(VMFlds.NIC_DNS,"");
		}	

		body.setString(VMFlds.NIC_DOMAIN_NAME, (String) contextParams
				.get(VMFlds.NIC_DOMAIN_NAME) == null ? ""
				: (String) contextParams.get(VMFlds.NIC_DOMAIN_NAME));

		body.setString(VMFlds.VM_TYPE,
				(String) contextParams.get(VMFlds.VM_TYPE));

		body.setString(VMFlds.GUEST_USER_NAME, (String) contextParams.get(SAConstants.USER_NAME));
		body.setString(VMFlds.GUEST_PASSWORD, (String) contextParams.get(SAConstants.USER_PASSWORD));
		
		body.setString(VMFlds.ROUTE_IPS, (String) contextParams.get(VMFlds.ROUTE_IPS));
		body.setString(VMFlds.ROUTE_GWS, (String) contextParams.get(VMFlds.ROUTE_GWS));
		body.setString(VMFlds.ROUTE_MASKS, (String) contextParams.get(VMFlds.ROUTE_MASKS));
		
		// 配置ip节点参数优化，支持一个或多个网卡配置
		List<DeviceNetIP> vmIPInfoList = null;
		@SuppressWarnings("unchecked")
		List<String> devIds = (List<String>) contextParams.get("destVmIds");
		log.info("devIds:{}",devIds);
		if ( devIds.isEmpty() || devIds.size() >1 ) {
			throw new Exception("获取虚拟机Id 异常");
		}
		try {
			vmIPInfoList = getIIpAllocToDeviceService().qryAllocedIPForDevices(devIds.get(0));
//			getIIpAllocToDeviceService().qryAllocedIP(devIds);
			String vmNetType = RmPlatForm.X86.getValue() + RmVirtualType.VMWARE.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.MGMT.getValue();
			String vpNetType = RmPlatForm.X86.getValue() + RmVirtualType.VMWARE.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.PROD.getValue();
			String vpriNetType = RmPlatForm.X86.getValue() + RmVirtualType.VMWARE.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.PRI.getValue();
			// 生成所有ip的list类型的参数 2015-11-19
			log.debug("生成所有ip的list类型的参数 2015-11-19");
			List<IDataObject> nicInfos = new ArrayList<IDataObject>();
			List<NetIPInfo> allIps = new ArrayList<NetIPInfo>();
			if (vmIPInfoList.get(0).getNetIPs().get(vmNetType) != null) {
				allIps.addAll(vmIPInfoList.get(0).getNetIPs().get(vmNetType));
			}
			if (vmIPInfoList.get(0).getNetIPs().get(vpNetType) != null) {
				allIps.addAll(vmIPInfoList.get(0).getNetIPs().get(vpNetType));
			}
			if (vmIPInfoList.get(0).getNetIPs().get(vpriNetType) != null) {
				allIps.addAll(vmIPInfoList.get(0).getNetIPs().get(vpriNetType));
			}
			for (NetIPInfo ipobj : allIps) {
				IDataObject obj = BodyDO.CreateDataObject();
				obj.setString(VMFlds.ADD_NIC_TYPE_NAME, getAutomationService().getAppParameter("VM_MGR_NIC_TYPE_NAME"));
				obj.setString(VMFlds.ADD_NIC_PORTGROUP, ipobj.getExtendInfo().get("VMWARE_PGNAM"));
				obj.setString("IP", ipobj.getIp());
				obj.setString("MASK", ipobj.getIpMask());
				obj.setString("GATEWAY", ipobj.getGateWay());
				nicInfos.add(obj);
			}
			body.setList("NIC_INFO", nicInfos);
			log.debug("生成所有ip的list类型的参数 完毕。");
		} catch (Exception e) {
			Utils.printExceptionStack(e);
			throw new Exception("获取虚拟机网络地址并组装参数报文出现异常!", e);
		}
		
		reqData.setDataObject(MesgFlds.BODY, body);

		return reqData;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contextParams,
			IDataObject responseDataObject) {

	}

	protected String handleResonpse(String devId,
			Map<String, Object> contextParams, IDataObject responseDataObject) throws Exception{
		
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER,  HeaderDO.class);
		
				return null;
		
	}



}
