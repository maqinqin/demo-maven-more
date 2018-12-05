<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript">
<!--

	$(document).ready(function() {
		$("#forms1").validate({
			rules : {
				"sysOrganizationPo\\.orgName" : "required"
			},
			messages : {
				"sysOrganizationPo.orgName" : "组织机构名称不能为空! "
			}
		});
	});
//-->
</script>
<style>
#edit p{width:280px; padding:0; padding-left:16px;}
#edit p i{width:100px; text-align:left;}
/*#edit label{ display:block;  padding-left:120px;}*/
</style>
<div id="show" style="display: none; padding-top:20px;">
	<table border="0" class="report" width="97%" id=sysOrganizationPo>
		<tr>
			<th ><icms-i18n:label name="sys_l_orgName"></icms-i18n:label>：</th>
			<td width="30%"><label id="sysOrganizationPo_orgName"></label></td>
			<th><icms-i18n:label name="sys_l_orgDesc"></icms-i18n:label>：</th>
			<td><label id="sysOrganizationPo_remark"></label></td>
		</tr>
		<tr>
			<th ><icms-i18n:label name="sys_l_blogsOrg"></icms-i18n:label>：</th>
			<td><label id="sysOrganizationPo_parent_orgName"></label></td>
		</tr>
	</table>
	<p class="btnbar1" style="padding-left:40px;">
		<input type="button" class="btn btn_dd2" id="btn_add_scp" onclick="show('2')" value="<icms-i18n:label name="addsub"/>"
			style="margin-right: 5px;margin-left: 0px;" />
			<input type="button" class="btn btn_dd2" id="btn_mod_scp" onclick="show('1')"
			value="<icms-i18n:label name="edit"/>" style="margin-right: 5px;margin-left: 0px;" />
			<input type="button" class="btn btn_dd2" id="btn_mod_scp"
			onclick="deletes()" value="<icms-i18n:label name="delete"/>" style="margin-right: 5px;margin-left: 0px;" />
	</p>
</div>
<div id="edit" style="display:none;" class="pageFormContent">
	<form action="" id="forms1">
		<input id="sysOrganizationPo.orgId" type="hidden"> <input id="sysOrganizationPo.parent.orgId" type="hidden">

		<p>
			<i><font color="red">*</font><icms-i18n:label name="sys_l_orgName"></icms-i18n:label>：</i><input id="sysOrganizationPo.orgName" name="sysOrganizationPo.orgName" class="textInput">
		</p>
		<p>
			<i><icms-i18n:label name="sys_l_orgDesc"></icms-i18n:label>：</i>
			<textarea id="sysOrganizationPo.remark" name="sysOrganizationPo.remark" class="textInput" style="width:144px; margin-right:0;"></textarea>
		</p>
		<p>
			<i><icms-i18n:label name="sys_l_blogsOrg"></icms-i18n:label>：</i> <label id="sysOrganizationPo_parent_orgName2"></label>
		</p>
		<p class="winbtnbar btnbar1" style="text-align:right; margin-bottom:20px; width:282px;">
			<label id="sp_error_tip"></label> 
			<input type="button" id="btnAddSp" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>'
				onclick="closeView('edit')" style="margin-right: 25px; margin-left:0;" />
			<input type="button" id="btn_mod_sp" class="btn btn_dd2 btn_dd3" onclick="save( )" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>'
				style="margin-right: 5px;margin-left: 5px; margin-left:0;" />
		</p>
	</form>
</div>