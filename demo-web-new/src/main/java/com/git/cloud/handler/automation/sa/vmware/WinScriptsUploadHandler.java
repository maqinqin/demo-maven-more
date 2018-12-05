package com.git.cloud.handler.automation.sa.vmware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.support.ApplicationCtxUtil;
import com.git.cloud.handler.automation.AbstractAutomationHandler;
import com.git.cloud.handler.automation.sa.common.HandlerThread;
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.constants.SAConstants;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
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
 * @author HongDongSheng
 * @version 1.0 May 8, 2013
 * @see
 */

public class WinScriptsUploadHandler extends AbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(WinScriptsUploadHandler.class);
		
		
		
		// 默认的超时时间
		private static final int TIME_OUT = 60*60*1000;
		private static final String SPLIT_SIGN = "@";
//		private static final String SPLIT_ID_SIGN = "|";
		private String localPath = "";
		private String scriptBasePath = "";
		private final String LOCAL_PATH="TAR_TEMP_PATH";
//		private final String SCRIPT_SERVER_IP="SCRIPT_SERVER_IP";
//		private final String SCRIPT_USER_NAME="SCRIPT_SERVER_USER_NAME";
//		private final String SCRIPT_USER_PASSWORD="SCRIPT_SERVER_PASSWORD";
		private final String SCRIPT_BASE_PATH="SCRIPT_BASE_PATH";
		
		protected static final String COMBINED_CMD="COMBINED_CMD";
		

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
	@Override
	public String execute(HashMap<String, Object> contextParams) throws BizException, Exception {

		String deviceLogStr = "";
		
		if (!localPath.endsWith("/"))
			localPath =  localPath + "/";
		
		scriptBasePath = getAutomationService().getAppParameter(SCRIPT_BASE_PATH);
		
		if(scriptBasePath==null){
			scriptBasePath="";
		}else if(!scriptBasePath.startsWith("/")){
			scriptBasePath = "/"+scriptBasePath+"/";
		}
		
//		long startTime = System.currentTimeMillis();

		String rrinfoId = null;
		String srInfoId = null;
		String flowInstId = null;
		String nodeId = null;

		try {
			
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);
			flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			nodeId = getContextStringPara(contextParams, NODE_ID);			

			
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			
			localPath = getAutomationService().getAppParameter(LOCAL_PATH)+"/"+flowInstId;
						
			// 获取全局业务参数
			Map<String, Map<String, String>> handleParams = this
					.getHandleParams(flowInstId);

			// 将工作流相关参数和业务参数合并
			contextParams.putAll(handleParams);

			Map<String, Map<String, String>> packagePathNameParams = this
					.getPackageTarShellMap(contextParams);

			if (packagePathNameParams != null) {
				contextParams.putAll(packagePathNameParams);
			}

			IDataObject requestDataObject = DataObject.CreateDataObject();
			HeaderDO header = HeaderDO.CreateHeaderDO();
//			BodyDO body = BodyDO.CreateBodyDO();
			requestDataObject.setDataObject(MesgFlds.HEADER, header);
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());

			// 数据库查表组织cmdShell命令
			@SuppressWarnings("unchecked")
			Map<String, String> packageTarShellMap = (Map<String, String>) contextParams
					.get(SAConstants.PACKAGE_TAR_SHELL_MAP);
			

			// 用于记录设备发送脚本的执行结果
			Map<String, List<StringBuffer>> allResults = new HashMap<String, List<StringBuffer>>();
			
			if (packageTarShellMap != null && packageTarShellMap.size() > 0) {

				Set<String> packageAndModuleIds = packageTarShellMap.keySet();
				
				
				//遍历packageAndModuleIds
				for (String packageAndModuleId : packageAndModuleIds) {
					String cmd = packageTarShellMap.get(packageAndModuleId);
					String[] cmds = cmd.split(SPLIT_SIGN);
//					String tarShellCmd = cmds[0];
//					String untarShellCmd = cmds[1];
//					String delShellCmd = cmds[2];
					String packagePath = cmds[3];
//					String tarName = cmds[4];
					String packageDistrPath = packagePath
							.substring(scriptBasePath.length());					

					if (!packageDistrPath.startsWith("/"))
						packageDistrPath = "/" + packageDistrPath;
					
					contextParams.put(VMFlds.SCRIPT_SERVER_PATH, packagePath);
					
					//构造适配器所需的脚本路径列表
					List<String> scriptPathNameList = new ArrayList<String>();
					String[] packageIdAndModuleId = packageAndModuleId.split(SPLIT_SIGN);
					String packageIdLong = packageIdAndModuleId[0];
					String moduleIdLong = packageIdAndModuleId[1];
//					List<String> scriptNameList = scriptPackageCommonDAO.findScriptNameByPackageId(packageIdLong);
					List<String> scriptNameList = getAutomationService().getScriptNameByPackageIdAndModuleId(packageIdLong, moduleIdLong);
					for(String scriptName : scriptNameList){
						scriptPathNameList.add(scriptName );   //只有脚本名，不带路径
					}
					contextParams.put(VMFlds.SCRIPT_LIST_NAME, scriptPathNameList);
					
					//允许由前台指定部分设备执行动作
					String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
					log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
					List<String> vmIds = getDeviceIdList(flowInstId, nodeId, rrinfoId, redoErrorDev);
					deviceLogStr = vmIds.toString();
					log.debug("*******************20140324 debuging : devIdList = "+ deviceLogStr +"*********************************");
					
					log.debug("Command execute devices: " + deviceLogStr);
					
					ExecutorService exec = null;		
					int threadNum = getHandlerThreadNum(contextParams);
					log.debug("Command thread count: " + threadNum);
					exec = Executors.newFixedThreadPool(threadNum);
					
					Map<String, StringBuffer> singlePachageResults = new HashMap<String, StringBuffer>();
					for (String vmId : vmIds) {
						singlePachageResults.put(vmId, new StringBuffer());
					}
					Map<String,Future<String>> futureMap = Maps.newHashMap();
					for (String vmId : vmIds) {
						
						WinScriptsUploadInstance instance = new WinScriptsUploadInstance();
						
						@SuppressWarnings("unchecked")
						HashMap<String, Object> params = (HashMap<String, Object>) Utils.depthClone(contextParams);
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
					
					
//					String errorResult = "";
//					for (Long key : resultsMap.keySet()) {
//						log.info(resultsMap.get(key).toString());
//						if (!(resultsMap.get(key).toString().equals(MesgRetCode.SUCCESS))) {
//							errorResult += "[" + resultsMap.get(key) + "]";
//						}
//					}
//					if (!errorResult.equals("")) {
//						log.error("Command execute error: " + errorResult);
//						return errorResult;
//					}
				}
				// 处理allResults，合并同一个dev的操作结果为一个对象
				Map<String, StringBuffer> mergedResult = mergeResults(allResults);
				String handlerReturn = getHandlerAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						rrinfoId, srInfoId, mergedResult, flowInstId, nodeId);
				log.debug("下发脚本Handler返回：" + handlerReturn);
				return handlerReturn;
			}
			else{
				String errorMsg = "WinScriptsUploadHandler没有生成操作命令字符串，请检查是否数据有问题（如流程实例的服务请求ID为空）！";
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
	 * 组合shell命令打tar包 删除包 两个命令行的分割符SPLIT_SIGN是@
	 * @param srvReqId 服务请求Id
	 * @return
	 */
	public Map<String, Map<String, String>> getPackageTarShellMap(HashMap<String, Object> contextParams) throws Exception {
	//  rr请求ID
		String rrinfoId = (String) contextParams.get(RRINFO_ID);
		
		List<Map<String, ?>> packagePathList = null;//scriptPackageCommonDAO.findPackageNamePathByRrinfoId(rrinfoId);
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
		Map<String, Map<String, String>> scriptPackage = null;
		if(packagePathList!=null && packagePathList.size()>0){
			//执行的脚本参数
			scriptPackage = Maps.newHashMap();
			Map<String, String> packageTarShellMap = scriptPackage.get(rrinfoId);
			if(packageTarShellMap==null){
				packageTarShellMap = Maps.newHashMap();
				scriptPackage.put(SAConstants.PACKAGE_TAR_SHELL_MAP, packageTarShellMap);
			}
			for (Map<String, ?> map : packagePathList) {
				String packageId = map.get("PACKAGE_ID").toString();
				String moduleId = map.get("MODULE_ID").toString();
				String packagePath = map.get("PACKAGE_PATH").toString();
				String modulePath = map.get("MODULE_PATH").toString();
				
				packagePath += "/" + modulePath;
				
				if(packagePath==null || "".equals(packagePath)){
					continue;
				}else{
					packagePath = scriptBasePath+packagePath;
				}
				//打包命令
				StringBuffer sbPackageTarShell = new StringBuffer();
				sbPackageTarShell.append("cd ");
				sbPackageTarShell.append(packagePath);
				sbPackageTarShell.append(";");
				sbPackageTarShell.append(" tar -cf ");
				String tarName = UUID.randomUUID().toString();
				sbPackageTarShell.append(tarName
						+ ".iomp.tar --exclude '*.iomp.tar' *");
				
				//解压包命令
				StringBuffer sbUntarShell = new StringBuffer();
				String tar = "cd " + packagePath + "; tar -xf  " + tarName + ".iomp.tar;";
				String mkExec = "chmod -R +x " + packagePath + "/*";
				sbUntarShell.append(tar);
				sbUntarShell.append(mkExec);
			
				//删除文件命令
				StringBuffer sbPackageDelShell = new StringBuffer();
				sbPackageDelShell.append("cd ");
				sbPackageDelShell.append(packagePath);
				sbPackageDelShell.append(";");
				sbPackageDelShell.append("rm -f ");
				sbPackageDelShell.append(tarName+ ".iomp.tar");
				
				//合并
				sbPackageTarShell.append(SPLIT_SIGN+sbUntarShell.toString()+SPLIT_SIGN+sbPackageDelShell.toString()+SPLIT_SIGN+packagePath+SPLIT_SIGN+tarName+".tar");
				
				String key = packageId + SPLIT_SIGN + moduleId;
				packageTarShellMap.put(key, sbPackageTarShell.toString());
			}
		}
		return scriptPackage;
	}
	
	
	/**
	 * 构造请求数据对象
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @return
	 */
	protected IDataObject buildRequestData(
			Map<String, Object> contenxtParmas) {
		return null;
	}
	
	/**
	 * 获取适配器调用接口
	 * 
	 * @return
	 * @throws Exception 
	 */
	protected IResAdptInvoker getResAdpterInvoker() throws Exception {
		ResAdptInvokerFactory invkerFactory = (ResAdptInvokerFactory) ApplicationCtxUtil.getBean("resInvokerFactory");
		return invkerFactory.findInvoker("AMQ");
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
		responseDataObject.set(EXEC_RESULT, sshSaResult.get("SSH_SA_RESULT").toString());
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
	 * 执行命令放入body里
	 * @param body
	 * @param requestDataObject
	 * @param cmdShell
	 */
//	private void buildReqBody(HashMap<String, Object> contenxtParams,HeaderDO header,BodyDO body,IDataObject requestDataObject,
//			String cmdShell , List<String> scriptPathNameList){
//		
//		/**
//		 * uploadVo.setEsxiUrl(reqBody.getString(VMFlds.ESXI_URL));
//		 * uploadVo.
//		 * setEsxiUserName(reqBody.getString(VMFlds.ESXI_USERNAME));
//		 * uploadVo
//		 * .setEsxiPassword(reqBody.getString(VMFlds.ESXI_PASSWORD));
//		 * uploadVo
//		 * .setGuestUserName(reqBody.getString(VMFlds.GUEST_USER_NAME));
//		 * uploadVo
//		 * .setGuestPassword(reqBody.getString(VMFlds.GUEST_PASSWORD));
//		 * //从脚本服务器上传到适配器服务器脚本的命令和删除脚本的命令
//		 * uploadVo.setCopyFileScript(reqBody.getString(VMFlds
//		 * .EXECUTE_SCRIPT)); 
//		 * //上传到适配器服务器脚本路径
//		 * uploadVo.setScriptPathList(reqBody
//		 * .getList(VMFlds.SCRIPT_LIST_NAME));
//		 * */
//		
//		header.setOperation(VMOpration.UPLOAD_GUEST_FILE);
//		requestDataObject.setDataObject(MesgFlds.HEADER, header);
//		String esxiUrl = deviceParamInfo.get(VMFlds.ESXI_URL);        //TODO 以下这三个参数在上下文里有吗？
//		String esxiUname = deviceParamInfo.get(VMFlds.ESXI_USERNAME);
//		String esxiPwd = deviceParamInfo.get(VMFlds.ESXI_PASSWORD);
//		String guestUname = this.getConfigParam(SCRIPT_USER_NAME);   
//		String guestPwd = this.getConfigParam(SCRIPT_USER_PASSWORD);      
//		
//		// 执行脚本上传需要的参数
//		header.setOperation(VMOpration.UPLOAD_GUEST_FILE);
//		body.setString(VMFlds.ESXI_URL, esxiUrl);
//		body.setString(VMFlds.ESXI_USERNAME, esxiUname);
//		body.setString(VMFlds.ESXI_PASSWORD, esxiPwd);
//		body.setString(VMFlds.GUEST_USER_NAME, guestUname); 
//		body.setString(VMFlds.GUEST_PASSWORD, guestPwd);
//		body.setString(VMFlds.EXECUTE_SCRIPT, cmdShell);
//		body.setList(VMFlds.SCRIPT_LIST_NAME, scriptPathNameList);
//		
//		body.setBoolean(SAConstants.IS_TOARRAY, false);
//		body.setBoolean(SAConstants.IS_KEY, false);
//		body.setInt(SAConstants.CHARSET, 0);
//		
//		requestDataObject.setDataObject(MesgFlds.BODY, body);
//	}
	
	
	
}
