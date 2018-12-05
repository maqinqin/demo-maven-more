package com.git.cloud.sys.dao.impl;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.IsActiveEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.sys.dao.IUserDao;
import com.git.cloud.sys.model.po.*;
import com.git.cloud.sys.model.vo.AppInfoVo;
import com.git.cloud.sys.model.vo.SysRolemManageVo;
import com.git.cloud.sys.model.vo.SysUserVo;
import org.apache.shiro.SecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends CommonDAOImpl implements IUserDao{
	/**
	 * 保存用户信息	
	 */
	@Override
	public void saveUser(SysUserPo sysUserPo) throws RollbackableBizException{
		sysUserPo.setIsActive(IsActiveEnum.YES.getValue());
		this.save("insertUser", sysUserPo);
	}
	/**
	 * 更新用户信息
	 */
	@Override
	public void updateUser(SysUserPo sysUserPo) throws RollbackableBizException{
		sysUserPo.setIsActive(IsActiveEnum.YES.getValue());
		this.update("updateUser", sysUserPo);
	}
	/**
	 * 删除用户信息
	 */
	@Override
	public void deleteUser(String userId) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("isActive", IsActiveEnum.NO.getValue());
		map.put("userId", userId);
		this.deleteForParam("deleteUser", map);
	}
	@Override
	public void deleteRloeManage(String userId,String appInfoId) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("appInfoId",appInfoId);
		map.put("userId", userId);
		this.deleteForParam("deleteRloeManage", map);
	}
	/**
	 * 用户分配系统角色
	 */
	@Override
	public List<AppInfoVo> findUserRoleResult(String userId) throws RollbackableBizException {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		paramMap.put("userId", userId);
		return this.findListByParam("findUserRoleResult", paramMap);
	}
	/**
	 * 根据ID获取用户信息Vo
	 */
	@Override
	public SysUserVo findUserById(String userId) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("isActive", IsActiveEnum.YES.getValue());
		map.put("userId", userId);
		return this.findObjectByMap("getUserById", map);
	}
	/**
	 * 根据ID获取用户信息Po
	 */
	@Override
	public SysUserPo findUserPoById(String userId) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("isActive", IsActiveEnum.YES.getValue());
		map.put("userId", userId);
		return this.findObjectByMap("getUserPoById", map);
	}
	/**
	 * 根据机构ID获得所有用户
	 */
	@Override
	public List<SysUserVo> findUserByOrgId(String orgId) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("isActive", IsActiveEnum.YES.getValue());
		map.put("orgId", orgId);
		return this.findListByParam("getUserByOrgId", map);
	}
	/**
	 * 保存授予角色
	 * @param roleList
	 * @throws RollbackableBizException
	 */
	@Override
	public void saveRoleList(List<SysUserRole> roleList) throws RollbackableBizException {
		if(CollectionUtil.hasContent(roleList)){
			this.batchInsert("insertRoleList",roleList);	
		}
	}
	/**
	 * 取消授予角色
	 *
	 * @throws RollbackableBizException
	 */
	@Override
	public void deleteRoleList(Map<String,Object> mapRole) throws RollbackableBizException {
		 this.deleteForParam("deleteRloe", mapRole);
	}
	/**
	 * 用户列表
	 * @param paginationParam
	 * @return
	 */
	@Override
	public Pagination<SysUserPo> findUserPage(PaginationParam paginationParam) throws RollbackableBizException{
		return this.pageQuery("selectUserListTotal","selectUserListPage", paginationParam);
	}
	/**
	 * 系统授权复选框初始化
	 * @return
	 */
	@Override
	public List<AppInfoVo> initAddSysRoleNoTree(String userId) throws RollbackableBizException{
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("userId", userId);
		return this.findListByParam("initAddSysRoleNoTree",hashMap);
	}
	/**
	 * 系统授权复选框初始化
	 * @return
	 */
	@Override
	public List<AppInfoVo> initAddSysRoleTree(String userId) throws RollbackableBizException{
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("userId", userId);
		return this.findListByParam("initAddSysRoleTree",hashMap);
	}
	/**
	 * 云服务授权复选框初始化
	 * @return
	 */
	@Override
	public List<CloudServicePo> initCloudServiceNoTree(String userId) throws RollbackableBizException{
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("userId", userId);
		return this.findListByParam("initCloudServiceNoTree",hashMap);
	}
	@Override
	public List<CloudServicePo> initCloudServiceTree(String userId) throws RollbackableBizException{
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("userId", userId);
		return this.findListByParam("initCloudServiceTree",hashMap);
	}
	//删除当前用户所有授权的云服务
	@Override
	public void deleteCloudService(String userId, String id) throws RollbackableBizException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("cloudServiceId",id);
		map.put("userId", userId);
		this.deleteForParam("deleteCloudService", map);
	}
	//保存当前用户所有授权的云服务
	@Override
	public void saveCloudService(SysRolemManageVo cloudServiceVo)
			throws RollbackableBizException {
		super.save("saveCloudService", cloudServiceVo);
	}
	
	
	
	/**
	 * 角色列表
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	@Override
	public Pagination<SysRolePo> findSysRolePagination(PaginationParam paginationParam) throws RollbackableBizException {
		return pageQuery("findUserRoleTotal", "findUserRolePage", paginationParam);
	}
	/**
	 * 根据登录名获得授权资源
	 * @return
	 */
	@Override
	public List<SysMenuPo> findResourceByLoginInfo(String userId,String loginName,String resourceType,String parentId) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("isActive", IsActiveEnum.YES.getValue());
		map.put("userId", userId);
		map.put("loginName", loginName);
		map.put("resourceType", resourceType);
		map.put("parentId", parentId);
		return this.findListByParam("selectResByLoginName", map);
	}
	/**
	 * 验证登录名是否存在
	 * @param loginName
	 * @return
	 */
	@Override
	public boolean findLoginNameCount(String loginName) throws RollbackableBizException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("isActive", IsActiveEnum.YES.getValue());
		map.put("loginName", loginName);
		int size = this.findListByParam("selectLoginNameCount", map).size();
		if(size==0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * 获取当前在线用户
	 * @return
	 */
	@Override
	public String findUserNameOnline(){
		SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
		if(shiroUser != null) {
			return shiroUser.getLoginName();
		}
		return "";
	}
	@Override
	public List<SysRolePo> findRoleByUserId(String userId) throws RollbackableBizException {
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("userId", userId);
		return this.findListByParam("findRoleByUserId", map);
	}
	
	@Override
	public void saveRole(SysRolemManageVo sysRolemManageVo) throws RollbackableBizException{
		super.save("saveRole", sysRolemManageVo);
	}
	
	@Override
	public List<SysUserVo> findUserByLoginName(String loginName) throws RollbackableBizException {
		Map<String, Object> map = new HashMap<String, Object> ();
		map.put("login_name", loginName);
		return this.findListByParam("findUserByLoginName", map);
	}
	@Override
	public List<SysRolemManageVo> initRadio(String userId) throws RollbackableBizException {
		Map<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("userId", userId);
		return this.findListByParam("initRadio",hashMap);
	}
}
