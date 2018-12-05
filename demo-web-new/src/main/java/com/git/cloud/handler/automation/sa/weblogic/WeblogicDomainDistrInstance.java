package com.git.cloud.handler.automation.sa.weblogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SAOpration;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;

/**
 * 脚本自动化执行组件
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 Aug 13, 2013
 * @see
 */
public class WeblogicDomainDistrInstance extends
		RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(WeblogicDomainDistrInstance.class);

	// 单台设备执行脚本的超时时间
	protected static final int TIME_OUT = 4 * 3600 * 1000;

	private final String SPLIT_SIGN = "@";
	private String localPath = "";
	private final String LOCAL_PATH = "TAR_TEMP_PATH";

	private final String TARGET_CMD = "TARGET_CMD";

	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 从contextParams取到设备Id列表，再根据设备id到表里取到所有跟设备相关的参数 循环设备id表执行操作
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 * @throws Exception 
	 * @throws BizException 
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) throws BizException, Exception {

		// 单台设备执行结果
		String devResult = "";

		// 所有设备执行的最终结果
		String finalResult = "";

		// 保存所有设备执行结果的map
		Map<String, String> resultsMap = Maps.newHashMap();
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contextParams.get(NODE_ID); // 要根据NODE_ID在工作流配置表中查到当前节点的业务所需参数
			// 资源请求ID
			String rrinfoId = (String) contextParams.get(RRINFO_ID);

			// 获取配置文件信息
			localPath = getAutomationService().getAppParameter(LOCAL_PATH) + "/" + flowInstId;
			
			long startTime = System.currentTimeMillis();

			// 读取虚拟机列表，用于取得参数信息
			@SuppressWarnings("unchecked")
			List<String> vmIds = (List<String>)contextParams.get("destVmIds");
			//获取虚机设备id
			String vmId = vmIds.get(0);
							
			String deviceLogStr = null;
			String logMsg = null;		

			try {
						
				deviceLogStr = "[设备:" + vmId + "]";
						
				logMsg = "X86虚机执行【WeblogicDomainDistrInstance】线程开始，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。" + deviceLogStr;
						
				log.debug(logMsg);
						
				IDataObject requestDataObject = DataObject.CreateDataObject();
				HeaderDO header = HeaderDO.CreateHeaderDO();
				
				// 增加数据中心路由标识
				String queueIdentify = this.getQueueIdent(rrinfoId);
				header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
				
				header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
				header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
				BodyDO body = BodyDO.CreateBodyDO();

				String script_deviceIp = contextParams.get(SAConstants.TARGET_SERVER_IP).toString();
				String script_userName = contextParams.get(SAConstants.TARGET_USER_NAME).toString();
				String script_pwd = contextParams.get(SAConstants.TARGET_USER_PWD).toString();
				String domainTarPath = contextParams.get(SAConstants.DOMAIN_TAR).toString();
				String nodemanagerTarPath = contextParams.get(SAConstants.NODEMANAGER_TAR).toString();
				String targetDir = contextParams.get(SAConstants.PACKAGE_DISTR_PATH).toString();

				@SuppressWarnings("unchecked")
				Map<String, String> deviceParamInfo = (Map<String, String>) contextParams.get(vmId.toString());

				String deviceIp = deviceParamInfo.get(SAConstants.SERVER_IP);
				String userName = deviceParamInfo.get(SAConstants.V_USER_NAME);
				String pwd = deviceParamInfo.get(SAConstants.V_USER_PASSWD);
				header.setOperation(SAOpration.EXEC_PACKAGE_DISTR);
				requestDataObject.setDataObject(MesgFlds.HEADER, header);
				body.setString(SAConstants.TARGET_SERVER_IP, deviceIp);
				body.setString(SAConstants.TARGET_USER_NAME, userName);
				body.setString(SAConstants.TARGET_USER_PWD, pwd);
				body.setString(SAConstants.PACKAGE_DISTR_PATH, targetDir);
				body.setString(SAConstants.IS_CHMOD, "N");

				body.setString(SAConstants.SERVER_IP, script_deviceIp);
				body.setString(SAConstants.USER_NAME, script_userName);
				body.setString(SAConstants.USER_PASSWORD, script_pwd);

				// 下发第一个包
				devResult = distrTarToDestinationVM(contextParams, body,
						requestDataObject, domainTarPath);
				resultsMap.put(vmId + SAConstants.DOMAIN_TAR, devResult);
				// 下发第二个包
				devResult = distrTarToDestinationVM(contextParams, body,
						requestDataObject, nodemanagerTarPath);
				resultsMap.put(vmId + SAConstants.NODEMANAGER_TAR, devResult);

				// 得到所有设备执行完毕后的最终结果
				finalResult = getFinalResult(resultsMap);
				log.debug("finalResult=" + finalResult);
			} catch (Exception e) {
				String errorMsg = logMsg + "错误：{" + e + "}" + deviceLogStr;
				log.error(errorMsg);
				printExceptionStack(e);
				// Add new version return 
				String instanceAutomationReturn = getInstanceStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						vmId,errorMsg);
				log.info(instanceAutomationReturn);
				return instanceAutomationReturn;
			} finally {

				// 尽快的释放内存
				if (contextParams != null)
					contextParams.clear();
			}

			log.debug(
					"20131210 debugging,WeblogicDomainDistrInstance,excute的执行结果:{}",
					finalResult);
			log.debug("线程执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
		}else{
			return "ERR_PARAMETER_WRONG;contextParams is null";
		}
		return finalResult;
	}

	/**
	 * 获取资源操作的超时时间
	 * 
	 * @return
	 */
	protected int getTimeOut() {
		return TIME_OUT;
	}

	/**
	 * @param contenxtParams
	 * @param body
	 * @param requestDataObject
	 * @param tarPath
	 * @return
	 */
	private String distrTarToDestinationVM(
			HashMap<String, Object> contenxtParams, BodyDO body,
			IDataObject requestDataObject, String tarPath) {
		
		String tarNameNodeManager = tarPath
				.substring(tarPath.lastIndexOf("/") + 1);
		String path = tarPath.substring(0, tarPath.lastIndexOf('/'));

		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setInt(SAConstants.CHARSET, 0);
		body.set(SAConstants.EXEC_SHELL, path + SPLIT_SIGN + tarNameNodeManager
				+ SPLIT_SIGN + localPath);
		body.set(TARGET_CMD, path + SPLIT_SIGN + tarNameNodeManager
				+ SPLIT_SIGN + localPath);

		requestDataObject.setDataObject(MesgFlds.BODY, body);

		String result = "1";
		// 调用适配器执行操作
		IDataObject responseDataObject;
		try {
			responseDataObject = getResAdpterInvoker().invoke(
					requestDataObject, getTimeOut());
			// 处理返回结果
			handleResonpse(contenxtParams, responseDataObject);
			result = getHandleResult(responseDataObject);
		} catch (Exception e) {
			log.error("发生错误"+e);
		}
		// 获取操作执行结果状态
		return result;
	}

	/**
	 * 遍历resultsMap，
	 * 如有执行失败码，
	 * 返回9997。
	 * @param resultsMap
	 * @return
	 */
	protected String getFinalResult(Map<String, String> resultsMap) {
		String finalResult = null;	//MesgRetCode.SUCCESS;
		boolean isError = false;
		for (String key : resultsMap.keySet()) {
			String result = resultsMap.get(key);
			if (!result.equals(MesgRetCode.SUCCESS)) {
				log.debug("下发weblogic域在设备Id【" + key + "】上返回为【" + result
						+ "】，执行失败！");
				isError = true;
				if (finalResult != null) {
					finalResult += ", ";
				}
				finalResult += result;
			} else {
				log.debug("下发weblogic域在设备Id【" + key + "】上返回为【" + result
						+ "】，执行成功！");
			}
		}
		if (!isError) {
			finalResult = MesgRetCode.SUCCESS;
		}
		return finalResult;
	}
	
	@Override
	protected String getHandleResult(IDataObject responseDataObject) {
		HeaderDO header = (HeaderDO) responseDataObject.get(MesgFlds.HEADER);
		if (header.getString(MesgFlds.RET_CODE).equals(MesgRetCode.SUCCESS)) {
			return MesgRetCode.SUCCESS;
		} else {
			return header.getRetMesg();
		}
//		return header.getString(MesgFlds.RET_CODE);
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {

	}

}
