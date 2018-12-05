$(function() {
	var queryData = {};
		$("#gridTable").jqGrid({
			url : ctx+"/cloud-service/image/showSoftAll.action", 
			rownumbers : true, 
			datatype : "json", 
			postData : queryData,
			mtype : "post", 
			height : heightTotal() + 100,
			autowidth : true, 
			//multiselect:true,
			colNames:['softwareId',i18nShow('image_soft_ware_name'),i18nShow('image_soft_ware_type'),i18nShow('image_soft_ware_type'),i18nShow('image_soft_ware_path'),i18nShow('image_soft_ware_remark'),'是否有效',i18nShow('com_operate')],
			colModel : [ 
			            {name : "softwareId",index : "softwareId",width : 120,sortable : true,align : 'left',hidden:true},
			            {name : "softwareName",index : "softwareName",width : 120,sortable : true,align : 'left'},
			            {name : "softwareType",index : "softwareType",	width : 120,sortable : true,align : 'left',hidden:true},
			            {name : "softwareTypeName",index : "softwareTypeName",	width : 120,sortable : true,align : 'left'},
			            {name : "softwarePath",index : "softwarePath",	width : 130,sortable : true,align : 'left'},
			            {name : "remark",index : "remark",	width : 130,sortable : true,align : 'left'},
			            {name : "isActive",index : "isActive",	width : 120,sortable : false,align : 'left',hidden:true},
			            {name:"option",index:"option",width:120,sortable:false,align:"left",
							formatter:function(cellVall,options,rowObject){
								var result = "  ";
								 var updateBut = "<a  style='margin-right: 10px;margin-left: -10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdateDiv('"+rowObject.softwareId+"') >"+i18nShow('com_update')+"</a>";
								 var deleteBut = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"+rowObject.softwareId+"') >"+i18nShow('com_delete')+"</a>";
								 var updateFlag =  $("#updateFlag").val();
								 var deleteFlag = $("#deleteFlag").val();
								 var verFlag = $("#verFlag").val();
								 var s = "";
								 if(updateFlag == "1"){
									 result += updateBut;
								 }
								 if(deleteFlag == "1"){
									 result += deleteBut;
								 }
								 if(verFlag == "1"){
									 s += "<option value='1' >"+i18nShow('image_soft_ware_version')+"</option >";
								 }
								 if(verFlag == "1"){
									 result += "<select onchange=\"selMeched(this,'"+rowObject.softwareId+"')\" style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"+i18nShow('com_select_defalt')+"</option>'"+s+"'</select>" ;
								 }
								return result;
							}
			            }
			            ],
			viewrecords : true,
			sortname : "softwareId",
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
			hidegrid : false
		});
		
		$(window).resize(function() {
			$("#gridTable").setGridWidth($("#gridTableDiv").width());
			$("#gridTable").setGridHeight(heightTotal() + 100);
	    });
		
		$("#softwareVerTable").jqGrid({
			url : ctx+"/cloud-service/image/showVerAllBySoftId.action", 
			rownumbers : true, 
			postData:{"softwareId":"null"},
			datatype : "json", 
			mtype : "post", 
			height : 200,
			autowidth : true, 
			//multiselect:true,
			colNames:['softwareVerId',i18nShow('image_soft_ware_version_name'),i18nShow('image_soft_ware_version_remark'),'是否有效',i18nShow('com_operate')],
			colModel : [ 
			            {name : "softwareVerId",index : "softwareVerId",sortable : true,align : 'left',hidden:true},
			            {name : "verName",index : "verName", sortable : true,align : 'left'},
			            {name : "remark",index : "remark", sortable : true,align : 'left'},
			            {name : "isActive",index : "isActive",sortable : false,align : 'left',hidden:true},
			            {name:"option",index:"option",sortable:false,align:"left",
							formatter:function(cellVall,options,rowObject){
								 var result = " ";
								 var updateVarBut = "<a  style='margin-right: 10px;margin-left: -5px;text-decoration:none;' href='javascript:#' title=''  onclick=showUpdateVerDiv('"+rowObject.softwareVerId+"')>"+i18nShow('com_update')+"</a>";
								 var deleteVarBut = "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteVerData('"+rowObject.softwareVerId+"')>"+i18nShow('com_delete')+"</a>";
								 var updateVarFlag =  $("#updateVarFlag").val();
								 var deleteVarFlag = $("#deleteVarFlag").val();
								 if(updateVarFlag == "1"){
									 result += updateVarBut;
								 }
								 if(deleteVarFlag == "1"){
									 result += deleteVarBut;
								 }
								return result;
							}
			            }
			            ],
			viewrecords : true,
			sortname : "softwareVerId",
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			},
			hidegrid : false
		});
		
		$("#softwareVerForm").validate({
			rules: {
				verName: {	required:true,
					        remote:{                
					               type:"POST",
					               url:ctx+"/cloud-service/image/checkSoftwareVerNameAct.action",
					               data:{
					            	   "cloudSoftwareVer.softwareVerId":function(){return $("#softwareVerId").val();},"cloudSoftwareVer.softwareId":function(){return $("#verSoftwareId").val();},"cloudSoftwareVer.verName":function(){return $("#verName").val();}
					               		} 
					               } 
					      },
			    verRemark: "required"
			},
			messages: {
				verName: {required:i18nShow('validate_data_required'),remote:i18nShow('validate_data_remote')},
				verRemark: i18nShow('validate_data_required')
			},
			submitHandler: function() {
				updateOrSaveVerData();
			}
		});
		
		$("#updateForm").validate({
			rules: {
				softwareName:{required:true,
			        remote:{                
			               type:"POST",
			               url:ctx+"/cloud-service/image/checkSoftwareNameAct.action",
			               data:{
			            	   "cloudSoftware.softwareId":function(){return $("#softwareId").val();},"cloudSoftware.softwareName":function(){return $("#softwareName").val();}
			               } 
			              } 
			            },
				softwareType: "required",
				softwarePath: "required"
			},
			messages: {
				softwareName:{required:i18nShow('validate_data_required'),remote:i18nShow('validate_data_remote')},
				softwareType: i18nShow('validate_data_required'),
				softwarePath: i18nShow('validate_data_required')
			},
			submitHandler: function() {
				updateOrSaveData();
			}
		});
	});	

