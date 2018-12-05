package com.git.cloud.resmgt.common.service.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.git.cloud.common.enums.EnumResouseHeader;
import com.git.cloud.common.enums.RmHostType;
import com.git.cloud.common.enums.RmVirtualType;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.interceptor.ResolveObjectUtils;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.model.base.BaseBO;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.excel.model.vo.DataStoreVo;
import com.git.cloud.excel.model.vo.HostVo;
import com.git.cloud.foundation.util.PwdUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.policy.model.vo.AllocIpParamVo;
import com.git.cloud.policy.service.IComputePolicyService;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.policy.service.IRmVmParamService;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.resmgt.common.CloudClusterConstants;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmPasswordDAO;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.dao.IRmGeneralServerDAO;
import com.git.cloud.resmgt.common.dao.IRmResPoolDAO;
import com.git.cloud.resmgt.common.dao.IRmVmManageServerDAO;
import com.git.cloud.resmgt.common.dao.IRmVmTypeDAO;
import com.git.cloud.resmgt.common.dao.IRmVmwareLicenseDAO;
import com.git.cloud.resmgt.common.model.bo.CmClusterHostNetShowBo;
import com.git.cloud.resmgt.common.model.bo.CmClusterHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmDeviceHostShowBo;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.bo.CmIpShowBo;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmLocalDiskPo;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.git.cloud.resmgt.common.model.po.CmVmDatastorePo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.model.po.RmDeviceVolumesRefPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.model.po.RmVmManageServerPo;
import com.git.cloud.resmgt.common.model.vo.CmDatastoreVo;
import com.git.cloud.resmgt.common.model.vo.CmDeviceAndModelVo;
import com.git.cloud.resmgt.common.model.vo.CmDeviceVo;
import com.git.cloud.resmgt.common.model.vo.CmHostDatastoreRefVo;
import com.git.cloud.resmgt.common.model.vo.CmHostRefVo;
import com.git.cloud.resmgt.common.model.vo.CmHostVo;
import com.git.cloud.resmgt.common.model.vo.CmStorageVo;
import com.git.cloud.resmgt.common.model.vo.RmGeneralServerVo;
import com.git.cloud.resmgt.common.model.vo.RmResPoolVo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;
import com.git.cloud.resmgt.common.util.AutomationHostsManager;
import com.git.cloud.resmgt.compute.dao.IOpenstackSynckDao;
import com.git.cloud.resmgt.compute.dao.IRmCdpDAO;
import com.git.cloud.resmgt.compute.dao.impl.RmClusterDAO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;
import com.git.cloud.resmgt.network.dao.IProjectVpcDao;
import com.git.cloud.resmgt.network.model.po.OpenstackIpAddressPo;
import com.git.cloud.resmgt.network.model.vo.DeviceNetIP;
import com.git.cloud.resmgt.network.model.vo.NetIPInfo;
import com.git.cloud.resmgt.network.service.IVirtualNetworkService;
import com.git.cloud.resmgt.openstack.dao.IFloatingIpDao;
import com.git.cloud.resmgt.openstack.model.vo.FloatingIpVo;
import com.git.cloud.resmgt.openstack.model.vo.VolumeDetailVo;
import com.git.cloud.shiro.model.CertificatePo;
import com.git.support.common.MesgFlds;
import com.git.support.common.MesgRetCode;
import com.git.support.common.VMFlds;
import com.git.support.common.VMOpration;
import com.git.support.constants.SAConstants;
import com.git.support.invoker.common.impl.ResAdptInvokerFactory;
import com.git.support.invoker.common.inf.IResAdptInvoker;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * CopyRight(c) 2014 by GIT
 * 
 * @Title: CmDeviceServiceImpl.java
 * @Package com.git.cloud.resmgt.common.service.impl
 * @Description: 设备管理功能，
 * @author lizhizhong
 * @date 2014-12-18 上午10:06:37
 * @version V1.0
 */
public class CmDeviceServiceImpl implements ICmDeviceService {
	private static Logger logger = LoggerFactory.getLogger(CmDeviceServiceImpl.class);
	private ICmDeviceDAO icmDeviceDAO;

	private IIpAllocToDeviceNewService iIpAllocToDeviceService;

	private ResAdptInvokerFactory resInvokerFactory;
	private IRmVmTypeDAO rmVmTypeDAO;
	private IRmVmManageServerDAO rmVmMgServerDAO;
	private IRmCdpDAO rmCdpDAO;
	private IRmDatacenterDAO rmDCDAO;
	private ICmPasswordDAO cmPasswordDAO;
	private IRmVmwareLicenseDAO vmLicenseDAO;
	private ICmHostDAO cmHostDAO;
	private IRmResPoolDAO rmResPoolDAO;
	private IRmGeneralServerDAO rmGeneralServerDAO;
	private RmClusterDAO rmClusterDAO;
	private IBmSrRrinfoDao bmSrRrinfoDao;
	private IComputePolicyService computePolicyService;
	private IOpenstackSynckDao openstackSynckDao;
	@Autowired
	private IFloatingIpDao floatingIpDao;
	
	@Autowired
	private IRmVmParamService rmVmParamService ;
	
	@Autowired
	private IProjectVpcDao projectVpcDao;
	@Autowired
	private IVirtualNetworkService virtualNetworkService;
	
	public IComputePolicyService getComputePolicyService() {
		return computePolicyService;
	}

	public void setComputePolicyService(IComputePolicyService computePolicyService) {
		this.computePolicyService = computePolicyService;
	}

	public IBmSrRrinfoDao getBmSrRrinfoDao() {
		return bmSrRrinfoDao;
	}

	public void setBmSrRrinfoDao(IBmSrRrinfoDao bmSrRrinfoDao) {
		this.bmSrRrinfoDao = bmSrRrinfoDao;
	}

	public RmClusterDAO getRmClusterDAO() {
		return rmClusterDAO;
	}

	public void setRmClusterDAO(RmClusterDAO rmClusterDAO) {
		this.rmClusterDAO = rmClusterDAO;
	}

	public IRmResPoolDAO getRmResPoolDAO() {
		return rmResPoolDAO;
	}

	public void setRmResPoolDAO(IRmResPoolDAO rmResPoolDAO) {
		this.rmResPoolDAO = rmResPoolDAO;
	}


	public ICmDeviceDAO getIcmDeviceDAO() {
		return icmDeviceDAO;
	}

	public void setIcmDeviceDAO(ICmDeviceDAO icmDeviceDAO) {
		this.icmDeviceDAO = icmDeviceDAO;
	}

