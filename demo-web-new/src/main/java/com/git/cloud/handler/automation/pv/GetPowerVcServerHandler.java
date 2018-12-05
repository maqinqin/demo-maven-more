package com.git.cloud.handler.automation.pv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.powervc.common.OpenstackPowerVcServiceFactory;

/** 
 * 查询计算实例
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public abstract class GetPowerVcServerHandler extends OpenstackPowerVcCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(GetPowerVcServerHandler.class);
	
	protected int executeTimes = 10; // 执行次数，需要循环获取计算实例状态验证操作是否成功
	protected boolean isCheckDelete = false; // 验证是否已删除
	protected String deviceStatus = "";
	
	@SuppressWarnings("unchecked")
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		int len = deviceIdList == null ? 0 : deviceIdList.size();
		List<String> successDeviceList = new ArrayList<String> ();
		this.commonInitParam();
		int n = executeTimes;
		while(true) {
			if(successDeviceList.size() == len) {
				break;
			}
			if(n == 0) {
				throw new Exception("查询计算实例验证结果超时.");
			}
			try {
				// 休眠30秒
				Thread.sleep(30000);
			} catch(Exception e) {
				logger.error("异常exception",e);
			}
			for(int i=0 ; i<deviceIdList.size() ; i++) {
				deviceStatus = "";
				String deviceId = deviceIdList.get(i);
				if(successDeviceList.indexOf(deviceId) >= 0) {
					continue;
				}
				logCommon = "查询第" + (i+1) + "台计算实例";
				logger.debug(logCommon + "开始...");
				deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
				String domainName = deviceParams.get("DOMAIN_NAME");
				String openstackIp = deviceParams.get("OPENSTACK_IP");
				if(openstackIp == null || "".equals(openstackIp)) {
					throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
				}
				String token = deviceParams.get("TOKEN");
				if(token == null || "".equals(token)) {
					throw new Exception("认证TOKEN为空，请检查参数[TOKEN].");
				}
				String projectId = deviceParams.get("PROJECT_ID");
				if(projectId == null || "".equals(projectId)) {
					throw new Exception("ProjectId为空，请检查参数[PROJECT_ID].");
				}
				String targetServerId = (String) deviceParams.get("TARGET_SERVER_ID");
				if(targetServerId == null || "".equals(targetServerId)) {
					throw new Exception("计算实例ID为空，请检查参数[TARGET_SERVER_ID].");
				}
				String hostType = deviceParams.get("HOST_TYPE");
				if(hostType == null || "".equals(hostType)) {
					throw new Exception("主机类型为空，请检查参数[HOST_TYPE].");
				}
				Boolean isVm = null;
				if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
					isVm = true;
				} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
					isVm = false;
				} else {
					throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
				}
				String state = "";
				try {
					state = OpenstackPowerVcServiceFactory.getComputeServiceInstance(openstackIp,domainName, token).getServerState(projectId, targetServerId, isVm);
				} catch(Exception e) {
					
				}
				if(this.isOperateVmSuccess(state)) {
					if(successDeviceList.indexOf(deviceId) < 0) {
						successDeviceList.add(deviceId);
						if(!"".equals(deviceStatus)) {
							setHandleResultParam(deviceId, "CURRENT_DEVICE_STATUS", deviceStatus);
						}
						logger.debug(logCommon + "完成...");
					}
				} else {
					logger.info("[GetServerHandler]查询计算实例验证结果，目前计算实例尚未处理完毕");
				}
			}
			n--;
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
	
	protected abstract void commonInitParam();
	
	/**
	 * 根据每个对虚机操作之后的状态来单独写实现类
	 * @param contenxtParams
	 * @param responseDataObject
	 * @return
	 */
	protected abstract boolean isOperateVmSuccess(String state);
}