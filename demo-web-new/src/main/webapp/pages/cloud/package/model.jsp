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
	function edit2() {
		$("label.error").remove();
		openDialog("edit2", "<icms-i18n:label name="module_modify"/>", 650, 250);
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getSelectedNodes();
		for (var i = 0; i < nodes.length; i++) {
			load(nodes[i].id, nodes[i].type);
		}
	}
	$(document).ready(function() {
		
	});
	function ssb()
	{
	}
</script>
<style>
#edit2 i {text-align:left; padding-right:3px;}
#edit2 p{width:600px; margin-bottom:5px; margin-left:18px;  }
#edit2 p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
 label{display:block; padding-left:110px;}
</style>
<div id="div2" style="display: none; padding-top:20px;">
	<table border="0" cellpadding="0" cellspacing="0" class="report" width="97%">
		<tr>
			<th ><icms-i18n:label name="com_l_moduleName"></icms-i18n:label>：</th>
			<td  width="30%"><label id="modelModelVO_name" style="font-size: 14px; padding-left:0;"></label></td>
			<th><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</th>
			<td><label id="modelModelVO_remark" style="font-size: 14px; padding-left:0;"></label></td>
		</tr>
		<tr>
			<th><icms-i18n:label name="bim_l_filePath"></icms-i18n:label>：</th>
			<td><label id="modelModelVO_filePath" style="font-size: 14px; padding-left:0;"></label></td>
			<th><icms-i18n:label name="bim_l_blogsPack"></icms-i18n:label>：</th>
			<td><label id="modelModelVO_packageModelVO_name" style="font-size: 14px; padding-left:0;"></label></td>
		</tr>
	</table>
	<p class="btnbar1" style="margin-left:28px;">
		<shiro:hasPermission name="script:createScript">
			<input type="button" value='<icms-i18n:label name="bim_l_addScriptBtn"/>' onclick="toAdd(3)" class="btn btn_dd2" style="margin-left:0; margin-right:14px;" /> 
		</shiro:hasPermission>
		<shiro:hasPermission name="script:updateModel">
			<input type="button" value='<icms-i18n:label name="bim_l_editModelBtn"/>'	onclick="edit2()" class="btn btn_dd2" style="margin-left:0; margin-right:14px;"/>
		</shiro:hasPermission>
		<shiro:hasPermission name="script:deleteModel">
			<input type="button" value='<icms-i18n:label name="bim_l_deleteModelBtn"/>' onclick="deletes('',2)" class="btn btn_dd2" style="margin-left:0; margin-right:14px;"/>
		</shiro:hasPermission>
	</p>
</div>
<div id="edit2" style="display: none;" class="pageFormContent">
	<form action="" id="forms2">
		<input name="modelModelVO.id" id="modelModelVO.id" type=hidden> 
		<input type=hidden name="modelModelVO.packageModelVO.id" id="modelModelVO.packageModelVO.id">
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_moduleName"></icms-i18n:label>：</i>
				<input type = "text" name="modelModelVO.name" id="modelModelVO.name" class="textInput" value="" onblur="ssb()"/>
			</span>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</i>
				<input name="modelModelVO.remark" id="modelModelVO.remark" class="textInput" />
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="bim_l_filePath"></icms-i18n:label>：</i>
				<input name="modelModelVO.filePath" id="modelModelVO.filePath" class="textInput" />
			</span>
			</p>
		<p class="btnbar btnbar1" style="width:570px; text-align:right;">
			<input type="button" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('edit2')" class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/>
			<input type="button" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="save(2);" class="btn btn_dd2 btn_dd3" style="margin-left:0; margin-right:5px;"/>
		</p>
	</form>
</div>