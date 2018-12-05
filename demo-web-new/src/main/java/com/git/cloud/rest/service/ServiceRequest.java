package com.git.cloud.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.po.DeployUnitPo;
import com.git.cloud.appmgt.service.IDeployunitService;
import com.git.cloud.cloudservice.model.po.CloudServiceFlowRefPo;
import com.git.cloud.common.enums.AllocedStatus;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.vo.OpenstackVmParamVo;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.request.dao.IBmSrAttrValDao;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.dao.IVirtualExtendDAO;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.vo.BmSrAttrValVo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.cloud.request.service.IRequestWorkflowService;
import com.git.cloud.request.service.IVirtualSupplyService;
import com.git.cloud.request.tools.SrCodeGenerator;
import com.git.cloud.request.tools.SrDateUtil;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.DeviceStatusEnum;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;
import com.git.cloud.resmgt.network.dao.IVirtualNetworkDao;
import com.git.cloud.resmgt.network.dao.IVirtualSubnetDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.po.VirtualNetworkPo;
import com.git.cloud.resmgt.network.model.po.VirtualSubnetPo;
import com.git.cloud.resmgt.network.service.IVirtualNetworkService;
import com.git.cloud.rest.model.ReturnMeta;
import com.git.cloud.rest.model.ServiceRequestParam;
import com.google.common.collect.Lists;


public abstract class ServiceRequest {
	private static Logger logger = LoggerFactory.getLogger(ServiceRequest.class);
	BmSrRrinfoVo rrinfoVo;
	BmSrRrinfoPo rrinfoPo;
	BmSrVo bmSr ;
	List<CloudServiceFlowRefPo> flowIdList;
	@Autowired
	private IVirtualNetworkService virtualNetworkService;
	
	public abstract BmSrVo startService(String type, Object serviceRequestParam) throws RollbackableBizException;
	
