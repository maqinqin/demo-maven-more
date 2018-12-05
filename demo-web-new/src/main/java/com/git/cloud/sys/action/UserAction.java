package com.git.cloud.sys.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.common.model.CommonTreeNode;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.model.po.SysUserRole;
import com.git.cloud.sys.model.vo.SysRolemManageVo;
import com.git.cloud.sys.model.vo.SysUserVo;
import com.git.cloud.sys.service.IUserService;

/**
 * 用户管理Action
 * @ClassName:UserAction
 * @Description:TODO
 * @author dongjinquan
 * @date 2014-11-27 下午3:37:17
 */
public class UserAction extends BaseAction<Object>{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(UserAction.class);
	private IUserService userService;
	private SysUserPo sysUserPo;
	
	/**
	 * 用户管理界面
	 * @throws Exception 
	 */
	public String findUserList(){
		return "success"; 
	}
	
	/**
	 * 修改密码界面
	 * @throws Exception 
	 */
	public String modifyUserPassword(){
		return "success"; 
	}
	
	/**
	 * 获取当前登录用户信息
	 * @throws Exception
	 */
	public void findUserByCurrent() throws Exception{
		SysUserPo sysUserPo=(SysUserPo) SecurityUtils.getSubject().getPrincipal();
		String userId = sysUserPo.getUserId();
		SysUserVo vo=userService.findUserById(userId);
		this.jsonOut(vo);
	}
	
	/**
	 * 修改用户密码
	 * @throws Exception
	 */
	public void modifyUserByOldPassword() throws Exception{
		String userId = this.getRequest().getParameter("userId");
		String passWord = this.getRequest().getParameter("passWord");
		String result = userService.updateUserPassword(userId, passWord);
		this.stringOut(result);
	}
	
	/**
	 * 保存用户
	 * @throws Exception 
	 */
	public void saveUser() throws RollbackableBizException{
		try {
			String loginNameOld = this.getRequest().getParameter("loginNameOld");
			String loginPassOld = this.getRequest().getParameter("loginPassOld");
			String result = "";
			String loginPassword = sysUserPo.getLoginPassword();
			if(null==sysUserPo.getUserId()||sysUserPo.getUserId().length()==0){
				result = userService.saveUser(sysUserPo,loginNameOld);
			}else{
				result = userService.updateUser(sysUserPo,loginNameOld,loginPassOld);
			}
			this.stringOut(result);
		} catch (Exception e) {
             logger.error("操作异常:"+e);
             throw new RollbackableBizException(e.getMessage());
		}
	}
	
	/**
	 * 保存系统授权
	 * @throws Exception 
	 */
	public void saveRole() throws RollbackableBizException{
		String roleStr = this.getRequest().getParameter("roleStr");
		String userId = this.getRequest().getParameter("userId");
		
		List<CommonTreeNode> list = userService.initAddSysRoleTree(userId);
		String id = null;
		
		if(list!=null && list.size()>0){
			for(CommonTreeNode commonTreeNode:list){
				id = commonTreeNode.getId();
				userService.deleteRloeManage(userId,id);
			}
		}
		userService.deleteRloeManage(userId,"ALL");
		
		if(roleStr.equals("ALL")) {
			SysRolemManageVo sysRolemManageVo= new SysRolemManageVo();
			sysRolemManageVo.setId(UUIDGenerator.getUUID());
			sysRolemManageVo.setAppInfoId("ALL");
			sysRolemManageVo.setUserId(userId);
			userService.saveRole(sysRolemManageVo);
		} else {
			String [] roleTreeStr = roleStr.split(",");
			SysRolemManageVo sysRolemManageVo;
			if(roleTreeStr.length>0){
				for(String roleStrId :roleTreeStr){
					if(roleStrId != null && !roleStrId.equals("")){
						sysRolemManageVo= new SysRolemManageVo();
						sysRolemManageVo.setId(UUIDGenerator.getUUID());
						sysRolemManageVo.setAppInfoId(roleStrId);
						sysRolemManageVo.setUserId(userId);
						userService.saveRole(sysRolemManageVo);
					}
				}
			}
		}
	}
	

