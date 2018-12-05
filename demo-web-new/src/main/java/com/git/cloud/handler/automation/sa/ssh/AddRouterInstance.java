package com.git.cloud.handler.automation.sa.ssh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.common.ScriptUtil;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.handler.po.CmRoutePo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
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
import com.google.common.collect.Maps;

/**
 * 脚本自动化执行组件
 * <p>
 * 
 * @author 
 * @version 1.0 June 18, 2013
 * @see
 * 修改：
 * 		2015-11-06 增加 PACKAGE_BASE_PATH 用于作为运行脚本的父目录
 * 		2015-11-07 修改路由的网关读取方式，使用管理网卡网关
 * 					修改PACKAGE_BASE_PATH引入的执行路径的问题
 */
@Component("addRouterInstance")
public class AddRouterInstance extends ScriptExecutionInstance {

	private static Logger log = LoggerFactory
			.getLogger(AddRouterInstance.class);

	static final String EXCUTE_PARAMS = "EXCUTE_PARAMS"; // 自定义脚本执行参数的key
	static final String SCRIPT_PATH_AND_NAME = "SCRIPT_PATH_AND_NAME"; // 自定义脚本绝对路径和文件名的key

	public static final String SSH_SA_RESULT = "SSH_SA_RESULT";// ssh适配器返回的执行结果的key
	public static final String ECHO_INFO = "ECHO_INFO"; //
	public static final String EXIT_CODE = "EXIT_CODE";
	public static final String ERROR_INFO = "ERROR_INFO";
	public static final int EXECUTE_SUCCESS_CODE = 0;
	public static final int EXECUTE_FAIL_CODE = 1;
	public static final String IS_SUCCESS = "IS_SUCCESS"; // 自己定义的要保存至数据库中的表示脚本是否执行成功的key

	// 定义脚本执行范围的code，根据ADMIN_DIC字典表
	static final String EXE_ONLY_FIRST = "01";
	static final String EXE_SECOND_TO_LAST = "02";
	static final String EXE_ALL = "03";	
	

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
		String devResult = "";
				
		//所有设备执行的最终结果
		//		String finalResult = "";
				
		//保存所有设备执行结果的map
		Map<String, String> resultsMap = Maps.newHashMap();
		
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contextParams.get(NODE_ID); // 要根据NODE_ID在工作流配置表中查到当前节点的业务所需参数
			
			// 资源请求ID
			String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			
			//数据中心ID
			//		String dataCenterId = (String) contextParams.get(DATA_CENTER_ID);
			
			// 读取虚拟机列表，用于取得参数信息
			@SuppressWarnings("unchecked")
			List<String> vmIds = (List<String>)contextParams.get("destVmIds");
			
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			
			//获取虚机设备id
			String deviceId = vmIds.get(0);
							
			String deviceLogStr = null;
			String logMsg = null;	

