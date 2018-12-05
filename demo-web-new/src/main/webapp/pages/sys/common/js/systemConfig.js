function initLog(){
var queryData = {};
	
	
	$("#gridTable").jqGrid({
		url : 'local',
		rownumbers : true,
		datatype : "json",
		postData : queryData,
		mtype : "post",
		height : 260,
		autowidth : true,
		colNames : ['序号','系统功能描述 ','操作'],
		colModel : [{
			name : "serial",
			index : "serial",
			width: 150,
			hidden : true
		},{
			name : "systemRemark",
			index : "systemRemark",
			sortable : true,//排序属性
			width: 180,
			align : 'center'
		} ,{
			name : "option",
			index : "option",
			width : 60,
			sortable : false,
			align : "center",
			formatter : function(cellVall, options, rowObject) {
				
				var ret ="";
			    ret += '<input type="button" style=" margin-right: 10px;" class="btn_apply_s" onclick="refresh()" title="刷新"/>';
				ret += "　";
				return ret;
				}
			}],
		viewrecords : true,
		sortname : "logId",
		rowNum : 10,
		rowList : [5, 10, 15, 20, 30 ],
		jsonReader : {
			root : "dataList",
			records : "record",
			page : "page",
			total : "total",
			repeatitems : false
		},
		pager : "#gridPager"
	});
	
	
	var systemLogObj = {
			serial:"1",
			systemRemark:"系统操作日志配置"
	};
	$("#gridTable").jqGrid("addRowData", 1, systemLogObj);
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTableDiv").width());
    });
} 
function refresh(){
	$.post(ctx+"/sys/config/refreshSysOperLogCfg.action",{},function(){showTip("刷新成功");
	});
}