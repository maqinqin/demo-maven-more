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

public class AssignLunHandler extends RemoteAbstractAutomationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AssignLunHandler.class);
	
	private StorageDAO storageDao;
	
	public StringBuffer rtn_sb = new StringBuffer();
	private Map<String,String> exeParams = Maps.newHashMap();
	private String errorMsg = new String();
	private String storageSn = "";
	private String storageName = "";
	private String assignLunInfos = "";
	List<CmLun> cmLunList;
	
	public void initPrivateInstance() {
		storageSn = "";
		storageName = "";
		assignLunInfos = "";
		cmLunList = null;
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
			logger.info("[AssignLunHandler]分配LUN过程开始" + commonInfo);
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
				responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());
				handleResonpse(deviceParams, responseDataObject);
				saveParamInsts(flowInstId, nodeId);
				rtn_map.put("checkCode", MesgRetCode.SUCCESS);
				rtn_map.put("exeMsg", rtn_sb.toString());
				rtn_map.put("exeParams", exeParams);
				logger.info("[AssignLunHandler]创建Fabric正常结束" + commonInfo);
			} catch(Exception e) {
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getMessage());
				logger.error("[AssignLunHandler]创建Fabric异常结束," + e + commonInfo);
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
		header.setOperation(3028);
		String queueIdentify = "";
		try {
			// 增加数据中心路由标识
			queueIdentify = this.getQueueIdent(rrinfoId);
			header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		} catch (Exception e) {
			throw new Exception("get LinkRouteType error!",e);
		}
		String storageMirror = (String) deviceParams.get(SeConstants.STORAGE_MIRROR);
//		storageMirror = "enable";
		String mainFlag = (String) deviceParams.get("MAIN_STORAGE_FLAG");
		String suId = "";
		String storageId = "";
		String isM = ""; // 是否镜像，镜像为M
		if(mainFlag.equals("1")) { // 主存储
			suId = "11";
			storageSn = (String) deviceParams.get("STORAGE_SN1");
			storageName = (String) deviceParams.get("STORAGE_NAME1");
			storageId = (String) deviceParams.get("STORAGE_ID1");
		} else {
			suId = "12";
			isM = "M";
			storageSn = (String) deviceParams.get("STORAGE_SN2");
			storageName = (String) deviceParams.get("STORAGE_NAME2");
			storageId = (String) deviceParams.get("STORAGE_ID2");
		}
		StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");
		// 获取存储管理机登陆信息
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
		List<String> hbaWwnList = this.getHbawwn((String) deviceParams.get("HBA_WWPN"));
//		String hbaWwns = this.getHostHbaWwns(hbaWwnList);
//		String otherHbaWwns = "";
		if(deviceParams.get("HBA_WWPN_OTHER") != null) {
			List<String> hbaWwnOther = this.getHbawwn((String) deviceParams.get("HBA_WWPN_OTHER"));
//			otherHbaWwns = this.getHostHbaWwns(hbaWwnOther);
			hbaWwnList.addAll(hbaWwnOther);
		}
		body.setList("HBA_WWN", hbaWwnList);
		String dataDisk = (String) deviceParams.get(StorageDBBasicEnum.DATA_DISK_CAPACITY.name());
		String archDisk = (String) deviceParams.get(StorageDBBasicEnum.ARCH_DISK_CAPACITY.name());
		if(storageMirror.equals("enable")) {
			body.setList("LUN_INFO", this.getLunInfo(deviceParams, storageId, Integer.valueOf(dataDisk), Integer.valueOf(archDisk), isM));
		} else {
			body.setList("LUN_INFO", this.getLunInfo(deviceParams, storageId, Integer.valueOf(dataDisk), Integer.valueOf(archDisk)));
		}
		/*for(int i=0 ; i<deviceIdList.size() ; i++) {
			// 两台物理机的HBA卡信息一同保存
			// HOST_HBA_WWN格式为 主机名1:主机HBA卡组合1;主机名2:主机HBA卡组合2
			String hostHbaWwns = deviceParams.get(PowerVMAIXVariable.SYSTEMNAME) + ":" + hbaWwns + ";" + deviceParams.get(PowerVMAIXVariable.SYSTEMNAME + "_other") + ":" + otherHbaWwns;
			this.setHandleResultParam(deviceIdList.get(i), "HOST_HBA_WWN", hostHbaWwns);
		}*/
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
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParams, IDataObject responseDataObject) throws Exception {
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		logger.info(header.getRetMesg());
		logger.info("amq-返回信息："+JSONObject.toJSONString(responseDataObject));
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			// 出错回收刚分配的LUN信息
			if(cmLunList != null && cmLunList.size() > 0) {
				for(int i=0 ; i<cmLunList.size() ; i++) {
					cmLunList.get(i).setLunStatus("0");
					cmLunList.get(i).setLunType("");
					cmLunList.get(i).setUsedResourceId("");
				}
				storageDao.batchUpdateLunStatusAndType(cmLunList);
			}
			errorMsg = header.getRetMesg();
			logger.info(errorMsg);
			throw new Exception(errorMsg);
		}
		String rrinfoId = (String) contenxtParams.get(RRINFO_ID);
		String nodeId = (String) contenxtParams.get(NODE_ID);
		List<String> deviceIdList = getDeviceIdList("",nodeId,rrinfoId,"");
		for(int i=0 ; i<deviceIdList.size() ; i++) {
			setHandleResultParam(String.valueOf(deviceIdList.get(i)), storageSn + "_ASSIGN_LUN_INFO", assignLunInfos.length() > 0 ? assignLunInfos.substring(1) : "");
		}
		// 回写LUN_NAME