	public abstract ReturnMeta startServiceAuto(String type, ServiceRequestParam serviceRequestParam) throws Exception;
	/**
	 * 虚拟机供给主流程
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public BmSrVo saveVirtualSupply(JSONObject serviceRequestParam) throws RollbackableBizException {
		try {
			logger.info("saveVirtualSupply serviceRequestParam:{}",serviceRequestParam);
			String operModelType = serviceRequestParam.getString("operModelType");
			logger.info("saveVirtualSupply operModelType :"+operModelType);
			String serviceId = serviceRequestParam.getString("serviceId");
			String dataCenterId = serviceRequestParam.getString("datacenterId");
			int orderId = 0;
			String creatorId = serviceRequestParam.getString("creatorId");
			String tenantId = serviceRequestParam.getString("tenantId");
			JSONArray attrValList = serviceRequestParam.getJSONArray("attrValList");
			
			// 创建服务申请BM_SR
			bmSr = new BmSrVo();
			String srId = UUIDGenerator.getUUID();
			bmSr.setSrCode(SrCodeGenerator.getInstance().getSRID(operModelType));
			bmSr.setCreateTime(SrDateUtil.getSrFortime(new Date()));
			bmSr.setSrId(srId);
			bmSr.setAppId(serviceRequestParam.get("appId") == null ? null : serviceRequestParam.getString("appId"));
			bmSr.setSrTypeMark(operModelType);
			bmSr.setDatacenterId(dataCenterId);
			bmSr.setApproveMark(srId);
			bmSr.setSrStatusCode("");
			bmSr.setCreatorId(creatorId);
			bmSr.setTenantId(tenantId);
			bmSrDao().insertBmSr(bmSr); // 新增服务请求对象
			
			//服务器角色不为空时，为服务器角色绑定云服务
			Object duId = serviceRequestParam.get("duId");
			if(duId != null){
				DeployUnitPo deployUnitPo = new DeployUnitPo();
				deployUnitPo.setDuId(duId.toString());
				deployUnitPo.setServiceId(serviceId);
				deployUtImpl().updateDeployUnitServiceId(deployUnitPo);
			}
			
			// 获取云服务对应的流程实例
			Map<String,Object> flowparam = new HashMap<String,Object>();
			flowparam.put("serviceId", serviceId);
			flowparam.put("srTypeMark",operModelType);
			flowparam.put("isActive", IsActiveEnum.YES.getValue());
			List<CloudServiceFlowRefPo> csflow = findListParam().findListByParam("getCloudServiceFlowId", flowparam);
			String flowId = "";
			if(csflow == null || csflow.size() == 0) {
				logger.error("未到流程模板信息，云服务ID为：{}",serviceId);
				throw new RollbackableBizException("不能找到流程模板信息");
			} else {
				flowId = csflow.get(0).getFlowId();
			}
			
			// 创建资源申请BM_SR_RRINFO
			String rrinfoId = UUIDGenerator.getUUID();
			serviceRequestParam.remove("attrValList");
			if(serviceRequestParam.getBooleanValue("fiFlag") && !serviceRequestParam.containsKey("serviceInstanceId")){
				serviceRequestParam.put("serviceInstanceId", UUIDGenerator.getUUID());
				int n  = 2;
				JSONArray jsoa = serviceRequestParam.getJSONArray("componentList");
				for (Object object : jsoa) {
					if("8".equals(object.toString())||"9".equals(object.toString())||"13".equals(object.toString())||"20".equals(object.toString())){
						n++;
					}
				}
				serviceRequestParam.put("floatIpNum", n);
			}
			rrinfoVo = new BmSrRrinfoVo();
			rrinfoVo.setParametersJson(serviceRequestParam.toJSONString());
			rrinfoVo.setRrinfoId(rrinfoId);
			rrinfoVo.setDuId(serviceRequestParam.getString("duId"));
			rrinfoVo.setServiceId(serviceRequestParam.getString("serviceId"));
			rrinfoVo.setDataCenterId(dataCenterId);
			rrinfoVo.setSrId(bmSr.getSrId());
			rrinfoVo.setFlowId(flowId);
			rrinfoVo.setCreateTime(SrDateUtil.getSrFortime(new Date()));// 创建时间
			rrinfoVo.setOrderId(orderId);
			rrinfoVo.setTenantId(tenantId);
			bmSrRrinfoDao().insertBmSrRrinfo(rrinfoVo);
			
			//向参数表中，添加供给参数对象
			if(attrValList != null){
				List<BmSrAttrValVo> attrList = Lists.newArrayList();
				for (Object valVoObject:attrValList) {
					BmSrAttrValVo valVo = ((JSONObject)valVoObject).toJavaObject(BmSrAttrValVo.class);
					valVo.setSrAttrValId(UUIDGenerator.getUUID());
					valVo.setRrinfoId(rrinfoVo.getRrinfoId());
					attrList.add(valVo);
				}
				bmSrAttrValDao().insertBmSrAttrList(attrList);
			}
		} catch (RollbackableBizException e) {
			logger.error("请检查传递的供给服务参数是否有误,错误信息：{}",e);
			throw new RollbackableBizException(e);
		} catch (Exception e) {
			logger.error("请检查传递的供给服务参数是否有误,错误信息：{}",e);
			throw new RollbackableBizException(e);
		}
		return bmSr;
	}
	/**
	 * 虚拟机扩容主流程
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public BmSrVo saveVirtualExtend(JSONObject serviceRequestParam)throws RollbackableBizException{
		try {
			logger.info("saveVirtualExtend serviceRequestParam:{}",serviceRequestParam);
			String serviceId = serviceRequestParam.getString("serviceId");
			String operModelType = serviceRequestParam.getString("operModelType");
			String dataCenterId = serviceRequestParam.getString("datacenterId");
			String deviceId = serviceRequestParam.getString("deviceId");
			String appId = serviceRequestParam.get("appId") == null ? null :serviceRequestParam.getString("appId");
			String creatorId = serviceRequestParam.getString("creatorId");
			String tenantId = serviceRequestParam.getString("tenantId");
			
			List<CmVmPo> vmList = new ArrayList<CmVmPo>();
			CmVmPo cmVmPo = null;
			int orderId = 0;
			if(!CommonUtil.isEmpty(deviceId)){
				cmVmPo = cmVmDao().findCmVmById(deviceId);
				if(cmVmPo != null){
					vmList.add(cmVmPo);
				}else{
					throw new RollbackableBizException("查询设备信息，失败，deviceId：" + deviceId);
				}
			}else{
				throw new RollbackableBizException("传递的设备ID为空，请检查传递参数");
			}
			// 创建服务申请BM_SR
			bmSr = new BmSrVo();
			String srId = UUIDGenerator.getUUID();
			bmSr.setSrCode(SrCodeGenerator.getInstance().getSRID(operModelType));
			bmSr.setCreateTime(SrDateUtil.getSrFortime(new Date()));
			bmSr.setSrId(srId);
			bmSr.setAppId(appId);
			bmSr.setSrTypeMark(operModelType);
			bmSr.setDatacenterId(dataCenterId);
			bmSr.setApproveMark(srId);
			bmSr.setSrStatusCode("");
			bmSr.setCreatorId(creatorId);
			bmSr.setTenantId(tenantId);
			bmSrDao().insertBmSr(bmSr); // 新增服务请求对象
			
			// 获取云服务对应的流程实例
			Map<String,Object> flowparam = new HashMap<String,Object>();
			flowparam.put("serviceId", serviceId);
			flowparam.put("srTypeMark",operModelType);
			flowparam.put("isActive", IsActiveEnum.YES.getValue());
			List<CloudServiceFlowRefPo> csflow = findListParam().findListByParam("getCloudServiceFlowId", flowparam);
			String flowId = "";
			if(csflow == null || csflow.size() == 0) {
				logger.error("未到流程模板信息，云服务ID为：{}",serviceId);
				throw new RollbackableBizException("不能找到流程模板信息");
			} else {
				flowId = csflow.get(0).getFlowId();
			}
			
			// 创建资源申请BM_SR_RRINFO
			String rrinfoId = UUIDGenerator.getUUID();
			serviceRequestParam.remove("attrValList");
			rrinfoVo = new BmSrRrinfoVo();
			rrinfoVo.setParametersJson(serviceRequestParam.toJSONString());
			rrinfoVo.setRrinfoId(rrinfoId);
			rrinfoVo.setSrId(bmSr.getSrId());
			rrinfoVo.setFlowId(flowId);
			rrinfoVo.setCreateTime(SrDateUtil.getSrFortime(new Date()));// 创建时间
			rrinfoVo.setOrderId(orderId);
			rrinfoVo.setTenantId(tenantId);
			bmSrRrinfoDao().insertBmSrRrinfo(rrinfoVo);
			
			CmDevicePo vmDevice;
			List<BmSrRrVmRefPo> vmRefList = new ArrayList<BmSrRrVmRefPo> ();
			BmSrRrVmRefPo vmRef;
			CmVmPo cmVm;
			if(vmList.size() > 0) {
				vmRefList = new ArrayList<BmSrRrVmRefPo> ();
				for(int i=0 ; i<vmList.size() ; i++) {
					cmVm = vmList.get(i);
					vmRef = new BmSrRrVmRefPo();
					vmRef.setRefId(UUIDGenerator.getUUID());
					vmRef.setRrinfoId(rrinfoVo.getRrinfoId());
					vmRef.setSrId(bmSr.getSrId());
					vmRef.setDeviceId(cmVm.getId());
					vmRef.setCpuOld(cmVm.getCpu());
					vmRef.setMemOld(cmVm.getMem());
					vmRefList.add(vmRef);
					//更新设备构建状态
					vmDevice = cmDeviceDao().findCmDeviceById(vmRefList.get(i).getDeviceId());
                    vmDevice.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_CHANGING.getValue());
                    cmDeviceDao().updateCmDeviceState(vmDevice);
				}
				bmSrRrVmRefDao().insertBmSrRrVmRef(vmRefList); //添加扩容对象
			} else {
				throw new RollbackableBizException("未找到虚拟机列表信息");
			}
		} catch (RollbackableBizException e) {
			logger.error("请检查传递的扩容服务参数是否有误,错误信息：{}",e);
			throw new RollbackableBizException(e);
		} catch (Exception e) {
			logger.error("请检查传递的扩容服务参数是否有误,错误信息：{}",e);
			throw new RollbackableBizException(e);
		}
		return bmSr;
	}
	
	/**
	 * 虚拟机回收主流程
	 * @param jsonData
	 * @return
	 * @throws RollbackableBizException
	 * @throws Exception
	 */
	public BmSrVo saveVirtualRecycle(JSONObject serviceRequestParam)throws RollbackableBizException{
		try {
			logger.info("saveVirtualRecycle serviceRequestParam:{}",serviceRequestParam);
			String serviceId = serviceRequestParam.getString("serviceId");
			String operModelType = serviceRequestParam.getString("operModelType");
			String deviceId = serviceRequestParam.getString("id");
			int orderId = 0;
			String creatorId = serviceRequestParam.getString("creatorId");
			String tenantId = serviceRequestParam.getString("tenantId");
			String dcId = serviceRequestParam.getString("dcId");
			
			// 创建服务申请BM_SR
			bmSr = new BmSrVo();
			String srId = UUIDGenerator.getUUID();
			bmSr.setSrCode(SrCodeGenerator.getInstance().getSRID(operModelType));
			bmSr.setCreateTime(SrDateUtil.getSrFortime(new Date()));
			bmSr.setSrId(srId);
			bmSr.setDatacenterId(dcId);
			bmSr.setSrTypeMark(operModelType);
			bmSr.setApproveMark(srId);
			bmSr.setSrStatusCode("");
			bmSr.setCreatorId(creatorId);
			bmSr.setTenantId(tenantId);
			bmSrDao().insertBmSr(bmSr); // 新增服务请求对象
			
			// 获取云服务对应的流程实例
			Map<String,Object> flowparam = new HashMap<String,Object>();
			flowparam.put("serviceId", serviceId);
			flowparam.put("srTypeMark",operModelType);
			flowparam.put("isActive", IsActiveEnum.YES.getValue());
			List<CloudServiceFlowRefPo> csflow = findListParam().findListByParam("getCloudServiceFlowId", flowparam);
			String flowId = "";
			if(csflow == null || csflow.size() == 0) {
				logger.error("未到流程模板信息，云服务ID为：{}",serviceId);
				throw new RollbackableBizException("不能找到流程模板信息");
			} else {
				flowId = csflow.get(0).getFlowId();
			}
			
			// 创建资源申请BM_SR_RRINFO
			String rrinfoId = UUIDGenerator.getUUID();
			rrinfoVo = new BmSrRrinfoVo();
			rrinfoVo.setParametersJson(serviceRequestParam.toJSONString());
			rrinfoVo.setRrinfoId(rrinfoId);
			rrinfoVo.setSrId(bmSr.getSrId());
			rrinfoVo.setFlowId(flowId);
			rrinfoVo.setVmNum(1);
			rrinfoVo.setDataCenterId(dcId);
			rrinfoVo.setCreateTime(SrDateUtil.getSrFortime(new Date()));// 创建时间
			rrinfoVo.setOrderId(orderId);
			rrinfoVo.setTenantId(tenantId);
			bmSrRrinfoDao().insertBmSrRrinfo(rrinfoVo);
			//关联关系表中插入数据
			BmSrRrVmRefPo vmRef;
			List<BmSrRrVmRefPo> vmRefList = new ArrayList<BmSrRrVmRefPo> ();
			vmRef = new BmSrRrVmRefPo();
			vmRef.setRefId(UUIDGenerator.getUUID());
			vmRef.setSrId(srId);
			vmRef.setRrinfoId(rrinfoId);
			vmRef.setDeviceId(deviceId);
			vmRefList.add(vmRef);
			bmSrRrVmRef().insertBmSrRrVmRef(vmRefList);
			//更新设备构建状态
			CmDevicePo vmDevice = cmDeviceDao().findCmDeviceById(deviceId);
            vmDevice.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_RECYCLEING.getValue());
            cmDeviceDao().updateCmDeviceState(vmDevice);
		} catch (RollbackableBizException e) {
			logger.error("请检查传递的回收服务参数是否有误：",e);
			throw new RollbackableBizException(e);
		} catch (Exception e) {
			logger.error("请检查传递的回收服务参数是否有误",e);
			throw new RollbackableBizException(e);
		}
		return bmSr;
	}
	
	/**
	 * 创建主流程
	 * @return
	 * @throws Exception 
	 * @throws RollbackableBizException 
	 */
	public String createMainInstance(BmSrVo bmSr) throws RollbackableBizException{
		String instanceId = "";
		try {
			logger.info("开始创建审批流程，srId：{},srCode:{}",bmSr.getSrId(),bmSr.getSrCode());
			instanceId = requestWorkflowService().createMainWorkflowInstance(bmSr.getSrId(), bmSr.getSrCode(), null);
			if(instanceId.equals("")){
				throw new RollbackableBizException("创建主流程信息失败");
			}
		} catch (Exception e) {
			logger.error("创建审批流程失败：{}",e);
			throw new RollbackableBizException(e);
		}
		return instanceId;
	}
	/**
	 * 创建主流程
	 * @return
	 * @throws Exception 
	 * @throws RollbackableBizException 
	 */
	public String createMainInstance(String srId, String srCode, String srTypeMark) throws RollbackableBizException{
		String instanceId = "";
		HashMap<String, String> wfParam = null;
		try {
			logger.info("开始创建审批流程，srId：{},srCode:{}", srId, srCode);
			instanceId = requestWorkflowService().createMainWorkflowInstance(srId, srCode, wfParam);
			if(instanceId.equals("")){
				throw new RollbackableBizException("创建主流程信息失败");
			}
		} catch (Exception e) {
			logger.error("创建审批流程失败：{}",e);
			throw new RollbackableBizException(e);
		}
		return instanceId;
	}
	public void startMainInstance(String instanceId) throws RollbackableBizException{
		try {
			requestWorkflowService().startMainWorkflowInstance(instanceId);
			requestBaseService().updateBmSrStatus(bmSr.getSrId(), SrStatusCodeEnum.REQUEST_WAIT_APPROVE.getValue());
			logger.info("启动审批流程结束，instanceId:{}",instanceId);
		} catch (Exception e) {
			logger.error("启动审批流程失败",e);
			throw new RollbackableBizException(e);
		}
		
	}
	public void startMainInstance(String srId, String instanceId) throws RollbackableBizException{
		try {
			requestWorkflowService().startMainWorkflowInstance(instanceId);
			requestBaseService().updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_WAIT_APPROVE.getValue());
			logger.info("启动审批流程结束，instanceId:{}",instanceId);
		} catch (Exception e) {
			logger.error("启动审批流程失败",e);
			throw new RollbackableBizException(e);
		}
		
	}
	public void startDriveWorkflow(String todoId, String driveWfType) throws Exception{
		try {
			requestWorkflowService().driveWorkflow(todoId, driveWfType);
		} catch (Exception e) {
			logger.error("驱动流程失败",e);
			throw new RollbackableBizException(e);
		}
	}
	/**
	 * 添加网卡
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public  ReturnMeta addNetworkCard(ServiceRequestParam serviceRequestParam) throws Exception{
		ReturnMeta result = new ReturnMeta();
		try {
			String platformCode = serviceRequestParam.getPlatformCode();
			if("O".equals(platformCode)){
				/*第一步，初始化参数信息,验证传递虚拟机id和网络资源池id*/
				String deviceId = serviceRequestParam.getDeviceId();
				if(CommonUtil.isEmpty(deviceId)) {
					throw new Exception("设备ID为空，请检查参数，"+serviceRequestParam);
				}
				String networkId = serviceRequestParam.getRmNwResPoolId();//选择的网络资源池id
				if(CommonUtil.isEmpty(networkId)) {
					throw new Exception("内部网络ID为空，请检查参数，"+serviceRequestParam);
				}
				String addIpAddress = serviceRequestParam.getAddIpAddress();//页面选择的ip
				List<VirtualSubnetPo> subnetList = getVirtualSubnetDaoImpl().selectSubnetByNetwork(networkId);
				if(subnetList == null || subnetList.size() == 0) {
					logger.error("[addNetworkCard]获取内部网络子网信息失败,networkId:" + networkId);
					throw new Exception("[addNetworkCard]获取内部网络子网信息失败,networkId:" + networkId);
				}
				OpenstackVmParamVo openstackVmParamVo = getAutomationService().findOpenstackVmParamByVmId(deviceId);
				if(openstackVmParamVo == null) {
					logger.error("[addNetworkCard]获取设备相关信息失败,deviceID:" + deviceId);
					throw new Exception("[addNetworkCard]获取设备相关信息失败,deviceID:" + deviceId);
				}
				String openstackId = openstackVmParamVo.getOpenstackId();
				
				String targetProjectId = projectVpcDao().findProjectByProjectId(openstackVmParamVo.getProjectId()).getIaasUuid();
				String targetServerId = cmVmDAO().findCmVmById(deviceId).getIaasUuid();

				String openstackIp = openstackVmParamVo.getOpenstackIp();
				String domainName = openstackVmParamVo.getDomainName();
				String projectName =  openstackVmParamVo.getProjectName();
				String hostType = RmHostType.VIRTUAL.getValue();
				
				//第二步，分配IP
				logger.debug("分配IP开始...");
				VirtualNetworkPo virtualNetworkPo = virtualNetworkDaoImpl().selectVirtualNetwork(networkId);
				if(virtualNetworkPo == null) {
					logger.error("[addNetworkCard]获取内部网络信息失败,networkId:" + networkId);
					throw new Exception("[addNetworkCard]获取内部网络信息失败,networkId:" + networkId);
				}
				String targetNetworkId = virtualNetworkPo.getIaasUuid();
				
				VirtualSubnetPo virtualSubnetPo = virtualSubnetDaoImpl().selectVirtualSubnetPoById(subnetList.get(0).getVirtualSubnetId());
				if(virtualSubnetPo == null) {
					logger.error("[addNetworkCard]获取内部网络子网信息失败,子网id:" + subnetList.get(0).getVirtualSubnetId());
					throw new Exception("[addNetworkCard]获取内部网络子网信息失败,子网id:" + subnetList.get(0).getVirtualSubnetId());
				}
				String targetSubnetId = virtualSubnetPo.getIaasUuid();
				
				// 开始分配IP地址
				String newIpAddressId = null;
				if(!CommonUtil.isEmpty(addIpAddress)) {
					//页面传递的ip地址不为空，检查页面传递的ip是否被占用
					OpenstackIpAddressPo validateIp = virtualNetworkService.selectIpAddressByNetworkAndIp(networkId, addIpAddress);
					if(!CommonUtil.isEmpty(validateIp)) {
						if(validateIp.getUseRelCode().equals(AllocedStatus.NOT_ALLOC.getValue())) {
							newIpAddressId = validateIp.getId();
							logger.info("新录入的IP地址["+addIpAddress+"]可以使用.");
						}else {
							throw new RollbackableBizException("指定的IP["+addIpAddress+"]不可用, 状态标识为["+validateIp.getUseRelCode()+"]");
						}
					}else {
						throw new RollbackableBizException("指定的IP["+addIpAddress+"]不在网络资源池中.");
					}
				}else {
					logger.debug("前台传递ip地址为空，后台自动分配ip"+serviceRequestParam);
					List<OpenstackIpAddressPo> ipList = getVirtualNetworkService().selectIpAddressByNetwork(networkId);
					if(ipList == null || ipList.size() == 0) {
						throw new RollbackableBizException("未查询到可以使用的IP地址.");
					}
					newIpAddressId = ipList.get(0).getId();
					logger.info("设备id："+deviceId+",从资源池中分配的ip为："+ipList.get(0).getIp());
				}
			
				OpenstackIpAddressPo updateIpAddress = new OpenstackIpAddressPo();
				updateIpAddress.setId(newIpAddressId);
