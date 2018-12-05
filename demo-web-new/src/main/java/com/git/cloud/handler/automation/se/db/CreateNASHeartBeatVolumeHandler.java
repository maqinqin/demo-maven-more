package com.git.cloud.handler.automation.se.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
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

public class CreateNASHeartBeatVolumeHandler extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(CreateNASHeartBeatVolumeHandler.class);
	private List<String> deviceIdList = Lists.newArrayList();

	private List<Map<String,Object>> list = Lists.newArrayList();
	private String qtree_name = "";
	private String vol_name = "";
	private StringBuffer rtn_sb = new StringBuffer();
	private String nas_ip_used ="";
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
		header.setOperation(SEOpration.CREATE_VOLUME);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		String deviceId = getDeviceId();

		Map<String, Object> deviceParams = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(deviceId));

		String choose_flag = (String)deviceParams.get(StorageConstants.RAC_NAS_CHOOSE_FINISHED);
		if(StringUtils.isBlank(choose_flag)){
			throw new Exception("未完成选择RAC NAS心跳盘存储操作！");
		}
		String finished = (String)deviceParams.get(StorageConstants.CREATE_HEARTBEAT_VOL_FINISHED);
		if(StringUtils.isNotBlank(finished)){
			throw new Exception("已完成RAC-NAS-HEARTBEAT创建！");
		}
		//通过NAS生产IP选择一个可用IP
		String nas_ips_str = (String)deviceParams.get(StorageConstants.NAS_NPS);
		if(StringUtils.isBlank(nas_ips_str)){
			throw new Exception("获取NAS联通性测试，返回可用NAS生产IP信息错误");
		}
		nas_ip_used = getNasNPUsed(nas_ips_str);
		// 获取数据中心
		String dcenter_ename = (String) deviceParams
				.get(StorageConstants.DATACENTER_ENAME);
		body.setString(StorageConstants.DATACENTER_ENAME, dcenter_ename);
		
		// STORAGE_MODEL_NETAPP_FAS
		//String storage_model = (String)deviceParams.get(StorageConstants.NAS_MODEL);
		body.setString("NAS_MODEL", "NETAPP FAS");
		
		//RAC_NAS_SN
		String nas_sn =(String)deviceParams.get(StorageConstants.RAC_NAS_SN);
		body.setString("NAS_SN", nas_sn);
		rtn_sb.append("NAS SN:"+nas_sn).append("\n");
		
		log.info("nas_sn:"+nas_sn);
		log.info("nwaddr_type_code:NM");
		log.info("vm_ms_type_code:4");
		//根据NAS设备sn号获取登陆名密码
