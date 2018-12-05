package com.git.cloud.handler.automation.sa.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.common.ScriptUtil;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SAOpration;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmGlobalConstants;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * <p>
 * 
 * @author HouDongsheng
 * @version 1.0 
 * @see
 */
public class PingX86Instance extends BaseInstance {
	private static Logger log = LoggerFactory.getLogger(PingX86Instance.class);

	public static final int EXECUTE_SUCCESS_CODE = 0;
	public static final String EXIT_CODE = "EXIT_CODE";

	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public String execute(HashMap<String, Object> contextParams) throws Exception {
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contextParams.get(NODE_ID);

//			// 服务请求Id
//			String srvReqId = (String) contenxtParams.get(SRV_REQ_ID);
			
			// 资源请求ID
			String rrinfoId = (String) contextParams.get(RRINFO_ID);

			String pingShellNum = getAutomationService().getAppParameter("VM.V_PING_COUNT");
			
			// 读取虚拟机列表，用于取得参数信息
//			@SuppressWarnings("unchecked")
			List<String> vmIds = (List<String>)contextParams.get("destVmIds");

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

			//获取虚机设备id
			String deviceId = vmIds.get(0);								
			String deviceLogStr = null;
			String logMsg = null;	
			try {
				deviceLogStr = "[设备:" + deviceId + "]";			
				logMsg = "X86虚机执行【PingX86Instance】线程，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。" + deviceLogStr;
				
				// 获取全局业务参数
				Map<String, Map<String, String>> handleParams = this
						.getHandleParams(flowInstId);
				contextParams.putAll(handleParams);

				IDataObject requestDataObject = DataObject.CreateDataObject();
				HeaderDO header = HeaderDO.CreateHeaderDO();
				BodyDO body = BodyDO.CreateBodyDO();
//				List<String> deviceIdList = 
				getAutomationService().getDeviceIdsSort(rrinfoId);

				String vmId = vmIds.get(0);

				// 獲取scriptId列表
				List<String> scriptIdList = ScriptUtil.getScriptIdList(contextParams);

				if (scriptIdList == null || scriptIdList.size() == 0) {
					throw new BizException("没有配置脚本，请检查!");
				}

				String scriptId = scriptIdList.get(0);
				ScriptModelVO script = getAutomationService().getScript(scriptId);
				String scriptName = script.getFileName();
				String scriptPathAndName = getAutomationService().getScriptFullPath(scriptId);

				Map<String, String> deviceParamInfo = (Map<String, String>) contextParams
						.get(vmId.toString());

				deviceParamInfo.put(RRINFO_ID, rrinfoId.toString());

				String nicIpProd = deviceParamInfo.get(SAConstants.NIC_IP_PROD);// 生产IP
				String nicIpMgmt = deviceParamInfo.get(SAConstants.NIC_IP_MGMT);// 管理Ip

				// ping同网段的254
				String cmdProd = "";
				String cmdMgmt = "";
				List<String> cmdList = new ArrayList<String>();
				if (!StringUtils.isEmpty(nicIpProd)) {

					nicIpProd = nicIpProd.substring(0,
							nicIpProd.lastIndexOf(".") + 1) + "254";

					if (deviceParamInfo.get(VMFlds.VM_TYPE).equals(
							VmGlobalConstants.VM_TYPE_LINUX)) {
						cmdProd = scriptPathAndName + " " + nicIpProd + " "
								+ pingShellNum + "\"";
					}
					if (deviceParamInfo.get(VMFlds.VM_TYPE).equals(
							VmGlobalConstants.VM_TYPE_WIN)) {
						cmdProd = scriptName + VmGlobalConstants.SPLIT_FLAG
								+ nicIpProd + VmGlobalConstants.SPLIT_FLAG
								+ pingShellNum;
					}
					// // 将执行命令LINUX_ping.sh放入body里.
					// setBodyInfo(deviceParamInfo, header, body,
					// requestDataObject, cmdProd);
					//
					// // 获得结果
					// devResult = execGetResult(vmId, requestDataObject,
					// deviceParamInfo, flowInstId, nodeId);
					// resultsMap.put(vmId + SAConstants.NIC_IP_PROD, devResult);
				}

				if (!StringUtils.isEmpty(nicIpMgmt)) {
					nicIpMgmt = nicIpMgmt.substring(0,
							nicIpMgmt.lastIndexOf(".") + 1) + "254";

					if (deviceParamInfo.get(VMFlds.VM_TYPE).equals(
							VmGlobalConstants.VM_TYPE_LINUX)) {
						cmdMgmt = scriptPathAndName + " " + nicIpMgmt + " "
								+ pingShellNum + "\"";
					}
					if (deviceParamInfo.get(VMFlds.VM_TYPE).equals(
							VmGlobalConstants.VM_TYPE_WIN)) {
						cmdMgmt = scriptName + VmGlobalConstants.SPLIT_FLAG
								+ nicIpMgmt + VmGlobalConstants.SPLIT_FLAG
								+ pingShellNum;
					}

					// // 执行命令LINUX_ping.sh
					// setBodyInfo(deviceParamInfo, header, body,
					// requestDataObject, cmdMgmt);
					//
					// // 获得结果
					// devResult = execGetResult(vmId, requestDataObject,
					// deviceParamInfo, flowInstId, nodeId);
					// resultsMap.put(vmId + SAConstants.NIC_IP_MGMT, devResult);
				}

				cmdList.add(cmdMgmt);
				cmdList.add(cmdProd);

				setBodyInfo(deviceParamInfo, header, body, requestDataObject,
						cmdList);
				
				// 调用适配器执行操作
				IDataObject responseDataObject;
				responseDataObject = getResAdpterInvoker().invoke(requestDataObject,
						getTimeOut());

				String className = this.getClass().getName();
				return instanceHandleNormal(contextParams, flowInstId, nodeId,
						deviceId, startTime, deviceLogStr, className, true, responseDataObject);

			} catch (Exception e) {
				return instaceHandleException(contextParams, deviceId,
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
	 * 获取操作结果
	 * 
	 * @param responseDataObject
	 *            资源操作适配器返回的结果
	 * @return true/false
	 */
	protected String getHandleResult(IDataObject responseDataObject) {
		return responseDataObject.getString(EXEC_RESULT);
	}

	/**
	 * 调用适配器获得返回结果
	 * 
	 * @param requestDataObject
	 * @param contenxtParams
	 * @param flowInstId
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	String execGetResult(String devId, IDataObject requestDataObject,
			Map<String, String> contenxtParams, String flowInstId, String nodeId)
			throws Exception {
		String result = "";
		// 调用适配器执行操作
		IDataObject responseDataObject;
		responseDataObject = getResAdpterInvoker().invoke(requestDataObject,
				getTimeOut());
		// 处理返回结果
		handleResonpse(devId, contenxtParams, responseDataObject);
		// 获取操作执行结果状态
		result = getHandleResult(responseDataObject);

		return result;
	}

	/**
	 * 执行命令放入body里
	 * 
	 * @param body
	 * @param requestDataObject
	 * @param cmdShell
	 * @throws Exception 
	 */
	private void setBodyInfo(Map<String, String> contextParams,
			HeaderDO header, BodyDO body, IDataObject requestDataObject,
			List<String> cmdList) throws Exception {
		
		
		String rrinfoId = (String) contextParams.get(RRINFO_ID);
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		
		body.setInt(SAConstants.CHARSET, 0);
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setString(SAConstants.EXEC_FLAG, "independent"); // independent独立
		body.setString(SAConstants.LOCALPRIKEY_URL, "");
		
		if(contextParams.get(VMFlds.VM_TYPE).equals(VmGlobalConstants.VM_TYPE_LINUX)){
			header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
			header.setOperation(SAOpration.EXEC_SHELL);
			body.setString(SAConstants.SERVER_IP,
					contextParams.get(SAConstants.SERVER_IP));
			body.setString(SAConstants.USER_NAME,
					contextParams.get(SAConstants.USER_NAME).toString());
			body.setString(SAConstants.USER_PASSWORD,
					contextParams.get(SAConstants.USER_PASSWORD).toString());
			body.setList(SAConstants.EXEC_SHELL, cmdList); // 至此body生成完毕
		}
		
		if(contextParams.get(VMFlds.VM_TYPE).equals(VmGlobalConstants.VM_TYPE_WIN)){
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperation(VMOpration.EXECUTE_GUEST_BAT);
			String esxiUrl = contextParams.get(VMFlds.ESXI_URL).toString();        
			String esxiUname = contextParams.get(VMFlds.ESXI_USERNAME).toString();
			String esxiPwd = contextParams.get(VMFlds.ESXI_PASSWORD).toString();
			body.setString(VMFlds.ESXI_URL, esxiUrl);
			body.setString(VMFlds.ESXI_USERNAME, esxiUname);
			body.setString(VMFlds.ESXI_PASSWORD, esxiPwd);
			body.setString(VMFlds.GUEST_USER_NAME,
					contextParams.get(SAConstants.USER_NAME).toString());
			body.setString(VMFlds.GUEST_PASSWORD,
					contextParams.get(SAConstants.USER_PASSWORD).toString());
			String vappName = contextParams.get(VMFlds.VAPP_NAME).toString();
			body.setString(VMFlds.VAPP_NAME, vappName);
			body.setList(VMFlds.SCRIPT_LIST_NAME, cmdList);   //Windows
		}
		
		requestDataObject.setDataObject(MesgFlds.HEADER, header);
		requestDataObject.setDataObject(MesgFlds.BODY, body);
		
	}

	/**
	 * 对操作返回对象进行处理，比如保存返回参数
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @param responseDataObject
	 *            资源操作适配器返回的结果
	 */
	@SuppressWarnings("rawtypes")
	protected void handleResonpse(String devId,
			Map<String, String> contextParams, IDataObject responseDataObject) {	
		
		this.logResponseData(responseDataObject);

		if(contextParams.get(VMFlds.VM_TYPE).equals(VmGlobalConstants.VM_TYPE_LINUX)){
			BodyDO rspBody = responseDataObject.getDataObject(MesgFlds.BODY,
					BodyDO.class);
			
			String allScriptExitCode = MesgRetCode.SUCCESS;

			List sshSaResult = (List) rspBody.get(SAConstants.SSH_SA_RESULT);

			// 预先定好的返回结果参数列表
//			@SuppressWarnings("unchecked")
//			List<Object[]> outParamList = (List<Object[]>) contextParams
//					.get(SAConstants.OUT_PARAM_LIST);

			for (int i = 0; i < sshSaResult.size(); i++) {
				HashMap map = (HashMap) sshSaResult.get(i);
				int exitCode = Integer.parseInt(map.get(SAConstants.EXIT_CODE).toString());
				List echoInfoLineList = (List) map.get(SAConstants.ECHO_INFO);
				List errorInfoLineList = (List) map.get(SAConstants.ERROR_INFO);

				switch (exitCode) {
				case EXECUTE_SUCCESS_CODE:
					log.debug("脚本【ping.sh/pingserver.bat】执行成功，但没有返回值！！");
					break;
				default:
					StringBuffer sb = new StringBuffer();
					for (int j1 = 0; j1 < echoInfoLineList.size(); j1++) {
						sb.append(echoInfoLineList.get(j1).toString());
					}
					for (int j2 = 0; j2 < errorInfoLineList.size(); j2++) {
						sb.append(errorInfoLineList.get(j2).toString());
					}
					if (sb.length() != 0 && !sb.toString().equals("")) {
						log.debug("脚本【ping.sh/pingserver.bat】执行失败，有返回值，写数据库");
						setHandleResultParam(devId.toString(), SAConstants.ERROR_INFO, sb.toString());
					} else {
						log.debug("脚本【ping.sh/pingserver.bat】执行失败，但没有返回值！！");
					}
					log.debug("脚本【ping.sh/pingserver.bat】执行失败!!!");
					allScriptExitCode = MesgRetCode.ERR_PROCESS_FAILED; // 只要一个脚本未执行成功，EXEC_RESULT为N
					break;
				}
			}
			responseDataObject.set(EXEC_RESULT, allScriptExitCode);
		}
		
		if(contextParams.get(VMFlds.VM_TYPE).equals(VmGlobalConstants.VM_TYPE_WIN)){
			HeaderDO rspHeader = responseDataObject.getDataObject(MesgFlds.HEADER,
					HeaderDO.class);			
			String execResult = rspHeader.get(MesgFlds.RET_CODE).toString();
			responseDataObject.set(EXEC_RESULT, execResult);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.iomp.cloud.core.automation.handler.RemoteAbstractAutomationHandler
	 * #handleResonpse(java.util.Map,
	 * com.ccb.iomp.cloud.pub.sdo.inf.IDataObject)
	 */
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {

	}
	
	/**
	 * @param resultsMap
	 * @return
	 */
	protected String getFinalResult(Map<String,String> resultsMap){
		String finalResult = MesgRetCode.SUCCESS;
		for (String key : resultsMap.keySet()){
			String result = resultsMap.get(key);
			if(!result.equals(MesgRetCode.SUCCESS)){
				log.debug("ping.sh/pingserver.bat在设备Id【" + key +"】上返回码为【" + result + "】，执行失败！");
				finalResult = MesgRetCode.ERR_PROCESS_FAILED;
			}else 	
				log.debug("ping.sh/pingserver.bat在设备Id【" + key +"】上返回码为【" + result + "】，执行成功！");
		}
		return finalResult;
	}

}
