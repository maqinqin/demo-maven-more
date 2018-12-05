$(function(){
	
	$("#type").change(function(){
		checkTypeValue($("#type").val());
	})
	//alert($("#type").val());
	
	
	$('#gridTable').jqGrid({
			url : ctx+"/resmgt-common/server/queryRmGeneralServer.action", 
			datatype : "json", 
			mtype : "post", 
			height :heightTotal() + 40,
			autowidth : true, 
			rownumbers : true,
			//multiselect:true,
			colNames:['serverId',i18nShow('rm_general_server_name'),i18nShow('rm_general_server_type'),'IP',i18nShow('rm_general_server_mask'),i18nShow('rm_general_server_gateway'),i18nShow('rm_datacenter'), i18nShow('my_req_sr_vmuserName'),i18nShow('com_operate')],
			colModel : [ 
						{name:"id", index:"id",hidden:true, sortable : true},
			            {name : "serverName",index : "serverName",sortable : true,align : 'left', width:100},
			            {name : "typeName",index : "typeName",sortable : true,align : 'left', width:100},
			            {name : "serverIp",index : "serverIp",sortable : true,align : 'left', width:130},
			            {name : "subMask",index : "subMask",sortable : false,align : 'left', width:130},
			            {name : "gateway",index : "gateway",sortable : false,align : 'left', width:100},
			            {name : "datacenterName",index : "datacenterName",sortable : true,align : 'left'},
			            {name : "userName",index : "userName",sortable : false,align : 'left'},
			            {name : "option",index : "option",sortable : false,align : 'left',formatter:function(cellVal,options,rowObject){
			            	var result = "";
			            	var deleteFlag =  $('#deleteFlag').val();
			            	var updateFlag = $('#updateFlag').val();
			            	if(updateFlag == "1"){
			            		result +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=preUpdateOrSaveData('"+rowObject.id+"') >"+i18nShow('com_update')+"</a>";
			            	}
			            	if(deleteFlag =='1'){
			            		result +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteData('"+rowObject.id+"')>"+i18nShow('com_delete')+"</a>";
			            	}		            	
							return result;
			            }}
			            ],
			sortname : "id",            
			viewrecords : true,
			rowNum : 10,
			rowList : [10, 20, 50, 100 ],
			prmNames : {
				search : "reloadGrid"
			},
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			},
			pager : "#pageGrid",
			hidegrid : false
	});
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#gridTableDiv").width());
		$("#gridTable").setGridHeight(heightTotal() + 40);
    });
	$('#serviceForm').validate({
		rules : {
			"rmGeneralServerVo.serverName":{
				required:true,
				remote:{
					type:"POST",
					url:ctx+"/resmgt-common/server/checkServerName.action",
					data : {
						"rmGeneralServerVo.serverName":function(){return $('#serverName').val()},
						"rmGeneralServerVo.id" : function(){return $('#serverId').val();}
					}
				}
			},
			"rmGeneralServerVo.type":'required',
			"rmGeneralServerVo.serverIp":{
				required:true,
				isIPAddress : true
			},
			"rmGeneralServerVo.subMask":{isIPAddress : true},
			"rmGeneralServerVo.gateway":{isIPAddress : true},
			"rmGeneralServerVo.datacenterId":{required:true},
			"rmGeneralServerVo.userName":{required:true,"maxlength":30},
			"rmGeneralServerVo.password":{required:true,"maxlength":50}
		},
		messages:{
			"rmGeneralServerVo.serverName":{required:i18nShow('validate_data_required'),remote:i18nShow('validate_data_remote')},
			"rmGeneralServerVo.type":i18nShow('validate_data_required'),
			"rmGeneralServerVo.serverIp":{
				required:i18nShow('validate_data_required'),
				isIPAddress : i18nShow('validate_general_server_ip')
			},
			"rmGeneralServerVo.subMask":{isIPAddress : i18nShow('validate_general_server_mask')},
			"rmGeneralServerVo.datacenterId":{required:i18nShow('validate_data_required')},
			"rmGeneralServerVo.gateway":{isIPAddress : i18nShow('validate_general_server_gate')},
			"rmGeneralServerVo.userName":{required:i18nShow('validate_data_required'),"maxlength":i18nShow('validate_general_server_username')},
			"rmGeneralServerVo.password":{required:i18nShow('validate_data_required'),"maxlength":i18nShow('validate_general_server_pwd')}
		},
		submitHandler: function() {
			updateOrSaveData();
		}
	});
});

