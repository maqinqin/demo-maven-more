package com.git.cloud.sys.dao;

import java.util.List;

import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysMenuRolePo;
import com.git.cloud.sys.model.po.SysRolePo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.po.SysUserRole;

/**
  * @ClassName: ISysRoleDAO
  * @Description: TODO
  * @author guojianjun
  * @date 2014-12-17 下午2:58:24
  *
  */
public interface ISysRoleDAO extends ICommonDAO{

	public Pagination<SysRolePo> getSysRolePagination(PaginationParam paginationParam) throws RollbackableBizException;

	public SysRolePo findSysRoleByRoleId(String roleId) throws RollbackableBizException;

	public void saveSysRolePo(SysRolePo sysRolePo) throws RollbackableBizException;

	public void updateSysRolePo(SysRolePo sysRolePo) throws RollbackableBizException;

	public void deleteSysRolePoByRoleId(String[] ids) throws RollbackableBizException;

	public int findRoleNumByRoleName(SysRolePo sysRolePo)throws RollbackableBizException;

	public void deleteSysMenuRolePoByRoleId(String roleId)throws RollbackableBizException;

	public void saveSysMenuRolePo(SysMenuRolePo sysMenuRolePo)throws RollbackableBizException;

	public List<SysMenuPo> findSysMenuPoByRoleId(String roleId)throws RollbackableBizException;

	public Pagination<SysUserPo> findSysUserPagination(PaginationParam paginationParam)throws RollbackableBizException;

	public List<SysUserPo> findSysUserByRoleId(String roleId)throws RollbackableBizException;

	/**
	 * 进行角色删除时查询是否被删除角色与用户有关系数据
	 */
	public List<SysUserRole> findSysUserRoleByRoleId(String roleId)throws RollbackableBizException;
}
