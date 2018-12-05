
package com.git.cloud.handler.automation.sa.ssh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.cloudservice.model.vo.ScriptFullPathVo;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.HandlerReturnObjectForOneDev;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.common.ScriptUtil;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SAOpration;
import com.git.support.constants.PubConstants;
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
 * @author 
 * @version 1.0 
 * @see
 * 
 * 修改：
 * 		2015-11-06 增加 PACKAGE_BASE_PATH 用于作为运行脚本的父目录
 */
public class ScriptExecutionInstance extends BaseInstance {

	private static Logger log = LoggerFactory.getLogger(ScriptExecutionInstance.class);
	
	// 单台设备执行脚本的超时时间
	protected static final int TIME_OUT = 4 * 3600 * 1000;

	static final String EXCUTE_PARAMS = "EXCUTE_PARAMS"; // 自定义脚本执行参数的key
	static final String SCRIPT_PATH_AND_NAME = "SCRIPT_PATH_AND_NAME"; // 自定义脚本绝对路径和文件名的key

	public static final String SSH_SA_RESULT = "SSH_SA_RESULT";// ssh适配器返回的执行结果的key
	public static final String ECHO_INFO = "ECHO_INFO"; //
	public static final String EXIT_CODE = "EXIT_CODE";
	public static final String ERROR_INFO = "ERROR_INFO";
	public static final int EXECUTE_SUCCESS_CODE = 0;
	public static final int EXECUTE_FAIL_CODE = 1;
	public static final String IS_SUCCESS = "IS_SUCCESS"; // 自己定义的要保存至数据库中的表示脚本是否执行成功的key

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
		
		//单台设备执行结果
		//String devResult = "";
		
//		//保存所有设备执行结果的map
//		Map<String, String> resultsMap = Maps.newHashMap();
		
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contextParams.get(NODE_ID); // 要根据NODE_ID在工作流配置表中查到当前节点的业务所需参数
			
			
			// 读取虚拟机列表，用于取得参数信息
			@SuppressWarnings("unchecked")
			List<String> vmIds = (List<String>)contextParams.get("destVmIds");
			
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			
			for (String key : contextParams.keySet()) {
				log.debug("context param: ");
				log.debug(key + "=" + contextParams.get(key));
			}
			
			String deviceLogStr = null;
			String logMsg = null;
			
			String vmId = vmIds.get(0);
			
