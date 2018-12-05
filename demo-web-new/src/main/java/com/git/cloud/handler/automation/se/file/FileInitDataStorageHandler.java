package com.git.cloud.handler.automation.se.file;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.common.support.ApplicationCtxUtil;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.handler.service.AutomationService;
import com.git.cloud.handler.service.BizParamInstService;
import com.git.cloud.handler.service.StorageService;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgRetCode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @ClassName:FileInitDataStorageHandler
 * @Description:初始化
 * @author chengbin
 * @date 2014-9-25 上午10:54:35
 *
 *
 */
public class FileInitDataStorageHandler extends LocalAbstractAutomationHandler{

	private static Logger log = LoggerFactory
			.getLogger(FileInitDataStorageHandler.class);
	//AutomationCommonDAO automationCommonDAO;
//	BizParamInstService bizParamService;
//	AutomationCommonDAO automationCommonDAO;
//	IBizParamService bizParamService;
//	IInitDataStorageService initDataStorageService;
	BizParamInstService bizParamInstService;
	AutomationService automationService;
	StorageService storageService;
	private String logMsg;
	
	public FileInitDataStorageHandler() throws Exception{
		storageService=(StorageService) ApplicationCtxUtil.getBean("storageService");
		bizParamInstService = getBizParamInstService();
		automationService = getAutomationService();
	}
	@Override
	public String execute(HashMap<String, Object> contenxtParmas)
			throws Exception {
		String flowInstId = null;
		String nodeId = null;
		String rrinfoId = null;
		String srvReqId = null;
		List<String> deviceIdList = null;
		// 待保存的参数列表
		List<BizParamInstPo> paras = Lists.newArrayList();
		
		Map<String, Object> rtn_map = Maps.newHashMap();
		try{
			//流程实例Id
			flowInstId = (String) contenxtParmas.get(FLOW_INST_ID);

			// 服务请求Id
			srvReqId = (String) contenxtParmas.get(SRV_REQ_ID);

			// 资源请求ID
			rrinfoId = (String) contenxtParmas.get(RRINFO_ID);
			
			deviceIdList = getDeviceIdList("","",rrinfoId,"");
			if (deviceIdList == null || deviceIdList.size() <= 0) {
				throw new Exception("Not found device.");
			}
			// 根据设备ID列表获取判断其为物理机还是虚拟机
			Map<String,String> dev_type_map = getdeviceTypeMap(deviceIdList);
			
			// 获取数据中心信息
			RmDatacenterPo datacenter = getAutomationService().getDatacenter(rrinfoId);

			Map<String,Object> app_du_map = storageService.getAppAndDuIdBySrIdRrInfoId(srvReqId,rrinfoId);

			if(app_du_map==null){
				throw new Exception("根据服务ID:["+srvReqId+"]获取服务器角色ID、系统ID信息错误！");
			}
			String app_id = (String)app_du_map.get("APP_ID");
			if(StringUtils.isBlank(app_id)){
				throw new Exception("获取设备:"+deviceIdList.toString()+"APP_ID信息失败！");
			}
			String du_id = (String)app_du_map.get("DU_ID");
			if(StringUtils.isBlank(du_id)){
				throw new Exception("获取设备:"+deviceIdList.toString()+"DU_ID信息失败！");
			}
			
			// 读取云服务属性表中的字段
			Map<String, String> attrs = automationService.getServiceAttr(rrinfoId);
			
			// 读取存储云服务默认属性表中的字段信息
			Map<String, String> type_attrs = storageService.getSeTypeAttrByRrinfoId(rrinfoId,"FILE");
			
			String attrValue = null;
			BizParamInstPo para = null;
			

			for (String deviceId : deviceIdList) {
				//添加设备类型为物理机(HM)或虚拟机(VM)
				para = this.makePara(flowInstId, nodeId, deviceId, "DEV_TYPE", dev_type_map.get(deviceId));
				paras.add(para);
				
				// 数据中心英文名
				para = this.makePara(flowInstId, nodeId, deviceId,"DATACENTER_ENAME",datacenter.getEname());
				paras.add(para);
				// 数据使用方式
				para = this.makePara(flowInstId, nodeId, deviceId,"DATA_APP_TYPE", "FILE");
				paras.add(para);
				
				// APP_ID
				para = this.makePara(flowInstId, nodeId, deviceId,"APP_ID",app_id);
				paras.add(para);

				// DU_ID
				para = this.makePara(flowInstId, nodeId, deviceId,"DU_ID",du_id.toString());
				paras.add(para);
				// 数据使用类型 1.生产 ,2.应急,3.历史,4.归档,5.日志
				attrValue = getAttribute(attrs, "DATA_TYPE",true, "1");
				para = this.makePara(flowInstId, nodeId, deviceId,"DATA_TYPE", attrValue);
				paras.add(para);
				
				// RESPONSE_TIME
				attrValue = getAttribute(attrs, "RESPONSE_TIME",true, "1");
				para = this.makePara(flowInstId, nodeId, deviceId,"RESPONSE_TIME", attrValue);
				paras.add(para);
				
				// IOPS
				attrValue = getAttribute(attrs, "IOPS", true,"6000");
				para = this.makePara(flowInstId, nodeId, deviceId,"IOPS", attrValue);
				paras.add(para);

				// MBPS
				attrValue = getAttribute(attrs, "MBPS", true,"3000");
				para = this.makePara(flowInstId, nodeId, deviceId,"MBPS", attrValue);
				paras.add(para);
				
				// 服务器角色缩写
				Map<String, String> duInfoMap = storageService.getDuInfo(rrinfoId);
				String duName = duInfoMap.get("PROJECTABBR");
				para = this.makePara(flowInstId, nodeId, deviceId,"PROJECTABBR", duName);
				paras.add(para);

				// 服务器功能 AP\DB\RP
				String serverFunction = duInfoMap.get("SERVERFUNCTION");
				para = this.makePara(flowInstId, nodeId, deviceId,"SERVERFUNCTION",serverFunction);
				paras.add(para);
				
				// 描述信息 生产 P 测试 T
				attrValue = getAttribute(attrs,"DESCRIBE", true, "P");
				para = this.makePara(flowInstId, nodeId, deviceId,"DESCRIBE", attrValue);
				paras.add(para);

				// 应用级别 A+ A+/A/B/C
				String appLevel = storageService.getApplicationLevel(rrinfoId);
				para = this.makePara(flowInstId, nodeId, deviceId,"APPLICATION_LEVEL",appLevel);
				paras.add(para);
				
				// 获取服务器位置
//				String seatCode = getSeatCodeByDeviceId(deviceId);
//				para = this.makePara(flowInstId, nodeId, deviceId,
//						StorageFileBasicEnum.HOST_SEAT.name(), seatCode);
//				paras.add(para);
				
				//挂载点
				attrValue = getAttribute(attrs, "VOL_MOUNT_POINT", true,"");
				if(attrValue.endsWith("//")){
					throw new Exception("输入[VOL_MOUNT_POINT]参数格式错误！");
				}else if(attrValue.endsWith("/")){
					attrValue = attrValue.substring(0,attrValue.length()-1);
				}
				para = this.makePara(flowInstId, nodeId, deviceId,"VOL_MOUNT_POINT", attrValue);
				paras.add(para);
				
				//是否共享
				attrValue = getAttribute(attrs, "IS_VOL_SHARED", true,"");
				para = this.makePara(flowInstId, nodeId, deviceId,"IS_VOL_SHARED", attrValue);
				paras.add(para);
				
				//共享的卷
				attrValue = getAttribute(attrs, "VOL_SHARE", true,"");
				para = this.makePara(flowInstId, nodeId, deviceId,"VOL_SHARE", attrValue);
				paras.add(para);
				
				//申请大小
				attrValue = getAttribute(attrs, "CREATE_VOL_CAPACITY", true,
						"");
				para = this.makePara(flowInstId, nodeId, deviceId,"CREATE_VOL_CAPACITY", attrValue);
				paras.add(para);
				
				//交易类型
				attrValue = getAttribute(attrs, "TRADE_TYPE", true,
						"");
				para = this.makePara(flowInstId, nodeId, deviceId,"TRADE_TYPE", attrValue);
				paras.add(para);
				
				// 封装默认参数值为BizParamInstPo对象
				List<BizParamInstPo> list = makeTypeAttrPara(flowInstId,
						nodeId, deviceId, type_attrs);
				paras.addAll(list);

				// NAS共享设备类型
				String os_name = type_attrs.get("OS_NAME");
				String nas_share_type ="";
				if(os_name.equalsIgnoreCase("windows")){
					nas_share_type = "CIFS";
				}else{
					nas_share_type = "NFS";
				}
				para = this.makePara(flowInstId, nodeId, deviceId,"NAS_SHARE_TYPE",
						nas_share_type);
				paras.add(para);
				
			}
			for (BizParamInstPo po : paras) {
				log.debug(po.toString());
			}
			String is_vol_shared = getisVolShared(paras);
			bizParamInstService.saveParas(paras);
			Map<String,String> exeParams = Maps.newHashMap();
			exeParams.put("IS_VOL_SHARED", is_vol_shared);
			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", logMsg);
			rtn_map.put("exeParams", exeParams);
			return JSON.toJSONString(rtn_map);
		}catch(Exception e){
			String errorMsg = logMsg + "错误：{" + e.getMessage() + "}";
			log.error(errorMsg,e);
			printExceptionStack(e);
			// 构造工作流新版本的返回值
			rtn_map.put("checkCode", MesgRetCode.ERR_OTHER);
			rtn_map.put("exeMsg", errorMsg);
			return JSON.toJSONString(rtn_map);
		}
	}

