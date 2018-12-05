/**
 * @Title:GeneralShellExecHandler.java
 * @Package:com.git.cloud.handler.automation.sa.ssh
 * @Description:TODO
 * @author zhuzy
 * @date 2015-7-6 上午10:58:47
 * @version V1.0
 */
package com.git.cloud.handler.automation.sa.ssh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.handler.automation.HandlerReturnObject;
import com.git.cloud.handler.automation.HandlerReturnObjectForOneDev;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.Server;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.SAOpration;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;

/**
 * @ClassName:GeneralShellExecHandler
 * @Description:TODO
 * @author zhuzy
 * @date 2015-7-6 下午5:42:45
 *
 *
 */
public class GeneralShellExecHandler extends RemoteAbstractAutomationHandler {



	private static Logger log = LoggerFactory.getLogger(GeneralShellExecHandler.class);
	
	private static final String DEVICE_IDS = "DEVICE_IDS";
	private static final String MANAGEMENT_TARGET = "MANAGEMENT_TARGET";
	private static final String AUTOMATION_TYPE = "AUTOMATION_TYPE";
	private static final String HANDLER_THERAD_NUM = "HANDLER_THERAD_NUM";
	private static final String SHELL_COMMAND_LINE = "SHELL_COMMAND_LINE";
	
