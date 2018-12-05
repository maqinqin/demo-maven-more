package com.git.cloud.handler.automation.se.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.common.StorageDBRACEnum;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.StorageService;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.PMFlds.NwAddrType;
import com.git.support.common.SEOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ConfigNASHeartBeatVolumeHandler extends RemoteAbstractAutomationHandler  {

	private static Logger log = LoggerFactory
			.getLogger(ConfigNASHeartBeatVolumeHandler.class);
	private List<String> deviceIdList = Lists.newArrayList();

	private String deviceId;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public List<String> getDeviceIdList() {
		return deviceIdList;
	}

	public void setDeviceIdList(List<String> deviceIdList) {
		this.deviceIdList = deviceIdList;
	}

	private List<Map<String,Object>> list = Lists.newArrayList();
	
	private StringBuffer rtn_sb = new StringBuffer();
//	private IIpAllocToVMService ipAllocToVMService = (IIpAllocToVMService) FrameworkContext.getApplicationContext().getBean(
//			"IpToVMPowervmRacServiceImpl");
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");
	private StorageService storageService = (StorageService)WebApplicationManager.getBean("storageService");

	// 生产地址的数量
	private static final int PRODUCT_IP_COUNT = 3;

	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		// TODO Auto-generated method stub
		AutomationService automationService = (AutomationService) WebApplicationManager.getBean("automationServiceImpl");

		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		String rrinfoId = (String) contenxtParmas.get(RRINFO_ID);

		try {
			// 增加数据中心路由标识
			String queueIdentify = this.getQueueIdent(rrinfoId);
			header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),
					queueIdentify);
		} catch (Exception e) {
			throw new Exception("get LinkRouteType error!", e);
		}

		header.setResourceClass("SE");
		header.setResourceType(StorageConstants.RESOURCE_TYPE_STORAGE_NETAPP);
		header.setOperation(SEOpration.CONF_VOLUME);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		String deviceId = getDeviceId();

		Map<String, Object> deviceParams = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(deviceId));


		String finished = (String)deviceParams.get(StorageConstants.CREATE_HEARTBEAT_VOL_FINISHED);
		if(StringUtils.isBlank(finished)){
			throw new Exception("未完成RAC-NAS-HEARTBEAT创建！");
		}
		String config_finished = (String)deviceParams.get(StorageConstants.CONFIG_RAC_HEARTBEAT_VOL_FINISHED);
		if(StringUtils.isNotBlank(config_finished)){
			throw new Exception("已完成RAC-NAS-HEARTBEAT配置！");
		}
		
		// 获取数据中心
		String dcenter_ename = (String) deviceParams
				.get(StorageConstants.DATACENTER_ENAME);
		body.setString(StorageConstants.DATACENTER_ENAME, dcenter_ename);
		
		// STORAGE_MODEL_NETAPP_FAS
		String storage_model = (String)deviceParams.get(StorageConstants.RAC_NAS_MODEL);
		body.setString("NAS_MODEL", storage_model);
		
		//DATA_TYPE = NASHeartbeat
		String heartbeat_name = (String)deviceParams.get(StorageDBRACEnum.NAS_HEARTBEAT_NAME.name());
		body.setString("DATA_TYPE", heartbeat_name);
		
		//RAC_NAS_SN
		String nas_sn =(String)deviceParams.get(StorageConstants.RAC_NAS_SN);
		body.setString("NAS_SN", nas_sn);
		rtn_sb.append("NAS SN:"+nas_sn).append("\n");
		
		//根据NAS设备sn号获取登陆名密码
		log.info("nas_sn:"+nas_sn);
		log.info("nwaddr_type_code:NM");
		log.info("vm_ms_type_code:4");
		//根据NAS设备sn号获取登陆名密码
//		Map<String,String> mgr_info = service.getNASMgrLoginInfo(nas_sn,
//				StorageConstants.IP_ADDR_TYPE_CODE_NM,
//				StorageConstants.IP_STORAGE_TYPE);
		String storageId = storageDao.findStorageDevBySn(nas_sn).getId();
		Map<String,String> mgr_info = Maps.newHashMap();
		if(storageId!=null){
			mgr_info.put("deviceId", storageId);
		}
		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoByParam(mgr_info);
		if(mgr_info==null||mgr_info.isEmpty()){
			throw new Exception("获取存储sn:["+nas_sn+"] 管理机登陆信息失败！");
		}
		body.setString(StorageConstants.SERVICE_IP, mgrVo.getManagerIp());
		body.setString(StorageConstants.USERNAME, mgrVo.getUserName());
		body.setString(StorageConstants.USERPASSWD, mgrVo.getPassword());
		
