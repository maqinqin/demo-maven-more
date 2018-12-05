package com.git.cloud.resmgt.common.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.dao.impl.CmPasswordDAO;
import com.git.cloud.resmgt.common.dao.impl.CmVmDAO;
import com.git.cloud.resmgt.common.dao.impl.RmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.impl.RmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.service.ICheckResource;
import com.git.support.common.MesgFlds;
import com.git.support.common.VMFlds;
import com.git.support.common.VmReturnFlds;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

@Service
public class CheckResourceImpl implements ICheckResource {
	private static final Logger log = LoggerFactory.getLogger(CheckResourceImpl.class);
	private ResAdptInvokerFactory invkerFactory;
	@Override
	public boolean checkVmInVc(String vmId) throws RollbackableBizException {
		CmVmDAO cmVmDao = (CmVmDAO) WebApplicationManager.getBean("cmVmDAO");
		CmVmPo vmPo = cmVmDao.findCmVmById(vmId);
		RmVmManageServerDAO rmVmMSDao = (RmVmManageServerDAO) WebApplicationManager.getBean("rmVmManageServerDAO");
		RmVmManageServerPo vmManagerServerPo = rmVmMSDao.findRmVmManageServerByVmId(vmId);
		String uuid = vmPo.getIaasUuid();
		
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
//		header.setOperation(VMOpration.QUERY_VM_INFO);
		header.setOperationBean("queryVmInfoServiceImpl");
		// 增加数据中心路由标识
		RmDatacenterDAO rmDcDao = (RmDatacenterDAO)WebApplicationManager.getBean("rmDatacenterDAO");
		String queueIdentify = rmDcDao.getDataCenterById(vmManagerServerPo.getDatacenterId()).getQueueIden();
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),
				queueIdentify);
		reqData.setDataObject(MesgFlds.HEADER, header);
		
		BodyDO body = BodyDO.CreateBodyDO();
		String hostIp=vmManagerServerPo.getManageIp();
		String userName = vmManagerServerPo.getUserName();
		String password = "";
		try {
			CmPasswordDAO cmPasswordDAO = (CmPasswordDAO)WebApplicationManager.getBean("cmPasswordDAO");
			CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(vmManagerServerPo.getId(), userName);
			password = pwpo.getPassword();
			if(StringUtils.isBlank(password))
				throw new Exception("获取ManagerServer[" + vmManagerServerPo.getId() + "] password is null");
			password = PwdUtil.decryption(password);
		} catch (Exception e) {
			log.error("异常exception",e);
		}
		body.set(VMFlds.ESXI_URL, CloudClusterConstants.VCENTER_URL_HTTPS+hostIp+CloudClusterConstants.VCENTER_URL_SDK);
		body.set(VMFlds.ESXI_USERNAME, userName);
		body.set(VMFlds.ESXI_PASSWORD, password);
		if(invkerFactory==null){
			invkerFactory = (ResAdptInvokerFactory) WebApplicationManager.getBean("resInvokerFactory");
		}
		IResAdptInvoker invoker = invkerFactory.findInvoker("AMQ");
		body.set(VMFlds.VAPP_NAME, vmPo.getDeviceName());
		reqData.setDataObject(MesgFlds.BODY, body);
		IDataObject rsp;
		try {
			rsp = invoker.invoke(reqData, 300000);
			BodyDO rspBody = (BodyDO)rsp.get(MesgFlds.BODY);
			DataObject vmObj = (DataObject)rspBody.get(VmReturnFlds.VM_NAME);
			String vcUUID = vmObj.getString(GeneralKeyField.VM.UUID);
//			System.out.println(vmObj.get(GeneralKeyField.VM.UUID));
//			System.out.println(vmObj.getString(GeneralKeyField.VM.UUID));
			log.info("vCenter中的uuid:"+vcUUID+", 云平台中的uuid:"+uuid);
			if(vcUUID!=null && vcUUID.equals(uuid)){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