	/* (non-Javadoc)
	 * <p>Title:execute</p>
	 * <p>Description:</p>
	 * @param contenxtParmas
	 * 	缺省包含的参数
	 * 		管理对象：MANAGEMENT_TARGET		取值范围：OBJECT_1:目标服务器1、OBJECT_2_N:除对象1之外、OBJECT_ALL:所有目标服务器
	 * 		资源自动化类型：AUTOMATION_TYPE		范围：（缺省）CLOUD_REQUEST:云服务申请、RESOURCE_MANAGEMENT：资源管理
	 * 		RESOURCE_MANAGEMENT类型的参数：实例id、DEVICE_IDS:设备id集合,例如：["111111","2222"]
	 * 		REMOTE_SERVER_IP		登录IP	[sql$xxx] 或 [$xxx]
  			REMOTE_SERVER_USER		登录用户
  			REMOTE_SERVER_PWD		登录密码
  			并发数		HANDLER_THREAD_NUM
  			返回信息正确性   此表达式为真则表示节点成功，否则失败
  			输出参数 
  			返回信息处理方法  输入类.方法 系统自动调用此方法
	 * @return
	 * @throws Exception
	 * @see com.git.cloud.handler.automation.IAutomationHandler#execute(java.util.HashMap)
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) throws Exception {
		String srInfoId = null;
		String rrinfoId = null;
		
		String flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
		String nodeId = getContextStringPara(contextParams, NODE_ID);
		
		String automationType = getContextStringPara(contextParams, AUTOMATION_TYPE);
		int handlerTheadNum = 1;
		try {
			handlerTheadNum = getContextLongPara(contextParams, HANDLER_THERAD_NUM).intValue();
		} catch (Exception e) {
			log.warn("HANDLER_THERAD_NUM is null, use 1");
		}
		String managementTarget = getContextStringPara(contextParams, MANAGEMENT_TARGET);
		
		String shellCommandLine = getContextStringPara(contextParams, SHELL_COMMAND_LINE);
		
		String remoteIpParam = getContextStringPara(contextParams, "REMOTE_SERVER_IP");
		String remoteUserParam = getContextStringPara(contextParams, "REMOTE_SERVER_USER");
		String remotePwdParam = getContextStringPara(contextParams, "REMOTE_SERVER_PWD");
		
		List<String> devs = null;
		if (automationType != null && automationType.equalsIgnoreCase("RESOURCE_MANAGEMENT")) {
			String devIds = getContextStringPara(contextParams, DEVICE_IDS);
			devs = (List<String>)JSON.parse(devIds);
		} else if (automationType == null || automationType.equalsIgnoreCase("CLOUD_REQUEST")) {
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			devs = getDeviceIdList(flowInstId, nodeId, rrinfoId, null);
		} else {
			throw new Exception("AUTOMATION_TYPE Error, value should be RESOURCE_MANAGEMENT or CLOUD_REQUEST, but value is : " + automationType);
		}
		// 获取需要被操作的设备集合
		List<String> processDevIds = new ArrayList<String>();
		if (managementTarget != null && managementTarget.equalsIgnoreCase("OBJECT_1")) {
			if(devs == null || devs.size() == 0) {
				processDevIds.add(rrinfoId);
			} else {
				processDevIds.add(devs.get(0));
			}
		} else if (managementTarget != null && managementTarget.equalsIgnoreCase("OBJECT_2_N")) {
			processDevIds.addAll(devs);
			processDevIds.remove(0);
		} else if (managementTarget != null && managementTarget.equalsIgnoreCase("OBJECT_ALL")) {
			processDevIds.addAll(devs);
		} else {
			throw new Exception("MANAGEMENT_TARGET Error, value should be OBJECT_1 or OBJECT_2_N or OBJECT_ALL, but value is : " + managementTarget);
		}
		int timeOutMs = Integer.parseInt(contextParams.get("TIME_OUT").toString()) * 1000;
		// 依次登录服务器执行命令
		ExecutorService  workers = Executors.newFixedThreadPool(handlerTheadNum);
		List<Future<IDataObject>> listFuture = new ArrayList<Future<IDataObject>>();
		for (String managedDevId : processDevIds) {
			contextParams.put("DEVICE_ID", managedDevId);
			// 读取设备的流程参数
			Map<String, String> deviceParams = getBizParamInstService().getInstanceParasMap(flowInstId, managedDevId);
			String dcQueueIden = null;
			dcQueueIden = deviceParams.get(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue());
			if (dcQueueIden == null || dcQueueIden.trim().equals("")) {
				RmDatacenterPo dc = getAutomationService().getDatacenterByDeviceIdInResPool(managedDevId);
				dcQueueIden = dc.getQueueIden();
			}
			// 获取远程服务器信息
			String remoteUserValue = getParamValue(contextParams, remoteUserParam, deviceParams);
			String remoteIpValue = getParamValue(contextParams, remoteIpParam, deviceParams);
			String remotePwdValue = getParamValue(contextParams, remotePwdParam, deviceParams);
			Server targetServer = new Server(remoteIpValue, remoteUserValue, remotePwdValue);
			// 执行命令
			String realCommandLine = Utils.getRealStr(shellCommandLine, contextParams, deviceParams);
			CallShell callShell = new CallShell(targetServer, realCommandLine, dcQueueIden, timeOutMs);
			listFuture.add(workers.submit(callShell));
		}
		workers.shutdown();
		HandlerReturnObject returnObjs = new HandlerReturnObject();
		int index = 0;
		for (Future<IDataObject> ft : listFuture) {
			// 解析返回信息
			IDataObject responseData = ft.get();
			List<String> echos = getEcho(responseData);
			List<String> errors = getError(responseData);
			HandlerReturnObjectForOneDev retObj = new HandlerReturnObjectForOneDev(processDevIds.get(index++), 
					getReturnCode(responseData), echos, errors);
			retObj.setReturnMesg(getReturnMesg(responseData));
			log.info("Return index " + index + ": " + retObj.getMesg());
			returnObjs.addOneObject(retObj);
			try {
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("DEVICE_ID", retObj.getDeviceId());
				paramMap.put("ECHOS", echos);
				this.handleResonpse(paramMap, responseData);
				this.saveParamInsts(flowInstId, nodeId);
			} catch(Exception e) {
				log.error("异常exception",e);
				log.error("处理返回结果出现异常" + e);
			}
		}
		// 判断返回对象集合的正确性
		try {
			String returnString = getAutomationService().getHandlerReturnString(returnObjs);
			return returnString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "0000";
		}
	}
	
	class CallShell implements Callable<IDataObject> {

		private Server targetServer;
		private String realCommandLine;
		private String dcQueue;
		private int timeOut;
		
		public CallShell(Server targetServer, String realCommandLine, String dcQueue, int timeOut) {
			super();
			this.targetServer = targetServer;
			this.realCommandLine = realCommandLine;
			this.dcQueue = dcQueue;
			this.timeOut = timeOut;
		}

		@Override
		public IDataObject call() throws Exception {
			return callShellCommandLine(targetServer, realCommandLine, dcQueue, timeOut);
		}
		
	}
	/**
	 * @Title: getParamValue
	 * @Description: TODO
	 * @field: @param contextParams
	 * @field: @param remoteIpParam
	 * @field: @param deviceParams
	 * @field: @throws Exception
	 * @return String
	 * @throws
	 */
	private String getParamValue(HashMap<String, Object> contextParams, String remoteIpParam, Map<String, String> deviceParams) throws Exception {
		String realValue = null;
		if (remoteIpParam.trim().startsWith("[sql$") && remoteIpParam.trim().endsWith("]")) {
			String decorateSql = remoteIpParam.trim().substring(5, remoteIpParam.trim().length() - 1);
			String realSql = Utils.getRealStr(decorateSql, contextParams, deviceParams);
			// 调用查询方法获取参数值
			realValue = getAutomationService().getSingleStringValueBySql(realSql);
		} else {
			realValue = Utils.getRealStr(remoteIpParam, contextParams, deviceParams);
		}
		return realValue;
	}

