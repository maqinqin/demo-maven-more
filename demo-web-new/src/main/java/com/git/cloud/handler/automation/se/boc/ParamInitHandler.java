package com.git.cloud.handler.automation.se.boc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.appmgt.model.po.AppSysPO;
import com.git.cloud.handler.automation.LocalAbstractAutomationHandler;
import com.git.cloud.handler.automation.se.common.StorageDBBasicEnum;
import com.git.cloud.handler.po.BizParamInstPo;
import com.git.cloud.handler.service.BizParamInstService;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgRetCode;
import com.google.common.collect.Maps;

public class ParamInitHandler extends LocalAbstractAutomationHandler {

	private static Logger logger = LoggerFactory.getLogger(ParamInitHandler.class);
	
	public String execute(HashMap<String, Object> contenxtParams) throws Exception {
		Map<String, Object> rtn_map = Maps.newHashMap();
		List<BizParamInstPo> paramList = new ArrayList<BizParamInstPo>();
		List<String> deviceIdList = null;
		String commonInfo = "";
		try {
			// 流程实例Id
			String flowInstId = (String) contenxtParams.get(SeConstants.FLOW_INST_ID);
			// 流程节点Id
			String nodeId = (String) contenxtParams.get(SeConstants.NODE_ID);
			// 服务请求Id
			String srvReqId = (String) contenxtParams.get(SeConstants.SRV_REQ_ID);
			// 资源请求ID
			String rrinfoId = (String) contenxtParams.get(SeConstants.RRINFO_ID);
			commonInfo = ",服务请求ID:" + srvReqId + ",资源请求ID:" + rrinfoId + ",流程实例ID:" + flowInstId;
			logger.info("[ParamInitHandler]构建所需参数实例化开始" + commonInfo);
			// 获取数据中心信息
			deviceIdList = getDeviceIdList("","",rrinfoId,"");
			if (deviceIdList == null || deviceIdList.size() <= 0) {
				logger.info("RRINFO_ID:"+"获取虚拟机设备ID信息为空");
				throw new Exception("Not found device.");
			}
			RmDatacenterPo dcPo = getAutomationService().getDatacenter(rrinfoId);
			String dcenter_ename  = dcPo.getEname();
			if(StringUtils.isBlank(dcenter_ename)) {
				throw new Exception("获取设备:"+deviceIdList.toString()+"数据中心信息失败！请检查所在资源池数据中心信息");
			}
			AppSysPO appSys = getAutomationService().findAppInfoBySrId(srvReqId);
			if(appSys == null || StringUtils.isBlank(appSys.getEname())) {
				throw new Exception("获取设备:"+deviceIdList.toString()+"应用系统信息失败！");
			}
			String appEname = appSys.getEname().toUpperCase();
			BizParamInstPo param = null;
			String attrValue;
			Map<String, String> attrs = getAutomationService().getServiceAttr(rrinfoId);
			if(attrs == null || attrs.size() == 0) {
				attrs = getAutomationService().getServiceAttrDevice(rrinfoId, "");
			}
			for(int i=0 ; i<deviceIdList.size() ; i++) {
				String deviceId = deviceIdList.get(i);
				param = this.makePara(flowInstId, nodeId, deviceId, StorageDBBasicEnum.DATACENTER_ENAME.name(), dcenter_ename);
				paramList.add(param);
				param = this.makePara(flowInstId, nodeId, deviceId, "APP_ENAME", appEname);
				paramList.add(param);
				// 存储单双边
				attrValue = getAttribute(attrs, SeConstants.STORAGE_MIRROR, true, "enable");
				param = this.makePara(flowInstId, nodeId, deviceId, SeConstants.STORAGE_MIRROR, attrValue);
				paramList.add(param);
				// 申请DATA盘容量大小
				attrValue = getAttribute(attrs, StorageDBBasicEnum.DATA_DISK_CAPACITY.name(), true, "");
				param = this.makePara(flowInstId, nodeId, deviceId, StorageDBBasicEnum.DATA_DISK_CAPACITY.name(), attrValue);
				paramList.add(param);
				// 申请ARCH盘容量大小
				attrValue = getAttribute(attrs, StorageDBBasicEnum.ARCH_DISK_CAPACITY.name(), true, "");
				param = this.makePara(flowInstId, nodeId, deviceId, StorageDBBasicEnum.ARCH_DISK_CAPACITY.name(), attrValue);
				paramList.add(param);
				// 第一个存储SN
				param = this.makePara(flowInstId, nodeId, deviceId, "STORAGE_SN1", "451519000027");
				paramList.add(param);
				// 第二个存储SN
				param = this.makePara(flowInstId, nodeId, deviceId, "STORAGE_SN2", "451519000026");
				paramList.add(param);
				// 第一个存储名称
				param = this.makePara(flowInstId, nodeId, deviceId, "STORAGE_NAME1", "FAS8060A-2E");
				paramList.add(param);
				// 第二个存储名称
				param = this.makePara(flowInstId, nodeId, deviceId, "STORAGE_NAME2", "FAS8060B-2E");
				paramList.add(param);
				// 第一个存储ID
				param = this.makePara(flowInstId, nodeId, deviceId, "STORAGE_ID1", "1461D733E07248AEA3AB9DEAC1DA533A");
				paramList.add(param);
				// 第二个存储ID
				param = this.makePara(flowInstId, nodeId, deviceId, "STORAGE_ID2", "1461D733E072484EA38B9DEDC1DA5330");
				paramList.add(param);
				// FABRIC管理机ID
				param = this.makePara(flowInstId, nodeId, deviceId, "FABRIC_MGR_ID", "BROCADE-MGR-ID");
				paramList.add(param);
				// 系统类型
				param = this.makePara(flowInstId, nodeId, deviceId, SeConstants.OS_TYPE, "aix");
				paramList.add(param);
				// 存储类型
				param = this.makePara(flowInstId, nodeId, deviceId, SeConstants.STORAGE_MODEL, "FAS");
				paramList.add(param);
			}
			for (BizParamInstPo po : paramList) {
				logger.info(po.toString());
			}
			// 保存参数
			BizParamInstService bizParamService = getBizParamInstService();
			bizParamService.saveParas(paramList);
			logger.info("[ParamInitHandler]构建所需参数实例化结束" + commonInfo);
			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", "init success!");
		} catch(Exception e) {
			logger.error("异常exception",e);
			rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
			String errorMsg = "[ParamInitHandler]构建所需参数实例化出现异常，" + e + commonInfo;
			rtn_map.put("exeMsg", errorMsg + "，" + e.getMessage());
			logger.error(errorMsg);
		}
		return JSON.toJSONString(rtn_map);
	}

	@Override
	public String service(Map<String, Object> contenxtParams) throws Exception {
		return null;
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
}

