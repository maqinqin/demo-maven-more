package com.git.cloud.resmgt.compute.model.vo;

import java.util.List;
/**
 * 
 * @author git
 * jqGrid返回信息Vo
 * 
 */
public class JqGridJsonVo {
	private Integer page;
	private Integer total;
	private Integer record;
	private String sord;
	private String sidx;
	private String search;
	private List <ScanVmResultVo> rowss;
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
	public List<ScanVmResultVo> getrows() {
		return rowss;
	}
	public void setrows(List<ScanVmResultVo> list) {
		this.rowss = list;
	}
	

}
