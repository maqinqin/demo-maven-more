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
import com.git.cloud.handler.automation.se.common.StorageDBBasicEnum;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.po.CmLun;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Maps;

public class RecoverSomeLunHandler extends RemoteAbstractAutomationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(RecoverSomeLunHandler.class);
	
	private StorageDAO storageDao;
	
	public StringBuffer rtn_sb = new StringBuffer();
	private Map<String,String> exeParams = Maps.newHashMap();
	private String errorMsg = new String();
	private String storageSn = "";
	private String storageName = "";
	private List<String> lunPathList;
	private List<String> deviceIdList;
	
	public void initPrivateInstance() {
		deviceIdList = null;
		lunPathList = null;
		storageName = "";
		storageSn = "";
		if(storageDao == null) {
			storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAOImpl");
		}
	}
	
	@SuppressWarnings("unchecked")
	public String execute(HashMap<String, Object> contenxtParams) {
		this.initPrivateInstance();
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
			logger.info("[RecoverSomeLunHandler]分配LUN过程开始" + commonInfo);
			try {
				Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);
				contenxtParams.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParams);
				if (extHandleParams != null) {
					contenxtParams.putAll(extHandleParams);
				}
				deviceIdList = getDeviceIdList("","",rrinfoId,"");
				Map<String, Object> deviceParams;
				IDataObject requestDataObject; // 发送请求
				IDataObject responseDataObject; // 返回结果
				deviceParams = (Map<String, Object>) contenxtParams.get(String.valueOf(deviceIdList.get(0)));
				if(contenxtParams.get("MAIN_STORAGE_FLAG") != null) {
					deviceParams.put("MAIN_STORAGE_FLAG", contenxtParams.get("MAIN_STORAGE_FLAG"));
				}
				deviceParams.put(SeConstants.RRINFO_ID, contenxtParams.get(SeConstants.RRINFO_ID));
				if(deviceIdList.size() > 1) {
					Map<String, Object> deviceParams2 = (Map<String, Object>) contenxtParams.get(String.valueOf(deviceIdList.get(1)));
					deviceParams.put("HBA_WWPN_OTHER", deviceParams2.get("HBA_WWPN"));
//					deviceParams.put(PowerVMAIXVariable.SYSTEMNAME + "_other", deviceParams2.get(PowerVMAIXVariable.SYSTEMNAME));
				}
				requestDataObject = buildRequestData(deviceParams);
				if(requestDataObject == null) {
					rtn_map.put("checkCode", MesgRetCode.SUCCESS);
					return JSON.toJSONString(rtn_map);
				}
				responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());
				handleResonpse(deviceParams, responseDataObject);
				saveParamInsts(flowInstId, nodeId);
				rtn_map.put("checkCode", MesgRetCode.SUCCESS);
				rtn_map.put("exeMsg", rtn_sb.toString());
				rtn_map.put("exeParams", exeParams);
				logger.info("[RecoverSomeLunHandler]创建Fabric正常结束" + commonInfo);
			} catch(Exception e) {
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getMessage());
				logger.error("[RecoverSomeLunHandler]创建Fabric异常结束," + e + commonInfo);
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
	protected IDataObject buildRequestData(Map<String, Object> deviceParams) throws Exception {
		AutomationService automationService = (AutomationService) WebApplicationManager.getBean("automationServiceImpl");
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		String rrinfoId = (String) deviceParams.get(SeConstants.RRINFO_ID);
		header.setResourceClass("SE");
		header.setResourceType("NETAPP");
		header.setOperation(3030); // 回收LUN
		String queueIdentify = "";
		try {
			// 增加数据中心路由标识
			queueIdentify = this.getQueueIdent(rrinfoId);
			header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		} catch (Exception e) {
			throw new Exception("get LinkRouteType error!",e);
		}
		String mainFlag = (String) deviceParams.get("MAIN_STORAGE_FLAG");
		String suId = "";
		if(mainFlag.equals("1")) { // 主存储
			suId = "11";
			storageName = (String) deviceParams.get("STORAGE_NAME1");
			storageSn = (String) deviceParams.get("STORAGE_SN1");
		} else {
			suId = "12";
			storageName = (String) deviceParams.get("STORAGE_NAME2");
			storageSn = (String) deviceParams.get("STORAGE_SN2");
		}
		lunPathList = new ArrayList<String> ();
		String delLuns = (String) deviceParams.get("del_lun_numbers");
		HashMap<String, List<String>> lunMap = this.getDelLunMap(delLuns);
		if(lunMap.get(storageName) == null) {
			logger.info("[RecoverSomeLunHandler]删除存储LUN时，当前存储[" + storageName + "]没有指定删除的LUN信息");
			return null;
		}
		StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");
		// 获取存储管理机登录信息
		StorageMgrVo mgrVo = storageDao.findStorageMgrInfoBySuId(suId);
		
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString("DATACENTER_ENAME", (String) deviceParams.get(StorageDBBasicEnum.DATACENTER_ENAME.name()));
		body.setString(SeConstants.OS_TYPE, (String) deviceParams.get(SeConstants.OS_TYPE));
		body.setString(SeConstants.STORAGE_MODEL, (String) deviceParams.get(SeConstants.STORAGE_MODEL));
		body.setString("SMISISERVER_IP", mgrVo.getManagerIp());
		body.setString("USER_NAME", mgrVo.getUserName());
		body.setString("USER_PASSWD", mgrVo.getPassword());
		body.setString("STORAGE_SN", storageSn);
		String appEname = (String) deviceParams.get("APP_ENAME");
		String lparName = (String) deviceParams.get("lparname");
		body.setString("VIEW_NAME", appEname + "DBPOC" + lparName.replaceAll("_", ""));
		body.setList("PATH", lunMap.get(storageName));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParams, IDataObject responseDataObject) throws Exception {
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		logger.info(header.getRetMesg());
		logger.info("amq-返回信息："+JSONObject.toJSONString(responseDataObject));
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			errorMsg = header.getRetMesg();
			logger.info(errorMsg);
			throw new Exception(errorMsg);
		}
		if(deviceIdList != null && deviceIdList.size() > 0) {
			// 释放已分配的LUN信息
			List<CmLun> lunList = storageDao.getUsedLunsByResourceIds(deviceIdList);
			if(lunList != null && lunList.size() > 0) {
				for(int i=lunList.size()-1 ; i>=0 ; i--) {
					if(lunPathList.indexOf(storageName + ":" + lunList.get(i).getLunPath()) < 0) {
						lunList.remove(i);
						continue;
					}
					lunList.get(i).setLunStatus("0");
					lunList.get(i).setLunType("");
					lunList.get(i).setUsedResourceId("");
				}
				storageDao.batchUpdateLunStatusAndType(lunList);
			}
		}
	}
	
	private HashMap<String, List<String>> getDelLunMap(String delLuns) {
		HashMap<String, List<String>> lunMap = new HashMap<String, List<String>> ();
		if(delLuns == null) {
			return lunMap;
		}
		String[] delLunArr = delLuns.split(",");
		for(int i=0 ; i<delLunArr.length ; i++) {
			String[] subArr = delLunArr[i].split(":");
			if(lunMap.get(subArr[0]) == null) {
				List<String> lunList = new ArrayList<String> ();
				lunList.add(subArr[1]);
				lunMap.put(subArr[0], lunList);
			} else {
				lunMap.get(subArr[0]).add(subArr[1]);
			}
			lunPathList.add(delLunArr[i]);
		}
		return lunMap;
	}
}