//		Map<String,Object> vol_info = getHeartBeatVolInfo(deviceParams);
//		List<Map<String,Object>> vol_info_list = Lists.newArrayList();
//		vol_info_list.add(vol_info);
//		list.addAll(vol_info_list);

		//List<Map<String,String>> deviceIpList = getDeviceProductIP(contenxtParmas,"P");
		
		Map<String,String> snapshot = getSnapshot_info();
		list = getConfigVolInfo(deviceParams, snapshot,contenxtParmas);
		body.setList("VOL_INFO", list);
		log.debug(JSONObject.toJSONString(body));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getDeviceProductIP(Map<String, Object> contenxtParmas,String pm_type) throws Exception{
		List<Map<String,String>> list = Lists.newArrayList();
		List<String> ip_list = Lists.newArrayList();
		for(String deviceId:deviceIdList){
			Map<String,Object> devInfo = (Map<String,Object>)contenxtParmas.get(String.valueOf(deviceId));
			String dev_type = (String)devInfo.get("DEV_TYPE");
			String os_name = (String)devInfo.get("OS_NAME");
			if(os_name.contains(StorageConstants.OS_NAME_HP_UX)){
				os_name = StorageConstants.OS_NAME_HP_UX;
			}else if(os_name.equalsIgnoreCase(StorageConstants.OS_NAME_LINUX)){
				os_name = StorageConstants.OS_NAME_LINUX;
			}

			List<String> host_ip_list = storageService.getDeviceIPs(deviceId);
			if(null==host_ip_list|| host_ip_list.size()==0){
				throw new Exception("deviceId:["+deviceId+"] "+os_name+","+dev_type+","+pm_type+" 获取IP地址信息失败！");
			}
			ip_list.addAll(host_ip_list);
		}
		for(String ip:ip_list){
			Map<String,String> host_map = Maps.newHashMap();
			host_map.put("HOST_IP", ip);
			list.add(host_map);
		}
		return list;
	}
	
