package com.git.cloud.handler.automation.se.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.support.ApplicationCtxUtil;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.BizParamInstService;
import com.git.cloud.handler.service.StorageService;
import com.git.cloud.handler.automation.se.common.StorageConstants;
import com.git.cloud.handler.automation.se.common.StorageDBBasicEnum;
import com.git.cloud.handler.automation.se.common.StorageDBHAEnum;
import com.git.cloud.handler.automation.se.common.StorageDBRACEnum;
import com.git.cloud.handler.automation.se.common.StorageDBSingleEnum;
import com.git.cloud.handler.automation.se.common.StorageFileBasicEnum;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.common.dao.impl.CmHostDAO;
import com.git.cloud.resmgt.common.dao.impl.CmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgRetCode;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @ClassName:DBStorageDataInitHandler
 * @Description:DB类型分配SAN存储初始化参数
 * @author chengbin.co
 * @date 2014-4-15 下午4:16:59
 *
 *
 */
public class DBStorageDataInitHandler extends LocalAbstractAutomationHandler {

	private String logMsg = null;
	private static Logger log = LoggerFactory
			.getLogger(DBStorageDataInitHandler.class);

	BizParamInstService bizParamService;
	private CmVmDAO vmDao = (CmVmDAO) WebApplicationManager.getBean("cmVmDAO");
	private CmHostDAO hostDao = (CmHostDAO) WebApplicationManager.getBean("cmHostDAO");
//	private CmDeviceDAO deviceDao=(CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");;
	StorageService storageService;
	AutomationService automationService;
	
	public DBStorageDataInitHandler() throws Exception{
		storageService=(StorageService) WebApplicationManager.getBean("storageService");
		bizParamService = getBizParamInstService();
		automationService = getAutomationService();
	}
	@Override
	public String service(Map<String, Object> contenxtParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute(HashMap<String, Object> contextParams) {

		String flowInstId = null;
		String nodeId = null;
		String rrinfoId = null;
		String srInfoId = null;
		List<String> deviceIdList = null;

		// 待保存的参数列表
		List<BizParamInstPo> paras = new ArrayList<BizParamInstPo>();

		try {
			if (bizParamService == null) {
				bizParamService = (BizParamInstService) WebApplicationManager.getBean("bizParamInstServiceImpl");
			}
//			if (deviceDao == null) {
//				deviceDao = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
//			}
			if (vmDao == null) {
				vmDao = (CmVmDAO)  WebApplicationManager.getBean("cmVmDAO");
			}
			if(storageService ==null){ 
				storageService = (StorageService) WebApplicationManager.getBean("storageService");
			}
			// 流程实例Id
			flowInstId = getContextStringPara(contextParams, FLOW_INST_ID);
			// 节点ID
			nodeId = getContextStringPara(contextParams, NODE_ID);
			// 资源请求ID
			rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
			srInfoId = getContextStringPara(contextParams, SRV_REQ_ID);

			logMsg = "初始化存储相关参数，流程实例ID：" + flowInstId + ",节点ID："
					+ nodeId + "。";
			long startTime = System.currentTimeMillis();
			log.info("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);

			// 读取设备列表
			deviceIdList = getDeviceIdList("","",rrinfoId,"");
			if (deviceIdList == null || deviceIdList.size() <= 0) {
				log.info("RRINFO_ID:"+"获取虚拟机设备ID信息为空");
				throw new Exception("Not found device.");
			}

			// 根据设备ID列表获取判断其为物理机还是虚拟机
			Map<String,String> dev_type_map = getdeviceTypeMap(deviceIdList);
			if(dev_type_map==null || dev_type_map.isEmpty()){
				throw new Exception("无法判断设备："+deviceIdList.toString()+"为虚拟机或物理机！");
			}
			// 获取数据中心信息
			RmDatacenterPo dcPo = getAutomationService().getDatacenter(rrinfoId);
			String dcenter_ename  = dcPo.getEname();
			if(StringUtils.isBlank(dcenter_ename)){
				throw new Exception("获取设备:"+deviceIdList.toString()+"数据中心信息失败！请检查所在资源池数据中心信息");
			}
			// 获取APP_ID,DU_ID
			Map<String,Object> app_du_map = storageService.getAppAndDuIdBySrIdRrInfoId(srInfoId,rrinfoId);
			if(app_du_map==null){
				throw new Exception("根据服务ID:["+srInfoId+"]获取服务器角色ID、系统ID信息错误！");
			}
			String app_id = String.valueOf(app_du_map.get("app_id"));
			if(app_id==null){
				throw new Exception("获取设备:"+deviceIdList.toString()+"APP_ID信息失败！");
			}
			String du_id = String.valueOf(app_du_map.get("du_id"));
			if(du_id==null){
				throw new Exception("获取设备:"+deviceIdList.toString()+"DU_ID信息失败！");
			}
			// 读取云服务属性表中的字段
			Map<String, String> attrs = automationService.getServiceAttr(rrinfoId);
			for(String key : attrs.keySet()){
				logMsg += "key:["+key+"]="+attrs.get(key)+"\n";
			}
			Map<String, String> type_attrs = storageService
					.getSeTypeAttrByRrinfoId(rrinfoId,
							StorageConstants.DATA_APP_TYPE_DB);
			String attrValue = null;
			BizParamInstPo para = null;
			String cluster_type = "";
			String cloud_service_type = "";
			for (String deviceId : deviceIdList) {
				//添加设备类型为物理机(HM)或虚拟机(VM)
				para = this.makePara(flowInstId, nodeId, deviceId, "DEV_TYPE", dev_type_map.get(deviceId));
				paras.add(para);
				
				// 数据中心英文名
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.DATACENTER_ENAME.name(),
						dcenter_ename);
				paras.add(para);
				
				// 数据使用方式
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageConstants.DATA_APP_TYPE, StorageConstants.DATA_APP_TYPE_DB);
				paras.add(para);
				
				// APP_ID
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.APP_ID.name(),
						app_id.toString());
				paras.add(para);
				// DU_ID
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.DU_ID.name(),
						du_id.toString());
				paras.add(para);

				// 数据使用类型 1.生产 ,2.应急,3.历史,4.归档,5.日志
				attrValue = getAttribute(attrs, StorageConstants.DATA_TYPE,
						true, "1");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageConstants.DATA_TYPE, attrValue);
				paras.add(para);