function selMeched(element,id){
	var val = element.value;
	if(val == "1"){
		selectVer(id);
	}
}
function clearAll(){
	$("#selectSoftName").val("");
}


function showVerData(){
	$("label.error").remove();
	emptyValue("verName");
	$("#softwareVerDiv").dialog({
			autoOpen : true,
			modal:true,
			height:240,
			title:i18nShow('image_soft_ware_version_save'),
			width:350
	});
	clearVerTab();
	$("#verMethod").val("save");
}
function deleteData( dataId){
	if(dataId){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/cloud-service/image/deleteSoftWare.action",
				async : false,
				data:{"cloudSoftware.softwareId":dataId},
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
			list[list.length] = rowData.softwareId;
			})
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/cloud-service/image/deleteSoftWare.action",
				async : false,
				data:{"cloudSoftware.softwareId":list.join(",")},
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
function createData(){
	emptyValue("softwareName");
	$("label.error").remove();
	$("#updateDiv").dialog({
			autoOpen : true,
			modal:true,
			height:250,
			width:670,
			title:i18nShow('image_soft_ware_save')
	});
	clearTab();
	selectByValue("softwareType","");
	$("#objectId").html("<option value=''> </option>");
	$("#method").val("save");
}
//删除软件版本
function deleteVerData( dataId){
	if(dataId){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/cloud-service/image/deleteSoftWareVer.action",
				async : false,
				data:{"cloudSoftwareVer.softwareVerId":dataId},
				success:(function(data){
					showTip(i18nShow('tip_delete_success'));
					$("#softwareVerTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('tip_delete_fail'));
				} 
			});
		});
	}else{
		var ids = jQuery("#softwareVerTable").jqGrid('getGridParam','selarrrow');

		if(ids.length == 0){
			alert(i18nShow('error_select_one_data'));
			return;
		}
		var list = [];
		$(ids).each(function (index,id){
			var rowData = $("#softwareVerTable").getRowData(id);
			list[list.length] = rowData.softwareVerId;
			})
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/cloud-service/image/deleteSoftWareVer.action",
				async : false,
				data:{"cloudSoftwareVer.softwareVerId":list.join(",")},
				success:(function(data){
					showTip(i18nShow('tip_delete_success'));
					$("#softwareVerTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showTip(i18nShow('tip_delete_fail'));
				} 
			});
		});
	};

}

function saveOrUpdateBtn(){
	$("#updateForm").submit();  
}
function updateOrSaveData(){
	var method = $("#method").val();
	var softwareId = $("#softwareId").val();
	var softwareName = $("#softwareName").val();
	var softwareType = $("#softwareType").val();
	var softwarePath = $("#softwarePath").val();
	var remark = $("#remark").val();
	var isActive = $("#isActive").val();
	var url;
	if(method=="update"){
		url= ctx+"/cloud-service/image/updateSoftWare.action"
	}else{
		url= ctx+"/cloud-service/image/insertSoftWare.action"
	}
	var data = {'cloudSoftware.softwareId':softwareId,'cloudSoftware.softwareName':softwareName,'cloudSoftware.softwareType':softwareType,'cloudSoftware.softwarePath':softwarePath,'cloudSoftware.remark':remark,'cloudSoftware.isActive':isActive};
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
			$("#updateDiv").dialog("close");
			showTip(i18nShow('tip_save_success'));
			$("#gridTable").jqGrid().trigger("reloadGrid");
			closeTip();
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
        	showTip(i18nShow('tip_save_fail'));
		} 
	});
}

