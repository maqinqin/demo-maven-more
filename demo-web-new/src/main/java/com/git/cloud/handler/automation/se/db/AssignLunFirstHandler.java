package com.git.cloud.handler.automation.se.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.common.StorageDBBasicEnum;
import com.git.cloud.handler.automation.se.common.StorageDBHAEnum;
import com.git.cloud.handler.automation.se.common.StorageDBRACEnum;
import com.git.cloud.handler.automation.se.common.StorageDBSingleEnum;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.po.CmLun;
import com.git.cloud.handler.automation.se.po.StorageMgrPo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.storage.model.vo.StorageDeviceVo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SEOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.PwdUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AssignLunFirstHandler extends RemoteAbstractAutomationHandler {
	private static Logger log = LoggerFactory.getLogger(AssignLunFirstHandler.class);

	private List<String> scriptNameList = new ArrayList<String>();
	List<String> deviceIdList = Lists.newArrayList();
	private String sn = "";
	private Map<String, String> cur_sn_map = Maps.newHashMap();
	private List<String> lunIdLists = Lists.newArrayList();
	private String viewName = new String();
	private String storage_model = new String();
	private StringBuffer result_sb = new StringBuffer();
	private String deviceId;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");
	private CmDeviceDAO devDao = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");

	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {

		AutomationService automationService = (AutomationService) WebApplicationManager.getBean("automationServiceImpl");

		Map<String, Object> devMap = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(getDeviceId()));
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
		Map<String, Object> devMapInfo = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(deviceIdList.get(0)));
		header.setResourceClass("SE");
		String resoutce_type = devMapInfo.get(StorageConstants.RESOURCE_TYPE_STORAGE).toString();
		if(StringUtils.isBlank(resoutce_type)){
			throw new Exception("获取[RESOURCE_TYPE_STORAGE]参数错误！");
		}
		header.setResourceType(resoutce_type);
		header.setOperation(SEOpration.ASSIGN_LUN);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();
		//获取assign标志
		String assign_sn = (String)devMapInfo.get(StorageConstants.STORAGEONE_FINISHED);
		if(StringUtils.isNotBlank(assign_sn)){
			throw new Exception("存储["+assign_sn+"]已完成分盘操作!");
		}
		// 获取数据中心
		String dcenter_ename = (String) devMapInfo
				.get(StorageConstants.DATACENTER_ENAME);
		if(StringUtils.isBlank(dcenter_ename)){
			throw new Exception("获取DATACENTER_ENAME参数错误!");
		}
		body.setString(StorageConstants.DATACENTER_ENAME, dcenter_ename);
		String su_id = (String) devMapInfo.get(StorageConstants.SU_ID);
		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoBySuId(su_id);
		
		body.setString(StorageConstants.SERVICE_IP,mgrVo.getManagerIp());
		body.setString(StorageConstants.USERNAME,mgrVo.getUserName());
		body.setString(StorageConstants.USERPASSWD,mgrVo.getPassword());
		body.setString(StorageConstants.NAMESPACE, mgrVo.getNamespace());
		body.setHashMap("OS_INFO", buildOsInfo(devMap));

//		String sn_map_str = (String) devMapInfo
//				.get(StorageConstants.STORAGE_SN_MAP);
//		JSONObject sn_map = JSONObject.parseObject(sn_map_str);
		// 获取存储资源池服务级别
