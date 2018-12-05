<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<link rel="stylesheet" type="text/css" href="/icms-web/css/new.css"></link>
<title>菜单管理</title>
<script type="text/javascript">
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : ""
			}
		},
		callback : {
			onMouseDown : onMouseDown
		},
		view:{
			showLine:false
		}
	};

	$(document).ready(function() {
		jQuery.validator.addMethod("menuNameCheck", function(value, element) {
			var validateValue = true;
			$.ajax({
				type : 'post',
				datatype : "json",
				data : {
					"sysMenuPo.menuName" : value,
					"sysMenuPo.id" : $("#sysMenuPo\\.id").val()
				},
				url : ctx + "/sys/menu/isrepeat.action",
				async : false,
				success : (function(data) {
					validateValue = data.result.data;
				}),
			});
			return validateValue;
		}, i18nShow('validate_data_remote'));

		$("#forms1").validate({
			rules : {
				"sysMenuPo.menuName" : {
					required : true,
					menuNameCheck : true
				}
			},
			messages : {
				"sysMenuPo.menuName" : {
					required : i18nShow('validate_data_required')
				}
			}
		});
		var t = $("#tree");
		// 加载左侧树
		$.ajax({
			type : "post",
			url : ctx + "/sys/menu/loadtree.do",
			data : {},
			beforeSend : function(XMLHttpRequest) {
			},
			success : function(data, textStatus) {
				var zNodes = data.list;
				var cloudMenu = {
					'id' : '0',
					'name' : '云管菜单',
					'pid' : '',
					'open' : true	
				};
				var tenantMenu = {
						'id' : '-1',
						'name' : '租户菜单',
						'pid' : '',
						'open' : true	
					};
				zNodes.push(cloudMenu);
				zNodes.push(tenantMenu);
				t = $.fn.zTree.init(t, setting, zNodes);
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				alert(i18nShow('tip_save_fail'));
				flage = false;
			}
		});
	});
	function onMouseDown(event, treeId, treeNode) {
		if(treeNode != null  && treeNode.id != null){
			if ( treeNode.id != '0' && treeNode.id != '-1' ) {
				load(treeNode.id);
				var queryData = {
					"sysMenuPo.id" : treeNode.id
				};
				$("#roles").jqGrid("setGridParam", {
					postData : queryData
				}).trigger("reloadGrid");
			} else {
				$("#sysMenuPo\\.parent\\.id").val(treeNode.id);
				$("#showRoot").show();
				$("#show").hide();
				$("#edit").hide();
			}
		}
		
	}
	function load(nodeId) {
		$.ajax({
			type : "post",
			url : ctx + "/sys/menu/load.do",
			data : {
				"sysMenuPo.id" : nodeId
			},
			success : function(data, textStatus) {
				$("#showRoot").hide();
				$("#show").show();
				$("#roles").setGridWidth($("#rolesDiv").width());
				var zTree = $.fn.zTree.getZTreeObj("tree");
				$("#sysMenuPo_menuName").text(data.sysMenuPo.menuName);
				$("#sysMenuPo_menuCode").text(data.sysMenuPo.menuCode);
				$("#sysMenuPo_menuUrl").text(data.sysMenuPo.menuUrl);
				if (data.sysMenuPo.resourceType == "menu") {
					$("#sysMenuPo_resourceType").html("<span class='tip_blue'>"+i18nShow('sys_menu_menu')+"</span>");
				} else {
					$("#sysMenuPo_resourceType").html("<span class='tip_green'>"+i18nShow('sys_menu_function')+"</span>");
				}

				//$("#sysMenuPo_resourceType").text(data.sysMenuPo.resourceType);
				$("#sysMenuPo_menuDesc").text(data.sysMenuPo.menuDesc);
				$("#sysMenuPo_orderId").text(data.sysMenuPo.orderId);
				
				if(data.sysMenuPo.imageUrl == null || data.sysMenuPo.imageUrl == ""){
					$("#sysMenuPo_imageUrl").text(i18nShow('sys_menu_no_pic'));
				}
				else{
					$("#sysMenuPo_imageUrl").text(data.sysMenuPo.imageUrl);
				}
				

				var pnode = zTree.getNodeByParam("id", data.sysMenuPo.parent.id, null);
				if (pnode != null) {
					$("#sysMenuPo_parent_name").text(pnode.name);
					$("#sysMenuPo_parent_name2").text(pnode.name);
				} else {
					$("#sysMenuPo_parent_name").text("");
					$("#sysMenuPo_parent_name2").text("");
				}
				$("#sysMenuPo\\.id").val(data.sysMenuPo.id);

				$("#sysMenuPo\\.menuName").val(data.sysMenuPo.menuName);
				$("#sysMenuPo\\.menuCode").val(data.sysMenuPo.menuCode);
				$("#sysMenuPo\\.menuUrl").val(data.sysMenuPo.menuUrl);
				$("#sysMenuPo\\.resourceType").val(data.sysMenuPo.resourceType);
				$("#sysMenuPo\\.menuDesc").val(data.sysMenuPo.menuDesc);
				$("#sysMenuPo\\.orderId").val(data.sysMenuPo.orderId);
				$("#sysMenuPo\\.parent\\.id").val(data.sysMenuPo.parent.id);
				$("#sysMenuPo\\.imageUrl").val(data.sysMenuPo.imageUrl);
			},
			error : function() {
				alert(i18nShow('tip_save_fail'));
				flage = false;
			}
		});
	}
	function save() {
		if (!$('#forms1').valid()) {
			return false;
		} else {
			if ($("#sysMenuPo\\.resourceType").val() == "") {
				showTip(i18nShow('tip_sys_menu_type'));
				return false;
			}
			if ($("#sysMenuPo\\.parent\\.id").val() == "") {
				$("#sysMenuPo\\.parent\\.id").val("0");
			}
			var params = {
				'sysMenuPo.id' : $("#sysMenuPo\\.id").val(),
				'sysMenuPo.menuName' : $("#sysMenuPo\\.menuName").val(),
				'sysMenuPo.menuCode' : $("#sysMenuPo\\.menuCode").val(),
				'sysMenuPo.menuUrl' : $("#sysMenuPo\\.menuUrl").val(),
				'sysMenuPo.resourceType' : $("#sysMenuPo\\.resourceType").val(),
				'sysMenuPo.menuDesc' : $("#sysMenuPo\\.menuDesc").val(),
				'sysMenuPo.orderId' : $("#sysMenuPo\\.orderId").val(),
				'sysMenuPo.parent.id' : $("#sysMenuPo\\.parent\\.id").val(),
				'sysMenuPo.imageUrl': $("#sysMenuPo\\.imageUrl").val(),
			};
			$.ajax({
				type : "post",
				url : ctx + "/sys/menu/save.do",
				data : params,
				beforeSend : function(XMLHttpRequest) {
				},
				success : function(data, textStatus) {
					var zTree = $.fn.zTree.getZTreeObj("tree");
					var node = zTree.getNodeByParam("id", data.sysMenuPo.id, null);
					if (node == null) {
						var pnode = null;
						node = {};
						node.name = data.sysMenuPo.menuName;
						node.id = data.sysMenuPo.id;
						if (data.sysMenuPo.parent.id == "") {
							node.pid = "0";
						} else {
							node.pid = data.sysMenuPo.parent.id;
						}
						pnode = zTree.getNodeByParam("id", node.pid, null);

						if (("-1" != data.sysMenuPo.parent.id || "0" != data.sysMenuPo.parent.id ) && "menu" == data.sysMenuPo.resourceType) {
							node.icon = ctx + "/css/zTreeStyle/img/icons/twolevelmenu.png";
						}
						if ("function" == data.sysMenuPo.resourceType) {
							node.icon = ctx + "/css/zTreeStyle/img/icons/function.png";
						}
						if (("-1" != data.sysMenuPo.parent.id || "0" != data.sysMenuPo.parent.id ) && "menu" == data.sysMenuPo.resourceType) {
							node.icon = ctx + "/css/zTreeStyle/img/icons/onelevelmenu.png";
						}
					zTree.addNodes(pnode, node, true);
					} else {
						node.name = data.sysMenuPo.menuName;
					}
					zTree.updateNode(node, true);
					showTip(i18nShow('tip_save_success'));
					$("#show").hide();
					closeView("edit");
					$("#showRoot").show();
				},
				complete : function(XMLHttpRequest, textStatus) {
				},
				error : function() {
					showTip(i18nShow('tip_save_fail'));
					flage = false;
				}
			});
		}
	}
	function deletes() {
		var params = {
			'sysMenuPo.id' : $("#sysMenuPo\\.id").val()
		};

		showTip(i18nShow('tip_delete_confirm'), function() {
			$.ajax({
				type : "post",
				url : ctx + "/sys/menu/delete.do",
				data : params,
				beforeSend : function(XMLHttpRequest) {
				},
				success : function(data, textStatus) {
					var zTree = $.fn.zTree.getZTreeObj("tree");
					var node = zTree.getNodeByParam("id", $("#sysMenuPo\\.id").val(), null);
					zTree.removeNode(node, false);
					showTip(i18nShow('tip_delete_success'));
				},
				complete : function(XMLHttpRequest, textStatus) {
				},
				error : function() {
					showTip(i18nShow('tip_delete_fail'));
					flage = false;
				}
			});
		});
	}
	function show(type) {
		$("#sysMenuPo\\.id").val("");
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var nodes = zTree.getSelectedNodes();
		if (type == "1") {
			for (var i = 0; i < nodes.length; i++) {
				load(nodes[i].id);
			}
			$("label.error").remove();
			openDialog("edit", i18nShow('sys_menu_update'), 654, 375);
		} else if (type == "0") {
			$("#sysMenuPo\\.id").val("");
			$("#sysMenuPo\\.menuName").val("");
			$("#sysMenuPo\\.menuCode").val("");
			$("#sysMenuPo\\.menuUrl").val("");
			$("#sysMenuPo\\.imageUrl").val("");
			$("#sysMenuPo\\.resourceType").val("");
			$("#sysMenuPo\\.menuDesc").val("");
			$("#sysMenuPo\\.orderId").val("");
			//$("#sysMenuPo\\.parent\\.id").val("0");
			$("#sysMenuPo_parent_name").text("");
			$("#sysMenuPo_parent_name2").text(i18nShow('sys_menu_res'));
			$("label.error").remove();
			openDialog("edit", i18nShow('sys_menu_save'), 654, 375);
		} else {
			for (var i = 0; i < nodes.length; i++) {
				$("#sysMenuPo_parent_name2").text(nodes[i].name);
				$("#sysMenuPo\\.parent\\.id").val(nodes[i].id);
			}
			$("#sysMenuPo\\.id").val("");
			$("#sysMenuPo\\.menuName").val("");
			$("#sysMenuPo\\.menuCode").val("");
			$("#sysMenuPo\\.menuUrl").val("");
			$("#sysMenuPo\\.resourceType").val("");
			$("#sysMenuPo\\.menuDesc").val("");
			$("#sysMenuPo\\.orderId").val("");
			$("#sysMenuPo\\.imageUrl").val("");
			$("label.error").remove();
			openDialog("edit", i18nShow('sys_menu_save_son'), 654, 375);
		}
	}
	function closeView(divId) {
		$("#" + divId).dialog("close");
	}
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
</script>
<style type="text/css">
html,body {
	height: 100%
}
.error{
	padding-left:105px;
}
#edit p{
	height:40px;
}
</style> 
</head>
<body class="main1">


	<div id="content" class="main">
	<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_menu"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="treeDiv"
			class="tree-div tree-div1">
			<ul id="tree" class="ztree" style="width: 260px; overflow: auto;"></ul>
		</div>
		<div id="rightContentDiv" class="right-div right-div1" style="width:74%; margin-top:10px;">

			<div id="showRoot" style="display: block">
				<div style="width:100%">
					<table border="0" cellpadding="0" cellspacing="0" class="pagelist" style="float:left;width:40%" >
						<tr>
						<th><img src="../../css/zTreeStyle/img/icons/onelevelmenu.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="sys_l_menuIcon"></icms-i18n:label></label></td>
						</tr>
						<tr>
						<th><img src="../../css/zTreeStyle/img/icons/twolevelmenu.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="sys_l_menuIcon_secondary"></icms-i18n:label></label></td>
						</tr>
						<tr>
						<th><img src="../../css/zTreeStyle/img/icons/function.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="sys_l_funcIcon"></icms-i18n:label></label></td>
						</tr>				
					</table>
				</div>
				<div style="width:40%">
					<p class="btnbar1">
						<shiro:hasPermission name="menu:save">
							<a href="javascript:void(0)" type="button" class="btn" id="btn_add_scp" onclick="show('0')" value="<icms-i18n:label name="addMenu"/>"
								style="margin-right:5px; margin-left:5px;" ><span>添加菜单</span></a>
						</shiro:hasPermission>
					</p>
				</div>
			</div>
			<jsp:include page="update.jsp"></jsp:include>
		</div>
	</div>

</body>
</html>