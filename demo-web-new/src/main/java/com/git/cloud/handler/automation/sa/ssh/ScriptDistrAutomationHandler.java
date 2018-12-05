package com.git.cloud.handler.automation.sa.ssh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.sa.common.HandlerThread;
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SAOpration;
import com.git.support.constants.PubConstants;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;

/**
 * 脚本自动化下发组件
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 May 8, 2013
 * @see
 */
// @Component("scriptDistrAutomationHandler")
public class ScriptDistrAutomationHandler extends
		RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(ScriptDistrAutomationHandler.class);

	// 默认的超时时间
	private static final int TIME_OUT = 60 * 60 * 1000;
	private final String SPLIT_SIGN = "@";
	private String scriptBasePath = "";

	protected static final String PACKAGE_PATH = "PACKAGE_PATH";
	protected static final String PACKAGE_DISTR_PATH = "PACKAGE_DISTR_PATH";
	protected static final String TAR_NAME = "TAR_NAME";

	static final String EXCUTE_PARAMS = "EXCUTE_PARAMS"; // 自定义脚本执行参数的key
	static final String SCRIPT_PATH_AND_NAME = "SCRIPT_PATH_AND_NAME"; // 自定义脚本绝对路径和文件名的key

	public static final String SSH_SA_RESULT = "SSH_SA_RESULT";// ssh适配器返回的执行结果的key
	public static final String ECHO_INFO = "ECHO_INFO"; //
	public static final String EXIT_CODE = "EXIT_CODE";
	public static final String ERROR_INFO = "ERROR_INFO";
	public static final int EXECUTE_SUCCESS_CODE = 0;
	public static final int EXECUTE_FAIL_CODE = 1;
	public static final String IS_SUCCESS = "IS_SUCCESS"; // 自己定义的要保存至数据库中的表示脚本是否执行成功的key
	private static final String PACKAGE_NAME = "PACKAGE_NAME";

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
	@SuppressWarnings("unchecked")
	/* (non-Javadoc)
	 * @see com.ccb.iomp.cloud.core.automation.handler.IAutomationHandler#execute(java.util.HashMap)
	 */
	/* (non-Javadoc)
	 * @see com.ccb.iomp.cloud.core.automation.handler.IAutomationHandler#execute(java.util.HashMap)
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) throws BizException, Exception {

		String result = null;

		scriptBasePath = null;

		if (scriptBasePath == null) {
			scriptBasePath = "";
		} else if (!scriptBasePath.startsWith("/")) {
			scriptBasePath = "/" + scriptBasePath + "/";
		}

		// long startTime = System.currentTimeMillis();
		
		String deviceLogStr = null;

		String rrinfoId = null;
		String srInfoId = null;
		String flowInstId = null;
		String nodeId = null;

		try {
			
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			nodeId = getContextStringPara(contextParams, NODE_ID);
			String automationType = getContextStringPara(contextParams, "AUTOMATION_TYPE");
			
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

			// 获取全局业务参数
			Map<String, Map<String, String>> handleParams = this
					.getHandleParams(flowInstId);
			
			// 将工作流相关参数和业务参数合并
			contextParams.putAll(handleParams);

			Map<String, String> packageTarShellMap = this.getPackageTarShellMap(contextParams);

			IDataObject requestDataObject = DataObject.CreateDataObject();
			HeaderDO header = HeaderDO.CreateHeaderDO();
			BodyDO body = BodyDO.CreateBodyDO();
			
			header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
			
			//允许由前台指定部分设备执行动作
			String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
			log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
			List<String> devIdList = null;
			if (automationType != null && automationType.equalsIgnoreCase("RESOURCE_MANAGEMENT")) {
				// 从流程初始化参数表中读取所需数据
				String devIds = getContextStringPara(contextParams, "DEVICE_IDS");
				devIdList = (List<String>)JSON.parse(devIds);
				if (devIdList == null || devIdList.size() <= 0) {
					throw new Exception("DEVICE_IDS is null.");
				}
				Map<String, String> deviceParams = getBizParamInstService().getInstanceParasMap(flowInstId, devIdList.get(0));
				header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), deviceParams.get(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue()));
				body.setString(SAConstants.SERVER_IP, deviceParams.get("SCRIPT_SERVER_IP"));
				body.setString(SAConstants.USER_NAME, deviceParams.get("SCRIPT_SERVER_USER"));
				body.setString(SAConstants.USER_PASSWORD, deviceParams.get("SCRIPT_SERVER_PASSWORD"));
			} else if (automationType == null || automationType.equalsIgnoreCase("CLOUD_REQUEST")) {
				// 从资源请求中读取所需数据
				devIdList = getDeviceIdList(flowInstId, nodeId, rrinfoId, redoErrorDev);
				String queueIdentify = this.getQueueIdent(rrinfoId);
				header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
				//根据资源请求id取得数据中心id
				String dataCenterId = this.getDatacenterId(rrinfoId);
				//根据数据中心id取得脚本服务器信息并装入body
				body = this.putScriptServerInfo(body, dataCenterId);
			} else {
				throw new Exception("AUTOMATION_TYPE Error, value should be RESOURCE_MANAGEMENT or CLOUD_REQUEST, but value is : " + automationType);
			}
			deviceLogStr = devIdList.toString();
			
			header.setOperation(SAOpration.EXEC_SINGLE_SHELL);
			requestDataObject.setDataObject(MesgFlds.HEADER, header);
			
			// 用于记录设备发送脚本的执行结果
			Map<String, List<StringBuffer>> allResults = new HashMap<String, List<StringBuffer>>();
			
			// 数据库查表组织cmdShell命令
			if (packageTarShellMap != null && packageTarShellMap.size() > 0) {

				Set<String> packageIds = packageTarShellMap.keySet();
				
				for (String packageId : packageIds) {
					String cmd = packageTarShellMap.get(packageId);
					String[] cmds = cmd.split(SPLIT_SIGN);
					String tarShellCmd = cmds[0];
					String delShellCmd = cmds[1];
					String packagePath = cmds[2];
					String tarName = cmds[3];
					String packageDistrPath = packagePath
							.substring(scriptBasePath.length());

					contextParams.put(PACKAGE_PATH, packagePath);
					contextParams.put(PACKAGE_DISTR_PATH, packageDistrPath);
					contextParams.put(TAR_NAME, tarName);

					if (!packageDistrPath.startsWith("/"))
						packageDistrPath = "/" + packageDistrPath;

					//将打包命令放入body
					buildReqDataObject(body,requestDataObject, tarShellCmd);
					
					// 获得结果
					result = saveParamGetResult(requestDataObject,
							contextParams, flowInstId, nodeId);
					if (!PubConstants.EXEC_RESULT_SUCC.equals(result)) {
						String errorMsg = "在脚本服务器执行打包命令" + tarShellCmd + "失败！";
						log.error(errorMsg);
						// 构造工作流新版本的返回值
						String handlerStringReturn = getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), errorMsg, MesgRetCode.ERR_OTHER);
						return handlerStringReturn;
					}

					log.debug("*******************20140324 debuging : devIdList = "+ deviceLogStr +"*********************************");

					log.debug("Command execute devices: " + deviceLogStr);

					ExecutorService exec = null;		
					int threadNum = getHandlerThreadNum(contextParams);
					log.debug("Command thread count: " + threadNum);
					exec = Executors.newFixedThreadPool(threadNum);
					
					Map<String, StringBuffer> singlePachageResults = new ConcurrentHashMap<String, StringBuffer>();

					for (String vmId : devIdList) {
						singlePachageResults.put(vmId, new StringBuffer());
					}

					Map<String,Future<String>> futureMap = Maps.newHashMap();
					for (String vmId : devIdList) {
						ScriptDistrAutomationInstance instance = new ScriptDistrAutomationInstance();

						HashMap<String, Object> params = (HashMap<String, Object>) Utils
								.depthClone(contextParams);
						List<String> ids = new ArrayList<String>();
						ids.add(vmId);
						params.put("destVmIds", ids);

						log.debug("Start execute for device: " + vmId);
						Future<String> future = exec.submit(new HandlerThread(instance, params, singlePachageResults));
						futureMap.put(vmId, future);
					}
					
					putFutureResult(futureMap,singlePachageResults);
					exec.shutdown();
					exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
					log.debug("Command execute finish. Devices: " + deviceLogStr);

					// 将删除命令放入body
					buildReqDataObject(body,requestDataObject, delShellCmd);
					// 获得结果
					result = saveParamGetResult(requestDataObject, contextParams, flowInstId, nodeId);
					if (!PubConstants.EXEC_RESULT_SUCC.equals(result)) {
						String errorMsg = "在设备" + devIdList.toString() + "上执行删除脚本压缩包命令:【" +  delShellCmd + "】 失败!";
						log.error(errorMsg);
						//continue;
						throw new Exception(errorMsg);
					}
					// 保存新产生的参数实例
					saveParamInsts(flowInstId, nodeId);
					
					// 将执行结果加入到allResults
					for (String devId : singlePachageResults.keySet()) {
						log.debug("加入Instance返回结果, devId=" + devId);
						log.debug("加入Instance返回结果, return str=" + singlePachageResults.get(devId).toString());
						if (allResults.containsKey(devId)) {
							allResults.get(devId).add(singlePachageResults.get(devId));
						} else {
							List<StringBuffer> results = new ArrayList<StringBuffer>();
							results.add(singlePachageResults.get(devId));
							allResults.put(devId, results);
						}
					}
				}
				// 处理allResults，合并同一个dev的操作结果为一个对象
				Map<String, StringBuffer> mergedResult = mergeResults(allResults);
				String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						rrinfoId, srInfoId, mergedResult, flowInstId, nodeId);
				log.debug("下发脚本Handler返回：" + handlerReturn);
				return handlerReturn;
			} else {
				String errorMsg = "ScriptDistrAutomationHandler没有生成操作命令字符串，请检查是否数据有问题（如流程实例的服务请求ID为空）！";
				log.error(errorMsg);
				// 构造工作流新版本的返回值
				String handlerStringReturn = getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), errorMsg, MesgRetCode.ERR_OTHER);
				return handlerStringReturn;
			}
		} catch (Exception e) {
			String errorMsg = "错误：{" + e + "}" + deviceLogStr;
			log.error(errorMsg);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			String handlerStringReturn = getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), errorMsg, MesgRetCode.ERR_OTHER);
			return handlerStringReturn;
		} finally {
			if (contextParams != null) {
				contextParams.clear();
			}
		}
	}

	/**
	 * 返回的map，其key是packageId。
	 * 组合shell命令打tar包 删除包 两个命令行的分割符SPLIT_SIGN是|。
	 * 
	 * @param srvReqId
	 *            服务请求Id
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> getPackageTarShellMap(
			HashMap<String, Object> contextParams) throws Exception {
		// 资源请求ID
		String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);

		log.info("getPackageTarShellMap====rrinfoId:" + rrinfoId);
		Map<String, String> packageTarShellMap = Maps.newHashMap();

		List<Map<String, ?>> packagePathList = null;
		// 如果根据资源请求没有查询到脚本包，则使用用户填写的脚本包来查询
		if (packagePathList == null || packagePathList.size() <= 0) {
			String packages = getContextStringPara(contextParams, PACKAGE_NAME);
			log.info("package: " + packages);
			String[] aryPack;
			if(packages.indexOf(";") > 0) {
				aryPack = packages.split(";");
			} else {
				aryPack = packages.split(",");
			}
			List<String> packageNames = Arrays.asList(aryPack);
			packagePathList = getAutomationService().getPackagePackNames(packageNames);
		}

		if (packagePathList != null && packagePathList.size() > 0) {

			for (Map<String, ?> map : packagePathList) {

				String packageId = map.get("PACKAGE_ID").toString();
				String packagePath = map.get("PACKAGE_PATH").toString();
//				String modulePath = map.get("MODULE_PATH").toString();
//				
//				packagePath += modulePath;

				if (packagePath == null || "".equals(packagePath)) {
					continue;
				} else {
					packagePath = scriptBasePath + packagePath;
				}
				// 打包命令
				StringBuffer sbPackageTarShell = new StringBuffer();
				sbPackageTarShell.append("cd ");
				sbPackageTarShell.append(packagePath);
				sbPackageTarShell.append(";");
				sbPackageTarShell.append(" tar -cf ");
				String tarName = UUID.randomUUID().toString();

				sbPackageTarShell.append(tarName
						+ ".iomp.tar --exclude '*.iomp.tar' *");
				// 删除文件命令
				StringBuffer sbPackageDelShell = new StringBuffer();
				sbPackageDelShell.append("cd ");
				sbPackageDelShell.append(packagePath);
				sbPackageDelShell.append(";");
				sbPackageDelShell.append("rm -f ");
				sbPackageDelShell.append(tarName + ".iomp.tar");
				sbPackageTarShell.append(SPLIT_SIGN
						+ sbPackageDelShell.toString() + SPLIT_SIGN
						+ packagePath + SPLIT_SIGN + tarName + ".iomp.tar");   //在SshAdapter中出现的tarName实际上是tarName + ".iomp.tar"
				packageTarShellMap.put(packageId, sbPackageTarShell.toString());
			}

		}
		return packageTarShellMap;
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
	private String saveParamGetResult(IDataObject requestDataObject,
			HashMap<String, Object> contenxtParams, String flowInstId, String nodeId)
			throws Exception {
		String result = "";
		// 调用适配器执行操作
		IDataObject responseDataObject;
		responseDataObject = getResAdpterInvoker().invoke(requestDataObject,
				getTimeOut());
		// 处理返回结果
		handleResonpse(contenxtParams, responseDataObject);
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
	 */
	private void buildReqDataObject(BodyDO body, IDataObject requestDataObject,
			String cmdShell) {
//		header.setOperation(SAOpration.EXEC_SINGLE_SHELL);
//		requestDataObject.setDataObject(MesgFlds.HEADER, header);
//		
//		body.setString(SAConstants.SERVER_IP,
//				this.getConfigParam(SCRIPT_SERVER_IP));
//		body.setString(SAConstants.USER_NAME,
//				this.getConfigParam(SCRIPT_USER_NAME));
//		body.setString(SAConstants.USER_PASSWORD,
//				this.getConfigParam(SCRIPT_USER_PASSWORD));
		
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setInt(SAConstants.CHARSET, 0);

		body.set(SAConstants.EXEC_SHELL, cmdShell);
		requestDataObject.setDataObject(MesgFlds.BODY, body);
	}
	

}
