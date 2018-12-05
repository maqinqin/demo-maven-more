package com.git.cloud.common.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.git.cloud.common.support.GetParamMap;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.common.support.ResolveObject;
import com.git.cloud.sys.model.po.SysUserPo;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;

/**
 * Action����
 * @author Spring.Cao
 * @version v1.0 2013-03-22
 */
public class BaseAction<T> extends ActionSupport {
	
	private static final long serialVersionUID = -1L;
	
	private List<T> dataList = Collections.emptyList();
	private Integer rows = 0;
	private Integer page = 0;
	private Integer total = 0;
	private Integer record = 0;
	private String sord;
	private String sidx;
	private String search;

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}
	
	@SuppressWarnings("rawtypes")
	protected void initJsonObject() throws ClassNotFoundException, SecurityException, NoSuchMethodException
										, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		String jsonData = this.getRequest().getParameter("jsonData");
		String jsonClass = this.getRequest().getParameter("jsonClass");
		if(jsonData != null && !"".equals(jsonData) && jsonClass != null && !"".equals(jsonClass)) {
			Map<String, Class> classMap = ResolveObject.getSubClass(Class.forName(jsonClass));
			
			Object obj = JSONObject.toJavaObject(JSONObject.parseObject(jsonData), Class.forName(jsonClass));
//			Object obj = JSONObject.toBean(JSONObject.fromObject(jsonData), Class.forName(jsonClass), classMap);
			String setter = "set" + jsonClass.substring(jsonClass.lastIndexOf(".") + 1);
			Method method = this.getClass().getMethod(setter, new Class[] {Class.forName(jsonClass)});
			method.invoke(this, new Object[] {obj});
		}
	}

	@SuppressWarnings("unchecked")
	protected PaginationParam getPaginationParam() {
		PaginationParam paginationParam = new PaginationParam();
		paginationParam.setOrderField(sidx);     //设置排序字段名
		paginationParam.setOrderType(sord);      // 排序的方式，asc或者desc
		paginationParam.setPageSize(rows);       // 每页显示的记录数
		paginationParam.setParams(GetParamMap.getParamMap(this.getRequest().getParameterMap()));
		paginationParam.setCurrentPage(Integer.valueOf((String) paginationParam.getParams().get("page")));
		SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
		if(shiroUser != null) {
			String superAdmin = this.getRequest().getParameter("superAdmin");
			if(superAdmin == null || !"true".equals(superAdmin)) {
				paginationParam.getParams().put("userId", shiroUser.getUserId());
			}
		}
		return paginationParam;
	}
	
	@SuppressWarnings("unchecked")
	protected PaginationParam getPaginationParamByReq(HttpServletRequest req){
		String sidx = req.getParameter("sidx");
		String sord = req.getParameter("sord");
		String rows = req.getParameter("rows");
		Integer rowss = Integer.valueOf(rows);
		PaginationParam paginationParam = new PaginationParam();
		paginationParam.setOrderField(sidx);     //设置排序字段名
		paginationParam.setOrderType(sord);      // 排序的方式，asc或者desc
		paginationParam.setPageSize(rowss);       // 每页显示的记录数
		paginationParam.setParams(GetParamMap.getParamMap(req.getParameterMap()));
		paginationParam.setCurrentPage(Integer.valueOf((String) paginationParam.getParams().get("page")));
		SysUserPo shiroUser = (SysUserPo) SecurityUtils.getSubject().getPrincipal();
		if(shiroUser != null) {
			String superAdmin = req.getParameter("superAdmin");
			if(superAdmin == null || !"true".equals(superAdmin)) {
				paginationParam.getParams().put("userId", shiroUser.getUserId());
			}
		}
		return paginationParam;		
	}
	
	protected void renderJson(Object data, HttpServletResponse response) {
		try {
//			String jsonString = JSONObject.fromObject(data).toString();
			String jsonString = JSONObject.toJSONString(data);
			response.setHeader("Content-Type", "application/json");
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().print(jsonString);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	protected void stringOut(String str) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print("{\"result\":\""+str+"\"}");
	}
	
	protected void jsonOut(Object object) throws Exception {
//		String jsonString = JSONObject.fromObject(object).toString();
		String jsonString = JSONObject.toJSONString(object);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonString);
	}
	
	protected void ObjectOut(Object object) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(object);
	}
	
	protected void errorOut(Exception e) throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=utf-8");
		response.setStatus(500);
		response.getWriter().print(e.getMessage());
	}
	
	protected void arrayOut(Object object) throws Exception {
		String jsonString = JSONArray.fromObject(object).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().print(jsonString);
	}
	
	protected SysUserPo getCurrentUser() {
		return (SysUserPo) SecurityUtils.getSubject().getPrincipal();
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecord() {
		return record;
	}

	public void setRecord(Integer record) {
		this.record = record;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
