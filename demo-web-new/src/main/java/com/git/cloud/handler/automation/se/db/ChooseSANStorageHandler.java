package com.git.cloud.handler.automation.se.db;

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
import com.git.cloud.handler.automation.se.po.RmStorageSuAppRefPo;
import com.git.cloud.handler.automation.se.po.StorageSuPo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.BizParamInstService;
import com.git.cloud.handler.service.StorageService;
//import com.ccb.iomp.cloud.data.dao.StructureUnitDAO;
import com.git.cloud.resmgt.common.dao.impl.CmVmDAO;
import com.git.cloud.resmgt.storage.model.po.SePoolLevelRulePo;
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

/**
 * @ClassName:ChooseSANStorageHandler
 * @Description:选取DB-SAN存储资源分配信息 
 *                               向adapter层提供VMAX获取VSP可用资源登陆和SN信息，以及分配LUN类型、大小、数量等信息
 *                               返回，待分配存储sn以及相关LUN类型的LUN_ID信息列表。
 * @author chengbin.co
 * @date 2014-3-10 下午2:56:47
 * 
 * 
 */
public class ChooseSANStorageHandler  extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(ChooseSANStorageHandler.class);

	private boolean FLAG = Boolean.TRUE;

	//private String errorMsg = "";
	private StringBuffer handler_errorMsg = new StringBuffer();

	public StringBuffer rtn_sb = new StringBuffer();

	private List<String> suId_used_list = Lists.newArrayList();
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");

	private StorageService storageService = (StorageService)WebApplicationManager.getBean("storageService");

	private CmVmDAO vmDao = (CmVmDAO) WebApplicationManager.getBean("cmVmDAO");
	private Map<String, Object> storage_info_map = Maps.newHashMap();
	Map<String, List<String>> vmax_sn_sel_lun_map = Maps.newHashMap();
	Map<String, List<String>> vsp_sn_sel_lun_map = Maps.newHashMap();
	private int storage_model_num = 0;
	private String rrinfoId = "";
	private String flowInstId = "";
	private String nodeId = "";
	private String resPoolLevel = new String();
	private String cluster_type = "";
	private String NAME = "name";
	private String SIZE = "size";
	private String COUNT = "count";
	private String deviceId ;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	private Map<String,String> exeParams = Maps.newHashMap();
	@SuppressWarnings("unchecked")
	protected IDataObject buildRequestData(Map<String, Object> deviceParams) throws Exception {
		// TODO Auto-generated method stub
		AutomationService automationService = (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
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

		Entry<String, Object> entry = (Entry<String, Object>) deviceParams
				.get("storage_info_map_entry");
		String storage_model = entry.getKey();
		List<Object> storage_info = (List<Object>) entry.getValue();

		String storage_type = "";
		if (storage_model.equals(StorageConstants.STORAGE_MODEL_VMAX)) {
			storage_type = "EMC";
		} else if (storage_model.equals(StorageConstants.STORAGE_MODEL_VSP)) {
			storage_type = "HDS";
		}
		header.setResourceClass("SE");
		header.setResourceType(storage_type);
		header.setOperation(SEOpration.SELECT_STORAGE_UINT);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		// 获取数据中心
		String dcenter_ename = (String) deviceParams
				.get(StorageConstants.DATACENTER_ENAME);
		if(StringUtils.isBlank(dcenter_ename)){
			throw new Exception("获取DATACENTER_ENAME参数错误!");
		}
		body.setString(StorageConstants.DATACENTER_ENAME, dcenter_ename);

		// 获取存储可用率
		String available = (String)deviceParams.get(StorageConstants.AVAILABLE_RATE_THRESHOLD);
		if(StringUtils.isBlank(available)){
			throw new Exception("获取AVAILABLE_RATE_THRESHOLD参数错误!");
		}
		body.setString(StorageConstants.AVAILABLE_RATE_THRESHOLD, available);
		
		// 获取操作系统类型
		//String os_type = (String)deviceParams.get(StorageConstants.OS_TYPE);
		String os_type = "VIOC";
		if(StringUtils.isBlank(os_type)){
			throw new Exception("获取OS_TYPE参数错误!");
		}
		body.setString(StorageConstants.OS_TYPE,os_type);


		// 构建分盘类型、容量、数量信息
		List<Map<String, String>> list = buildDataType(deviceParams);
		body.setList(StorageConstants.DATA_TYPE, list);

		// 构建选择存储信息
		Map<String, List<String>> select_lun_map = getSelectLunInfoByModel(
				storage_model, storage_info);

		// 添加供选择的存储相关信息
		HashMap<String, Object> req_sn_info_map = Maps.newHashMap();
		req_sn_info_map.put(StorageConstants.STORAGE_TYPE,
				StorageConstants.STORAGE_APPLY_TYPE_SAN);
		req_sn_info_map.put(storage_model, storage_info);
		req_sn_info_map.put(StorageConstants.LUN_SELECTED, select_lun_map);

		body.setHashMap(StorageConstants.STORAGE_SN_KEY, req_sn_info_map);

		reqData.setDataObject(MesgFlds.BODY, body);

		log.debug("获取存储请求信息：" + JSONObject.toJSONString(reqData));
		return reqData;
	}

	/**
	 * @Title: getSelectLunInfoByModel
	 * @Description: 获取指定VMAX、VSP类型存储中已占用lun信息
	 * @field: @param storage_model
	 * @field: @param storage_info
	 * @field: @return
	 * @return Map<String,List<String>>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String, List<String>> getSelectLunInfoByModel(
			String storage_model, List<Object> storage_info) {
		Map<String, List<String>> rtn_map = Maps.newHashMap();
		for (int i = 0; i < storage_info.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) storage_info.get(i);
			List<String> sn_list = (List<String>) map.get("SN");
			for (String sn : sn_list) {
				if (storage_model.equals(StorageConstants.STORAGE_MODEL_VMAX)) {
					if (vmax_sn_sel_lun_map.containsKey(sn)) {
						rtn_map.put(sn, vmax_sn_sel_lun_map.get(sn));
					}
				} else if (storage_model
						.equals(StorageConstants.STORAGE_MODEL_VSP)) {
					if (vsp_sn_sel_lun_map.containsKey(sn)) {
						rtn_map.put(sn, vsp_sn_sel_lun_map.get(sn));
					}
				}
			}
		}
		return rtn_map;
	}

	/**
	 * @throws Exception 
	 * @Title: getStorageInfoMap
	 * @Description: 根据构建单元ID，分别获取VMAX、VSP类型相关存储信息
	 * @field: @param deviceParams
	 * @field: @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getStorageInfoMap(
			Map<String, Object> deviceParams) throws Exception {
		// old---获取可用构建单元列表
		// 获取可用存储资源子池 modify by chengbin 2016-09-18
		List<StorageSuPo> su_list = getStorageSuPoList(deviceParams);

		List<Object> vmax_list = Lists.newArrayList();
		List<Object> vsp_list = Lists.newArrayList();

		for (StorageSuPo suPo : su_list) {
			// 主机已分配存储类型
//			Map<String, ?> assignedInfoMap = getAssginedStorageDevModel(su_id,
//					getDeviceId());
			Map<String, ?> assignedInfoMap = Maps.newHashMap();
			// 获取构建单元下的存储信息
			Map<String, Object> type_unit_map = getTypeUnitMapInfo(suPo,
					assignedInfoMap, suId_used_list);
			if (type_unit_map == null || type_unit_map.isEmpty()) {
				continue;
			}
			// 分配将vmax、vsp类型存储信息保存到list中
			if (type_unit_map.containsKey(StorageConstants.STORAGE_MODEL_VMAX)) {
				vmax_list.add(type_unit_map
						.get(StorageConstants.STORAGE_MODEL_VMAX));
			} else if (type_unit_map
					.containsKey(StorageConstants.STORAGE_MODEL_VSP)) {
				vsp_list.add(type_unit_map
						.get(StorageConstants.STORAGE_MODEL_VSP));
			} else {
				continue;
			}
		}
		if (vmax_list.size() == 0 && vsp_list.size() == 0) {
			throw new Exception("获取可用构建单元信息失败！");
		}
		// 获取vmax，vsp存储列表信息
		Map<String, Object> storage_sn_map = Maps.newHashMap();
		if (vmax_list.size() > 0) {
			storage_sn_map.put(StorageConstants.STORAGE_MODEL_VMAX, vmax_list);
		}
		if (vsp_list.size() > 0) {
			storage_sn_map.put(StorageConstants.STORAGE_MODEL_VSP, vsp_list);
		}
		// 添加已占用的lun-id信息
		// storage_sn_map.put(StorageConstants.LUN_SELECTED, sn_sel_lun_map);
		return storage_sn_map;
	}

	/**
	 * @Title: getAssginStorageDevModel
	 * @Description: 根据su_id获取此构建单元是否分配为当前主机分配过存储，使用相同类型的存储设备
	 * @field: @param su_id
	 * @field: @param device_id
	 * @return void
	 * @throws
	 */
	public Map<String, ?> getAssginedStorageDevModel(String su_id, String device_id) {
		List<Map<String, ?>> list = storageDao.findUsedStorageDevInfoBySuId(su_id,
				device_id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @throws Exception 
	 * @Title: getTypeUnitMapInfo
	 * @Description: 组装可用存储相关信息
	 * @field: @param su_id
	 * @field: @param assignedInfoMap
	 * @field: @param suId_used
	 * @field: @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getTypeUnitMapInfo(StorageSuPo suPo,
			Map<String, ?> assignedInfoMap, List<String> suId_used_list) throws Exception {
		Map<String, Object> type_unit_map = Maps.newHashMap();
		String model_code = "";
		if (assignedInfoMap != null && assignedInfoMap.size() > 0) {
			model_code = String.valueOf(assignedInfoMap
					.get(StorageConstants.STORAGE_MODEL_CODE));
		}

		// 根据su_id获取存储信息列表
		// 根据资源子池获取存储设备信息列表 --modify by chengbin 2016-09-19
		List<StorageDeviceVo> storage_info_list = null;
				//表已经删除 需要进行改造 storageDao.findStorageDevInfoByPoolId(suPo.getStorageChildResPoolId(), model_code);
		if (storage_info_list == null || storage_info_list.size() == 0) {
			return null;
		}
		List<String> sn_list = Lists.newArrayList();
		String _model_name = "";
		// 获取存储sn列表以及存储类型vmax,vsp
		for (StorageDeviceVo vo : storage_info_list) {
			sn_list.add(vo.getSn());
			_model_name = vo.getDeviceModel();
		}

		Map<String, Object> unit_map = getUnitMapInfo(suPo.getSuId(), suId_used_list,
				sn_list, _model_name);
		if (unit_map != null && !unit_map.isEmpty()) {
			type_unit_map.put(_model_name, unit_map);
		}

		return type_unit_map;
	}

	/**
	 * @throws Exception 
	 * @Title: getUnitMapInfo
	 * @Description: 封装单个unit信息
	 * @field: @param su_id
	 * @field: @param suId_used
	 * @field: @param sn_list
	 * @field: @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getUnitMapInfo(String su_id, List<String> suId_used_list,
			List<String> sn_list, String storage_model) throws Exception {
		Map<String, Object> unit_map = Maps.newHashMap();
		// 添加存储sn列表信息
		unit_map.put(StorageConstants.SN, sn_list);
		// 判断是否优先选择
		if (suId_used_list !=null && suId_used_list.contains(su_id)) {
			unit_map.put(StorageConstants.IS_PRIOR, "Y");
		} else {
			unit_map.put(StorageConstants.IS_PRIOR, "N");
		}
		// 指定存储已选择但未使用的lun-id列表信息
		/*
		for (String sn : sn_list) {
			List<String> selectedLun = storageDeviceDao.findOccupyLunBysn(sn);
			if (storage_model.equals(StorageConstants.STORAGE_MODEL_VMAX)) {
				if (selectedLun != null && selectedLun.size() > 0) {
					if (vmax_sn_sel_lun_map.containsKey(sn)) {
						vmax_sn_sel_lun_map.get(sn).addAll(selectedLun);
					} else {
						vmax_sn_sel_lun_map.put(sn, selectedLun);
					}
				} else {
					continue;
				}
			} else if (storage_model.equals(StorageConstants.STORAGE_MODEL_VSP)) {
				if (selectedLun != null && selectedLun.size() > 0) {
					if (vsp_sn_sel_lun_map.containsKey(sn)) {
						vsp_sn_sel_lun_map.get(sn).addAll(selectedLun);
					} else {
						vsp_sn_sel_lun_map.put(sn, selectedLun);
					}
				} else {
					continue;
				}
			}
		}*/
		// unit_map.put(StorageConstants.LUN_SELECTED, sn_list_map);

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
		unit_map.put(StorageConstants.STORAGE_MGR, mgrMap);
		
		// 添加构建单元id
		unit_map.put("SU_ID", su_id);
		return unit_map;
	}

	/**
	 * @Title: findSuId
	 * @Description: 获取当前系统已经分配过存储的构建单元id
	 * @field: @param deviceParamsList
	 * @field: @return
	 * @return String
	 * @throws
	 */
	private List<Long> findSuIds(Map<String, Object> deviceParamsList) {
		try {
			Long appId = Long.valueOf(deviceParamsList.get("APP_ID").toString());
			log.debug("The App_Id is {}", appId);
			String duType = String.valueOf(deviceParamsList
					.get("SERVERFUNCTION"));
			log.debug("The du_type is {}", duType);

			
			List<Long> suId_list = storageService.findSuIdsByAppIdAndDutype(appId,duType);
			log.info("appid["+appId+"],duType["+duType+"] 分配过的构建单元"+suId_used_list.toString());

			if(suId_list==null||suId_list.size()==0){
				return null;
			}
			log.debug("The su_ids is {}", suId_list.toString());

			return suId_list;
		} catch (Exception e) {
			log.error("异常exception",e);
			//throw new CommonRuntimeException(e.printStackTrace());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected void handleResonpse(Map<String, Object> deviceParmas,
			IDataObject responseDataObject) throws Exception {
		// TODO Auto-generated method stub

		log.info("amq-返回信息："+JSONObject.toJSONString(responseDataObject));
		log.info("当前获取存储信息次数："+String.valueOf(deviceParmas.get("cur_model_num")));
		int cur_model_num = Integer.valueOf(String.valueOf(deviceParmas.get("cur_model_num")));
		String cloud_service_type = (String)deviceParmas.get(StorageConstants.CLOUD_SERVICE_TYPE);
		
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		log.info(header.getRetMesg());

		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())){
			if(cur_model_num == storage_model_num){
				handler_errorMsg.append(header.getRetMesg());
				log.info(handler_errorMsg.toString());
				throw new Exception(handler_errorMsg.toString());
			}else{
				handler_errorMsg.append(header.getRetMesg());
				log.info(handler_errorMsg.toString());
				return ;
			}
		}

		BodyDO body = responseDataObject.getDataObject(MesgFlds.BODY,
				BodyDO.class);

		// rrinfoId = (Long) contenxtParmas.get(RRINFO_ID);
		List<String> deviceIdList = getDeviceIdList("", "", rrinfoId, "");

//		// 获取存储资源池服务级别
//		resPoolLevel = (String) deviceParmas
//				.get(StorageConstants.RES_POOL_LEVEL);
		// 获取服务级别对应的存储设备数量
		int storage_size = 0;
		if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_PLATINUM)) {
			storage_size = StorageConstants.PLATINUM_STORAGE_SIZE;
		} else if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_GLOD)) {
			storage_size = StorageConstants.GLOD_STORAGE_SIZE;
		} else if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_SILVER)) {
			storage_size = StorageConstants.SILVER_STORAGE_SIZE;
		}
		// 存储模式 VMAX,VSP
		String storage_model = body.getString(StorageConstants.STORAGE_MODEL);

		// 获取构建单元ID
		String su_id = body.getString(StorageConstants.SU_ID);

		// 获取存储sn列表
		List<String> sn_list = body.getList(StorageConstants.SN);
