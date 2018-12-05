package com.git.cloud.resmgt.compute.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.bo.CmIpShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostUsernamePasswordPo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.common.model.vo.CmDatastoreVo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.dao.IRmHostDao;
import com.git.cloud.resmgt.compute.model.vo.VmMonitorVo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmGlobalConstants;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.general.field.GeneralValueField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Maps;

import edu.emory.mathcs.backport.java.util.Arrays;
@Service
public class VmControllerServiceKVMImpl implements VmControllerService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Logger log = LoggerFactory
			.getLogger(VmControllerServiceKVMImpl.class);
	private ICmDeviceService iCmDeviceService;
	@Autowired
	private AutomationService automationService;
	@Autowired
	private ResAdptInvokerFactory resInvokerFactory;
	@Autowired
	private ICmHostDAO cmHostDAO;
	@Autowired
	private IRmVmManageServerDAO rmVmMgServerDAO;
	@Autowired
	private ICmPasswordDAO cmPasswordDAO;
	@Autowired
	private IRmDatacenterDAO rmDatacenterDAO;
	@Autowired
	private ICmVmDAO cmVMDao;
	private IIpAllocToDeviceNewService iIpAllocToDeviceService;
	private ICmDeviceDAO icmDeviceDAO;
	private IRmHostDao iRmHostDao;
	@Autowired
	private ParameterService parameterServiceImpl;
	public IRmHostDao getiRmHostDao() {
		return iRmHostDao;
	}
	public void setiRmHostDao(IRmHostDao iRmHostDao) {
		this.iRmHostDao = iRmHostDao;
	}
	public ICmDeviceDAO getIcmDeviceDAO() {
		return icmDeviceDAO;
	}
	public void setIcmDeviceDAO(ICmDeviceDAO icmDeviceDAO) {
		this.icmDeviceDAO = icmDeviceDAO;
	}
	public IIpAllocToDeviceNewService getiIpAllocToDeviceService() {
		return iIpAllocToDeviceService;
	}
	public void setiIpAllocToDeviceService(
			IIpAllocToDeviceNewService iIpAllocToDeviceService) {
		this.iIpAllocToDeviceService = iIpAllocToDeviceService;
	}
	
	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}
	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}
	public AutomationService getAutomationService() {
		return automationService;
	}
	public void setAutomationService(AutomationService automationService) {
		this.automationService = automationService;
	}
	public ResAdptInvokerFactory getResInvokerFactory() {
		return resInvokerFactory;
	}
	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}
	public ICmHostDAO getCmHostDAO() {
		return cmHostDAO;
	}
	public void setCmHostDAO(ICmHostDAO cmHostDAO) {
		this.cmHostDAO = cmHostDAO;
	}
	public IRmVmManageServerDAO getRmVmMgServerDAO() {
		return rmVmMgServerDAO;
	}
	public void setRmVmMgServerDAO(IRmVmManageServerDAO rmVmMgServerDAO) {
		this.rmVmMgServerDAO = rmVmMgServerDAO;
	}
	public ICmPasswordDAO getCmPasswordDAO() {
		return cmPasswordDAO;
	}
	public void setCmPasswordDAO(ICmPasswordDAO cmPasswordDAO) {
		this.cmPasswordDAO = cmPasswordDAO;
	}
