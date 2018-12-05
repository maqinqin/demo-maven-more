package com.git.cloud.handler.automation.sa.ssh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.git.cloud.handler.automation.Constants;
import com.git.cloud.handler.automation.Server;
import com.git.cloud.handler.automation.sa.powervm.PowerVMAIXVariable;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.support.common.MesgRetCode;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Lists;
/**
 * 
 * <p> 在目标服务器执行命令行
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-5-22 
 * @see
 * 
 * 修改
 * 		2015-11-04 删除lparid获取的注释代码，修改lparid范围为100 - 150
 * 					修改generalserver和supervisor的用户获取方式，由原来的查询参数表获取改为从通用服务器表或管理机表获取
 */
@Repository("commandExecutionInstance")
public class CommandExecutionInstance extends ScriptExecutionInstance {

	private static final String HMC_SERVER = "HMC-Server";
	private static final String NIM_SERVER = "NIM-Server";
	private static final String IGNITE_SERVER = "IGNITE-Server";
	private static final String DEST_SERVER = "DEST-Server";
	private static final String SUPERVISOR = "SUPERVISOR";	// 管理机
	
	// 用户老一代物理机的安装
	private static final String PHY_HMC_SERVER = "PHY-HMC-Server";
	
	private static final String SCRIPT = "SCRIPT";
	private static final String COMMAND = "COMMAND";
	/* 流程节点对应的command id */
	private static final String EXEC_COMMAND_ID = "EXEC_COMMAND_ID";
	/* 执行类型，key: EXEC_TYPE , 可能的值为：命令(COMMAND), 脚本(SCRIPT)*/
	private static final String EXEC_TYPE = "EXEC_TYPE";
	/* 目标服务器类型，key: EXEC_DEST_TYPE
	选择：NIM Server(NIM-Server),HMC Server(HMC-Server)，括号中的内容为key的值*/
	private static final String EXEC_DEST_TYPE = "EXEC_DEST_TYPE";
	/* 选择脚本，可以从数据库中选择一个脚本，key: EXEC_SCRIPT_ID*/
	private static final String EXEC_SCRIPT_ID = "EXEC_SCRIPT_ID";
	/* 命令，可以输入字符串, key：EXEC_COMMAND_STR*/
	public static final String EXEC_COMMAND_STR = "EXEC_COMMAND_STR";

	private static Logger log = LoggerFactory.getLogger(CommandExecutionInstance.class);
	
	// 输出的日志中的公用信息
	private String logMsg = null;
	private String deviceLogStr = null;

