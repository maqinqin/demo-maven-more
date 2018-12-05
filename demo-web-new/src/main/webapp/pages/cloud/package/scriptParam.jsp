<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript">
	function openAddParams(scriptParamId) {
		$("#scriptParamModelVO\\.id").val('');
		$("#scriptParamModelVO\\.name").val('');
		$("#scriptParamModelVO\\.code").val('');
		$("#scriptParamModelVO\\.splitChar").val('');
		$("#scriptParamModelVO\\.paramType").val('');
		$("#scriptParamModelVO\\.paramValType").val('');
		$("#scriptParamModelVO\\.orders").val('');
		if (scriptParamId != "") {
			var params = "params['id']=" + scriptParamId + "&params['type']=4";
			$.ajax({
				type : "post",
				url : ctx + "/cloud-service/script/load.do",
				data : params,
				beforeSend : function(XMLHttpRequest) {

				},
				success : function(data, textStatus) {
					$("#scriptParamModelVO\\.id").val(data.result.data.id);
					$("#scriptParamModelVO\\.name").val(data.result.data.name);
					$("#scriptParamModelVO\\.code").val(data.result.data.code);
					$("#scriptParamModelVO\\.splitChar").val(data.result.data.splitChar);
					$("#scriptParamModelVO\\.paramType").val(data.result.data.paramType);
					$("#scriptParamModelVO\\.paramValType").val(data.result.data.paramValType);
					$("#scriptParamModelVO\\.orders").val(data.result.data.orders);
					$("#scriptParamModelVO\\.scriptModelVO\\.id").val(data.result.data.scriptModelVO.id);
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
		$("label.error").remove();
		openDialog("div4", i18nShow('zh2'), 340, 450);
	}
	function saveparams() {
		if (!$("#forms4").valid()) {
			return false;
		} else {

			$("#forms4").validate();
			var params = $("#forms4").serialize();
			params += "&params['type']=4";
			$.ajax({
				type : "post",
				url : ctx + "/cloud-service/script/save.do",
				data : params,
				beforeSend : function(XMLHttpRequest) {

				},
				success : function(data, textStatus) {
					reloadParamsTable(data.result.data.scriptModelVO.id);
					closeView('div4');
				},
				complete : function(XMLHttpRequest, textStatus) {
				},
				error : function() {
					alert("添加失败！");
					flage = false;
				}
			});
		}
	}
	$(document).ready(function() {
		$("#forms4").validate({
			rules : {
				"scriptParamModelVO.name" :{required:true},
				"scriptParamModelVO.code":{required:true},
				"scriptParamModelVO.orders":{required:true,number : true,rangelength:[0,3]}
				
			},
			messages : {
				"scriptParamModelVO.name" :{required:"<icms-i18n:label name="para_name_not_null"/>"},
				"scriptParamModelVO.code":{required:"<icms-i18n:label name="para_code_not_null"/>"},
				"scriptParamModelVO.orders":{required:"<icms-i18n:label name="para_order_not_null"/>",number : "<icms-i18n:label name="para_order_input_number"/>",rangelength:"<icms-i18n:label name="para_order_mini"/>"}
			}
		});
	});
		
</script>
<div id="div4" style="display: none;text-align: center;" class="pageFormContent">
	<form action="packageModel/save.do" id="forms4">
		<input id="scriptParamModelVO.scriptModelVO.id" type=hidden name="scriptParamModelVO.scriptModelVO.id"> <input
			id="scriptParamModelVO.id" type=hidden name="scriptParamModelVO.id">
			<p style="width:290px;">
				<i><font color="red">*</font><icms-i18n:label name="com_l_paramName"></icms-i18n:label>：</i>
				<input id="scriptParamModelVO.name" name="scriptParamModelVO.name" class="textInput" />
			</p>
			<p style="width:290px;">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_paramCode"></icms-i18n:label>：</i>
				<input id="scriptParamModelVO.code" name="scriptParamModelVO.code" class="textInput" />
			</p>
			<p style="width:290px;">
				<i><icms-i18n:label name="bim_l_spaceMark"></icms-i18n:label>：</i>
				<input id="scriptParamModelVO.splitChar" name="scriptParamModelVO.splitChar" class="textInput" />
			</p>
			<p style="width:290px;">
				<i><icms-i18n:label name="com_l_paramType"></icms-i18n:label>：</i>
				<input id="scriptParamModelVO.paramType" name="scriptParamModelVO.paramType" class="textInput" />
			</p>
			<p style="width:290px;">
				<i><icms-i18n:label name="bim_l_paramValType"></icms-i18n:label>：</i>
				<input id="scriptParamModelVO.paramValType" name="scriptParamModelVO.paramValType" class="textInput" />
			</p>
			<p style="width:290px;">
				<i><font color="red">*</font><icms-i18n:label name="bim_l_order"></icms-i18n:label>：</i>
				<input id="scriptParamModelVO.orders" name="scriptParamModelVO.orders" class="textInput" />
			</p>
	</form>
	<p class="btnbar btnbar1" style="margin-right: 0px; margin-left:100px; width:183px;">
		<input type="button" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('div4')" class="btn btn_dd2 btn_dd3" style="margin-right: 5px; margin-left:0;" />
		<input type="button" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveparams();" class="btn btn_dd2 btn_dd3" style="margin-right: 5px; margin-left:0;" /> 
			<input type="hidden" id="updateFlag" name="updateFlag" value="1"/>
			<input type="hidden" id="deleteFlag" value="1"/>
	</p>
</div>