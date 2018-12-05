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
.form_table tr th,tr td {
	border: 1px solid #A6CACA;
	line-height: 30px;
}
#propTable input{height:20px; line-height:20px; font-size:12px; background-color: #f5f5f5;border: 1px solid #d5d5d5; box-shadow: 1px 1px 2px #ededed;}
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
		<div style="height: 180px; overflow: auto;">
			<table width="100%" align="center" class="form_table" id="propTable">
				<thead>
					<tr>
						<th width="25%" height="22"><div align="center"><icms-i18n:label name="process_para_key"/></div></th>
						<th width="35%" height="22"><div align="center"><icms-i18n:label name="process_para_name"/></div></th>
						<th width="25%" height="22"><div align="center"><icms-i18n:label name="process_para_value"/></div></th>
						<th id="basicOperate" width="15%" height="22"><div
								align="center"><icms-i18n:label name="mw_l_operate"/></div></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>

		<div style="margin-top: 10px;" align="right">
			<input type="button" onclick="cancel()" value='<icms-i18n:label name="com_btn_cancel"/>' class="btn_gray" style="margin-left:0; margin-right:5px;" />
			<input type="button" onclick="paramsSave()"	value='<icms-i18n:label name="com_btn_save"/>' class="btn_gray" style="margin-left:0; margin-right:5px;" /> 
			<input type="button" onclick="addGlobalParamsTr()" value='<icms-i18n:label name="process_para_add"/>'class="btn_gray" style="margin-left:0; margin-right:5px;"/> 
		</div>
	</form>
</body>
</html>