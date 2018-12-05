function initCmMacList() {
	$("#macGridTable").jqGrid({
		url : ctx+"/resmgt-common-mac/mac/getMacList.action", 
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 320,
		autowidth : true, // 是否自动调整宽度
		multiselect:true,   // 第一是否可以多选
		multiboxonly: false,  
		colNames:['ID','Mac地址','SN','临时IP','Cobbler概要文件','状态','创建时间','wfModelId','instanceId','操作'],
		colModel : [  {
			name : "id",
			index : "id",
			label : "id",
			width : 120,
			sortable : true,
			align : 'left',
			hidden : true
		}, {
			name : "macAddr",
			index : "macAddr",
			label : "MAC地址",
			width : 120,
			sortable : true,
			align : 'left'
			
		},{
			name : "serialNo",
			index : "serialNo",
			label : "设备序列号",
			width : 120,
			sortable : true,
			align : 'left'
// //触发该功能之后会跳转查询页面 formatter : function(cellValue,options,rowObject) {
// return "<a href='#' style='text-decoration : none'
// onclick=showLookDiv('"+rowObject.id+"','"+rowObject.deviceType+"')>"+cellValue+"</a>"
// }
		},  {
			name : "tempIP",
			index : "tempIP",
			label : "临时IP",
			width : 150,
			sortable : true,
			align : 'left'
		}, {
			name : "profile",
			index : "profile",
			label : "Cobbler概要文件",
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			
			name : "istState",
			index : "istState",
			width : 120,
			sortable : false,
			align : 'left',
			formatter:function(cellVall,options,rowObject){
				if(cellVall == 'I'){
					return "<span>正在初始化</span>";
				}else if(cellVall == 'P'){
					return "<span>等待初始化</span>";
				}else if(cellVall == 'C'){
					return "<span>初始化完成</span>";
				}else if(cellVall == 'SI'){
					return "<span>操作系统正在安装</span>";
				}else if(cellVall == 'SC'){
					return "<span>操作系统安装结束</span>";
				}
				return cellVall ;
			}
		}, 
		{
			name :"createDatetime",
			index :"createDatetime",
			width : 120 ,
			sortable : false ,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
				return formatTime(cellValue);
          }
		},
		{   name :"wfModelId",
			index :"wfModelId",
			sortable : false,
			hidden : true
		},
		{   name:"instanceId",
			index:"instanceId",
			sortable : false ,
			hidden : true
		},
		{
			name : 'option',
		    index : 'option',
		    label : "操作",
			width : 100,
			align : "center",
			sortable:false,
			formatter : function(cellValue,options,rowObject) {
				var ref="";
				if(rowObject.istState == 'P'){
//					str1= "<input type='button' value ='初始化' onclick=initMacSystem('"+rowObject.id+"')  title='初始化' />&nbsp;&nbsp;"
					ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=initMacSystem('"+rowObject.id+"') >初始化</a>"; 
					}
				if(rowObject.istState=='SI'||rowObject.istState=='SC'){
					ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showInstance('"+rowObject.instanceId+"','"+rowObject.wfModelId+"') >工作流</a>"; 
//					str2 = "<input type='button' value='工作流' onclick=showInstance('"+rowObject.instanceId+"','"+rowObject.wfModelId+"')  title ='工作流' />&nbsp;&nbsp;"
				}
			return 	ref;
// return "<input type='button' class='btn_edit_s'
// onclick=showUpdateDiv('"+rowObject.id+"','"+rowObject.deviceType+"')
// title='修改' /><input type='button' style='margin-left:5px;' class='btn_del_s'
// onclick=deleteDevice('"+rowObject.id+"') title='删除'/>"
			}														
		}],
		
		viewrecords : true,    // 是否要显示总记录数 ， 默认是false
		sortname : "id",       // 排序列的名称，此参数会被传到后台
		rowNum : 10,           // 在grid上显示记录条数，这个参数是要被传递到后台
		rowList : [ 5, 10, 15, 20, 30 ],  // 一个下拉选择框，用来改变显示记录数，当选择时会覆盖rowNum参数传递到后台
		prmNames : {                    //
			search : "search"
		},
		jsonReader : {      // 描述json 数据格式的数组
			root : "dataList",
			records : "record",    // 查询出的记录数
			repeatitems : false    // 指明每行的数据是可以重复的，如果设为false，则会从返回的数据中按名字来搜索元素
		},
		pager : "#gridPager",    // 定义翻页用的导航栏，必须是有效的html元素。翻页工具栏可以放置在html页面任意位置
// caption : "设备信息记录",
		hidegrid : false         // 启用或者禁用控制表格显示、隐藏的按钮，只有当caption 属性不为空时起效
					})

}

function search() {
	$("#macGridTable").jqGrid('setGridParam', { 
		url : ctx + "/resmgt-common-mac/mac/getMacList.action",// 你的搜索程序地址
		postData : {
			"serialNo": $.trim($("#serialNo").val()),
			"macAddr" : $.trim($("#macAddr").val()),
			"istState" : $.trim($("#tm_istState").val())
			
		}, // 发送搜索条件
		pager : "#gridPager"
	}).trigger("reloadGrid"); // 重新载入
}
//初始化操作
function initMacSystem(dataId) {
	var initInfo = new Object();
	if (dataId) {
		showTip("确定初始化操作？", function() {
			$.ajax({
				type : 'post',
				datatype : "json",
				url : ctx + "/resmgt-common-mac/mac/initMacSystemList.action",
				async : false,
				data : {
					"initInfo" : JSON.stringify(dataId)
				},
				success : (function(data) {
					$("#macGridTable").jqGrid().trigger("reloadGrid");
				}),
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					showError("初始化失败!");
				}
			});
		});
	} 
	else {
		var ids = jQuery("#macGridTable").jqGrid('getGridParam', 'selarrrow');
		if (ids.length == 0) {
			showError("至少选择一条数据!");
			return;
		}
		var list=[] ;
		$(ids).each( function(index , id2){
			list[list.length] = id2;
		})
		showTip("确定初始化？",function(){
			$.ajax({
				type :'post',
				datatype : "json",
				url : ctx + "/resmgt-common-mac/mac/initMacSystemList.action",
				async: false , 
				data :{
					"initInfo" : JSON.stringify(list.join(","))
				},
				success:(function(data){
					$("#macGridTable").jqGrid().trigger("reloadGrid");
				}),error: function(XMLHttpRequest, textStatus, errorThrown) {
					showError("初始化失败");
				}				
			});
		});
	};
}
function showInstance(instanceId,wfModelId) {
	window.location.href = ctx+"/workflow/instance/bpmInstance_processInstance.action?state=instance&instanceId="+instanceId;
}