//	public List<Map<String, String>> getDeviceProductIPByContextParmas(
//			Map<String, Object> contenxtParmas, String pm_type) {
//		List<Map<String,String>> list = Lists.newArrayList();
//		List<String> ip_list = Lists.newArrayList();
//		try {
//			List<DeviceNetIP> deviceNetIps = ipAllocToVMService.qryAllocedIP(deviceIdList);
//			for(DeviceNetIP deviceNetIP :deviceNetIps){
//				List<NetIPInfo> vmIPs = deviceNetIP.getNetIPs().get(NwAddrType.VM_PROD.getValue());
//				if (vmIPs != null && vmIPs.size() == PRODUCT_IP_COUNT) {		
//					for (NetIPInfo netIp: vmIPs) {
//						ip_list.add(netIp.getIp());
//					}
//				} else {
//					throw new Exception("vioc生产ip为空或生产ip数量 != 3。");
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			throw new Exception(e);
//		}
//
//		for(String ip:ip_list){
//			Map<String,String> host_map = Maps.newHashMap();
//			host_map.put("HOST_IP", ip);
//			list.add(host_map);
//		}
//		return list;
//	}

	public Map<String,String> getSnapshot_info(){
		Map<String,String> map = Maps.newHashMap();
		map.put("SNAPSHOT_RESERVE_PERCENT", "0");
		map.put("SNAPSHOT_SCHEDULE_WEEKS", "0");
		map.put("SNAPSHOT_SCHEDULE_DAYS", "0");
		map.put("SNAPSHOT_SCHEDULE_HOURS", "0");
		return map;
	}
	
	public List<Map<String, Object>> getExportfs_info(String vol_name,
			List<String> qtree_name_list,Map<String, Object> contenxtParmas,String cloud_service_type) throws Exception {
		List<Map<String, Object>> list = Lists.newArrayList();
		for (String qtree_name : qtree_name_list) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("PERSISTENT_FLAG", Boolean.TRUE);
			map.put("PATH_NAME", "/" + vol_name + "/" + qtree_name);
//			List<Map<String,String>> root_hosts =Lists.newArrayList();
//			if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_STORAGE_INSTALL)){
//				root_hosts = getDeviceProductIP(contenxtParmas, "P");
//			}else if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_VM_INSTALL)){
//				root_hosts = this.getDeviceProductIPByContextParmas(contenxtParmas, "P");
//			}
//			map.put("ROOT_HOSTS", root_hosts);
//			List<Map<String,String>> rw_hosts = Lists.newArrayList();
//			if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_STORAGE_INSTALL)){
//				rw_hosts = getDeviceProductIP(contenxtParmas, "P");
//			}else if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_VM_INSTALL)){
//				rw_hosts = this.getDeviceProductIPByContextParmas(contenxtParmas, "P");
//			}
//			getDeviceProductIP(contenxtParmas, "P");
//			map.put("RW_HOSTS", rw_hosts);
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
	public List<Map<String,Object>> getConfigVolInfo(Map<String, Object> deviceParams,Map<String,String> snapshot,Map<String,Object> contenxtParmas) throws Exception{
		String create_vol_info_str = (String)deviceParams.get(StorageConstants.CREATE_HEARTBEAT_VOL_INFO);
		if(StringUtils.isBlank(create_vol_info_str)){
			throw new Exception("获取创建的vol信息错误！");
		}
		log.debug("CREATE_HEARTBEAT_VOL_INFO:"+create_vol_info_str);

		String cloud_service_type = (String)deviceParams.get(StorageConstants.CLOUD_SERVICE_TYPE);
		if(StringUtils.isBlank(cloud_service_type)){
			throw new Exception("获取[CLOUD_SERVICE_TYPE]参数错误！");
		}
		List<Map<String,Object>> rtn_list = Lists.newArrayList();
		List<Map> l = JSONObject.parseArray(create_vol_info_str, Map.class);
		for(Map m : l){
			Map<String,Object> map = Maps.newHashMap();
			//添加vol_name
			String vol_name = String.valueOf(m.get("VOL_NAME"));
			map.put("VOL_NAME", vol_name);
			rtn_sb.append("VOL_NAME:"+vol_name).append("\n");
			//添加快照信息
			map.put("SNAPSHOT_INFO", snapshot);
			rtn_sb.append("SNAPSHOT_INFO:").append("\n");
			for(String key:snapshot.keySet()){
				rtn_sb.append(key+":"+snapshot.get(key));
			}
			//添加主机mount到qtree的主机ip地址信息
			List<Map<String,String>> qtree_map_list = (List<Map<String,String>>)m.get("QTREES");
			List<String> qtree_name_list = Lists.newArrayList();
			for(Map<String,String> qtree_map : qtree_map_list){
				qtree_name_list.add(qtree_map.get("QTREE_NAME"));
				rtn_sb.append("QTREE_NAME:"+qtree_map.get("QTREE_NAME"));
			}
			List<Map<String, Object>> exports_info= getExportfs_info(vol_name,qtree_name_list,contenxtParmas,cloud_service_type);
			map.put("EXPORTFS_INFO", exports_info);
			rtn_list.add(map);
		}
		return rtn_list;
	}
	
//	public Map<String,Object> getHeartBeatVolInfo(Map<String,Object> deviceParams){
//		Map<String,Object> vol_info = Maps.newHashMap();
//		//获取应用名称
//		String app_name = (String)deviceParams.get(StorageConstants.PROJECTABBR);
//		//获取主机挂载点
//		String mountpoint = (String)deviceParams.get(StorageConstants.NAS_HEARTBEAT_MOUNTPOINT);
//		
//		//组装vol-name
//		String[] point_array = mountpoint.split("/");
//		String last_point = point_array[point_array.length-1];
//		String vol_name = app_name+"_"+last_point;
//		vol_info.put("VOL_NAME", vol_name);
//		
//		//卷大小
//		String vol_size = (String)deviceParams.get("NAS_HEARTBEAT_CAPACITY");
//		vol_info.put("VOL_SIZE", vol_size);
//		
//		//pool_name
//		String vol_pool_str = (String)deviceParams.get(StorageConstants.RAC_NAS_VOL_POOL);
//		JSONObject vol_pool_map = JSONObject.parseObject(vol_pool_str);
//		String pool_name = String.valueOf(vol_pool_map.get(vol_name));
//		if(StringUtils.isBlank(pool_name)){
//			throw new Exception("获取VOL_NAME:"+vol_name+"所在POOL信息失败！");
//		}
//		vol_info.put("POOL_NAME", pool_name);
//		
//		//qtree info
//		String qtree_name = last_point;
//		String qtree_mode = StorageConstants.QTREE_MODE;
//		Map<String,String> qtree_info_map = Maps.newHashMap();
//		qtree_info_map.put("QTREE_NAME", qtree_name);
//		qtree_info_map.put("QTREE_MODE", qtree_mode);
//		List<Map<String,String>> qtree_info_list = Lists.newArrayList();
//		qtree_info_list.add(qtree_info_map);
//		
//		vol_info.put("QTREES", qtree_info_list);
//		return vol_info;
//	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {
		
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		log.info(header.getRetMesg());
		
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String exportfs_time = sdf.format(d);
		for (int i = 0; i < deviceIdList.size(); i++) {
			String deviceId = String.valueOf(deviceIdList.get(i));
			setHandleResultParam(deviceId, StorageConstants.CONFIG_HEARTBEAT_VOL_INFO, JSONObject.toJSONString(list));
			setHandleResultParam(deviceId, StorageConstants.EXPORTFS_TIME,exportfs_time);
			setHandleResultParam(deviceId, StorageConstants.CONFIG_RAC_HEARTBEAT_VOL_FINISHED,"FINISHED");
		}

	}

	public String execute(HashMap<String, Object> contenxtParmas) {
		Map<String, Object> rtn_map = Maps.newHashMap();
		if(contenxtParmas!=null){
			String flowInstId = (String) contenxtParmas.get(FLOW_INST_ID);
			String nodeId = (String) contenxtParmas.get(NODE_ID);
			String rrinfoId = (String) contenxtParmas.get(RRINFO_ID);

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
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null");
		}
		return JSON.toJSONString(rtn_map);
	}
}
