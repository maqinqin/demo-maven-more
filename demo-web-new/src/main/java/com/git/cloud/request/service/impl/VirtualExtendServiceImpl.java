package com.git.cloud.request.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.common.exception.BizException;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.policy.model.vo.PolicyResultVo;
import com.git.cloud.policy.model.vo.RequsetResInfoVo;
import com.git.cloud.policy.service.IComputePolicyService;
import com.git.cloud.request.dao.IBmSrAttrValDao;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.dao.IVirtualExtendDAO;
import com.git.cloud.request.model.SrStatusCodeEnum;
import com.git.cloud.request.model.po.BmSrRrVmRefPo;
import com.git.cloud.request.model.po.BmSrRrinfoPo;
import com.git.cloud.request.model.vo.BmSrAttrValVo;
import com.git.cloud.request.model.vo.BmSrRrinfoVo;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.model.vo.VirtualExtendVo;
import com.git.cloud.request.service.IVirtualExtendService;
import com.git.cloud.resmgt.common.dao.ICmDeviceDAO;
import com.git.cloud.resmgt.common.dao.ICmHostDAO;
import com.git.cloud.resmgt.common.dao.ICmVmDAO;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.model.po.CmHostPo;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.model.vo.DeviceInfoVo;
import com.git.cloud.resmgt.compute.dao.IRmClusterDAO;
import com.git.cloud.resmgt.compute.model.po.RmClusterPo;

