package com.git.cloud.shiro.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.shiro.service.PermissionService;
import com.git.cloud.sys.model.po.SysMenuPo;
import com.git.cloud.sys.model.po.SysUserPo;
import com.git.cloud.sys.service.IUserService;

/**
 * Shiro登录认证权限实现
 * @ClassName: ShiroDbRealm
 * @Description:TODO
 * @author caohaihong
 * @date 2014-11-27 下午3:47:17
 */
public class ShiroDbRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	private PermissionService permissionService;

	public String getName() {
		return "ShiroDbRealm";
	}

	private IUserService userService;

	public ShiroDbRealm() {
		super();
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		/* 这里编写认证代码 */
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysUserPo sysUser = permissionService.findUserByLoginName(token.getUsername());
		if (sysUser != null) {
			return new SimpleAuthenticationInfo(sysUser, sysUser.getLoginPassword(), getName());
		} else {
			throw new UnknownAccountException();// 没找到帐号
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("principals should not be null");
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		SysUserPo sysUserPo = (SysUserPo) principals.fromRealm(this.getName()).iterator().next();
		String username = sysUserPo.getLoginName();
		List<SysMenuPo> functions;
		try {
			functions = userService.findResourceByLoginInfo("", username, "function", "");
			for (SysMenuPo f : functions) {
				info.addStringPermission(f.getMenuCode());
				//System.out.println(f.getMenuCode());
			}
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		return info;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	// @PostConstruct
	// public void initCredentialsMatcher() {//MD5加密
	// HashedCredentialsMatcher matcher = new
	// HashedCredentialsMatcher(ALGORITHM);
	// setCredentialsMatcher(matcher);
	// }
}
