$(function() {
		$("#gridTable").jqGrid({
			url : ctx+"/policy/rmvmparam/queryRmVmParamPoPaginationAct.action", 
			rownumbers : true, 
			datatype : "json", 
			mtype : "post", 
			height : heightTotal() + 60 ,
			autowidth : true, 
			//multiselect:true,
			colNames:['paramId',i18nShow('rm_vm_para_objectTypeName'),i18nShow('rm_vm_para_objectName'),i18nShow('rm_vm_para_paramTypeName'),i18nShow('rm_vm_para_value'),'是否有效',i18nShow('com_operate')],
			colModel : [ 
			            {name : "paramId",index : "paramId",sortable : true,align : 'left',hidden:true},
			            {name : "objectTypeName",index : "objectTypeName",	sortable : true,align : 'left',editor:"text", width:70},
			            {name : "objectName",index : "objectName",	sortable : true,align : 'left',editor:"text",
							formatter:function(cellVall,options,rowObject){
								return (cellVall == null || cellVall=="") ? i18nShow('rm_vm_para_objectName_global'):cellVall;
							}
			            },
			            {name : "paramTypeName",index : "paramTypeName",sortable : true,align : 'left', width:80},
			            {name : "value",index : "value",sortable : false,align : 'left', width:50},
			            {name : "isActive",index : "isActive",sortable : false,align : 'left',hidden:true},
			            {name:"option",index:"option",sortable:false,align:"left", width:50,
							formatter:function(cellVall,options,rowObject){
								 var result = "   ";
								 var updateBut = "<a  style='margin-right: 10px;margin-left: -15px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdateDiv('"+rowObject.paramId+"') >"+i18nShow('com_update')+"</a>";
								 var deleteBut = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"+rowObject.paramId+"') >"+i18nShow('com_delete')+"</a>";
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
			sortname : "paramId",
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
			$("#gridTable").setGridHeight(heightTotal() + 60);
	    });
		
		$("#updateForm").validate({
			rules: {
				//objectType: "required",
				//objectId: {equalTo:""},
				//paramType: "required",
				value:{
				//	number:true ,
					required:true
				//	min:0
				}
			},
			messages: {
				//objectType: "参数对象类型不能为空",
				//objectId: "参数对象不能为空",
			//	paramType: "参数类别不能为空",
				value:{
					number:i18nShow('validate_number') ,
					required:i18nShow('validate_data_required'),
					min:i18nShow('validate_vmBase_min')
				}
			},
//	    	errorPlacement:function(error,element){
//	    		error.insertAfter("#tip_error");
//	    	},
			submitHandler: function() {
				updateOrSaveData();
			}
		});
		changeObjectTypeSelect($("#objectTypeSel").val());
		$("#objectTypeSel").change(function() { 
			changeObjectTypeSelect(this.value);
		});
	});	

function initObjectTypeSelect(value){ 
	if(value == "POOL"){
		$("#objectId").removeAttr("disabled");
		$("#objectId").html("<option value='1'>"+i18nShow('com_select_defalt')+"...</option>");
		//$("#objectId").html("");
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/policy/rmvmparam/queryPoolList.action", {"_":new Date().getTime()}, function(data) {
			$.each(data, function() {
				$("#objectId").append("<option value='" + this.id + "'>" + this.name + "</option>");
			});
		});	
	}else if(value == "CDP"){
		$("#objectId").removeAttr("disabled");
		$("#objectId").html("<option value='1'>"+i18nShow('com_select_defalt')+"...</option>");
//		$("#objectId").html("");
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/policy/rmvmparam/queryCdpList.action", {"_":new Date().getTime()}, function(data) {
			$.each(data, function() {
				$("#objectId").append("<option value='" + this.id + "'>" + this.name + "</option>");
			});
		});	
	}else if(value == "GLOBAL"){
		$("#objectId").html("<option value=''>"+i18nShow('rm_vm_para_objectName_global')+"</option>");
		$("#objectId").attr("disabled","disabled");
	}else{
		$("#objectId").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	}
}