	/**
	 * @throws Exception 
	 * @Title: callShellCommandLine
	 * @Description: TODO
	 * @field: @param targetServer
	 * @field: @param shellCommandLine
	 * @field: @param queueIden
	 * @field: @return
	 * @return IDataObject
	 * @throws
	 */
	private IDataObject callShellCommandLine(Server targetServer, String shellCommandLine, String queueIden, int timeOutMs) throws Exception {
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIden);
		header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
		header.setOperation(SAOpration.EXEC_SHELL);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(SAConstants.SERVER_IP, targetServer.getServerIp());
		body.setString(SAConstants.USER_NAME, targetServer.getUser());
		body.setString(SAConstants.USER_PASSWORD, targetServer.getPassword());
		body.setInt(SAConstants.CHARSET, 0);
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setString(SAConstants.EXEC_FLAG, "independent");
		body.setString(SAConstants.LOCALPRIKEY_URL, "");

		List<String> cmdList = Lists.newArrayList(shellCommandLine);
		body.setList(SAConstants.EXEC_SHELL, cmdList);
		
		reqData.setDataObject(MesgFlds.BODY, body);
		
		IDataObject responseDataObject = getResAdpterInvoker().invoke(reqData, timeOutMs);
		
		return responseDataObject;
	}

	
	/* (non-Javadoc)
	 * <p>Title:buildRequestData</p>
	 * <p>Description:</p>
	 * @param contenxtParmas
	 * @return
	 * @throws Exception
	 * @see com.git.cloud.handler.automation.RemoteAbstractAutomationHandler#buildRequestData(java.util.Map)
	 */
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * <p>Title:handleResonpse</p>
	 * <p>Description:</p>
	 * @param contenxtParmas
	 * @param responseDataObject
	 * @throws Exception
	 * @see com.git.cloud.handler.automation.RemoteAbstractAutomationHandler#handleResonpse(java.util.Map, com.git.support.sdo.inf.IDataObject)
	 */
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) throws Exception {
		// TODO Auto-generated method stub
		List<String> echos = (List<String>) contenxtParmas.get("ECHOS");
		if(echos != null && echos.size() > 0) {
			for(int i=0 ; i<echos.size() ; i++) {
				String[] subArr = echos.get(i).split("=");
				if(subArr[0].length() > 100 || subArr.length < 2 || subArr[1].length() > 3000) {
					continue;
				}
				setHandleResultParam((String) contenxtParmas.get("DEVICE_ID"), subArr[0], subArr[1]);
			}
		}
	}

}
