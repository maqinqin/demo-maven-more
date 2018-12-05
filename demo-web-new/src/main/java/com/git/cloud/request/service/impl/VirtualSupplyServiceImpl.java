package com.git.cloud.request.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.cloudservice.dao.ICloudServiceDao;
import com.git.cloud.cloudservice.dao.ImageDao;
import com.git.cloud.cloudservice.model.HaTypeEnum;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.enums.AllocedStatus;
import com.git.cloud.common.enums.ComputeResourceNameTypeEnum;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.handler.service.BizParamInstService;
import com.git.cloud.iaas.openstack.IaasInstanceFactory;
import com.git.cloud.iaas.openstack.model.OpenstackIdentityModel;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.policy.dao.IRmNwRuleDao;
import com.git.cloud.policy.model.po.RmNwRuleListPo;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.policy.model.vo.PolicyInfoVo;
import com.git.cloud.policy.model.vo.PolicyResultVo;
import com.git.cloud.policy.service.IComputePolicyService;
import com.git.cloud.request.dao.IBmSrAttrValDao;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.dao.IVirtualSupplyDAO;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.vo.BmSrAttrValVo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.model.vo.VirtualSupplyVo;
import com.git.cloud.request.service.IVirtualSupplyService;
import com.git.cloud.request.tools.SrDateUtil;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.IRmHostTypeDAO;
import com.git.cloud.resmgt.common.dao.IRmResPoolDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.DeviceStatusEnum;
import com.git.cloud.resmgt.common.model.PlatFormTypeEnum;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmHostTypePo;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.git.cloud.resmgt.common.service.IRmPlatformService;
import com.git.cloud.resmgt.common.util.ComputeResourceNameManager;
import com.git.cloud.resmgt.common.util.VmComputeResourceNameManager;
import com.git.cloud.resmgt.compute.dao.IRmClusterDAO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
import com.git.cloud.resmgt.network.dao.IVirtualNetworkDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.po.VirtualNetworkPo;
import com.git.cloud.resmgt.network.service.IVirtualNetworkService;
import com.git.cloud.resmgt.openstack.dao.IFloatingIpDao;
import com.git.cloud.resmgt.openstack.dao.impl.OpenstackVolumeDaoImpl;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;
import com.git.support.common.OSOperation_bak;
import com.google.common.collect.Maps;

