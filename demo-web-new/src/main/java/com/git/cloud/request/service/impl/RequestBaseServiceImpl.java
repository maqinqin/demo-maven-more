package com.git.cloud.request.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.po.DeployUnitPo;
import com.git.cloud.appmgt.model.vo.AppStatVo;
import com.git.cloud.appmgt.model.vo.AppSysKpiVo;
import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.appmgt.service.IAppMagService;
import com.git.cloud.appmgt.service.IDeployunitService;
import com.git.cloud.bill.service.BillService;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.service.ICloudServiceService;
import com.git.cloud.common.enums.OperationType;
import com.git.cloud.common.enums.ResourceType;
import com.git.cloud.common.enums.RmMountStatusEnum;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.common.enums.RmVolumeTypeEnum;
import com.git.cloud.common.enums.Source;
import com.git.cloud.common.enums.Type;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.fw.model.FwRequestStatusEnum;
import com.git.cloud.handler.automation.se.po.CmLun;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.network.service.FirewallRequestService;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.request.dao.IBmApproveDao;
import com.git.cloud.request.dao.IBmSrAttrValDao;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.dao.IBmSrStatusMapDao;
import com.git.cloud.request.dao.IBmToDoDao;
import com.git.cloud.request.dao.impl.BmSrDaoImpl;
import com.git.cloud.request.model.DriveWfTypeEnum;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.model.SrTypeMarkEnum;
import com.git.cloud.request.model.TodoStatusCodeEnum;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.po.BmSrStatusMapPo;
import com.git.cloud.request.model.po.BmToDoPo;
import com.git.cloud.request.model.vo.BmApproveVo;
import com.git.cloud.request.model.vo.BmSrAttrValVo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.model.vo.BmToDoVo;
import com.git.cloud.request.model.vo.VirtualSupplyVo;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.cloud.request.service.IRequestWorkflowService;
import com.git.cloud.request.service.IVirtualSupplyService;
import com.git.cloud.request.tools.SrDateUtil;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDatastoreRefDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.DeviceStatusEnum;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.network.dao.IVirtualNetworkDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.sys.dao.IUserDao;
import com.git.cloud.sys.model.po.SysRolePo;
import com.git.cloud.sys.model.po.SysUserLimitPo;
import com.git.cloud.sys.model.vo.AppInfoVo;
import com.git.cloud.taglib.util.Internation;
import com.gitcloud.tankflow.dao.IBpmInstanceDao;