/**
 * 云服务扩容申请接口类
 * @ClassName:VirtualExtendServiceImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class VirtualExtendServiceImpl implements IVirtualExtendService {
	
	private static Logger logger = LoggerFactory.getLogger(VirtualExtendServiceImpl.class);
	
	private IComputePolicyService computePolicyService;
	private IBmSrDao bmSrDao;
	private IBmSrRrinfoDao bmSrRrinfoDao;
	private IBmSrRrVmRefDao bmSrRrVmRefDao;
	private IBmSrAttrValDao bmSrAttrValDao;
	private IVirtualExtendDAO virtualExtendDAO;
	// 外包服务
	private IRmClusterDAO rmClusterDao;
	private ICmVmDAO cmVmDao;
	private ICmHostDAO cmHostDao;
	private ICmDeviceDAO cmDeviceDao;
	
	/**
	 * 获取云服务扩容信息
	 * @param srId
	 * @return
	 */
	public VirtualExtendVo findVirtualExtendById(String srId) throws RollbackableBizException{
		if(null==srId||srId.length()==0){
			logger.info("执行获取云服务申请时：云服务请求ID接收为空");
			return null;
		}
		
		VirtualExtendVo virtualExtendVo = new VirtualExtendVo();
		try{	
			BmSrVo bmSr = bmSrDao.findBmSrVoById(srId); // 服务请求主表信息
			List<BmSrRrinfoVo> rrinfoList = bmSrRrinfoDao.findBmSrRrinfoListBySrId(srId); //资源信息
	
			for (BmSrRrinfoVo bmSrRrinfoVo :rrinfoList) {
				List<BmSrAttrValVo> attrValList = bmSrAttrValDao.findBmSrAttrValListByRrinfoId(bmSrRrinfoVo.getRrinfoId()); //供给参数信息
				
				for (BmSrAttrValVo srAttrval: attrValList) {
					String attrType = srAttrval.getAttrType();
					if ("S".equals(attrType)) {
						List<CloudServiceAttrSelPo> attrSelList = virtualExtendDAO
						      .findByID("getBmSrAttrSel", srAttrval.getAttrId());//参数属性
						srAttrval.setAttrSelList(attrSelList);
					}
				}
				bmSrRrinfoVo.setAttrValList(attrValList);
			}
			String startTime = String.valueOf(bmSr.getSrStartTime());
			if(!"".equals(startTime)){
				bmSr.setStartTimeStr(startTime);
			}
		    String endTime = String.valueOf(bmSr.getSrEndTime());
	        if(!"".equals(endTime)){
	    	   bmSr.setEndTimeStr(endTime);
			}
			virtualExtendVo.setBmSr(bmSr);
			virtualExtendVo.setRrinfoList(rrinfoList);
		
		} catch (RollbackableBizException e) {
			logger.error("执行查找云服务申请时发生异常:"+e);
		}
		return virtualExtendVo;
	}	

	@Override
	public Pagination<BmSrRrinfoVo> queryVEBmSrRrinfoList(
			PaginationParam paginationParam) {
		return virtualExtendDAO.pageQuery("selectVEBmSrRrinfoListTotal", "selectVEBmSrRrinfoListPage", paginationParam);
	}
	
	@Override
	public Pagination<BmSrRrinfoVo> queryVmSrDeviceinfoList(
			PaginationParam paginationParam) {
		return virtualExtendDAO.pageQuery("selectVmSrDeviceinfoListTotal", "selectVmSrDeviceinfoListPage", paginationParam);
	}
	
	@Override
	public void updateResourceAssign(String srId) throws BizException, Exception {
		List<BmSrRrinfoVo> rrinfoList = bmSrRrinfoDao.findBmSrRrinfoListBySrId(srId);
		int rrLen = rrinfoList == null ? 0 : rrinfoList.size();
		BmSrRrinfoVo rrinfo;
		String rrinfoId;
		List<BmSrRrVmRefPo> vmRefList;
		BmSrRrVmRefPo vmRef;
		CmVmPo cmVm;
		CmHostPo host;
		HashMap<String, String> map = new HashMap<String, String> (); // openstackX86平台使用
		boolean allotFlag = true; // 分配成功失败标识
		for(int i=0 ; i<rrLen ; i++) {
			rrinfo = rrinfoList.get(i);
			JSONObject json = JSONObject.parseObject(rrinfo.getParametersJson());
			rrinfoId = rrinfo.getRrinfoId();
			vmRefList = bmSrRrVmRefDao.findBmSrRrVmRefListByRrinfoId(rrinfoId);
			int len = vmRefList == null ? 0 : vmRefList.size();
			for(int j=0 ; j<len ; j++) {
				vmRef = vmRefList.get(j);
				cmVm = cmVmDao.findCmVmById(vmRef.getDeviceId());
				host = cmHostDao.findCmHostById(cmVm.getHostId());
				if(host == null) {
					throw new BizException("指定的物理机不存在");
				}
				if(j == 0) {
					map.put(rrinfoId, host.getClusterId());
				}
				int cpu = 0, mem = 0;
				if(cmVm.getCpu() < json.getIntValue("cpu")) { // 原CPU比现在申请的CPU小，扩容操作
					cpu = json.getIntValue("cpu") - cmVm.getCpu();
				}
				if(cmVm.getMem() < json.getIntValue("mem")) { // 原内存比现在申请的内存小，扩容操作
					mem = json.getIntValue("mem") - cmVm.getMem();
				}
				if(cpu > 0 || mem > 0) { // 有扩容操作，则进行验证资源是否充足
					boolean isFree = computePolicyService.checkHostResPoolInfo(host.getId(), cpu, mem);
					if(isFree) { // 资源充足则进行锁定本机资源
						vmRef.setIsEnough("1");
					} else {
						allotFlag = false;
						vmRef.setIsEnough("0");
					}
				} else { // 若CPU内存都不需要扩容，则默认本机资源充足
					vmRef.setIsEnough("1");
				}
				bmSrRrVmRefDao.updateBmSrRrVmRef(vmRef);
			}
		}
		if(allotFlag) { // 分配成功
			bmSrDao.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_ASSIGN_SUCCESS.getValue());
		} else {
			bmSrDao.updateBmSrStatus(srId, SrStatusCodeEnum.REQUEST_ASSIGN_FAILURE.getValue());
		}
	}
	
	
	private String getHostIds(List<PolicyResultVo> policyList, String hostId) {
		String hostIds = "";
		int len = policyList == null ? 0 : policyList.size();
		for(int i=0 ; i<len ; i++) {
			if(hostId.equals(policyList.get(i).getHostId())) {
				continue;
			}
			hostIds += ",'" + policyList.get(i).getHostId() + "'";
		}
		return hostIds.length() > 0 ? hostIds.substring(1) : "";
	}
	
	
	public String getDeviceName(String deviceId) throws BizException{
		String deviceName = "";
		CmDevicePo cmDevicePo = cmDeviceDao.findCmDeviceById(deviceId);
		if(cmDevicePo != null) {
			deviceName = cmDevicePo.getDeviceName();
		}
		return deviceName;
	}
	
	
	public void setVirtualExtendDAO(IVirtualExtendDAO virtualExtendDAO) {
		this.virtualExtendDAO = virtualExtendDAO;
	}
	public void setComputePolicyService(IComputePolicyService computePolicyService) {
		this.computePolicyService = computePolicyService;
	}
	public void setRmClusterDao(IRmClusterDAO rmClusterDao) {
		this.rmClusterDao = rmClusterDao;
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
	public void setCmVmDao(ICmVmDAO cmVmDao) {
		this.cmVmDao = cmVmDao;
	}
	public void setCmHostDao(ICmHostDAO cmHostDao) {
		this.cmHostDao = cmHostDao;
	}
	public void setCmDeviceDao(ICmDeviceDAO cmDeviceDao) {
		this.cmDeviceDao = cmDeviceDao;
	}
	public void setBmSrAttrValDao(IBmSrAttrValDao bmSrAttrValDao) {
		this.bmSrAttrValDao = bmSrAttrValDao;
	}
}