//		if (sn_list.size() != storage_size) {
//			throw new CommonRuntimeException("获取的存储sn数量错误！");
//		}

		String is_morrior_storage = "";
		String is_nas_heartbeat = "FALSE";
		if(resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_PLATINUM)||resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_GLOD)){
			is_morrior_storage = "TRUE";
			if(cluster_type.equals(StorageConstants.CLUSTER_TYPE_RAC)){
				is_nas_heartbeat = "TRUE";
			}
		}else{
			is_morrior_storage = "FALSE";
		}

		exeParams.put(StorageConstants.STORAGE_MODEL, storage_model);
		exeParams.put(StorageConstants.IS_MORRIOR_STORAGE, is_morrior_storage);
		exeParams.put(StorageConstants.IS_NAS_HEARTBEAT, is_nas_heartbeat);

		BizParamInstService bizParamService = getBizParamInstService();
		List<BizParamInstPo> paramInsts = Lists.newArrayList();
		for (int j = 0; j < deviceIdList.size(); j++) {
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.STORAGE_MODEL, storage_model);
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.SU_ID, su_id);
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.SN, JSONObject.toJSONString(sn_list));
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.RESOURCE_TYPE_STORAGE, header.getResourceType());
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.RES_POOL_LEVEL, resPoolLevel);
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.DB_CHOOSE_STORAGE_FINISHED, "FINISHED");
			setHandleResultParam(String.valueOf(deviceIdList.get(j)),
					StorageConstants.IS_NAS_HEARTBEAT, is_nas_heartbeat);
			
			//bizParamService.batchSaveParamInst(unSavedParamInsts);
			if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_VM_INSTALL)){
				BizParamInstPo po = new BizParamInstPo();
				po.setDeviceId(deviceIdList.get(j));
				po.setFlowInstId(flowInstId);
				po.setNodeId(nodeId);
				po.setParamKey("storagetype");
				po.setParamValue(storage_model);
				paramInsts.add(po);
				bizParamService.saveParas(paramInsts);
			}
		}

		
		
		rtn_sb.append("存储资源池级别："+StorageConstants.STORAGE_APPLY_TYPE_SAN+"-"+resPoolLevel).append("\n");
		rtn_sb.append("存储设备类型："+header.getResourceType()+"-"+storage_model).append("\n");
		rtn_sb.append("构建单元ID:"+su_id).append("\n");
		rtn_sb.append("选择存储SN:"+sn_list.toString()).append("\n");
		FLAG = Boolean.FALSE;
	}

	/**
	 * @Title: persistUnitInfo
	 * @Description: 保存分配相关信息pooltype,fabric,storagemgr_id
	 * @field: @param sn
	 * @field: @param deviceIdList
	 * @return void
	 * @throws
	 
	public void persistUnitInfo(String sn, List<Long> deviceIdList) {
		HashMap<String, String> map = new HashMap<String, String>();
		StorageChildResPoolDAO subPoolDao = (StorageChildResPoolDAO) FrameworkContext
				.getApplicationContext().getBean("storageChildResPoolDAO");
		StorageSuPo unitPo = unitDao.findSuInfoBySN(sn);
		String fabric1Id = unitPo.getFabricName1();
		String fabric2Id = unitPo.getFabricName2();
		String storageMgrId = unitPo.getStorageMgrId();
		// 获取存储池类型VMAX,VSP
		String poolStorageType = subPoolDao.findStoragePoolTypeBySuId(suId);
		map.put(StorageConstants.STORAGE_POOL_TYPE, poolStorageType);

		// 获取fabric信息
		FabricPo fabricPo = fabricParamsDao.findObjectByField("fabricId",
				fabric1Id);
		StorageMgrPo ciscoMgrPo = storageMgrDao.findObjectByField(
				"storageMgrId", fabricPo.getStorageMgrId());

		FabricPo fabricPo2 = fabricParamsDao.findObjectByField("fabricId",
				fabric2Id);
		String fabric1name = fabricPo.getFabricName();
		String fabric2name = fabricPo2.getFabricName();
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
		String switchType = ciscoMgrPo.getStorageMgrTypeCode();
		map.put(StorageConstants.SWITCH_TYPE, switchType);
		// 管理机ID
		map.put(StorageConstants.STORAGEMGR_ID, String.valueOf(storageMgrId));
		String unitInfo = JSONObject.toJSONString(map);
		for (Long deviceId : deviceIdList) {
			setHandleResultParam(String.valueOf(deviceId),
					StorageConstants.UNIT_INFO, unitInfo);
		}
	}
*/
	@SuppressWarnings("unchecked")
	public void persistSuId(String sn, Map<String, Object> contenxtParmas,
			String _suId_used) {
		Map<String, Object> deviceMap = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(getDeviceId()));
		String duType = StorageConstants.DATA_APP_TYPE_DB;
		String appId = deviceMap.get(StorageDBBasicEnum.APP_ID.name()).toString();

		StorageSuPo unitPo = storageDao.findSuInfoBySN(sn);
		String suId = unitPo.getSuId();
		String _suId = String.valueOf(suId);
		if (_suId.equals(_suId_used)) {
			return;
		} else {
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
	}

	public boolean checkMirrorStorageConsistency(
			List<HashMap<String, HashMap<String, List<String>>>> list) {
		HashMap<String, HashMap<String, List<String>>> map = list.get(0);
		HashMap<String, List<String>> subMap = map.entrySet().iterator().next()
				.getValue();
		log.info("[the disk type and lun map of the first storage is] :"
				+ subMap.toString());
		for (int i = 1; i < list.size(); i++) {
			HashMap<String, List<String>> secondStorage = list.get(i)
					.entrySet().iterator().next().getValue();
			log.info("[the disk type and lun map of the second storage is] :"
					+ subMap.toString());
			if (!compareMapEntry(subMap, secondStorage)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HashMap<String, Object> contenxtParmas) {
		Map<String, Object> rtn_map = Maps.newHashMap();
		if(contenxtParmas!=null){
			flowInstId =  contenxtParmas.get(FLOW_INST_ID).toString();
			nodeId = contenxtParmas.get(NODE_ID).toString();
			rrinfoId =contenxtParmas.get(RRINFO_ID).toString();

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			try {
				Map<String, Map<String, String>> handleParams = this
						.getHandleParams(flowInstId);

				contenxtParmas.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParmas);

				if (extHandleParams != null)
					contenxtParmas.putAll(extHandleParams);

				List<String> deviceIdList = getDeviceIdList("","",rrinfoId,"");
				setDeviceId(deviceIdList.get(0));
				IDataObject responseDataObject = null;
				Map<String, Object> deviceParams = (Map<String, Object>) contenxtParmas
						.get(String.valueOf(deviceIdList.get(0)));
				
				String choose_finished_flag = (String) deviceParams.get(StorageConstants.DB_CHOOSE_STORAGE_FINISHED);
				if(StringUtils.isNotBlank(choose_finished_flag)){
					throw new Exception("已完成SAN存储选择！");
				}

				
				// 查询app_id是否已有分配的构建单元
				//suId_used_list = findSuIds(deviceParams);
				
				// 获取可用存储信息
				storage_info_map = getStorageInfoMap(deviceParams);

				storage_model_num = storage_info_map.size();
				
				// 循环VMAX、VSP类型存储获取选择的信息
				int i=1;
				for (Iterator<Entry<String, Object>> itr = storage_info_map
						.entrySet().iterator(); itr.hasNext() && FLAG;) {
					Entry<String, Object> entry = itr.next();
					deviceParams.put("storage_info_map_entry", entry);
					deviceParams.put("cur_model_num", i);
					IDataObject requestDataObject = buildRequestData(deviceParams);
					responseDataObject = getResAdpterInvoker().invoke(
							requestDataObject, getTimeOut());
					handleResonpse(deviceParams, responseDataObject);
					i++;
				}
				// for (int i = 0; i < THREE && FLAG; i++) {
				// IDataObject requestDataObject = buildRequestData(contenxtParmas);
				// responseDataObject = getResAdpterInvoker().invoke(
				// requestDataObject, getTimeOut());
				// handleResonpse(contenxtParmas, responseDataObject);
				// }

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
			rtn_map.put("exeParams", exeParams);
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null");
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
		cluster_type = (String) deviceParams.get("CLUSTER_TYPE");
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
			heartBeatName = deviceParams.get(StorageDBRACEnum.HEARTBEAT_NAME.name()).toString();
			dataDiskName = deviceParams.get(StorageDBRACEnum.DATA_DISK_NAME.name()).toString();
			dataCapacity = deviceParams.get(StorageDBRACEnum.DATA_DISK_CAPACITY.name()).toString();
			archDiskName = deviceParams.get(StorageDBRACEnum.ARCH_DISK_NAME.name()).toString();
			archCapacity = deviceParams.get(StorageDBRACEnum.ARCH_DISK_CAPACITY.name()).toString();
			heartBeatCapacity = deviceParams.get(StorageDBRACEnum.HEARTBEAT_CAPACITY.name()).toString();
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

	/**
	 * @throws Exception 
	 * @Title: getSuList
	 * @Description: 根据输入参数确定资源池类型； 获取构建单元ID； 验证主机位置与构建单元关联的FABRIC位置是否在同一区域
	 * @field: @param deviceParams
	 * @field: @return
	 * @return List<Long>
	 * @throws
	 */
	private List<StorageSuPo> getStorageSuPoList(Map<String, Object> deviceParams) throws Exception {
		List<String> su_list = Lists.newArrayList();
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
		String data_app_type = StorageConstants.DATA_APP_TYPE_DB;
		String storageApplyType = StorageConstants.STORAGE_APPLY_TYPE_SAN;
		// 获取服务资源池级别
		resPoolLevel = getResPoolLevel(availability_level,
				performance_level, data_app_type);
		if(StringUtils.isBlank(resPoolLevel)){
			throw new Exception("可用性级别[" + availability_level
					+ "] 计算性能级别[" + performance_level + "]" + "数据使用类型["
					+ data_app_type + "]" + "\n" + "无对应SAN存储资源池级别");
		}
		log.info("服务资源池级别：" + storageApplyType + " " + resPoolLevel);

	//	RmResStoragePoolChildDAO resourcePoolChildDao = (RmResStoragePoolChildDAO) WebApplicationManager.getBean("RmResStoragePoolChildDAO");

		// 获取存储类型(VMAX、VSP),存储资源子池id
//		List<Map<String, ?>> resChildList = resourcePoolChildDao
//				.findListByDeviceId(getDeviceId(), storageApplyType,
//						resPoolLevel);
//		StructureUnitDAO structureUnitDao = (StructureUnitDAO) FrameworkContext
//				.getApplicationContext().getBean("structureUnitDao");
		//List<StorageSuPo> structureUnitList = Lists.newArrayList();

		List<Map<String,String>> resChildList = null;//表已经删除 需要进行改造storageService.getSeChildPoolIds(storageApplyType, resPoolLevel, getDeviceId(), "");
		// 获取构建单元列表
		for (Map<String,String> child:resChildList) {
			List<StorageSuPo> storageSuPoList = storageService.getStorageSuByPoolId(child.get("id").toString());
			if(storageSuPoList==null||storageSuPoList.isEmpty()){
				throw new Exception("获取构建单元信息失败！");
			}else{
				return storageSuPoList;
			}
		}
//
//		// 查询主机位置
//		String seatCode = "";
//		String dev_type = (String)deviceParams.get("DEV_TYPE");
//		if(dev_type.equals(StorageConstants.DEV_TYPE_VM)){
//			seatCode = vmDao.getHostNameByVmId(getDeviceId());
//			if (StringUtils.isBlank(seatCode)) {
//				throw new Exception("获取虚拟机deviceId:["+getDeviceId()+"]主机位置失败！");
//			}
//		}else if(dev_type.equals(StorageConstants.DEV_TYPE_HM)){
//			seatCode = deviceDao.findById(getDeviceId()).getSeatCode();
//			if (StringUtils.isBlank(seatCode)) {
//				throw new CommonRuntimeException("获取物理机deviceId:["+getDeviceId()+"]位置失败！");
//			}
//		}
//		String parentCodeOfVm = getParentSeatCode(seatCode);
//
//		List<String> parentSeatCodeList = new ArrayList<String>();
//
//		// 循环cm_stroage_su表对象。获取fabric信息
//		for (StructureUnitPo po : structureUnitList) {
//			List<String> fabricId_list = Arrays.asList(new String[] {
//					po.getFabricId1(), po.getFabricId2() });
//			List<String> fabric_name_list = Lists.newArrayList();
//			String fabric_seatCode = new String();
//			// 循环获取fabric信息
//			for (String fabricId : fabricId_list) {
//				FabricPo fabricPo = fabricParamsDao.findObjectByField(
//						"fabricId", fabricId);
//
//				if (fabricPo == null
//						|| StringUtils.isEmpty(fabricPo.getSeatCode())) {
//					throw new Exception("Can Not Find Fabric");
//				}
//
//				// 获取fabric根位置
//				String parentSeatCode = getParentSeatCode(fabricPo
//						.getSeatCode());
//				parentSeatCodeList.add(parentSeatCode);
//
//				fabric_name_list.add(fabricPo.getFabricName());
//			}
//			// 验证2个fabric根位置是否一致
//			if (!parentSeatCodeList.get(0).equalsIgnoreCase(
//					parentSeatCodeList.get(1))) {
//				throw new Exception(
//						"The parent_code of fabric1 and fabric2 is not equal");
//			}
//			fabric_seatCode = parentSeatCodeList.get(0);
//			// 判断fabric位置与主机位置是否一致
//			if (parentCodeOfVm.equals(fabric_seatCode)) {
//				su_list.add(po.getSuId());
//			}
//		}
		return null;
	}

	/**
	 * @Title: getParentSeatCode
	 * @Description: 获取设备根位置信息
	 * @field: @param seatCode
	 * @field: @return
	 * @return String
	 * @throws
	 
	public String getParentSeatCode(String seatCode) {
		SeatManageDAO dao = (SeatManageDAO) FrameworkContext
				.getApplicationContext().getBean("SeatManageDAO");
		String sql = "select s.parentCode from SeatPo s where s.seatCode=:seatCode";
		while (true) {
			HashMap<String, String> params = Maps.newHashMap();
			params.put("seatCode", seatCode);
			String parentCode = dao.findObjectByJql(sql, params);
			if ("0".equals(parentCode)) {
				return seatCode;
			} else {
				seatCode = parentCode;
			}
		}
	}
	*/
	protected boolean compareMapEntry(HashMap<String, List<String>> map,
			HashMap<String, List<String>> comparedMap) {
		int size = map.size();
		if (size == comparedMap.size()) {
			for (Iterator<Entry<String, List<String>>> it = map.entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry<String, List<String>> entry = it.next();
				String key = entry.getKey();
				List<String> list = entry.getValue();
				for (int i = 0; i < list.size(); i++) {
					String temp = list.get(i);
					if (!(comparedMap.containsKey(key) && comparedMap.get(key)
							.contains(temp))) {
						return Boolean.FALSE;
					}
				}
			}
		}
		return Boolean.TRUE;
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
			int rule_rTime = Integer.valueOf(po.getCellValue().replace("<", ""));
			if (rTime < rule_rTime) {
				r_cell_value = po.getConditionTwo();
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
			String rule_iops = po.getCellValue();
			if (!rule_iops.contains("-")) {
				if (iops >= Integer.valueOf(rule_iops.trim())) {
					i_cell_value = po.getConditionTwo();
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
			String rule_mbps = po.getCellValue();
			if(!rule_mbps.contains("-")){
				if(mbps>=Integer.valueOf(rule_mbps.trim())){
					m_cell_value = po.getConditionTwo();
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
}
