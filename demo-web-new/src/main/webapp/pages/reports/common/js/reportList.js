function initCmDeviceList() {
	$("#deviceGridTable").jqGrid({
		url : ctx+"/reports/common/getReportList.action", ///reports/common/save.action
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal()  + 60,
		autowidth : true, // 是否自动调整宽度
		/*multiselect:true,*/
		multiboxonly: false,
		colNames:['id','报表关键字',i18nShow('sys_report_name'),'报表描述关键字',i18nShow('sys_report_remark'),i18nShow('sys_report_JASPER_url'),i18nShow('sys_report_menu_url'),i18nShow('com_operate')],
		colModel : [  {
			name : "id",
			index : "id",
			label : "id",
			width : 120,
			sortable : true,
			align : 'left',
			hidden: true	
		}, {
			name : "reportNameKey",
			index : "reportNameKey",
			label : "报表关键字",
			width : 120,
			sortable : true,
			align : 'left',	
			hidden: true	
		}, {
			name : "reportNameValue",
			index : "reportNameValue",
			label : "报表名称",
			width : 120,
			sortable : true,
			align : 'left'
			
		}, {
			name : "reportDecKey",
			index : "reportDecKey",
			label : "报表描述关键字",
			width : 150,
			sortable : true,
			align : 'left',
			hidden: true	
		}, {
			name : "reportDecValue",
			index : "reportDecValue",
			label : "报表描述",
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			
			name : "jasperPath",
			index : "jasperPath",
			label : "JASPER地址",
			width : 200,
			sortable : true,
			align : 'left'	
		}, {
			name : "reportPath",
			index : "reportPath",
			label : "菜单地址",
			width : 200,
			sortable : false,
			align : 'left',
			formatter : function(cellValue,options,rowObject) {
				return "/reports/common/showReport.action?id="+rowObject.id;
			}
		}, {
			name : 'option',
		    index : 'option',
		    label : "操作",
			width : 80,
			align : "left",
			sortable:false,
			formatter : function(cellValue,options,rowObject) {
				var updateFlag = $("#updateFlag").val();
				var deleteFlag = $("#deleteFlag").val();
				var matchFlag = $("matchFlag").val();
				return 	"<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdateDiv('"+rowObject.id+"') >"+i18nShow('com_update')+"</a><a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteReport('"+rowObject.id+"') >"+i18nShow('com_delete')+"</a>";
			}														
		}],
		
		viewrecords : true,
		sortname : "id",
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
		hidegrid : false
	});
	//验证设备表单
	
	jQuery.validator.addMethod("reportNameCheck", function(value, element) { 
		var validateValue=true;
		var method=$("#reportType").val();
		var reportName = $("#reportNameValue").val();
		if(value!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"reportName":value},
			url : ctx+"/reports/common/getReportForName.action",
			async : false,
			success:(function(data){
				if(method=="update"){
					if(data==null||data.reportNameValue==reportName){
						validateValue=true;
					}else{
						validateValue=false;
					}
					
				}else{
				if(data==null){
					validateValue=true;
				}else{
					validateValue=false;
				}
				}
			}),
			
		});
		}
		return this.optional(element) || validateValue;
		},
	"报表名称不能重复"); 
	//-----------------
	$("#createReportForm").validate({
		rules: {
			reportNameKey: {
			        required: true
			    },
			    reportNameValue: {
			    	required:true,
			    	reportNameCheck:true
			    },
			    descKey: {
			        required: true
			    },
			    descValue: {
			        required: true
			    },
			    conKey: {
			        required: true
			    },
			    conValue:{
			    	required : true
			    },
			    proKey: {
			        required: true
			    },
			    proValue: {
			        required: true
			    },
			    sqlKey: {
			        required: true
			    },
			    sqlValue:{
			    	 required: true
			    },
			    jasperPath:{
			    	required:true
			    },
			    sqlSelectValue:{
			    	required:true
			    }
		},
		messages: {
			reportNameKey:{required:i18nShow('validate_data_required')},
			reportNameValue:{required:i18nShow('validate_data_required'),reportNameCheck:i18nShow('validate_data_remote')},
			descKey: {required:i18nShow('validate_data_required')},
			descValue:{required:i18nShow('validate_data_required')},
			conKey: {required:i18nShow('validate_data_required')},
			conValue: {required:i18nShow('validate_data_required')},
			proKey: {required:i18nShow('validate_data_required')},
			proValue:{required:i18nShow('validate_data_required')},
			sqlKey: {required:i18nShow('validate_data_required')},
			sqlValue: {required:i18nShow('validate_data_required')},
			sqlSelectValue: {required:i18nShow('validate_data_required')},
			jasperPath:{required:i18nShow('validate_data_required')}
		},
		submitHandler: function() {
			save();
		}
	});
	

	        
			$("#deviceGridTable").setGridWidth($("#deviceGridTable_div").width());
			$(window).resize(function() {
				$("#deviceGridTable").setGridWidth($("#deviceGridTable_div").width());
				$("#deviceGridTable").setGridHeight(heightTotal() + 60);
		    });
}