//		storageDao.batchUpdateLunName(lunList);
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
	
	/**
	 * 双边分配算法
	 * @param dataDisk
	 * @param archDisk
	 * @param isM
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String, String>> getLunInfo(Map<String, Object> deviceParams, String storageId, int dataDisk, int archDisk, String isM) throws Exception {
		List<Map<String, String>> lunList = new ArrayList<Map<String, String>> ();
		int sysNum = 1; // 心跳盘个数
		int dataNum = dataDisk / SeConstants.LUN_SIZE; // 数据盘个数
		int archNum = archDisk / SeConstants.LUN_SIZE; // ARCH盘个数
		int n = sysNum + dataNum + archNum; // LUN的个数 = 心跳盘sysNum个LUN + 数据盘dataNum个LUN + ARCH盘archNum个LUN
		cmLunList = this.findCmLun(storageId, n);
		if(cmLunList == null || cmLunList.size() < n) {
			logger.error("[AssignLunHandler]分配LUN过程中，已有LUN个数小于需要LUN个数，存储：" + storageSn + "，需要LUN个数：" + n);
			return lunList;
		}
		String rrinfoId = (String) deviceParams.get(SeConstants.RRINFO_ID);
		Map<String, String> lunMap;
		for(int i=0 ; i<n ; i++) {
			lunMap = new HashMap<String, String> ();
			lunMap.put("LUN_ID", cmLunList.get(i).getLunName());
			lunMap.put("PATH", cmLunList.get(i).getLunPath());
			lunList.add(lunMap);
			if(i == 0) { // 心跳盘
				cmLunList.get(i).setLunType("SYSDISK" + isM);
			} else if(i-sysNum < dataNum) { // 数据盘
				cmLunList.get(i).setLunType("DATADISK" + isM);
			} else { // ARCH盘
				cmLunList.get(i).setLunType("ARCHDISK" + isM);
			}
			cmLunList.get(i).setLunStatus("1"); // 已分配
			cmLunList.get(i).setUsedResourceId(rrinfoId); // 指定使用该LUN的资源申请ID
			assignLunInfos += "," + storageName + ":" + cmLunList.get(i).getLunPath() + ":" + cmLunList.get(i).getLunType();
		}
		// 保存LUN的状态以及类型
		storageDao.batchUpdateLunStatusAndType(cmLunList);
		return lunList;
	}
	
	/**
	 * 单边分配算法
	 * @param dataDisk
	 * @param archDisk
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String, String>> getLunInfo(Map<String, Object> deviceParams, String storageId, int dataDisk, int archDisk) throws Exception {
		List<Map<String, String>> lunList = new ArrayList<Map<String, String>> ();
		int sysNum = 2; // 心跳盘个数
		int dataNum = dataDisk / SeConstants.LUN_SIZE; // 数据盘个数
		int archNum = archDisk / SeConstants.LUN_SIZE; // ARCH盘个数
		int n = sysNum + dataNum + archNum; // LUN的个数 = 心跳盘sysNum个LUN + 数据盘dataNum个LUN + ARCH盘archNum个LUN
		cmLunList = this.findCmLun(storageId, n);
		if(cmLunList == null || cmLunList.size() < n) {
			logger.error("[AssignLunHandler]分配LUN过程中，已有LUN个数小于需要LUN个数，存储：" + storageSn + "，需要LUN个数：" + n);
			return lunList;
		}
		String rrinfoId = (String) deviceParams.get(SeConstants.RRINFO_ID);
		Map<String, String> lunMap;
		for(int i=0 ; i<n ; i++) {
			lunMap = new HashMap<String, String> ();
			lunMap.put("LUN_ID", cmLunList.get(i).getLunName());
			lunMap.put("LUN_SN", cmLunList.get(i).getLunSn());
			lunMap.put("PATH", cmLunList.get(i).getLunPath());
			lunList.add(lunMap);
			if(i == 0) { // 心跳盘
				cmLunList.get(i).setLunType("SYSDISK");
			} else if(i == 1) { // 心跳镜像盘
				cmLunList.get(i).setLunType("SYSDISKM");
			} else if(i-sysNum < dataNum) { // 数据盘
				cmLunList.get(i).setLunType("DATADISK");
			} else { // ARCH盘
				cmLunList.get(i).setLunType("ARCHDISK");
			}
			cmLunList.get(i).setLunStatus("1"); // 已分配
			cmLunList.get(i).setUsedResourceId(rrinfoId);
			assignLunInfos += "," + storageName + ":" + cmLunList.get(i).getLunPath() + ":" + cmLunList.get(i).getLunType();
		}
		// 保存LUN的状态以及类型
		storageDao.batchUpdateLunStatusAndType(cmLunList);
		return lunList;
	}
	
	/**
	 * 根据存储ID获取可用的LUN信息
	 * @param storageId
	 * @return
	 */
	private List<CmLun> findCmLun(String storageId, int lunNum) {
		Map<String,String> paramMap = new HashMap<String, String> ();
		paramMap.put("storageId", storageId);
		paramMap.put("lunStatus", "0");
		List<CmLun> lunList = storageDao.findLunList(paramMap);
		if(lunList == null || lunList.size() < lunNum) {
			return null;
		}
		return lunList.subList(0, lunNum);
	}
}