/**
 * 云服务供给申请接口类
 * 
 * @ClassName:VirtualSupplyServiceImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class VirtualSupplyServiceImpl implements IVirtualSupplyService {

	private static Logger logger = LoggerFactory.getLogger(VirtualSupplyServiceImpl.class);

	private IBmSrDao bmSrDao;
	private IBmSrRrinfoDao bmSrRrinfoDao;
	private IBmSrRrVmRefDao bmSrRrVmRefDao;
	private IBmSrAttrValDao bmSrAttrValDao;
	private IVirtualSupplyDAO virtualSupplyDAO;
	// 外包服务
	private IComputePolicyService computePolicyService;
	private ICloudServiceDao cloudServiceDao;
	private IDeployunitDao deployunitDao;
	private ICmDeviceDAO cmDeviceDao;
	private ICmHostDAO cmHostDao;
	private ICmVmDAO cmVmDao;
	private ICmPasswordDAO cmPasswordDao;
	private IRmVmManageServerDAO rmVmManageServerDao;
	@Autowired
	private IRmClusterDAO rmclsDao;
	@Autowired
	private IRmHostTypeDAO rmHostTypeDAO;
	private ImageDao imageDao;
	@Autowired
	private IFloatingIpDao floatingIpDao;
	@Autowired
	private IRmResPoolDAO rmResPoolDAO;
	private IVirtualNetworkService virtualNetworkService;
	@Autowired
	private IVirtualNetworkDao virtualNetworkDao;
	@Autowired
	private BizParamInstService bizParamInstServiceImpl;
	@Autowired
	private IRmPlatformService rmPlatformServiceImpl;
	@Autowired
	private IRmNwRuleDao rmNwRuleDao;

	/**
	 * 获取云服务供给信息
	 * 
	 * @param srId
	 * @return
	 */
	@Override
	public VirtualSupplyVo findVirtualSupplyById(String srId) throws RollbackableBizException {

		if (null == srId || srId.length() == 0) {
			logger.info("执行获取云服务申请时：云服务请求ID接收为空");
			return null;
		}

		VirtualSupplyVo virtualSupplyVo = new VirtualSupplyVo();

		try {
			BmSrVo bmSr = bmSrDao.findBmSrVoById(srId); // 服务请求主表信息
			List<BmSrRrinfoVo> rrinfoList = bmSrRrinfoDao.findBmSrRrinfoListBySrId(srId); // 资源信息

			for (BmSrRrinfoVo bmSrRrinfoVo : rrinfoList) {
				List<BmSrAttrValVo> attrValList = bmSrAttrValDao
						.findBmSrAttrValListByRrinfoId(bmSrRrinfoVo.getRrinfoId()); // 供给参数信息

				for (BmSrAttrValVo srAttrval : attrValList) {
					String attrType = srAttrval.getAttrType();
					if ("S".equals(attrType)) {
						List<CloudServiceAttrSelPo> attrSelList = virtualSupplyDAO.findByID("getBmsrAttrSel",
								srAttrval.getAttrId());// 参数属性
						srAttrval.setAttrSelList(attrSelList);
					}
				}
				bmSrRrinfoVo.setAttrValList(attrValList);
			}
			String startTime = String.valueOf(bmSr.getSrStartTime());
			if (!"".equals(startTime)) {
				bmSr.setStartTimeStr(startTime);
			}
			String endTime = String.valueOf(bmSr.getSrEndTime());
			if (!"".equals(endTime)) {
				bmSr.setEndTimeStr(endTime);
			}
			virtualSupplyVo.setBmSr(bmSr);
			virtualSupplyVo.setRrinfoList(rrinfoList);

		} catch (RollbackableBizException e) {
			logger.error("执行查找云服务申请时发生异常:" + e);
		}
		return virtualSupplyVo;
	}

	public void saveResNew(BmSrRrinfoPo rrinfo, CloudServicePo service, DeployUnitVo du,
			List<PolicyResultVo> policyList, List<String> vmNameList, List<String> vmIdList, String networkId)
			throws RollbackableBizException {
		String eStr = "";
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());

		// 调用网络接口生成网络ip
		VirtualNetworkPo virtualNetwork = null;
		boolean fiFlag = json.getBooleanValue("fiFlag");
		if (!fiFlag) {
			// 是否分配浮动ip
			try {
				virtualNetwork = virtualNetworkService.selectVirtualNetwork(networkId);
				boolean floatIpFlag = json.getBooleanValue("floatIpFlag");
				if (floatIpFlag) {
					for (String deviceId : vmIdList) {
						List<FloatingIpVo> ipUnUsed = floatingIpDao.findFloatingIpUnUsed(virtualNetwork.getProjectId());
						if (ipUnUsed != null && ipUnUsed.size() > 0) {
							FloatingIpVo floatingIpVo = ipUnUsed.get(0);
							floatingIpVo.setDeviceId(deviceId);
							floatingIpDao.updateFloatingIp(floatingIpVo);
						} else {
							eStr = eStr + (du == null ? "" : "【" + du.getFullCname() + "】") + "资源申请失败原因：分配浮动ip失败";
							throw new RollbackableBizException(eStr);
						}
					}
				}
				// }
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				eStr = eStr + (du == null ? "" : "【" + du.getFullCname() + "】") + "资源申请失败原因：分配浮动ip失败";
				throw new RollbackableBizException(eStr);
			}
			try {
				// 开始分配IP地址
				List<OpenstackIpAddressPo> ipList = virtualNetworkService.selectIpAddressByNetwork(networkId);
				if (ipList == null) {
					throw new RollbackableBizException("IP池中可用地址不足申请虚机数量.");
				}
				String platformType = rrinfo.getPlatformType();
				OpenstackIpAddressPo updateIpAddress;
				if (PlatFormTypeEnum.O.getCode().equals(platformType)  || PlatFormTypeEnum.PV.getCode().equals(platformType)) {
					logger.debug("分配OpenStack power vc网络资源");
					// openstackIP资源分配
					if (ipList.size() < vmNameList.size()) {
						throw new RollbackableBizException("IP池中可用地址不足申请虚机数量.");
					}
					for (int i = 0; i < vmIdList.size(); i++) {
						updateIpAddress = new OpenstackIpAddressPo();
						updateIpAddress.setId(ipList.get(i).getId());
						updateIpAddress.setInstanceId(vmIdList.get(i));
						updateIpAddress.setUseRelCode(AllocedStatus.ALLOCTODEV.getValue()); // A2DV为已分配
						updateIpAddress.setOriginalIp("Y");
						// 保存IP占用信息
						virtualNetworkDao.updateIpAddressByNetwork(updateIpAddress);
					}
				} else if (PlatFormTypeEnum.X.getCode().equals(platformType)) {
					logger.debug("分配VMware网络资源");
					// VMware网络资源分配
					// 根据资源池ID获取资源池信息
					AllocIpParamVo allocIpParam = this.getAllocIpParamVo(rrinfo, service, policyList.get(0));
					String logMsg = "传入的平台编码:" + allocIpParam.getPlatformType() + ";;虚拟化类型编码:"
							+ allocIpParam.getVmType() + ";;主机类型编码:" + allocIpParam.getDeviceType();
					logger.debug("开始为设备分配ip地址，检查传入参数");
					logger.info("分配设备ip，设备id{}, 分配参数：{}", new Object[] { vmIdList, allocIpParam });
					if (vmIdList.isEmpty()) {
						throw new RollbackableBizException("传入的设备ID列表为空，请检查设备ID列表传入是否正确");
					}
					logger.debug("根据cdp参数，读取规则列表");
					if (allocIpParam.getCloudServiceHATypeCode() == null) {
						allocIpParam.setCloudServiceHATypeCode(HaTypeEnum.SINGLE.toString());
					}
					List<RmNwRuleListPo> ruleList = rmNwRuleDao.findRuleList(allocIpParam);
					if (ruleList.isEmpty()) {
						logger.error("未匹配可分配网络资源的平台类型" + platformType);
						throw new RollbackableBizException("规则不存在，请检查规则表中数据是否正确!" + logMsg);
					}
					int totalNum = 0;
					List<RmNwRuleListPo> allRuleList = new ArrayList<RmNwRuleListPo>();
					for (RmNwRuleListPo ruleListPo : ruleList) {
						totalNum += ruleListPo.getActNum() + ruleListPo.getOccNum();
						for (int i = 0; i < totalNum; i++) {
							allRuleList.add(ruleListPo);
						}
					}
					logger.debug("未匹配可分配网络资源的平台类型" + platformType);
					if (ipList.size() < vmNameList.size() * totalNum) {
						throw new RollbackableBizException("IP池中可用地址不足申请虚机数量.");
					}
					// 判断IP是否够分配
					for (int i = 0; i < vmIdList.size(); i++) {
						for (int j = 0; j < allRuleList.size(); j++) {
							updateIpAddress = new OpenstackIpAddressPo();
							updateIpAddress.setId(ipList.get(i + j).getId());
							updateIpAddress.setInstanceId(vmIdList.get(i));
							updateIpAddress.setNwRuleListId(allRuleList.get(j).getRmNwRuleListId());
							updateIpAddress.setUseRelCode(AllocedStatus.ALLOCTODEV.getValue()); // A2DV为已分配
							updateIpAddress.setOriginalIp("Y");
							// 保存IP占用信息
							virtualNetworkDao.updateIpAddressByNetwork(updateIpAddress);
						}
					}
				} else {
					logger.error("未匹配可分配网络资源的平台类型" + platformType);
				}
			} catch (Exception e) {
				logger.error("异常exception", e);
				eStr = eStr + "【" + du.getFullCname() + "】资源申请失败原因：分配网络ip失败，" + e.getMessage() + "";
				throw new RollbackableBizException(eStr);
			}

			try {
				if (virtualNetwork != null) {
					this.insertBatchVirtualData(rrinfo, service, vmIdList, vmNameList, policyList,
							virtualNetwork.getProjectId());
				} else {
					this.insertBatchVirtualData(rrinfo, service, vmIdList, vmNameList, policyList, null);
				}
			} catch (Exception e) {
				logger.error("异常exception", e);
				eStr = eStr + "【" + du.getFullCname() + "】资源申请失败原因：生成虚拟机失败，" + e.getMessage() + "";
				throw new RollbackableBizException(eStr);
			}
		} else {
			try {
				virtualNetwork = virtualNetworkService.selectVirtualNetwork(networkId);
				// 分配IP
				List<OpenstackIpAddressPo> findIpAddr = virtualNetworkService.selectIpAddressByNetwork(networkId);
				if (findIpAddr != null && findIpAddr.size() < vmIdList.size()) {
					throw new RollbackableBizException("此资源池IP数量不足");
				}
				OpenstackIpAddressPo updateIpAddress;
				for (int i = 0; i < vmIdList.size(); i++) {
					updateIpAddress = new OpenstackIpAddressPo();
					updateIpAddress.setId(findIpAddr.get(i).getId());
					updateIpAddress.setInstanceId(vmIdList.get(i));
					updateIpAddress.setUseRelCode(AllocedStatus.ALLOCTODEV.getValue()); // A2DV为已分配
					// 保存IP占用信息
					virtualNetworkDao.updateIpAddressByNetwork(updateIpAddress);
				}
				this.insertBatchVirtualData(rrinfo, service, vmIdList, vmNameList, policyList, null);
			} catch (Exception e) {
				logger.error("异常exception", e);
				eStr = eStr + "【" + du.getFullCname() + "】资源申请失败原因：生成虚拟机失败，" + e.getMessage() + "";
				throw new RollbackableBizException(eStr);
			}
		}
	}

	// 构造AllocIpParamVo对象
	private AllocIpParamVo getAllocIpParamVo(BmSrRrinfoPo rrinfo, CloudServicePo service, PolicyResultVo policy)
			throws RollbackableBizException {
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
		AllocIpParamVo allocIpParam = new AllocIpParamVo();
		RmClusterPo cls = rmclsDao.findRmClusterPoById(policy.getClusterId());
		allocIpParam.setResPoolId(policy.getPoolId());
		RmPlatformPo platform = rmVmManageServerDao.getPlatformInfoById(service.getPlatformType());
		allocIpParam.setPlatformType(platform.getPlatformCode());
		RmVirtualTypePo rmVirtualType = deployunitDao.findObjectByID("selectRmVirtualTypePoById", service.getVmType());
		allocIpParam.setVmType(rmVirtualType.getVirtualTypeCode());
		RmHostTypePo rmHostType = rmHostTypeDAO.getRmHostTypeById(service.getHostType());
		allocIpParam.setDeviceType(rmHostType.getHostTypeCode());
		allocIpParam.setNetworkConvergence(cls.getNetworkConvergence());
		String duId = json.getString("duId");
		if (duId != null && !duId.equals("") && !duId.equals("null")) {
			DeployUnitVo du = deployunitDao.getDeployUnitById(duId);
			allocIpParam.setAppDuId(du.getDuId());
			allocIpParam.setSecureAreaType(du.getSecureAreaCode());
			allocIpParam.setSecureLayer(du.getSevureTierCode());
			allocIpParam.setDatacenterId(du.getDatacenterId());
		} else {
			// 查询资源池的安全区域和安全层
			RmResPoolVo rmResPoolVo = rmResPoolDAO.findRmResPoolVoById(policy.getPoolId());
			allocIpParam.setSecureAreaType(rmResPoolVo.getSecureAreaType());
			allocIpParam.setSecureLayer(rmResPoolVo.getSecureLayer());
			allocIpParam.setDatacenterId(json.getString("datacenterId"));
		}

		// 增加云服务ha类型数据项
		allocIpParam.setCloudServiceHATypeCode(service.getHaType());
		return allocIpParam;
	}

	private void insertBatchVirtualData(BmSrRrinfoPo rrinfo, CloudServicePo service, List<String> vmIdList,
			List<String> vmNameList, List<PolicyResultVo> policyList, String projectId)
			throws RollbackableBizException {
		CmDevicePo hostDevice;
		JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
		boolean fiFlag = json.getBooleanValue("fiFlag");
		List<CmDevicePo> deviceList = new ArrayList<CmDevicePo>();
		CmDevicePo device;
		// CmHostPo host;
		CloudImage cloudImage;
		List<CmVmPo> vmList = new ArrayList<CmVmPo>();
		CmVmPo vm = null;
		List<BmSrRrVmRefPo> vmRefList = new ArrayList<BmSrRrVmRefPo>();
		BmSrRrVmRefPo vmRef;
		List<CmPasswordPo> pwdList = new ArrayList<CmPasswordPo>();
		CmPasswordPo pwd;
		String hostId, vmId;
		CmPasswordPo imagePwd = cmPasswordDao.findCmPasswordByResourceId(service.getImageId()); // 根据获取用户名密码
		if (!fiFlag) {
			if (imagePwd == null) {
				throw new RollbackableBizException("获取不到【" + service.getServiceName() + "】服务对应镜像的密码！");
			}

			try {
				cloudImage = imageDao.findImage(service.getImageId());
			} catch (Exception e) {
				throw new RollbackableBizException("获取不到【" + service.getServiceName() + "】服务对应镜像的信息！");
			}

			for (int i = 0; i < vmNameList.size(); i++) {
				vmId = vmIdList.get(i);
				PolicyResultVo policyResultVo = policyList.get(i);
				hostId = policyResultVo.getHostId();
				// host = cmHostDao.findCmHostById(hostId);
				// if(host == null) {
				// throw new RollbackableBizException("预分配的物理机不存在");
				// }
				// 虚拟机设备表信息初始化
				hostDevice = cmDeviceDao.findCmDeviceById(hostId);
				device = new CmDevicePo();
				device.setId(vmId);
				device.setDeviceName(vmNameList.get(i));
				device.setLparName(vmNameList.get(i)); // powervm
				device.setLparNamePrefix(vmNameList.get(i)); // powervm
				device.setSn(hostDevice == null ? "" : hostDevice.getSn());
				device.setResPoolId(policyList.get(i).getPoolId());
				device.setIsActive(IsActiveEnum.YES.getValue());
				device.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_BUILDING.getValue());
				deviceList.add(device);
				// 虚拟机表初始化
				vm = new CmVmPo();
				vm.setId(vmId);
				vm.setHostId(hostId);
				vm.setCpu(json.getInteger("cpu"));
				vm.setMem(json.getInteger("mem"));
				// OpenstackX86平台，cm_vm表保存的是页面选的磁盘大小
				if (service.getPlatformType() != null && service.getPlatformType().equals("4")) {
					vm.setDisk(json.getIntValue("sysDisk"));
				} else {
					// 其他平台的虚拟机，cm_vm表中保存的是镜像的磁盘容量
					vm.setDisk(Integer.valueOf(cloudImage.getDiskCapacity()));
				}
				vm.setCreateTime(SrDateUtil.getSrFortime(new Date()));
				vm.setDuId(json.getString("duId"));
				vm.setAppId(json.getString("appId"));
				vm.setOrderNum(Integer.valueOf(vmNameList.get(i).substring(vmNameList.get(i).length() - 3)));
				logger.info("异常projectId{}",projectId);
				logger.info("异常json.getString projectId{}",json.getString("projectId"));
				vm.setProjectId(StringUtils.isNotEmpty(projectId)?projectId:json.getString("projectId"));
				String platformCode = "";
				if (service.getPlatformType().equals("1")) {
					platformCode = "X";
					vm.setDatastoreId(policyResultVo.getDataStoreId());
					/*vm.setDatastoreType(cmDeviceDao.findDatastoreTypeById(hostId));*/
				} else if (service.getPlatformType().equals("2")) {
					platformCode = "P";
				} else if (service.getPlatformType().equals("4")) {
					platformCode = "O";
				} else if (service.getPlatformType().equals("5")) {
					platformCode = "PV";
				}
				vm.setServiceId(service.getServiceId());
				vm.setPlatformCode(platformCode);
				vm.setTenantId(rrinfo.getTenantId());
				// 虚拟机组ID
				vm.setVmGroupId(json.getString("vmGroupId"));
				vmList.add(vm);
				// 虚拟机与资源申请关系表初始化
				vmRef = new BmSrRrVmRefPo();
				vmRef.setRefId(UUIDGenerator.getUUID());
				vmRef.setSrId(rrinfo.getSrId());
				vmRef.setRrinfoId(rrinfo.getRrinfoId());
				vmRef.setDeviceId(vmId);
				vmRef.setVolumeType(json.getString("VOLUME_TYPE_ID"));
				vmRefList.add(vmRef);
				pwd = new CmPasswordPo();
				pwd.setId(UUIDGenerator.getUUID());
				pwd.setResourceId(vmId);
				if (imagePwd != null) {
					pwd.setUserName(imagePwd.getUserName());
					pwd.setPassword(imagePwd.getPassword());
				}
				pwd.setLastModifyTime(SrDateUtil.getSrFortime(new Date()));
				pwdList.add(pwd);
			}

		}
		// 将虚拟机信息保存到设备表
		cmDeviceDao.insertCmDevice(deviceList);
		// 将虚拟机信息保存到虚拟机表
		cmVmDao.insertCmVm(vmList);
		// 更新物理机已用的cpu和内存
		cmHostDao.updateCmHostUsed();
		// 将虚拟机信息同时保存到原虚拟机表
		cmVmDao.insertOldCmVm(vmList);
		// 将资源申请的虚拟机保存到关系表
		bmSrRrVmRefDao.insertBmSrRrVmRef(vmRefList);
		// 将虚拟机密码信息保存到密码表
		cmPasswordDao.insertCmPassword(pwdList);
	}

	public synchronized String doUpdateResourceAssignNew(String srId) throws BizException {
		// 定义异常字符串
		String eStr = "";
		// 查询未分配成功的资源申请信息
		List<BmSrRrinfoPo> rrinfoList = bmSrRrinfoDao.findNoAssignRrinfoListBySrId(srId);
		// 循环分配每个资源申请
		int len = rrinfoList == null ? 0 : rrinfoList.size();
		// bm_sr信息查询租户ID信息
		String tenantId = "";
		BmSrVo bmSr = bmSrDao.findBmSrVoById(srId);
		if (bmSr != null) {
			tenantId = bmSr.getTenantId();
		}
		BmSrRrinfoPo rrinfo;
		CloudServicePo service;
		DeployUnitVo du;
		List<PolicyResultVo> policyList = null;
		List<String> vmNameList = null;
		List<String> vmIdList = null;
		for (int i = 0; i < len; i++) {
			vmIdList = new ArrayList<String>();
			rrinfo = rrinfoList.get(i);
			JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
			boolean fiFlag = json.getBooleanValue("fiFlag");
			// 非大数据资源分配
			if (!fiFlag) {
				if (!CommonUtil.isEmpty(tenantId)) {
					rrinfo.setTenantId(tenantId);
				}
				service = cloudServiceDao.findById(json.getString("serviceId"));
				du = deployunitDao.getDeployUnitById(json.getString("duId"));
				try {
					int vmNum = Integer.valueOf(json.getString("vmNum"));
					if (vmNum <= 0) {
						eStr = "资源申请失败原因：申请单的虚机数量为0.";
						continue;
					}
					List<PolicyInfoVo> hostList = cmHostDao.findHostByResPoolId(json.getString("rmHostRespoolId"));
					if (hostList == null || hostList.size() == 0) {
						eStr = "资源申请失败原因：没有可用的计算资源.";
						continue;
					}
					// 通过关联的云服务查询平台类型
					String platformTypeId = service.getPlatformType();
					RmPlatformPo platformPo = rmPlatformServiceImpl.selectRmPlatform(platformTypeId);
					String platformTypeCode = platformPo.getPlatformCode();
					// 将云服务值 添加到服务请求信息对象
					rrinfo.setPlatformType(platformTypeCode);
					if (PlatFormTypeEnum.O.getCode().equals(platformTypeCode)  || PlatFormTypeEnum.PV.getCode().equals(platformTypeCode)) {
						logger.debug("分配OpenStack,powerv vc 资源");
						// openstack计算资源分配
						policyList = this.assigneComputeResourceForOPenstack(json.getString("rmHostRespoolId"), vmNum,
								hostList,json.getString("appointHostId"));
					} else if (PlatFormTypeEnum.X.getCode().equals(platformTypeCode)) {
						logger.debug("分配VMware计算资源");
						// VMware计算资源分配
						policyList = computePolicyService.distribHostForVmWare(hostList, rrinfo, vmNum);
						// VMwaredatastore资源分配
					} else {
						logger.error("未匹配可分配计算资源的平台类型" + platformTypeCode);
					}
				} catch (Exception e) {
					logger.error("异常exception", e);
					eStr = eStr + (du == null ? "" : "【" + du.getFullCname() + "】") + "资源申请失败原因：虚机分配算法异常，"
							+ e.getMessage() + "";
					continue; // by wangdy 没有资源的申请就跳过，不需要中断程序。
				}
				try { // 生成虚拟机名称
					HashMap<String, Object> paramMap = Maps.newHashMap();
					paramMap.put("duId", json.getString("duId"));
					paramMap.put("nameNumber", json.getInteger("vmNum"));
					paramMap.put("ls", policyList);
					paramMap.put("sp", service);
					paramMap.put("dcId", json.getString("datacenterId"));
					vmNameList = ComputeResourceNameManager
							.getRecourceName(ComputeResourceNameTypeEnum.VMNAME.getValue(), paramMap);

				} catch (Exception e) {
					logger.error("异常exception", e);
					eStr = eStr + "【" + du.getFullCname() + "】资源申请失败原因：生成虚机名称算法异常，" + e.getMessage() + "";
					continue;
				}
				if (vmNameList == null || vmNameList.size() == 0) {
					eStr = eStr + "【" + du.getFullCname() + "】资源申请失败原因：生成的虚机名称列表为空。";
					continue;
				}
				if (vmNameList.size() != policyList.size()) {
					eStr = eStr + "【" + du.getFullCname() + "】资源申请失败原因：获取到的虚机名称数量与实际申请虚机数量不匹配。";
					continue;
				}

				// 生成虚机id
				for (int j = 0; j < policyList.size(); j++) {
					vmIdList.add(UUIDGenerator.getUUID());
				}

				try {
					saveResNew(rrinfo, service, du, policyList, vmNameList, vmIdList, json.getString("rmNwResPoolId"));
				} catch (RollbackableBizException e) {
					eStr = eStr + e.getMessage();
				}
			} else {
				if (!CommonUtil.isEmpty(tenantId)) {
					rrinfo.setTenantId(tenantId);
				}
				service = cloudServiceDao.findById(json.getString("serviceId"));
				du = deployunitDao.getDeployUnitById(json.getString("duId"));

				vmNameList = VmComputeResourceNameManager.getNames(json.getIntValue("vmNum"));
				if (vmNameList == null || vmNameList.size() == 0) {
					eStr = eStr + "【" + du.getFullCname() + "】资源申请失败原因：生成的虚机名称列表为空。";
					continue;
				}

				// 生成虚机id
				for (int j = 0; j < json.getIntValue("vmNum"); j++) {
					vmIdList.add(UUIDGenerator.getUUID());
				}
				try {
					saveResNew(rrinfo, service, du, policyList, vmNameList, vmIdList, json.getString("rmNwResPoolId"));
				} catch (RollbackableBizException e) {
					eStr = eStr + e.getMessage();
				}
			}
		}
		return eStr;
	}

	public ParameterService getParameterService() throws Exception {
		return (ParameterService) WebApplicationManager.getBean("parameterServiceImpl");
	}

	/**
	 * openstack分配计算资源
	 * 
	 * @return
	 * @throws RollbackableBizException 
	 */
	private List<PolicyResultVo> assigneComputeResourceForOPenstack(String respoolId, int reqVMNum,
			List<PolicyInfoVo> hostList,String assigneHostId) throws Exception {
		List<PolicyResultVo> policyList = new ArrayList<PolicyResultVo>();
		PolicyResultVo policyResult;
		if (assigneHostId != null && !"".equals(assigneHostId)) {
			// 指定物理机
			// 根据指定主机ID获取主机所属集群和资源池ID
			CmHostPo cmHost = cmHostDao.findCmHostById(assigneHostId);
			for (int j = 0 ; j < reqVMNum ; j ++) {
				policyResult = new PolicyResultVo();
				policyResult.setPoolId(respoolId);
				policyResult.setClusterId(cmHost.getClusterId());
				policyResult.setHostId(assigneHostId);
				policyList.add(policyResult);
			}
		} else {
			if(hostList == null || hostList.size() == 0) {
				throw new Exception("资源申请失败原因：没有可用的计算资源.");
			}
			int n = 0;
			for(int j=0 ; j<reqVMNum ; j++) {
				if(n >= hostList.size()) {
					n = 0;
				}
				policyResult = new PolicyResultVo();
				policyResult.setPoolId(respoolId);
				policyResult.setClusterId(hostList.get(n).getClusterId());
				policyResult.setHostId(hostList.get(n).getHostId());
				policyList.add(policyResult);
				n++;
			}
		}
		return policyList;
	}

	public synchronized void doUpdateResourceRecycle(String srId, String rrinfoId) throws Exception {
		List<BmSrRrVmRefPo> refList = null;
		int refLen = 0;
		if (rrinfoId != null && !"".equals(rrinfoId)) {
			refList = bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(rrinfoId);
			refLen = refList == null ? 0 : refList.size();
			if (refLen > 0) {
				bmSrRrVmRefDao.deleteBmSrRrVmRefByRrinfoId(rrinfoId);
			}
		} else {
			refList = bmSrRrVmRefDao.findBmSrRrVmRefListBySrId(srId);
			refLen = refList == null ? 0 : refList.size();
			if (refLen > 0) {
				bmSrRrVmRefDao.deleteBmSrRrVmRefBySrId(srId);
			}
		}
		try {
			String platform = "";
			if (rrinfoId != null && !"".equals(rrinfoId)) {
				BmSrRrinfoPo rrinfo = bmSrRrinfoDao.findBmSrRrinfoById(rrinfoId);
				CloudServicePo service = cloudServiceDao.findById(rrinfo.getServerId());
				platform = service.getPlatformType();
			} else {
				List<BmSrRrinfoPo> rrinfoList = bmSrRrinfoDao.findBmSrRrinfoBySrId(srId);
				if (rrinfoList != null && rrinfoList.size() > 0) {
					JSONObject json = JSONObject.parseObject(rrinfoList.get(0).getParametersJson());
					CloudServicePo service = cloudServiceDao.findById(json.getString("serviceId"));
					platform = service.getPlatformType();
				}
			}
			if (platform != null && "4".equals(platform)) {
				// 只有OpenStack需要做回收OpenStack底层资源
				this.openstackResourceRecycle(refList);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 大数据相关
		List<BmSrRrinfoVo> srRrinfoList = bmSrRrinfoDao.findBmSrRrinfoListBySrId(srId);
		for (BmSrRrinfoVo bmSrRrinfoVo : srRrinfoList) {
			JSONObject json = JSONObject.parseObject(bmSrRrinfoVo.getParametersJson());
			if (!json.containsKey("clusterId") && json.containsKey("serviceInstanceId")) {
				String instanceId = json.getString("serviceInstanceId");
				cmDeviceDao.deleteServiceInstanceById(instanceId);
				cmDeviceDao.deleteServiceInstanceRefById(instanceId);
			}
		}
		String deviceId;
		for (int i = 0 ; i < refLen ; i ++) {
			deviceId = refList.get(i).getDeviceId();
			try {
				// 回收IP地址信息
				OpenstackIpAddressPo ipAddress = new OpenstackIpAddressPo();
				ipAddress.setInstanceId(deviceId);
				virtualNetworkDao.updateIpAddressRecycleByVm(ipAddress);
			} catch (Exception e) {
				logger.error("异常exception", e);
				throw new RollbackableBizException("回收网络IP失败，设备ID：" + deviceId);
			}
			// 删除密码表信息
			cmPasswordDao.deleteCmPassword(deviceId);
			// 删除虚机配置表信息（这里没有删除CM_VM_OLD表信息，待确认再进行操作）
//			cmVmDao.delete("deleteCmVmById", deviceId);
			// 删除虚机设备表信息
			cmDeviceDao.delete("updateCmDeviceById", deviceId);
		}
		// 更新物理机已用的cpu和内存
		if (refLen > 0) {
			cmHostDao.updateCmHostUsed();
		}
	}

	private void openstackResourceRecycle(List<BmSrRrVmRefPo> refList) throws Exception {
		int refLen = refList == null ? 0 : refList.size();
		String deviceId, targetId;
		String vmMsId = "";
		String version = "";
		OpenstackVolumeDaoImpl openstackVolumeDaoImpl = (OpenstackVolumeDaoImpl) WebApplicationManager.getBean("openstackVolumeDaoImpl");
		for (int i = 0 ; i < refLen ; i++) {
			deviceId = refList.get(i).getDeviceId();
			targetId = "";
			HashMap<String, String> bizParamMap = bizParamInstServiceImpl.getInstanceParasMapByDevice(refList.get(i).getSrId(), refList.get(i).getRrinfoId(), deviceId);
			vmMsId = bizParamMap.get("OPENSTACK_ID");
			version = bizParamMap.get("VERSION");
			// 创建的系统盘信息
			String targetVolumeId = bizParamMap.get("VOLUME_ID");
			if (targetVolumeId == null) {
				logger.info("there is no sys disk for device ["+deviceId+"].");
				continue;
			}
			try {
				targetId = cmVmDao.findCmVmById(deviceId).getIaasUuid();
			} catch (RollbackableBizException e) {
				e.printStackTrace();
			}
			OpenstackIdentityModel model = new OpenstackIdentityModel();
			model.setVersion(version);
			model.setOpenstackIp(bizParamMap.get("OPENSTACK_IP"));
			model.setDomainName(bizParamMap.get("DOMAIN_NAME"));
			model.setManageOneIp(bizParamMap.get("MANAGE_ONE_IP"));
			model.setProjectId(bizParamMap.get("PROJECT_ID"));
			model.setProjectName(bizParamMap.get("PROJECT_NAME"));
			if (!"".equals(targetId)) {
				// 虚拟机已经创建成功，开始进行删除虚机
				// 先判断虚机是否存在
				boolean vmExist = false;
	            try {
	            	IaasInstanceFactory.computeInstance(version).getServerDetail(model, targetId, true);
	            	vmExist = true;
	            } catch (Exception e) {
	            	// 此虚机在OpenStack上不存在，不做特殊事情
	            	logger.error("get detail info has error for device ["+deviceId+"], targetId ["+targetId+"].");
	            	e.printStackTrace();
	            }
	            if (vmExist) {
	            	// 查询虚拟机是否存在数据盘，如存在则删除
	            	String jsonData = IaasInstanceFactory.computeInstance(version).getServerVolume(model, targetId, true);
	     			logger.info("the device [" + deviceId + "] mount the data disks detail : " + jsonData);
	     			JSONObject json = JSONObject.parseObject(jsonData);
	     			JSONObject subJson;
	     			JSONArray array = json.getJSONArray("volumeAttachments");
	     			for(int j=0 ; j<array.size() ; j++) {
	     				subJson = array.getJSONObject(j);
	     				String dataVolumeId = subJson.getString("volumeId");
	     				if (targetVolumeId.equals(dataVolumeId)) {
	     					// 系统卷不在这里处理
	     					continue;
	     				}
	     				// 卸载数据卷
	     				try {
	     					IaasInstanceFactory.computeInstance(version).unmountServerVolume(model, targetId, dataVolumeId, true);
	     					// 验证卸载是否成功
	     					int n = 10;
	     					while (true) {
	     						if (n == 0) {
	     							break;
	     						}
	     						try {
	     							Thread.sleep(5000);
	     						} catch(Exception e) {
	     							e.printStackTrace();
	     						}
	     						String state = IaasInstanceFactory.storageInstance(version).getVolumeStatus(model, dataVolumeId);
	     						if (state.equals("available") || state.equals("error") || state.equals("ERROR")) {
	     							break;
	     						}
	     						n--;
	     					}
	     					// 删除数据卷
	     					try {
	     						IaasInstanceFactory.storageInstance(version).deleteVolume(model, dataVolumeId);
	     					} catch(Exception e) {
	     						logger.error("delete data disk has error for volume ["+dataVolumeId+"].");
	     						e.printStackTrace();
	     					}
	     				} catch(Exception e) {
	     					logger.error("unmount data disk has error for volume ["+dataVolumeId+"].");
	     					e.printStackTrace();
	     				}
	     			}
	            	// 若能够查询到此虚机，则说明虚机在OpenStack存在，进行删除虚机
	            	try {
	            		IaasInstanceFactory.computeInstance(version).operateVm(model, targetId, OSOperation_bak.DELETE_VM, 204);
	            	} catch (Exception e) {
	            		logger.error("delete vm has error for device ["+deviceId+"], targetId ["+targetId+"].");
	            		e.printStackTrace();
	            	}
	            }
			} else {
				logger.info("there is no targetServerId for device ["+deviceId+"].");
			}
			// 开始进行删除系统盘
			boolean volumeExist = false;
			try {
				String state = IaasInstanceFactory.storageInstance(version).getVolumeStatus(model, targetVolumeId);
				if (state.equals("available")) {
					volumeExist = true;
				} else {
					logger.error("get sys disk status is ["+state+"] for device ["+deviceId+"], targetVolumeId ["+targetVolumeId+"].");
				}
			} catch (Exception e) {
				// 系统卷在OpenStack上不存在，不做特殊事情
				logger.error("get sys disk status has error for device ["+deviceId+"], targetVolumeId ["+targetVolumeId+"].");
			}
			if (volumeExist) {
				try {
					// 如果当前系统卷为可用卷，则需要进行删除
					IaasInstanceFactory.storageInstance(version).deleteVolume(model, targetVolumeId);
				} catch (Exception e) {
					logger.error("delete sys disk has error for targetVolumeId ["+targetVolumeId+"], error is : " + e.getMessage());
					e.printStackTrace();
				}
			}
			// 删除云管的系统盘信息
			try {
				String sysVolumeId = openstackVolumeDaoImpl.selectCloudVolumeIdByIaasUuid(targetVolumeId).getId();
				openstackVolumeDaoImpl.deleteVolume(sysVolumeId);
			} catch (Exception e) {
				logger.error("delete sys disk has error at cmp for targetVolumeId ["+targetVolumeId+"], error is : " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void setComputePolicyService(IComputePolicyService computePolicyService) {
		this.computePolicyService = computePolicyService;
	}

	public void setBmSrDao(IBmSrDao bmSrDao) {
		this.bmSrDao = bmSrDao;
	}

	public void setBmSrRrinfoDao(IBmSrRrinfoDao bmSrRrinfoDao) {
		this.bmSrRrinfoDao = bmSrRrinfoDao;
	}

	public void setBmSrRrVmRefDao(IBmSrRrVmRefDao bmSrRrVmRefDao) {
		this.bmSrRrVmRefDao = bmSrRrVmRefDao;
	}

	public void setBmSrAttrValDao(IBmSrAttrValDao bmSrAttrValDao) {
		this.bmSrAttrValDao = bmSrAttrValDao;
	}

	public void setVirtualSupplyDAO(IVirtualSupplyDAO virtualSupplyDAO) {
		this.virtualSupplyDAO = virtualSupplyDAO;
	}

	public void setCloudServiceDao(ICloudServiceDao cloudServiceDao) {
		this.cloudServiceDao = cloudServiceDao;
	}

	public void setDeployunitDao(IDeployunitDao deployunitDao) {
		this.deployunitDao = deployunitDao;
	}

	public void setCmDeviceDao(ICmDeviceDAO cmDeviceDao) {
		this.cmDeviceDao = cmDeviceDao;
	}

	public void setCmHostDao(ICmHostDAO cmHostDao) {
		this.cmHostDao = cmHostDao;
	}

	public void setCmVmDao(ICmVmDAO cmVmDao) {
		this.cmVmDao = cmVmDao;
	}

	public void setCmPasswordDao(ICmPasswordDAO cmPasswordDao) {
		this.cmPasswordDao = cmPasswordDao;
	}

	public void setRmVmManageServerDao(IRmVmManageServerDAO rmVmManageServerDao) {
		this.rmVmManageServerDao = rmVmManageServerDao;
	}

	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	public ImageDao getImageDao() {
		return imageDao;
	}

	public void setVirtualNetworkService(IVirtualNetworkService virtualNetworkService) {
		this.virtualNetworkService = virtualNetworkService;
	}

	public void setVirtualNetworkDao(IVirtualNetworkDao virtualNetworkDao) {
		this.virtualNetworkDao = virtualNetworkDao;
	}

	public void setBizParamInstServiceImpl(BizParamInstService bizParamInstServiceImpl) {
		this.bizParamInstServiceImpl = bizParamInstServiceImpl;
	}

}