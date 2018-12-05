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
<script type="text/javascript" src="js/oaForm.js"></script>
<style type="text/css">
.form_table tr td {
	border: 1px solid #A6CACA;
	line-height: 30px;
}
.form_table input{height:20px; line-height:20px; border: 1px solid #d5d5d5;color: #615d70;background-color: #f5f5f5; margin-left:4px; font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
.form_table select{margin-left:4px;}
</style>
</head>
<body>
	<!-- url路径-->
	<input type="hidden" value="${ctx}" id="url" />

	<!-- 当前状态 -->
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

	<div id="menuContent" class="menuContent" style="position: absolute;z-index: 10000;">
		<ul id="operIdsTree" class="ztree"
			style="margin-top: 0; padding-lef: -30; width: 193px; height: 80px; display: none; position: absolute;border:1px solid #a6caca;background-color:#ffffff;"></ul>
	</div>

	<form id="OaForm">
		<table class="form_table" width="98%" cellpadding="0" cellspacing="0"
			border="0" style="margin: 5px;">
			<tr>
				<td style="padding-left: 5px;"><font color="red">*</font><icms-i18n:label name="process_node_name"/>:</td>
				<td><input type="text" class="textInput" id="NodeName"
					name="NodeName" style="width: 240px;" /></td>
			</tr>
			<tr>
				<td style="padding-left: 5px;">
					<font color="red">*</font>URL:
				</td>
				<td><input type="text" class="textInput" id="path" name="path"
					style="width: 240px;" /></td>
			</tr>
			<tr>
				<td style="padding-left: 5px;">
					<icms-i18n:label name="process_special_role"/>:
				</td>
				<td>
					<select class="selInput" id="specialRole" name="specialRole" onchange="specialRoleChangeHandler()" style="width:240px;">
						<option value="-1"><icms-i18n:label name="res_l_comput_select"/></option> 
						<option value="1"><icms-i18n:label name="process_create_user"/></option>
					</select>
				</td>
			</tr>
			<tr class="userGroupTr">
				<td style="padding-left: 5px;">
					<font color="red">*</font><icms-i18n:label name="process_working_group"/>:
				</td>
				<td>
					<icms-ui:dic id="userGroup" name="userGroup"
								sql="select role_id as id, role_id as value,role_name as name,'0' kind from SYS_ROLE where is_active = 'Y' "
								showType="select" attr="class='selInput' style='width:240px;' onchange='getOperIds()'" />
				</td>
			</tr>
			<tr class="userGroupTr">
				<td style="padding-left: 5px;">
					<icms-i18n:label name="process_deal_user"/>:
				</td>
				<td>
					<!-- <input type="text" class="textInput" id="operIds" name="operIds" style="width: 200px;" 
						readonly="readonly" onclick="showMenu()"/>-->
					<select id="operId" name="operId" class="selInput" style="width:240px;">
					</select>
				</td>
			</tr>
		</table>

		<div style="margin-top: 15px; text-align: right;">
			<input type="button" id="cancelDecisionBtn" onclick="cancel();" value='<icms-i18n:label name="com_btn_cancel"/>' class="btn_gray" style="margin-left:0; margin-right:5px;" />
			<input type="button" id="saveDecisionBtn" onclick="save();"	value='<icms-i18n:label name="com_btn_save"/>' class="btn_gray" style="margin-left:0; margin-right:5px;"/> 
		</div>
	</form>

	<div id="todoList" class="tablediv2"
		style="padding-top: 10px; display: none;">
		<table id="todoListTable"></table>
		<div id="pageNav"></div>
	</div>
</body>
</html>