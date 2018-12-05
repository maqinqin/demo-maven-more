	var firstAttr = true;
	//展示属性信息
	function showAttribute(serviceId) {
		$("label.error").remove();
		
		$("#cloudServiceId").val(serviceId);
		
		//展示属性信息grid
		if(firstAttr){
			firstAttr = false;
			$("#serviceAttrGridTable").jqGrid({
					url : ctx + "/cloud-service/serviceattr/search.action",
					rownumbers : true,
					datatype : "json",
					postData : { SERVICE_ID : serviceId},
					mtype : "post",
					height : 360,
					autowidth : true,
					colNames : ['attrId', i18nShow('cloud_service_attr_name'), i18nShow('cloud_service_model_cname'), '属性类别id', '属性类型id',i18nShow('cloud_service_model_class'), i18nShow('cloud_service_model_type'), i18nShow('cloud_service_model_defVal'),i18nShow('cloud_service_model_isVisible'), i18nShow('cloud_service_model_isRequire'), i18nShow('cloud_service_model_rule'), i18nShow('cloud_service_operate') ],
					colModel : [
							{name : "attrId",index : "attrId",sortable : true,align : 'left',hidden:true},
							{ name : "attrName", index : "attrName", sortable : false, width:100, align : 'left' },
							{ name : "attrCname", index : "attrCname", sortable : false, width:100, align : 'left' },
							{ name : "attrClass", index : "attrClass", sortable : false, align : 'left',hidden:true },
							{ name : "attrType", index : "attrType", sortable : false,  align : 'left',hidden:true },
							{ name : "attrClassName", index : "attrClassName", sortable : false, align : 'left', width:50,
								formatter:function(cellVall, options, rowObject){
									if(rowObject.attrClass=='SELECT'){
										return '<a href="javascript:;" style=" text-decoration:none" onclick="viewOption(\''+rowObject.attrId+'\')">'+rowObject.attrClassName+'</a>';
									}else{
										return cellVall;
									}
							} },
							{ name : "attrTypeName", index : "attrTypeName", sortable : false,width:50,  align : 'left' },
							{ name : "defVal", index : "defVal", sortable : false, align : 'left', width:130 },
							{ name : "isVisible", index : "isVisible", sortable : false, align : 'left', width:60 },
							{ name : "isRequire", index : "isRequire", sortable : false, align : 'left',hidden:false,
								formatter : function(cellValue,options,rowobject){
									if(cellValue == 'N'){
										return i18nShow('cloud_service_require_N');
									}else{
										return i18nShow('cloud_service_require_Y');
									}
								} },
							{ name : "remark", index : "remark", sortable : false,  align : 'left', width:90},
							{
								name : "optiong", index : "optiong", sortable : false,  align : "left",width:70,
								formatter : function(cellVall, options, rowObject) {
									var updateAttrFlag = $('#updateAttrFlag').val();
									var deleteAttrFlag = $('#deleteAttrFlag').val();
									var ret = "　　";
									if(updateAttrFlag)
									ret += "<a  style='margin-right: 10px;margin-left: -25px;text-decoration:none;' href='javascript:#' title='' onclick=loadAttr('" + rowObject.attrId+"') >"+i18nShow('cloud_service_edit')+"</a>";
									if(deleteAttrFlag)
									ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteAttr('" + rowObject.attrId + "') >"+i18nShow('cloud_service_delete')+"</a>";
									return ret;
								}
							} ],
					viewrecords : true,
					sortname : "serviceId",
					rowNum : 10,
					rowList : [ 10, 20, 50, 100 ],
					prmNames : {
						search : "search"
					},
					jsonReader : {
						root : "pagination.dataList",
						records : "pagination.record",
						page : "pagination.page",
						total : "pagination.total",
						repeatitems : false
					},
					pager : "#serviceAttrGridPager",
					hidegrid : false
				});
		}else{
			jqGridReload("serviceAttrGridTable", {
				"SERVICE_ID" : serviceId
			});
		}
		
		//展示属性信息dialog
		$("#serviceAttr").dialog({ 
			autoOpen : true, 
			modal : true, 
			height : 550, 
			title:i18nShow('cloud_service_para'), 
			width : 1050,
			close:function(){
				//重新加载页面
				$("#gridTable").jqGrid().trigger("reloadGrid");
			}	
		});
		//自适应
		$("#serviceAttrGridTable").setGridWidth($("#serviceAttrGridDiv").width());
		$(window).resize(function() {
			$("#serviceAttrGridTable").setGridWidth($("#serviceAttrGridDiv").width());
	    });
		
		//初始化属性选项信息表头及内容grids
		$("#serviceAttrSelGridTable").jqGrid(
		{
			url : ctx + "/cloud-service/serviceattrsel/search.action",
			rownumbers : true,
			datatype : "json",
			postData :  {	attrId : "null"},
			mtype : "post",
			height : 110,
			autowidth : true,
			colNames : [ "id",i18nShow('cloud_service_attr_attrKey'), i18nShow('cloud_service_attr_attrValue'), i18nShow('cloud_service_operate')],
			colModel : [
					{ name : "attrSelId", index : "attrSelId", sortable : true,  align : 'left',hidden:true},
					{ name : "attrKey", index : "attrKey", sortable : false,  align : 'left' },
					{ name : "attrValue", index : "attrValue", sortable : false,  align : 'left' },
					{
						name : "option", index : "option", sortable : false, align : "left", 
						formatter : function(cellvalue, options, rowObject) {
							var ret = "　　";
							ret += "<a href='javascript:void(0);' style=' text-decoration:none' onclick=updateAttrOption('" + options.rowId + "')>"+i18nShow('cloud_service_edit')+"</a>&nbsp;&nbsp;&nbsp;";
							ret += "<a href='javascript:void(0);' style=' text-decoration:none' onclick=deleteAttrSel('" + options.rowId	+ "')>"+i18nShow('cloud_service_delete')+"</a>&nbsp;&nbsp;&nbsp;";
							
							//ret += '<input type="button" style=" margin-right: 10px;" class="btn_edit_s" title="修改" onclick=updateAttrOption("' + options.rowId+'")>';
							//ret += '<input type="button" style=" margin-right: 10px;" class="btn_del_s" onclick=deleteAttrSel("' + options.rowId + '") title="删除"/>';
								
							ret += "　　　　　　　";
							return ret;
						}
					} ],
			viewrecords : true,
			sortname : "attrKey",
			prmNames : {
				search : "search"
			},
			jsonReader : {
				root : "pagination.dataList",
				records : "pagination.record",
				page : "pagination.page",
				total : "pagination.total",
				repeatitems : false
			},
			hidegrid : false
		});
		
		//自适应
		$(window).resize(function() {
			$("#serviceAttrSelGridTable").setGridWidth($("#optionsInfo").width());
	    });
		$("#serviceAttrForm").validate({
			rules: { 
				cloudAttrName: {required:true,
								remote:{                
						               type:"POST",
						               url:ctx+"/cloud-service/serviceattr/checkCloudServiceAttrsAct.action",
						               data:{
						            	   "cloudServiceAttrPo.attrId":function(){return $("#cloudAttrId").val()},"cloudServiceAttrPo.serviceId":function(){return $("#cloudServiceId").val()},"cloudServiceAttrPo.attrName":function(){return $("#cloudAttrName").val();}
						               		} 
				              			} 
								},
				attrType: "required",
				attrClass:'required'
			},
			messages: {
				cloudAttrName: {required:i18nShow('validate_service_cloudAttrName_required'),remote:i18nShow('validate_service_cloudAttrName_remote')},
				attrType:i18nShow('validate_service_attrType_required'),
				attrClass:i18nShow('validate_service_attrClass_required')
			},
			submitHandler: function() {
				saveAttr();
			}
		});
		
		$("#attrForm").validate({
			rules: {
				KEY: "required",
				value: "required"
			},
			messages: {
				KEY: i18nShow('validate_service_key_required'),
				value:i18nShow('validate_service_val_required')
			},
			submitHandler: function() {
				updateOrSaveAttrOption();
			}
		});
		//属性类型添加联动事件
		$("#attrClass").change(function() {
			initAttrTypeSelect(this.value);
		});
	}
	
	//联动事件：是否显示选项信息
	function initAttrTypeSelect(value, title){
		if(value == "SELECT"){
			$("#optionsInfoTop").show();
			$("#optionsInfo").show();
			$("#attrListSqlText").hide();
			if(title){
				$("#serviceAttrLoad").dialog({ autoOpen : true, modal:true, height:600, width : 1020, title:title });
			}else{
				$("#serviceAttrLoad").dialog({ autoOpen : true, modal:true, height:600, width : 1020 });
			}
		}else if(value == "TEXT"){	
			$("#optionsInfoTop").hide();
			$("#optionsInfo").hide();
			$("#attrListSqlText").hide();
			if(title){
				$("#serviceAttrLoad").dialog({ autoOpen : true,  modal:true, height:450,width : 1020, title:title});
			}else{
				$("#serviceAttrLoad").dialog({ autoOpen : true,  modal:true, height:450,width : 1020});
			}
		}else if(value == "LIST"){
			$("#optionsInfoTop").hide();
			$("#optionsInfo").hide();
			$("#attrListSqlText").show();
			if(title){
				$("#serviceAttrLoad").dialog({ autoOpen : true, modal:true, height:600, width : 1020, title:title });
			}else{
				$("#serviceAttrLoad").dialog({ autoOpen : true, modal:true, height:600, width : 1020 });
			}
		}
		else{
			$("#optionsInfoTop").hide();
			$("#optionsInfo").hide();
			$("#attrListSqlText").hide();
			if(title){
				$("#serviceAttrLoad").dialog({autoOpen : true,  modal:true, height:380,width : 1020, title:title });
			}else{
				$("#serviceAttrLoad").dialog({autoOpen : true,  modal:true, height:380,width : 1020 });
			}
		}
		$("#serviceAttrSelGridTable").setGridWidth($("#optionsInfo").width());
	}
	
	var firstView = true;
	//查看属性选项信息
	function viewOption(attrId){
		if(firstView){
			firstView = false;
			$("#viewServiceAttrSelGridTable").jqGrid(
			{
				url : ctx + "/cloud-service/serviceattrsel/search.action",
				rownumbers : true,
				datatype : "json",
				postData :  {	attrId : attrId},
				mtype : "post",
				autowidth : true,
				colNames : [ "id",i18nShow('cloud_service_attr_attrKey'), i18nShow('cloud_service_attr_attrValue') ],
				colModel : [
						{ name : "attrSelId", index : "attrSelId", sortable : true,  align : 'left',hidden:true},
						{ name : "attrKey", index : "attrKey", sortable : false,  align : 'left' },
						{ name : "attrValue", index : "attrValue", sortable : false,  align : 'left' }
				 ],
				viewrecords : true,
				sortname : "attrKey",
				prmNames : {
					search : "search"
				},
				jsonReader : {
					root : "pagination.dataList",
					records : "pagination.record",
					page : "pagination.page",
					total : "pagination.total",
					repeatitems : false
				},
				hidegrid : false
			});
		}else{
			$("#viewServiceAttrSelGridTable").jqGrid("setGridParam",{postData :  {	attrId : attrId}}).trigger("reloadGrid");
		}
		
		$("#viewOptionsInfo").dialog({modal:true, height:380,width : 600, title:i18nShow('cloud_service_op_info') });
		$("#viewServiceAttrSelGridTable").setGridWidth($("#viewOptionsInfo").width());
		$("#viewServiceAttrSelGridTable").setGridHeight($("#viewOptionsInfo").height()-80);
	}
	//新建及修改属性dialog
	function loadAttr(attrId) {
		var title;
		if(!attrId){
			attrId='null';
		}
		//重新初始化属性选项信息表头及内容			添加参数reload
		$("#serviceAttrSelGridTable").jqGrid("setGridParam", { postData : {attrId : attrId} }).trigger("reloadGrid");
		
		//清空属性信息
		clearTab('attrDiv');		
		//清空remote验证结果
		emptyValue("cloudAttrName");
		
		if (attrId != "null")
		{	
			title = i18nShow('cloud_service_attr_update');
			$.post(ctx + "/cloud-service/serviceattr/load.action", {
				"cloudServiceAttrPo.attrId" : attrId
			}, function(data) {
				if (data.cloudServiceAttrPo != null) {
					$("#cloudAttrId").val(data.cloudServiceAttrPo.attrId);
					flatSelectByValue("attrType", data.cloudServiceAttrPo.attrType);
					manuFlatSelectByValue("attrClass", data.cloudServiceAttrPo.attrClass,"initAttrTypeSelect");
					initAttrTypeSelect(data.cloudServiceAttrPo.attrClass,title);
					$("#cloudIsActive").val(data.cloudServiceAttrPo.isActive);
					$("#cloudAttrName").val(data.cloudServiceAttrPo.attrName);
					$("#cloudAttrCname").val(data.cloudServiceAttrPo.attrCname);
					$("#cloudDefVal").val(data.cloudServiceAttrPo.defVal);
					$("#cloudIsActive").val(data.cloudServiceAttrPo.isActive);
					$("#cloudRemark").val(data.cloudServiceAttrPo.remark);
					$("#attrListSql").val(data.cloudServiceAttrPo.attrListSql);
					$("#attrClass").val(data.cloudServiceAttrPo.attrClass)
					//wmy，用户是否可见
					flatSelectByValue("isVisible", data.cloudServiceAttrPo.isVisible);
					//是否必填
					flatSelectByValue("isRequire", data.cloudServiceAttrPo.isRequire);
					$('#attrMethod').val('update');
				}
			});
		}else{
			$('#attrMethod').val('create');
			title = i18nShow('cloud_service_attr_save');
			initAttrTypeSelect($("#attrClass").val(), title);
		}
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
	//修改属性选项信息dialog
	function updateAttrOption(rowid){
		var rowObject = $("#serviceAttrSelGridTable").jqGrid("getRowData", rowid);
		$("label.error").remove();
		$("#attrOptionDiv").dialog({
				autoOpen : true,
				modal:true,
				height:241,
				width:327,
				title:i18nShow('cloud_service_attr_op_update'),
				resizable:true
		});
		$('#KEY').val(rowObject.attrKey);
		$('#value').val(rowObject.attrValue);
		$("#attrOptionMethod").val("update");
		$("#attrRowId").val(rowid);
	}
	//新建属性选项信息dialog
	function createAttrOption(){
		$("label.error").remove();
		$("#attrOptionDiv").dialog({
				autoOpen : true,
				modal:true,
				height:241,
				width:350,
				title:i18nShow('cloud_service_attr_op_save'),
				resizable:true
		});
		clearTab('attrOptionDiv');
		
		$("#attrOptionMethod").val("create");
		var id = $("#serviceAttrSelGridTable").jqGrid('getDataIDs').length+1;
		$("#attrRowId").val(id);
	}
	//保存新建或者修改的属性选项信息
	function saveAttrOption(){
		$("#attrForm").submit();  
	}
	//检验后执行保存新建或者修改的属性选项信息
	function updateOrSaveAttrOption(){
		var KEY = $("#KEY").val();
		var value = $("#value").val();
		
		var id = $('#attrRowId').val();
		var method = $('#attrOptionMethod').val();
		var dataObj ={'attrKey':KEY, 'attrValue':value,'option':''};
		if(method=='create'){
			$("#serviceAttrSelGridTable").jqGrid("addRowData", id, dataObj);
		}else{
			$("#serviceAttrSelGridTable").jqGrid("setRowData", id, dataObj);
		}
		$("#attrOptionDiv").dialog("close");
	}
	//保存属性校验
	function saveAttrValidate(){
		$('#serviceAttrForm').submit();
	}
	//保存属性信息
	function saveAttr() {
		$("label.error").remove();
		
        var params = $("#serviceAttrForm").serialize();
//		console.log(params);    //用FireBug输出
		
		var serviceId = $("#cloudServiceId").val();
		var attrName = $('#cloudAttrName').val();
		var attrCname = $('#cloudAttrCname').val();
		var attrType = $('#attrType').val();
		var atttrClass = $('#attrClass').val();
		var defVal = $('#cloudDefVal').val();
		var attrId = $('#cloudAttrId').val();
		var remark = $('#cloudRemark').val();
		var isActive= $('#cloudIsActive').val();
		var attrListSql=$("#attrListSql").val();
		//wmy-----添加是否可见字段----
		var isVisible = $("#isVisible").val();
		var isRequire = $("#isRequire").val();
		//-------------------------
		var url;
		var data;
		
		var method = $('#attrMethod').val();
		
		if(method=='create'){
			url= ctx+"/cloud-service/serviceattr/saveAttr.action";
			data = {'cloudServiceAttrPo.attrName':attrName,'cloudServiceAttrPo.serviceId':serviceId,'cloudServiceAttrPo.attrCname':attrCname,'cloudServiceAttrPo.attrType':attrType,'cloudServiceAttrPo.attrClass':atttrClass,'cloudServiceAttrPo.defVal':defVal,'cloudServiceAttrPo.isActive':isActive,'cloudServiceAttrPo.remark':remark,'cloudServiceAttrPo.isVisible':isVisible,'cloudServiceAttrPo.isRequire':isRequire,'cloudServiceAttrPo.attrListSql':attrListSql};
		}else{
			url= ctx+"/cloud-service/serviceattr/updateAttr.action";
			data = {'cloudServiceAttrPo.attrId':attrId,'cloudServiceAttrPo.attrName':attrName,'cloudServiceAttrPo.serviceId':serviceId,'cloudServiceAttrPo.attrCname':attrCname,'cloudServiceAttrPo.attrType':attrType,'cloudServiceAttrPo.attrClass':atttrClass,'cloudServiceAttrPo.defVal':defVal,'cloudServiceAttrPo.isActive':isActive,'cloudServiceAttrPo.remark':remark,'cloudServiceAttrPo.isVisible':isVisible,'cloudServiceAttrPo.isRequire':isRequire,'cloudServiceAttrPo.attrListSql':attrListSql};
//			alert(JSON.stringify(data));
		}
		
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
				if(atttrClass=='SELECT'){
					saveAttrSel(data.attrId);
				}else{
					deleteAttrOption(attrId);
				}
				$("#serviceAttrLoad").dialog("close");
				$("#serviceAttrGridTable").jqGrid().trigger("reloadGrid");
				showTip(i18nShow('tip_save_success'));
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('tip_save_fail'));   
			} 
		});
	}
	//保存属性选项信息
	function saveAttrSel(attrId){
		var ids = $("#serviceAttrSelGridTable").jqGrid('getDataIDs');
		var list = [];
		$.each(ids, function(index, id) {
			var rowData = $("#serviceAttrSelGridTable").jqGrid("getRowData", id);
			list[list.length] = rowData;
		});
		
		var jsonData = JSON.stringify(list);

		var url= ctx+"/cloud-service/serviceattrsel/saveOption.action";
		var data = {'cloudServiceAttrSelPo.attrId':attrId,'cloudServiceAttrSelPo.isActive':'Y','cloudServiceAttrSelPo.attrSelId':jsonData};
		
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data
		});
	}
	//页面删除属性选项
	function deleteAttrSel(rowId){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$("#serviceAttrSelGridTable").jqGrid("delRowData", rowId);
		});
		
	}
	//后台删除属性选项
	function deleteAttrOption(attrId){
		var queryData = {
			"cloudServiceAttrSelPo.attrId" : attrId
		};
		$.post(ctx + "/cloud-service/serviceattrsel/deleteOption.action", queryData, function(data) {
		});
	}
	//删除属性信息
	function deleteAttr(attrId) {
		var params = {
			"cloudServiceAttrPo.attrId" : attrId
		};
		showTip(i18nShow('tip_delete_confirm'),function(){
				$.post(ctx + "/cloud-service/serviceattr/delete.action", params, function(data) {
				deleteAttrOption(attrId);
				showTip(i18nShow('tip_delete_success'));
				showAttribute($("#cloudServiceId").val());
			});
		});
	}
	//wmy----关闭窗口
	function closeViews(id){
		$("#"+id).dialog("close");
	}
