package com.git.cloud.resmgt.network.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.network.dao.IRmNwUseDAO;
import com.git.cloud.resmgt.network.model.po.RmNwUsePo;
import com.git.cloud.resmgt.network.model.po.RmNwUseRelPo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseRelVo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseVo;
import com.git.cloud.resmgt.network.service.IRmNwUseService;
import com.git.cloud.taglib.util.Internation;
/**
 * @Title 		RmNwUseServiceImpl.java 
 * @Package 	com.git.cloud.resmgt.network.service.impl
 * @author 		make
 * @date 		2015-3-6下午14:24:21
 * @version 	1.0.0
 * @Description  
 *
 */
public class RmNwUseServiceImpl implements IRmNwUseService{
	private IRmNwUseDAO rmNwUseDAO;	

	public IRmNwUseDAO getRmNwUseDAO() {
		return rmNwUseDAO;
	}

	public void setRmNwUseDAO(IRmNwUseDAO rmNwUseDAO) {
		this.rmNwUseDAO = rmNwUseDAO;
	}

	@Override
	public Pagination<RmNwUseVo> queryRmNwUsePoPagination(
			PaginationParam pagination) {
		return rmNwUseDAO.queryRmNwUsePoPagination(pagination);
	}
	
	@Override
	public List<Map<String, Object>> queryUseNameList() throws RollbackableBizException {
		return rmNwUseDAO.queryUseNameList();
	}
	
	@Override
	public List<Map<String, Object>> queryDatacenterList() throws RollbackableBizException {
		return rmNwUseDAO.queryDatacenterList();
	}
	
	@Override
	public  void saveRmNwUsePo(RmNwUsePo rmNwUsePo) throws Exception {
		if(this.checkRmNwUsePo(rmNwUsePo)){
			rmNwUsePo.setUseId(UUIDGenerator.getUUID());
			rmNwUsePo.setIsActive(IsActiveEnum.YES.getValue());
			rmNwUseDAO.saveRmNwUsePo(rmNwUsePo);
		}else{
			throw new Exception(Internation.language("ip_use_exit"));
		}
	}
	
	@Override
	public  void saveRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) throws Exception {
		if(this.checkRmNwUseRelPo(rmNwUseRelPo)){
			rmNwUseRelPo.setUseRelId(UUIDGenerator.getUUID());
			rmNwUseRelPo.setIsActive(IsActiveEnum.YES.getValue());
			rmNwUseDAO.saveRmNwUseRelPo(rmNwUseRelPo);
		}else{
			throw new Exception(Internation.language("ip_use_rela_exit"));
		}
	}
	
	@Override
	public  void updateRmNwUsePo(RmNwUsePo rmNwUsePo) throws Exception {
		if(this.checkRmNwUsePo(rmNwUsePo)){
		rmNwUseDAO.updateRmNwUsePo(rmNwUsePo);
		}else{
			throw new Exception(Internation.language("ip_use_exit"));
		}
	}
	
	@Override
	public  void updateRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) throws Exception {
		if(this.checkRmNwUseRelPo(rmNwUseRelPo)){
		rmNwUseDAO.updateRmNwUseRelPo(rmNwUseRelPo);
		}else{
			throw new Exception(Internation.language("ip_use_rela_exit"));
		}
	}
	
	@Override
	public boolean checkRmNwUsePo(RmNwUsePo rmNwUsePo)
			throws RollbackableBizException {
		int count = rmNwUseDAO.findRmNwUsePosByUse(rmNwUsePo);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public boolean checkRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo)
			throws RollbackableBizException {
		int count = rmNwUseDAO.findRmNwUseRelPosByUseRel(rmNwUseRelPo);
		if(count > 0){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public RmNwUsePo findRmNwUsePoById(String id) throws RollbackableBizException {
		RmNwUsePo rmNwUsePo = rmNwUseDAO.findRmNwUsePoById(id);
		return rmNwUsePo;
	}
	
	@Override
	public RmNwUseRelPo findRmNwUseRelPoById(String id) throws RollbackableBizException {
		RmNwUseRelPo rmNwUseRelPo = rmNwUseDAO.findRmNwUseRelPoById(id);
		return rmNwUseRelPo;
	}
	
	@Override
	public List<RmNwUseRelPo> findRmNwUseRelsByUseId(String useId) throws RollbackableBizException{
		List<RmNwUseRelPo> lst = new ArrayList<RmNwUseRelPo>();
		lst = rmNwUseDAO.findRmNwUseRelsByUseId(useId);
		return lst;
	}
	
	@Override
	public String deleteRmNwUsePoById(String[] ids) throws RollbackableBizException {
		StringBuffer result = new StringBuffer();
		List<String> delIds = new ArrayList<String>();
		for(String id : ids)
		{
			if (findRmNwUseRelsByUseId(id).size() > 0)
			{
				if (result.length() > 0)
				{
					result.append(",");
				}
				result.append(findRmNwUsePoById(id).getUseName());
			}
			else
			{
				delIds.add(id);
			}	
		}
		if (result.length() <= 0)
		{
			for (String id : delIds)
			{
				rmNwUseDAO.deleteRmNwUsePoById(id);
			}
		}
		return result.toString();
	}
	
	@Override
	public void deleteRmNwUseRelPoById(String[] ids) throws RollbackableBizException {
		for(String id : ids)
		{
			rmNwUseDAO.deleteRmNwUseRelPoById(id);
		}
	}
	
	@Override
	public Pagination<RmNwUseRelVo> queryRmNwUseRelPoPagination(
			PaginationParam pagination) throws Exception {
		return rmNwUseDAO.queryRmNwUseRelPoPagination(pagination);
	}
	
	@Override
	public List<Map<String, Object>> selectRmHostTypeSel() throws RollbackableBizException {
		return rmNwUseDAO.selectRmHostTypeSel();
	}
}
