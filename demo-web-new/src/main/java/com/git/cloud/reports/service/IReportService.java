package com.git.cloud.reports.service;

import com.git.cloud.common.support.Pagination;
import com.git.cloud.common.support.PaginationParam;
import com.git.cloud.reports.model.po.CreateReportParamPo;

public interface IReportService {
	
	//public String save(CreateReportParamPo crpo);

	public CreateReportParamPo showReport(String id);

	public Pagination<CreateReportParamPo> getReportPagination(
			PaginationParam paginationParam);

	public void deleteReportList(String id);
	
	public CreateReportParamPo selectReport(String id);
	
	public CreateReportParamPo selectReportByName(String reportName);

	public String save(String[] params, String type, String reportId);

}
