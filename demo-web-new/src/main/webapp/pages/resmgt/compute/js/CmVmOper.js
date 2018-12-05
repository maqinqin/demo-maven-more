function initCmVmOperList() {
	$("#macGridTable").jqGrid({
		url : ctx+"/resmgt-common-mac/mac/getVmOperList.action", 
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 320,
		autowidth : true, // 是否自动调整宽度
		multiselect:true,   // 第一是否可以多选
		multiboxonly: false,  
		colNames:['ID','名称','ip','状态','vCPU','内存','磁盘空间','云服务','所属应用/服务器角色','操作'],
		colModel : [  {
			name : "id",
			index : "id",
			label : "id",
			width : 120,
			sortable : true,
			align : 'left',
			hidden : true
		}, {
			name : "名称",
			index : "名称",
			label : "名称",
			width : 120,
			sortable : true,
			align : 'left'
			
		},{
			name : "ip",
			index : "ip",
			label : "ip",
			width : 120,
			sortable : true,
			align : 'left'
// //触发该功能之后会跳转查询页面 formatter : function(cellValue,options,rowObject) {
// return "<a href='#' style='text-decoration : none'
// onclick=showLookDiv('"+rowObject.id+"','"+rowObject.deviceType+"')>"+cellValue+"</a>"
// }
		},  {
			name : "状态",
			index : "状态",
			label : "状态",
			width : 150,
			sortable : true,
			align : 'left'
		}, {
			name : "vCPU",
			index : "vCPU",
			label : "vCPU",
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			
			name : "内存",
			index : "内存",
			width : 120,
			sortable : false,
			align : 'left',
		}, 
		{
			name :"磁盘空间",
			index :"磁盘空间",
			width : 120 ,
			sortable : false ,
			align : 'left',
		},
		{   name :"云服务",
			index :"云服务",
			sortable : false,
			hidden : true
		},
		{   name:"所属应用/服务器角色",
			index:"所属应用/服务器角色",
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
					ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=powerOn('"+rowObject.id+"') >开机</a>"; 
					}
				if(rowObject.istState=='SI'||rowObject.istState=='SC'){
					ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=powerOff('"+rowObject.instanceId+"','"+rowObject.wfModelId+"') >关机</a>"; 
//					str2 = "<input type='button' value='工作流' onclick=showInstance('"+rowObject.instanceId+"','"+rowObject.wfModelId+"')  title ='工作流' />&nbsp;&nbsp;"
				}
				if(rowObject.istState=='SI'||rowObject.istState=='SC'){
					ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=createSnapshot('"+rowObject.instanceId+"','"+rowObject.wfModelId+"') >快照</a>"; 
				}
				if(rowObject.istState=='SI'||rowObject.istState=='SC'){
					ref += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=revertSnapshot('"+rowObject.instanceId+"','"+rowObject.wfModelId+"') >恢复</a>"; 
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
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/resmgt-common-mac/mac/cmVmOpersearch.action",
		async : true,
	});
}

function powerOn(dataId) {
 if (dataId) {	
	showTip("确定开机操作？", function() {
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/resmgt-common-mac/mac/powerOn.action",
			async : true,
		});
	});
 }
}
function powerOff(dataId) {
 if (dataId) {	
	showTip("确定关机操作？", function() {
		$.ajax({
			type : 'post',
			datatype : "json",
			url : ctx + "/resmgt-common-mac/mac/powerOff.action",
			async : true,
		});
	});
 }
}

function createSnapshot(dataId) {
 if (dataId) {	
		showTip("确定快照操作？", function() {
			$.ajax({
				type : 'post',
				datatype : "json",
				url : ctx + "/resmgt-common-mac/mac/createSnapshot.action",
			});
		});
	 }
}

function revertSnapshot(dataId) {
 if (dataId) {	
		showTip("确定快照恢复操作？", function() {
			$.ajax({
				type : 'post',
				datatype : "json",
				url : ctx + "/resmgt-common-mac/mac/revertSnapshot.action",
				async : true,
			});
		});
	 }
}

//新建快照
function saveSnapshotBtn() {
	var searchForName = $.trim($("#snapshot_Name").val());
	/*var cluster_id = $("#cluster_id").val();
	var cluster_cdpId = $("#cluster_cdpId").val();
	var u_cluster_Name = $("#u_cluster_Name").val();
	var u_cluster_ename = $("#u_cluster_ename").val();
	var u_cluster_remark = $("#snapshot_remark").val();*/
	if(searchForName == ""){
		alert("请输入快照名称");
	}else{
		var url =  ctx +"/createSnapshot.mvc";
	}
	//showTip('新建快照可能需要几分钟，请耐心等待。。。');
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"nodeName" : searchForName/*,"snapshot_remark":$("#snapshot_remark").val()*/},
		error : function() {//请求失败处理函数  
			showError("请求失败");
		},
		success : function(data) { //请求成功后处理函数。     
			//zTreeInit("treeRm", data);
			alert(data);
		}
	});
}
function search() {
	$("#macGridTable").jqGrid('setGridParam', { 
		url : ctx + "/resmgt-common-mac/mac/getMacList.action",// 你的搜索程序地址
		postData : {
			"serialNo": $.trim($("#serialNo").val()).replace(/(^\s*)|(\s*$)/g, ""),
			"macAddr" : $.trim($("#macAddr").val()).replace(/(^\s*)|(\s*$)/g, ""),
			"istState" : $.trim($("#tm_istState").val()).replace(/(^\s*)|(\s*$)/g, "")
			
		}, // 发送搜索条件
		pager : "#gridPager"
	}).trigger("reloadGrid"); // 重新载入
}
//新建快照弹框
function createSnapshotDiv(resId){
	$("label.error").remove();
	//得到当前窗口的父类
	 var dialogParent = $("#snapshotAddDiv").parent();
	 //对窗口进行克隆，并进行隐藏
     var dialogOwn = $("#snapshotAddDiv").clone();  
     dialogOwn.hide();  
          
     $("#snapshotAddDiv").dialog({  
    	 	autoOpen : true,
			modal:true,
			height:300,
			width:500,
			title:'新建快照',
			//resizable:false,
            close: function () {
            	//将隐藏的克隆窗口追加到页面上
                dialogOwn.appendTo(dialogParent);  
                $(this).dialog("destroy").remove();   
            }
        });  
	//----------------------------------------
//	clearTab();
	//清除form所有数据
	clearForm($('#snapshotAddForm'));
//	$("#cdpUpdateDiv").disable(); 
	//使form所有数据为可输入
	$("#snapshotAddDiv").enable(); 
	//添加隐藏域的默认值
	$("#clusterMethod").val("save");
	$("#cluster_cdpId").val(resId);
	//添加默认的用户信息
	$("#cluster_createUser").val("createUser");
	//默认为请选择
	$("#hid_icon").val(iconPath+'cluster.png');
	//selectByValue("u_storage_id","");
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
