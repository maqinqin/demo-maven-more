package com.git.cloud.sys.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.service.IService;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysRolePo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.vo.SysRoleVo;

/**
  * @ClassName: ISysRoleService
  * @Description: TODO
  * @author guojianjun
  * @date 2014-12-17 下午2:59:34
  *
  */
public interface ISysRoleService extends IService {
	public Pagination<SysRolePo> getSysRolePagination(PaginationParam paginationParam) throws RollbackableBizException;

	public SysRolePo findSysRoleByRoleId(String roleId) throws RollbackableBizException;

	public String saveSysRolePo(SysRolePo sysRolePo) throws RollbackableBizException, Exception;

	public String updateSysRolePo(SysRolePo sysRolePo) throws RollbackableBizException, Exception;

	public String deleteSysRolePoByRoleId(String[] ids) throws RollbackableBizException, Exception;

	public String saveAuthorization(SysRoleVo sysRoleVo)throws RollbackableBizException, Exception;

	public List<SysMenuPo> findSysMenuPoByRoleId(String roleId)throws RollbackableBizException, Exception;

	public Pagination<SysUserPo> findSysUserPagination(PaginationParam paginationParam)throws RollbackableBizException;

	public List<SysUserPo> findSysUserByRoleId(String roleId)throws RollbackableBizException;

}
