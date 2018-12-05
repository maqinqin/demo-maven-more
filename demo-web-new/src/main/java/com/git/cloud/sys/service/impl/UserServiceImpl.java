package com.git.cloud.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.common.model.ZtreeIconEnum;
import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.foundation.util.CollectionUtil;
import com.git.cloud.foundation.util.DateUtil;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.shiro.realm.ShiroDbRealm;
import com.git.cloud.sys.dao.IUserDao;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysRolePo;
import com.git.cloud.sys.model.po.SysUserLimitPo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.po.SysUserRole;
import com.git.cloud.sys.model.vo.AppInfoVo;
import com.git.cloud.sys.model.vo.SysRolemManageVo;
import com.git.cloud.sys.model.vo.SysUserVo;
import com.git.cloud.sys.service.IUserService;
import com.git.cloud.sys.tools.Security;

/**
 * 用户管理
 * @ClassName:UserServiceImpl
 * @Description:TODO
 * @author dongjinquan
 * @date 2014-11-27 下午3:47:17
 */
public class UserServiceImpl implements IUserService{
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static String mat = "yyyy-MM-dd HH:mm:ss";
	private static Security sec = new Security();
	private IUserDao  userDao;
	
	/**
	 * 保存用户信息
	 * @param sysUserPo
	 * @return
	 * @throws RollbackableBizException
	 */
	public String saveUser(SysUserPo sysUserPo,String loginNameOld) throws RollbackableBizException{
		try {		
			String time = DateUtil.format(new Date(), mat);
			String pass = sysUserPo.getLoginPassword();
			
			loginNameOld = loginNameOld==null?"":loginNameOld;
			if(!loginNameOld.equals(sysUserPo.getLoginName())){
				if(userDao.findLoginNameCount(sysUserPo.getLoginName())){
					return "faile";
				}
			}
				sysUserPo.setUserId(UUIDGenerator.getUUID());
				sysUserPo.setCreateDateTime(DateUtil.parse(time, mat));
				/*if(pass.length()>0){
				 sysUserPo.setLoginPassword(sec.digestToHex(pass.getBytes()));
				}*/
				userDao.saveUser(sysUserPo);

		} catch (Exception e) {
			logger.error("保存用户信息时发生异常!");
			throw new RollbackableBizException(e.getMessage());
		}
		return sysUserPo.getUserId();
	}
	
	/**
	 * 更新用户信息
	 * @param sysUserPo
	 * @return
	 * @throws RollbackableBizException
	 */
	public String updateUser(SysUserPo sysUserPo,String loginNameOld,String loginPassOld) throws RollbackableBizException{
		try {		
			String time = DateUtil.format(new Date(), mat);
			String pass = sysUserPo.getLoginPassword();
			
			loginNameOld = loginNameOld==null?"":loginNameOld;
			if(!loginNameOld.equals(sysUserPo.getLoginName())){
				if(userDao.findLoginNameCount(sysUserPo.getLoginName())){
					return "faile";
				}
			}
				/*if(pass.length()>0&&!pass.equals(loginPassOld)){
					 sysUserPo.setLoginPassword(sec.digestToHex(pass.getBytes()));
			   }*/
				sysUserPo.setUpdateDateTime(DateUtil.parse(time, mat));
				userDao.updateUser(sysUserPo);
		} catch (Exception e) {
			logger.error("更新用户信息时发生异常!");
			throw new RollbackableBizException(e.getMessage());
		}
		return sysUserPo.getUserId();
	}
	
	/**
	 * 更新用户密码
	 * @param sysUserPo
	 * @return
	 * @throws RollbackableBizException
	 */
	public String updateUserPassword(String userId,String passWord) throws RollbackableBizException{
		try {		
			String time = DateUtil.format(new Date(), mat);
			SysUserPo sysUserPo = userDao.findUserPoById(userId);
			sysUserPo.setUpdateDateTime(DateUtil.parse(time, mat));
			sysUserPo.setLoginPassword(passWord);
			userDao.updateUser(sysUserPo);
		} catch (Exception e) {
			logger.error("更新用户信息时发生异常!");
			throw new RollbackableBizException(e.getMessage());
		}
		return "0";
	}
	
