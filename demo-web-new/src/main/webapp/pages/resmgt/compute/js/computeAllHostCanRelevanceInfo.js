var clusterName;
var clusterIdSpecified;
var datastoreType;

$(document).ready(function(){
	$("#js_xzgl").click(function(){ 
		initAllHostCanRelevanceInfo();
	});
	$("#isManuallySetIP").click(function(){
		if($("#isManuallySetIP").attr("checked")=="checked")
		{
			$("#setingDiv").dialog('option','height', 270);
			document.getElementById("manageIP").disabled=false;	
			$("#operation").show();
		}
		else
		{
			$("#setingDiv").dialog('option',
					'height', 270);
			$("#manageIP").val("");
			document.getElementById("manageIP").disabled=true;	
			$("#operation").show();
		}
	});
});

//加载可关联的物理机信息
function initAllHostCanRelevanceInfo() {
	$("#cluster_div div").show();
	//未关联的主机信息DIV，显示。
	$("#device_show_div").show();
	//已关联的主机信息DIV，隐藏。
	$("#cluster_show_div").hide();
	//获取集群名称
	clusterName=$("#cluster_ename").html();
	//获取指定的集群ID
	clusterIdSpecified=$("#clusterIdSpecified").val();
	//获取隐藏的集群类型
	var hid_vmType = $("#hidden_vmType").val();
	// 先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#deviceTable").jqGrid("GridUnload");
	$("#deviceTable").jqGrid({
		url : ctx+"/resmgt-compute/host/getAllHostCanRelevanceInfo.action", // 提交的action地址:获取可关联的所有物理机信息。
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 290,
		autowidth : true, // 是否自动调整宽度
		multiselect : true,
		colModel : [ {
			name : "id",
			index : "id",
			label : "",
			width : 0,
			sortable : true,
			align : 'left',
			hidden : true
		}, {
			name : "order_num",
			index : "order_num",
			label : "顺序号",
			width : 40,
			sortable : true,
			align : 'left',
			hidden : true
		}, {
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
			width : 40,
			sortable : true,
			align : 'center'
		}, {
			name : "seat_name",
			index : "seat_name",
			label : i18nShow('host_res_seat_name'),
			width : 70,
			sortable : true,
			align : 'left'
		}, {
			name : "datastore_id",
			index : "datastore_id",
			label : "存储ID",
			width : 0,
			sortable : true,
			align : 'left',
			hidden : true
		}, {
			name : "datastore_name",
			index : "datastore_name",
			label : i18nShow('host_res_defaultDa_name'),
			width : 100,
			sortable : true,
			align : 'left'
		}
		, {
			name : "username",
			index : "username",
			label : i18nShow('host_res_username'),
			width : 50,
			sortable : true,
			align : 'center'
		}, {
			name : "password",
			index : "password",
			label : i18nShow('host_res_password'),
			width : 60,
			sortable : true,
			align : 'left',
			hidden : true
		},{
			name : "isManuallySetIP",
			index : "isManuallySetIP",
			label : i18nShow('host_res_password'),
			width : 60,
			sortable : true,
			align : 'left',
			hidden : true
		},{
			name : "manageIP",
			index : "manageIP",
			label : i18nShow('host_res_password'),
			width : 60,
			sortable : true,
			align : 'left',
			hidden : true
		},/*{
			name: "isBare",
			index: "isBare",
			label: i18nShow('host_res_isBare'),
			width: 60 ,
			sortable: true ,
			align: 'center',
			formatter:function(cellValue,options,rowObject){
				if(cellValue=='Y'){
					return "Y";
				}else if(cellValue=='N'){
					return "N";
				}
				
			}
		},{
			name : "bareV",
			index: "bareV",
			label:i18nShow('host_res_bareV'),
			width: 60 ,
			sortable: true ,
			align: 'center',
			hidden : true ,
			formatter:function(cellValue,options,rowObject){
				if(rowObject.isBare == "Y"){
					return "Y" ;
				}else if(rowObject.isBare == "N"){
					return "N" ;
				}
				return cellValue ;
			}
			
		},*/{
			name : "",
			index : "",
			label : i18nShow('host_res_option'),
			width : 40,
			align : 'left',
			formatter:function(cellVall, options, rowObject){
				if(hid_vmType!="POWERVM"){
					return "<a href='javascript:void(0)' style=' text-decoration:none' onclick=showSetingDiv('"+rowObject.id+"')>"+i18nShow('host_res_option')+"</a>"
				}else{
					return "";
				}
			}
		} ],
		viewrecords : true,
		sortname : "device_name",
		rowNum : 8,
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
		//caption : "可关联的物理机：",
		hidegrid : false
	});
	//自适应宽度
	$("#deviceTable").setGridWidth($("#deviceTable_div").width());
	$(window).resize(function() {
		$("#deviceTable").setGridWidth($("#deviceTable_div").width());
    });
}