function saveOrUpdateBtn(){
	$("#createReportForm").submit();  
}

//查询列表
function search() {
	$("#deviceGridTable").jqGrid('setGridParam', {
		url : ctx+"/reports/common/getReportList.action",//你的搜索程序地址
		postData : {
			"reportName" : $.trim($("#reportName").val()),
		}, //发送搜索条件
		pager : "#gridPager"
	}).trigger("reloadGrid"); //重新载入
}
//删除设备
function deleteReport(dataId){
	showTip(i18nShow('tip_delete_confirm'),function(){
	$.ajax({
		type:'post',
		datatype : "json",
		url : ctx+"/reports/common/deleteReportList.action",
		//url:ctx+"/resmgt-common/device/deleteDeviceListAction.action",
		async : false,
		data:{"crpo.id":dataId},
		success:(function(data){
			showTip(i18nShow('tip_delete_success'));
			$("#deviceGridTable").jqGrid().trigger("reloadGrid");
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showError(i18nShow('tip_delete_fail'));
		} 
	});
	});
}

//弹出修改设备窗口
 function showUpdateDiv(objectId){
	 $("#datastoreGridTable").clearGridData(); 
		$("label.error").remove();
		$("#list").hide();
		$("#updateDiv").show();
		$("#s_updateTitle").html(i18nShow('sys_report_update'));
		$("#reportType").val("update");
		$("#reportID").val(objectId);
		clearTab();
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/reports/common/getReport.action",
			async : false,
			data:{"id":objectId},
			success:(function(data){
				$("#sqlUL").children(":first").nextAll('li').remove(); 
				$("#conditionsUl").children(":first").nextAll('li').remove();
				$("#reportNameKey").val(data.reportNameKey);
				$("#reportNameValue").val(data.reportNameValue);
				$("#descKey").val(data.reportDecKey);
				$("#descValue").val(data.reportDecValue);
				$("#jasperPath").val(data.jasperPath);
				for(var i = 0 ; i < data.conProList.length ; i++){
					if(data.conProList[i].conType == "selectSql"){
						addSelectSqlConLi();
						$("#conKey_"+i+"").val(data.conProList[i].conKey);
						$("#conValue_"+i+"").val(data.conProList[i].conValue);
						$("#sqlSelectValue_"+i+"").val(data.conProList[i].conType_dec);
						$("#conId_"+i+"").val(data.conProList[i].id);
						if(data.conProList[i].isSqlParam == "Y"){
							var chk = document.getElementById("isSqlParam_"+i+"");//通过getElementById获取节点
							chk.checked = true;//设置checked为选中状态
						}
					}else if(data.conProList[i].conType == "time"){
						addTime();
						$("#conKey_"+i+"").val(data.conProList[i].conKey);
						$("#conValue_"+i+"").val(data.conProList[i].conValue);
						$("#conId_"+i+"").val(data.conProList[i].id);
						if(data.conProList[i].conType_dec == "y"){
							var chkY = document.getElementById("year_"+i+"");
							chkY.checked = true;
							var chkM = document.getElementById("month_"+i+"");
							chkM.removeAttribute("disabled");
							chkM.checked = false;
							var chkD = document.getElementById("day_"+i+"");
							chkD.setAttribute("disabled","disabled");
							chkD.checked = false;
						}else if(data.conProList[i].conType_dec == "ym"){
							var chkY = document.getElementById("year_"+i+"");
							chkY.checked = true;
							var chkM = document.getElementById("month_"+i+"");
							chkM.checked = true;
							var chkD = document.getElementById("day_"+i+"");
							chkD.removeAttribute("disabled");
							chkD.checked = false;
						}else if(data.conProList[i].conType_dec == "ymd"){
							var chkY = document.getElementById("year_"+i+"");
							chkY.checked = true;
							var chkM = document.getElementById("month_"+i+"");
							chkM.checked = true;
							var chkD = document.getElementById("day_"+i+"");
							chkD.removeAttribute("disabled");
							chkD.checked = true;
						}
						if(data.conProList[i].isSqlParam == "Y"){
							var chk = document.getElementById("isSqlParam_"+i+"");//通过getElementById获取节点
							chk.checked = true;//设置checked为选中状态
						}
					}else if(data.conProList[i].conType == "select"){
						addSelectConLi();
						$("#conKey_"+i+"").val(data.conProList[i].conKey);
						$("#conValue_"+i+"").val(data.conProList[i].conValue);
						$("#conId_"+i+"").val(data.conProList[i].id);
						delPro(document.getElementById("conLi"+i+"_pro0_value"));
						delPro(document.getElementById("conLi"+i+"_pro1_value"));
						for(var y = 0 ; y < data.conProList[i].proList.length ; y++){
							addPro(document.getElementById("conKey_"+i+""));
							$("#conLi"+i+"_pro"+y+"_key").val(data.conProList[i].proList[y].propertyKey);
							$("#conLi"+i+"_pro"+y+"_value").val(data.conProList[i].proList[y].propertyValue);
							$("#conLi"+i+"_pro"+y+"_Id").val(data.conProList[i].proList[y].id);
						}
						if(data.conProList[i].isSqlParam == "Y"){
							var chk = document.getElementById("isSqlParam_"+i+"");//通过getElementById获取节点
							chk.checked = true;//设置checked为选中状态
						}
					}else if(data.conProList[i].conType == "text"){
						addTextConLi();
						$("#conKey_"+i+"").val(data.conProList[i].conKey);
						$("#conValue_"+i+"").val(data.conProList[i].conValue);
						$("#conId_"+i+"").val(data.conProList[i].id);
						if(data.conProList[i].isSqlParam == "Y"){
							var chk = document.getElementById("isSqlParam_"+i+"");//通过getElementById获取节点
							chk.checked = true;//设置checked为选中状态
						}
					}
				}
				for(var i = 0 ; i < data.sqlList.length ; i++){
					addSql();
					$("#sqlKey_"+i+"").val(data.sqlList[i].sqlKey);
					$("#sqlValue_"+i+"").val(data.sqlList[i].sqlValue);
					$("#sqlId_"+i+"").val(data.sqlList[i].id);
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_req_fail'));
			} 
		});
	
 }


//弹出添加窗口
	function createData(){
		$("#datastoreGridTable").clearGridData();//清空datastore jqgrid表格
		$("label.error").remove();
		$("#list").hide();
		$("#updateDiv").show();
		$("#s_updateTitle").html(i18nShow('sys_report_save'));
		$("#reportType").val("add");
		clearTab();
		
	}
	//清空表单
	function clearTab(){
		 //var tab = document.getElementById("updateTab") ;
		 var inputs = document.getElementsByTagName("input"); 
		 $("#description").val("");
		 for(var k=0;k<inputs.length;k++){ 
			 if(inputs[k].type!='button'&&inputs[k].type!='hidden'){
				 inputs[k].value=""; 
			 }
		 } 
		 $("#sqlUL").children(":first").nextAll('li').remove(); 
			$("#conditionsUl").children(":first").nextAll('li').remove();
			addSelectConLi();
			addSql();
	}
	//关闭窗口
	function closeView(){
		$("#updateDiv").hide();
		$("#list").show();
	}