	/**
	 * 删除用户信息
	 * @param userId
	 * @return
	 * @throws RollbackableBizException
	 */
	public String deleteUser(String userId) throws RollbackableBizException{
		String result = "1";
		try {
			// 查询用户的所属角色
			List<SysRolePo> roleList = userDao.findRoleByUserId(userId);
			// 将用户从角色中删除
			Map<String,Object> mapRole = new HashMap<String,Object> ();
			mapRole.put("userId", userId);
			for(int i=0 ; i<roleList.size() ; i++) {
				mapRole.put("roleId", roleList.get(i).getRoleId());
				userDao.deleteRoleList(mapRole);
			}
			// 删除用户
			userDao.deleteUser(userId);
		} catch (Exception e) {
			result = "0";
			logger.error("删除用户信息前发生异常:"+e);
		}
		return result;
	}
	
	/**
	 * 根据主键获得用户信息VO
	 * @param userId
	 * @throws RollbackableBizException
	 */
	public SysUserVo findUserById(String userId) throws RollbackableBizException{
		return userDao.findUserById(userId);
	}
	
	/**
	 * 根据主键获得用户信息PO
	 * @param userId
	 * @throws RollbackableBizException
	 */
	public SysUserPo findUserPoById(String userId) throws RollbackableBizException{
		return userDao.findUserPoById(userId);
	}
	
	/**
	 * 根据机构ID获得所有用户
	 * @param orgId
	 * @throws RollbackableBizException
	 */
	public List<SysUserVo> findUserByOrgId(String orgId) throws RollbackableBizException{
		return userDao.findUserByOrgId(orgId);
	}
	
	 /**
	  * 获得用户列表
	  * @param paginationParam
	  * @return
	  */
	public Pagination<SysUserPo> findUserPage(PaginationParam paginationParam) throws RollbackableBizException{
		return userDao.findUserPage(paginationParam);
	}
	
	/**
	 * 系统授权复选框初始化
	 * @param paginationParam
	 * @return
	 */
	public List<CommonTreeNode> initAddSysRoleNoTree(String userId) throws RollbackableBizException{
		List<AppInfoVo> list = userDao.initAddSysRoleNoTree(userId);
		List<CommonTreeNode> treeNodeList = initAddRoleTree(list);;
		return treeNodeList;
	}
	public List<CommonTreeNode> initAddSysRoleTree(String userId) throws RollbackableBizException{
		List<AppInfoVo> list = userDao.initAddSysRoleTree(userId);
		List<CommonTreeNode> treeNodeList = initAddRoleTree(list);
		return treeNodeList;
	}
	