				// RESPONSE_TIME
				attrValue = getAttribute(attrs, StorageConstants.RESPONSE_TIME,
						true, "1");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageConstants.RESPONSE_TIME, attrValue);
				paras.add(para);

				// IOPS
				attrValue = getAttribute(attrs, StorageConstants.IOPS, true,
						"6000");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageConstants.IOPS, attrValue);
				paras.add(para);

				// MBPS
				attrValue = getAttribute(attrs, StorageConstants.MBPS, true,
						"3000");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageConstants.MBPS, attrValue);
				paras.add(para);

				// 申请DATA盘容量大小
				attrValue = getAttribute(attrs,
						StorageDBBasicEnum.DATA_DISK_CAPACITY.name(), true,
						"");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.DATA_DISK_CAPACITY.name(),
						attrValue);
				paras.add(para);

				// 申请ARCH盘容量大小
				attrValue = getAttribute(attrs,
						StorageDBBasicEnum.ARCH_DISK_CAPACITY.name(), true,
						"");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.ARCH_DISK_CAPACITY.name(),
						attrValue);
				paras.add(para);

				// 描述信息 生产 P 测试 T
				attrValue = getAttribute(attrs,
						StorageDBBasicEnum.DESCRIBE.name(), true, "P");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.DESCRIBE.name(), attrValue);
				paras.add(para);

				// 应用级别 A+ A+/A/B/C
				String appLevel = storageService.getApplicationLevel(rrinfoId);
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.APPLICATION_LEVEL.name(),
						appLevel);
				paras.add(para);

				// 服务器角色缩写
				Map<String, String> duInfoMap = storageService.getDuInfo(rrinfoId);
				String duName = duInfoMap.get("PROJECTABBR");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.PROJECTABBR.name(), duName);
				paras.add(para);

				// 服务器功能 AP\DB\RP
				String serverFunction = duInfoMap.get("SERVERFUNCTION");
				para = this.makePara(flowInstId, nodeId, deviceId,
						StorageDBBasicEnum.SERVERFUNCTION.name(),
						serverFunction);
				paras.add(para);

				// 获取服务器位置
