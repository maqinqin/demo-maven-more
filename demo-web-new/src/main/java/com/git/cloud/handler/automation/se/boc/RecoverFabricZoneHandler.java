package com.git.cloud.handler.automation.se.boc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.sa.powervm.PowerVMAIXVariable;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.po.FabricPo;
import com.git.cloud.handler.automation.se.po.StorageFCPortGroupPo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Maps;

public class RecoverFabricZoneHandler extends RemoteAbstractAutomationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(CreateFabricZoneHandler.class);
	public StringBuffer rtn_sb = new StringBuffer();
	private Map<String,String> exeParams = Maps.newHashMap();
	private StorageDAO storageDao;
	
	public void initPrivateInstance() {
		if(storageDao == null) {
			storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAOImpl");
		}
	}
	
	@SuppressWarnings("unchecked")
	public String execute(HashMap<String, Object> contenxtParams) {
		initPrivateInstance();
		Map<String, Object> rtn_map = Maps.newHashMap();
		if(contenxtParams!=null){
			// 流程实例Id
			String flowInstId = (String) contenxtParams.get(SeConstants.FLOW_INST_ID);
			// 流程几点Id
			String nodeId = (String) contenxtParams.get(SeConstants.NODE_ID);
			// 服务请求Id
			String srvReqId = (String) contenxtParams.get(SeConstants.SRV_REQ_ID);
			// 资源请求ID
			String rrinfoId = (String) contenxtParams.get(SeConstants.RRINFO_ID);
			String commonInfo = ",服务请求ID:" + srvReqId + ",资源请求ID:" + rrinfoId + ",流程实例ID:" + flowInstId + ",流程节点ID:" + nodeId;
			logger.info("[CreateFabricZoneHandler]创建Fabric开始" + commonInfo);
			try {
				Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);
				contenxtParams.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParams);
				if (extHandleParams != null) {
					contenxtParams.putAll(extHandleParams);
				}
				List<String> deviceIdList = getDeviceIdList("","",rrinfoId,"");
				Map<String, Object> deviceParams;
				IDataObject requestDataObject; // 发送请求
				IDataObject responseDataObject; // 返回结果
				deviceParams = (Map<String, Object>) contenxtParams.get(String.valueOf(deviceIdList.get(0)));
				deviceParams.put(SeConstants.RRINFO_ID, contenxtParams.get(SeConstants.RRINFO_ID));
				if(deviceIdList.size() > 1) {
					Map<String, Object> deviceParams2 = (Map<String, Object>) contenxtParams.get(String.valueOf(deviceIdList.get(1)));
					deviceParams.put("HBA_WWPN_OTHER", deviceParams2.get("HBA_WWPN"));
					deviceParams.put(PowerVMAIXVariable.SYSTEMNAME + "_other", deviceParams2.get(PowerVMAIXVariable.SYSTEMNAME));
				}
				requestDataObject = buildRequestData(deviceParams);
				responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());
				handleResonpse(deviceParams, responseDataObject);
				saveParamInsts(flowInstId, nodeId);
				rtn_map.put("checkCode", MesgRetCode.SUCCESS);
				rtn_map.put("exeMsg", rtn_sb.toString());
				rtn_map.put("exeParams", exeParams);
				logger.info("[CreateFabricZoneHandler]创建Fabric正常结束" + commonInfo);
			} catch(Exception e) {
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getMessage());
				logger.error("[CreateFabricZoneHandler]创建Fabric异常结束," + e + commonInfo);
			} finally {
				if (contenxtParams != null)
					contenxtParams.clear();
			}
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null!");	
		}
		return JSON.toJSONString(rtn_map);
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> deviceParams)
			throws Exception {
		AutomationService automationService = (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		String rrinfoId = (String) deviceParams.get(SeConstants.RRINFO_ID);
		header.setResourceClass("SW");
		header.setResourceType("BROCADE");
		header.setOperation(3016);
		try {
			// 增加数据中心路由标识
			String queueIdentify = this.getQueueIdent(rrinfoId);
			header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		} catch (Exception e) {
			throw new Exception("get LinkRouteType error!",e);
		}
		// 获取管理机登录信息
		HashMap<String, String> param = new HashMap<String, String> ();
		param.put("managerId", (String) deviceParams.get("FABRIC_MGR_ID"));
		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoByParam(param);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString("SWITCH_TYPE", "BROCADE");
		body.setString("SERVICE_IP", mgrVo.getManagerIp());
		body.setString("SERVICE_PORT", mgrVo.getPort());
		body.setString("USER_NAME", mgrVo.getUserName());
		body.setString("USER_PASSWD", mgrVo.getPassword());
		body.setString("NAME_SPACE", mgrVo.getNamespace());
		List<String> hbaWwnList = this.getHbawwn((String) deviceParams.get("HBA_WWPN"));
		String hbaWwns = this.getHostHbaWwns(hbaWwnList);
		String otherHbaWwns = "";
		if(deviceParams.get("HBA_WWPN_OTHER") != null) {
			List<String> hbaWwnOther = this.getHbawwn((String) deviceParams.get("HBA_WWPN_OTHER"));
			otherHbaWwns = this.getHostHbaWwns(hbaWwnOther);
			hbaWwnList.addAll(hbaWwnOther);
		}
		// 两台物理机的HBA卡信息一同保存
		// HOST_HBA_WWN格式为 主机名1:主机HBA卡组合1;主机名2:主机HBA卡组合2
		String hostHbaWwns = deviceParams.get(PowerVMAIXVariable.SYSTEMNAME) + ":" + hbaWwns + ";" + deviceParams.get(PowerVMAIXVariable.SYSTEMNAME + "_other") + ":" + otherHbaWwns;
		body.setList("VFINFOS", getVfinfos(deviceParams, hostHbaWwns));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}
	
	private String getHostHbaWwns(List<String> hbaWwnList) {
		String hbaWwns = "";
		for(int i=0 ; i<hbaWwnList.size() ; i++) {
			hbaWwns += "," + hbaWwnList.get(i).toUpperCase().replaceAll(":", "").trim();
		}
		return hbaWwns.length() > 0 ? hbaWwns.substring(1) : "";
	}
	
	private List<String> getHbawwn(String hbaPwwn) {
		//BA_WWPN="{\"0\":\"C0:50:76:09:55:02:00:08\",\"1\":\"C0:50:76:09:55:02:00:0A\",\"2\":\"C0:50:76:09:55:02:00:0C\",\"3\":\"C0:50:76:09:55:02:00:0E\"}";
		JSONObject json = JSONObject.parseObject(hbaPwwn);
		List<String> wwnList = new ArrayList<String> ();
		try {
			for(int i=0 ; i<4 ; i++) {
				String wwn = json.getString(i+"");
				wwnList.add(wwn);
			}
		} catch(Exception e) {
			logger.error("异常exception",e);
			logger.error("[AssignLunHandler]分配LUN过程中，hbaPwwn信息解析错误，请检查：" + hbaPwwn);
		}
		return wwnList;
	}

	private List<Map<String, Object>> getVfinfos(Map<String, Object> deviceParams, String hostHbaWwns) throws Exception {
		Map<String, Object> vfMap = new HashMap<String, Object> ();
		String mgrId = (String) deviceParams.get("FABRIC_MGR_ID");
		FabricPo fabric = storageDao.findFabricPoByMgrId(mgrId);
		if(fabric == null) {
			throw new Exception ("未找到FABRIC信息,FABRIC管理机ID为：" + mgrId);
		}
		vfMap.put("VFNAME", fabric.getFabricName());
		List<Map<String, Object>> zoneList = new ArrayList<Map<String, Object>> ();
		Map<String, Object> zoneMap;
		StorageFCPortGroupPo port;
		String [] hostHbaWwnArr = hostHbaWwns.split(";");
		for(int i=0 ; i<hostHbaWwnArr.length ; i++) {
			// 第一个存储
			port = this.getStorageFcPortListBySN((String) deviceParams.get("STORAGE_SN1"));
			if(port == null) {
				throw new Exception ("未找到存储的端口，存储SN：" + deviceParams.get("STORAGE_SN1"));
			}
			String [] subArr = hostHbaWwnArr[i].split(":");
			zoneMap = new HashMap<String, Object> ();
			zoneMap.put("NAME", "POC_" + subArr[0] + "_" + port.getFcport());
			zoneMap.put("ACTIVE_ZONESET", "TESTING");
			zoneList.add(zoneMap);
			// 第二个存储
			if(deviceParams.get("STORAGE_SN2") == null) {
				continue;
			}
			port = this.getStorageFcPortListBySN((String) deviceParams.get("STORAGE_SN2"));
			if(port == null) {
				throw new Exception ("未找到存储的端口，存储SN：" + deviceParams.get("STORAGE_SN2"));
			}
			zoneMap = new HashMap<String, Object> ();
			zoneMap.put("NAME", "POC_" + subArr[0] + "_" + port.getFcport());
			zoneMap.put("ACTIVE_ZONESET", "TESTING");
			zoneList.add(zoneMap);
		}
		vfMap.put("ZONEINFOS", zoneList);
		List<Map<String, Object>> vfMapList = new ArrayList<Map<String, Object>> ();
		vfMapList.add(vfMap);
		return vfMapList;
	}
	
	private StorageFCPortGroupPo getStorageFcPortListBySN(String storageSn) {
		List<StorageFCPortGroupPo> portList = storageDao.getStorageFcPortListBySN(storageSn);
		if(portList != null && portList.size() > 0) {
			return portList.get(0);
		}
		return null;
	}
	
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParams,
			IDataObject responseDataObject) throws Exception {
		
	}
}
