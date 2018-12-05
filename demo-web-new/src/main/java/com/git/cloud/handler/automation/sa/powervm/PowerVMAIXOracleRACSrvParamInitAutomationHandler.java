package com.git.cloud.handler.automation.sa.powervm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.CUseType;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.service.PowerVMAIXService;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.common.MesgRetCode;
import com.git.support.util.CloudGlobalConstants;

/**
 * 
 * <p>
 * 初始化power vm + aix + oracle + rac 服务的参数
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-5-17
 * @see
 */
public class PowerVMAIXOracleRACSrvParamInitAutomationHandler extends
		LocalAbstractAutomationHandler {

	
	private static Logger log = LoggerFactory
			.getLogger(PowerVMAIXOracleRACSrvParamInitAutomationHandler.class);

	private PowerVMAIXService powerVMAIXService = (PowerVMAIXService) WebApplicationManager
			.getBean("powerVMAIXServiceImpl");
	
	private String logMsg = null;

	@Override
	public String execute(HashMap<String, Object> contextParams) {
		return service(contextParams);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.iomp.cloud.core.automation.handler.LocalAbstractAutomationHandler
	 * #service(java.util.Map)
	 */
	@Override
	public String service(Map<String, Object> contextParams) {

		try {
			// 定义参数对象
			PowerVMAIXOracleRACContextObject contextObj = new PowerVMAIXOracleRACContextObject();
			// 流程实例Id
			String flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			// 节点ID
			String nodeId = getContextStringPara(contextParams, NODE_ID);
			// 资源请求ID
			String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			
			//参数清理
			getBizParamInstService().deleteParamInstsOfFlow(flowInstId);
			
			logMsg = "创建Power服务，初始化流程参数，流程实例ID：" + flowInstId + ",节点ID：" + nodeId + "。";
			
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
			if (deviceIdList == null || deviceIdList.size() == 0) {
				throw new Exception("没有查询到虚拟机信息。");
			}
			log.debug("Init cloud attr parameter.");
			Map<String, String> cloudAttr = getAutomationService().getServiceAttr(rrinfoId);
			for (String devId : deviceIdList) {
				for (String attrName : cloudAttr.keySet()) {
					contextObj.setDevicePara(devId.toString(), attrName, cloudAttr.get(attrName));
				}
			}
			/*
			 *  读取vioc的各种ip地址
			 */
			List<DeviceNetIP> deviceNetIPs = powerVMAIXService.getDeviceNetIp(deviceIdList);
			for (DeviceNetIP deviceNetIP : deviceNetIPs) {
				String deviceID = deviceNetIP.getDeviceID().toString();
				log.debug("开始设置" + deviceID + "的Ip地址参数。");
				// 读取管理ip
				String pmNetType = RmPlatForm.POWER.getValue() + RmVirtualType.POWERVM.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.MGMT.getValue();
				List<NetIPInfo> vmIPs = deviceNetIP.getNetIPs().get(pmNetType);
				if (vmIPs != null) {
					powerVMAIXService.makeViocIp(contextObj, deviceID, vmIPs);
				}
				// 读取所有生产ip
				pmNetType = RmPlatForm.POWER.getValue() + RmVirtualType.POWERVM.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.PROD.getValue();
				vmIPs = deviceNetIP.getNetIPs().get(pmNetType);
				if (vmIPs != null) {
					powerVMAIXService.makeProductIp(contextObj, deviceNetIP, vmIPs);
				}
				// 生成配置生产网卡和心跳网卡的参数，格式为 ip_mask_interfacename ip_mask_interfacename
				String netStr = contextObj.getDevicePara(deviceID, PowerVMAIXVariable.PRODUCT_IP_1)
						+ "-" + contextObj.getDevicePara(deviceID, PowerVMAIXVariable.PRODUCT_IP_1_MASK)
						+ "-" + PowerVMNetInterfaceName.EN0.getValue();
				// 读取心跳网卡信息
				pmNetType = RmPlatForm.POWER.getValue() + RmVirtualType.POWERVM.getValue() + RmHostType.VIRTUAL.getValue() + CUseType.PRI.getValue();
				vmIPs = deviceNetIP.getNetIPs().get(pmNetType);
				if (vmIPs != null) {
					NetIPInfo heartIP = vmIPs.get(0);
					// heart_ip
					contextObj.setDevicePara(deviceNetIP.getDeviceID().toString(), PowerVMAIXVariable.HEART_IP, heartIP.getIp());
					// heart_mask
					contextObj.setDevicePara(deviceNetIP.getDeviceID().toString(), PowerVMAIXVariable.HEART_MASK, heartIP.getIpMask());
					// heart_gateway
					contextObj.setDevicePara(deviceNetIP.getDeviceID().toString(), PowerVMAIXVariable.HEART_GATWAY, heartIP.getGateWay());
					// product_pvid 生产网卡vlanid
					contextObj.setDevicePara(deviceNetIP.getDeviceID().toString(), PowerVMAIXVariable.HEART_PVID, heartIP.getVlanID());
					netStr += " " + contextObj.getDevicePara(deviceID, PowerVMAIXVariable.HEART_IP)
							+ "-" + contextObj.getDevicePara(deviceID, PowerVMAIXVariable.HEART_MASK)
							+ "-" + PowerVMNetInterfaceName.EN1.getValue();
				}
				contextObj.setDevicePara(deviceID, PowerVMAIXVariable.IP_MASK_INTERFACENAME, netStr);
			}
			
			powerVMAIXService.makeFlowContextObject(contextObj, rrinfoId, deviceIdList);
			
			// 设置uncap_weight，从配置文件中读取
			contextObj.setContextPara(PowerVMAIXVariable.UNCAP_WEIGHT, 
					getAutomationService().getAppParameter(PowerVMAIXVariable.P_V_O_R_UNCAP_FOR_ORACLE_RAC));
			
			log.debug("初始化的工作流参数信息为：");
			log.debug(contextObj.toString());
			
			// 保存初始化参数
			Map<String, Map<String, String>> deviceParaMap = contextObj.getContextDeviceMap();
			log.debug("保存初始化参数");
			setNeedSaveParams(deviceParaMap);
			saveParamInsts(flowInstId, nodeId);
			
			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
					logMsg, MesgRetCode.SUCCESS);
		} catch (Exception e) {
			String errorMsg = logMsg + "错误：{" + e + "}";
			log.error(errorMsg);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
					errorMsg, MesgRetCode.ERR_OTHER);
		}
		
	}
	
}