	private Map<String, String> getdeviceTypeMap(List<String> deviceIdList) {
		Map<String,String> map = Maps.newHashMap();
		for(String deviceId:deviceIdList){
			map.put(deviceId, "VM");
		}
		return map;
	}

	@Override
	public String service(Map<String, Object> contenxtParams) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
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
	
	public List<BizParamInstPo> makeTypeAttrPara(String flowInstId, String nodeId,
			String vmId, Map<String, String> map) {
		List<BizParamInstPo> list = Lists.newArrayList();
		for (Iterator<Entry<String, String>> itr = map.entrySet().iterator(); itr
				.hasNext();) {
			Entry<String, String> entry = itr.next();
			BizParamInstPo po = new BizParamInstPo();
			po.setDeviceId(vmId);
			po.setFlowInstId(flowInstId);
			po.setNodeId(nodeId);
			po.setParamKey(entry.getKey());
			po.setParamValue(entry.getValue());
			list.add(po);
		}

		return list;
	}
	
	public String getisVolShared(List<BizParamInstPo> paras) throws Exception{
		String is_vol_shared = "";
		String vol_share = "";
		String vol_capacity = "";
		for(BizParamInstPo po:paras){
			if(po.getParamKey().equals("IS_VOL_SHARED")){
				is_vol_shared = po.getParamValue();
				log.info("key:[IS_VOL_SHARED]="+is_vol_shared);
				logMsg += "key:[IS_VOL_SHARED]="+is_vol_shared+"\n";
			}else if(po.getParamKey().equals("VOL_SHARE")){
				vol_share = po.getParamValue();
				log.info("key:[VOL_SHARE]="+vol_share);
				logMsg += "key:[VOL_SHARE]="+vol_share+"\n";
			}else if(po.getParamKey().equals("CREATE_VOL_CAPACITY")){
				vol_capacity = po.getParamValue();
				log.info("key:[CREATE_VOL_CAPACITY]="+vol_capacity);
				logMsg += "key:[CREATE_VOL_CAPACITY]="+vol_capacity+"\n";
			}
		}
		if(StringUtils.isBlank(is_vol_shared)){
			throw new Exception("获取[IS_VOL_SHARED]参数错误！");
		}
		if(is_vol_shared.equals("Y")){
			if(StringUtils.isBlank(vol_share)){
				throw new Exception("获取[VOL_SHARE]参数错误！");
			}
		}else if(is_vol_shared.equals("N")){
			if(StringUtils.isBlank(vol_capacity)){
				throw new Exception("获取[CREATE_VOL_CAPACITY]参数错误！");
			}
		}
		return is_vol_shared;
	}
}
