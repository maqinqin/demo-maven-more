package com.git.cloud.sys.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.model.ZtreeIconEnum;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.service.ISysRoleService;
import com.git.cloud.sys.service.SysMenuService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 菜单管理
 * 
 * @ClassName: SysMenuAction
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class SysMenuAction extends ActionSupport {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = -8783973185595308634L;
	private SysMenuPo sysMenuPo;
	private SysMenuService SysMenuService;
	private Map<String, Object> result;
	private List<Object> list;
	private String roleId;
	private ISysRoleService sysRoleService;

	public String toMain() {
		return SUCCESS;
	}

	public String isrepeat() {
		Map<String, String> map = new HashMap<String, String>();
		if (sysMenuPo != null && StringUtils.isNotEmpty(sysMenuPo.getMenuName()) && !"".equals(sysMenuPo.getMenuName())) {
			map.put("menuName", sysMenuPo.getMenuName());
		}
		if (sysMenuPo != null && StringUtils.isNotEmpty(sysMenuPo.getId()) && !"".equals(sysMenuPo.getId())) {
			map.put("menuId", sysMenuPo.getId());
		}
		Boolean flag = SysMenuService.isrepeat(map);
		result = new HashMap();
		result.put("code", "success");
		result.put("data", !flag);

		return SUCCESS;
	}

	/**
	 * 查詢包含指定菜单的角色
	 * 
	 * @return
	 */
	public String roles() {
		try {
			Subject subject = SecurityUtils.getSubject();
			SysUserPo sysUserPo = (SysUserPo) subject.getPrincipal();
			
			if (sysMenuPo != null && sysMenuPo.getId() != null)
				list = SysMenuService.roles(sysMenuPo.getId());
			result = new HashMap<String, Object>();
			result.put("code", "success");
			result.put("data", list);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return SUCCESS;
	}

	public String load() {
		try {
			if (sysMenuPo != null && sysMenuPo.getId() != null)
				sysMenuPo = SysMenuService.load(sysMenuPo.getId());
			result = new HashMap<String, Object>();
			result.put("code", "success");
			result.put("data", sysMenuPo);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return SUCCESS;
	}

	public String loadTree() {
		try {
			List<?> list1 = this.getSysMenuService().loadTree();
			list = new ArrayList<Object>();
			if (list1 != null) {
				for (Object o : list1) {
					Map<String, String> map = new HashMap<String, String>();
					SysMenuPo m = (SysMenuPo) o;
					map.put("id", m.getId());
					if (m.getParent() != null && !"0".equals(m.getParent().getId()) && "menu".equals(m.getResourceType())) {
						map.put("icon", ZtreeIconEnum.TWOLEVELMENU.getIcon());
						map.put("pid", m.getParent().getId());
					} else if (m.getParent() != null && "function".equals(m.getResourceType())) {
						map.put("icon", ZtreeIconEnum.FUNCTION.getIcon());
						map.put("pid", m.getParent().getId());
					} else if (m.getParent() != null && "0".equals(m.getParent().getId()) && "menu".equals(m.getResourceType())) {

						map.put("icon", ZtreeIconEnum.ONELEVELMENU.getIcon());
						map.put("pid", m.getParent().getId());
					}
					map.put("name", m.getMenuName());
					list.add(map);
				}
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}

		return SUCCESS;
	}

	public String loadtreeNew() {
		try {
			List<SysMenuPo> list1 = sysRoleService.findSysMenuPoByRoleId(getRoleId());

			list = new ArrayList<Object>();
			if (list1 != null) {
				for (Object o : list1) {
					Map<String, String> map = new HashMap<String, String>();
					SysMenuPo m = (SysMenuPo) o;
					map.put("id", m.getId());
					if (m.getParent() != null && !"0".equals(m.getParent().getId()) && "menu".equals(m.getResourceType())) {
						map.put("icon", ZtreeIconEnum.TWOLEVELMENU.getIcon());
						map.put("pid", m.getParent().getId());
					} else if (m.getParent() != null && "function".equals(m.getResourceType())) {
						map.put("icon", ZtreeIconEnum.FUNCTION.getIcon());
						map.put("pid", m.getParent().getId());
					} else if (m.getParent() != null && "0".equals(m.getParent().getId()) && "menu".equals(m.getResourceType())) {

						map.put("icon", ZtreeIconEnum.ONELEVELMENU.getIcon());
						map.put("pid", m.getParent().getId());
					}

					Boolean checked = m.getChecked();
					String menuId = m.getMenuId();
					if (menuId != null || checked == true) {
						map.put("checked", "true");
						// map.put("open", "true");

						// if (m.getParent() != null){
						// m.getParent().setChecked(true);
						// }
					} else {
						map.put("checked", "false");
						// map.put("open", "false");
					}
					map.put("name", m.getMenuName());

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
			if (sysMenuPo != null && sysMenuPo.getId() != null && !"".equals(sysMenuPo.getId())) {
				sysMenuPo = this.getSysMenuService().update(sysMenuPo);
			} else {
				sysMenuPo = this.getSysMenuService().insert(sysMenuPo);
			}
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return SUCCESS;
	}

	public String delete() {
		try {
			this.getSysMenuService().delete(sysMenuPo.getId());
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return SUCCESS;
	}

	public SysMenuPo getSysMenuPo() {
		return sysMenuPo;
	}

	public void setSysMenuPo(SysMenuPo sysMenuPo) {
		this.sysMenuPo = sysMenuPo;
	}

	public SysMenuService getSysMenuService() {
		return SysMenuService;
	}

	public void setSysMenuService(SysMenuService SysMenuService) {
		this.SysMenuService = SysMenuService;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public ISysRoleService getSysRoleService() {
		return sysRoleService;
	}

	public void setSysRoleService(ISysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

}
