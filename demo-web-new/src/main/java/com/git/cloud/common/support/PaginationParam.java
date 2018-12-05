package com.git.cloud.common.support;

import java.util.HashMap;
import java.util.Map;

public class PaginationParam {

	private String _orderby; // 排序关键字
	private String orderField;// 排序字段名
	private String orderType;// 排序的方式，asc或者desc
	private Integer pageSize;// 每页显示的记录数
	private Integer currentPage;// 当前页面
	private Integer startIndex;// 当前查询的开始索引
	private Map<String, Object> params = new HashMap<String, Object>();// 查询的业务参数

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		if(orderField == null) {
			orderField = "";
		}
		if(!"".equals(orderField)) {
			this.set_orderby("order by");
		}
		this.orderField = orderField;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		if(orderType == null) {
			orderType = "";
		}
		this.orderType = orderType;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Integer getStartIndex() {

		if (currentPage == null || currentPage.intValue() == 0) {
			this.startIndex = new Integer(0);
		} else {
			this.startIndex = (this.currentPage.intValue() - 1) * this.pageSize.intValue();
		}
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public String get_orderby() {
		return _orderby;
	}

	public void set_orderby(String _orderby) {
		this._orderby = _orderby;
	}
}
