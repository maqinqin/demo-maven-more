package com.git.cloud.resmgt.compute.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.ScanResult;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.model.vo.VmMonitorVo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VmGlobalConstants;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

public class VmControllerServicePOWERVMImpl implements VmControllerService{
	private static Logger log = LoggerFactory.getLogger(VmControllerServicePOWERVMImpl.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	
	
	public IIpAllocToDeviceNewService getiIpAllocToDeviceService() {
		return iIpAllocToDeviceService;
	}

	public void setiIpAllocToDeviceService(
			IIpAllocToDeviceNewService iIpAllocToDeviceService) {
		this.iIpAllocToDeviceService = iIpAllocToDeviceService;
	}

	public ICmDeviceDAO getIcmDeviceDAO() {
		return icmDeviceDAO;
	}

	public void setIcmDeviceDAO(ICmDeviceDAO icmDeviceDAO) {
		this.icmDeviceDAO = icmDeviceDAO;
	}

	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}
	
	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String vmRunningState(String vmId) throws Exception {
		CmVmPo vmInfo = iCmDeviceService.findPowerInfoByVmId(vmId);
		if(vmInfo == null){
			return null;
		}
		//遍历扫描所需信息，并进行查询
		String hostId = vmInfo.getHostId();
		String url = vmInfo.getManageIp();
		String username = vmInfo.getUserName();
		String password = vmInfo.getPassword();
		if (StringUtils.isBlank(password))
			throw new RollbackableBizException("获取HOST [" + username + "] password is null");
		password = PwdUtil.decryption(password);
		String hostName = vmInfo.getHostName();
		List<String> vmNameList = new ArrayList<String>();
		String vmName = vmInfo.getLparName();
		String loginfo = "虚拟机名称:" + vmName;
		log.debug(loginfo);
		vmNameList.add(vmName);
		String result = "";
		try {
			//header
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setOperationBean("scanVMState");
			header.setResourceClass(EnumResouseHeader.PV_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.PV_RES_TYPE.getValue());
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			System.out.println("rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden():  "+rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			//body
			BodyDO body = BodyDO.CreateBodyDO();			
			body.set(GeneralKeyField.Power.URL, url);		
			body.set(GeneralKeyField.Power.USERNAME, username);	
			body.set(GeneralKeyField.Power.PASSWORD, password);	
			body.set(GeneralKeyField.Power.HOST_NAME, hostName);	
			body.set(GeneralKeyField.VM.VAPP_NAME, vmNameList);	
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
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,	HeaderDO.class);
				BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY,BodyDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					DataObject powerState = (DataObject) rspBody.get("POWER_STATE");
					List<Map> vmStateList =  (List<Map>) powerState.get(hostName);
					result = (String) vmStateList.get(0).get(vmName);
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
	//执行开机、关机、挂起、重启
	private  String vmPowerOperation(String vmId,String operation) throws RollbackableBizException{
		//CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(vmId);
		CmVmPo vmInfo = iCmDeviceService.findPowerInfoByVmId(vmId);
		//String vmName = cmDeviceVMShowBo.getVm_name();
		String hostId = vmInfo.getHostId();
		String vmName =vmInfo.getLparName();
		String hostName=vmInfo.getHostName();
		String userName = vmInfo.getUserName();
		String powerPwd = vmInfo.getPassword();
		powerPwd = PwdUtil.decryption(powerPwd);
		String ip = vmInfo.getManageIp();
	//	String vcHostIP = "";
				String result = "";
				try {
					String loginfo = "虚拟机名称:" + vmName;
					log.debug(loginfo);
					//header
					HeaderDO header = HeaderDO.CreateHeaderDO();
					header.setResourceClass(EnumResouseHeader.PV_RES_CLASS.getValue());
					header.setResourceType(EnumResouseHeader.PV_RES_TYPE.getValue());
					header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
					header.setOperationBean("vmPowerManagerImpl");
					//body
					BodyDO body = BodyDO.CreateBodyDO();
					body.set(GeneralKeyField.Power.URL, ip);
					body.set(GeneralKeyField.Power.USERNAME, userName);
					body.set(GeneralKeyField.Power.PASSWORD, powerPwd);
					body.set(GeneralKeyField.Power.HOST_NAME,hostName);
					body.set(GeneralKeyField.VM.VAPP_NAME, vmName);
					body.set(GeneralKeyField.VM.POWER_OPER_TYPE, operation);	
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
						if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
							String runningState = "unknown";
							if("poweron".equals(operation)){
								runningState = "poweron";
							}else if("shutdown".equals(operation)){
								runningState = "poweroff";
							}
							CmDevicePo vmVo = new CmDevicePo();
							vmVo.setId(vmId);
							vmVo.setRunningState(runningState);
							iCmDeviceService.updateCmdeviceRunningState(vmVo);
							result = "success";
							
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

	private String moveVM(String sourceCIp, String targetCIp, String vmId,
			String dataStoreName,String info) throws Exception {
		CmVmPo vmInfo = iCmDeviceService.findPowerInfoByVmId(vmId);
		if(vmInfo == null){
			return null;
		}
		//遍历扫描所需信息，并进行查询
		String hostId = vmInfo.getHostId();
		String url = vmInfo.getManageIp();
		String username = vmInfo.getUserName();
		String password = vmInfo.getPassword();
		if (StringUtils.isBlank(password))
			throw new RollbackableBizException("获取HOST [" + username + "] password is null");
		password = PwdUtil.decryption(password);
		String hostName = vmInfo.getHostName();
		String vmName = vmInfo.getLparName();
		String loginfo = "虚拟机名称:" + vmName;
		log.debug(loginfo);
		String result = "";
		
		String[] infoList = info.split(";");
		String tarHostId = infoList[0];
		//目标lpar_id   
		String dest_lpar_id = infoList[1];
		//npiv光纤卡对应关系    
		String virtual_fc_mappings = infoList[2];
		//vscsi光纤卡对应关系
		String virtual_scsi_mappings = infoList[3];
		//源vois lparID
		String source_msp_id = infoList[4];
		//目标vois lparID
		String dest_msp_id = infoList[5];
		
		CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(tarHostId);
		String destHostName = cmDeviceHostShowBo.getDevice_name();
		try {
			//header
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setOperationBean("migratePowerLparImpl");
			header.setResourceClass(EnumResouseHeader.PV_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.PV_RES_TYPE.getValue());
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			System.out.println("rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden():  "+rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			//body
			BodyDO body = BodyDO.CreateBodyDO();			
			body.set(GeneralKeyField.Power.URL, url);		
			body.set(GeneralKeyField.Power.USERNAME, username);	
			body.set(GeneralKeyField.Power.PASSWORD, password);	
			//源物理主机名称    源lpar所在物理机
			body.set(GeneralKeyField.Power.HOST_NAME, hostName);
			//虚拟机名称   源lpar名字
			body.set(GeneralKeyField.VM.VAPP_NAME, vmName);
			body.set(GeneralKeyField.Power.DEST_LPAR_ID,dest_lpar_id);
			body.set(GeneralKeyField.Power.VIRTUAL_FC_MAPPINGS, virtual_fc_mappings);
			body.set(GeneralKeyField.Power.VIRTUAL_SCSI_MAPPINGS, virtual_scsi_mappings);
			body.set("source_msp_id", source_msp_id);
			body.set("dest_msp_id", dest_msp_id);
			body.set(GeneralKeyField.Power.DEST_HOST_NAME, destHostName);
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
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,	HeaderDO.class);
				BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY,BodyDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					result = "success";
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

	@SuppressWarnings("unchecked")
	@Override
	public List<ScanResult> scanAndUpdateVmIndicator(List<CmDevicePo> vmPoList,	String virtualTypeCode) throws Exception {
		
		String vmName = null;
		String hostId = null;
		String url = null;
		String username = null;
		String password = null;
		String hostName = null;
		List<String> vmNameList = new ArrayList<String>();
		String vmId = null;
		List<ScanResult> resultList = new ArrayList<ScanResult>();

		for (CmDevicePo devicePo : vmPoList) {
			vmName = devicePo.getDeviceName();
			vmId = iCmDeviceService.findVmIdByName(vmName);
			CmVmPo vmInfo = iCmDeviceService.findPowerInfoByVmId(vmId);
			if(vmInfo == null){
				return null;
			}
			//遍历扫描所需信息，并进行查询
			hostId = vmInfo.getHostId();
			url = vmInfo.getManageIp();
			username = vmInfo.getUserName();
			password = vmInfo.getPassword();
			if (StringUtils.isBlank(password))
				throw new RollbackableBizException("获取HOST [" + username + "] password is null");
			password = PwdUtil.decryption(password);
			hostName = vmInfo.getHostName();
			vmName = vmInfo.getLparName(); // 都使用lpar名称
			String loginfo = "虚拟机名称:" + vmName;
			log.debug(loginfo);
			vmNameList.add(vmName);
		}
		
		try {
			//header
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setOperationBean("scanVMState");
			header.setResourceClass(EnumResouseHeader.PV_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.PV_RES_TYPE.getValue());
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
			//body
			BodyDO body = BodyDO.CreateBodyDO();			
			body.set(GeneralKeyField.Power.URL, url);		
			body.set(GeneralKeyField.Power.USERNAME, username);	
			body.set(GeneralKeyField.Power.PASSWORD, password);	
			body.set(GeneralKeyField.Power.HOST_NAME, hostName);	
			body.set(GeneralKeyField.VM.VAPP_NAME, vmNameList);	
			//reqData
			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);

			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				return null;
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,	HeaderDO.class);
				BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY,BodyDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					
					DataObject hostData = (DataObject) rspBody.get("POWER_STATE");
					DataObject vmData = (DataObject) hostData.get(hostName);
					 for(String vmNme : vmNameList){
						String state = (String) vmData.get(vmNme);
						//更新数据
						CmDevicePo cmDevicePo = new CmDevicePo();
						cmDevicePo.setId(iCmDeviceService.findVmIdByName(vmNme));
						cmDevicePo.setRunningState(state);
						iCmDeviceService.updateCmdeviceRunningState(cmDevicePo);
						//返回数据
						ScanResult sr = new ScanResult();
						sr.setDeviceStatus(state);
						sr.setDeviceId(hostId);
						sr.setDeviceName(hostName);
						resultList.add(sr);
					}
				}else{
					resultList = null;
				}
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return resultList;
	}
}