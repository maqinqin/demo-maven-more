package com.git.cloud.handler.automation.sa.vmware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmGlobalConstants;
import com.git.support.constants.PubConstants;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;

/**
 * 
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 2013-5-11 
 * @see
 */
public  class WinScriptsUploadInstance  extends BaseInstance{
	private static Logger log = LoggerFactory
			.getLogger(WinScriptsUploadInstance.class);
	
	// 默认的超时时间
	private static final int TIME_OUT = 60*60*1000;
//	private String localPath = "";
//	private String scriptBasePath = "";
	private final String TAR_TEMP_PATH="TAR_TEMP_PATH";
	private final String SCRIPT_SERVER_IP="SCRIPT_SERVER_IP";
	private final String SCRIPT_USER_NAME="SCRIPT_SERVER_USER_NAME";
	private final String SCRIPT_USER_PASSWORD="SCRIPT_SERVER_PASSWORD";
//	private final String SCRIPT_BASE_PATH="SCRIPT_BASE_PATH";
	

	
	
	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String execute(HashMap<String, Object> contextParams) throws Exception {
		String result = null;
		
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contextParams.get(NODE_ID);
			// 资源请求ID
			String rrinfoId = (String) contextParams.get(RRINFO_ID);
			
//			localPath = this.getConfigParam(TAR_TEMP_PATH)+"/"+flowInstId;
//			scriptBasePath = this.getConfigParam(SCRIPT_BASE_PATH);
//			
//			if(scriptBasePath==null){
//				scriptBasePath="";
//			}else if(!scriptBasePath.startsWith("/")){
//				scriptBasePath = "/"+scriptBasePath+"/";
//			}
			
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			
			// 获取全局业务参数
			Map<String, Map<String, String>> handleParams = this
					.getHandleParams(flowInstId);

			// 将工作流相关参数和业务参数合并
			contextParams.putAll(handleParams);
			
			// 读取虚拟机列表，用于取得参数信息
		    @SuppressWarnings("unchecked")
			List<String> vmIds = (List<String>)contextParams.get("destVmIds");	
					
			String deviceLogStr = null;
			String logMsg = null;

			//获取虚机设备id
			String vmId = vmIds.get(0);	

			try {								

				deviceLogStr = "[设备:" + vmId + "]";
						
				logMsg = "X86虚机执行【DeployOvfInstance】线程，流程实例ID:" + flowInstId + "，节点ID:" + nodeId + "。" + deviceLogStr;
						
				log.debug(logMsg);

				IDataObject requestDataObject = DataObject.CreateDataObject();
				HeaderDO header = HeaderDO.CreateHeaderDO();
				
				// 增加数据中心路由标识
				String queueIdentify = this.getQueueIdent(rrinfoId);
				String dataCenterId = this.getDatacenterId(rrinfoId);
				header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
				
				BodyDO body = BodyDO.CreateBodyDO();
				requestDataObject.setDataObject(MesgFlds.HEADER, header);
				header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
				header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
				List<String> scriptPathNameList = (List<String>) contextParams.get(VMFlds.SCRIPT_LIST_NAME);

				Map<String, String> deviceParamInfo = (Map<String, String>) contextParams
						.get(vmId.toString());

				
				//更新物理机URL，用户名，密码为VCenter的
				header.setOperation(VMOpration.UPLOAD_GUEST_FILE);
				requestDataObject.setDataObject(MesgFlds.HEADER, header);
				String esxiUrl = deviceParamInfo.get(VMFlds.VCENTER_URL);        
				String esxiUname = deviceParamInfo.get(VMFlds.VCENTER_USERNAME);
				String esxiPwd = deviceParamInfo.get(VMFlds.VCENTER_PASSWORD);
				String guestUname = deviceParamInfo.get(SAConstants.USER_NAME);   
				String guestPwd = deviceParamInfo.get(SAConstants.USER_PASSWORD);    
				
				
				String scriptServerPath = contextParams.get(VMFlds.SCRIPT_SERVER_PATH).toString();
//				log.debug("================WinScriptsUploadInstance中的SCRIPT_SERVER_PATH = " + scriptServerPath);
				
				String vappName = deviceParamInfo.get(VMFlds.VAPP_NAME);
				
				//TODO 测试用，生产要改
				String adapterScriptPath = vappName;
//				String adapterScriptPath = Constants.JAVA_IO_TMPDIR + File.separator + vmId;
				
				// 执行脚本上传需要的参数
				body.setString(VMFlds.ESXI_URL, esxiUrl);
				body.setString(VMFlds.ESXI_USERNAME, esxiUname);
				body.setString(VMFlds.ESXI_PASSWORD, esxiPwd);
				body.setString(VMFlds.GUEST_USER_NAME, guestUname); 
				body.setString(VMFlds.GUEST_PASSWORD, guestPwd);
				
				//根据数据中心id取得脚本服务器SCRIPT_SERVER_IP等信息并装入body
				body = this.putScriptServerInfo(body, dataCenterId);
				
				
//				body.setString(SAConstants.SERVER_IP,               //旧版的读取脚本服务器信息，unused
//						this.getConfigParam(SCRIPT_SERVER_IP));
//				body.setString(SAConstants.USER_NAME,
//						this.getConfigParam(SCRIPT_USER_NAME));
//				body.setString(SAConstants.USER_PASSWORD,
//						this.getConfigParam(SCRIPT_USER_PASSWORD));
				
//				body.setString(VMFlds.EXECUTE_SCRIPT, cmdShell);   //这个没有用到
				
				body.setList(VMFlds.SCRIPT_LIST_NAME, scriptPathNameList);
				
//				//脚本服务器上需上传脚本路径
//				uploadVo.setScriptServerPath(reqBody.getString(VMFlds.SCRIPT_SERVER_PATH));
//				//上传到适配器服务器路径
//				uploadVo.setAdapterScriptPath(reqBody.getString(VMFlds.ADAPTER_SCRIPT_PATH));
				body.setString(VMFlds.SCRIPT_SERVER_PATH, scriptServerPath);
				body.setString(VMFlds.ADAPTER_SCRIPT_PATH, adapterScriptPath);
				body.setString(VMFlds.VM_TYPE, VmGlobalConstants.VM_TYPE_WIN);
				body.setString(VMFlds.VAPP_NAME, vappName);
				
				body.setBoolean(SAConstants.IS_TOARRAY, false);
				body.setBoolean(SAConstants.IS_KEY, false);
				body.setInt(SAConstants.CHARSET, 0);
				
				requestDataObject.setDataObject(MesgFlds.BODY, body);

				// 调用适配器执行操作
				IDataObject responseDataObject;
				responseDataObject = getResAdpterInvoker().invoke(
						requestDataObject, getTimeOut());

				// 处理返回结果
				handleResonpse(contextParams, responseDataObject);
				// 获取操作执行结果状态
				result = getHandleResult(responseDataObject);

				if (!PubConstants.EXEC_RESULT_SUCC.equals(result)) {
					log.error("upload failed deviceId" + vmId);
				}
//				// Add new version return 
				String instanceAutomationReturn = getInstanceAutomationReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
						vmId,responseDataObject);
				log.info("下发脚本Instance返回：" + instanceAutomationReturn);
				return instanceAutomationReturn;

			} catch (Exception e) {
				return instaceHandleException(contextParams, vmId,
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
	protected IDataObject buildRequestData(
			Map<String, Object> contenxtParmas) {
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
		
		HeaderDO rspHeader = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		String execResult = rspHeader.getContianer().get(MesgFlds.RET_CODE).toString();
		responseDataObject.set(EXEC_RESULT, execResult);
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
	
}