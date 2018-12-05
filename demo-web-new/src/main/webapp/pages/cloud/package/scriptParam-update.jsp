<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title></title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>

<script type="text/javascript" src="<%=basePath%>scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
	var pid = "${params['pid']}";
	var id = "${params['id']}";
	function init() {

		$("#scriptParamModelVO\\.id").val(id);
		$("#scriptParamModelVO\\.scriptModelVO\\.id").val(pid);
		var params = "params['id']=" + id + "&params['type']=4";
		$.ajax({
			type : "post",
			url : "<%=basePath%>packageModel/ajax/load.do",
			data : params,
			beforeSend : function(XMLHttpRequest) {

			},
			success : function(data, textStatus) {
				$("#scriptParamModelVO\\.id").val(data.result.data.id);
				$("#scriptParamModelVO\\.name").val(data.result.data.name);
				$("#scriptParamModelVO\\.code").val(data.result.data.code);
				$("#scriptParamModelVO\\.splitChar").val(
						data.result.data.splitChar);
				$("#scriptParamModelVO\\.paramType").val(
						data.result.data.paramType);
				$("#scriptParamModelVO\\.paramValType").val(
						data.result.data.paramValType);
				$("#scriptParamModelVO\\.orders").val(data.result.data.orders);
				$("#scriptParamModelVO\\.scriptModelVO\\.id").val(
						data.result.data.scriptModelVO.id);
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				alert("添加失败！");
				flage = false;
			}
		});
	}
	function save() {

		var params = $("#forms").serialize();
		params += "&params['type']=4";
		$.ajax({
			type : "post",
			url : "<%=basePath%>packageModel/ajax/save.do",
			data : params,
			beforeSend : function(XMLHttpRequest) {

			},
			success : function(data, textStatus) {
				alert(data.result.code);
				window.close();
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				alert("添加失败！");
				flage = false;
			}
		});
	}
</script>
</head>

<body onload="init();" class="main1">
<center>
	<div id="div1" style="width:100%;background-color: #DFDFDF">
		<form action="packageModel/save.do" id="forms">
			<input id="scriptParamModelVO.scriptModelVO.id" type=hidden
				name="scriptParamModelVO.scriptModelVO.id"> <input
				id="scriptParamModelVO.id" type=hidden name="scriptParamModelVO.id">
			<table>
				<tr>
					<td align=right><icms-i18n:label name="com_l_paramName"></icms-i18n:label>：</td>
					<td><input id="scriptParamModelVO.name"
						name="scriptParamModelVO.name"></td>
				</tr>
				<tr>
					<td align=right><icms-i18n:label name="bim_l_paramCode"></icms-i18n:label>：</td>
					<td><input id="scriptParamModelVO.code"
						name="scriptParamModelVO.code"></td>
				</tr>
				<tr>
					<td align=right><icms-i18n:label name="bim_l_spaceMark"></icms-i18n:label>：</td>
					<td><input id="scriptParamModelVO.splitChar"
						name="scriptParamModelVO.splitChar"></td>
				</tr>
				<tr>
					<td align=right><icms-i18n:label name="com_l_paramType"></icms-i18n:label>：</td>
					<td><input id="scriptParamModelVO.paramType"
						name="scriptParamModelVO.paramType"></td>
				</tr>
				<tr>
					<td align=right><icms-i18n:label name="bim_l_paramValType"></icms-i18n:label>：</td>
					<td><input id="scriptParamModelVO.paramValType"
						name="scriptParamModelVO.paramValType"></td>
				</tr>
				<tr>
					<td align=right><icms-i18n:label name="bim_l_order"></icms-i18n:label>：</td>
					<td><input id="scriptParamModelVO.orders"
						name="scriptParamModelVO.orders"></td>
				</tr>
				<tr>
					<td align=center>
						<button onclick="save(); return false;"><icms-i18n:label name="com_btn_save"></icms-i18n:label></button>
						<button onclick="window.close();"><icms-i18n:label name="com_btn_close"></icms-i18n:label></button>
						</td>
				</tr>
			</table>
		</form>
	</div></center>
</body>
</html>
