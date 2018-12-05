package com.git.cloud.common.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.sys.model.po.SysUserPo;

/**
 * 访问web时加载的类
 * 
 * @author sunhailong
 */
public class WebActionContextFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(WebActionContextFilter.class);
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		Subject subject = SecurityUtils.getSubject();
		Object obj = subject.getPrincipal();

		HashMap<String, Object> hash = new HashMap<String, Object>();
		if (obj != null) {
			String ipSession = subject.getSession().getHost()+"-"+subject.getSession().getId();
			logger.debug("校验记录ip-seesion:{}",ipSession);
			if(subject.getSession().getAttribute(ipSession) == null) {
				logger.debug("校验记录ipseesion:{}不在存,返回登陆页",ipSession);
				arg0.getRequestDispatcher("/").forward(arg0, arg1);
			}
			SysUserPo shiroUser = (SysUserPo) obj;
			hash.put(ActionContext.HTTP_SYS_USER, shiroUser);
		} else {
			hash.put(ActionContext.HTTP_SYS_USER, null);
		}
		hash.put(ActionContext.HTTP_REQUEST, arg0);
		ActionContext.setContext(new ActionContext(hash));
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void destroy() {

	}
}
