package com.git.cloud.policy.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.policy.model.po.RmVmParamPo;
import com.git.cloud.policy.model.vo.RmVmParamVo;
import com.git.cloud.policy.service.IRmVmParamService;

/**
 * @Title 		RmVmParamAction.java 
 * @Package 	com.git.cloud.policy.action 
 * @author 		yangzhenhai
 * @date 		2014-9-11下午4:42:14
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmVmParamAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private RmVmParamPo rmVmParamPo ;
	
	private List<Map<String, Object>> resultLst;
	
	private IRmVmParamService rmVmParamService;
	
	public RmVmParamPo getRmVmParamPo() {
		return rmVmParamPo;
	}
	
	public void setRmVmParamPo(RmVmParamPo rmVmParamPo) {
		this.rmVmParamPo = rmVmParamPo;
	}
	
	public IRmVmParamService getRmVmParamService() {
		return rmVmParamService;
	}

	public void setRmVmParamService(IRmVmParamService rmVmParamService) {
		this.rmVmParamService = rmVmParamService;
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
	public void saveRmVmParamPo() throws Exception{
		
		try {
			rmVmParamService.saveRmVmParamPo(rmVmParamPo);
		} catch (Exception e) {
			errorOut(e);
		}
	}
	/**
	 * 
	 * 更新数据
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateRmVmParamPo() throws Exception{
		try {
			rmVmParamService.updateRmVmParamPo(rmVmParamPo);
		} catch (Exception e) {
			errorOut(e);
		}
	}
	/**
	 * 
	 *根据ID查实体
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void findRmVmParamPoById() throws Exception{
		rmVmParamPo = rmVmParamService.findRmVmParamPoById(rmVmParamPo.getParamId());
		this.jsonOut(rmVmParamPo);
	}
	/**
	 * 
	 * 分页方法
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void queryRmVmParamPoPagination() throws Exception{
		Pagination<RmVmParamVo> pagination = rmVmParamService.queryRmVmParamPoPagination(this.getPaginationParam());
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
	public void deleteRmVmParamPoById() throws Exception{
		rmVmParamService.deleteRmVmParamPoById(rmVmParamPo.getParamId().split(","));
	}
	/**
	 * 
	 * @Title: checkRmVmParamPo
	 * @Description: 验证是否已经存在相同条件的数据
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void checkRmVmParamPo() throws Exception{
		ObjectOut(rmVmParamService.checkRmVmParamPo(rmVmParamPo));
	}
	/**
	 * 查询所有资源池
	 * @Title: queryPoolList
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void queryPoolList() throws Exception {
		resultLst = rmVmParamService.queryPoolList();
		this.arrayOut(resultLst);
	}
}
