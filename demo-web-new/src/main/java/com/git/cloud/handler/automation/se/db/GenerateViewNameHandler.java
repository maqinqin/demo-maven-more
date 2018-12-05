package com.git.cloud.handler.automation.se.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.SEOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GenerateViewNameHandler  extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(GenerateViewNameHandler.class);



	private StringBuffer rtn_sb = new StringBuffer();
	private String base_viewName = "";
	private String deviceId;
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");

	@SuppressWarnings("unchecked")
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {

		Map<String, Object> deviceParams = (HashMap<String, Object>) contenxtParmas
				.get(String.valueOf(getDeviceId()));

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
		String resource_type = deviceParams.get(StorageConstants.RESOURCE_TYPE_STORAGE).toString();
		if(StringUtils.isBlank(resource_type)){
			throw new Exception("获取参数[RESOURCE_TYPE_STORAGE]错误！");
		}
		header.setResourceType(resource_type);
		header.setOperation(SEOpration.GET_VIEW_NAME);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();

		// 添加数据中心
		String datecenter = (String) deviceParams
				.get(StorageConstants.DATACENTER_ENAME);
		if(StringUtils.isBlank(datecenter)){
			throw new Exception("获取[DATACENTER]信息失败!");
		}
		body.setString(StorageConstants.DATACENTER_ENAME, datecenter);
		// 存储MODEL
		String storage_model = (String) deviceParams
				.get(StorageConstants.STORAGE_MODEL);
		if(StringUtils.isBlank(storage_model)){
			throw new Exception("获取[STORAGE_MODEL]信息失败!");
		}
		body.setString(StorageConstants.STORAGE_MODEL, storage_model);
		// 获取存储登陆信息
		String su_id = (String) deviceParams.get(StorageConstants.SU_ID);
		if(StringUtils.isBlank(su_id)){
			throw new Exception("获取[SU_ID]信息失败!");
		}
		// 获取存储管理机登陆信息
//		List<Map<String, ?>> mgr_list_map = suDao.findStorageMgrInfo(Long
//				.parseLong(su_id));

		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoBySuId(su_id);
		
		body.setString(StorageConstants.SERVICE_IP,mgrVo.getManagerIp());
		body.setString(StorageConstants.USERNAME,mgrVo.getUserName());
		body.setString(StorageConstants.USERPASSWD,mgrVo.getPassword());
		body.setString(StorageConstants.NAMESPACE, mgrVo.getNamespace());

		// 获取存储sn列表
		String sn_list_str = (String)deviceParams.get(StorageConstants.SN);
		List<String> sn_list = JSONArray.parseArray(sn_list_str, String.class);
		body.setList(StorageConstants.SN,sn_list);
		
		// 获取基本view name
		String projectabbr = deviceParams.get(StorageConstants.PROJECTABBR).toString();
		String serverfunction =deviceParams.get(StorageConstants.SERVERFUNCTION).toString();
		String describe = deviceParams.get(StorageConstants.DESCRIBE).toString();
		base_viewName = projectabbr+serverfunction+describe;
		log.info("BASE_VIEW 名称：["+base_viewName+"]");
		body.setString(StorageConstants.VIEW_NAME, base_viewName);


		reqData.setDataObject(MesgFlds.BODY, body);
		log.debug("获取存储VIEW NAME请求信息：" + JSONObject.toJSONString(reqData));
		return reqData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {
		
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		log.info(header.getRetMesg());
		log.info(JSONObject.toJSONString(responseDataObject));
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}

		BodyDO body = responseDataObject.getDataObject(MesgFlds.BODY,
				BodyDO.class);
		
		String rrinfoId = (String) contenxtParmas.get(RRINFO_ID);
		List<String> deviceIdList = getDeviceIdList("","",rrinfoId,"");
		
		List<String> sn_list =body.getList("SN");
		if(sn_list.isEmpty()){
			throw new Exception("获取返回存储sn信息失败！");
		}
		
		HashMap<String,List<String>> viewName_map = body.getHashMap("VIEW_NAME");
		if(viewName_map.isEmpty()){
			throw new Exception("获取view name/host group信息失败！");
		}
		List<String> all_viewName_list = Lists.newArrayList();
		for(String sn :sn_list){
			if(viewName_map.containsKey(sn)){
				all_viewName_list.addAll(viewName_map.get(sn));
			}else{
				throw new Exception("存储sn："+sn+"VIEW NAME 获取失败！");
			}
		}
		int view_num = 0;
		for(String viewName:all_viewName_list){
			if(viewName.contains(base_viewName)){
				String v = viewName.replaceFirst(base_viewName, "");
				if(StringUtils.isBlank(v)){
					continue;
				}
				int i = Integer.parseInt(v);
				if(view_num < i){
					view_num = i;
				}
			}
		}
		String view_num_str = "";
		if(view_num==0){
			view_num_str = view_num+"1";
		}else if(view_num>0&&view_num<9){
			view_num_str = "0"+view_num;
		}else {
			view_num_str = String.valueOf(view_num+1);
		}
		String view_name = base_viewName+ view_num_str;
		for (int i = 0; i < deviceIdList.size(); i++) {
			String deviceId = String.valueOf(deviceIdList.get(i));
			setHandleResultParam(deviceId, "VIEW_NAME", view_name);
			setHandleResultParam(deviceId, StorageConstants.DB_GENERATE_VIEW_NAME_FINISHED, "FINISHED");
		}
		String storage_model = body.getString(StorageConstants.STORAGE_MODEL);
		if(storage_model.equals(StorageConstants.STORAGE_MODEL_VMAX)){
			rtn_sb.append("获取view name信息：\n");
		}else if(storage_model.equals(StorageConstants.STORAGE_MODEL_VSP)){
			rtn_sb.append("获取host group信息：\n");
		}
		for(String sn: sn_list){
			rtn_sb.append(sn).append(":").append(view_name).append("\n");
		}
	}

	@Override
	public String execute(HashMap<String, Object> contenxtParmas) {
		// result = null;
		Map<String, String> rtn_map = Maps.newHashMap();
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

				List<String> deviceIdList = getDeviceIdList("","",rrinfoId,"");
				setDeviceId(deviceIdList.get(0));
				IDataObject requestDataObject = buildRequestData(contenxtParmas);

				IDataObject responseDataObject = getResAdpterInvoker().invoke(
						requestDataObject, getTimeOut());
				handleResonpse(contenxtParmas, responseDataObject);

				saveParamInsts(flowInstId, nodeId);

			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:"
						+ nodeId + ".错误信息:" + e.getMessage();
				log.error(errorMsg,e);
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

}
