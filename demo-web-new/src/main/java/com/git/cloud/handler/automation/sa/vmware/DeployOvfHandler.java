package com.git.cloud.handler.automation.sa.vmware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.AbstractAutomationHandler;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.sa.common.HandlerThread;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.impl.CmPasswordDAO;
import com.git.cloud.resmgt.common.dao.impl.CmVmDAO;
import com.git.cloud.resmgt.common.dao.impl.RmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmReturnFlds;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;


/**
 * @author 
 * 
 */
public class DeployOvfHandler extends AbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(DeployOvfHandler.class);
	private ResAdptInvokerFactory invkerFactory;
	String deviceLogStr = "";

	@Override
	public String execute(HashMap<String, Object> contextParams) {

		long startTime = System.currentTimeMillis();
		
		String rrinfoId = null;
		String srInfoId = null;
		String instanceId = null;
		String nodeId = null;
		
		try {
			
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			instanceId = getContextStringPara(contextParams, FLOW_INST_ID);
			nodeId = getContextStringPara(contextParams, NODE_ID);
			
			log.debug("【DeployOvfHandler，使用线程池】执行自动化操作开始,流程实例ID:{},节点ID:{}", instanceId, nodeId);

			//允许由前台指定部分设备执行动作
			String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
			log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
			List<String> devIdList = getDeviceIdList(instanceId, nodeId, rrinfoId, redoErrorDev);
			log.debug("*******************20140324 debuging : devIdList = "+ devIdList.toString() +"*********************************");

			for (String vmid : devIdList) {
				deviceLogStr += "[" + vmid + "]";
			}
			log.debug("【DeployOvfHandler】execute devices: " + deviceLogStr);

			ExecutorService exec = null;
			
			int threadNum = getHandlerThreadNum(contextParams);
			log.debug("Command thread count: " + threadNum);
			exec = Executors.newFixedThreadPool(threadNum);
			
			Map<String, StringBuffer> resultsMap = new ConcurrentHashMap<String, StringBuffer>();

			for (String vmId : devIdList) {
				resultsMap.put(vmId, new StringBuffer());
			}
			Map<String,Future<String>> futureMap = Maps.newHashMap();
			for (String deviceId : devIdList) {

				DeployOvfInstance instance = new DeployOvfInstance();

				@SuppressWarnings("unchecked")
				HashMap<String, Object> params = (HashMap<String, Object>) Utils
						.depthClone(contextParams);
				List<String> ids = new ArrayList<String>();
				ids.add(deviceId);
				params.put("destVmIds", ids);

				log.debug("Start execute for device: " + deviceId);
				Future<String> future = exec.submit(new HandlerThread(instance, params, resultsMap));
				futureMap.put(deviceId, future);
			}
			
			putFutureResult(futureMap,resultsMap);
			exec.shutdown();
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			log.debug("Command execute finish. Devices: "
					+ deviceLogStr);
			

			log.debug("【DeployOvfHandler，使用线程池】执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					instanceId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
			
			//将虚机UUID保存到主键映射表
			saveUuidFromVcenter(rrinfoId, devIdList);
			
			// 供给服务申请 ，构造工作流新版本的返回值
			String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
							rrinfoId, srInfoId, resultsMap, instanceId, nodeId);
			log.debug("【DeployOvfHandler】 return str: " + handlerReturn);
//			return "0000";
			return handlerReturn;
		} catch (Exception e) {
			String errorMsg = "错误：{" + e + "}" + deviceLogStr;
			log.error(errorMsg);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			String handlerStringReturn = getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), errorMsg, MesgRetCode.ERR_OTHER);
			return handlerStringReturn;
//			return "0000";
		} finally {
			if (contextParams != null) {
				contextParams.clear();
			}
		}
	}
	/**
	 * 虚拟机供给流程时将虚机UUID保存到主键映射表
	 * @param rrinfoId
	 * @param devIdList
	 * @throws Exception
	 */
	private void saveUuidFromVcenter(String rrinfoId, List<String> devIdList) throws Exception{
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.QUERY_VM_INFO);
//		header.setOperationBean("queryVmInfoServiceImpl");
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),
				queueIdentify);
		reqData.setDataObject(MesgFlds.HEADER, header);
		
		BodyDO body = BodyDO.CreateBodyDO();
		/*body.set(VMFlds.ESXI_URL, "https://10.220.8.24/sdk");
		body.set(VMFlds.ESXI_USERNAME, "Administrator@vsphere.local");
		body.set(VMFlds.ESXI_PASSWORD, "Password@123");*/
		RmVmManageServerDAO rmVmMSDao = (RmVmManageServerDAO) WebApplicationManager.getBean("rmVmManageServerDAO");
		RmVmManageServerPo vmManagerServerPo = rmVmMSDao.findRmVmManageServerByVmId(devIdList.get(0));
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
		CmVmDAO cmVmDao = (CmVmDAO) WebApplicationManager.getBean("cmVmDAO");
		for (String vmId : devIdList) {
			CmVmPo vmPo = cmVmDao.findCmVmById(vmId);
			body.set(VMFlds.VAPP_NAME, vmPo.getDeviceName());
			reqData.setDataObject(MesgFlds.BODY, body);
			IDataObject rsp = invoker.invoke(reqData, 300000);
			BodyDO rspBody = (BodyDO)rsp.get(MesgFlds.BODY);
			DataObject vmObj = (DataObject)rspBody.get(VmReturnFlds.VM_NAME);
			log.info("vCenter中的uuid:"+vmObj.getString(GeneralKeyField.VM.UUID));
			ICmVmDAO cmVmDAO = (ICmVmDAO) WebApplicationManager.getBean("cmVmDAO");
			CmVmPo cmvm = new CmVmPo();
			cmvm.setId(vmId);
			cmvm.setIaasUuid(vmObj.getString(GeneralKeyField.VM.UUID));
			cmVmDAO.updateCmVm(cmvm);
		}
	}

}
