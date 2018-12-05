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
 * 安装虚机单进程
 * <p>
 * 
 * @author gaoyang
 * @version 1.0 Aug 21, 2013
 * @see
 */
public class DeployOvfInstance extends BaseInstance {

	private static Logger log = LoggerFactory
			.getLogger(DeployOvfInstance.class);

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contextParams)
			throws Exception {

		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.CREATE_VM_FROM_OVF);

		String rrinfoId = getContextStringPara(contextParams, RRINFO_ID);
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(),
				queueIdentify);

		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();

		String vCenterurl = contextParams.get(VMFlds.VCENTER_URL).toString();

		body.setString(VMFlds.VCENTER_URL, (String) contextParams.get(VMFlds.VCENTER_URL));
		body.setString(VMFlds.VCENTER_USERNAME, (String) contextParams.get(VMFlds.VCENTER_USERNAME));
		body.setString(VMFlds.VCENTER_PASSWORD, (String) contextParams.get(VMFlds.VCENTER_PASSWORD));
		body.setString(VMFlds.ESXI_URL, (String) contextParams.get(VMFlds.ESXI_URL));
		body.setString(VMFlds.ESXI_USERNAME, (String) contextParams.get(VMFlds.ESXI_USERNAME));
		body.setString(VMFlds.ESXI_PASSWORD, (String) contextParams.get(VMFlds.ESXI_PASSWORD));
		//以前HOST_NAME传递的是主机名称，现传递为宿主机的ip地址 update2018-01-31
		body.setString(VMFlds.HOST_NAME, (String) contextParams.get(VMFlds.ESXI_IP));
		//body.setString(VMFlds.HOST_NAME, (String) contextParams.get(VMFlds.HOST_NAME));
		body.setString(VMFlds.VAPP_NAME, (String) contextParams.get(VMFlds.VAPP_NAME));
		body.setString(VMFlds.OVF_URL, (String) contextParams.get(VMFlds.OVF_URL));
		body.setString(VMFlds.DATASTORE_NAME, (String) contextParams.get(VMFlds.DATASTORE_NAME));

		// 虚机类型
		body.setString(VMFlds.VM_TYPE,
				(String) contextParams.get(VMFlds.VM_TYPE));

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
