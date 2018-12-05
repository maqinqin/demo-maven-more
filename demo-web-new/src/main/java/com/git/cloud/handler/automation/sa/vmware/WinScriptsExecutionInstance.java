package com.git.cloud.handler.automation.sa.vmware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.common.ScriptUtil;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmGlobalConstants;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Maps;

/**
 * 脚本自动化执行组件
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 May 8, 2013
 * @see
 */

public class WinScriptsExecutionInstance extends BaseInstance {

	private static Logger log = LoggerFactory.getLogger(WinScriptsExecutionInstance.class);

	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 从contextParams取到设备Id列表，再根据设备id到表里取到所有跟设备相关的参数 循环设备id表执行操作
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) {

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

			// 读取虚拟机列表，用于取得参数信息
			@SuppressWarnings("unchecked")
			List<String> vmIds = (List<String>) contextParams.get("destVmIds");

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

			// 获取虚机设备id
			String deviceId = vmIds.get(0);

			String deviceLogStr = null;
			String logMsg = null;

			try {

				deviceLogStr = "[设备:" + deviceId + "]";

				logMsg = "X86虚机执行【WinScriptsExecutionInstance】线程，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。"
						+ deviceLogStr;

				// 获取全局业务参数
				Map<String, String> handleParams = this.getHandleParams(flowInstId, deviceId);

				// 将工作流相关参数和业务参数合并
				contextParams.putAll(handleParams);

				// 获取脚本Id列表
				List<String> scriptIdList = ScriptUtil.getScriptIdList(contextParams);

				if (scriptIdList != null && scriptIdList.size() != 0) {

					List<String> scriptNameList = new ArrayList<String>();

					// SCRIPT_ARGS_LIST
					List<String> scriptArgsList = new ArrayList<String>();

					List<Object[]> outParamList = new ArrayList<Object[]>();

					for (String scriptId : scriptIdList) {
						log.debug("执行获取脚本名List以及参数名List操作开始,设备ID:{},脚本ID:{}", deviceId, scriptId);
						String scriptName = getAutomationService().getScript(scriptId).getFileName();

						String scriptArgs = generateScriptArgs(scriptId, contextParams);
						scriptNameList.add(scriptName + " " + scriptArgs);
					}
					// 将脚本名的List和参数名的List装入contextParams
					Map<String, Object> extHandleParams = getExtHandleParams(scriptNameList, scriptArgsList); // scriptArgsList实际上没用到
					if (extHandleParams != null)
						contextParams.putAll(extHandleParams);

					// 将OutParamMap装入上下文，用于下面的验证
					if (!outParamList.isEmpty())
						contextParams.put(SAConstants.OUT_PARAM_LIST, outParamList);

					// 构造自动化操作请求参数
					IDataObject requestDataObject = buildRequestData(contextParams);

					// 调用适配器执行操作
					IDataObject responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());

					// 处理返回结果
					handleResonpse(deviceId, contextParams, responseDataObject);

					// 获取操作执行结果状态
					devResult = getHandleResult(responseDataObject);

					// 将单台设备执行结果放入map中
					resultsMap.put(deviceId, devResult);

					String className = this.getClass().getName();
					return instanceHandleNormal(contextParams, flowInstId, nodeId, deviceId, startTime, deviceLogStr,
							className, true, responseDataObject);

				} else {
					throw new Exception("没有配置脚本，请检查!");
				}

			} catch (Exception e) {
				return instaceHandleException(contextParams, deviceId, deviceLogStr, logMsg, e);
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
	 * 获取全局业务参数
	 * 
	 * @param flowInstId
	 * @param devId
	 * @return
	 * @throws Exception 
	 * @throws BizException 
	 */
	protected Map<String, String> getHandleParams(String flowInstId, String devId) throws BizException, Exception {

		log.debug("开始获取业务全局参数,流程实例ID:{},设备ID:{}", flowInstId, devId);

		List<BizParamInstPo> paramInsts = getBizParamInstService().getInstanceParas(flowInstId, devId);
				
		Map<String, String> handleParams = Maps.newHashMap();

		if (paramInsts != null) {

			for (BizParamInstPo paramInstPo : paramInsts) {
				handleParams.put(paramInstPo.getParamKey(), paramInstPo.getParamValue());
			}
		}

		log.debug("完成获取业务全局参数,流程实例ID:{},设备ID:{}", flowInstId, devId);

		return handleParams;
	}

	/**
	 * 获取流程节点的脚本执行参数，以及脚本在远程机器上存放路径和脚本名
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @return
	 * @throws Exception 
	 * @throws BizException 
	 */
	protected String generateScriptArgs(String scriptId, Map<String, Object> contextParams) throws BizException, Exception {

		StringBuffer sbArgs = new StringBuffer();

		List<String> paramKeyArrayList = getAutomationService().getScriptParasSort(scriptId);

		/**
		 * SP表里的order_number是参数的执行顺序！
		 * findParamKeyByScriptId方法应经将查询结果按order_number升序排列
		 * */
		if (!paramKeyArrayList.isEmpty() && paramKeyArrayList.size() != 0 && !paramKeyArrayList.contains(null)) {
			for (String key : paramKeyArrayList) {
				if (contextParams.get(key.trim()) != null) {
					sbArgs.append(VmGlobalConstants.SPLIT_FLAG);
					log.debug(contextParams.get(key.trim()).toString());
					String paramValue = contextParams.get(key.trim()).toString();

					sbArgs.append(paramValue);
				}
			}
		}

		// sbArgs.append("\"");
		log.debug(sbArgs.toString());
		return sbArgs.toString();
	}

	/**
	 * 将脚本执行命令放入上下文中
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @return
	 */
	protected Map<String, Object> getExtHandleParams(List<String> scriptNameList, List<String> scriptArgsList) {

		Map<String, Object> exthandleParams = Maps.newHashMap();

		exthandleParams.put(SAConstants.CMD_LIST, scriptNameList);

		exthandleParams.put(VMFlds.SCRIPT_ARGS_LIST_NAME, scriptArgsList);

		return exthandleParams;
	}

	/**
	 * 构造请求数据对象
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contextParams) throws Exception {

		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();

		// 资源请求ID
		String rrinfoId = (String) contextParams.get(RRINFO_ID);
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);

		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.EXECUTE_GUEST_BAT);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();

		/**
		 * 要知道Key的命名规则： 脚本路径、脚本名、执行参数等 然后从contenxtParmas中将以上这些Key对应的值取出来拼成cmd
		 */
		body.setString(SAConstants.SERVER_IP, contextParams.get(SAConstants.SERVER_IP).toString());
		body.setString(SAConstants.USER_NAME, contextParams.get(SAConstants.USER_NAME).toString());
		body.setString(SAConstants.USER_PASSWORD, contextParams.get(SAConstants.USER_PASSWORD).toString());
		body.setInt(SAConstants.CHARSET, 0);
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setString(SAConstants.EXEC_FLAG, "independent"); // independent独立
		body.setString(SAConstants.LOCALPRIKEY_URL, "");

		String esxiUrl = contextParams.get(VMFlds.VCENTER_URL).toString();
		String esxiUname = contextParams.get(VMFlds.VCENTER_USERNAME).toString();
		String esxiPwd = contextParams.get(VMFlds.VCENTER_PASSWORD).toString();
		body.setString(VMFlds.ESXI_URL, esxiUrl);
		body.setString(VMFlds.ESXI_USERNAME, esxiUname);
		body.setString(VMFlds.ESXI_PASSWORD, esxiPwd);
		body.setString(VMFlds.GUEST_USER_NAME, contextParams.get(SAConstants.USER_NAME).toString());
		body.setString(VMFlds.GUEST_PASSWORD, contextParams.get(SAConstants.USER_PASSWORD).toString());
		
		String vappName = contextParams.get(VMFlds.VAPP_NAME).toString();
		body.setString(VMFlds.VAPP_NAME, vappName);

		List<String> cmdList = (List<String>) contextParams.get(SAConstants.CMD_LIST);
		body.setList(VMFlds.SCRIPT_LIST_NAME, cmdList); // 至此body生成完毕

		reqData.setDataObject(MesgFlds.BODY, body);

		return reqData;
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
	protected void handleResonpse(String devId, Map<String, Object> contextParams, IDataObject responseDataObject) {

		if (contextParams.get(SAConstants.CMD_LIST) != null && !contextParams.get(SAConstants.CMD_LIST).equals("")) {
			log.debug("【执行的命令】为" + contextParams.get(SAConstants.CMD_LIST).toString());
		}

		log.debug("【消息队列返回的responseDataObject】为");
		this.logResponseData(responseDataObject);

		HeaderDO rspHeader = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);

		String execResult = rspHeader.get(MesgFlds.RET_CODE).toString();

		responseDataObject.set(EXEC_RESULT, execResult);

	}

	/**
	 * 将适配器返回的脚本执行结果用其中第一个“=”隔开
	 * 
	 * @param info
	 * @return
	 */
	protected static String[] parseReturnedInfoByFirstEqual(String info) {
		return info.split("=", 2);
	}

	/**
	 * 验证脚本返回的结果参数与SCRIPT_PARAMETER表里预定义的结果参数是否匹配。 若不匹配，返回false；若匹配，返回true
	 * 
	 * @param echoParamName
	 * @param outParamNameList
	 * @return
	 */
	protected static boolean validate(String echoParamName, List<String> outParamNameList) {
		if (outParamNameList.contains(echoParamName)) {
			return true;
		}
		return false;
	}

	/**
	 * 将适配器返回的脚本执行结果用其中所有的“=”隔开
	 * 
	 * @param info
	 * @return
	 */
	protected static String[] parseReturnedInfoByEveryEqual(String info) {
		return info.split("=");
	}

	/**
	 * 获取操作结果
	 * 
	 * @param responseDataObject
	 *            资源操作适配器返回的结果
	 * @return true/false
	 */
	@Override
	protected String getHandleResult(IDataObject responseDataObject) {

		return responseDataObject.getString(EXEC_RESULT);
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
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) {

	}

}
