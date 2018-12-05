package com.git.cloud.request.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.appmgt.model.vo.DeployUnitVo;
import com.git.cloud.appmgt.service.IDeployunitService;
import com.git.cloud.cloudservice.dao.ICloudServiceDao;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.handler.common.CommonUtil;
import com.git.cloud.policy.service.IIpAllocToDeviceNewService;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.request.dao.IBmSrRrVmRefDao;
import com.git.cloud.request.dao.IBmSrRrinfoDao;
import com.git.cloud.request.dao.IVirtualRecycleDAO;
import com.git.cloud.request.model.vo.BmSrVo;
import com.git.cloud.request.model.vo.VirtualRecycleVo;
import com.git.cloud.request.service.IVirtualRecycleService;
import com.git.cloud.resmgt.common.model.bo.CmDeviceVMShowBo;
import com.git.cloud.resmgt.common.model.bo.CmIpShowBo;
import com.git.cloud.resmgt.common.model.vo.CmVmVo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;

/**
 * 云服务回收申请接口类
 * @ClassName:VirtualRecycleServiceImpl
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class VirtualRecycleServiceImpl implements IVirtualRecycleService {
	
	private static Logger logger = LoggerFactory.getLogger(VirtualRecycleServiceImpl.class);
	
	private IBmSrDao bmSrDao;
	private IBmSrRrinfoDao bmSrRrinfoDao;
	private IBmSrRrVmRefDao bmSrRrVmRefDao;
	private IVirtualRecycleDAO virtualRecycleDAO;
	// 外包服务
	private ICloudServiceDao cloudServiceDao;
	IIpAllocToDeviceNewService ipAllocToVMService;
	
	// 根据srId获取回收的信息对象
	
	public VirtualRecycleVo getVirtualRecycleVoBySrId(String srId) throws Exception {
		ICmDeviceService cmDeviceService = (ICmDeviceService) WebApplicationManager.getBean("cmDeviceServiceImpl");
		IDeployunitService deployunitServiceImpl = (IDeployunitService) WebApplicationManager.getBean("deployunitServiceImpl");
		VirtualRecycleVo virtualRecycleVo = new VirtualRecycleVo();	
		List<CmVmVo> cmVmVoList = new ArrayList<CmVmVo>();
		cmVmVoList = virtualRecycleDAO.findByID("findCmVmVoListBySrId", srId);
		for(CmVmVo vmVo: cmVmVoList){
			CmDeviceVMShowBo cmDeviceVMShowBo = cmDeviceService.getCmDeviceVMInfoOpenstack(vmVo.getId());
			List<CmIpShowBo> list = cmDeviceVMShowBo.getIpList();
			String ips = null ;
			for (int i = 0; i < list.size(); i++) {
				ips = "<tr><td style='border: 0px solid #abc6d1;width:15%;'>"+list.get(i).getRm_ip_type_name()+"：</td><td style='border: 0px solid #abc6d1'><label id=''>"+list.get(i).getIp()+"</label></td></tr>";
			}
			vmVo.setCmVmIps("<table id='ip_table' class='ip_table' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:100%'>" + ips + "</table>");
			if(!CommonUtil.isEmpty(cmDeviceVMShowBo.getDuID())) {
				DeployUnitVo deployUnitVo = deployunitServiceImpl.getDeployUnitById(cmDeviceVMShowBo.getDuID().toString());
				if(!CommonUtil.isEmpty(deployUnitVo)) {}{
					vmVo.setDuId(cmDeviceVMShowBo.getDuID());
					vmVo.setDuEname(deployUnitVo.getEname());
					vmVo.setDuName(deployUnitVo.getCname());
				}
			}
		}
		BmSrVo bmSrVo = bmSrDao.findBmSrVoById(srId);
		virtualRecycleVo.setBmSr(bmSrVo);
		virtualRecycleVo.setCmVmList(cmVmVoList);
		return virtualRecycleVo;
	}
	
	
	public IBmSrDao getBmSrDao() {
		return bmSrDao;
	}
	public IBmSrRrinfoDao getBmSrRrinfoDao() {
		return bmSrRrinfoDao;
	}
	public IBmSrRrVmRefDao getBmSrRrVmRefDao() {
		return bmSrRrVmRefDao;
	}
	public IVirtualRecycleDAO getVirtualRecycleDAO() {
		return virtualRecycleDAO;
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
	public void setVirtualRecycleDAO(IVirtualRecycleDAO virtualRecycleDAO) {
		this.virtualRecycleDAO = virtualRecycleDAO;
	}
	public ICloudServiceDao getCloudServiceDao() {
		return cloudServiceDao;
	}
	public void setCloudServiceDao(ICloudServiceDao cloudServiceDao) {
		this.cloudServiceDao = cloudServiceDao;
	}
	public IIpAllocToDeviceNewService getIpAllocToVMService() {
		return ipAllocToVMService;
	}
	public void setIpAllocToVMService(IIpAllocToDeviceNewService ipAllocToVMService) {
		this.ipAllocToVMService = ipAllocToVMService;
	}
}