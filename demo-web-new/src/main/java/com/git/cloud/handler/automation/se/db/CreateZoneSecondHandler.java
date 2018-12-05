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
import com.git.cloud.handler.automation.se.common.SshExecShell;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.po.CmSwitchPo;
import com.git.cloud.handler.automation.se.po.FabricPo;
import com.git.cloud.handler.automation.se.po.StorageMgrPo;
import com.git.cloud.handler.automation.se.po.VsanPo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.parame.service.ParameterService;
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

public class CreateZoneSecondHandler  extends RemoteAbstractAutomationHandler{

	private static Logger log = LoggerFactory.getLogger(CreateZoneSecondHandler.class);
	private List<String> deviceIdList = new ArrayList<String>();
	private List<String> scriptNameList = Lists.newArrayList();
	private HashMap<String, String> coreSwitchMap;
	private String fabricName;
	private String fabricId;
	private String fabric_key_name = "FABRIC2";;
	private String switch_type;
	private String fid;
	private String coreSwitch;
	private Map<String,String> fabric_vsan_map = Maps.newHashMap();
	private String dc_ename = "";//数据中心英文名
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");
	private StringBuffer result_sb = new StringBuffer();
	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
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
		HashMap<String,Object> devMap = (HashMap<String,Object>)contenxtParmas.get(String.valueOf(deviceIdList.get(0)));
		String resource_type = (String)devMap.get(StorageConstants.RESOURCE_TYPE_SWITCH);
		if(StringUtils.isBlank(resource_type)){
			throw new Exception("获取[RESOURCE_TYPE_SWITCH]参数错误！");
		}
		dc_ename = (String)devMap.get(StorageConstants.DATACENTER_ENAME);
		if(StringUtils.isBlank(dc_ename)){
			throw new Exception("获取[DATACENTER_ENAME]参数错误！");
		}
		header.setResourceType(resource_type);
		header.setOperation(SEOpration.CREATEZONE);
		reqData.setDataObject(MesgFlds.HEADER, header);
		
		BodyDO body = BodyDO.CreateBodyDO();

		//checkFabricFinished(contextParams);
		//backup switch status
		coreSwitchMap = new HashMap<String, String>();
		if(switch_type.equals(StorageConstants.SWITCH_TYPE_CISCO)){
//			coreSwitch = getCoreSwitchName(devMap);
			CmSwitchPo cmSwitch = this.getCoreSwitch(devMap);
			coreSwitch = cmSwitch.getSwitchName();
			coreSwitchMap.put("NAME", coreSwitch);
			coreSwitchMap.put("IP", cmSwitch.getIp());
			//uploadCiscoFile(StorageConstants.SWITCH_CISCO_POSTFIX_ORIGINAL, coreSwitch);
		}else if(switch_type.equals(StorageConstants.SWITCH_TYPE_BROCADE)){
//			coreSwitch = getCoreSwitchName(devMap);
			CmSwitchPo cmSwitch = this.getCoreSwitch(devMap);
			coreSwitch = cmSwitch.getSwitchName();
			coreSwitchMap.put("NAME", coreSwitch);
			coreSwitchMap.put("IP", cmSwitch.getIp());
			fid = getFid(devMap);
			//uploadBrocadeFile(StorageConstants.SWITCH_ORIGINAL,coreSwitch,fid);
		}
		
		String unitInfo_str = devMap.get(StorageConstants.UNIT_INFO).toString();
		if(StringUtils.isBlank(unitInfo_str)){
			throw new Exception("获取[UNIT_INFO]参数错误！");
		}
		JSONObject unitInfo = JSONObject.parseObject(unitInfo_str);
		fabricId = unitInfo.getString(fabric_key_name+"_ID");

		StorageMgrVo storageMgrVo = storageDao.findStorageMgrInfoByFabric(fabricId);

		body.setString(StorageConstants.SERVICE_IP, storageMgrVo.getManagerIp());
		body.setString(StorageConstants.SERVICE_PORT, storageMgrVo.getPort());
		body.setString(StorageConstants.USERNAME, storageMgrVo.getUserName());
		body.setString(StorageConstants.USERPASSWD, storageMgrVo.getPassword());
		body.setString(StorageConstants.NAMESPACE, storageMgrVo.getNamespace());
		body.setHashMap(StorageConstants.CORE_SWITCH, coreSwitchMap);
		body.setString(StorageConstants.SWITCH_TYPE, switch_type);
		
