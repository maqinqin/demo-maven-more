package com.git.cloud.resmgt.common.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.git.cloud.common.enums.*;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.resmgt.common.dao.ICmAlarmDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.po.CmDatastorePo;
import com.git.cloud.resmgt.common.model.po.RmHostVmInfoPo;
import com.git.cloud.resmgt.common.model.vo.VCenterAlarmVo;
import com.git.cloud.resmgt.common.service.IVCenterAlarmService;

/**
 * @author WangJingxin
 * @date 2016年5月5日 下午2:39:39
 *
 */
public class VCenterAlarmServiceImpl implements IVCenterAlarmService {

	@Override
	public void saveLogInfo(List<VCenterAlarmVo> voList) throws Exception {
		// 向Notification表插入数据
		for (VCenterAlarmVo vo : voList) {
			// 初始化数据
			vo.setId(UUIDGenerator.getUUID());
			vo.setInsertTime(new Date());
			vo.setvCenterUrl(vo.getvCenterUrl().replace("https://", "").replace("/sdk", ""));
			Map<String, String> pMap = new HashMap<String, String>();

			NotificationPo notiPO = new NotificationPo();
			notiPO.setSource(Source.GRABFROMVC.getValue());
			notiPO.setType(Type.WARNING.getValue());
			notiPO.setOperationType(OperationType.SYNC_ALARM.getValue());
			notiPO.setOperationContent("vCenter ：" + vo.getvCenterUrl() + " : " + vo.getName() + " : " + vo.getDescription());
			notiPO.setCreator("SYNC_ALARM");
			notiPO.setAlarmKey(vo.getKey());
			// Resource ID
			if (vo.getEntityType().equals("HostSystem")) {
				pMap.put("hostIp", vo.getEntityName());
				// 查询物理机、虚拟机
				RmHostVmInfoPo hostVmInfo = rmVmManagerServerDAOHandle.findHostVmInfo(pMap);
				if(hostVmInfo != null){
					notiPO.setResourceId(hostVmInfo.getId());
				}
				notiPO.setResourceType(ResourceType.PHYSICAL.getValue());
			} else if (vo.getEntityType().equals("VirtualMachine")) {
				pMap.put("deviceName", vo.getEntityName());
				// 查询物理机、虚拟机
				RmHostVmInfoPo hostVmInfo = rmVmManagerServerDAOHandle.findHostVmInfo(pMap);
				if(hostVmInfo != null){
					notiPO.setResourceId(hostVmInfo.getId());
				}
				notiPO.setResourceType(ResourceType.VIRTUAL.getValue());	
			} else {
				// 将相关物理机名称存储到VO
				if (vo.getHostNameList() != null) {
					String hostNames = "";
					for (String hostName : vo.getHostNameList()) {
						hostNames += hostName + ",";
					}
					if (hostNames.length() > 0) {
						hostNames = hostNames.substring(0, hostNames.length() - 2);
					}
					vo.setHostNames(hostNames);
				}
				// 将相关虚拟机名称存储到VO
				if (vo.getVmNameList() != null) {
					String vmNames = "";
					for (String vmName : vo.getVmNameList()) {
						vmNames += vmName + ",";
					}
					if (vmNames.length() > 0) {
						vmNames = vmNames.substring(0, vmNames.length() - 2);
					}
					vo.setVmNames(vmNames);
				}
				if (vo.getEntityType().equals("ComputeResource")) {
					notiPO.setResourceType(ResourceType.DATASTORE.getValue());
				} else if (vo.getEntityType().equals("Datacenter")) {
					notiPO.setResourceType(ResourceType.DATACENTER.getValue());
				} else if (vo.getEntityType().equals("DistributedVirtualSwitch")) {
					notiPO.setResourceType(ResourceType.DISTRIBUTED_VIRTUAL_SWITCH.getValue());
				} else if (vo.getEntityType().equals("Network")) {
					notiPO.setResourceType(ResourceType.NETWORK.getValue());
				} else if (vo.getEntityType().equals("ResourcePool")) {
					notiPO.setResourceType(ResourceType.RESOURCE_POOL.getValue());
				} else if (vo.getEntityType().equals("Datastore")) {
					if(vo.getHostNameList() != null){
						String hostIp = vo.getHostNameList().get(0);
						// 查询Datastore
						pMap.put("dsName", vo.getEntityName());
						pMap.put("hIp", hostIp);
						CmDatastorePo dsInfo = rmVmManagerServerDAOHandle.findDatastoreInfo(pMap);
						if(dsInfo != null){
							notiPO.setResourceId(dsInfo.getId());
						}
					}
					notiPO.setResourceType(ResourceType.DATASTORE.getValue());
				}
			}
			String id = notificationHandel.findNotificationIdByAlarmKey(vo.getKey());
			if(id == null) {
				// 向Notification表插入数据
				notiPO.setAlarmTotal(0);
				notificationHandel.insertNotification(notiPO);
			}
			else {
				// 更新报警状态和时间
				notiPO.setId(id);
				notificationHandel.updateAlarmState(notiPO);
			}
		}
		// 向报警表插入数据
		cmAlarmDAOHandle.insertAlarmInfo(voList);
	}

	@Resource(name = "cmAlarmDAOImpl")
	private ICmAlarmDAO cmAlarmDAOHandle;

	@Resource(name = "noti_service")
	private INotificationService notificationHandel;

	@Resource(name = "rmVmManageServerDAO")
	private IRmVmManageServerDAO rmVmManagerServerDAOHandle;
}
