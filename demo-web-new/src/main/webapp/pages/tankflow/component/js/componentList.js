/**
 * 保存新增或修改组件信息
 */
function saveUpdateComponent(componentId, componentName, infPathName, pagePathName, componentIcon, componentType, remark) {
	$.ajax({
		type : "POST",
		url : ctx + '/workflow/component/component_saveComponentInfo.action',
		datatype : "json",
		data : {
			"componentPo.componentId" : componentId,
			"componentPo.componentName" : componentName,
			"componentPo.infPathName" : infPathName,
			"componentPo.pagePathName" : pagePathName,
			"componentPo.componentType" : componentType,
			"componentPo.remark" : remark
		},
		async : true,
		cache : false,
		success : function(data) {
			if (data.result == 'success') {
				showTip("保存成功");
				$("#component_div").dialog("close");
				$("#componentTable").jqGrid().trigger("reloadGrid");
			} else {
				showTip("保存失败");
			}

		},
		error : function(e) {
			showTip("保存失败");
		}
	});
}
/**
 * 删除组件信息
 * 
 * @param componentId
 */
function deleteComponent(componentId) {
	showTip("请确认是否删除？", 
		function() {$.ajax({
			type : "POST",
			url : ctx + '/workflow/component/component_deleteComponetnInfo.action',
			datatype : "json",
			data : {
				"componentPo.componentId" : componentId
			},
			async : true,
			cache : false,
			success : function(data) {
				showTip("删除成功！");
			},
			error : function(e) {
				showTip("删除发生错误\n" + e);
			}
		});
	});
}

function closeViews(divId) {
	$("#" + divId).dialog("close");
}
