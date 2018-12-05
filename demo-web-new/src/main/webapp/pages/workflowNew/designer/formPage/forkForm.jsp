<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/forkForm.js"></script>
<style type="text/css">
.form_table tr th{
	border: 1px solid #A6CACA;
	line-height: 30px;
}
.form_table tr td{
	border: 1px solid #A6CACA;
	line-height: 30px;
}
#expressionTable input{height:20px; line-height:20px; border:1px solid #d5d5d5; ;background-color: #f5f5f5;font-size: 12px;box-shadow: 1px 1px 2px #ededed;"}
</style>
</head>
<body style="overflow-y: auto;">
	<!-- 标准定义 -->
	<input type="hidden" value="${ctx}" id="url" />
	<input type="hidden" value="<c:out value="${param.state}"></c:out>"
		id="state" />

	<!-- 流程设计器相关参数定义 -->
	<input type="hidden" value="<c:out value="${param.id}"></c:out>"
		id="dNodeId" />
	<input type="hidden"
		value="<c:out value="${param.processDefinitionID}"></c:out>"
		id="modelId" />
	<input type="hidden"
		value="<c:out value="${param.processInstanceId}"></c:out>"
		id="instanceId" />

	<div id="menuContent" class="menuContent" style="position: absolute;">

	</div>

	<form id="forkForm">
		<table width="50%" cellpadding="0" cellspacing="0" border="0"
			style="text-align: left; line-height: 30px;">
			<tr>
				<td class="form_win_label"><icms-i18n:label name="process_node_name"/>：</td>
				<td colspan="2" align="left"><input type="text"
					class="form_input" id="forkName" name="forkName"
					style="width: 200px; height:22px; line-height:22px; border:1px solid #d5d5d5; ;background-color: #f5f5f5;padding:2px 4px;font-size: 12px;box-shadow: 1px 1px 2px #ededed;" /></td>
			</tr>
		</table>

		<div style="height: 160px; overflow: auto; margin-top: 10px;">
			<table id="expressionTable" class="form_table" width="100%"
				cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th style="text-align: center;"><icms-i18n:label name="process_expression"/></th>
						<th style="text-align: center;"><icms-i18n:label name="process_node_name"/></th>
						<th style="text-align: center;"><icms-i18n:label name="mw_l_operate"/></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>

		<div id="btnTable" style="margin-top: 10px; text-align:right; ">
			<input type="button" onclick="cancel();" value='<icms-i18n:label name="com_btn_cancel"/>' class="btn_gray" style="margin-left:0; margin-right:5px;"/>
			<input type="button" onclick="forkSave();" value='<icms-i18n:label name="com_btn_save"/>' class="btn_gray" style="margin-left:0; margin-right:5px;"/>
			<input type="button" onclick="addRow();" value='<icms-i18n:label name="process_route_add"/>' class="btn_gray" style="margin-left:0; margin-right:5px;"/>
		</div>
	</form>
</body>
</html>