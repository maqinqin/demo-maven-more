package com.git.cloud.handler.automation.se.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.git.cloud.handler.automation.se.common.StorageFileBasicEnum;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.StorageService;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmSeatPo;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SEOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Maps;

/**
 * @ClassName:ChooseNASStorageHandler
 * @Description:选择可用的NAS存储设备
 * @author chengbin
 * @date 2014-9-25 下午1:55:37
 *
 *
 */
public class ChooseNASStorageHandler extends RemoteAbstractAutomationHandler {


	private static Logger log = LoggerFactory
			.getLogger(ChooseNASStorageHandler.class);
	private String rrinfoId ="";

	public StringBuffer rtn_sb = new StringBuffer();

	private String errorMsg = new String();
	private String resPoolLevel = "";
	private List<String> deviceIdList = new ArrayList<String>();
	private String deviceId;
	
	StorageService storageService;
	AutomationService automationService;
	public ChooseNASStorageHandler() throws Exception{
		automationService = getAutomationService();
		storageService = (StorageService) ApplicationCtxUtil.getBean("storageService");
	}
	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		try {
			// 增加数据中心路由标识
			String queueIdentify = this.getQueueIdent(rrinfoId);
			header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),
					queueIdentify);
		} catch (Exception e) {
			throw new Exception("get LinkRouteType error!",e);
		}
		header.setResourceClass(EnumResouseHeader.SE_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.NET_RES_TYPE.getValue());
		header.setOperation(SEOpration.SELECTSTORAGE);
		reqData.setDataObject(MesgFlds.HEADER, header);
		
		BodyDO body = BodyDO.CreateBodyDO();
		Map<String,Object> deviceParamsList = (Map<String,Object>)contenxtParmas.get(this.getDeviceId());

		String is_vol_shared = (String) deviceParamsList.get(StorageConstants.IS_VOL_SHARED);
		if(StringUtils.isBlank(is_vol_shared)){
			throw new Exception("获取[IS_VOL_SHARED]参数！");
		}
		if(is_vol_shared.equals("Y")){
			throw new Exception("共享参数[IS_VOL_SHARED]为Y,无需选择NAS存储");
		}
		// 添加数据中心
		String datecenter = (String) deviceParamsList
				.get(StorageConstants.DATACENTER_ENAME);
		body.setString(StorageConstants.DATACENTER_ENAME, datecenter);

		// 获取存储可用率
		String available_rate_threshold = deviceParamsList
				.get(StorageConstants.NAS_AVAILABLE_RATE_THRESHOLD).toString();
		body.setString(StorageConstants.NAS_AVAILABLE_RATE_THRESHOLD, available_rate_threshold);
		
		// 分配类型
		body.setString("CHOOSE_TYPE",StorageConstants.DATA_APP_TYPE_FILE);
		
		// 获取DATA_TYPE信息
		HashMap<String,String> data_type_map = buildDataType(deviceParamsList);
		List<HashMap<String,String>> data_type_map_list = new ArrayList<HashMap<String,String>>();
		data_type_map_list.add(data_type_map);
		body.setList(StorageConstants.DATA_TYPE, data_type_map_list);
		
		// 获取已使用su_id
		//List<Long> suId_used_list = findSuIds(deviceParamsList);
		
		// 获取备选存储su_sn信息
		Map<String, List<String>> pool_sn_list = getPoolSnList(deviceParamsList);
		
		//获取存储sn相关信息
		List<Map<String,Object>> sn_info_list = new ArrayList<Map<String,Object>>();
		for(Iterator<Entry<String,List<String>>> itr = pool_sn_list.entrySet().iterator();itr.hasNext();){
			Entry<String,List<String>> entry = itr.next();
			//根据su_id获取登陆信息
			String childPoolId = entry.getKey();
			List<String> sn_list = entry.getValue();
			for(String sn:sn_list){
				Map<String,String> mgr_info = storageService.getNASMgrLoginInfo(sn);
				if(mgr_info==null||mgr_info.isEmpty()){
					throw new Exception("获取存储sn:["+sn+"]管理机登陆信息为空!");
				}
				Map<String,String> sn_mgr_info = new HashMap<String,String>();
				sn_mgr_info.put(StorageConstants.SERVICE_IP, mgr_info.get("SERVER_IP"));
				sn_mgr_info.put(StorageConstants.USERNAME, mgr_info.get("USER_NAME"));
				sn_mgr_info.put(StorageConstants.USERPASSWD, mgr_info.get("USER_PASSWD"));
				sn_mgr_info.put(StorageConstants.NAMESPACE, "interop");
					Map<String,Object> sn_info_map = new HashMap<String,Object>();
				sn_info_map.put("SN", sn);
				sn_info_map.put("STORAGE_MGR", sn_mgr_info);
//				if(suId_used_list!=null && suId_used_list.contains(su_id)){
//					sn_info_map.put("IS_PRIOR", "Y");
//				}else{
//					sn_info_map.put("IS_PRIOR", "N");
//				}
				sn_info_list.add(sn_info_map);
			}
		}
		body.setList("NAS_SN", sn_info_list);
		log.debug("选择NAS存储REQ:"+JSONObject.toJSONString(body));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
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
		String nas_model = storageService.getStorageModelBySN(nas_sn);
		Map<String,String> vol_pool_map = body.getHashMap("VOLUME_POOL");
		
		
		//根据NAS_SN获取nas设备生产IP
		List<String> nas_ips = storageService.getNasProcIPsBySN(nas_sn);
		if(nas_ips==null||nas_ips.isEmpty()){
			throw new Exception("获取NAS存储SN:["+nas_sn+"]生产ip信息错误！");
		}
		List<String> nas_ips_name = Arrays.asList(new String[]{"NAS_NP1","NAS_NP2","NAS_NP3"});
		Map<String,String> nas_ips_map = new HashMap<String,String>();
		for(int i=0;i<nas_ips_name.size();i++){
			if(nas_ips.size()>i)
				nas_ips_map.put(nas_ips_name.get(i), nas_ips.get(i));
			else
				nas_ips_map.put(nas_ips_name.get(i), "");
		}
		//String nas_model = body.getString("NAS_MODEL");
		for(String deviceId:deviceIdList){
			setHandleResultParam(deviceId,StorageConstants.NAS_SN , nas_sn);
			setHandleResultParam(deviceId,StorageConstants.NAS_VOL_POOL,JSONObject.toJSONString(vol_pool_map));
			setHandleResultParam(deviceId,StorageConstants.NAS_MODEL,nas_model);
			setHandleResultParam(deviceId,StorageConstants.NAS_CHOOSE_FINISHED,"FINISHED");
			setHandleResultParam(deviceId,StorageConstants.RES_POOL_LEVEL, resPoolLevel);
			for(String key:nas_ips_map.keySet()){
				setHandleResultParam(deviceId,key,nas_ips_map.get(key));
			}
		}
		rtn_sb.append("选择NAS SN:"+nas_sn).append("\n");
		rtn_sb.append("获取vol与pool:"+JSONObject.toJSONString(vol_pool_map)).append("\n");
	}

	@SuppressWarnings("unchecked")
	@Override
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

				deviceIdList = this.getDeviceIdList("", "", rrinfoId, "");
				setDeviceId(deviceIdList.get(0));
				IDataObject responseDataObject = null;
				Map<String, Object> deviceParams = (Map<String, Object>) contenxtParmas
						.get(String.valueOf(deviceIdList.get(0)));
				String choose_flag = (String)deviceParams.get(StorageConstants.NAS_CHOOSE_FINISHED);
				if(StringUtils.isNotBlank(choose_flag)){
					throw new Exception("已完成NAS存储设备选择操作！");
				}
				// 获取可用存储信息
				//getSuSnList
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
	 * @throws Exception 
	 * @Title: getSuList
	 * @Description: 根据输入参数确定资源池类型； 获取构建单元ID； 验证主机位置与构建单元关联的FABRIC位置是否在同一区域
	 * @field: @param deviceParams
	 * @field: @return
	 * @return List<Long>
	 * @throws
	 */
	private Map<String,List<String>> getPoolSnList(Map<String, Object> deviceParams) throws Exception {

		//交易类型
		String trade_type = (String)deviceParams.get(StorageConstants.NAS_TRADE_TYPE);
		if(StringUtils.isBlank(trade_type)){
			throw new Exception("获取参数[TRADE_TYPE]错误");
		}
		Map<String,List<String>> su_sn_map = Maps.newHashMap();
		
		// 获取可用性级别
		String availability_level = getAvailabilityLevel(deviceParams);
		if(StringUtils.isBlank(availability_level)){
			throw new Exception("无法获取可用性级别！");
		}
		log.info("可用性级别：" + availability_level);
		// 获取计算性能
		String performance_level = getPerformanceLevel(deviceParams);
		if(StringUtils.isBlank(performance_level)){
			throw new Exception("无法获取计算性能！");
		}
		log.info("计算性能：" + performance_level);

		//
		String data_app_type = deviceParams.get("DATA_APP_TYPE").toString();
		String storagePoolCode = "";
		if (data_app_type.equals(StorageConstants.DATA_APP_TYPE_FILE)) {
			storagePoolCode = StorageConstants.STORAGE_APPLY_TYPE_NAS;
		}else{
			throw new Exception("服务请求参数错误[DATA_APP_TYPE]");
		}
		// 获取服务资源池级别
		resPoolLevel = getResPoolLevel(availability_level,
				performance_level, data_app_type);
		if(StringUtils.isBlank(resPoolLevel)){
			throw new Exception("可用性级别[" + availability_level
					+ "] 计算性能级别[" + performance_level + "]" + "数据使用类型["
					+ data_app_type + "]" + "\n" + "无对应NAS存储资源池级别");
		}
		log.info("服务资源池级别：" + storagePoolCode + " " + resPoolLevel);

		String dev_type = String.valueOf(deviceParams.get("DEV_TYPE"));
		//根据存储资源池编码(storage_res_pool_code),服务级别，申请分配存储的虚拟机所在资源池，确定资源子池
		List<Map<String,String>> child_pool_id_list = null;// 表已经删除 需要进行改造//storageService.getSeChildPoolIds(storagePoolCode,resPoolLevel,getDeviceId(),dev_type);
		
		//根据资源子池ID获取存储设备信息
		
		// 查询主机位置
		String seatCode = storageService.getSeatCodeByDeviceId(getDeviceId(), dev_type);
		String parentCodeOfVm = getParentSeatCode(seatCode);

		// 循环cm_stroage_su表对象。NAS存储位置信息
		for (Map<String,String> childPoolIdMap : child_pool_id_list) {
			List<String> sn_list = new ArrayList<String>();
			String childPoolId = childPoolIdMap.get("id");
			//根据childPoolId获取存储设备sn信息
			List<CmDevicePo> dev_list = storageService.getDeviceByChildPoolId(childPoolId);
			if(dev_list.isEmpty()){
				continue;
			}
			for(CmDevicePo devPo:dev_list){
				String seat_id = devPo.getSeatId();
				String parentCodeOfSn = getParentSeatCode(seat_id);
				if(parentCodeOfSn.equals(parentCodeOfVm)){
					sn_list.add(devPo.getSn());
				}
			}
			if(sn_list.isEmpty()){
				continue;
			}
			su_sn_map.put(childPoolId, sn_list);
		}
		return su_sn_map;
	}
	
	/**
	 * @Title: getParentSeatCode
	 * @Description: 获取设备根位置信息
	 * @field: @param seatCode
	 * @field: @return
	 * @return String
	 * @throws
	 */
	public String getParentSeatCode(String seatId) {
		while (true) {
			CmSeatPo po = storageService.getCmSeat(seatId);
			if ("0".equals(po.getParentCode())) {
				return po.getSeatCode();
			} else {
				seatId = po.getParentCode();
			}
		}
	}