//		String resPoolLevel = (String) devMapInfo
//				.get(StorageConstants.RES_POOL_LEVEL);
		// 获取集群类型
		String cluster_type = (String) devMapInfo
				.get(StorageDBBasicEnum.CLUSTER_TYPE.name());

		List<String> lun_type = Lists.newArrayList();
		if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_RAC)) {
			lun_type.add((String) devMapInfo
					.get(StorageDBRACEnum.HEARTBEAT_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBRACEnum.DATA_DISK_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBRACEnum.ARCH_DISK_NAME.name()));
		} else if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_HA)) {
			lun_type.add((String) devMapInfo
					.get(StorageDBHAEnum.HEARTBEAT_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBHAEnum.DATA_DISK_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBHAEnum.ARCH_DISK_NAME.name()));
		} else if (cluster_type.equals(StorageConstants.CLUSTER_TYPE_SINGLE)) {
			lun_type.add((String) devMapInfo
					.get(StorageDBSingleEnum.DATA_DISK_NAME.name()));
			lun_type.add((String) devMapInfo
					.get(StorageDBSingleEnum.ARCH_DISK_NAME.name()));
		}
		//获取VIEW_NAME 

		viewName = devMapInfo.get(StorageConstants.VIEW_NAME).toString();
		// 获取存储设备类型
		storage_model = devMapInfo.get(StorageConstants.STORAGE_MODEL).toString();
		body.setString(StorageConstants.STORAGE_MODEL,storage_model);
		
		sn = devMapInfo.get(StorageConstants.STORAGEONE).toString();
		if(StringUtils.isBlank(sn)){
			throw new Exception("获取"+StorageConstants.STORAGEONE+"信息错误！");
		}
		cur_sn_map.put(StorageConstants.STORAGEONE, sn);
		
		// 获取存储lunids
		lunIdLists = getLunIdsList(devMapInfo, lun_type, sn);
		if (storage_model.equals(StorageConstants.STORAGE_MODEL_VMAX)) {
			// back up storage before
			//backupView(sn, "-before", contenxtParmas);
			// 获取设备hba-wwn信息
			List<String> wwpnList = Lists.newArrayList();
			for (String deviceId : deviceIdList) {
				Map<String, Object> dev_map_info = (Map<String, Object>) contenxtParmas
						.get(String.valueOf(deviceId));
				wwpnList.addAll(convertVsanJson(dev_map_info.get("HBA_WWN")));
			}
			// 根据存储SN号获取前端口信息
			Map<String, String> fapwwn_map = getfaPwwnBySN(devMapInfo,
					cur_sn_map, sn);
			List<String> pwwnList = getPwwnList(fapwwn_map);


			
			HashMap<String, Object> viewInfo = new HashMap<String, Object>();
			viewInfo.put(StorageConstants.STORAGE_SN_KEY, sn);
			viewInfo.put("HBA_WWN", wwpnList);
			viewInfo.put("FA_WWN", pwwnList);
			viewInfo.put("LUN_ID", lunIdLists);

			viewInfo.put("IG_NAME", viewName);
			viewInfo.put("PG_NAME", viewName);
			viewInfo.put("SG_NAME", viewName);
			viewInfo.put("VIEW_NAME", viewName);
			
			body.setHashMap("VIEW_INFO", viewInfo);
			log.info("[ view info ]:" + viewInfo.toString());
			reqData.setDataObject(MesgFlds.BODY, body);
		} else if (storage_model
				.equals(StorageConstants.STORAGE_MODEL_VSP)) {
			HashMap<String,Object> viewInfo = Maps.newHashMap();
			viewInfo.put(StorageConstants.STORAGE_SN_KEY, sn);
			viewInfo.put("LUN_ID", lunIdLists);
			
			// 根据存储SN号获取前端口信息
			Map<String, String> fapwwn_map = getfaPwwnBySN(devMapInfo,
					cur_sn_map, sn);
			List<String> pwwnList = getPwwnList(fapwwn_map);
			
			//根据zone信息获取fa_pwwn对应的hba_wwpn信息
			List<Map<String,Object>> view_member_list = getViewMember(contenxtParmas,deviceIdList,pwwnList);
			viewInfo.put("VIEW_MEMBER", view_member_list);
			body.setHashMap("VIEW_INFO", viewInfo);
			reqData.setDataObject(MesgFlds.BODY, body);
		}
		log.info("AssignLunFirst reqData:"+JSONObject.toJSONString(reqData));
		return reqData;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getViewMember(Map<String,Object> contenxtParmas,List<String> deviceIdList,List<String> pwwnList){
		List<List<String>> list_members = Lists.newArrayList();
		for(String deviceId:deviceIdList){
			Map<String,Object> devMapInfo = (Map<String,Object>)contenxtParmas.get(String.valueOf(deviceId));
			String fabric1_obj_str = (String)devMapInfo.get(StorageConstants.FABRIC1);
			String fabric2_obj_str = (String)devMapInfo.get(StorageConstants.FABRIC2);
			List<String> list = Arrays.asList(new String[]{fabric1_obj_str, fabric2_obj_str});
			for(String fabric_obj_str:list){
				JSONArray array_obj = JSONObject.parseArray(fabric_obj_str);
				for(int i=0;i<array_obj.size();i++){
					String zone_obj_str = array_obj.getString(i);
					JSONObject zone_obj_map = JSONObject.parseObject(zone_obj_str);
					JSONArray members_array = zone_obj_map.getJSONArray("members");
					List<String> members_list = Lists.newArrayList();
					for(int j=0;j<members_array.size();j++){
						members_list.add(String.valueOf(members_array.get(j)));
					}
					list_members.add(members_list);
				}
			}
		}
		List<Map<String,Object>> view_member_list = Lists.newArrayList();
		for(String pwwn:pwwnList){
			Map<String,Object> pwwn_hba_map = Maps.newHashMap();
			List<String> hba_list = Lists.newArrayList();
			for(List<String> members : list_members){
				if(members.contains(pwwn)){
					members.remove(pwwn);
					hba_list.add(members.get(0));
				}
			}
			pwwn_hba_map.put("FA_WWN", pwwn);
			pwwn_hba_map.put("HBA_WWN", hba_list);
			view_member_list.add(pwwn_hba_map);
		}
		return view_member_list;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {
		log.info(JSONObject.toJSONString(responseDataObject));
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);

		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}
		BodyDO body = responseDataObject.getDataObject(MesgFlds.BODY,
				BodyDO.class);
		
		result_sb.append("----------"+storage_model+" Storage sn:"+ sn + "----------").append("\n");
		result_sb.append("devices:");
		if (storage_model.equals(StorageConstants.STORAGE_MODEL_VMAX)) {
			//保存VIEW_INFO信息
			Map<String,Object> viewInfo = body.getHashMap("VIEW_INFO");
			for (int i = 0; i < deviceIdList.size(); i++) {
				setHandleResultParam(String.valueOf(deviceIdList.get(i)),
						StorageConstants.STORAGEONE_FINISHED, cur_sn_map.get(StorageConstants.STORAGEONE));
				setHandleResultParam(String.valueOf(deviceIdList.get(i)),
						sn+"_VIEW_INFO", JSONObject.toJSONString(viewInfo));
				result_sb.append(devDao.findCmDeviceById(deviceIdList.get(i))+" ");

			}
			result_sb.append("\n");
			result_sb.append("VIEW_NAME:" + viewInfo.get("VIEW_NAME").toString())
					.append("\n");
			result_sb.append("IG_NAME:" + viewInfo.get("IG_NAME").toString()).append(
					"\n");
			result_sb.append(
					"IG_MEMBER:" + viewInfo.get("IG_MEMBER").toString())
					.append("\n");
			result_sb.append("PG_NAME:" + viewInfo.get("PG_NAME")).append(
					"\n");
			result_sb
					.append("PG_MEMBER:" + viewInfo.get("PG_MEMBER").toString())
					.append("\n");
			result_sb.append("SG_NAME:" + viewInfo.get("SG_NAME")).append(
					"\n");
			result_sb.append("SG_MEMBER:" + viewInfo.get("SG_MEMBER")).append(
					"\n");

			// back up storage after
			//backupView(sn, "-after", contenxtParmas);

		} else if (storage_model
				.equals(StorageConstants.STORAGE_MODEL_VSP)) {//保存VIEW_INFO信息
			List<Map<String,Object>> viewInfo = body.getList("VIEW_INFO");
			for (int i = 0; i < deviceIdList.size(); i++) {
				setHandleResultParam(String.valueOf(deviceIdList.get(i)),
						StorageConstants.STORAGEONE_FINISHED, cur_sn_map.get(StorageConstants.STORAGEONE));
				setHandleResultParam(String.valueOf(deviceIdList.get(i)),
						sn+"_VIEW_INFO", JSONObject.toJSONString(viewInfo));
				result_sb.append(devDao.findCmDeviceById(deviceIdList.get(i))+" ");
			}
			for(int i=0;i<viewInfo.size();i++){
				Map<String,Object> view_info_map = viewInfo.get(i);
				result_sb.append("VIEW_NAME:" + view_info_map.get("VIEW_NAME"))
				.append("\n");
//				result_sb.append("IG_NAME:" + jsonObj.getString("IG_NAME")).append(
//						"\n");
				result_sb.append(
						"IG_MEMBER:" + view_info_map.get("IG_MEMBER").toString())
						.append("\n");
//				result_sb.append("PG_NAME:" + jsonObj.getString("PG_NAME")).append(
//						"\n");
				result_sb
						.append("PG_MEMBER:" + view_info_map.get("PG_MEMBER").toString())
						.append("\n");
//				result_sb.append("SG_NAME:" + jsonObj.getString("SG_NAME")).append(
//						"\n");
				result_sb.append("SG_MEMBER:" + view_info_map.get("SG_MEMBER")).append(
						"\n");
			}
		}

		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, String> createtime_map = Maps.newHashMap();// fabricName
		createtime_map.put(sn, sdf.format(d));
		String scriptFileName = JSONObject.toJSONString(scriptNameList);
		String createTime = JSONObject.toJSONString(createtime_map);
		for (int i = 0; i < deviceIdList.size(); i++) {
			setHandleResultParam(String.valueOf(deviceIdList.get(i)), sn
					+ "_STORAGE_BACKUP_FILE", scriptFileName);
			setHandleResultParam(String.valueOf(deviceIdList.get(i)), sn
					+ "_LUN_CREATE_TIME", createTime);
		}
		// exeMsg_map.put("BACKUP_FILE1", scriptNameList);
		result_sb
				.append("STORAGE_BACKUP_FILE:" + scriptNameList.toString())
				.append("\n");
		
		//更新cm_lun表中lun状态占用改为使用
		List<CmLun> autoLunList = buildAutoCmLunList(sn,d);
		if(autoLunList==null || autoLunList.size()==0){
			throw new Exception("获取需要更新的cm_lun信息错误！sn:"+sn+"\nlunList:"+lunIdLists.toString());
		}
		storageDao.lunInfoBatchupdate(autoLunList);
	}
	
	public List<CmLun> buildAutoCmLunList(String sn,Date lunAssignTime) throws Exception {
		List<CmLun> list = new ArrayList<CmLun>();
		try{
			StorageDeviceVo vo = storageDao.findStorageDevBySn(sn);
			for (int i = 0; i < lunIdLists.size(); i++) {
				HashMap<String, String> params = Maps.newHashMap();
				params.put("storageId", vo.getStorageId());
				params.put("lunName", lunIdLists.get(i));
				params.put("lunStatus", StorageConstants.LUN_STATUS_OCCYPY);
				CmLun lun = storageDao.findLunList(params).get(0);
				lun.setLunStatus(StorageConstants.LUN_STATUS_USED);
				lun.setLunAssignTime(lunAssignTime);
				list.add(lun);
			}
		}catch(Exception e){
			throw new Exception("获取更新cm_lun表中lun状态异常！");
		}
		return list;
	}

	@Override
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
				this.setDeviceId(deviceIdList.get(0));
				if (deviceIdList == null || deviceIdList.size() == 0) {
					String errorMsg = "The Device List Of The rrinfoId [ "
							+ rrinfoId + " ] Is Null";
					throw new Exception(errorMsg);
				}

				IDataObject requestDataObject = buildRequestData(contextParams);

				IDataObject responseDataObject = getResAdpterInvoker().invoke(
						requestDataObject, getTimeOut());

				handleResonpse(contextParams, responseDataObject);

				saveParamInsts(flowInstId, nodeId);

			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:"
						+ nodeId+ ".错误信息:" + e.getMessage();
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
			rtn_map.put("exeMsg", result_sb.toString());
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "contextParams is null");
		}
		return JSON.toJSONString(rtn_map);
	}

	protected String getViewName(Map<String, Object> contenxtParmas) throws Exception {
		Object projectAbbreviationObj = contenxtParmas.get("PROJECTABBR");
		Object serverFunctionObj = contenxtParmas.get("SERVERFUNCTION");
		Object describeObj = contenxtParmas.get("DESCRIBE");
		if (projectAbbreviationObj == null || serverFunctionObj == null
				|| describeObj == null) {
			String errorMsg = "PROJECTABBR or SERVERFUNCTION or DESCRIBE is null!";
			throw new Exception(errorMsg);
		}
		String projectAbbreviation = String.valueOf(projectAbbreviationObj);
		String serverFunction = String.valueOf(serverFunctionObj);
		String describe = String.valueOf(describeObj);

		String viewName = projectAbbreviation + serverFunction + describe;

		return viewName;
	}

	public List<String> getLunIdsList(Map<String, Object> devMapInfo,
			List<String> typeList, String sn) {
		List<String> lunid_list = Lists.newArrayList();
		for (String type : typeList) {
			String lunInfo = String.valueOf(devMapInfo
					.get(sn + "_TYPE_" + type));
			JSONArray lun_array = JSONArray.parseArray(lunInfo);
			for (int i = 0; i < lun_array.size(); i++) {
				lunid_list.add(String.valueOf(lun_array.get(i)));
			}
		}
		return lunid_list;
	}

	public List<String> getPwwnList(Map<String, String> map) throws Exception {
		List<String> pwwnList = new ArrayList<String>();
		map.remove("STORAGE_SN");
		map.remove("GROUP_ID");
		Set<String> keys = map.keySet();
		for (String key : keys) {
			pwwnList.add(convertFormat(map.get(key)));
		}
		if (pwwnList.size() != 4) {
			String errorMsg = "The Size Of FA_WWN Is Not Four";
			throw new Exception(errorMsg);
		}
		log.debug("The storage pwwn:{}", pwwnList.toString());
		return pwwnList;
	}

	public Map<String, String> getfaPwwnBySN(Map<String, Object> devMapInfo,
			Map<String, String> cur_sn_map, String sn) throws Exception {
		String fapwwn = "";
		if (cur_sn_map.containsKey(StorageConstants.STORAGEONE)) {
			fapwwn = (String) devMapInfo.get(StorageConstants.FA_WWNONE);
		} else if (cur_sn_map.containsKey(StorageConstants.STORAGETWO)) {
			fapwwn = (String) devMapInfo.get(StorageConstants.FA_WWNTWO);
		}
		if(StringUtils.isBlank(fapwwn)){
			throw new Exception("获取sn:["+sn+"]fa wwn信息失败!");
		}

		Map<String, String> map = Maps.newHashMap();
		String[] array = null;
		if (fapwwn != null) {
			String portNamePwwnString = String.valueOf(fapwwn).trim();
			array = portNamePwwnString.substring(1,
					portNamePwwnString.length() - 1).split(",");
			for (int i = 0; i < array.length; i++) {
				String entry = array[i].trim();
				int index = entry.indexOf("=");
				String key = entry.substring(0, index);
				String value = entry.substring(index + 1);
				if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
					String errorMsg = "FA_WWN:" + fapwwn
							+ " Contain Null Value";
					throw new Exception(errorMsg);
				}
				map.put(key, value);
			}
		} else {
			String errorMsg = "FA_WWN in global table is null";
			throw new Exception(errorMsg);
		}
		if (map.containsValue(sn))
			return map;
		else
			return null;
	}


