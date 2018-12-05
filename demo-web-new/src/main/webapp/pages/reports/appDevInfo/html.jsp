<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>应用系统资源统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>

<!-- JavaScript -->
<script type="text/javascript" src="${ctx }/pages/reports/appDevInfo/js/html.js"></script>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/new.css">
</head>
<body >

<div id="pageGrid" class="content-main clear">
	<!-- 分页头信息： 包括标题、解释等信息 -->
	<div id="pageHeader" class="page-header">
		<h1>
			应用系统资源统计1
			<small>应用系统资源统计</small>
		</h1>
	</div>
	
	<!-- 分页条件查询信息  -->
	<div id="pageForm" class="pageFormContent">
		<form action="">
		<div class="searchInWrap_top">
			<div class="searchIn_left" style="width:66%; float:left;">
				<!-- <div class="searchInOne" > -->
					<table height="12%" width="100%" align="center">
						<tr style=" height:55px; ">
							<input type="hidden" id="serverName" value="<%=request.getServerName()%>"></input>
							<input type="hidden" id="serverPort" value="<%=request.getServerPort()%>"></input>
							<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"></input>
							
							<input type="hidden" id="timeLineForBack" value="<%=request.getParameter("timeLine")%>"></input>
							<input type="hidden" id="queryvmTypeForBack" value="<%=request.getParameter("queryvmType")%>"></input>
							
<!-- 							<a href="/reports/appDevInfo/toPdf.action">aaaaaaaa</a> -->
							<td class="tabBt">统计月份：</td>
							<td class="tabCon" >
								<select id="year" name="year" class="selInput">
									<option id="default1" value="default"><icms-i18n:label name="zxc2"/></option>
								</select>
							</td>
							<td>
								<select id="month" name="month" class="selInput">
									<option id="default2" value="default"><icms-i18n:label name="zxc2"/></option>
								</select>
							</td>
							<td class="tabBt">虚机类型：</td>
							<td class="tabCon">
								<icms-ui:dic name="queryvmType" id="queryvmType"
									sql="SELECT '1' as id,  VM.VIRTUAL_TYPE_NAME AS VALUE,VM.VIRTUAL_TYPE_NAME AS NAME ,'1' AS KIND FROM RM_VIRTUAL_TYPE VM WHERE VM.IS_ACTIVE='Y'"
									showType="select" attr="class='selInput'" />
							</td>
						
						</tr>
					</table>
				<!-- </div> -->
			</div>
			<div class="searchBtn_right" style="width:28%; float:left" >
				<table height="12%" width="100%" align="center">
					<tr style=" height:52px;">
						<td>
							<input type="button" class="Btn_Serch" onclick="search()" title="查询" style="vertical-align:middle"/>
							<input type="reset" title="清空" class="Btn_Empty" style="vertical-align:middle;text-indent:-999px;" />
						</td>	
					</tr>
				</table>
			</div>
		</div>
		</form>	
	</div>
	
	
</div>

</body>
</html> 
