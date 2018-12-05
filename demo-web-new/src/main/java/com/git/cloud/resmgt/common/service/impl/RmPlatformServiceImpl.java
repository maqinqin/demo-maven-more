package com.git.cloud.resmgt.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.dao.IRmPlatformDAO;
import com.git.cloud.resmgt.common.model.po.RmHostResPo;
import com.git.cloud.resmgt.common.model.po.RmPlatformPo;
import com.git.cloud.resmgt.common.model.po.RmVirtualTypePo;
import com.git.cloud.resmgt.common.service.IRmPlatformService;
import com.git.cloud.resmgt.compute.model.po.RmCdpPo;
import com.git.cloud.resmgt.network.model.po.RmNwCclassPo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.taglib.util.Internation;

public class RmPlatformServiceImpl implements IRmPlatformService{
	private IRmPlatformDAO rmPlatformDAO;

	public IRmPlatformDAO getRmPlatformDAO() {
		return rmPlatformDAO;
	}

	public void setRmPlatformDAO(IRmPlatformDAO rmPlatformDAO) {
		this.rmPlatformDAO = rmPlatformDAO;
	}

	@Override
	public Object getRmPlatformPagination(PaginationParam paginationParam) {
		return rmPlatformDAO.pageQuery("findRmPlatformTotal", "findRmPlatformPage", paginationParam);
	}

	@Override
	public void saveRmPlatform(RmPlatformPo rmPlatformPo) throws RollbackableBizException {
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		String id = com.git.cloud.foundation.util.UUIDGenerator.getUUID();
		rmPlatformPo.setCreateDateTime(new Date());
		rmPlatformPo.setCreateUser(user);
		rmPlatformPo.setPlatformId(id);
		rmPlatformPo.setIsActive(IsActiveEnum.YES.getValue());
		rmPlatformDAO.savaRmPlatform(rmPlatformPo);
		
	}

	@Override
	public void updateRmPlatform(RmPlatformPo rmPlatformPo) throws RollbackableBizException {
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		rmPlatformPo.setUpdateUser(user);
		rmPlatformPo.setUpdateDateTime(new Date());
		rmPlatformDAO.updateRmPlatform(rmPlatformPo);
		
	}

	@Override
	public RmPlatformPo selectRmPlatform(String platformId) throws RollbackableBizException {
		return rmPlatformDAO.selectRmPlatform(platformId);
	}
	
	@Override
	public List<Map<String, Object>> selectRmPlatformSel() throws RollbackableBizException {
		return rmPlatformDAO.selectRmPlatformSel();
	}

	@Override
	public String deleteRmPlatform(String[] split) throws RollbackableBizException {
		String meg=Internation.language("plat_delete_s");//selectRmVirtualTypeByPlatformId
			for(String id:split){
				rmPlatformDAO.deleteRmPlatform(id);
			}
		return meg;
	}

	@Override
	public RmPlatformPo selectRmPlatformForTrim(String platformId) throws RollbackableBizException {
		return rmPlatformDAO.selectRmPlatformForTrim(platformId);
	}

	@Override
	public Object getRmVirtualTypePagination(PaginationParam paginationParam) {
		
		return rmPlatformDAO.pageQuery("findRmVirtualTypTotal", "findRmVirtualTypPage", paginationParam);
	}

	@Override
	public void saveVirtualType(RmVirtualTypePo rmVirtualTypePo) throws RollbackableBizException {
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		String id = com.git.cloud.foundation.util.UUIDGenerator.getUUID();
		rmVirtualTypePo.setCreateDateTime(new Date());
		rmVirtualTypePo.setCreateUser(user);
		rmVirtualTypePo.setVirtualTypeId(id);
		rmVirtualTypePo.setIsActive(IsActiveEnum.YES.getValue());
		rmPlatformDAO.savaVirtualType(rmVirtualTypePo);
	}

	@Override
	public RmVirtualTypePo selectRmVirtualTypeForTrim(String virtualTypeCode) throws RollbackableBizException {
		return rmPlatformDAO.selectRmVirtualTypeForTrim(virtualTypeCode);
	}