// 查询可关联的物理机信息。
function search2() {
	$("#deviceTable").jqGrid('setGridParam', {
		url : ctx + "/resmgt-compute/host/getAllHostCanRelevanceInfo.action",//你的搜索程序地址
		postData : {
			"clusterid" : nodeId,
			"deviceName" : $("#deviceName2").val().replace(/(^\s*)|(\s*$)/g, ""),
			"sn" : $("#sn2").val().replace(/(^\s*)|(\s*$)/g, "")
			/*"isBare" : $("#isBare2").val()*/
		}, //发送搜索条件
		pager : "#devicePager"
	}).trigger("reloadGrid"); //重新载入
}



//wmy-------------------------------------
function openDialog(divId,title,width,height){
	$("#"+divId).dialog({
			autoOpen : true,
			modal:true,
			height:height,
			width:width,
			title:title,
	       // resizable:false,
	        close : function(){
	        	$("#objectIdTemp").val("")
	        }
	});
}
//删除选择的datastore
function deleteDataStore(datastoreId,storageType){
	var hostId = $("#hostId").val();
	var msg = "";
	if(storageType == 'SAN'){
		msg = i18nShow('host_res_unmountDataTip1');
	}else{
		msg = i18nShow('host_res_unmountDataTip2');
	}
	showTip(msg,function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			url:ctx+"/resmgt-common/device/deleteDatastoreInfo.action",
			data : {
					"hostId":hostId,
					"id":datastoreId,
					"storageType":storageType
			},
			type:'post',
			dataType : "json",
			error : function() {//请求失败处理函数   
				closeTip();
				showError(i18nShow('host_res_unRefDataTip'));
			},
			success : function(data){
				closeTip();
				if(data.result == "success"){
					showTip(i18nShow('host_res_unRefDataTipSuccsee'));
				}else{
					showTip(data.result);
				}
				searchDatastore();
			}
		});
	});
	
}

//判断物理机类型
function checkDataStore(datastoreId,storageType){
	$("#datastoreId").val(datastoreId);
	saveDataStore(datastoreId,storageType);
}
//保存已经选择的datastore
function saveDataStore(datastoreId,storageType){
	var hostId = $("#hostId").val();
	var msg = "";
	if(storageType == "SAN"){
		msg = i18nShow('host_res_mountDataTip1');
	}else{
		msg = i18nShow('host_res_mountDataTip2');
	}
	showTip(msg,function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			url:ctx+"/resmgt-common/device/saveDatastoreInfo.action",
			data : {
					"hostId":hostId,
					"id":datastoreId,
					"storageType":storageType
			},
			type:'post',
			dataType : "json",
			error : function() {//请求失败处理函数   
				showError(i18nShow('host_res_saveDataFail'));
			},
			success : function(data){
				if(data.result == "success"){
					closeTip();
					showTip(i18nShow('host_res_mountDataSuccess'));
					searchDatastore();
				}else{
					closeTip();
					showError(i18nShow('host_res_mountDataFail')+data.result);
					searchDatastore();
				}
			}
		});
	});

}

