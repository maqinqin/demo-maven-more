package com.git.cloud.resmgt.compute.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.appmgt.dao.IDeployunitDao;
import com.git.cloud.appmgt.model.po.DeployUnitPo;
import com.git.cloud.appmgt.model.vo.AppStatVo;
import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.appmgt.service.IAppMagService;
import com.git.cloud.appmgt.service.IDeployunitService;
import com.git.cloud.common.enums.OperationType;
import com.git.cloud.common.enums.ResourceType;
import com.git.cloud.common.enums.Source;
import com.git.cloud.common.enums.Type;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.log.model.po.NotificationPo;
import com.git.cloud.log.service.INotificationService;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.vo.IpRuleInfoVo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.compute.dao.IRmHostDao;
import com.git.cloud.resmgt.compute.handler.HostControllerServiceImpl;
import com.git.cloud.resmgt.compute.model.comparator.ScanVmResultVoComparator;
import com.git.cloud.resmgt.compute.model.po.DuPoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.CloudServiceVoByRmHost;
import com.git.cloud.resmgt.compute.model.vo.IpObj;
import com.git.cloud.resmgt.compute.model.vo.IpRules;
import com.git.cloud.resmgt.compute.model.vo.ScanVmResultVo;
import com.git.cloud.resmgt.compute.model.vo.VmVo;
import com.git.cloud.resmgt.compute.service.IRmHostService;
import com.git.cloud.resource.model.po.VmInfoPo;
import com.git.cloud.taglib.util.Internation;




//@Service("rmHostService")
public class RmHostServiceImpl implements IRmHostService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ICmDeviceService iCmDeviceService;
	private IRmHostDao rmHostDao;
	private IIpAllocToDeviceNewService ipAllocService;
	private HostControllerServiceImpl hostControllerServiceImpl;
	private INotificationService notiServiceImpl;
	private IDeployunitService deployunitServiceImpl;
	private IDeployunitDao deployunitDaoImpl;
	private ICmHostDAO cmHostDao;
	private IAppMagService appMagServiceImpl;
	
	public IDeployunitDao getDeployunitDaoImpl() {
		return deployunitDaoImpl;
	}

	public void setDeployunitDaoImpl(IDeployunitDao deployunitDaoImpl) {
		this.deployunitDaoImpl = deployunitDaoImpl;
	}

	public ICmHostDAO getCmHostDao() {
		return cmHostDao;
	}

	public void setCmHostDao(ICmHostDAO cmHostDao) {
		this.cmHostDao = cmHostDao;
	}

	public IDeployunitService getDeployunitServiceImpl() {
		return deployunitServiceImpl;
	}

	public void setDeployunitServiceImpl(IDeployunitService deployunitServiceImpl) {
		this.deployunitServiceImpl = deployunitServiceImpl;
	}

	public IAppMagService getAppMagServiceImpl() {
		return appMagServiceImpl;
	}

	public void setAppMagServiceImpl(IAppMagService appMagServiceImpl) {
		this.appMagServiceImpl = appMagServiceImpl;
	}
	
	public INotificationService getNotiServiceImpl() {
		return notiServiceImpl;
	}

	public void setNotiServiceImpl(INotificationService notiServiceImpl) {
		this.notiServiceImpl = notiServiceImpl;
	}

	public HostControllerServiceImpl getHostControllerServiceImpl() {
		return hostControllerServiceImpl;
	}

	public void setHostControllerServiceImpl(
			HostControllerServiceImpl hostControllerServiceImpl) {
		this.hostControllerServiceImpl = hostControllerServiceImpl;
	}

	@Override
	public List<ScanVmResultVo> scanVmList(VmVo vm) throws Exception {
		List<ScanVmResultVo> resultList = hostControllerServiceImpl.scanVmFromHost(vm);
		//获取IP规则
		List<IpRules> rules = this.getIpRules(vm);
		//获取云服务列表
		List<CloudServiceVoByRmHost> clouds = this.vmCloudServiceList(vm);
		for(ScanVmResultVo soj :resultList){
			if (this.checkVmIsExsit(soj.getVmName()))
				soj.setIsExist(1);
			else {
				soj.setIsExist(0);
				soj.setCloudServiceList(clouds);
				soj.setRules(rules);
				soj.setVmId(UUIDGenerator.getUUID());
			}
			String ips[] = soj.getIp().split(",");
			List<IpObj> ipList = new ArrayList<IpObj>();
			for (int i = 0; i < ips.length; i++) {
				IpObj ip = new IpObj();
				ip.setIp(ips[i]);
				ipList.add(ip);
			}
			soj.setIpList(ipList);
		}
		/*	对扫描结果进行排序	*/
		ScanVmResultVoComparator comparator = new ScanVmResultVoComparator();
		Collections.sort(resultList, comparator);
		return resultList;
		}

	@Override
	public List<CloudServiceVoByRmHost> vmCloudServiceList(VmVo vm) {
		return rmHostDao.getCloudServices(vm);
	}

	@Override
	public List<IpRules> getIpRules(VmVo vm) {
		if ("0".equals(vm.getCloudService()) || vm.getCloudService() == null || "1".equals(vm.getCloudService())) {
			vm.setCloudService("");
		}
		List<IpRules> list = rmHostDao.getIpRules(vm);
		return list;
	}
	
	/**
	 * 检查虚拟机是否存在
	 */
	@Override
	public boolean checkVmIsExsit(String vmName) {

		return rmHostDao.checkVmIsExist(vmName);
	}

	/**
	 * 获取服务器角色列表
	 */
	@Override
	public List<DuPoByRmHost> getDuList(VmVo vm) {

		List<DuPoByRmHost> duList = rmHostDao.getDuList(vm);
		List<DuPoByRmHost> duListNoSrvId = rmHostDao.getDuListNoServiceId();
		duList.addAll(duListNoSrvId);
		return duList;
	}

	/**
	 * 检查datastore是否存在
	 */
	@Override
	public boolean checkDataStore(String dataStoreName, String hostId) {
		return rmHostDao.checkDataStore(dataStoreName, hostId);
	}

	public ICmDeviceService getiCmDeviceService() {
		return iCmDeviceService;
	}

	@Autowired
	public void setiCmDeviceService(ICmDeviceService iCmDeviceService) {
		this.iCmDeviceService = iCmDeviceService;
	}


	public IRmHostDao getRmHostDao() {
		return rmHostDao;
	}

	@Autowired
	public void setRmHostDao(IRmHostDao rmHostDao) {
		this.rmHostDao = rmHostDao;
	}
	
	public IIpAllocToDeviceNewService getIpAllocService() {
		return ipAllocService;
	}
	@Autowired
	public void setIpAllocService(IIpAllocToDeviceNewService ipAllocService) {
		this.ipAllocService = ipAllocService;
	}

}
