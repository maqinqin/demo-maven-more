package com.git.cloud.handler.automation.se.db;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.common.StorageDBRACEnum;
import com.git.cloud.handler.dao.AutomationDAO;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.StorageService;
import com.git.cloud.handler.vo.NasInfoVo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SEOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ChooseNASHeartbeatHandler extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(ChooseNASHeartbeatHandler.class);
	public StringBuffer rtn_sb = new StringBuffer();

	private List<String> deviceIdList  = Lists.newArrayList();
	
	private String errorMsg = new String();
	private String rrinfoId;
	private String deviceId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

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
			throw new Exception("get LinkRouteType error!",e);
		}

		header.setResourceClass("SE");
		header.setResourceType(StorageConstants.RESOURCE_TYPE_STORAGE_NETAPP);
		header.setOperation(SEOpration.SELECTSTORAGE);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		String deviceId = getDeviceId();

		Map<String, Object> deviceParams = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(deviceId));

		// 获取数据中心
		String dcenter_ename = (String) deviceParams
				.get(StorageConstants.DATACENTER_ENAME);
		body.setString(StorageConstants.DATACENTER_ENAME, dcenter_ename);
		
		body.setString("CHOOSE_TYPE", StorageConstants.DATA_APP_TYPE_DB);
		
		String available = (String)deviceParams.get(StorageConstants.NAS_AVAILABLE_RATE_THRESHOLD);
		if(StringUtils.isBlank(available)){
			throw new Exception("获取[NAS_AVAILABLE_RATE_THRESHOLD]参数错误!");
		}
		
		body.setString(StorageConstants.NAS_AVAILABLE_RATE_THRESHOLD, available);
		// 获取DATA_TYPE信息
		HashMap<String,String> data_type_map = buildDataType(deviceParams);
		List<HashMap<String,String>> data_type_map_list = Lists.newArrayList();
		data_type_map_list.add(data_type_map);
		body.setList(StorageConstants.DATA_TYPE, data_type_map_list);
		
		//添加可选择存储SN
		List<String> sn_list = getChooseSN();
		if(sn_list==null||sn_list.isEmpty()){
			throw new Exception("获取可用NAS存储设备sn列表失败！");
		}
		//body.setList(StorageConstants.SN, sn_list);		
		
		//获取NAS_SN信息
		List<Map<String,Object>> nas_sn_info_list = generateInfo(deviceParams,sn_list);
		body.setList("NAS_SN", nas_sn_info_list);
		reqData.setDataObject(MesgFlds.BODY, body);
		log.info("RAC 心跳盘NAS存储请求信息："+JSONObject.toJSONString(body));
		return reqData;
	}
	
	public List<Map<String,Object>> generateInfo(Map<String,Object> deviceParams,List<String> sn_list) throws Exception{
		List<Map<String,Object>> storage_Info_list = Lists.newArrayList();
		for(String sn:sn_list){
			//Long su_id = service.getSuIdByStorageId(sn);
			//根据sn获取设备类型
			//String deviceModel = service.getStorageModelBySN(sn);
			String deviceModel = StorageConstants.STORAGE_MODEL_NETAPP_FAS;
			if(deviceModel.equals(StorageConstants.STORAGE_MODEL_NETAPP_FAS)){
				//根据NAS设备sn号获取登陆名密码
//				Map<String,String> mgr_info = service.getNASMgrLoginInfo(sn,
//						StorageConstants.IP_ADDR_TYPE_CODE_NM,
//						StorageConstants.IP_STORAGE_TYPE);
				StorageService storageService = (StorageService) WebApplicationManager.getBean("storageService");

				Map<String,String> mgr_info = storageService.getNASMgrLoginInfo(sn);

				if(mgr_info==null||mgr_info.isEmpty()){
					throw new Exception("获取存储sn:["+sn+"]管理机登陆信息为空!");
				}
				Map<String,String> sn_mgr_info = Maps.newHashMap();
				sn_mgr_info.put(StorageConstants.SERVICE_IP, mgr_info.get("SERVER_IP"));
				sn_mgr_info.put(StorageConstants.USERNAME, mgr_info.get("USER_NAME"));
				sn_mgr_info.put(StorageConstants.USERPASSWD, mgr_info.get("USER_PASSWD"));
				sn_mgr_info.put(StorageConstants.NAMESPACE, "interop");
				
				Map<String,Object> map = Maps.newHashMap();
				map.put("SN", sn);
				map.put("STORAGE_MGR", sn_mgr_info);
				storage_Info_list.add(map);
			}else{
				continue;
			}
		}
		return storage_Info_list;
	}
	
	public HashMap<String,String> buildDataType(Map<String, Object> deviceParams) throws Exception{
		//String name = (String)deviceParams.get(StorageDBRACEnum.NAS_HEARTBEAT_NAME.toString());
		String size = (String)deviceParams.get(StorageDBRACEnum.NAS_HEARTBEAT_CAPACITY.toString());
		//String count = (String)deviceParams.get(StorageDBRACEnum.NAS_HEARTBEAT_COUNT.toString());
		
		HashMap<String,String> map = Maps.newHashMap();
		//获取应用名称
		String app_name = (String)deviceParams.get(StorageConstants.PROJECTABBR);
		//判断服务请求为虚拟机安装提供存储还是单独的存储服务
		String cloud_service_type = (String)deviceParams.get(StorageConstants.CLOUD_SERVICE_TYPE);
		if(StringUtils.isBlank(cloud_service_type)){
			throw new Exception("获取[CLOUD_SERVICE_TYPE]参数错误！");
		}
		if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_STORAGE_INSTALL)){
			//单独申请存储服务
			//获取主机挂载点
			String mountpoint = (String)deviceParams.get(StorageConstants.NAS_HEARTBEAT_MOUNTPOINT);
			if(StringUtils.isBlank(mountpoint)){
				throw new Exception("获取[NAS_HEARTBEAT_MOUNTPOINT]信息错误！");
			}
			//组装vol-name
			String[] point_array = mountpoint.split("/");
			String last_point = point_array[point_array.length-1];
			String vol_name = app_name+"_"+last_point;
			map.put("vol_name", vol_name);
			map.put("size", size);
		}else if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_VM_INSTALL)){
			//虚拟机数据RAC安装提供NAS心跳盘
			String db_name = (String)deviceParams.get(StorageConstants.DB_NAME);
			if(StringUtils.isBlank(db_name)){
				throw new Exception("获取[DB_NAME]参数信息错误！");
			}
			String projectAbbr = (String)deviceParams.get(StorageConstants.PROJECTABBR);
			String vol_name = StorageConstants.RAC_NAS_PR+"_"+projectAbbr+"_"+db_name;
			map.put("vol_name", vol_name);
			map.put("size", size);
		}else {
			throw new Exception("未知的服务请求类型！");
		}
		return map;
	}
	
	public List<String> getChooseSN() throws Exception {
		List<String> sn_list = Lists.newArrayList();

		//获取主机根位置
		AutomationDAO automationDao = (AutomationDAO) WebApplicationManager.getBean("AutomationDaoImpl");
		StorageService storageService = (StorageService) WebApplicationManager.getBean("storageService");
		String seatCode = "";
		seatCode = automationDao.getSeatCodeByVmId(getDeviceId());
		if(seatCode==null || seatCode.equals("")) {
			seatCode = automationDao.getSeatCodeByHostId(getDeviceId());
			if(seatCode==null || seatCode.equals("")) {
				throw new Exception("获取主机位置失败！");
			}
		}
		String host_parentCode = getParentSeatCode(seatCode);
		
		//获取可用存储
//		List<Map<String,?>> nas_sn_list = service.getUsedNasDeviceSN();
		List<NasInfoVo> nasList = storageService.getUsedNasDeviceSN();
		
		if(nasList==null || nasList.isEmpty()) {
			throw new Exception("获取可使用NAS存储列表失败！");
		}
		
		for(NasInfoVo nas : nasList){
			String storage_app_type_code = nas.getStorageAppTypeCode();
			if(!StorageConstants.STORAGE_APP_TYPE_FILE.equals(storage_app_type_code)){
				continue;
			}
			String sn = nas.getSn();
			String seat = nas.getSeatCode();
			if(StringUtils.isBlank(sn)||StringUtils.isBlank(seat)){
				continue;
			}
			String parent_seat = getParentSeatCode(seat);
			if(parent_seat.equals(host_parentCode)){
				sn_list.add(sn);
			}
		}
		return sn_list;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		log.info(header.getRetMesg());
		
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			errorMsg = header.getRetMesg();
			log.info(errorMsg);
			throw new Exception(errorMsg);
		}
		
		BodyDO body = responseDataObject.getDataObject(MesgFlds.BODY, BodyDO.class);
		String nas_sn = body.getString("NAS_SN");
		if(StringUtils.isBlank(nas_sn)){
			throw new Exception("获取可分配NAS设备SN信息失败！");
		}
		//根据NAS_SN获取storage_model
		String nas_model = "";//service.getStorageModelBySN(nas_sn);
		Map<String,String> vol_pool_map = body.getHashMap("VOLUME_POOL");
		//根据NAS_SN获取nas设备生产IP
		List<String> nas_ips = Lists.newArrayList();//service.getNasProcIPsBySN(nas_sn);
		if(nas_ips==null||nas_ips.isEmpty()){
			throw new Exception("获取NAS存储SN:["+nas_sn+"]生产ip信息错误！");
		}
		List<String> nas_ips_name = Arrays.asList(new String[]{"NAS_NP1","NAS_NP2","NAS_NP3"});
		Map<String,String> nas_ips_map = Maps.newHashMap();
		for(int i=0;i<nas_ips_name.size();i++){
			if(nas_ips.size()>i)
				nas_ips_map.put(nas_ips_name.get(i), nas_ips.get(i));
			else
				nas_ips_map.put(nas_ips_name.get(i), "");
		}
		//String nas_model = body.getString("NAS_MODEL");
		for(String deviceId:deviceIdList){
			setHandleResultParam(String.valueOf(deviceId),StorageConstants.RAC_NAS_SN , nas_sn);
			setHandleResultParam(String.valueOf(deviceId),StorageConstants.RAC_NAS_VOL_POOL,JSONObject.toJSONString(vol_pool_map));
			setHandleResultParam(String.valueOf(deviceId),StorageConstants.RAC_NAS_MODEL,nas_model);
			setHandleResultParam(String.valueOf(deviceId),StorageConstants.RAC_NAS_CHOOSE_FINISHED,"FINISHED");
			String nas_ips_str = "";
			for(String key:nas_ips_map.keySet()){
				nas_ips_str += "," + nas_ips_map.get(key);
				setHandleResultParam(String.valueOf(deviceId),key,nas_ips_map.get(key));
			}
			setHandleResultParam(String.valueOf(deviceId), StorageConstants.NAS_NPS, nas_ips_str.length() > 0 ? nas_ips_str.substring(1) : "");
		}
		rtn_sb.append("选择NAS SN:"+nas_sn).append("\n");
		rtn_sb.append("获取vol与pool:"+JSONObject.toJSONString(vol_pool_map)).append("\n");
	}

	@SuppressWarnings("unchecked")
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
				setDeviceId(deviceIdList.get(0));
				Map<String, Object> devMapInfo = (Map<String, Object>) contenxtParmas
						.get(String.valueOf(getDeviceId()));

				//是否已选择完RAC 心跳盘
				String choose_rac_flag = (String)devMapInfo.get(StorageConstants.RAC_NAS_CHOOSE_FINISHED);
				if(StringUtils.isNotBlank(choose_rac_flag)){
					throw new Exception("以完成选择NAS心跳盘操作！");
				}
				// 获取高可用类型，是否为RAC
				String cluster_type = (String) devMapInfo
						.get(StorageConstants.CLUSTER_TYPE);
				if (!cluster_type.equals(StorageConstants.CLUSTER_TYPE_RAC)) {
					throw new Exception("高可用性非RAC，无需进行NAS 心跳盘分配");
				}

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
	/**
	 * @Title: getParentSeatCode
	 * @Description: 获取设备根位置信息
	 * @field: @param seatCode
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public String getParentSeatCode(String seatCode) throws BizException {
		AutomationDAO automationDao = (AutomationDAO) WebApplicationManager.getBean("AutomationDaoImpl");
		while (true) {
			String parentCode = automationDao.getParentSeatCodeByCode(seatCode);
			if ("0".equals(parentCode)) {
				return seatCode;
			} else {
				seatCode = parentCode;
			}
		}
	}
}
