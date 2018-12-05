<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>系统运维</title>
<style type="text/css">
	.table_show{border:1px solid #e1e1e1; margin-top:20px; margin-bottom:20px; margin-left:60px; clear:both;width:90%}
	.table_show td{height:23px; line-height:23px; padding:4px 16px; color:#646964; border:1px solid #e1e1e1;}
	a{text-decoration:none;}
</style>
<script type="text/javascript" src="${ctx}/pages/sys/common/js/systemOperation.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
	$(function() {
	});
</script>
</head>
<body  class="main1">
	<div class="page-header">
		<h1>
			<div class="WorkBtBg"><icms-i18n:label name="sys_title_oper"></icms-i18n:label></div>
			<div class="WorkSmallBtBg">
				<small>
					<icms-i18n:label name="sys_title_operDesc"></icms-i18n:label>
				</small>
			</div>
		</h1>
	</div>
	<div style="padding-top:2px; background:#fff; border-top:2px solid #d9d9d9; padding-bottom:20px;">
		<table id="csrTable" class="table_show" >
		<tr>
			<td colspan="3"><icms-i18n:label name="sys_l_woOrCan"></icms-i18n:label></td>
		</tr>
		<tr>
			<td style="width:10%;"><icms-i18n:label name="sys_l_woOrNum"></icms-i18n:label>：</td>
			<td style="width:30%;">
				<input type="text" id="csrTable_srCode" name="csrTable_srCode" class="textInput" style="width:100%;border:1px"/>
			</td>
			<td style="width:15%;text-align:center">
				<a href="javascript:;" onclick="cancelServiceRequest();"><u><icms-i18n:label name="sys_l_cancel"></icms-i18n:label></u></a>
			</td>
			<td style="width:45%;text-align:left">
				作废操作：只会将流程进行关闭，并且将待办事项关闭；
			</td>
		</tr>
		<tr>
			<td style="width:10%;"><icms-i18n:label name="sys_l_woOrNum"></icms-i18n:label>：</td>
			<td style="width:30%;">
				<input type="text" id="back_srCode" name="back_srCode" class="textInput" style="width:100%;border:1px"/>
			</td>
			<td style="width:15%;text-align:center">
				<a href="javascript:;" onclick="backServiceRequest();"><u>资源回退并作废</u></a>
			</td>
			<td style="width:45%;text-align:left">
				资源回退并作废：在作废基础上会将数据进行回退操作；
			</td>
		</tr>
	</table>
	</div>
	
</body>
</html>