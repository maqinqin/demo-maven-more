package com.git.cloud.sys.service;

import java.util.List;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysRolePo;
import com.git.cloud.sys.model.po.SysUserLimitPo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.po.SysUserRole;
import com.git.cloud.sys.model.vo.AppInfoVo;
import com.git.cloud.sys.model.vo.SysRolemManageVo;
import com.git.cloud.sys.model.vo.SysUserVo;

/**
 * 用户管理
 * @ClassName:IUserService
 * @Description:TODO
 * @author
 * @date 2014-11-27 下午3:57:17
 */
public interface IUserService {
	public String saveUser(SysUserPo sysUserPo,String loginNameOld) throws RollbackableBizException;
	public String updateUser(SysUserPo sysUserPo,String loginNameOld,String loginPassOld) throws RollbackableBizException;
	public String updateUserPassword(String userId,String passWord) throws RollbackableBizException;
	public String deleteUser(String userId) throws RollbackableBizException;
	public SysUserVo findUserById(String userId) throws RollbackableBizException;
	public SysUserPo findUserPoById(String userId) throws RollbackableBizException;
	public Pagination<SysUserPo> findUserPage(PaginationParam paginationParam) throws RollbackableBizException;
	public List<CommonTreeNode> initAddSysRoleTree(String userId) throws RollbackableBizException;
	public List<CommonTreeNode> initAddSysRoleNoTree(String userId) throws RollbackableBizException;
	public List<SysRolemManageVo> initRadio(String userId) throws RollbackableBizException;
	public void saveRoleList(List<SysUserRole> roleList) throws RollbackableBizException;
	public void deleteRoleList(String userId,String roleId) throws RollbackableBizException;
	public Pagination<SysRolePo> findSysRolePagination(PaginationParam paginationParam) throws RollbackableBizException;
	public List<SysMenuPo> findResourceByLoginInfo(String userId,String loginName,String resourceType,String parentId) throws RollbackableBizException;
	public List<SysUserVo> findUserByOrgId(String orgId) throws RollbackableBizException;
	public String findUserNameOnline();
	public String saveRole(SysRolemManageVo sysRolemManageVo) throws RollbackableBizException;
	public void deleteRloeManage(String userId,String appInfoId) throws RollbackableBizException;
	public List<AppInfoVo> findUserRoleResult(String userId) throws RollbackableBizException;
	public List<CommonTreeNode> initCloudServiceTree(String userId) throws RollbackableBizException;
	public List<CommonTreeNode> initCloudServiceNoTree(String userId) throws RollbackableBizException;
	public void deleteCloudService(String userId, String id) throws RollbackableBizException;
	public String saveCloudService(SysRolemManageVo sysRolemManageVo) throws RollbackableBizException;
}
