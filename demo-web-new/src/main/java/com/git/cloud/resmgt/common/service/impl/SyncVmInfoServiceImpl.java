package com.git.cloud.resmgt.common.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.enums.OperationType;
import com.git.cloud.common.enums.ResourceType;
import com.git.cloud.common.enums.Source;
import com.git.cloud.common.enums.Type;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.handler.common.NetUtil;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.ISyncVmInfoDAO;
import com.git.cloud.resmgt.common.model.po.CmVmSynInfoPo;
import com.git.cloud.resmgt.common.model.vo.SyncVmInfoVo;
import com.git.cloud.resmgt.common.service.ISyncVmInfoService;
import com.git.cloud.taglib.util.Internation;

/**
 * 
 * @author WangJingxin
 * @date 2016年5月6日 上午10:50:27
 * 
 */
public class SyncVmInfoServiceImpl implements ISyncVmInfoService {
	private static final Logger log = LoggerFactory.getLogger(SyncVmInfoServiceImpl.class);

	@Autowired
	private ICmVmDAO cmVmDAO;
	@Autowired
	private ICmHostDAO cmHostDao;
	@Resource(name = "noti_service")
	private INotificationService notificationHandel;
	@Autowired
	private ISyncVmInfoDAO syncVmDaoHandle;

	@Override
	public void saveVMInfo(List<SyncVmInfoVo> syncVmList) throws Exception {

		// 保存当前vCenter的IP
		Map<String, List<SyncVmInfoVo>> vCenterMap = new HashMap<String, List<SyncVmInfoVo>>();
		for (SyncVmInfoVo vo : syncVmList) {
			if (vCenterMap.containsKey(vo.getVcenter())) {
				vCenterMap.get(vo.getVcenter()).add(vo);
			} else {
				List<SyncVmInfoVo> newVoList = new ArrayList<SyncVmInfoVo>();
				newVoList.add(vo);
				vCenterMap.put(vo.getVcenter(), newVoList);
			}
		}
		// 保存vCenter下vm的信息
		List<CmVmSynInfoPo> currentVmInfoList = null;
		for (String vCenterName : vCenterMap.keySet()) {
			// vm信息
			currentVmInfoList = syncVmDaoHandle.findSyncVmInfoByManageIp(vCenterName);

			// 从查询结果中获取vm信息和vm的IP列表
			for (CmVmSynInfoPo po : currentVmInfoList) {
				SyncVmInfoVo syncInfo = null;
				for (SyncVmInfoVo vo : vCenterMap.get(vCenterName)) {
					if (po.getVmName().equals(vo.getVmName())) {
						syncInfo = vo;
						vCenterMap.get(vCenterName).remove(vo);
						break;
					}
				}
				if (syncInfo != null) {
					// 比较
					compareVmInfo(po, syncInfo);
				}
				// 不存在时
				else {
					// 写入通知表
					insertNotification(po.getId(), OperationType.SYNC_VM_INFO.getValue(),
							"" + po.getPoolName() + ">>" + po.getRcName() + ">>" + po.getVmHostIp()
									+ Internation.language("res_pool_operate_pm_sub") + po.getVmName()
									+ Internation.language("res_sync_stay") + " VCenter(" + po.getMgtName() + ") "
									+ Internation.language("res_sync_tip_info"));
					// 重置虚拟机状态为未知
					syncInfo = new SyncVmInfoVo();
					syncInfo.setVmName(po.getVmName());
					syncInfo.setPowerState("unknown");
					cmVmDAO.updateVmStatus(syncInfo);
				}
			}

		}

	}

