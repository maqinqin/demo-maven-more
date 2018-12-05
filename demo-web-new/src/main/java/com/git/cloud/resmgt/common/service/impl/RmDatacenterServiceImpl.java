package com.git.cloud.resmgt.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.common.dao.IRmDatacenterDAO;
import com.git.cloud.resmgt.common.model.po.RmDatacenterPo;
import com.git.cloud.resmgt.common.service.IRmDatacenterService;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.support.util.PwdUtil;
import com.google.common.collect.Maps;

/**
 * @ClassName:RmDatacenterServiceImpl
 * @Description:数据中心信息
 * @author mijia
 * @date 2014-12-17 下午1:43:25
 *
 *
 */
public class RmDatacenterServiceImpl implements IRmDatacenterService {

	private IRmDatacenterDAO rmDatacenterDAO;
	
	@Override
	public RmDatacenterPo getDataCenterById(String dcId)
			throws RollbackableBizException {
		return rmDatacenterDAO.getDataCenterById(dcId);
	}

	@Override
	public List<RmDatacenterPo> getDataCenters()
			throws RollbackableBizException {
		return rmDatacenterDAO.getDataCenters();
	}

	@Override
	public Object getDevicePagination(PaginationParam paginationParam) {
		return rmDatacenterDAO.pageQuery("findRmDatacenterTotal", "findRmDatacenterPage", paginationParam);
	}

	@Override
	public void saveRmDatacenter(RmDatacenterPo rmDatacenterPo) throws RollbackableBizException {
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		String id = com.git.cloud.foundation.util.UUIDGenerator.getUUID();
		rmDatacenterPo.setCreateDateTime(new Date());
		rmDatacenterPo.setCreateUser(user);
		rmDatacenterPo.setId(id);
		rmDatacenterPo.setIsActive(IsActiveEnum.YES.getValue());
		rmDatacenterDAO.saveRmDatacenter(rmDatacenterPo);
		
	}

	@Override
	public void updateRmDatacenter(RmDatacenterPo rmDatacenterPo) throws RollbackableBizException {
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		rmDatacenterPo.setUpdateDateTime(new Date());
		rmDatacenterPo.setUpdateUser(user);
		rmDatacenterDAO.updateRmDatacenter(rmDatacenterPo);
		
	}

	@Override
	public Map<String, String> selectPoolByDatacenterId(String dataCenterId) throws RollbackableBizException {
		String count=(String) rmDatacenterDAO.selectPoolByDatacenterId(dataCenterId);
		RmDatacenterPo rmDatacenterPo=rmDatacenterDAO.getDataCenterById(dataCenterId);
		String datacenterName=rmDatacenterPo.getDatacenterCname();
		Map<String, String> map = Maps.newHashMap();
		map.put("count", count);
		map.put("datacenterName", datacenterName);
		return  map;
	}

	@Override
	public void deleteDatacenter(String[] split) throws RollbackableBizException {
		rmDatacenterDAO.deleteDatacenter(split);
		
	}

	@Override
	public RmDatacenterPo selectQueueIdenfortrim(String queueIden) throws RollbackableBizException {
		return rmDatacenterDAO.selectQueueIdenfortrim(queueIden);
		
	}

	@Override
	public RmDatacenterPo selectDCenamefortrim(String ename) throws RollbackableBizException {
		return  rmDatacenterDAO.selectDCenamefortrim(ename);
	}

	public void setRmDatacenterDAO(IRmDatacenterDAO rmDatacenterDAO) {
		this.rmDatacenterDAO = rmDatacenterDAO;
	}

	@Override
	public List<RmDatacenterPo> getDataCenterAccessData() throws RollbackableBizException {
		List<RmDatacenterPo> poList = rmDatacenterDAO.getDataCenterAccessData();
		for(RmDatacenterPo po : poList){
			po.setPassword(PwdUtil.decryption(po.getPassword()));
		}
		return poList;
	}
	
	@Override
	public String getDiskList() throws Exception{
//		String openstackIp = "172.21.31.10";
//		String token = OpenstackServiceFactory.getTokenServiceInstance(openstackIp).getToken();
//		String targetProjectId = OpenstackServiceFactory.getIdentityServiceInstance(openstackIp, token).getManageProject();
//		String volumeList = OpenstackServiceFactory.getVolumeServiceInstance(openstackIp, token).getVolumeList(token, targetProjectId);
		return null;
	}

	@Override
	public String getDiskDetailed(String volumeId) throws Exception {
//		String openstackIp = "172.21.31.10";
//		String token = OpenstackServiceFactory.getTokenServiceInstance(openstackIp).getToken();
//		String targetProjectId = OpenstackServiceFactory.getIdentityServiceInstance(openstackIp, token).getManageProject();
//		String volumeDetail = OpenstackServiceFactory.getVolumeServiceInstance(openstackIp, token).getVolumeDetail(token, targetProjectId, volumeId);
		return null;
	}

}
