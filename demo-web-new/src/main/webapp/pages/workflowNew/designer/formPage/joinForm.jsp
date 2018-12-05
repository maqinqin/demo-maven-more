<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>聚合</title>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/jquery_common.jsp"%>
	<%@ include file="/common/zTree_load.jsp"%>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script type="text/javascript" src="js/joinForm.js"></script>
</head>
<body style="padding:10px;width:97%;">
	<!-- 上下文定义 -->
	<input type="hidden" value="${ctx}" id="url"/>
	<!-- 流程所处阶段定义：design/processDefinition/processInstance -->
	<input type="hidden" value="<c:out value="${param.state}"></c:out>" id="state"/>
	<!-- mxGraph节点Id -->
	<input type="hidden" value="<c:out value="${param.id}"></c:out>" id="dNodeId"/>
	<!-- 流程节点Id -->
	<input type="hidden" value="<c:out value="${param.nodeId}"></c:out>" id="nodeId"/>
	<!-- 流程模板Id -->
	<input type="hidden" value="<c:out value="${param.processDefinitionID}"></c:out>" id="modelId"/>
	<!-- 流程实例Id -->
	<input type="hidden" value="<c:out value="${param.processInstanceId}"></c:out>" id="instanceId"/>
	<!-- 管理权限 -->
	<input type="hidden" value="<c:out value="${param.userAdmin}"></c:out>" id="userAdmin"/>
	
	<!-- 聚合节点表单 -->
	<form id="joinForm">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td>
					<icms-i18n:label name="process_node_name"/>:
				</td>
				<td>
					<input id="dNodeName" name="dNodeName" type="text"/>
				</td>
				<td>
					<icms-i18n:label name="process_business_scenarios"/>:
				</td>
				<td>
					<select id="businessType" name="businessType" onchange="businessTypeChange()">
						<option value=""><icms-i18n:label name="res_l_comput_select"/></option>
					</select>
				</td>
			</tr>
		</table>
	
		<!-- 多路径审批表单 -->
		<div id="div_multiPathApprove" style="width:100%;display:none;">
			<div style="width:100%;height:30px;">
				<a href="#" onclick="addMpaData()" style="float:right;">
					<div class="panel_btn">
						<div class="btn_icon add_row_icon"></div>
						<span><icms-i18n:label name="process_add_route"/>&nbsp;&nbsp;</span>
					</div>
				</a>
			</div>	
			<div style="height:170px;overflow: auto;">
				<table id="tb_multiPathApprove" class="table_2" width="100%" cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr>
						<th width="120" ><icms-i18n:label name="process_parameter"/></th>
						<th width="120" ><icms-i18n:label name="process_expression"/></th>
						<th width="120" ><icms-i18n:label name="process_para_value"/></th>
						<th width="120" ><icms-i18n:label name="process_jump_node"/></th>
						<th width="60" ><icms-i18n:label name="mw_l_operate"/></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>	
		</div>
	
		<!-- 提交按钮 -->
		<table id="table_btn" style="position:absolute;bottom:-5px;width:100%">
			<tr id="btnTr">
				<td colspan="4" class="form_table_bottom" align="center">
					<input type="button" onclick="cancel();" value='<icms-i18n:label name="com_btn_cancel"/>' class="form_btn" /> 
					<input type="button" onclick="save();" value='<icms-i18n:label name="com_btn_save"/>' class="form_btn" /> 
				</td>
			</tr>
		</table>
	</form>
</body>
</html>