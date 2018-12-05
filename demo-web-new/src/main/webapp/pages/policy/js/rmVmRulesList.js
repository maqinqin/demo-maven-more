var lastsel=null;
var sortTypes={};
$(function() {
		var sortTypeNames = getSortTypeName();
		$("#gridTable").jqGrid({
			url : ctx+"/policy/rmvmrules/queryRmVmRulesPoPaginationAct.action", 
			rownumbers : true, 
			datatype : "json", 
			mtype : "post", 
			height : 400,
			autowidth : true, 
//			multiselect:true,
			colNames:['rulesId',i18nShow('rm_vm_rule_softObjectName'),'sortObject',i18nShow('rm_vm_rule_softTypeName'),'是否有效',i18nShow('com_operate')],
			colModel : [ 
			            {name : "rulesId",index : "rulesId",width : 120,sortable : true,align : 'left',hidden:true},
			            {name : "sortObjectName",index : "sortObjectName",	width : 120,sortable : true,align : 'left'},
			            {name : "sortObject",index : "sortObject",	width : 120,sortable : true,align : 'left',hidden:true},
			            {name : "sortTypeName",index : "sortTypeName",	width : 130,editable : true, edittype : "select",editoptions:{value:sortTypeNames}, sortable : false,align : 'left'
//			            	formatter: function (cellVall,options,rowObject) { 
//			            		return sortTypes[cellVall]; 
//			            		}
			            },
			            {name : "isActive",index : "isActive",	width : 120,sortable : false,align : 'left',hidden:true},
			            {name:"option",index:"option",width:120,sortable:false,align:"left",
							formatter:function(cellVall,options,rowObject){
								 var result = "   ";
								 var updateFlag =  $("#updateFlag").val();
								 if(updateFlag == "1"){
									 //result += "<input type='button' style=\" margin-right: 10px;\" class='btn_edit_s' onclick=showUpdate('"+options.rowId+"')  title='修改' /><input type='button' style=\" margin-right: 10px;\" class='btn_ok_s' onclick=updateData('"+options.rowId+"')  title='保存' />";
									 result += "<a  style='margin-right: 10px;margin-left: -15px;text-decoration:none;' href='javascript:#' title=''  onclick=showUpdate('"+options.rowId+"')>"+i18nShow('com_update')+"</a><a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=updateData('"+options.rowId+"')>"+i18nShow('com_save')+"</a>";
								 }
								return result;
							}
			            }
			            /*<a href='#' style=' text-decoration:none' onclick=deleteData('"+rowObject.rulesId+"')>删除</a>*/
			            ],
			viewrecords : true,
			sortname : "rulesId",
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
//			editurl: ctx+"/policy/rmvmrules/saveRmVmRulesPoAct.action",
//			pager : "#gridPager",
//			caption : "虚机分配规则管理",
			hidegrid : false
		});
		
		$(window).resize(function() {
			$("#gridTable").setGridWidth($("#gridTableDiv").width());
	    });
		$("#updateForm").validate({
			rules: {
				sortObject: "required",
				sortType: "required"
			},
			messages: {
				sortObject: i18nShow('validate_data_required'),
				sortType: i18nShow('validate_data_required')
			},
			submitHandler: function() {
				updateOrSaveData();
			}
		});
		
	});	

function getSortTypeName(){
	var sortTypeName ="";
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/policy/rmvmrules/getSortTypeNamesAct.action", {"_":new Date().getTime()}, function(data) {
		 var jsonobj=eval(data);
	     var length=jsonobj.length;
		 for(var i=0;i<length;i++){
            if(i!=length-1){
            	sortTypes[jsonobj[i].dicCode] = jsonobj[i].dicName; 
            	sortTypeName+=jsonobj[i].dicCode+":"+jsonobj[i].dicName+";";
            }else{
            	sortTypes[jsonobj[i].dicCode] = jsonobj[i].dicName; 
            	sortTypeName+=jsonobj[i].dicCode+":"+jsonobj[i].dicName;
            }
	      }
	});	
	return sortTypeName;
}

