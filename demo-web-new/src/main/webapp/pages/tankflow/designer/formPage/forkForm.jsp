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
		<table width="95%" align="center" cellpadding="0" cellspacing="0" border="0"
			style="text-align: left; line-height: 30px;">
			<tr>
				<td style="font-weight: bold; width: 100px; border: 0;">节点名称：</td>
				<td colspan="2" align="left" style="border: 0;">
					<input type="text" class="tf_form_input" id="forkName" name="forkName" style="width: 200px;" />
				</td>
			</tr>
		</table>
		<div id="btnTable" align="center" style="padding-top: 10px;">
			<a href="javascript:void(0)" id="addRowBtn" class="btn"
				title="增加路由" onclick="addRow();return false;" style="float:none;">
				<span class="text">增加路由</span>
			</a>
			<a href="javascript:void(0)" id="forkSaveBtn"
				class="btn" title="保存" onclick="forkSave();return false;" style="float:none;">
				<span class="text">保存</span>
			</a>
			<a href="javascript:void(0)" id="cancelBtn"
				class="btn" title="取消" onclick="cancel();return false;" style="float:none;">
				<span class="text">取消</span>
			</a>
		</div>
		<br>
		<div style="height: 160px; overflow: no; margin-top: 10px;">
			<table id="expressionTable" class="tf_form_table" width="95%" align="center"
				cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th style="text-align: center; font-weight: bold;">表达式</th>
						<th style="text-align: center; font-weight: bold;">节点名称</th>
						<th style="text-align: center; font-weight: bold;">操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>

	</form>
</body>
</html>