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
<script type="text/javascript" src="js/sub-process.js"></script>
<style type="text/css">
.tf_form_table tr td {
	border: 0px solid #A6CACA;
	line-height: 28px;
	padding: 5px 5px 5px 5px;
}
.tf_form_win_label {
	padding-left: 3px;
	padding: 5px 5px 5px 3px;
	text-align: right;
}
.tf_textInput1 {
	padding: 5px 5px 5px 5px;
	width: 50px;
	border: 1px solid #9a9a9a;
	height: 14px;
	line-height: 14px;
	color: #858585;
	background-color: #F4F3F3;
	border: 1px solid #d5d5d5;
	padding: 4px 4px 5px;
	font-size: 12px;
	border-radius:4px;
	text-align:center;
}
.tf_form_input {
	width: 180px;
	border: 1px solid #d5d5d5;
	height: 23px;
	line-height: 23px;
	color: #615d70;
	background-color: #f7f7f9;
	padding: 1px 4px 0px 5px;
	font-size: 12px;
	border: 1px solid #cbcad8;
	border-radius: 3px;
	background-color: #fff;
}
.tf_form_textarea {
	width: 220px;
	border: 1px solid #d5d5d5;
	height: 35px;
	line-height: 23px;
	color: #615d70;
	background-color: #f7f7f9;
	padding: 1px 4px 0px 5px;
	font-size: 12px;
	border: 1px solid #cbcad8;
	border-radius: 3px;
	background-color: #fff;
	resize: none;
}
.tf_form_select {
	width: 180px;
	height: 25px;
	border: 1px solid #d5d5d5;
	line-height: 23px;
	color: #615d70;
	background-color: #f7f7f9;
	padding: 1px 4px 0px 5px;
	font-size: 12px;
	border: 1px solid #cbcad8;
	border-radius: 3px;
	background-color: #fff;
}
.tf_form_check {
	display: inline-block;
	width: 18px;height: 18px;
	border: 1px solid #ccc;
	cursor: pointer;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	border-radius: 3px;
	vertical-align: middle;
}
.tf_select_button {
	margin-left: 2px;
	height: 38px;
	line-height: 23px;
	color: #eee;
	padding: 1px 5px 1px 5px;
	font-size: 12px;
	border: 1px solid #cbcad8;
	border-radius: 3px;
	background-color: #fff;
	display: inline;
}
input.error { border: 1px solid red; }
label.error {
	padding-left: 3px;
	padding-bottom: 2px;
	font-weight: bold;
	color: #EA5200;
}
label.checked {
}
body {font-size: 14px;
	font-family: Microsoft Yahei;
	color: #4c566c;
	background:#eee;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
</head>
<body >
   
	<!-- 标准定义 -->
	<input type="hidden" value="${ctx}" id="url" />
	<input type="hidden" value="<c:out value="${param.state}"></c:out>"
		id="state" />
	<input type="hidden" value="<c:out value="${param.tempTypeId}"></c:out>"
		id="tempTypeId" />
	<input type="hidden" value="<c:out value="${param.templateId}"></c:out>"
		id="templateId" />

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

	<form id="subProcessForm">
		<table class="tf_form_table" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td class="tf_form_win_label"><font class="font_r" color="red">*</font>任务名称：</td>
				<td><input type="text" id="tNodeName" name="tNodeName"
					class="tf_form_input" style="background-color: white;height: 20px"/> 
					<input type="hidden" id="tNodeId"
					name="tNodeId" class="form_input" /></td>
				<td class="tf_form_win_label"><font class="font_r" color="red">*</font>关联模板：</td>
				<td>
					<select id="subprocessCode" name="subprocessCode" class="tf_form_select"/> 
                </td>
			</tr>
			<tr>
				<td class="tf_form_win_label"><font class="font_r" color="red">*</font>执行方式：</td>
				<td>
			  		<select id="exeWay" name="exeWay" class="tf_form_select">
						<option value="0">同步执行</option>
						<option value="1" selected>异步执行</option>
					</select>
            	</td>
			</tr>
		</table>

		<table id="servicePolicyParamTable" width="100%" border="0"
			cellpadding="0" cellspacing="0"
			style="border: 1px solid #A6CACA; border-top: none;">
			<tbody>
			</tbody>
		</table>
		<div align="center" style="padding-top: 10px;">
			<input type="button" id="saveTemplateBtn" onclick="componentSave();" style="height: 25px;"
				value="保存" class="btn"  style="float:none;"/>
			<input type="button" id="cancelTemplateBtn" style="height: 25px;" 
				onclick="cancel();" value="取消" class="btn"  style="float:none;"/>
		</div>
	</form>

	
</body>
</html>