	@Override
	public RmVirtualTypePo selectRmVirtualType(String virtualTypeId) throws RollbackableBizException {
		return rmPlatformDAO.selectRmVirtualType(virtualTypeId);
	}
	
	@Override
	public List<Map<String, Object>> selectRmVirtualTypeSel(String platformId) throws RollbackableBizException {
		return rmPlatformDAO.selectRmVirtualTypeSel(platformId);
	}

	@Override
	public void updateRmVirtualType(RmVirtualTypePo rmVirtualTypePo) throws RollbackableBizException {
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		rmVirtualTypePo.setUpdateUser(user);
		rmVirtualTypePo.setUpdateDateTime(new Date());
		rmPlatformDAO.updateRmVirtualType(rmVirtualTypePo);
	}

	@Override
	public String deleteVirtualType(String virtualTypeId) throws RollbackableBizException {
		String meg=Internation.language("plat_delete_s");
			rmPlatformDAO.deleteVirtualType(virtualTypeId);
		return meg;
	}

	@Override
	public RmPlatformPo selectRmPlatformNameForTrim(String platformName) throws RollbackableBizException {
		return rmPlatformDAO.selectRmPlatformNameForTrim(platformName);
	}

	@Override
	public String isDeleteRmPlatform(String[] split) throws RollbackableBizException {
		String meg="";//selectRmVirtualTypeByPlatformId
		if(split!=null&&split.length>0){
			for(String id:split){
				RmPlatformPo rmPlatformPo=rmPlatformDAO.selectRmPlatform(id);
				List<RmHostResPo> rmHostResPoList=rmPlatformDAO.selectRmHostResByPlatformId(id);
				List<RmCdpPo> rmCdpPoList=rmPlatformDAO.selectRmCdpByPlatformId(id);
				List<RmNwCclassPo> rmNwCclassPoList=rmPlatformDAO.selectRmNwCclassByPlatformId(id);
				List<CloudServicePo> cloudServicePoList=rmPlatformDAO.selectRmCloudServiceByPlatformId(id);
				List<RmVirtualTypePo> rmVirtualTypePoList=rmPlatformDAO.selectRmVirtualTypeByPlatformId(id);
				if(rmHostResPoList!=null&&rmHostResPoList.size()>0){
					meg=Internation.language("name_you_select")+rmPlatformPo.getPlatformName()+Internation.language("used_in_compute_pool");
					break;
				}else if(rmCdpPoList!=null&&rmCdpPoList.size()>0){
					meg=Internation.language("name_you_select")+rmPlatformPo.getPlatformName()+Internation.language("used_in_convergence");
					break;
				}else if(rmNwCclassPoList!=null&&rmNwCclassPoList.size()>0){
					meg=Internation.language("name_you_select")+rmPlatformPo.getPlatformName()+Internation.language("user_in_network");
					break;
				}else if(cloudServicePoList!=null&&cloudServicePoList.size()>0){
					meg=Internation.language("name_you_select")+rmPlatformPo.getPlatformName()+Internation.language("user_in_cloud_service");
					break;
				}else if(rmVirtualTypePoList!=null&&rmVirtualTypePoList.size()>0){
					meg=Internation.language("name_you_select")+rmPlatformPo.getPlatformName()+Internation.language("vitrual_exited_in");
					break;
				}else{
					meg="";
				}
				
			}
			
		}
		return meg;
		
	}

	@Override
	public String isDeleteVirtualType(String virtualTypeId) throws RollbackableBizException {
		String meg="";
		List<RmCdpPo> rmCdpPoList=rmPlatformDAO.selectRmCdpByvirtualTypeId(virtualTypeId);
		List<CloudServicePo> cloudServicePoList=rmPlatformDAO.selectRmCloudServiceByvirtualTypeId(virtualTypeId);
		if(rmCdpPoList!=null&&rmCdpPoList.size()>0){
			meg=Internation.language("used_in_cdp");
		}else if(cloudServicePoList!=null&&cloudServicePoList.size()>0){
			meg=Internation.language("used_in_cloud_service2");
		}else{
			meg="";
		}
		
		return meg;
	}
	
}
