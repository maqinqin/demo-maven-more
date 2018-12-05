package com.git.cloud.handler.automation.sa.weblogic;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.support.common.VMFlds;
import com.git.support.constants.PubConstants;
import com.git.support.constants.SAConstants;
import com.git.support.constants.WeblogicConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * weblogic安装所需参数初始化
 * <p>
 * 
 * @author mengzx
 * @version 1.0 2013-5-7
 * @see
 */
public class WeblogicBuildParamInitAutomationHandler extends LocalAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(WeblogicBuildParamInitAutomationHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.iomp.cloud.core.automation.handler.LocalAbstractAutomationHandler
	 * #service(java.util.Map)
	 */
	public String service(Map<String, Object> contenxtParams) throws Exception {

		// 流程实例Id
		String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);

		// 服务请求Id
		String srvReqId = (String) contenxtParams.get(SRV_REQ_ID);

		// 资源请求ID
		String rrinfoId = (String) contenxtParams.get(RRINFO_ID);

		logger.info("weblogic构建所需参数实例化开始,服务请求ID:" + srvReqId + ",流程实例ID:" + flowInstId);

		// 获取虚拟机相关的参数，weblogic参数初始化化必须在VM初始化之后执行
		Map<String, Map<String, String>> vmParamMap = this.getHandleParams(flowInstId);

		// 获取云服务定义参数
		Map<String, String> srvAttrInfoMap = getAutomationService().getServiceAttr(rrinfoId);

		// 根据資源请求Id获取设备列表(本次服务请求需要创建的虚拟机)初始化每台设备对应的参数值
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		if (deviceIdList != null) {
			/**
			 * 为每台设备准备weblogic的初始化参数
			 */
			this.buildWeblogicParam(vmParamMap, contenxtParams, deviceIdList, srvAttrInfoMap);

		}

		logger.info("weblogic构建所需参数实例化结束,服务请求ID:" + srvReqId + ",流程实例ID:" + flowInstId);

		return PubConstants.EXEC_RESULT_SUCC;
	}

	/**
	 * 构造初始化参数
	 * 
	 * @param allVMParamMap
	 *            虚拟机相关的参数集合
	 * @param contenxtParams
	 *            上下文参数集合
	 * @param deviceIdList
	 *            设备Id列表 ntp信息
	 * @param srvAttrInfoMap
	 *            云服务属性信息
	 * @throws Exception 
	 * @throws BizException 
	 */
	@SuppressWarnings("unchecked")
	private void buildWeblogicParam(Map<String, Map<String, String>> allVMParamMap, Map<String, Object> contenxtParams,
			List<String> deviceIdList, Map<String, String> srvAttrInfoMap) throws BizException, Exception {

		// 被管Server名称
		StringBuilder msAddrs = new StringBuilder(PubConstants.SINGLE_QUOTE_MARK);
		// Machine名称使用所在服务器的名称
		StringBuilder machineAddrs = new StringBuilder(PubConstants.SINGLE_QUOTE_MARK);
		// 虚拟机管理ip组，格式：ip1:ip2:ip3 ...
		StringBuffer vmManagementIps = new StringBuffer();
		// 虚拟机生产ip组，格式：ip1:ip2:ip3 ...
		StringBuffer vmProductionIps = new StringBuffer();

		int msAddrSn = 1;// 被管理server的顺序号
		int msAddrPort = Integer.parseInt(getAutomationService().getAppParameter("WLS.MS_ADDR_PORT_BEGIN"));// 被管Server的端口

		// 循环准备每台设备的信息
		for (String deviceId : deviceIdList) {

			logger.info("设备ID号为[" + deviceId + "]的Weblogic参数初始化开始.");

			// 单台设备的weblogic参数集合
			Map<String, String> weblogicParamMap = Maps.newHashMap();
			String deviceIdStr = deviceId.toString();
			contenxtParams.put(deviceIdStr, weblogicParamMap);
			this.setHandleResultParam(deviceIdStr, weblogicParamMap);

			// 添加所有云服务属性中定义的参数
			for (String attrName : srvAttrInfoMap.keySet()) {
				weblogicParamMap.put(attrName, srvAttrInfoMap.get(attrName));
			}

			/* 从虚拟机参数初始化类中移动过来的参数，用于创建用户、组、lv */
			weblogicParamMap.put(SAConstants.V_GROUP_ID, srvAttrInfoMap.get(SAConstants.V_GROUP_ID));// 用户组ID
			weblogicParamMap.put(SAConstants.V_GROUP_NAME, srvAttrInfoMap.get(SAConstants.V_GROUP_NAME));// 用户组ID
			weblogicParamMap.put(SAConstants.V_USER_ID, srvAttrInfoMap.get(SAConstants.V_USER_ID));// 用户ID
			weblogicParamMap.put(SAConstants.V_USER_PASSWD, srvAttrInfoMap.get(SAConstants.V_USER_PASSWD));// 用户密码
			weblogicParamMap.put(SAConstants.V_USER_NAME, srvAttrInfoMap.get(SAConstants.V_USER_NAME));// 用户名
			String apHomePath = getAutomationService().getAppParameter("VM.V_USER_DIR_PREFIX")
					+ srvAttrInfoMap.get(SAConstants.V_USER_NAME);
			weblogicParamMap.put(SAConstants.V_USER_DIR, apHomePath);// 用户目录位置

			weblogicParamMap.put(WeblogicConstants.V_WEBLOGIC_GROUP_NAME,
					this.getAutomationService().getAppParameter("WLS.GROUP_NAME"));// weblogic用户组名称

			// lv信息
			weblogicParamMap.put(SAConstants.V_LV_SIZE, srvAttrInfoMap.get(SAConstants.V_LV_SIZE));// lv容量
			weblogicParamMap.put(SAConstants.V_LV_NAME, srvAttrInfoMap.get(SAConstants.V_LV_NAME));// lv名称
			weblogicParamMap.put(SAConstants.V_VG_NAME, srvAttrInfoMap.get(SAConstants.V_VG_NAME));// VG名称
			weblogicParamMap.put(SAConstants.V_LV_MNT, srvAttrInfoMap.get(SAConstants.V_LV_MNT));// 挂载点
			/* end */

			// 虚拟机相关的参数
			Map<String, String> vmParamMap = allVMParamMap.get(deviceIdStr);
			String vmHostName = vmParamMap.get(VMFlds.VM_HOST_NAME);// 主机名
			String vmMgrIp = vmParamMap.get(SAConstants.SERVER_IP);// 虚拟机的管理IP
			String vmProdIp = vmParamMap.get(SAConstants.NIC_IP_PROD);// 生产IP
			// 生成wls管理ip，如果存在生产ip则使用生产ip，否则使用管理ip
			String wlsMsIp = null;
			if (vmProdIp != null && !StringUtils.isBlank(vmProdIp.trim())) {
				wlsMsIp = vmProdIp;
			} else {
				wlsMsIp = vmMgrIp;
			}
			// 生成虚拟机管理ip组参数
			if (vmManagementIps.length() > 0) {
				vmManagementIps.append(PubConstants.SEPARATOR_COLON);
			}
			vmManagementIps.append(vmMgrIp);
			// 生成虚拟机生产ip组参数
			if (vmProductionIps.length() > 0) {
				vmProductionIps.append(PubConstants.SEPARATOR_COLON);
			}
			vmProductionIps.append(vmProdIp);
			// ==================weblogic 相关的参数 begin =================
			weblogicParamMap.put(WeblogicConstants.APP_USER, srvAttrInfoMap.get(SAConstants.V_USER_NAME));// 应用用户名
			weblogicParamMap.put(WeblogicConstants.WLS_HOME, srvAttrInfoMap.get(WeblogicConstants.WLS_HOME));// weblogic安装目录
			weblogicParamMap.put(WeblogicConstants.DOMAIN_TEMPLATE_FILE,
					srvAttrInfoMap.get(WeblogicConstants.DOMAIN_TEMPLATE_FILE));// Domain模板文件位置
			weblogicParamMap.put(WeblogicConstants.DOMAIN_NAME, srvAttrInfoMap.get(WeblogicConstants.DOMAIN_NAME));// 目标Domain名称
			weblogicParamMap.put(WeblogicConstants.DOMAIN_DIR,
					apHomePath + "/domains/" + srvAttrInfoMap.get(WeblogicConstants.DOMAIN_NAME));// 目标Domain目录
			String adminPort = srvAttrInfoMap.get(WeblogicConstants.ADMIN_PORT);
			weblogicParamMap.put(WeblogicConstants.ADMIN_PORT, adminPort);// 管理服务器监听端口
			weblogicParamMap.put(WeblogicConstants.ADMIN_ADDR, PubConstants.SINGLE_QUOTE_MARK + vmHostName
					+ PubConstants.SEPARATOR_COLON + wlsMsIp + PubConstants.SEPARATOR_COLON + adminPort
					+ PubConstants.SINGLE_QUOTE_MARK);// Admin Server
			// 所属Machine、IP及端口号，用冒号分隔，如Machine1:127.0.0.1:7001
			weblogicParamMap.put(WeblogicConstants.WLS_USERNAME, srvAttrInfoMap.get(WeblogicConstants.WLS_USERNAME));// 目标Domain用户名
			weblogicParamMap.put(WeblogicConstants.WLS_PASSWORD, srvAttrInfoMap.get(WeblogicConstants.WLS_PASSWORD));// 目标Domain密码

			// 当前机器上被管理服务器的名称
			String mgrServerName = getAutomationService().getAppParameter("WLS.MS_SERVER_NAME_PREFIX") + msAddrSn;

			// 虚拟机的名称和地址集合
			StringBuilder vmServerNames = new StringBuilder(PubConstants.SINGLE_QUOTE_MARK);
			StringBuilder vmServerAddrs = new StringBuilder(PubConstants.SINGLE_QUOTE_MARK);

			if (msAddrSn == 1) {
				vmServerNames.append("AdminServer");
				vmServerNames.append(PubConstants.SEPARATOR_COLON);
				vmServerAddrs.append("AdminServer");
				vmServerAddrs.append(PubConstants.SEPARATOR_COLON);
				vmServerAddrs.append(wlsMsIp);
				vmServerAddrs.append(PubConstants.SEPARATOR_COLON);
				vmServerAddrs.append(adminPort);
				vmServerAddrs.append(PubConstants.SEPARATOR_SEMICOLON);
			}
			vmServerNames.append(mgrServerName);
			vmServerAddrs.append(mgrServerName);
			vmServerAddrs.append(PubConstants.SEPARATOR_COLON);
			vmServerAddrs.append(wlsMsIp);
			vmServerAddrs.append(PubConstants.SEPARATOR_COLON);
			vmServerAddrs.append(msAddrPort);

			vmServerNames.append(PubConstants.SINGLE_QUOTE_MARK);
			vmServerAddrs.append(PubConstants.SINGLE_QUOTE_MARK);

			msAddrSn++;

			weblogicParamMap.put(WeblogicConstants.VM_SERVER_NAMES, vmServerNames.toString());// 在当前VM上的server名称
			weblogicParamMap.put(WeblogicConstants.VM_SERVER_ADDRS, vmServerAddrs.toString());// 在当前VM上的Addr名称

			// 被管Server名称、所属Machine、IP及端口号
			msAddrs.append(mgrServerName + PubConstants.SEPARATOR_COLON + vmHostName + PubConstants.SEPARATOR_COLON
					+ wlsMsIp + PubConstants.SEPARATOR_COLON + msAddrPort + PubConstants.SEPARATOR_SEMICOLON);

			String nodeManagerPort = srvAttrInfoMap.get(WeblogicConstants.NODEMANAGER_PORT);
			weblogicParamMap.put(WeblogicConstants.NODEMANAGER_PORT, nodeManagerPort);// nodeManager端口
			weblogicParamMap.put(WeblogicConstants.NODEMANAGER_ADDR, wlsMsIp);// nodemanager监听地址

			// Machine名称及nodemanager IP及端口号
			machineAddrs.append(vmHostName + PubConstants.SEPARATOR_COLON + vmMgrIp + PubConstants.SEPARATOR_COLON
					+ nodeManagerPort + PubConstants.SEPARATOR_SEMICOLON);

			weblogicParamMap.put(WeblogicConstants.LOGFILES_NUMBER,
					srvAttrInfoMap.get(WeblogicConstants.LOGFILES_NUMBER));// 日志文件数量最大值
			weblogicParamMap.put(WeblogicConstants.ADMIN_MANAGE_ADDR, vmMgrIp);// Admin
			// Server的带管IP地址
			weblogicParamMap.put(WeblogicConstants.AS_JVM_PARA, srvAttrInfoMap.get(WeblogicConstants.AS_JVM_PARA));// Admin
			// Server对应的JVM参数
			weblogicParamMap.put(WeblogicConstants.MS_JVM_PARA, srvAttrInfoMap.get(WeblogicConstants.MS_JVM_PARA));// 被管Server对应的JVM参数
			weblogicParamMap.put(WeblogicConstants.START_OPTIONS, srvAttrInfoMap.get(WeblogicConstants.START_OPTIONS));// 服务器启动参数

			logger.info("设备ID号为[" + deviceId + "]的weblogic参数初始化 结束.");
			// ==================weblogic 相关的参数 end =================
		}

		// 云服务所需要的所有参数
		List<String> allParamNameList = Lists.newArrayList();
		// 云服务所需要的所有非空参数
		List<String> notNullParamList = Lists.newArrayList();
//		Collections.addAll(allParamNameList, getAutomationService().getAppParameter("WLS.PARAM.VALIDATE.ALL").split(
//								PubConstants.SEPARATOR_VERTICAL));
//		Collections.addAll(notNullParamList, getAutomationService().getAppParameter("WLS.PARAM.VALIDATE.NOTNULL")
//				.split(PubConstants.SEPARATOR_VERTICAL));

		msAddrs.append(PubConstants.SINGLE_QUOTE_MARK);
		machineAddrs.append(PubConstants.SINGLE_QUOTE_MARK);
		// 设置汇总后的参数值
		for (String deviceId : deviceIdList) {
			Map<String, String> weblogicParamMap = (Map<String, String>) contenxtParams.get(deviceId.toString());

			weblogicParamMap.put(WeblogicConstants.MS_ADDRS, msAddrs.toString());// 被管Server名称、所属Machine、IP及端口号
			weblogicParamMap.put(WeblogicConstants.MACHINE_ADDRS, machineAddrs.toString());// Machine名称及nodemanager
			// 新增的管理ip组和生产ip组参数
			weblogicParamMap.put("VM_MANAGEMENT_IPS", vmManagementIps.toString());
			weblogicParamMap.put("VM_PRODUCTION_IPS", vmProductionIps.toString());
			/**
			 * 对参数进行校验 1、校验参数的个数是否正确 2、校验参数的值是否为非空
			 */
			validateDeviceParam(allParamNameList, notNullParamList, weblogicParamMap);
		}

	}

	/**
	 * 校验虚拟机的参数是否合法
	 * 
	 * @param allParamNameList
	 *            需要的所有参数
	 * @param notNullParamList
	 *            不能为空参数
	 * @param weblogicParamMap
	 *            虚拟机设备的参数信息
	 * @throws Exception 
	 */
	private void validateDeviceParam(List<String> allParamNameList, List<String> notNullParamList,
			Map<String, String> weblogicParamMap) throws Exception {
		for (String paramName : allParamNameList) {

			if (StringUtils.isEmpty(paramName))
				continue;

			String paramValue = weblogicParamMap.get(paramName);
			if (null == paramValue) {
				String msg = "Weblogic云服务参数校验失败,缺少参数[" + paramName + "].";
				throw new Exception(msg);
			} else {

				if (notNullParamList.contains(paramName) && StringUtils.isEmpty(paramValue)) {

					String msg = "Weblogic云服务参数校验失败,参数[" + paramName + "]的值为空.";
					throw new Exception(msg);
				}
			}
		}
	}

}
