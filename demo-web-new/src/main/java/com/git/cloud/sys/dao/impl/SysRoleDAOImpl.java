package com.git.cloud.sys.dao.impl;

import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.sys.dao.ISysRoleDAO;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysMenuRolePo;
import com.git.cloud.sys.model.po.SysRolePo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.po.SysUserRole;

/**
  * @ClassName: SysRoleDAOImpl
  * @Description: 角色管理
  * @author guojianjun
  * @date 2014-12-17 下午2:58:36
  *
  */
public class SysRoleDAOImpl extends CommonDAOImpl implements ISysRoleDAO{
	
	@Override
	public Pagination<SysRolePo> getSysRolePagination(
			PaginationParam paginationParam) throws RollbackableBizException {
		return pageQuery("findSysRoleTotal", "findSysRolePage", paginationParam);
	}

	@Override
	public SysRolePo findSysRoleByRoleId(String roleId)
			throws RollbackableBizException {
		SysRolePo sysRolePo = super.findObjectByID("findSysRolePoByRoleId", roleId);
		return sysRolePo;
	}

	@Override
	public void saveSysRolePo(SysRolePo sysRolePo)
			throws RollbackableBizException {
		super.save("saveSysRolePo", sysRolePo);
	}

	@Override
	public void updateSysRolePo(SysRolePo sysRolePo)
			throws RollbackableBizException {
		super.update("updateSysRolePo", sysRolePo);
		
	}

	@Override
	public void deleteSysRolePoByRoleId(String[] ids)
			throws RollbackableBizException {
		for(String id : ids){
			super.delete("deleteSysRolePoByRoleId", id);
		}
	}

	@Override
	public int findRoleNumByRoleName(SysRolePo sysRolePo)
			throws RollbackableBizException {
		List count = getSqlMapClientTemplate().queryForList("findRoleNumByRoleName", sysRolePo);
		return (Integer) count.get(0);
	}

	@Override
	public void deleteSysMenuRolePoByRoleId(String roleId)
			throws RollbackableBizException {
		super.delete("deleteSysMenuRolePoByRoleId", roleId);
	}

	@Override
	public void saveSysMenuRolePo(SysMenuRolePo sysMenuRolePo)
			throws RollbackableBizException {
		super.save("saveSysMenuRolePo", sysMenuRolePo);
	}

	@Override
	public List<SysMenuPo> findSysMenuPoByRoleId(String roleId)
			throws RollbackableBizException {
		List<SysMenuPo> list = super.findByID("findSysMenuPoByRoleId", roleId);
		return list;
	}

	@Override
	public Pagination<SysUserPo> findSysUserPagination(
			PaginationParam paginationParam) throws RollbackableBizException {
		return pageQuery("findRoleUserTotal", "findRoleUserPage", paginationParam);
	}

	@Override
	public List<SysUserPo> findSysUserByRoleId(String roleId)
			throws RollbackableBizException {
		List<SysUserPo> list = super.findByID("findSysUserByRoleId", roleId);
		return list;
	}

	/**
	 * 进行角色删除时查询是否被删除角色与用户有关系数据
	 */
	@Override
	public List<SysUserRole> findSysUserRoleByRoleId(String roleId)
			throws RollbackableBizException {
		List<SysUserRole> list = super.findByID("findSysUserRoleByRoleId", roleId);
		return list;
	}




}
