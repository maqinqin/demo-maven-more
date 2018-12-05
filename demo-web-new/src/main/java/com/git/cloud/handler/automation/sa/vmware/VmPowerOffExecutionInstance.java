package com.git.cloud.handler.automation.sa.vmware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.handler.automation.LinkRouteType;
import com.git.cloud.handler.automation.RemoteAbstractAutomationHandler;
import com.git.cloud.handler.vo.VcenterRfDeivceVo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.common.VmGlobalConstants;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.CloudGlobalConstants;
import com.google.common.collect.Maps;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 关闭虚拟机
 * @ClassName:VmPowerOffExecutionInstance
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 上午9:52:45
 */
public class VmPowerOffExecutionInstance extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(VmPowerOffExecutionInstance.class);

	// 单台设备执行脚本的超时时间
	protected static final int TIME_OUT = 20 * 1000;

	/**
	 * 自动化操作服务,发送操作指令给消息队列,由底层适配层对应的适配器执行具体的逻辑
	 * 从contextParams取到设备Id列表，再根据设备id到表里取到所有跟设备相关的参数 循环设备id表执行操作
	 * 
	 * @param contextParams
	 *            上下文参数
	 * @return
	 */
	@Override
	public String execute(HashMap<String, Object> contextParams) {

		// 单vcenter设备执行结果
		String devResult = "";

		// 所有设备执行的最终结果
		String finalResult = "";

		// 保存所有设备执行结果的map
		Map<String, String> resultMap = Maps.newHashMap();
		if(contextParams!=null){
			// 流程实例Id
			String flowInstId = (String) contextParams.get(FLOW_INST_ID);
			// 节点ID
			String nodeId = (String) contextParams.get(NODE_ID); // 要根据NODE_ID在工作流配置表中查到当前节点的业务所需参数
			// 资源请求ID
			String rrinfoId = (String) contextParams.get(RRINFO_ID);

			long startTime = System.currentTimeMillis();
			log.debug("执行自动化操作开始,流程实例ID:{},节点ID:{}", flowInstId, nodeId);
			int timeOutMs = Integer.parseInt(contextParams.get("TIME_OUT").toString()) * 1000;

			try {
				/*
				 * 目前回收以虚拟机为维度，只支持单个虚拟机回收
				 * 1.需要获取到虚机的信息
				 * vcenterUrl 通过管理服务器获取
				 * vcenterName 通过管理服务器获取
				 * vcenterPwd 记得转义，通过管理服务器获取
				 * datacenterId 数据中心id
				 * vmName 虚机名称
				 * 2.构造自动化操作请求参数
				 * 根据rrinfoID获取数据中心ID，获取数据中心标识
				 * 拼接url
				 */
				List<VcenterRfDeivceVo> vcenterList = getAutomationService().getVcenterRfDeivce(rrinfoId);

				for (VcenterRfDeivceVo vmcenter : vcenterList) {
					// 构造自动化操作请求参数
					IDataObject requestDataObject = buildRequestData(vmcenter,rrinfoId);
					// 调用适配器执行操作
					IDataObject responseDataObject = getResAdpterInvoker().invoke(
							requestDataObject, timeOutMs * vmcenter.getVmNames().size());

					// 获取操作执行结果状态
					devResult = getHandleResult(responseDataObject);

					// 将单次设备执行结果放入map中
					resultMap.put(vmcenter.getVcenterUrl(), devResult);

				}

				finalResult = getFinalResult(resultMap);
				// }


			} catch (Exception e) {

				String errorMsg = "执行自动化操作失败,流程实例ID:" + flowInstId + ",节点ID:"
						+ nodeId;
				log.error(errorMsg, e);

			} finally {

				// 尽快的释放内存
				if (contextParams != null)
					contextParams.clear();
			}

			log.debug("执行自动化操作结束,流程实例ID:{},节点ID:{},耗时:{}毫秒。", new Object[] {
					flowInstId, nodeId,
					new Long((System.currentTimeMillis() - startTime)) });
		}else{
			finalResult = "ERR_PARAMETER_WRONG;contextParams is null";
		}
		return finalResult;
	}
	