/**
 * 云服务申请公共接口类
 * @ClassName:RequestBaseServiceImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class RequestBaseServiceImpl implements IRequestBaseService {

	private static Logger logger = LoggerFactory.getLogger(RequestBaseServiceImpl.class);
	
	private IRequestWorkflowService requestWorkflowService;
	
	private IBmSrDao bmSrDao;
	private IBmApproveDao bmApproveDao;
	private IBmToDoDao bmToDoDao;
	private IBmSrAttrValDao bmSrAttrValDao;
	private IBmSrRrinfoDao bmSrRrinfoDao;
	private IBmSrRrVmRefDao bmSrRrVmRefDao;
	private IBmSrStatusMapDao bmSrStatusMapDao;
	// 外包服务
	private IIpAllocToDeviceNewService ipAllocToDeviceService;
	private IUserDao userDao;
	private IDeployunitDao deployunitDao;
	private ICmDeviceDAO cmDeviceDao;
	private ICmHostDAO cmHostDao;
	private ICmVmDAO cmVmDao;
	private IAppMagService appMagServiceImpl;
	private INotificationService notiServiceImpl;
	@Autowired
	private ICmDeviceService iCmDeviceService;
	private IBpmInstanceDao bpmInstanceDaoImpl;
	@Autowired
	public BillService billServiceImpl;
	@Autowired
	private ICloudServiceService cloudServiceService; 
	@Autowired
	private IDeployunitService deployunitServiceImpl;
	@Autowired
	private IVirtualNetworkDao virtualNetworkDao;
	@Autowired
	private ICmHostDatastoreRefDAO cmHostDatastoreRefDAO; 
	
	public INotificationService getNotiServiceImpl() {
		return notiServiceImpl;
	}
	@Autowired
	private IVirtualSupplyService virtualSupplyService;
	@Autowired
	private FirewallRequestService firewallRequestServiceImpl;

	public FirewallRequestService getFirewallRequestServiceImpl() {
		return firewallRequestServiceImpl;
	}
	public void setFirewallRequestServiceImpl(FirewallRequestService firewallRequestServiceImpl) {
		this.firewallRequestServiceImpl = firewallRequestServiceImpl;
	}
	public void setNotiServiceImpl(INotificationService notiServiceImpl) {
		this.notiServiceImpl = notiServiceImpl;
	}
	public void setAppMagServiceImpl(IAppMagService appMagServiceImpl) {
		this.appMagServiceImpl = appMagServiceImpl;
	}

	public Pagination<BmSrVo> getCloudReqeustPagination(PaginationParam pagination) {
		return bmSrDao.pageQuery("findCloudReqeustTotal", "findCloudReqeustPage", pagination);
	}
	
	public Pagination<BmToDoVo> getCloudRequestWaitDealPagination(PaginationParam pagination) {
		String roleIds = "";
		try {
			String userId = (String) pagination.getParams().get("userId");
			List<SysRolePo> roleList = userDao.findRoleByUserId(userId);
			int len = roleList == null ? 0 : roleList.size();
			for(int i=0 ; i<len ; i++) {
				roleIds += ",'" + roleList.get(i).getRoleId() + "'";
			}
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
			logger.error(e.getMessage());
		}
		pagination.getParams().put("roleIds", roleIds.length() > 0 ? roleIds.substring(1) : "'-1'");
		return bmSrDao.pageQuery("getCloudRequestWaitDealTotal", "getCloudRequestWaitDealPage", pagination);
	}
	
	public BmToDoVo getCloudRequestWaitDealBySrId(String srId) {
        return bmSrDao.getCloudRequestWaitDealBySrId(srId);
    }

	public void insertBmToDo(BmToDoPo bmTodo) throws RollbackableBizException {
		// 插入待办
		bmToDoDao.insertBmToDo(bmTodo);
		// 更新工单状态
		BmSrStatusMapPo statusMap = bmSrStatusMapDao.findBmSrStatusMap(bmTodo.getSrId(), bmTodo.getCurrentStep());
		if(statusMap != null) {
			bmSrDao.updateBmSrStatus(bmTodo.getSrId(), statusMap.getSrStatusCode());
		}
	}
	
	public void updateBmSrStatus(String srId, String srStatusCode) throws RollbackableBizException {
		bmSrDao.updateBmSrStatus(srId, srStatusCode);
	}
	
	public void updateAssignResult(String srId, String assignResult) throws RollbackableBizException {
		bmSrDao.updateAssignResult(srId, assignResult);
	}

	public BmSrVo findBmSrVoById(String srId) {
		try {
			return bmSrDao.findBmSrVoById(srId);
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
			logger.error(e.getMessage());
		}
		return null;
	}
	@Override
	public BmSrVo getSrByToDoId(String toDoId) {
		BmSrVo bmSrVo = new BmSrVo();
		try {
			List<BaseBO> list = bmSrDao.findByID("selectBmSrByToDoId", toDoId);
			if (list != null && list.size() > 0) {
				bmSrVo = (BmSrVo) list.get(0);
			}
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
			logger.error(e.getMessage());
		}
		return bmSrVo;
	}
	
	@Override
	public BmSrVo selectBmSrForDelToDoId(String toDoId) {
		BmSrVo bmSrVo = new BmSrVo();
		try {
			List<BaseBO> list = bmSrDao.findByID("selectBmSrForDelToDoId", toDoId);
			if (list != null && list.size() > 0) {
				bmSrVo = (BmSrVo) list.get(0);
			}
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
			logger.error(e.getMessage());
		}
		return bmSrVo;
	}
	
	public void todoStartDeal(String todoId) throws RollbackableBizException {
		bmToDoDao.updateBmToDoStatus(todoId, TodoStatusCodeEnum.TODO_STATUS_DEALING.getValue());
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pagination<BmSrRrinfoVo> queryBmSrRrinfoList(PaginationParam pagination) {
		Pagination<BmSrRrinfoVo> queryData= bmSrDao.pageQuery("selectBmSrRrinfoListTotal", "selectBmSrRrinfoListPage", pagination);
		for(Object o :queryData.getDataList()) {
			Map map = (Map)o;
			JSONObject jsonObj = JSON.parseObject((String)map.get("parametersJson"));
			if(!CommonUtil.isEmpty(map) && !CommonUtil.isEmpty(jsonObj)) {
				//获取云服务名称
				Object serviceId = jsonObj.get("serviceId");
				try {
					if(!CommonUtil.isEmpty(serviceId)) {
						CloudServicePo cloudService = cloudServiceService.findById(serviceId.toString());
						if(!CommonUtil.isEmpty(cloudService)) {
							map.put("serviceName",cloudService.getServiceName());
						}
					}
				} catch (RollbackableBizException e) {
					logger.error("cloudService is null",e);
				}
				//获取服务器角色英文和中文名称
				Object duId = jsonObj.get("duId");
				if(!CommonUtil.isEmpty(duId)) {
					DeployUnitVo deployUnitVo = deployunitServiceImpl.getDeployUnitById(duId.toString());
					if(!CommonUtil.isEmpty(deployUnitVo)) {}{
						map.put("duName", deployUnitVo.getCname());
						map.put("duEname", deployUnitVo.getEname());
						map.put("duId",duId);
					}
				}
			}
		}
		return queryData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pagination<BmSrRrinfoVo> queryBmSrRrinfoAffirmList(PaginationParam pagination) {
		Pagination _pagination = bmSrDao.pageQuery("selectBmSrRrinfoAffirmTotal", "selectBmSrRrinfoAffirmPage", pagination);
		for(Object o :_pagination.getDataList()){
			Map map = (Map)o;
			String vmpwd = (String)map.get("vmpwd");
			if(vmpwd != null && !"".equals(vmpwd)){
				map.put("vmpwd", PwdUtil.decryption(vmpwd));
			}
			JSONObject jsonObj = JSON.parseObject((String)map.get("parametersJson"));
			if(!CommonUtil.isEmpty(jsonObj)) {
				Object duId = jsonObj.get("duId");
				if(!CommonUtil.isEmpty(duId)) {
					DeployUnitVo deployUnitVo = deployunitServiceImpl.getDeployUnitById(duId.toString());
					if(!CommonUtil.isEmpty(deployUnitVo)) {}{
						map.put("duName", deployUnitVo.getCname());
						map.put("duEname", deployUnitVo.getEname());
					}
				}
			}
		}
		return _pagination;
	}

	public BmSrPo findBmSrNewestRecord() throws RollbackableBizException {
		List<BmSrPo> bmSrList = bmSrDao.findAll("findBmSrNewestRecord");
		BmSrPo bmSr = null;
		if (bmSrList != null && bmSrList.size() > 0) {
			bmSr = bmSrList.get(0);
		}
		return bmSr;
	}
	
	public String saveApproveLog(BmApproveVo bmApproveVo) throws RollbackableBizException {
		BmSrVo bmSr = bmSrDao.findBmSrVoById(bmApproveVo.getSrId());
		String logContent = "服务单号：" + bmSr.getSrCode()
						  + "；审批结果：" + bmApproveVo.getApproveResult()
						  + "；审批意见：" + bmApproveVo.getApproveRemark();
		return logContent;
	}
	
	public List<BmApproveVo> findApproveResult(String srId) throws RollbackableBizException {
		return bmApproveDao.findApproveResult(srId);
	}
	
	public void deleteApprove(String srId, String todoId) throws Exception {
		VirtualSupplyVo virtualSupplyVo = virtualSupplyService.findVirtualSupplyById(srId);
		List<BmSrRrinfoVo> rrinfoList = virtualSupplyVo.getRrinfoList();
		DeployUnitPo deployUnitPo = new DeployUnitPo();
		//将服务器角色资源释放
		for(BmSrRrinfoVo vo:rrinfoList){
			boolean flag = deployunitDao.includeVm(vo.getDuId());
			if(!flag){
				deployUnitPo.setServiceId(null);
				deployUnitPo.setDuId(vo.getDuId());
				deployunitDao.updateDeployUnitServiceId(deployUnitPo);
			}
		}
		requestWorkflowService.driveWorkflow(todoId, DriveWfTypeEnum.DRIVE_WF_DISAGREE.getValue());
		bmSrDao.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_DELETE.getValue());
	}
	public String createInstance(String srId, String flowIds) {
		String instanceId = null;
		try {
			if (srId == null || flowIds == null) {
				throw new RollbackableBizException("无启动流程！");
			}
			String[] flowArr = flowIds.split(",");
			int wfLen = flowArr.length;
			if (wfLen == 0) {
				throw new RollbackableBizException("无启动流程！");
			}
			String flowId, srrId;
			for (int i = 0; i < wfLen; i++) {
				String[] subFlow = flowArr[i].split(":");
				flowId = subFlow[0];
				srrId = subFlow[1];
				instanceId = requestWorkflowService.createSubWorkflowInstance(srId, srrId, flowId, null);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用流程失败:" + e);
		}
		return instanceId;
	}
	public String createInstanceAuto(String srId, String flowIdStr) {
		String instanceId = null;
		try {
			if (srId == null || flowIdStr == null) {
				throw new RollbackableBizException("无启动流程！");
			}
			String flowId, rrinfoId;
			String[] subFlow = flowIdStr.split(":");
			flowId = subFlow[0];
			rrinfoId = subFlow[1];
			instanceId = requestWorkflowService.createSubWorkflowInstance(srId, rrinfoId, flowId, null);
			
		} catch (Exception e) {
			throw new RuntimeException("调用流程失败:" + e);
		}
		return instanceId;
	}
	public String startInstance(String instanceId) throws Exception{
		requestWorkflowService.startSubWorkflowInstance(instanceId);
		return Internation.language("process_start_success");
	}

	public List<DeviceNetIP> getVmNetIp(String rrinfoId, String vmdeviceId) throws BizException{
		List<DeviceNetIP> deviceNetIPs = null;
		try {
			deviceNetIPs = ipAllocToDeviceService.qryAllocedIPForDevices(vmdeviceId);
		} catch (Exception e) {
			throw new BizException(e);
		}
		return deviceNetIPs;
	}

	@SuppressWarnings("rawtypes")
	public Pagination<Map> queryBmSrRrinfoByParam(PaginationParam pagination) {
		return bmSrDao.pageQuery("selectBmSrRrinfoByParamTotal", "selectBmSrRrinfoByParamPage", pagination);
	}
	
	@SuppressWarnings("rawtypes")
	public Pagination<Map> queryBmSrRrinfoRecycleByParam(PaginationParam pagination) {
		return bmSrDao.pageQuery("selectBmSrRrinfoRecycleByParamTotal", "selectBmSrRrinfoRecycleByParamPage", pagination);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pagination<Map> queryBmSrRrinfoExtendByParam(PaginationParam pagination) {
		String srId = (String) pagination.getParams().get("srId");
		Pagination<Map> pagMap = null;
		try {
			BmSrVo bmSr = bmSrDao.findBmSrVoById(srId);
			if(bmSr.getSrTypeMark().equals("VE")){
				pagMap =  bmSrDao.pageQuery("selectBmSrRrinfoExtendByParamTotal", "selectBmSrRrinfoExtendByParamPage", pagination);
			
				for(Object o :pagMap.getDataList()){
					Map maps = (Map)o;
					JSONObject jsonObj = JSON.parseObject((String)maps.get("parametersJson"));
					maps.put("cpu", jsonObj.get("cpu"));
					maps.put("mem", jsonObj.get("mem"));
					if(!CommonUtil.isEmpty(jsonObj)) {
						Object duId = jsonObj.get("duId");
						if(!CommonUtil.isEmpty(duId)) {
							DeployUnitVo deployUnitVo = deployunitServiceImpl.getDeployUnitById(duId.toString());
							if(!CommonUtil.isEmpty(deployUnitVo)) {}{
								maps.put("duName", deployUnitVo.getCname());
								maps.put("duEname", deployUnitVo.getEname());
							}
						}
					}
				}
			}
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		return pagMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pagination<Map> queryBmSrRrinfoResoureList(PaginationParam pagination) {
		Pagination<Map> paginat = bmSrDao.pageQuery("selectBmSrRrinfoResoureTotal", "selectBmSrRrinfoResourePage", pagination);
		List<Map> list = paginat.getDataList();
		for(Map<String, String> map : list){
			if(map.get("datastoreType") == null || "LOCAL_DISK".equals(map.get("datastoreType"))){
				map.remove("datastoreName");
				map.put("datastoreName", map.get("localDatastoreName"));
			}
			JSONObject jsonObj = JSON.parseObject((String)map.get("parametersJson"));
			if(!CommonUtil.isEmpty(jsonObj)) {
				Object duId = jsonObj.get("duId");
				if(!CommonUtil.isEmpty(duId)) {
					DeployUnitVo deployUnitVo = deployunitServiceImpl.getDeployUnitById(duId.toString());
					if(!CommonUtil.isEmpty(deployUnitVo)) {}{ 
						map.put("duName", deployUnitVo.getCname());
						map.put("duEname", deployUnitVo.getEname());
						map.put("duId",duId.toString());
					}
				}
			}
		}
		return paginat;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pagination<Map> findExpandVirtualDeviceBySrId(PaginationParam pagination) {
		Pagination<Map> data = bmSrDao.pageQuery("selectExpandVirtualDeviceTotal", "selectExpandVirtualDevicePage", pagination);
		for(Object o :data.getDataList()){
			Map map = (Map)o;
			JSONObject jsonObj = JSON.parseObject((String)map.get("parametersJson"));
			if(!CommonUtil.isEmpty(jsonObj)) {
				Object duId = jsonObj.get("duId");
				if(!CommonUtil.isEmpty(duId)) {
					DeployUnitVo deployUnitVo = deployunitServiceImpl.getDeployUnitById(duId.toString());
					if(!CommonUtil.isEmpty(deployUnitVo)){
						map.put("duName", deployUnitVo.getCname());
						map.put("duEname", deployUnitVo.getEname());
					}
				}
			}
		}
		return data;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List queryWorkflowLinkList(Map paramMap) {
		List data = bmSrDao.findListByParam("selectWorkflowLinkList", paramMap);
		for(Object o :data){
			Map map = (Map)o;
			JSONObject jsonObj = JSON.parseObject((String)map.get("parametersJson"));
			if(!CommonUtil.isEmpty(jsonObj)) {
				Object duId = jsonObj.get("duId");
				if(!CommonUtil.isEmpty(duId)) {
					DeployUnitVo deployUnitVo = deployunitServiceImpl.getDeployUnitById(duId.toString());
					if(!CommonUtil.isEmpty(deployUnitVo)) {
						map.put("duName", deployUnitVo.getCname());
						map.put("duEname", deployUnitVo.getEname());
					}
				}
			}
		}
		return data;
	}
	
	public List<BmSrAttrValVo> getBmSrAttr(String rrinfoId) throws RollbackableBizException{
		return bmSrAttrValDao.findBmSrAttrValListByRrinfoId(rrinfoId);
	}
	public  synchronized void saveOperateEnd(String srId, String todoId, String srType) throws Exception{
		if(!srType.equals("PS") && !srType.equals("PR")) { // 物理机不走此逻辑，此逻辑中非空验证缺失太多，后续再完善
			this.saveInfo(srId);
		}
		 RmDeviceVolumesRefPo rmDeviceVolumesRefPo;
		 String dataDisk;
		List<BmSrRrinfoVo> srRrinfoList = bmSrRrinfoDao.findBmSrRrinfoListBySrId(srId);
		if (srType.equals(SrTypeMarkEnum.VIRTUAL_SUPPLY.getValue()) || srType.equals(SrTypeMarkEnum.PHYSICAL_SUPPLY.getValue())) {
			CmDevicePo vmDevice;
			CmVmPo vm;
			for (BmSrRrinfoVo srRrinfoVo : srRrinfoList) {
				JSONObject json = JSONObject.parseObject(srRrinfoVo.getParametersJson());
			    dataDisk = json.getIntValue("dataDisk")+"";
				List<BmSrRrVmRefPo> vmRefList = bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(srRrinfoVo.getRrinfoId());
				int len = vmRefList == null ? 0 : vmRefList.size();
				for(int i=0 ; i<len ; i++) {
					vmDevice = cmDeviceDao.findCmDeviceById(vmRefList.get(i).getDeviceId());
					//更新设备构建状态
					vmDevice.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_ONLINE.getValue());
					cmDeviceDao.updateCmDeviceState(vmDevice);
					vm = cmVmDao.findCmVmById(vmRefList.get(i).getDeviceId());
					vm.setOnlineTime(SrDateUtil.getSrFortime(new Date()));
					String platformCode = vm.getPlatformCode();
					//更新设备上线时间
					cmVmDao.updateCmVmOnlineTime(vm);
					//虚拟机供给，平台类型为vmwarex86平台的虚拟机，外挂磁盘大于0，向rm_device_volumes_ref中插入数据，此步骤针对vmware类型虚拟机
					if(srType.equals(SrTypeMarkEnum.VIRTUAL_SUPPLY.getValue())){
						if(!CommonUtil.isEmpty(platformCode) && platformCode.equals("X") ){
							if(!CommonUtil.isEmpty(dataDisk) && Integer.parseInt(dataDisk)>0){
								rmDeviceVolumesRefPo = new RmDeviceVolumesRefPo();
								rmDeviceVolumesRefPo.setId(UUIDGenerator.getUUID());
								rmDeviceVolumesRefPo.setDeviceId(vmRefList.get(i).getDeviceId());
								rmDeviceVolumesRefPo.setDiskSize(dataDisk);
								rmDeviceVolumesRefPo.setMountStatus(RmMountStatusEnum.MOUNT.getValue());
								rmDeviceVolumesRefPo.setDiskType(RmVolumeTypeEnum.EXTERNAL_DISK.getValue());
								rmDeviceVolumesRefPo.setVolumeName(RmVolumeTypeEnum.EXTERNAL_DISK.getValue()+System.currentTimeMillis());
								cmDeviceDao.saveRmDeviceVolumesRefPo(rmDeviceVolumesRefPo);
							}
						}
					}
				}
			}
			//更新物理机已用的cpu和内存
			cmHostDao.updateCmHostUsed();
		}else if(srType.equals(SrTypeMarkEnum.VIRTUAL_EXTEND.getValue())){
			CmVmPo vm;
			CmDevicePo vmDevice;
			for (BmSrRrinfoVo srRrinfoVo : srRrinfoList) {
				JSONObject json = JSONObject.parseObject(srRrinfoVo.getParametersJson());
				dataDisk = json.getIntValue("dataDisk")+"";
				//sysDisk = srRrinfoVo.getSysDisk().toString();
				List<BmSrRrVmRefPo> vmRefList = bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(srRrinfoVo.getRrinfoId());
				int len = vmRefList == null ? 0 : vmRefList.size();
				for(int i=0 ; i<len ; i++) {
					String vmId = vmRefList.get(i).getDeviceId();
					vm = cmVmDao.findCmVmById(vmId);
					vm.setCpu(json.getIntValue("cpu"));
					vm.setMem(json.getIntValue("mem"));
					//更新虚拟机的cpu、内存
					cmVmDao.updateCmVmModel(vm);
					String platformCode = vm.getPlatformCode();
					//虚拟机扩容，平台类型为vmwarex86平台的虚拟机，外挂磁盘大于0，向rm_device_volumes_ref中插入数据，此步骤针对vmware类型虚拟机
					if(!CommonUtil.isEmpty(platformCode) && platformCode.equals("X")){
						if(!CommonUtil.isEmpty(dataDisk) && Integer.parseInt(dataDisk)>0){
							rmDeviceVolumesRefPo = new RmDeviceVolumesRefPo();
							rmDeviceVolumesRefPo.setId(UUIDGenerator.getUUID());
							rmDeviceVolumesRefPo.setDeviceId(vmRefList.get(i).getDeviceId());
							rmDeviceVolumesRefPo.setDiskSize(dataDisk);
							rmDeviceVolumesRefPo.setMountStatus(RmMountStatusEnum.MOUNT.getValue());
							rmDeviceVolumesRefPo.setDiskType(RmVolumeTypeEnum.EXTERNAL_DISK.getValue());
							rmDeviceVolumesRefPo.setVolumeName(RmVolumeTypeEnum.EXTERNAL_DISK.getValue()+System.currentTimeMillis());
							cmDeviceDao.saveRmDeviceVolumesRefPo(rmDeviceVolumesRefPo);
						}
					}
					vmDevice = cmDeviceDao.findCmDeviceById(vmRefList.get(i).getDeviceId());
					//更新设备构建状态
					vmDevice.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_ONLINE.getValue());
					cmDeviceDao.updateCmDeviceState(vmDevice);
				}
			}
			//更新物理机已用cpu和已用内存
			cmHostDao.updateCmHostUsed();
		}else if(srType.equals(SrTypeMarkEnum.VIRTUAL_RECYCLE.getValue()) || srType.equals(SrTypeMarkEnum.PHYSICAL_RECYCLE.getValue())){
			CmDevicePo vmDevice;
			CmVmPo vm;
			for (BmSrRrinfoVo srRrinfoVo : srRrinfoList) {
				List<BmSrRrVmRefPo> vmRefList = bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(srRrinfoVo.getRrinfoId());
				int len = vmRefList == null ? 0 : vmRefList.size();
				for(int i=0 ; i<len ; i++) {
					String vmId = vmRefList.get(i).getDeviceId();
					vm = cmVmDao.findCmVmById(vmId);
					//清空cm_vm表中，虚拟机所属服务器角色
					if(vm!=null){
						cmVmDao.updateCmVmDuId(vm);
						vm.setUpdateDateTime(SrDateUtil.getSrFortime(new Date()));
					}
					//更新设备表中设备的下线时间（cm_vm表）
					cmVmDao.updateVmOfflineTime(vm);
					vmDevice = cmDeviceDao.findCmDeviceById(vmId);
					//更新设备构建状态为下线，并在cm_device表中逻辑删除
					if(vmDevice!=null){
						vmDevice.setIsActive(IsActiveEnum.NO.getValue());
						vmDevice.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_OFFLINE.getValue());
						cmDeviceDao.updateCmDeviceState(vmDevice);
					}
					try {
						//回收设备对应的ip地址
						OpenstackIpAddressPo updateIpAddress = new OpenstackIpAddressPo();
						updateIpAddress.setInstanceId(vmId);
						virtualNetworkDao.updateIpAddressRecycleByVm(updateIpAddress);
					} catch (Exception e) {
						throw new RollbackableBizException("回收设备IP失败，" + e);
					}
				}
				
				String duId = srRrinfoVo.getDuId();
				if(!CommonUtil.isEmpty(duId)){
					//查询服务器角色下的虚拟机列表，若为空，将服务器角色表中的云服务信息清空
					List<CmVmPo> cmVmList = cmVmDao.findCmVmByDuId(duId);
					if(cmVmList == null || cmVmList.size() == 0) {
						DeployUnitPo deployUnitPo = new DeployUnitPo();
						deployUnitPo.setDuId(duId);
						deployUnitPo.setServiceId(null);
						deployUnitPo.setUpdateServiceFlag(true);
						deployunitDao.updateDeployunit(deployUnitPo);
					}
				}
			}
			// 将物理机锁定资源释放
			cmHostDao.updateCmHostUsed();
		}
		//
		if(!CommonUtil.isEmpty(todoId)){
			this.saveAndDriveWorkflow(srId, SrStatusCodeEnum.REQUEST_WAIT_CLOSE.getValue(), todoId);
		}
		logger.info("实施完成执行完毕");
	}
	//验证关单实现类
	public synchronized void closeRequestSr(String srId, String todoId) throws Exception {
		if(!CommonUtil.isEmpty(todoId)){
			this.saveAndDriveWorkflow(srId, SrStatusCodeEnum.REQUEST_CLOSED.getValue(), todoId);
		}
		BmSrVo bmSr = bmSrDao.findBmSrVoById(srId);
		bmSrDao.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_CLOSED.getValue());
		if(bmSr.getSrTypeMark().equals(SrTypeMarkEnum.VIRTUAL_SUPPLY.getValue()) || bmSr.getSrTypeMark().equals(SrTypeMarkEnum.PHYSICAL_SUPPLY.getValue())) {
			bmSrDao.updateDeviceState(srId);
		}
		//update by wmy 20171212   
		if(bmSr.getSrTypeMark().equals("SA_ADD_VOLUME") || bmSr.getSrTypeMark().equals("SA_DELETE_VOLUME")){
			//服务自动化，添加卷，什么也不做
		}else {
			List<AppStatVo> list=bmSrDao.findAppStatBySrId(srId);
			HashMap<String, String> rrinfoMap = new HashMap<String, String> ();
			for(int i=0;i<list.size();i++){
				AppStatVo appStatVo=list.get(i);
				JSONObject json = JSONObject.parseObject(appStatVo.getParam());
				if(appStatVo.getCpu()==null){
					appStatVo.setCpu(0);
				}if(appStatVo.getMem()==null){
					appStatVo.setMem(0);
				}
				if(appStatVo.getSrTypeMark().equals("VE")) {
					appStatVo.setDisk(json.getIntValue("dataDisk"));
					appStatVo.setDuID(json.getString("duId"));
					appStatVo.setServiceID(json.getString("serviceId"));
					appStatVo.setCpu(appStatVo.getCpu() - appStatVo.getCpuOld());
					appStatVo.setMem(appStatVo.getMem() - appStatVo.getMemOld());
					//appStatVo.setDisk(appStatVo.getDisk() - appStatVo.getDiskOld());
				}
				if(appStatVo.getSrTypeMark().equals("VS") || appStatVo.getSrTypeMark().equals("SA")) {
					List<CmLun> lunList = bmSrRrinfoDao.getLunListByRrinfoId(appStatVo.getRrinfoId());
					appStatVo.setDuID(json.getString("duId"));
					appStatVo.setServiceID(json.getString("serviceId"));
					if(lunList != null && lunList.size() > 0) {
						int disk = this.getLunSize(lunList);
						if(rrinfoMap.get(appStatVo.getRrinfoId()) == null) {
							appStatVo.setDisk(disk);
							// 一个资源请求下只更新一个虚拟机的disk信息，因为同一个资源请求下的虚机使用的disk都是同一组lun信息
							CmVmPo vm = cmVmDao.findCmVmById(appStatVo.getDiviceID());
							vm.setDisk(disk);
							cmVmDao.updateVmDisk(vm);
							rrinfoMap.put(appStatVo.getRrinfoId(), "true");
						}
					} else {
						
						if(appStatVo.getSrTypeMark().equals("SA")) {
							continue;
						}
					}
				}
				
				/*if(list.get(i).getSrTypeMark().equals("VS")){
					appStatVo.setSrTypeMark(SrTypeMarkEnum.VIRTUAL_SUPPLY.getValue());
				}else if(list.get(i).getSrTypeMark().equals("VE")){
					appStatVo.setSrTypeMark(SrTypeMarkEnum.VIRTUAL_EXTEND.getValue());
				}else if(list.get(i).getSrTypeMark().equals("VR")){
					appStatVo.setSrTypeMark(SrTypeMarkEnum.VIRTUAL_RECYCLE.getValue());
				}else if(list.get(i).getSrTypeMark().equals("SA")){
					appStatVo.setSrTypeMark(SrTypeMarkEnum.SERVICE_AUTO.getValue());
				}*/
				appStatVo.setSrTypeMark(list.get(i).getSrTypeMark());
				appMagServiceImpl.addAppStat(appStatVo);
			}
		}
		
		logger.info("验证关单执行完成");
	}
	
	private int getLunSize(List<CmLun> lunList) {
		int len = lunList == null ? 0 : lunList.size();
		int lunSize = 0;
		for(int i=0 ; i<len ; i++) {
			lunSize += lunList.get(i).getLunSize();
		}
		return lunSize;
	}
	
	public void saveAndDriveWorkflow(String srId, String srStatus, String todoId) throws Exception {
//		if(srStatus != null && !"".equals(srStatus)) {
//			bmSrDao.updateBmSrStatus(srId, srStatus);
//		}
		requestWorkflowService.driveWorkflow(todoId, DriveWfTypeEnum.DRIVE_WF_SUBMIT.getValue());
	}
	
	public String recordLog(String srId) throws RollbackableBizException {
		BmSrVo bmSr = bmSrDao.findBmSrVoById(srId);
		String logContent = "服务单号：" + bmSr.getSrCode();
		return logContent;
	}
	
	public void saveAndDriveWorkflow(String srId, String srStatus, String todoId, String driveWfType) throws Exception {
		/**流程结束修改状态*/
//		bmSrDao.updateBmSrStatus(srId, srStatus);
		requestWorkflowService.driveWorkflow(todoId, driveWfType);
	}
	
	public List<BmSrVo> findNewestCompleteRequest(int num) throws RollbackableBizException {
		return bmSrDao.findNewestCompleteRequest(num);
	}
	
	public List<BmSrVo> findNewestCreateRequest(int num, String creatorId) throws RollbackableBizException {
		return bmSrDao.findNewestCreateRequest(num, creatorId);
	}
	
	public List<BmToDoVo> findNewestWaitDealRequest(int num, String creatorId) throws RollbackableBizException {
		String roleIds = "";
		List<SysRolePo> roleList = userDao.findRoleByUserId(creatorId);
		int len = roleList == null ? 0 : roleList.size();
		for(int i=0 ; i<len ; i++) {
			roleIds += ",'" + roleList.get(i).getRoleId() + "'";
		}
		return bmSrDao.findNewestWaitDealRequest(num, creatorId, roleIds.length() > 0 ? roleIds.substring(1) : "'-1'");
	}
	
	public List<AppSysKpiVo> findAppSysVirtualServer() throws RollbackableBizException {
		return bmSrDao.findAppSysVirtualServer();
	}
	
	public List<AppSysKpiVo> findAppSysCompleteRequest() throws RollbackableBizException {
		return bmSrDao.findAppSysCompleteRequest();
	}
	
	public String getHasAttrRrinfoIdBySrId(String srId) throws RollbackableBizException {
		return bmSrRrinfoDao.getHasAttrRrinfoIdBySrId(srId);
	}
	
	public void deleteRrinfoById(String rrinfoId) throws RollbackableBizException {
		
		//将服务器角色资源释放
		BmSrRrinfoPo rrinfo = bmSrRrinfoDao.findBmSrRrinfoById(rrinfoId);
		DeployUnitPo deployUnitPo = new DeployUnitPo();
		deployUnitPo.setDuId(rrinfo.getDuId());
		boolean flag = deployunitDao.includeVm(rrinfo.getDuId());
		if(!flag){
			deployUnitPo.setServiceId(null);
			deployunitDao.updateDeployUnitServiceId(deployUnitPo);
		}
		bmSrAttrValDao.deleteBmSrAttrByRrinfoId(rrinfoId);
		bmSrRrinfoDao.deleteBmSrRrinfoById(rrinfoId);
	}
	public void deleteRrinfoByIds(String rrinfoIds) throws RollbackableBizException {
		if(rrinfoIds != null && rrinfoIds.length()>0){
			String[] rrinfoIdArr = rrinfoIds.split(",");
			for(int i=0;i<rrinfoIdArr.length;i++){
				String rrinfoId = rrinfoIdArr[i];
				bmSrAttrValDao.deleteBmSrAttrByRrinfoId(rrinfoId);
				bmSrRrinfoDao.deleteBmSrRrinfoById(rrinfoId);
			}
		}
		
	}
	
	/********************************* setter or getter **************************/
	public void setRequestWorkflowService(
			IRequestWorkflowService requestWorkflowService) {
		this.requestWorkflowService = requestWorkflowService;
	}
	
	public void setBmSrDao(BmSrDaoImpl bmSrDao) {
		this.bmSrDao = bmSrDao;
	}
	
	public void setBmApproveDao(IBmApproveDao bmApproveDao) {
		this.bmApproveDao = bmApproveDao;
	}

	public void setBmToDoDao(IBmToDoDao bmToDoDao) {
		this.bmToDoDao = bmToDoDao;
	}


	public void setIpAllocToDeviceService(
			IIpAllocToDeviceNewService ipAllocToDeviceService) {
		this.ipAllocToDeviceService = ipAllocToDeviceService;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setBmSrAttrValDao(IBmSrAttrValDao bmSrAttrValDao) {
		this.bmSrAttrValDao = bmSrAttrValDao;
	}

	public void setBmSrRrinfoDao(IBmSrRrinfoDao bmSrRrinfoDao) {
		this.bmSrRrinfoDao = bmSrRrinfoDao;
	}

	public void setBmSrRrVmRefDao(IBmSrRrVmRefDao bmSrRrVmRefDao) {
		this.bmSrRrVmRefDao = bmSrRrVmRefDao;
	}

	public void setBmSrStatusMapDao(IBmSrStatusMapDao bmSrStatusMapDao) {
		this.bmSrStatusMapDao = bmSrStatusMapDao;
	}

	public void setDeployunitDao(IDeployunitDao deployunitDao) {
		this.deployunitDao = deployunitDao;
	}

	public void setBmSrDao(IBmSrDao bmSrDao) {
		this.bmSrDao = bmSrDao;
	}

	public void setCmDeviceDao(ICmDeviceDAO cmDeviceDao) {
		this.cmDeviceDao = cmDeviceDao;
	}

	public void setCmHostDao(ICmHostDAO cmHostDao) {
		this.cmHostDao = cmHostDao;
	}
	public void setCmVmDao(ICmVmDAO cmVmDao) {
		this.cmVmDao = cmVmDao;
	}

	@Override
	public  void saveHostName(String vmId,String hostId) throws Exception {
		List<String> listHostIds = new ArrayList<String>();
		listHostIds.add(hostId);
		List<CmHostDatastorePo> cmHostDatastorePos = cmHostDatastoreRefDAO.findDatastoreIdByHosts(listHostIds);
		String datastoreId = null ;
		if( cmHostDatastorePos != null && cmHostDatastorePos.size() > 0){
			datastoreId = cmHostDatastorePos.get(0).getDatastoreId();
		}else{
			throw new Exception("物理机["+hostId+"]无可用的datastore信息");
		}
		CmVmPo cmVmPo = new CmVmPo();
		cmVmPo.setDatastoreId(datastoreId);
		cmVmPo.setHostId(hostId);
		cmVmPo.setId(vmId);
		cmVmDao.update("updataVmById", cmVmPo);
	}

	private void saveInfo(String srId) throws RollbackableBizException{
		List<AppStatVo> list=bmSrDao.findAppStatBySrId(srId);
		NotificationPo notiPo = new NotificationPo();
		String operation = "";
		for(int i=0;i<list.size();i++){
			AppStatVo appStatVo=list.get(i);
			String resourceType = ResourceType.VIRTUAL.getValue();
			if(list.get(i).getSrTypeMark().equals("VS")){
				operation = OperationType.BUILD_VM.getValue();
			}else if(list.get(i).getSrTypeMark().equals("VE")){
				operation = OperationType.EXPANSION.getValue();
			}else if(list.get(i).getSrTypeMark().equals("VR")){
				operation = OperationType.RECYCLE.getValue();
				CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(list.get(i).getDiviceID());
				String content = "";
				if(StringUtils.isNotBlank(cmDeviceVMShowBo.getAppInfo_name())){
					content += cmDeviceVMShowBo.getAppInfo_name()+".";
				}else if(StringUtils.isNotBlank(cmDeviceVMShowBo.getDu_name())){
					content += cmDeviceVMShowBo.getDu_name()+".";
				}else if(StringUtils.isNotBlank(cmDeviceVMShowBo.getVm_name())){
					content += cmDeviceVMShowBo.getVm_name()+".";
				}
				notiPo.setOperationContent(content);
			}else if(list.get(i).getSrTypeMark().equals("SA")) {
				operation = OperationType.SERVICEAUTO.getValue();
			}else if(list.get(i).getSrTypeMark().equals("PS")) {
				operation = OperationType.BUILD_HOST.getValue();
				resourceType = ResourceType.PHYSICAL.getValue();
			}else if(list.get(i).getSrTypeMark().equals("PR")) {
				operation = OperationType.RECYCLE_HOST.getValue();
				resourceType = ResourceType.PHYSICAL.getValue();
			}
			notiPo.setOperationType(operation);
			notiPo.setResourceId(appStatVo.getDiviceID());
			notiPo.setResourceType(resourceType);
			notiPo.setSource(Source.MANUALOPERATION.getValue());
			notiPo.setType(Type.TIP.getValue());
			notiPo.setUserId(list.get(0).getCreatorId());
			notiPo.setTenantId(list.get(0).getTenantId());
			notiServiceImpl.insertNotification(notiPo);
		}
	}
	
	public void closeServiceRequest(String srCode) throws Exception {
		this.closeServiceRequest(srCode, false);
	}
	
	public void closeServiceRequest(String srCode, boolean deleteResource) throws Exception {
		BmSrVo sr = null;
		try {
			sr = bmSrDao.findBmSrVoBySrCode(srCode);
		} catch(Exception e) {
			logger.error("异常exception",e);
		}
		if(sr == null) {
			throw new Exception(Internation.language("not_found_work") + srCode);
		}
		if(sr.getSrStatusCode().equals(SrStatusCodeEnum.REQUEST_CLOSED.getValue())) {
			throw new Exception(Internation.language("work_order_been_destroy") + srCode);
		}
		String srId = sr.getSrId();
		// 作废时是否回收资源标识
		if (deleteResource) {
			if(sr.getSrTypeMark().equals(SrTypeMarkEnum.VIRTUAL_SUPPLY.getValue())) { // 供给
				// 预分配资源回收
				virtualSupplyService.doUpdateResourceRecycle(srId, "");
			} else {
				throw new Exception("申请单号[" + srCode + "]为" + sr.getSrTypeMark() + "类型, 暂不支持资源回退.");
			}
		}
		if (sr.getSrTypeMark().equals(SrTypeMarkEnum.FIREWALL.getValue())) {
			// 云防火墙申请
			List<BmSrRrinfoPo> rrinfoList = bmSrRrinfoDao.findBmSrRrinfoBySrId(srId);
			if (rrinfoList != null && rrinfoList.size() > 0) {
				BmSrRrinfoPo rrinfo = rrinfoList.get(0);
				String parametersJson = rrinfo.getParametersJson();
				logger.info("[FirewallCommonHandler] parametersJson : " + parametersJson);
				JSONObject json = JSONObject.parseObject(parametersJson);
				String firewallRequestId = json.getString("firewallRequestId");
				// 云防火墙申请作废操作，将状态改为删除
				firewallRequestServiceImpl.updateFirewallRequestStatus(firewallRequestId, FwRequestStatusEnum.DELETE.getValue(), IsActiveEnum.NO.getValue());
			}
		} else if (sr.getSrTypeMark().equals(SrTypeMarkEnum.FIREWALL_RECYCLE.getValue())) {
			// 云防火墙回收
			List<BmSrRrinfoPo> rrinfoList = bmSrRrinfoDao.findBmSrRrinfoBySrId(srId);
			if (rrinfoList != null && rrinfoList.size() > 0) {
				BmSrRrinfoPo rrinfo = rrinfoList.get(0);
				String parametersJson = rrinfo.getParametersJson();
				logger.info("[FirewallCommonHandler] parametersJson : " + parametersJson);
				JSONObject json = JSONObject.parseObject(parametersJson);
				String firewallRequestId = json.getString("firewallRequestId");
				// 云防火墙回收作废操作，将状态改为已开通
				firewallRequestServiceImpl.updateFirewallRequestStatus(firewallRequestId, FwRequestStatusEnum.OPENED.getValue(), IsActiveEnum.YES.getValue());
			}
		}
		// 将待办事项状态更新为已完成
		bmToDoDao.updateBmToDoStatusBySrId(srId);
		// 强制结束此工单的所有流程
		bpmInstanceDaoImpl.closeInstanceBySrId(srId);
		// 更新工单状态为已作废
		this.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_DELETE.getValue());
		/*List<BmSrRrinfoVo> srRrinfoList = bmSrRrinfoDao.findBmSrRrinfoListBySrId(srId);
		if(srRrinfoList !=null && srRrinfoList.size()>0){
			//将服务器角色资源释放
			for(BmSrRrinfoVo vo:srRrinfoList){
				DeployUnitPo deployUnitPo = new DeployUnitPo();
				deployUnitPo.setServiceId(null);
				deployUnitPo.setDuId(vo.getDuId());
				deployunitDao.updateDeployUnitServiceId(deployUnitPo);
			}
		}*/
	}
	
	public void setBpmInstanceDaoImpl(IBpmInstanceDao bpmInstanceDaoImpl) {
		this.bpmInstanceDaoImpl = bpmInstanceDaoImpl;
	}
	//针对工银瑞信的查询
	@Override
	public Pagination<BmSrVo> getCloudReqeustPagination2(
			PaginationParam pagination) {
		return bmSrDao.pageQuery("findCloudReqeustTotal2", "findCloudReqeustPage2", pagination);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Pagination<Map> queryBmSrRrinfoResoureListAuto(PaginationParam pagination) {
		return bmSrDao.pageQuery("selectBmSrRrinfoResoureAutoTotal", "selectBmSrRrinfoResoureAutoPage", pagination);
	}
	@Override
	public void updateVmProjectId(String vmId, String projectId, String virtualTypeCode)
			throws RollbackableBizException {
		if(RmVirtualType.OPENSTACKKVM.getValue().equals(virtualTypeCode)) {
			iCmDeviceService.updateVmProjectId(vmId,projectId);
		}
	}
	public void setVirtualNetworkDao(IVirtualNetworkDao virtualNetworkDao) {
		this.virtualNetworkDao = virtualNetworkDao;
	}
}