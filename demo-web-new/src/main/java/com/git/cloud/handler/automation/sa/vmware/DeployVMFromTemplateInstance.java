package com.git.cloud.handler.automation.sa.vmware;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.support.common.MesgFlds;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * @author wangmingyue
 */
public class DeployVMFromTemplateInstance extends BaseInstance {

	private static Logger log = LoggerFactory.getLogger(DeployVMFromTemplateInstance.class);

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contextParams)
			throws Exception {

		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.CREATE_VM_FROM_OVF);
		header.setOperationBean("deployVMFromTemplateImpl");
		String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),queueIdentify);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(VMFlds.VCENTER_URL, (String) contextParams.get(VMFlds.VCENTER_URL));
		body.setString(VMFlds.VCENTER_USERNAME, (String) contextParams.get(VMFlds.VCENTER_USERNAME));
		body.setString(VMFlds.VCENTER_PASSWORD, (String) contextParams.get(VMFlds.VCENTER_PASSWORD));
		body.setString(VMFlds.DATACENTER_NAME, (String) contextParams.get(VMFlds.DATACENTER_NAME));
		body.setString(VMFlds.ESXI_IP, (String) contextParams.get(VMFlds.ESXI_IP));
		body.setString(VMFlds.VAPP_NAME, (String) contextParams.get(VMFlds.VAPP_NAME));
		body.setString(VMFlds.TEMPLATE_NAME, (String) contextParams.get(VMFlds.TEMPLATE_NAME));
		body.setString(VMFlds.DATASTORE_NAME, (String) contextParams.get(VMFlds.DATASTORE_NAME));
		// 虚机类型
		body.setString(VMFlds.VM_TYPE,(String) contextParams.get(VMFlds.VM_TYPE));
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contextParams,
			IDataObject responseDataObject) {

	}

	protected String handleResonpse(Long devId,
			Map<String, Object> contextParams, IDataObject responseDataObject)
			throws Exception {

		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		return null;

	}

}
