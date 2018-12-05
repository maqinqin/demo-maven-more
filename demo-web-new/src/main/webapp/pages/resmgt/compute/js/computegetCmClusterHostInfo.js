
// 加载已关联物理机信息
function initCmClusterHostInfo() {
	
	$("#cluster_div div").show();
	//已关联的主机信息DIV，显示。
	$("#cluster_show_div").show();
	//未关联的主机信息DIV，隐藏。
	$("#device_show_div").hide();

	// 先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#gridTable").jqGrid("GridUnload");
	$("#gridTable").jqGrid({
		url : ctx+"/resmgt-compute/host/getCmClusterHostInfo.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		multiselect : true,
		postData : {
			clusterid : nodeId
		},
		height : 255,
		autowidth : true, // 是否自动调整宽度
		colModel : [ 
		{
			name : "id",
			index : "id",
			label : i18nShow('host_res_id'),
			width : 100,
			sortable : true,
			align : 'left',
			hidden : true
		},
		{
			name : "device_name",
			index : "device_name",
			label : i18nShow('host_res_device_name'),
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "sn",
			index : "sn",
			label : "SN",
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "seat_name",
			index : "seat_name",
			label : i18nShow('host_res_seat_name'),
			width : 80,
			sortable : true,
			align : 'left'
		},{
			name : "datastore_name",
			index : "datastore_name",
			label : i18nShow('host_res_datastore_name'),
			width : 80,
			sortable : true,
			align : 'left'
		},
		{
			name : "ip",
			index : "ip",
			label : "IP",
			width : 200,
			sortable : true,
			align : 'left'
		}],
		viewrecords : true,
		sortname : "device_name",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		pager : "#gridPager",
		//caption : "已关联物理机信息：",
		hidegrid : false
	});
	//自适应宽度
//	$("#gridTable").setGridWidth($("#gridTable_div").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTable_div").width());
    });
}
//删除主机与集群关关系
function deleteHostRel(){
	//获取jGrid选取的ID信息
	var ids = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');
	
	if(ids.length == 0){
		showError(i18nShow('compute_res_selectData'));
		return;
	}
	var deviceIds = '';
	$(ids).each(function(index, id){
		var rowData = $("#gridTable").getRowData(id);
		deviceIds +=rowData.id+",";//主机id
	});
	showTip(i18nShow('compute_res_removeDevice_toCluster'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url: ctx+"/resmgt-compute/host/deleteCmClusterHostInfo.action",
			async : false,
			data:{"deviceIds":deviceIds},
			beforeSend: function () {
	        	showTip("load");
	        },
			success:(function(data){
	        	closeTip();
				$("#gridTable").jqGrid().trigger("reloadGrid");
//			    if(data != "" && data != null && data != undefined && data !="undefined"){
//					//选择树节点
//					zTree.selectNode(zTree.getNodeByParam("id",clusterIdSpecified,null));
//					bizType = "cluster";
//					for(var i=0;i<data.length;i++){
//						addNode(data[i].id,data[i].device_name,iconPath+'host.png',null,false,"host",false,true);
//					}
//			    }else{
//					showError("移除失败。");
//			    }
				if(data.result=='success'){
					$(ids).each(function(index, id){
						var treeObj = zTree;
						var nodes = treeObj.getNodesByParam("id", id, null);
						var node = nodes[0];
						if(typeof(node) == 'undefined'){
//							alert("no node by selected");
							return;
						}
						//取父节点
						nextNode = node.getParentNode();
						treeObj.removeNode(node);
						treeObj.selectNode(nextNode);
						treeNodeOnClick(null, null, nextNode);
					});
					showTip(i18nShow('compute_res_removeDevice_success'));
				}else{
					showError(i18nShow('compute_res_removeDevice_fail')+":"+data.result);
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
	        	closeTip();
				showError(i18nShow('compute_res_removeDevice_fail'));
			} 
		});
	});
}

// 查询已关联的物理机信息
function search() {
	$("#gridTable").jqGrid('setGridParam', {
		url : ctx + "/resmgt-compute/host/getCmClusterHostInfo.action",//你的搜索程序地址
		postData : {
			"clusterid" : nodeId,
			"deviceName" : $("#deviceName").val().replace(/(^\s*)|(\s*$)/g, ""),
			"sn" : $("#sn").val().replace(/(^\s*)|(\s*$)/g, "")
		}, //发送搜索条件
		pager : "#gridPager"
	}).trigger("reloadGrid"); //重新载入
}


function inVcControlCluster(hostId,hostName){
	showTip("load");
	$('input:button').attr("disabled", "disabled");
	$('input:button').removeClass("btn").addClass('btn_disabled');
	$.post(ctx + "/resmgt-common/device/inVCtrole.action?hostId="+hostId+"&hostName"+hostName,
		function(data) {
		closeTip();
		if(data.result!=null && data.result=="success"){
			showTip(i18nShow('compute_res_inVcControl_success'), function () {
				var queryData = {
						clusterid : nodeId
					};
				jqGridReload("gridTable", queryData);
				});  
		}else{
			showError(i18nShow('compute_res_inVcControl_fail')+data.result);
			$('input:button').removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
		}
	}).error(function() {
		closeTip();
		showTip(i18nShow('compute_res_error'),null,"red");
		$('input:button').removeAttr("disabled").removeClass("btn_disabled").addClass("btn");
	});
}