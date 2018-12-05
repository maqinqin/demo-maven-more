package com.git.cloud.handler.automation.sa.powervm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.Constants;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.common.NetUtil;
import com.git.cloud.handler.common.PropertiesFileUtil;
import com.git.cloud.handler.dao.AutomationDAO;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.PowerVMAIXService;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgRetCode;
import com.git.support.constants.SAConstants;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;

/**
 * 
 * <p>
 * 产生 oracle rac 配置文件，并发送到目标机器
 * 
 * @author zhuzhaoyong
 * @version 1.0 2013-5-20
 * @see
 */
@Repository("powerAIXVMOracleRacSendOracleConfHandler")
public class PowerVMAIXOracleRACSendOracleConfHandler extends
		RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(PowerVMAIXOracleRACSendOracleConfHandler.class);
	
	private String VM_NAME = "vm_name";
	PowerVMAIXService powerVMAIXService;
	AutomationDAO AutomationDAOImpl;
	AutomationService automationService;
	ParameterService parameterService;
	// 输出的日志中的公用信息
	private String logMsg = null;

	/**
	 * 初始化所需的dao类
	 */
	private void initDao() {
		if (powerVMAIXService == null) {
			powerVMAIXService = (PowerVMAIXService) WebApplicationManager.getBean("powerVMAIXServiceImpl");
		}
		if (AutomationDAOImpl == null) {
			AutomationDAOImpl = (AutomationDAO) WebApplicationManager.getBean("AutomationDaoImpl");
		}
		if (parameterService == null) {
			parameterService = (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
		}
		if (automationService == null) {
			automationService = (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ccb.iomp.cloud.core.automation.handler.IAutomationHandler#execute
	 * (java.util.HashMap)
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) {
		
		/*FileWriter fw = null;
		File confFile = null;*/
		try {
			initDao();
			// 流程实例Id
			String flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			// 节点ID
			String nodeId = getContextStringPara(contextParams, NODE_ID);
			// 资源请求ID
			String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			
			if (contextParams.get(TIME_OUT_STR) == null || StringUtils.isEmpty(contextParams.get(TIME_OUT_STR).toString())) {
				contextParams.put(TIME_OUT_STR, TIME_OUT);
			}
			
			logMsg = "创建Power服务，生成并下发rac_parameter.conf，流程实例ID：" + flowInstId + ",节点ID：" + nodeId + "。";
			
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			
			// 获取全局业务参数
			Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);
			
			// 获取文件在本地的临时存放路径
			/*String oracleConfPath = ParameterService.getConstant(PowerVMAIXVariable.P_V_O_R_LOCAL_ORACLE_CONF_TMP_PATH);
			if (StringUtils.isEmpty(oracleConfPath)) {
				oracleConfPath = Constants.JAVA_IO_TMPDIR;
			}
			if (!oracleConfPath.endsWith(Constants.FILE_SEPARATOR)) {
				oracleConfPath += Constants.FILE_SEPARATOR;
			}
			File path = new File(oracleConfPath);
			if (!path.exists()) {
				path.mkdirs();
			}*/
			
			// 存放文件内容的对象
			StringBuffer racPara = new StringBuffer();
			
			// 在本地临时存放的文件名
			/*String uuidFileName = UUID.randomUUID().toString();
			confFile = new File(oracleConfPath + uuidFileName);
			log.debug("Create oracle rac params file. " + confFile);*/
			
			// 读取服务关联的虚拟机列表
			List<String> deviceIdList = getDeviceIdList(flowInstId, nodeId, rrinfoId, "");
			Map<String, String> vioc1 = null;
			Map<String, String> vioc2 = null;
			if (deviceIdList != null && deviceIdList.size() == 2) {
				vioc1 = handleParams.get(deviceIdList.get(0).toString());
				vioc2 = handleParams.get(deviceIdList.get(1).toString());
			} else {
				throw new Exception("服务所关联的虚拟机数量不对。");
			}
			racPara.append("NODE1=\"").append(vioc1.get(PowerVMAIXVariable.PRODUCT_IP_1)).append("\"").append(Constants.BR);
			racPara.append("NODE2=\"").append(vioc2.get(PowerVMAIXVariable.PRODUCT_IP_1)).append("\"").append(Constants.BR);
			racPara.append("PUB1_IP=\"").append(vioc1.get(PowerVMAIXVariable.PRODUCT_IP_1)).append("\"").append(Constants.BR);
			racPara.append("PUB2_IP=\"").append(vioc2.get(PowerVMAIXVariable.PRODUCT_IP_1)).append("\"").append(Constants.BR);
			racPara.append("PRIV1_IP=\"").append(vioc1.get(PowerVMAIXVariable.HEART_IP)).append("\"").append(Constants.BR);
			racPara.append("PRIV2_IP=\"").append(vioc2.get(PowerVMAIXVariable.HEART_IP)).append("\"").append(Constants.BR);
			racPara.append("VIP1_IP=\"").append(vioc1.get(PowerVMAIXVariable.PRODUCT_IP_2)).append("\"").append(Constants.BR);
			racPara.append("VIP2_IP=\"").append(vioc2.get(PowerVMAIXVariable.PRODUCT_IP_2)).append("\"").append(Constants.BR);
			racPara.append("SCAN_IP=\"").append(vioc1.get(PowerVMAIXVariable.PRODUCT_IP_3)).append("\"").append(Constants.BR);
			String deviceName1 = vioc1.get(PowerVMAIXVariable.HOSTNAME);
			if(deviceName1 == null || "".equals(deviceName1)) {
				deviceName1 = vioc1.get(VM_NAME);
			}
			String deviceName2 = vioc2.get(PowerVMAIXVariable.HOSTNAME);
			if(deviceName2 == null || "".equals(deviceName2)) {
				deviceName2 = vioc2.get(VM_NAME);
			}
			racPara.append("PUB1_NAME=\"").append(deviceName1).append("\"").append(Constants.BR);
			racPara.append("PUB2_NAME=\"").append(deviceName2).append("\"").append(Constants.BR);
			racPara.append("VIP1_NAME=\"").append(deviceName1).append("-vip").append("\"").append(Constants.BR);
			racPara.append("VIP2_NAME=\"").append(deviceName2).append("-vip").append("\"").append(Constants.BR);
			racPara.append("PRIV1_NAME=\"").append(deviceName1).append("-pri").append("\"").append(Constants.BR);
			racPara.append("PRIV2_NAME=\"").append(deviceName2).append("-pri").append("\"").append(Constants.BR);
			
			Map<String, String> cloudAttr = automationService.getServiceAttr(rrinfoId);
			
			racPara.append(PropertiesFileUtil.makePropertiesFileOneLine("SCANNAME", cloudAttr.get(PowerVMAIXVariable.DB_NAME) + "-scan"
					, true, false));
			
			racPara.append("PUB_EN=").append(vioc1.get(PowerVMAIXVariable.INTERFACE_0)).append(Constants.BR);
			String pubSubnet = NetUtil.getNetAddr(vioc1.get(PowerVMAIXVariable.PRODUCT_IP_1),
					vioc1.get(PowerVMAIXVariable.PRODUCT_IP_1_MASK));
			racPara.append("PUB_SUBNET=").append(pubSubnet).append(Constants.BR);
			
			racPara.append("PRIV_EN=").append(vioc1.get(PowerVMAIXVariable.INTERFACE_1)).append(Constants.BR);
			String privSubnet = NetUtil.getNetAddr(vioc1.get(PowerVMAIXVariable.HEART_IP), vioc1.get(PowerVMAIXVariable.HEART_MASK));
			racPara.append("PRIV_SUBNET=").append(privSubnet).append(Constants.BR);
			
			racPara.append(PropertiesFileUtil.makePropertiesFileOneLine("DB_NAME", cloudAttr.get(PowerVMAIXVariable.DB_NAME)
					, true, false));
			//DB_BLOCK_SIZE由写死为8，改成从用户填写自定义参数
			//racPara.append("DB_BLOCK_SIZE=").append(ParameterService.getConstant(PowerVMAIXVariable.P_V_O_R_DB_BLOCK_SIZE)).append(Constants.BR);
			if(null == cloudAttr.get("DB_BLOCK_SIZE") || "".equals(cloudAttr.get("DB_BLOCK_SIZE")))
				racPara.append("DB_BLOCK_SIZE=").append(parameterService.getParamValueByName((PowerVMAIXVariable.P_V_O_R_DB_BLOCK_SIZE))).append(Constants.BR);
			else
				racPara.append("DB_BLOCK_SIZE=").append(cloudAttr.get("DB_BLOCK_SIZE")).append(Constants.BR);
			// Write new oracle rac parameter
			/*NLS_CHARACTERSET=ALS32UTF8      
			NLS_NCHAR_CHARACTERSET=AL16UTF16  
			DISKMTYPE=NORMAL                  */
			
			racPara.append("NLS_CHARACTERSET=").append(cloudAttr.get(PowerVMAIXVariable.P_V_O_R_NLS_CHARACTERSET)).append(Constants.BR);
			racPara.append("NLS_NCHAR_CHARACTERSET=").append(cloudAttr.get(PowerVMAIXVariable.P_V_O_R_NLS_NCHAR_CHARACTERSET)).append(Constants.BR);
			racPara.append("DISKMTYPE=").append(cloudAttr.get(PowerVMAIXVariable.P_V_O_R_DISKMTYPE)).append(Constants.BR);
			
			racPara.append("NAS_VIP=").append(vioc1.get("NAS_VIP")).append(Constants.BR);
//			racPara.append("NAS_FILESYSTEM=").append("/vol_").append(vioc1.get(PowerVMAIXVariable.LPARNAME)).append(BR);
			racPara.append("NAS_FILESYSTEM=").append(vioc1.get("NAS_FILESYSTEM")).append(Constants.BR);
//			String cpuQt = vioc1.get(PowerVMAIXVariable.CPU_QT);
			String memQt = vioc1.get("mem");
//			int memSize = Integer.parseInt(memQt) * 1024 * 1024;
			int memSize = Integer.parseInt(memQt);
//			int memSize = Integer.parseInt(cpuQt) * 1024 * 4;
			racPara.append("MEMSIZE=").append(memSize).append(Constants.BR);
			racPara.append("DB_CACHE_SIZE=").append(memSize / 32768 * 2048).append(Constants.BR);
			racPara.append("SHARED_POOL_SIZE=").append(memSize / 32768 * 2048).append(Constants.BR);
			racPara.append("PGA_AGGREGATE_TARGET=").append(memSize / 32768 * 1024).append(Constants.BR);
			racPara.append("SGA_MAX_SIZE=").append(memSize / 32768 * 5120).append(Constants.BR);
			
			log.debug("Oracle conf file :" + Constants.BR + racPara);
			
			/*log.debug("Write oracle conf file.");
			fw = new FileWriter(confFile, false);
			fw.write(racPara.toString());
			fw.close();
			fw = null;*/
			
			StringBuffer rac1 = new StringBuffer(racPara.toString());
			StringBuffer rac2 = new StringBuffer(racPara.toString());
			//String storageJson = vioc1.get(PowerVMAIXVariable.STORAGE_RESULT);
			String storageJson = getBigParamValue(vioc1, PowerVMAIXVariable.STORAGE_RESULT);
			if (StringUtils.isEmpty(storageJson)) {
				throw new Exception("oracle的存储路径STORAGE_RESULT为空。");
			}
			JSONObject json = (JSONObject) JSON.parse(storageJson);
			rac1.append("ARCHDISK=\"").append(json.get(PowerVMAIXVariable.ARCHDISK)).append("\"").append(Constants.BR);
			rac1.append("DATADISK=\"").append(json.get(PowerVMAIXVariable.DATADISK)).append("\"").append(Constants.BR);
			rac1.append("SYSDISK=\"").append(json.get(PowerVMAIXVariable.SYSDISK)).append("\"").append(Constants.BR);
			rac1.append("ARCHDISKM=\"").append(json.get(PowerVMAIXVariable.ARCHDISKM) == null ? "" : json.get(PowerVMAIXVariable.ARCHDISKM)).append("\"").append(Constants.BR);
			rac1.append("DATADISKM=\"").append(json.get(PowerVMAIXVariable.DATADISKM) == null ? "" : json.get(PowerVMAIXVariable.DATADISKM)).append("\"").append(Constants.BR);
			rac1.append("SYSDISKM=\"").append(json.get(PowerVMAIXVariable.SYSDISKM) == null ? "" : json.get(PowerVMAIXVariable.SYSDISKM)).append("\"").append(Constants.BR);
			
			storageJson = getBigParamValue(vioc2, PowerVMAIXVariable.STORAGE_RESULT);
			if (StringUtils.isEmpty(storageJson)) {
				throw new Exception("oracle的存储路径STORAGE_RESULT为空。");
			}
			json = (JSONObject) JSON.parse(storageJson);
			rac2.append("ARCHDISK=\"").append(json.get(PowerVMAIXVariable.ARCHDISK)).append("\"").append(Constants.BR);
			rac2.append("DATADISK=\"").append(json.get(PowerVMAIXVariable.DATADISK)).append("\"").append(Constants.BR);
			rac2.append("SYSDISK=\"").append(json.get(PowerVMAIXVariable.SYSDISK)).append("\"").append(Constants.BR);
			rac2.append("ARCHDISKM=\"").append(json.get(PowerVMAIXVariable.ARCHDISKM) == null ? "" : json.get(PowerVMAIXVariable.ARCHDISKM)).append("\"").append(Constants.BR);
			rac2.append("DATADISKM=\"").append(json.get(PowerVMAIXVariable.DATADISKM) == null ? "" : json.get(PowerVMAIXVariable.DATADISKM)).append("\"").append(Constants.BR);
			rac2.append("SYSDISKM=\"").append(json.get(PowerVMAIXVariable.SYSDISKM) == null ? "" : json.get(PowerVMAIXVariable.SYSDISKM)).append("\"").append(Constants.BR);
			
			// 读取vioc1的用户和密码
			String vioc1Password = vioc1.get(SAConstants.USER_PASSWORD);
			//powerVMAIXService.findResourcePassword(deviceIdList.get(0), AIX_VM_USER);
			// 发送文件到目标服务器
			String destOracleConfPath = parameterService.getParamValueByName(PowerVMAIXVariable.P_V_O_R_ORACLE_CONF_PATH);
			RmDatacenterPo datacenter = automationService.getDatacenter(rrinfoId);
			String fileName = parameterService.getParamValueByName(PowerVMAIXVariable.P_V_O_R_ORACLE_CONF_FILENAME);
			
			sendStringAndMakeFile(vioc1.get(SAConstants.SERVER_IP), PowerVMAIXVariable.AIX_VM_USER, vioc1Password, 
					fileName, rac1.toString(), destOracleConfPath, datacenter.getQueueIden(), 
					Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()));
			
			/*sendLocalFile(vioc1.get(SAConstants.SERVER_IP), PowerVMAIXVariable.AIX_VM_USER, vioc1Password, destOracleConfPath, 
					oracleConfPath, uuidFileName, ParameterService.getParameter(PowerVMAIXVariable.P_V_O_R_ORACLE_CONF_FILENAME),
					Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()), datacenter);*/
			
			// 读取vioc2的用户和密码
			String vioc2Password = vioc2.get(SAConstants.USER_PASSWORD);
			sendStringAndMakeFile(vioc2.get(SAConstants.SERVER_IP), PowerVMAIXVariable.AIX_VM_USER, vioc2Password, 
					fileName, rac2.toString(), destOracleConfPath, datacenter.getQueueIden(), 
					Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()));
			//powerVMAIXService.findResourcePassword(deviceIdList.get(1), AIX_VM_USER);
			// 发送文件到目标服务器
			/*sendLocalFile(vioc2.get(SAConstants.SERVER_IP), PowerVMAIXVariable.AIX_VM_USER, vioc2Password, destOracleConfPath,
					oracleConfPath, uuidFileName, ParameterService.getParameter(PowerVMAIXVariable.P_V_O_R_ORACLE_CONF_FILENAME),
					Integer.parseInt(contextParams.get(TIME_OUT_STR).toString()), datacenter);*/
			
			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
					logMsg, MesgRetCode.SUCCESS);
		} catch (Exception e) {
			String errorMsg = logMsg + "错误：{" + e + "}";
			log.error(errorMsg);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(getContextStringPara(contextParams, CloudGlobalConstants.BUS_VERSION), 
					errorMsg, MesgRetCode.ERR_OTHER);
		} finally {
			/*try {
				if (fw != null) {
					fw.close();
					fw = null;
				}
				// 删除本地服务器的文件
				if (confFile.exists()) {
					log.debug("删除本地服务器的文件" + confFile.getAbsolutePath());
					FileUtils.deleteQuietly(confFile);
				}
			} catch (IOException e) {
				String errorMsg = logMsg + "{" + e.getMessage() + "}";
				log.error(errorMsg);
				printExceptionStack(e);
				return errorMsg;
			}*/
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.ccb.iomp.cloud.core.automation.handler.RemoteAbstractAutomationHandler#buildRequestData(java.util.Map)
	 */
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ccb.iomp.cloud.core.automation.handler.RemoteAbstractAutomationHandler#handleResonpse(java.util.Map, com.ccb.iomp.cloud.pub.sdo.inf.IDataObject)
	 */
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {
		// TODO Auto-generated method stub
		
	}
	
}
