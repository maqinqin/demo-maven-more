package com.git.cloud.handler.automation.se.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.support.ApplicationCtxUtil;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.SshExecShell;
import com.git.cloud.handler.service.StorageService;
import com.git.cloud.handler.automation.se.vo.SshExecShellVo;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.inf.IDataObject;

public class CheckConfigNasVolumeHandler extends RemoteAbstractAutomationHandler {

	private static Logger logger = LoggerFactory
			.getLogger(CheckConfigNasVolumeHandler.class);

	private static final String CREATE_VOL_CAPACITY = "CREATE_VOL_CAPACITY";// 创建卷大小

	private static final String NAS_VOL_POOL = "NAS_VOL_POOL";// {卷名称:池名称}

	private static final String VERIFY_NAS_VOLUME_CMD = "df -h";// 验证卷Shell命令

	private static final String VERIFY_NAS_QTREE_CMD = "qtree status";// 验证Qtree命令

	private static final String SERVER_IP = "SERVER_IP";

	private static final String NAS_SHARE_TYPE = "NAS_SHARE_TYPE";// 共享类型:CIFS/NFS

	private static final String VOL_TYPE_STYLE = "unix";// NFS格式的卷对应的nas_qtree的style值

	private static final String NFS = "NFS";// 共享类型的值 NFS

	private static final String CREATE_QTREE_NAME = "CREATE_QTREE_NAME";

	String nas_vol_capacity = "";// 实际容量
	
	StorageService storageService;
	

