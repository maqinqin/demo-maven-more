$(function() {
		$("#gridTable").jqGrid({
			url : ctx+"/network/converge/queryRmNwConvergePoPaginationAct.action", 
			rownumbers : true, 
			datatype : "json", 
			mtype : "post", 
			height : heightTotal() + 80,
			autowidth : true, 
			//multiselect:true,
			colNames:['convergeId',i18nShow('rm_nw_converge_name'),i18nShow('rm_datacenter'),'是否有效',i18nShow('com_operate')],
			colModel : [ 
			            {name : "convergeId",index : "convergeId",sortable : true,align : 'left',hidden:true},
			            {name : "convergeName",index : "convergeName",	sortable : true,align : 'left',editor:"text", width:100},
			            {name : "datacenterName",index : "datacenterName",	sortable : true,align : 'left',editor:"text"},
			            {name : "isActive",index : "isActive",sortable : false,align : 'left',hidden:true},
			            {name:"option",index:"option",sortable:false,align:"left", width:80,
							formatter:function(cellVall,options,rowObject){
								 var result = "   ";
								 var updateBut = "<a  style='margin-right: 10px;margin-left: -15px;text-decoration:none;' href='javascript:#' title=''  onclick=showUpdateDiv('"+rowObject.convergeId+"') >"+i18nShow('com_update')+"</a>";
								 var deleteBut = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteData('"+rowObject.convergeId+"') >"+i18nShow('com_delete')+"</a>";
								 var updateFlag =  $("#updateFlag").val();
								 var deleteFlag = $("#deleteFlag").val();
								 if(updateFlag == "1"){
									 result += updateBut;
								 }
								 if(deleteFlag == "1"){
									 result += deleteBut;
								 }
								return result;
							}
			            }
			            ],
			viewrecords : true,
			sortname : "convergeId",
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
			pager : "#gridPager",
//			caption : "虚机分配参数管理",
			hidegrid : false
		});
		
		$(window).resize(function() {
			$("#gridTable").setGridWidth($("#gridTableDiv").width());
			$("#gridTable").setGridHeight(heightTotal() + 80);
	    });
		
		/**************表单验证开始****************/
		jQuery.validator.addMethod("convergeNameCheck", function() { 
			var validateValue=true;
			var method = $("#method").val();
			var addIsActive = "Y";
			var addConvergeName = $("#addConvergeName").val();
			var addDatacenterId = $("#addDatacenterIdSel").val();
			var convergeId;
			if (method != "update")
			{
				convergeId = "";
			}
			$.ajax({
				type:'post',
				datatype : "json",
				data : {'rmNwConvergePo.convergeId':convergeId,'rmNwConvergePo.convergeName':addConvergeName,'rmNwConvergePo.datacenterId':addDatacenterId,'rmNwConvergePo.isActive':addIsActive},
				url:ctx+"/network/converge/checkConvergeName.action",
				async : false,
				success:(function(data){
//					alert(data.result);
					if(data.result != "Y"){
						validateValue=false;
					}		
				}),
			});
			return validateValue;
			},
		"网络汇聚名称已存在！"); 
		
		$("#updateForm").validate({
			rules: {
				"convergeName": {required : true, convergeNameCheck : true},
				"datacenterId": {required : true}
			},
			messages: {
				"convergeName": {required : i18nShow('validate_data_required'), convergeNameCheck : i18nShow('validate_data_remote')},
				"datacenterId": {required : i18nShow('validate_data_required')}
			},
//	    	errorPlacement:function(error,element){
//	    		error.insertAfter("#tip_error");
//	    	},
			submitHandler: function() {
				updateOrSaveData();
			}
		});
		
		initConvergeNameSelect();
		
		initDatacenterSelect();
	});	
//初始化网络汇聚名称下拉框
function initConvergeNameSelect()
{
	$("#convergeIdSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/converge/queryConvergeNameList.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#convergeIdSel").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}

//初始化网络汇聚名称下拉框
function initDatacenterSelect()
{
	$("#datacenterIdSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/converge/queryDatacenterList.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#datacenterIdSel").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}

//初始化新增修改窗口网络汇聚名称下拉框
function initAddDatacenterSelect()
{
	$("#addConvergeName").val("");
	$("#addDatacenterIdSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/converge/queryDatacenterList.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#addDatacenterIdSel").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}