	/* (non-Javadoc)
	 * @see com.ccb.iomp.cloud.core.automation.handler.IAutomationHandler#execute(java.util.HashMap)
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) {
		if(contextParams!=null){
			List<String> processingVmIds = null;
			try {
				List<IDataObject> responseDatas = Lists.newArrayList();
				// 流程实例Id
				String flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
				// 节点ID
				String nodeId = getContextStringPara(contextParams, NODE_ID);
				// 资源请求ID
				String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
				
				if (contextParams.get(TIME_OUT_STR) == null || StringUtils.isEmpty(contextParams.get(TIME_OUT_STR).toString())) {
					contextParams.put(TIME_OUT_STR, TIME_OUT);
					log.debug("TIME_OUT is null. Use default timeout:" + TIME_OUT);
				} else {
					int timeOutMs = Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()) * 1000;
					log.debug("Command TIME_OUT is :" + timeOutMs);
					contextParams.put(TIME_OUT_STR, timeOutMs);
				}
				// 读取执行的命令行
				String execComm = null;
				execComm = contextParams.get(EXEC_COMMAND_STR) == null ? "" : contextParams.get(EXEC_COMMAND_STR).toString();
				// 读取执行的脚本id
				String execScriptId = null;
				execScriptId = contextParams.get(EXEC_SCRIPT_ID) == null ? "" : contextParams.get(EXEC_SCRIPT_ID).toString();
				// 读取需要执行命令的目标服务器类型
				String execDestType = null;
				execDestType = contextParams.get(EXEC_DEST_TYPE) == null ? "" : contextParams.get(EXEC_DEST_TYPE).toString();
				
				// 读取执行类型，脚本或命令
				String execType = null;
				execType = contextParams.get(EXEC_TYPE) == null ? "COMMAND" : contextParams.get(EXEC_TYPE).toString();
				// 读取command id
				String commandId = null;
				commandId = contextParams.get(EXEC_COMMAND_ID) == null ? "0" : contextParams.get(EXEC_COMMAND_ID).toString();
				
				//添加 输出参数
				addOutputPara(contextParams, commandId);
				
				// 读取虚拟机列表，用于取得参数信息	// 读取需要操作的目标
				processingVmIds = (List<String>)contextParams.get("destVmIds");
				
				if(processingVmIds!=null && processingVmIds.size()>0){
					deviceLogStr = "[设备:" + processingVmIds.get(0).toString() + "]";
					logMsg = "创建CommandExec服务，执行自动化脚本或命令，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。" + deviceLogStr;					
					long startTime = System.currentTimeMillis();
					log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}, 设备：{}", new Object[]{flowInstId, nodeId, deviceLogStr});
					
					// 读取需要操作的虚拟机的工作流参数
					log.debug("设备id:" + processingVmIds);
					Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId, processingVmIds);
					log.debug("读取到的工作流参数为：" + handleParams.toString() + deviceLogStr);
					
					String dataCenterId = this.getDatacenterId(rrinfoId);
					
					// 读取执行命令的目标服务器信息
					Server destServer = null;
					// 首先去通用服务器表中查询需要远程操作的服务器，如果没找到则调用getDestServer方法查询远程操作的服务器
					List<RmGeneralServerPo> genServers = getAutomationService().getGeneralServers(dataCenterId, execDestType);
					if (genServers != null && genServers.size() > 0) {
						RmGeneralServerPo genServer = genServers.get(0);
						destServer = new Server();
						log.warn("没有找到manager server, execDestType=" + execDestType);
						destServer.serverIp = genServer.getServerIp();
						if (destServer.serverIp == null || destServer.serverIp.trim().equals("")) {
							throw new Exception("没有找到manager server HOST IP, execDestType=" + execDestType);
						}
						destServer.user = genServer.getUserName();
						if (destServer.user == null || destServer.user.trim().equals("")) {
							throw new Exception("没有找到manager server USER, execDestType=" + execDestType);
						}
						destServer.password = getAutomationService().getPassword(genServer.getId(), destServer.user);
						if (destServer.password == null || destServer.password.trim().equals("")) {
							throw new Exception("没有找到manager server password, execDestType=" + execDestType);
						}
						destServer.setServerIdAndName(genServer.getId(), genServer.getServerName());
						log.debug("manager server is: {}, {}, {}", new Object[]{destServer.serverIp, destServer.user, destServer.password});
					} else {
						destServer = getDestServer(execDestType, processingVmIds, handleParams.get(processingVmIds.get(0).toString()), rrinfoId);
					}
					
					// 装入服务器相关参数
					contextParams.put(SAConstants.SERVER_IP, destServer.serverIp);
					contextParams.put(SAConstants.USER_NAME, destServer.user);
					contextParams.put(SAConstants.USER_PASSWORD, destServer.password);
					contextParams.put(SAConstants.SERVER_NAME, destServer.serverName);
					log.debug("目标服务器:" + destServer.toString() + deviceLogStr);
					if (execType.equalsIgnoreCase(COMMAND)) {
						responseDatas = execCommand(contextParams, execComm, processingVmIds, flowInstId, nodeId, handleParams);
					} else if (execType.equalsIgnoreCase(SCRIPT)) {
						execScript(contextParams, execScriptId, processingVmIds, handleParams);
						// 保存新产生的参数实例
						saveParamInsts(flowInstId, nodeId);
					} else {
						throw new Exception("错误的执行类型（command或script），实际为：" + execType);
					}
					log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。{}", new Object[] {
							flowInstId, nodeId,
							new Long((System.currentTimeMillis() - startTime)), deviceLogStr});
					
					// Add new version return 
					String instanceAutomationReturn = getInstanceAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), processingVmIds.get(0),
							responseDatas.get(0));
					log.info(instanceAutomationReturn);
					return instanceAutomationReturn;
				}else{
					return "destVmIds is null";
				}
			} catch (Exception e) {
				String errorMsg = logMsg + "错误：{" + e + "}" + deviceLogStr;
				log.error(errorMsg);
				printExceptionStack(e);
				// Add new version return 
				String instanceAutomationReturn = getInstanceStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), processingVmIds!=null?processingVmIds.get(0):"",
						errorMsg);
				log.info(instanceAutomationReturn);
				return instanceAutomationReturn;
			} 
		}else{
			return "ERR_PARAMETER_WRONG;contextParams is null!";
		}
	}

	/**
	 * @param contextParams
	 * @param commandId
	 */
	private void addOutputPara(HashMap<String, Object> contextParams,
			String commandId) {
		List<Object[]> outParamList = new ArrayList<Object[]>();
		List<String> paramStrings = new ArrayList<String>();
		log.debug("读取输出参数列表：commandId = " + commandId);
		Object[] outParam = {commandId, paramStrings};
		outParamList.add(outParam);
		//将OutParamMap装入上下文，用于下面的验证
		if (!outParamList.isEmpty()) {
			contextParams.put(SAConstants.OUT_PARAM_LIST, outParamList);
		}
	}