//				String seatCode = getSeatCodeByDeviceId(deviceId);
//				para = this.makePara(flowInstId, nodeId, deviceId,
//						StorageDBBasicEnum.HOST_SEAT.toString(), seatCode);
//				paras.add(para);
				
				// 封装默认参数值为BizParamInstPo对象
				List<BizParamInstPo> list = makeTypeAttrPara(flowInstId,
						nodeId, deviceId, type_attrs);
				paras.addAll(list);
				
				cluster_type = type_attrs.get(StorageConstants.CLUSTER_TYPE);
				cloud_service_type = (String)type_attrs.get(StorageConstants.CLOUD_SERVICE_TYPE);

				if(cluster_type.equals(StorageConstants.CLUSTER_TYPE_RAC)){
					// NAS共享设备类型
					String os_name = type_attrs.get(StorageConstants.OS_NAME);
					String nas_share_type ="";
					if(os_name.equalsIgnoreCase("windows")){
						nas_share_type = StorageConstants.NAS_SHARE_TYPE_CIFS;
					}else{
						nas_share_type = StorageConstants.NAS_SHARE_TYPE_NFS;
					}
					para = this.makePara(flowInstId, nodeId, deviceId,
							StorageConstants.NAS_SHARE_TYPE,
							nas_share_type);
					paras.add(para);
					
					//NAS心跳盘挂载点
					if(cloud_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_STORAGE_INSTALL)){
						attrValue = getAttribute(attrs,
								StorageConstants.NAS_HEARTBEAT_MOUNTPOINT, true,
								"");
						if(attrValue.endsWith("//")){
							throw new Exception("输入[VOL_MOUNT_POINT]参数格式错误！");
						}else if(attrValue.endsWith("/")){
							attrValue = attrValue.substring(0,attrValue.length()-1);
						}
							
						para = this.makePara(flowInstId, nodeId,deviceId, StorageConstants.NAS_HEARTBEAT_MOUNTPOINT,  attrValue);
						paras.add(para);
					}
				}
			}
			for (BizParamInstPo po : paras) {
				log.info(po.toString());
			}
