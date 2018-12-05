package com.git.cloud.shiro.dao;

import java.util.List;

import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysUserPo;
/**
 * Shiro登录认证
 * @ClassName: PermissionDao
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public interface PermissionDao {
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
