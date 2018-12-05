package com.git.cloud.handler.automation.sa.vmware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.model.vo.PhysicsMachineVo;
import com.git.cloud.resmgt.compute.service.IRmComputeVmListService;
import com.git.cloud.resmgt.network.model.po.RmNwIpAddressPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.common.VmReturnFlds;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

public class VmResourceInfoQueryHandler  extends BaseInstance implements HandlerInstance{
	private static Logger log = LoggerFactory.getLogger(VmResourceInfoQueryHandler.class);
	ICmHostDAO cmHostDao = (ICmHostDAO) WebApplicationManager.getBean("cmHostDAO");
	AutomationService automationService = (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
	ICmDeviceService iCmDeviceService = (ICmDeviceService) WebApplicationManager.getBean("cmDeviceServiceImpl");
	ICmDeviceDAO icmDeviceDao = (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
	IRmVmManageServerDAO rmVmMgServerDAO = (IRmVmManageServerDAO) WebApplicationManager.getBean("rmVmManageServerDAO");
	IRmDatacenterDAO rmDatacenterDao = (IRmDatacenterDAO) WebApplicationManager.getBean("rmDatacenterDAO");
	ICmPasswordDAO cmPasswordDao = (ICmPasswordDAO) WebApplicationManager.getBean("cmPasswordDAO");
	IRmComputeVmListService iRmComputeVmListService = (IRmComputeVmListService) WebApplicationManager.getBean("iRmComputeVmListService");
	ICmVmDAO cmVmDao = (ICmVmDAO)WebApplicationManager.getBean("cmVmDAO");
	@Override
	public String execute(HashMap<String, Object> contenxtParmas) throws Exception {
		String result= MesgRetCode.SUCCESS;
		String rrinfoId = null;
		String srInfoId = null;
		String instanceId = null;
		String nodeId = null;
		String vcHostIp = null;
		String dbHostIp = null;
		CmDeviceVMShowBo vmInfo = new CmDeviceVMShowBo();
		rrinfoId = getContextStringPara(contenxtParmas, RRINFO_ID);
		srInfoId = getContextStringPara(contenxtParmas, SRV_REQ_ID);
		instanceId = getContextStringPara(contenxtParmas, FLOW_INST_ID);
		nodeId = getContextStringPara(contenxtParmas, NODE_ID);
		List<String> devIdList = getDeviceIdList(instanceId, nodeId, rrinfoId, "");
		if(devIdList !=null && devIdList.size()>0){
			for(String vmId : devIdList){
				vmInfo = icmDeviceDao.getCmDeviceHostInfo("selectCmDeviceVMInfo", vmId);
				if(vmInfo !=null  && !vmInfo.getHostId().equals("")){
					dbHostIp = icmDeviceDao.findHostIpById(vmInfo.getHostId());
				}else{
					log.error("通过虚拟机id："+vmId+",未查询到所在物理机id，请检查");
					result=MesgRetCode.ERR_PARAMETER_WRONG;
					throw new Exception("通过虚拟机id："+vmId+",未查询到所在物理机id，请检查");
					
				}
				// 调接口查询虚拟机所在物理机的ip
				vcHostIp = getHostIpByInterface(vmId);
				if(dbHostIp != null && !dbHostIp.equals("")){
					if(vcHostIp !=null && !vcHostIp.equals("")){
						if(!dbHostIp.equals(vcHostIp)){
							// 不相等证明虚拟机漂移
							 PhysicsMachineVo physicsMachineVo = new PhysicsMachineVo();
							 //根据接口获取的物理机ip，查询该设备在数据库中的设备id
							 CmHostVo cmHostVo = cmHostDao.selectHostIdByIp(vcHostIp);
							 //rmNwIpAddrDAO.findByIpAddrs(vcHostIp).get(0);
							 if(!cmHostVo.getId().equals("")){
								 physicsMachineVo.setDevceId(cmHostVo.getId());
							 }else{
								 throw new Exception("通过接口查询出的ip："+vcHostIp+"未在数据库中查询到相关设备记录");
							 }
					         physicsMachineVo.setVid(vmId);
					         iRmComputeVmListService.updateVCMVMId(physicsMachineVo);
							 //更新物理机已用cpu和已用内存
							 cmHostDao.updateCmHostUsed();
						}
					}else{
						log.error("通过虚拟机ID："+vmId+",调用接口未查询到所在物理机ip，请检查");
						result=MesgRetCode.ERR_PARAMETER_WRONG;
						throw new Exception("通过虚拟机ID："+vmId+",调用接口未查询到所在物理机ip，请检查");
					}
				}else{
					log.error("通过物理机ID："+vmInfo.getHostId()+",未在数据库中查询到设备ip，请检查");
					result=MesgRetCode.ERR_PARAMETER_WRONG;
					throw new Exception("通过物理机ID："+vmInfo.getHostId()+",未在数据库中查询到设备ip，请检查");
				}
			}
		}else{
			log.error("获取设备列表失败");
			result=MesgRetCode.ERR_PARAMETER_WRONG;
			throw new Exception(result+"获取设备列表失败");
		} 
		return result;
	}
	
	/**
	 * 调接口查询虚拟机所在物理机的ip
	 * @param vmId
	 * @return
	 * @throws Exception
	 */
	public String getHostIpByInterface(String vmId) throws Exception{
		CmDeviceVMShowBo cmDeviceVMShowBo = iCmDeviceService.getCmDeviceVMInfo(vmId);
		String vmName = cmDeviceVMShowBo.getVm_name();
		String hostId = cmDeviceVMShowBo.getHostId();
		String vcenterUserName = "";
		String vcenterPwd = "";
		String dcId = "";
		String vcHostIP = "";
		String repVmHostIp = "";
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> l = (List<HashMap<String, Object>>) cmHostDao
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
				CmPasswordPo pwpo = cmPasswordDao.findCmPasswordByResourceUser(
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
		// 日志，调试需要
		String loginfo = "vc用户名:" + vcenterUserName + ";" + "vc密码:"
				+ vcenterPwd + ";" + "虚拟机名称:" + vmName;
		log.debug(loginfo);
		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDao.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			header.setOperationBean("queryVmInfoServiceImpl");
			BodyDO body = BodyDO.CreateBodyDO();
			//传递的是vc的用户名、密码、url
			body.set(VMFlds.ESXI_URL,CloudClusterConstants.VCENTER_URL_HTTPS + vcHostIP+ CloudClusterConstants.VCENTER_URL_SDK);
			body.set(VMFlds.ESXI_USERNAME, vcenterUserName);
			body.set(VMFlds.ESXI_PASSWORD, vcenterPwd);
			body.set(VMFlds.VAPP_NAME, vmName);

			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);
			IDataObject rspData = null;
			IResAdptInvoker invoker = getResAdpterInvoker();
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
						HeaderDO.class);
				BodyDO resBody = rspData.getDataObject(MesgFlds.BODY,BodyDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					DataObject objVm = (DataObject) resBody.getDataObject("VM_NAME");
					repVmHostIp = objVm.getString(VmReturnFlds.IP_ADDRESS);
					if(repVmHostIp == null || repVmHostIp.equals("")){
						throw new Exception("查询失败");
					}
					log.debug("通过接口在vc中查询，该设备Ip地址为:"+repVmHostIp);
				} else {
					result = "fail";
				}
			}
		} catch (Exception e) {
			log.error("查询虚拟机ip失败，虚拟机名称："+vmName+",虚拟机ID："+vmId+",查询所在物理机的ip异常",e);
			throw e;
		}
		return repVmHostIp;
	} 
	@Override
	public BaseInstance getInstance() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