	public IOpenstackSynckDao getOpenstackSynckDao() {
		return openstackSynckDao;
	}

	public void setOpenstackSynckDao(IOpenstackSynckDao openstackSynckDao) {
		this.openstackSynckDao = openstackSynckDao;
	}

	public String deviceInPool(List<HashMap<String, String>> h_list,
			List<HashMap<String, String>> d_list,
			List<HashMap<String, String>> p_list,
			List<HashMap<String, String>> ds_list, String dcName,
			String poolName, String cdpName, String clusterName)
			throws RollbackableBizException {
		//更新cm_host表集群id、顺序号、入池时间
		icmDeviceDAO.updateDeviceOfBatch1(h_list);
		//更新cm_device表资源池id、设备名称
		icmDeviceDAO.updateDeviceOfBatch2(d_list);
		// 设置用户名和密码
		icmDeviceDAO.updateDeviceOfBatch3(p_list);
		// 批量的将指定携带的外挂存储设备关联到指定的物理机
		if (ds_list.size() > 0) {
			icmDeviceDAO.updateDeviceOfBatch4(ds_list);
		}
		return null;
	}
	
	public String deviceInPoolLog(ArrayList<HashMap<String, String>> h_list,
			ArrayList<HashMap<String, String>> d_list,
			ArrayList<HashMap<String, String>> p_list,
			ArrayList<HashMap<String, String>> ds_list, String dcName,
			String poolName, String cdpName, String clusterName){
		
		StringBuffer log = new StringBuffer();
		log.append("设备");
		for(HashMap<String,String> d:d_list){
			log.append("["+d.get("device_name")+"]");
		}
		log.append(",入数据中心["+dcName+"]");
		log.append(",资源池["+poolName+"]");
		log.append(",CDP["+cdpName+"]");
		log.append(",集群["+clusterName+"]");
		
		return log.toString();
	}
	
	
	

	@Override
	public <T extends BaseBO> List<T> getCmDeviceHostInfo(String bizId, String deviceName, String sn) {
		Map<String, String> map = Maps.newHashMap();
		map.put("bizId", bizId);
		map.put("deviceName", deviceName);
		map.put("sn", sn);

		List<CmClusterHostShowBo> list = null;

		try {

			// 根据输入条件，获取的物理机信息。
			list = icmDeviceDAO.getCmDeviceHostInfo("selectCmHostInfo", map);
			// 根据输入条件，获取物理机对应的网络信息。
			List<CmClusterHostNetShowBo> listNet = icmDeviceDAO.getCmDeviceHostNetInfo("selectCmHostNetInfo", map);

			// 各自循环主机信息列表和主机对应的网络信息列表，当主机id匹配的情况下，将网络信息列表的ip地址累串到主机信息列表的ip字段中。
			for (CmClusterHostShowBo cmClusterHostShowBo : list) {
				for (CmClusterHostNetShowBo cmClusterHostNetShowBo : listNet) {
					if (cmClusterHostShowBo.getId().equals(cmClusterHostNetShowBo.getId())
							&& !"".equals(cmClusterHostNetShowBo.getIp())) {
						cmClusterHostShowBo.getIp_str().append(cmClusterHostNetShowBo.getIp() + ",");
					}
				}

				if (StringUtils.isNotBlank(cmClusterHostShowBo.getIp_str().toString())) {
					cmClusterHostShowBo.getIp_str().deleteCharAt(cmClusterHostShowBo.getIp_str().length() - 1);
				}
			}

		} catch (RollbackableBizException e) {
			logger.error("获取物理机详细信息时时发生异常:" + e);
		} finally {}

		return (List<T>) list;
	}


	@Override
	public <T extends BaseBO> T getCmDeviceHostInfo(String bizId) {
		CmDeviceHostShowBo hostInfo = null;
		
		try {
			RmClusterPo rmClusterPo=new RmClusterPo();
			HostVo host = openstackSynckDao.selectHostByHostId(bizId);
			String clusterid =host.getClusterId();
			try {
				//根据集群 id 查找所属的平台类型
				rmClusterPo=openstackSynckDao.selectPlatByClusterId(clusterid);
			} catch (RollbackableBizException e1) {
				e1.printStackTrace();
			}
			if("O".equalsIgnoreCase(rmClusterPo.getPlatformType())){
				hostInfo = icmDeviceDAO.getCmDeviceHostInfo("selectCmDeviceHostOpenStackInfo", bizId);
			}else{
				hostInfo = icmDeviceDAO.getCmDeviceHostInfo("selectCmDeviceHostInfo", bizId);
				if(hostInfo!=null){
					if(hostInfo.getIpmiPwd()!=null && !"".equals(hostInfo.getIpmiPwd())){
						hostInfo.setIpmiPwd(PwdUtil.decryption(hostInfo.getIpmiPwd()));
					}
				}
			}
			if(!"O".equalsIgnoreCase(rmClusterPo.getPlatformType())) {
				List<CmIpShowBo> cmIpShowBoList = icmDeviceDAO.selectCmIpInfo(bizId);
				hostInfo.setIpList(cmIpShowBoList);
			}
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取物理机详细信息时发生异常:" + e);
			;
		}
		return (T) hostInfo;
	}

