package com.git.cloud.common.support;

import java.util.Collections;
import java.util.List;

public class Pagination<T> {
	private List<T> dataList = Collections.emptyList();
	private Integer rows;
	private Integer page;
	private Integer total;
	private Integer record;
	private String sord;
	private String sidx;
	private String search;

	public Pagination() {
		
	}
	public Pagination(PaginationParam paginationParam){
		this.page = paginationParam.getCurrentPage();
		this.rows = paginationParam.getPageSize();
		this.sord = paginationParam.getOrderType();
		this.sidx = paginationParam.getOrderField();
	}
	
	public Pagination(List<T> dataList, Integer rows, Integer page,
			Integer total, Integer record, String sord, String sidx,
			String search) {
		super();
		this.dataList = dataList;
		this.rows = rows;
		this.page = page;
		this.total = total;
		this.record = record;
		this.sord = sord;
		this.sidx = sidx;
		this.search = search;
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
