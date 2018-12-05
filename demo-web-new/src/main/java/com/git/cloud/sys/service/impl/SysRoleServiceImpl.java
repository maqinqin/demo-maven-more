package com.git.cloud.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.shiro.realm.ShiroDbRealm;
import com.git.cloud.sys.dao.ISysRoleDAO;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysMenuRolePo;
import com.git.cloud.sys.model.po.SysRolePo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.po.SysUserRole;
import com.git.cloud.sys.model.vo.SysRoleVo;
import com.git.cloud.sys.service.ISysRoleService;
/**
 * 
  * @ClassName: SysRoleServiceImpl
  * @Description: 角色管理
  * @author guojianjun
  *
 */
public class SysRoleServiceImpl implements ISysRoleService {

	private ISysRoleDAO sysRoleDAO;
	/**
	 * 角色管理列表信息
	 */
	@Override
	public Pagination<SysRolePo> getSysRolePagination(PaginationParam paginationParam) throws RollbackableBizException {
		return sysRoleDAO.getSysRolePagination(paginationParam);
	}

	public ISysRoleDAO getSysRoleDAO() {
		return sysRoleDAO;
	}

	public void setSysRoleDAO(ISysRoleDAO sysRoleDAO) {
		this.sysRoleDAO = sysRoleDAO;
	}

	@Override
	public SysRolePo findSysRoleByRoleId(String roleId)
			throws RollbackableBizException {
		SysRolePo sysRolePo = sysRoleDAO.findSysRoleByRoleId(roleId);
		return sysRolePo;
	}

	/**
	 * 保存角色信息
	 */
	@Override
	public String saveSysRolePo(SysRolePo sysRolePo)
			throws RollbackableBizException, Exception {
		int roleNum = 0;
		String rtnMsg = "";
		
		roleNum = sysRoleDAO.findRoleNumByRoleName(sysRolePo);
		if(roleNum > 0){
			rtnMsg = "新增的角色名已存在!";
		}else{
			sysRolePo.setRoleId(UUIDGenerator.getUUID());
			sysRolePo.setIsActive(IsActiveEnum.YES.getValue());
			SysUserPo createUser = (SysUserPo)SecurityUtils.getSubject().getPrincipal();
			sysRolePo.setCreateUser(createUser.getLoginName());
			sysRoleDAO.saveSysRolePo(sysRolePo);
		}
		return rtnMsg;
	}

	/**
	 * 修改角色信息
	 */
	@Override
	public String updateSysRolePo(SysRolePo sysRolePo) throws RollbackableBizException,
			Exception {
		String rtnMsg = "";
		SysUserPo updateUser = (SysUserPo)SecurityUtils.getSubject().getPrincipal();
		sysRolePo.setUpdateUser(updateUser.getLoginName());
		sysRoleDAO.updateSysRolePo(sysRolePo);
		return rtnMsg;
	}

	/**
	 * 删除角色信息
	 */
	@Override
	public String deleteSysRolePoByRoleId(String[] ids)
			throws RollbackableBizException, Exception {
		
		String rtnMsg = "";
		boolean flag = false;
		List<SysUserRole> list = new ArrayList();
		//进行角色删除时查询是否被删除角色与用户有关系数据
		for(String id : ids){
			list = sysRoleDAO.findSysUserRoleByRoleId(id);
			if(list.size()>0){
				flag = true;
				rtnMsg = "角色已被授于用户,有关联数据,不能删除!";
				break;
			}
		}
		if(!flag){
			//删除角色
			sysRoleDAO.deleteSysRolePoByRoleId(ids);
			//删除角色与菜单对应表数据
			for(String id : ids){
				//删除原有的授权
				sysRoleDAO.deleteSysMenuRolePoByRoleId(id);
			}
			RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();  
			ShiroDbRealm shiroDbRealm = (ShiroDbRealm)securityManager.getRealms().iterator().next();  
			//清除所有用户授权信息缓存
			shiroDbRealm.clearAllCachedAuthorizationInfo();
		}
		return rtnMsg;
	}

	/**
	 * 保存角色已关联到的菜单
	 */
	@Override
	public String saveAuthorization(SysRoleVo sysRoleVo)
			throws RollbackableBizException, Exception {
		String rtnMsg = "";
		String roleMenus = sysRoleVo.getRoleMenus();
		String [] menus;
		
		if(!roleMenus.trim().equals("")){
			//删除原有的授权
			sysRoleDAO.deleteSysMenuRolePoByRoleId(sysRoleVo.getRoleId());
			
			//新增菜单授权
			menus = roleMenus.split(",");
			for(String menuId:menus){
				SysMenuRolePo sysMenuRolePo = new SysMenuRolePo(sysRoleVo.getRoleId(),menuId);
				sysRoleDAO.saveSysMenuRolePo(sysMenuRolePo);
			}
		}else{
			//删除原有的授权
			sysRoleDAO.deleteSysMenuRolePoByRoleId(sysRoleVo.getRoleId());
		}
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();  
		ShiroDbRealm shiroDbRealm = (ShiroDbRealm)securityManager.getRealms().iterator().next();  
		//更新用户授权信息缓存.
//		shiroDbRealm.clearCachedAuthorizationInfo((String)SecurityUtils.getSubject().getPrincipal());
		//清除所有用户授权信息缓存
		shiroDbRealm.clearAllCachedAuthorizationInfo();
		return rtnMsg;
	}
	/**
	 * 根据roleId 查询到角色已关联到的菜单
	 */
	@Override
	public List<SysMenuPo> findSysMenuPoByRoleId(String roleId)
			throws RollbackableBizException, Exception {
		return sysRoleDAO.findSysMenuPoByRoleId(roleId);
	}
	
	/**
	 * 根据角色得到用户列表
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	@Override
	public Pagination<SysUserPo> findSysUserPagination(PaginationParam paginationParam) throws RollbackableBizException {
		return sysRoleDAO.findSysUserPagination(paginationParam);
	}
	/**
	 * 根据角色得到用户列表
	 * @return
	 * @throws RollbackableBizException
	 */
	@Override
	public List<SysUserPo> findSysUserByRoleId(String roleId) throws RollbackableBizException {
		return sysRoleDAO.findSysUserByRoleId(roleId);
	}
	/**
	 * 进行角色删除时查询是否被删除角色与用户有关系数据
	 */
	public List<SysUserRole> findSysUserRoleByRoleId(String roleId)
			throws RollbackableBizException, Exception {
		return sysRoleDAO.findSysUserRoleByRoleId(roleId);
	}
	
}
