package com.git.cloud.handler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.po.AppSysPO;
import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.cloudservice.dao.ICloudServiceDao;
import com.git.cloud.cloudservice.dao.IPackageDefDao;
import com.git.cloud.cloudservice.dao.IScriptDao;
import com.git.cloud.cloudservice.dao.IScriptParamDao;
import com.git.cloud.cloudservice.dao.ImageDao;
import com.git.cloud.cloudservice.model.po.CloudImage;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.model.po.PackageModel;
import com.git.cloud.cloudservice.model.po.ScriptModel;
import com.git.cloud.cloudservice.model.vo.ScriptFullPathVo;
import com.git.cloud.cloudservice.model.vo.ScriptModelVO;
import com.git.cloud.cloudservice.model.vo.ScriptParamModelVO;
import com.git.cloud.common.enums.CUseType;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmPlatForm;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.handler.automation.HandlerReturnObject;
import com.git.cloud.handler.automation.HandlerReturnObjectForOneDev;
import com.git.cloud.handler.common.Utils;
import com.git.cloud.handler.dao.AutomationDAO;
import com.git.cloud.handler.po.CmRoutePo;
import com.git.cloud.handler.vo.OpenstackVmParamVo;
import com.git.cloud.handler.vo.VcenterRfDeivceVo;
import com.git.cloud.handler.vo.VmDeviceVo;
import com.git.cloud.parame.service.ParameterService;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.request.dao.IBmSrAttrValDao;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.vo.BmSrAttrValVo;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDatastoreRefDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.model.DatastoreTypeEnum;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmGeneralServerPo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.resmgt.compute.dao.IRmCdpDAO;
import com.git.cloud.resmgt.compute.dao.IRmClusterDAO;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.emory.mathcs.backport.java.util.Arrays;

public class AutomationServiceImpl implements AutomationService {
	
	private static Logger logger = LoggerFactory.getLogger(AutomationServiceImpl.class);

	private IIpAllocToDeviceNewService ipAllocToDevice;
	private ParameterService  parameterServiceImpl;
	private AutomationDAO automationDao;
	private ICmVmDAO cmVmDao;
	private IBmSrRrinfoDao bmSrRrinfoDao;
	private IBmSrRrVmRefDao bmSrRrVmRefDao;
	private IBmSrAttrValDao bmSrAttrValDao;
	private ICloudServiceDao cloudServiceDao;
	private IPackageDefDao packageDefDao;
	private ImageDao imageDao;
	private IScriptDao scriptDao;
	private IScriptParamDao scriptParamDao;
	private ICmPasswordDAO cmPasswordDao;
	private ICmDeviceDAO cmDeviceDao;
	private IDeployunitDao deployunitDao;
	private IRmCdpDAO rmCdpDao;
	private IRmClusterDAO rmClusterDao;
	private ICmHostDAO cmHostDao;
	private ICmHostDatastoreRefDAO cmHostDatastoreRefDao;
	private IRmVmManageServerDAO rmVmManageServerDAO;
	private IRmDatacenterDAO rmDatacenterDAO;

	@Override
	public String getAppParameter(String paraName) throws BizException {
		return parameterServiceImpl.getParamValueByName(paraName);
	}

	@Override
	public List<String> getDeviceIdsSort(String rrinfoId) throws BizException {
		List<BmSrRrVmRefPo> vmRefList = bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(rrinfoId);
		List<String> vmIdList = new ArrayList<String> ();
		int len = vmRefList == null ? 0 : vmRefList.size();
		for(int i=0 ; i<len ; i++) {
			vmIdList.add(vmRefList.get(i).getDeviceId());
		}
		return vmIdList;
	}

