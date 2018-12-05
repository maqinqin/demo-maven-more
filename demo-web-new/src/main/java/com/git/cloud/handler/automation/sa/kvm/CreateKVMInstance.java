package com.git.cloud.handler.automation.sa.kvm;

import java.util.Map;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.handler.automation.BaseInstance;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.general.field.GeneralKeyField;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

public class CreateKVMInstance extends BaseInstance {

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) throws Exception {
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.KVM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.KVM_RES_TYPE.getValue());
		// header.setOperation(VMOpration.CREATEANDCONF_VM);
		header.setOperationBean("kvmCreateVMFromUrl");

		String rrinfoId = getContextStringPara(contenxtParmas, RRINFO_ID);
		// 增加数据中心路由标识
		String queueIdentify = this.getQueueIdent(rrinfoId);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);

		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(GeneralKeyField.KVM.URL, (String) contenxtParmas.get(GeneralKeyField.KVM.URL));
		body.setString(GeneralKeyField.VM.VAPP_NAME, (String) contenxtParmas.get(GeneralKeyField.VM.VAPP_NAME));
		body.setString(GeneralKeyField.VM.CPU_CORE_VALUE, (String) contenxtParmas.get(GeneralKeyField.VM.CPU_CORE_VALUE));
		body.setString(GeneralKeyField.VM.MEMORY_VALUE, ((String) contenxtParmas.get(GeneralKeyField.VM.MEMORY_VALUE)).replace(".0", ""));

		String capacity_size = (String) contenxtParmas.get(GeneralKeyField.VM.VM_VOL_CAPACITY_SIZE);
		capacity_size = String.valueOf(Integer.valueOf(capacity_size) * 1024);
		body.setString(GeneralKeyField.VM.VM_VOL_CAPACITY_SIZE, capacity_size);

		String allocation_size = (String) contenxtParmas.get(GeneralKeyField.VM.VM_VOL_ALLOCATION_SIZE);
		allocation_size = String.valueOf(Integer.valueOf(allocation_size) * 1024);
		body.setString(GeneralKeyField.VM.VM_VOL_ALLOCATION_SIZE, allocation_size);

		body.setString(GeneralKeyField.VM.VM_IMAGE_NAME, (String) contenxtParmas.get(GeneralKeyField.VM.VM_IMAGE_NAME));

		body.setString(GeneralKeyField.VM.MAX_CPU_VALUE, (String) contenxtParmas.get(GeneralKeyField.VM.MAX_CPU_VALUE));
		body.setString(GeneralKeyField.VM.MAX_MEMORY_VALUE, (String) contenxtParmas.get(GeneralKeyField.VM.MAX_MEMORY_VALUE));
		// 存储资源池的名称
		body.setString(GeneralKeyField.KVM.STORAGEPOOL_NAME, (String) contenxtParmas.get(GeneralKeyField.KVM.STORAGEPOOL_NAME));
		reqData.setDataObject(MesgFlds.BODY, body);

		return reqData;
	}

	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas, IDataObject responseDataObject) throws Exception {

		HeaderDO header = responseDataObject.getDataObject(MesgFlds.HEADER, HeaderDO.class);

		if (!MesgRetCode.SUCCESS.equalsIgnoreCase(header.getRetCode())) {
			String errorMsg = header.getRetMesg();
			throw new Exception(errorMsg);
		}

	}

}
