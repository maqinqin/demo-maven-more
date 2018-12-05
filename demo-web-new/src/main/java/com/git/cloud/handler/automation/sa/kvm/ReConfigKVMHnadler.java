package com.git.cloud.handler.automation.sa.kvm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * @ClassName:ReConfigKVMHnadler
 * @Description:KVM扩容
 * @author chengbin
 * 
 * 
 */
public class ReConfigKVMHnadler extends RemoteAbstractAutomationHandler {

	private String rrinfoId = "";
	private StringBuffer rtn_sb = new StringBuffer();
	private static Logger log = LoggerFactory.getLogger(ReConfigKVMHnadler.class);
	String cur_deviceId = "";
	private ICmDeviceDAO devDao = (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");

	@SuppressWarnings("unchecked")
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

				List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);

				for (String deviceId : deviceIdList) {
					cur_deviceId = deviceId;
					Map<String, Object> devInfo = (Map<String, Object>) contextParams.get(deviceId);
					String reconfigFlag = (String) devInfo.get("RECONFIG_FLAG");
					if (StringUtils.isBlank(reconfigFlag)) {
						IDataObject responseDataObject = null;
						IDataObject requestDataObject = buildRequestData(devInfo);
						responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());
						handleResonpse(contextParams, responseDataObject);
					}
				}

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
			rtn_map.put("exeMsg", "扩容设备:" + rtn_sb.toString() + "成功！");
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null!");	
		}

		return JSON.toJSONString(rtn_map);
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		rtn_sb.append(devDao.findCmDeviceById(cur_deviceId).getDeviceName());
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
		// header.setOperation(VMOpration.RECONFIG_VM);
		header.setOperationBean("kvmReconfigVm");
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(GeneralKeyField.KVM.URL, (String) contenxtParmas.get(GeneralKeyField.KVM.URL));
		body.setString(GeneralKeyField.VM.VAPP_NAME, (String) contenxtParmas.get(GeneralKeyField.VM.VAPP_NAME));
		body.setString(GeneralKeyField.VM.CPU_VALUE_CHANGED, (String) contenxtParmas.get(VMFlds.CPU_VALUE));
		body.setString(GeneralKeyField.VM.MEMORY_VALUE_CHANGED, (String) contenxtParmas.get(GeneralKeyField.VM.MEMORY_VALUE + "_NEW"));
		body.setString(GeneralKeyField.VM.VM_TYPE, (String) contenxtParmas.get(GeneralKeyField.VM.VM_TYPE));
		// 存储资源池的名称
		body.setString(GeneralKeyField.KVM.STORAGEPOOL_NAME, (String) contenxtParmas.get(GeneralKeyField.KVM.STORAGEPOOL_NAME));
		// 用户名
		body.setString(GeneralKeyField.KVM.USERNAME, (String) contenxtParmas.get(GeneralKeyField.KVM.USERNAME));
		// 密码
		body.setString(GeneralKeyField.KVM.PASSWORD, (String) contenxtParmas.get(GeneralKeyField.KVM.PASSWORD));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) throws Exception {

		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		log.info(header.getRetMesg());

		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			rtn_sb.append("扩容失败！").append("\n");
			throw new Exception(errorMsg);
		} else {
			setHandleResultParam(cur_deviceId, "RECONFIG_FLAG", "Y");
		}
		rtn_sb.append("扩容成功！").append("\n");
	}

}
