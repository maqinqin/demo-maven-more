package com.git.cloud.tenant.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.JsonVo;
import com.git.cloud.request.dao.IBmSrDao;
import com.git.cloud.resmgt.common.model.po.CmVmPo;
import com.git.cloud.resmgt.common.service.ICmVmService;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.vo.SysUserVo;
import com.git.cloud.sys.service.IUserService;
import com.git.cloud.tenant.model.po.QuotaPo;
import com.git.cloud.tenant.service.ITenantQuotaService;

/**
 * 租户配额
 * @author
 */
@RequestMapping(value="/quota")
@Controller
public class TenantQuotaAction extends BaseAction<Object>{	

	/**
	 * 
	 */
	private static final long serialVersionUID = -901109391248414421L;
	private Logger logger = LoggerFactory.getLogger(TenantQuotaAction.class);
	@Autowired
	private ITenantQuotaService tenantQuotaService;
	@Autowired
	private IBmSrDao bmSrDao;
	@Autowired
	private IUserService userService;
	
	
	/**
	 * 租户配额管理，验证是否有授权资源池
	 */
	@RequestMapping(value="/quotaconfig/queryResPoolByTenantId")
	@ResponseBody
	public JsonVo getResPoolByTenantId(String tenantId) {
		//logger.info("get quotaConfig begin");
		JsonVo jsonVo = new JsonVo();
		Map<String, List> map =null;
		try {
			String list = tenantQuotaService.getResPoolByTenantId(tenantId);			
			jsonVo.setSuccess(true);
			jsonVo.setMsg("验证资源池是否授权成功");
			jsonVo.setObj(list);
			//logger.info("get quotaConfig succeeded.");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("验证资源池是否授权失败");
			jsonVo.setObj(map);
			//logger.error("get quotaConfig failed");
		}
		return jsonVo;	
	}
	
	
	/**
	 * 获取配额指标
	 * @return
	 */
	@RequestMapping(value="/quotaconfig/list")
	@ResponseBody
	public JsonVo getQuotaConfigInfo(String tenantId){
		logger.info("get quotaConfig begin");
		JsonVo jsonVo = new JsonVo();
		Map<String, List> map =null;
		try {
			map = tenantQuotaService.getQuotaConfigInfo(tenantId);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("获取配额指标成功");
			jsonVo.setObj(map);
			logger.info("get quotaConfig succeeded.");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("获取配额指标失败");
			jsonVo.setObj(map);
			logger.error("get quotaConfig failed");
		}
		return jsonVo;		
	}
	
	/**
	 * 根据租户id获取租户配额
	 * @return JsonVo
	 */
	@RequestMapping(value="/{tenantId}/quota/list")
	@ResponseBody
	public JsonVo getQuotaInfo(@PathVariable(value="tenantId") String tenantId){
		logger.info("get quota by tenantId begin");
		JsonVo jsonVo = new JsonVo();
		List<QuotaPo> list =  null;
		try {
			list = tenantQuotaService.selectQuotaByTenantId(tenantId);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("获取租户id获取租户配额成功");
			jsonVo.setObj(list);
			logger.info("get quota by tenantId succeeded");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("获取租户id获取租户配额失败");
			jsonVo.setObj(list);
			logger.error("get quota by tenantId failed",e);
		}
		return jsonVo;
	}
	/**
	 * 添加租户配额
	 * @return JsonVo
	 */
	@RequestMapping(value="/quota/save")
	@ResponseBody
	public JsonVo addQuota(HttpServletRequest request) throws Exception {
		logger.info("add quota");
		String quotasStr = request.getParameter("quotasStr");
		String tenantId = request.getParameter("tenantId");
		JsonVo jsonVo = new JsonVo();
		List<QuotaPo> list = JSON.parseArray(quotasStr, QuotaPo.class);
		try {
			tenantQuotaService.addQuota(tenantId,list);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("添加租户配额");	
			logger.info("add quota succeeded");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("添加租户配额失败");
			logger.error("add quota failed", e);
		} catch (RuntimeException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg(e.getMessage());
			logger.error("add quota failed", e);
		} catch (Exception e) {
			throw e;
		}
		return jsonVo;
	}
	/**
	 * 更改配额
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/quota/update")
	@ResponseBody
	public JsonVo updateQuota(HttpServletRequest request) throws Exception {
		logger.info("updata quota begin");
		String quotasStr = request.getParameter("quotasStr");
		String tenantId = request.getParameter("tenantId");
		JsonVo jsonVo = new JsonVo();
		List<QuotaPo> list = JSON.parseArray(quotasStr, QuotaPo.class);
		try {
			tenantQuotaService.updateQuota(tenantId,list);
			jsonVo.setSuccess(true);
			jsonVo.setMsg("修改租户配额成功");
			logger.info("updata quota succeeded");
		} catch (RollbackableBizException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg("修改租户配额失败");			
			logger.error("updata quota failed",e);
		} catch (RuntimeException e) {
			jsonVo.setSuccess(false);
			jsonVo.setMsg(e.getMessage());
			logger.error("add quota failed", e);
		} catch (Exception e) {
			throw e;
		}

		return jsonVo;
	}
}
