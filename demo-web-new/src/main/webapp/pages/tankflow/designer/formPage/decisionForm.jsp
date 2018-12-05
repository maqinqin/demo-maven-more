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
			<table width="95%" align="center" border="0" style="line-height: 30px; border: 0;">
				<tr>
					<td style="text-align: left;  font-weight: bold; width: 100px; border: 0;">&nbsp;&nbsp;节点名称：</td>
					<td style="text-align: left; border: 0;"><input id="decisionName" name="decisionName" type="text"
						class="tf_form_input" style="width: 200px;" /></td>
			</table>
			<table id="expressionTable" align="center" class="tf_form_table" width="95%"
				style="margin-top: 10px;">
				<thead>
					<tr>
						<th style="text-align: center; font-weight: bold;">表达式</th>
						<th style="text-align: center; font-weight: bold;">路由名称</th>
						<th style="text-align: center; font-weight: bold;">节点名称</th>
						<th height="22" style="display: none; font-weight: bold;"><div align="center">节点ID</div></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<!-- <div id="btnTr" style="margin-top: 10px;" align="center">
			<input type="button" id="saveDecisionBtn" onclick="decisionSave();"
				value="保存" class="btn" /> <input type="button"
				id="cancelDecisionBtn" onclick="cancel();" value="取消" class="btn" />
		</div>
		 -->
		<div id="btnTr" align="center" style="padding-top: 10px;">
			<a href="javascript:void(0)" id="saveDecisionBtn" class="btn"
				title="保存" onclick="decisionSave();return false;"  style="float:none;">
				<span class="text">保存</span>
			</a>
			<a href="javascript:void(0)" id="cancelBtn"
				class="btn" title="取消" onclick="cancel();return false;" style="float:none;">
				<span class="text">取消</span>
			</a>
		</div>
	</form>
</body>
</html>