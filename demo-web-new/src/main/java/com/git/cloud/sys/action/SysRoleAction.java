package com.git.cloud.sys.action;

import java.util.List;
import java.util.Map;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.sys.model.po.SysMenuRolePo;
import com.git.cloud.sys.model.po.SysRolePo;
import com.git.cloud.sys.model.vo.SysRoleVo;
import com.git.cloud.sys.service.ISysRoleService;
import com.google.common.collect.Maps;

/**
  * @ClassName: SysRoleAction
  * @Description: 角色管理
  * @author guojianjun
  * @date 2014-12-17 下午2:57:50
  *
  */
public class SysRoleAction extends BaseAction{
	private static final long serialVersionUID = -2339663084679105606L;
	private ISysRoleService sysRoleService;
	private SysRoleVo sysRoleVo;
	private SysRolePo sysRolePo;

	public String sysRoleView(){
		return SUCCESS;
	}
	/**
	 * 查询所有的role信息，分页展示到前台
	 * 
	 * @throws Exception
	 */
	public void getSysRoleList() throws Exception {
		this.jsonOut(sysRoleService.getSysRolePagination(this.getPaginationParam()));
	}
	
	public void findSysRoleById() throws Exception{
		sysRolePo = sysRoleService.findSysRoleByRoleId(sysRolePo.getRoleId());
		this.jsonOut(sysRolePo);
	}
	
	public void saveSysRolePo() throws Exception{
		String rtnMsg = sysRoleService.saveSysRolePo(sysRolePo);
		Map<String, String> map = Maps.newHashMap();
		map.put("rtnMsg", rtnMsg);
		jsonOut(map);
	}
	
	public void updateSysRolePo() throws Exception{
		String rtnMsg = sysRoleService.updateSysRolePo(sysRolePo);
		Map<String, String> map = Maps.newHashMap();
		map.put("rtnMsg", rtnMsg);
		jsonOut(map);
	}
	
	public void deleteSysRolePoByRoleId() throws Exception{
		
		String rtnMsg = sysRoleService.deleteSysRolePoByRoleId(sysRolePo.getRoleId().split(","));
		Map<String, String> map = Maps.newHashMap();
		map.put("rtnMsg", rtnMsg);
		jsonOut(map);
	}
	
	public void saveAuthorization() throws Exception{
		String rtnMsg = sysRoleService.saveAuthorization(sysRoleVo);
		Map<String, String> map = Maps.newHashMap();
		map.put("rtnMsg", rtnMsg);
		jsonOut(map);
	}
	
	/**
	 * 查询指定角色下用户列表
	 * @throws Exception
	 */
	public void findSysUserList() throws Exception {
		this.jsonOut(sysRoleService.findSysUserPagination(this.getPaginationParam()));
	}

	public ISysRoleService getSysRoleService() {
		return sysRoleService;
	}

	public void setSysRoleService(ISysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	public SysRoleVo getSysRoleVo() {
		return sysRoleVo;
	}

	public void setSysRoleVo(SysRoleVo sysRoleVo) {
		this.sysRoleVo = sysRoleVo;
	}

	public SysRolePo getSysRolePo() {
		return sysRolePo;
	}

	public void setSysRolePo(SysRolePo sysRolePo) {
		this.sysRolePo = sysRolePo;
	}
	
}
