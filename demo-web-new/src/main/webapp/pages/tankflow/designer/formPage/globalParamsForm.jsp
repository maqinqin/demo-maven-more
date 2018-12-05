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
<script type="text/javascript" src="js/globalParamsForm.js"></script>
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
	<input type="hidden" value="<c:out value="${param.nodeId}"></c:out>"
		id="nodeId" />
	<input type="hidden"
		value="<c:out value="${param.processDefinitionID}"></c:out>"
		id="modelId" />
	<input type="hidden"
		value="<c:out value="${param.processInstanceId}"></c:out>"
		id="instanceId" />

	<!-- 表单定义 -->
	<form id="globalParamsForm">
		<div style="height: 380px; overflow: auto;">
			<table width="90%" align="center" class="tf_form_table" id="propTable">
				<thead>
					<tr style="font-weight: bold;">
						<th width="22%" height="22" style="font-weight: bold;"><div align="center">参数Key</div></th>
						<th width="22%" height="22" style="font-weight: bold;"><div align="center">参数名称</div></th>
						<th width="22%" height="22" style="font-weight: bold;"><div align="center">参数值</div></th>
						<th width="14%" height="22" style="font-weight: bold;"><div align="center">是否手填</div></th>
						<th width="14%" height="22" style="font-weight: bold;"><div align="center">参数类型</div></th>
						<th id="basicOperate" width="20%" height="22" style="font-weight: bold;"><div align="center">操作</div></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>

		<div style="margin-top: 10px;" align="center">
			<input type="button" onclick="addGlobalParamsTr()" value="增加参数" style="height: 25px;"
				class="btn"  style="float:none;"/>
			<input type="button" onclick="paramsSave()" style="height: 25px;"
				value="保存" class="btn"  style="float:none;"/>
			<input type="button" onclick="cancel()" style="height: 25px;"
				value="取消" class="btn"  style="float:none;"/>
		</div>
	</form>
</body>
</html>