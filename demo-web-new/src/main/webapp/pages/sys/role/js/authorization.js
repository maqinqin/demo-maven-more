var setting = {
	check : {
		enable : true,
		chkStyle : "checkbox",
		chkboxType : {
			"Y" : "ps",
			"N" : "ps"
		}
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pid",
			rootPId : ""
		}
	},
	callback : {
		beforeClick: beforeClickmenus,
	}
};

//点击节点名称时自动将当前节点或其子节点选中或取消
	function beforeClickmenus(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("menuTree");
		zTree.checkNode(treeNode, !treeNode.checked, true, true);
		var nodes = treeNode.children;
		if(nodes.length>0){
			for (var i=0, l=nodes.length; i < l; i++) {
				zTree.checkNode(nodes[i], treeNode.checked, true, true);
			}
		}
		return false;
	}

$(document).ready(function() {
	initMenuTree();
});

function initMenuTree() {
	var t = $("#menuTree");
	// 加载左侧树
	$.ajax({
		type : "post",
		url : ctx + "/sys/menu/loadtreeNew.do",
		data : {"roleId" : $("#roleAuthorId").val()},
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
			showTip(i18nShow('tip_sys_role_authorization'));
			flage = false;
		}
	});
}

$("#authorizationForm").validate({
	submitHandler : function() {
		saveAuthorization();
	}
});

function roleAuthorization() {
	var ids = jQuery("#gridTable").jqGrid('getGridParam', 'selarrrow');

	if (ids.length > 1 || ids.length == 0) {
		showTip(i18nShow('tip_sys_role_authorization1'));
		return;
	}
	showRoleAuthorizationDiv($("#gridTable").getRowData(ids[0]).roleId, $("#gridTable").jqGrid("getCell", ids[0],
			'roleName'));
}

// 查看角色授权信息。
function showRoleAuthorizationDiv(objectId, roleName) {
	$("label.error").remove();
	//openDialog('roleAuthorizationDiv','【' + roleName + '】'+i18nShow('sys_role_authorization_detail'),430,470);
	$("#roleAuthorizationDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 470,
		width : 430,
		title :'【' + roleName + '】'+i18nShow('sys_role_authorization_detail'),
		close:function(){
			 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
	$("#roleAuthorId").val(objectId);
	initMenuTree();
//	$.fn.zTree.getZTreeObj("menuTree").checkAllNodes(false);

//	var menuList = [];
//	$.ajax({
//		async : false,
//		cache : false,
//		type : 'POST',
//		url : ctx + "/sys/role/findSysMenuRolePoByRoleIdAct.action",// 请求的action路径
//		data : {
//			"sysRolePo.roleId" : objectId
//		},
//		error : function() {// 请求失败处理函数
//			showTip("请求失败！");
//		},
//		success : function(data) { // 请求成功后处理函数。
//			$.each(data, function() {
//				menuList[menuList.length] = this.menuId;
//			});
//		}
//	});
//	if(menuList.length>0){
		//此处延迟勾选节点是因为上面AJAX的请求返回菜单ID列表比较慢，
//	}
}

//根据列表中的ID将其与父亲均勾选+展开
function expandNode(menuList){
	if (menuList.length > 0) {
		var parentNodeOld ="";
		for (i = 0; i < menuList.length; i++) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			var node = zTree.getNodeByParam('id', menuList[i], null);
			// 将原有的菜单权限勾选上
			 zTree.checkNode(node, true, false);
			 parentNode = node.getParentNode();
			 
			 if(parentNode != parentNodeOld){
				 parentNodeOld = parentNode;
				 //将有菜单权限的大类默认展开
				 zTree.expandNode(parentNode, true, false, true);
			 }
				 
		}
	}
}
// 修改或者添加角色菜单信息
function saveAuthorization() {
	$("label.error").remove();
	var roleId = $("#roleAuthorId").val();// 角色id
	// 取得菜单选择权限
	var menutree = $.fn.zTree.getZTreeObj("menuTree");
	var menunodes = menutree.getCheckedNodes(true);
	var roleMenus = "";
	for (i = 0; i < menunodes.length; i++) {
		roleMenus += menunodes[i].id + ",";
	}
	var url;
	url = ctx + "/sys/role/saveAuthorizationAct.action"
	var data = {
		'sysRoleVo.roleId' : roleId,
		'sysRoleVo.roleMenus' : roleMenus
	};
	$.ajax({
		type : 'post',
		datatype : "json",
		url : url,
		async : false,
		data : data,
		/*
		 * beforeSend:(function(data){ return validate(datas); }),
		 */
		success : (function(data) {
			if (data.rtnMsg != "") {
				showTip(data.rtnMsg);
			} else {
				showTip(i18nShow('tip_sys_role_authorization_success'));
				$("#roleAuthorizationDiv").dialog("close");
				$("#gridTable").jqGrid().trigger("reloadGrid"); 
				//用于授权后刷新右侧页面内容,实时显示授权后的迎按钮信息
//				window.parent.frames.item('rightFrame').location.reload();
				location.reload();
			}
		}),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			showError(i18nShow('tip_save_fail'));
		}
	});
}

function closeView(divId) {
	$("#" + divId).dialog("close");
	$.fn.zTree.getZTreeObj("menuTree").checkAllNodes(false);
	$( "#gridTable" ).jqGrid().trigger("reloadGrid" );
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