package com.git.cloud.resmgt.compute.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.exception.BizException;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.impl.RmVmManageServerDAO;
import com.git.cloud.resmgt.compute.model.po.ScanHmcHostPo;
import com.git.cloud.resmgt.compute.model.vo.ScanVmResultVo;
import com.git.cloud.resmgt.compute.model.vo.VmVo;
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

public class HostControllerServicePowerImpl implements HostControllerService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RmVmManageServerDAO rmVmMgServerDAO;
	@Autowired	
	private ResAdptInvokerFactory resInvokerFactory;
	@Autowired
	private IRmDatacenterDAO rmDatacenterDAO;

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<ScanVmResultVo> scanVmFromHost(VmVo vm) throws Exception {
		List<ScanVmResultVo> resultList = new ArrayList<ScanVmResultVo>();
		String result = "";
		try {
			ScanHmcHostPo hostPo = rmDatacenterDAO.findHmcHostInfoById(vm.getHostId());
			
			String url = hostPo.getUrl();
			String userName = hostPo.getUserName();
			String password = PwdUtil.decryption(hostPo.getPassword());
			String hostName = hostPo.getHostName();
			String queueIden = hostPo.getQueueIden();
			
			//开始拼写报文
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setOperationBean("scanVMInfoByHostNameImpl");
			header.setResourceClass("PW");
			header.set("DATACENTER_QUEUE_IDEN", queueIden);
			header.setResourceType("PW");
			
			BodyDO body = BodyDO.CreateBodyDO();
			body.set(GeneralKeyField.Power.URL, url); 
			body.set(GeneralKeyField.Power.USERNAME, userName);
			body.set(GeneralKeyField.Power.PASSWORD, password); 
			body.set(GeneralKeyField.Power.HOST_NAME, hostName);

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
				BodyDO rspBody = rspData.getDataObject(MesgFlds.BODY, BodyDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					List<DataObject> dataList = rspBody.getList(GeneralKeyField.VM.VM_INFO);
					for (DataObject obj : dataList) {
						ScanVmResultVo soj = new ScanVmResultVo();
						soj.setVmName(obj.getString(GeneralKeyField.VM. VAPP_NAME));
						soj.setCpu(obj.getString(GeneralKeyField.VM.CPU_CORE_VALUE));
						soj.setMemory(obj.getString(GeneralKeyField.VM.MEMORY_VALUE));
						soj.setIp(obj.getString(GeneralKeyField.VM.VM_IP));
						soj.setPowerState(obj.getString(GeneralKeyField.VM.VM_POWER_STATE));
						soj.setHostType("POWER");
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

}
