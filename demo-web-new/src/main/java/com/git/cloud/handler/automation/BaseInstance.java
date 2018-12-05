package com.git.cloud.handler.automation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;

/**
 * 单进程实例父类， 在其中定义了x86安装Instance类的excute方法。
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 April 21, 2014
 * @see
 */
public abstract class BaseInstance extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(BaseInstance.class);

	private final static int TIME_OUT = 120 * 60 * 1000;

	protected boolean hasFailed = false;
	protected static String IS_REPEAT = "IS_REPEAT";	// 是否持续执行节点直到返回成功的参数key
	protected static String IS_REPEAT_YES = "YES";
	protected static String IS_REPEAT_NO = "NO";
	// body中返回结果的key，如果格式为VM_NAME.VM_DATASTORE_NAME则程序解析返回对象并读取类DataObject中的VM_DATASTORE_NAME属性
	protected static String REPEAT_EXEC_RESULT_BODY_KEY = "REPEAT_EXEC_RESULT_BODY_KEY";
	/**
	 * 执行成功应该返回的值，如果格式为[$key]则表示从流程参数表中读取该key的值作为执行成功应该返回的值
	 */
	protected static String REPEAT_EXEC_RESULT_SUCCESS_IDEN = "REPEAT_EXEC_RESULT_SUCCESS_IDEN";
	protected static String REPEAT_EXEC_EXCEPTION_CONTINUE = "REPEAT_EXEC_EXCEPTION_CONTINUE";
	protected static String REPEAT_EXEC_EXCEPTION_CONTINUE_YES = "YES";
	protected static String REPEAT_EXEC_EXCEPTION_CONTINUE_NO = "NO";
	

	@Override
	protected int getTimeOut() {
		return TIME_OUT;
	}

	@Override
	public String execute(HashMap<String, Object> contextParams) throws Exception {
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			// 节点ID
			String nodeId = getContextStringPara(contextParams, NODE_ID);
			// // 服务请求ID
			// Long srvReqId = (Long) contextParams.get(SRV_REQ_ID);
			//
			// // 资源请求ID
			String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			// 读取虚拟机列表，用于取得参数信息
			@SuppressWarnings("unchecked")
			List<String> vmIds = (List<String>) contextParams.get("destVmIds");
			// 获取虚机设备id
			String deviceId = vmIds.get(0);
			String deviceLogStr = null;
			String logMsg = null;
			try {
				String className = this.getClass().getName();
				deviceLogStr = "[设备:" + deviceId + "]";
				logMsg = "虚机执行【" + className + "】线程，流程实例ID:" + flowInstId
						+ "，节点ID:" + nodeId + "。" + deviceLogStr;
				log.debug(logMsg);
				long startTime = System.currentTimeMillis();
				log.debug("线程执行自动化操作开始,设备ID:{}", deviceId);
				// 单个设备的参数，将全局参数加入到单个设备参数中
				Map<String, Object> deviceParamInfo = new HashMap<String, Object>();
				deviceParamInfo.putAll(contextParams);
				// 获取参数表中的所有参数
				Map<String, Map<String, String>> handleParams = this
						.getHandleParams(flowInstId);
				// 将工作流相关参数和业务参数合并
				contextParams.putAll(handleParams);
				// 扩展业务参数
				Map<String, Object> extHandleParams = getExtHandleParams(contextParams);
				if (extHandleParams != null) {
					contextParams.putAll(extHandleParams);
				}
				deviceParamInfo.putAll((Map<String, Object>) contextParams
						.get(deviceId.toString()));
				// deviceParamInfo.put(RRINFO_ID,rrinfoId);
				// 构造自动化操作请求参数
				IDataObject requestDataObject = this.buildRequestData(deviceParamInfo);

				// 调用适配器执行操作
				IDataObject responseDataObject = null;
				log.info("Time out is : " + getTimeOut());
				String isRepeat = getContextStringPara(contextParams, IS_REPEAT);
				String timeOutLog = null;
				if (isRepeat != null && isRepeat.trim().toUpperCase().equals(IS_REPEAT_YES)) {
					//如果该节点被配置成持续执行，则循环执行此节点，直到返回成功标识
					boolean isTimeout = true;
					int runTimes = 1;
					while ((System.currentTimeMillis() - startTime) <= getTimeOut()) {
						if (runTimes > 1) {
							Thread.sleep(20 * 1000);
						}
						log.info("此节点第{}次运行，运行时间：{}", runTimes++, (System.currentTimeMillis() - startTime));
						responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());
						if (getReturnCode(responseDataObject).equals(MesgRetCode.SUCCESS)) {
							// 如果执行成功，则判断返回标识是否正确，如果正确则跳出循环，否则继续循环
							String resultKey = getContextStringPara(contextParams, REPEAT_EXEC_RESULT_BODY_KEY);
							log.info("resultKey=" + resultKey);
							if (resultKey == null) {
								throw new Exception("没有在流程节点中定义body返回的key，" + REPEAT_EXEC_RESULT_BODY_KEY);
							}
							String successIden = getContextStringPara(contextParams, REPEAT_EXEC_RESULT_SUCCESS_IDEN).trim();
							log.info("successIden=" + successIden);
							
							if (successIden == null) {
								throw new Exception("没有在流程节点中定义成功标识，" + REPEAT_EXEC_RESULT_SUCCESS_IDEN);
							} else if (successIden.startsWith("[$") && successIden.endsWith("]")) {
								log.info("successIden=" + successIden);
								successIden = getContextStringPara(deviceParamInfo, successIden);
								log.info("successIden=" + successIden);
								if (successIden == null) {
									throw new Exception("没有在流程节点中定义成功标识，" + REPEAT_EXEC_RESULT_SUCCESS_IDEN);
								}
							}
							String returnResult = LinkDataObjectUtil.getReturnBodyItem(responseDataObject, resultKey);
							log.info("returnResult=" + returnResult);
							if (returnResult == null) {
								throw new Exception("Automation返回body中没有成功标识，key:" + REPEAT_EXEC_RESULT_BODY_KEY);
							}
							if (returnResult.trim().equalsIgnoreCase(successIden.trim())) {
								log.info("body返回结果与预定义标识一致，节点正确执行，跳出循环");
								isTimeout = false;
								break;
							} else {
								timeOutLog = "body返回结果与预定义标识不一致，节点没有正确执行，继续循环。body返回值:" + returnResult.trim() + 
										", 预定义值:" + successIden.trim();
								log.info(timeOutLog);
								continue;
							}
						} else {
							// 如果执行失败，则根据配置来确定是否继续
							String continueExec = getContextStringPara(contextParams, REPEAT_EXEC_EXCEPTION_CONTINUE);
							if (continueExec != null && continueExec.trim().equalsIgnoreCase(REPEAT_EXEC_EXCEPTION_CONTINUE_YES)) {
								continue;
							} else {
								isTimeout = false;
								break;
							}
						}
					}
					if (isTimeout) {
						log.info("节点执行过程中没有返回正确的结果且执行超时，超时时间：" + getTimeOut() + "ms");
						if (getReturnCode(responseDataObject).equals(MesgRetCode.SUCCESS)) {
							LinkDataObjectUtil.setReturnCode(responseDataObject, MesgRetCode.ERR_TIMEOUT);
						}
						LinkDataObjectUtil.addReturnMesg(responseDataObject, 
								"执行超时，超时时间：" + getTimeOut() + "ms，" + timeOutLog);
					}
				} else {
					// 节点只执行一次
					responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());
				}
				boolean needSaveFlag = true;
				return instanceHandleNormal(contextParams, flowInstId, nodeId,
						deviceId, startTime, deviceLogStr, className, needSaveFlag,
						responseDataObject);
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
	 * @param contextParams
	 * @param flowInstId
	 * @param nodeId
	 * @param deviceId
	 * @param startTime
	 * @param deviceLogStr
	 * @param className
	 * @param needSaveFlag
	 *            :是否需要保存参数实例信息；
	 * @param responseDataObject
	 * @return
	 * @throws Exception
	 */
	protected String instanceHandleNormal(
			HashMap<String, Object> contextParams, String flowInstId,
			String nodeId, String deviceId, Long startTime, String deviceLogStr,
			String className, boolean needSaveFlag,
			IDataObject responseDataObject) throws Exception {
		String result;
		// 获取操作执行结果状态
		HeaderDO header = (HeaderDO) responseDataObject.get(MesgFlds.HEADER);
		result = header.getString(MesgFlds.RET_CODE);
		if (!result.equals(MesgRetCode.SUCCESS)) {
			hasFailed = true;
		}
		if (hasFailed) {
			String errorMsg = "虚机执行【" + className + "】运行脚本失败,流程实例ID:"
					+ flowInstId + ",节点ID:" + nodeId + ",DeviceId:" + deviceId;
			log.error(errorMsg);
		} else if (needSaveFlag) {
			// 保存新产生的参数实例
			saveParamInsts(flowInstId, nodeId);
		}
		// Add new version return
		String instanceAutomationReturn = getInstanceAutomationReturn(getContextStringPara(contextParams,
						CloudGlobalConstants.BUS_VERSION), deviceId, responseDataObject);
		log.info(instanceAutomationReturn);
		log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。{}", new Object[] {
				flowInstId, nodeId, new Long((System.currentTimeMillis() - startTime)),
				deviceLogStr });
		return instanceAutomationReturn;
	}

	/**
	 * @param contextParams
	 * @param deviceId
	 * @param deviceLogStr
	 * @param logMsg
	 * @param e
	 * @return
	 */
	protected String instaceHandleException(
			HashMap<String, Object> contextParams, String deviceId,
			String deviceLogStr, String logMsg, Exception e) {
		String errorMsg = logMsg + "错误：{" + e.getMessage() + "}" + deviceLogStr;
		log.error(errorMsg,e);
		printExceptionStack(e);
		// Add new version return
		String instanceAutomationReturn = getInstanceStringReturn(
				getContextStringPara(contextParams,
						CloudGlobalConstants.BUS_VERSION), deviceId, errorMsg);
		log.info(instanceAutomationReturn);
		return instanceAutomationReturn;
	}

}