	/**
	 * 云服务授权复选框初始化
	 * @param paginationParam
	 * @return
	 */
	@Override
	public List<CommonTreeNode> initCloudServiceNoTree(String userId) throws RollbackableBizException{
		List<CloudServicePo> list = userDao.initCloudServiceNoTree(userId);
		List<CommonTreeNode> treeNodeList = initCloudTree(list);;
		return treeNodeList;
	}
	@Override
	public List<CommonTreeNode> initCloudServiceTree(String userId) throws RollbackableBizException{
		List<CloudServicePo> list = userDao.initCloudServiceTree(userId);
		List<CommonTreeNode> treeNodeList = initCloudTree(list);
		return treeNodeList;
	}
	//初始化云服务授权树
	public List<CommonTreeNode> initCloudTree(List<CloudServicePo> list){
		List<CommonTreeNode> treeNodeList = null;
		if (list != null && list.size() > 0) {
			treeNodeList = new ArrayList<CommonTreeNode>();
			for (CloudServicePo cloudInfoVo : list) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(cloudInfoVo.getServiceId());
				treeNode.setUpId("");
				treeNode.setIconSkin("");
				treeNode.setIcon(ZtreeIconEnum.APP_SYS.getIcon());
				treeNode.setName(cloudInfoVo.getServiceName());
				treeNode.setTitle(cloudInfoVo.getServiceName());
				treeNode.setParent(false);
				treeNode.setNocheck(false);
				treeNodeList.add(treeNode);
			}
		}
		return treeNodeList;
	}
	//删除当前用户所有授权的云服务
	@Override
	public void deleteCloudService(String userId, String id)
			throws RollbackableBizException {
		userDao.deleteCloudService(userId, id);
	}
	//保存当前用户所有授权的云服务
	@Override
	public String saveCloudService(SysRolemManageVo cloudServiceVo)
			throws RollbackableBizException {
		userDao.saveCloudService(cloudServiceVo);
		return null;
	}

	
	public List<CommonTreeNode> initAddRoleTree(List<AppInfoVo> list){
		List<CommonTreeNode> treeNodeList = null;
		if (list != null && list.size() > 0) {
			treeNodeList = new ArrayList<CommonTreeNode>();
			for (AppInfoVo appInfoVo : list) {
				CommonTreeNode treeNode = new CommonTreeNode();
				treeNode.setId(appInfoVo.getAppId());
				treeNode.setUpId("");
				treeNode.setIconSkin("");
				treeNode.setIcon(ZtreeIconEnum.APP_SYS.getIcon());
				treeNode.setName(appInfoVo.getCname());
				treeNode.setTitle(appInfoVo.getCname());
				treeNode.setParent(false);
				treeNode.setNocheck(false);
				treeNodeList.add(treeNode);
			}
		}
		return treeNodeList;
	}
	
	/**
	 * 保存授予角色
	 * @param roleList
	 * @throws RollbackableBizException
	 */
	public void saveRoleList(List<SysUserRole> roleList) throws RollbackableBizException {
		if(CollectionUtil.hasContent(roleList)){
			userDao.saveRoleList(roleList);
			RealmSecurityManager securityManager =  
			      (RealmSecurityManager) SecurityUtils.getSecurityManager();  
					ShiroDbRealm shiroDbRealm = (ShiroDbRealm)securityManager.getRealms().iterator().next(); 
					shiroDbRealm.clearAllCachedAuthorizationInfo();
		}
	}
	/**
	 * 取消授予角色
	 * @param userId
	 * @param roleId
	 * @throws RollbackableBizException
	 */
	public void deleteRoleList(String userId,String roleId) throws RollbackableBizException {
		Map<String,Object> mapRole = new HashMap<String,Object>();
		mapRole.put("userId", userId);
		mapRole.put("roleId", roleId);
		userDao.deleteRoleList(mapRole);
		RealmSecurityManager securityManager =  
		      (RealmSecurityManager) SecurityUtils.getSecurityManager();  
				ShiroDbRealm shiroDbRealm = (ShiroDbRealm)securityManager.getRealms().iterator().next();  
				//更新用户授权信息缓存.
//				shiroDbRealm.clearCachedAuthorizationInfo((String)SecurityUtils.getSubject().getPrincipal());
				shiroDbRealm.clearAllCachedAuthorizationInfo(); //清除所有用户授权信息缓存
	}
	
	/**
	 * 角色列表
	 * @param paginationParam
	 * @return
	 * @throws RollbackableBizException
	 */
	public Pagination<SysRolePo> findSysRolePagination(PaginationParam paginationParam) throws RollbackableBizException {
		return userDao.findSysRolePagination(paginationParam);
	}
	
	/**
	 * @param userId null but loginName not null
	 * @param loginName null but userId not null
	 * @param resourceType not null
	 * @param parentId null
	 * 根据登录名获得授权资源
	 */
	public List<SysMenuPo> findResourceByLoginInfo(String userId,String loginName,String resourceType,String parentId) throws RollbackableBizException{
		if(null!=loginName && loginName.length()>0)
			loginName = loginName.toLowerCase();
		
		return userDao.findResourceByLoginInfo(userId,loginName,resourceType,parentId);
	}
	
	/**
	 * 用户分配系统角色
	 * @param
	 * @return
	 * @throws RollbackableBizException
	 */
	public List<AppInfoVo> findUserRoleResult(String userId) throws RollbackableBizException {
		return userDao.findUserRoleResult(userId);
	}
	
	/**
	 * 角色系统授权
	 * @param
	 * @return
	 * @throws RollbackableBizException
	 */
	public String saveRole(SysRolemManageVo sysRolemManageVo) throws RollbackableBizException {
			userDao.saveRole(sysRolemManageVo);
		return null;
	}
	
	public void deleteRloeManage(String userId,String roleId) throws RollbackableBizException {
		userDao.deleteRloeManage(userId,roleId);
	}
	
	/**
	 * 获取当前在线用户
	 * @return
	 */
	public String findUserNameOnline(){
		return userDao.findUserNameOnline();
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<SysRolemManageVo> initRadio(String userId) throws RollbackableBizException {
		// TODO Auto-generated method stub
		return userDao.initRadio(userId);
	}

}