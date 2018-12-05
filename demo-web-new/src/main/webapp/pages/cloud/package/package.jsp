<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">

	function openDialog(divId, title, width, height) {
		$("#" + divId).dialog({
			autoOpen : true,
			modal : true,
			height : height,
			width : width,
			title : title,
			//resizable : false
		});
	}
	function edit1() {
		$("label.error").remove();
		openDialog("edit1", "<icms-i18n:label name="package_modify"/>", 650, 300);
		$("input[name='packageModelVO.operationType']").val($("input[name='packageModelVO.name']").val());
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		for (var i = 0; i < nodes.length; i++) {
			load(nodes[i].id, nodes[i].type);
		}
		$("#packageModelVO.name-error").val('');
	}
	
	
	$(document).ready(function() {
		
	}); 

</script>
<style type="text/css">
#edit1 i {text-align:left; padding-right:3px;}
#edit1 p{width:600px; margin-bottom:5px; margin-left:18px;  }
#edit1 p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
 label{display:block; padding-left:110px;}
</style>
<div id="div1" style="display: none; padding-top:20px;">
	<table border="0" cellpadding="0" cellspacing="0" class="report" width="97%">
		<tr>
			<th ><icms-i18n:label name="bim_l_packName"></icms-i18n:label>：</th>
			<td width="30%"><label id="packageModelVO_name" style="font-size: 14px;padding-left:0;"></label></td>
			<th><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</th>
			<td ><label id="packageModelVO_remark" style="font-size: 14px;padding-left:0;"></label></td>
		</tr>
		<tr>
			<th><icms-i18n:label name="bim_l_filePath"></icms-i18n:label>：</th>
			<td ><label id="packageModelVO_filePath" style="font-size: 14px; padding-left:0;"></label></td>
			<th><icms-i18n:label name="bim_l_principal"></icms-i18n:label>：</th>
			<td ><label id="packageModelVO_fzr" style="font-size: 14px;padding-left:0;"></label></td>
		</tr>
	</table>
	<p class="btnbar1" style="margin-left:26px;">
	<shiro:hasPermission name="script:createModel">
		<input type="button" value='<icms-i18n:label name="bim_l_addModelBtn"/>' onclick="toAdd(2)" class="btn btn_dd2" style="margin-right: 14px;margin-left: 0px;" /> 
	</shiro:hasPermission>
	<shiro:hasPermission name="script:updatePackage">
		<input type="button" value='<icms-i18n:label name="bim_l_editPackageBtn"/>' onclick="edit1()" class="btn btn_dd2" style="margin-right: 14px;margin-left: 0px;" />
	</shiro:hasPermission>
	<shiro:hasPermission name="script:deletePackage"> 
		<input style="margin-right: 14px;margin-left:0px;" type="button" value='<icms-i18n:label name="bim_l_deletePackageBtn"/>' onclick="deletes('',1)" class="btn btn_dd2" />
	</shiro:hasPermission>
	</p>
</div>
<div id="edit1" style="display:none;" class="pageFormContent">
	<form action="" id="forms1">
		<input name="packageModelVO.id" type=hidden id="packageModelVO.id">
		<input name="packageModelVO.operationType" type=hidden id="packageModelVO.operationType" value="">
			<p	style="height:40px;">
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="bim_l_packName"></icms-i18n:label>：</i>
					<input name="packageModelVO.name" id="packageModelVO.name" class="textInput" />
				</span>
				<span class="updateDiv_list">
					<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</i>
					<input name="packageModelVO.remark" id="packageModelVO.remark" class="textInput" />
				</span>
			</p>
			<p style="height:40px;">
				<span class="updateDiv_list">
					<i><icms-i18n:label name="bim_l_filePath"></icms-i18n:label>：</i>
					<input name="packageModelVO.filePath" id="packageModelVO.filePath" class="textInput" />
				</span>
				<span class="updateDiv_list">
					<i><icms-i18n:label name="bim_l_principal"></icms-i18n:label>：</i>
					<input name="packageModelVO.fzr" id="packageModelVO.fzr" class="textInput" />
				</span>
			</p>
	</form>
	<p class="btnbar btnbar1" style="text-align:right; width:570px;">
		<input type="button" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('edit1')" class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/>
		<input type="button" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="save(1)" class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;" />
	</p>
</div>