//	public void backupView(String sn, String suffix,
//			Map<String, Object> contenxtParmas) {
////		ModifyPassWordVo vo = new ModifyPassWordVo();
////		String ip = ParameterService.getParameter("JumpIp");
////		String userName = ParameterService.getParameter("JumpUserName");
////		String passWord = PwdUtil.decryption(ParameterService
////				.getParameter("JumpPassWord"));
////		vo.setScriptIp(ip);
////		vo.setScriptUserName(userName);
////		vo.setScriptPassWord(passWord);
//		//List<String> cmdList = new ArrayList<String>();
//		StringBuilder builder = new StringBuilder();
//		String basePath = ParameterService.getParameter("BasePath");
//		builder.append(basePath).append("/");
//		String file = ParameterService.getParameter("BackupFile");
//		builder.append(file);
//		builder.append(" --cmd_dir=\"");
//		builder.append(basePath);
//		builder.append("\" --host_loginInfo=\"");
//		HashMap<String, String> map = buildSuperUser(contenxtParmas);
//
//		builder.append(map.get("SUPER_SMISISERVER_IP")).append(" ");
//		builder.append(map.get("SUPER_USER_NAME")).append(" ");
//		builder.append(PwdUtil.decryption(map.get("SUPER_USER_PASSWD")));
//		builder.append("\" --sid=\"");
//		builder.append(sn);
//		builder.append("\" --basePath=\"");
//		builder.append(ParameterService.getParameter("BackupBasePath"));
//		builder.append("\" --file_name=\"");
//		builder.append(buildFileName(sn, suffix)).append("\"");
////		cmdList.add(builder.toString());
////		vo.setCmdList(cmdList);
////		SshExecShell util = new SshExecShell();
//		try {
//			//util.execShell(vo);
//			localExec(builder.toString());
//		} catch (Exception e) {
//			String errorMsg = "脚本返回结果出错:" + e.getMessage();
//			throw new Exception(errorMsg);
//		}
//	}

	public String buildFileName(String sn, String suffix) {
		StringBuilder builder = new StringBuilder("lunmask");
		builder.append(getCurrentStringDate()).append("-");
		builder.append(sn);
		builder.append(suffix);
		builder.append(".txt");
		String fileName = builder.toString();
		scriptNameList.add(fileName);
		return fileName;
	}
	/**
	 * @throws Exception 
	 * @Title: localExec
	 * @Description: 本地执行脚本命令
	 * @field: @param execStr
	 * @return void
	 * @throws
	 */
	public void localExec(String str) throws Exception{
		log.info("localExec:["+str+"]");
		String[] cmdarrays = new String[]{"/bin/sh","-c",str};
		BufferedReader error_br = null;
		BufferedReader br = null;
		try {
			Process process  = Runtime.getRuntime().exec(cmdarrays,null,null);
			error_br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String error_line = null;
			List<String> error_list = Lists.newArrayList();
			while((error_line=error_br.readLine())!=null){
				error_list.add(error_line);
			}
			if(!error_list.isEmpty()){
				throw new Exception("执行:["+str+"]\n错误信息："+error_list.toString());
			}
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line= null;
			List<String> line_list = Lists.newArrayList();
			while((line=br.readLine())!=null){
				line_list.add(line);
			}
			log.debug("执行：["+str+"]\n返回信息："+line_list.toString());
		} catch (Exception e) {
			log.error("异常exception",e);
			throw new Exception("执行指令错误["+Arrays.toString(cmdarrays)+"]"+e.getMessage(),e);
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					log.error("异常exception",e);
				}
			}
			if(error_br!=null){
				try {
					error_br.close();
				} catch (IOException e) {
					log.error("异常exception",e);
				}
			}
		}
	}
	protected HashMap<String, String> buildOsInfo(
			Map<String, Object> contenxtParmas) {
		HashMap<String, String> osInfoMap = new HashMap<String, String>();
		osInfoMap.put("OS_NAME", String.valueOf(contenxtParmas.get("OS_NAME")));
		osInfoMap.put("OS_VERSION",
				String.valueOf(contenxtParmas.get("OS_VERSION")));
		return osInfoMap;
	}
	protected List<String> convertVsanJson(Object hbaWwpnObj) throws Exception {
		List<String> list = new ArrayList<String>();
		log.debug("[HBA_WWP]:{}", hbaWwpnObj);
		if (hbaWwpnObj == null) {
			String errorMsg = "HBA_WWP is null value";
			throw new Exception(errorMsg);
		}
		String hbaWwpn = String.valueOf(hbaWwpnObj);

		JSONObject object;
		try {
			object = JSONObject.parseObject(hbaWwpn);
		} catch (Exception e) {
			String errorMsg = "convert HBA_WWN String into JSONObject Failed:"
					+ e.getMessage();
			throw new Exception(errorMsg);
		}
		Iterator<Entry<String, Object>> iterator = object.entrySet().iterator();
		Entry<String, Object> entry = null;
		String wwpn = null;
		while (iterator.hasNext()) {
			entry = iterator.next();
			wwpn = convertFormat(String.valueOf(entry.getValue())).trim();
			if (wwpn.length() != 16) {
				String errorMsg = "[ vm hba wwn ] :" + wwpn + " length error";
				throw new Exception(errorMsg);
			}
			list.add(String.valueOf(wwpn.toUpperCase()));
		}
//		if (list.size() != 4) {
//			String errorMsg = "HBA_WWPN do not contain four hba wwpn";
//			throw new Exception(errorMsg);
//		}
		return list;
	}
	public String convertFormat(String wwpn) {
		String noncolonWwpn = wwpn.trim().replace(":", "");
		return noncolonWwpn.toUpperCase();
	}
	protected String getCurrentStringDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.CHINA);
		String date = format.format(Calendar.getInstance().getTime());
		return date;
	}
}
