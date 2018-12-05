package com.git.cloud.resmgt.network.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.resmgt.network.model.po.RmNwUsePo;
import com.git.cloud.resmgt.network.model.po.RmNwUseRelPo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseRelVo;
import com.git.cloud.resmgt.network.model.vo.RmNwUseVo;
import com.git.cloud.resmgt.network.service.IRmNwUseService;

/**
 * @Title 		RmNwUseAction.java 
 * @Package 	com.git.cloud.resmgt.network.action 
 * @author 		make
 * @date 		2015-3-6下午14:42:14
 * @version 	1.0.0
 * @Description 
 *
 */
public class RmNwUseAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private RmNwUseVo rmNwUseVo;
	private RmNwUsePo rmNwUsePo;
	private RmNwUseRelPo rmNwUseRelPo;
	private IRmNwUseService rmNwUseService;
	private List<Map<String, Object>> resultLst;

	public RmNwUseVo getRmNwUseVo() {
		return rmNwUseVo;
	}

	public void setRmNwUseVo(RmNwUseVo rmNwUseVo) {
		this.rmNwUseVo = rmNwUseVo;
	}

	public RmNwUsePo getRmNwUsePo() {
		return rmNwUsePo;
	}

	public void setRmNwUsePo(RmNwUsePo rmNwUsePo) {
		this.rmNwUsePo = rmNwUsePo;
	}

	public IRmNwUseService getRmNwUseService() {
		return rmNwUseService;
	}

	public RmNwUseRelPo getRmNwUseRelPo() {
		return rmNwUseRelPo;
	}

	public void setRmNwUseRelPo(RmNwUseRelPo rmNwUseRelPo) {
		this.rmNwUseRelPo = rmNwUseRelPo;
	}

	public void setRmNwUseService(IRmNwUseService rmNwUseService) {
		this.rmNwUseService = rmNwUseService;
	}

	public String index(){
		return SUCCESS;
	}
	
	/**
	 * 
	 * 分页方法
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void queryRmNwUsePoPagination() throws Exception{
		Pagination<RmNwUseVo> pagination = rmNwUseService.queryRmNwUsePoPagination(this.getPaginationParam());
		this.jsonOut(pagination);
	}
	
	/**
	 * 查询所有网络汇聚名称
	 * @Title: queryUseNameList
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void queryUseNameList() throws Exception {
		resultLst = rmNwUseService.queryUseNameList();
		this.arrayOut(resultLst);
	}
	
	/**
	 * 查询数据中心
	 * @Title: queryDatacenterList
	 * @Description: TODO
	 * @field: @throws Exception
	 * @return void
	 * @throws
	 */
	public void queryDatacenterList() throws Exception {
		resultLst = rmNwUseService.queryDatacenterList();
		this.arrayOut(resultLst);
	}
	
	/**
	 * 
	 * 保存数据
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void saveRmNwUsePoAct() throws Exception{
		
		try {
			rmNwUseService.saveRmNwUsePo(rmNwUsePo);
		} catch (Exception e) {
			errorOut(e);
		}
	}
	
	/**
	 * 
	 * 保存数据
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void saveRmNwUseRelPoAct() throws Exception{
		
		try {
			rmNwUseService.saveRmNwUseRelPo(rmNwUseRelPo);
		} catch (Exception e) {
			errorOut(e);
		}
	}
	
	/**
	 * 
	 * 修改数据
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateRmNwUsePoAct() throws Exception{
		try {
			rmNwUseService.updateRmNwUsePo(rmNwUsePo);
		} catch (Exception e) {
			errorOut(e);
		}
	}
	
	/**
	 * 
	 * 修改数据
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateRmNwUseRelPoAct() throws Exception{
		try {
			rmNwUseService.updateRmNwUseRelPo(rmNwUseRelPo);
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
	public void findRmNwUsePoByIdAct() throws Exception{
		rmNwUsePo = rmNwUseService.findRmNwUsePoById(rmNwUsePo.getUseId());
		this.jsonOut(rmNwUsePo);
	}
	
	/**
	 * 
	 *根据ID查实体
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void findRmNwUseRelPoByIdAct() throws Exception{
		rmNwUseRelPo = rmNwUseService.findRmNwUseRelPoById(rmNwUseRelPo.getUseRelId());
		this.jsonOut(rmNwUseRelPo);
	}
	
	/**
	 * 
	 * 验证删除数据
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void checkBeforeDeleteUsePoByIdAct() throws Exception{
		rmNwUseService.deleteRmNwUsePoById(rmNwUsePo.getUseId().split(","));
	}
	
	/**
	 * 
	 * 删除数据
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void deleteRmNwUsePoById() throws Exception{
		String result = "";
		result = rmNwUseService.deleteRmNwUsePoById(rmNwUsePo.getUseId().split(","));
		if (result != null && !"".equals(result))
		{
			result = result + "已存在用途关系，无法删除！";
		}
		else
		{
			result = "删除成功！";
		}
		stringOut(result);
	}
	
	/**
	 * 
	 * 删除数据
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void deleteRmNwUseRelPoById() throws Exception{
		rmNwUseService.deleteRmNwUseRelPoById(rmNwUseRelPo.getUseRelId().split(","));
		String result = "删除成功!";
		stringOut(result);
	}
	
	/**
	 * 
	 * 查看IP用途名称是否重复
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void checkUseName() throws Exception{
		String flag = "N";
		try {
			if (rmNwUseService.checkRmNwUsePo(rmNwUsePo))
			{
				flag = "Y";//网络汇聚名称没被占用
			}	
		} catch (Exception e) {
			errorOut(e);
		}
		rmNwUsePo.setUseCode(null);
		rmNwUsePo.setUseName(null);
		stringOut(flag);
	}
	
	/**
	 * 
	 * 查看IP用途代码是否重复
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void checkUseCode() throws Exception{
		String flag = "N";
		try {
			if (rmNwUseService.checkRmNwUsePo(rmNwUsePo))
			{
				flag = "Y";//网络汇聚名称没被占用
			}	
		} catch (Exception e) {
			errorOut(e);
		}
		rmNwUsePo.setUseCode(null);
		rmNwUsePo.setUseName(null);
		stringOut(flag);
	}
	
	/**
	 * 
	 * 分页方法查询IP用途关系表
	 * @throws Exception 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	public void queryRmNwUseRelPoPagination() throws Exception{
//		PaginationParam pageParams = this.getPaginationParam();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("useId", rmNwUsePo.getUseId());
//		pageParams.setParams(map);
		Pagination<RmNwUseRelVo> pagination = rmNwUseService.queryRmNwUseRelPoPagination(this.getPaginationParam());
		this.jsonOut(pagination);
	}
	
	/**
	 * 
	 * @throws Exception
	 * @Description查询主机类型下拉框
	 */
	public void selectRmHostTypeSel() throws Exception{
		resultLst = rmNwUseService.selectRmHostTypeSel();
		this.arrayOut(resultLst);
	}
}