function showUpdate(id){
//	  jQuery('#gridTable').restoreRow(lastsel2);
	if(lastsel && lastsel != id){
		updateData(lastsel);
//		alert(lastsel);
	}
	lastsel = id;
    jQuery('#gridTable').editRow(id,true);
//      jQuery('#gridTable').restoreRow(id);
}
function updateData(id){
	
	var rowData = $("#gridTable").jqGrid("getRowData", id); 
	var data = {'rmVmRulesPo.rulesId':rowData.rulesId,'rmVmRulesPo.sortObject':rowData.sortObject,'rmVmRulesPo.sortType':rowData.sortType,'rmVmRulesPo.isActive':rowData.isActive};
	saveparameters = {
		    "url" : ctx+"/policy/rmvmrules/updateRmVmRulesPoAct.action",
		    "extraparam" : data,
		    "restoreAfterError" : true,
		    "mtype" : "POST",
		    successfunc: function( response ) {
		    	$("#gridTable").jqGrid().trigger("reloadGrid");
		    }
		}
	jQuery("#gridTable").jqGrid('saveRow',id,saveparameters);
//	jQuery('#gridTable').restoreRow(id);
//	jQuery('#gridTable').jqGrid('restoreRow', id)
}
function showUpdateDiv(objectId){
		$("label.error").remove();
		$("#updateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:200,
				width:300,
				title:i18nShow('rm_vm_rule_update')
		})
		$.post(ctx+"/policy/rmvmrules/findRmVmRulesPoByIdAct.action",{"rmVmRulesPo.rulesId" : objectId},function(data){
			$("#rulesId").val(data.rulesId);
//			$("#sortObject").val(data.sortObject);
//			$("#sortType").val(data.sortType);
			$("#isActive").val(data.isActive);
			$("#method").val("update");
			selectByValue("sortObject", data.sortObject);
			selectByValue("sortType", data.sortType);
		})
	}

	function saveOrUpdateBtn(){
		$("#updateForm").submit();  
	}

	function updateOrSaveData(){
		var method = $("#method").val();
		var rulesId = $("#rulesId").val();
		var sortObject =$('#sortObject option:selected').val();
		var sortType = $('#sortType option:selected').val();;
		var isActive = $("#isActive").val();
		var url;
		if(method=="update"){
			url= ctx+"/policy/rmvmrules/updateRmVmRulesPoAct.action"
		}else{
			url= ctx+"/policy/rmvmrules/saveRmVmRulesPoAct.action"
		}
		var data = {'rmVmRulesPo.rulesId':rulesId,'rmVmRulesPo.sortObject':sortObject,'rmVmRulesPo.sortType':sortType,'rmVmRulesPo.isActive':isActive};
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
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
				showTip(i18nShow('tip_save_fail')); 
			} 
		});
	}

	function createData(){
		$("label.error").remove();
		$("#updateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:200,
				width:300,
				title:i18nShow('rm_vm_rule_save')
		});
		clearTab();
		selectByValue("sortObject", "");
		selectByValue("sortType","");
		$("#method").val("save");
	}
	function deleteData( dataId){
		if(dataId){
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/policy/rmvmrules/deleteRmVmRulesPoByIdAct.action",
					async : false,
					data:{"rmVmRulesPo.rulesId":dataId},
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
				showError(i18nShow('error_select_one_data'));
				return;
			}
			var list = [];
			$(ids).each(function (index,id){
				var rowData = $("#gridTable").getRowData(id);
				list[list.length] = rowData.rulesId;
				})
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/policy/rmvmrules/deleteRmVmRulesPoByIdAct.action",
					async : false,
					data:{"rmVmRulesPo.rulesId":list.join(",")},
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
			 if(inputs[k].type!='button'){
				 inputs[k].value=""; 
			 }
		 } 
	}
	function closeView(){
		$("#updateDiv").dialog("close");
	}
	
