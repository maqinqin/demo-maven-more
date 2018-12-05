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
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDatastoreRefDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.common.model.vo.CmDatastoreVo;
import com.git.cloud.resmgt.common.service.ICheckResource;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.model.vo.VmMonitorVo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmGlobalConstants;
import com.git.support.common.VmReturnFlds;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.general.field.GeneralValueField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

import edu.emory.mathcs.backport.java.util.Arrays;


@Service
public class VmControllerServiceVMWareImpl implements VmControllerService{
	private static Logger log = LoggerFactory
			.getLogger(VmControllerServiceVMWareImpl.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private AutomationService automationService;
	private ResAdptInvokerFactory resInvokerFactory;
	private ICmHostDAO cmHostDAO;
	private IRmVmManageServerDAO rmVmMgServerDAO;
	private ICmPasswordDAO cmPasswordDAO;
	@Autowired
	private IRmDatacenterDAO rmDatacenterDAO;
	private ICmDeviceService iCmDeviceService;
	@Autowired
	private ICmVmDAO cmVMDao;
	private IIpAllocToDeviceNewService iIpAllocToDeviceService;
	private ICmDeviceDAO icmDeviceDAO;


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


	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}


	public void setAutomationService(AutomationService automationService) {
		this.automationService = automationService;
	}

	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}

	public void setCmHostDAO(ICmHostDAO cmHostDAO) {
		this.cmHostDAO = cmHostDAO;
	}

	public void setRmVmMgServerDAO(IRmVmManageServerDAO rmVmMgServerDAO) {
		this.rmVmMgServerDAO = rmVmMgServerDAO;
	}

	public void setCmPasswordDAO(ICmPasswordDAO cmPasswordDAO) {
		this.cmPasswordDAO = cmPasswordDAO;
	}


	public void setRmDCDAO(IRmDatacenterDAO rmDCDAO) {
		this.rmDatacenterDAO = rmDCDAO;
	}

	@SuppressWarnings("unused")
	@Override
	public String vmRunningState(String vmId) throws RollbackableBizException {
		CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(vmId);
		String vmName = cmDeviceVMShowBo.getVm_name();
		String hostId = cmDeviceVMShowBo.getHostId();
		String dcId = "";
		String vcHostIP = "";
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> l = (List<HashMap<String, Object>>) cmHostDAO
				.findHostCpuCdpInfo(hostId);
		if (l.isEmpty() || l.size() == 0) {
		} else {
			HashMap<String, Object> h = l.get(0);
			String vcId = (String) h.get("vcId");
			List<RmVmManageServerPo> list = rmVmMgServerDAO.findByID(
					"findRmVmManagerServerPo", vcId);
			if (list.isEmpty() || list.size() == 0) {
				throw new RollbackableBizException("get vm manager info error!");
			} else {
				RmVmManageServerPo vmManagerServerPo = list.get(0);
				dcId = vmManagerServerPo.getDatacenterId();
				vcHostIP = vmManagerServerPo.getManageIp();
			}
		}
		String result = "";
		try {
			String esxiUserName = automationService.getAppParameter("VSPHERE.ESXI_USER_NAME");
			String vshpereUrlPrefix = automationService.getAppParameter("VSPHERE.URL_PREFIX");
			String vshpereUrlSuffix = automationService.getAppParameter("VSPHERE.URL_SUFFIX");
			String esxiIp = automationService.getDeviceManagementIp(hostId);
			String esxiUrl = vshpereUrlPrefix + esxiIp + vshpereUrlSuffix;
			String esxiPassword = automationService.getPassword(hostId, esxiUserName);
			// 日志，调试需要
			String loginfo = "esxiUserName:" + esxiUserName + ";" + "esxiPassword:"
					+ esxiPassword + ";" + "虚拟机名称:" + vmName;
			log.debug(loginfo);
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperation(VMOpration.QUERY_POWER_STATE);
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			BodyDO body = BodyDO.CreateBodyDO();
			body.set(VMFlds.URL, esxiUrl);
			body.set(VMFlds.USERNAME, esxiUserName);
			body.set(VMFlds.PASSWORD, esxiPassword);
			body.set(VMFlds.VAPP_NAME, vmName);

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
					result = rspBody.getString(VmReturnFlds.VM_POWER_STATE);
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

	private  String vmPowerOperation(String vmId,String operation) throws RollbackableBizException {
		CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(vmId);
		String vmName = cmDeviceVMShowBo.getVm_name();
		String hostId = cmDeviceVMShowBo.getHostId();
		String vcenterUserName = "";
		String vcenterPwd = "";
		String dcId = "";
		String vcHostIP = "";
		
		String result = "";
		/*boolean flag = checkResource.checkVmInVc(vmId);
		log.info("uuid flag="+flag);
		if(!flag){
			return result = "vCenter中不存在当前虚机！";
		}*/
		
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> l = (List<HashMap<String, Object>>) cmHostDAO
				.findHostCpuCdpInfo(hostId);
		if (l.isEmpty() || l.size() == 0) {
		} else {
			HashMap<String, Object> h = l.get(0);
			String vcId = (String) h.get("vcId");
			List<RmVmManageServerPo> list = rmVmMgServerDAO.findByID(
					"findRmVmManagerServerPo", vcId);
			if (list.isEmpty() || list.size() == 0) {
				throw new RollbackableBizException("get vm manager info error!");
			} else {
				RmVmManageServerPo vmManagerServerPo = list.get(0);
				dcId = vmManagerServerPo.getDatacenterId();
				vcHostIP = vmManagerServerPo.getManageIp();
				vcenterUserName = vmManagerServerPo.getUserName();
				CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(
						vcId, vcenterUserName);
				vcenterPwd = pwpo.getPassword();
				if (StringUtils.isBlank(vcenterPwd))
					throw new RollbackableBizException("获取ManagerServer["
							+ vmManagerServerPo.getServerName()
							+ "] password is null");
				vcenterPwd = PwdUtil.decryption(vcenterPwd);
			}
		}
		// 日志，调试需要
		String loginfo = "vc用户名:" + vcenterUserName + ";" + "vc密码:"
				+ vcenterPwd + ";" + "虚拟机名称:" + vmName;
		log.debug(loginfo);
		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperation(VMOpration.VMPOWER_OPS);
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());

			BodyDO body = BodyDO.CreateBodyDO();
			body.set(VMFlds.VCENTER_URL,
					CloudClusterConstants.VCENTER_URL_HTTPS + vcHostIP
							+ CloudClusterConstants.VCENTER_URL_SDK);
			body.set(VMFlds.VCENTER_USERNAME, vcenterUserName);
			body.set(VMFlds.VCENTER_PASSWORD, vcenterPwd);
			List<String> list = new ArrayList<String>();
			list.add(vmName);
			body.setList(VMFlds.VAPP_NAME, list);
			body.set(VMFlds.VM_POWER_OPER_TYPE, operation);

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
					String runningState = "unknown";
					if(VmGlobalConstants.VM_REBOOT.equals(operation) || 
							VmGlobalConstants.VM_POWERON.equals(operation)){
						runningState = "poweron";
					}else if(VmGlobalConstants.VM_SHUTDOWN.equals(operation)){
						runningState = "poweroff";
					}else if("suspend".equals(operation)){
						runningState = "paused";
					}
					CmDevicePo vmVo = new CmDevicePo();
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

	private  String vmSnapshotOperation(String snapshotName,String vmId,String vmName,String operation,String snapshotMemory,String snapshotSilence) throws RollbackableBizException {
		String result = "";
		try {
			List<RmVmManageServerPo> rmVmList =null;
			String vcHostIP = "";
			String dcId = "";
			String esxiUserName = "";
			String esxiPwd = "";
			CmVmPo vmInfo = cmVMDao.findCmVmById(vmId);
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) cmHostDAO.findHostCpuCdpInfo(vmInfo.getHostId());
			if(!list.isEmpty() || list.size() != 0){
				String vcId = list.get(0).get("vcId").toString();
				rmVmList = rmVmMgServerDAO.findByID("findRmVmManagerServerPo", vcId);
				if(!rmVmList.isEmpty() || rmVmList.size() != 0){
					  RmVmManageServerPo vmManagerServerPo = rmVmList.get(0);
					  dcId = vmManagerServerPo.getDatacenterId();
					  vcHostIP = vmManagerServerPo.getManageIp();
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
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());

			// header.setOperation(VMOpration.ADD_NAS);
			header.setOperationBean("vmSnapshotServiceImpl");


			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(VMFlds.ESXI_URL, CloudClusterConstants.VCENTER_URL_HTTPS + vcHostIP + CloudClusterConstants.VCENTER_URL_SDK);
			body.setString(VMFlds.ESXI_USERNAME, esxiUserName);
			body.setString(VMFlds.ESXI_PASSWORD, esxiPwd); 
			body.setString(VMFlds.VAPP_NAME, vmName); 
		    body.setString("VM_SNAPSHOT_NAME", snapshotName); 
			body.setBoolean(GeneralKeyField.VM.VM_SNAP_MEMORY, Boolean.parseBoolean(snapshotMemory)); 
			body.setBoolean(GeneralKeyField.VM.VM_SNAP_SILENCE, Boolean.parseBoolean(snapshotSilence)); 
		    body.setString("OPERATION", operation); 

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
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> vmPoList,String virtualTypeCode) throws Exception {
		@SuppressWarnings("unchecked")
		List<ScanResult> scanResultList = new ArrayList<ScanResult>();
		CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(vmPoList.get(0).getId());
		String hostId = cmDeviceVMShowBo.getHostId();
		String dcId = "";
		String vcHostIP = "";
		String vcenterUserName = "";
		String vcenterPwd = "";
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> l = (List<HashMap<String, Object>>) cmHostDAO
				.findHostCpuCdpInfo(hostId);
		if (l.isEmpty() || l.size() == 0) {
		} else {

			HashMap<String, Object> h = l.get(0);
			String vcId = (String) h.get("vcId");
			List<RmVmManageServerPo> list = rmVmMgServerDAO.findByID(
					"findRmVmManagerServerPo", vcId);
			if (list.isEmpty() || list.size() == 0) {
				throw new RollbackableBizException("get vm manager info error!");
			} else {
				RmVmManageServerPo vmManagerServerPo = list.get(0);
				dcId = vmManagerServerPo.getDatacenterId();
				vcHostIP = vmManagerServerPo.getManageIp();
				vcenterUserName = vmManagerServerPo.getUserName();
				CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(
						vcId, vcenterUserName);
				vcenterPwd = pwpo.getPassword();
				if (StringUtils.isBlank(vcenterPwd))
					throw new RollbackableBizException("获取ManagerServer["
							+ vmManagerServerPo.getServerName()
							+ "] password is null");
				vcenterPwd = PwdUtil.decryption(vcenterPwd);
			}
		
		}
		String result = "";
		try {
			
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperation(VMOpration.QUERY_POWER_STATE);
			header.setOperationBean("queryVmPowerStateServiceImpl");
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			BodyDO body = BodyDO.CreateBodyDO();
			body.set(GeneralKeyField.VMware.VCENTER_URL,CloudClusterConstants.VCENTER_URL_HTTPS + vcHostIP+ CloudClusterConstants.VCENTER_URL_SDK);
			body.set(GeneralKeyField.VMware.VCENTER_USERNAME, vcenterUserName);
			body.set(GeneralKeyField.VMware.VCENTER_PASSWORD, vcenterPwd);
			List<String> vmNameList = new ArrayList<String>();
			for(CmDevicePo li : vmPoList){
				vmNameList.add(li.getDeviceName());
			}
			body.setList(GeneralKeyField.VM.VAPP_NAME, vmNameList);
			
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
					Map<String,String> resultMap = rspBody.getHashMap(VmReturnFlds.VM_POWER_STATE);
					for (CmDevicePo vmVo : vmPoList) {
						ScanResult sr = new ScanResult();
						String state = resultMap.get(vmVo.getDeviceName());
						vmVo.setRunningState(state);
						iCmDeviceService.updateCmdeviceRunningState(vmVo);
						sr.setDeviceStatus(state);
						sr.setDeviceId(vmVo.getId());
						sr.setDeviceName(vmVo.getDeviceName());
						scanResultList.add(sr);
					}
				}else{
					result = "fail";
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			logger.error("异常exception",e);
			Utils.printExceptionStack(e);
		}
		return scanResultList;
	}

	private String createIscsiInterface(String vcHostIP, String vcHostUserName, String vcHostPwd, String hostMgrIp,String  name,String path,String  mgrIp,String dcId){
		String result = "";
		// 日志，调试需要
		String loginfo = "vc用户名:" + vcHostUserName + ";" + "vc密码:" + vcHostPwd;
		log.debug(loginfo);

		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperationBean("vmiSCSIServiceImpl");

			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());

			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(GeneralKeyField.VMware.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS
					+ vcHostIP + CloudClusterConstants.VCENTER_URL_SDK);
			body.setString(GeneralKeyField.VMware.VCENTER_USERNAME, vcHostUserName);
			body.setString(GeneralKeyField.VMware.VCENTER_PASSWORD, vcHostPwd);
			body.setString(GeneralKeyField.VMware.ESXI_HOST_NAME, hostMgrIp);
			body.setString(GeneralKeyField.Storage.ISCSI_TARGET_HOST, mgrIp);
			body.setString(GeneralKeyField.Storage.ISCSI_TARGET_GUID, path);
			body.setString(GeneralKeyField.Storage.DATASTORE_NAME, name);
			body.setString(GeneralKeyField.Storage.ISCSI_OPER_TYPE,GeneralValueField. StorageOperation.ADD_ISCSI.getValue());
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
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;

	
	}
	/**
	 * 
	 * @param vcHostIP ,VCenter URL
	 * @param vcHostUserName VCenter ,用户名
	 * @param vcHostPwd ,VCenter 密码
	 * @param hostMgrIp ,物理机IP
	 * @param sanName ,存储别名
	 * @param String dcId,数据中心id
	 * @param identifier 标识符
	 * @return
	 */
	private String createSanInterface(String vcHostIP, String vcHostUserName,String vcHostPwd,String hostMgrIp,String sanName,String identifier,String dcId){
		String result = "";
		// 日志，调试需要
		String loginfo = "vc用户名:" + vcHostUserName + ";" + "vc密码:" + vcHostPwd;
		log.debug(loginfo);

		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperationBean("vmFcSanServiceImpl");
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());

			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(GeneralKeyField.VMware.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS
					+ vcHostIP + CloudClusterConstants.VCENTER_URL_SDK);
			body.setString(GeneralKeyField.VMware.VCENTER_USERNAME, vcHostUserName);
			body.setString(GeneralKeyField.VMware.VCENTER_PASSWORD, vcHostPwd);
			body.setString(GeneralKeyField.VMware.ESXI_HOST_NAME, hostMgrIp);
			body.setString(GeneralKeyField.Storage.DATASTORE_NAME, sanName);
			body.setString(GeneralKeyField.Storage.FCSAN_DEVICE_ID, identifier);
			body.setString(GeneralKeyField.Storage.FCSAN_OPER_TYPE, GeneralValueField.StorageOperation.ADD_FCSAN_WITH_HARDWAREFCoE.getValue());
			
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
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;

	
	}
	/**
	 * @param String dcId
	 * @param vcHostIP,VCenter URL
	 * @param vcHostUserName，VCenter 用户名
	 * @param vcHostPwd，VCenter 密码
	 * @param hostMgrIp，物理机IP
	 * @param name，Nas存储别名
	 * @param path，Nas存储路径
	 * @param mgrIp，Nas存储IP  
	 * @param dcId，数据中心id
	 * @return
	 */
	public String createNasInterface(String vcHostIP, String vcHostUserName,
			String vcHostPwd, String hostMgrIp, String name, String path,
			String mgrIp, String dcId) throws Exception{

		String result = "";
		// 日志，调试需要
		String loginfo = "vc用户名:" + vcHostUserName + ";" + "vc密码:" + vcHostPwd;
		log.debug(loginfo);

		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperation(VMOpration.ADD_NAS);

			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());

			BodyDO body = BodyDO.CreateBodyDO();
			List<Map<String, String>> dsList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put(VMFlds.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS
					+ vcHostIP + CloudClusterConstants.VCENTER_URL_SDK);
			map.put(VMFlds.VCENTER_USERNAME, vcHostUserName);
			map.put(VMFlds.VCENTER_PASSWORD, vcHostPwd);
			map.put(VMFlds.HOST_NAME, hostMgrIp);
			map.put(VMFlds.NAS_HOST_REMOTE_IP, mgrIp);
			map.put(VMFlds.NAS_REMOTE_PATH, path);
			map.put(VMFlds.NAS_LOCAL_PATH, name);
			dsList.add(map);
			body.setList(VMFlds.NAS_ADD_RECS, dsList);

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
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;

	}

	public String removeNasDataStore(String hostMgrIp, String vcHostUserName, String hostPwd, String path, String dcId,String vcHostIP)
			throws RollbackableBizException {
		String result = "";
		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());

			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			header.setOperationBean("removeNasDataStoreServiceImpl");
			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(VMFlds.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS + vcHostIP
					+ CloudClusterConstants.VCENTER_URL_SDK);
			body.setString(VMFlds.VCENTER_USERNAME, vcHostUserName);
			body.setString(VMFlds.VCENTER_PASSWORD, hostPwd);
			body.setString(VMFlds.HOST_NAME, hostMgrIp);
			body.setString(VMFlds.NAS_LOCAL_PATH, path);
			
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
	public String removeSanDatastore(String hostMgrIp,String vcHostUserName,String vcHostPwd,String datastoreName, String dcId,String vcHostIP){

		String result = "";
		// 日志，调试需要
		String loginfo = "vc用户名:" + vcHostUserName + ";" + "vc密码:" + vcHostPwd;
		log.debug(loginfo);

		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperationBean("vmFcSanServiceImpl");
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());

			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(GeneralKeyField.VMware.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS
					+ vcHostIP + CloudClusterConstants.VCENTER_URL_SDK);
			body.setString(GeneralKeyField.VMware.VCENTER_USERNAME, vcHostUserName);
			body.setString(GeneralKeyField.VMware.VCENTER_PASSWORD, vcHostPwd);
			body.setString(GeneralKeyField.VMware.ESXI_HOST_NAME, hostMgrIp);
			body.setString(GeneralKeyField.Storage.DATASTORE_NAME, datastoreName);
			body.setString(GeneralKeyField.Storage.FCSAN_OPER_TYPE, GeneralValueField.StorageOperation.REMOVE_FCSAN.getValue());
			
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
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	}
	private String removeIscsiDatastore(String hostMgrIp,String vcHostUserName,String vcHostPwd,String datastoreName, String dcId,String vcHostIP){
		String result = "";
		// 日志，调试需要
		String loginfo = "vc用户名:" + vcHostUserName + ";" + "vc密码:" + vcHostPwd;
		log.debug(loginfo);

		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperationBean("vmiSCSIServiceImpl");
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			
			BodyDO body = BodyDO.CreateBodyDO();
			body.setString(GeneralKeyField.VMware.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS
					+ vcHostIP + CloudClusterConstants.VCENTER_URL_SDK);
			body.setString(GeneralKeyField.VMware.VCENTER_USERNAME, vcHostUserName);
			body.setString(GeneralKeyField.VMware.VCENTER_PASSWORD, vcHostPwd);
			body.setString(GeneralKeyField.VMware.ESXI_HOST_NAME, hostMgrIp);
			body.setString(GeneralKeyField.Storage.DATASTORE_NAME, datastoreName);
			body.setString(GeneralKeyField.Storage.ISCSI_OPER_TYPE,GeneralValueField. StorageOperation.REMOVE_ISCSI.getValue());
			
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
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	
	}
}