function searchDatastore() {	
	var dcId = $("#datacenterId").val().replace(/(^\s*)|(\s*$)/g, "");
	var hostId = $("#hostId").val().replace(/(^\s*)|(\s*$)/g, "");
	jqGridReload("gridTable1", {
		"dcId" :dcId,
		"hostId":hostId
	});
}
function ipmiMessage(){
	var hostId = $("#hostId").val();
	var impiFlag = $("#impiFlag").val();
	if(impiFlag == 'N'){
		$("#iUser").val("");
		$("#iPwd").val("");
		$("#iUrl").val("");
		$("#iVer").val("");
		openDialog('ipmiMessage',i18nShow('host_res_setUserPwd'),400,300);
	}else if(impiFlag == 'Y'){
		getHardWareStatusById(hostId);
	}
}
function closeMessage(){
	$("#ipmiMessage").dialog("close");
}
function showHardWareStatus(){
	var hostId = $("#hostId").val();
	var iUser =$("#iUser").val();
	var iPwd = $("#iPwd").val();
	var iUrl = $("#iUrl").val();
	var iVer = $("#iVer").val();
	if(iUser==""){
		showTip(i18nShow('host_res_userTip'));
		return false;
	}else if(iPwd==""){
		showTip(i18nShow('host_res_pwdTip'));
		return false;
	}else if(iUrl==""){
		showTip(i18nShow('host_res_ipAdd'));
		return false;
	}else if(iVer==""){
		showTip(i18nShow('host_res_version'));
		return false;
	}
	//closeMessage();
	$("#ipmiMessage").dialog("close");
	showTip("load");
	$("#showHardWareStatusTable").html("");
	openDialog('showHardWareStatus',i18nShow('host_res_showHardWareStatus'),450,500);
	$.ajax({
		async : false,
		cache : false,
		url: ctx+"/rest/vmScan/getPmSensorInfo",
		data : {
				"pmId"  : hostId,
				"ipmiName" : iUser,
				"ipmiPword"  : iPwd,
				"pmIp"  : iUrl,
				"ipmiVer"  : iVer
		},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			closeTip();
			showError(i18nShow('host_res_showHardWareStatusErr'));
		},
		success : function(data){
			closeTip();
			for(var i=0;i<data.length;i++){
				$("#showHardWareStatusTable").append("<tr><th>"+data[i][0]+"：</th><td><label id=''>"+data[i][1]+"</label></td></tr>");
			}
		}
	});

}

function getHardWareStatusById(hostId){
	var hostId = $("#hostId").val();
	showTip("load");
	$("#showHardWareStatusTable").html("");
	openDialog('showHardWareStatus',i18nShow('host_res_showHardWareStatus'),450,500);
	$.ajax({
		async : false,
		cache : false,
		url: ctx+"/rest/vmScan/getPmSensorInfoById",
		data : {
				"hostId"  : hostId
		},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('host_res_showHardWareStatusErr'));
		},
		success : function(data){
			closeTip();
			for(var i=0;i<data.length;i++){
				$("#showHardWareStatusTable").append("<tr><th>"+data[i][0]+"：</th><td><label id=''>"+data[i][1]+"</label></td></tr>");
			}
		}
	});
}