//	private List<Long> findSuIds(Map<String, Object> deviceParamsList) {
//		try {
//			Long appId = Long.valueOf(deviceParamsList.get("APP_ID").toString());
//			log.debug("The App_Id is {}", appId);
//			String duType = String.valueOf(deviceParamsList
//					.get("SERVERFUNCTION"));
//			log.debug("The du_type is {}", duType);
//
//			CmAppStorageSuRefDao refDao = (CmAppStorageSuRefDao) FrameworkContext
//					.getApplicationContext().getBean("cmAppStorageSuRefDao");
//			List<Long> suId_list = refDao.findSuIdsByAppIdAndDutype(appId,duType);
//			log.info("appid["+appId+"],duType["+duType+"] 分配过的构建单元"+suId_list.toString());
//
//			if(suId_list==null||suId_list.size()==0){
//				return null;
//			}
//			log.debug("The su_ids is {}", suId_list.toString());
//
//			return suId_list;
//		} catch (Exception e) {
//			logger.error("异常exception",e);
//			//throw new Exception(e.printStackTrace());
//		}
//		return null;
//	}

	public HashMap<String,String> buildDataType(Map<String, Object> deviceParams){
		
		String size = (String)deviceParams.get(StorageFileBasicEnum.CREATE_VOL_CAPACITY.name());
		
		HashMap<String,String> map = new HashMap<String,String>();
		//获取应用名称
		String app_name = (String)deviceParams.get(StorageConstants.PROJECTABBR);
		//获取主机挂载点
		String mountpoint = (String)deviceParams.get(StorageFileBasicEnum.VOL_MOUNT_POINT.name());
		
		//组装vol-name
		String[] point_array = mountpoint.split("/");
		String last_point = point_array[point_array.length-1];
		String vol_name = app_name+"_"+last_point;
		map.put("vol_name", vol_name);
		map.put("size", size);		
		return map;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @throws Exception 
	 * @Title: getAvailabilityLevel
	 * @Description: 获取可用性级别
	 * @field: @param deviceParams
	 * @field: @return
	 * @return String
	 * @throws
	 */
	protected String getAvailabilityLevel(Map<String, Object> deviceParams) throws Exception {

		String appLevelObj = (String)deviceParams.get("APPLICATION_LEVEL");
		if(StringUtils.isBlank(appLevelObj)){
			throw new Exception("获取[APPLICATION_LEVEL]参数错误！");
		}
		String dataTypeObj = (String)deviceParams.get("DATA_TYPE");
		if(StringUtils.isBlank(dataTypeObj)){
			throw new Exception("获取[DATA_TYPE]参数错误！");
		}
		log.info("[ APPLICATION_LEVEL ] : " + appLevelObj);
		log.info("[ DATA_TYPE ] : " + dataTypeObj);
		List<SePoolLevelRulePo> rule_list = storageService.getAvailabilityInfo(
				appLevelObj, dataTypeObj, StorageConstants.AVAILABILITY_TYPE);
		if (rule_list != null && rule_list.size() == 1) {
			return rule_list.get(0).getCellValue();
		} else {
			throw new Exception("[ APPLICATION_LEVEL ] : " + appLevelObj+"[ DATA_TYPE ] : " + dataTypeObj+" 获取可用性级别错误！");
		}
	}

	/**
	 * @throws Exception 
	 * @Title: getPerformanceLevel
	 * @Description: 获取计算性能级别
	 * @field: @param deviceParams
	 * @field: @return
	 * @return String
	 * @throws
	 */
	protected String getPerformanceLevel(Map<String, Object> deviceParams) throws Exception {

		String responseTimeObj = (String)deviceParams.get("RESPONSE_TIME");
		if(StringUtils.isBlank(responseTimeObj)){
			throw new Exception("获取[RESPONSE_TIME]参数错误！");
		}
		String iopsObj = (String)deviceParams.get("IOPS");
		if(StringUtils.isBlank(iopsObj)){
			throw new Exception("获取[iopsObj]参数错误！");
		}
		String mbpsObj = (String)deviceParams.get("MBPS");
		if(StringUtils.isBlank(mbpsObj)){
			throw new Exception("获取[MBPS]参数错误！");
		}
		log.info("[ RESPONSE_TIME ] :" + responseTimeObj);
		log.info("[ IOPS ] :" + iopsObj);
		log.info("[ MBPS ] :" + mbpsObj);

		int rTime = Integer.valueOf(responseTimeObj);
		// 获取响应时间规则信息
		List<SePoolLevelRulePo> r_list = storageService.getPerformanceInfo(
				StorageConstants.RESPONSE_TIME,
				StorageConstants.PERFORMANCE_TYPE);
		String r_cell_value = "";
		for (SePoolLevelRulePo po : r_list) {
			int rule_rTime = Integer.valueOf(po.getConditionTwo());
			if (rTime < rule_rTime) {
				r_cell_value = po.getCellValue();
				break;
			} else {
				continue;
			}
		}
		if ("".equals(r_cell_value)) {
			r_cell_value = "LOW";
			//throw new Exception("获取响应时间性能失败！"+"[ RESPONSE_TIME ] :" + responseTimeObj);
		}
		log.info("[ RESPONSE_TIME ] [LEVEL]:" + r_cell_value);

		int iops = Integer.valueOf(iopsObj);
		// 获取IOPS规则信息
		List<SePoolLevelRulePo> i_list = storageService.getPerformanceInfo(
				StorageConstants.IOPS, StorageConstants.PERFORMANCE_TYPE);
		String i_cell_value = "";
		for (SePoolLevelRulePo po : i_list) {
			String rule_iops = po.getConditionTwo();
			if (!rule_iops.contains("-")) {
				if (iops >= Integer.valueOf(rule_iops)) {
					i_cell_value = po.getCellValue();
					break;
				}
				continue;
			} else {
				String[] rule_iops_array = rule_iops.split("-");
				if (iops >= Integer.valueOf(rule_iops_array[0])
						&& iops < Integer.valueOf(rule_iops_array[1])) {
					i_cell_value = po.getCellValue();
					break;
				}
				continue;
			}
		}
		if ("".equals(i_cell_value)) {
			i_cell_value = "LOW";
			//throw new Exception("获取IOPS性能参数失败！"+"[ IOPS ] :" + iopsObj);
		}

		log.info("[ IOPS ] [LEVEL]:" + i_cell_value);
		int mbps = Integer.valueOf(mbpsObj);
		// 获取MBPS规则信息
		List<SePoolLevelRulePo> m_list = storageService.getPerformanceInfo(
				StorageConstants.MBPS, StorageConstants.PERFORMANCE_TYPE);
		String m_cell_value = "";
		for(SePoolLevelRulePo po:m_list){
			String rule_mbps = po.getConditionTwo();
			if(!rule_mbps.contains("-")){
				if(mbps>=Integer.valueOf(rule_mbps)){
					m_cell_value = po.getCellValue();
					break;
				}
				continue;
			}else {
				String[] rule_mbps_array = rule_mbps.split("-");
				if(mbps>=Integer.valueOf(rule_mbps_array[0])&&mbps<Integer.valueOf(rule_mbps_array[1])){
					m_cell_value = po.getCellValue();
					break;
				}
				continue;
			}
		}
		if("".equals(m_cell_value)){
			throw new Exception("获取MBPS性能参数失败！"+"[ MBPS ] :" + mbpsObj);
		}
		log.info("[ MBPS ] [LEVEL]:" + m_cell_value);
		//根据RESPONSE_TIME、IOPS、MBPS获取数据性能级别
		String perf_level = "";
		Map<String ,String> perf_map = new HashMap<String,String>();
		perf_map.put(StorageConstants.RESPONSE_TIME, r_cell_value);
		perf_map.put(StorageConstants.IOPS, i_cell_value);
		perf_map.put(StorageConstants.MBPS, m_cell_value);
		if(perf_map.containsValue(StorageConstants.LEVLE_ADVANCED)){
			if(m_cell_value.equals(StorageConstants.LEVLE_ADVANCED)){
				perf_map.remove(StorageConstants.MBPS);
				if(perf_map.containsValue((StorageConstants.LEVLE_ADVANCED))){
					perf_level = StorageConstants.LEVLE_ADVANCED;
				}else {
					perf_level = StorageConstants.LEVLE_HIGH;
				}
			}else{
				perf_level = StorageConstants.LEVLE_ADVANCED;
			}
		}else if(perf_map.containsValue(StorageConstants.LEVLE_HIGH)){
			perf_level = StorageConstants.LEVLE_HIGH;
		}else if(perf_map.containsValue(StorageConstants.LEVLE_MIDDLE)){
			perf_level = StorageConstants.LEVLE_MIDDLE;
		}else if(perf_map.containsValue(StorageConstants.LEVLE_LOW)){
			perf_level = StorageConstants.LEVLE_LOW;
		}
		return perf_level;
	}
	
	/**
	 * @Title: getResPoolLevel
	 * @Description: 获取资源池服务级别
	 * @field: @param availability_level
	 * @field: @param perf_level
	 * @field: @param data_app_type
	 * @field: @return
	 * @return String
	 * @throws
	 */
	protected String getResPoolLevel(String availability_level,String perf_level,String data_app_type){
		String rule_type ="";
		if(data_app_type.equals(StorageConstants.DATA_APP_TYPE_DB)){
			rule_type = StorageConstants.SAN_SERVICE_LEVEL;
		}else if(data_app_type.equals(StorageConstants.DATA_APP_TYPE_FILE)){
			rule_type = StorageConstants.NAS_SERVICE_LEVEL;
		}
		return storageService.getResPoolLevel(availability_level, perf_level, rule_type);
	}
}
