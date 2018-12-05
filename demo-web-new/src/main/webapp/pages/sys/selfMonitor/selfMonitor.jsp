<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/count.css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.table_show{border:1px solid #e1e1e1; margin-top:20px;margin-bottom:20px; margin-left:60px; clear:both;width:90%}
.table_show td{height:23px; line-height:23px; padding:4px 16px; color:#646964; border:1px solid #e1e1e1;}
a{
text-decoration:none;
}
</style>
<script type="text/javascript" src="${ctx}/pages/sys/selfMonitor/js/selfMonitor.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
	
	
</script>
</head>
<body  class="main1 main-body">
<div class="page-header" style="margin-bottom:20px;">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_selfTest"></icms-i18n:label></div>
					<div class="WorkSmallBtBg">
						<small>
							<icms-i18n:label name="sys_title_selfDesc"></icms-i18n:label>
						</small>
					</div>
			</h1>
		</div>
	<div class="searchBg searchBg_margin">
		<table id="hmc_list"  class="table_show" ></table>
	</div>
	<div class="searchBg searchBg_margin">
		<table id="vcenter_list"class="table_show" ></table>
   	</div>
   	<div class="searchBg searchBg_margin">
   		<table id="script_list" class="table_show"></table>
   	</div>
   	<div class="searchBg searchBg_margin">
   		<table id="auto_list" class="table_show"></table>
   	</div>
   	<div class="searchBg searchBg_margin">
   		<table id="mq_list" class="table_show"></table>
   	</div>
   	<div class="searchBg searchBg_margin">
   		<table id="bpm_list" class="table_show"></table>
   	</div>
   	<div class="searchBg searchBg_margin">
   		<table id="image_list" class="table_show"></table>
   	</div>
</body>
</html>