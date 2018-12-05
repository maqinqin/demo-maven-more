package com.git.cloud.handler.automation.sa.ssh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.Constants;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.SAOpration;
import com.git.support.constants.PubConstants;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;

/**
 * 
 * <p>
 * 
 * @author HouDongsheng
 * @version 1.0 2013-5-11
 * @see
 */
public class ScriptDistrAutomationInstance extends
		BaseInstance {
	private static Logger log = LoggerFactory
			.getLogger(ScriptDistrAutomationInstance.class);

	// 默认的超时时间
	private static final int TIME_OUT = 60 * 60 * 1000;
	private final String SPLIT_SIGN = "@";
	private String localPath = "";
	private final String LOCAL_PATH = "TAR_TEMP_PATH";
	private final String PACKAGE_DISTR_PATH = "PACKAGE_DISTR_PATH";

	private final String TARGET_USER_NAME = "TARGET_USER_NAME";
	private final String TARGET_USER_PWD = "TARGET_USER_PWD";
	private final String TARGET_SERVER_IP = "TARGET_SERVER_IP";
	private final String TARGET_CMD = "TARGET_CMD";

	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 * @throws Exception 
	 * @throws BizException 
	 */
	@SuppressWarnings("unchecked")
	public String execute(HashMap<String, Object> contextParams) throws BizException, Exception {
		String result = null;
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contextParams.get(NODE_ID);
			
			String rrinfoId = (String) contextParams.get(RRINFO_ID);

			localPath = getAutomationService().getAppParameter(LOCAL_PATH) + "/" + flowInstId;

			// long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

			// 获取全局业务参数
			Map<String, Map<String, String>> handleParams = this
					.getHandleParams(flowInstId);

			// 将工作流相关参数和业务参数合并
			contextParams.putAll(handleParams);
			
			String deviceLogStr = null;
			String logMsg = null;	
			
			// 读取虚拟机列表，用于取得参数信息
			List<String> vmIds = (List<String>) contextParams.get("destVmIds");
			String vmId = vmIds.get(0);

			try {
				
				deviceLogStr = "[设备:" + vmId + "]";
				
				logMsg = "虚机执行【ScriptDistrAutomationInstance】线程，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。" + deviceLogStr;

				IDataObject requestDataObject = DataObject.CreateDataObject();
				HeaderDO header = HeaderDO.CreateHeaderDO();
				BodyDO body = BodyDO.CreateBodyDO();
				requestDataObject.setDataObject(MesgFlds.HEADER, header);
				
				header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
				header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());

				String packagePath = contextParams.get(
						ScriptDistrAutomationHandler.PACKAGE_PATH).toString();
				String packageDistrPath = contextParams.get(
						ScriptDistrAutomationHandler.PACKAGE_DISTR_PATH).toString();
				String tarName = contextParams.get(
						ScriptDistrAutomationHandler.TAR_NAME).toString();

				Map<String, String> deviceParamInfo = (Map<String, String>) contextParams.get(vmId.toString());
				
				String automationType = getContextStringPara(contextParams, "AUTOMATION_TYPE");
				if (automationType != null && automationType.equalsIgnoreCase("RESOURCE_MANAGEMENT")) {
					// 从流程初始化参数表中读取所需数据
					header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), deviceParamInfo.get(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue()));
					body.setString(SAConstants.SERVER_IP, deviceParamInfo.get("SCRIPT_SERVER_IP"));
					body.setString(SAConstants.USER_NAME, deviceParamInfo.get("SCRIPT_SERVER_USER"));
					body.setString(SAConstants.USER_PASSWORD, deviceParamInfo.get("SCRIPT_SERVER_PASSWORD"));
				} else if (automationType == null || automationType.equalsIgnoreCase("CLOUD_REQUEST")) {
					// 从资源请求中读取所需数据
					String queueIdentify = this.getQueueIdent(rrinfoId);
					header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
					//根据资源请求id取得数据中心id
					String dataCenterId = this.getDatacenterId(rrinfoId);
					//根据数据中心id取得脚本服务器信息并装入body
					body = this.putScriptServerInfo(body, dataCenterId);
				} else {
					throw new Exception("AUTOMATION_TYPE Error, value should be RESOURCE_MANAGEMENT or CLOUD_REQUEST, but value is : " + automationType);
				}
				String deviceIp = deviceParamInfo.get(SAConstants.SERVER_IP);
				String userName = deviceParamInfo.get(SAConstants.USER_NAME);
				String pwd = deviceParamInfo.get(SAConstants.USER_PASSWORD);
				// 执行脚本下发需要的参数
				header.setOperation(SAOpration.EXEC_PACKAGE_DISTR);
				requestDataObject.setDataObject(MesgFlds.HEADER, header);
				body.setString(TARGET_SERVER_IP, deviceIp);
				body.setString(TARGET_USER_NAME, userName);
				body.setString(TARGET_USER_PWD, pwd);
				body.setBoolean(SAConstants.IS_TOARRAY, false);
				body.setBoolean(SAConstants.IS_KEY, false);
				body.setInt(SAConstants.CHARSET, 0);
				body.set(TARGET_CMD, packagePath + SPLIT_SIGN + tarName + SPLIT_SIGN + localPath);
				body.set(SAConstants.EXEC_SHELL, packagePath + SPLIT_SIGN + tarName + SPLIT_SIGN + localPath);
				// 目标服务器的路径 @ tarname @ 本地路径
				/*if (packagePath.startsWith("//")) {
					packagePath.substring(1);
				}*/
				String packageBasePath = getContextStringPara(contextParams, "PACKAGE_BASE_PATH");
				if (packageBasePath != null) {
					if (packageBasePath.endsWith("/")) {
						packageBasePath = packageBasePath.substring(0, packageBasePath.length() - 1);
					}
					packagePath = packageBasePath + packagePath;
				}
				packageDistrPath = packagePath;
				log.info("=====================" + packagePath + SPLIT_SIGN + tarName + SPLIT_SIGN + localPath);
				body.setString(PACKAGE_DISTR_PATH, packageDistrPath);


				requestDataObject.setDataObject(MesgFlds.BODY, body);

				// 调用适配器执行操作
				IDataObject responseDataObject;
				responseDataObject = getResAdpterInvoker().invoke(
						requestDataObject, getTimeOut());

				// 处理返回结果
				log.debug("发送调用请求，请求信息为：" + Constants.BR + getRequestDataObjectContent(requestDataObject) + Constants.BR +
						"Request timeout is(ms):" + Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()));
				handleResonpse(contextParams, responseDataObject);
				// 显示执行命令的结果
				logResponseData(responseDataObject);
				// 获取操作执行结果状态
				result = getHandleResult(responseDataObject);

				if (!PubConstants.EXEC_RESULT_SUCC.equals(result)) {
					log.error("upload failed deviceId" + vmId);
					//return result;
				}
				
				// Add new version return 
				String instanceAutomationReturn = getInstanceAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						vmId,responseDataObject);
				log.info("下发脚本Instance返回：" + instanceAutomationReturn);
				return instanceAutomationReturn;

			} catch (Exception e) {
				return instaceHandleException(contextParams, vmId,
						deviceLogStr, logMsg, e);
			} finally {

				// 尽快的释放内存
				if (contextParams != null)
					contextParams.clear();
			}
		}else{
			return "ERR_PARAMETER_WRONG;contextParams is null";
		}
	}

	/**
	 * 构造请求数据对象
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @return
	 */
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		return null;
	}

	/**
	 * 获取资源操作的超时时间，各资源操作实现Handler类可以重载此方法获取返回超时时间 比如从脚本配置表中配置中获取脚边执行超时时间
	 * 
	 * @return
	 */
	protected int getTimeOut() {

		return TIME_OUT;
	}

	/**
	 * 对操作返回对象进行处理，比如保存返回参数
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @param responseDataObject
	 *            资源操作适配器返回的结果
	 */
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {

		BodyDO rspBody = responseDataObject.getDataObject(MesgFlds.BODY,
				BodyDO.class);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> sshSaResult = (HashMap<String, Object>) rspBody
				.getContianer().get("SSH_SA_RESULT");

		responseDataObject.set(EXEC_RESULT, sshSaResult.get("SSH_SA_RESULT")
				.toString());
	}

	/**
	 * 获取操作结果
	 * 
	 * @param responseDataObject
	 *            资源操作适配器返回的结果
	 * @return true/false
	 */
	protected String getHandleResult(IDataObject responseDataObject) {

		return responseDataObject.getString(EXEC_RESULT);
	}

}