package com.git.cloud.handler.automation.se.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.common.StorageDBBasicEnum;
import com.git.cloud.handler.automation.se.common.StorageDBHAEnum;
import com.git.cloud.handler.automation.se.common.StorageDBRACEnum;
import com.git.cloud.handler.automation.se.common.StorageDBSingleEnum;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.po.CmLun;
import com.git.cloud.handler.automation.se.po.FabricPo;
import com.git.cloud.handler.automation.se.po.RmStorageSuAppRefPo;
import com.git.cloud.handler.automation.se.po.StorageMgrPo;
import com.git.cloud.handler.automation.se.po.StorageSuPo;
import com.git.cloud.handler.automation.se.vo.FabricVo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.storage.model.vo.StorageDeviceVo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SEOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SelectLunHandler  extends RemoteAbstractAutomationHandler{

	private static Logger log = LoggerFactory.getLogger(SelectLunHandler.class);

	public StringBuffer rtn_sb = new StringBuffer();

	private boolean FLAG = Boolean.TRUE;
//	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("structureUnitDao");

	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");
	private String errorMsg = new String();

	private String su_id = "";
	private String deviceId ;
	private String storage_model;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	private List<String> sn_list = Lists.newArrayList();

	protected static final String STORAGEBASE = "STORAGE_SN";

	protected static final String STORAGEONE = STORAGEBASE + 1;

	protected static final String STORAGETWO = STORAGEBASE + 2;


	protected static final String NAME = "name";

	protected static final String SIZE = "size";

	protected static final String COUNT = "count";
	@SuppressWarnings("unchecked")
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

		String deviceId = getDeviceId();
		Map<String, Object> deviceParams = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(deviceId));
		String resource_type = (String) deviceParams
				.get(StorageConstants.RESOURCE_TYPE_STORAGE);
		if(StringUtils.isBlank(resource_type)){
			throw new Exception("获取[RESOURCE_TYPE_STORAGE]参数错误！");
		}
		header.setResourceClass("SE");
		header.setResourceType(resource_type);
		header.setOperation(SEOpration.SELECT_LUN);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();

		String select_finished_flag = (String)deviceParams.get(StorageConstants.DB_SELECT_LUN_FINISHED);
		if(StringUtils.isNotBlank(select_finished_flag)){
			throw new Exception("已完成选择存储操作！");
		}
		// 获取数据中心
		String dcenter_ename = (String) deviceParams
				.get(StorageConstants.DATACENTER_ENAME);
		if(StringUtils.isBlank(dcenter_ename)){
			throw new Exception("获取DATACENTER_ENAME参数错误!");
		}
		body.setString(StorageConstants.DATACENTER_ENAME, dcenter_ename);

		//获取STORAGE_MODEL
		storage_model = (String)deviceParams.get(StorageConstants.STORAGE_MODEL);
		if(StringUtils.isBlank(storage_model)){
			throw new Exception("获取STORAGE_MODEL参数错误!");
		}
		body.setString(StorageConstants.STORAGE_MODEL, storage_model);
		
		// String os_type = (String)deviceParams.get(StorageConstants.OS_TYPE);
		String os_type = "VIOC";
		if(StringUtils.isBlank(os_type)){
			throw new Exception("获取OS_TYPE参数错误！");
		}
		// 获取存储可用率
		String available = (String)deviceParams.get(StorageConstants.AVAILABLE_RATE_THRESHOLD);
		if(StringUtils.isBlank(available)){
			throw new Exception("获取AVAILABLE_RATE_THRESHOLD参数错误!");
		}
		body.setString(StorageConstants.AVAILABLE_RATE_THRESHOLD, available);
		
		// 获取构建单元id
		su_id = (String) deviceParams.get(StorageConstants.SU_ID);

		// 获取存储sn信息
		String sn_list_str = (String) deviceParams.get(StorageConstants.SN);
		sn_list = JSONObject.parseArray(sn_list_str, String.class);
		body.setList(StorageConstants.SN, sn_list);

		// 获取存储管理机登陆信息
		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoBySuId(su_id);

		if (mgrVo==null) {
			throw new Exception("获取构建单元su_id:" + su_id
					+ "管理机登陆信息失败！");
		}
		HashMap<String,String> mgrMap = Maps.newHashMap();
		mgrMap.put("SMISISERVER_IP", mgrVo.getManagerIp());
		mgrMap.put("NAME_SPACE", mgrVo.getNamespace());
		mgrMap.put("USER_NAME", mgrVo.getUserName());
		mgrMap.put("USER_PASSWD", mgrVo.getPassword());
		//unit_map.put(StorageConstants.STORAGE_MGR, mgrMap);
				
		body.setHashMap(StorageConstants.STORAGE_MGR, mgrMap);

		// 构建分盘类型、容量、数量信息
		List<Map<String, String>> list = buildDataType(deviceParams);
		body.setList(StorageConstants.DATA_TYPE, list);

		// 获取存储已选择但未使用的LUN信息
		HashMap<String,List<String>> map = getSelectedLun(sn_list);
		body.setHashMap("LUN_SELECTED", map);
		reqData.setDataObject(MesgFlds.BODY, body);
		log.info("请求选择lun信息："+JSONObject.toJSONString(reqData));
		return reqData;
	}

	@SuppressWarnings("unchecked")
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {
		// TODO Auto-generated method stub
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		log.info(header.getRetMesg());

		log.info("amq-返回信息："+JSONObject.toJSONString(responseDataObject));
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			errorMsg = header.getRetMesg();
			log.info(errorMsg);
			throw new Exception(errorMsg);
		}

		BodyDO body = responseDataObject.getDataObject(MesgFlds.BODY,
				BodyDO.class);

		String rrinfoId = (String) contenxtParmas.get(RRINFO_ID);
		String nodeId = (String) contenxtParmas.get(NODE_ID);

		List<String> deviceIdList = getDeviceIdList("",nodeId,rrinfoId,"");

		// 获取存储资源池服务级别
		Map<String, Object> deviceParmas = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(deviceIdList.get(0)));
		String storage_model = (String) deviceParmas
				.get(StorageConstants.STORAGE_MODEL);

		Map<String, Object> model_sn_lun_type_map = body
				.getHashMap(storage_model);

		if (model_sn_lun_type_map.isEmpty()) {
			throw new Exception("获取选择的lun信息失败！");
		}
		Map<String, Object> sn_lun_type_map =  body
				.getHashMap(storage_model);

		List<String> selectedLunResult = new ArrayList<String>();
		List<CmLun> selectedSaveLun = new ArrayList<CmLun>();

		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		Map<String, String> storage_sn_map = Maps.newHashMap();
		String storageSN = "";
		for (int i = 0; i < sn_list.size(); i++) {
			storageSN = sn_list.get(i);
			if(!sn_lun_type_map.containsKey(storageSN)){
				throw new Exception("获取存储:"+storageSN+"选择LUN为空！");
			}
			storage_sn_map.put(STORAGEBASE + (i + 1), storageSN);
			rtn_sb.append(STORAGEBASE + (i + 1) + ":" + storageSN).append("\n");
			Map<String, Object> lun_type_map = (Map<String, Object>) sn_lun_type_map
					.get(storageSN);
			Iterator<Entry<String, Object>> lun_type_itr = lun_type_map
					.entrySet().iterator();
			while (lun_type_itr.hasNext()) {
				Entry<String, Object> entry_lun_type = lun_type_itr.next();
				String type = entry_lun_type.getKey();
				List<String> lunList = (List<String>) entry_lun_type.getValue();

				if (type == null || lunList == null || "".equals(type)
						|| lunList.size() == 0) {
					errorMsg = "The type and lun map:"
							+ entry_lun_type.toString()
							+ " contains null value";
					throw new Exception(errorMsg);
				}

//				if (service.checkLunOccupyStatus(lunList, storageSN)) {
//					errorMsg = "The assigned lun:" + lunList + " is occupied";
//					throw new Exception(errorMsg);
//				}
				selectedLunResult.addAll(lunList);

				for (int j = 0; j < deviceIdList.size(); j++) {
					setHandleResultParam(String.valueOf(deviceIdList.get(j)),
							storageSN + "_TYPE_" + type, JSONObject.toJSONString(lunList));
				}
				rtn_sb.append(storageSN + ":[" + type + "]" + lunList).append(
						"\n");
			}
			selectedSaveLun.addAll(buildAutoCmLunList(selectedLunResult,
					storageSN, d));
		}
		// 获取存储类型
		String storage_dev_type = "";
		Map<String, String> param = Maps.newHashMap();
		param.put("sn", storageSN);
		StorageDeviceVo storage_dev = storageDao.findStorageDevBySn(storageSN);
		if (storage_dev.getDeviceModel().trim()
				.contains(StorageConstants.STORAGE_DEV_TYPE_EMC)) {
			storage_dev_type = StorageConstants.STORAGE_DEV_TYPE_EMC;
		}else if(storage_dev.getDeviceModel().trim().contains(StorageConstants.STORAGE_DEV_TYPE_HITACHI)){
			storage_dev_type = StorageConstants.STORAGE_DEV_TYPE_HITACHI;
		}else{
			throw new Exception("无法处理"+storage_dev_type+"类型存储！");
		}

		for (int j = 0; j < deviceIdList.size(); j++) {
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.STORAGE_SN_MAP, JSONObject.toJSONString(storage_sn_map));
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.STORAGEONE,
					storage_sn_map.get(StorageConstants.STORAGEONE));
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.STORAGETWO,
					storage_sn_map.get(StorageConstants.STORAGETWO));
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.STORAGE_DEV_TYPE, storage_dev_type);
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
							StorageConstants.DB_SELECT_LUN_FINISHED, "FINISHED");
		}

		// 保存su_id到CM_APP_STORAGESU_REF表中
		persistSuId(storageSN, contenxtParmas);

		persistUnitInfo(deviceIdList);
		FLAG = Boolean.FALSE;
		//service.batchPersistLun(selectedSaveLun);
		log.info("[ STORAGE_INFO ] : " + sn_lun_type_map.toString());
	}

	@Override
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

				List<String> deviceIdList = getDeviceIdList("",nodeId, rrinfoId,"");
				setDeviceId(deviceIdList.get(0));
				IDataObject responseDataObject = null;
				for (int i = 0; i < 3 && FLAG; i++) {
					IDataObject requestDataObject = buildRequestData(contenxtParmas);
					responseDataObject = getResAdpterInvoker().invoke(
							requestDataObject, getTimeOut());
					handleResonpse(contenxtParmas, responseDataObject);
				}

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
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null！");
		}
		return JSON.toJSONString(rtn_map);
	}

	/**
	 * @throws Exception 
	 * @Title: buildDataType
	 * @Description: 组装请求创建lun数据结构
	 * @field: @param deviceParams
	 * @field: @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> buildDataType(
			Map<String, Object> deviceParams) throws Exception {
		// 获取集群模式
		String cluster_type = (String) deviceParams.get(StorageConstants.CLUSTER_TYPE);
		//资源池服务级别
		String resPoolLevel = (String) deviceParams.get(StorageConstants.RES_POOL_LEVEL);
		String heartBeatName = "";
		String heartBeatCount = "";
		String heartBeatCapacity = "";
		String dataDiskName = "";
		String dataCapacity = "";
		String archDiskName = "";
		String archCapacity = "";
		int cell_capacity = 0;
		boolean heartDiskFlag = Boolean.TRUE;
		List<Map<String, String>> list = Lists.newArrayList();
		Map<String, String> heartBeat_map = Maps.newHashMap();

		// RAC集群lun-type信息
		if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_RAC)) {
			heartBeatName = deviceParams.get(StorageDBRACEnum.HEARTBEAT_NAME.name())
					.toString();
			dataDiskName = deviceParams.get(StorageDBRACEnum.DATA_DISK_NAME.name())
					.toString();
			dataCapacity = deviceParams
					.get(StorageDBRACEnum.DATA_DISK_CAPACITY.name()).toString();
			archDiskName = deviceParams.get(StorageDBRACEnum.ARCH_DISK_NAME.name())
					.toString();
			archCapacity = deviceParams
					.get(StorageDBRACEnum.ARCH_DISK_CAPACITY.name()).toString();
			heartBeatCapacity = deviceParams.get(
					StorageDBRACEnum.HEARTBEAT_CAPACITY.name()).toString();
			cell_capacity = Integer.valueOf(deviceParams.get(
					StorageDBRACEnum.CELL_CAPACITY.name()).toString());
		} else if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_HA)) {
			// HA集群类型lun-type信息
			heartBeatName = deviceParams.get(StorageDBHAEnum.HEARTBEAT_NAME.name())
					.toString();
			dataDiskName = deviceParams.get(StorageDBHAEnum.DATA_DISK_NAME.name())
					.toString();
			dataCapacity = deviceParams.get(StorageDBHAEnum.DATA_DISK_CAPACITY.name())
					.toString();
			archDiskName = deviceParams.get(StorageDBHAEnum.ARCH_DISK_NAME.name())
					.toString();
			archCapacity = deviceParams.get(StorageDBHAEnum.ARCH_DISK_CAPACITY.name())
					.toString();
			heartBeatCapacity = deviceParams.get(
					StorageDBHAEnum.HEARTBEAT_CAPACITY.name()).toString();
			cell_capacity = Integer.valueOf(deviceParams.get(
					StorageDBHAEnum.CELL_CAPACITY.name()).toString());
		} else if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_SINGLE)) {
			// single单机模式lun-type类型；无心跳盘
			heartDiskFlag = Boolean.FALSE;
			dataDiskName = deviceParams.get(StorageDBSingleEnum.DATA_DISK_NAME.name())
					.toString();
			dataCapacity = deviceParams.get(
					StorageDBSingleEnum.DATA_DISK_CAPACITY.name()).toString();
			archDiskName = deviceParams.get(StorageDBSingleEnum.ARCH_DISK_NAME.name())
					.toString();
			archCapacity = deviceParams.get(
					StorageDBSingleEnum.ARCH_DISK_CAPACITY.name()).toString();
			cell_capacity = Integer.valueOf(deviceParams.get(
					StorageDBSingleEnum.CELL_CAPACITY.name()).toString());
		}
		// SAN心跳盘数据结构
		if (heartDiskFlag) {
			if(resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_PLATINUM)){
				heartBeatCount = String.valueOf(deviceParams.get("PLATINUM_HEARTBEAT_COUNT"));
			}else if(resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_GLOD)){
				heartBeatCount = String.valueOf(deviceParams.get("GLOD_HEARTBEAT_COUNT"));
			}else {
				heartBeatCount = String.valueOf(deviceParams.get("SILVER_HEARTBEAT_COUNT"));
			}
			heartBeat_map.put("name", heartBeatName);
			heartBeat_map.put("size", heartBeatCapacity);
			heartBeat_map.put("count", heartBeatCount);
			list.add(heartBeat_map);
		}

		// data盘数据结构
		Map<String, String> dataMap = buildDishMap(dataDiskName, dataCapacity,
				cell_capacity);
		if (dataMap == null || dataMap.size() == 0) {
			throw new Exception("获取DATA盘分盘初始化信息失败！");
		}
		list.add(dataMap);
		// arch盘数据结构
		Map<String, String> archMap = buildDishMap(archDiskName, archCapacity,
				cell_capacity);
		if (archMap == null || archMap.size() == 0) {
			throw new Exception("获取ARCH盘分盘初始化信息失败！");
		}
		list.add(archMap);
		return list;
	}

	/**
	 * @throws Exception 
	 * @Title: buildDishMap
	 * @Description: 根据请求分盘大小与单位分盘大小获取指定lun-type的数据结构
	 * @field: @param diskName
	 * @field: @param diskCapacity
	 * @field: @param cellCapacity
	 * @field: @return
	 * @return HashMap<String,String>
	 * @throws
	 */
	private HashMap<String, String> buildDishMap(String diskName,
			String diskCapacity, int cellCapacity) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		// validate if the data size value is null

		if (diskName == null && diskCapacity == null) {
			return null;
		}
		if (diskName == null || diskCapacity == null) {
			throw new Exception(
					" disk name or disk capacity size null");
		}

		long dataSizeLong = Long.parseLong(String.valueOf(diskCapacity));
		// validate the data size value is zero or negative
		if (dataSizeLong <= 0) {
			throw new Exception(
					"data capacity size is zero or negative");
		}

		int count = (int) dataSizeLong / cellCapacity;
		if (dataSizeLong % cellCapacity != 0) {
			map.put(COUNT, String.valueOf(count + 1));
		} else {
			map.put(COUNT, String.valueOf(count));
		}
		map.put(NAME, String.valueOf(diskName));
		map.put(SIZE, String.valueOf(cellCapacity));
		return map;
	}

	private List<CmLun> buildAutoCmLunList(List<String> lunList, String sn,
			Date d) {
		//DevicePo po = storageDeviceDao.findObjectByField("sn", sn);

		StorageDeviceVo storage_dev = storageDao.findStorageDevBySn(sn);
		List<CmLun> list = new ArrayList<CmLun>();
		for (int i = 0; i < lunList.size(); i++) {
			CmLun lun = new CmLun();
			lun.setLunName(lunList.get(i));
			lun.setLunStatus(StorageConstants.LUN_STATUS_OCCYPY);
			lun.setStorageId(storage_dev.getId());
			lun.setLunId(UUIDGenerator.getUUID());
			lun.setLunSelectTime(d);
			list.add(lun);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public void persistSuId(String sn, Map<String, Object> contenxtParmas) {
		Map<String, Object> deviceMap = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(getDeviceId()));
		String duType = StorageConstants.DATA_APP_TYPE_DB;
		String appId = deviceMap.get(StorageDBBasicEnum.APP_ID.name()).toString();

		StorageSuPo unitPo = storageDao.findSuInfoBySN(sn);
		String suId = unitPo.getSuId();
		RmStorageSuAppRefPo refPo = new RmStorageSuAppRefPo();
		refPo.setSuId(suId);
		refPo.setAppId(appId);
		refPo.setDuType(duType);
		refPo.setAppStorageId(UUIDGenerator.getUUID());
		try {
			storageDao.save("se.suSave", refPo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			log.error("异常exception",e);
		}
		
	}

	/**
	 * @Title: persistUnitInfo
	 * @Description: 保存分配相关信息pooltype,fabric,storagemgr_id
	 * @field: @param sn
	 * @field: @param deviceIdList
	 * @return void
	 * @throws
	 */
	public void persistUnitInfo(List<String> deviceIdList) {
		HashMap<String, String> map = new HashMap<String, String>();

		StorageSuPo unitPo = storageDao.findStorageSuById(su_id);
		String fabric1Id = unitPo.getFabricId1();
		String fabric2Id = unitPo.getFabricId2();
		String storageMgrId = unitPo.getStorageMgrId();
		String childPoolId = unitPo.getStorageChildResPoolId();
		// 获取存储池类型VMAX,VSP
//		String poolStorageType = subPoolDao.findStoragePoolTypeBySuId(Long
//				.valueOf(su_id));
		map.put(StorageConstants.STORAGE_POOL_TYPE, storage_model);

		// 获取fabric信息
		FabricVo fabricVo1 = storageDao.findFabricVoById(fabric1Id);
		FabricVo fabricVo2 = storageDao.findFabricVoById(fabric2Id);
		String fabric1name = fabricVo1.getFabricName();
		String fabric2name = fabricVo2.getFabricName();
		if (fabric1name.compareTo(fabric2name) < 0) {
			map.put("FABRIC1_ID", fabric1Id);
			map.put("FABRIC2_ID", fabric2Id);
			log.info("FABRIC1_NAME:" + fabric1name + "FABRIC2_NAME"
					+ fabric2name);
		} else {
			map.put("FABRIC1_ID", fabric2Id);
			map.put("FABRIC2_ID", fabric1Id);
			log.info("FABRIC1_NAME:" + fabric2name + "FABRIC2_NAME"
					+ fabric1name);
		}
		// 获取交换机类型
		String switchType = fabricVo1.getManagerTypeCode();
		map.put(StorageConstants.SWITCH_TYPE, switchType);
		// 管理机ID
		map.put(StorageConstants.STORAGEMGR_ID, String.valueOf(storageMgrId));
		String unitInfo = JSONObject.toJSONString(map);
		for (String deviceId : deviceIdList) {
			setHandleResultParam(String.valueOf(deviceId),
					StorageConstants.UNIT_INFO, unitInfo);
			setHandleResultParam(String.valueOf(deviceId),
					StorageConstants.RESOURCE_TYPE_SWITCH, switchType);
		}
	}
	
	public HashMap<String,List<String>> getSelectedLun(List<String> sn_list){
		List<String> selectedLun = Lists.newArrayList();
		HashMap<String,List<String>> map = Maps.newHashMap();
		for(String sn:sn_list){
			selectedLun = storageDao.findOccupyLunBysn(sn);
			map.put(sn, selectedLun);
		}
		return map;
	}

	
//	/**
//	 * @Title: findSuId
//	 * @Description: 获取当前系统已经分配过存储的构建单元id
//	 * @field: @param deviceParamsList
//	 * @field: @return
//	 * @return String
//	 * @throws
//	 */
//	private String findSuId(Map<String, Object> deviceParamsList) {
//		try {
//			Long appId = Long.valueOf(deviceParamsList.get("APP_ID").toString());
//			log.debug("The App_Id is {}", appId);
//			String duType = String.valueOf(deviceParamsList
//					.get("SERVERFUNCTION"));
//			log.debug("The du_type is {}", duType);
//
//			CmAppStorageSuRefDao refDao = (CmAppStorageSuRefDao) FrameworkContext
//					.getApplicationContext().getBean("cmAppStorageSuRefDao");
//			String suId = String.valueOf(refDao.findSuIdByAppIdAndDutype(appId,
//					duType));
//			log.debug("The su_id is {}", suId);
//
//			return suId;
//		} catch (Exception e) {
//			logger.error("异常exception",e);
//			//throw new Exception(e.printStackTrace());
//		}
//		return null;
//	}
}