	/**
	 * @Title: compareVmInfo
	 * @Description: 比较两边都存在的vm信息
	 * @param po
	 * @param vo	automation返回的对象
	 * @return void 返回类型
	 * @throws RollbackableBizException
	 */
	private void compareVmInfo(CmVmSynInfoPo po, SyncVmInfoVo vo) throws RollbackableBizException {
		// 虚拟机电源状态
		if (!po.getState().equals(vo.getPowerState())) {
			// 更新数据库
			cmVmDAO.updateVmStatus(vo);
			// 写入通知表
			insertNotification(po.getId(), OperationType.SYNC_VM_INFO.getValue(),
					"" + po.getPoolName() + ">>" + po.getRcName() + ">>" + po.getVmHostIp() + ">>" + po.getVmName()
							+ Internation.language("res_tip_power_state") + po.getState()
							+ Internation.language("res_tip_changeTo") + vo.getPowerState() + "。");
		}
		// 如果虚拟机关机或挂起接口无法返回ip地址
		log.info("sync vm info power state is: {}", vo.getPowerState());
		if ("poweron".equals(vo.getPowerState())) {
			// 比较IP
			String[] dbIp = po.getVmIp().split(",");
			String[] vmIp = vo.getIps().split(",");
			log.info("dbip: {}", po.getVmIp());
			log.info("vmIp: {}", vo.getIps());
			// 过滤掉vmIp中的ipv6的ip
			List<String> syncVMIps = new ArrayList<>();
			for (String ip : vmIp) {
				if (NetUtil.isIPV4(ip.trim())) {
					syncVMIps.add(ip);
				}
			}
			String[] vmIpForV4 = syncVMIps.toArray(new String[syncVMIps.size()]);
			
			Arrays.sort(dbIp);
			Arrays.sort(vmIpForV4);
			if (!Arrays.equals(dbIp, vmIpForV4)) {
				// 写入通知表
				/*insertNotification(po.getId(), OperationType.SYNC_VM_INFO.getValue(),
						"" + po.getPoolName() + ">>" + po.getRcName() + ">>" + po.getVmHostIp() + ">>" + po.getVmName()
						+ Internation.language("res_sync_platform") + po.getVmIp()
						+ Internation.language("res_sync_with") + vo.getVcenter()
						+ Internation.language("res_sync_configured") + vo.getIps()
						+ Internation.language("res_sync_configured_atypism"));*/
				insertNotification(po.getId(), OperationType.SYNC_VM_INFO.getValue(),
						"" + po.getPoolName() + ">>" + po.getRcName() + ">>" + po.getVmHostIp() + ">>" + po.getVmName()
						+ " 云管平台与VC[" + vo.getVcenter() + "]中IP地址信息不一致 云管: " + po.getVmIp()
						+ " VC: " + vo.getIps() + "]");
			}
		}
		// 虚拟机所在物理机
		if (!po.getVmHostIp().equals(vo.getHostName())) {
			// 更新虚拟机所在物理机
			cmVmDAO.updateVmHostId(vo);
			cmHostDao.updateCmHostUsed();
			// 写入通知表
			insertNotification(po.getId(), OperationType.SYNC_VM_INFO.getValue(),
					"" + po.getPoolName() + ">>" + po.getRcName() + ">>" + po.getVmName()
							+ Internation.language("res_tip_move1") + po.getVmHostIp()
							+ Internation.language("res_tip_move2") + vo.getHostName() + "。");
		}
		// 存储
		if (!po.getDsName().equals(vo.getDatastore())) {
			List<DataStoreVo> hostDsList = cmHostDao.findDatastoreByHostIp(vo.getHostName());
			boolean isMounted = false;
			String datastoreId = null;
			for (DataStoreVo dsVo : hostDsList) {
				if (dsVo.getDsName().equals(vo.getDatastore())) {
					isMounted = true;
					datastoreId = dsVo.getId();
					break;
				}
			}
			log.info("add isMounted info vo.isMounted{}", isMounted);
			if (isMounted) {
				// 更新存储
				Map<String, String> updateDataMap = new HashMap<String, String>();
				updateDataMap.put("dsId", datastoreId);
				updateDataMap.put("vmId", po.getId());
				syncVmDaoHandle.saveSyncVmDatastore(updateDataMap);
				// 写入通知表
				insertNotification(po.getId(), OperationType.SYNC_VM_INFO.getValue(),
						"" + po.getPoolName() + ">>" + po.getRcName() + ">>" + po.getVmHostIp() + ">>" + po.getVmName()
								+ Internation.language("res_tip_move1") + po.getDsName()
								+ Internation.language("res_tip_move2") + vo.getDatastore() + "。");
			} else {
				// 写入通知表
				insertNotification(po.getId(), OperationType.SYNC_VM_INFO.getValue(),
						"" + po.getPoolName() + ">>" + po.getRcName() + ">>" + po.getVmHostIp() + ">>" + po.getVmName()
								+ ">> " + po.getDsName() + Internation.language("res_tip_changeTo") + vo.getDatastore()
								+ Internation.language("res_tip_storage"));
			}
		}
	}

	private void insertNotification(String vmDeviceID, String operationType, String operationContent)
			throws RollbackableBizException {
		NotificationPo notiPo = new NotificationPo();
		notiPo.setOperationType(operationType);
		notiPo.setResourceId(vmDeviceID);
		notiPo.setResourceType(ResourceType.VIRTUAL.getValue());
		notiPo.setSource(Source.AUTORECOGNITION.getValue());
		notiPo.setType(Type.TIP.getValue());
		notiPo.setOperationContent(operationContent);
		notificationHandel.insertNotification(notiPo);
	}

}