	/**获取目的服务器
	 * @param execDestType
	 * @param vmIds
	 * @throws Exception 
	 */
	public Server getDestServer(String execDestType, List<String> vmIds, Map<String, String> map, String rrinfoId) throws Exception {
		
		Server destServer = new Server();
		if (execDestType.equalsIgnoreCase(NIM_SERVER)) {
			RmDatacenterPo dc = getAutomationService().getDatacenterByDeviceId(vmIds.get(0));
			/*CmNIMPo nim = powerVMAIXService.findNIMServerByDatacenter(dc.getDatacenterId());
			if (nim.getIp() == null || nim.getIp().trim().equals("")) {
				throw new Exception("没有配置nim server的IP地址");
			}
			destServer.serverIp = nim.getIp();
			destServer.user = PowerVMAIXVariable.NIM_USER_NAME;
			destServer.password = powerVMAIXService.findResourcePassword(nim.getNimId(), destServer.user);
			destServer.setServerIdAndName(nim.getNimId(), nim.getNimName());*/
			//destServer.password = "123";
		} else if (execDestType.equalsIgnoreCase(HMC_SERVER)) {
			
			/*List<Map<String, ?>> hmcs = automationCommonDAO.findMgrServerInfoByDeviceId(vmIds);
			if (hmcs == null || hmcs.size() == 0) {
				throw new Exception("没有找到hmc server。");
			}
			// 两台虚拟机是一个hmc的管理，所以只能查询到一个hmc server
			Map<String, ?> mapHmc = hmcs.get(0);
			if (mapHmc.get("HOST_IP") == null) {
				throw new Exception("没有配置hmc server的IP地址");
			}
			destServer.serverIp = mapHmc.get("HOST_IP").toString();
//			destServer.user = PowerVMAIXVariable.HMC_USER_NAME;
			destServer.user = getAdminParameterService().findParameter(PowerVMAIXVariable.HMC_USER_NAME_KEY);
			if (destServer.user == null) {
				throw new Exception("没有找到hmc用户定义。");
			}
			String id = mapHmc.get("VM_MANAGER_SERVER_ID").toString();
			destServer.password = powerVMAIXService.findResourcePassword(id, destServer.user);*/
		} else if (execDestType.equalsIgnoreCase(IGNITE_SERVER)) {
			/*List<CmIgnitePo> ignites = automationService.findIgnitesByRrinfo(rrinfoId);
			if (ignites == null || ignites.size() == 0) {
				throw new Exception("没有找到ignite server。");
			}
			CmIgnitePo ignite = ignites.get(0);
			destServer.serverIp = ignite.getIgniteIp();
//			destServer.user = PowerVMAIXVariable.IGNITE_USER_NAME;
			destServer.user = getAdminParameterService().findParameter(PowerVMAIXVariable.IGNITE_USER_NAME_KEY);
			if (destServer.user == null) {
				throw new Exception("没有找到ignite用户定义。");
			}
			destServer.password = powerVMAIXService.findResourcePassword(ignite.getIgniteId(), destServer.user);
			log.debug("Ignite server is: {}, {}, {}", new Object[]{destServer.serverIp, destServer.user, destServer.password});*/
		}  else if (execDestType.equalsIgnoreCase(DEST_SERVER)) {
			/*destServer.serverIp = map.get(CommonParamInitAutomationHandler.DEST_SERVER_IP);
			destServer.user = map.get(CommonParamInitAutomationHandler.DEST_SERVER_USER);
			destServer.password = map.get(CommonParamInitAutomationHandler.DEST_SERVER_PW);
			log.debug("Dest server is: {}, {}, {}", new Object[]{destServer.serverIp, destServer.user, destServer.password});*/
		} else if (execDestType.equalsIgnoreCase(PHY_HMC_SERVER)) {
			// 读取物理机对应的hmc信息
			/*VmPo vm = vmDAO.findById(vmIds.get(0));
			CmDeviceManagerSerRefPo ref = deviceManagerSerRefDAO.findDeviceManagerRef(vm.getHostId());
			VmManagerServerPo managerServerPo = vmManagerServerDAO.findById(ref.getManagerServerId());
			destServer.serverIp = managerServerPo.getHostIp();
			if (destServer.serverIp == null || destServer.serverIp.trim().equals("")) {
				throw new Exception("没有配置hmc server的IP地址");
			}
//			destServer.user = PowerVMAIXVariable.HMC_USER_NAME;
			destServer.user = getAdminParameterService().findParameter(PowerVMAIXVariable.HMC_USER_NAME_KEY);
			if (destServer.user == null) {
				throw new Exception("没有找到hmc用户定义。");
			}
			String pass = powerVMAIXService.findResourcePassword(ref.getManagerServerId(), destServer.user);
			destServer.password = pass;
			log.debug("Dest server is: {}, {}, {}", new Object[]{destServer.serverIp, destServer.user, destServer.password});*/
		} else if (execDestType.equalsIgnoreCase(SUPERVISOR)) {
			
			List<RmVmManageServerPo> manageServerPos = getAutomationService().getManageServers(vmIds.get(0));
			if (manageServerPos == null || manageServerPos.size() == 0) {
				throw new Exception("没有找到SUPERVISOR。");
			}
			RmVmManageServerPo manageServerPo = manageServerPos.get(0);
			if (manageServerPo.getManageIp() == null) {
				throw new Exception("没有配置SUPERVISOR的IP地址");
			}
			destServer.serverIp = manageServerPo.getManageIp();
			destServer.user = manageServerPo.getUserName();
			if (destServer.user == null) {
				throw new Exception("没有找到SUPERVISOR用户定义。");
			}
			destServer.password = getAutomationService().getPassword(manageServerPo.getId(), destServer.user);
		} else {
			throw new Exception("命令执行的目标错误，不存在此目标：" + execDestType);
		}
		if (destServer.password == null) {
			throw new Exception("没有找到用户的密码,user : " + destServer.user + ",server:" + destServer.serverIp);
		}
		return destServer;
	}

