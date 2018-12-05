package com.git.cloud.handler.automation.se.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.SshExecShell;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.vo.SshExecShellVo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.resmgt.storage.model.vo.StorageDeviceVo;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.PwdUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ConfigVSPHostGroupFirstHandler extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(ConfigVSPHostGroupFirstHandler.class);

	private List<String> deviceIdList = Lists.newArrayList();

	private StringBuffer result_sb = new StringBuffer();
	Properties prop = new Properties();
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
	private static final String FILE_PATH = "/config/ParameterStorageInterface.properties";
	@SuppressWarnings("unchecked")
	public String execute(HashMap<String, Object> contextParams) throws Exception {
		Map<String, String> rtn_map = Maps.newHashMap();
		if(contextParams!=null){
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			String nodeId = (String) contextParams.get(NODE_ID);
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
	        InputStream in = getClass().getResourceAsStream(FILE_PATH);    
	        prop.load(in); 
			String vsp_login_cmd = prop.getProperty(StorageConstants.VSP_LOGIN);
			String vsp_lock_cmd = prop.getProperty(StorageConstants.VSP_LOCK);
			String vsp_unlock_cmd = prop.getProperty(StorageConstants.VSP_UNLOCK);
			String vsp_logout_cmd = prop.getProperty(StorageConstants.VSP_LOGOUT);
			List<String> values = Lists.newArrayList();
			boolean login_flag = false;
			boolean lock_flag = false;
			SshExecShellVo vo = new SshExecShellVo();
			try {
				Map<String, Map<String, String>> handleParams = getHandleParams(flowInstId);

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

				// 获取storage_modle
				String storage_model = (String) deviceInfoMap
						.get(StorageConstants.STORAGE_MODEL);
				if (!StorageConstants.STORAGE_MODEL_VSP.equals(storage_model)) {
					throw new Exception("非 VSP 类型存储，不进行HOSTGROUP设置！");
				}
				String finished_flag = (String)deviceInfoMap.get("SETTING_FIRST_HOSTGROUP_FINISHED");
				if(StringUtils.isNotBlank(finished_flag)){
					throw new Exception("已完成SN:["+finished_flag+"] hostgroup 设置");
				}
				String sn = (String) deviceInfoMap
						.get(StorageConstants.STORAGEONE_FINISHED);

				if (StringUtils.isBlank(sn)) {
					throw new Exception("未完成存储sn:" + sn + "分配操作!");
				}

				String assign_info_str = deviceInfoMap.get(sn + "_VIEW_INFO")
						.toString();
				if (StringUtils.isBlank(assign_info_str)) {
					throw new Exception("获取sn:" + sn + "分盘信息失败");
				}
				// 获取规则生成hostgroup信息
				String hostgroup_name = (String) deviceInfoMap.get("VIEW_NAME");
				if (StringUtils.isBlank(hostgroup_name)) {
					throw new Exception("获取HOSTGROUP信息失败！");
				}
				// 获取管理机登陆信息
				String unit_info_str = (String) deviceInfoMap
						.get(StorageConstants.UNIT_INFO);
				JSONObject unit_info_map = JSONObject.parseObject(unit_info_str);
				String mgr_id = unit_info_map.getString("STORAGEMGR_ID");
				
				Map<String, String> mgr_map = Maps.newHashMap();
				mgr_map.put("managerId", mgr_id);
				mgr_map.put("typeCode", StorageConstants.MACHINE_CODE_HDS_SUPER);
				
				StorageMgrVo mgrVo = storageDao.findStorageMgrInfoByParam(mgr_map);
				String user = mgrVo.getUserName();
				String pwd = mgrVo.getPassword();
				pwd = PwdUtil.decryption(pwd);
				String ip = mgrVo.getManagerIp();

				vo.setUser(user);
				vo.setPwd(pwd);
				vo.setIp(ip);

				// 获取port-hostgroup相关命令
				String vsp_hostgroup_gid_cmd = prop.getProperty(StorageConstants.VSP_HOSTGROUP_GID);
				String vsp_hostgroup_config = prop.getProperty(StorageConstants.VSP_HOSTGROUP_CONFIG);
				String vsp_hostgroup_scan = prop.getProperty(StorageConstants.VSP_HOSTGROUP_SCAN);

				// VSP登陆并加锁
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
				String login_cmd = generateCmd(vsp_login_cmd, values,StorageConstants.VSP_LOGIN);

				login_flag = sshCommandExe(vo, login_cmd,StorageConstants.VSP_LOGIN);
				if (!login_flag) {
					throw new Exception("登陆sn:[" + sn + "]"
							+ "VSP 系统失败[" + login_cmd + "]");
				}
				lock_flag = sshCommandExe(vo, vsp_lock_cmd,StorageConstants.VSP_LOCK);
				if (!lock_flag) {
					throw new Exception("锁定sn:[" + sn + "]" + "资源失败["
							+ vsp_lock_cmd + "]");
				}
				// 获取分盘信息
				JSONArray assign_info_array = JSONObject
						.parseArray(assign_info_str);
				for (int i = 0; i < assign_info_array.size(); i++) {
					Map<String, Object> assign_info_map = (Map<String, Object>) assign_info_array
							.get(i);
					String storage_sn = (String) assign_info_map.get("STORAGE_SN");
					if (storage_sn.equals(sn)) {
						String vsp_hostgroup = (String) assign_info_map
								.get("VIEW_NAME");
						if (StringUtils.isBlank(vsp_hostgroup)) {
							throw new Exception("获取sn：" + sn
									+ " VIEW_NAME 信息失败！");
						}
						List<String> fa_wwn_list = (List<String>) assign_info_map
								.get("PG_MEMBER");
						String fa_wwn = toColonString(fa_wwn_list.get(0));
						// 根据fa_wwn获取 portName
						String fa_portName = storageDao.findFCPortByWWN(fa_wwn).getFcport();
						if (StringUtils.isBlank(fa_portName)) {
							throw new Exception("获取存储sn：" + sn
									+ "前端端口wwn:" + fa_wwn + " 的port name信息失败！");
						}
						// 获取GID
						values = Arrays.asList(new String[] { fa_portName,
								vsp_hostgroup });
						String gid_cmd = generateCmd(vsp_hostgroup_gid_cmd, values,StorageConstants.VSP_HOSTGROUP_GID);
						String gid = getGIDInfo(vo, gid_cmd);
						if (StringUtils.isBlank(gid)) {
							throw new Exception("获取GID信息失败： port:["
									+ fa_portName + "];hostgroup:[" + vsp_hostgroup
									+ "].");
						}
						// 设置指定hostgroup
						values = Arrays.asList(new String[] {
								fa_portName + "-" + gid, hostgroup_name });
						String config_cmd = generateCmd(vsp_hostgroup_config,
								values,StorageConstants.VSP_HOSTGROUP_CONFIG);
						boolean config_flag = sshCommandExe(vo, config_cmd,StorageConstants.VSP_HOSTGROUP_CONFIG);
						if (!config_flag) {
							throw new Exception("设置hostgroup失败：["
									+ config_cmd + "]");
						}
					} else {
						throw new Exception("获取sn：" + sn
								+ " VIEW_NAME 信息失败！");
					}
				}
				boolean unlock_flag = sshCommandExe(vo, vsp_unlock_cmd,StorageConstants.VSP_UNLOCK);
				if(unlock_flag){
					lock_flag = false;
				}
				boolean logout_flag = sshCommandExe(vo, vsp_logout_cmd,StorageConstants.VSP_LOGOUT);
				if(logout_flag){
					login_flag = false;
				}
				// 执行扫描hostgroup语句
				// 根据sn信息获取管理机IP地址
//				String c_ip_type = StorageConstants.storage_ip_type.get("M");
//				String storage_dev_id = storageDao.findStorageDevBySn(sn).getId();
//				List<String> ips = service.getHostIp(storage_dev_id, c_ip_type);
//				if (ips == null || ips.isEmpty()) {
//					throw new Exception("获取sn:[" + sn + "];IP信息失败！");
//				}
//				String storage_ip = ips.get(0);
//				AdminPwPo po = adminPwDao.findObjectByField("resourceId",
//						Long.valueOf(mgr_id));
//				if (po == null) {
//					throw new Exception("获取管理id：" + mgr_id + "登陆信息失败！");
//				}
//				values = Arrays.asList(new String[] { ip, po.getUserName(),
//						PwdUtil.decryption(po.getPassword()), storage_ip });
//				String scan_cmd = this.generateCmd(vsp_hostgroup_scan, values,StorageConstants.VSP_HOSTGROUP_SCAN);
//				boolean scan_flag = sshCommandExe(vo, scan_cmd,StorageConstants.VSP_HOSTGROUP_SCAN);
//				if (!scan_flag) {
//					throw new Exception("扫描hostgroup失败：[" + scan_cmd
//							+ "]");
//				}

				for (int i = 0; i < deviceIdList.size(); i++) {
					String deviceId = String.valueOf(deviceIdList.get(i));
					setHandleResultParam(deviceId,
							"SETTING_FIRST_HOSTGROUP_FINISHED", sn);
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
				if (lock_flag) {
					sshCommandExe(vo, vsp_unlock_cmd,StorageConstants.VSP_UNLOCK);
				}
				if (login_flag) {
					sshCommandExe(vo, vsp_logout_cmd,StorageConstants.VSP_LOGOUT);
				}
			}

			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });

			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", result_sb.toString());
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "contextParams is null");
		}
		return JSON.toJSONString(rtn_map);
	}

	public String generateCmd(String _cmd, List<String> values,String cmd_key) throws Exception {
		String cmd = _cmd;
		CharSequence c = cmd.subSequence(0, cmd.length());
		int size = StringUtils.countMatches(c, "!");
		if (values.size() != size) {
			throw new Exception("传入参数[" + values.toString()
					+ "]数量与输入命令：[" + cmd + "]所需参数数量不符");
		}
		for (String value : values) {
			cmd = cmd.replaceFirst("!", value);
		}
		log.info("生成命令：["+cmd_key+":"+cmd+"]");
		return cmd;
	}

	public String getGIDInfo(SshExecShellVo vo, String cmd) {
		SshExecShell ssh = new SshExecShell();
		vo.setCmd(cmd);
		log.info("cmd:["+StorageConstants.VSP_HOSTGROUP_GID+"][:" + cmd + "]");
		try {
			List<String> result_msg = ssh.execShellCmd(vo);
			if (result_msg.isEmpty() || result_msg == null) {
				throw new Exception("获取GID命令错误[" + cmd
						+ "];ssh---IP=" + vo.getIp() + ";USER=" + vo.getUser());
						//+ ";PASSWORD:" + vo.getPwd());
			}
			return result_msg.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("异常exception",e);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public boolean sshCommandExe(SshExecShellVo vo, String cmd,String cmd_key) {
		SshExecShell ssh = new SshExecShell();
		log.info("cmd:["+cmd_key+"]:[" + cmd + "]");
		List<String> cmds = Arrays.asList(new String[] { cmd });
		try {
			List list = ssh.execShell(vo.getIp(), vo.getUser(), vo.getPwd(),
					cmds);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String exitCode = map.get("EXIT_CODE").toString();
				if (exitCode.equals(StorageConstants.SSH_SUCCESS)) {
					// 正常执行完成
					log.info(map.get("ECHO_INFO").toString());
					return true;
				} else {
					log.info(map.get("ERROR_INFO").toString());
					throw new Exception(
							"host group cmd return error:"
									+ map.get("ERROR_INFO").toString());
				}
			}
		} catch (Exception e) {
			log.error("异常exception",e);
		}
		return false;
	}

	public static String toColonString(String pwwn) {
		String newPwwn = "";

		pwwn = pwwn.toLowerCase().replaceAll(":", "");

		newPwwn += pwwn.charAt(0);
		for (int i = 1; i < pwwn.length(); i++) {
			char c = pwwn.charAt(i);

			if (0 == i % 2 && ':' != c) {
				newPwwn += ":";
			}

			newPwwn += pwwn.charAt(i);
		}

		return newPwwn;
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
	
	
}