	@Override
	public String makeInstanceReturnString(HandlerReturnObject returnObject)
			throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 生成code
		map.put("checkCode", returnObject.getReturnCode());
		BmSrRrinfoPo rrInfo = bmSrRrinfoDao.findBmSrRrinfoById(returnObject.getRrInfoId());
		if (rrInfo == null) {
			StringBuffer returnStr = new StringBuffer();
			for (HandlerReturnObjectForOneDev oneObj : returnObject.getDevObjects()) {
				StringBuffer oneObjStr = new StringBuffer();
				String devId = oneObj.getDeviceId();
				CmDevicePo dev = cmDeviceDao.findCmDeviceById(devId);
				String devName = dev.getDeviceName();
				String mesg = oneObj.getMesg();
				oneObjStr.append("主机名：").append(devName).append(", ");
				oneObjStr.append("输出：").append(Utils.BR).append(mesg);
				returnStr.append(oneObjStr.toString()).append(Utils.BR);
				returnStr.append("============================================").append(Utils.BR);
			}
			map.put("exeMsg", returnStr);
			String jsonDATA = JSON.toJSONString(map);
			logger.info("AutomationReturnString is: " + jsonDATA);
			return jsonDATA;
		}
		JSONObject json = JSONObject.parseObject(rrInfo.getParametersJson());
		//CloudServicePo service = cloudServiceDao.findById(rrInfo.getServiceId());
		CloudServicePo service = cloudServiceDao.findById(json.getString("serviceId"));
		IIpAllocToDeviceNewService ipAllocToVMService = (IIpAllocToDeviceNewService) WebApplicationManager.getBean("ipAllocToDeviceImpl");
		