			try {

				deviceLogStr = "[设备:" + vmId.toString() + "]";

				logMsg = "虚机执行脚本，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。"
						+ deviceLogStr;

				// 获取全局业务参数
				Map<String, String> handleParams = this.getHandleParams(flowInstId,
						vmId);

				// 将工作流相关参数和业务参数合并
				contextParams.putAll(handleParams);

				// 获取脚本Id列表
				List<String> scriptIdList = ScriptUtil.getScriptIdList(contextParams);

				if (scriptIdList != null && scriptIdList.size() != 0) {
					
					log.debug("执行拼凑执行shell命令List操作开始,设备ID:{}",vmId);

					putCmdList2Context(contextParams, scriptIdList);

					// 构造自动化操作请求参数
					IDataObject requestDataObject = buildRequestData(contextParams);

					// 调用适配器执行操作
					IDataObject responseDataObject = getResAdpterInvoker().invoke(
							requestDataObject, getTimeOut());

					// 处理返回结果
					handleResonpse(vmId, contextParams, responseDataObject);

					String className = this.getClass().getName();

					return instanceHandleNormal(contextParams, flowInstId, nodeId,
							vmId, startTime, deviceLogStr, className, true,
							responseDataObject);
				} else {
					throw new Exception("没有配置脚本，请检查!");
				}

			} catch (Exception e) {
				return instaceHandleException(contextParams, vmId,
						deviceLogStr, logMsg, e);
			} finally {

				// 尽快的释放内存
				if (contextParams != null)
					contextParams.clear();
			}
//			return finalResult;
		}else{
			return "ERR_PARAMETER_WRONG;contextParams is null";
		}
	}

	/**
	 * 生成需执行的shell命令的List，并放入上下文。
	 * @param contextParams
	 * @param vmId
	 * @param scriptIdList
	 * @throws Exception 
	 */
	protected void putCmdList2Context(HashMap<String, Object> contextParams,
			List<String> scriptIdList) throws Exception {
		List<String> cmdList = new ArrayList<String>();

		List<Object[]> outParamList = new ArrayList<Object[]>();

		for (String scriptId : scriptIdList) {
			log.debug("执行拼凑执行shell命令List操作开始,脚本ID:{}",scriptId);
			cmdList.add(generateCmd(scriptId, contextParams));
			Object[] outParam = {scriptId, new ArrayList<String> ()};
			outParamList.add(outParam);
		}
		// 将命令的List装入contextParams
		Map<String, Object> extHandleParams = getExtHandleParams(cmdList);
		if (extHandleParams != null)
			contextParams.putAll(extHandleParams);

		// 将OutParamMap装入上下文，用于下面的验证
		if (!outParamList.isEmpty())
			contextParams.put(SAConstants.OUT_PARAM_LIST,
					outParamList);
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
	 * 获取全局业务参数
	 * 
	 * @param flowInstId
	 * @param devId
	 * @return
	 */
	protected Map<String, String> getHandleParams(String flowInstId, String devId) throws Exception {

		log.debug("开始获取业务全局参数,流程实例ID:{},设备ID:{}", flowInstId, devId);
		Map<String, String> fields = Maps.newHashMap();
		fields.put("flowInstId", flowInstId);
		fields.put("deviceId", devId);
		
		List<BizParamInstPo> paramInsts = getBizParamInstService().getInstanceParas(flowInstId, devId);
		Map<String, String> handleParams = Maps.newHashMap();
		if (paramInsts != null) {
			for (BizParamInstPo paramInstPo : paramInsts) {
				handleParams.put(paramInstPo.getParamKey(),
						paramInstPo.getParamValue());
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
	 */
	protected String generateCmd(String scriptId, Map<String, Object> contextParams) throws Exception {

		StringBuffer cmd = new StringBuffer();
		
		boolean needSudo = false;
		ScriptFullPathVo sfv = getAutomationService().getScriptFullPathVo(scriptId);
		log.info("Script Full Object is :" + sfv);
		if (sfv == null) {
			log.error("Script Full Object is not allow NULL.");
		}
		if (sfv!=null && sfv.getRunUser() != null && StringUtils.isNotBlank(sfv.getRunUser())) {
			needSudo = true;
			cmd.append("su - ");
			String user = null;
			if (sfv.getRunUser().startsWith("$")) {
				user = contextParams.get(sfv.getRunUser().substring(2, sfv.getRunUser().lastIndexOf("}"))).toString();
				cmd.append(user);
			} else {
				cmd.append(sfv.getRunUser());
			}
			cmd.append(" -c \"");
		}
		
		String scriptPathAndName = getAutomationService().getScriptFullPath(scriptId);
		String prefix = getContextStringPara(contextParams, "PACKAGE_BASE_PATH");
		if (prefix == null) {
			prefix = "";
		} else {
			if (prefix.endsWith("/")) {
				prefix = prefix.substring(0, prefix.length() - 1);
			}
		}
		cmd.append(prefix + scriptPathAndName);

		List<String> paramKeyArrayList = getAutomationService().getScriptParasSort(scriptId);
		/**
		 * SP表里的order_number是参数的执行顺序！
		 * findParamKeyByScriptId方法应经将查询结果按order_number升序排列
		 * */
		if (paramKeyArrayList != null && paramKeyArrayList.size() > 0) {
			log.debug("Script para num is:" + paramKeyArrayList.size());
			log.debug("paramKeyArrayList is : " + paramKeyArrayList.toString());
			for (String key : paramKeyArrayList) {
				log.debug("Script para key is: " + key);
				if (contextParams.get(key.trim()) != null) {
					cmd.append(" ");
					log.debug("Script para key is: " + key + ", para value is:" + contextParams.get(key.trim()).toString());
					String paramValue = contextParams.get(key.trim()).toString();
					if (paramValue.contains(PubConstants.SEPARATOR_COLON) || paramValue.contains(PubConstants.SEPARATOR_SPACE)) {
						if (!paramValue.startsWith(PubConstants.SINGLE_QUOTE_MARK)) {
							paramValue = PubConstants.SINGLE_QUOTE_MARK + paramValue;
						}
						if (!paramValue.endsWith(PubConstants.SINGLE_QUOTE_MARK)) {
							paramValue = paramValue + PubConstants.SINGLE_QUOTE_MARK;
						}
					}
					cmd.append(paramValue);
				}
			}
		}
		if (needSudo) {
			cmd.append("\"");
		}
		log.debug(cmd.toString());
		return cmd.toString();
	}

	/**
	 * 将脚本执行命令放入上下文中
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @return
	 */
	protected Map<String, Object> getExtHandleParams(List<String> cmdList) {

		Map<String, Object> exthandleParams = Maps.newHashMap();

		exthandleParams.put(SAConstants.CMD_LIST, cmdList);

		return exthandleParams;
	}

	/**
	 * 构造请求数据对象
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contextParams) throws Exception {
		
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		
		String queueIdentify = null;
		if (contextParams.get("datacenterQueueIden") == null) {
			String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			log.debug("rrinfo id is: " + rrinfoId);
			// 增加数据中心路由标识
			queueIdentify = this.getQueueIdent(rrinfoId);
		} else {
			log.debug("回收流程，直接从参数map中获取数据中心标识。");
			queueIdentify = contextParams.get("datacenterQueueIden").toString();
		}
		
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		
		header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
		header.setOperation(SAOpration.EXEC_SHELL);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();

		/**
		 * 要知道Key的命名规则： 脚本路径、脚本名、执行参数等 然后从contenxtParmas中将以上这些Key对应的值取出来拼成cmd
		 */
		body.setString(SAConstants.SERVER_IP,
				contextParams.get(SAConstants.SERVER_IP).toString());
		body.setString(SAConstants.USER_NAME,
				contextParams.get(SAConstants.USER_NAME).toString());
		body.setString(SAConstants.USER_PASSWORD,
				contextParams.get(SAConstants.USER_PASSWORD).toString());
		if (contextParams.get(SAConstants.SERVER_NAME) != null) {
			body.setString(SAConstants.SERVER_NAME, contextParams.get(SAConstants.SERVER_NAME).toString());
		}
		body.setInt(SAConstants.CHARSET, 0);
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setString(SAConstants.EXEC_FLAG, "independent"); // independent独立
		body.setString(SAConstants.LOCALPRIKEY_URL, "");

		List<String> cmdList = (List<String>) contextParams.get(SAConstants.CMD_LIST);
		body.setList(SAConstants.EXEC_SHELL, cmdList); // 至此body生成完毕

		reqData.setDataObject(MesgFlds.BODY, body);

		return reqData;
	}

	/**
	 * 1.验证脚本执行返回的结果参数与预设的结果参数是否一致；
	 * 2.如果一致，持久化返回参数
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @param responseDataObject
	 *            资源操作适配器返回的结果
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	protected String handleResonpse(String devId,
			Map<String, Object> contextParams, IDataObject responseDataObject) throws Exception {
		
		if(contextParams.get(SAConstants.CMD_LIST) != null && !contextParams.get(SAConstants.CMD_LIST).equals("")){
			log.debug("【执行的命令】为" + contextParams.get(SAConstants.CMD_LIST).toString());
		}
		HandlerReturnObjectForOneDev retObj = new HandlerReturnObjectForOneDev();
		
		log.debug("【消息队列返回的responseDataObject】为");
		this.logResponseData(responseDataObject);
		
		// 如果header中状态为失败，则返回失败
		String returnCode = this.getReturnCode(responseDataObject);
		if (returnCode == null || ! returnCode.equals(MesgRetCode.SUCCESS)) {
			retObj = new HandlerReturnObjectForOneDev(devId, returnCode, null, null);
			return retObj.toJsonString();
		}
		
		BodyDO rspBody = responseDataObject.getDataObject(MesgFlds.BODY,
				BodyDO.class);
		
//		String allScriptExitCode = MesgRetCode.SUCCESS;

		//sshSaResult中的对象个数与当前节点执行脚本数量相同，在这里实际上是1
		List sshSaResult = (List) rspBody.get(SAConstants.SSH_SA_RESULT);

		// 预先定好的返回结果参数列表
		@SuppressWarnings("unchecked")
		List<Object[]> outParamList = (List<Object[]>) contextParams
				.get(SAConstants.OUT_PARAM_LIST);
		
		//为HandlerReturnObjectForOneDev准备的参数
		List<String> echos = new ArrayList<String>();
		List<String> errors = new ArrayList<String>();
		String echoStr ="";
		String errorStr ="";
		
		for (int i = 0; i < sshSaResult.size(); i++) {
			HashMap map = (HashMap) sshSaResult.get(i);
			int exitCode = Integer.parseInt(map.get(SAConstants.EXIT_CODE).toString());
			List echoInfoLineList = (List) map.get(SAConstants.ECHO_INFO);
			List errorInfoLineList = (List) map.get(SAConstants.ERROR_INFO);

			// outParam[0]是scriptId，outParam[1]是脚本返回结果参数List
			Object[] outParam = outParamList.get(i);//outParamList中对象数量也与脚本数量相同，在这里是1
			@SuppressWarnings("unchecked")
			List<String> outParamNameList = (List<String>) outParam[1];
			switch (exitCode) {
			case EXECUTE_SUCCESS_CODE:
				if (!echoInfoLineList.isEmpty() && echoInfoLineList.size() > 0) {
					for (int j = 0; j < echoInfoLineList.size(); j++) {
						String echoInfoLine = echoInfoLineList.get(j).toString();
						if (!echoInfoLine.contains("=")) continue;
						log.debug("echoInfoLine is: " + echoInfoLine);
						String[] echo = ScriptUtil.parseReturnedInfoByFirstEqual(echoInfoLine);
						if (!outParamNameList.isEmpty() && !ScriptUtil.validate(echo[0], outParamNameList)) {
							StringBuffer error = new StringBuffer();
							error.append("脚本【");
							error.append(outParam[0]);
							error.append("】执行返回的结果参数【");
							error.append(echo[0]);
							error.append("】与SCRIPT_PARAMETER表中的预设结果参数 ");
							error.append(outParamNameList.toString());
							error.append(" 不匹配！");	
							log.debug(error.toString());
							errors.add(error.toString());
						} else {
							echoStr = "脚本【" + outParam[0] + "】执行成功，有返回值，写数据库！";
							log.debug(echoStr);
							echos.add(echoStr);
							// 如果参数名为空，则不保存
							log.debug("Parameter key is: " + echo[0] + ", value is: " + echo[1]);
							if (!StringUtils.isEmpty(echo[0])) {
								log.debug("Insert new parameter, key is: " + echo[0]);
								//将参数保持到参数实例表中
								setHandleResultParam(devId.toString(), echo[0], echo[1]);
							}
						}
					}
					setRetObj(retObj, devId, MesgRetCode.SUCCESS, echos, errors, null);
					break;
				}
				echoStr = "脚本【" + outParam[0] + "】执行成功，但没有返回值！！";
				log.debug(echoStr);
				echos.add(echoStr);
				setRetObj(retObj, devId, MesgRetCode.SUCCESS, echos, errors, null);
				break;
			default:
				StringBuffer echoSb = new StringBuffer();
				StringBuffer errorSb = new StringBuffer();
				
				for(Object echoLine : echoInfoLineList){
					echoSb.append(echoLine.toString());
				}
				for(Object errorLine : errorInfoLineList){
					errorSb.append(errorLine.toString());
				}
				if (echoSb.length() != 0 && !echoSb.toString().equals("")) {
					echoStr = echoSb.toString();
					errorStr = "脚本【" + outParam[0] + "】执行失败，有返回值。";
					log.debug(errorStr);
					echos.add(echoStr);
					errors.add(errorStr);
//					setHandleResultParam(devId.toString(), SAConstants.ERROR_INFO, sb.toString());
				} else if(errorSb.length() != 0 && !errorSb.toString().equals("")){
					errorStr = "脚本【" + outParam[0] + "】执行失败，但没有返回值！";
					log.debug(errorStr);
					errors.add(errorStr);
				}
				errorStr = "脚本【" + outParam[0] + "】执行失败！";
				log.debug(errorStr);
				errors.add(errorStr);
				setRetObj(retObj, devId, MesgRetCode.ERR_PROCESS_FAILED, echos, errors, null);
//				allScriptExitCode = MesgRetCode.ERR_PROCESS_FAILED; // 只要一个脚本未执行成功，EXEC_RESULT为N
				break;
			}
		}
		return retObj.toJsonString();

	}
	
	protected void setRetObj(HandlerReturnObjectForOneDev retObj, String devId, String retCode,List<String> echos, List<String> errors, String msgStr){
		retObj.setDeviceId(devId);
		retObj.setReturnCode(retCode);
		retObj.setEchos(echos);
		retObj.setErrors(errors);
		retObj.setReturnMesg(msgStr);
	}

	

//	@Override
//	protected String getHandleResult(IDataObject responseDataObject) {
//
//		return responseDataObject.getString(EXEC_RESULT);
//	}

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
	
	
	
	
}
