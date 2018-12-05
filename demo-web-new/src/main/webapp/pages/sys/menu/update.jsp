<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript">
<!--
	//检查当前选中菜单级别
	/* 	jQuery.validator.addMethod("checkMenuLevel", function(value, element, param) {
	 var zTree = $.fn.zTree.getZTreeObj("tree");
	 //1 检查当前选中菜单级别 
	 var nodes = zTree.getSelectedNodes();
	 var node = nodes[0];
	 var level = 0;
	 while (node == null) {
	 node = node.getParentNode();
	 if (node != null)
	 level++;
	 }
	 alert(level);
	 if (level > 2) {
	 return false;
	 }
	 return false;
	 }, "菜单级别不能超过2级！"); */
	function checkMenuLevels() {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		//1 检查当前选中菜单级别 
		var nodes = zTree.getSelectedNodes();
		var node = nodes[0];
		var level = 0;
		while (node == null) {
			node = node.getParentNode();
			if (node != null)
				level++;
		}
		alert("菜单级别不能超过2级！");
		if (level > 2) {
			return false;
		}
		return false;
	}
	$(document).ready(function() {
		initParamsTable();
	});
	function initParamsTable() {
		var queryData = {
			"sysMenuPo.id" : $("#sysMenuPo\\.id").val()
		};
		$("#roles").jqGrid({
			url : ctx + "/sys/menu/roles.do",
			datatype : "json",
			postData : queryData,
			mtype : "post",
			autowidth : true,
			height : 210,
			colNames : [ '<icms-i18n:label name="role"/>', '<icms-i18n:label name="beizhu"/>' ],
			colModel : [ {
				name : "roleName",
				index : "roleName",
				sortable : false,
				align : 'left'
			}, {
				name : "remark",
				index : "remark",
				sortable : false,
				align : 'left'
			} ],
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "result.data",
				repeatitems : false
			},
			hidegrid : false,
			// wmy，添加下面的方法，表头的列名居左
			gridComplete: function(){
			               $( '#roles' ).closest("div.ui-jqgrid-view" )
			              .find( "div.ui-jqgrid-sortable" )
			              .css( "text-align" , "left" );
			          }

		});

		$(window).resize(function() {
			$("#roles").setGridWidth($("#rolesDiv").width());
		});

		$("#roles").closest(".ui-jqgrid-bdiv").css({
			'overflow-y' : 'scroll',
			'overflow-x' : 'hidden'
		});
	}
//-->
</script>
<style type="text/css">
.report tr th{padding-left:0;}
.btnbar1{padding-top:12px; padding-bottom:10px;}
#edit i {text-align:left; padding-right:3px;}
#edit p{width:600px; margin-bottom:5px; margin-left:18px;  }
#edit p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
/* #edit label{display:block; padding-left:104px;} */
#edit .textInput{width:160px;}
#show  th{padding-left:8px;}