	public void findUserRoleResult() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		arrayOut(userService.findUserRoleResult(userId));
	}
	
	/**
	 * 删除用户
	 * @throws Exception 
	 */
	public void deleteUser() throws RollbackableBizException{
		try {
			String userId = this.getRequest().getParameter("userId");
			String result = userService.deleteUser(userId); 
			this.stringOut(result);
		} catch (Exception e) {
			logger.error("操作异常:"+e);
		}
	}
	
	/**
	 * 根据ID获取用户信息
	 * @throws Exception
	 */
	public void findUserById() throws Exception{
		String userId = this.getRequest().getParameter("userId");
		SysUserVo vo=userService.findUserById(userId);
		this.jsonOut(vo);
	}
	
	/**
	 * 根据机构获得所有用户
	 * @param orgId
	 * @throws RollbackableBizException
	 */
	public void findUserByOrgId() throws Exception{
		String orgId = this.getRequest().getParameter("orgId");
		List<SysUserVo> list = userService.findUserByOrgId(orgId);
		this.arrayOut(list);
	}
	
	/**
	 * 用户列表
	 * @throws Exception
	 */
	public void findUserPage() throws Exception {
		this.jsonOut(userService.findUserPage(this.getPaginationParam()));
	}
	
	/**
	 * 系统授权复选框初始化
	 * @throws Exception
	 */
	public void initAddSysRoleNoTree() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		List<CommonTreeNode> list = userService.initAddSysRoleNoTree(userId);
		this.arrayOut(list);
	}
	public void initRadio() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		List<SysRolemManageVo> list= userService.initRadio(userId);
		this.arrayOut(list);
	}
	public void initAddSysRoleTree() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		List<CommonTreeNode> list = userService.initAddSysRoleTree(userId);
		this.arrayOut(list);
	}
	
	/**
	 * 云服务授权复选框初始化
	 * @throws Exception
	 */
	public void initCloudServiceNoTree() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		List<CommonTreeNode> list = userService.initCloudServiceNoTree(userId);
		this.arrayOut(list);
	}
	public void initCloudServiceTree() throws Exception {
		String userId = this.getRequest().getParameter("userId");
		List<CommonTreeNode> list = userService.initCloudServiceTree(userId);
		this.arrayOut(list);
	}
	/**
	 * 保存云服务授权
	 * @throws Exception 
	 */
	public void saveCloudService() throws RollbackableBizException{
		String cloudStr = this.getRequest().getParameter("cloudStr");
		String userId = this.getRequest().getParameter("userId");
		
		List<CommonTreeNode> list = userService.initCloudServiceTree(userId);
		String id = null;
		
		if(list!=null && list.size()>0){
			for(CommonTreeNode commonTreeNode:list){
				id = commonTreeNode.getId();
				userService.deleteCloudService(userId,id);
			}
		}
		userService.deleteCloudService(userId,"ALL");
		
		if(cloudStr.equals("ALL")){
			SysRolemManageVo cloudServiceVo= new SysRolemManageVo();
			cloudServiceVo.setId(UUIDGenerator.getUUID());
			cloudServiceVo.setCloudServiceId("ALL");
			cloudServiceVo.setUserId(userId);
			userService.saveCloudService(cloudServiceVo);
		}else{
			String [] cloudTreeStr = cloudStr.split(","); 
			SysRolemManageVo cloudServiceVo;
			if(cloudTreeStr.length>0){
				for(String cloudId :cloudTreeStr){
					if(cloudId != null && !cloudId.equals("")){ 
						cloudServiceVo = new SysRolemManageVo();
						cloudServiceVo.setId(UUIDGenerator.getUUID());
						cloudServiceVo.setCloudServiceId(cloudId);
						cloudServiceVo.setUserId(userId);
						userService.saveCloudService(cloudServiceVo);
					}
				}
			} 
		}
		
//		jsonOut();
	}
	
	/**
	 * 保存授予角色
	 * @param roleList
	 * @throws RollbackableBizException
	 */
	public void saveAutoRole() throws RollbackableBizException {
		String userId = this.getRequest().getParameter("userId");
		String roleIds = this.getRequest().getParameter("roleIds");
		List<SysUserRole> roleList = new ArrayList<SysUserRole>();
		
		for(String roleId : roleIds.split(",")){
			SysUserRole userRole = new SysUserRole(userId,roleId);
			roleList.add(userRole);
		}
		userService.saveRoleList(roleList);
	}
	
	/**
	 * 取消授予角色
	 * @param userId
	 * @param roleId
	 * @throws RollbackableBizException
	 */
	public void calcleAutoRole() throws RollbackableBizException {
		String userId = this.getRequest().getParameter("userId");
		String roleIds = this.getRequest().getParameter("roleIds");
		
		for(String roleId : roleIds.split(",")){
			userService.deleteRoleList(userId, roleId);
		}
	}
	
	/**
	 * 查询角色列表
	 * @throws Exception
	 */
	public void findSysRoleList() throws Exception {
		this.jsonOut(userService.findSysRolePagination(this.getPaginationParam()));
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public SysUserPo getSysUserPo() {
		return sysUserPo;
	}
	public void setSysUserPo(SysUserPo sysUserPo) {
		this.sysUserPo = sysUserPo;
	}
}