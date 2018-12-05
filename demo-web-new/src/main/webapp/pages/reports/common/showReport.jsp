<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通用报表显示界面</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx }/pages/reports/common/js/showReport.js"></script>
<style type="text/css">
.zjx{
overflow:auto; 
background-attachment:   fixed;   
background-repeat:   no-repeat;   
border-style:   solid; 
border-color:   #FFFFFF;
width: 75px; 
height: 17px;
}
</style>
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/new.css">
</head>
<body onload="completeSelect();" class="main1">
<div class="content-main clear">
<div id="pageHeader" class="page-header">
	<input type="hidden" id="reportNameKey" value="${createReportParam.reportNameKey }"></input>
	<input type="hidden" id="reportNameValue" value="${createReportParam.reportNameValue }"></input>
	<input type="hidden" id="reportDecKey" value="${createReportParam.reportDecKey }"></input>
	<input type="hidden" id="reportDecValue" value="${createReportParam.reportDecValue }"></input>
	<input type="hidden" id="reportId" value="${createReportParam.id }"></input>
	<h1 style="display:none; " id="reportTop"><div class="WorkBtBg">报表名称：${createReportParam.reportNameValue }</div><small>报表描述：${createReportParam.reportDecValue }</small></h1>
</div>
</br>
	
<div id="pageForm" class="pageFormContent searchBg" style="height:55px;">
	<!-- 条件   -->
	<div class="searchIn_left" style="margin-top: 10px;">
		<c:forEach items="${createReportParam.conProList}" var="con">
			<c:if test="${con.conType == 'selectSql'}">
				<textarea readonly="readonly"  class="zjx">${con.conValue}:</textarea>
				<icms-ui:dic name="con" id="${con.conKey }"
					sql="${con.conType_dec }"
					showType="select" attr="style='width: 164px;' class='selInput'" />
				<%-- <select id="${con.conKey }" name="con" class="selInput">
					<c:forEach items="${con.proList }" var="pro">
						<option  value="${pro.propertyKey }">${pro.propertyValue }</option>
					</c:forEach>
				</select> --%>
			</c:if>
			<c:if test="${con.conType == 'select'}">
				<textarea readonly="readonly" class="zjx">${con.conValue}:</textarea>
				<select id="${con.conKey }" name="con" class="selInput">
					<c:forEach items="${con.proList }" var="pro">
						<option  value="${pro.propertyKey }">${pro.propertyValue }</option>
					</c:forEach>
				</select>
			</c:if>
			<c:if test="${con.conType == 'text'}">
				<textarea readonly="readonly" class="zjx">${con.conValue}:</textarea>
				<input type="text" id="${con.conKey }" name="con" class="textInput"/>
			</c:if>
			<c:if test="${con.conType == 'time' }">
				<textarea readonly="readonly" class="zjx">${con.conValue}:</textarea>
				<select id="${con.conKey }_year" name="year" class="selInput" onchange="alterDaySelect(this);">
					<option id="default_year_${con.conKey }" value="default"><icms-i18n:label name="zxc2"/></option>
				</select>
				<c:if test="${con.conType_dec == 'ym' }">
					<select id="${con.conKey }_month" name="month" class="selInput" onchange="alterDaySelect(this);">
						<option id="default_month_${con.conKey }" value="default"><icms-i18n:label name="zxc2"/></option>
					</select>
				</c:if>
				<c:if test="${con.conType_dec == 'ymd' }">
					<select id="${con.conKey }_month" name="month" class="selInput" onchange="alterDaySelect(this);">
						<option id="default_month_${con.conKey }" value="default"><icms-i18n:label name="zxc2"/></option>
					</select>
					<select id="${con.conKey }_day" name="day" class="selInput">
						<option id="default_day_${con.conKey }" value="default"><icms-i18n:label name="zxc2"/></option>
					</select>
				</c:if>
			</c:if>
			<c:if test="${con.isSqlParam == 'Y' }">
				<input type="checkbox" id="isSqlParam_${con.conKey }" name="isSqlParam" value="Y" checked="checked" disabled="disabled" style="display:none;"/><font class="zjx" style="display:none;">是否SQL所需</font>
			</c:if>
			<c:if test="${con.isSqlParam == 'N' }">
				<input type="checkbox" id="isSqlParam_${con.conKey }" name="isSqlParam" value="N" disabled="disabled" style="display:none;"/><font class="zjx" style="display:none;">是否SQL所需</font>
			</c:if>
			</br></br>
		</c:forEach>
	</div>
	
	<!-- sql语句  -->
	<div class="searchIn_left" style="margin-bottom: 10px;display:none;">
		<textarea readonly="readonly" class="zjx">SQL语句：</textarea>
		<c:forEach items="${createReportParam.sqlList}" var="sql">
			<textarea id="${sql.sqlKey}" name="sql" disabled="disabled">${sql.sqlValue}</textarea>
		</c:forEach>
	</div>
	
	<!-- jasper路径  -->
	<div class="searchIn_left" style="margin-bottom: 10px;display:none;">
		<textarea readonly="readonly" class="zjx">Jasper文件路径：</textarea>
		<textarea id="jasperPath" disabled="disabled" >${createReportParam.jasperPath }</textarea>
	</div>
	
	<!-- 按钮   -->
	<div class="searchBtn_right" style="width:28%;display:none; " id="searchDiv">
		<table height="12%" width="100%" align="center">
			<tr style=" height:52px;">
				<td>
				<a href="javascript:search()" type="button" title="查询" value="查询" class="btn" onclick="search();return false;">
							<span class="icon iconfont icon-icon-search"></span>
							<span>查询</span>
						</a>
					<!-- <input type="button" class="Btn_Serch" onclick="search()" title="查询" style="vertical-align:middle"/>
					<input type="reset" title="清空" class="Btn_Empty" style="vertical-align:middle;text-indent:-999px;" /> -->
					<button type="button" title="清空" value="清空" class="btnDel" onclick="clearAll()">
							<span class="icon iconfont icon-icon-clear1"></span>
							<span>清空</span>
						</button>
				</td>	
			</tr>
		</table>
	</div>
</div>
</div>
</body>
</html>