		//result_sb.append("-------Creat First Zone Fabric:"+po.getFabricName()+"-------").append("\n");
		List<HashMap<String, Object>> zones = Lists.newArrayList();
		if(switch_type.equals(StorageConstants.SWITCH_TYPE_CISCO)){
			zones = buildCiscoZones(contenxtParmas);
			body.setList("ZONEINFOS", zones);
			List<Map<String, String>>  vsan_zoneset_list = getActiveZoneSet();
			body.setList("ACTIVE_ZONESET", vsan_zoneset_list);
		}else if(switch_type.equals(StorageConstants.SWITCH_TYPE_BROCADE)){
			zones = buildBrocadeZones(contenxtParmas);
			body.setList("VFINFOS", zones);
		}
		log.info(zones.toString());
		reqData.setDataObject(MesgFlds.BODY, body);
		log.info("REQ CREATE ZONE DATA:"+JSONObject.toJSONString(reqData));
		return reqData;
	}
	
	

	@SuppressWarnings("unchecked")
	private String getFid(HashMap<String, Object> devMap) {
		log.info("fabric_key_name:"+fabric_key_name);
		String fabric_str = (String)devMap.get(fabric_key_name);
		log.info("fabric_str:"+fabric_str);
		JSONArray array = JSONObject.parseArray(fabric_str);
		Map<String,Object> map = (Map<String,Object>)array.get(0);
		String vf_str = (String)map.get("vsanID");
		String[] vfname_array = vf_str.split("-");
		String fid = vfname_array[vfname_array.length-1];
		return fid;
	}



	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {
		
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		log.info(header.getRetMesg());
		
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}
		for (int i = 0; i < deviceIdList.size(); i++) {
			String deviceId = String.valueOf(deviceIdList.get(i));
			setHandleResultParam(deviceId, StorageConstants.DB_CRETAE_ZONE_SECOND_FINISHED, "FINISHED");
		}
		result_sb.append("FABRICFINISHED："+"FABRIC2").append("\n");
		if(switch_type.equals(StorageConstants.SWITCH_TYPE_CISCO)){
		//Any One Switch ShellCmd
			//uploadCiscoFile("", coreSwitch);
		
		//back up switch status in fabric after creating zone finished
			//uploadCiscoFile("-Final", coreSwitch);
		}else if(switch_type.equals(StorageConstants.SWITCH_TYPE_BROCADE)){
			//uploadBrocadeFile(StorageConstants.SWITCH_FINAL,coreSwitch,fid);
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute(HashMap<String, Object> contextParams) {
		Map<String,String> rtn_map = Maps.newHashMap();
		if(contextParams!=null){
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			String nodeId = (String) contextParams.get(NODE_ID);
			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			try {
				Map<String,Map<String, String>> handleParams = this.getHandleParams(flowInstId);

				contextParams.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contextParams);

				if (extHandleParams != null)
					contextParams.putAll(extHandleParams);
				
				String rrinfoId = String.valueOf(contextParams.get(RRINFO_ID));
				
				deviceIdList = getDeviceIdList("",nodeId,rrinfoId,"");
				if (deviceIdList == null || deviceIdList.size() == 0) {
					String errorMsg = "The Device List Of The rrinfoId [ " + rrinfoId + " ] Is Null";
					throw new Exception(errorMsg);
				}		
				Map<String, Object> deviceInfoMap = (Map<String, Object>)contextParams.get(String.valueOf(deviceIdList.get(0)));

				//获取zone信息完成标志
				String generate_zone_flag = (String)deviceInfoMap.get(StorageConstants.DB_GENERATE_ZONE_FINISHED);
				if(StringUtils.isBlank(generate_zone_flag)){
					throw new Exception("未完成获取zone信息操作");
				}
				//获取创建第二个zone完成标志
				String createSecond_zone_flag = (String)deviceInfoMap.get(StorageConstants.DB_CRETAE_ZONE_SECOND_FINISHED);
				if(StringUtils.isNotBlank(createSecond_zone_flag)){
					throw new Exception("已完成FABRIC2创建zone信息操作！");
				}
				//fabric_key_name = getCreateFabricKey(contextParams);
				// 获取交换机类型信息
				String unitInfo_str = deviceInfoMap.get(StorageConstants.UNIT_INFO).toString();
				if(StringUtils.isBlank(unitInfo_str)){
					throw new Exception("获取[UNIT_INFO]参数错误！");
				}
				JSONObject unitInfo = JSONObject.parseObject(unitInfo_str);
				switch_type = String
						.valueOf(unitInfo.get(StorageConstants.SWITCH_TYPE));
				if(StringUtils.isBlank(switch_type)){
					throw new Exception("获取[SWITCH_TYPE]参数错误!");
				}
				
				IDataObject requestDataObject = buildRequestData(contextParams);
				
				IDataObject responseDataObject = getResAdpterInvoker().invoke(
						requestDataObject, getTimeOut());
				
				handleResonpse(contextParams, responseDataObject);
				
				String scriptName = JSONObject.toJSONString(scriptNameList);

				Calendar c = Calendar.getInstance();
				Date d = c.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map<String,String> createtime_map = Maps.newHashMap();//fabricName
				createtime_map.put(fabricName, sdf.format(d));
				String create_time_str = JSONObject.toJSONString(createtime_map);
				
				for (int i = 0; i < deviceIdList.size(); i++) {
					String deviceId = String.valueOf(deviceIdList.get(i));
					setHandleResultParam(deviceId, "ZONE2_SCRIPT_FILE", scriptName);
					setHandleResultParam(deviceId, "FABRIC2_CREATE_TIME", create_time_str);
				}
				result_sb.append("ZONE2_SCRIPT_FILE:"+scriptName.toString()).append("\n");
		        //back up switch slot0 status after creating zone finished
//		        updateSwitchSlotStatus();
		        
				saveParamInsts(flowInstId, nodeId);
			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:" + nodeId+".错误信息:" + e.getMessage();
				log.error(errorMsg, e);
				rtn_map .put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
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
	
	private CmSwitchPo getCoreSwitch(Map<String, Object> deviceInfoMap) throws Exception {
		FabricPo po = null;
		Object fabricObject = deviceInfoMap.get(fabric_key_name);
		if (fabricObject == null) {
			String errorMsg = fabric_key_name+" do not exist in global param table";
			throw new Exception(errorMsg);
		}
		JSONArray zoneArray = null;
		try {
			zoneArray = JSONObject.parseArray(String.valueOf(fabricObject));
			JSONObject jsonObj = zoneArray.getJSONObject(0);
			String fabricName = jsonObj.getString("fbcName");
			po = storageDao.findFabricPoByName(fabricName);
		} catch (Exception e) {
			String errorMsg = "Convert FABRIC2 " + fabricObject + " From JSONObject Failed " + e.toString();
			throw new Exception(errorMsg);
		}
		//CmSwitchDao dao = (CmSwitchDao)FrameworkContext.getApplicationContext().getBean("cmSwitchDao");
		HashMap<String, String> params = Maps.newHashMap();
		params.put("fabricId", po.getFabricId());
		params.put("isCore", "Y");
		List<CmSwitchPo> switchPoList = storageDao.findSwitchList(params);
		if (switchPoList == null || switchPoList.size() == 0) {
			throw new Exception("Get core switch Failed");
		}
		CmSwitchPo cmSwitch = switchPoList.get(0);
//		String coreSwitch = switchPoList.get(0).getSwitchName();
		log.debug("The core switch name is:{}", switchPoList.get(0).getSwitchName());
		return cmSwitch;
	}
	
	@SuppressWarnings("unchecked")
	private List<HashMap<String, Object>> buildCiscoZones(Map<String, Object> contenxtParmas) throws Exception {
		List<HashMap<String, Object>> zones = new ArrayList<HashMap<String,Object>>();
		for (int j = 0; j < deviceIdList.size(); j++) {
			
			Map<String, Object> deviceInfoMap = (Map<String, Object>)contenxtParmas.get(String.valueOf(deviceIdList.get(j)));
			Object fabricObject = deviceInfoMap.get(fabric_key_name);
			if (fabricObject == null) {
				String errorMsg = "FABRIC2 do not exist in global param table";
				throw new Exception(errorMsg);
			}
			JSONArray zoneArray = null;
			try {
				zoneArray = JSONObject.parseArray(String.valueOf(fabricObject));
			} catch (Exception e) {
				String errorMsg = "Convert FABRIC2 " + fabricObject + " From JSONObject Failed " + e.toString();
				throw new Exception(errorMsg);
			}
			
			int size = zoneArray.size();
			for (int i = 0; i < size; i++) {
				JSONObject zoneObj = zoneArray.getJSONObject(i);
				HashMap<String, Object> zone = new HashMap<String, Object>();
				String name = zoneObj.getString("name");
				fabricName = zoneObj.getString("fbcName");
				String vsanID = zoneObj.getString("vsanID");
				
				JSONArray memberArray = zoneObj.getJSONArray("members");
				String wwpn = memberArray.getString(0);
				String pwwn = memberArray.getString(1);
				
				if ("".equals(name) || "".equals(fabricName) || "".equals(vsanID) || "".equals(wwpn) || "".equals(pwwn)) {
					String errorMsg = "FABRIC2:" + fabricObject + " Contain Null Value";
					throw new Exception(errorMsg);
				}
				List<String> members = Arrays.asList(new String[]{wwpn, pwwn});
				
				zone.put("name", name);
				zone.put("fbcName", fabricName);
				zone.put("vsanID", vsanID);
				zone.put("members", members);
				fabric_vsan_map.put(fabricName, vsanID);
				zones.add(zone);
				result_sb.append("vsanID:"+vsanID+"		zoneName:"+name).append("\n");
				//result_sb.append("vsanID:"+vsanID).append("\n");
				result_sb.append("members:"+members.toString()).append("\n");
			}
		}
		return zones;
	}
	public List<Map<String,String>> getActiveZoneSet() throws Exception{
		
		//根据fabricName获取vsan相关信息
		List<Map<String,String>> vsan_list = Lists.newArrayList();
		List<VsanPo> vsanPoList = Lists.newArrayList();
		String fabricId = storageDao.findFabricPoByName(fabricName).getFabricId();
		if(StringUtils.isEmpty(fabricId)){
			throw new Exception("find fabricName:"+fabricName+" the fiabricId error!");
		}else{
			vsanPoList = storageDao.findVsanInfoByFabircId(fabricId);
			for(VsanPo po :vsanPoList){
				Map<String,String> map = Maps.newHashMap();
				map.put("VSAN_ID", po.getId());
				map.put("ZONESET_NAME", po.getActiveZoneset());
				vsan_list.add(map);
			}
		}
		if(vsan_list.isEmpty()){
			throw new Exception("fabricName:"+fabricName+"find vsan info list by fabricId:"+fabricId+"error!");
		}
		String vsan = fabric_vsan_map.get(fabricName);
		List<Map<String,String>> list = Lists.newArrayList();
		for(Map<String,String> m : vsan_list){
			if(m.get("VSAN_ID").equals(vsan)){
				list.add(m);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<HashMap<String, Object>> buildBrocadeZones(Map<String, Object> contenxtParmas) throws Exception{

		List<HashMap<String,Object>> vfInfos = Lists.newArrayList();
		for (int j = 0; j < deviceIdList.size(); j++) {

			List<HashMap<String, Object>> zones = new ArrayList<HashMap<String,Object>>();
			Map<String, Object> deviceInfoMap = (Map<String, Object>)contenxtParmas.get(String.valueOf(deviceIdList.get(j)));
			Object fabricObject = deviceInfoMap.get(fabric_key_name);
			if (fabricObject == null) {
				String errorMsg = "FABRIC2 do not exist in global param table";
				throw new Exception(errorMsg);
			}
			JSONArray zoneArray = null;
			try {
				zoneArray = JSONObject.parseArray(String.valueOf(fabricObject));
			} catch (Exception e) {
				String errorMsg = "Convert FABRIC2 " + fabricObject + " From JSONObject Failed " + e.toString();
				throw new Exception(errorMsg);
			}
			
			int size = zoneArray.size();
			for (int i = 0; i < size; i++) {
				JSONObject zoneObj = zoneArray.getJSONObject(i);
				HashMap<String, Object> zone = new HashMap<String, Object>();
				String name = zoneObj.getString("name");
				fabricName = zoneObj.getString("fbcName");
				String vf_name = zoneObj.getString("vsanID");
				
				JSONArray memberArray = zoneObj.getJSONArray("members");
				String wwpn = memberArray.getString(0);
				String pwwn = memberArray.getString(1);
				
				if ("".equals(name) || "".equals(fabricName) || "".equals(vf_name) || "".equals(wwpn) || "".equals(pwwn)) {
					String errorMsg = "FABRIC2:" + fabricObject + " Contain Null Value";
					throw new Exception(errorMsg);
				}
				List<String> members = Arrays.asList(new String[]{wwpn, pwwn});
				
				zone.put("NAME", name);
				zone.put("fbcName", fabricName);
				zone.put("VFNAME", vf_name);
				zone.put("MEMBERS", members);
				FabricPo fabricPo = storageDao.findFabricPoByName(fabricName);

				Map<String,String> paramMap = Maps.newHashMap();
				paramMap.put("fabricId", fabricPo.getFabricId());
				paramMap.put("vsanNam", vf_name);
				String active_zoneset = storageDao.findVsanPoByParams(paramMap).getActiveZoneset();
				if(StringUtils.isBlank(active_zoneset)){
					throw new Exception("获取 vf["+vf_name+"] ACTIVE_ZONESET 信息错误！");
				}
				zone.put("ACTIVE_ZONESET", active_zoneset);
				zones.add(zone);
				result_sb.append("VF_NAME:"+vf_name+" zoneName:"+name).append("\n");
				//result_sb.append("vsanID:"+vsanID).append("\n");
				result_sb.append("members:"+members.toString()).append("\n");
			}
			Map<String,List<Map<String,Object>>> map = Maps.newHashMap();
			for(HashMap<String, Object> zone:zones){
				String vfName = zone.get("VFNAME").toString();
				zone.remove("VFNAME");
				if(map.containsKey(vfName)){
					map.get(vfName).add(zone);
				}else{
					List<Map<String,Object>> list = Lists.newArrayList();
					list.add(zone);
					map.put(vfName, list);
				}
			}
			for(Iterator<Entry<String,List<Map<String,Object>>>> itr = map.entrySet().iterator();itr.hasNext();){
				HashMap<String,Object> vfInfo_map = Maps.newHashMap();
				Entry<String,List<Map<String,Object>>> entry = itr.next();
				String vfName = entry.getKey();
				vfInfo_map.put("VFNAME", vfName);
				List<Map<String,Object>> zoneInfos = entry.getValue();
				vfInfo_map.put("ZONEINFOS", zoneInfos);
				vfInfos.add(vfInfo_map);
			}
		}
		return vfInfos;
	}
	/**
	 * back up any one switch status in fabric
	 * @param status
	 * @param coreSwitch
	 
	public void uploadCiscoFile(String status, String coreSwitch) {
		ModifyPassWordVo vo = fabricParamsDao.findSwitchBySql(coreSwitch);
		coreSwitchMap = new HashMap<String, String>();
		coreSwitchMap.put("NAME", coreSwitch);
		coreSwitchMap.put("IP", vo.getSwitchIp());
		
		List<String> list = new ArrayList<String>();

		if (!"".equalsIgnoreCase(status)) {
			String fileName = buildFileName(coreSwitch, status);
			scriptNameList.add(fileName);
			list = buildCmdList(vo, fileName);
		} else {
			list = buildConfigCmdList(vo);
		}
		try {
			this.localExec(list.get(0));
		} catch (Exception e) {
			String errorMsg = "Back Up Switch Status Error:" + e.toString();
			throw new Exception(errorMsg);
		}
	}
	*/
//	public void uploadBrocadeFile(String postfix,String coreSwitch,String fid){
//		ModifyPassWordVo vo = fabricParamsDao.findSwitchBySql(coreSwitch);
//		coreSwitchMap = new HashMap<String, String>();
//		coreSwitchMap.put("NAME", coreSwitch);
//		coreSwitchMap.put("IP", vo.getSwitchIp());
//		List<String> list = new ArrayList<String>();
//		String fileAllName = "";
//		String fileFidName = "";
//		if(postfix.equals(StorageConstants.SWITCH_ORIGINAL)){
//			fileAllName = buildBrocadeFileName(coreSwitch,StorageConstants.SWITCH_BROCADE_ALL_POSTFIX_ORIGINAL);
//			fileFidName = buildBrocadeFileName(coreSwitch,StorageConstants.SWITCH_BROCADE_FID_POSTFIX_ORIGINAL);
//		}else if(postfix.equals(StorageConstants.SWITCH_FINAL)){
//			fileAllName = buildBrocadeFileName(coreSwitch,StorageConstants.SWITCH_BROCADE_ALL_POSTFIX_FINAL);
//			fileFidName = buildBrocadeFileName(coreSwitch,StorageConstants.SWITCH_BROCADE_FID_POSTFIX_FINAL);
//		}
//		String ftpIp = ParameterService.getParameter(dc_ename+"_"+"BrocadeFtpJumpIp");
//		String ftpUser = ParameterService.getParameter(dc_ename+"_"+"BrocadeFtpJumpUserName");
//		String ftpPwd =  PwdUtil.decryption(ParameterService.getParameter(dc_ename+"_"+"BrocadeFtpJumpPassWord"));
//		String filePath = ParameterService.getParameter(dc_ename+"_"+"BrocadeFtpJumpDir");
//		String _cmd_all = ParameterService.getParameter("BROCADE_SWITCH_ALL_UPLOAD");
//		String _cmd_all_value = ftpIp+","+ftpUser+","+filePath+fileAllName+","+ftpPwd;
//		String cmd_all = this.generateCmd(_cmd_all,Arrays.asList(new String[]{_cmd_all_value}));
//		
//		String _cmd_fid = ParameterService.getParameter("BROCADE_SWITCH_FID_UPLOAD");
//		String _cmd_fid_value = ftpIp+","+ftpUser+","+filePath+fileFidName+","+ftpPwd;
//		String cmd_fid = this.generateCmd(_cmd_fid, Arrays.asList(new String[]{fid,_cmd_fid_value}));
//		
//		list.add(cmd_all);
//		list.add(cmd_fid);
//		vo.setCmdList(list);
//		SshExecShell util = new SshExecShell();
//		try {
//			util.execShell(vo);
//		} catch (Exception e) {
//			String errorMsg = "Back Up Switch Status Error:" + e.toString();
//			throw new Exception(errorMsg);
//		}
//		scriptNameList.add(fileAllName);
//		scriptNameList.add(fileFidName);
//	}
	
	private String buildFileName(String coreSwitch, String suffix) {
		StringBuilder builder = new StringBuilder();
		String date = getCurrentStringDate();
		builder.append(coreSwitch);
		builder.append("RUNCFG");
		builder.append(date);
		builder.append(suffix);
		return builder.toString();
	}
	private String buildBrocadeFileName(String coreSwitch,String postfix){
		StringBuffer sb = new StringBuffer();
		String date = getCurrentStringDate();
		sb.append(coreSwitch);
		sb.append(date);
		sb.append(postfix);
		return sb.toString();
	}

//	private List<String> buildCmdList(ModifyPassWordVo vo, String fileName) {
//		List<String> cmdList = new ArrayList<String>();
//		StringBuilder builder = new StringBuilder();
//		String basePash = ParameterService.getParameter("BasePath");
//		builder.append(basePash);
//		builder.append("/");
//		builder.append(ParameterService.getParameter("UpdateSwitchFtp"));
//		builder.append(" ").append("--cmd_dir=\"");
//		builder.append(basePash).append("\"");
//		builder.append(" --switch_loginInfo=\"");
//		builder.append(vo.getSwitchIp()).append(" ");
//		builder.append(vo.getSwitchName()).append(" ");
//		builder.append(vo.getPassWord()).append("\"");
//		builder.append(" --ftp_path=\"ftp://");
//		builder.append(ParameterService.getParameter(dc_ename+"_"+"FtpJumpIp"));
//		builder.append(ParameterService.getParameter(dc_ename+"_"+"FtpJumpDir"));
//		
//		builder.append(fileName);
//		
//		builder.append("\" --ftp_connInfo=\"");
//		builder.append(ParameterService.getParameter(dc_ename+"_"+"FtpJumpUserName"));
//		builder.append(" ");
//		String passWord = PwdUtil.decryption(ParameterService.getParameter(dc_ename+"_"+"FtpJumpPassWord"));
//		builder.append(passWord);
//		builder.append("\"");
//		cmdList.add(builder.toString());
//		log.info(fileName+"：["+builder.toString()+"]");
//		return cmdList;
//	}
//	
//	private List<String> buildConfigCmdList(ModifyPassWordVo vo) {
//		List<String> cmdList = new ArrayList<String>();
//		StringBuilder builder = new StringBuilder();
//		String basePath = ParameterService.getParameter("BasePath");
//		builder.append(basePath);
//		builder.append("/");
//		builder.append(ParameterService.getParameter("UpdateSwitchCfg"));
//		builder.append(" ").append("--cmd_dir=\"");
//		builder.append(basePath).append("\"");
//		builder.append(" --switch_loginInfo=\"");
//		builder.append(vo.getSwitchIp()).append(" ");
//		builder.append(vo.getSwitchName()).append(" ");
//		builder.append(vo.getPassWord()).append("\"");		
//		cmdList.add(builder.toString());
//		return cmdList;
//	}
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
	public String generateCmd(String _cmd, List<String> values) throws Exception {
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
		return cmd;
	}
	protected String getCurrentStringDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.CHINA);
		String date = format.format(Calendar.getInstance().getTime());
		return date;
	}
}