/*	public IRmDatacenterDAO getRmDCDAO() {
		return rmDCDAO;
	}
	public void setRmDCDAO(IRmDatacenterDAO rmDCDAO) {
		this.rmDCDAO = rmDCDAO;
	}
	*/
	
	
	//查询虚拟机状态
	@Override
	public String vmRunningState(String vmId) throws RollbackableBizException {
		
		CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(vmId);
		String hostId = cmDeviceVMShowBo.getHostId();
		String vmName = cmDeviceVMShowBo.getVm_name();
		CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(hostId);
		List<CmIpShowBo> ipList = cmDeviceHostShowBo.getIpList();
		String url = ipList.get(0).getIp();
		CmHostUsernamePasswordPo cmHostUsernamePasswordPo = cmHostDAO.findHostUsernamePassword(hostId);
		String username = cmHostUsernamePasswordPo.getUsername();
		String password = cmHostUsernamePasswordPo.getPassword();
		if (StringUtils.isBlank(password))
			throw new RollbackableBizException("获取HOST [" + username + "] password is null");
		password = PwdUtil.decryption(password);
		
		//物理机 URL集合
		List<DataObject> urlList = new ArrayList<DataObject>();		
		DataObject hostDataObject = DataObject.CreateDataObject();
		hostDataObject.setString(GeneralKeyField.KVM.URL, url);
		hostDataObject.setString(GeneralKeyField.KVM.USERNAME, username);
		hostDataObject.setString(GeneralKeyField.KVM.PASSWORD, password);
		//虚拟机名
		List<String> domainList = new ArrayList<String>();
		domainList.add(vmName);
		hostDataObject.setList(GeneralKeyField.VM.VAPP_NAME, domainList);	
		//设置hostDataObject
		urlList.add(hostDataObject);	
		
		String result = "";
		try {
			String loginfo = "虚拟机名称:" + vmName;
			log.debug(loginfo);
			//header
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			header.setOperation(VMOpration.QUERY_POWER_STATE);
			
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			header.setOperationBean("kvmVMPowerOps");
			//body
			BodyDO body = BodyDO.CreateBodyDO();			
			body.setList("POWER_STATE", urlList);			
			body.set(GeneralKeyField.VM.POWER_OPER_TYPE, VmGlobalConstants.VM_STATE);			
			body.set(GeneralKeyField.VM.VM_TYPE, VmGlobalConstants.VM_TYPE_KVM);	
			//reqData
			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);

			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
						HeaderDO.class);
				BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY,
						BodyDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					DataObject resultDataObject = (DataObject) rspBody.get("POWER_STATE");
					DataObject resultDataObject1 = (DataObject) resultDataObject.get(url);
					result = resultDataObject1.getString(vmName);
				}else{
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	}

	
	//执行开机、关机、快照和恢复操作。
	private  String vmPowerOperation(String vmId,String operation) throws RollbackableBizException {
		CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(vmId);
		String hostId = cmDeviceVMShowBo.getHostId();
		String vmName = cmDeviceVMShowBo.getVm_name();
		CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(hostId);
		List<CmIpShowBo> ipList = cmDeviceHostShowBo.getIpList();
		String url = ipList.get(0).getIp();
		CmHostUsernamePasswordPo cmHostUsernamePasswordPo = cmHostDAO.findHostUsernamePassword(hostId);
		String username = cmHostUsernamePasswordPo.getUsername();
		String password = cmHostUsernamePasswordPo.getPassword();
		if (StringUtils.isBlank(password))
			throw new RollbackableBizException("获取HOST [" + username + "] password is null");
		password = PwdUtil.decryption(password);
		
		//物理机 URL集合
		List<DataObject> urlList = new ArrayList<DataObject>();
		DataObject hostDataObject = DataObject.CreateDataObject();
		hostDataObject.setString(GeneralKeyField.KVM.URL, url);
		hostDataObject.setString(GeneralKeyField.KVM.USERNAME, username);
		hostDataObject.setString(GeneralKeyField.KVM.PASSWORD, password);
		//虚拟机
		List<String> domainList = new ArrayList<String>();
		domainList.add(vmName);
		hostDataObject.setList(GeneralKeyField.VM.VAPP_NAME, domainList);				
		urlList.add(hostDataObject);				

		String result = "";
		// 日志，调试需要
		String loginfo = "用户名:" + username + ";" + "密码:"
				+ password + ";" + "虚拟机名称:" + vmName;
		log.debug(loginfo);
		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			header.setOperationBean("kvmVMPowerOps");

			BodyDO body = BodyDO.CreateBodyDO();			
			body.setList("POWER_STATE", urlList);			
			body.set(GeneralKeyField.VM.VM_TYPE, VmGlobalConstants.VM_TYPE_KVM);
			body.set(GeneralKeyField.VM.POWER_OPER_TYPE, operation);

			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);

			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
						HeaderDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					CmDevicePo vmVo = new CmDevicePo();
					String runningState = "unknown";
					if("poweron".equals(operation)){
						runningState = "poweron";
					}else if("shutdown".equals(operation)){
						runningState = "poweroff";
					}else if("suspend".equals(operation)){
						runningState = "paused";
					}else if("resume".equals(operation)){
						runningState = "poweron";
					}else if("reboot".equals(operation)){
						runningState = "poweron";
					}
					vmVo.setId(vmId);
					vmVo.setRunningState(runningState);
					iCmDeviceService.updateCmdeviceRunningState(vmVo);
					result = "success";
				} else {
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	}
	
	
	


	
	private  String vmSnapshotOperation(String snapshotName,String vmId,String vmName,String operation) throws RollbackableBizException {
		String result = "";
		try {
			List<RmVmManageServerPo> rmVmList =null;
			String vmHostIp = "" ;
			String dcId = "";
			String esxiUserName = "";	
			String esxiPwd = "";
			CmVmPo vmInfo = cmVMDao.findCmVmById(vmId);
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) cmHostDAO.findHostCpuCdpInfo(vmInfo.getHostId());
			CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(vmInfo.getHostId());
			dcId = cmDeviceHostShowBo.getDatacenter_id();
			vmHostIp = cmDeviceHostShowBo.getIpList().get(0).getIp();
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterById(dcId).getQueueIden());
			header.setOperationBean("kvmSnapShotOperation");

			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(GeneralKeyField.VM.VM_TYPE, "KVM"); 
			body.setString(GeneralKeyField.KVM.URL,vmHostIp); 
			body.setString(GeneralKeyField.VM.VAPP_NAME, vmName); 
			body.setString(GeneralKeyField.KVM.USERNAME, esxiUserName);
			body.setString(GeneralKeyField.KVM.PASSWORD, esxiPwd); 
		    body.setString("VM_SNAPSHOTNAME", snapshotName); 
		    body.setString("VM_SNAP_OPERATION", operation); 
		    

			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);

			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					if("revert".equals(operation)){
						String runningState = this.vmRunningState(vmId);
						CmDevicePo vmVo = new CmDevicePo();
						vmVo.setId(vmId);
						vmVo.setRunningState(runningState);
						iCmDeviceService.updateCmdeviceRunningState(vmVo);
					}
						result = "success";
				} else {
					result = rspHeader.getRetMesg();
				}
			}

		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	}

	
	private String moveVM(String vmId,String dataStoreName,String rceCIp,String targetCIp,String info) 
			throws Exception {
		String result = "";
		try {
			List<RmVmManageServerPo> rmVmList =null;
			String esxiUserName = "";
			String esxiPwd = "";
			CmVmPo vmInfo = cmVMDao.findCmVmById(vmId);
			String hostId = vmInfo.getHostId();
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) cmHostDAO.findHostCpuCdpInfo(vmInfo.getHostId());
			if(!list.isEmpty() || list.size() != 0){
				String vcId = list.get(0).get("vcId").toString();
				rmVmList = rmVmMgServerDAO.findByID("findRmVmManagerServerPo", vcId);
				if(!rmVmList.isEmpty() || rmVmList.size() != 0){
					RmVmManageServerPo vmManagerServerPo = rmVmList.get(0);
					esxiUserName =vmManagerServerPo.getUserName();
					CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(
							vcId, esxiUserName);
					esxiPwd = pwpo.getPassword();
					if (StringUtils.isBlank(esxiPwd))
						throw new RollbackableBizException("获取ManagerServer["
								+ vmManagerServerPo.getServerName()
								+ "] password is null");
					esxiPwd = PwdUtil.decryption(esxiPwd);
				}else{}
			}else{}
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			header.setOperationBean("kvmMigrate");
			BodyDO body = BodyDO.CreateBodyDO();
			body.setString("vmName", vmInfo.getDeviceName());
			body.setString("TYPE", GeneralValueField.VMType.KVM.getValue());
			body.setString(VMFlds.USERNAME, esxiUserName);
			body.setString(VMFlds.PASSWORD, esxiPwd);
			body.setString(VMFlds.URL, rceCIp);
			body.setString("VM_DESTINATIONURL", targetCIp);
			
			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.BODY, body);
			reqData.setDataObject(MesgFlds.HEADER, header);
			
			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1300000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					result = "success";
				} else {
					result = rspHeader.getRetMesg();
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	}

	@Override
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> deviceList,String virtualTypeCode) throws Exception {
		List<ScanResult> scanResultList = new ArrayList<ScanResult>();
		Map<String,List<CmDevicePo>> devMap = Maps.newHashMap();
		//设备信息 
		List<DataObject> devList = new ArrayList<DataObject>();		
		for (CmDevicePo devicePo : deviceList) {
			String hostId = devicePo.getId();
			List<CmDevicePo> vmDeviceList = iCmDeviceService.findVMByHostId(hostId);
			if(vmDeviceList==null || vmDeviceList.isEmpty()){
				continue;
			}
			CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(hostId);
			List<CmIpShowBo> ipList = cmDeviceHostShowBo.getIpList();
			String url = ipList.get(0).getIp();
			CmHostUsernamePasswordPo cmHostUsernamePasswordPo = cmHostDAO.findHostUsernamePassword(hostId);
			String username = cmHostUsernamePasswordPo.getUsername();
			String password = cmHostUsernamePasswordPo.getPassword();
			if (StringUtils.isBlank(password))
				throw new RollbackableBizException("获取HOST [" + username + "] password is null");
			password = PwdUtil.decryption(password);
			DataObject hostDataObject = DataObject.CreateDataObject();
			hostDataObject.setString(GeneralKeyField.KVM.URL, url);
			hostDataObject.setString(GeneralKeyField.KVM.USERNAME, username);
			hostDataObject.setString(GeneralKeyField.KVM.PASSWORD, password);
			//虚拟机
			List<String> vmNameList = new ArrayList<String>();
			devMap.put(url, vmDeviceList);
			for(CmDevicePo li : vmDeviceList){
				vmNameList.add(li.getDeviceName());
			}
			hostDataObject.setList(GeneralKeyField.VM.VAPP_NAME, vmNameList);
			devList.add(hostDataObject);	
		}
		
		
		try {
			//header
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			header.setOperation(VMOpration.QUERY_POWER_STATE);
			
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(deviceList.get(0).getId()).getQueueIden());
			header.setOperationBean("kvmVMPowerOps");
			//body
			BodyDO body = BodyDO.CreateBodyDO();			
			body.setList("POWER_STATE", devList);			
			body.set(GeneralKeyField.VM.POWER_OPER_TYPE, VmGlobalConstants.VM_STATE);			
			body.set(GeneralKeyField.VM.VM_TYPE, VmGlobalConstants.VM_TYPE_KVM);	
			//reqData
			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);

			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				log.error("请求响应失败");
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
						HeaderDO.class);
				BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY,
						BodyDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					DataObject resultDataObject = (DataObject) rspBody.get("POWER_STATE");
					for (Iterator<String> iterator = devMap.keySet().iterator(); iterator.hasNext();) {
						ScanResult sr = new ScanResult();
						String key = iterator.next();
						DataObject vmDataObject = (DataObject) resultDataObject.get(key);
						List<CmDevicePo> vmDevList = devMap.get(key);
						for (CmDevicePo hostVo : vmDevList) {
							String state = vmDataObject.getString(hostVo.getDeviceName());
							hostVo.setRunningState(state);
							iCmDeviceService.updateCmdeviceRunningState(hostVo);
							sr.setDeviceStatus(state);
							sr.setDeviceId(hostVo.getId());
							sr.setDeviceName(hostVo.getDeviceName());
							scanResultList.add(sr);
						}
					}
				}else{
					log.error("获取返回值失败！");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
		return scanResultList;
	}
	
	private String mount(String hostId, String datastoreId,String storageType) throws Exception {
		if(hostId == null || hostId.equals("") || datastoreId == null || datastoreId.equals("")){
			return "主机Id或datastoreId为空";
		}
		//获取主机用户名和密码
		CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceId(hostId);
		String hostUsername = pwpo.getUserName();
		String hostPassword = pwpo.getPassword();
		if (StringUtils.isBlank(hostPassword))
			throw new RollbackableBizException("获取Host" + hostId + "的用户[" + hostUsername + "] password is null");
		hostPassword = PwdUtil.decryption(pwpo.getPassword());
		// 获取主机hostIp
		String hostIp = new String();
		// 获取ip需要进行改造
		List<DeviceNetIP> devIpList = iIpAllocToDeviceService.qryAllocedIPForDevices(hostId);
		
		//获取Datacenter信息
		RmDatacenterPo rmDatacenterPo = iRmHostDao.findDatacenterInfoByHostId(hostId); 
		String queueIden = rmDatacenterPo.getQueueIden();
		//挂nas
		Map<String,String> map1 = new HashMap<String, String>();
        map1.put( "datastoreId", datastoreId);
        String nasName = "" ;
        String nasPath = "" ;
        String nasManageIp = "" ; 
         try {
              CmDatastoreVo  c=  null;//icmDeviceDAO.findStorageDeviceInfo(map1);
              nasName = c.getName();
              nasPath = c.getPath();
              nasManageIp = c.getMgrIp();
        } catch (Exception e) {
        	log.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
        } 
         String  resPoolPath = parameterServiceImpl.getParamValueByName("KVM.RES_POOL_PAHT")+nasName;
        return createNasInterface(queueIden, nasName, nasPath, nasManageIp, hostIp, hostUsername, hostPassword, resPoolPath);
	}
	
	/**
	 * @param GeneralKeyField.Storage.DATASTORE_NAME  nasName，Nas存储别名
	 * @param GeneralKeyField.Storage.DATASTORE_TYPE  nasType -- GeneralValueField.StorageType.NETFS.getValue()
	 * @param GeneralKeyField.Storage.SOURCE_PATH	nasPath，Nas存储路径
	 * @param GeneralKeyField.Storage.SOURCE_HOST	nasManageIp Nas存储IP 
	 * @param GeneralKeyField.VM.VM_TYPE		vmType -- GeneralValueField.VMType.KVM.getValue()
	 * @param GeneralKeyField.KVM.URL			hostIp
	 * @param GeneralKeyField.KVM.USERNAME		hostUsername
	 * @param GeneralKeyField.KVM.PASSWORD		hostPassword
	 * @param GeneralKeyField.Storage.TARGET_PATH	localPath -- 挂载到本地的目标路径
	 * @param queueIden 队列
	 * @return
	 */
	public String createNasInterface(String queueIden, String nasName, String nasPath, String nasManageIp, 
			String hostIp, String hostUsername, String hostPassword, String localPath) throws Exception{
		String result = "";
		// 日志，调试需要
		String loginfo = "KVM用户名:" + hostUsername + ";" + "KVM密码:" + hostPassword;
		log.debug(loginfo);

		try {
			//设置header
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
//			header.setOperation(VMOpration.ADD_NAS);
			header.setOperationBean("kvmAddStoragePool");
			header.set("DATACENTER_QUEUE_IDEN", queueIden);

			//设置body
			BodyDO body = BodyDO.CreateBodyDO();
			body.set(GeneralKeyField.Storage.DATASTORE_NAME, nasName);
			body.set(GeneralKeyField.Storage.DATASTORE_TYPE,  GeneralValueField.StorageType.NETFS.getValue());
			body.set(GeneralKeyField.Storage.SOURCE_HOST, nasManageIp);
			body.set(GeneralKeyField.Storage.SOURCE_PATH, nasPath);
			body.set(GeneralKeyField.VM.VM_TYPE, GeneralValueField.VMType.KVM.getValue());
			body.set(GeneralKeyField.KVM.URL, hostIp);
			body.set(GeneralKeyField.KVM.USERNAME, hostUsername);
			body.set(GeneralKeyField.KVM.PASSWORD, hostPassword);
			body.set(GeneralKeyField.Storage.TARGET_PATH, localPath);
			
			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);

			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,	HeaderDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					result = "success";
				} else {
					result = rspHeader.getRetMesg();
				}
			}

		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	}
	
	private String uninstall(String hostId, String datastoreId,String storageType) throws Exception {
		CmDeviceHostShowBo 	cmDeviceHostShowBo = icmDeviceDAO.getCmDeviceHostInfo("selectCmDeviceHostInfo", hostId);
		String name = "";
		String result = "success";
		if(cmDeviceHostShowBo.getIsInvc().equals("Y")){
			String vcHostIP = cmHostDAO.findHostIpById(hostId);
			CmDatastoreVo  cmVo = icmDeviceDAO.findObjectByID("selectOneCmDatastore", datastoreId);
			name = cmVo.getName();
			result = this.removeNasDataStore(name, hostId ,vcHostIP);
			if(result.equals("success")){
				result = "success";
			}else{
				throw new Exception(result);
			}
		}
	return result;
	}
		public String removeNasDataStore(String name, String hostId,String vcHostIP)
			throws RollbackableBizException {
		String result = "";
		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			header.setOperationBean("kvmRemoveStoragePool");
			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(GeneralKeyField.KVM.URL, vcHostIP);
			body.setString(GeneralKeyField.VM.VM_TYPE, GeneralValueField.VMType.KVM.getValue());
			body.setString(GeneralKeyField.Storage.DATASTORE_NAME, name);
			
			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);

			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
						HeaderDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					result = "success";
				} else {
					result = rspHeader.getRetMesg();
				}
			}

		} catch (Exception e) {
			result= "卸载Nas异常" + e.getMessage();
			log.error("卸载Nas异常" + e);
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
		return result;
	}
}