	/**分别在虚拟机上执行脚本
	 * @param contextParams
	 * @param flowInstId
	 * @param nodeId
	 * @param execScriptId
	 * @param processingVmIds
	 * @param handleParams
	 * @throws Exception
	 */
	private void execScript(HashMap<String, Object> contextParams, String execScriptId,
			List<String> processingVmIds,
			Map<String, Map<String, String>> handleParams) throws Exception {
		String scriptId = execScriptId;
		// 读取脚本信息和需要的参数信息，拼装成命令, script变量以/开头，包含路径信息
		String script = getAutomationService().getScriptFullPath(scriptId);
		// 读取脚本所需参数
		List<String> scriptParams = getAutomationService().getScriptParasSort(scriptId);
		
		/*String nimScriptPath = ParameterService.getConstant(PowerVMAIXVariable.P_V_O_R_NIMSERVER_SCRIPT_PATH);
		nimScriptPath = nimScriptPath.endsWith("/") ? nimScriptPath.substring(0, nimScriptPath.length() - 1) 
				: nimScriptPath;*/
		for (String vmId : processingVmIds) {
			// 生成一个虚拟机的命令
			String vmIdStr = vmId.toString();
			StringBuffer command = new StringBuffer();
			// 脚本文件应该放在服务器的指定目录下
			String theScript = script;
			command.append(theScript);
			for (String string : scriptParams) {
				String param = handleParams.get(vmIdStr).get(string);
				if (StringUtils.isEmpty(param)) {
					throw new Exception("拼装脚本及参数时，参数为空参数值为空。参数名=" + string);
				} else {
					command.append(" ").append("\"").append(param).append("\"");
				}
			}
			List<String> cmds = new ArrayList<String>();
			cmds.add(command.toString());
			contextParams.put(SAConstants.CMD_LIST, cmds);
			// 执行命令
			invokeShell(contextParams, vmId, cmds);
		}
	}