//				updateIpAddress.setInstanceId(deviceId);
				updateIpAddress.setUseRelCode(AllocedStatus.ALLOCTODEV.getValue()); // A2DV为已分配
				// 保存IP占用信息
				getVirtualNetworkService().updateIpAddressByNetwork(updateIpAddress);
				logger.debug("分配IP结束...");
				
				//第三步，获取token
				if(openstackIp == null || "".equals(openstackIp)) {
					throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
				}
				if(projectName == null || "".equals(projectName)) {
					throw new Exception("Project名称为空，请检查参数[PROJECT_NAME].");
				}
				
				String version = openstackVmParamVo.getVersion();
				OpenstackIdentityModel model = new OpenstackIdentityModel();
	            model.setVersion(version);
	            model.setOpenstackIp(openstackIp);
	            model.setDomainName(domainName);
	            model.setManageOneIp(openstackVmParamVo.getManageOneIp());
	            model.setProjectName(projectName);
				model.setProjectId(targetProjectId);
				
				//第四步，查询设备状态（CheckServerActiveStatusHandler）
				Boolean isVm = null;
				if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
					isVm = true;
				} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
					isVm = false;
				} else {
					logger.error("[addNetworkCard]未查询到可以使用的IP地址,主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
					throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
				}
				String state = "";
				try {
					state = IaasInstanceFactory.computeInstance(version).getServerState(model, targetServerId, isVm);
				} catch(Exception e) {
					logger.error("[addNetworkCard]调底层接口查询设备状态失败deviceId=" + deviceId);
					throw new Exception("查询设备状态失败");
				}
				logger.info("设备:"+deviceId+",状态为："+state);
				if(state.equals("ACTIVE")) {
					/*查询网卡状态,同步网卡信息
					 *本次添加的网卡和机器中已有网卡相同，直接同步数据至数据库
					 *本次添加的网卡和机器中已有网卡不同，直接添加网卡，进行后续数据处理*/
					
					List<OpenstackIpAddressPo> dIpList = getVirtualNetworkService().selectIpAddressByDeviceId(deviceId);
					List<String> deviceIpList = new ArrayList<String>();
					for(OpenstackIpAddressPo i : dIpList) {
						deviceIpList.add(i.getIp());
					}
					List<String> interfaceReturnIpLis = new ArrayList<String>();
					logger.debug("查询网卡开始...");
					String networkCard = IaasInstanceFactory.computeInstance(version).getNetworkCard(model, targetServerId);
					logger.debug("查询网卡完成，开始进行数据处理...");
					JSONObject json = JSONObject.parseObject(networkCard);
					JSONArray jsa = json.getJSONArray("interfaceAttachments");
					Iterator<Object> iterator = jsa.iterator();
					Boolean newIpAddressOperateFlag = false;
					while (iterator.hasNext()) {
						Object next = iterator.next();
						JSONObject nextObject = JSONObject.parseObject(next.toString());
						JSONArray fixed_ips = nextObject.getJSONArray("fixed_ips");
						Iterator iterator2 = fixed_ips.iterator();
						while (iterator2.hasNext()) {
							Object next2 = iterator2.next();
							JSONObject fromObject = JSONObject.parseObject(next2.toString());
							String ipAddress = fromObject.get("ip_address").toString();
							interfaceReturnIpLis.add(ipAddress);
							if(addIpAddress.equals(ipAddress)) {
								newIpAddressOperateFlag = true;//资源池中分配的网卡已经被使用
							}
						}
					}
					logger.info("设备Id:"+deviceId+",接口返回网卡信息:"+interfaceReturnIpLis);
					for(String i: interfaceReturnIpLis) {
						//接口返回的ip，云平台中未被占用的，需要在云平台中占用该ip
						if(!deviceIpList.contains(i)){
							//ip数据处理（IpDataHandler）
							logger.debug("同步接口返回的ip数据，处理开始...");
							//根据ip，查询资源池及其相关信息
							updateIpAddress.setInstanceId(deviceId);
							// 保存IP占用信息
							getVirtualNetworkService().updateIpAddressByNetwork(updateIpAddress);
						}
					}
					//分配的网卡未被使用
					if(!newIpAddressOperateFlag) {
						//第五步，创建PORT并制定IP（CreatePortHandler）
						logger.debug("创建端口并指定IP开始...");
						String jsonData = IaasInstanceFactory.networkInstance(version).createPort(model, targetNetworkId, targetSubnetId, addIpAddress, "");
						String newPortId = JSONObject.parseObject(jsonData).getJSONObject("port").get("id").toString();
						logger.debug("创建端口并指定IP结束...");
						
						//第六步，添加网卡（AddNetworkCardTHandler），并查询网卡是否添加成功（CheckNetworkCardStatusHandler）
						logger.debug("添加网卡开始...");
						for (int i = 0; i < 1000;  i++) {
							String portListJson = IaasInstanceFactory.networkInstance(version).getPortList(model);
							logger.info(portListJson);
							JSONArray ports = JSONObject.parseObject(portListJson).getJSONArray("ports");
							boolean exist = false;
							for(Object obj : ports) {
								JSONObject portObj = (JSONObject) obj;
								if(newPortId.equals(portObj.getString("id"))) {
									exist = true;
									break;
								}
							}
							if(exist) {
								break;
							}
							else {
								Thread.sleep(500);
							}
						}
						try {
							IaasInstanceFactory.computeInstance(version).addNetworkCard(model, targetServerId, newPortId);
						} catch(Exception e1) {
							// 删除创建的端口
							try {
								IaasInstanceFactory.networkInstance(version).deletePort(model, newPortId);
							} catch(Exception e2) {
								logger.error("添加网卡失败，删除端口失败");
								throw new Exception ("添加网卡失败，删除端口失败");
							}
							throw new Exception ("创建端口成功，添加网卡失败");
						}
						try {
							// 休眠10秒
							Thread.sleep(10000);
							String networkCardStatus = IaasInstanceFactory.computeInstance(version).getNetworkCardStatus(model, targetServerId, newPortId);
							if(!networkCardStatus.equals("ACTIVE")){
								logger.error("查询网卡状态失败,状态："+networkCardStatus);
								throw new Exception("网卡状态错误");
							}
						} catch(Exception e) {
							logger.error("查询网卡状态失败");
							throw new Exception(e);
						}
						logger.debug("添加网卡结束...");
						
						//第七步，ip数据处理（IpDataHandler）
						logger.debug("ip数据处理开始...");
						//根据ip，查询资源池及其相关信息
						updateIpAddress.setInstanceId(deviceId);
						// 保存IP占用信息
						getVirtualNetworkService().updateIpAddressByNetwork(updateIpAddress);
						logger.debug("ip数据结束...");
					}
					result.setCode("0000");
				}else {
					logger.error("设备状态为："+state+",请检查设备状态");
					result.setCode("9999");	
					throw new Exception("设备状态为："+state+",请检查设备状态");
				}
			}else if("PV".equals(platformCode)){/*
				第一步，初始化参数信息,验证传递虚拟机id和网络资源池id
				String deviceId = serviceRequestParam.getDeviceId();
				if(CommonUtil.isEmpty(deviceId)) {
					throw new Exception("设备ID为空，请检查参数，"+serviceRequestParam);
				}
				String netPoolId = serviceRequestParam.getRmNwResPoolId();//选择的网络资源池id
				if(CommonUtil.isEmpty(netPoolId)) {
					throw new Exception("网络资源池ID为空，请检查参数，"+serviceRequestParam);
				}
				RmGeneralServerVo server = getAutomationService().findDeviceServerInfo(deviceId);
				if(server == null) {
					logger.error("[addNetworkCard]获取服务器信息失败,deviceID:" + deviceId);
					throw new Exception("[addNetworkCard]获取服务器信息失败,deviceID:" + deviceId);
				}
				OpenstackVmParamVo openstackVmParamVo = getAutomationService().findOpenstackVmParamByVmId(deviceId);
				if(openstackVmParamVo == null) {
					logger.error("[addNetworkCard]获取设备相关信息失败,deviceID:" + deviceId);
					throw new Exception("[addNetworkCard]获取设备相关信息失败,deviceID:" + deviceId);
				}
				String openstackId = openstackVmParamVo.getOpenstackId();
				String targetProjectId = AdminKeyMapUtil.getAdminKeyMapService().getTargetIdByMyIdForOpenstack(KeyTypeEnum.IDENTITY_PROJECT.getValue(), openstackVmParamVo.getProjectId(), openstackId);
				String targetServerId = AdminKeyMapUtil.getAdminKeyMapService().getTargetIdByMyIdForOpenstack(KeyTypeEnum.COMPUTE_VM.getValue(), deviceId, openstackId);
				String openstackIp = openstackVmParamVo.getOpenstackIp();
				String domainName = openstackVmParamVo.getDomainName();
				String projectName =  openstackVmParamVo.getProjectName();
				String hostType = RmHostType.VIRTUAL.getValue();
				
				//第二步，分配IP
				logger.debug("分配IP开始...");
				String targetNetworkId = AdminKeyMapUtil.getAdminKeyMapService().getTargetIdByMyIdForOpenstack(KeyTypeEnum.NETWORK_PRIVATE.getValue(), netPoolId, openstackId);
				String targetSubnetId = AdminKeyMapUtil.getAdminKeyMapService().getTargetIdByMyIdForOpenstack(KeyTypeEnum.NETWORK_PRIVATE_SUBNET.getValue(), netPoolId, openstackId);
				List<RmNwIpAddressPo> ipList = rmNwIpAddrDAO.findAnIpAddress(netPoolId);
				if(ipList ==null || ipList.size() == 0) {
					logger.error("[addNetworkCard]未查询到可以使用的IP地址");
					throw new Exception("未查询到可以使用的IP地址.");
				}
				String newIpAddress = ipList.get(0).getIp();//新增网卡ip
				RmNwIpAddressPo ip = ipList.get(0);
				ip.setAllocedStatusCode("A2DV");
				rmNwIpAddrDAO.updateIpAddrPo(ip);
				logger.info("设备id："+deviceId+",从资源池中分配的ip为："+newIpAddress);
				logger.debug("分配IP结束...");
				
				//第三步，获取token
				if(openstackIp == null || "".equals(openstackIp)) {
					throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
				}
				if(projectName == null || "".equals(projectName)) {
					throw new Exception("Project名称为空，请检查参数[PROJECT_NAME].");
				}
				logger.info("获取token开始...");
				String token = OpenstackPowerVcServiceFactory.getTokenServiceInstance(openstackIp,domainName).getToken(projectName);
				if(token == null || "".equals(token)) {
					logger.error("获取token失败，openstackIp:" + openstackIp + ";projectName:" + projectName);
					throw new Exception("获取token失败，openstackIp:" + openstackIp + ";projectName:" + projectName);
				}
				logger.info("获取token结束...");
				
				//第四步，查询设备状态（CheckServerActiveStatusHandler）
				Boolean isVm = null;
				if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
					isVm = true;
				} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
					isVm = false;
				} else {
					logger.error("[addNetworkCard]未查询到可以使用的IP地址,主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
					throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
				}
				String state = "";
				try {
					state = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getServerState(targetProjectId, targetServerId, isVm);
				} catch(Exception e) {
					logger.error("[addNetworkCard]调底层接口查询设备状态失败deviceId=" + deviceId);
					throw new Exception("查询设备状态失败");
				}
				logger.info("设备:"+deviceId+",状态为："+state);
				if(state.equals("ACTIVE")) {
					查询网卡状态,同步网卡信息
					 *本次添加的网卡和机器中已有网卡相同，直接同步数据至数据库
					 *本次添加的网卡和机器中已有网卡不同，直接添加网卡，进行后续数据处理
					List<RmNwIpAddressPo> deviceIps = rmNwIpAddrDAO.findIpAddrs(deviceId);
					List<String> deviceIpList = new ArrayList<String>();
					for(RmNwIpAddressPo i : deviceIps) {
						deviceIpList.add(i.getIp());
					}
					List<String> interfaceReturnIpLis = new ArrayList<String>();
					logger.debug("查询网卡开始...");
					String networkCard = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getNetworkCard(targetProjectId, targetServerId);
					logger.debug("查询网卡完成，开始进行数据处理...");
					JSONObject json = JSONObject.parseObject(networkCard);
					JSONArray jsa = json.getJSONArray("interfaceAttachments");
					Iterator<Object> iterator = jsa.iterator();
					Boolean newIpAddressOperateFlag = false;
					while (iterator.hasNext()) {
						Object next = iterator.next();
						JSONObject nextObject = JSONObject.parseObject(next.toString());
						JSONArray fixed_ips = nextObject.getJSONArray("fixed_ips");
						Iterator iterator2 = fixed_ips.iterator();
						while (iterator2.hasNext()) {
							Object next2 = iterator2.next();
							JSONObject fromObject = JSONObject.parseObject(next2.toString());
							String ipAddress = fromObject.get("ip_address").toString();
							interfaceReturnIpLis.add(ipAddress);
							if(newIpAddress.equals(ipAddress)) {
								newIpAddressOperateFlag = true;//资源池中分配的网卡已经被使用
							}
						}
					}
					logger.info("设备Id:"+deviceId+",接口返回网卡信息:"+interfaceReturnIpLis);
					for(String i: interfaceReturnIpLis) {
						//接口返回的ip，云平台中未被占用的，需要在云平台中占用该ip
						if(!deviceIpList.contains(i)){
							//ip数据处理（IpDataHandler）
							logger.debug("同步接口返回的ip数据，处理开始...");
							// 请参见openstack修改ip的方法
							logger.debug("同步接口返回的ip数据，处理结束...");
						}
					}
					//分配的网卡未被使用
					if(!newIpAddressOperateFlag) {
						//第五步，创建PORT并制定IP（CreatePortHandler）
						logger.debug("创建端口并指定IP开始...");
						String jsonData = OpenstackPowerVcServiceFactory.getNetworkServiceInstance(openstackIp,domainName, token).createPort(targetNetworkId, targetSubnetId, newIpAddress, null);
						String newPortId = JSONObject.parseObject(jsonData).getJSONObject("port").get("id").toString();
						logger.debug("创建端口并指定IP结束...");
						
						//第六步，添加网卡（AddNetworkCardTHandler），并查询网卡是否添加成功（CheckNetworkCardStatusHandler）
						logger.debug("添加网卡开始...");
						try {
							OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).addNetworkCard(targetProjectId, targetServerId, newPortId);
						} catch(Exception e1) {
							// 删除创建的端口
							try {
								OpenstackPowerVcServiceFactory.getNetworkServiceInstance(openstackIp, domainName, token).deletePort(newPortId);
							} catch(Exception e2) {
								logger.error("添加网卡失败，删除端口失败");
								throw new Exception ("添加网卡失败，删除端口失败");
							}
							throw new Exception ("创建端口成功，添加网卡失败");
						}
						try {
							// 休眠10秒
							Thread.sleep(10000);
							String networkCardStatus = OpenstackPowerVcServiceFactory.getComputeServiceInstance(targetProjectId,domainName, token).getNetworkCardStatus(targetProjectId, targetServerId, newPortId);
							if(!networkCardStatus.equals("ACTIVE")&&!networkCardStatus.equals("DOWN")){
								logger.error("查询网卡状态失败,状态："+networkCardStatus);
								throw new Exception("网卡状态错误");
							}
						} catch(Exception e) {
							logger.error("查询网卡状态失败");
							throw new Exception(e);
						}
						logger.debug("添加网卡结束...");
						
						//第七步，ip数据处理（IpDataHandler）
						logger.debug("ip数据处理开始...");
						// 请参见openstack修改ip的方法
						logger.debug("ip数据结束...");
					}
					result.setCode("0000");
				}else {
					logger.error("设备状态为："+state+",请检查设备状态");
					result.setCode("9999");	
					throw new Exception("设备状态为："+state+",请检查设备状态");
				}
			*/}
		} catch (Exception e) {
			logger.error("添加网卡失败，错误信息：{}",e);
			throw new RollbackableBizException(e);
		}
		return result;
	
	}
	/**
	 * 删除网卡
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public  ReturnMeta deleteNetworkCard(ServiceRequestParam serviceRequestParam) throws RollbackableBizException{
		ReturnMeta result = new ReturnMeta();
		try {
			String platformCode = serviceRequestParam.getPlatformCode();
			if("O".equals(platformCode)){
				//第一步，初始化参数(OpenstackMVInitParam)，验证传递的设备id和需要删除网卡的ip
				String deviceId = serviceRequestParam.getDeviceId();
				if(CommonUtil.isEmpty(deviceId)) {
					logger.error("设备ID为空，请检查参数:{}"+serviceRequestParam);
					throw new RollbackableBizException("设备ID为空，请检查参数:{}"+serviceRequestParam) ;
				}
				String oldIp_address =  serviceRequestParam.getDeleteIpAddress();//需要删除的网卡ip
				if(CommonUtil.isEmpty(oldIp_address)) {
					logger.error("设备deleteIpAddress为空，请检查参数:{}"+serviceRequestParam);
					throw new RollbackableBizException("设备deleteIpAddress为空，请检查参数:{}"+serviceRequestParam) ;
				}
				/*RmGeneralServerVo server = getAutomationService().findDeviceServerInfo(deviceId);
				if(server == null) {
					logger.error("[deleteNetworkCard]获取服务器信息失败,deviceID:" + deviceId);
					throw new Exception("[deleteNetworkCard]获取服务器信息失败,deviceID:" + deviceId);
				}*/
				OpenstackVmParamVo openstackVmParamVo = getAutomationService().findOpenstackVmParamByVmId(deviceId);
				if(openstackVmParamVo == null) {
					logger.error("[deleteNetworkCard]获取设备相关信息失败,deviceID:" + deviceId);
					throw new Exception("[deleteNetworkCard]获取设备相关信息失败,deviceID:" + deviceId);
				}
				String targetProjectId = projectVpcDao().findProjectByProjectId(openstackVmParamVo.getProjectId()).getIaasUuid();
				String targetServerId = cmVmDAO().findCmVmById(deviceId).getIaasUuid();
				String openstackIp = openstackVmParamVo.getOpenstackIp();
				String domainName = openstackVmParamVo.getDomainName();
				String projectName =  openstackVmParamVo.getProjectName();
				String hostType = RmHostType.VIRTUAL.getValue();
				
				//第二步，获取token(GetTokenHandler)
				String version = openstackVmParamVo.getVersion();
				OpenstackIdentityModel model = new OpenstackIdentityModel();
	            model.setVersion(version);
	            model.setOpenstackIp(openstackIp);
	            model.setDomainName(domainName);
	            model.setManageOneIp(openstackVmParamVo.getManageOneIp());
	            model.setProjectName(projectName);
				model.setProjectId(targetProjectId);
				if(CommonUtil.isEmpty(openstackIp)) {
					logger.error("openstack服务器地址为空");
					throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
				}
				if(CommonUtil.isEmpty(projectName)) {
					logger.error("Project名称为空");
					throw new Exception("Project名称为空，请检查参数[PROJECT_NAME].");
				}
				
				
				//第三步，查询设备状态
				Boolean isVm = null;
				if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
					isVm = true;
				} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
					isVm = false;
				} else {
					logger.error("主机类型HOST_TYPE不识别");
					throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
				}
				String state = "";
				try {
					state = IaasInstanceFactory.computeInstance(version).getServerState(model, targetServerId, isVm);
				} catch(Exception e) {
					logger.error("查询设备状态失败,deviceId:"+deviceId);
					throw new Exception("查询设备状态失败");
				}
				logger.info("当前设备状态为："+state);
				if(state.equals("ACTIVE")) {
					/*查询网卡状态,同步网卡信息
					 *本次添加的网卡和机器中已有网卡相同，直接同步数据至数据库
					 *本次添加的网卡和机器中已有网卡不同，直接添加网卡，进行后续数据处理*/
					List<OpenstackIpAddressPo> dIpList = getVirtualNetworkService().selectIpAddressByDeviceId(deviceId);
					/*List<String> deviceIpList = new ArrayList<String>();
					for(OpenstackIpAddressPo i : dIpList) {
						deviceIpList.add(i.getIp());
					}*/
					/*List<RmNwIpAddressPo> deviceIps = rmNwIpAddrDAO.findIpAddrs(deviceId);
					List<String> deviceIpList = new ArrayList<String>();
					for(RmNwIpAddressPo i : deviceIps) {
						deviceIpList.add(i.getIp());
					}*/
					List<String> interfaceReturnIpLis = new ArrayList<String>();
					//第四步，查询网卡状态（GetNetworkCardHandler）
					logger.debug("查询网卡开始...");
					String networkCard = IaasInstanceFactory.computeInstance(version).getNetworkCard(model, targetServerId);
					logger.debug("查询网卡完成，开始进行数据处理...");
					JSONObject json = JSONObject.parseObject(networkCard);
					JSONArray jsa = json.getJSONArray("interfaceAttachments");
					Iterator<Object> iterator = jsa.iterator();
					String portId = "";
					Boolean operateFlag = false;
					while (iterator.hasNext()) {
						Object next = iterator.next();
						JSONObject nextObject = JSONObject.parseObject(next.toString());
						JSONArray fixed_ips = nextObject.getJSONArray("fixed_ips");
						Iterator iterator2 = fixed_ips.iterator();
						while (iterator2.hasNext()) {
							Object next2 = iterator2.next();
							JSONObject fromObject = JSONObject.parseObject(next2.toString());
							String ipAddress = fromObject.get("ip_address").toString();
							interfaceReturnIpLis.add(ipAddress);
							if(oldIp_address.equals(ipAddress)){
								operateFlag = true;
								portId = nextObject.get("port_id").toString();
								break;
							}
						}
					}
					if (!"".equals(portId)) {
						logger.info("port_id:"+portId);
					}else{
						//portId为空，证明底层已经将网卡删除，现在需要将ip在云平台中解绑
						logger.info("设备Id:"+deviceId+",接口返回网卡信息:"+interfaceReturnIpLis);
						for(OpenstackIpAddressPo i: dIpList) {
							if(!interfaceReturnIpLis.contains(i.getIp())){
								//ip数据处理（IpDataHandler）
								logger.debug("删除网卡同步数据，ip数据处理开始...");
								OpenstackIpAddressPo updateIpAddress = new OpenstackIpAddressPo();
								updateIpAddress.setId(i.getId());
								updateIpAddress.setInstanceId("");
								updateIpAddress.setUseRelCode(AllocedStatus.NOT_ALLOC.getValue());
								// 保存IP占用信息
								getVirtualNetworkService().updateIpAddressByNetwork(updateIpAddress);
								logger.debug("删除网卡同步数据，ip数据处理结束...");
							}
						}
						logger.info("未找到匹配的资源portId,进行数据同步处理");
					}
					logger.debug("查询网卡结束...");
					if(operateFlag) {
						//第五步，删除网卡，删除port(DeleteNetworkCardHandler)
						IaasInstanceFactory.computeInstance(version).deleteNetworkCard(model, targetServerId, portId);
						String oldPortId = portId;
						// 查询网卡，如果网卡不存在，则继续
						for(int i = 0; i < 100; i++) {
							logger.debug("查询网卡开始...");
							networkCard = IaasInstanceFactory.computeInstance(version).getNetworkCard(model, targetServerId);
							logger.debug("查询网卡完成，开始进行数据处理...");
							json = JSONObject.parseObject(networkCard);
							jsa = json.getJSONArray("interfaceAttachments");
							iterator = jsa.iterator();
							String tmpPortId = "";
							while (iterator.hasNext()) {
								Object next = iterator.next();
								JSONObject nextObject = JSONObject.parseObject(next.toString());
								JSONArray fixed_ips = nextObject.getJSONArray("fixed_ips");
								Iterator iterator2 = fixed_ips.iterator();
								while (iterator2.hasNext()) {
									Object next2 = iterator2.next();
									JSONObject fromObject = JSONObject.parseObject(next2.toString());
									String ipAddress = fromObject.get("ip_address").toString();
									interfaceReturnIpLis.add(ipAddress);
									if(oldIp_address.equals(ipAddress)){
										tmpPortId = nextObject.get("port_id").toString();
										break;
									}
								}
							}
							if ("".equals(tmpPortId)) {
								break;
							}
							else {
								Thread.sleep(500);
							}
						}
						
						if (!"".equals(oldPortId)) {
							try{
								IaasInstanceFactory.networkInstance(version).deletePort(model, oldPortId);
							}catch(Exception e){
								logger.error("网卡删除结束, port删除失败. portId=["+portId+"]", e);
								throw new RollbackableBizException("网卡删除结束, port删除失败. portId=["+portId+"]", e) ;
							}
						}
						//第六步，数据处理(DelNetworkCardIpDataHandler)
						logger.debug("ip数据处理开始...");
						
						for(OpenstackIpAddressPo i: dIpList) {
							if(oldIp_address.equals(i.getIp())){
								logger.debug("删除网卡同步数据，ip数据处理开始...");
								OpenstackIpAddressPo updateIpAddress = new OpenstackIpAddressPo();
								updateIpAddress.setId(i.getId());
								updateIpAddress.setInstanceId("");
								updateIpAddress.setUseRelCode(AllocedStatus.NOT_ALLOC.getValue());
								// 保存IP占用信息
								getVirtualNetworkService().updateIpAddressByNetwork(updateIpAddress);
								logger.debug("删除网卡同步数据，ip数据处理结束...");
								break;
							}
						}
						logger.debug("ip数据处理结束...");
					}
					result.setCode("0000");
				}else {
					logger.error("删除网卡失败,请检查设备状态："+state);
					result.setCode("9999");	
					throw new Exception("设备状态为："+state+",请检查设备状态");
				}
			}else if("PV".equals(platformCode)){/*
				//第一步，初始化参数(OpenstackMVInitParam)，验证传递的设备id和需要删除网卡的ip
				String deviceId = serviceRequestParam.getDeviceId();
				if(CommonUtil.isEmpty(deviceId)) {
					logger.error("设备ID为空，请检查参数:{}"+serviceRequestParam);
					throw new RollbackableBizException("设备ID为空，请检查参数:{}"+serviceRequestParam) ;
				}
				String oldIp_address =  serviceRequestParam.getDeleteIpAddress();//需要删除的网卡ip
				if(CommonUtil.isEmpty(oldIp_address)) {
					logger.error("设备deleteIpAddress为空，请检查参数:{}"+serviceRequestParam);
					throw new RollbackableBizException("设备deleteIpAddress为空，请检查参数:{}"+serviceRequestParam) ;
				}
				RmGeneralServerVo server = getAutomationService().findDeviceServerInfo(deviceId);
				if(server == null) {
					logger.error("[deleteNetworkCard]获取服务器信息失败,deviceID:" + deviceId);
					throw new Exception("[deleteNetworkCard]获取服务器信息失败,deviceID:" + deviceId);
				}
				OpenstackVmParamVo openstackVmParamVo = getAutomationService().findOpenstackVmParamByVmId(deviceId);
				if(openstackVmParamVo == null) {
					logger.error("[deleteNetworkCard]获取设备相关信息失败,deviceID:" + deviceId);
					throw new Exception("[deleteNetworkCard]获取设备相关信息失败,deviceID:" + deviceId);
				}
				String openstackId = openstackVmParamVo.getOpenstackId();
				String targetProjectId = AdminKeyMapUtil.getAdminKeyMapService().getTargetIdByMyIdForOpenstack(KeyTypeEnum.IDENTITY_PROJECT.getValue(), openstackVmParamVo.getProjectId(), openstackId);
				String targetServerId = AdminKeyMapUtil.getAdminKeyMapService().getTargetIdByMyIdForOpenstack(KeyTypeEnum.COMPUTE_VM.getValue(), deviceId, openstackId);
				String openstackIp = openstackVmParamVo.getOpenstackIp();
				String domainName = openstackVmParamVo.getDomainName();
				String projectName =  openstackVmParamVo.getProjectName();
				String hostType = RmHostType.VIRTUAL.getValue();
				
				//第二步，获取token(GetTokenHandler)
				if(CommonUtil.isEmpty(openstackIp)) {
					logger.error("openstack服务器地址为空");
					throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
				}
				if(CommonUtil.isEmpty(projectName)) {
					logger.error("Project名称为空");
					throw new Exception("Project名称为空，请检查参数[PROJECT_NAME].");
				}
				logger.info("获取token开始...");
				String token = OpenstackPowerVcServiceFactory.getTokenServiceInstance(openstackIp,domainName).getToken(projectName);
				if(CommonUtil.isEmpty(token)) {
					logger.error("获取token失败，openstackIp:" + openstackIp + ";projectName:" + projectName);
					throw new Exception("获取token失败，openstackIp:" + openstackIp + ";projectName:" + projectName);
				}
				logger.info("获取token结束...");
				
				//第三步，查询设备状态
				Boolean isVm = null;
				if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
					isVm = true;
				} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
					isVm = false;
				} else {
					logger.error("主机类型HOST_TYPE不识别");
					throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
				}
				String state = "";
				try {
					state = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getServerState(targetProjectId, targetServerId, isVm);
				} catch(Exception e) {
					logger.error("查询设备状态失败,deviceId:"+deviceId);
					throw new Exception("查询设备状态失败");
				}
				logger.info("当前设备状态为："+state);
				if(state.equals("ACTIVE")) {
					查询网卡状态,同步网卡信息
					 *本次添加的网卡和机器中已有网卡相同，直接同步数据至数据库
					 *本次添加的网卡和机器中已有网卡不同，直接添加网卡，进行后续数据处理
					List<RmNwIpAddressPo> deviceIps = rmNwIpAddrDAO.findIpAddrs(deviceId);
					List<String> deviceIpList = new ArrayList<String>();
					for(RmNwIpAddressPo i : deviceIps) {
						deviceIpList.add(i.getIp());
					}
					List<String> interfaceReturnIpLis = new ArrayList<String>();
					//第四步，查询网卡状态（GetNetworkCardHandler）
					logger.debug("查询网卡开始...");
					String networkCard = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getNetworkCard(targetProjectId, targetServerId);
					logger.debug("查询网卡完成，开始进行数据处理...");
					JSONObject json = JSONObject.parseObject(networkCard);
					JSONArray jsa = json.getJSONArray("interfaceAttachments");
					Iterator<Object> iterator = jsa.iterator();
					String portId = "";
					Boolean operateFlag = false;
					while (iterator.hasNext()) {
						Object next = iterator.next();
						JSONObject nextObject = JSONObject.parseObject(next.toString());
						JSONArray fixed_ips = nextObject.getJSONArray("fixed_ips");
						Iterator iterator2 = fixed_ips.iterator();
						while (iterator2.hasNext()) {
							Object next2 = iterator2.next();
							JSONObject fromObject = JSONObject.parseObject(next2.toString());
							String ipAddress = fromObject.get("ip_address").toString();
							interfaceReturnIpLis.add(ipAddress);
							if(oldIp_address.equals(ipAddress)){
								operateFlag = true;
								portId = nextObject.get("port_id").toString();
								break;
							}
						}
					}
					if (!"".equals(portId)) {
						logger.info("port_id:"+portId);
					}else{
						//portId为空，证明底层已经将网卡删除，现在需要将ip在云平台中解绑
						logger.info("设备Id:"+deviceId+",接口返回网卡信息:"+interfaceReturnIpLis);
						for(String i: deviceIpList) {
							if(!interfaceReturnIpLis.contains(i)){
								//ip数据处理（IpDataHandler）
								logger.debug("删除网卡同步数据，ip数据处理开始...");
								// 请参见openstack修改ip的方法
//								RmNwIpAddressPo oldIp = rmNwIpAddrDAO.findByIpAddrs(i).get(0);
//								RmNwResPoolPo resPool = rmNwResPoolDAO.findResPool(oldIp.getNwResPoolId());//查询网络资源池
//								resPool.setIpAvailCnt(resPool.getIpAvailCnt()+1);//可用IP数量加一
//								rmNwResPoolDAO.updateAvailCnt(resPool);
//								oldIp.setAllocedStatusCode("NA");
//								oldIp.setDeviceId("");
//								rmNwIpAddrDAO.updateIpAddrPo(oldIp);
								logger.debug("删除网卡同步数据，ip数据处理结束...");
							}
						}
						logger.info("未找到匹配的资源portId,进行数据同步处理");
					}
					logger.debug("查询网卡结束...");
					if(operateFlag) {
						//第五步，删除网卡，删除port(DeleteNetworkCardHandler)
						OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).deleteNetworkCard(targetProjectId, targetServerId, portId);
						//第六步，数据处理(DelNetworkCardIpDataHandler)
						logger.debug("ip数据处理开始...");
						// powervc ip处理 请参见opensatck的ip处理方式
//						RmNwIpAddressPo oldIp = rmNwIpAddrDAO.findByIpAddrs(oldIp_address).get(0);
//						RmNwResPoolPo resPool = rmNwResPoolDAO.findResPool(oldIp.getNwResPoolId());//查询网络资源池
//						resPool.setIpAvailCnt(resPool.getIpAvailCnt()+1);//可用IP数量加一
//						rmNwResPoolDAO.updateAvailCnt(resPool);
//						oldIp.setAllocedStatusCode("NA");
//						oldIp.setDeviceId("");
//						rmNwIpAddrDAO.updateIpAddrPo(oldIp);
						logger.debug("ip数据处理结束...");
					}
					result.setCode("0000");
				}else {
					logger.error("删除网卡失败,请检查设备状态："+state);
					result.setCode("9999");	
					throw new Exception("设备状态为："+state+",请检查设备状态");
				}
			*/}
		} catch (Exception e) {
			logger.error("删除网卡失败",e);
			throw new RollbackableBizException(e);
		}
		return result;
	}
	
	/**
	 * 服务自动化-添加卷
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public BmSrVo saveServiceAuto(JSONObject serviceRequestParam) throws RollbackableBizException {
		try {
			logger.info("saveServiceAuto serviceRequestParam:{}",serviceRequestParam);
			logger.info("saveServiceAuto operModelType :"+serviceRequestParam.getString("operModelType"));
			String serviceId = serviceRequestParam.getString("serviceId");
			String dataCenterId = serviceRequestParam.getString("datacenterId");
			int orderId = 0;
			int cpu = 0;
			int mem = 0;
			int disk = 0;
			int serviceNum = 0;
			
			// 创建服务申请BM_SR
			bmSr = new BmSrVo();
			String srId = UUIDGenerator.getUUID();
			bmSr.setSrCode(SrCodeGenerator.getInstance().getSRID("SA"));
			bmSr.setCreateTime(SrDateUtil.getSrFortime(new Date()));
			bmSr.setSrId(srId);
			bmSr.setAppId("");
			bmSr.setSrTypeMark(serviceRequestParam.getString("operModelType"));
			bmSr.setDatacenterId(dataCenterId);
			bmSr.setApproveMark(srId);
			bmSr.setSrStatusCode("");
			bmSr.setCreatorId(serviceRequestParam.getString("creatorId"));
			bmSr.setTenantId(serviceRequestParam.getString("tenantId"));
			bmSrDao().insertBmSr(bmSr); // 新增服务请求对象
			
			
			// 获取云服务对应的流程实例
			Map<String,Object> flowparam = new HashMap<String,Object>();
			flowparam.put("serviceId", serviceId);
			flowparam.put("srTypeMark","SA");
			flowparam.put("isActive", IsActiveEnum.YES.getValue());
			List<CloudServiceFlowRefPo> csflow = findListParam().findListByParam("getCloudServiceFlowId", flowparam);
			String flowId = "";
			if(csflow == null || csflow.size() == 0) {
				logger.error("未到流程模板信息，云服务ID为：{}",serviceId);
				throw new RollbackableBizException("不能找到流程模板信息");
			} else {
				flowId = csflow.get(0).getFlowId();
			}
			
			// 创建资源申请BM_SR_RRINFO
			String rrinfoId = UUIDGenerator.getUUID();
			rrinfoVo = new BmSrRrinfoVo();
			rrinfoVo.setRrinfoId(rrinfoId);
			rrinfoVo.setSrId(bmSr.getSrId());
			rrinfoVo.setServiceId(serviceId);
			rrinfoVo.setFlowId(flowId);
			rrinfoVo.setDataCenterId(dataCenterId);
			rrinfoVo.setCpu(cpu);
			rrinfoVo.setMem(mem);
			rrinfoVo.setSysDisk(disk);
			rrinfoVo.setDuId("");
			rrinfoVo.setVmNum(serviceNum);
			rrinfoVo.setCreateTime(SrDateUtil.getSrFortime(new Date()));// 创建时间
			rrinfoVo.setOrderId(orderId);
			rrinfoVo.setTenantId(serviceRequestParam.getString("tenantId"));
			rrinfoVo.setRmHostResPoolId("");
			bmSrRrinfoDao().insertBmSrRrinfo(rrinfoVo);
			JSONArray attrValList = serviceRequestParam.getJSONArray("attrValList");
			
			//向参数表中，添加参数对象
			if(attrValList != null){
				List<BmSrAttrValVo> attrList = Lists.newArrayList();
				for (Object valVoObject:attrValList) {
					BmSrAttrValVo valVo = ((JSONObject)valVoObject).toJavaObject(BmSrAttrValVo.class);
					valVo.setSrAttrValId(UUIDGenerator.getUUID());
					valVo.setRrinfoId(rrinfoVo.getRrinfoId());
					attrList.add(valVo);
				}
				bmSrAttrValDao().insertBmSrAttrList(attrList);
			}
		}catch (Exception e) {
			logger.error("请检查传递的供给服务参数是否有误,错误信息：{}",e);
			throw new RollbackableBizException(e);
		}
		return bmSr;
	}
	//定价审批，构造申请数据
	public BmSrVo saveExamineAndApprove(JSONObject serviceRequestParam) throws RollbackableBizException {
		try {
			logger.info("saveExamineAndApprove serviceRequestParam:{}",serviceRequestParam);
			String operModelType = SrTypeMarkEnum.PRICE_EXAMINE_APPROVE.getValue();
			logger.info("saveExamineAndApprove operModelType :"+operModelType);
			String serviceId = "";
			String dataCenterId = "";
			int orderId = 0;
			String creatorId = "1";
			String tenantId = "1";
			
			// 创建服务申请BM_SR
			bmSr = new BmSrVo();
			String srId = UUIDGenerator.getUUID();
			bmSr.setSrCode(SrCodeGenerator.getInstance().getSRID("SA"));
			bmSr.setCreateTime(SrDateUtil.getSrFortime(new Date()));
			bmSr.setSrId(srId);
			bmSr.setAppId("");
			bmSr.setSrTypeMark(operModelType);
			bmSr.setDatacenterId(dataCenterId);
			bmSr.setApproveMark(srId);
			bmSr.setSrStatusCode("");
			bmSr.setCreatorId(creatorId);
			bmSr.setTenantId(tenantId);
			bmSrDao().insertBmSr(bmSr); // 新增服务请求对象
			
			// 获取云服务对应的流程实例
			Map<String,Object> flowparam = new HashMap<String,Object>();
			flowparam.put("serviceId", serviceId);
			flowparam.put("srTypeMark",operModelType);
			flowparam.put("isActive", IsActiveEnum.YES.getValue());
			String flowId = "";
			
			// 创建资源申请BM_SR_RRINFO
			String rrinfoId = UUIDGenerator.getUUID();
			rrinfoVo = new BmSrRrinfoVo();
			rrinfoVo.setParametersJson(serviceRequestParam.toJSONString());
			rrinfoVo.setRrinfoId(rrinfoId);
			rrinfoVo.setSrId(bmSr.getSrId());
			rrinfoVo.setFlowId(flowId);
			rrinfoVo.setCreateTime(SrDateUtil.getSrFortime(new Date()));// 创建时间
			rrinfoVo.setOrderId(orderId);
			rrinfoVo.setTenantId(tenantId);
			bmSrRrinfoDao().insertBmSrRrinfo(rrinfoVo);
			
		} catch (RollbackableBizException e) {
			logger.error("错误信息：{}",e);
			throw new RollbackableBizException(e);
		} catch (Exception e) {
			logger.error("错误信息：{}",e);
			throw new RollbackableBizException(e);
		}
		return bmSr;
	}
	// 资源分配接口
	public String startResourceAssign(String srId) throws BizException, Exception {
		String eStr = virtualSupplyService().doUpdateResourceAssignNew(srId);
		if ("".equals(eStr)) {
			requestBaseService().updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_ASSIGN_SUCCESS.getValue());
		} else {
			requestBaseService().updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_ASSIGN_FAILURE.getValue());
			if (eStr == null) {
				requestBaseService().updateAssignResult(srId, "未知");
			} else {
				requestBaseService().updateAssignResult(srId, eStr);
			}
		}
		return eStr;
	}
		
	
	public IVirtualSupplyService virtualSupplyService() throws Exception {
		return (IVirtualSupplyService) WebApplicationManager.getBean("virtualSupplyServiceImpl");
	}
	public AutomationService getAutomationService() throws Exception {
		return (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
	}
	public IRequestBaseService requestBaseService() throws Exception {
		return (IRequestBaseService) WebApplicationManager.getBean("requestBaseServiceImpl");
	}
	public IBmSrRrVmRefDao bmSrRrVmRef() throws Exception {
		return (IBmSrRrVmRefDao) WebApplicationManager.getBean("bmSrRrVmRefDaoImpl");
	}
	public IBmSrDao bmSrDao() throws Exception {
		return (IBmSrDao) WebApplicationManager.getBean("bmSrDaoImpl");
	}
	public IRequestWorkflowService requestWorkflowService() throws Exception {
		return (IRequestWorkflowService) WebApplicationManager.getBean("requestWorkflowServiceImpl");
	}
	public IDeployunitDao deployUtImpl() throws Exception {
		return (IDeployunitDao) WebApplicationManager.getBean("deployunitDaoImpl");
	}
	public ICmVmDAO cmVmDao() throws Exception {
		return (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
	}
	public IVirtualExtendDAO findListParam() throws Exception {
		return (IVirtualExtendDAO) WebApplicationManager.getBean("virtualExtendDAO");
	}
	public IBmSrRrVmRefDao bmSrRrVmRefDao() throws Exception {
		return (IBmSrRrVmRefDao) WebApplicationManager.getBean("bmSrRrVmRefDaoImpl");
	}
	public IDeployunitService deployunitService() throws Exception {
		return (IDeployunitService) WebApplicationManager.getBean("deployunitServiceImpl");
	}
	public IBmSrRrinfoDao bmSrRrinfoDao() throws Exception {
		return (IBmSrRrinfoDao) WebApplicationManager.getBean("bmSrRrinfoDaoImpl");
	}
	public IBmSrAttrValDao bmSrAttrValDao() throws Exception {
		return (IBmSrAttrValDao) WebApplicationManager.getBean("bmSrAttrValDaoImpl");
	} 
	private ICmDeviceDAO cmDeviceDao() throws Exception {
		return (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
	}
	public IVirtualNetworkService getVirtualNetworkService() throws Exception {
		return (IVirtualNetworkService) WebApplicationManager.getBean("virtualNetworkService");
	} 
	public IVirtualSubnetDao getVirtualSubnetDaoImpl() throws Exception {
		return (IVirtualSubnetDao) WebApplicationManager.getBean("virtualSubnetDao");
	}  
	public IProjectVpcDao projectVpcDao() throws Exception {
		return (IProjectVpcDao) WebApplicationManager.getBean("projectVpcDao");
	} 
	public ICmVmDAO cmVmDAO() throws Exception {
		return (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
	}
	public IVirtualNetworkDao virtualNetworkDaoImpl() throws Exception {
		return (IVirtualNetworkDao) WebApplicationManager.getBean("virtualNetworkDao");
	} 
	public IVirtualSubnetDao virtualSubnetDaoImpl() throws Exception {
		return (IVirtualSubnetDao) WebApplicationManager.getBean("virtualSubnetDao");
	} 
}