//		Map<String,String> mgr_info = service.getNASMgrLoginInfo(nas_sn,
//				StorageConstants.IP_ADDR_TYPE_CODE_NM,
//				StorageConstants.IP_STORAGE_TYPE);
//		if(mgr_info==null||mgr_info.isEmpty()){
//			throw new Exception("获取存储sn:["+nas_sn+"] 管理机登陆信息失败！");
//		}
		StorageService storageService = (StorageService) WebApplicationManager.getBean("storageService");

		Map<String,String> mgr_info = storageService.getNASMgrLoginInfo(nas_sn);

		body.setString(StorageConstants.SERVICE_IP, mgr_info.get("SERVER_IP"));
		body.setString(StorageConstants.USERNAME, mgr_info.get("USER_NAME"));
		body.setString(StorageConstants.USERPASSWD, mgr_info.get("USER_PASSWD"));
		
		Map<String,Object> vol_info = getHeartBeatVolInfo(deviceParams);
		List<Map<String,Object>> vol_info_list = Lists.newArrayList();
		vol_info_list.add(vol_info);
		list.addAll(vol_info_list);
		System.out.println(JSONObject.toJSONString(list));
		body.setList("VOL_INFO", vol_info_list);
		log.debug(JSONObject.toJSONString(body));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}
	public Map<String,Object> getHeartBeatVolInfo(Map<String,Object> deviceParams) throws Exception{
		Map<String,Object> vol_info = Maps.newHashMap();
		//获取应用名称
		String projectAbbr = (String)deviceParams.get(StorageConstants.PROJECTABBR);

		//卷大小
		String vol_size = (String)deviceParams.get("NAS_HEARTBEAT_CAPACITY");
		if(StringUtils.isBlank(vol_size)){
			throw new Exception("获取[NAS_HEARTBEAT_CAPACITY]参数错误!");
		}
		vol_info.put("VOL_SIZE", vol_size);
		
		String cloud_service_type = (String)deviceParams.get(StorageConstants.CLOUD_SERVICE_TYPE);
		if(StringUtils.isBlank(cloud_service_type)){
			throw new Exception("获取[CLOUD_SERVICE_TYPE]参数错误！");
		}

		if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_STORAGE_INSTALL)){
			//获取主机挂载点
			String mountpoint = (String)deviceParams.get(StorageConstants.NAS_HEARTBEAT_MOUNTPOINT);
			
			//组装vol-name
			String[] point_array = mountpoint.split("/");
			String last_point = point_array[point_array.length-1];
			vol_name = projectAbbr+"_"+last_point;
			vol_info.put("VOL_NAME", vol_name);
					
			//qtree info
			qtree_name = last_point;
		}else if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_VM_INSTALL)){
			//虚拟机数据RAC安装提供NAS心跳盘
			String db_name = (String)deviceParams.get(StorageConstants.DB_NAME);
			if(StringUtils.isBlank(db_name)){
				throw new Exception("获取[DB_NAME]参数信息错误！");
			}
			vol_name = StorageConstants.RAC_NAS_PR+"_"+projectAbbr+"_"+db_name;
			vol_info.put("VOL_NAME", vol_name);
			qtree_name = vol_name;
		}else {
			throw new Exception("未知的服务请求类型！");
		}
		
		String qtree_mode = StorageConstants.QTREE_MODE;
		Map<String,String> qtree_info_map = Maps.newHashMap();
		qtree_info_map.put("QTREE_NAME", qtree_name);
		qtree_info_map.put("QTREE_MODE", qtree_mode);
		List<Map<String,String>> qtree_info_list = Lists.newArrayList();
		qtree_info_list.add(qtree_info_map);
		
		vol_info.put("QTREES", qtree_info_list);
		

		//pool_name
		String vol_pool_str = (String)deviceParams.get(StorageConstants.RAC_NAS_VOL_POOL);
		JSONObject vol_pool_map = JSONObject.parseObject(vol_pool_str);
		String pool_name = (String)vol_pool_map.get(vol_name);
		if(StringUtils.isBlank(pool_name)){
			throw new Exception("获取VOL_NAME:"+vol_name+"所在POOL信息失败！");
		}
		vol_info.put("POOL_NAME", pool_name);
		
		return vol_info;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {
		
		log.info(JSONObject.toJSONString(responseDataObject));
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		log.info(header.getRetMesg());
		
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}
		for(Map<String,Object> map : list){
			rtn_sb.append("VOL_NAME:").append(map.get("VOL_NAME").toString()).append("\n");
			rtn_sb.append("POOL_NAME:").append(map.get("POOL_NAME").toString()).append("\n");
			rtn_sb.append("QTREES:").append(map.get("QTREES").toString()).append("\n");
			rtn_sb.append("生产IP:").append(nas_ip_used).append("\n");
			rtn_sb.append("NAS_PATH:").append(nas_ip_used+":/vol/"+vol_name+"/"+qtree_name).append("\n");
		}
		for (int i = 0; i < deviceIdList.size(); i++) {
			String deviceId = String.valueOf(deviceIdList.get(i));
			setHandleResultParam(deviceId, StorageConstants.CREATE_HEARTBEAT_VOL_INFO, JSONObject.toJSONString(list));
			setHandleResultParam(deviceId, StorageConstants.CREATE_HEARTBEAT_QTREE_NAME, qtree_name);
			setHandleResultParam(deviceId, StorageConstants.CREATE_HEARTBEAT_VOL_FINISHED, "FINISHED");			
			setHandleResultParam(deviceId, StorageConstants.NAS_NP_USED, nas_ip_used);
			//127.0.0.1:/vol/volName/qtreeName
			setHandleResultParam(deviceId, StorageConstants.NAS_PATH, nas_ip_used+":/vol/"+vol_name+"/"+qtree_name);
			setHandleResultParam(deviceId, StorageConstants.NAS_VIP, nas_ip_used);
			setHandleResultParam(deviceId, StorageConstants.NAS_FILESYSTEM, "/vol/"+vol_name+"/"+qtree_name);
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
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null!");	
		}
		return JSON.toJSONString(rtn_map);
	}
	public String getNasNPUsed(String nas_ips_str){
		log.info("获取可用存储生产ip信息："+nas_ips_str);
		String[] array = nas_ips_str.split(",");
		List<String> ip_list = Lists.newArrayList();
		for(String str : array){
			if(StringUtils.isNotBlank(str)){
				ip_list.add(str);
			}
		}
		Random r = new Random();
		int index = r.nextInt(ip_list.size());
		log.info("获取随机数索引："+index);
		String ip = ip_list.get(index);
		log.info("使用NAS存储生产IP:"+ip);
		return ip;
	}
}