//			checkDBData(paras,cluster_type,cloud_service_type);
			// 保存参数
			bizParamService.saveParas(paras);

			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(
					getContextStringPara(contextParams,
							CloudGlobalConstants.BUS_VERSION), logMsg,
					MesgRetCode.SUCCESS);
		} catch (Exception e) {
			String errorMsg = logMsg + "错误：{" + e + "}";
			log.error(errorMsg);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			return getHandlerStringReturn(
					getContextStringPara(contextParams,
							CloudGlobalConstants.BUS_VERSION), errorMsg,
					MesgRetCode.ERR_OTHER);
		}
	}

	public List<BizParamInstPo> makeTypeAttrPara(String flowInstId, String nodeId,
			String vmId, Map<String, String> map) {
		List<BizParamInstPo> list = Lists.newArrayList();
		for (Iterator<Entry<String, String>> itr = map.entrySet().iterator(); itr
				.hasNext();) {
			Entry<String, String> entry = itr.next();
			BizParamInstPo po = new BizParamInstPo();
			po.setId(UUIDGenerator.getUUID());
			po.setDeviceId(vmId);
			po.setFlowInstId(flowInstId);
			po.setNodeId(nodeId);
			po.setParamKey(entry.getKey());
			po.setParamValue(entry.getValue());
			list.add(po);
		}

		return list;
	}
	public BizParamInstPo makePara(String flowInstId, String nodeId, String vmId,
			String key, String value) {
		log.debug("Create new parameter. key=value, " + key + "=" + value);
		BizParamInstPo para = new BizParamInstPo();
		para.setId(UUIDGenerator.getUUID());
		para.setDeviceId(vmId);
		para.setFlowInstId(flowInstId);
		para.setNodeId(nodeId);
		para.setParamKey(key);
		para.setParamValue(value);
		return para;
	}
	/**
	 * 读取服务属性
	 * 
	 * @param attrs
	 * @param key
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	private String getAttribute(Map<String, String> attrs, String key,
			boolean allowNull, String defaultValue) throws Exception {
		String attrValue = null;
		if (attrs == null) {
			throw new Exception("The rrinfo attrs is null.");
		}
		attrValue = attrs.get(key);
		// 如果map中的值为空，则将缺省值赋予
		if (StringUtils.isEmpty(attrValue)) {
			attrValue = defaultValue;
		}
		// 如果不允许为空且值为空，则抛出异常
		if (!allowNull && attrValue == null) {
			throw new Exception("Not found para: " + key);
		}
		return attrValue;
	}

//	private String getSeatCodeByDeviceId(Long deviceId) {
//
//		String seatCode = "";
//		// 获取服务器位置
//		seatCode = vmDao.getHostNameByVmId(deviceId);
//		if ("".equals(seatCode) || null == seatCode) {
//			seatCode = deviceDao.findById(deviceId).getSeatCode();
//		}
//		return seatCode;
//	}

	/**
	 * @throws Exception 
	 * @Title: checkParam
	 * @Description: 根据数据类型和集群类型参数，获取相应参数枚举类，查看相关参数是否完整获取
	 * @field: @param map 新增流程实例参数
	 * @field: @param os_type 操作类型
	 * @field: @param data_app_type 数据使用类型
	 * @field: @param cluster_type 集群类型
	 * @field: @return
	 * @return boolean
	 * @throws
	 */
	public boolean checkParam(Map<String, String> map, String os_type,
			String data_app_type, String cluster_type) throws Exception {
		try {
			if (data_app_type.equals(StorageConstants.DATA_APP_TYPE_DB)) {
				StorageDBBasicEnum[] array = StorageDBBasicEnum.values();
				for (StorageDBBasicEnum e : array) {
					if (map.containsKey(e.name())) {
						String value = map.get(e.name());
						if (null == value || "".equals(value)) {
							throw new Exception(
									"error DB parameter：" + e.name()
											+ "is null!");
						} else {
							continue;
						}
					} else {
						throw new Exception("error " + e.name()
								+ " not in DB parameter!");
					}
				}

				String className = "com.git.cloud.handler.automation.se.common.Storage"
						+ data_app_type + cluster_type + "Enum";
				Class<?> c = Class.forName(className);
				Object[] c_array = c.getEnumConstants();
				for (Object e : c_array) {
					if (e.toString().equals(StorageConstants.RES_POOL_LEVEL))
						continue;
					if (map.containsKey(e.toString())) {
						String value = map.get(e.toString());
						if (null == value || "".equals(value)) {
							throw new Exception(
									"error DB parameter：" + e.toString()
											+ "is null!");
						} else {
							continue;
						}
					} else {
						throw new Exception("error "
								+ e.toString() + " not in DB parameter!");
					}
				}
			} else if (data_app_type
					.equals(StorageConstants.DATA_APP_TYPE_FILE)) {
				StorageFileBasicEnum[] array = StorageFileBasicEnum.values();
				for (StorageFileBasicEnum e : array) {
					if (e.name().equals(StorageConstants.RES_POOL_LEVEL))
						continue;
					if (map.containsKey(e.name())) {
						String value = map.get(e.name());
						if (null == value || "".equals(value)) {
							throw new Exception(
									"error FILE parameter：" + e.name()
											+ "is null!");
						} else {
							continue;
						}
					} else {
						throw new Exception("error " + e.name()
								+ " not in FILE parameter!");
					}
				}
			} else {
				throw new Exception("没有" + os_type + "-"
						+ cluster_type + "枚举类");
			}
		} catch (ClassNotFoundException e) {
			throw new Exception("获取枚举类失败！");
		}
		return true;
	}
	
	
	public Map<String,String> getdeviceTypeMap(List<String> deviceIdList) throws Exception{
				Map<String,String> map = Maps.newHashMap();
		for(String deviceId:deviceIdList){
			//首先查询是否为虚拟机
			CmVmPo vmPo = vmDao.findCmVmById(deviceId);
			if(null!=vmPo){
				//此主机为虚拟机
				map.put(deviceId, "VM");
			}else {
				CmHostPo hostPo = hostDao.findCmHostById(deviceId);
				if(null!=hostPo){
					//此主机为物理机
					map.put(deviceId, "HM");
				}else {
					throw new Exception("deviceId:"+deviceId+"虚拟机或物理机信息失败！");
				}
			}
		}
		return map;
	}
	
	public void checkDBData(List<BizParamInstPo> paras,String cluster_type,String cloude_service_type) throws Exception{
		//logMsg += "初始化参数为：\n";
		StorageDBBasicEnum[] array = StorageDBBasicEnum.values();
		for(int i=0;i<array.length;i++){
			String key = array[i].name();
			boolean flag = true;
			for(BizParamInstPo po:paras){
				if(key.equals(po.getParamKey())){
					flag = false;
					if(StringUtils.isNotBlank(po.getParamValue())){
						log.info("key:["+key+"]="+po.getParamValue());
						break;
					}else{
						throw new Exception("获取["+key+"]参数错误!");
					}
				}
			}
			if(flag){
				throw new Exception("获取["+key+"]参数错误!");
			}
		}
		if(cluster_type.equals(StorageConstants.CLUSTER_TYPE_RAC)){
			StorageDBRACEnum[] rac_array = StorageDBRACEnum.values();
			for(int i=0;i<rac_array.length;i++){
				String key = rac_array[i].name();
				boolean flag = true;
				for(BizParamInstPo po:paras){
					if(key.equals(po.getParamKey())){
						flag = false;
						if(StringUtils.isNotBlank(po.getParamValue())){						
							log.info("key:["+key+"]="+po.getParamValue());
							break;
						}else{
							if(cloude_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_VM_INSTALL)){
								continue;
							}
							throw new Exception("获取["+key+"]参数错误!");
						}
					}
				}
				if(flag){
					if(cloude_service_type.equals(StorageConstants.CLOUD_SERVICE_TYPE_VM_INSTALL)){
						continue;
					}
					throw new Exception("获取["+key+"]参数错误!");
				}
			}
		}else if(cluster_type.equals(StorageConstants.CLUSTER_TYPE_HA)){
			StorageDBHAEnum[] ha_array = StorageDBHAEnum.values();
			for(int i=0;i<ha_array.length;i++){
				String key = ha_array[i].name();
				boolean flag = true;
				for(BizParamInstPo po:paras){
					if(key.equals(po.getParamKey())){
						flag = false;
						if(StringUtils.isNotBlank(po.getParamValue())){				
							log.info("key:["+key+"]="+po.getParamValue());
							break;
						}else{
							throw new Exception("获取["+key+"]参数错误!");
						}
					}
				}
				if(flag){
					throw new Exception("获取["+key+"]参数错误!");
				}
			}
		}else if(cluster_type.equals(StorageConstants.CLUSTER_TYPE_SINGLE)){
			StorageDBSingleEnum[] single_array = StorageDBSingleEnum.values();
			for(int i=0;i<single_array.length;i++){
				String key = single_array[i].name();
				boolean flag = true;
				for(BizParamInstPo po:paras){
					if(key.equals(po.getParamKey())){
						flag = false;
						if(StringUtils.isNotBlank(po.getParamValue())){				
							log.info("key:["+key+"]="+po.getParamValue());
							break;
						}else{
							throw new Exception("获取["+key+"]参数错误!");
						}
					}
				}
				if(flag){
					throw new Exception("获取["+key+"]参数错误!");
				}
			}
		}
	}

}