//判断输入的是否为IP格式
isIPAddress=function(ip)   
{   
    var re = /^$|^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/   
    return re.test(ip);
}
jQuery.validator.addMethod("isIPAddress", isIPAddress, "请输入正确的IP地址");
//清空页面
function clearTab(tableId){
	 var tab = document.getElementById(tableId) ;
	 var inputs = tab.getElementsByTagName("input"); 
	 for(var k=0;k<inputs.length;k++) 
	 { 
		 if(inputs[k].type!='button'  && inputs[k].id != 'type' && inputs[k].id != 'datacenterId'){
			 inputs[k].value=""; 
		 }
	 } 
	 var texts = tab.getElementsByTagName("textarea"); 
	  for(var k=0;k<texts.length;k++) 
	 { 
			 texts[k].value=""; 
	 } 
}
function closeDiv(){
	$('#updateDiv').dialog("close");
}
function reloadGrid(){
	var queryData = {
		serverName:$('#queryServerName').val().replace(/(^\s*)|(\s*$)/g, ""),
		type:$('#serverTypeSel').val(),
		serverIp:$('#queryIP').val().replace(/(^\s*)|(\s*$)/g, ""),
		datacenterId:$('#queryDataCenter').val(),
	}
	jqGridReload("gridTable", queryData);
}
function preUpdateOrSaveData(serverId){
	
	checkTypeValue($("#type").val());
	
	$("label.error").remove();
	$("#p_username").show();
	$("#p_pwd").show();
	
	clearTab('updateDiv');
	emptyValue("serverName");
	
	var title;
	if(serverId){
		title = i18nShow('rm_general_server_update');
		$.post(ctx+"/resmgt-common/server/queryRmGeneralServerById.action",{"rmGeneralServerVo.id":serverId},function(data){
//			alert(JSON.stringify(data));
			if(data){
				$('#serverId').val(data.id);
				$('#serverName').val(data.serverName);
				$('#type').val(data.type);
				$('#serverIp').val(data.serverIp);
				$('#subMask').val(data.subMask);
				$('#gateway').val(data.gateway);
				$('#datacenterId').val(data.datacenterId);
				$('#userName').val(data.userName);
				$('#password').val(data.password);
				
				$('#serviceMethod').val('update');
				$('#updateDiv').dialog({autoOpen : true, title :title,height: 330,width : 670,modal : true });
				checkTypeValue($("#type").val());
			}else{
				showTip("no data!");
				reloadGrid();
			}
		})
	}else{
		title = i18nShow('rm_general_server_save');
		selectByValue('type','');
		selectByValue('datacenterId','');
		$('#serviceMethod').val('create');
		$('#updateDiv').dialog({autoOpen : true, title :title,height: 360,width :670,modal : true });
		checkTypeValue($("#type").val());
	}
}
function updateOrSaveForm(){
	$('#serviceForm').submit();
}
//新增、修改
function updateOrSaveData(){
	var serviceMethod = $('#serviceMethod').val();
	var url;
	if(serviceMethod=='update'){
		url = ctx + "/resmgt-common/server/updateRmGeneralServer.action";
		if($("#type").val() == "NTP_SERVER" || $("#type").val() == "DNS_SERVER"){
			$("#userName").val('');
			$("#password").val('');
		}
	}else{
		url = ctx + "/resmgt-common/server/saveRmGeneralServer.action";
	}
//	$.post(url,data,function(data){
//		$('#updateDiv').dialog("close");
//		reloadGrid();
//	});
	var data = $('#serviceForm').serialize();
	
	$.ajax({
		type:'post',
		datatype:"json",
		url:url,
		async:false,
		data:data,
		beforeSend:function(){
			showTip("load");
		},
		success:function(data){
			closeTip();
			$('#updateDiv').dialog("close");
			reloadGrid();
			showTip(i18nShow('tip_save_success'));
		},
		error: function(){
			showTip(i18nShow('tip_save_fail'));
		}
	});
}
//删除
function deleteData(serverId){
	if(serverId){
		showTip(i18nShow('tip_delete_confirm'),function(){
//			$.post(ctx+"/resmgt-common/server/deleteRmGeneralServer.action",{"rmGeneralServerVo.id":serverId},function(data){
//				reloadGrid();
//			})
			$.ajax({
				type:'post',
				datatype:'json',
				url:ctx+"/resmgt-common/server/deleteRmGeneralServer.action",
				async:false,
				data:{"rmGeneralServerVo.id":serverId},
				beforeSend:function(){
					showTip("load");
				},
				success:function(data){
					closeTip();
					reloadGrid();
					showTip(i18nShow('tip_delete_success'));
				},
				error: function(){
					showTip(i18nShow('tip_delete_fail'));
				}
			});
		})
	}else{
		var ids = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');
		if(ids.length==0){
			showError(i18nShow('error_select_one_data'));
			return;
		}
		var list = [];
		$.each(ids, function(index, id) {
			var rowData = $("#gridTable").jqGrid("getRowData", id);		//通过行id获取Grid的当前行数据
//			alert(JSON.stringify(rowData));
			list[list.length] = rowData.id;
		});
		showTip(i18nShow('tip_delete_confirm'),function(){
//			$.post(ctx+"/resmgt-common/server/deleteRmGeneralServer.action",{"rmGeneralServerVo.id":list.join(",")},function(data){
//				reloadGrid();
//			})
			$.ajax({
				type:'post',
				datatype:'json',
				url:ctx+"/resmgt-common/server/deleteRmGeneralServer.action",
				async:false,
				data:{"rmGeneralServerVo.id":list.join(",")},
				beforeSend:function(){
					showTip("load");
				},
				success:function(data){
					closeTip();
					reloadGrid();
					showTip(i18nShow('tip_delete_success'));
				},
				error: function(){
					showTip(i18nShow('tip_delete_fail'));
				}
			});
		})
	}
}

function checkTypeValue(typevalue){
	if(typevalue == "NTP_SERVER" || typevalue == "DNS_SERVER"){
		$("#p_username").hide();
		$("#p_pwd").hide();
	}else if(typevalue == "SCRIPT_SERVER"||typevalue == "AUTOMATION_SERVER"){
		$("#p_username").show();
		$("#p_pwd").show();
	}	
}