	/**分别在虚拟机上执行命令行
	 * @param contextParams
	 * @param flowInstId
	 * @param nodeId
	 * @param execComm
	 * @param processingVmIds
	 * @param handleParams
	 * @return TODO
	 * @throws Exception
	 */
	public List<IDataObject> execCommand(HashMap<String, Object> contextParams, String execComm,
			List<String> processingVmIds, String flowInstId, String nodeId,
			Map<String, Map<String, String>> handleParams) throws Exception {
		List<IDataObject> responseDatas = new ArrayList<IDataObject>();
		for (String vmId : processingVmIds) {
			IDataObject responseObject = null;
			String oneExecComm = execComm;
			// 读取vm的参数
			Map<String, String> params = handleParams.get(vmId.toString());
			// 解析命令行添加参数
			while (oneExecComm.indexOf("[$") >= 0) {
				int p1 = oneExecComm.indexOf("[$");
				int p2 = oneExecComm.indexOf("]", p1 + 2);
				String key = oneExecComm.substring(p1 + 2, p2);
				if (params.containsKey(key)) {
					String value = params.get(key);
					if (value == null) {
						value = "";
					}
					oneExecComm = oneExecComm.substring(0, p1) + value + oneExecComm.substring(p2 + 1);
				} else {
					throw new Exception("拼装脚本及参数时，不存在参数: " + key);
				}
			}
			List<String> cmds = new ArrayList<String>();
			cmds.add(oneExecComm.toString());
			// 执行命令
			responseObject = invokeShell(contextParams, vmId, cmds);
			log.debug("命令执行完成" + getReturnCode(responseObject));
			responseDatas.add(responseObject);
			if (getReturnCode(responseObject).equals(MesgRetCode.SUCCESS)) {
				// 解析命令执行的结果，并存储相应的参数
				List<BizParamInstPo> paraPos = new ArrayList<BizParamInstPo>();
				List<String> echos = null;
				log.debug("命令" + execComm);
				if (execComm.trim().startsWith("lssyscfg") && execComm.trim().endsWith("'lpar_id:name'")) {
					// 解析 lssyscfg -r lpar -m 2_P750B_066A3DT -F 'lpar_id:name'，lparid规定100 - 150
					echos = getEcho(responseObject);
					log.debug("echo:" + echos);
					List<Integer> counts = new ArrayList<Integer>();
					int lparid = 0;
					for (String string : echos) {
						String[] ary = string.split(":");
						int count = Integer.parseInt(ary[0]);
						counts.add(new Integer(count));
					}
					log.debug("安装新一代虚拟机时lparid从100开始");
					int maxCount = 150;
					for (int i = 100; i < maxCount; i++) {
						if (! counts.contains(new Integer(i))) {
							lparid = i;
							break;
						}
					}
					log.debug("Lpar id is: " + lparid);
					if (lparid == 0) {
						throw new Exception("Not find valid lparid. Echo info is: " + echos.toString());
					}
					BizParamInstPo para = makePara(flowInstId, nodeId, vmId, PowerVMAIXVariable.LPAR_ID, String.valueOf(lparid));
					paraPos.add(para);
					para = makePara(flowInstId, nodeId, vmId, PowerVMAIXVariable.REMOTE_SLOT_NUMBER_SCSI, String.valueOf(lparid + 100));
					paraPos.add(para);
					para = makePara(flowInstId, nodeId, vmId, PowerVMAIXVariable.REMOTE_SLOT_NUMBER_FC_1, String.valueOf(lparid * 2 + 100));
					paraPos.add(para);
					para = makePara(flowInstId, nodeId, vmId, PowerVMAIXVariable.REMOTE_SLOT_NUMBER_FC_2, String.valueOf(lparid * 2 + 101));
					paraPos.add(para);
					// 保存
					getBizParamInstService().saveParas(paraPos);
				} else if (execComm.trim().startsWith("lshwres") && execComm.trim().endsWith(PowerVMAIXVariable.MAC_ADDR)) {
					// lshwres -r virtualio -m 2_P750B_066A3DT --rsubtype eth --level lpar --filter "lpar_names=bj1test01,slots=102" -F mac_addr
					echos = getEcho(responseObject);
					
					BizParamInstPo para = makePara(flowInstId, nodeId, vmId, PowerVMAIXVariable.MAC_ADDR, echos.get(0));
					paraPos.add(para);
					// 保存
					getBizParamInstService().saveParas(paraPos);
				} else if (execComm.trim().startsWith("lssyscfg") && execComm.trim().endsWith("state")){
					// lssyscfg -r lpar -m 2_P750B_066A3DT --filter "lpar_names=bj1test01" -F state
					echos = getEcho(responseObject);
					String state = echos.get(0);
					if (! state.trim().equalsIgnoreCase("Not Activated")) {
						throw new Exception("VIOC的状态为：" + state);
					}
				} else {
					// 保存新产生的参数实例
					saveParamInsts(flowInstId, nodeId);
				}
			}
		}
		return responseDatas;
	}

