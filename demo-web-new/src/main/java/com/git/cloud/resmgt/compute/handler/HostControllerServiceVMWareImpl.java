package com.git.cloud.resmgt.compute.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.dao.IRmHostDao;
import com.git.cloud.resmgt.compute.model.comparator.ScanVmResultVoComparator;
import com.git.cloud.resmgt.compute.model.vo.CloudServiceVoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.IpObj;
import com.git.cloud.resmgt.compute.model.vo.IpRules;
import com.git.cloud.resmgt.compute.model.vo.ScanVmResultVo;
import com.git.cloud.resmgt.compute.model.vo.VmVo;
import com.git.cloud.resmgt.compute.service.impl.RmHostServiceImpl;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.PwdUtil;

public class HostControllerServiceVMWareImpl implements HostControllerService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private AutomationService automationService;
	private ICmHostDAO cmHostDAO;
	private IRmVmManageServerDAO rmVmMgServerDAO;
	
	private ResAdptInvokerFactory resInvokerFactory;
	
	private IRmDatacenterDAO rmDatacenterDAO;
	@Autowired
	public void setRmDatacenterDAO(IRmDatacenterDAO rmDatacenterDAO) {
		this.rmDatacenterDAO = rmDatacenterDAO;
	}
	public ResAdptInvokerFactory getResInvokerFactory() {
		return resInvokerFactory;
	}

	@Autowired
	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}
	@Override
	public List<ScanVmResultVo> scanVmFromHost(VmVo vm) throws Exception {
		List<ScanVmResultVo> resultList = new ArrayList<ScanVmResultVo>();
		String dcId = "";
		String vcHostIP = "";
		String vcId = "";
		List<HashMap<String, Object>> l = (List<HashMap<String, Object>>) cmHostDAO.findHostCpuCdpInfo(vm.getHostId());
		if (l.isEmpty() || l.size() == 0) {
		} else {
			HashMap<String, Object> h = l.get(0);
			vcId = (String) h.get("vcId");
			List<RmVmManageServerPo> list = rmVmMgServerDAO.findByID("findRmVmManagerServerPo", vcId);
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
			// TODO 获取username password url 方法没找到，
			String esxiUserName = "";
			String esxiPassword = "";
			String esxiIp = "";

			if ("".equals(vcId) || vcId == null) {
				throw new RollbackableBizException("get vcId error!");
			} else {
				List<RmVmManageServerPo> vcenterns = rmVmMgServerDAO.findByID("findRmVCenternInfoByVcId", vcId);
				if (vcenterns.isEmpty() || vcenterns.size() == 0) {
					throw new RollbackableBizException("get vm manager info error!");
				} else {
					RmVmManageServerPo vcenternPo = vcenterns.get(0);
					esxiUserName = vcenternPo.getUserName();
					esxiPassword = PwdUtil.decryption(vcenternPo.getPassword());
					esxiIp = vcenternPo.getManageIp();
				}
			}

			String vshpereUrlPrefix = automationService.getAppParameter("VSPHERE.URL_PREFIX");
			String vshpereUrlSuffix = automationService.getAppParameter("VSPHERE.URL_SUFFIX");
			String esxiUrl = vshpereUrlPrefix + esxiIp + vshpereUrlSuffix;
			String hostIp = cmHostDAO.findHostIpById(vm.getHostId());

			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDatacenterDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());
			header.setOperationBean("queryInfoByHostNameServiceImpl");
			BodyDO body = BodyDO.CreateBodyDO();
			body.set(GeneralKeyField.VMware.VCENTER_URL, esxiUrl);
			body.set(GeneralKeyField.VMware.VCENTER_USERNAME, esxiUserName);
			body.set(GeneralKeyField.VMware.VCENTER_PASSWORD, esxiPassword);
			body.set(GeneralKeyField.VMware.ESXI_HOST_NAME, hostIp);

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

					List<DataObject> dataList = rspBody.getList(GeneralKeyField.VM.VM_INFO);
					for (DataObject obj : dataList) {
						ScanVmResultVo soj = new ScanVmResultVo();
						soj.setCpu(obj.getString(GeneralKeyField.VM.CPU_CORE_VALUE));
						soj.setVmName(obj.getString(GeneralKeyField.VM.VAPP_NAME));
						soj.setMemory(obj.getString(GeneralKeyField.VM.MEMORY_VALUE));
						soj.setDataStore(obj.getString("DATASTORE_NAME"));
						soj.setIp(obj.getString(GeneralKeyField.VM.VM_IP));
						soj.setPowerState(obj.getString(GeneralKeyField.VM.VM_POWER_STATE));
						resultList.add(soj);
					}
				} else {
					result = "fail";
				}
			}

		} catch (BizException e) {
			logger.error("异常exception",e);
		}
		return resultList;
	}
	public AutomationService getAutomationService() {
		return automationService;
	}
	public void setAutomationService(AutomationService automationService) {
		this.automationService = automationService;
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
	public IRmDatacenterDAO getRmDatacenterDAO() {
		return rmDatacenterDAO;
	}
	
}

