package com.git.cloud.handler.automation.se.db;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.SshExecShell;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.vo.SshExecShellVo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.resmgt.storage.model.vo.StorageDeviceVo;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.PwdUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CreateIGFlagSecondHandler  extends RemoteAbstractAutomationHandler{

	private static Logger log = LoggerFactory
			.getLogger(CreateIGFlagSecondHandler.class);
	private List<String> deviceIdList = Lists.newArrayList();
	private String mgr_id = new String();
	private StringBuffer sb = new StringBuffer("执行命令：").append("\n");
	private static final String FILE_PATH = "/config/ParameterStorageInterface.properties";
	Properties prop = new Properties();

	private static final String CHECK_IG_FLAG_VMAX = "CHECK_IG_FLAG_VMAX";
	private static final String CHECK_IG_FLAG_VSP = "CHECK_IG_FLAG_VSP";
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public String execute(HashMap<String, Object> contextParams) {
		Map<String, String> rtn_map = Maps.newHashMap();
		if(contextParams!=null){
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			String nodeId = (String) contextParams.get(NODE_ID);
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			try {
				Map<String, Map<String, String>> handleParams = this
						.getHandleParams(flowInstId);

				contextParams.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contextParams);

				if (extHandleParams != null)
					contextParams.putAll(extHandleParams);

				String rrinfoId = String.valueOf(contextParams
						.get(RRINFO_ID));

				deviceIdList = getDeviceIdList("","",rrinfoId,"");
				if (deviceIdList == null || deviceIdList.size() == 0) {
					String errorMsg = "The Device List Of The rrinfoId [ "
							+ rrinfoId + " ] Is Null";
					throw new Exception(errorMsg);
				}
				Map<String, Object> deviceInfoMap = (Map<String, Object>) contextParams
						.get(String.valueOf(deviceIdList.get(0)));
//				String create_ig_flag = (String)deviceInfoMap.get(StorageConstants.STORAGEONE_IG_FLAG_FINISHED);
//				if(StringUtils.isNotBlank(create_ig_flag)){
//					throw new Exception("已完成创建SN:["+create_ig_flag+"]创建IG_FLAG操作！");
//				}
				String sn = (String) deviceInfoMap.get(StorageConstants.STORAGETWO);
				if (StringUtils.isBlank(sn)) {
					throw new Exception("获取存储设备SN信息错误");
				}
				SshExecShellVo vo = new SshExecShellVo();

				String storage_model = (String) deviceInfoMap
						.get(StorageConstants.STORAGE_MODEL);
				// 获取系统名称AIX,HP-UX,LINUX
				String os_name = (String) deviceInfoMap
						.get(StorageConstants.OS_NAME);
				// 获取系统版本信息
				String os_version = (String) deviceInfoMap
						.get(StorageConstants.OS_VERSION);
				List<String> cmd_list = Lists.newArrayList();
				List<String> values = Lists.newArrayList();
				// 获取view_name或host-group信息
				String view_name = (String) deviceInfoMap
						.get(StorageConstants.VIEW_NAME);
				InputStream in = getClass().getResourceAsStream(FILE_PATH);
		        prop.load(in);
				// 根据存储设备类型判断获取创建IG-FLAG命令
				if (storage_model.equals(StorageConstants.STORAGE_MODEL_VMAX)) {
					vo = getSshExecShellVo(deviceInfoMap,storage_model);
					String cmd_key ="";
					String cmd = "";
					if (StringUtils.isBlank(view_name)) {
						throw new Exception("获取sn:" + sn
								+ " view name 错误！");
					}
					if (os_name.equals(StorageConstants.OS_NAME_AIX)) {
						cmd_key = "VMAX_AIX_IG_FLAG";
						cmd = prop.getProperty("VMAX_AIX_IG_FLAG");
					} else if (os_name.contains(StorageConstants.OS_NAME_HP_UX)) {
						if (os_version.contains(StorageConstants.OS_VERSION_HU_UX_1111)) {
							cmd_key = "VMAX_HP-UX_1111_IG_FLAG";
							cmd = prop.getProperty("VMAX_HP-UX_1111_IG_FLAG");
						} else if (os_version.contains(StorageConstants.OS_VERSION_HU_UX_1123)) {
							cmd_key = "VMAX_HP-UX_1123_IG_FLAG";
							cmd = prop.getProperty("VMAX_HP-UX_1123_IG_FLAG");
						} else if (os_version.contains(StorageConstants.OS_VERSION_HU_UX_1131)) {
							cmd_key = "VMAX_HP-UX_1131_IG_FLAG";
							cmd = prop.getProperty("VMAX_HP-UX_1131_IG_FLAG");
						}
					} else if (os_name.equalsIgnoreCase(StorageConstants.OS_NAME_LINUX)) {
						cmd_key = "VMAX_LINUX_IG_FLAG";
						cmd = prop.getProperty("VMAX_LINUX_IG_FLAG");
					} else {
						throw new Exception("未知OS_NAME类型");
					}
					if(StringUtils.isBlank(cmd)){
						throw new Exception("OS_NAME:["+os_name+"] OS_VERSION:["+os_version+"] 获取IG-FLAG命令失败！");
					}
					values = Arrays.asList(new String[] { sn, view_name });
					cmd_list.addAll(generateCmdList(cmd, values,cmd_key));
					execIGFlagCmds(vo, cmd_list);
					checkVMAXIgFlag(vo, sn, view_name);
				} else if (storage_model.equals(StorageConstants.STORAGE_MODEL_VSP)) {
					vo = getSshExecShellVo(deviceInfoMap,storage_model);
					createVSPIGFlag(deviceInfoMap, sn, view_name, os_name, vo);
				}

				for (int i = 0; i < deviceIdList.size(); i++) {
					String deviceId = String.valueOf(deviceIdList.get(i));
					setHandleResultParam(deviceId,
							StorageConstants.STORAGETWO_IG_FLAG_FINISHED, sn);
				}

				saveParamInsts(flowInstId, nodeId);
			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:"
						+ nodeId;
				log.error(errorMsg, e);
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getMessage());
				return JSON.toJSONString(rtn_map);
			} finally {
				if (contextParams != null)
					contextParams.clear();
			}

			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });

			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", sb.toString());
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null");
		}
		return JSON.toJSONString(rtn_map);
	}

	/**
	 * get the storage pwwn list from FC_PWWN in the contextParams
	 * 
	 * @param fcpwwnStr
	 *            : FC_PWWN={STORAGE_SN=1 , GROUP_ID=1 , 1=1, 2=2 , 3=3 , 4=4}
	 * @return [1,2,3,4]
	 * @throws Exception 
	 */
	private List<String> getFCPwwnList(Map<String, Object> deviceMapInfo) throws Exception {
		Object fcpwwn = deviceMapInfo.get(StorageConstants.FA_WWNTWO);
		String fcpwwnStr = String.valueOf(fcpwwn);
		String[] array = fcpwwnStr.substring(1, fcpwwnStr.length() - 1).split(
				",");
		List<String> fcPwwnList = Lists.newArrayList();
		for (int i = 0; i < array.length; i++) {
			int index = array[i].indexOf("=");
			if (index != -1) {
				String key = array[i].substring(0, index);
				String value = array[i].substring(index + 1);
				if (key == null || "".equals(key) || value == null
						|| "".equals(value)) {
					throw new Exception("FA_WWN:" + fcpwwn
							+ " contain null value");
				}
				if (!StorageConstants.STORAGE_SN_KEY.equalsIgnoreCase(key
						.trim())) {
					if (!StorageConstants.GROUP_ID.equalsIgnoreCase(key.trim())) {
						fcPwwnList.add(key.trim());
					}
				}
			}
		}
		if (fcPwwnList.size() != 4) {
			throw new Exception("the size of FA_WWN:" + fcpwwn
					+ " is not four");
		}
		return fcPwwnList;
	}

	public List<String> generateCmdList(String _cmd, List<String> values,String cmd_key) throws Exception {
		String cmd = _cmd;
		List<String> list = Lists.newArrayList();
		CharSequence c = cmd.subSequence(0, cmd.length());
		int size = StringUtils.countMatches(c, "!");
		if (values.size() != size) {
			throw new Exception("传入参数[" + values.toString()
					+ "]数量与输入命令：[" + cmd + "]所需参数数量不符");
		}
		for (String value : values) {
			cmd = cmd.replaceFirst("!", value);
		}
		log.info("cmd:["+cmd_key+"="+cmd+"]");
		if(!cmd_key.equals(StorageConstants.VSP_LOGIN)){
			sb.append("cmd:["+cmd_key+"="+cmd+"]").append("\n");			
		}
		list.add(cmd);
		return list;
	}

	public SshExecShellVo getSshExecShellVo(Map<String, Object> deviceInfoMap,String storage_model) {
		// 获取管理机登陆信息
		String unit_info_str = (String) deviceInfoMap
				.get(StorageConstants.UNIT_INFO);
		JSONObject unit_info_map = JSONObject.parseObject(unit_info_str);
		mgr_id = unit_info_map.getString("STORAGEMGR_ID");
		log.info("storage_mgr_id:[" + mgr_id + "]");
		Map<String, String> mgr_map = Maps.newHashMap();
		
		mgr_map.put("managerId", mgr_id);

		if(storage_model.equals(StorageConstants.STORAGE_MODEL_VMAX)){
			mgr_map.put("typeCode", StorageConstants.MACHINE_CODE_EMC_SUPER);
		}else if(storage_model.equals(StorageConstants.STORAGE_MODEL_VSP)){
			mgr_map.put("typeCode", StorageConstants.MACHINE_CODE_HDS_SUPER);
		}
		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoByParam(mgr_map);
		
		String user = mgrVo.getUserName();
		String pwd = mgrVo.getPassword();
		pwd = PwdUtil.decryption(pwd);
		String ip = mgrVo.getManagerIp();

		log.info("user:[" + user + "]");
		log.info("password:[" + pwd + "]");
		log.info("ip:[" + ip + "]");
		SshExecShellVo vo = new SshExecShellVo();
		vo.setUser(user);
		vo.setPwd(pwd);
		vo.setIp(ip);
		return vo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean execIGFlagCmds(SshExecShellVo vo, List<String> cmd_list) {
		try {
			SshExecShell ssh = new SshExecShell();
			List l = ssh.execShell(vo.getIp(), vo.getUser(), vo.getPwd(),
					cmd_list);

			for (int i = 0; i < l.size(); i++) {
				Map<String, Object> request_map = (Map<String, Object>) l
						.get(i);
				String exit_code = String.valueOf(request_map.get("EXIT_CODE"));
				if (StorageConstants.SSH_SUCCESS.equals(exit_code)) {
					continue;
				} else {
					List<String> error_info = (List<String>) request_map
							.get("ERROR_INFO");
					String error_msg = error_info.toString();
					throw new Exception("执行语句[" + cmd_list.get(i)
							+ "]错误：" + error_msg);
				}
			}
		} catch (Exception e) {
			log.error("异常exception",e);
		}
		return Boolean.TRUE;
	}

	public void createVSPIGFlag(Map<String, Object> deviceInfoMap, String sn,
			String view_name, String os_name, SshExecShellVo vo) throws Exception {
		List<String> values = Lists.newArrayList();
		List<String> cmd_list = Lists.newArrayList();
		boolean login_flag = false;
		boolean lock_flag = false;
		if (StringUtils.isBlank(view_name)) {
			throw new Exception("获取sn:" + sn + " host group 错误！");
		}
		try {
			// 添加登陆执行语句
			String login_cmd = prop.getProperty(StorageConstants.VSP_LOGIN);
			Map<String, String> vsp_system_map = getVSPSystemLoginInfo(sn,
					mgr_id);
			String vsp_system_user = vsp_system_map
					.get(StorageConstants.USERNAME);
			String vsp_system_pw = PwdUtil.decryption(vsp_system_map
					.get(StorageConstants.USERPASSWD));
			String vsp_series_number = vsp_system_map
					.get(StorageConstants.VSP_SERIES_NUMBER);
			values = Arrays.asList(new String[] { vsp_system_user,
					vsp_system_pw, vsp_series_number });
			login_flag = execIGFlagCmds(vo,generateCmdList(login_cmd, values,StorageConstants.VSP_LOGIN));
			if (!login_flag) {
				throw new Exception("登陆sn:[" + sn + "]存储系统错误");
			}
			// 加锁语句
			String lock_cmd = prop.getProperty(StorageConstants.VSP_LOCK);
			log.info("cmd:["+StorageConstants.VSP_LOCK+"="+lock_cmd+"]");

			sb.append("cmd:["+StorageConstants.VSP_LOCK+"="+lock_cmd+"]").append("\n");
			lock_flag = execIGFlagCmds(vo,
					Arrays.asList(new String[] { lock_cmd }));
			if (!lock_flag) {
				throw new Exception("存储sn:[" + sn + "]资源加锁失败!");
			}

			List<String> fcportName_list = getFCPwwnList(deviceInfoMap);
			String hds_cmd = "";
			String hds_cmd_key = "";
			if (os_name.equals(StorageConstants.OS_NAME_AIX)) {
				hds_cmd_key = "VSP_AIX_IG_FLAG";
				hds_cmd = prop.getProperty("VSP_AIX_IG_FLAG");
			} else if (os_name.contains(StorageConstants.OS_NAME_HP_UX)) {
				hds_cmd_key = "VSP_HP-UX_IG_FLAG";
				hds_cmd = prop.getProperty("VSP_HP-UX_IG_FLAG");
			} else if (os_name.equalsIgnoreCase(StorageConstants.OS_NAME_LINUX)) {
				hds_cmd_key = "VSP_LINUX_IG_FLAG";
				hds_cmd = prop.getProperty("VSP_LINUX_IG_FLAG");
			}
//			// 添加创建IG-FLAG语句
//			for (String fcportName : fcportName_list) {
//				values = Arrays.asList(new String[] { fcportName, view_name });
//				cmd_list.addAll(generateCmdList(hds_cmd, values,hds_cmd_key));
//			}
//			// 执行创建IG-FLAG语句
//			boolean flag = execIGFlagCmds(vo, cmd_list);
//			if (!flag) {
//				throw new Exception("创建IG-FLAG失败！");
//			}
			checkVSPFlag(vo,fcportName_list, view_name);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (lock_flag) {
				String unlock_cmd = prop.getProperty(StorageConstants.VSP_UNLOCK);
				log.info("cmd:["+StorageConstants.VSP_UNLOCK+"="+unlock_cmd+"]");
				sb.append("cmd:["+StorageConstants.VSP_UNLOCK+"="+unlock_cmd+"]").append("\n");
				execIGFlagCmds(vo, Arrays.asList(new String[] { unlock_cmd }));
			}
			if (login_flag) {
				// 添加登出执行语句
				String logout_cmd = prop.getProperty("VSP_LOGOUT");
				log.info("cmd:["+StorageConstants.VSP_LOGOUT+"="+logout_cmd+"]");
				sb.append("cmd:["+StorageConstants.VSP_LOGOUT+"="+logout_cmd+"]").append("\n");
				execIGFlagCmds(vo, Arrays.asList(new String[] { logout_cmd }));
			}
		}
	}

	public Map<String, String> getVSPSystemLoginInfo(String sn, String mgr_id) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("sn", sn);
		//DevicePo devPo = deviceDao.findObjectByFields(params);
		StorageDeviceVo devVo = storageDao.findStorageDevBySn(sn);
		String storage_series_number = devVo.getRemark();

//		Map<String, String> map = service.getStorageMgrPw(Long.valueOf(mgr_id),
//				StorageConstants.MACHINE_CODE_HDS_SYSTEM,StorageConstants.SMIS_FALSE);
		
		Map<String, String> mgr_map = Maps.newHashMap();
		mgr_map.put("managerId", mgr_id);
		mgr_map.put("typeCode", StorageConstants.MACHINE_CODE_HDS_SYSTEM);
		
		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoByParam(mgr_map);
		Map<String,String> map = Maps.newHashMap();
		
		map.put(StorageConstants.VSP_SERIES_NUMBER, storage_series_number);
		map.put(StorageConstants.USERNAME, mgrVo.getUserName());
		map.put(StorageConstants.USERPASSWD, mgrVo.getPassword());
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean checkVMAXIgFlag(SshExecShellVo vo, String sn,String view_name) {
		try {
			SshExecShell ssh = new SshExecShell();
			List<String> values = Arrays.asList(new String[]{sn,view_name});
			String cmd = prop.getProperty(CHECK_IG_FLAG_VMAX);
			List<String> cmd_list = generateCmdList(cmd, values,CHECK_IG_FLAG_VMAX);
			List l = ssh.execShell(vo.getIp(), vo.getUser(), vo.getPwd(),
					cmd_list);

			for (int i = 0; i < l.size(); i++) {
				Map<String, Object> request_map = (Map<String, Object>) l
						.get(i);
				String exit_code = String.valueOf(request_map.get("EXIT_CODE"));
				if (StorageConstants.SSH_SUCCESS.equals(exit_code)) {
					List<String> echo_info = (List<String>)request_map.get("ECHO_INFO");
					for(String str: echo_info){
						sb.append(str).append("\n");
					}
				} else {
					List<String> error_info = (List<String>) request_map
							.get("ERROR_INFO");
					String error_msg = error_info.toString();
					throw new Exception("执行语句[" + cmd_list.get(i)
							+ "]错误：" + error_msg);
				}
			}
		} catch (Exception e) {
			log.error("异常exception",e);
		}
		return Boolean.TRUE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean checkVSPFlag(SshExecShellVo vo, List<String> fcportName_list,String view_name) {
		try {
			SshExecShell ssh = new SshExecShell();
			for(String fcportName:fcportName_list){
				List<String> values = Arrays.asList(new String[]{fcportName,view_name});
				String cmd = prop.getProperty(CHECK_IG_FLAG_VSP);
				List<String> cmd_list = generateCmdList(cmd, values,CHECK_IG_FLAG_VSP);
				List l = ssh.execShell(vo.getIp(), vo.getUser(), vo.getPwd(),
						cmd_list);
	
				for (int i = 0; i < l.size(); i++) {
					Map<String, Object> request_map = (Map<String, Object>) l
							.get(i);
					String exit_code = String.valueOf(request_map.get("EXIT_CODE"));
					if (StorageConstants.SSH_SUCCESS.equals(exit_code)) {
						List<String> echo_info = (List<String>)request_map.get("ECHO_INFO");
						for(String str: echo_info){
							sb.append(str).append("\n");
						}
					} else {
						List<String> error_info = (List<String>) request_map
								.get("ERROR_INFO");
						String error_msg = error_info.toString();
						throw new Exception("执行语句[" + cmd_list.get(i)
								+ "]错误：" + error_msg);
					}
				}
			}
		} catch (Exception e) {
			log.error("异常exception",e);
		}
		return Boolean.TRUE;
	}
}
