package com.git.cloud.resmgt.network.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.resmgt.network.dao.IRmNwRuleDAO;
import com.git.cloud.resmgt.network.model.po.RmNwRuleListPo;
import com.git.cloud.resmgt.network.model.po.RmNwRulePo;
import com.git.cloud.resmgt.network.service.IRmNwRuleService;
import com.git.cloud.sys.model.po.SysUserPo;

public class RmNwRuleServiceImpl implements IRmNwRuleService{

	private IRmNwRuleDAO iRmNwRuleDAO;
	
	public IRmNwRuleDAO getiRmNwRuleDAO() {
		return iRmNwRuleDAO;
	}

	public void setiRmNwRuleDAO(IRmNwRuleDAO iRmNwRuleDAO) {
		this.iRmNwRuleDAO = iRmNwRuleDAO;
	}

	@Override
	public Object getRmNwRulePagination(PaginationParam paginationParam) {
		return iRmNwRuleDAO.pageQuery("findRmNwRuleTotal", "findRmNwRulePage", paginationParam);
	}

	@Override
	public void saveRmNwRule(RmNwRulePo rmNwRulePo) throws RollbackableBizException {
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String user=sysUserPo.getFirstName()+sysUserPo.getLastName();
		String id = com.git.cloud.foundation.util.UUIDGenerator.getUUID();
		rmNwRulePo.setCreateDateTime(new Date());
		rmNwRulePo.setCreateUser(user);
		rmNwRulePo.setRmNwRuleId(id);
		rmNwRulePo.setIsActive(IsActiveEnum.YES.getValue());
		iRmNwRuleDAO.saveRmNwRule(rmNwRulePo);
	}

	@Override
	public RmNwRulePo selectRmNwRuleById(String rmNwRuleId) throws RollbackableBizException {
		RmNwRulePo rmNwRulePo=iRmNwRuleDAO.selectRmNwRuleById(rmNwRuleId);
		List<RmNwRuleListPo> rmNwRuleListPoList=iRmNwRuleDAO.selectRmNwRuleListByrmNwRuleId(rmNwRuleId);
		rmNwRulePo.setRmNwRuleLIstCount(rmNwRuleListPoList.size());
		return rmNwRulePo;
		
	}

	@Override
	public void updateRmNwRule(RmNwRulePo rmNwRulePo) throws RollbackableBizException {
		iRmNwRuleDAO.updateRmNwRule(rmNwRulePo);
		
	}

	@Override
	public String deleteRmNwRule(String[] split) throws RollbackableBizException {
		String msg="删除成功！";
			for(String id: split){
				iRmNwRuleDAO.deleteRmNwRule(id);
			}
		return msg;
	}

	@Override
	public Object getRmNwRuleListPagination(PaginationParam paginationParam) {
		return iRmNwRuleDAO.pageQuery("findRmNwRuleListTotal", "findRmNwRuleListPage", paginationParam);
	}

	@Override
	public void saveRmNwRuleList(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException {
		String id = com.git.cloud.foundation.util.UUIDGenerator.getUUID();
		rmNwRuleListPo.setRmNwRuleListId(id);
		rmNwRuleListPo.setIsActive(IsActiveEnum.YES.getValue());
		iRmNwRuleDAO.saveRmNwRuleList(rmNwRuleListPo);
		
	}

	@Override
	public RmNwRuleListPo selectRmNwRuleListById(String rmNwRuleListId) throws RollbackableBizException {
		return iRmNwRuleDAO.selectRmNwRuleListById(rmNwRuleListId);
	}

	@Override
	public void updateRmNwRuleList(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException {
		iRmNwRuleDAO.updateRmNwRuleList(rmNwRuleListPo);
	}

	@Override
	public void deleteRmNwRuleList(String rmNwRuleListId) throws RollbackableBizException {
		iRmNwRuleDAO.deleteRmNwRuleList(rmNwRuleListId);
	}

	@Override
	public RmNwRuleListPo selectRmNwRuleListByUseCode(RmNwRuleListPo rmNwRuleListPo) throws RollbackableBizException {
		return iRmNwRuleDAO.selectRmNwRuleListByUseCode(rmNwRuleListPo);
	}

	@Override
	public RmNwRulePo selectRmNwRuleByRuleName(String platFormId, String virtualTypeId, String hostTypeId,String haType) throws RollbackableBizException {
		return iRmNwRuleDAO.selectRmNwRuleByRuleName(platFormId,virtualTypeId,hostTypeId,haType);
	}

	@Override
	public String isDeleteRmNwRule(String[] split) throws RollbackableBizException {
		String msg="";
		for(String id:split){
			List<RmNwRuleListPo> rmNwRuleListPoList=iRmNwRuleDAO.selectRmNwRuleListByrmNwRuleId(id);
			RmNwRulePo rmNwRulePo=iRmNwRuleDAO.selectRmNwRuleById(id);
			if(rmNwRuleListPoList!=null&&rmNwRuleListPoList.size()>0){
				msg="所选择的名称为“"+rmNwRulePo.getRuleName()+"”的IP分配规则下已存在用途类型，无法删除！";
				break;
			}
		}
		
		return msg;
	}

}
