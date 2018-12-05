<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>应用系统资源统计2</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>

<!-- JS -->
<script type="text/javascript" src="${ctx }/pages/reports/appDevInfo/js/html1.js"></script>
<script type="text/javascript">
	function aa(){
		alert("location.hostname "+location.hostname);
		alert("location.pathname "+location.pathname );
		alert("location.port "+location.port );
		alert("location.protocol "+location.protocol );
	}
</script>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/new.css">

</head>


<body onload="aa();">

<div id="pageGrid" class="content-main clear">
	<!-- 分页头信息： 包括标题、解释等信息 -->
	<div id="pageHeader" class="page-header">
		<h1>
			应用系统资源统计
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
							<%-- <td class="tabBt" style="width:70px;">应用系统：</td>
							<td class="tabCon">
								<icms-ui:dic name="queryApp" id="queryApp" 
									sql="SELECT '1' as id, app.APP_ID AS VALUE,app.CNAME AS NAME ,'1' AS KIND FROM APP_INFO app WHERE app.IS_ACTIVE='Y'" 
									showType="select" attr="class='selInput'"></icms-ui:dic>
							</td> --%>
							
							<td class="tabBt">年&nbsp;&nbsp;&nbsp;&nbsp;月：</td>
							<td class="tabCon" >
								<select id="year" name="year">
									<option id="default1" value="default"><icms-i18n:label name="zxc2"/></option>
								</select>年
								<select id="month" name="month">
									<option id="default2" value="default"><icms-i18n:label name="zxc2"/></option>
								</select>月
							</td>
							<td class="tabBt">虚机类型：</td>
							<td class="tabCon">
								<icms-ui:dic name="queryvmType" id="queryvmType"
									sql="SELECT '1' as id,  VM.VIRTUAL_TYPE_NAME AS VALUE,VM.VIRTUAL_TYPE_NAME AS NAME ,'1' AS KIND FROM RM_VIRTUAL_TYPE VM WHERE VM.IS_ACTIVE='Y'"
									showType="select" attr="class='selInput'" />
							</td>
						
							<td class="tabBt" ></td>
							<td class="tabCon"></td>
							
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
	
	<!-- 分页详细信息  -->
	<div id="gridTableDiv" class="panel clear">
		<div>
			<table id="gridTable" ></table>
				<div id="gridPager"></div>
		</div>
	</div>	
</div>










</body>
</html>