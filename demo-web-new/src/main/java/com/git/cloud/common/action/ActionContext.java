package com.git.cloud.common.action;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.git.cloud.sys.model.po.SysUserPo;

/**
 * 用于保存当前线程中的变量数据，为其他类使用。
 * @author sunhailong
 */
@SuppressWarnings("serial")
public class ActionContext implements Serializable {
	
	@SuppressWarnings("rawtypes")
	private static ThreadLocal actionContext = new ThreadLocal();
	Map<String, Object> context;
	public static final String HTTP_REQUEST = "com.git.HttpServletRequest";
	public static final String HTTP_SYS_USER = "com.git.cloud.sys.model.po.SysUserPo";

	@SuppressWarnings("unchecked")
	public static void setContext(ActionContext context) {
		actionContext.set(context);
	}

	public static ActionContext getContext() {
		Object object = actionContext.get();
		if (object != null) {
			return (ActionContext) object;
		} else {
			return null;
		}
	}

	public ActionContext(Map<String, Object> context) {
		this.context = context;
	}

	public Object get(String key) {
		return context.get(key);
	}

	public void put(String key, Object value) {
		context.put(key, value);
	}
	
	public static void setRequest(HttpServletRequest request) {
		getContext().put(HTTP_REQUEST, request);
	}
	
	public static HttpServletRequest getRequest() {
		Object obj = null;
		if(getContext() != null) {
			obj = getContext().get(HTTP_REQUEST);
		}
		if(obj != null) {
			return (HttpServletRequest) obj;
		} else {
			return null;
		}
	}
	
	public static void setSysUserPo(SysUserPo sysUserPo) {
		getContext().put(HTTP_SYS_USER, sysUserPo);
	}
	
	public Map<String, Object> getContextMap() {
		return context;
	}

	public static SysUserPo getCurrentSysUser() {
		SysUserPo sysUserPo = null;
		if(getContext() != null) {
			Object obj = getContext().get(HTTP_SYS_USER);
			if (obj != null) {
				sysUserPo = (SysUserPo) obj;
			}
		}
		return sysUserPo;
	}

}