function showUpdateDiv(objectId){
	emptyValue("softwareName");
	$("label.error").remove();
	$("#updateDiv").dialog({
			autoOpen : true,
			modal:true,
			height:230,
			width:670,
			title:i18nShow('image_soft_ware_update')
//			draggable: false,
	})
	$.post(ctx+"/cloud-service/image/showSoftWareBysoftWareId.action",{"cloudSoftware.softwareId" : objectId},function(data){
		$("#softwareId").val(data.softwareId);
		$("#softwareName").val(data.softwareName);
		$("#softwarePath").val(data.softwarePath);
		$("#remark").val(data.remark);
		$("#isActive").val(data.isActive);
		$("#method").val("update");
		selectByValue("softwareType", data.softwareType);
	})
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
	

function selectVer(softwareId) {
	$("#softwareVersDiv").dialog({
		autoOpen : true,
		modal:true,
		height:400,
		title:i18nShow('image_soft_ware_version_view'),
		width:700,
		close:function(){
			  $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
		}
	})	
	$("#verSoftwareId").val(softwareId);
	$("#softwareVerTable").jqGrid("setGridParam", { postData : {softwareId:softwareId} }).trigger("reloadGrid");
	//自适应宽度
	$("#softwareVerTable").setGridWidth($("#softwareVerTableDiv").width());
	$(window).resize(function() {
		$("#softwareVerTable").setGridWidth($("#softwareVerTableDiv").width());
    });
}
//	window.open(ctx+"/cloud-service/image/showVerBysoftWareIdAll.action?cloudSoftware.softwareId="+softwareId+"");
	
	
	function saveVerBtn(){
		$("#softwareVerForm").submit();  
	}
	function updateOrSaveVerData(){
		var method = $("#verMethod").val();
		var softwareId = $("#verSoftwareId").val();
		var softwareVerId = $("#softwareVerId").val();
		var verName = $("#verName").val();
		var remark = $("#verRemark").val();
		var isActive = $("#verIsActive").val();
		var url;
		if(method=="update"){
			url= ctx+"/cloud-service/image/updateSoftWareVer.action";
		}else{
			url= ctx+"/cloud-service/image/insertSoftWareVer.action";
		}
		var data = {'cloudSoftwareVer.softwareVerId':softwareVerId,'cloudSoftwareVer.softwareId':softwareId,'cloudSoftwareVer.verName':verName,'cloudSoftwareVer.remark':remark,'cloudSoftwareVer.isActive':isActive};
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
				$("#softwareVerDiv").dialog("close");
				$("#softwareVerTable").jqGrid().trigger("reloadGrid");
				showTip(i18nShow('tip_save_success'));
				closeTip();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
	        	showTip(i18nShow('tip_save_fail'));
			} 
		});
	}

	/**
	 * 点击修改时调用的方法
	 * @param objectId
	 * @return
	 */
	function showUpdateVerDiv(objectId){
			$("label.error").remove();
			emptyValue("verName");
			$("#softwareVerDiv").dialog({
					autoOpen : true,
					modal:true,
					height:280,
					title:i18nShow('image_soft_ware_version_update'),
					width:350
			})
			$.post(ctx+"/cloud-service/image/showSoftWareVerUpdate.action",{"cloudSoftwareVer.softwareVerId" : objectId},function(data){
				$("#softwareVerId").val(data.softwareVerId);
				$("#verName").val(data.verName);
				$("#verRemark").val(data.remark);
				$("#verIsActive").val(data.isActive);
				$("#verMethod").val("update");
			});
		}

	function clearVerTab(){
		 var tab = document.getElementById("updateVerTab") ;
		 var inputs = tab.getElementsByTagName("input"); 
		 for(var k=0;k<inputs.length;k++) 
		 { 
			 if(inputs[k].type!='button'){
				 inputs[k].value=""; 
			 }
		 } 
	}


	function closeVer(){
		$("#softwareVerDiv").dialog("close");
	}

	function search(){
		var queryData = {
				selectSoftName : $("#selectSoftName").val().replace(/(^\s*)|(\s*$)/g, "")
			};
		jqGridReload("gridTable", queryData);
//		$("#gridTable").jqGrid("setGridParam", {
//			postData : queryData
//		}).trigger("reloadGrid");
	}