</style>
<div id="show" style="display: none; padding-top:20px;">
	<table border="0" cellpadding="0" cellspacing="0" class="report" width="97%" id="sysMenuPo">
		<tr>
			<th><icms-i18n:label name="com_l_name"></icms-i18n:label>：</th>
			<td width="24%"><label id="sysMenuPo_menuName"></label></td>
			<th><icms-i18n:label name="sys_l_code"></icms-i18n:label>：</th>
			<td><label id="sysMenuPo_menuCode"></label></td>
		</tr>
		<tr>
			<th><icms-i18n:label name="sys_l_addr"></icms-i18n:label>：</th>
			<td><label id="sysMenuPo_menuUrl"></label></td>
			<th><icms-i18n:label name="sys_l_type"></icms-i18n:label>：</th>
			<td><label id="sysMenuPo_resourceType"></label></td>
		</tr>
		<tr>
			<th><icms-i18n:label name="sys_l_desc"></icms-i18n:label>：</th>
			<td><label id="sysMenuPo_menuDesc"></label></td>
			<th><icms-i18n:label name="sys_l_order"></icms-i18n:label>：</th>
			<td><label id="sysMenuPo_orderId"></label></td>
		</tr>
		<tr>
			<th><icms-i18n:label name="sys_l_blogsMenu"></icms-i18n:label>：</th>
			<td><label id="sysMenuPo_parent_name"></label></td>
		</tr>
		<tr>
			<th><icms-i18n:label name="sys_l_imageUrl"></icms-i18n:label>：</th>
			<td><label id="sysMenuPo_imageUrl"></label></td>
		</tr>
	</table>
	
	<p class="btnbar1">
		<shiro:hasPermission name="menu:save">
			<input type="button" class="btn btn_dd2 btn_dd3" id="btn_add_scp" onclick="show('2')" value="<icms-i18n:label name="add"/>"
				style="margin-right: 5px;margin-left: 5px;" />
			</shiro:hasPermission>
			<shiro:hasPermission name="menu:update">
			<input type="button" class="btn btn_dd2 btn_dd3" id="btn_mod_scp" onclick="show('1')" value="<icms-i18n:label name="edit"/>"
				style="margin-right: 5px;margin-left: 5px;" />
			</shiro:hasPermission>
			<shiro:hasPermission name="menu:delete">
			<input type="button" class="btn btn_dd2 btn_dd3" id="btn_mod_scp" onclick="deletes()" value="<icms-i18n:label name="delete"/>"
				style="margin-right: 5px;margin-left: 5px;" />
		</shiro:hasPermission>
	</p>
	<div class="pageTableBg pageTableBg2" style="width:100%; padding-top:0;">
		<div class="panel clear" id="rolesDiv" style="margin-top: 10px;">
		<table id="roles"></table>
	</div>
	</div>
	
	
</div>

<div id="edit" style="display:none;padding-top:0; padding-top:20px;" class="pageFormContent">
	<form action="" id="forms1">
		<p>
		<span class="updateDiv_list">
			<i><font color="red">*</font><icms-i18n:label name="com_l_name"></icms-i18n:label>：</i><input id="sysMenuPo.menuName" name="sysMenuPo.menuName" class="textInput">
		</span>
		<span class="updateDiv_list">
			<i><icms-i18n:label name="sys_l_code"></icms-i18n:label>：</i><input id="sysMenuPo.menuCode" class="textInput">
		</span>
		</p>
		<p>
		<span class="updateDiv_list">
			<i><icms-i18n:label name="sys_l_addr"></icms-i18n:label>：</i><input id="sysMenuPo.menuUrl" class="textInput">
		</span>
		<span class="updateDiv_list">
			<i><font color="red">*</font><icms-i18n:label name="sys_l_type"></icms-i18n:label>：</i><select id="sysMenuPo.resourceType" name="sysMenuPo.resourceType"
				class="selInput">
				<option value=""><icms-i18n:label name="res_l_comput_select"/></option>
				<option value="menu">menu</option>
				<option value="function">function</option>
			</select>
		</span>
		</p>
		<p>
		<span class="updateDiv_list">
			<i><icms-i18n:label name="sys_l_desc"></icms-i18n:label>：</i><textarea id="sysMenuPo.menuDesc" name="sysMenuPo.menuDesc" class="textInput"></textarea>
		</span>
		<span class="updateDiv_list">
			<i><icms-i18n:label name="sys_l_order"></icms-i18n:label>：</i><input id="sysMenuPo.orderId" class="textInput">
		</span>
		</p>
		<p>
		<span class="updateDiv_list">
			<i><icms-i18n:label name="sys_l_imageUrl"></icms-i18n:label>：</i><input id="sysMenuPo.imageUrl" class="textInput">
		</span>
		<span class="updateDiv_list">
			<i><icms-i18n:label name="sys_l_blogsMenu"></icms-i18n:label>：</i><label id="sysMenuPo_parent_name2"></label>
		</span>
		</p>
		<input id="sysMenuPo.id" type="hidden"> <input id="sysMenuPo.parent.id" type="hidden">
		<p class="winbtnbar btnbar1" style="text-align:right; margin-bottom:20px; width:598px;">
			<label id="sp_error_tip"></label>
			
			<input type="button" id="btnAddSp" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('edit')"
				style="margin-right: 30px; margin-left:0;" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' />
			<shiro:hasPermission name="menu:save">
			<input type="button" id="btn_mod_sp" class="btn btn_dd2 btn_dd3" onclick="save()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>'
				style="margin-right: 5px;margin-left: 0px;" />
			</shiro:hasPermission>
		</p>
	</form>
</div>