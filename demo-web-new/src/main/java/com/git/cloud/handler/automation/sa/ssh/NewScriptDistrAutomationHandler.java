package com.git.cloud.handler.automation.sa.ssh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
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
public class NewScriptDistrAutomationHandler extends
		RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(NewScriptDistrAutomationHandler.class);

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
			String dcQueueIden = null;
			String automationType = getContextStringPara(contextParams, "AUTOMATION_TYPE");
			
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

			// 获取全局业务参数
			Map<String, Map<String, String>> handleParams = this
					.getHandleParams(flowInstId);
			
			// 将工作流相关参数和业务参数合并
			contextParams.putAll(handleParams);

			//获取包名称对应的ID及路径
			Map<String, String> packageShellMap = this.getPackageShellMap(contextParams);

			IDataObject requestDataObject = DataObject.CreateDataObject();
			HeaderDO header = HeaderDO.CreateHeaderDO();
			BodyDO body = BodyDO.CreateBodyDO();
			
			header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
			
			//允许由前台指定部分设备执行动作
			String redoErrorDev = getContextStringPara(contextParams, REDO_ERROR_DEV_KEY);
			log.debug("REDO_ERROR_DEV_KEY: " + redoErrorDev);
			List<String> devIdList = null;
			//裸机安装-资源
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
				//云服务申请
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
			
			deviceLogStr = devIdList.toString();
//			
			header.setOperation(SAOpration.EXEC_SINGLE_SHELL);
			requestDataObject.setDataObject(MesgFlds.HEADER, header);
			
			// 数据库查表组织cmdShell命令
			if (packageShellMap != null && packageShellMap.size() > 0) {

				Set<String> packageIds = packageShellMap.keySet();
				
				for (String packageId : packageIds) {
					String cmd = packageShellMap.get(packageId);
					String[] cmds = cmd.split(SPLIT_SIGN);
					String shellCmd = cmds[0];					
					log.debug("Command execute:"+shellCmd);
					for (String vmId : devIdList) {
						Map<String, String> deviceParams = getBizParamInstService().getInstanceParasMap(flowInstId, vmId);
						CmPasswordPo password = getAutomationService().getPassword(vmId);
						body.setString(SAConstants.SERVER_IP, deviceParams.get("SERVER_IP"));
						body.setString(SAConstants.USER_NAME, password.getUserName());
						body.setString(SAConstants.USER_PASSWORD, password.getPassword());
						// 将下发到客户机的脚本命令放入body
						buildReqDataObject(body,requestDataObject, shellCmd);
						// 获得结果
						result = saveParamGetResult(requestDataObject, contextParams, flowInstId, nodeId);
						if (!PubConstants.EXEC_RESULT_SUCC.equals(result)) {
							String errorMsg = "在设备" + devIdList.toString() + "上执行下发脚本命令:【" +  shellCmd + "】 失败!";
							log.error(errorMsg);
							throw new Exception(errorMsg);
						}else{
							log.debug("Command execute finish. DeviceId: " + vmId);
						}
					}
					// 保存新产生的参数实例
					saveParamInsts(flowInstId, nodeId);
				}
				return "0000";
			} else {
				String errorMsg = "NewScriptDistrAutomationHandler执行失败，请检查是否数据有问题（如流程实例的服务请求ID为空、脚本服务器中脚本的执行权限）！";
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
	 * 组合shell命令
	 * @param srvReqId
	 *            服务请求Id
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> getPackageShellMap(
			HashMap<String, Object> contextParams) throws Exception {
		String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
		//先获取数据中心id
		String dcId = this.getDatacenterId(rrinfoId);
		List<RmGeneralServerPo> poList = getAutomationService().getGeneralServers(dcId, "SCRIPT_SERVER");
		//脚本服务器IP
		String script_ip = poList.get(0).getServerIp();
		
		log.info("getPackageShellMap====rrinfoId:" + rrinfoId);
		Map<String, String> packageShellMap = Maps.newHashMap();

		List<Map<String, ?>> packagePathList = null;
		List<String> packageNames = null;
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
			packageNames = Arrays.asList(aryPack);
			packagePathList = getAutomationService().getPackagePackNames(packageNames);
			List<String> privateKeyList = new ArrayList<String>();
			ResourceBundle resource = ResourceBundle.getBundle("config.cloudservice.VMConfig"); 
			for(int i=1;i<=27;i++){
				privateKeyList.add(resource.getString("lin"+i));
		    }
			
			if (packagePathList != null && packagePathList.size() > 0) {
				for (Map<String, ?> map : packagePathList) {
					String packageId = map.get("PACKAGE_ID").toString();
					String packagePath = map.get("PACKAGE_PATH").toString();
					if (packagePath == null || "".equals(packagePath)) {
						continue;
					} else {
						packagePath = scriptBasePath + packagePath;
					}
					StringBuffer packageShell = new StringBuffer();
					for(int i=0;i<privateKeyList.size();i++ ){
						if(i==0 ){
							packageShell.append("mkdir -p ");
							packageShell.append(packagePath);
							packageShell.append(";");
							packageShell.append("echo");
							packageShell.append( " '" ); 
							packageShell.append(privateKeyList.get(i)); 
							packageShell.append( "' " ); 
							packageShell.append("> "); 
						}else{
							packageShell.append("echo");
							packageShell.append( " '" ); 
							packageShell.append(privateKeyList.get(i)); 
							packageShell.append( "' " ); 
							packageShell.append(">> "); 
						}
						packageShell.append(packagePath); 
						packageShell.append("/private_key");
						packageShell.append(";");
						
					}
					packageShell.append("chmod 600 ");
					packageShell.append(packagePath);
					packageShell.append("/private_key");
					packageShell.append(";");
					
					
					packageShell.append("chown root");
					packageShell.append(":");
					packageShell.append("root ");
					packageShell.append(packagePath);
					packageShell.append("/private_key");
					packageShell.append(";");
					
					
					packageShell.append("scp -r -i ");
					packageShell.append(packagePath);
					packageShell.append("/private_key -o StrictHostKeyChecking=no ");
					packageShell.append(script_ip);
					packageShell.append(":");
					packageShell.append(packagePath);
					packageShell.append(" $(dirname ");
					packageShell.append(packagePath);
					packageShell.append(");");
					
					packageShell.append("rm -rf ");
					packageShell.append(packagePath);
					packageShell.append("/private_key ");
					packageShell.append(";");
					
					packageShell.append(SPLIT_SIGN);
					
					packageShellMap.put(packageId, packageShell.toString());
					log.info(packageShell.toString());
				}
			}
		}
		return packageShellMap;
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
		System.out.println("******"+getResAdpterInvoker());
		responseDataObject = getResAdpterInvoker().
				invoke(requestDataObject,getTimeOut());
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
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setInt(SAConstants.CHARSET, 0);

		body.set(SAConstants.EXEC_SHELL, cmdShell);
		requestDataObject.setDataObject(MesgFlds.BODY, body);
	}
	

}