/**
	 * 构造请求数据对象
	 * 
	 * @param contextParams
	 *            上下文参数
	 * 
	 * 
	 * @return
 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	protected IDataObject buildRequestData(VcenterRfDeivceVo vmDeviceVo,String rrinfoId) throws Exception {

		/**
		 * destoryVo.setVcenterUrl(reqBody.getString(VMFlds.VCENTER_URL));
		 * destoryVo.setVcenterUserName(reqBody.getString(VMFlds.VCENTER_USERNAME));
		 * destoryVo.setVcenterPassword(reqBody.getString(VMFlds.VCENTER_PASSWORD));
		 * destoryVo.setDestoryResourceName(reqBody.getString(VMFlds.DESTORY_RESOURCE_NAME));
		 */
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.VMPOWER_OPS);
		//获取数据中心标识，用于调用接口
		String queueIdentify = this.getQueueIdent(rrinfoId);
		
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO(); // url
		String vcenterUrl = CloudGlobalConstants.VCENTER_URL_HTTPS
				+ vmDeviceVo.getVcenterUrl()
				+ CloudGlobalConstants.VCENTER_URL_SDK;
		body.setString(VMFlds.VCENTER_URL, vcenterUrl);
		body.setString(VMFlds.VCENTER_USERNAME, // vcentername
				vmDeviceVo.getVcenterName());
		body.setString(VMFlds.VCENTER_PASSWORD, // vcentepass
				vmDeviceVo.getVcenterPwd());
		body.setString(VMFlds.VM_POWER_OPER_TYPE, // 关闭类型
				VmGlobalConstants.VM_POWEROFF);

		body.setList(VMFlds.VAPP_NAME, vmDeviceVo.getVmNames());

		reqData.setDataObject(MesgFlds.BODY, body);

		return reqData;
	}

	/**
	 * 获取操作结果
	 * 
	 * @param responseDataObject
	 *            资源操作适配器返回的结果
	 * @return true/false
	 */
	@Override
	protected String getHandleResult(IDataObject responseDataObject) {
        
		HeaderDO rspHeader = responseDataObject.getDataObject(MesgFlds.HEADER,
				HeaderDO.class);
		return rspHeader.getString("RET_CODE")+VmGlobalConstants.SPLIT_FLAG+rspHeader.getString("RET_MESG");
	}

	
	@Override
	protected void handleResonpse(Map<String, Object> contenxtParmas,
			IDataObject responseDataObject) {

	}

	protected String getFinalResult(Map<String, String> resultsMap) {
		String finalResult = MesgRetCode.SUCCESS;
		String vmResult = "";
		String errResult="";
		for (String key : resultsMap.keySet()) {
			String[] result = resultsMap.get(key).split(
					VmGlobalConstants.SPLIT_FLAG_REG);
			String vmName = "";
			String operRes = "";
			if (!result[0].equals(MesgRetCode.SUCCESS)) {
				log.debug("脚本在esxi【" + key + "】上返回码为【" + result[0] + "】，执行失败！");
				 for(int i=1;i<result.length;i++){
					 vmName=result[i].split(":")[0];
					 if(result[i].split(":").length>1){
					   operRes=result[i].split(":")[1].equals("1")?"成功":"失败";
					   vmResult=vmResult+vmName+":"+operRes+";";
					 }else{
					      vmResult=vmResult+key+"处理结果："+vmName+";";
					 }
				
				  }
				 errResult = MesgRetCode.ERR_OTHER;
			} else {

				log.debug("脚本在vcenter【" + key + "】上返回码为【" + Arrays.toString(result) + "】，执行成功！");
				
				 for(int i=1;i<result.length;i++){
					 vmName=result[i].split(":")[0];
					 operRes=result[i].split(":")[1].equals("1")?"成功":"失败";
					 vmResult=vmResult+vmName+":"+operRes+";";
				
				  }
			}

		}
		if (!StringUtils.isEmpty(errResult)) {
			finalResult = vmResult;
		} 
	    return finalResult;
	}

	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		// TODO Auto-generated method stub
		return null;
	}

}
