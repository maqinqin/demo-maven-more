/**
 * 查询页面初始化-ls
 */

function initRoute() {

	var queryData = {};
	
	
	$("#gridTable").jqGrid({
		url : ctx + "/resmgt/common/route/getDeviceList.action",
		rownumbers : true,
		datatype : "json",
		postData : queryData,
		mtype : "post",
		height : heightTotal() + 70,
		autowidth : true,
		//multiselect:true,//是否显示下拉框
		multiboxonly: false,
		colNames : [ 'id',i18nShow('rm_router_datacenter'), i18nShow('rm_router_name'),i18nShow('openstack_floating_ip_address'),i18nShow('rm_nw_external_sub_mask')/*,i18nShow('rm_router_defalt_gateway')*/,i18nShow('com_remark'),i18nShow('com_operate')],
		colModel : [ {
			name : "id",
			index : "id",
			width: 120,
			sortable : true,
			hidden : true
		}, {
			name : "datacenterId",
			index : "datacenterId",
			sortable : true,
			width: 180,
			align : 'left',
			
		},{
			name : "name2",
			index : "name2",
			sortable : true,
			width: 180,
			align : 'left'
		},{
			name : "ip",
			index : "ip",
			sortable : true,
			width: 180,
			align : 'left'
		} ,{
			name : "mask",
			index : "mask",
			sortable : true,
			width: 180,
			align : 'left'
		} /*,{
			name : "gateway",
			index : "gateway",
			sortable : true,
			width: 180,
			align : 'left'
		} */,{
			name : "remark",
			index : "remark",
			sortable : true,//排序属性
			width: 180,
			align : 'left'
		} ,{
			name : "option",
			index : "option",
			width : 180,
			sortable : false,
			align:'left',
			formatter : function(cellVall, options, rowObject) {
				var updateFlag = $('#updateFlag').val();
				var deleteFlag = $('#deleteFlag').val();
				var ret = "　　";
				if(updateFlag){
					ret += '<a  style="margin-right: 10px;margin-left: -25px;text-decoration:none;" href="javascript:#" title=""  onclick=addRoute("' + rowObject.id + '")>'+i18nShow('com_update')+'</a>';
				}
				if(deleteFlag){
					ret += '<a  style="margin-right: 10px;text-decoration:none;" href="javascript:#" title=""  onclick=deletes("' + rowObject.id + '")>'+i18nShow('com_delete')+'</a>';
				}
				return ret;
				}
			}],
		viewrecords : true,
		sortname : "name",
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
	
		$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridMain").width());
		$("#gridTable").setGridHeight(heightTotal() + 70);
    }); 
	$("#add_update_Route_Form").validate({
		rules:{
			"route_dataCenter_id":{required:true},
			"name":{required:true},
			"ip":{required:true,isIPAddress : true},
			"mask":{required:true,isIPAddress : true}
			/*"gateway":{required:true,isIPAddress : true}*/
			},
		messages:{
			"route_dataCenter_id":{required:i18nShow('validate_data_required')},
			"name":{required:i18nShow('validate_data_required')},
			"ip":{required:i18nShow('validate_data_required'),isIPAddress : i18nShow('validate_rm_router_ip')},
			"mask":{required:i18nShow('validate_data_required'),isIPAddress : i18nShow('validate_rm_router_mask')}
	        /*"gateway":{required:i18nShow('validate_data_required'),isIPAddress : i18nShow('validate_rm_router_gate')}*/
		},
		 submitHandler:function(){
			 save();
		 }
		 
	 });

}
//判断输入的是否为IP格式
isIPAddress=function(ip)   
{   
    var re = /^$|^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/   
    return re.test(ip);
}
jQuery.validator.addMethod("isIPAddress", isIPAddress, "请输入正确的IP地址");
//清空页面
function clearTable(tableId){
	 var tab = document.getElementById(tableId) ;
	 var inputs = tab.getElementsByTagName("input"); 
	 for(var k=0;k<inputs.length;k++) 
	 { 
		 if(inputs[k].type!='button'  && inputs[k].id != 'route_dataCenter_id' ){
			 inputs[k].value=""; 
		 }
	 } 
	 var texts = tab.getElementsByTagName("textarea"); 
	  for(var k=0;k<texts.length;k++) 
	 { 
			 texts[k].value=""; 
	 } 
}
function search(){
	
	var queryData = {
			
			name2 : $("#route_name").val().replace(/(^\s*)|(\s*$)/g, ""),
			ip:$("#route_ip").val().replace(/(^\s*)|(\s*$)/g, "")
		};
	jqGridReload("gridTable", queryData);
}
function addRoute(id){
	$("label.error").remove();//清楚提示信息
	clearTable('add_update_Route_Div');//
	  $("#id").val("");
	 $("#route_dataCenter_id").val("");
	$("#name").val("");
	$("#ip").val("");
	$("#mask").val("");
	/*$("#gateway").val("");*/
	$("#remark").val("");
	var title;
	if(id){
		
		title=i18nShow('rm_router_update');
		
		$.post(ctx + "/resmgt/common/route/viewCmRoute.action",{"cmRoutePo.id" : id},function(data){
			$("#id").val(data.id);
			$("#route_dataCenter_id").val(data.datacenterId);
			$("#name").val(data.name2);
			$("#ip").val(data.ip);
			$("#mask").val(data.mask);
			/*$("#gateway").val(data.gateway);*/
			$("#remark").val(data.remark);
			$("#add_update_Route_Div").dialog({
				width : 654,
				autoOpen : true,
				modal : true,
				height : 290,
				title:title,
				//resizable:false //控制div大小
			});
		});
		
	}else{
		
		title=i18nShow('rm_router_save');
	$("#add_update_Route_Div").dialog({
		width : 670,
		autoOpen : true,
		modal : true,
		height :290,
		title:title,
		//resizable:false
		
	});
	}
}
function saveRouteBtn(){
	$("#add_update_Route_Form").submit();
}
function save(){
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/resmgt/common/route/saveCmRoute.action",
		async : false,
       data : {//获得表单数据
			
    	    "cmRoutePo.id" : $("#id").val(),
    	    "cmRoutePo.datacenterId" : $("#route_dataCenter_id").val(),
			"cmRoutePo.name2" : $("#name").val(),
			"cmRoutePo.ip" : $("#ip").val(),
			"cmRoutePo.mask" : $("#mask").val(),
			/*"cmRoutePo.gateway" : $("#gateway").val(),*/
			"cmRoutePo.remark" : $("#remark").val()
			
		},
		success : function(){
			closeView("add_update_Route_Div");
			search();
		},
		error : function() {
			showTip(i18nShow('tip_error'),null,"red");
		}
	});
	
}
function closeView(id){
	$("#"+id).dialog("close");
}
function deletes(id){
	// alert(id);
	if(id){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.post(ctx+"/resmgt/common/route/deleteCmRoute.action",{
				"cmRoutePo.id" : id
			},function(data){
				search();
				showTip(i18nShow('tip_delete_success'));
				
			});
		});
	}
	else{
		var ids = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');

		if(ids.length == 0){
			showError(i18nShow('error_select_one_data'));
			return;
		}
		var list = [];
		$(ids).each(function (index,id3){
			var rowData = $("#gridTable").getRowData(id3);
			list[list.length] = rowData.id;
			})
	
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.post(ctx+"/resmgt/common/route/deleteCmRoute.action",{
				"cmRoutePo.id" : list.join(",")
			},function(data){
				search();
				showTip(i18nShow('tip_delete_success'));
				
			});
		});
	}
}

function cancel(){
   $("#route_name").val("");
   $("#route_ip").val("");
}