package com.git.cloud.handler.automation.sa.kvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMOpration;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @ClassName:DestoryKVMHandler
 * @Description:删除KVM
 * @author chengbin
 * @date 2014-12-11 上午11:45:55
 * 
 * 
 */
public class DestoryKVMHandler extends RemoteAbstractAutomationHandler {

	private String rrinfoId = "";
	private StringBuffer rtn_sb = new StringBuffer();
	private static Logger log = LoggerFactory.getLogger(DestoryKVMHandler.class);

	@Override
	public String execute(HashMap<String, Object> contextParams) throws Exception {
		Map<String, Object> rtn_map = new HashMap<String, Object>();
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contextParams.get(NODE_ID); // 要根据NODE_ID在工作流配置表中查到当前节点的业务所需参数
			// 资源请求ID
			rrinfoId = (String) contextParams.get(RRINFO_ID);

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			try {

				Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);

				contextParams.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contextParams);

				if (extHandleParams != null)
					contextParams.putAll(extHandleParams);

				IDataObject responseDataObject = null;
				IDataObject requestDataObject = buildRequestData(contextParams);
				responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());
				handleResonpse(contextParams, responseDataObject);

			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:" + nodeId + ".错误信息:" + e.getMessage();
				log.error(errorMsg, e);
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", e.getMessage());
				return JSON.toJSONString(rtn_map);
			} finally {
				if (contextParams != null)
					contextParams.clear();
			}

			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。",
					new Object[] { flowInstId, nodeId, new Long((System.currentTimeMillis() - startTime)) });
			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", "回收设备:" + rtn_sb.toString() + "成功！");
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "contextParams is null！");
		}
		return JSON.toJSONString(rtn_map);
	}

	@SuppressWarnings({ "unchecked", "null" })
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		rtn_sb.append(deviceIdList.toString());
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		try {
			// 增加数据中心路由标识
			String queueIdentify = this.getQueueIdent(rrinfoId);
			header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		} catch (Exception e) {
			throw new Exception("get LinkRouteType error!", e);
		}

		header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
		// header.setOperation(VMOpration.DESTORY_RESOURCE_LIST);
		header.setOperationBean("kvmDestoryResource");
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();

		HashMap<String, Object> devInfo = (HashMap<String, Object>) contenxtParmas.get(deviceIdList.get(0));

		
		body.setString(GeneralKeyField.VM.VM_TYPE, (String) devInfo.get(GeneralKeyField.VM.VM_TYPE));
		String vmNameListStr = (String) devInfo.get(GeneralKeyField.Recycling.DESTORY_RESOURCE_RECS);
		List<String> vmNameList = JSONObject.parseArray(vmNameListStr, String.class);
		//body.setList(GeneralKeyField.Recycling.DESTORY_RESOURCE_RECS, vmNameList);
		// 存储资源池的名称
		//body.setString(GeneralKeyField.KVM.STORAGEPOOL_NAME, (String) devInfo.get(GeneralKeyField.KVM.STORAGEPOOL_NAME));
		
		//add by wmy2016/03/18
		List<String> destroyVms = null;
		Map<String,List<String>> map =Maps.newHashMap();
		for(String info : vmNameList){
			String[] data = info.split(",");
			String vmName = data[0];
			String hostIp = data[1];
			String datastoreName = data[2];
			String value = vmName + "：" + datastoreName;
			destroyVms = map.get(hostIp);
			if (destroyVms == null) {
				destroyVms = new ArrayList<String>();
				map.put(hostIp, destroyVms);
			}
			destroyVms.add(value);
			
		}
		body.setHashMap(GeneralKeyField.KVM.URL, (HashMap) map);
		//body.setString(GeneralKeyField.KVM.URL, (String) devInfo.get(GeneralKeyField.KVM.URL));
		//body.setList(GeneralKeyField.Recycling.DESTORY_RESOURCE_RECS, vmList);
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) throws Exception {

		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		log.info(header.getRetMesg());

		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}

	}
}