	public CheckConfigNasVolumeHandler() throws Exception{
		storageService=(StorageService) ApplicationCtxUtil.getBean("storageService");
	}
	@SuppressWarnings("unchecked")
	@Override
	public String execute(HashMap<String, Object> contenxtParmas) {
		// 验证结果集合
		Map<String, String> rtn_map = new HashMap<String,String>();
		if(contenxtParmas!=null){
			// 资源请求ID
			String rrinfoId = (String) contenxtParmas.get(RRINFO_ID);
			// 流程实例ID
			String flowInstId = (String) contenxtParmas.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contenxtParmas.get(NODE_ID);
			// 操作起始时间
			Long startTime = System.currentTimeMillis();
			// 日志输出
			StringBuilder return_sb = new StringBuilder();
			// 是否符合需求的卷大小
			boolean isFitRequireCapacity = false;
			// 是否创建QTree成功
			boolean isCreateQTree = false;

			logger.debug("执行NAS存储自动化(File)验证卷、验证Qtree操作开始，服务请求ID:" + rrinfoId
					+ ",流程实例ID:" + flowInstId + ",节点ID:" + nodeId);

			try {
				logger.info("Begin To Get Contenxt Parameter ......");
				// 获取全局业务参数
				Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);

				// 将工作流相关参数和业务参数合并
				contenxtParmas.putAll(handleParams);
				// 扩展业务参数
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParmas);

				if (extHandleParams != null) {
					contenxtParmas.putAll(extHandleParams);
				}
				logger.info("End To Get Contenxt Parameter OK");

				logger.info("Begin To Verify NAS Volume ......");

				logger.info("Begin To Get Need Parameters ......");

				List<String> deviceIdList = this.getDeviceIdList("", nodeId, rrinfoId, "");
				if (null == deviceIdList || deviceIdList.isEmpty()) {
					String errorMsg = "Get Device IDs From ContextParam Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				String deviceId = deviceIdList.get(0);

				logger.info("current device id :" + deviceId);

				// 当前设备的参数列表
				HashMap<String, Object> deviceParams = (HashMap<String, Object>) contenxtParmas
						.get(String.valueOf(deviceId));
				if (null == deviceParams || deviceParams.isEmpty()) {
					String errorMsg = "Get Current DeviceParams From ContextParam Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				// 需求卷容量
				String need_vol_capacity = (String) deviceParams
						.get(CREATE_VOL_CAPACITY);
				if (null == need_vol_capacity || need_vol_capacity.isEmpty()) {
					String errorMsg = "Get Current Vol_Capacity Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current Need Check Vol_Capacity is : "
						+ need_vol_capacity);

				String nas_vol_pool = (String) deviceParams.get(NAS_VOL_POOL);
				if (null == nas_vol_pool || nas_vol_pool.isEmpty()) {
					String errorMsg = "Get nas_vol_pool Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current nas_vol_pool is : " + nas_vol_pool.toString());

				// 转换为NAS卷名称和池名称集合
				JSONObject map_nas_vol_pool = JSON.parseObject(nas_vol_pool);
				if (null == map_nas_vol_pool || map_nas_vol_pool.isEmpty()) {
					String errorMsg = "Get Transform map_nas_vol_pool Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current Need Check map_nas_vol_pool is : "
						+ map_nas_vol_pool.toString());

				String need_nas_vol_name = "";// 需要验证的卷名称

				if (map_nas_vol_pool.keySet().iterator().hasNext()) {
					need_nas_vol_name = map_nas_vol_pool.keySet().iterator().next();
				} else {
					String errorMsg = "The map_nas_vol_pool not include element";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Get need_nas_vol_name is :" + map_nas_vol_pool);

				// 获取NAS SN
				String nas_sn = (String) deviceParams.get("NAS_SN");
				if (null == nas_sn || nas_sn.isEmpty()) {
					String errorMsg = "Get Current nas_sn Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current nas_sn is : " + nas_sn);

				logger.info("End Get Need Parameters ......");

				logger.info("build nas loginInfo and execShell cmd.....");

				// 根据SN获取NAS登陆信息
				// 调用谢工接口获取ip信息，并通过sn信息获取登陆信息
				Map<String, String> nas_mgr_info = storageService.getNASMgrLoginInfo(nas_sn);
				if (null == nas_mgr_info || nas_mgr_info.isEmpty()) {
					String errorMsg = "Get nas_mgr_info Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current nas_mgr_info is : " + nas_mgr_info);

				SshExecShellVo shellVo = new SshExecShellVo();

				String server_ip = nas_mgr_info.get(SERVER_IP);
				if (null == server_ip || server_ip.isEmpty()) {
					String errorMsg = "Get server ip Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current Nas Server Ip is :" + server_ip);

				String user_name = nas_mgr_info.get("USER_NAME");
				if (null == user_name || user_name.isEmpty()) {
					String errorMsg = "Get server username Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current Nas Server username is :" + user_name);

				String user_pwd = nas_mgr_info.get("USER_PASSWD");
				if (null == user_pwd || user_pwd.isEmpty()) {
					String errorMsg = "Get server userpwd Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current Nas Server userpwd is :" + user_pwd);

				shellVo.setIp(server_ip);
				shellVo.setUser(user_name);
				shellVo.setPwd(PwdUtil.decryption(user_pwd));
				shellVo.setCmd(VERIFY_NAS_VOLUME_CMD + " " + need_nas_vol_name);

				SshExecShell execShell = new SshExecShell();

				// 获得执行验证卷结果
				List<String> result_executed = execShell.execShellCmd(shellVo);
				if (null == result_executed || result_executed.isEmpty()) {
					String errorMsg = "Get server response Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current Nas Server Response Result is :"
						+ result_executed.toString());

				logger.info("end build nas loginInfo and execShell cmd.....");

				logger.info("analysis shell cmd and compare with need args...");

				isFitRequireCapacity = checkVolCapacity(result_executed,
						need_vol_capacity);

				if (!isFitRequireCapacity) {
					String errorMsg = "实际卷容量与规划容量不符！需求容量：" + need_vol_capacity
							+ ",实际容量：" + nas_vol_capacity;
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				if (isFitRequireCapacity) {
					return_sb.append("新卷：" + need_nas_vol_name + "创建成功！卷实际容量为："
							+ nas_vol_capacity + "GB，同需求一致！");
				}

				logger.info("End Verify NAS Volume ......");

				logger.info("Begin To Verify NAS QTree Create ......");

				logger.info("Begin To Get QTreeName Parameter......");

				// 获取共享类型
				String nas_share_type = (String) deviceParams.get(NAS_SHARE_TYPE);
				if (null == nas_share_type || nas_share_type.isEmpty()) {
					String errorMsg = "get nas_share_type fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("get current nas_share_type is :" + nas_share_type);

				String need_qtree_name = (String) deviceParams
						.get(CREATE_QTREE_NAME);
				if (null == need_qtree_name || need_qtree_name.isEmpty()) {
					String errorMsg = "get qtree_name fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("get current qtree_name is :" + need_qtree_name);

				logger.info("End Get QTreeName Parameter......");

				shellVo.setCmd(VERIFY_NAS_QTREE_CMD + " " + need_nas_vol_name);

				// 获得执行验证Qtree结果
				List<String> result_executed_qtree = execShell
						.execShellCmd(shellVo);
				if (null == result_executed_qtree
						|| result_executed_qtree.isEmpty()) {
					String errorMsg = "Get check qtree response Fail";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("Current Nas Server CheckQtree Response Result is :"
						+ result_executed_qtree.toString());

				List<String> includeQtreeInfos = new ArrayList<String>();

				for (String record : result_executed_qtree) {
					if (record.contains(need_nas_vol_name)) {
						includeQtreeInfos.add(record);
					}
				}

				isCreateQTree = checkCreateQTree(includeQtreeInfos,
						need_qtree_name, nas_share_type);

				if (isCreateQTree) {
					return_sb.append("新卷：" + need_nas_vol_name + "对应的Qtree："
							+ need_qtree_name + "创建成功！");
				} else {
					String errorMsg = "新卷：" + need_nas_vol_name + "创建Qtree："
							+ need_qtree_name + "失败！";
					logger.error(errorMsg);
					throw new Exception(errorMsg);
				}

				logger.info("End Verify NAS QTree Create ......");

			} catch (Exception e) {
				String errorMsg = "执行NAS存储自动化(File)验证卷、验证Qtree操作失败,服务请求ID: "
						+ rrinfoId + ", 流程实例ID: " + flowInstId + ", 节点ID: "
						+ nodeId;
				logger.error(errorMsg, e);
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getMessage());
				return JSON.toJSONString(rtn_map);
			} finally {
				if (contenxtParmas != null) {
					contenxtParmas.clear();
				}
			}

			logger.debug(
					"执行NAS存储自动化(File)验证卷、验证Qtree操作结束,服务请求ID:{},流程实例ID:{},节点ID:{},耗时:{}毫秒.",
					new Object[] { rrinfoId, flowInstId, nodeId,
							new Long((System.currentTimeMillis() - startTime)) });

			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg",
					"(File)验证卷、验证Qtree操作成功!验证信息如下:" + return_sb.toString());
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null!");	
		}
		return JSON.toJSONString(rtn_map);
	}

	/**
	 * 检查Qtree是否创建成功
	 * 
	 * @param includeQtreeInfos
	 * @param need_qtree_name
	 * @param nas_share_type
	 * @return
	 */
	private boolean checkCreateQTree(List<String> includeQtreeInfos,
			String need_qtree_name, String nas_share_type) {
		boolean final_check_result = false;
		for (String qtreeinfo : includeQtreeInfos) {
			List<String> temp_array = new ArrayList<String>();
			String[] temp_split = qtreeinfo.split(" ");
			for (String string : temp_split) {
				if (string.length() > 1) {
					temp_array.add(string);
				}
			}
			if (temp_array.size() != 5) {
				continue;
			}
			String temp_qtree_name = temp_array.get(1);
			logger.info("get current temp_qtree_name is :" + temp_qtree_name);

			String temp_vol_type = temp_array.get(2);
			logger.info("get current temp_vol_type is :" + temp_vol_type);

			if (temp_qtree_name.equals(need_qtree_name)
					&& NFS.equals(nas_share_type)
					&& VOL_TYPE_STYLE.equals(temp_vol_type)) {
				final_check_result = true;
				logger.info("check Create Qtree Ok!");
				break;
			} else if (!temp_qtree_name.equals(need_qtree_name)) {
				logger.info("qtree name not consistent");
			} else if (!(NFS.equals(nas_share_type) && VOL_TYPE_STYLE
					.equals(temp_vol_type))) {
				logger.info("nas_share_type is:" + nas_share_type
						+ ",vol_type_style is :" + temp_vol_type);
			}
		}
		return final_check_result;
	}

	/**
	 * 解析并获取实际容量的数量和单位并与需求容量进行一致性比较
	 * 
	 * @param result_executed
	 *            shell执行结果
	 * @param need_vol_capacity
	 *            需求容量
	 * @return 一致返回True，不一致返回False
	 */
	private boolean checkVolCapacity(List<String> result_executed,
			String need_vol_capacity) {
		if (result_executed.size() != 3) {
			logger.error("shell cmd result array is not have three elements");
			return false;
		}
		float f_total_capacity = 0.0f;
		List<String> temp_array = new ArrayList<String>();
		for (int i = 1; i < result_executed.size(); i++) {
			String[] temp_split = result_executed.get(i).split(" ");
			for (String string : temp_split) {
				if (string.length() > 1) {
					temp_array.add(string);
				}
			}
			f_total_capacity += getNasVolCapacity(temp_array.get(1));
			temp_array.clear();
		}

		logger.info("before transform value is :" + f_total_capacity + " GB");

		nas_vol_capacity = String.valueOf((int) Math.ceil(f_total_capacity));

		logger.info("after transform value is :" + nas_vol_capacity + " GB");

		if (nas_vol_capacity.equals(need_vol_capacity)) {
			return true;
		}
		return false;
	}

	/**
	 * 拆分容量需求的单位和数字部分，容量单位统一使用GB
	 * 
	 * @param result_total_capacity
	 * @return
	 */
	protected float getNasVolCapacity(String result_total_capacity) {
		// 容量需求的数字部分
		String capacity_size = result_total_capacity.substring(0,
				result_total_capacity.length() - 2);
		// 容量需求的单位部分
		String capacity_unit = result_total_capacity.substring(
				result_total_capacity.length() - 2,
				result_total_capacity.length());
		float f_nas_vol_capacity = 0.0f;

		if ("MB".equals(capacity_unit)) {
			f_nas_vol_capacity = Float.parseFloat(capacity_size) / 1024;
		} else if ("GB".equals(capacity_unit)) {
			f_nas_vol_capacity = Float.parseFloat(capacity_size);
		} else if ("TB".equals(capacity_unit)) {
			f_nas_vol_capacity = Float.parseFloat(capacity_size) * 1024;
		}
		logger.info("get nas_vol_capacity is:" + f_nas_vol_capacity + "GB");

		return f_nas_vol_capacity;
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {
		// TODO Auto-generated method stub
		
	}
}
