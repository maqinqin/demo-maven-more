package com.git.cloud.shiro.dao.impl;

import java.util.List;

import com.git.cloud.common.dao.CommonDAOImpl;
import com.git.cloud.shiro.dao.PermissionDao;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysUserPo;
/**
 * Shiro登录认证
 * @ClassName: PermissionDaoImpl
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class PermissionDaoImpl extends CommonDAOImpl implements PermissionDao {
	/**
	 * 根据用户名查询用户
	 * 
	 * @param logname
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public SysUserPo findUserByLoginName(String logname) {
		List list = this.getSqlMapClientTemplate().queryForList("Shiro.findUserByLoginName", logname);
		if (list != null) {
			for (Object o : list) {
				return (SysUserPo) o;
			}
		}
		return null;
	}

	/**
	 * 根据用户id查询用户的全部权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysMenuPo> userPermissions(String userId) {
		List list = this.getSqlMapClientTemplate().queryForList("Shiro.userPermissions", userId);
		return list;
	}

}
