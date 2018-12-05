package com.git.cloud.handler.automation.se.file;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.support.ApplicationCtxUtil;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.StorageService;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SEOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ConfigNASVolumeHandler extends RemoteAbstractAutomationHandler {



	private static Logger log = LoggerFactory
			.getLogger(ConfigNASVolumeHandler.class);
	private List<String> deviceIdList = Lists.newArrayList();	
	private String deviceId;

	private List<Map<String, Object>> list = Lists.newArrayList();

	private StringBuffer rtn_sb = new StringBuffer();
	private String rrinfoId ="";
	private String nas_sn = "";
	private String is_vol_shared = "";
	private String vol_share = "";
	private String storage_ip = "";
	StorageService storageService;
	AutomationService automationService;
	
	public ConfigNASVolumeHandler() throws Exception{
		automationService = getAutomationService();
		storageService = (StorageService) ApplicationCtxUtil.getBean("storageService");
	}
	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		// TODO Auto-generated method stub

		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		try {
			// 增加数据中心路由标识
			String queueIdentify = this.getQueueIdent(rrinfoId);
			header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),
					queueIdentify);
		} catch (Exception e) {
			throw new Exception("get LinkRouteType error!", e);
		}

		header.setResourceClass(EnumResouseHeader.SE_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.NET_RES_TYPE.getValue());
		header.setOperation(SEOpration.CONF_VOLUME);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		String deviceId = getDeviceId();

		Map<String, Object> deviceParams = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(deviceId));

		
		// 获取数据中心
		String dcenter_ename = (String) deviceParams
				.get(StorageConstants.DATACENTER_ENAME);
		body.setString(StorageConstants.DATACENTER_ENAME, dcenter_ename);

		// STORAGE_MODEL_NETAPP_FAS
		String storage_model = (String) deviceParams
				.get(StorageConstants.NAS_MODEL);
		body.setString("NAS_MODEL", storage_model);

		// 获取是否为共享卷
		is_vol_shared = (String) deviceParams
				.get(StorageConstants.IS_VOL_SHARED);
		if (StringUtils.isBlank(is_vol_shared)) {
			throw new Exception("获取[IS_VOL_SHARED]参数！");
		}
		// 新创建配置请求信息
		if (is_vol_shared.equals("N")) {
			String finished = (String)deviceParams.get(StorageConstants.CREATE_VOL_FINISHED);
			if (StringUtils.isBlank(finished)) {
				throw new Exception("未完成NAS-VOLUME创建！");
			}
			String config_finished = (String)deviceParams.get(StorageConstants.CONFIG_VOL_FINISHED);
			if (StringUtils.isNotBlank(config_finished)) {
				throw new Exception("已完成NAS-VOLUME配置！");
			}

			// NAS_SN
			nas_sn = (String) deviceParams.get(StorageConstants.NAS_SN);
			body.setString("NAS_SN", nas_sn);
			rtn_sb.append("NAS SN:"+nas_sn).append("\n");
			
			// 根据NAS设备sn号获取登陆名密码
			log.info("nas_sn:"+nas_sn);
			log.info("nwaddr_type_code:NM");
			log.info("vm_ms_type_code:4");
			//根据NAS设备sn号获取登陆名密码
			Map<String,String> mgr_info = storageService.getNASMgrLoginInfo(nas_sn);
			if(mgr_info==null||mgr_info.isEmpty()){
				throw new Exception("获取存储sn:["+nas_sn+"] 管理机登陆信息失败！");
			}
			body.setString(StorageConstants.SERVICE_IP, mgr_info.get("SERVER_IP"));
			body.setString(StorageConstants.USERNAME, mgr_info.get("USER_NAME"));
			body.setString(StorageConstants.USERPASSWD, mgr_info.get("USER_PASSWD"));

			// 获取主机生成IP
//			List<Map<String, String>> deviceIpList = getDeviceProductIP(
//					contenxtParmas, "P");
			// 获取快照信息
			Map<String, String> snapshot = getSnapshot_info();
			List<Map<String, Object>> vol_info_list = getCreateConfigVolInfo(
					deviceParams, snapshot,contenxtParmas);
			body.setList("VOL_INFO", vol_info_list);
			list.addAll(vol_info_list);
			System.out.println(JSONObject.toJSONString(vol_info_list));
			System.out.println(JSONObject.toJSONString(list));
			log.debug(body.toString());
		} else if (is_vol_shared.equals("Y")) {
			// 共享卷配置请求信息
			vol_share = (String) deviceParams
					.get(StorageConstants.VOL_SHARE);
			if (StringUtils.isBlank(vol_share)) {
				throw new Exception("获取共享卷信息失败!");
			}

			String[] vol_share_array = vol_share.split("/");
			if (vol_share_array.length != 4) {
				throw new Exception("获取共享信息[" + vol_share
						+ "]格式错误，应为[ip:/vol/volname/qtreename]");
			}
			storage_ip = vol_share_array[0].replaceFirst(":", "");
			String vol_name = vol_share_array[2];
			String qtree_name = vol_share_array[3];
			// 根据IP地址获取存储SN以及登陆信息
			String c_ip_type = StorageConstants.storage_ip_type.get("P");
			Map<String, String> mgr_info = storageService.getNASMgrLoginInfo(nas_sn);
			nas_sn = mgr_info.get("SN");
			body.setString("NAS_SN", mgr_info.get("SN"));
			rtn_sb.append("NAS_SN:"+mgr_info.get("SN")).append("\n");
			rtn_sb.append("VIEW_NAME:"+vol_name).append("\n");
			rtn_sb.append("QTREE_NAME:"+qtree_name).append("\n");
			// 登陆信息
			body.setString(StorageConstants.SERVICE_IP, mgr_info.get("SERVER_IP"));
			body.setString(StorageConstants.USERNAME, mgr_info.get("USER_NAME"));
			body.setString(StorageConstants.USERPASSWD, mgr_info.get("USER_PASSWD"));

			// 获取主机生成IP
			List<Map<String, String>> deviceIpList = getDeviceProductIP(
					contenxtParmas, "P");
			// 获取快照信息
			Map<String, String> snapshot = getSnapshot_info();
			List<Map<String, Object>> vol_info_list = getSharedConfigVolInfo(
					vol_name, qtree_name, deviceIpList, snapshot,contenxtParmas);
			body.setList("VOL_INFO", vol_info_list);
			list.addAll(vol_info_list);
		}
		log.debug(JSONObject.toJSONString(body));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}

	public Map<String, String> getSnapshot_info() {
		Map<String, String> map = Maps.newHashMap();
		map.put("SNAPSHOT_RESERVE_PERCENT", "0");
		map.put("SNAPSHOT_SCHEDULE_WEEKS", "0");
		map.put("SNAPSHOT_SCHEDULE_DAYS", "0");
		map.put("SNAPSHOT_SCHEDULE_HOURS", "0");
		return map;
	}

	public List<Map<String, Object>> getExportfs_info(String vol_name,
			List<String> qtree_name_list,Map<String, Object> contenxtParmas) throws Exception {
		List<Map<String, Object>> list = Lists.newArrayList();
		for (String qtree_name : qtree_name_list) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("PERSISTENT_FLAG", Boolean.TRUE);
			map.put("PATH_NAME", "/" + vol_name + "/" + qtree_name);
			List<Map<String,String>> root_hosts =getDeviceProductIP(contenxtParmas, "P");
			map.put("ROOT_HOSTS", root_hosts);
			List<Map<String,String>> rw_hosts = getDeviceProductIP(contenxtParmas, "P");
			map.put("RW_HOSTS", rw_hosts);
			list.add(map);
			rtn_sb.append("PERSISTENT_FLAG:"+Boolean.TRUE).append("\n");
			rtn_sb.append("PATH_NAME:"+ "/"+vol_name+"/"+qtree_name).append("\n");
			rtn_sb.append("RW_HOSTS:"+ rw_hosts).append("\n");
			rtn_sb.append("ROOT_HOSTS:"+ root_hosts).append("\n");
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> getCreateConfigVolInfo(
			Map<String, Object> deviceParams,
			Map<String, String> snapshot,Map<String,Object> contenxtParmas) throws Exception {

		List<Map<String, Object>> rtn_list = Lists.newArrayList();
		// 新创建Volume配置Qtree
		String create_vol_info_str = (String) deviceParams.get(StorageConstants.CREATE_VOL_INFO);
		if (StringUtils.isBlank(create_vol_info_str)) {
			throw new Exception("获取创建的vol信息错误！");
		}
		log.info("CREATE_VOL_INFO:" + create_vol_info_str);

		List<Map> l = JSONObject.parseArray(create_vol_info_str, Map.class);
		for (Map m : l) {
			Map<String, Object> map = Maps.newHashMap();
			// 添加vol_name
			String vol_name = String.valueOf(m.get("VOL_NAME"));
			map.put("VOL_NAME", vol_name);
			rtn_sb.append("VOL_NAME:"+vol_name).append("\n");
			// 添加快照信息
			map.put("SNAPSHOT_INFO", snapshot);
			rtn_sb.append("SNAPSHOT_INFO:").append("\n");
			for(String key:snapshot.keySet()){
				rtn_sb.append(key+":"+snapshot.get(key)).append("\n");
			}
			// 添加主机mount到qtree的主机ip地址信息
			List<Map<String, String>> qtree_map_list = (List<Map<String, String>>) m
					.get("QTREES");
			List<String> qtree_name_list = Lists.newArrayList();
			for (Map<String, String> qtree_map : qtree_map_list) {
				qtree_name_list.add(qtree_map.get("QTREE_NAME"));
			}
			List<Map<String, Object>> exports_info = getExportfs_info(vol_name,
					qtree_name_list, contenxtParmas);
			map.put("EXPORTFS_INFO", exports_info);
			rtn_list.add(map);
		}
		return rtn_list;
	}

	public List<Map<String, Object>> getSharedConfigVolInfo(String vol_name,
			String qtree_name, List<Map<String, String>> deviceIpList,
			Map<String, String> snapshot,Map<String, Object> contenxtParmas) throws Exception {
		List<Map<String, Object>> rtn_list = Lists.newArrayList();
		Map<String, Object> vol_info_map = Maps.newHashMap();
		vol_info_map.put("VOL_NAME", vol_name);
		vol_info_map.put("SNAPSHOT_INFO", snapshot);
		for(String key: snapshot.keySet()){
			rtn_sb.append(key+":"+snapshot.get(key)).append("\n");
		}
		List<Map<String, Object>> exports_info = getExportfs_info(vol_name,
				Arrays.asList(new String[] { qtree_name }),contenxtParmas);
		vol_info_map.put("EXPORTFS_INFO", exports_info);
		rtn_list.add(vol_info_map);
		return rtn_list;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getDeviceProductIP(
			Map<String, Object> contenxtParmas, String pm_type) throws Exception {
		List<Map<String, String>> list = Lists.newArrayList();
		List<String> ip_list = Lists.newArrayList();
		for (String deviceId : deviceIdList) {
			Map<String, Object> devInfo = (Map<String, Object>) contenxtParmas
					.get(String.valueOf(deviceId));
			String dev_type = (String) devInfo.get("DEV_TYPE");
			String os_name = (String) devInfo.get("OS_NAME");
			if(os_name.contains(StorageConstants.OS_NAME_HP_UX)){
				os_name = StorageConstants.OS_NAME_HP_UX;
			}else if(os_name.equalsIgnoreCase(StorageConstants.OS_NAME_LINUX)){
				os_name = StorageConstants.OS_NAME_LINUX;
			}
//			Map<String,String> ip_type_map = StorageConstants.dev_ip_type.get(os_name+"_"+dev_type);
//			String c_ip_type = ip_type_map.get(pm_type);
			List<String> host_ip_list = storageService.getDeviceIPs(deviceId);
			if(null==host_ip_list|| host_ip_list.size()==0){
				throw new Exception("deviceId:["+deviceId+"] "+os_name+","+dev_type+","+pm_type+" 获取IP地址信息失败！");
			}
			ip_list.addAll(host_ip_list);
		}
		for (String ip : ip_list) {
			Map<String, String> host_map = Maps.newHashMap();
			host_map.put("HOST_IP", ip);
			list.add(host_map);
		}
		return list;
	}

	
	public Map<String, Object> getHeartBeatVolInfo(
			Map<String, Object> deviceParams) throws Exception {
		Map<String, Object> vol_info = Maps.newHashMap();
		// 获取应用名称
		String app_name = (String) deviceParams
				.get(StorageConstants.PROJECTABBR);
		// 获取主机挂载点
		String mountpoint = (String) deviceParams
				.get(StorageConstants.NAS_HEARTBEAT_MOUNTPOINT);

		// 组装vol-name
		String[] point_array = mountpoint.split("/");
		String last_point = point_array[point_array.length - 1];
		String vol_name = app_name + "_" + last_point;
		vol_info.put("VOL_NAME", vol_name);

		// 卷大小
		String vol_size = (String) deviceParams.get("CREATE_VOL_CAPACITY");
		vol_info.put("VOL_SIZE", vol_size);

		// pool_name
		String vol_pool_str = (String) deviceParams
				.get(StorageConstants.NAS_VOL_POOL);
		JSONObject vol_pool_map = JSONObject.parseObject(vol_pool_str);
		String pool_name = String.valueOf(vol_pool_map.get(vol_name));
		if (StringUtils.isBlank(pool_name)) {
			throw new Exception("获取VOL_NAME:" + vol_name
					+ "所在POOL信息失败！");
		}
		vol_info.put("POOL_NAME", pool_name);

		// qtree info
		String qtree_name = last_point;
		String qtree_mode = StorageConstants.QTREE_MODE;
		Map<String, String> qtree_info_map = Maps.newHashMap();
		qtree_info_map.put("QTREE_NAME", qtree_name);
		qtree_info_map.put("QTREE_MODE", qtree_mode);
		List<Map<String, String>> qtree_info_list = Lists.newArrayList();
		qtree_info_list.add(qtree_info_map);

		vol_info.put("QTREES", qtree_info_list);
		return vol_info;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {

		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		log.info(header.getRetMesg());

		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}
		for (int i = 0; i < deviceIdList.size(); i++) {
			String deviceId = String.valueOf(deviceIdList.get(i));
			setHandleResultParam(deviceId, StorageConstants.CONFIG_VOL_INFO,
					JSONObject.toJSONString(list));
			setHandleResultParam(deviceId, StorageConstants.CONFIG_VOL_FINISHED,"FINISHED");
			if(is_vol_shared.equals("Y")){
				setHandleResultParam(deviceId, StorageConstants.NAS_SN,nas_sn);
				setHandleResultParam(deviceId, StorageConstants.NAS_PATH,vol_share);
				setHandleResultParam(deviceId, StorageConstants.NAS_NP_USED,storage_ip);
			}
		}

	}

	public String execute(HashMap<String, Object> contenxtParmas) {
		Map<String, Object> rtn_map = new HashMap<String,Object>();
		if(contenxtParmas!=null){
			String flowInstId = (String) contenxtParmas.get(FLOW_INST_ID);
			String nodeId = (String) contenxtParmas.get(NODE_ID);
			rrinfoId = (String) contenxtParmas.get(RRINFO_ID);

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			try {
				Map<String, Map<String, String>> handleParams = this
						.getHandleParams(flowInstId);

				contenxtParmas.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParmas);

				if (extHandleParams != null)
					contenxtParmas.putAll(extHandleParams);

				deviceIdList = getDeviceIdList("","",rrinfoId,"");
				this.setDeviceId(deviceIdList.get(0));
				IDataObject responseDataObject = null;
				IDataObject requestDataObject = buildRequestData(contenxtParmas);
				responseDataObject = getResAdpterInvoker().invoke(
						requestDataObject, getTimeOut());
				handleResonpse(contenxtParmas, responseDataObject);
				saveParamInsts(flowInstId, nodeId);
			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:"
						+ nodeId + ".错误信息:" + e.getMessage();
				log.error(errorMsg, e);
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getMessage());
				return JSON.toJSONString(rtn_map);
			} finally {
				if (contenxtParmas != null)
					contenxtParmas.clear();
			}

			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", rtn_sb.toString());
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null!");
		}
		return JSON.toJSONString(rtn_map);
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
