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
<script type="text/javascript" src="js/commandForm.js"></script>
<style type="text/css">
.form_table tr td {
	border: 1px solid #A6CACA;
	line-height: 30px;
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
.form_table input{height:20px; width:168px; line-height:20px;border: 1px solid #d5d5d5;color: #615d70;background-color: #f5f5f5;padding:0px 4px; margin-left:4px;font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
.form_table select{height:22px;width:180px; line-height:22px; border: 1px solid #d5d5d5;color: #615d70;background-color: #f5f5f5; margin-left:4px;font-size: 12px;box-shadow: 1px 1px 2px #ededed;}
</style>
</head>
<body style="overflow-y: hidden;">
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

	<form id="serviceModuleForm">
		<table class="form_table" width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font><icms-i18n:label name="process_task_name"/></td>
				<td><input type="text" id="tNodeName" name="tNodeName"
					class="form_input" /> <input type="hidden" id="tNodeId"
					name="tNodeId" class="form_input" /></td>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font><icms-i18n:label name="process_time_out"/></td>
				<td><input type="text" id="outtime" name="outtime"
					class="form_input" />(s)</td>
			</tr>
			<tr>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font><icms-i18n:label name="process_task_error_handle"/></td>
				<td><icms-ui:dic id="exceptionCode" name="exceptionCode"
						kind="BPM_EXCEPTION_CODE" value="" showType="select"
						attr="" /></td>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font><icms-i18n:label name="process_auto_handle"/></td>
				<td style="text-align:left; margin-left:4px;"><input type="checkbox" name="isAutoNode" id="isAutoNode"
					checked="checked" style="width:20px; text-align:left;"></td>
			</tr>
			<tr>
				<td class="form_win_label"><icms-i18n:label name="process_command"/></td>
				<td colspan="3"><textarea id="commandContent"
						name="commandContent" style="width:312px; height:55px;border: 1px solid #d5d5d5;color: #615d70;background-color: #f5f5f5; margin:2px 4px; font-size: 12px;box-shadow: 1px 1px 2px #ededed;"></textarea></td>
			</tr>
			<tr>
				<td class="form_win_label"><font class="font_r"
					style="color: red;">*</font><icms-i18n:label name="process_service_strategy"/></td>
				<td colspan="3" ><select id="servicePolicyCode" name="servicePolicyCode"
				 onchange="buildModulePolicyParamTree('')"></select>
					<input type="hidden" id="servicePolicyId" name="servicePolicyId" /></td>
			</tr>
		</table>


		<table id="servicePolicyParamTable" width="100%" border="0"
			cellpadding="0" cellspacing="0" style="border: 1px solid #A6CACA;border-top: none;">
			<tbody>
			</tbody>
		</table>

		<div style="padding-top: 10px; text-align:right; ">
			<input type="button" id="cancelTemplateBtn" onclick="cancel();" value='<icms-i18n:label name="com_btn_cancel"/>' class="btn_gray" style="margin-left:0; margin-right:5px; color:#fff; heignt:30px; width:85px; float:right; border:none;"/>
			<input type="button" id="saveTemplateBtn" onclick="componentSave();" value='<icms-i18n:label name="com_btn_save"/>' class="btn_gray" style="margin-left:0; margin-right:5px; color:#fff; heignt:30px; width:85px; float:right; border:none; "/>
		</div>
	</form>

</body>
</html>