package com.git.cloud.shiro.service;

import java.util.List;

import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysUserPo;
/**
 * Shiro登录认证权限实现
 * @ClassName: PermissionService
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface PermissionService {

	/**
	 * 根据用户名查询用户
	 * 
	 * @param logname
	 * @return
	 */
	public SysUserPo findUserByLoginName(String logname);

	/**
	 * 根据用户id查询用户的全部权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysMenuPo> userPermissions(String userId);

}
