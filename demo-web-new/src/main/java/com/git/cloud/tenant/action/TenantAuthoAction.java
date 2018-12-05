package com.git.cloud.tenant.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.JsonVo;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.tenant.model.po.TenantPo;
import com.git.cloud.tenant.model.po.TenantResPoolPo;
import com.git.cloud.tenant.model.po.TenantUsersPo;
import com.git.cloud.tenant.service.ITenantAuthoService;

@RequestMapping(value="/tenant")
@Controller
public class TenantAuthoAction extends BaseAction{
	
	private static final long serialVersionUID = 2952762352435567091L;
	private Logger logger = LoggerFactory.getLogger(TenantAuthoAction.class);
	
	@Autowired
	private ITenantAuthoService tenantService;
	
	public ITenantAuthoService getTenantService() {
		return tenantService;
	}

	public void setTenantService(ITenantAuthoService tenantService) {
		this.tenantService = tenantService;
	}

	
	
	/**
	 * 根据租户Id获取租户用户信息列表 以及获取无关联的租户信息
	 * @return
	 */
	@RequestMapping(value="/user/list")
	@ResponseBody
	public Pagination<TenantUsersPo> getTenantAndNotTenantUsersByTenantId(HttpServletRequest request){		
		logger.info("get users which are belongs tenant or not belongs tenant By tenantId begin");	
		Pagination<TenantUsersPo> pagination= null;
		try {			
			pagination =  tenantService.selectTenantAndNotTenantUsersByTenantId(this.getPaginationParamByReq(request));
			logger.info("get users which are belongs tenant or not belongs tenant By tenantId succeeded");
			
		} catch (RollbackableBizException e) {			
			logger.error("get users which are belongs tenant or not belongs tenant By tenantId failed",e);
		} catch (Exception e) {
			logger.error("return users which are belongs tenant or not belongs tenant By tenantId failed",e);			
		}
		return pagination;	
					
	}

	/**
	 * 更新租户用户信息列表 以及删除无关联的租户信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/user/add")
	@ResponseBody
	public JsonVo addTenantAndUserRelation(HttpServletRequest request) {
		logger.info("update relations about tenant with users begin");
		JsonVo jsonVo = new JsonVo();
		String tenantId = request.getParameter("tenantId");
		String dataList = request.getParameter("dataList");
		List<TenantUsersPo> list = JSON.parseArray(dataList, TenantUsersPo.class);

		try {
			tenantService.addTenantAndUserRelation(tenantId, list);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("更新租户用户信息列表 以及删除无关联的租户信息成功");
			logger.info("update relations about tenant with users succeeded");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("更新租户用户信息列表 以及删除无关联的租户信息失败");
			logger.error("update relations about tenant with users  failed", e);
		} catch (RuntimeException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg(e.getMessage());
			logger.error("update relations about tenant with users  failed", e);
		}
		return jsonVo;
	}
	/**
	 * 添加租户
	 * @param tenantPo
	 * @return
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public JsonVo addTenant(TenantPo tenantPo){
		JsonVo jsonVo = new JsonVo();
		logger.info("add tenant begin");
		try {
			tenantService.addTenant(tenantPo);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("添加租户成功");			
			logger.info("add tenant succeeded");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("添加租户失败");			
			logger.error("add tenant failed",e);
		}		
		return jsonVo;
	}
	
	/**
	 * 修改租户
	 * @param tenantPo
	 * @return
	 */
	@RequestMapping(value="/update")
	@ResponseBody
	public JsonVo updateTenant(TenantPo tenantPo) {
		JsonVo jsonVo = new JsonVo();
		logger.info("updata tenant begin");
		try {
			tenantService.updateTenant(tenantPo);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("修改租户成功");			
			logger.info("update tenant succeeded");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("修改租户失败");			
			logger.error("update tenant failed",e);
		}		
		return jsonVo;
	}
	/**
	 * 删除租户
	 * @param tenantPo
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public JsonVo deleteTenant(String tenantId) {
		JsonVo jsonVo = new JsonVo();
		logger.info("delete tenant begin");
		try {
			boolean result = tenantService.deleteTenant(tenantId);
			if(result) {
				jsonVo.setSuccess(true);
				jsonVo.setMsg("删除租户成功");			
				logger.info("delete tenant succeeded");
			}else {
				jsonVo.setSuccess(false);
				jsonVo.setMsg("租户下面含有相关资源，不能删除租户");			
				logger.error("somethings are used the tenant,delete tenant failed");
			}			
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("删除租户失败");
			logger.error("delete tenant failed",e);
		} catch (RuntimeException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg(e.getMessage());
			logger.error("delete tenant failed",e);
		}
		return jsonVo;
	}
	/**
	 * 查询租户
	 * @param tenantId
	 * @return
	 */
	@RequestMapping(value="/selectTenant")
	@ResponseBody
	public JsonVo selectTenant(String tenantId) {
		JsonVo jsonVo = new JsonVo();
		logger.info("query tenant begin");
		try {
			TenantPo tenantPo = tenantService.selectTenant(tenantId);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("查询租户成功");
			jsonVo.setObj(tenantPo);
			logger.info("query tenant succeeded");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("查询租户失败");			
			logger.error("query tenant failed",e);
		}		
		return jsonVo;
	}
	/**
	 * 查询租户列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Pagination<TenantPo> list(HttpServletRequest request) {		
		logger.info("query all tenants with pages begin");
		Pagination<TenantPo> pagination = null;
		try {
			 pagination = tenantService.selectTenants(getPaginationParamByReq(request));
			logger.info("query all tenants with pages  succeeded ");
		} catch (RollbackableBizException e) {
			logger.error("query all tenants with pages failed",e);
		}			
		return pagination;
	}
	@RequestMapping(value="/getTenants")
	@ResponseBody
	public List getTenants(){
		logger.info("query all tenants begin");
		List<TenantPo> selectTenantWithoutLimit = null;
		try {
			selectTenantWithoutLimit = tenantService.selectTenantWithoutLimit();
			logger.info("query all tenants succeeded");
		} catch (RollbackableBizException e) {
			logger.error("query all tenants failed",e);
		}
		return selectTenantWithoutLimit;
	}
	
	
	
	
	/**
	 * 更新租户资源池信息列表
	 * @return
	 */
	@RequestMapping(value="/respools")
	@ResponseBody
	public JsonVo addTenantAndResPoolRelation(HttpServletRequest request){		
		JsonVo jsonVo = new JsonVo();
		String tenantId = request.getParameter("tenantId");
		String dataList = request.getParameter("dataList");	
		List<TenantResPoolPo> list = JSON.parseArray(dataList, TenantResPoolPo.class);
		try {
			tenantService.addTenantAndResPoolRelation(tenantId,list);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("更新租户资源池信息成功");
			logger.info("update relations about tenant with pools succeeded");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("更新租户资源池关联关系失败");			
			logger.error("update relations about tenant with pools failed",e);
		} catch (RuntimeException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg(e.getMessage());
			logger.error("update relations about tenant with pools failed",e);
		}
		return jsonVo;				
	}
	
	@RequestMapping(value="/respools/list")
	@ResponseBody
	public Pagination<TenantResPoolPo> getResPoolsRelationByTenantId(HttpServletRequest request){		
		Pagination<TenantResPoolPo> pagination= null;
		try {			
			pagination =  tenantService.getResPoolsRelationByTenantId(this.getPaginationParamByReq(request));
			
		} catch (RollbackableBizException e) {			
		} catch (Exception e) {
		}
		return pagination;	
	}
}
