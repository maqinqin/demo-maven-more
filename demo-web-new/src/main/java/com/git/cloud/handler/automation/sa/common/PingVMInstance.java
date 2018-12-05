package com.git.cloud.handler.automation.sa.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.automation.sa.ssh.CommandExecutionHandler;
import com.git.cloud.handler.common.NetUtil;
import com.git.support.common.MesgRetCode;
import com.git.support.constants.SAConstants;

/**
 * ping vm 
 * @author zhuzhaoyong
 *
 */
public class PingVMInstance extends LocalAbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(CommandExecutionHandler.class);
	// 输出的日志中的公用信息
	private String logMsg = null;
	private String deviceLogStr = null;
	
	@Override
	public String execute(HashMap<String, Object> contextParams) {

		Map<String, Map<String, String>> bpmPara = new HashMap<String, Map<String,String>>();
		Map<String, String> deviceParaMap = new HashMap<String, String>();
		List<String> deviceIds = new ArrayList<String>();
		String flowInstId, rrinfoId, nodeId;
		try {
			// 流程实例Id
			flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			// 节点ID
			nodeId = getContextStringPara(contextParams, NODE_ID);
			// 资源请求ID
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			
			logMsg = "检查待分配的虚拟机ip是否能够连通，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。";
			
			// 读取虚拟机列表，用于取得参数信息
			@SuppressWarnings("unchecked")
			List<String> vmIds = (List<String>)contextParams.get("destVmIds");
			deviceLogStr = "[设备:" + vmIds.get(0).toString() + "]";
			
			// Read bpm parameters
			bpmPara = this.getHandleParams(flowInstId, vmIds);
			deviceIds = vmIds;
			
			for (String deviceId : deviceIds) {
				log.debug("Ping vm ip is avaliable {}", deviceId);
				deviceParaMap = bpmPara.get(deviceId.toString());
				
				List<String> vmIps = new ArrayList<String>();
				// for aix
				/*if (!StringUtils.isEmpty(deviceParaMap.get(PowerVMAIXVariable.VIOC_IP))) {
					log.debug("Vm management ip: " + deviceParaMap.get(PowerVMAIXVariable.VIOC_IP));
					vmIps.add(deviceParaMap.get(PowerVMAIXVariable.VIOC_IP));
				}
				if (!StringUtils.isEmpty(deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_1))) {
					log.debug("Vm product ip: " + deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_1));
					vmIps.add(deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_1));
				}
				if (!StringUtils.isEmpty(deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_2))) {
					log.debug("Vm product ip: " + deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_2));
					vmIps.add(deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_2));
				}
				if (!StringUtils.isEmpty(deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_3))) {
					log.debug("Vm product ip: " + deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_3));
					vmIps.add(deviceParaMap.get(PowerVMAIXVariable.PRODUCT_IP_3));
				}*/
				// fox x86
				if (!StringUtils.isEmpty(deviceParaMap.get(SAConstants.NIC_IP_MGMT))) {
					log.debug("Vm management ip: " + deviceParaMap.get(SAConstants.NIC_IP_MGMT));
					vmIps.add(deviceParaMap.get(SAConstants.NIC_IP_MGMT));
				}
				if (!StringUtils.isEmpty(deviceParaMap.get(SAConstants.NIC_IP_PROD))) {
					log.debug("Vm product ip: " + deviceParaMap.get(SAConstants.NIC_IP_PROD));
					vmIps.add(deviceParaMap.get(SAConstants.NIC_IP_PROD));
				}
				
				if (vmIps.size() < 2) {
					throw new Exception("没有读取到虚拟机足够的Ip。");
				}
				int count = 5;
				for (String ip : vmIps) {
					log.debug("Ping -c " + count + " " + ip + deviceLogStr);
					if (NetUtil.doPingForLinux(ip, count)) {
						throw new Exception("待分配的虚拟机的Ip已经被使用，Ip：" + ip + deviceLogStr);
					}
					log.debug("The ip: " + ip + " is not reachable." + deviceLogStr);
				}
			}
		} catch (Exception e) {
			String errorMsg = logMsg + "错误：{" + e + "}" + deviceLogStr;
			log.error(errorMsg);
			printExceptionStack(e);
			return errorMsg;
		}
		
		return MesgRetCode.SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.ccb.iomp.cloud.core.automation.handler.LocalAbstractAutomationHandler#service(java.util.Map)
	 */
	@Override
	public String service(Map<String, Object> contenxtParams) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
