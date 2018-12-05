package com.git.cloud.resmgt.compute.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.enums.OperationType;
import com.git.cloud.common.enums.ResourceType;
import com.git.cloud.common.enums.Source;
import com.git.cloud.common.enums.Type;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.model.comparator.PmHandlerResult;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMOpration;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.general.field.GeneralValueField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

public class PmHandlerServiceImpl implements PmHandlerService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ICmDeviceService iCmDeviceService;
	private IRmDatacenterDAO rmDatacenterDAO;
	private INotificationService notiServiceImpl;
	
	public INotificationService getNotiServiceImpl() {
		return notiServiceImpl;
	}
	public void setNotiServiceImpl(INotificationService notiServiceImpl) {
		this.notiServiceImpl = notiServiceImpl;
	}
	public IRmDatacenterDAO getRmDatacenterDAO() {
		return rmDatacenterDAO;
	}
	public void setRmDatacenterDAO(IRmDatacenterDAO rmDatacenterDAO) {
		this.rmDatacenterDAO = rmDatacenterDAO;
	}
	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}
	@Autowired
	private ResAdptInvokerFactory resInvokerFactory;
	@Autowired
	private IRmVmManageServerDAO rmVmMgServerDAO;
	@Autowired
	private ICmHostDAO cmHostDAO;
	@Autowired
	private ICmPasswordDAO cmPasswordDAO;
	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}
	@Override
	public String pmRunningState(String pmId) throws RollbackableBizException{
		String result = "";
		CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(pmId);
		String ipmi_ip = "";
		String ipmi_ver = "";
		 String ipmi_Name = "";
		 String ipmi_Pword = "";
		 if(cmDeviceHostShowBo.getIpmiPwd()!=null){
			 ipmi_Pword=cmDeviceHostShowBo.getIpmiPwd();
		 }
		 if(cmDeviceHostShowBo.getIpmiUser()!=null){
			 ipmi_Name=cmDeviceHostShowBo.getIpmiUser();
		 }
		 if(cmDeviceHostShowBo.getIpmiVer()!=null){
			 ipmi_ver=cmDeviceHostShowBo.getIpmiVer();
			 if(ipmi_ver.equals("2.0")){
				 ipmi_ver = GeneralValueField.IPMI.IPMI_VERSION_V20.getValue();
			 }else if(ipmi_ver.equals("1.5")){
				 ipmi_ver = GeneralValueField.IPMI.IPMI_VERSION_V15.getValue();
			 }
		 }
		 if(cmDeviceHostShowBo.getIpmiUrl()!=null){
			 ipmi_ip=cmDeviceHostShowBo.getIpmiUrl();
			 int pnumber1 = ipmi_ip.indexOf(":");
			 if(pnumber1 != -1){
				 ipmi_ip = ipmi_ip.substring(pnumber1+3);
				 int pnumber2 = ipmi_ip.indexOf(":");
				 int pnumber3 = ipmi_ip.indexOf("/");
				 if(pnumber3 !=-1){
					 ipmi_ip = ipmi_ip.substring(0, pnumber3);
				 }
				 if(pnumber2!=-1){
					 ipmi_ip = ipmi_ip.substring(0, pnumber2);
				 }
			 }
		 }
		//物理机 IP集合
		List<String> ipList = new ArrayList<String>();		
		ipList.add(ipmi_ip);
		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(pmId).getQueueIden());
			header.setOperationBean("PMPowerOps");
			
			BodyDO body = BodyDO.CreateBodyDO();			
			body.setList(GeneralKeyField.IPMI.IPMI_IP, ipList);	
			body.set(GeneralKeyField.IPMI.IPMI_VERSION, ipmi_ver);
			body.set(GeneralKeyField.IPMI.USER_NAME, ipmi_Name);
			body.set(GeneralKeyField.IPMI.PASSWORD, ipmi_Pword);
			body.set(GeneralKeyField.IPMI.POWER_OPER_TYPE, GeneralValueField.PowerOperation.STATE.getValue());			

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
					result = (String) resultDataObject.get(ipmi_ip);
				}else{
					result = "fail";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		if(result.equals("fail")){
			result="未知";
		}else if(result.equals("on")){
			result="开机";
		}else if(result.equals("off")){
			result="关机";
		}
		return result;
	}
	@Override
	public List<String[]> getPmSensorInfo(String id,String pmIp,String ipmi_ver,String ipmi_Name,String ipmi_Pword) throws Exception {
		List<String[]> list =new ArrayList<String[]>();
		
		String result ="";
		 String infoType ="";
			 if(ipmi_ver.equals("2.0")){
				 ipmi_ver = GeneralValueField.IPMI.IPMI_VERSION_V20.getValue();
			 }else if(ipmi_ver.equals("1.5")){
				 ipmi_ver = GeneralValueField.IPMI.IPMI_VERSION_V15.getValue();
			 }
			 int pnumber1 = pmIp.indexOf(":");				//	:  :   /
			 if(pnumber1 != -1){
				 pmIp = pmIp.substring(pnumber1+3);
				 int pnumber2 = pmIp.indexOf(":");
				 int pnumber3 = pmIp.indexOf("/");
				 if(pnumber3 !=-1){
					 pmIp = pmIp.substring(0, pnumber3);
				 }
				 if(pnumber2!=-1){
					 pmIp = pmIp.substring(0, pnumber2);
				 }
			 }
			 List<String> ipList = new ArrayList<String>();		
			ipList.add(pmIp);
			for(int i=0;i<3;){
				if(i==0){
					infoType= GeneralValueField.HardwareInfoType.RAM_TEMP.getValue();
				} if(i==1){
					infoType= GeneralValueField.HardwareInfoType.CPU_TEMP.getValue();
				}if(i==2){
					infoType= GeneralValueField.HardwareInfoType.FAN.getValue();
				}
				i++;
				try {
					HeaderDO header = HeaderDO.CreateHeaderDO();
					header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
					header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
					header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(id).getQueueIden());
					header.setOperationBean("PMSdrInfo");
					
					BodyDO body = BodyDO.CreateBodyDO();			
					body.setList(GeneralKeyField.IPMI.IPMI_IP, ipList);	
					body.set(GeneralKeyField.IPMI.IPMI_VERSION, ipmi_ver);
					body.set(GeneralKeyField.IPMI.USER_NAME, ipmi_Name);
					body.set(GeneralKeyField.IPMI.PASSWORD, ipmi_Pword);
					body.set(GeneralKeyField.IPMI.SDR_INFO_TYPE, infoType);			

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
						DataObject dataObject=(DataObject)rspBody.getDataObject(pmIp);
						for(String nameString : dataObject.getContianer().keySet()){
							String valueString =  dataObject.getString(nameString);
							String an[] = new String[2];
							an[0] = nameString;
							an[1] = valueString;
							list.add(an);
						}
					}
				} catch (Exception e) {
					result = e.getMessage();
					logger.error("异常exception",e);
				}
			}
			/*	对扫描结果进行排序	*/
			PmHandlerResult comparator = new PmHandlerResult();
			Collections.sort(list, comparator);
			return list;
	}

	@Override
	public String pmCloseByVc(String pmId) throws Exception {
		String result = this.pmPowerOperationByVc(pmId,GeneralValueField.PowerOperation.SHUTDOWN.getValue());
		if(result.equals("success")){
			CmDevicePo host = new CmDevicePo();
			host.setId(pmId);
			host.setRunningState("");
			iCmDeviceService.updateCmdeviceRunningState(host);
			this.insertNoti(pmId, OperationType.PMSHUTDOWN.getValue());
		}
		return result;
	}
	@Override
	public String pmRebootByVc(String pmId) throws Exception {
		String result =  this.pmPowerOperationByVc(pmId,GeneralValueField.PowerOperation.REBOOT.getValue());
		if(result.equals("success")){
			this.insertNoti(pmId, OperationType.RESTART.getValue());
		}
		return result;
	}
	@Override
	public String pmPowerOperationByVc(String pmId, String operation)throws Exception {
		String vcenterUserName = "";
		String vcenterPwd = "";
		String dcId = "";
		String vcHostIP = "";
		String pmIp=iCmDeviceService.findHostIpById(pmId);
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> l = (List<HashMap<String, Object>>) cmHostDAO
				.findHostCpuCdpInfo(pmId);
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
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			header.setOperationBean("esxiPowerOps");
			BodyDO body = BodyDO.CreateBodyDO();
			body.set(GeneralKeyField.VMware.VCENTER_URL,CloudClusterConstants.VCENTER_URL_HTTPS + vcHostIP+ CloudClusterConstants.VCENTER_URL_SDK);
			body.set(GeneralKeyField.VMware.VCENTER_USERNAME, vcenterUserName);
			body.set(GeneralKeyField.VMware.VCENTER_PASSWORD, vcenterPwd);
			body.set(GeneralKeyField.VMware.ESXI_HOST_NAME, pmIp);
			body.set(GeneralKeyField.VMware.ESXI_POWER_OPER_TYPE, operation);

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
//				NotificationPo notiPo = new NotificationPo();
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
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
	@Override
	public List<String[]> getPmSensorInfoById(String hostId) throws Exception {
		CmDeviceHostShowBo cmDeviceHostShowBo = iCmDeviceService.getCmDeviceHostInfo(hostId);
		 String ipmi_ver = cmDeviceHostShowBo.getIpmiVer();
		 String ipmi_Name = cmDeviceHostShowBo.getIpmiUser();
		 String ipmi_Pword = cmDeviceHostShowBo.getIpmiPwd();
		 String pmIp=cmDeviceHostShowBo.getIpmiUrl();
		 if(ipmi_ver.equals("2.0")){
			 ipmi_ver = GeneralValueField.IPMI.IPMI_VERSION_V20.getValue();
		 }else if(ipmi_ver.equals("1.5")){
			 ipmi_ver = GeneralValueField.IPMI.IPMI_VERSION_V15.getValue();
		 }
		List<String[]> list =new ArrayList<String[]>();
		
		String result ="";
		 String infoType ="";
			 if(ipmi_ver.equals("2.0")){
				 ipmi_ver = GeneralValueField.IPMI.IPMI_VERSION_V20.getValue();
			 }else if(ipmi_ver.equals("1.5")){
				 ipmi_ver = GeneralValueField.IPMI.IPMI_VERSION_V15.getValue();
			 }
			 int pnumber1 = pmIp.indexOf(":");
			 if(pnumber1 != -1){
				 pmIp = pmIp.substring(pnumber1+3);
				 int pnumber2 = pmIp.indexOf(":");
				 int pnumber3 = pmIp.indexOf("/");
				 if(pnumber3 !=-1){
					 pmIp = pmIp.substring(0, pnumber3);
				 }
				 if(pnumber2!=-1){
					 pmIp = pmIp.substring(0, pnumber2);
				 }
			 }
			 List<String> ipList = new ArrayList<String>();		
			ipList.add(pmIp);
			for(int i=0;i<3;){
				if(i==0){
					infoType= GeneralValueField.HardwareInfoType.RAM_TEMP.getValue();
				} if(i==1){
					infoType= GeneralValueField.HardwareInfoType.CPU_TEMP.getValue();
				}if(i==2){
					infoType= GeneralValueField.HardwareInfoType.FAN.getValue();
				}
				i++;
				try {
					HeaderDO header = HeaderDO.CreateHeaderDO();
					header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
					header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
					header.set("DATACENTER_QUEUE_IDEN", rmDatacenterDAO.getDataCenterByHostId(hostId).getQueueIden());
					header.setOperationBean("PMSdrInfo");
					
					BodyDO body = BodyDO.CreateBodyDO();			
					body.setList(GeneralKeyField.IPMI.IPMI_IP, ipList);	
					body.set(GeneralKeyField.IPMI.IPMI_VERSION, ipmi_ver);
					body.set(GeneralKeyField.IPMI.USER_NAME, ipmi_Name);
					body.set(GeneralKeyField.IPMI.PASSWORD, ipmi_Pword);
					body.set(GeneralKeyField.IPMI.SDR_INFO_TYPE, infoType);			

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
						DataObject dataObject=(DataObject)rspBody.getDataObject(pmIp);
						for(String nameString : dataObject.getContianer().keySet()){
							String valueString =  dataObject.getString(nameString);
							String an[] = new String[2];
							an[0] = nameString;
							an[1] = valueString;
							list.add(an);
						}
					}
				} catch (Exception e) {
					result = e.getMessage();
					logger.error("异常exception",e);
				}
			}
			/*	对扫描结果进行排序	*/
			PmHandlerResult comparator = new PmHandlerResult();
			Collections.sort(list, comparator);
			return list;
	}
	@Override
	public String maintenanceMode(String pmId) throws Exception {
		String result = this.pmPowerOperationByVc(pmId,GeneralValueField.PowerOperation.ENTER_MAINTENCE_MODE.getValue());
		if(result.equals("success")){
			CmDevicePo host = new CmDevicePo();
			host.setId(pmId);
			host.setRunningState(OperationType.ENTER_MAINTENANCE.getValue());
			iCmDeviceService.updateCmdeviceRunningState(host);
			this.insertNoti(pmId, OperationType.ENTER_MAINTENANCE.getValue());
		}
		return result;
		
	}
	@Override
	public String exitMaintenanceMode(String pmId) throws Exception {
		String result = this.pmPowerOperationByVc(pmId,GeneralValueField.PowerOperation.EXIT_MAINTENCE_MODE.getValue());
		if(result.equals("success")){
			CmDevicePo host = new CmDevicePo();
			host.setId(pmId);
			host.setRunningState(OperationType.EXIT_MAINTENANCE.getValue());
			iCmDeviceService.updateCmdeviceRunningState(host);
			this.insertNoti(pmId, OperationType.EXIT_MAINTENANCE.getValue());
		}
		return result;
	}
	@SuppressWarnings("unused")//写入通知表
	private void insertNoti(String hostId,String operationType) throws RollbackableBizException{
		NotificationPo notiPo = new NotificationPo();
		notiPo.setOperationType(operationType);
		notiPo.setResourceId(hostId);
		notiPo.setResourceType(ResourceType.PHYSICAL.getValue());
		notiPo.setSource(Source.MANUALOPERATION.getValue());
		notiPo.setType(Type.TIP.getValue());
		notiServiceImpl.insertNotification(notiPo);
	}
}
