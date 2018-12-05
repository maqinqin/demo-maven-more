package com.git.cloud.resmgt.network.action;

import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.resmgt.network.model.po.RmNwRuleListPo;
import com.git.cloud.resmgt.network.model.po.RmNwRulePo;
import com.git.cloud.resmgt.network.service.IRmNwRuleService;
import com.google.common.collect.Maps;

public class RmNwRuleAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IRmNwRuleService iRmNwRuleService;
	private RmNwRulePo rmNwRulePo;
	private RmNwRuleListPo rmNwRuleListPo;
	public IRmNwRuleService getiRmNwRuleService() {
		return iRmNwRuleService;
	}
	public void setiRmNwRuleService(IRmNwRuleService iRmNwRuleService) {
		this.iRmNwRuleService = iRmNwRuleService;
	}
	public RmNwRulePo getRmNwRulePo() {
		return rmNwRulePo;
	}
	public void setRmNwRulePo(RmNwRulePo rmNwRulePo) {
		this.rmNwRulePo = rmNwRulePo;
	}
	public RmNwRuleListPo getRmNwRuleListPo() {
		return rmNwRuleListPo;
	}
	public void setRmNwRuleListPo(RmNwRuleListPo rmNwRuleListPo) {
		this.rmNwRuleListPo = rmNwRuleListPo;
	}
	
	public String rmNwRuleView()  {
		return "success";
	}	
	
	public void getRmNwRuleList() throws Exception{
		this.jsonOut(iRmNwRuleService.getRmNwRulePagination(this.getPaginationParam()));
	}

	public void saveRmNwRule() throws RollbackableBizException{
		iRmNwRuleService.saveRmNwRule(rmNwRulePo);
	}
	
	public void selectRmNwRuleById() throws Exception{
		RmNwRulePo rmNwRulePo1=iRmNwRuleService.selectRmNwRuleById(rmNwRulePo.getRmNwRuleId());
		jsonOut(rmNwRulePo1);
	}
	
	public void updateRmNwRule() throws RollbackableBizException{
		iRmNwRuleService.updateRmNwRule(rmNwRulePo);
	}
	
	public void deleteRmNwRule() throws Exception {
		String msg="";
		msg=iRmNwRuleService.isDeleteRmNwRule(rmNwRulePo.getRmNwRuleId().split(","));
		if("".equals(msg)){
			msg=iRmNwRuleService.deleteRmNwRule(rmNwRulePo.getRmNwRuleId().split(","));
		}
	Map<String, String> map = Maps.newHashMap();
	map.put("msg", msg);
	jsonOut(map);
	}
	
	public void getRmNwRuleListList() throws Exception{
		this.jsonOut(iRmNwRuleService.getRmNwRuleListPagination(this.getPaginationParam()));
	}
	
	public void saveRmNwRuleList() throws RollbackableBizException{
		iRmNwRuleService.saveRmNwRuleList(rmNwRuleListPo);
	}
	
	public void selectRmNwRuleListById() throws Exception{
		RmNwRuleListPo rmNwRuleListPo1=iRmNwRuleService.selectRmNwRuleListById(rmNwRuleListPo.getRmNwRuleListId());
		jsonOut(rmNwRuleListPo1);
		
	}
	
	public void  updateRmNwRuleList() throws RollbackableBizException{
		iRmNwRuleService.updateRmNwRuleList(rmNwRuleListPo);
	}
	
	public void deleteRmNwRuleList() throws RollbackableBizException{
		iRmNwRuleService.deleteRmNwRuleList(rmNwRuleListPo.getRmNwRuleListId());
		
	}
	
	public void selectRmNwRuleListByUseCode() throws Exception{
		RmNwRuleListPo rmNwRuleListPo1=iRmNwRuleService.selectRmNwRuleListByUseCode(rmNwRuleListPo);
		jsonOut(rmNwRuleListPo1);
		
	}
	
	public void selectRmNwRuleByRuleName() throws Exception{
		RmNwRulePo rmNwRulePo1=iRmNwRuleService.selectRmNwRuleByRuleName(rmNwRulePo.getPlatFormId(),rmNwRulePo.getVirtualTypeId(),rmNwRulePo.getHostTypeId(),rmNwRulePo.getHaType());
		jsonOut(rmNwRulePo1);
	}
}