		// 生成文本信息
		StringBuffer returnStr = new StringBuffer();
		String duName = null;
		String devName = null;
		String ipAddr = null;
		String mesg = null;
		// 如果服务申请为供给，则读取rrinfo中的服务器角色;如果服务申请为回收，则读取虚拟机中的du_id
		DeployUnitVo du = deployunitDao.getDeployUnitById(json.getString("duId"));
		if(du!=null){
			duName = du.getFullCname();
		}
		String netType = service.getPlatformType() + service.getVmType() + RmHostType.VIRTUAL + CUseType.MGMT;
		for (HandlerReturnObjectForOneDev oneObj : returnObject.getDevObjects()) {
			StringBuffer oneObjStr = new StringBuffer();
			String devId = oneObj.getDeviceId();
			CmDevicePo dev = cmDeviceDao.findCmDeviceById(devId);
			devName = dev.getDeviceName();
			try {
				List<DeviceNetIP> deviceNetIPs = ipAllocToVMService.qryAllocedIPForDevices(oneObj.getDeviceId());
				List<NetIPInfo> vmIPs = deviceNetIPs.get(0).getNetIPs().get(netType);
				if (vmIPs != null && vmIPs.size() > 0) {
					ipAddr = vmIPs.get(0).getIp();
				}
			} catch (Exception e) {
				logger.error("Can not found ip, " + e);
			}
			mesg = oneObj.getMesg();
			oneObjStr.append("主机名：").append(devName).append(", ");
			if (ipAddr != null) {
				oneObjStr.append("Ip地址：").append(ipAddr).append(", ");
			}
			oneObjStr.append("服务器角色：").append(duName).append(Utils.BR);
			oneObjStr.append("输出：").append(Utils.BR).append(mesg);
			returnStr.append(oneObjStr.toString()).append(Utils.BR);
			returnStr.append("============================================").append(Utils.BR);
		}
		map.put("exeMsg", returnStr);
		String jsonDATA = JSON.toJSONString(map);
		logger.info("AutomationReturnString is: " + jsonDATA);
		return jsonDATA;
	}

	@Override
	public RmDatacenterPo getDatacenter(String rrInfoId) throws BizException {
		return automationDao.getDatacenter(rrInfoId);
	}

	@Override
	public Map<String, String> getSrAttrInfoByRrinfoId(String rrinfoId)
			throws BizException {
		List<BmSrAttrValVo> attrValList = bmSrAttrValDao.findBmSrAttrValListByRrinfoId(rrinfoId);
		BmSrAttrValVo attrVal;
		int len = attrValList == null ? 0 : attrValList.size();
		Map<String, String> attrValMap = new HashMap<String, String> ();
		for(int i=0 ; i<len ; i++) {
			attrVal = attrValList.get(i);
			attrValMap.put(attrVal.getAttrName(), attrVal.getAttrValue());
		}
		return attrValMap;
	}

	@Override
	public ScriptModelVO getScript(String scriptId) throws BizException {
		return scriptDao.load(scriptId);
	}

	@Override
	public String getScriptFullPath(String scriptId) throws BizException {
		ScriptFullPathVo sfp = scriptDao.findScriptFullPath(scriptId);
		if(sfp == null) {
			throw new BizException("获取不到脚本路径");
		}
		return sfp.getPackageFillPath() + "/" + sfp.getModelFilePath() + "/" + sfp.getScriptFileName();
	}
	
	@Override
	public ScriptFullPathVo getScriptFullPathVo(String scriptId) throws BizException {
		ScriptFullPathVo sfp = scriptDao.findScriptFullPath(scriptId);
		return sfp;
	}

	@Override
	public List<String> getScriptParasSort(String scriptId) throws BizException {
		List<String> resultList = new ArrayList<String> ();
		List<ScriptParamModelVO> scriptParamList = scriptParamDao.loadParamsByScriptId(scriptId);
		int len = scriptParamList == null ? 0 : scriptParamList.size();
		for(int i=0 ; i<len ; i++) {
			resultList.add(scriptParamList.get(i).getCode());
		}
		return resultList;
	}

	@Override
	public List<CmRoutePo> getRoutes(String datacenterId) throws BizException {
		return automationDao.getRoutes(datacenterId);
	}

	@Override
	public RmDatacenterPo getDatacenterByDeviceId(String deviceId)
			throws BizException {
		return automationDao.getDatacenterByDeviceId(deviceId);
	}
	
	@Override
	public RmDatacenterPo getDatacenterByDeviceIdInResPool(String deviceId)
			throws BizException {
		return automationDao.getDatacenterByDeviceIdInResPool(deviceId);
	}

	@Override
	public BmSrPo getSrById(String srId) throws BizException {
		return automationDao.getSrById(srId);
	}

	@Override
	public RmVmManageServerPo getVmManagementServer(String datacenterId,
			String type) throws BizException {
		return automationDao.getVmManagementServer(datacenterId, type);
	}

	@Override
	public List<RmVmManageServerPo> getVmManagementServers(String datacenterId,
			String type) throws BizException {
		return automationDao.getVmManagementServers(datacenterId, type);
	}

	@Override
	public String getPassword(String resourceId, String userName)
			throws BizException {
		CmPasswordPo cmPassword = cmPasswordDao.findCmPasswordByResourceId(resourceId, userName);
		if(cmPassword == null) {
			throw new BizException("未查询到匹配的密码信息");
		}
		return PwdUtil.decryption(cmPassword.getPassword());
	}
	
	@Override
	public CmPasswordPo getPassword(String resourceId)
			throws BizException {
		CmPasswordPo cmPassword = cmPasswordDao.findCmPasswordByResourceId(resourceId);
		if(cmPassword == null) {
			throw new BizException("未查询到匹配的密码信息");
		}
		cmPassword.setPassword(PwdUtil.decryption(cmPassword.getPassword()));
		return cmPassword;
	}

	@Override
	public List<Map<String, ?>> getPackagePackNames(List<String> packNames)
			throws BizException {
		List<PackageModel> packList = packageDefDao.findPackageModelByNameList(packNames);
		List<Map<String, ?>> packMapList = new ArrayList<Map<String, ?>> ();
		Map<String, String> packMap;
		int len = packList == null ? 0 : packList.size();
		for(int i=0 ; i<len ; i++) {
			packMap = new HashMap<String, String> ();
			String id = packList.get(i).getId();
			String [] idArr = id.split(",");
			packMap.put("PACKAGE_ID", idArr[0]);
			String path = packList.get(i).getFilePath();
			String [] pathArr = path.split(",");
			packMap.put("PACKAGE_PATH", pathArr[0]);
			if(idArr.length > 1) {
				packMap.put("MODULE_ID", idArr[1]);
				packMap.put("MODULE_PATH", pathArr[1]);
			}
			packMapList.add(packMap);
		}
		return packMapList;
	}

	@Override
	public Map<String, String> getServiceAttr(String rrinfoId)
			throws BizException {
		List<BmSrAttrValVo> attrValList = bmSrAttrValDao.findBmSrAttrValListByRrinfoId(rrinfoId);
		BmSrAttrValVo attrVal;
		int len = attrValList == null ? 0 : attrValList.size();
		Map<String, String> attrValMap = new HashMap<String, String> ();
		for(int i=0 ; i<len ; i++) {
			attrVal = attrValList.get(i);
			attrValMap.put(attrVal.getAttrName(), attrVal.getAttrValue());
		}
		// 添加不可见参数
		List<BmSrAttrValVo> notVisibleAttrValList = bmSrAttrValDao.findBmSrNotVisibleAttrValListByRrinfoId(rrinfoId);
		len = notVisibleAttrValList == null ? 0 : notVisibleAttrValList.size();
		for(int i=0 ; i<len ; i++) {
			attrVal = notVisibleAttrValList.get(i);
			attrValMap.put(attrVal.getAttrName(), attrVal.getAttrValue());
		}
		return attrValMap;
	}
	
	public Map<String, String> getServiceAttrDevice(String rrinfoId, String deviceId)
			throws BizException {
		List<BmSrAttrValVo> attrValList = bmSrAttrValDao.findBmSrDeviceAttrValListByRrinfoId(rrinfoId);
		BmSrAttrValVo attrVal;
		int len = attrValList == null ? 0 : attrValList.size();
		Map<String, String> attrValMap = new HashMap<String, String> ();
		for(int i=0 ; i<len ; i++) {
			attrVal = attrValList.get(i);
			attrValMap.put(attrVal.getAttrName(), attrVal.getAttrValue());
		}
		// 添加不可见参数
		List<BmSrAttrValVo> notVisibleAttrValList = bmSrAttrValDao.findBmSrNotVisibleAttrValListByRrinfoId(rrinfoId);
		len = notVisibleAttrValList == null ? 0 : notVisibleAttrValList.size();
		for(int i=0 ; i<len ; i++) {
			attrVal = notVisibleAttrValList.get(i);
			attrValMap.put(attrVal.getAttrName(), attrVal.getAttrValue());
		}
		return attrValMap;
	}
	
	public Map<String, String> getServiceAttrForDevice(String rrinfoId, String deviceId) throws BizException {
		List<BmSrAttrValVo> attrValList = bmSrAttrValDao.findBmSrAttrAutoListByDeviceId(rrinfoId, deviceId);
		BmSrAttrValVo attrVal;
		int len = attrValList == null ? 0 : attrValList.size();
		Map<String, String> attrValMap = new HashMap<String, String> ();
		for(int i=0 ; i<len ; i++) {
			attrVal = attrValList.get(i);
			attrValMap.put(attrVal.getAttrName(), attrVal.getAttrValue());
		}
		// 添加不可见参数
		List<BmSrAttrValVo> notVisibleAttrValList = bmSrAttrValDao.findBmSrNotVisibleAttrValListByRrinfoId(rrinfoId);
		len = notVisibleAttrValList == null ? 0 : notVisibleAttrValList.size();
		for(int i=0 ; i<len ; i++) {
			attrVal = notVisibleAttrValList.get(i);
			attrValMap.put(attrVal.getAttrName(), attrVal.getAttrValue());
		}
		return attrValMap;
	}

	@Override
	public List<CmVmPo> getVms(List<String> deviceIdList) throws BizException {
		return automationDao.getVms(deviceIdList);
	}

	@Override
	public List<RmVmManageServerPo> getMgrServerInfoByDeviceId(
			List<String> deviceIdList) throws BizException {
		return rmVmManageServerDAO.findRmVmManageServerListByVmIdList(deviceIdList);
	}

	@Override
	public RmVmManageServerPo getMgrServerInfoByDeviceId(String deviceId)
			throws BizException {
		return rmVmManageServerDAO.findRmVmManageServerByVmId(deviceId);
	}

	@Override
	public CloudImage getImage(String rrinfoId) throws BizException {
		try {
			BmSrRrinfoPo rrinfo = bmSrRrinfoDao.findBmSrRrinfoById(rrinfoId);
			JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
			//CloudServicePo service = cloudServiceDao.findById(rrinfo.getServiceId());
			CloudServicePo service = cloudServiceDao.findById(json.getString("serviceId"));
			CloudImage image = new CloudImage();
			image.setImageId(service.getImageId());
			return imageDao.findImage(image.getImageId());
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return null;
	}

	@Override
	public CmDevicePo getDevice(String devId) throws BizException {
		return cmDeviceDao.findCmDeviceById(devId);
	}

	@Override
	public String getDatastoreName(String vmId) throws BizException {
		return automationDao.getDatastoreName(vmId);
	}

	@Override
	public List<String> getScriptNameByPackageIdAndModuleId(String packId,
			String moduleId) throws BizException {
		List<ScriptModel> scriptList = scriptDao.findScriptByModelId(moduleId);
		List<String> scriptNameList = new ArrayList<String> ();
		int len = scriptList == null ? 0 : scriptList.size();
		for(int i=0 ; i<len ; i++) {
			scriptNameList.add(scriptList.get(i).getFileName());
		}
		
		return scriptNameList;
	}

	@Override
	public BmSrRrinfoPo getRrinfo(String rrinfoId) throws BizException {
		return bmSrRrinfoDao.findBmSrRrinfoById(rrinfoId);
	}

	@Override
	public CloudServicePo getService(String serviceId) throws BizException {
		return cloudServiceDao.findById(serviceId);
	}

	@Override
	public CmVmPo getVm(String vmId) throws BizException {
		return cmVmDao.findCmVmById(vmId);
	}

	@Override
	public String getDeviceManagementIp(String devId) throws BizException {
		String hostIp = "";
		try {
			DeviceNetIP vmIPInfo = ipAllocToDevice.qryAllocedIPForHost(devId);
			hostIp = vmIPInfo.getHostIp();
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return hostIp;
	}

	@Override
	public int getCpuCoreNum(int cpuNum) throws BizException {
		if (cpuNum <= 0) {
			throw new BizException("cpu数量不能小于等于0");
		}
		if (cpuNum < 2) {
			return 1;
		} else if (cpuNum >= 8) {
			return 4;
		} else {
			return 2;
		}
	}

	@Override
	public List<BmSrRrVmRefPo> getSrRrVmRefs(String rrinfoId)
			throws BizException {
		return bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(rrinfoId);
	}

	@Override
	public String getHostDatastore(String hostId) throws Exception {
		String datastoreName = "";
		try {
			CmHostPo cmHost = cmHostDao.findCmHostById(hostId);
			if(DatastoreTypeEnum.DATASTORE_TYPE_LOCAL_DISK.getValue().equals(cmHost.getDatastoreType())) {
				datastoreName = cmHost.getLocalDatastoreName();
			} else if(DatastoreTypeEnum.DATASTORE_TYPE_NAS_DATASTORE.getValue().equals(cmHost.getDatastoreType())){
				datastoreName = cmHost.getDatastoreName();
			}
		} catch (Exception e) {
			throw e;
		}
		return datastoreName;
	}
	
	@Override
	public List<RmGeneralServerPo> getGeneralServers(String datacenterId, String type) throws BizException {
		List<RmGeneralServerPo> generalServerPos = automationDao.getGaneralServers(datacenterId, type);
		for (RmGeneralServerPo rmGeneralServerPo : generalServerPos) {
			String resourceId = rmGeneralServerPo.getId();
			String user = rmGeneralServerPo.getUserName();
			if (user != null) {
				CmPasswordPo pwdPo = cmPasswordDao.findCmPasswordByResourceUser(resourceId, user);
				if (pwdPo != null) {
					rmGeneralServerPo.setPwd(pwdPo.getPassword());
				}
			}
		}
		return generalServerPos;
	}
	
	public List<VcenterRfDeivceVo> getVcenterRfDeivce(String rrinfoId) throws BizException {
		List<VmDeviceVo> vmdeviceList = automationDao.getVmVcenter(rrinfoId);
		HashMap<String, VcenterRfDeivceVo> vcenterMap = Maps.newHashMap();
		List<VcenterRfDeivceVo> vcenterList = Lists.newArrayList();
		VcenterRfDeivceVo vcentervo;
		for (VmDeviceVo vmvo : vmdeviceList) {
			if (vcenterMap.get(vmvo.getVcenterUrl()) != null) {
				vcentervo = vcenterMap.get(vmvo.getVcenterUrl());
				vcentervo.getVmNames().add(vmvo.getVmName());

			} else {
				vcentervo = new VcenterRfDeivceVo();
				List<String> vmNameList = Lists.newArrayList();
				vcentervo.setVcenterName(vmvo.getVcenterName());
				vcentervo.setVcenterPwd(PwdUtil.decryption(vmvo.getVcenterPwd()));
				vcentervo.setVcenterUrl(vmvo.getVcenterUrl());
				vcentervo.setDatacenterId(vmvo.getDatacenterId());
				vmNameList.add(vmvo.getVmName());
				vcentervo.setVmNames(vmNameList);
				vcenterMap.put(vmvo.getVcenterUrl(), vcentervo);
			}

		}
		vcenterList.addAll(vcenterMap.values());
		return vcenterList;
	}
	
	public String findDcIdenById(String datacenterId) throws BizException {
		if(datacenterId == null && !"".equals(datacenterId)) {
			return "";
		}
		RmDatacenterPo dc = rmDatacenterDAO.getDataCenterById(datacenterId);
		String dcIden = dc == null ? "" : dc.getQueueIden();
		return dcIden == null ? "" : dcIden;
	}
	
	public CmHostPo getCmHostPo(String hostId)  throws BizException{
		CmHostPo cmHost = cmHostDao.findCmHostById(hostId);
		return cmHost;
	}

	/*******************************setter&&getter**************************/
	public void setIpAllocToDevice(IIpAllocToDeviceNewService ipAllocToDevice) {
		this.ipAllocToDevice = ipAllocToDevice;
	}
	
	public void setParameterServiceImpl(ParameterService parameterServiceImpl) {
		this.parameterServiceImpl = parameterServiceImpl;
	}

	public void setAutomationDao(AutomationDAO automationDao) {
		this.automationDao = automationDao;
	}
	public void setCmVmDao(ICmVmDAO cmVmDao) {
		this.cmVmDao = cmVmDao;
	}
	public void setBmSrRrVmRefDao(IBmSrRrVmRefDao bmSrRrVmRefDao) {
		this.bmSrRrVmRefDao = bmSrRrVmRefDao;
	}
	public void setBmSrAttrValDao(IBmSrAttrValDao bmSrAttrValDao) {
		this.bmSrAttrValDao = bmSrAttrValDao;
	}
	public void setCloudServiceDao(ICloudServiceDao cloudServiceDao) {
		this.cloudServiceDao = cloudServiceDao;
	}
	public void setScriptDao(IScriptDao scriptDao) {
		this.scriptDao = scriptDao;
	}
	public void setScriptParamDao(IScriptParamDao scriptParamDao) {
		this.scriptParamDao = scriptParamDao;
	}
	public void setBmSrRrinfoDao(IBmSrRrinfoDao bmSrRrinfoDao) {
		this.bmSrRrinfoDao = bmSrRrinfoDao;
	}
	public void setCmPasswordDao(ICmPasswordDAO cmPasswordDao) {
		this.cmPasswordDao = cmPasswordDao;
	}
	public void setPackageDefDao(IPackageDefDao packageDefDao) {
		this.packageDefDao = packageDefDao;
	}
	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}
	public void setCmDeviceDao(ICmDeviceDAO cmDeviceDao) {
		this.cmDeviceDao = cmDeviceDao;
	}
	public void setDeployunitDao(IDeployunitDao deployunitDao) {
		this.deployunitDao = deployunitDao;
	}
	public void setCmHostDao(ICmHostDAO cmHostDao) {
		this.cmHostDao = cmHostDao;
	}
	public void setRmCdpDao(IRmCdpDAO rmCdpDao) {
		this.rmCdpDao = rmCdpDao;
	}
	public void setRmClusterDao(IRmClusterDAO rmClusterDao) {
		this.rmClusterDao = rmClusterDao;
	}
	public void setCmHostDatastoreRefDao(
			ICmHostDatastoreRefDAO cmHostDatastoreRefDao) {
		this.cmHostDatastoreRefDao = cmHostDatastoreRefDao;
	}
	public void setRmVmManageServerDAO(IRmVmManageServerDAO rmVmManageServerDAO) {
		this.rmVmManageServerDAO = rmVmManageServerDAO;
	}
	public void setRmDatacenterDAO(IRmDatacenterDAO rmDatacenterDAO) {
		this.rmDatacenterDAO = rmDatacenterDAO;
	}

	/* (non-Javadoc)
	 * <p>Title:getManageServers</p>
	 * <p>Description:</p>
	 * @param vmId
	 * @return
	 * @throws BizException
	 * @see com.git.cloud.handler.service.AutomationService#getManageServers(java.lang.String)
	 */
	@Override
	public List<RmVmManageServerPo> getManageServers(String vmId) throws BizException {
		RmVmManageServerPo po = this.rmVmManageServerDAO.findRmVmManageServerByVmId(vmId);
		if(po==null)
			return null;
		else 
			return Arrays.asList(new RmVmManageServerPo[]{po});
	}
	
	@Override
	public String getSingleStringValueBySql(String sql) throws Exception {
		HashMap<String, String> result = (HashMap<String, String>) automationDao.findStringForSql(sql).get(0);
		if (result == null || result.values().size() < 1) {
			throw new Exception("没有查询结果。");
		}
		return result.values().iterator().next();
	}
	
	@Override
	public String getHandlerReturnString(HandlerReturnObject returnObjs) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 生成code
		map.put("checkCode", returnObjs.getReturnCode());
		StringBuffer returnStr = new StringBuffer();
		for (HandlerReturnObjectForOneDev oneObj : returnObjs.getDevObjects()) {
			StringBuffer oneObjStr = new StringBuffer();
			String devId = oneObj.getDeviceId();
			CmDevicePo dev = cmDeviceDao.findCmDeviceById(devId);
			String devName = dev.getDeviceName();
			String mesg = oneObj.getMesg();
			oneObjStr.append("主机名：").append(devName).append(", ");
			oneObjStr.append("输出：").append(Utils.BR).append(mesg);
			returnStr.append(oneObjStr.toString()).append(Utils.BR);
			returnStr.append("============================================").append(Utils.BR);
		}
		
		map.put("exeMsg", returnStr);
		String jsonDATA = JSON.toJSONString(map);
		logger.info("AutomationReturnString is: " + jsonDATA);
		return jsonDATA;
	}
	
	public RmGeneralServerVo findDeviceServerInfo(String deviceId) throws Exception {
		return automationDao.findDeviceServerInfo(deviceId);
	}
	
	public AppSysPO findAppInfoBySrId(String srId) throws Exception {
		return automationDao.findAppInfoBySrId(srId);
	}

	@Override
	public String getGYRXHostDatastore(String hostId) throws Exception {
		return cmHostDao.getGYRXHostDatastore(hostId);
	}
	
	public OpenstackVmParamVo findOpenstackVmParamByVmId(String vmId) throws Exception {
		return automationDao.findOpenstackVmParamByVmId(vmId);
	}
	
}
