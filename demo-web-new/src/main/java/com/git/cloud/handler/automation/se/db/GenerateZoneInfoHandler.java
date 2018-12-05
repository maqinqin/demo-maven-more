package com.git.cloud.handler.automation.se.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.dao.StorageDAO;
import com.git.cloud.handler.automation.se.po.FabricPo;
import com.git.cloud.handler.automation.se.po.FcsPo;
import com.git.cloud.handler.automation.se.po.VsanPo;
import com.git.cloud.handler.automation.se.vo.FabricVo;
import com.git.cloud.handler.automation.se.vo.StorageMgrVo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
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
 * @ClassName:GenerateZoneInfoHandler
 * @Description:生成zone信息
 * @author chengbin.co
 * @date 2014-2-19 下午1:53:43
 * 
 * 
 */
public class GenerateZoneInfoHandler  extends RemoteAbstractAutomationHandler{

	private static Logger log = LoggerFactory
			.getLogger(GenerateZoneInfoHandler.class);
	private String errorMsg = new String();
	private StringBuffer result_sb = new StringBuffer(); 

	private Map<String, List<String>> fabric_vf_map = Maps.newHashMap();
	private ICmDeviceDAO devDao = (ICmDeviceDAO)WebApplicationManager.getBean("cmDeviceDAO");
	private ICmVmDAO vmDao = (ICmVmDAO)WebApplicationManager.getBean("cmVmDAO");

	private String deviceId;
	private String fabric1Id,fabric2Id,fabric1Name,fabric2Name;
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	private String switch_type = new String();
	public String getSwitch_type() {
		return switch_type;
	}

	public void setSwitch_type(String switch_type) {
		this.switch_type = switch_type;
	}
	private StorageDAO storageDao = (StorageDAO) WebApplicationManager.getBean("storageDAO");

//	private FcsDAO fcsDao = (FcsDAO) FrameworkContext.getApplicationContext()
//			.getBean("fcsDAO");
//	private VsanDAO vsanDao = (VsanDAO) FrameworkContext
//			.getApplicationContext().getBean("vsanDao");

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
		Map<String, Object> deviceParams = (HashMap<String, Object>) contenxtParmas
				.get(String.valueOf(getDeviceId()));
		
		header.setResourceClass("SE");
		String resourceType = (String)deviceParams.get(StorageConstants.RESOURCE_TYPE_SWITCH);
		if(StringUtils.isBlank(resourceType)){
			throw new Exception("获取[RESOURCE_TYPE_SWITCH]参数错误！");
		}
		header.setResourceType(resourceType);
		header.setOperation(SEOpration.GETCREATEZONEINFO);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();
		List<String> wwpnList = Lists.newArrayList();
		// 获取hab_pwwn信息
		Object hbawwnObj = deviceParams.get(StorageConstants.HBA_WWN);
		wwpnList.addAll(convertVsanJson(hbawwnObj));
		body.setList(StorageConstants.HBA_WWN, wwpnList);

		// 获取FA_PWWN信息
		String resPoolLevel = (String) deviceParams
				.get(StorageConstants.RES_POOL_LEVEL);
		int storage_size = 0;
		String storage_sn_str = (String) deviceParams
				.get(StorageConstants.STORAGE_SN_MAP);
		JSONObject storage_sn_map = JSONObject.parseObject(storage_sn_str);
		if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_PLATINUM)) {
			storage_size = StorageConstants.PLATINUM_STORAGE_SIZE;
		} else if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_GLOD)) {
			storage_size = StorageConstants.GLOD_STORAGE_SIZE;
		} else if (resPoolLevel.equals(StorageConstants.RES_POOL_LEVEL_SILVER)) {
			storage_size = StorageConstants.SILVER_STORAGE_SIZE;
		}
