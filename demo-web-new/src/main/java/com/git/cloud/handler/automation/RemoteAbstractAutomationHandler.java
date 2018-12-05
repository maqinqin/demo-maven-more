/**
 * Copyright (c) 2014, Git Co., Ltd. All rights reserved.
 *
 * 审核人：
 */
package com.git.cloud.handler.automation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.common.AutomationConstants;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SAOpration;
import com.git.support.constants.PubConstants;
import com.git.support.constants.SAConstants;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * 远程自动化操作,发送操作指令给消息队列，由消息对象路由选择相应的适配器执行自动化操作
 * <p>
 * 
 * @author 
 * @version 1.0 2013-4-27
 * @see
 */
public abstract class RemoteAbstractAutomationHandler extends AbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(RemoteAbstractAutomationHandler.class);

	private ResAdptInvokerFactory invkerFactory;

	// 默认的超时时间
	public static final int TIME_OUT = 3600;
	
	
	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 
	 * @param contenxtParams
	 *            上下文参数
	 * @return
	 */
	public String execute(Map<String, Object> contenxtParams) {

		String result = null;
		if(contenxtParams!=null){
			// 流程实例Id
			String flowInstId = (String) contenxtParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contenxtParams.get(NODE_ID);

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

			try {

				// 获取全局业务参数
				Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);

				// 将工作流相关参数和业务参数合并
				contenxtParams.putAll(handleParams);
				// 扩展业务参数
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParams);

				if (extHandleParams != null)
					contenxtParams.putAll(extHandleParams);

				// 构造自动化操作请求参数
				IDataObject requestDataObject = buildRequestData(contenxtParams);

				// 调用适配器执行操作
				IDataObject responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());

				// 处理返回结果
				handleResonpse(contenxtParams, responseDataObject);

				// 获取操作执行结果状态
				result = getHandleResult(responseDataObject);

				// 保存新产生的参数实例
				saveParamInsts(flowInstId, nodeId);

			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:" + nodeId + ".错误信息:" + e.getMessage();
				log.error(errorMsg, e);
			} finally {
				// 尽快的释放内存
				if (contenxtParams != null)
					contenxtParams.clear();
			}
			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] { flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
		}else{
			return "ERR_PARAMETER_WRONG;contextParams is null";
		}
		
		return result;
	}

	/**
	 * 获取适配器调用接口
	 * 
	 * @return
	 * @throws Exception 
	 */
	protected IResAdptInvoker getResAdpterInvoker() throws Exception {
		if (invkerFactory == null) {
			invkerFactory = (ResAdptInvokerFactory) WebApplicationManager.getBean("resInvokerFactory");
		}
		return invkerFactory.findInvoker("AMQ");
	}

	/**
	 * 构造请求数据对象
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @return
	 * @throws Exception 
	 */
	protected abstract IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception;

	/**
	 * 对操作返回对象进行处理，比如保存返回参数
	 * 
	 * @param contenxtParmas
	 *            上下文参数
	 * @param responseDataObject
	 *            资源操作适配器返回的结果
	 * @throws Exception 
	 */
	protected abstract void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) throws Exception;

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
	 * 获取资源操作的超时时间，各资源操作实现Handler类可以重载此方法获取返回超时时间 比如从脚本配置表中配置中获取脚边执行超时时间
	 * 
	 * @return
	 */
	protected int getTimeOut() {
		return TIME_OUT * 1000;
	}
	
	/**
	 * 将字符串发送到目标服务器，并生成文件保存
	 * @param destIp
	 * @param userName
	 * @param pwd
	 * @param fileName
	 * @param fileContent
	 * @param destPath
	 */
	public void sendStringAndMakeFile(String destIp, String userName, String pwd, String fileName, 
			String fileContent, String destPath, String queueIden, int timeout) throws Exception {
		IDataObject requestDataObject = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIden);
		header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
		header.setOperation(SAOpration.MAKE_AND_DISTRIBUTE_FILE);
		requestDataObject.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(SAConstants.SERVER_IP, destIp);
		body.setString(SAConstants.USER_NAME,userName);
		body.setString(SAConstants.USER_PASSWORD, pwd);
		body.setString(SAConstants.DEST_PATH, destPath);
		body.setString(SAConstants.FILE_NAME, fileName);
		body.setString(SAConstants.FILE_CONTENT, fileContent);
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setInt(SAConstants.CHARSET, 0);
		body.setString("IS_CHMOD", "N");
		requestDataObject.setDataObject(MesgFlds.BODY, body);
		
		IDataObject responseDataObject = invoke(timeout, requestDataObject, true);
		checkResponseData(responseDataObject);
	}

	/**
	 * 执行服务器端的命令
	 * @param destServerIp
	 * @param destUser
	 * @param destPassword
	 * @param timeout
	 * @param cmdList
	 * @throws Exception
	 */
	public IDataObject invokeCommand(String destServerIp, String destUser, String destPassword, int timeout, List<String> cmdList, 
			String queueIden, boolean isKey)
			throws Exception {
		IDataObject requestDataObject;
		requestDataObject = getSshShellRequest(destServerIp, destUser, destPassword, cmdList, queueIden, isKey);
		// invoke
		IDataObject responseDataObject = invoke(timeout, requestDataObject, false);
		return responseDataObject;
	}

	/**
	 * 将脚本服务器的文件打包，先取回到本应用服务器，然后发送到目标服务器， 其中脚本服务器的地址用户信息使用参数文件配置
	 * 
	 * @param destServerIp
	 *            目标服务器地址
	 * @param destUser
	 *            目标服务器用户
	 * @param destPassword
	 *            目标服务器密码
	 * @param destPath
	 *            目标服务器路径
	 * @param scriptServerIp
	 *            本地应用服务器ip
	 * @param scriptlUser
	 *            本地应用服务器用户
	 * @param scriptPassword
	 *            本地应用服务器密码
	 * @param scriptPath
	 *            脚本服务器路径
	 * @param scriptFileName脚本文件名
	 * @param localPath
	 *            本地应用服务器暂存路径
	 * @param timeout
	 *            超时时间
	 * @param datacenter
	 * 			    所在数据中心
	 * @throws Exception
	 */
	protected void sendScript(String destServerIp, String destUser, String destPassword, String destPath,
			String scriptServerIp, String scriptlUser, String scriptPassword, String scriptPath, String scriptFileName,
			String localPath, int timeout, String queueIden, boolean isKey) throws Exception {
	
		// 打包文件
		log.debug("Start tar script server package...");
		log.debug("scriptPath is: " + scriptPath);
		log.debug("scriptFileName is: " + scriptFileName);
		StringBuffer sbTarCmd = new StringBuffer();
		sbTarCmd.append("cd ").append(scriptPath).append(";");
		String tarName = UUID.randomUUID().toString() + ".tar";
		sbTarCmd.append(" tar -cf ");
		if (StringUtils.isEmpty(scriptFileName)) {
			scriptFileName = "*";
		} else {
			// tar参数如果以/开头 则需要使用相对目录
			if (scriptFileName.startsWith("/")) {
				scriptFileName = scriptFileName.substring(1);
			}
		}
		sbTarCmd.append(tarName).append(" ").append(scriptFileName);
		List<String> cmdList = new ArrayList<String>();
		cmdList.add(sbTarCmd.toString());
		IDataObject responseDataObject = invokeCommand(scriptServerIp, scriptlUser, scriptPassword, timeout, cmdList, queueIden, isKey);
		checkResponseData(responseDataObject);
		Thread.sleep(2000);

		// 上传文件
		log.debug("Start upload tar");
		log.debug("scriptFileName is: " + scriptFileName);
		
		/*requestDataObject = getUploadFileRequest(destServerIp, destUser, destPassword, 2006, 
				"/", tarName, scriptPath, scriptServerIp, scriptlUser, scriptPassword);
		// invoke
		invoke(timeout, requestDataObject, true);*/
		invokeSendFSingleFile(destServerIp, destUser, destPassword, "/", scriptPath, tarName, timeout, scriptServerIp, 
				scriptlUser, scriptPassword, queueIden);

		// 删除包
		log.debug("Start delete package tar on script server");
		StringBuffer sbDelCmd = new StringBuffer();
		sbDelCmd.append("cd ").append(scriptPath).append(";");
		sbDelCmd.append("rm -f ").append(tarName);
		cmdList = new ArrayList<String>();
		cmdList.add(sbDelCmd.toString());
		responseDataObject = invokeCommand(scriptServerIp, scriptlUser, scriptPassword, timeout, cmdList, queueIden, isKey);
		checkResponseData(responseDataObject);
		Thread.sleep(2000);

		// 在虚拟机上解压并设置+x
		log.debug("Start untar file");
		StringBuffer unTarAndXCmd = new StringBuffer();
		unTarAndXCmd.append("cd ").append("/").append(";");
		unTarAndXCmd.append("tar xvf ").append(tarName).append(";");
		unTarAndXCmd.append("cd ").append("/").append(";");
		
		String destScriptPath = null;
		if (scriptFileName.startsWith(".")) {
			destScriptPath = scriptFileName.substring(1);
		} else {
			destScriptPath = scriptFileName;
		}
		unTarAndXCmd.append("chmod -R +x ").append(destScriptPath).append(";");
		unTarAndXCmd.append("mv ").append(tarName).append(" /tmp;");
		unTarAndXCmd.append("rm -f ").append(" /tmp/" + tarName).append(";");
		
		cmdList = new ArrayList<String>();
		cmdList.add(unTarAndXCmd.toString());
		responseDataObject = invokeCommand(destServerIp, destUser, destPassword, timeout, cmdList, queueIden, false);
		checkResponseData(responseDataObject);
		Thread.sleep(1000);
	}
	/**
	 * 发送文件到目标服务器
	 * @param destServerIp
	 * @param destUser
	 * @param destPassword
	 * @param destPath
	 * @param sourcePath
	 * @param fileName
	 * @param timeout
	 * @param sourceServerIp
	 * @param sourceServerUser
	 * @param sourceServerPassword
	 * @throws Exception
	 */
	private void invokeSendFSingleFile(String destServerIp, String destUser, String destPassword, String destPath, String sourcePath,
			String fileName, int timeout, String sourceServerIp, String sourceServerUser, String sourceServerPassword, String queueIden)
			throws Exception {
		
		IDataObject requestDataObject;
		
		requestDataObject = getUploadFileRequest(destServerIp, destUser, destPassword, SAOpration.EXEC_PACKAGE_DISTR, 
				destPath, fileName, sourcePath, sourceServerIp, sourceServerUser, sourceServerPassword, queueIden);

		// invoke
		log.debug("Start upload local file...");
		log.debug("destPath is: " + destPath);
		log.debug("fileName is： " + fileName);
		log.debug("sourcePath is： " + sourcePath);
		IDataObject responseDataObject = invoke(timeout, requestDataObject, true);
		checkResponseData(responseDataObject);
	}
	/**
	 * 获取上传一个文件的请求对象，上传过程为：
	 * 		1. automation组件将源服务器上的文件复制到本服务器上指定的临时中转目录
	 * 		2. automation将临时中转目录的文件复制到目标服务器指定的位置
	 * 		TARGET_CMD格式：被上传文件在源服务器上的目录@被上传文件名@本地中转的路径名（必须为可删除的专用的路径）
	 * 
	 * @param destServerIp	目标服务器的ip
	 * @param destUser		目标服务器的用户名
	 * @param destPassword	
	 * @param operation		操作类型代码，2006为通过automation组件上传文件
	 * @param destPath		目标服务器上的存放路径
	 * @param fileName		需要上传得文件
	 * @param localPath		被上传文件在源服务器上的路径
	 * @param sourceServerIp	源服务器ip
	 * @param sourceServerUser
	 * @param sourceServerPassword
	 * @return
	 * @throws Exception	
	 */
	private IDataObject getUploadFileRequest(String destServerIp, String destUser, String destPassword, 
			int operation, String destPath, String fileName, String sourcePath, String sourceServerIp, String sourceServerUser, 
			String sourceServerPassword, String queueIden) throws Exception {
		
		IDataObject requestDataObject = DataObject.CreateDataObject();
		
		HeaderDO header = HeaderDO.CreateHeaderDO();
		// 增加数据中心路由标识
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIden);
		
		header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
		header.setOperation(operation);
		requestDataObject.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();
		
		/* 目标服务器地址和路径信息 */
		body.setString("TARGET_SERVER_IP",destServerIp);
		body.setString("TARGET_USER_NAME",destUser);
		body.setString("TARGET_USER_PWD", destPassword);
		body.setString("PACKAGE_DISTR_PATH", destPath);
		
		//String localTmpPath = JAVA_IO_TMPDIR;
		// automation上的临时中转目录
		String localTmpPath = Constants.JAVA_IO_TMPDIR + "/" + UUID.randomUUID().toString();
		localTmpPath = StringUtils.isEmpty(localTmpPath) ? "/" : localTmpPath;
		// TARGET_CMD格式：被上传文件在源服务器上的目录@被上传文件名@本地中转的路径名（必须为可删除的专用的路径）
		body.set("TARGET_CMD", sourcePath + Constants.SPLIT_SIGN + fileName + Constants.SPLIT_SIGN + localTmpPath);
		
		/* 源服务器信息 */
		body.setString(SAConstants.SERVER_IP, sourceServerIp);
		body.setString(SAConstants.USER_NAME, sourceServerUser);
		body.setString(SAConstants.USER_PASSWORD, sourceServerPassword);
		
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setInt(SAConstants.CHARSET, 0);
		body.setString("IS_CHMOD", "N");
		// 和TARGET_CMD一致，应该没用
		body.set(SAConstants.EXEC_SHELL, sourcePath + Constants.SPLIT_SIGN + fileName + Constants.SPLIT_SIGN + localTmpPath);
		
		requestDataObject.setDataObject(MesgFlds.BODY, body);
		return requestDataObject;
	}

	/**
	 * 获取执行ssh shell 的请求
	 * @param serverIp
	 * @param user
	 * @param password
	 * @param cmdList
	 * @return
	 */
	protected IDataObject getSshShellRequest(String serverIp, String user, String password, List<String> cmdList, 
			String queueIden, boolean isKey) {
		IDataObject reqData = DataObject.CreateDataObject();
		
		HeaderDO header = HeaderDO.CreateHeaderDO();
		// 增加数据中心路由标识
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIden);
		header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
		header.setOperation(SAOpration.EXEC_SHELL);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(SAConstants.SERVER_IP, serverIp);
		body.setString(SAConstants.USER_NAME, user);
		body.setString(SAConstants.USER_PASSWORD, password);
		
		body.setInt(SAConstants.CHARSET, 0);
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, isKey);
		body.setString(SAConstants.EXEC_FLAG, "independent"); // independent独立
		body.setString(SAConstants.LOCALPRIKEY_URL, "");
		
		body.setList(SAConstants.EXEC_SHELL, cmdList);
		// 至此body生成完毕
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}
	
	/**
	 * 执行发送文件或ssh命令, 使用headerdo获取返回值，不使用原有对象
	 * 
	 * @param timeout
	 * @param requestDataObject
	 * @throws Exception
	 */
	private IDataObject invoke(int timeout, IDataObject requestDataObject, boolean isSendFileInvoke) throws Exception {
		IDataObject responseDataObject;

		log.debug("发送调用请求，请求信息为：" + Constants.BR + getRequestDataObjectContent(requestDataObject) + Constants.BR +
				"Request timeout is(ms):" + timeout * 1000);

		responseDataObject = getResAdpterInvoker().invoke(requestDataObject, timeout * 1000);
		if (responseDataObject == null) {
			log.error("InvokeShell finish. Response data is null.");
			return null;
		} else {
			log.debug("InvokeShell finish. Request is: " + Constants.BR + getRequestDataObjectContent(requestDataObject) + Constants.BR + "The response is: "
					+ Constants.BR + getRequestDataObjectContent(responseDataObject));
			
			if (!isSendFileInvoke) {
				log.debug("调用返回结果为, Response object：");
				logResponseData(responseDataObject);
			} else {
				log.debug("Response object: " + responseDataObject);
			}
			return responseDataObject;
		}
	}

	private void checkResponseData(IDataObject responseDataObject)
			throws Exception {
		if (responseDataObject == null) {
			String errorMsg = "调用执行错误：返回响应为空。";
			throw new Exception(errorMsg);
		}
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = "调用执行错误：" + header.getRetMesg() + ", message:" + header.getRetMesg();
			throw new Exception(errorMsg);
		}
	}

	/**
	 * 读取命令请求的IdataObject的内容
	 * 
	 * @param data
	 * @return
	 */
	protected String getRequestDataObjectContent(IDataObject data) {
		StringBuffer cont = new StringBuffer();
		if (data == null) {
			log.error("Response data is null.");
		}else{
			HeaderDO header = data.getDataObject(MesgFlds.HEADER, HeaderDO.class);
			BodyDO body = data.getDataObject(MesgFlds.BODY, BodyDO.class);
			log.debug("Response header is:");
			cont.append("Header: {").append(Constants.BR).append(getOneRequestDataObjectContent(header)).append("}").append(Constants.BR);
			log.debug("Response body is:");
			cont.append("Body: {").append(Constants.BR).append(getOneRequestDataObjectContent(body)).append("}").append(Constants.BR);
		}
		return cont.toString();
	}

	/**
	 * 读取一个命令请求的body或header的内容
	 * 
	 * @param data
	 * @return
	 */
	private String getOneRequestDataObjectContent(IDataObject data) {
		if (data == null) {
			return "Response data is null.";
		}
		StringBuffer cont = new StringBuffer();
		HashMap<String, Object> content = data.getContianer();
		for (String item : content.keySet()) {
			String value = content.get(item) == null ? "" : content.get(item).toString();
			cont.append(item).append(":").append(value).append(Constants.BR);
		}
		return cont.toString();
	}

	/**
	 * 输出返回结果的信息
	 * 
	 * @param responseDataObject
	 */
	@SuppressWarnings("unchecked")
	protected void logResponseData(IDataObject responseDataObject) {

		try {
			// header
			HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
			HashMap<String, Object> content = header.getContianer();
			if (content != null) {
				log.debug("header 信息为：");
				for (String item : content.keySet()) {
					String value = content.get(item) == null ? "" : content.get(item).toString();
					log.debug("		" + item + ": " + value);
				}
			}
			// 处理body
			BodyDO respBody = responseDataObject.getDataObject(MesgFlds.BODY, BodyDO.class);
			if (respBody != null) {
				
				Object obj = respBody.get(SAConstants.SSH_SA_RESULT);
				if (obj != null && obj instanceof List) {
					
					List<Object> sshSaResult = (List<Object>) respBody.get(SAConstants.SSH_SA_RESULT);
					int i = 1;
					if (sshSaResult != null) {
						for (Object object : sshSaResult) {
							log.debug("返回的结果为List，其中第" + i++ + "为：");
							Map<String, Object> map = (Map<String, Object>) object;
							log.debug("		" + AutomationConstants.EXIT_CODE + ":" + map.get(AutomationConstants.EXIT_CODE));
							List<Object> echoList = (List<Object>) map.get(AutomationConstants.ECHO_INFO);
							if (echoList != null) {
								log.debug("		echo列表为：");
								for (Object echo : echoList) {
									log.debug("			" + echo.toString());
								}
							}
							List<Object> errorList = (List<Object>) map.get(AutomationConstants.ERROR_INFO);
							if (errorList != null) {
								log.debug("		error列表为：");
								for (Object error : errorList) {
									log.debug("			" + error.toString());
								}
							}
						}
					}
				} else {
					log.debug("The SSH_SA_RESULT is: " + obj);
				}
			}
			log.debug("Response info log end.");
		} catch (Exception e) {
			log.error("Print response data error.");
			printExceptionStack(e);
		}
	}

	/**
	 * 读取命令响应返回值echolist列表
	 */
	public List<String> getEcho(IDataObject responseData) throws Exception {
		if (responseData == null) {
			throw new Exception("responseObject is null.");
		}
		List<String> echos = new ArrayList<String>();
		BodyDO respBody = responseData.getDataObject(MesgFlds.BODY, BodyDO.class);
		if (respBody == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		//List<Object> sshSaResult = (List<Object>) respBody.get(SAConstants.SSH_SA_RESULT);
		List<Object> sshSaResult = null;
		if (respBody.get(SAConstants.SSH_SA_RESULT) instanceof List) {
			sshSaResult = (List<Object>) respBody.get(SAConstants.SSH_SA_RESULT);
		} else if (respBody.get(SAConstants.SSH_SA_RESULT) instanceof Map){
			if (getReturnCode(responseData).equals(MesgRetCode.SUCCESS)) {
				sshSaResult = new ArrayList<Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				List<String> ecs = new ArrayList<String>();
				ecs.add(getReturnMesg(responseData));
				map.put(AutomationConstants.ECHO_INFO, ecs);
				sshSaResult.add(map);
			} else {
				return null;
			}
		} else {
			log.error("SSH_SA_RESULT is null");
		}
		if (null != sshSaResult){
			for (Object object : sshSaResult) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) object;
				@SuppressWarnings("unchecked")
				List<Object> echoList = (List<Object>) map.get(AutomationConstants.ECHO_INFO);
				if (echoList != null) {
					for (Object echo : echoList) {
						String value = echo == null ? "" : echo.toString();
						echos.add(value);
					}
				}
			}
		}
		
		return echos;
	}

	/**
	 * 读取命令响应返回值errorlist列表
	 */
	public List<String> getError(IDataObject responseData) throws Exception {
		if (responseData == null) {
			throw new Exception("responseObject is null.");
		}
		List<String> echos = new ArrayList<String>();
		BodyDO respBody = responseData.getDataObject(MesgFlds.BODY, BodyDO.class);
		if (respBody == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
//		List<Object> sshSaResult = (List<Object>) respBody.get(SAConstants.SSH_SA_RESULT);
		List<Object> sshSaResult = null;
		if (respBody.get(SAConstants.SSH_SA_RESULT) instanceof List) {
			sshSaResult = (List<Object>) respBody.get(SAConstants.SSH_SA_RESULT);
		} else if (respBody.get(SAConstants.SSH_SA_RESULT) instanceof Map){
			if (!getReturnCode(responseData).equals(MesgRetCode.SUCCESS)) {
				sshSaResult = new ArrayList<Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				List<String> ecs = new ArrayList<String>();
				ecs.add(getReturnMesg(responseData));
				map.put(AutomationConstants.ERROR_INFO, ecs);
				sshSaResult.add(map);
			} else {
				return null;
			}
		} else {
			log.error("SSH_SA_RESULT is null");
		}
		if (null != sshSaResult){
			for (Object object : sshSaResult) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) object;
				@SuppressWarnings("unchecked")
				List<Object> errorList = (List<Object>) map.get(AutomationConstants.ERROR_INFO);
				if (errorList != null) {
					for (Object err : errorList) {
						String value = err == null ? "" : err.toString();
						echos.add(value);
					}
				}
			}
		}
		
		return echos;
	}
	
	/**
	 * 读取返回码
	 * @param responseData
	 * @return
	 */
	public String getReturnCode(IDataObject responseData) throws Exception {
		if (responseData == null) {
			throw new Exception("responseObject is null.");
		}
		HeaderDO responseHeader = responseData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		return responseHeader.getRetCode();
	}
	
	/**
	 * 读取返对象
	 * @param responseData
	 * @return
	 */
	public String getReturnMesg(IDataObject responseData) throws Exception {
		if (responseData == null) {
			throw new Exception("responseObject is null.");
		}
		HeaderDO responseHeader = responseData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		return responseHeader.getRetMesg();
	}
	
	/**
	 * 构造一个设备的自动化执行结果的返回字符串
	 * @param busVersion
	 * @param devId
	 * @param responseData
	 * @return
	 * @throws Exception
	 */
	protected String getInstanceAutomationReturn(String busVersion,
			String devId, IDataObject responseData)
			throws Exception {
		String ret = null;
		if (busVersion == null) {
			ret = getReturnCode(responseData);
		} else {
			List<String> echos = getEcho(responseData);
			List<String> errors = getError(responseData);
			HandlerReturnObjectForOneDev retObj = new HandlerReturnObjectForOneDev(devId, 
					getReturnCode(responseData), echos, errors);
			retObj.setReturnMesg(getReturnMesg(responseData));
			ret = retObj.toJsonString();
		}
		log.info("InstanceAutomationReturn is: " + ret);
		return ret;
	}
	
	 /** 脚本下发instance专用处理返回结果方法
	 * @param busVersion
	 * @param devId
	 * @param responseData
	 * @return
	 * @throws Exception
	 */
	protected String getScriptDistrInstanceAutomationReturn(String busVersion,
			String devId, IDataObject responseData)
			throws Exception {
		String ret = null;
		if (busVersion == null) {
			ret = getReturnCode(responseData);
		} else {
			List<String> echos = new ArrayList<String>();			
			List<String> errors = new ArrayList<String>();
			HeaderDO header = responseData.getDataObject(MesgFlds.HEADER,
					HeaderDO.class);
			if(PubConstants.EXEC_RESULT_SUCC.equals(header.getRetCode())){
				String echo = header.getRetMesg();
				echos.add(echo);
			}else{
				String error = header.getRetMesg();
				errors.add(error);
			}			
			HandlerReturnObjectForOneDev retObj = new HandlerReturnObjectForOneDev(devId, 
					getReturnCode(responseData), echos, errors);
			retObj.setReturnMesg(getReturnMesg(responseData));
			ret = retObj.toJsonString();
		}
		log.info("InstanceAutomationReturn is: " + ret);
		return ret;
	}
	
	/**
	 * 将脚本服务器的ip、用户名、密码装入BodyDO对象
	 * @param body
	 * @param datacenterId
	 */
	protected BodyDO putScriptServerInfo(BodyDO body, String datacenterId) throws Exception {
		List<RmGeneralServerPo> poList = getAutomationService().getGeneralServers(datacenterId, "SCRIPT_SERVER");
		RmGeneralServerPo po = null;
		if(poList != null && poList.size() > 0) {
			po = poList.get(0);
		}
		if(po == null) {
			throw new Exception("获取不到指定数据中心的服务器信息");
		}
		String serverIp = po.getServerIp();
		String username = po.getUserName();
		String password = getAutomationService().getPassword(po.getId(), username);
		body.setString(SAConstants.SERVER_IP,serverIp);
		body.setString(SAConstants.USER_NAME,username);
		body.setString(SAConstants.USER_PASSWORD,password);
		return body;
		
	}
}
