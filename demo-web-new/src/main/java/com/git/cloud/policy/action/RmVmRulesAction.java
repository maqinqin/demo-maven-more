package com.git.cloud.policy.action;

import java.util.List;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.model.AdminDicPo;
import com.git.cloud.common.service.IDictionaryService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.policy.model.po.RmVmRulesPo;
import com.git.cloud.policy.model.vo.RmVmRulesVo;
import com.git.cloud.policy.service.IRmVmRulesService;

/**
 * @Title 		RmVmRulesAction.java 
 * @Package 	com.git.cloud.policy.action 
 * @author 		yangzhenhai
 * @date 		2014-9-16上午10:06:29
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVmRulesAction extends BaseAction {

	private IRmVmRulesService rmVmRulesService;
	
	private RmVmRulesPo rmVmRulesPo;
	
	private IDictionaryService dicService;
	
	private String sortTypeName;
	
	
	/**
	 * @return the sortTypeName
	 */
	public String getSortTypeName() {
		return sortTypeName;
	}
	/**
	 * @param sortTypeName the sortTypeName to set
	 */
	public void setSortTypeName(String sortTypeName) {
		this.sortTypeName = sortTypeName;
	}
	public IDictionaryService getDicService() {
		return dicService;
	}
	public void setDicService(IDictionaryService dicService) {
		this.dicService = dicService;
	}

	public IRmVmRulesService getRmVmRulesService() {
		return rmVmRulesService;
	}

	public void setRmVmRulesService(IRmVmRulesService rmVmRulesService) {
		this.rmVmRulesService = rmVmRulesService;
	}

	public RmVmRulesPo getRmVmRulesPo() {
		return rmVmRulesPo;
	}

	public void setRmVmRulesPo(RmVmRulesPo rmVmRulesPo) {
		this.rmVmRulesPo = rmVmRulesPo;
	}
	
	public String index(){
		
		return SUCCESS;
	}

	/**
	 * 
	 * 保存数据
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void saveRmVmRulesPo() throws Exception{
		rmVmRulesService.saveRmVmRulesPo(rmVmRulesPo);
	}
	/**
	 * 
	 * 更新数据
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateRmVmRulesPo() throws Exception{
		rmVmRulesPo.setSortType(sortTypeName);
		rmVmRulesService.updateRmVmRulesPo(rmVmRulesPo);
	}
	/**
	 * 
	 *根据ID查实体
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void findRmVmRulesPoById() throws Exception{
		rmVmRulesPo = rmVmRulesService.findRmVmRulesPoById(rmVmRulesPo.getRulesId());
		this.jsonOut(rmVmRulesPo);
	}
	/**
	 * 
	 * 分页方法
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void queryRmVmRulesPoPagination() throws Exception{
		Pagination<RmVmRulesVo> pagination = rmVmRulesService.queryRmVmRulesPoPagination(this.getPaginationParam());
		this.jsonOut(pagination);
	}
	/**
	 * 
	 * 删除数据
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void deleteRmVmRulesPoById() throws Exception{
		rmVmRulesService.deleteRmVmRulesPoById(rmVmRulesPo.getRulesId().split(","));
	}
	
	public void getSortTypeNames()throws Exception{
		List<AdminDicPo> adminDicPos = dicService.getByTypeCode("SORT_TYPE");
		arrayOut(adminDicPos);
	}
}