//物理机进入维护模式
function maintenanceModeMethod(){
	var hostId = $("#hostId").val();
	showTip(i18nShow('host_res_maintenance'),function(){
		showTip("load");
		$("#maintenanceMode").attr("disabled", "disabled");
		$("#maintenanceMode").removeClass("btn_gray").addClass('btn_disabled');
		$("#exitMaintenanceMode").attr("disabled", "disabled");
		$("#exitMaintenanceMode").removeClass("btn_gray").addClass('btn_disabled');
		$("#pmClose").attr("disabled", "disabled");
		$("#pmClose").removeClass("btn_gray").addClass('btn_disabled');
		$("#pmRestart").attr("disabled", "disabled");
		$("#pmRestart").removeClass("btn_gray").addClass('btn_disabled');
	$.ajax({
		async : false,
		cache : false,
		url: ctx+"/rest/vmScan/maintenanceMode",
		data : {
				"pmId"  : hostId
		},
		type:'post',
		traditional : true,
		error : function() {//请求失败处理函数   
			closeTip();
			showError(i18nShow('host_res_operateErr'));
		},
		success : function(data){
			closeTip();
			if(data!=null && data=="success"){
				showTip(i18nShow('host_res_maintenance_success'));
				$("#exitMaintenanceMode").attr("style", "display:block");
				$("#exitMaintenanceMode").bind('click',function(){exitMaintenanceModeMethod();});
				$("#pmClose").attr("style", "display:block");
				$("#pmClose").bind('click',function(){pmCloseMethod();});
				$("#pmRestart").attr("style", "display:block");
				$("#pmRestart").bind('click',function(){pmRestart();});
				$("#maintenanceMode").attr("style", "display:none");
				$("#maintenanceMode").unbind('click');
			}
			
		}
	});
	$("#maintenanceMode").removeAttr("disabled").removeClass("btn_disabled").addClass("btn btn_dd2");
	$("#exitMaintenanceMode").removeAttr("disabled").removeClass("btn_disabled").addClass("btn btn_dd2");
	$("#pmClose").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#pmRestart").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	});
}


//物理机退出维护模式
function exitMaintenanceModeMethod(){
	var hostId = $("#hostId").val();
	showTip(i18nShow('host_res_exit_maintenance_tip'),function(){
		showTip("load");
		$("#maintenanceMode").attr("disabled", "disabled");
		$("#maintenanceMode").removeClass("btn_gray").addClass('btn_disabled');
		$("#exitMaintenanceMode").attr("disabled", "disabled");
		$("#exitMaintenanceMode").removeClass("btn_gray").addClass('btn_disabled');
		$("#pmClose").attr("disabled", "disabled");
		$("#pmClose").removeClass("btn_gray").addClass('btn_disabled');
		$("#pmRestart").attr("disabled", "disabled");
		$("#pmRestart").removeClass("btn_gray").addClass('btn_disabled');
	$.ajax({
		async : false,
		cache : false,
		url: ctx+"/rest/vmScan/exitMaintenanceMode",
		data : {
				"pmId"  : hostId
		},
		type:'post',
		traditional : true,
		error : function() {//请求失败处理函数   
			closeTip();
			showError(i18nShow('host_res_operateErr'));
		},
		success : function(data){
			closeTip();
			if(data!=null && data=="success"){
			showTip(i18nShow('host_res_exit_maintenance_success'));
			$("#maintenanceMode").attr("style", "display:block");
			$("#maintenanceMode").bind('click',function(){maintenanceModeMethod();});
			$("#exitMaintenanceMode").attr("style", "display:none");
			$("#exitMaintenanceMode").unbind('click');
			$("#pmClose").attr("style", "display:none");
			$("#pmClose").unbind('click');
			$("#pmRestart").attr("style", "display:none");
			$("#pmRestart").unbind('click');
			}
		}
	});
	$("#maintenanceMode").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#exitMaintenanceMode").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#pmClose").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#pmRestart").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	});
}

//物理机关机
function pmCloseMethod(){
	var hostId = $("#hostId").val();
	showTip(i18nShow('host_res_pmClose_tip'),function(){
		showTip("load");
		$("#maintenanceMode").attr("disabled", "disabled");
		$("#maintenanceMode").removeClass("btn_gray").addClass('btn_disabled');
		$("#exitMaintenanceMode").attr("disabled", "disabled");
		$("#exitMaintenanceMode").removeClass("btn_gray").addClass('btn_disabled');
		$("#pmClose").attr("disabled", "disabled");
		$("#pmClose").removeClass("btn_gray").addClass('btn_disabled');
		$("#pmRestart").attr("disabled", "disabled");
		$("#pmRestart").removeClass("btn_gray").addClass('btn_disabled');
	$.ajax({
		async : false,
		cache : false,
		url: ctx+"/rest/vmScan/pmCloseByVc",
		data : {
				"pmId"  : hostId
		},
		type:'post',
		traditional : true,
		error : function() {//请求失败处理函数   
			closeTip();
			showError(i18nShow('host_res_operateErr'));
		},
		success : function(data){
			closeTip();
			if(data!=null && data=="success"){
			showTip(i18nShow('host_res_pmClose_success'));
			$("#maintenanceMode").attr("style", "display:none");
			$("#maintenanceMode").unbind('click');
			$("#exitMaintenanceMode").attr("style", "display:none");
			$("#exitMaintenanceMode").unbind('click');
			$("#pmClose").attr("style", "display:none");
			$("#pmClose").unbind('click');
			$("#pmRestart").attr("style", "display:none");
			$("#pmRestart").unbind('click');
			}
		}
	});
	$("#maintenanceMode").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#exitMaintenanceMode").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#pmClose").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#pmRestart").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	});

}
//物理机重启
function pmReboot(){
	var hostId = $("#hostId").val();
	showTip(i18nShow('host_res_pmReboot_tip'),function(){
	showTip("load");
	$("#maintenanceMode").attr("disabled", "disabled");
	$("#maintenanceMode").removeClass("btn_gray").addClass('btn_disabled');
	$("#exitMaintenanceMode").attr("disabled", "disabled");
	$("#exitMaintenanceMode").removeClass("btn_gray").addClass('btn_disabled');
	$("#pmClose").attr("disabled", "disabled");
	$("#pmClose").removeClass("btn_gray").addClass('btn_disabled');
	$("#pmRestart").attr("disabled", "disabled");
	$("#pmRestart").removeClass("btn_gray").addClass('btn_disabled');
	$.ajax({
		async : false,
		cache : false,
		url: ctx+"/rest/vmScan/pmRebootByVc",
		data : {
				"pmId"  : hostId
		},
		type:'post',
		error : function() {//请求失败处理函数   
			closeTip();
			showError(i18nShow('host_res_operateErr'));
		},
		success : function(data){
			closeTip();
			if(data!=null && data=="success"){
			showTip(i18nShow('host_res_pmReboot_success'));
			$("#exitMaintenanceMode").attr("style", "display:block");
			$("#exitMaintenanceMode").bind('click',function(){exitMaintenanceModeMethod();});
			$("#pmClose").attr("style", "display:block");
			$("#pmClose").bind('click',function(){pmCloseMethod();});
			$("#pmRestart").attr("style", "display:block");
			$("#pmRestart").bind('click',function(){pmRestart();});
			$("#maintenanceMode").attr("style", "display:none");
			$("#maintenanceMode").unbind('click');
			}
		}
	});
	$("#maintenanceMode").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#exitMaintenanceMode").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#pmClose").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	$("#pmRestart").removeAttr("disabled").removeClass("btn_disabled").addClass("btn_gray");
	});
}

//打开选择datastore的窗口
function showDataStore(){
	var dcId = $("#datacenterId").val();
	var hostId = $("#hostId").val();
	openDialog('showDataStore',i18nShow('compute_res_showDatastore'),700,420);
	$("#gridTableDiv").show();
	$("#gridPager1").show();
	//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#gridTable1").jqGrid().GridUnload("gridTable1");
		$("#gridTable1").jqGrid({
			url : ctx+"/resmgt-storage/device/getStorageDataStoresList.action",
			postData:{"dcId":dcId,"hostId":hostId},
			rownumbers : false, 
			datatype : "json", 
			mtype : "post", 
			height : 230,
			autowidth : true, 
			multiselect:false,
			colModel : [
			            {name : "id",index : "id",label : "id",width : 50,sortable : true,align : 'left',hidden:true},
			            {name : "poolName",index : "poolName",label : i18nShow('compute_res_datastore_resPoolName'),width : 100,sortable : true,align : 'left'},
			            {name : "deviceName",index : "deviceName",label : i18nShow('compute_res_datastore_storageDevice'),width : 80,sortable : true,align : 'left'},
			            {name : "storageType",index : "storageType",label : i18nShow('compute_res_datastore_storageType'),	width : 80,sortable : true,align : 'left'},
			            {name : "orderNum",index : "orderNum",label : i18nShow('compute_res_datastore_orderNum'),width : 30,sortable : true,align : 'left'},
			            {name : "name",index : "name",label : i18nShow('compute_res_datastore_name'),	width : 80,sortable : true,align : 'left'},
			            {name : "path",index : "path",label : i18nShow('compute_res_datastore_path'),	width : 100,sortable : true,align : 'left'},
			            {
			    			name : "defaultDataStore",
			    			index : "defaultDataStore",
			    			label : i18nShow('compute_res_whetherDefault'),
			    			width : 100,
			    			sortable : true,
			    			align : 'left',
			                formatter: function(cellValue,options,rewObject){
			    			var result = "";
			    			if(cellValue>0){
			    				  result = i18nShow('compute_res_yes');
			    			}
			    			if(cellValue==0 && rewObject.flag>=1){
			    				result =i18nShow('compute_res_no')+'<a  style=" margin-left: 20px;text-decoration:none;" href="javascript:#"  title="" onclick=saveDefaultDatastore("' + rewObject.id + '","' + rewObject.deviceName + '") >'+i18nShow('compute_res_defaultDataStore')+'</a>';
			    			}
			    				  return result;
			                }
			          },
			            {
			    			name : "flag",
			    			index : "flag",
			    			label : i18nShow('compute_res_whetherMount'),
			    			width : 135,
			    			sortable : true,
			    			align : 'left',
			                formatter: function(cellValue,options,rewObject){
			    			var result = "<font style='color:red;margin-right: 20px'>"+i18nShow('compute_res_volume_unmont')+"</font>"+'<a  style=" margin-right: 20px;text-decoration:none;" href="javascript:#"  title="" onclick=checkDataStore("' + rewObject.id + '","'+rewObject.storageType+'") >'+i18nShow('compute_res_datastore_mount')+'</a>';
			    			if(cellValue==1){
			    				  result = "<font style='color:black;margin-right: 20px'>"+i18nShow('compute_res_volume_mount')+"</font>"+'<a  style=" margin-right: 20px;text-decoration:none;" href="javascript:#"  title="" onclick=deleteDataStore("' + rewObject.id + '","'+rewObject.storageType+'") >'+i18nShow('compute_res_datastore_unmount')+'</a>';
			    			}if(cellValue==2 && rewObject.poolName==i18nShow('compute_res_nothing') ){
			    				result = i18nShow('compute_res_nothing');
			    			}
			    				  return result;
			                }
			          }
			         ],
			            
			viewrecords : true,
			sortname : "orderNum",
			rowNum : 10,
			rowList : [10, 20, 50, 100 ],
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			},
			pager : "#gridPager1",
			//caption : $("#lab_sdname").text()+"_DataStore数据",
			hidegrid : false
		});
		
}

//存储缺省的datastore
function saveDefaultDatastore(datastoreId,deviceName){
	var datastoreType;
	if(deviceName.trim()==i18nShow('compute_res_nothing')){
		datastoreType = 'LOCAL_DISK';
	}else{
		datastoreType = 'NAS_DATASTORE';
	}
	var hostId = $("#hostId").val();
	var dcId = $("#datacenterId").val();
	showTip(i18nShow('compute_res_defaultDataStore_tip'),function(){
		$.ajax({
	        async:false,
	        cache:false,
	        type: 'POST',
	        url:ctx+"/resmgt-common/device/saveDefaultDatastore.action",
	        data:{"cmDevicePo.id":hostId,"cmDevicePo.datastoreId":datastoreId,"cmDevicePo.datastoreType":datastoreType},
	        error: function () {//请求失败处理函数
	        	showError(i18nShow('compute_res_requestError'));
	        },
	        success:function(data){ //请求成功后处理函数。
	        	showTip(i18nShow('compute_res_saveSuccess'));
	        	searchDatastore();
	        }
	    });
	});
}
//关闭硬件状态的窗口
function closeHardWareStatus(){
	$("#objectIdTemp").val("");
	$("#showHardWareStatus").dialog("close");
}

//关闭选择datastore的窗口
function closeShowDataStore(){
	$("#objectIdTemp").val("");
	$("#showDataStore").dialog("close");
	var hostId = $("#hostId").val();
	loadDatastore(hostId);
}
//获取单选框的数据
function getCheckedRadio(div_name) {
	var id_temp = -1;
	for (var i = 0 ; i < document.getElementsByName(div_name).length ; i++) {
		if (document.getElementsByName(div_name)[i].checked) {
			id_temp = document.getElementsByName(div_name)[i].value;
			break;
		}
	}
	return id_temp;
}
//----------------------------------------
//清除选中的数据
function delStorageInfo(objectId){
	$("#deviceTable").jqGrid("setCell", objectId, "datastore_id", " ");
	$("#deviceTable").jqGrid("setCell", objectId, "res_pool_name", " ");
}

//将物理机关联到集群
function relevance(){
	var countCmAllHostOfCDP=getCountCmAllHost();

	//获取选择的行集合。
	var ids = jQuery("#deviceTable").jqGrid('getGridParam','selarrrow');
	if(ids.length == 0){
		showError(i18nShow('compute_res_selectData'));
		return;
	}
	var sn="";
	var sn1="";
	
	var list = [ ];
	$(ids).each(function(index, id){
		var relevanceInfo = new Object();
		var rowData = $("#deviceTable").getRowData(id);
		relevanceInfo.cluster_id=clusterIdSpecified;//集群id
		relevanceInfo.clusterName=clusterName;//集群名称
		relevanceInfo.id=rowData.id;//主机id
		/*relevanceInfo.isBare = rowData.isBare ;//主机裸机状态
		relevanceInfo.bareV = rowData.bareV ;//主机判断裸机状态值
*/		relevanceInfo.Sn =rowData.sn ;//主机SN
		
		var hidden_vmType = $("#hidden_vmType").val();
		if(hidden_vmType != 'POWERVM'){
			//判断是否输入密码。
			if(rowData.username.trim() == "" || rowData.password.trim() == ""){
				sn1=rowData.sn;
				return false;
			}
		}
		relevanceInfo.username=rowData.username;//主机用户名
		relevanceInfo.password=rowData.password;//主机密码
		relevanceInfo.isManualSetIp=rowData.isManuallySetIP;//是否手动设置管理IP
		relevanceInfo.ipAddress=rowData.manageIP;//管理IP
		list[list.length] = relevanceInfo;
		//累计CDP下，物理机的总数。
		countCmAllHostOfCDP=countCmAllHostOfCDP+1;
	})
	
	if(countCmAllHostOfCDP>=200){
		showTip(i18nShow('compute_res_refDevice_toCluster_tip'),function(){closeTip(true)});
		return false;
	}
	if(sn1){
		showError('SN：'+sn+'，'+i18nShow('compute_res_refDevice_toCluster_tip2'));
		return;
	}
	showTip(i18nShow('compute_res_refDevice_toCluster'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url: ctx+"/resmgt-compute/host/updateDevice.action",
			async : false,
			data:{"relevanceInfo":JSON.stringify(list)},
			beforeSend: function () {
	        	showTip("load");
	        },
			success:(function(data){
	        	closeTip();
				$("#deviceTable").jqGrid().trigger("reloadGrid");
				if(data.errorTip!=""){
					showError(data.errorTip);
					return;
				}
			    if(data.relevanceInfoMapList2 != "" && data.relevanceInfoMapList2 != null && data.relevanceInfoMapList2 != undefined && data.relevanceInfoMapList2 !="undefined"){
					bizType = "cluster";
					var zTree = $.fn.zTree.getZTreeObj("treeRm");
					var sNodes = zTree.getSelectedNodes();
					if (sNodes && sNodes.length>0) {
						refreshZTreeAfterAdd(zTree,sNodes[0]);
					}
			    }else{
					showError(i18nShow('compute_res_refDevice_toCluster_fail'));
			    }
				showTip(i18nShow('compute_res_refDevice_toCluster_succss'));
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(XMLHttpRequest.responseText)
	        	closeTip();
				//showError("关联失败。");
			} 
		});
	});
	
}

//获取 集群所属的CDP内物理机数量
function getCountCmAllHost(){
	var countCmAllHostOfCDP;

	ajaxCall("/resmgt-compute/host/getCountCmAllHost.action",
			{"cluster_id" : clusterIdSpecified},
			function(){
				countCmAllHostOfCDP=ajaxResult.countCmAllHostOfCDP;
			}
		)
		
		return countCmAllHostOfCDP;
}

//关闭对话框
function closeSetingDiv(){
	$("#setingDiv").dialog("close");
}

//显示设置用户名和密码的对话框
function showSetingDiv(objectId){	
	$("#setingDiv").dialog({
		autoOpen : true,
		modal:true,
		height:270,
		width:360,
		title:i18nShow('host_res_setUserPwd'),
		close:function(){
				$("#isManuallySetIP").attr("checked",false);
				$("#manageIP").val("");
			},
//		draggable:false,
        resizable:true
	});
	
	var isManuallySetIP = $("#deviceTable").getCell(objectId,'isManuallySetIP');
	var manageIP = $("#deviceTable").getCell(objectId,'manageIP');
	document.getElementById("manageIP").disabled=true;	
	if (isManuallySetIP == 'true')
	{
		$("#setingDiv").dialog('option','height', 270);
		$("#isManuallySetIP").attr("checked",true);
		$("#manageIP").val(manageIP);
		document.getElementById("manageIP").disabled=false;	
	}
	
	//将objectId保存在隐藏域。
	$("#objectId").val(objectId);
	//加载对话框的用户名和密码输入框。
	var aa=$("#deviceTable").getCell(objectId, "username");
	$("#username").val(aa);
	$("#password").val($("#deviceTable").getCell(objectId, "password"));
	
//	if($("#isManuallySetIP").attr("checked")=="checked")
//	{
//		$("#setingDiv").dialog('option',
//				'height', 320);
//	}	
}

//将用户名和密码设置到表格的指定位置。
function setingBtn(){
	var objectId=$("#objectId").val();
	
	if($("#username").val().trim()==""){
		showTip(i18nShow('host_res_userTip'));
		return false;
	}else if($("#password").val().trim()==""){
		showTip(i18nShow('host_res_pwdTip'));
		return false;
	}else{
		if($("#username").val().length>30){
			showTip(i18nShow('host_res_userTip_len'));
			return false;	
//		}else if($("#password").val().length != 8){
//			showTip("请输入8位数密码。");
//			return false;
		}else{
			if (($("#isManuallySetIP").attr("checked")=="checked"))
			{
				if (!checkIPV4($("#manageIP").val())) {
					// alert("请正确填写B段IP值！");
					showTip(i18nShow('host_res_ipAdd_tip'));
					return;
				}
				else
				{
					$("#deviceTable").jqGrid("setCell", objectId, "isManuallySetIP", "true");
				}
			}
			else
			{
				$("#deviceTable").jqGrid("setCell", objectId, "isManuallySetIP", "false");
			}
			$("#deviceTable").jqGrid("setCell", objectId, "username", $("#username").val());
			$("#deviceTable").jqGrid("setCell", objectId, "password", $("#password").val());
			$("#deviceTable").jqGrid("setCell", objectId, "manageIP", $("#manageIP").val());
		}
	}
	
	closeSetingDiv();
}

//返回已关联的物理机信息页面
function rtnCanRelevanceInfo() {

	$("#cluster_div div").show();
	//已关联的主机信息DIV，显示。
	$("#cluster_show_div").show();
	//未关联的主机信息DIV，隐藏。
	$("#device_show_div").hide();

	nodeId = clusterIdSpecified;
	zTree.selectNode(zTree.getNodeByParam("id",clusterIdSpecified,null));
	bizType = "cluster";
	clickFun();
}

//检验IP
function checkIPV4(value) {
	return /^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$/i
			.test(value);
}