function changeObjectTypeSelect(value){ 
	
	if(value == "POOL"){
		//$("#objectIdSel").html("<option value=''>资源池</option>");
		$("#objectId").html("");
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/policy/rmvmparam/queryPoolList.action", {"_":new Date().getTime()}, function(data) {
			$.each(data, function() {
				$("#objectIdSel").append("<option value='" + this.id + "'>" + this.name + "</option>");
			});
		});	
	}else if(value == "CDP"){
		//$("#objectIdSel").html("<option value=''>CDP</option>");
		$("#objectId").html("");
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/policy/rmvmparam/queryCdpList.action", {"_":new Date().getTime()}, function(data) {
			$.each(data, function() {
				$("#objectIdSel").append("<option value='" + this.id + "'>" + this.name + "</option>");
			});
		});	
	}else if(value == "GLOBAL"){
		$("#objectIdSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option><option value=''>"+i18nShow('rm_vm_para_objectName_global')+"</option>");
	}else{
		$("#objectIdSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	}
}
//查询
function search(){
	var queryData = {'objectType':$("#objectTypeSel").val(),'objectId':$("#objectIdSel").val(),'paramType':$("#paramTypeSel").val()};
	jqGridReload("gridTable", queryData);
}

function showUpdateDiv(objectId){
		$("label.error").remove();
		$("#updateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:380,
				width:590,
				title:i18nShow('rm_vm_para_update'),
				position: "middle"
		})
		$("#cdpTdId").attr("display","block"); 
		$.post(ctx+"/policy/rmvmparam/findRmVmParamPoByIdAct.action",{"rmVmParamPo.paramId" : objectId},function(data){
			initObjectTypeSelect(data.objectType);
			$("#paramId").val(data.paramId);
			//$("#objectType").val(data.objectType);
			manuFlatSelectByValue("objectType",data.objectType,"initObjectTypeSelect");
			//flatSelectByValue("objectType",data.objectType);
			selectByValue("objectId",data.objectId);
			//selectByValue("paramType",data.paramType);
			flatSelectByValue("paramType",data.paramType);
			$("#value").val(data.value);
			$("#isActive").val(data.isActive);
			$("#method").val("update");
			//initObjectTypeSelect(data.objectType);
		})
		
	}
	function manuFlatSelectByValue(id,value,fun){
	    if(typeof(id) != "undefined" && id != null && id.indexOf(".") != -1){
	         id = id.replace(".","\\.");
	    }
	 	$("#"+id+"_span a").each(function(){ 
	 	    $(this).removeClass();
	 	    $(this).attr("onclick","selectObj(this,'"+id+"');"+fun+"(this.getAttribute('value'))");
		    if(this.getAttribute('value') == value){
		       $("#"+id).val(value);
		       $(this).addClass('unit current');
		    }else{
		       $(this).addClass('unit');
		    }
		 });
	}

	function saveOrUpdateBtn(){
		$("#updateForm").submit();  
	}
	function updateOrSaveData(){
		var method = $("#method").val();
		var paramId = $("#paramId").val();
		var objectType = $("#objectType").val();
		var objectId = $("#objectId").val();
		var paramType = $("#paramType").val();
		var value = $("#value").val();
		var isActive = $("#isActive").val();
		
		var url;
		if(objectId=="1"){
			showTip(i18nShow('tip_rm_vm_para_type_need'));
			return;
		}
		if(method=="update"){
			url= ctx+"/policy/rmvmparam/updateRmVmParamPoAct.action"
		}else{
			url= ctx+"/policy/rmvmparam/saveRmVmParamPoAct.action"
		}
		var data = {'rmVmParamPo.paramId':paramId,'rmVmParamPo.objectType':objectType,'rmVmParamPo.objectId':objectId,'rmVmParamPo.paramType':paramType,'rmVmParamPo.value':value,'rmVmParamPo.isActive':isActive};
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
		//initObjectTypeSelect("GLOBAL");
		//window.location.reload();
		$("label.error").remove();
		$("#updateDiv").dialog({
				autoOpen : true,
				bgiframe : true, //解决IE6,兼容问题
				modal:true,
				height:380,
				width:620,
				position: "middle",
				title:i18nShow('rm_vm_para_save'),
				draggable:true
		});
		
		clearTab();
		$("#method").val("save");
	}
	function deleteData( dataId){
		if(dataId){
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/policy/rmvmparam/deleteRmVmParamPoByIdAct.action",
					async : false,
					data:{"rmVmParamPo.paramId":dataId},
					/*beforeSend:(function(data){
						if(confirm("确定删除数据？")){
							return true;
						}else{
							return false;
						}
					}),*/
					success:(function(data){
						showTip(i18nShow('tip_delete_success'));
						$("#gridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showTip(i18nShow('tip_delete_fail'));
					} 
				});
			});
		}else{
			var ids = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');

			if(ids.length == 0){
//				alert('至少选择一条数据!');
				showError(i18nShow('error_select_one_data'));
				return;
			}
			var list = [];
			$(ids).each(function (index,id){
				var rowData = $("#gridTable").getRowData(id);
				list[list.length] = rowData.paramId;
				})
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/policy/rmvmparam/deleteRmVmParamPoByIdAct.action",
					async : false,
					data:{"rmVmParamPo.paramId":list.join(",")},
					success:(function(data){
						showTip(i18nShow('tip_delete_success'));
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
	
	
	
