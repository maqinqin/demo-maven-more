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
<script type="text/javascript" src="js/decisionForm.js"></script>
<style type="text/css">
.form_table tr th {
	border: 1px solid #A6CACA;
	line-height: 30px;
}

.form_table tr td {
	border: 1px solid #A6CACA;
	line-height: 30px;
}
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

	<form id="decisionForm">
		<div style="height: 200px; overflow: auto;">
			<table width="50%" border="0" style="line-height: 30px;">
				<tr>
					<td class="form_win_label" style="text-align: left;"><icms-i18n:label name="process_node_name"/>：</td>
					<td><input id="decisionName" name="decisionName" type="text"
						class="form_input" style="width: 200px; height:22px; line-height:22px; border:1px solid #d5d5d5; background-color: #f5f5f5;box-shadow: 1px 1px 2px #ededed; font-size:12px; padding:2px 4px;" /></td>
			</table>
			<table id="expressionTable" class="form_table" width="100%"
				style="margin-top: 10px;">
				<thead>
					<tr>
						<th style="text-align: center;"><icms-i18n:label name="process_expression"/></th>
						<th style="text-align: center;"><icms-i18n:label name="process_route_name"/></th>
						<th style="text-align: center;"><icms-i18n:label name="process_node_name"/></th>
						<th height="22" style="display: none"><div align="center"><icms-i18n:label name="process_node_id"/></div></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<div id="btnTr" style="margin-top: 10px; text-align:right;">
			<input type="button" id="cancelDecisionBtn" onclick="cancel();" value='<icms-i18n:label name="com_btn_cancel"/>' class="btn_gray" style="margin-left:0; margin-right:5px;" />
			<input type="button" id="saveDecisionBtn" onclick="decisionSave();" value='<icms-i18n:label name="com_btn_save"/>' class="btn_gray" style="margin-left:0; margin-right:5px;"/> 
		</div>
	</form>
</body>
</html>