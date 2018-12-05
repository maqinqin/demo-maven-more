package com.git.cloud.sys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.model.ZtreeIconEnum;
import com.git.cloud.sys.model.po.SysOrganizationPo;
import com.git.cloud.sys.service.SysOrganizationService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 组织机构管理
 * 
 * @ClassName: SysOrganizationAction
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class SysOrganizationAction extends ActionSupport {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = -8783973185595308634L;
	private SysOrganizationPo sysOrganizationPo;
	private SysOrganizationService SysOrganizationService;
	private Map<String, Object> result;
	private List<Object> list;

	public String toMain() {
		return SUCCESS;
	}

	public String isrepeat() {
		Map<String, String> map = new HashMap<String, String>();
		if (sysOrganizationPo != null && StringUtils.isNotEmpty(sysOrganizationPo.getOrgName()) && !"".equals(sysOrganizationPo.getOrgName())) {
			map.put("orgName", sysOrganizationPo.getOrgName());
		}
		if (sysOrganizationPo != null && StringUtils.isNotEmpty(sysOrganizationPo.getOrgId()) && !"".equals(sysOrganizationPo.getOrgId())) {
			map.put("orgId", sysOrganizationPo.getOrgId());
		}
		if(sysOrganizationPo != null && StringUtils.isNotEmpty(sysOrganizationPo.getParent().getOrgId()) && !"".equals(sysOrganizationPo.getParent().getOrgId())){
			map.put("parentId", sysOrganizationPo.getParent().getOrgId());
		}else{
			map.put("parentId", "0");
		}
		Boolean flag = SysOrganizationService.isrepeat(map);
		result = new HashMap<String, Object>();
		result.put("code", "success");
		result.put("data", !flag);
		return SUCCESS;
	}

	public String load() {
		try {
			if (sysOrganizationPo != null && sysOrganizationPo.getOrgId() != null)
				sysOrganizationPo = SysOrganizationService.load(sysOrganizationPo.getOrgId());
			result = new HashMap<String, Object>();
			result.put("code", "success");
			result.put("data", sysOrganizationPo);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return SUCCESS;
	}

	public String loadTree() {
		try {
			List<?> list1 = this.getSysOrganizationService().loadTree();
			list = new ArrayList<Object>();
			if (list1 != null) {
				for (Object o : list1) {
					Map<String, String> map = new HashMap<String, String>();
					SysOrganizationPo m = (SysOrganizationPo) o;
					map.put("id", m.getOrgId());
					if (m.getParent() != null && StringUtils.isNotEmpty(m.getParent().getOrgId())) {
						map.put("pid", m.getParent().getOrgId());
					} else {
						map.put("pid", "0");
					}
					map.put("icon", ZtreeIconEnum.ORGAN.getIcon());
					map.put("name", m.getOrgName());
					list.add(map);
				}
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}

		return SUCCESS;
	}

	public String save() {
		try {
			if (sysOrganizationPo != null && sysOrganizationPo.getOrgId() != null) {
				sysOrganizationPo = this.getSysOrganizationService().update(sysOrganizationPo);
			} else {
				sysOrganizationPo = this.getSysOrganizationService().insert(sysOrganizationPo);
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return SUCCESS;
	}

	public String delete() {
		try {
			this.getSysOrganizationService().delete(sysOrganizationPo.getOrgId());
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return SUCCESS;
	}

	public SysOrganizationPo getSysOrganizationPo() {
		return sysOrganizationPo;
	}

	public void setSysOrganizationPo(SysOrganizationPo sysOrganizationPo) {
		this.sysOrganizationPo = sysOrganizationPo;
	}

	public SysOrganizationService getSysOrganizationService() {
		return SysOrganizationService;
	}

	public void setSysOrganizationService(SysOrganizationService SysOrganizationService) {
		this.SysOrganizationService = SysOrganizationService;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}
}
