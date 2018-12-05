
//加载可关联的物理机信息
function initCanRelevanceInfo() {
	openDialog('deviceDiv','关联主机设备',800,600);
	
	// 先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#deviceTable").jqGrid("GridUnload");
	$("#deviceTable").jqGrid({
		url : ctx+"/resmgt-common/cmseat/getCmseatAllHostCanRelevanceInfo.action", // 提交的action地址:获取可关联的所有物理机信息。
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData : {
			datacenterId : datacenterId
		},
		height : 280,
		autowidth : true, // 是否自动调整宽度
//		multiselect : true,
		colModel : [ {
			name : "",
			index : "id",
			label : "",
			width : 10,
			sortable : true,
			align : 'left',
			formatter : function(cellVall, options, rowObject) {
				return "<input type='radio' name='radioDicCode' value='" + rowObject.id+"'>";
			}
		}, {
			name : "device_name",
			index : "device_name",
			label : "主机名称",
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "sn",
			index : "sn",
			label : "SN",
			width : 60,
			sortable : true,
			align : 'left'
		}, {
			name : "cluster_name",
			index : "cluster_name",
			label : "所属集群",
			width : 60,
			sortable : true,
			align : 'left'
		}, {
			name : "cdp_name",
			index : "cdp_name",
			label : "所属CDP",
			width : 60,
			sortable : true,
			align : 'left',
			hidden : true
		}, {
			name : "pool_name",
			index : "pool_name",
			label : "所属资源池",
			width : 60,
			sortable : true,
			align : 'left'
		}, {
			name : "datacenter_cname",
			index : "datacenter_cname",
			label : "所属数据中心",
			width : 60,
			sortable : true,
			align : 'left'
		} ],
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
		pager : "#devicePager",
		caption : "设备列表：",
		hidegrid : false
	});
}

// 查询可关联的物理机信息。
function search() {
	jqGridReload("deviceTable", {
		"datacenterId" : datacenterId,
		"deviceName" : $("#deviceName").val(),
		"sn" : $("#sn").val()
	});

}

//将物理机关联到集群
function relevance(){
	var id=$("input:checked").val();

	if(id == undefined){
		showTip('请选择一条数据。',function(){closeTip(true)});
		return;
	}

	var relevanceInfo = new Object();
		relevanceInfo.uId=uId;//U位ID
		relevanceInfo.id=id;//主机id
	
	showTip("确定要关联主机吗？",function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url: ctx+"/resmgt-common/cmseat/updateDeviceSeat.action",
			async : false,
			data:{"relevanceInfo":JSON.stringify(relevanceInfo)},
			success:(function(data){
//				$("#deviceTable").jqGrid().trigger("reloadGrid");
				showTip("关联成功。");
				closegDiv();
				zTree.selectNode(zTree.getNodeByParam("id",uId,null));
				bizType = "u";
	        	myClickFun();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError("关联失败。");
			} 
		});
	});
	
}

//关闭对话框
function closegDiv(){
	$("#deviceDiv").dialog("close");
}

function openDialog(divId,title,width,height){
	$("#"+divId).dialog({
			autoOpen : true,
			modal:true,
			height:height,
			width:width,
			title:title,
//			draggable: false,
	       // resizable:false
	})
}
