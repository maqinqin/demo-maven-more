package com.git.cloud.handler.automation.sa.ssh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.handler.automation.Constants;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;

/**
 * Check server reachable
 * @author zhuzhaoyong
 *
 */
public class CheckVMReachableHandler extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(CheckVMReachableHandler.class);
	// 输出的日志中的公用信息
	private String logMsg = null;
	
	@Override
	public String execute(HashMap<String, Object> contextParams) {

		long startTime; 
		long timeout = 7200000;
		Map<String, Map<String, String>> bpmPara = new HashMap<String, Map<String,String>>();
		Map<String, String> deviceParaMap = new HashMap<String, String>();
		List<String> deviceIds = new ArrayList<String>();
		List<String> successDeviceIds = new ArrayList<String>();
		String flowInstId, rrinfoId, nodeId, srInfoId;
		try {
			// 流程实例Id
			flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			// 节点ID
			nodeId = getContextStringPara(contextParams, NODE_ID);
			// 资源请求ID
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			String dcQueueIden = null;
			String automationType = getContextStringPara(contextParams, "AUTOMATION_TYPE");
			String waitSeconds = getContextStringPara(contextParams, "WAIT_SECOND");
			if(StringUtils.isBlank(waitSeconds)){
				waitSeconds = "30";
			}
			long waitTime = Long.parseLong(waitSeconds) * 1000;
			Thread.sleep(waitTime);
			
			logMsg = "检查虚拟机是否可达，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。";
			// Read bpm parameters
			bpmPara = this.getHandleParams(flowInstId);
			// Get timeout time, millisecond 
			if (contextParams.get(TIME_OUT_STR) != null && !StringUtils.isEmpty(contextParams.get(TIME_OUT_STR).toString())) {
				timeout = Long.parseLong(contextParams.get(TIME_OUT_STR).toString());
				timeout *= 1000;
			}
			
			log.debug("Check server reachable. timeout is: " + timeout);
			// Get servers
			if (automationType != null && automationType.equalsIgnoreCase("RESOURCE_MANAGEMENT")) {
				String devIds = getContextStringPara(contextParams, "DEVICE_IDS");
				deviceIds = (List<String>)JSON.parse(devIds);
			} else if (automationType == null || automationType.equalsIgnoreCase("CLOUD_REQUEST")) {
				deviceIds = getAutomationService().getDeviceIdsSort(rrinfoId);
			} else {
				throw new Exception("AUTOMATION_TYPE Error, value should be RESOURCE_MANAGEMENT or CLOUD_REQUEST, but value is : " + automationType);
			}
			
			startTime = System.currentTimeMillis();
			while (true) {
				if ((System.currentTimeMillis() - startTime) > timeout) {
					throw new Exception("不能连接到目标服务器，连接超时。Timeout=" + timeout);
				}
				// Loop servers , check reachable
				for (String deviceId : deviceIds) {
					if (successDeviceIds.contains(deviceId)) {
						continue;
					}
					log.debug("Check reachable device {}", deviceId);
					deviceParaMap = bpmPara.get(deviceId.toString());
					String serverIp = deviceParaMap.get(SAConstants.SERVER_IP);
					String user = deviceParaMap.get(SAConstants.USER_NAME);
					String password = deviceParaMap.get(SAConstants.USER_PASSWORD);
					
					dcQueueIden = deviceParaMap.get(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue());
					// 如果初始化参数中没有队列标识，则使用设备id去查找
					if (dcQueueIden == null || dcQueueIden.trim().equals("")) {
						RmDatacenterPo datacenter = null;
						datacenter =getAutomationService().getDatacenterByDeviceId(deviceIds.get(0));
						dcQueueIden = datacenter.getQueueIden();
					}
				
					if (checkReachable(serverIp, user, password, timeout, rrinfoId, dcQueueIden)) {
						log.debug("Device {} is reachable.", deviceId);
						successDeviceIds.add(deviceId);
					}
				}
				if (successDeviceIds.size() == deviceIds.size()) {
					log.debug("Server is reachable all.");
					break;
				}
				// 休息30秒
				Thread.sleep(30 * 1000);
			}
//			return MesgRetCode.SUCCESS;
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
					logMsg, MesgRetCode.SUCCESS);
		} catch (Exception e) {
			String errorMsg = logMsg + "错误：{" + e + "}";
			log.error(errorMsg);
			printExceptionStack(e);
			//return errorMsg;
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
					errorMsg, MesgRetCode.ERR_OTHER);
		}
		
	}
	
	/**
	 * Check server reachable
	 * 
	 * @param serverIp
	 * @param user
	 * @param password
	 * @return 
	 */
	private boolean checkReachable(String serverIp, String user, String password, long timeout, String rrinfoId, String queueIden) throws Exception {
		// Make request
		List<String> cmdList = new ArrayList<String>();
		cmdList.add("echo " + MesgRetCode.SUCCESS);
		
		IDataObject reqData = getSshShellRequest(serverIp, user, password, cmdList, queueIden, false);
		
		log.debug("Check server reachable: {}, {}, {}.", new Object[]{serverIp, user, password});
		// 调用适配器执行操作
		log.debug("发送调用请求，请求信息为：" + Constants.BR + getRequestDataObjectContent(reqData));
		try {
			IDataObject responseDataObject = getResAdpterInvoker().invoke(reqData, (int) timeout);
			log.debug("Check server reachable finish.");
			// 显示执行命令的结果
			logResponseData(responseDataObject);
			HeaderDO responseHeader = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
			if (responseHeader.getRetCode().equals(MesgRetCode.SUCCESS)) {
				List<String> echos = getEcho(responseDataObject);
				if (echos != null && echos.size() > 0) {
					String result = echos.get(0);
					if (result.equals(MesgRetCode.SUCCESS)) {
						return true;
					}
				}
			} else {
				// "User("+userName+") connection failed"
				String returnMsg = responseHeader.getRetMesg();
				String errorMsg = "Check reachable，return code：" + responseHeader.getRetCode() + "，return message：" + returnMsg;
				log.debug("Check server reachable. errorMsg: " + errorMsg);
				if (returnMsg.startsWith("User") && returnMsg.endsWith("connection failed")) {
					log.info(errorMsg);
					return false;
				} else {
					throw new Exception(errorMsg);
				}
			}
		} catch (Exception e) {
			log.error("Method checkReachable() found exception." + e);
			printExceptionStack(e);
			throw e;
		}
		return false;
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) {
		// TODO Auto-generated method stub

	}
	

}
