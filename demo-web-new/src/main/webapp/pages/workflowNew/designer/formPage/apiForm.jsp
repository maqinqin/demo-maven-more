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
<script type="text/javascript" src="js/apiForm.js"></script>
<style type="text/css">
.form_table tr td {
	border: 1px solid #A6CACA;
	line-height: 28px;
}

.form_table2 tr td {
	border: 1px solid #A6CACA;
	border-top: none; //
	border-left: none; //
	border-right: none;
	border-bottom: none;
}

.form_win_label {
	padding-left: 5px;
}
.form_table checkbox{margin-left:4px;}
.form_table input{margin-left:5px; width:178px;height:20px; border:1px solid #d5d5d5; line-height:20px; background-color: #f5f5f5;font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
.form_table select{margin-left:5px;width:180px;height:22px; border:1px solid #d5d5d5; line-height:22px; background-color: #f5f5f5;font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
</style>
</head>
<body >

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
	<input type="hidden" value="<c:out value="${param.userAdmin}"></c:out>"
		id="userAdmin" />

<!-- api节点信息form start -->
	<form id="serviceModuleForm" style="height:200px;">
		<table class="form_table" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td class="form_win_label"><font class="font_r" color="red">*</font><icms-i18n:label name="process_task_name"/></td>
				<td><input type="text" id="tNodeName" name="tNodeName"
					class="form_input" /> <input type="hidden" id="tNodeId"
					name="tNodeId" class="form_input" /></td>
				<td class="form_win_label"><font class="font_r" color="red">*</font><icms-i18n:label name="process_time_out"/></td>
				<td><input type="text" id="outtime" name="outtime"
					class="form_input" />(s)</td>
			</tr>
			<tr>
				<td class="form_win_label"><font class="font_r" color="red">*</font><icms-i18n:label name="process_task_error_handle"/></td>
				<td><icms-ui:dic id="exceptionCode" name="exceptionCode"
						kind="BPM_EXCEPTION_CODE" value="" showType="select"
						attr="" /></td>
				<td class="form_win_label"><icms-i18n:label name="process_auto_handle"/></td>
				<td><input type="checkbox" name="isAutoNode" id="isAutoNode"
					checked="checked" style="width:20px; text-align:left;"></td>
			</tr>
			<tr>
				<td class="form_win_label"><font class="font_r" color="red">*</font><icms-i18n:label name="process_service_strategy"/></td>
				<td colspan="3"><select id="servicePolicyCode"
					name="servicePolicyCode" class="form_select"
					onchange="buildModulePolicyParamTree('')"></select> <input
					type="hidden" id="servicePolicyId" name="servicePolicyId" /></td>
			</tr>
		</table>

		<table id="servicePolicyParamTable" width="100%" border="0"
			cellpadding="0" cellspacing="0"
			style="border: 1px solid #A6CACA; border-top: none;">
			<tbody>
			</tbody>
		</table>
		<div align="center" style="margin-top: 10px; text-align:right; height:50px;">
			<input type="button" id="cancelTemplateBtn"	onclick="cancel();" value='<icms-i18n:label name="com_btn_cancel"/>' class="btn_gray" style="margin-left:0; margin-right:5px; float:right;height:28px; width:85px; text-align:center; border:none;" />
			<input type="button" id="saveTemplateBtn" onclick="componentSave();" value='<icms-i18n:label name="com_btn_save"/>' class="btn_gray" style="margin-left:0; margin-right:5px; float:right; height:28px; width:85px; text-align:center; border:none; " /> 
		</div>
	</form>
<!-- api节点信息form end -->


	<!-- 选择脚本树弹出框 start -->
	<div id="shellTreeDiv" class="form_main_div" style="display:none;">
		<div style="overflow: auto;height: 220px;">
			<ul id="shellTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<input id="cancelShellBtn" class="btn_cancel" type="button" onclick="cancelCustom('shellTreeDiv')" value='<icms-i18n:label name="com_btn_cancel"/>' >
			<input id="saveShellBtn" class="btn_ok" type="button" value='<icms-i18n:label name="com_btn_save"/>' onclick="saveCustom('shellTreeDiv')">
		</div>
	</div>
	<!-- 选择脚本树弹出框 end -->
	<!-- 选择服务器树弹出框 start -->
	<div id="serverTreeDiv" class="form_main_div" style="display:none;">
		<div style="overflow: auto;height: 220px;">
			<ul id="serverTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<input id="cancelServerBtn" class="btn_cancel" type="button" onclick="cancelCustom('serverTreeDiv')" value='<icms-i18n:label name="com_btn_cancel"/>' >
			<input id="saveServerBtn" class="btn_ok" type="button" value=<icms-i18n:label name="com_btn_save"/> onclick="saveCustom('serverTreeDiv')">
		</div>
	</div>
	<!-- 选择服务器树弹出框 end -->
	<!-- 选择软件包树弹出框 start -->
	<div id="softwareTreeDiv" class="form_main_div" style="display:none;">
		<div style="overflow: auto;height: 220px;">
			<ul id="softwareTreeDemo" class="ztree" style="width: 300px;"></ul>
		</div>
		<div style="text-align: center;">
			<input id="cancelsoftwareBtn" class="btn_cancel" type="button" onclick="cancelCustom('softwareTreeDiv')" value='<icms-i18n:label name="com_btn_cancel"/>'>
			<input id="savesoftwareBtn" class="btn_ok" type="button" value='<icms-i18n:label name="com_btn_save"/>' onclick="saveCustom('softwareTreeDiv')">
		</div>
	</div>
	<!-- 选择服务器树弹出框 end -->
	
	
</body>
</html>