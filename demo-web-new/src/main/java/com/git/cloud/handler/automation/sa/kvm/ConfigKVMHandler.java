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
import com.git.support.constants.SAConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * @ClassName:ConfigKVMHandler
 * @Description:配置KVM
 * @author chengbin
 * @date 2014-12-11 上午11:45:30
 * 
 * 
 */
public class ConfigKVMHandler extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory.getLogger(ConfigKVMHandler.class);
	String rrinfoId = "";
	StringBuffer rtn_sb = new StringBuffer();
	String cur_deviceId = "";
	private ICmDeviceDAO devDao = (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");

	@SuppressWarnings("unchecked")
	@Override
	public String execute(HashMap<String, Object> contenxtParmas) throws Exception {
		Map<String, Object> rtn_map = new HashMap<String, Object>();
		if(contenxtParmas!=null){
			// 流程实例Id
			String flowInstId = (String) contenxtParmas.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contenxtParmas.get(NODE_ID); // 要根据NODE_ID在工作流配置表中查到当前节点的业务所需参数
			// 资源请求ID
			rrinfoId = (String) contenxtParmas.get(RRINFO_ID);

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			try {

				Map<String, Map<String, String>> handleParams = this.getHandleParams(flowInstId);

				contenxtParmas.putAll(handleParams);
				Map<String, Object> extHandleParams = getExtHandleParams(contenxtParmas);

				if (extHandleParams != null)
					contenxtParmas.putAll(extHandleParams);
				List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
				for (String deviceId : deviceIdList) {
					cur_deviceId = deviceId;
					Map<String, Object> devInfo = (Map<String, Object>) contenxtParmas.get(deviceId);
					String configFlag = (String) devInfo.get("CONFIG_FLAG");
					if (StringUtils.isBlank(configFlag)) {
						IDataObject responseDataObject = null;
						IDataObject requestDataObject = buildRequestData(devInfo);
						responseDataObject = getResAdpterInvoker().invoke(requestDataObject, getTimeOut());
						handleResonpse(contenxtParmas, responseDataObject);
					}
				}
				saveParamInsts(flowInstId, nodeId);

			} catch (Exception e) {
				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:" + nodeId + ".错误信息:" + e.getMessage();
				log.error(errorMsg, e);
				rtn_map.put("checkCode", MesgRetCode.ERR_PROCESS_FAILED);
				rtn_map.put("exeMsg", rtn_sb.toString() + e.getMessage());
				return JSON.toJSONString(rtn_map);
			} finally {
				if (contenxtParmas != null)
					contenxtParmas.clear();
			}

			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。",
					new Object[] { flowInstId, nodeId, new Long((System.currentTimeMillis() - startTime)) });
			rtn_map.put("checkCode", MesgRetCode.SUCCESS);
			rtn_map.put("exeMsg", "配置虚拟机:" + rtn_sb.toString() + "成功！");
		}else{
			rtn_map.put("checkCode", MesgRetCode.ERR_PARAMETER_WRONG);
			rtn_map.put("exeMsg", "ERR_PARAMETER_WRONG;contextParams is null！");
		}
		return JSON.toJSONString(rtn_map);
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> devInfo) throws Exception {
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
		// header.setOperation(VMOpration.CONFIG_VM);
		header.setOperationBean("kvmConfVm");
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();

		body.setString(VMFlds.URL, (String) devInfo.get(VMFlds.URL));
		body.setString(VMFlds.VAPP_NAME, (String) devInfo.get(VMFlds.VAPP_NAME));
		body.setString(VMFlds.GUEST_USER_NAME, (String) devInfo.get(SAConstants.USER_NAME));
		body.setString(VMFlds.GUEST_PASSWORD, (String) devInfo.get(SAConstants.USER_PASSWORD));
		body.setString(VMFlds.NIC_IP, (String) devInfo.get(VMFlds.NIC_IP));
		body.setString(VMFlds.NIC_MASK, (String) devInfo.get(VMFlds.NIC_MASK));
		body.setString(VMFlds.NIC_GATEWAY, (String) devInfo.get(VMFlds.NIC_GATEWAY));
		body.setString(VMFlds.NIC_MASK, (String) devInfo.get(VMFlds.NIC_MASK));
		body.setString(VMFlds.NIC_GATEWAY, (String) devInfo.get(VMFlds.NIC_GATEWAY));
		body.setString(VMFlds.USERNAME, (String) devInfo.get(VMFlds.USERNAME));
		body.setString(VMFlds.PASSWORD, (String) devInfo.get(VMFlds.PASSWORD));
		body.setString(VMFlds.VM_TYPE, (String) devInfo.get(VMFlds.VM_TYPE));
		body.setString(VMFlds.ROUTE_IPS, (String) devInfo.get(VMFlds.ROUTE_IPS));
		body.setString(VMFlds.ROUTE_GWS, (String) devInfo.get(VMFlds.ROUTE_GWS));
		body.setString(VMFlds.ROUTE_MASKS, (String) devInfo.get(VMFlds.ROUTE_MASKS));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) throws Exception {
		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		log.info(header.getRetMesg());

		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			rtn_sb.append("配置失败！").append("\n");
			throw new Exception(errorMsg);
		} else {
			setHandleResultParam(cur_deviceId, "CONFIG_FLAG", "Y");
		}
		rtn_sb.append("配置成功！").append("\n");
	}

}
