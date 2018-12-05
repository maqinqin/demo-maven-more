$(document).ready(function() {
	var i = -1;
	// 添加年份，从1910年开始
	var yearSelect = document.getElementById("year");
	for (i = 1990; i <= new Date().getFullYear(); i++) {
		var option = document.createElement("option");
		option.text = i;
	    option.value = i;
	    yearSelect.appendChild(option);
	    yearSelect.options[0].selected = true;
	}
	var monthSelect = document.getElementById("month");
	// 添加月份
	for (i = 1; i <= 12; i++) {
		var option = document.createElement("option");
		option.text = i;
		option.value = i;
		if(i < 10){
			option.value = "0"+i;
		}
	    monthSelect.appendChild(option);
	    monthSelect.options[0].selected = true;
	}
});

var isFirstLoad = true;
//初始化表
/*function init() {*/
function search(){
	/*alert("search!");*/
	if(!isFirstLoad){
		reload();
		return ;
	}
	
	var year = $("#year").val();
	var month = $("#month").val();
	var timeLine = year+"-"+month;
	var monthNext = "1";
	if(month == "12"){
		year = parseInt(year)+1+"";
	}else{
		monthNext = "0"+(parseInt(month)+1);
	}
	var timeLineNext = year+"-"+monthNext;

	//请求参数
	var queryData = {
			/*queryApp : $("#queryApp").val(),*/
			timeLine : timeLine,
			timeLineNext : timeLineNext,
			queryvmType : $("#queryvmType").val()
	};
	$("#gridTable").jqGrid({
		url : ctx + "/appmgt/application/getAppDevInfo.action",
		rownumbers : true,
		datatype : "json",
		postData : queryData,
		mtype : "post",
		height : 340,
		autowidth : true,
		colNames : [ 'APP_ID', '业务系统', '上月留存虚拟机','供给虚拟机数量', '回收虚拟机数量', '留存cpu','新增cpu','回收cpu', '留存内存', '新增内存', '回收内存','留存磁盘','新增磁盘','回收磁盘' ],
		colModel : [ {
			name : "appId",
			index : "appId",
			sortable : true,
			align : 'left',
			hidden : true
		}, {
			name : "appName",
			index : "appName",
			sortable : false,
			width: 100,
			align : 'left'
		}, {
			name : "vmRemain",
			index : "vmRemain",
			sortable : false,
			width: 100,
			align : 'left'
		}, {
			name : "vmSupply",
			index : "vmSupply",
			sortable : false,
			width: 100,
			align : 'left'	
		}, {
			name : "vmRecycle",
			index : "vmRecycle",
			sortable : false,
			width: 100,
			align : 'left'
		}, {
			name : "cpuRemain",
			index : "cpuRemain",
			sortable : false,
			width: 70,
			align : 'left'
		},  {
			name : "cpuSupply",
			index : "cpuSupply",
			sortable : false,
			align : 'left',//SERVER'STORAGE
			width: 70
		},  {
			name : "cpuRecycle",
			index : "cpuRecycle",
			sortable : false,
			width: 70,
			align : 'left'
		},   {
			name : "memRemain",
			index : "memRemain",
			sortable : false,
			width: 70,
			align : 'left'
		}, {
			name : "memSupply",
			index : "memSupply",
			sortable : false,
			align : 'left',
			width: 70
		}, {
			name : "memRecycle",
			index : "memRecycle",
			width : 70,
			sortable : false,
			align : "left"
		},{
			name : "diskRemain",
			index : "diskRemain",
			width : 70,
			sortable : false,
			align : "left"
		},{
			name : "diskSupply",
			index : "diskSupply",
			width : 70,
			sortable : false,
			align : "left"
		},{
			name : "diskRecycle",
			index : "diskRecycle",
			width : 70,
			sortable : false,
			align : "left"
		}],
		viewrecords : true,
		sortname : "APP_ID",
		rowNum : 10,
		rowList : [ 10, 20, 50 ],
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "pagination.dataList",
			records : "pagination.record",
			page : "pagination.page",
			total : "pagination.total",
			repeatitems : false
		},
		pager : "#gridPager",
		hidegrid : false
	});
	isFirstLoad = false;
}


/**
 * 重新加载
 */
function reload() {
	var year = $("#year").val();
	var month = $("#month").val();
	var timeLine = year+"-"+month;
	var monthNext = "1";
	if(month == "12"){
		year = parseInt(year)+1+"";
	}else{
		monthNext = "0"+(parseInt(month)+1);
	}
	var timeLineNext = year+"-"+monthNext;

	//请求参数
	var queryData = {
			/*queryApp : $("#queryApp").val(),*/
			timeLine : timeLine,
			timeLineNext : timeLineNext,
			queryvmType : $("#queryvmType").val()
	};
	jqGridReload("gridTable", queryData); 
}