	/**调用shell命令
	 * @param contextParams
	 * @param flowInstId
	 * @param nodeId
	 * @param vmId
	 * @param cmds
	 * @return TODO
	 * @throws Exception
	 */
	private IDataObject invokeShell(HashMap<String, Object> contextParams, String vmId, List<String> cmds)
			throws Exception {
		
		String result;
		contextParams.put(SAConstants.CMD_LIST, cmds);
		log.debug("操作的目标为: " + vmId);
		log.debug("执行命令：" + cmds.get(0));
		
		// 向contextParams中添加datacenterQueueIden参数
		String srReqId = getContextStringPara(contextParams, SRV_REQ_ID);
		log.debug("srReqId：" + srReqId);
		BmSrPo sr = getAutomationService().getSrById(srReqId);
		RmDatacenterPo dc = getAutomationService().getDatacenterByDeviceId(vmId);
		contextParams.put("datacenterQueueIden", dc.getQueueIden());
		log.debug("向工作流参数map中增加数据中心标识。" + dc.getQueueIden());
		
		IDataObject requestDataObject = buildRequestData(contextParams);
		// 调用适配器执行操作
		log.debug("发送调用请求，请求信息为：" + Constants.BR + getRequestDataObjectContent(requestDataObject) + Constants.BR +
				"Request timeout is(ms):" + Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()));
		
		IDataObject responseDataObject = getResAdpterInvoker().invoke(requestDataObject, 
				Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()));
		
		log.debug("InvokeShell finish. Request is: " + Constants.BR + getRequestDataObjectContent(requestDataObject) + Constants.BR + "The response is: "
				+ getRequestDataObjectContent(responseDataObject));
		
		// 显示执行命令的结果
		logResponseData(responseDataObject);
		
		// 处理返回结果
		handleResonpse(vmId, contextParams, responseDataObject);
		// 获取操作执行结果状态
		result = getHandleResult(responseDataObject);
		log.debug("执行命令结果：" + result);
		return responseDataObject;
	}

}
