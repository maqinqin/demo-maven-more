

$(function() {
		$("#gridTable").jqGrid({
			url : ctx+"/network/use/queryRmNwUsePoPaginationAct.action", 
			rownumbers : true, 
			datatype : "json", 
			mtype : "post", 
			height :heightTotal() + 88,
			autowidth : true, 
			//multiselect:true,
			colNames:['useId',i18nShow('ip_use_code'),i18nShow('ip_use_name'),'是否有效',i18nShow('com_operate')],
			colModel : [ 
			            {name : "useId",index : "useId",sortable : true,align : 'left',hidden:true},
			            {name : "useCode",index : "useCode",	sortable : true,align : 'left',editor:"text", width:100},
			            {name : "useName",index : "useName",	sortable : true,align : 'left',editor:"text"},
			            {name : "isActive",index : "isActive",sortable : false,align : 'left',hidden:true},
			            {name:"option",index:"option",sortable:false,align:"left", width:80,
							formatter:function(cellVall,options,rowObject){
								 var result = "   ";
								 var updateFlag =  $("#updateFlag").val();
								 var deleteFlag = $("#deleteFlag").val();
								 var useRelFlag = $("#useRelFlag").val();
									 if(updateFlag == "1"){
										 result += "<a  style='margin-right: 10px;margin-left: -15px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdateDiv('"+rowObject.useId+"') >"+i18nShow('com_update')+"</a>";
									 }
									 if(deleteFlag == "1"){
										 result += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"+rowObject.useId+"') >"+i18nShow('com_delete')+"</a>";
									 }
									 var s = "";
									 if(useRelFlag == "1"){
										 s +="<option value='1' >"+i18nShow('ip_use_rel')+"</option >";
									 }
									 if(useRelFlag == "1"){
										 result += "<select onchange=\"selMeched(this,'"+rowObject.useId+"','"+rowObject.useCode+"')\" style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"+i18nShow('com_select_defalt')+"</option>'"+s+"'</select>" ;
									 }
								return result;
							}
			            }
			            ],
			viewrecords : true,
			sortname : "useId",
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
			$("#gridTable").setGridHeight(heightTotal() + 88);
	    });
		
		/**************表单验证开始****************/
		jQuery.validator.addMethod("useCodeCheck", function() { 
			var validateValue=true;
			var method = $("#method").val();
			var addIsActive = "Y";
			var addUseCode = $("#addUseCode").val();
			var useId;
			if (method != "update")
			{
				useId = "";
			}
			$.ajax({
				type:'post',
				datatype : "json",
				data : {'rmNwUsePo.useId':useId,'rmNwUsePo.useCode':addUseCode,'rmNwUsePo.isActive':addIsActive},
				url:ctx+"/network/use/checkUseCode.action",
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
		"IP用途编码已存在！"); 
		
		jQuery.validator.addMethod("useNameCheck", function() { 
			var validateValue=true;
			var method = $("#method").val();
			var addIsActive = "Y";
			var addUseName = $("#addUseName").val();
			var useId;
			if (method != "update")
			{
				useId = "";
			}
			$.ajax({
				type:'post',
				datatype : "json",
				data : {'rmNwUsePo.useId':useId,'rmNwUsePo.useName':addUseName,'rmNwUsePo.isActive':addIsActive},
				url:ctx+"/network/use/checkUseName.action",
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
		"IP用途名称已存在！"); 
		
		$("#updateUseForm").validate({
			rules: {
				"useCode": {required : true, maxlength : 4, useCodeCheck : true},
				"useName": {required : true, useNameCheck : true}
			},
			messages: {
				"useCode": {required : i18nShow('validate_data_required'), maxlength : i18nShow('validate_data_length_4'), useCodeCheck : i18nShow('validate_data_remote')},
				"useName": {required : i18nShow('validate_data_required'), useNameCheck : i18nShow('validate_data_remote')}
			},
//	    	errorPlacement:function(error,element){
//	    		error.insertAfter("#tip_error");
//	    	},
			submitHandler: function() {
				updateOrSaveData();
			}
		});
	});	


function selMeched(element,id,code){
	var val = element.value;
	if(val == "1"){
		showUseRelDiv(id,code);
	}
}
//查询
function search(){
//	var queryData = {'useId':$("#useIdSel").val(),'datacenterId':$("#datacenterIdSel").val()};
	var queryData = {'useName':$("#useNameInput").val().replace(/(^\s*)|(\s*$)/g, ""),
			'useCode':$("#useCodeInput").val().replace(/(^\s*)|(\s*$)/g, "")};
	jqGridReload("gridTable", queryData);
}

function showUpdateDiv(useId){
		$("label.error").remove();
		$("#updateDiv").dialog({
				autoOpen : true,
				modal:true,
				height:240,
				width:350,
				title:i18nShow('ip_use_update'),
				position: "middle"
		})
		$("#cdpTdId").attr("display","block"); 
		$.post(ctx+"/network/use/findRmNwUsePoByIdAct.action",{"rmNwUsePo.useId" : useId},function(data){
//			initAddDatacenterSelect();
//			selectByValue("addDatacenterIdSel",data.datacenterId);
			$("#addUseCode").val(data.useCode);
			$("#addUseName").val(data.useName);
			$("#isActive").val(data.isActive);
			$("#method").val("update");
		})
	}

	function submitForm()
	{
		$("#updateUseForm").submit();
	}
	
	function updateOrSaveData(){
		var method = $("#method").val();
		var addIsActive = "Y";
		var addUseName = $("#addUseName").val();
		var addUseCode = $("#addUseCode").val();
		var url;
		if(method=="update"){
			url= ctx+"/network/use/updateRmNwUsePoAct.action"
		}else{
			url= ctx+"/network/use/saveRmNwUsePoAct.action"
		}
		var data = {'rmNwUsePo.useName':addUseName,'rmNwUsePo.useCode':addUseCode,'rmNwUsePo.isActive':addIsActive};
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
				height:250,
				width:350,
				position: "middle",
				title:i18nShow('ip_use_save'),
				draggable:true
		});
		$("#method").val("");
		$("#addUseCode").val("");
		$("#addUseName").val("");
	}
	
	function deleteData(dataId){
		if(dataId){
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/network/use/deleteRmNwUsePoByIdAct.action",
					async : false,
					data:{"rmNwUsePo.useId":dataId},
					success:(function(data){
						showTip(data.result);
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
				list[list.length] = rowData.useId;
				})
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/network/use/deleteRmNwUsePoByIdAct.action",
					async : false,
					data:{"rmNwUsePo.useId":list.join(",")},
					success:(function(data){
						showTip(i18nShow('compute_res_delSuccess'));
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
	
	function closeUseRelView(){
		$("#updateUseRelDiv").dialog("close");
	}
	
	function showUseRelDiv(useId, useCode){
		$("#useCodeMark").val("" + useCode);
		$("#useIdMark").val("" + useId);
		$("#rmNwUseRelGridTable").jqGrid().GridUnload("rmNwUseRelGridTable");
		$("#rmNwUseRelGridTable").jqGrid({
			url : ctx+"/network/use/queryRmNwUseRelPoPaginationByUseId.action?useId=" + useId, 
			rownumbers :true, 
			datatype : "json", 
			mtype : "post", 
			height : 350,
			autowidth : true, 
			//multiselect:true,
			colNames:['useRelId',i18nShow('ip_use_rel_code'),i18nShow('cloud_service_platform'),i18nShow('cloud_service_vm_type'),i18nShow('cloud_service_host_type'),'是否有效',i18nShow('com_operate')],
			colModel : [ 
			            {name : "useRelId",index : "useRelId",sortable : true,align : 'left',hidden:true},
			            {name : "useRelCode",index : "useRelCode",	sortable : true,align : 'left',editor:"text", width:100},
			            {name : "platformName",index : "platformName",	sortable : true,align : 'left',editor:"text"},
			            {name : "virtualTypeName",index : "virtualTypeName",	sortable : true,align : 'left',editor:"text"},
			            {name : "hostTypeName",index : "hostTypeName",	sortable : true,align : 'left',editor:"text"},
			            {name : "isActive",index : "isActive",sortable : false,align : 'left',hidden:true},
			            {name: "option",index:"option",sortable:false,align:"left", width:80,
							formatter:function(cellVall,options,rowObject){
								 var result = "   ";
								 var updateFlag =  $("#updateFlag2").val();
								 var deleteFlag = $("#deleteFlag2").val();
								 if(updateFlag == "1"){
									 result += "<a  style='margin-right: 10px;margin-left: -15px;text-decoration:none;' href='javascript:#' title=''  onclick=showUseRelUpdateDiv('"+rowObject.useRelId+"')>"+i18nShow('com_update')+"</a>";
								 }
								 if(deleteFlag == "1"){
									 result += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteUseRelData('"+rowObject.useRelId+"')>"+i18nShow('com_delete')+"</a>";
								 }
								return result;
							}
			            }
			            ],
			viewrecords : true,
			sortname : "useId",
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
			pager : "#rmNwUseRelGridPager",
//			caption : "虚机分配参数管理",
			hidegrid : false
		});
		
		//展示属性信息dialog
		$("#rmNwUseRel").dialog({ autoOpen : true,
			modal : true,
			height : 550,
			title:i18nShow('ip_use_rel'),
			width : 1050,
			close:function(){
				//
				 $( "#gridTable" ).jqGrid().trigger("reloadGrid" );
			}
		});
		//自适应页面大小
		$("#rmNwUseRelGridTable").setGridWidth($("#rmNwUseRelGridDiv").width());
		//当窗体大小改变（拖动角标或最大化、窗口化的时候），自适应页面大小
		$(window).resize(function() {
			$("#rmNwUseRelGridTable").setGridWidth($("#rmNwUseRelGridDiv").width());
	    });
		
		/**************表单验证开始****************/
		$("#useRelForm").validate({
			rules: {
				"platformId": {required : true},
				"virtualTypeId": {required : true},
				"hostTypeId": {required : true},
				"useRelCode": {required : true},
			},
			messages: {
				"platformId": {required : i18nShow('validate_data_required')},
				"virtualTypeId": {required : i18nShow('validate_data_required')},
				"hostTypeId": {required : i18nShow('validate_data_required')},
				"useRelCode": {required : i18nShow('validate_data_required')}
			},
			submitHandler: function() {
				updateOrSaveUseRelData();
			}
		});
	}	
	
	var platformJson = {};
	var virtualTypeJson = {};
	var hostTypeJson = {};
	var platformCode = "";
	var virtualTypeCode = "";
	var hostTypeCode = "";
	
	function createRmNwUseRelData(){
		$("#platformSel").unbind();//解绑change事件，防止每次调用initUseRelData时多次绑定change事件
		$("#virtualTypeSel").unbind();//解绑change事件，防止每次调用initUseRelData时多次绑定change事件
		$("#hostTypeSel").unbind();//解绑change事件，防止每次调用initUseRelData时多次绑定change事件
		platformJson = {};
		virtualTypeJson = {};
		hostTypeJson = {};
		platformCode = "";
		virtualTypeCode = "";
		hostTypeCode = "";

		$("label.error").remove();
		$("#updateUseRelDiv").dialog({
				autoOpen : true,
				bgiframe : true, //解决IE6,兼容问题
				modal:true,
				height:260,
				width:650,
				position: "middle",
				title:i18nShow('ip_use_rel_save'),
				draggable:false
		});
		var useCode = $("#useCodeMark").val();
		$("#methodUseRel").val("");
		$("#platformSel").val("");
		$("#virtualTypeSel").val("");
		$("#hostTypeSel").val("");
		$("#useRelCode").val(useCode);
//		$("#platformCodeMark").val("");
//		$("#virtualTypeCodeMark").val("");
//		$("#hostTypeCodeMark").val("");
		initUseRelData();
	}
	
	//初始化用途关系新增、修改页面下拉框数据
	function initUseRelData()
	{
		initPlatformSelect();
		$("#virtualTypeSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
		$("#platformSel").change(function(){
			if ($("#platformSel").val() == "")
			{
				$("#virtualTypeSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
			}
			else
			{
				initVirtualTypeSelect($("#platformSel").val());
			}
			platformCode = platformJson[$("#platformSel").val()];
			virtualTypeCode = "";
			spellUseRelCode();
		});
		initHostTypeSelect();
		$("#virtualTypeSel").change(function(){
			virtualTypeCode = virtualTypeJson[$("#virtualTypeSel").val()];
			spellUseRelCode();
		});
		$("#hostTypeSel").change(function(){
			hostTypeCode = hostTypeJson[$("#hostTypeSel").val()];
			spellUseRelCode();
		});
	}

	function submitUseRelForm()
	{
		$("#useRelForm").submit();
	}
	
	//拼写用途关系编码
	function spellUseRelCode()
	{
		var useCode = $("#useCodeMark").val();
		$("#useRelCode").val(platformCode + virtualTypeCode + hostTypeCode + useCode);
	}
	
	//初始化新增修改窗口平台下拉框
	function initPlatformSelect()
	{
		$("#platformSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/resmgt-common/platform/selectRmPlatformSel.action", {"_":new Date().getTime()}, function(data) {
			$.each(data, function() {
				$("#platformSel").append("<option value='" + this.platformId + "'>" + this.platformName + "</option>");
				platformJson[this.platformId] = this.platformCode;
			});
			platformJson[""] = "";//未选择默认值
		});	
	}
	
	//初始化新增修改窗口虚机类型下拉框
	function initVirtualTypeSelect(platformId)
	{
		$("#virtualTypeSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/resmgt-common/platform/selectRmVirtualTypeSel.action", {"_":new Date().getTime(), "rmPlatformPo.platformId":platformId}, function(data) {
			$.each(data, function() {
				$("#virtualTypeSel").append("<option value='" + this.virtualTypeId + "'>" + this.virtualTypeName + "</option>");
				virtualTypeJson[this.virtualTypeId] = this.virtualTypeCode;
			});
			virtualTypeJson[""] = "";//未选择默认值
		});	
	}
	
	//初始化新增修改窗口主机类型下拉框
	function initHostTypeSelect()
	{
		$("#hostTypeSel").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
		$.ajaxSettings.async = false;
		$.getJSON(ctx+"/network/use/selectRmHostTypeSel.action", {"_":new Date().getTime()}, function(data) {
			$.each(data, function() {
				$("#hostTypeSel").append("<option value='" + this.hostTypeId + "'>" + this.hostTypeName + "</option>");
				hostTypeJson[this.hostTypeId] = this.hostTypeCode;
			});
			hostTypeJson[""] = "";//未选择默认值
		});	
	}
	
	function updateOrSaveUseRelData(){
		var methodUseRel = $("#methodUseRel").val();
		var useCode = $("#useCodeMark").val();
		var useId = $("#useIdMark").val();
		var platformSel = $("#platformSel").val();
		var virtualTypeSel = $("#virtualTypeSel").val();
		var hostTypeSel = $("#hostTypeSel").val();
		var useRelCode = $("#useRelCode").val();
		var addIsActive = "Y";
		var url;
		var useRelId;
		var data = {'rmNwUseRelPo.useRelCode':useRelCode,'rmNwUseRelPo.useId':useId,'rmNwUseRelPo.platformId':platformSel,'rmNwUseRelPo.virtualTypeId':virtualTypeSel,
				'rmNwUseRelPo.hostTypeId':hostTypeSel,'rmNwUseRelPo.isActive':addIsActive};
		if(methodUseRel=="update"){
			url= ctx+"/network/use/updateRmNwUseRelPoAct.action"
		}else{
			url= ctx+"/network/use/saveRmNwUseRelPoAct.action"
			useRelId = "";
			data["rmNwUseRelPo.useRelId"] =  useRelId;
		}
				
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
				$("#updateUseRelDiv").dialog("close");
				$("#rmNwUseRelGridTable").jqGrid().trigger("reloadGrid");
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeTip();
				showError(i18nShow('tip_save_fail')+XMLHttpRequest.responseText);
			} 
		});
	}
	
	function showUseRelUpdateDiv(useRelId){
		$("#platformSel").unbind();//解绑change事件，防止每次调用initUseRelData时多次绑定change事件
		$("#virtualTypeSel").unbind();//解绑change事件，防止每次调用initUseRelData时多次绑定change事件
		$("#hostTypeSel").unbind();//解绑change事件，防止每次调用initUseRelData时多次绑定change事件
		$("label.error").remove();
		$("#updateUseRelDiv").dialog({
				autoOpen : true,
				modal:true,
				height:260,
				width:650,
				title:i18nShow('ip_use_rel_update'),
				position: "middle"
		})
		$("#cdpTdId").attr("display","block"); 
		$.post(ctx+"/network/use/findRmNwUseRelPoByIdAct.action",{"rmNwUseRelPo.useRelId" : useRelId},function(data){
			initUseRelData();
//			initAddDatacenterSelect();
//			selectByValue("addDatacenterIdSel",data.datacenterId);
			$("#platformSel").val(data.platformId);
			initVirtualTypeSelect(data.platformId);
			$("#virtualTypeSel").val(data.virtualTypeId);
			$("#hostTypeSel").val(data.hostTypeId);
			$("#useRelCode").val(data.useRelCode);
			$("#methodUseRel").val("update");
			platformCode = platformJson[data.platformId];
			virtualTypeCode = virtualTypeJson[data.virtualTypeId];
			hostTypeCode = hostTypeJson[data.hostTypeId];
		})
	}
	
	function deleteUseRelData(dataId){
		if(dataId){
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/network/use/deleteRmNwUseRelPoByIdAct.action",
					async : false,
					data:{"rmNwUseRelPo.useRelId":dataId},
					success:(function(data){
						showTip(i18nShow('compute_res_delSuccess'));
						$("#rmNwUseRelGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showTip(i18nShow('tip_delete_fail'));
					} 
				});
			});
		}else{
			var ids = jQuery("#rmNwUseRelGridTable").jqGrid('getGridParam','selarrrow');
			if(ids.length == 0){
				showError(i18nShow('error_select_one_data'));
				return;
			}
			var list = [];
			$(ids).each(function (index,id){
				var rowData = $("#rmNwUseRelGridTable").getRowData(id);
				list[list.length] = rowData.useRelId;
				})
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/network/use/deleteRmNwUseRelPoByIdAct.action",
					async : false,
					data:{"rmNwUseRelPo.useRelId":list.join(",")},
					success:(function(data){
						showTip(i18nShow('compute_res_delSuccess'));
						//showTip("删除成功!");
						$("#rmNwUseRelGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showTip(i18nShow('tip_delete_fail'));
					} 
				});
			});
		};

	}