function initObjectTypeSelect(value){ 
	$("#objectId").html("");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/converge/queryDatacenterList.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#addDatacenterIdSel").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}


//查询
function search(){
//	var queryData = {'convergeId':$("#convergeIdSel").val(),'datacenterId':$("#datacenterIdSel").val()};
	var queryData = {'convergeName':$("#convergeNameInput").val().replace(/(^\s*)|(\s*$)/g, ""),'datacenterId':$("#datacenterIdSel").val()};
	jqGridReload("gridTable", queryData);
}

function showUpdateDiv(convergeId){
		$("label.error").remove();
		$("#updateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:200,
				width:350,
				title:i18nShow('rm_nw_converge_update'),
				position: "middle"
		})
		$("#cdpTdId").attr("display","block"); 
		$.post(ctx+"/network/converge/findRmNwConvergePoByIdAct.action",{"rmNwConvergePo.convergeId" : convergeId},function(data){
			initAddDatacenterSelect();
			selectByValue("addDatacenterIdSel",data.datacenterId);
			$("#addConvergeName").val(data.convergeName);
			$("#isActive").val(data.isActive);
			$("#method").val("update");
		})
	}

	function submitForm()
	{
		$("#updateForm").submit();
	}
	
	function updateOrSaveData(){
		var method = $("#method").val();
		var addIsActive = "Y";
		var addConvergeName = $("#addConvergeName").val();
		var addDatacenterId = $("#addDatacenterIdSel").val();
		var url;
		if(method=="update"){
			url= ctx+"/network/converge/updateRmNwConvergePoAct.action"
		}else{
			url= ctx+"/network/converge/saveRmNwConvergePoAct.action"
		}
		var data = {'rmNwConvergePo.convergeName':addConvergeName,'rmNwConvergePo.datacenterId':addDatacenterId,'rmNwConvergePo.isActive':addIsActive};
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			/*beforeSend:(function(data){
				return validate(datas);
			}),*/
			beforeSend: function () {
	        	showTip("load");
	        },
			success:(function(data){
				closeTip();
				showTip(i18nShow('tip_save_success'));   
				$("#updateDiv").dialog("close");
				$("#gridTable").jqGrid().trigger("reloadGrid");
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showTip(i18nShow('tip_save_fail')+XMLHttpRequest.responseText);
			} 
		});
	}

	function createData(){
		$("label.error").remove();
		$("#updateDiv").dialog({
				autoOpen : true,
				bgiframe : true, //解决IE6,兼容问题
				modal:true,
				height:200,
				width:350,
				position: "middle",
				title:i18nShow('rm_nw_converge_save'),
				draggable:true
		});
		//clearTab();
		$("#method").val("");
		initAddDatacenterSelect();
		
		//selectByValue("objectType","");
		//flatSelectByValue("objectType","CDP");
//        initObjectTypeSelect("CDP");
        //flatSelectByValue("paramType","MAX_CPU_UTILIZATION");
		//selectByValue("paramType","");
		//$("#objectId").html("<option value=''>请选择...</option>");
		//$("#method").val("save");
	}
	
	function deleteData(dataId){
		if(dataId){
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/network/converge/deleteRmNwConvergePoByIdAct.action",
					async : false,
					data:{"rmNwConvergePo.convergeId":dataId},
					/*beforeSend:(function(data){
						if(confirm("确定删除数据？")){
							return true;
						}else{
							return false;
						}
					}),*/
					success:(function(data){
						showTip(i18nShow('deletesuc'));
						//showTip("删除成功!");
						$("#gridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showTip(i18nShow('tip_delete_fail'));
					} 
				});
			});
		}else{
			var ids = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');
//			alert(ids);
			if(ids.length == 0){
				showError(i18nShow('error_select_one_data'));
				return;
			}
			var list = [];
			$(ids).each(function (index,id){
				var rowData = $("#gridTable").getRowData(id);
				list[list.length] = rowData.convergeId;
				})
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/network/converge/deleteRmNwConvergePoByIdAct.action",
					async : false,
					data:{"rmNwConvergePo.convergeId":list.join(",")},
					success:(function(data){
						showTip(i18nShow('deletesuc'));
						//showTip("删除成功!");
						$("#gridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showTip(i18nShow('tip_delete_fail'));
					} 
				});
			});
		};

	}
	function clearTab(){
		 var tab = document.getElementById("updateTab") ;
		 var inputs = tab.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button' && inputs[k].id != 'objectType' && inputs[k].id != 'paramType'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	function closeView(){
		$("#updateDiv").dialog("close");
	}
	

	
