package com.git.cloud.handler.automation.os;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmMountStatusEnum;
import com.git.cloud.common.enums.RmVolumeTypeEnum;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.automation.se.boc.SeConstants;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.impl.CmDeviceDAO;
import com.git.cloud.resmgt.common.dao.impl.CmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;


/** 
 * 查询计算实例
 * @author SunHailong 
 * @version 版本号 2017-4-3
 */
public abstract class GetServerHandler extends OpenstackCommonHandler {

	private static Logger logger = LoggerFactory.getLogger(GetServerHandler.class);
	
	protected int executeTimes = 10; // 执行次数，需要循环获取计算实例状态验证操作是否成功
	protected boolean updateVmHost = false; // 是否更新虚机所属物理机
	protected boolean relateVmSysDisk = false; // 是否关联虚机所属系统盘
	protected boolean isCheckDelete = false; // 验证是否已删除
	protected String deviceStatus = "";
	
	@SuppressWarnings("unchecked")
	public void executeOperate(HashMap<String, Object> reqMap) throws Exception {
		String rrinfoId = (String) reqMap.get(SeConstants.RRINFO_ID);
		BmSrRrinfoPo rrinfo = getAutomationService().getRrinfo(rrinfoId);
	    JSONObject parameterJson = JSONObject.parseObject(rrinfo.getParametersJson());
		HashMap<String, String> deviceParams;
		List<String> deviceIdList = getAutomationService().getDeviceIdsSort(rrinfoId);
		String logCommon = "";
		int len = deviceIdList == null ? 0 : deviceIdList.size();
		List<String> successDeviceList = new ArrayList<String> ();
		this.commonInitParam();
		int n = executeTimes;
		while(true) {
			if(successDeviceList.size() == len) {
				break;
			}
			if(n == 0) {
				throw new Exception("查询计算实例验证结果超时.");
			}
			try {
				// 休眠30秒
				Thread.sleep(30000);
			} catch(Exception e) {
				logger.error("异常exception",e);
			}
			for(int i=0 ; i<deviceIdList.size() ; i++) {
				deviceStatus = "";
				String deviceId = deviceIdList.get(i);
				if(successDeviceList.indexOf(deviceId) >= 0) {
					continue;
				}
				logCommon = "查询第" + (i+1) + "台计算实例";
				logger.debug(logCommon + "开始...");
				deviceParams = (HashMap<String, String>) reqMap.get(deviceIdList.get(i));
				String domainName = deviceParams.get("DOMAIN_NAME");
				String version = deviceParams.get("VERSION");
				if(version == null || "".equals(version)) {
					throw new Exception("VERSION为空，请检查参数[VERSION].");
				}
				String manageOneIp = deviceParams.get("MANAGE_ONE_IP");
				if(manageOneIp == null || "".equals(manageOneIp)) {
					throw new Exception("MANAGE_ONE_IP为空，请检查参数[MANAGE_ONE_IP].");
				}
				String openstackIp = deviceParams.get("OPENSTACK_IP");
				if(openstackIp == null || "".equals(openstackIp)) {
					throw new Exception("服务器IP地址为空，请检查参数[OPENSTACK_IP].");
				}
				String projectId = deviceParams.get("PROJECT_ID");
				if(projectId == null || "".equals(projectId)) {
					throw new Exception("ProjectId为空，请检查参数[PROJECT_ID].");
				}
				String targetServerId = (String) deviceParams.get("TARGET_SERVER_ID");
				if(targetServerId == null || "".equals(targetServerId)) {
					throw new Exception("计算实例ID为空，请检查参数[TARGET_SERVER_ID].");
				}
				String hostType = deviceParams.get("HOST_TYPE");
				if(hostType == null || "".equals(hostType)) {
					throw new Exception("主机类型为空，请检查参数[HOST_TYPE].");
				}
				String projectName = deviceParams.get("PROJECT_NAME");
				if(projectName == null || "".equals(projectName)) {
					throw new Exception("ProjectName为空，请检查参数[PROJECT_NAME].");
				}
				Boolean isVm = null;
				if(hostType.equals(RmHostType.VIRTUAL.getValue())) {
					isVm = true;
				} else if(hostType.equals(RmHostType.PHYSICAL.getValue())) {
					isVm = false;
				} else {
					throw new Exception("主机类型HOST_TYPE不识别，HOST_TYPE=" + hostType);
				}
				String state = "";
				OpenstackIdentityModel model = new OpenstackIdentityModel();
				model.setVersion(version);
	            model.setOpenstackIp(openstackIp);
	            model.setDomainName(domainName);
	            model.setManageOneIp(manageOneIp);
	            model.setProjectId(projectId);
	            model.setProjectName(projectName);
				try {
					state = IaasInstanceFactory.computeInstance(version).getServerState(model, targetServerId, isVm);
				} catch(Exception e) {
					
				}
				if(this.isOperateVmSuccess(state)) {
					if(successDeviceList.indexOf(deviceId) < 0) {
						successDeviceList.add(deviceId);
						if(updateVmHost) { // 修改虚机所属主机
							String hostId = parameterJson.getString("appointHostId");
							// 如果指定物理机id为空，需要通过OpenStack读取所属主机进行反填
							if (hostId == null || "".equals(hostId)) {
								String serverDetail =IaasInstanceFactory.computeInstance(version).getServerDetail(model, targetServerId, isVm);
								JSONObject json = JSONObject.parseObject(serverDetail);
								String hostName ="";
								if(isVm) {
									hostName = json.getJSONObject("server").getString("OS-EXT-SRV-ATTR:hypervisor_hostname");
									logger.info("OS-EXT-SRV-ATTR:hypervisor_hostname=" + hostName);
								}else {
									hostName = json.getJSONObject("server").getString("OS-EXT-SRV-ATTR:host");
									logger.info("OS-EXT-SRV-ATTR:host=" + hostName);
								}
								CmDeviceDAO cmDeviceDao = (CmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
								CmVmDAO cmVmDao = (CmVmDAO) WebApplicationManager.getBean("cmVmDAO");
								hostId = cmDeviceDao.findVmIdByName(hostName);
								logger.info("cmDeviceDao.findVmIdByName(hostId)=" + hostId);
								CmVmPo cmVm = new CmVmPo();
								cmVm.setHostId(hostId);
								cmVm.setId(deviceId);
								logger.info("deviceId=" + deviceId);
								cmVmDao.updateCmVmHostId(cmVm);
							}
						}
						if(relateVmSysDisk && isVm) {
							try {
								RmDeviceVolumesRefPo ref = new RmDeviceVolumesRefPo();
								ref.setTargetVolumeId(deviceParams.get("VOLUME_ID"));
								ref.setVolumeId(deviceParams.get("MY_VOLUME_ID"));
								ref.setId(UUIDGenerator.getUUID());
								ref.setDiskSize(deviceParams.get("SYS_DISK"));
								ref.setDeviceId(deviceId);
								ref.setVolumeName(deviceParams.get("SYS_VOLUME_NAME"));
								ref.setMountStatus(RmMountStatusEnum.MOUNT.getValue());
								ref.setDiskType(RmVolumeTypeEnum.INTERNAL_DISK.getValue());
								ICmDeviceDAO cmDeviceDAO = (ICmDeviceDAO) WebApplicationManager.getBean("cmDeviceDAO");
								cmDeviceDAO.saveRmDeviceVolumesRefPo(ref);
							} catch(Exception e) {
								logger.error("[GetServerHandler]关联虚机与系统卷关系出现异常," + e.getMessage());
								e.printStackTrace();
							}
						}
						if(!"".equals(deviceStatus)) {
							setHandleResultParam(deviceId, "CURRENT_DEVICE_STATUS", deviceStatus);
						}
						logger.debug(logCommon + "完成...");
					}
				} else {
					logger.info("[GetServerHandler]查询计算实例验证结果，目前计算实例尚未处理完毕");
				}
			}
			n--;
		}
		// 保存流程参数
		this.saveParam(reqMap);
	}
	
	protected abstract void commonInitParam();
	
	/**
	 * 根据每个对虚机操作之后的状态来单独写实现类
	 * @param contenxtParams
	 * @param responseDataObject
	 * @return
	 */
	protected abstract boolean isOperateVmSuccess(String state);
}