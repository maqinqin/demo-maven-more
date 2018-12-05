package com.git.cloud.sys.dao;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.dao.ICommonDAO;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.sys.model.po.*;
import com.git.cloud.sys.model.vo.AppInfoVo;
import com.git.cloud.sys.model.vo.SysRolemManageVo;
import com.git.cloud.sys.model.vo.SysUserVo;

import java.util.List;
import java.util.Map;

public interface IUserDao extends ICommonDAO{
	void saveUser(SysUserPo sysUserPo) throws RollbackableBizException;
	void updateUser(SysUserPo sysUserPo) throws RollbackableBizException;
	void deleteUser(String userId) throws RollbackableBizException;
	SysUserVo findUserById(String userId) throws RollbackableBizException;
	SysUserPo findUserPoById(String userId) throws RollbackableBizException;
	void saveRoleList(List<SysUserRole> roleList) throws RollbackableBizException;
	void deleteRoleList(Map<String,Object> mapRole) throws RollbackableBizException;
	Pagination<SysUserPo> findUserPage(PaginationParam paginationParam) throws RollbackableBizException;
	List<AppInfoVo> initAddSysRoleTree(String userId) throws RollbackableBizException;
	List<AppInfoVo> initAddSysRoleNoTree(String userId) throws RollbackableBizException;
	List<SysRolemManageVo> initRadio(String userId) throws RollbackableBizException;
	Pagination<SysRolePo> findSysRolePagination(PaginationParam paginationParam) throws RollbackableBizException;
	List<SysMenuPo> findResourceByLoginInfo(String userId,String loginName,String resourceType,String parentId) throws RollbackableBizException;
	boolean findLoginNameCount(String loginName) throws RollbackableBizException;
	List<SysUserVo> findUserByOrgId(String orgId) throws RollbackableBizException;
	List<SysRolePo> findRoleByUserId(String userId) throws RollbackableBizException;
	String findUserNameOnline();
	void saveRole(SysRolemManageVo sysRolemManageVo) throws RollbackableBizException;
	void deleteRloeManage(String userId,String appInfoId) throws RollbackableBizException;
	List<AppInfoVo> findUserRoleResult(String userId) throws RollbackableBizException;
	List<CloudServicePo> initCloudServiceNoTree(String userId) throws RollbackableBizException;
	List<CloudServicePo> initCloudServiceTree(String userId) throws RollbackableBizException;
	void deleteCloudService(String userId, String id) throws RollbackableBizException;
	void saveCloudService(SysRolemManageVo cloudServiceVo) throws RollbackableBizException;
	List<SysUserVo> findUserByLoginName(String loginName)throws RollbackableBizException;
}