			try {
				
				deviceLogStr = "[设备:" + deviceId + "]";			
				logMsg = "X86虚机执行【AddRouterInstance】线程，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。" + deviceLogStr;

				// 获取全局业务参数
				Map<String, String> handleParams = this.getHandleParams(flowInstId,
						deviceId);

				// 获取该设备管理网IP,此变量无用
				String nicIpMgmt = handleParams.get(SAConstants.NIC_IP_MGMT);
				
				// 读取设备管理网卡ip和网关
				
				/*String mgtGateway = getIIpAllocToDeviceService().qryAllocedIP(Utils.getStringList(deviceId), "VM")
						.get(0).getNetIPs().get("XVMVM").get(0).getGateWay();*/
				//List<DeviceNetIP> deviceNetIp = getIIpAllocToDeviceService()
				// .qryAllocedIP(Utils.getStringList(deviceId), "VM");
				List<DeviceNetIP> deviceNetIp = getIIpAllocToDeviceService().qryAllocedIPForDevices(deviceId);
				DeviceNetIP deviceNetIP = deviceNetIp.get(0);
				Map<String, List<NetIPInfo>> netIPs = deviceNetIP.getNetIPs();
				String mgtGateway = "";
				for (String key : netIPs.keySet()) {
					log.debug(key + "：" + netIPs.get(key));
					List<NetIPInfo> netIPInfo = netIPs.get(key);
					mgtGateway = netIPInfo.get(0).getGateWay();
				}
				log.debug("虚拟机网关信息为：" + mgtGateway);

				// 将工作流相关参数和业务参数合并
				contextParams.putAll(handleParams);

				// 獲取scriptId列表
				List<String> scriptIdList = ScriptUtil.getScriptIdList(contextParams);

				if (scriptIdList != null && scriptIdList.size() != 0) {

					List<String> cmdList = new ArrayList<String>();

					List<Object[]> outParamList = new ArrayList<Object[]>();

					for (String scriptId : scriptIdList) {
						log.debug("执行拼凑执行shell命令List操作开始,设备ID:{},脚本ID:{}",
								deviceId, scriptId);

						// ======两地三中心，根据资源请求id查找路由信息======
						String dataCenterId = this.getDatacenterId(rrinfoId);
						List<CmRoutePo> routes = getAutomationService().getRoutes(dataCenterId);
						int order = 0;
						for (CmRoutePo cmRoutePo : routes) {
							String ip = cmRoutePo.getIp();
							String subNetMask = cmRoutePo.getMask();
							String[] ipArray = nicIpMgmt.split("\\.");
							//String gateWay = ipArray[0] + "." + ipArray[1] + "." + ipArray[2] + "." + "254";
							//gateWay = cmRoutePo.getGateway();
							HashMap<String, Object> paramMap = Maps.newHashMap();
							// 将上下文参数加入到paramMap
							paramMap.putAll(contextParams);
							
							paramMap.put(SAConstants.V_INTERFACE, "eth0");
							paramMap.put(SAConstants.V_ORDER, order);
							paramMap.put(SAConstants.V_ADDRESS, ip);
							paramMap.put(SAConstants.V_NETMASK, subNetMask);
							paramMap.put(SAConstants.V_GATEWAY, mgtGateway);
							if (contextParams.get(VMFlds.VM_TYPE).equals(
									VmGlobalConstants.VM_TYPE_LINUX)) {
								paramMap.put(VMFlds.VM_TYPE,
										VmGlobalConstants.VM_TYPE_LINUX);
							}
							if (contextParams.get(VMFlds.VM_TYPE).equals(
									VmGlobalConstants.VM_TYPE_WIN)) {
								paramMap.put(VMFlds.VM_TYPE,
										VmGlobalConstants.VM_TYPE_WIN);
							}
							if (contextParams.get(VMFlds.VM_TYPE).equals(
									VmGlobalConstants.VM_TYPE_KVM)){
								paramMap.put(VMFlds.VM_TYPE, 
										VmGlobalConstants.VM_TYPE_LINUX);
							}
							
							cmdList.add(generateCmd(scriptId, paramMap));
							order++;
						}
					}
					// 将命令的List装入contextParams
					Map<String, Object> extHandleParams = getExtHandleParams(cmdList);
					if (extHandleParams != null)
						contextParams.putAll(extHandleParams);

					// 将OutParamMap装入上下文，用于下面的验证
					if (!outParamList.isEmpty())
						contextParams.put(SAConstants.OUT_PARAM_LIST, outParamList);

					// 构造自动化操作请求参数
					IDataObject requestDataObject = buildRequestData(contextParams);

					// 调用适配器执行操作
					IDataObject responseDataObject = getResAdpterInvoker().invoke(
							requestDataObject, getTimeOut());

					// 处理返回结果
					voidHandleResonpse(deviceId, contextParams,
							responseDataObject);

					// 获取操作执行结果状态
					devResult = getHandleResult(responseDataObject);

					// 将单台设备执行结果放入map中
					resultsMap.put(deviceId, devResult);
					
					String className = this.getClass().getName();
					
					return instanceHandleNormal(contextParams, flowInstId, nodeId,
							deviceId, startTime, deviceLogStr, className, true, responseDataObject);
				}else {
					throw new Exception("没有配置脚本，请检查!");
				}

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
	 * 获取流程节点的脚本执行参数，以及脚本在远程机器上存放路径和脚本名。
	 * 从上下文里取出虚机类型参数的值加以判断，分Liunx和windows分别处理。
	 * @param contenxtParmas
	 *            上下文参数
	 * @return
	 * @throws Exception 
	 * @throws BizException 
	 */
	protected String generateCmd(String scriptId,
			Map<String, Object> paramMap) throws BizException, Exception {

		StringBuffer cmd = new StringBuffer();

		List<String> paramKeyArrayList = getAutomationService().getScriptParasSort(scriptId);
		
		if(paramMap.get(VMFlds.VM_TYPE).equals(VmGlobalConstants.VM_TYPE_LINUX)){
			String scriptPathAndName = getAutomationService().getScriptFullPath(scriptId);
			// 增加目录前缀
			String prefix = getContextStringPara(paramMap, "PACKAGE_BASE_PATH");
			if (prefix == null) {
				prefix = "";
			} else {
				if (prefix.endsWith("/")) {
					prefix = prefix.substring(0, prefix.length() - 1);
				}
			}
			cmd.append(prefix + scriptPathAndName);   //Linux
			/**
			 * SP表里的order_number是参数的执行顺序！
			 * findParamKeyByScriptId方法应经将查询结果按order_number升序排列
			 * */
			if (!paramKeyArrayList.isEmpty() && paramKeyArrayList.size() != 0
					&& !paramKeyArrayList.contains(null)) {
				for (String key : paramKeyArrayList) {
					if (paramMap.get(key.trim()) != null) {
						cmd.append(" ");
						log.debug(paramMap.get(key.trim()).toString());
						cmd.append(paramMap.get(key.trim()).toString());
					}
				}
			}	
//			cmd.append("\"");
		}
			
		
		if(paramMap.get(VMFlds.VM_TYPE).equals(VmGlobalConstants.VM_TYPE_WIN)){
			String scriptName = getAutomationService().getScript(scriptId).getFileName();
			cmd.append(scriptName);          //Windows
			if (!paramKeyArrayList.isEmpty() && paramKeyArrayList.size() != 0
					&& !paramKeyArrayList.contains(null)) {
				for (String key : paramKeyArrayList) {
					if (paramMap.get(key.trim()) != null) {
						cmd.append(VmGlobalConstants.SPLIT_FLAG);
						log.debug(paramMap.get(key.trim()).toString());
						cmd.append(paramMap.get(key.trim()).toString());
					}
				}
			}
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
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contextParams) throws Exception {

		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		BodyDO body = BodyDO.CreateBodyDO();
		
		String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);

		/**
		 * 要知道Key的命名规则： 脚本路径、脚本名、执行参数等 然后从contenxtParmas中将以上这些Key对应的值取出来拼成cmd
		 */
		body.setString(SAConstants.SERVER_IP,
				contextParams.get(SAConstants.SERVER_IP).toString());
		
		body.setInt(SAConstants.CHARSET, 0);
		body.setBoolean(SAConstants.IS_TOARRAY, false);
		body.setBoolean(SAConstants.IS_KEY, false);
		body.setString(SAConstants.EXEC_FLAG, "independent"); // independent独立
		body.setString(SAConstants.LOCALPRIKEY_URL, "");
		
		List<String> cmdList = (List<String>) contextParams.get(SAConstants.CMD_LIST);
		
		if(VmGlobalConstants.VM_TYPE_LINUX.equals(contextParams.get(VMFlds.VM_TYPE)) 
				|| VmGlobalConstants.VM_TYPE_KVM.equals(contextParams.get(VMFlds.VM_TYPE))){
			header.setResourceClass(EnumResouseHeader.SA_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.SSH_RES_TYPE.getValue());
			header.setOperation(SAOpration.EXEC_SHELL);
			body.setString(SAConstants.USER_NAME,
					contextParams.get(SAConstants.USER_NAME).toString());
			body.setString(SAConstants.USER_PASSWORD,
					contextParams.get(SAConstants.USER_PASSWORD).toString());
			body.setList(SAConstants.EXEC_SHELL, cmdList);    //Linux
		}
			
		
		if(VmGlobalConstants.VM_TYPE_WIN.equals(contextParams.get(VMFlds.VM_TYPE))){
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperation(VMOpration.EXECUTE_GUEST_BAT);
			String esxiUrl = contextParams.get(VMFlds.VCENTER_URL).toString();        
			String esxiUname = contextParams.get(VMFlds.VCENTER_USERNAME).toString();
			String esxiPwd = contextParams.get(VMFlds.VCENTER_PASSWORD).toString();
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
			
		
		reqData.setDataObject(MesgFlds.HEADER, header);
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
	protected void voidHandleResonpse(String devId,
			Map<String, Object> contextParams, IDataObject responseDataObject) {

		log.debug("【消息队列返回的responseDataObject】为");
		this.logResponseData(responseDataObject);
		
		if(contextParams.get(VMFlds.VM_TYPE).equals(VmGlobalConstants.VM_TYPE_LINUX)){
			BodyDO rspBody = responseDataObject.getDataObject(MesgFlds.BODY,
					BodyDO.class);

//			rspHeader.setString(MesgFlds.RET_CODE, MesgRetCode.SUCCESS);   //初始值为执行成功
			
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
					log.debug("脚本【添加路由】执行成功，但没有返回值！！");
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
						log.debug("脚本【添加路由】执行失败，有返回值，写数据库");
						setHandleResultParam(devId.toString(), SAConstants.ERROR_INFO, sb.toString());
					} else {
						log.debug("脚本【添加路由】执行失败，但没有返回值！！");
					}
					log.debug("脚本【添加路由】执行失败!!!");
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
	protected static boolean validate(String echoParamName,
			List<String> outParamNameList) {
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
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {

	}

}