	@Override
	public <T extends BaseBO> T getCmDeviceVMInfo(String bizId) {
		CmDeviceVMShowBo vmInfo = new CmDeviceVMShowBo();
		try {
			vmInfo = icmDeviceDAO.getCmDeviceHostInfo("selectCmDeviceVMInfo", bizId);
			List<CmIpShowBo> cmIpShowBoList=null;
			if(vmInfo!=null){
			cmIpShowBoList = icmDeviceDAO.selectCmIpInfo(bizId);
			try {
				FloatingIpVo ipVo = floatingIpDao.findFloatingIpByDeviceId(bizId);
				if(ipVo != null){
					CmIpShowBo bo = new CmIpShowBo();
					bo.setRm_ip_type_name("弹性IP");
					bo.setIp(ipVo.getFloatingIp());
					cmIpShowBoList.add(bo);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("异常exception",e);
			}
			vmInfo.setIpList(cmIpShowBoList);
			}
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("获取虚拟机详细信息时发生异常:" + e);
		}
		return (T) vmInfo;
	}

	@Override
	public Pagination<CmDeviceVo> getDevicePagination(PaginationParam paginationParam) {

		return icmDeviceDAO.pageQuery("findCmDeviceTotal", "findCmDevicePage", paginationParam);
	}

	@Override
	public String deleteCmDeviceList(String[] ids) {
		String data = "";
		CmDeviceVo cmDeviceVO2 = null;
			for (String id : ids) {
				cmDeviceVO2 = new CmDeviceVo();
				cmDeviceVO2.setId(id);
				cmDeviceVO2.setIsActive(IsActiveEnum.NO.getValue());
				try {
					icmDeviceDAO.update("deleteCmDevice", cmDeviceVO2);
				} catch (RollbackableBizException e) {
					// TODO Auto-generated catch block
					logger.error("删除设备信息时:" + e);
				}
				data = "删除成功！";
			}
		return data;
	}
	@Override
	public String deleteCmDeviceListLog(String[] ids) throws RollbackableBizException{
		String logs = "";
		if(ids != null) {
			for(int i=0 ; i<ids.length ; i++) {
				CmDeviceVo cmDeviceVo=icmDeviceDAO.findObjectByID("selectCmDeviceForLog", ids[i]);
				logs += "、" + cmDeviceVo.getDeviceName();
			}
		}
		return logs.length() > 0 ? "【" + logs.substring(1) + "】" : "";
	}
	@Override
	public CmDatastoreVo selectCmDatastoreById(String id) throws RollbackableBizException {
		return icmDeviceDAO.findObjectByID("selectOneCmDatastore", id);
	}

	@Override
	public void updateCmDeviceHost(Map map) {

		CmDeviceVo cmDeviceVo = new CmDeviceVo();
		CmHostVo cmHostVo = new CmHostVo();
		CmLocalDiskPo cmLocalDiskPo = new CmLocalDiskPo();
		cmDeviceVo = (CmDeviceVo) map.get("cmDevicevo");
		cmHostVo = (CmHostVo) map.get("cmHostVo");
		cmLocalDiskPo = (CmLocalDiskPo) map.get("cmLocalDiskPo");
		try {
			icmDeviceDAO.update("updateCmDevice", cmDeviceVo);
			icmDeviceDAO.update("updateOneCmHost", cmHostVo);
			icmDeviceDAO.update("updateCmLocalDiskPo", cmLocalDiskPo);
		} catch (RollbackableBizException e) {
			logger.error("修改物理机信息时发生异常:" + e);
		}
	}
	@Override
	public String updateCmDeviceHostLog(HashMap map) throws RollbackableBizException{
		CmDeviceVo cmDeviceVo = new CmDeviceVo();
		CmHostVo cmHostVo = new CmHostVo();
		CmLocalDiskPo cmLocalDiskPo = new CmLocalDiskPo();
		cmDeviceVo = (CmDeviceVo) map.get("cmDevicevo");
		cmHostVo = (CmHostVo) map.get("cmHostVo");
		cmLocalDiskPo = (CmLocalDiskPo) map.get("cmLocalDiskPo");
		CmDeviceVo oldDevice=icmDeviceDAO.findObjectByID("selectCmDeviceForLog", cmDeviceVo.getId());
		CmHostVo oldCmHostVo = icmDeviceDAO.findObjectByID("selectOneCmHost",cmHostVo.getId());
		CmLocalDiskPo oldCmLocalDiskPo= icmDeviceDAO.findObjectByID("findCmLocalDiskPoById", cmLocalDiskPo.getId());
		String logContent="设备表信息："+ResolveObjectUtils.getObjectDifferentFieldString(oldDevice, cmDeviceVo, null);
		logContent+="物理机表信息："+ResolveObjectUtils.getObjectDifferentFieldString(oldCmHostVo, cmHostVo, null);
		logContent+="外挂磁盘信息："+ResolveObjectUtils.getObjectDifferentFieldString(oldCmLocalDiskPo, cmLocalDiskPo, null);
		return logContent;
	}
	@Override
	public void updateCmDeviceStorage(Map map) {
		CmDeviceVo cmDeviceVo = new CmDeviceVo();
		CmStorageVo cmStorageVo = new CmStorageVo();
		cmDeviceVo = (CmDeviceVo) map.get("cmDevicevo");
		cmStorageVo = (CmStorageVo) map.get("cmStorageVo");
		try {
			icmDeviceDAO.update("updateCmDevice", cmDeviceVo);
			icmDeviceDAO.update("updateCmStorage", cmStorageVo);
		} catch (RollbackableBizException e) {
			logger.error("修改存储设备信息 时发生异常:" + e);
		}

	}
	@Override
	public String updateCmDeviceStorageLog(HashMap map) throws RollbackableBizException{
		CmDeviceVo cmDeviceVo = new CmDeviceVo();
		CmStorageVo cmStorageVo = new CmStorageVo();
		cmDeviceVo = (CmDeviceVo) map.get("cmDevicevo");
		cmStorageVo = (CmStorageVo) map.get("cmStorageVo");
		CmDeviceVo oldDevice= icmDeviceDAO.findObjectByID("selectCmDeviceForLog", cmDeviceVo.getId());
		CmStorageVo oldCmStorageVo= icmDeviceDAO.findObjectByID("selectOneCmStorage", cmStorageVo.getId());
		String logContent="设备表信息："+ResolveObjectUtils.getObjectDifferentFieldString(oldDevice, cmDeviceVo, null);
		logContent+="存储表信息："+ResolveObjectUtils.getObjectDifferentFieldString(oldCmStorageVo, cmStorageVo, null);
		return logContent;
	}
	@Override
	public CmHostDatastoreRefVo selectCmHostDatastoreRefVo(String id) throws RollbackableBizException {

		return icmDeviceDAO.findObjectByID("selectCmHostDatastoreRefByHostId", id);
	}

	public IIpAllocToDeviceNewService getiIpAllocToDeviceService() {
		return iIpAllocToDeviceService;
	}

	public void setiIpAllocToDeviceService(IIpAllocToDeviceNewService iIpAllocToDeviceService) {
		this.iIpAllocToDeviceService = iIpAllocToDeviceService;
	}

	@Override
	public Pagination<CmDatastoreVo> getDatastorePagination(PaginationParam paginationParam) {
		return icmDeviceDAO.pageQuery("findCmDatastoreTotal", "findCmDatastorePage", paginationParam);
	}

	@Override
	public void deleteCmDatastoreVo(CmDatastoreVo cmDatastoreVo) throws RollbackableBizException {
		icmDeviceDAO.update("deleteCmDatastoreVo", cmDatastoreVo);

	}

	@Override
	public void deleteCmDatastoreVoById(String[] ids) throws RollbackableBizException {
		if (ids.length > 0) {
			for (String id : ids) {
				CmDatastoreVo cmDatastoreVo = new CmDatastoreVo();
				cmDatastoreVo = selectCmDatastoreById(id);
				icmDeviceDAO.update("deleteCmDatastoreVoById", cmDatastoreVo);
			}
		}
	}

	@Override
	public void updateCmDatastoreVo(CmDatastoreVo cmDatastoreVo) throws RollbackableBizException {
		icmDeviceDAO.update("updateCmDatastore", cmDatastoreVo);

	}

	@Override
	public void saveCmDeviceStorage(Map map) {
		CmDeviceVo cmDeviceVo = new CmDeviceVo();
		cmDeviceVo = (CmDeviceVo) map.get("cmDevicevo");
		CmStorageVo cmStorageVo = new CmStorageVo();
		cmStorageVo = (CmStorageVo) map.get("cmStorageVo");
		try {
			icmDeviceDAO.save("insertCmDevice", cmDeviceVo);
			icmDeviceDAO.save("insertCmStorge", cmStorageVo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("新增存储设备信息 时发生异常:" + e);
		}

	}
	@Override
	public String saveCmDeviceStorageLog(HashMap map) {
		CmDeviceVo cmDeviceVo = new CmDeviceVo();
		cmDeviceVo = (CmDeviceVo) map.get("cmDevicevo");
		CmStorageVo cmStorageVo = new CmStorageVo();
		cmStorageVo = (CmStorageVo) map.get("cmStorageVo");
		String logContent ="设备表信息："+ ResolveObjectUtils.getObjectFieldString(cmDeviceVo, null);
		logContent += "存储表信息："+ResolveObjectUtils.getObjectFieldString(cmStorageVo, null);
//		// 修改
//		cmDeviceVo.getId()
//		ResolveObjectUtils.getObjectDifferentFieldString(oldDevice, cmDeviceVo, null);
		return logContent;
	}

	@Override
	public void saveCmHost(Map map) {
		CmDeviceVo cmDeviceVo = new CmDeviceVo();
		cmDeviceVo = (CmDeviceVo) map.get("cmDevicevo");
		CmHostVo cmHostVo = new CmHostVo();
		cmHostVo = (CmHostVo) map.get("cmHostVo");
		CmLocalDiskPo cmLocalDiskPo = new CmLocalDiskPo();
		cmLocalDiskPo = (CmLocalDiskPo) map.get("cmLocalDiskPo");
		try {
			icmDeviceDAO.save("insertCmDevice", cmDeviceVo);
			icmDeviceDAO.save("insertCmHost", cmHostVo);
			icmDeviceDAO.save("insertCmLocalDiskPo", cmLocalDiskPo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("新增物理机信息 时发生异常:" + e);
		}

	}
	@Override
	public String saveCmHostLog(HashMap map) {
		CmDeviceVo cmDeviceVo = new CmDeviceVo();
		cmDeviceVo = (CmDeviceVo) map.get("cmDevicevo");
		CmHostVo cmHostVo = new CmHostVo();
		cmHostVo = (CmHostVo) map.get("cmHostVo");
		String logContent ="设备表信息："+ ResolveObjectUtils.getObjectFieldString(cmDeviceVo, null);
		logContent += "物理机表信息："+ResolveObjectUtils.getObjectFieldString(cmHostVo, null);
//		// 修改
//		cmDeviceVo.getId()
//		ResolveObjectUtils.getObjectDifferentFieldString(oldDevice, cmDeviceVo, null);
		return logContent;
	}
	@Override
	public List selectCmDeviceHost(String id) {
		List<Object> list = new ArrayList<Object>();
		CmDeviceAndModelVo cmDeviceAndModelVo = new CmDeviceAndModelVo();
		CmHostVo cmHostVo = new CmHostVo();
		CmLocalDiskPo cmLocalDiskPo = new CmLocalDiskPo();
		Integer i;
		Map<String, Object> map = Maps.newHashMap();
		try {
			cmDeviceAndModelVo = icmDeviceDAO.findObjectByID("selectOneCmDevice", id);
			cmHostVo = icmDeviceDAO.findObjectByID("selectOneCmHost", id);
			if(cmHostVo.getIpmiPwd()!=null){
			cmHostVo.setIpmiPwd(PwdUtil.decryption(cmHostVo.getIpmiPwd()));
			}
			i = icmDeviceDAO.findCmVmCount(id);
			map.put("vmCount", i);
			cmLocalDiskPo = icmDeviceDAO.findObjectByID("findCmLocalDiskPoById", id);
			list.add(cmDeviceAndModelVo);
			list.add(cmHostVo);
			list.add(map);
			list.add(cmLocalDiskPo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("查询物理机信息时发生异常:" + e);
		}
		return list;
	}
	
	@Override
	public CmHostDatastorePo selectCmHostDeafultDatastore(String id){
		CmHostDatastorePo po = new CmHostDatastorePo();
		try {
			po = icmDeviceDAO.findObjectByID("findCmDatastoreNameByHostId", id);
			
		} catch (RollbackableBizException e) {
			logger.error("查询物理机默认Datastore时发生异常:" + e);
		}
		return po;
	}
	@Override
	public CmVmDatastorePo selectCmVmDatastore(String id){
		CmVmDatastorePo po = new CmVmDatastorePo();
		try {
			po = icmDeviceDAO.findObjectByID("findCmDatastoreNameByVmId", id);
			
		} catch (RollbackableBizException e) {
			logger.error("查询虚拟机Datastore时发生异常:" + e);
		}
		return po;
	}

	public ResAdptInvokerFactory getResInvokerFactory() {
		return resInvokerFactory;
	}

	public void setResInvokerFactory(ResAdptInvokerFactory resInvokerFactory) {
		this.resInvokerFactory = resInvokerFactory;
	}

	public IRmGeneralServerDAO getRmGeneralServerDAO() {
		return rmGeneralServerDAO;
	}

	public void setRmGeneralServerDAO(IRmGeneralServerDAO rmGeneralServerDAO) {
		this.rmGeneralServerDAO = rmGeneralServerDAO;
	}

	/**
	 * @param String dcId
	 * @param vcHostIP,VCenter URL
	 * @param vcHostUserName，VCenter 用户名
	 * @param vcHostPwd，VCenter 密码
	 * @param hostMgrIp，物理机IP
	 * @param name，Nas存储别名
	 * @param path，Nas存储路径
	 * @param mgrIp，Nas存储IP  
	 * @param dcId，数据中心id
	 * @return
	 */
	public String createNasInterface(String vcHostIP, String vcHostUserName,
			String vcHostPwd, String hostMgrIp, String name, String path,
			String mgrIp, String dcId) {

		String result = "";
		// 日志，调试需要
		String loginfo = "vc用户名:" + vcHostUserName + ";" + "vc密码:" + vcHostPwd;
		logger.debug(loginfo);

		try {
			HeaderDO header = HeaderDO.CreateHeaderDO();
			header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
			header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
			header.setOperation(VMOpration.ADD_NAS);

			RmDatacenterPo dcPo = new RmDatacenterPo();
			dcPo = rmDCDAO.getDataCenterById(dcId);
			header.set("DATACENTER_QUEUE_IDEN", dcPo.getQueueIden());

			BodyDO body = BodyDO.CreateBodyDO();
			List<Map<String, String>> dsList = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put(VMFlds.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS
					+ vcHostIP + CloudClusterConstants.VCENTER_URL_SDK);
			map.put(VMFlds.VCENTER_USERNAME, vcHostUserName);
			map.put(VMFlds.VCENTER_PASSWORD, vcHostPwd);
			map.put(VMFlds.HOST_NAME, hostMgrIp);
			map.put(VMFlds.NAS_HOST_REMOTE_IP, mgrIp);
			map.put(VMFlds.NAS_REMOTE_PATH, path);
			map.put(VMFlds.NAS_LOCAL_PATH, name);
			dsList.add(map);
			body.setList(VMFlds.NAS_ADD_RECS, dsList);

			IDataObject reqData = DataObject.CreateDataObject();
			reqData.setDataObject(MesgFlds.HEADER, header);
			reqData.setDataObject(MesgFlds.BODY, body);

			IDataObject rspData = null;
			IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO rspHeader = rspData.getDataObject(MesgFlds.HEADER,
						HeaderDO.class);
				if (MesgRetCode.SUCCESS.equals(rspHeader.getRetCode())) {
					result = "success";
				} else {
					result = rspHeader.getRetMesg();
				}
			}

		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;

	}
	/**
	 * @Title: createHostInterface
	 * @Description: TODO
	 * @field: @param clusterId
	 * @field: @param vcHostUserName
	 * @field: @param vcHostPwd
	 * @field: @param vcHostIP
	 * @field: @param clusterEname
	 * @field: @param hostId
	 * @field: @param hostpasswd
	 * @field: @param license
	 * @field: @param hostname
	 * @field: @param hostMgrIp
	 * @field: @param dcId
	 * @field: @return
	 * @field: @throws RollbackableBizException
	 * @return String
	 * @throws
	 */
	public String createHostInterface(String vcHostUserName, String vcHostPwd, String vcHostIP, String clusterEname, String hostId, String hostpasswd, String license, String hostname, String hostMgrIp, String dcId) {
		String result = new String();
		IDataObject reqData = DataObject.CreateDataObject();
		// 日志，调试需要
		String loginfo = "manage device:" + hostname + ";" + "vc用户名:" + vcHostUserName + ";" + "vc密码:" + vcHostPwd;
		logger.debug(loginfo);
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.CREATE_HOST);
		RmDatacenterPo dcPo = null;
		try {
			dcPo = rmDCDAO.getDataCenterById(dcId);
		} catch (RollbackableBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");
		String queueIden = dcPo!=null?dcPo.getQueueIden():"";
		//首先更新automation的hosts文件
		/*try {
			result = UpdataAutomationHost(hostMgrIp,hostname,"add",dcId,queueIden,invoker);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		/*if(!result.equals(MesgRetCode.SUCCESS))
			return result;*/
		header.set("DATACENTER_QUEUE_IDEN", queueIden);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(VMFlds.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS + vcHostIP
				+ CloudClusterConstants.VCENTER_URL_SDK);

		body.setString(VMFlds.VCENTER_USERNAME, vcHostUserName);
		body.setString(VMFlds.VCENTER_PASSWORD, vcHostPwd);
		body.setString(VMFlds.CLUSTER_NAME, clusterEname);
		/*
		 * body.setString(VMFlds.HOST_USERNAME, (String)hostUserObj[0]); body.setString(VMFlds.HOST_PASSWORD,
		 * PwdUtil.decryption((String)hostUserObj[1]));
		 */
		body.setString(VMFlds.HOST_USERNAME, CloudClusterConstants.ESXI_ROOT_USERNAME);
		body.setString(VMFlds.HOST_PASSWORD, hostpasswd);
		body.setString(VMFlds.HOST_MANAGEMENT_IP, hostMgrIp);
		body.setString(VMFlds.VMK_ID, CloudClusterConstants.VMK0);// 增加管理流量参数
		// body.setString(VMFlds.HOST_MANAGEMENT_IP, "128.192.161.50");
		body.setBoolean(VMFlds.HOST_FORCE, true);
		// body.setString(VMFlds.HOST_NAME, hostname);
		body.setString(VMFlds.HOST_NAME, hostMgrIp);
		// body.setString(VMFlds.HOST_NAME, "128.192.161.50");
		body.setString(VMFlds.LICENSE_KEY, license);


		IDataObject rspData = null;
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		try {
			rspData = invoker.invoke(reqData, 1200000);
			if (rspData == null) {
				result = "请求响应失败!";
			} else {
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if (MesgRetCode.SUCCESS.equals(header1.getRetCode())) {
					result = "success";
				} else {
					result = header1.getRetMesg();
				}
			}

		} catch (Exception e) {
			result = e.getMessage();
			logger.error("异常exception",e);
		}
		return result;
	}

	public IRmVmTypeDAO getRmVmTypeDAO() {
		return rmVmTypeDAO;
	}

	public void setRmVmTypeDAO(IRmVmTypeDAO rmVmTypeDAO) {
		this.rmVmTypeDAO = rmVmTypeDAO;
	}

	public IRmVmManageServerDAO getRmVmMgServerDAO() {
		return rmVmMgServerDAO;
	}

	public void setRmVmMgServerDAO(IRmVmManageServerDAO rmVmMgServerDAO) {
		this.rmVmMgServerDAO = rmVmMgServerDAO;
	}

	public IRmCdpDAO getRmCdpDAO() {
		return rmCdpDAO;
	}

	public void setRmCdpDAO(IRmCdpDAO rmCdpDAO) {
		this.rmCdpDAO = rmCdpDAO;
	}

	public IRmDatacenterDAO getRmDCDAO() {
		return rmDCDAO;
	}

	public void setRmDCDAO(IRmDatacenterDAO rmDCDAO) {
		this.rmDCDAO = rmDCDAO;
	}

	public IRmVmwareLicenseDAO getVmLicenseDAO() {
		return vmLicenseDAO;
	}

	public void setVmLicenseDAO(IRmVmwareLicenseDAO vmLicenseDAO) {
		this.vmLicenseDAO = vmLicenseDAO;
	}

	public ICmHostDAO getCmHostDAO() {
		return cmHostDAO;
	}

	public void setCmHostDAO(ICmHostDAO cmHostDAO) {
		this.cmHostDAO = cmHostDAO;
	}

	public ICmPasswordDAO getCmPasswordDAO() {
		return cmPasswordDAO;
	}

	public void setCmPasswordDAO(ICmPasswordDAO cmPasswordDAO) {
		this.cmPasswordDAO = cmPasswordDAO;
	}

	@Override
	public List selectCmStorage(String id) {
		List<Object> list = new ArrayList<Object>();
		CmDeviceAndModelVo cmDeviceAndModelVo = new CmDeviceAndModelVo();
		CmStorageVo cmStorageVo = new CmStorageVo();
		try {
			cmDeviceAndModelVo = icmDeviceDAO.findObjectByID("selectOneCmDevice", id);
			cmStorageVo = icmDeviceDAO.findObjectByID("selectOneCmStorage", id);
			list.add(cmDeviceAndModelVo);
			list.add(cmStorageVo);
		} catch (RollbackableBizException e) {
			// TODO Auto-generated catch block
			logger.error("查询存储设备信息 时发生异常:" + e);
		}
		return list;
	}

	@Override
	public CmDeviceAndModelVo selectSNForTrim(String sn) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return icmDeviceDAO.findObjectByID("selectSNfortrim", sn);
	}

	@Override
	public CmDeviceAndModelVo selectDeviceNameForTrim(String deviceName) throws RollbackableBizException {
		return icmDeviceDAO.findObjectByID("selectDeviceNamefortrim", deviceName);
	}
	
	@Override
	public CmDeviceAndModelVo selectSeatIdForTrim(String seatId) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return icmDeviceDAO.findObjectByID("selectSeatIdForTrim", seatId);
	}

	public String destoryHostInterface(String managerServer,List<String> deviceNameList,Map<String,String> devMap) throws Exception{
		String result;
		//获取cdp所在资源池ids
		RmVmManageServerPo vmManagerServerPo = null;
		List<RmVmManageServerPo> list= rmVmMgServerDAO.findByID("findRmVmManagerServerPo", managerServer);
		if(list.isEmpty()||list.size()==0){
			throw new RuntimeException("get vm manager info error!");
		}else{
			vmManagerServerPo = list.get(0);
		}
		String userName = vmManagerServerPo.getUserName();
		String password = "";
		try {
			CmPasswordPo pwpo = cmPasswordDAO.findCmPasswordByResourceUser(managerServer, userName);
			password = pwpo.getPassword();
			if(StringUtils.isBlank(password))
				throw new Exception("获取ManagerServer["+managerServer+"] password is null");
			password = PwdUtil.decryption(password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		RmDatacenterPo dcPo = rmDCDAO.getDataCenterById(vmManagerServerPo.getDatacenterId());
		String dcId = dcPo.getId();
		String dcQueue = dcPo.getQueueIden();
		IDataObject reqData = DataObject.CreateDataObject();
		
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.set("DATACENTER_QUEUE_IDEN", dcQueue);		
		header.setResourceClass(EnumResouseHeader.VM_RES_CLASS.getValue());
		header.setResourceType(EnumResouseHeader.VM_RES_TYPE.getValue());
		header.setOperation(VMOpration.DESTORY_HOST_RESOURCE);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		body.setString(VMFlds.VCENTER_URL, CloudClusterConstants.VCENTER_URL_HTTPS+vmManagerServerPo.getManageIp()+CloudClusterConstants.VCENTER_URL_SDK);
		body.setString(VMFlds.VCENTER_USERNAME, userName);
		body.setString(VMFlds.VCENTER_PASSWORD, password);
		
		body.setList(VMFlds.DESTORY_RESOURCE_NAME, deviceNameList);//回收deviceName名称
		body.setString(VMFlds.DESTORY_TYPE, "HOST");
		IResAdptInvoker invoker = resInvokerFactory.findInvoker("AMQ");

		
		IDataObject rspData = null;
		reqData.setDataObject(MesgFlds.HEADER, header);
		reqData.setDataObject(MesgFlds.BODY, body);
		try {
			rspData = invoker.invoke(reqData, 300000);
			if(rspData==null){
				result="N";
			}else{
				HeaderDO header1 = rspData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
				if(MesgRetCode.SUCCESS.equals(header1.getRetCode())){
					result="success";
				}else{
					result=header1.getRetMesg();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if(result.equals("success")){
			/*for(String key :devMap.keySet()){
				String hostIP = key;
				String hostName = devMap.get(key);
				UpdataAutomationHost(hostIP, hostName, "del", dcId, dcQueue,invoker);
			}*/
		}
		return result;
	}

	@Override
	public CertificatePo findCertificatePath() throws RollbackableBizException {
		// TODO Auto-generated method stub
		return icmDeviceDAO.findCertificatePath();
	}

	@Override
	public List<CmDeviceVo> getCmDevicePoNumber()
			throws RollbackableBizException {
		// TODO Auto-generated method stub
		return icmDeviceDAO.getCmDevicePoNumber();
	}

	@Override
	public String isDeleteCmDeviceList(String[] ids) {
		String data = "";
		CmHostVo cmHostVo = new CmHostVo();
		CmStorageVo cmStorageVo = new CmStorageVo();
		CmHostDatastoreRefVo cmHostDatastoreRefVo = new CmHostDatastoreRefVo();
		int vmCount;
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				try {
					CmDeviceAndModelVo cmDeviceAndModelVo = new CmDeviceAndModelVo();
					cmHostDatastoreRefVo = (CmHostDatastoreRefVo) icmDeviceDAO.findObjectByID(
							"selectCmHostDatastoreRefByHostId", id);
					cmHostVo = (CmHostVo) icmDeviceDAO.findObjectByID("selectOneCmHostByClusterId", id);
					cmStorageVo = icmDeviceDAO.findObjectByID("selectOneCmStorage", id);
					vmCount = icmDeviceDAO.findCmVmCount(id);
					cmDeviceAndModelVo = icmDeviceDAO.findObjectByID("selectOneCmDevice", id);
					if (cmHostVo != null && cmHostVo.getClusterId() != null) {
						data = "删除失败，您选择的设备中名称为" + cmDeviceAndModelVo.getDeviceName() + "的设备已经关联集群，无法删除！";
						break;
						// 根据设备id查出对应的主机信息，判断是否有所属资源次
					} else if (cmHostDatastoreRefVo != null) {// 用设备id去查主机与datastore关联表，判断主物理机下是否挂datastore
						data = "删除失败，您选择的设备中名称为" + cmDeviceAndModelVo.getDeviceName() + "的设备挂载了datastore的设备，无法删除！";
						break;

					} else if (vmCount > 0) {// 用设备id去查虚拟机表，判断物理机下是否挂载虚拟机
						data = "删除失败，您选择的设备中名称为" + cmDeviceAndModelVo.getDeviceName() + "的设备存在虚拟机，无法删除！";
						break;
					} else if (cmStorageVo != null && cmStorageVo.getStorageChildPoolId() != null && !cmStorageVo.getStorageChildPoolId().equals("")) {
						data = "删除失败，您选择的设备中名称为" + cmDeviceAndModelVo.getDeviceName() + "的存储设备已有所属资源子池，无法删除！";
						break;

					} else {
						data = "";
					}
				} catch (RollbackableBizException e) {
					logger.error("查询删除条件:" + e);
				}
			}
		}
		return data;
		}

	public String UpdataAutomationHost(String hostIP,String hostName,String optType,String dcId,String dcQueue,IResAdptInvoker invoker) throws Exception{

		//获取automation服务器信息
		List<RmGeneralServerVo> serverList = rmGeneralServerDAO.findRmGeneralServerBydcId(dcId);
		if(serverList==null||serverList.isEmpty()){
			throw new Exception("获取Automation服务器信息失败！");
		}
		RmGeneralServerVo serverVo = serverList.get(0);
		String autoServerIp = serverVo.getServerIp();
		String autoUser = serverVo.getUserName();
		String autoUserPwd = PwdUtil.decryption(serverVo.getPassword());
		Map<String,Object> contextParams = Maps.newHashMap();
		contextParams.put("DC_QUEUE", dcQueue);
		contextParams.put(SAConstants.SERVER_IP, autoServerIp);
		//user:icmsauto
		contextParams.put(SAConstants.USER_NAME, autoUser);
		contextParams.put(SAConstants.USER_PASSWORD, autoUserPwd);
		//optType : add/del
		//TODO abandon
		String cmd = SAConstants.AUTOMATION_HOSTS_SHELL+"  "+optType+" "+hostIP+" "+hostName;
		
		logger.info(cmd);
		contextParams.put(SAConstants.CMD_LIST, Arrays.asList(new String[]{cmd}));
		String result = AutomationHostsManager.updateHosts(invoker, contextParams);
		return result;
	}

	@Override
	public boolean selectCmClusterHostInfos(String id)
			throws RollbackableBizException {
		boolean flag = false;
		List<CmClusterHostShowBo> list = icmDeviceDAO.selectCmClusterHostInfos(id);
		if(list.size()>0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	

	@Override
	public void updatemDeviceHostIsInvc(Map map) {
		CmHostVo cmHostVo = new CmHostVo();
		cmHostVo = (CmHostVo) map.get("cmHostVo");
		try {
			icmDeviceDAO.update("updateIsInvcCmHost", cmHostVo);
		} catch (RollbackableBizException e) {
			logger.error("修改物理机信息时发生异常:" + e);
		}
	}

	@Override
	public List<Object> isGetRelevanceInfoList(String relevanceInfo) {
		if(relevanceInfo == null){
			return null ;
		}
		List<Object> relevanceInfoList = JSONArray.toList(JSONArray.fromObject(relevanceInfo), HashMap.class) ;
		return relevanceInfoList ;
	}

	@Override
	public String selectBpmModelId(String modelName) throws RollbackableBizException {
		List<String> aa =icmDeviceDAO.selectBpmModelId(modelName) ;
		return aa.get(0);
	}
	
	/**
	 * 初始化admin_param中的modelname
	 */

	@Override
	public String selectModelName() throws RollbackableBizException {
		String aa = icmDeviceDAO.selectModelName().get(0);
		return aa;
	}


	@Override
	public RmVirtualTypePo findVirtualTypeById(String clusterId) throws RollbackableBizException {
		RmClusterPo clusterPo = rmClusterDAO.findRmClusterPoById(clusterId);
		String vmTypeId = clusterPo.getVmType();
		HashMap<String, String> params = Maps.newHashMap();
		params.put("vmTypeId", vmTypeId);
		RmVirtualTypePo rmVmTypePo = rmVmTypeDAO.findRmVirtualTypeInfo("findRmVirtualTypeInfo", params);
		return rmVmTypePo;
	}


	@Override
	public CmDevicePo selectCmDevicePoById(String id)
			throws RollbackableBizException {
		CmDevicePo cmDevicePo = new CmDevicePo();
		boolean isCmHost = this.isCmHost(id);
		boolean isCmVm = this.isCmVm(id);
		if(isCmHost){
			//记录状态，PHYSICAL("H"):物理机
			cmDevicePo.setHostType(RmHostType.PHYSICAL.getValue());
			CmDeviceHostShowBo cmDeviceHostShowBo = this.getCmDeviceHostInfo(id);
			RmVirtualTypePo vmType = this.findVirtualTypeById(cmDeviceHostShowBo.getCdpId());
			cmDevicePo.setVirtualType(vmType.getVirtualTypeCode());
		}
		if(isCmVm){
			//记录状态，VIRTUAL("V"):虚拟机
			cmDevicePo.setHostType(RmHostType.VIRTUAL.getValue());
			CmDeviceVMShowBo cmDeviceVMShowBo = this.getCmDeviceVMInfo(id);
			RmVirtualTypePo vmType = this.findVirtualTypeById(cmDeviceVMShowBo.getClusterId());
			cmDevicePo.setVirtualType(vmType.getVirtualTypeCode());
		}
		return cmDevicePo;
	}

	@Override
	public boolean isCmHost(String id) throws RollbackableBizException {
		Map<String, String> map = Maps.newHashMap();
		map.put("id", id);
		CmDevicePo P = icmDeviceDAO.isCmHost(map);
		if(P!=null){
			return true;
		}else{
			return false;	
		}
	}

	@Override
	public boolean isCmVm(String id) throws RollbackableBizException {
		Map<String, String> map = Maps.newHashMap();
		map.put("id", id);
		CmDevicePo P = icmDeviceDAO.isCmVm(map);
		if(P!=null){
			return true;
		}else{
			return false;	
		}
	}

	@Override
	public void updateCmdeviceRunningState(CmDevicePo cmDevicePo)
			throws RollbackableBizException {
		icmDeviceDAO.updateCmdeviceRunningState(cmDevicePo);
	}

	@Override
	public void deleteDatastoreInfo(String hostId, String datastoreId)
			throws RollbackableBizException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hostId", hostId);
		map.put("datastoreId", datastoreId);
		icmDeviceDAO.deleteDatastoreInfo(map);
	}

	@Override
	public void saveDefaultDatastore(CmDevicePo cmDevicePo)
			throws RollbackableBizException {
		icmDeviceDAO.saveDefaultDatastore(cmDevicePo);
		icmDeviceDAO.update("updateCmHostDatastoreType", cmDevicePo);
	}

	@Override
	public CmDevicePo getDefaultDatastore(String hostId)
			throws RollbackableBizException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("hostId", hostId);
		return icmDeviceDAO.getDefaultDatastore(map);
	}

	@Override
	public List<CmDevicePo> findVMByHostId(String hostId) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("hostId", hostId);
		return icmDeviceDAO.findListByFieldsAndOrder("findVMByHostId",map);
	}
	
	public CmVmPo findPowerInfoByVmId(String vmId) throws RollbackableBizException{
		return icmDeviceDAO.findPowerInfoByVmId(vmId);
	}

	@Override
	public String findVmIdByName(String deviceName)
			throws RollbackableBizException {
		return icmDeviceDAO.findVmIdByName(deviceName);
	}
	
	@Override
	public String findHostIpById(String id) throws RollbackableBizException {
		
		return icmDeviceDAO.findHostIpById(id);
	}

	@Override
	public String getPmRunningState(String hostId)throws RollbackableBizException {
		return icmDeviceDAO.getPmRunningState(hostId);
	}

	@Override
	public CmDevicePo findDeviceById(String id) throws RollbackableBizException {
		return icmDeviceDAO.findCmDeviceById(id);
	}

	@Override
	public List<CmDevicePo> findDeviceDefaultDatastore(String datastoreId)throws RollbackableBizException {
		return icmDeviceDAO.findDeviceDefaultDatastore(datastoreId);
	}
	public void updateCmDeviceLparId(CmDevicePo device) throws RollbackableBizException {
		icmDeviceDAO.update("updateCmDeviceLparId", device);
	}
	
	public void updateCmDeviceLparName(CmDevicePo device) throws RollbackableBizException {
		icmDeviceDAO.update("updateCmDeviceLparName", device);
	}
	
	public void updateCmDeviceProfileName(CmDevicePo device) throws RollbackableBizException {
		icmDeviceDAO.update("updateCmDeviceProfileName", device);
	}
	/**
	 * 
	 * 查询最新创建的虚拟机信息，只有一条
	 */
	@Override
	public CmDevicePo findNewDevcieByHostId()throws RollbackableBizException {
		return icmDeviceDAO.findNewDevcieByHostId();
	}

	@Override
	public CmHostPo findDistribHost(String ipInfo)throws RollbackableBizException {
		 return icmDeviceDAO.findDistribHost(ipInfo);
	}
	
	@Override
	public String findVmTypeCodeByVmId(String vmId) throws RollbackableBizException {
		HashMap<String, String> params = Maps.newHashMap();
		params.put("vmId", vmId);
		RmVirtualTypePo rmVmTypePo = rmVmTypeDAO.findRmVirtualTypeInfo("findRmVirtualTypeByVmId", params);
		String vmTypeCode = "";
		if(rmVmTypePo != null) {
			vmTypeCode = rmVmTypePo.getVirtualTypeCode();
		}
		return vmTypeCode;
	}
	
	@Override
	public List<String> findVolumeTypeList(String availableZoneId) throws RollbackableBizException {
		List<String> Volume = icmDeviceDAO.findVolumeTypeList(availableZoneId);
		return Volume;
	}

	@Override
	public Pagination<CmHostPo> getHostConfigure(PaginationParam paginationParam) throws RollbackableBizException {
		 return icmDeviceDAO.getHostConfigure(paginationParam);
	}
	
	@Override
	public Pagination<RmDeviceVolumesRefPo> getRmDeviceVolumesRefPoList(PaginationParam paginationParam)
			throws RollbackableBizException {
		return icmDeviceDAO.getRmDeviceVolumesRefPoList(paginationParam);
	}

	@Override
	public void saveRmDeviceVolumesRefPo(RmDeviceVolumesRefPo rmDeviceVolumesRefPo) throws RollbackableBizException {
		icmDeviceDAO.saveRmDeviceVolumesRefPo(rmDeviceVolumesRefPo);
	}

	@Override
	public void deleteRmDeviceVolumesRef(RmDeviceVolumesRefPo ref) throws RollbackableBizException {
		icmDeviceDAO.deleteRmDeviceVolumesRef(ref);
	}

	@Override
	public RmDeviceVolumesRefPo getRmDeviceVolumesRefByMap(HashMap<String, String> map)
			throws RollbackableBizException {
		return icmDeviceDAO.getRmDeviceVolumesRefByMap(map);
	}

	@Override
	public void updateRmDvRefVolumeId(RmDeviceVolumesRefPo ref) throws RollbackableBizException {
		icmDeviceDAO.updateRmDvRefVolumeId(ref);
	}
	
	@Override
	public void saveOpenstackVolume(VolumeDetailVo volumeVo) {
		// TODO Auto-generated method stub
		icmDeviceDAO.saveOpenstackVolume(volumeVo);
	}

	@Override
	public void updateVmProjectId(String vmId, String projectId) throws RollbackableBizException {
		icmDeviceDAO.updateVmProjectId(vmId, projectId);		
	}

	@Override
	public <T extends BaseBO> T getCmDeviceVMInfoOpenstack(String bizId) throws Exception {
		CmDeviceVMShowBo vmInfo = icmDeviceDAO.getCmDeviceHostInfo("selectCmDeviceVMInfo", bizId);
		List<CmIpShowBo> cmIpShowBoList = new ArrayList<>();
		 try {
			List<OpenstackIpAddressPo> list = virtualNetworkService.selectIpAddressByDeviceId(bizId);
			if(list.size() > 0) {
				for(OpenstackIpAddressPo li : list) {
					CmIpShowBo bo = new CmIpShowBo();
					bo.setRm_ip_type_name("管理IP");
					bo.setIp(li.getIp());
					cmIpShowBoList.add(bo);
				}
			}
			vmInfo.setIpList(cmIpShowBoList);
		 } catch (Exception e) {
			throw new RollbackableBizException("查询opnestack虚拟机ip失败" + e);
		}
		return (T) vmInfo;
	}

}
