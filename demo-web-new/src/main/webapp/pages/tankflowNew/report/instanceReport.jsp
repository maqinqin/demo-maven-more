<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ taglib prefix="icms-ui" uri="/WEB-INF/tld/icms-ui.tld"%>
<html>
<title>实例运行报告</title>
<script type="text/javascript" src="${ctx}/pages/tankflow/js/constants.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/instance/js/instanceReport.js?t=${sfx}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/tankflow/instance/css/instanceReport.css?t=${sfx}" />
<link rel="stylesheet" href="${ctx}/common/css/search_new.css?t=${sfx}" type="text/css"></link>
<style>
#nodeList td:first-child{
	width: 2%;
}
#nodeList td:nth-child(2){
	width: 35%;
}
</style>
</head>
<body style="width: auto; padding: 8px; margin: 0px;" class="main1">
	<div class="WorkBtBg">
		<div class="mainSearchBt" style="padding-bottom:10px; overflow:hidden;position: relative;bottom: 18px;left: 5px;width: 99%;"> 
		<span class="newsBt" style="font-size:15px;">报告</span>
		<div class="searchBtn_Wrap" style="float:right;">
			<!-- 传递的参数 -->
			<input type="hidden" value="${ctx}" id="url" /> <input
				id="processDefinitionId" type="hidden"
				value='<c:out value="${param.processDefinitionId}"></c:out>' /> <input
				id="processInstanceId" type="hidden"
				value='<c:out value="${param.processInstanceId}"></c:out>' /> 
				<!--<input
				type="button" class="btn_return" id="submit_btn" onclick="goback();"
				value="" /> <input type="button" id="downloadPdf"
				onclick="dlPdf();" class="DaoChu_btn" />-->
				<a href="javascript:void(0)" class="btn" id="submit_btn" title="返回" onclick="goback();return false;"><span class="text" style="padding:0 10px;">返回</span></a>
				<a href="javascript:void(0)" class="btn btn-green" id="downloadPdf" title="导出" onclick="dlPdf();return false;"><span class="text" style="padding:0 10px;">导出</span></a>
		</div>
	</div>
	</div>
	

	<div class="tree-div" style="top:45px; left:1%; width:30%; padding-top:20px; border-top: 2px solid #A0C4BF;
border-bottom: 2px solid #A0C4BF; background: #e4eaec; min-height:500px;">
		<div class="page-header"
			style="padding: 2px 0 6px 0; margin-top: 5px;">
			<small style="padding-left:36px;">流程运行报告 : <label id="instanceName" style="padding-left:52px;">实例名称</label>
			</small>
		</div>
		<div class="summary_text">
			<table width="100%" height="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td class="label">实例类型：</td>
					<td colspan="3" id="instanceType"></td>
				</tr>
				<!-- 
				<tr>
					<td class="label">服务请求：</td>
					<td colspan="3" id="serviceReqId"></td>
				</tr>
				 -->
				<tr>
					<td class="label">实例状态：</td>
					<td width="90" id="instanceStateId"></td>

				</tr>
				<tr>
					<td class="label">开始时间：</td>
					<td colspan="3" id="startDate"></td>
				</tr>
				<tr>
					<td class="label">结束时间：</td>
					<td colspan="3" id="endDate"></td>
				</tr>
				<tr>
					<td width="70" class="label">执行时长：</td>
					<td id="duration"></td>
				</tr>
				<tr>
					<td colspan="4" class="label">流程步骤：</td>
				</tr>
				<tr>
					<td colspan="4">
						<div class="LC_name">
							<table id="nodeList" cellpadding="0" cellspacing="0" border="0"
								width="100%">
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div> 
		<!-- 空白区域 -->
		<div style="height: 50px; clear: both;"></div>
	</div>
	<div class="right-div" style="top: 45px;left:32%; background-color: white; min-height:520px; width:67%;">
		<table id="nodeReport" width="100%" border="0" cellspacing="0"
			cellpadding="0">
		</table>
	</div>
</body>
</html>