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
 * 删除虚拟机
 * @ClassName:VmRecoverExecutionInstance
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 上午9:54:41
 */
public class VmRemoveExecutionInstance extends RemoteAbstractAutomationHandler {

	private static Logger log = LoggerFactory
			.getLogger(VmRemoveExecutionInstance.class);

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
				List<VcenterRfDeivceVo> vcenterList = getAutomationService().getVcenterRfDeivce(rrinfoId);

				// 根据center为单位发送请求报文，返回单次执行结果，结果为：操作结果$$虚拟机名:flag(1||0）$$
				for (VcenterRfDeivceVo vmcenter : vcenterList) {
					// 构造自动化操作请求参数
					IDataObject requestDataObject = buildRequestData(vmcenter,rrinfoId);

					// 调用适配器执行操作
					IDataObject responseDataObject = getResAdpterInvoker().invoke(
							requestDataObject,
							timeOutMs * vmcenter.getVmNames().size());

					// 获取操作执行结果状态
					devResult = getHandleResult(responseDataObject);

					// 将单次设备执行结果放入map中
					resultMap.put(vmcenter.getVcenterUrl(), devResult);

				}

				// 得到所有设备执行完毕后的最终结果

				finalResult = getFinalResult(resultMap);

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
		return rspHeader.getString("RET_CODE") + VmGlobalConstants.SPLIT_FLAG
				+ rspHeader.getString("RET_MESG");
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
				log.debug("脚本在esxi【" + key + "】上返回码为【" + Arrays.toString(result) + "】，执行失败！");
				 for(int i=1;i<result.length;i++){
					 vmName=result[i].split(":")[0];
					// if(result[i].split(":").length>1){
					 operRes=result[i].split(":")[1].equals("1")?"成功":"失败";
					// }
					 vmResult=vmResult+vmName+":"+operRes+";";
				
				  }
				 errResult = MesgRetCode.ERR_OTHER;
			} else {

				log.debug("脚本在vcenter【" + key + "】上返回码为【" + Arrays.toString(result) + "】，执行成功！");
				
				 for(int i=1;i<result.length;i++){
					 vmName=result[i].split(":")[0];
				 if(result[i].split(":").length>1){
						   operRes=result[i].split(":")[1].equals("1")?"成功":"失败";
						   vmResult=vmResult+vmName+":"+operRes+";";
						 }else{
						      vmResult=vmResult+key+"处理结果："+vmName+";";
						 }
					 
					 
				  }
			}

		}
		if (!StringUtils.isEmpty(errResult)) {
			finalResult = vmResult;
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

	protected IDataObject buildRequestData(VcenterRfDeivceVo vmDeviceVo,String rrinfoId) throws Exception {
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.DESTORY_RESOURCE);
		String queueIdentify = getAutomationService().findDcIdenById(vmDeviceVo.getDatacenterId());
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		reqData.setDataObject(MesgFlds.HEADER, header);

		BodyDO body = BodyDO.CreateBodyDO(); // vcenterUrl

		String vcenterUrl = CloudGlobalConstants.VCENTER_URL_HTTPS
				+ vmDeviceVo.getVcenterUrl()
				+ CloudGlobalConstants.VCENTER_URL_SDK;
		body.setString(VMFlds.VCENTER_URL, vcenterUrl);
		body.setString(VMFlds.VCENTER_USERNAME, // vcenter用户名
				vmDeviceVo.getVcenterName());
		body.setString(VMFlds.VCENTER_PASSWORD, // vcenter密码
				vmDeviceVo.getVcenterPwd());
		body.setString(VMFlds.DESTORY_TYPE, // 回收类型
				VmGlobalConstants.DESTORY_TYPE_VM);
		body.setList(VMFlds.DESTORY_RESOURCE_NAME, vmDeviceVo.getVmNames());
		reqData.setDataObject(MesgFlds.BODY, body);

		return reqData;
	}
	@Override
	protected IDataObject buildRequestData(Map<String, Object> contenxtParmas) {
		// TODO Auto-generated method stub
		return null;
	}

}
