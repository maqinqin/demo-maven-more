package com.git.cloud.workflow.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.action.BaseAction;
import com.gitcloud.tankflow.common.model.Json;

/**
 * 
 * @author git
 * 
 */
@SuppressWarnings("unchecked")
public class ServiceBaseAction extends BaseAction implements
		ServletContextAware, ServletRequestAware, ServletResponseAware {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 4269261972614854050L;
	public HttpServletRequest request;
	public HttpServletResponse response;
	public ServletContext context;
	public Map session;
	public String baseContext; // 上下文
	public String message; // 提示信息
	public String jumpUrl; // 跳转的路径;

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBaseContext() {
		return request.getContextPath();
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public ServletContext getContext() {
		return context;
	}

	/**
	 * 值栈设值;
	 * 
	 * @param name
	 * @param obj
	 */
	public void set(String name, Object obj) {
		ServletActionContext.getValueStack(request).set(name, obj);
	}
	
	/**
	 * 输出Json对象数据;
	 */
	public void jsonOut(Json json) {
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			JSONArray jsonArray = JSONArray.fromObject(json);
			out.println(jsonArray);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
	}

}