//		if (storage_sn_map.size() != storage_size) {
//			throw new Exception("获取存储数量错误！");
//		}

		List<String> fa_pwwn_str_list = Lists.newArrayList();
		for(int i=1;i<storage_size+1;i++){
			String fapwwn_str = (String)deviceParams.get(StorageConstants.FA_WWN+i);
			if(StringUtils.isBlank(fapwwn_str)){
				continue;
				//throw new Exception("获取存储端口[FA_WWN"+i+"]参数错误！");
			}else{
				fa_pwwn_str_list.add(fapwwn_str);
			}
		}
		List<String> fa_pwwn_list = Lists.newArrayList();
		for (Iterator<Entry<String, Object>> itr = storage_sn_map.entrySet()
				.iterator(); itr.hasNext();) {
			Entry<String, Object> entry = itr.next();
			String sn = (String) entry.getValue();
			for (String fa_pwwn_str : fa_pwwn_str_list) {
				if (fa_pwwn_str.contains(sn)) {
					fa_pwwn_list.addAll(getFCPwwnList(fa_pwwn_str));
				} else {
					continue;
				}
			}
		}
		log.debug("the storage front port list : " + fa_pwwn_list.toString());
		
		// 获取fabric列表信息		
		List<String> fabricNameList = Lists.newArrayList();
		List<FabricVo> fabricVoList = Lists.newArrayList();
		Map<String, Object> deviceMap = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(getDeviceId()));
		Object unitObj = deviceMap.get("UNIT_INFO");
		JSONObject unitInfo = JSONObject.parseObject(String.valueOf(unitObj));
		fabric1Id = unitInfo.getString("FABRIC1_ID");
		fabric2Id = unitInfo.getString("FABRIC2_ID");
		Map<String, String> map = Maps.newHashMap();
		map.put("fabricId", fabric1Id);
		map.put("isActive", "Y");
		FabricVo vo1 = storageDao.findFabricVoById(fabric1Id);
		fabric1Name = vo1.getFabricName();

		Map<String, String> map1 = Maps.newHashMap();
		map1.put("fabricId", fabric2Id);
		map1.put("isActive", "Y");
		FabricVo vo2 = storageDao.findFabricVoById(fabric2Id);
		fabric2Name = vo2.getFabricName();

		if (!vo1.getManagerId().equals(vo2.getManagerId())) {
			throw new Exception(
					"Fabric1 and Fabric2 no the same storage manager id");
		}
		fabricNameList.add(fabric1Name);
		fabricNameList.add(fabric2Name);
		log.debug("Fabric Name List : {}", fabricNameList.toString());
		// 获取交换机类型信息
		String unitInfo_str = deviceParams.get(StorageConstants.UNIT_INFO).toString();
		if(StringUtils.isBlank(unitInfo_str)){
			throw new Exception("获取[UNIT_INFO]参数错误！");
		}
		switch_type = String
				.valueOf(unitInfo.get(StorageConstants.SWITCH_TYPE));
		if(StringUtils.isBlank(switch_type)){
			throw new Exception("获取[SWITCH_TYPE]参数错误!");
		}
		body.setString(StorageConstants.SWITCH_TYPE, switch_type);
		if (switch_type.equals(StorageConstants.SWITCH_TYPE_CISCO)) {
			// build fabric name list by UNIT_INFO from global parameter table
			body.setList(StorageConstants.FABRIC_NAME, fabricNameList);
			body.setList(StorageConstants.FA_WWN, fa_pwwn_list);
		} else if (switch_type.equals(StorageConstants.SWITCH_TYPE_BROCADE)) {
			// 通过fabric获取vf信息
			List<String> vfName_list = getVFNameList(fabricVoList);
			body.setList(StorageConstants.VF_NAME, vfName_list);
			List<String> _fa_pwwn_list = Lists.newArrayList();
			for(String pwwn:fa_pwwn_list){
				pwwn = pwwn.replaceAll(":", "");
				_fa_pwwn_list.add(pwwn.toUpperCase());
			}
			body.setList(StorageConstants.FA_WWN, _fa_pwwn_list);
		}

		StorageMgrVo storageMgrVo = storageDao.findStorageMgrInfoByFabric(vo1.getFabricId());
		
		body.setString(StorageConstants.SERVICE_IP, storageMgrVo.getManagerIp());
		body.setString(StorageConstants.SERVICE_PORT, storageMgrVo.getPort());
		body.setString(StorageConstants.USERNAME, storageMgrVo.getUserName());
		body.setString(StorageConstants.USERPASSWD, storageMgrVo.getPassword());
		body.setString(StorageConstants.NAMESPACE, storageMgrVo.getNamespace());

		
		reqData.setDataObject(MesgFlds.BODY, body);
		
		log.info("请求选择lun信息："+JSONObject.toJSONString(reqData));

		return reqData;
	}

	private List<String> getVFNameList(List<FabricVo> fabricVoList) {
		List<String> vfName_list = Lists.newArrayList();
		for(FabricVo vo : fabricVoList){
			List<String> list = Lists.newArrayList();
			String fabricId = vo.getFabricId();
			List<VsanPo> vsanPoList = storageDao.findVsanInfoByFabircId(fabricId);
			for(VsanPo po:vsanPoList){
				list.add(po.getVsanName());
			}
			fabric_vf_map.put(vo.getFabricName(), list);
			vfName_list.addAll(list);
		}
		return vfName_list;
	}

	private String getActiveZoneset(String fabricName, String vfName) throws Exception {
		try {
			FabricPo fabricPo = storageDao.findFabricPoByName(fabricName);
			Map<String, String> params = Maps.newHashMap();
			params.put("vsanName", vfName);
			params.put("fabricId", fabricPo.getFabricId());
			params.put("isActive", "Y");
			VsanPo po = storageDao.findVsanPoByParams(params);
			String active_zoneset = po.getActiveZoneset();
			if(StringUtils.isBlank(active_zoneset)){
				throw new Exception("获取fabric："+fabricName+"vfName:"+vfName+" active zoneset 为空!");
			}
			return po.getActiveZoneset();
		} catch (Exception e) {
			throw new Exception("获取fabric：" + fabricName
					+ " vfName:" + vfName + " active zoneset信息失败！");
		}
	}

	/**
	 * get the storage pwwn list from FC_PWWN in the contextParams
	 * 
	 * @param fcpwwnStr
	 *            : FC_PWWN={STORAGE_SN=1 , GROUP_ID=1 , 1=1, 2=2 , 3=3 , 4=4}
	 * @return [1,2,3,4]
	 * @throws Exception 
	 */
	private List<String> getFCPwwnList(Object fcpwwn) throws Exception {
		String fcpwwnStr = String.valueOf(fcpwwn);
		String[] array = fcpwwnStr.substring(1, fcpwwnStr.length() - 1).split(
				",");
		List<String> fcPwwnList = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			int index = array[i].indexOf("=");
			if (index != -1) {
				String key = array[i].substring(0, index);
				String value = array[i].substring(index + 1);
				if (key == null || "".equals(key) || value == null
						|| "".equals(value)) {
					errorMsg = "FA_PWWN:" + fcpwwn + " contain null value";
					throw new Exception(errorMsg);
				}
				if (!"STORAGE_SN".equalsIgnoreCase(key.trim())) {
					if (!"GROUP_ID".equalsIgnoreCase(key.trim())) {
						fcPwwnList.add(value.trim());
					}
				}
			}
		}
		if (fcPwwnList.size() != 4) {
			errorMsg = "the size of FA_PWWN:" + fcpwwn + " is not four";
			throw new Exception(errorMsg);
		}
		return fcPwwnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) throws Exception {

		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		//log.info(header.getRetMesg());
		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}
		BodyDO body = responseDataObject.getDataObject(MesgFlds.BODY,
				BodyDO.class);

		log.info("获取返回zone信息:"+body.toString());
		// 获取交换机cisco类型的创建zone信息
		if (switch_type.equals(StorageConstants.SWITCH_TYPE_CISCO)) {
			log.info("获取deviceId:"+getDeviceId()+"FABRIC信息");
			logVsanInfo(body);

			HashMap<String, String> hbaVsan = convertMap(
					body.getHashMap("HBA_Vsan"), "HBA_Vsan");
			HashMap<String, String> hbaSwithName = convertMap(
					body.getHashMap("HBA_SwithName"), "HBA_SwithName");
			HashMap<String, String> hbaFbcName = convertMap(
					body.getHashMap("HBA_FbcName"), "HBA_FbcName");
			HashMap<String, String> fcPortAlias = convertMap(
					body.getHashMap("FAPort_Alias"), "FAPort_Alias");
			HashMap<String, String> faPortVsan = convertMap(
					body.getHashMap("FAPort_Vsan"), "FAPort_Vsan");
			HashMap<String, String> faPortFabric = convertMap(
					body.getHashMap("FAPort_FbcName"), "FAPort_FbcName");

			generateCiscoZoneInfo(hbaVsan, hbaSwithName, hbaFbcName,
					fcPortAlias, faPortVsan, faPortFabric, contenxtParmas);
		} else if (switch_type.equals(StorageConstants.SWITCH_TYPE_BROCADE)) {
			log.info("获取deviceId:"+getDeviceId()+"FABRIC信息");
			logVsanInfo(body);
			List<String> vfName_list = body.getList("VF_NAME");
			HashMap<String, String> hbaSwitchName = convertMap(
					body.getHashMap("HBA_SWITCHNAME"), "HBA_SWITCHNAME");
			HashMap<String, String> hbaVFName = convertMap(
					body.getHashMap("HBA_VFNAME"), "HBA_VFNAME");
			HashMap<String, String> fcPortAlias = convertMap(
					body.getHashMap("FAPORT_ALIAS"), "FAPORT_ALIAS");
			HashMap<String, String> fcPortVFName = convertMap(
					body.getHashMap("FAPORT_VFNAME"), "FAPORT_VFNAME");

			this.generateBrocadeZoneInfo(vfName_list, hbaSwitchName, hbaVFName,
					fcPortAlias, fcPortVFName, contenxtParmas);
		}
	}

	/**
	 * @throws Exception 
	 * @Title: generateCiscoZoneInfo
	 * @Description: 获取CISCO交换机zone信息
	 * @field: @param hbaVsan
	 * @field: @param hbaSwithName
	 * @field: @param hbaFbcName
	 * @field: @param fcPortAlias
	 * @field: @param faPortVsan
	 * @field: @param faPortFabric
	 * @field: @param contenxtParmas
	 * @return void
	 * @throws
	 */
	public void generateCiscoZoneInfo(HashMap<String, String> hbaVsan,
			HashMap<String, String> hbaSwithName,
			HashMap<String, String> hbaFbcName,
			HashMap<String, String> fcPortAlias,
			HashMap<String, String> faPortVsan,
			HashMap<String, String> faPortFabric,
			Map<String, Object> contenxtParmas) throws Exception {

		TreeMap<String, List<String>> fabricWwpnMap = buildFabricNameWwpnMap(hbaFbcName);
		Map<String, String> map = Maps.newHashMap();
		if (fabricWwpnMap == null) {
			return;
		}

		// 获取存储前端端口与wwn号对应列表hyh
		List<HashMap<String, String>> frontPortWwpnList = buildFrontPortWwpnList(contenxtParmas);
		// 获取主机hba卡 fcs:wwn对映Map信息
		HashMap<String, String> hbaWwpnMap = buildHbaWwpnMap(contenxtParmas);

		for (Iterator<Entry<String, List<String>>> itr = fabricWwpnMap
				.entrySet().iterator(); itr.hasNext();) {
			List<HashMap<String, Object>> zoneList = new ArrayList<HashMap<String, Object>>();
			Entry<String, List<String>> entry = itr.next();
			String fabricName = entry.getKey();
			log.info("[Fabric name]:" + fabricName);
			if (hbaWwpnMap.size() == 4) {
				// hba-wwpn对映存储前端端口pwwn列表Map
				HashMap<String, List<String>> wwpnPwwnMap = new HashMap<String, List<String>>();
				Map<String,String> paramMap = Maps.newHashMap();
				paramMap.put("fabricName", fabricName);
				List<FcsPo> poList = storageDao.findFcsPoListByFabricName(paramMap);
				result_sb.append(
						"---------fabricName:" + fabricName + "---------")
						.append("\n");
				for (int i = 0; i < poList.size(); i++) {
					FcsPo po = poList.get(i);
					String frontPortName = po.getFcPortName().toUpperCase();
					String fcs = po.getFcs();// fcs:frontPortName
					String deviceId = getDeviceId();
					// 获取规则表中存储端口对应的主机HBA卡fcs卡槽的wwn信息
					String wwpn = hbaWwpnMap.get(fcs);
					if (wwpn == null || "".equalsIgnoreCase(wwpn)) {
						errorMsg = wwpn
								+ " in vm:"
								+ deviceId
								+ " do not exist in HBA_WWPN in global param table";
						throw new Exception(errorMsg);
					} else if (!entry.getValue().contains(wwpn)) {
						errorMsg = wwpn + " in vm:" + deviceId
								+ " do not exist in HBA_FbcName map";
						throw new Exception(errorMsg);
					}

					for (int k = 0; k < frontPortWwpnList.size(); k++) {
						// 根据存储前端口名，从工作流参数表中获得的端口信息列表中，获取存储前端口wwn信息
						String pwwn = frontPortWwpnList.get(k).get(
								frontPortName);
						if (pwwn == null || "".endsWith(pwwn)) {
							continue;
						} else {
							if (checkWwpnAndPwwn(wwpn, pwwn, wwpnPwwnMap)) {
								continue;
							}
							// 获取返回参数中前端口wwn对应的fabric
							String pwwnFabricName = faPortFabric.get(pwwn);
							if (pwwnFabricName == null
									|| "".equals(pwwnFabricName)) {
								errorMsg = " the fabric name of pwwn:" + pwwn
										+ " do not exist";
								throw new Exception(errorMsg);
							}
							if (!fabricName.equalsIgnoreCase(pwwnFabricName)) {
								errorMsg = " zone member[" + wwpn
										+ " in fabric:" + fabricName + ","
										+ pwwn + " in fabric:" + pwwnFabricName
										+ "] not in the same fabric";
								throw new Exception(errorMsg);
							}
							// 验证hba和存储前端口是否在同一个fabric下的vsan中
//							if (!checkWwpnAndPwwnVsanConsistent(wwpn, pwwn,
//									deviceId, hbaVsan, faPortVsan, fabricName)) {
//								errorMsg = "[hba wwpn]:"
//										+ wwpn
//										+ " [storage pwwn]:"
//										+ pwwn
//										+ " [not in the same vsan or not in the fabric]:"
//										+ fabricName;
//								throw new Exception(errorMsg);
//							}
							// 封装zone信息对象
							HashMap<String, Object> zoneInfoMap = buildZone(
									wwpn, pwwn, fabricName, fcs, hbaSwithName,
									fcPortAlias, hbaVsan);
							if (zoneInfoMap == null) {
								errorMsg = " get zoneInfoMap error!";
								throw new Exception(errorMsg);
							}
							log.info("[Fabric Name is]:"
									+ zoneInfoMap.toString());
							zoneList.add(zoneInfoMap);
							wwpnPwwnMap = buildWwpnAndPwwn(wwpn, pwwn,
									wwpnPwwnMap);
						}
					}
				}
			} else if (hbaWwpnMap.size() == 2) {
				String deviceId = getDeviceId();
				// 主机有2个hba卡槽
				List<String> fabric_hbawwn_list = entry.getValue();
				Iterator<Entry<String, String>> itr_hba = hbaWwpnMap.entrySet()
						.iterator();
				while (itr_hba.hasNext()) {
					Entry<String, String> entry_hba = itr_hba.next();
					String fcs = entry_hba.getKey();
					String hba_wwn = entry_hba.getValue();
					if (fabric_hbawwn_list.contains(hba_wwn)) {
						// 获取fabric下的存储前端口
						List<String> fcwwn_list = getFabricFcPwwn(faPortFabric,
								fabricName, frontPortWwpnList);
						for (String fcwwn : fcwwn_list) {
							String fcPortName = getFcPortName(fcwwn,
									frontPortWwpnList);
							if (null == fcPortName || "".equals(fcPortName)) {
								throw new Exception("FC PORT WWN:"
										+ fcwwn + " not find fc port name");
							} else {
								// 封装zone信息对象
								HashMap<String, Object> zoneInfoMap = buildZone(
										hba_wwn, fcwwn, fabricName, fcs,
										hbaSwithName, fcPortAlias, hbaVsan);
								if (zoneInfoMap == null) {
									errorMsg = " get zoneInfoMap error!";
									throw new Exception(errorMsg);
								}

								log.info("[Fabric Name is]:"
										+ zoneInfoMap.toString());
								zoneList.add(zoneInfoMap);
							}
						}
					} else {
						log.debug("主机ID：" + deviceId + " hba_wwn:" + hba_wwn
								+ " 不在FABRIC:" + fabricName + "中");
						System.out.println("主机ID：" + deviceId + " hba_wwn:"
								+ hba_wwn + " 不在FABRIC:" + fabricName + "中");
						continue;
					}
				}
			}
			String zones = JSONObject.toJSONString(zoneList);
			map.put(fabricName, zones);
			log.info(" zones:" + zones.toString());
		}
		saveFabricZoneInfo(map, contenxtParmas);
	}

	public void generateBrocadeZoneInfo(List<String> vfName_list,
			HashMap<String, String> hbaSwitchName,
			HashMap<String, String> hbaVFName,
			HashMap<String, String> fcPortAlias,
			HashMap<String, String> fcPortVFName,
			Map<String, Object> contenxtParmas) throws Exception {

		Map<String, String> fabric_zone_map = Maps.newHashMap();
		// 存储前端端口信息 fcwwn:port
		List<HashMap<String, String>> frontPortWwpnList = buildFrontPortWwpnList(contenxtParmas);

		HashMap<String, String> fcwwn_port_map = Maps.newHashMap();
		for (HashMap<String, String> map : frontPortWwpnList) {
			for (Iterator<Entry<String, String>> itr = map.entrySet()
					.iterator(); itr.hasNext();) {
				Entry<String, String> entry = itr.next();
				fcwwn_port_map.put(entry.getValue(), entry.getKey());
			}
		}
		// 获取主机hba卡 fcs:wwn对映Map信息
		HashMap<String, String> hbaWwpnMap = buildHbaWwpnMap(contenxtParmas);
		HashMap<String, String> fcs_hbawwn_map = Maps.newHashMap();
		for (Iterator<Entry<String, String>> itr = hbaWwpnMap.entrySet()
				.iterator(); itr.hasNext();) {
			Entry<String, String> entry = itr.next();
			fcs_hbawwn_map.put(entry.getValue(), entry.getKey());
		}
		// 根据VF_NAME获取fabric对应关系
		Map<String, String> vf_fabric_map = getVFFabricMapInfo(vfName_list);

		TreeMap<String, List<String>> vfHbaWwnMap = buildVFNameWwpnMap(hbaVFName);
		TreeMap<String, List<String>> vfFcPwwnMap = buildVFNameWwpnMap(fcPortVFName);

		// 循环vf_fabric_map信息获取创建zone信息
		for (Iterator<Entry<String, String>> itr = vf_fabric_map.entrySet()
				.iterator(); itr.hasNext();) {
			List<HashMap<String, Object>> zoneList = new ArrayList<HashMap<String, Object>>();
			Entry<String, String> entry = itr.next();
			String vf_name = entry.getKey();
			List<String> hba_wwn_list = vfHbaWwnMap.get(vf_name);
			String fabric_id = entry.getValue();
			String fabric_name = storageDao.findFabricVoById(fabric_id).getFabricName();
			if (hbaWwpnMap.size() == 4) {

				// hba-wwpn对映存储前端端口pwwn列表Map
				HashMap<String, List<String>> wwpnPwwnMap = new HashMap<String, List<String>>();
				Map<String,String> paramMap = Maps.newHashMap();
				paramMap.put("fabricName", fabric_name);
				// 根据fabric_name获取fcsPo信息
				List<FcsPo> poList = storageDao.findFcsPoListByFabricName(paramMap);
				for (int i = 0; i < poList.size(); i++) {
					FcsPo po = poList.get(i);
					String frontPortName = po.getFcPortName().toUpperCase();
					String fcs = po.getFcs();// fcs:frontPortName
					String deviceId = getDeviceId();
					// 获取规则表中存储端口对应的主机HBA卡fcs卡槽的wwn信息
					String wwpn = hbaWwpnMap.get(fcs);
					if (wwpn == null || "".equalsIgnoreCase(wwpn)) {
						errorMsg = wwpn
								+ " in vm:"
								+ deviceId
								+ " do not exist in HBA_WWPN in global param table";
						throw new Exception(errorMsg);
					} else if (!hba_wwn_list.contains(wwpn)) {
						errorMsg = wwpn + " in vm:" + deviceId
								+ " do not exist in HBA_FbcName map";
						throw new Exception(errorMsg);
					}

					for (int k = 0; k < frontPortWwpnList.size(); k++) {
						// 根据存储前端口名，从工作流参数表中获得的端口信息列表中，获取存储前端口wwn信息
						String pwwn = frontPortWwpnList.get(k).get(
								frontPortName);
						if (pwwn == null || "".endsWith(pwwn)) {
							continue;
						} else {
							if (checkWwpnAndPwwn(wwpn, pwwn, wwpnPwwnMap)) {
								continue;
							}
							// 获取返回参数中前端口wwn对应的VF
							String pwwnVFName = fcPortVFName.get(pwwn);
							if (pwwnVFName == null || "".equals(pwwnVFName)) {
								errorMsg = " the vfName name of pwwn:" + pwwn
										+ " do not exist";
								throw new Exception(errorMsg);
							}
							if (!vf_name.equalsIgnoreCase(pwwnVFName)) {
								errorMsg = " zone member[" + wwpn
										+ " in vf_name:" + vf_name + "," + pwwn
										+ " in fabric:" + pwwnVFName
										+ "] not in the same vf";
								throw new Exception(errorMsg);
							}

							// 封装zone信息对象
							HashMap<String, Object> zoneInfoMap = this
									.buildBrocadeZone(wwpn, pwwn, fabric_name,
											fcs, hbaSwitchName, fcPortAlias,
											hbaVFName);

							if (zoneInfoMap == null) {
								errorMsg = " get zoneInfoMap error!";
								throw new Exception(errorMsg);
							}
							log.info("[Fabric Name is]:"
									+ zoneInfoMap.toString());
							zoneList.add(zoneInfoMap);
							wwpnPwwnMap = buildWwpnAndPwwn(wwpn, pwwn,
									wwpnPwwnMap);
						}
					}
				}
			} else if (hbaWwpnMap.size() == 2) {
				for (String hba_wwn : hba_wwn_list) {
					// 根据hbawwn获取卡槽位置
					String fcs = fcs_hbawwn_map.get(hba_wwn);
					// 获取当前vf关联存储前端口wwn
					List<String> fcpwwn_list = vfFcPwwnMap.get(vf_name);
					// 验证存储前端口wwn是否在选择的存储端口中
					if (!checkBrocadeFcPort(frontPortWwpnList, fcpwwn_list)) {
						throw new Exception("VF:" + vf_name
								+ "对应的存储前端口:[" + fcpwwn_list.toString()
								+ "]不在选择的存储前端口中["
								+ frontPortWwpnList.toString() + "]");
					}
					for (String pwwn : fcpwwn_list) {
						// 封装zone信息对象
						HashMap<String, Object> zoneInfoMap = buildBrocadeZone(
								hba_wwn, pwwn, fabric_name, fcs, hbaSwitchName,
								fcPortAlias, hbaVFName);
						zoneList.add(zoneInfoMap);
					}
				}
			}
			String zones = JSONObject.toJSONString(zoneList);
			fabric_zone_map.put(fabric_name, zones);
			log.info(" zones:" + zones.toString());
		}
		saveFabricZoneInfo(fabric_zone_map, contenxtParmas);
	}

	public boolean checkBrocadeFcPort(
			List<HashMap<String, String>> frontPortWwpnList,
			List<String> fcpwwn_list) {
		List<String> wwn_list = Lists.newArrayList();
		for (HashMap<String, String> fcportwwn : frontPortWwpnList) {
			for (Iterator<Entry<String, String>> itr = fcportwwn.entrySet()
					.iterator(); itr.hasNext();) {
				Entry<String, String> entry = itr.next();
				wwn_list.add(entry.getValue());
			}
		}

		for (String wwn : fcpwwn_list) {
			if (wwn_list.contains(wwn)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * @throws Exception 
	 * @Title: saveFabricZoneInfo
	 * @Description: 保存fabric-zone信息到工作流程实例表中
	 * @field: @param map
	 * @field: @param contenxtParmas
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void saveFabricZoneInfo(Map<String, String> map,
			Map<String, Object> contenxtParmas) throws Exception {

		System.out.println("this.getDeviceId()===" + this.getDeviceId());
		Map<String, Object> deviceMap = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(getDeviceId()));
		if (deviceMap == null) {
			throw new Exception("get deviceId:" + getDeviceId()
					+ " contenxtParmas is null!");
		}
		Object unit_info_obj = deviceMap.get(StorageConstants.UNIT_INFO);
		if (null == unit_info_obj) {
			errorMsg = "UNIT_INFO null";
			throw new Exception(errorMsg);
		}

		JSONObject unitObj = JSONObject.parseObject(String
				.valueOf(unit_info_obj));
		String fabric1_id = unitObj.getString("FABRIC1_ID");
		String fabric2_id = unitObj.getString("FABRIC2_ID");
		FabricVo fabricVo1 = storageDao.findFabricVoById(fabric1_id);
		FabricVo fabricVo2 = storageDao.findFabricVoById(fabric2_id);
		String fabric1_name = fabricVo1.getFabricName();
		String fabric2_name = fabricVo2.getFabricName();
		String fabric1_zone = map.get(fabric1_name);
		String fabric2_zone = map.get(fabric2_name);
		setHandleResultParam(String.valueOf(getDeviceId()),
				StorageConstants.FABRIC1, fabric1_zone);
		setHandleResultParam(String.valueOf(getDeviceId()),
				StorageConstants.FABRIC2, fabric2_zone);
		setHandleResultParam(String.valueOf(getDeviceId()),
				StorageConstants.SWITCH_TYPE, switch_type);
	}

	public Map<String, String> getVFFabricMapInfo(List<String> vfName_list) {
//		VsanDAO vsanDao = (VsanDAO) FrameworkContext.getApplicationContext()
//				.getBean("vsanDao");
		Map<String, String> vf_fabric_map = Maps.newHashMap();
		Map<String,String> paramMap = Maps.newHashMap();
		for (String vfName : vfName_list) {
			paramMap.put("vsanName", vfName);
			VsanPo po = storageDao.findVsanPoByParams(paramMap);
			vf_fabric_map.put(vfName, po.getFabricId());
		}
		return vf_fabric_map;
	}

	public void fabricHbaInfo(HashMap<String, String> vf_hba_map,
			HashMap<String, String> vf_fcPort_map,
			HashMap<String, String> fcPort_wwn_map,
			HashMap<String, String> fcs_hbawwn_map,
			HashMap<String, String> fcPortAlias,
			HashMap<String, String> hbaSwitchName) throws Exception {
		Iterator<Entry<String, List<String>>> fabric_vf_itr = fabric_vf_map
				.entrySet().iterator();
		Map<String, String> params = Maps.newHashMap();
		while (fabric_vf_itr.hasNext()) {
			Entry<String, List<String>> fabric_vf_entry = fabric_vf_itr.next();
			String fabricName = fabric_vf_entry.getKey();
			List<String> vf_list = fabric_vf_entry.getValue();
			for (String vfName : vf_list) {
				String hba_wwn = vf_hba_map.get(vfName);
				String fcs = fcs_hbawwn_map.get(hba_wwn);
				String fcPort_wwn = vf_fcPort_map.get(vfName);
				String fcPortName = fcPort_wwn_map.get(fcPort_wwn);
				// 根据fcs、fcportName、fabricName查询
				params.put("fcs", fcs);
				params.put("fcPortName", fcPortName);
				params.put("fabricName", fabricName);
				List<FcsPo> fcspo_list = storageDao.findFcsPoListByFabricName(params);
				if (fcspo_list == null || fcspo_list.isEmpty()) {
					throw new Exception("hba_wwn:" + hba_wwn
							+ " 与fcPort_wwn:" + fcPort_wwn + " 不在fabric:"
							+ fabricName + " 下的VF_NAME:" + vfName);
				}
				// 获取主机名
				CmVmPo vmDevPo = vmDao.findCmVmById(getDeviceId());
				String hostName = devDao.findCmDeviceById(vmDevPo.getHostId()).getDeviceName();
				Map<String, Object> zoneInfoMap = Maps.newHashMap();
				// 获取交换机名称
				String switchName = hbaSwitchName.get(hba_wwn);
				// 获取存储前端口别名
				String alias = fcPortAlias.get(fcPort_wwn);
				// 获取hba_wwn和fcport_wwn对应信息
				List<String> members = new ArrayList<String>();
				members.add(hba_wwn);
				members.add(fcPort_wwn);
				// 获取active_zoneset信息
				String active_zoneset = getActiveZoneset(fabricName, vfName);
				String zoneName = switchName + "-" + hostName + "H" + fcs + "-"
						+ alias;
				zoneInfoMap.put("name", zoneName);
				zoneInfoMap.put("members", members);
				zoneInfoMap.put("fbcName", fabricName);
				zoneInfoMap.put("vfName", vfName);
				zoneInfoMap.put("active_zoneset", active_zoneset);
			}
		}
	}

	/**
	 * @throws Exception 
	 * @Title: getFabricFcPwwn
	 * @Description: 只有2个hab wwn时获取指定FABRIC对应的存储端口wwn
	 * @field: @param faPortwwnFabric
	 * @field: @param fabricName
	 * @field: @param frontPortWwpnList
	 * @field: @return
	 * @return List<String>
	 * @throws
	 */
	public List<String> getFabricFcPwwn(
			HashMap<String, String> faPortwwnFabric, String fabricName,
			List<HashMap<String, String>> frontPortWwpnList) throws Exception {

		// 获取工作流参数中全部选择的端口wwn
		List<String> allfcPort_list = Lists.newArrayList();
		for (HashMap<String, String> map : frontPortWwpnList) {
			for (Iterator<Entry<String, String>> itr = map.entrySet()
					.iterator(); itr.hasNext();) {
				Entry<String, String> entry = itr.next();
				allfcPort_list.add(entry.getValue());
			}
		}
		// 获取当前fabric对应的存储前端端口wwn
		List<String> fcPortwwn = Lists.newArrayList();
		for (Iterator<Entry<String, String>> itr = faPortwwnFabric.entrySet()
				.iterator(); itr.hasNext();) {
			Entry<String, String> entry = itr.next();
			if (entry.getValue().equals(fabricName)) {
				if (allfcPort_list.contains(entry.getKey())) {
					fcPortwwn.add(entry.getKey());
				} else {
					throw new Exception("FABRIC:" + fabricName
							+ "的存储前端口wwn:" + entry.getValue() + "不在选择的存储端口组中"
							+ frontPortWwpnList.toString());
				}
			}
		}
		if (fcPortwwn != null) {
			return fcPortwwn;
		} else {
			throw new Exception("获取fabric的存储前端口错误！");
		}
	}

	public String getFcPortName(String pwwn,
			List<HashMap<String, String>> frontPortWwpnList) {
		for (HashMap<String, String> map : frontPortWwpnList) {
			Iterator<Entry<String, String>> itr = map.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, String> entry = itr.next();
				if (entry.getValue().equals(pwwn)) {
					return entry.getValue();
				} else {
					continue;
				}
			}
		}
		return null;
	}

	private HashMap<String, Object> buildZone(String wwpn, String pwwn,
			String fabricName, String fcs, Map<String, String> hbaSwitchMap,
			HashMap<String, String> fcPortAlias,
			HashMap<String, String> hbaVsanMap) throws Exception {
		CmVmPo vmDevPo = vmDao.findCmVmById(getDeviceId());
		String hostName = devDao.findCmDeviceById(vmDevPo.getHostId()).getDeviceName();
		
		if (hostName == null) {
			errorMsg = "获取主机名出错";
			throw new Exception(errorMsg);
		}
		HashMap<String, Object> zoneInfoMap = new HashMap<String, Object>();
		String switchName = hbaSwitchMap.get(wwpn);
		if(StringUtils.isBlank(switchName)){
			throw new Exception("获取hba_wwn："+wwpn+"对应switchName失败！");
		}
		String alias = fcPortAlias.get(pwwn);
		if(StringUtils.isBlank(alias)){
			throw new Exception("获取fa_wwn:"+pwwn+"对应alias失败！");
		}
		List<String> members = new ArrayList<String>();
		members.add(wwpn);
		members.add(pwwn);
		String zoneName = switchName + "-" + hostName + "H" + fcs + "-" + alias;
		zoneInfoMap.put("name", zoneName);
		zoneInfoMap.put("members", members);
		zoneInfoMap.put("fbcName", fabricName);
		zoneInfoMap.put("vsanID", hbaVsanMap.get(wwpn));
		result_sb.append(
				"vsanID:" + hbaVsanMap.get(wwpn) + "	zoneName:" + zoneName)
				.append("\n");
		result_sb.append("members:" + members.toString()).append("\n");
		return zoneInfoMap;
	}

	private HashMap<String, Object> buildBrocadeZone(String wwpn, String pwwn,
			String fabricName, String fcs, Map<String, String> hbaSwitchMap,
			HashMap<String, String> fcPortAlias,
			HashMap<String, String> hbaVFMap) throws Exception {
		CmVmPo vmDevPo = vmDao.findCmVmById(getDeviceId());
		String hostName = devDao.findCmDeviceById(vmDevPo.getHostId()).getDeviceName();
		
		if (hostName == null) {
			errorMsg = "获取主机名出错";
			throw new Exception(errorMsg);
		}
		HashMap<String, Object> zoneInfoMap = new HashMap<String, Object>();
		String switchName = hbaSwitchMap.get(wwpn);
		String alias = fcPortAlias.get(pwwn);
		List<String> members = new ArrayList<String>();
		members.add(wwpn);
		members.add(pwwn);
		String zoneName = switchName + "_" + hostName + "H" + fcs + "_" + alias;
		zoneInfoMap.put("name", zoneName);
		zoneInfoMap.put("members", members);
		zoneInfoMap.put("fbcName", fabricName);
		zoneInfoMap.put("vsanID", hbaVFMap.get(wwpn));
		result_sb.append(
				"vfName:" + hbaVFMap.get(wwpn) + "\nzoneName:" + zoneName)
				.append("\n");
		result_sb.append("members:" + members.toString()).append("\n");
		return zoneInfoMap;
	}

	/**
	 * @Title: checkWwpnAndPwwnVsanConsistent
	 * @Description: vsan是否在指定的fabric中 验证返回值中hba-wwn所在vsan，存储前端口所在vsan是否一致。
	 * @field: @param wwpn
	 * @field: @param pwwn
	 * @field: @param deviceId
	 * @field: @param hbaVsanMap
	 * @field: @param faPortVsanMap
	 * @field: @param fabricName
	 * @field: @return
	 * @return boolean
	 * @throws
	
	private boolean checkWwpnAndPwwnVsanConsistent(String wwpn, String pwwn,
			String deviceId, Map<String, String> hbaVsanMap,
			Map<String, String> faPortVsanMap, String fabricName) {
		List<String> vsanList = fabricParamsDao
				.findListByFabricName(fabricName);
		String pwwnVsan = faPortVsanMap.get(pwwn);
		String wwpnVsan = hbaVsanMap.get(wwpn);
		log.info("storage vsan:" + pwwnVsan);
		log.info("hba vsan:" + wwpnVsan);
		log.info("vsan list :" + vsanList.toString() + " in fabric : "
				+ fabricName);
		if (!(vsanList.contains(pwwnVsan) && vsanList.contains(wwpnVsan))) {
			return Boolean.FALSE;
		}
		return wwpnVsan.equalsIgnoreCase(pwwnVsan);
	}
 */
	/**
	 * @throws Exception 
	 * @Title: buildHbaWwpnMap
	 * @Description: 获取主机的HBA卡 fcs：wwn对映Map信息
	 * @field: @param contenxtParmas
	 * @field: @return
	 * @return HashMap<String,String>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, String> buildHbaWwpnMap(
			Map<String, Object> contenxtParmas) throws Exception {
		String deviceId = getDeviceId();
		Map<String, Object> deviceMap = (Map<String, Object>) contenxtParmas
				.get(String.valueOf(deviceId));
		Object hbaWwpnObj = deviceMap.get(StorageConstants.HBA_WWN);
		log.info("[HBA_WWN]:" + hbaWwpnObj + " in vm " + deviceId);
		if (hbaWwpnObj == null) {
			errorMsg = "[HBA_WWN]:" + hbaWwpnObj + " is null";
			throw new Exception(errorMsg);
		}
		HashMap<String, String> map = convertJson(hbaWwpnObj);
//		if (map.size() != StorageConstants.HBA_WWN_TWO && map.size()!=StorageConstants.HBA_WWN_FOUR){
//			errorMsg = "[HBA_WWN] size of vm " + deviceId + " is two or four";
//			throw new Exception(errorMsg);
//		}
		return map;
	}

	/**
	 * @throws Exception 
	 * @Title: buildFabricNameWwpnMap
	 * @Description: 构建 TreeMap<fabricName1:List<String> dev1_hba_wwpn_list>
	 *               <fabricName2:List<String> dev2_hba_wwpn_list> 列表树
	 * @field: @param map
	 * @field: @return
	 * @return TreeMap<String,List<String>>
	 * @throws
	 */
	private TreeMap<String, List<String>> buildFabricNameWwpnMap(
			HashMap<String, String> map) throws Exception {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		TreeMap<String, List<String>> fabricWwpnMap = new TreeMap<String, List<String>>();
		String value = null;
		String key = null;
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			value = entry.getValue();
			key = entry.getKey();
			if (key == null || value == null || "".equals(key)
					|| "".equals(value)) {
				errorMsg = " the hba wwpn and fabric name map contains null value";
				throw new Exception(errorMsg);
			}
			String addKey = convertFormat(key).toUpperCase();
			if (fabricWwpnMap.containsKey(value)) {
				fabricWwpnMap.get(value).add(addKey);
			} else {
				List<String> list = new ArrayList<String>();
				list.add(addKey);
				fabricWwpnMap.put(value, list);
			}
		}
		return fabricWwpnMap;
	}

	private TreeMap<String, List<String>> buildVFNameWwpnMap(
			HashMap<String, String> map) throws Exception {
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		TreeMap<String, List<String>> vfNameWwpnMap = new TreeMap<String, List<String>>();
		String value = null;
		String key = null;
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			value = entry.getValue();
			key = entry.getKey();
			if (key == null || value == null || "".equals(key)
					|| "".equals(value)) {
				errorMsg = " the hba wwpn and fabric name map contains null value";
				throw new Exception(errorMsg);
			}
			String addKey = convertFormat(key).toUpperCase();
			if (vfNameWwpnMap.containsKey(value)) {
				vfNameWwpnMap.get(value).add(addKey);
			} else {
				List<String> list = new ArrayList<String>();
				list.add(addKey);
				vfNameWwpnMap.put(value, list);
			}
		}
		return vfNameWwpnMap;
	}

	private HashMap<String, String> convertMap(Map<String, String> map,
			String name) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (map == null) {
			errorMsg = name + " map:is null";
			throw new Exception(errorMsg);
		}
		for (Iterator<Map.Entry<String, String>> it = map.entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			if (key == null || "".equals(key) || value == null
					|| "".equals(value)) {
				errorMsg = map.toString() + " contains null value";
				throw new Exception(errorMsg);
			}
			resultMap.put(convertFormat(key), entry.getValue());
		}
		return resultMap;
	}

	private void logVsanInfo(BodyDO body) throws Exception {
		if (switch_type.equals(StorageConstants.SWITCH_TYPE_CISCO)) {
			if (body.getHashMap("HBA_Vsan").isEmpty()
					|| body.getHashMap("HBA_SwithName").isEmpty()
					|| body.getHashMap("HBA_FbcName").isEmpty()
					|| body.getHashMap("FAPort_Alias").isEmpty()
					|| body.getHashMap("FAPort_Vsan").isEmpty()
					|| body.getHashMap("FAPort_FbcName").isEmpty()) {
				throw new Exception("获取zone返回信息错误！");
			}
			log.info("HBA_Vsan:" + body.getHashMap("HBA_Vsan").toString());
			log.info("HBA_SwithName:"
					+ body.getHashMap("HBA_SwithName").toString());
			log.info("HBA_FbcName:" + body.getHashMap("HBA_FbcName").toString());
			log.info("FAPort_Alias: "
					+ body.getHashMap("FAPort_Alias").toString());
			log.info("FAPort_Vsan: "
					+ body.getHashMap("FAPort_Vsan").toString());
			log.info("FAPort_FbcName: "
					+ body.getHashMap("FAPort_FbcName").toString());
		} else if (switch_type.equals(StorageConstants.SWITCH_TYPE_BROCADE)) {
			if (body.getHashMap("HBA_SWITCHNAME").isEmpty()
				|| body.getHashMap("HBA_VFNAME").isEmpty()
				|| body.getHashMap("FAPORT_ALIAS").isEmpty()
				|| body.getHashMap("FAPORT_VFNAME").isEmpty()) {
			throw new Exception("获取zone返回信息错误！");
		}
			log.info("HBA_SWITCHNAME:"
					+ body.getHashMap("HBA_SWITCHNAME").toString());
			log.info("HBA_VFNAME:" + body.getHashMap("HBA_VFNAME").toString());
			log.info("FAPORT_ALIAS:"
					+ body.getHashMap("FAPORT_ALIAS").toString());
			log.info("FAPORT_VFNAME:"
					+ body.getHashMap("FAPORT_VFNAME").toString());
			log.info("VF_NAME:"
					+ body.getList("VF_NAME").toString());
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

				List<String> deviceIdList = getDeviceIdList("",nodeId,rrinfoId,"");
				for (int i = 0; i < deviceIdList.size(); i++) {
					setDeviceId(deviceIdList.get(i));
					IDataObject requestDataObject = buildRequestData(contenxtParmas);

					IDataObject responseDataObject = getResAdpterInvoker().invoke(
							requestDataObject, getTimeOut());
					handleResonpse(contenxtParmas, responseDataObject);
					setHandleResultParam(String.valueOf(getDeviceId()),
							StorageConstants.DB_GENERATE_ZONE_FINISHED, "FINISHED");
				}
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
			rtn_map.put("exeMsg", result_sb.toString());
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null!");	
		}
		return JSON.toJSONString(rtn_map);
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
			throw new Exception(errorMsg,e);
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
	
	protected List<String> getFabricList(Map<String, Object> contextParams) throws Exception {
		String fabric1Name = null;
		String fabric2Name = null;
		try {
			Map<String, Object> deviceMap = (Map<String, Object>) contextParams
				.get(String.valueOf(getDeviceId()));
			Object unitObj = deviceMap.get("UNIT_INFO");
			JSONObject unitInfo = JSONObject.parseObject(String.valueOf(unitObj));
			String fabric1Id = unitInfo.getString("FABRIC1_ID");
			String fabric2Id = unitInfo.getString("FABRIC2_ID");
			Map<String, String> map = Maps.newHashMap();
			map.put("fabricId", fabric1Id);
			map.put("isActive", "Y");
			FabricVo vo1 = storageDao.findFabricVoById(fabric1Id);
			fabric1Name = vo1.getFabricName();

			Map<String, String> map1 = Maps.newHashMap();
			map1.put("fabricId", fabric2Id);
			map1.put("isActive", "Y");
			FabricVo vo2 = storageDao.findFabricVoById(fabric2Id);
			fabric2Name = vo2.getFabricName();

			if (!vo1.getManagerId().equals(vo2.getManagerId())) {
				throw new Exception(
						"Fabric1 and Fabric2 no the same storage manager id");
			}
		} catch (Exception e) {
			throw new Exception("Get Fabric Name List Failed "
					+ e.getMessage(),e);
		}
		return Arrays.asList(new String[] { fabric1Name, fabric2Name });
	}
	
	/**
	 * get FrontPortName_PWWN map from the contextParams
	 * 
	 * @param contenxtParmas
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	protected List<HashMap<String, String>> buildFrontPortWwpnList(
			Map<String, Object> contenxtParmas) throws Exception {
		Map<String, String> deviceParams = (HashMap<String, String>) contenxtParmas
				.get(String.valueOf(getDeviceId()));

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		List<String> faPwwnList = new ArrayList<String>();

		String portNamePwwn = deviceParams.get(StorageConstants.FA_WWNONE);
		if (StringUtils.isEmpty(portNamePwwn)) {
			String errorMsg = "[FA_WWN1]:" + portNamePwwn + " is null";
			throw new Exception(errorMsg);
		}
		log.info("[FA_WWN1]:" + portNamePwwn);
		faPwwnList.add(portNamePwwn);

		if (!StringUtils.isEmpty(deviceParams.get("STORAGE_SN2"))) {
			String portNamePwwn1 = deviceParams.get(StorageConstants.FA_WWNTWO);
			log.info("[FA_WWN2]:" + portNamePwwn1);
			if (!StringUtils.isEmpty(portNamePwwn1)) {
				faPwwnList.add(portNamePwwn1);
			}
		}

		for (int i = 0; i < faPwwnList.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			String faPwwn = faPwwnList.get(i);
			String[] array = faPwwn.substring(1, faPwwn.length() - 1)
					.split(",");
			for (int j = 0; j < array.length; j++) {
				int index = array[j].indexOf("=");
				String key = array[j].substring(0, index);
				String value = array[j].substring(index + 1);
				if ("".equals(key) || "".equals(value)) {
					String errorMsg = "[FA_WWN]:" + faPwwn
							+ " contain null value";
					throw new Exception(errorMsg);
				}

				map.put(key.trim().toUpperCase(), convertFormat(value));
			}
			list.add(map);
			if (map.size() != 6) {
				String errorMsg = "the size of FA_WWN is not four";
				throw new Exception(errorMsg);
			}
		}
		return list;
	}
	protected HashMap<String, List<String>> buildWwpnAndPwwn(String wwpn,
			String pwwn, HashMap<String, List<String>> map) {
		if (map.containsKey(wwpn) && map.get(wwpn).size() < 2) {
			map.get(wwpn).add(pwwn);
		} else {
			List<String> list = new ArrayList<String>();
			list.add(pwwn);
			map.put(wwpn, list);
		}
		return map;
	}
	protected boolean checkWwpnAndPwwn(String wwpn, String pwwn,
			HashMap<String, List<String>> map) {
		if (map.containsKey(wwpn)) {
			return map.get(wwpn).contains(pwwn);
		}
		return Boolean.FALSE;
	}
	
	protected HashMap<String, String> convertJson(Object hbaWwpnObj) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		log.info("HBA_WWN : " + hbaWwpnObj);
		if (hbaWwpnObj == null) {
			String errorMsg = "HBA_WWN is null value";
			throw new Exception(errorMsg);
		}
		String hbaWwpn = String.valueOf(hbaWwpnObj);
		JSONObject object;
		try {
			object = JSONObject.parseObject(hbaWwpn);
		} catch (Exception e) {
			String errorMsg = "convert HBA_WWN String into JSONObject Failed:"
					+ e.toString();
			throw new Exception(errorMsg);
		}
		Iterator<Entry<String, Object>> iterator = object.entrySet().iterator();
		Entry<String, Object> entry = null;
		String wwpn = null;
		String fcs = null;
		while (iterator.hasNext()) {
			entry = iterator.next();
			fcs = entry.getKey();
			wwpn = convertFormat(String.valueOf(entry.getValue()));
			if (wwpn.length() != 16) {
				String errorMsg = "[ vm hba wwpn ] :" + wwpn + " length error";
				throw new Exception(errorMsg);
			}
			if (!Pattern.matches("[0-9]", fcs)) {
				String errorMsg = "[ vm hba fcs ] :" + fcs + " format error";
				throw new Exception(errorMsg);
			}
			map.put(entry.getKey(), wwpn);
		}
		if (map.size() != StorageConstants.HBA_WWN_TWO && map.size()!=StorageConstants.HBA_WWN_FOUR) {
			String errorMsg = "HBA_WWN do not contain two or four hba wwpn";
			throw new Exception(errorMsg);
		}
		return map;
	}
}
