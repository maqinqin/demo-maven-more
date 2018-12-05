function openSynchDiv(vmSmId){ 
	$("#vmMsId").val(vmSmId);
	if(""==$("#gridTableImageList").text()){
		imageSynchList(vmSmId);
	}else{
		jqGridReload("gridTableImageList", {'vmSmId':vmSmId});
	}
	$("#imageSynchListDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 470,
		width : 667,
		title :i18nShow('image_synch'),
		close:function(){
			$("#vmMsId").val('');
			 $( "#openstackSynckTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
}

/**
 * 保存镜像同步
 */
function chooseImageSynch(){
	//var userId=$("#userId").val();
	var ids = jQuery("#gridTableImageList").jqGrid('getGridParam','selarrrow');
	var list = [];
	var extis = 0;
	if(ids.length == 0){
		showTip(i18nShow('tip_choose_image'));
		return;
	}
	$(ids).each(function (index,id){
		var rowData = $("#gridTableImageList").getRowData(id);
		if(extis==0){
			var indexCount = rowData.synchStatus.indexOf(i18nShow('image_synch_status_Y'));
			var indexCount1 = rowData.synchStatus.indexOf(i18nShow('image_synch_status_I'));
				if(indexCount>-1 || indexCount1>-1){
					extis = 1;//已被同步
				}
		}
		list.push(rowData.imageId);
		});
		if(extis==1){
			showTip(i18nShow('tip_re_choose_image'));
			return;
		}
		
		$.ajax({
		async : true,
		cache : false,
		url : ctx + "/cloud-service/image/imageSynch.action",
		data : {
				"vmMsId":$("#vmMsId").val(),
				"imageIds":list.join(",")
		},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('error_image_synch'));
			jqGridReload("gridTableImageList", {'vmSmId':$("#vmMsId").val()});
		},
		beforeSend: function () {
        	showTip("load");
        },
		success : function(datas){
			showTip(i18nShow('tip_create_flow_success'));
			closeTip();
			jqGridReload("gridTableImageList", {'vmSmId':$("#vmMsId").val()});
		}
	});
	
}

/**
 * 删除镜像同步
 */
function deleteImageSynch(){
	//var userId=$("#userId").val();
	var ids = jQuery("#gridTableImageList").jqGrid('getGridParam','selarrrow');
	var list = [];
	var extis = 0;
	if(ids.length == 0){
		showTip(i18nShow('tip_choose_image'));
		return;
	}
	$(ids).each(function (index,id){
		var rowData = $("#gridTableImageList").getRowData(id);
		if(extis==0){
			var indexCount = rowData.synchStatus.indexOf(i18nShow('image_synch_status_N'));
			var indexCount1 = rowData.synchStatus.indexOf(i18nShow('image_synch_status_I'));
				if(indexCount>-1 || indexCount1>-1){
					extis = 1;//已被同步
				}
		}
		list.push(rowData.imageId);
		});
		if(extis==1){
			showTip(i18nShow('tip_re_choose_image_dele'));
			return;
		}
		
		$.ajax({
		async : true,
		cache : false,
		url : ctx + "/cloud-service/image/imageSynchDelete.action",
		data : {
				"vmMsId":$("#vmMsId").val(),
				"imageIds":list.join(",")
		},
		type:'post',
		dataType : "json",
		error : function() {//请求失败处理函数   
			showError(i18nShow('error_image_synch'));
			jqGridReload("gridTableImageList", {'vmSmId':$("#vmMsId").val()});
		},
		beforeSend: function () {
        	showTip("load");
        },
		success : function(datas){
			showTip(i18nShow('tip_delete_success'));
			closeTip();
			jqGridReload("gridTableImageList", {'vmSmId':$("#vmMsId").val()});
		}
	});
	
}

/**
 * 关闭对话框
 * @param divId
 * @return
 */
function closeView(divId) {
	$("#vmMsId").val('');
	$("#" + divId).dialog("close");
}

/**
 * 镜像列表
 * @return
 */
function imageSynchList(vmSmId) {
	$("#gridTableImageList").jqGrid({
		url : ctx+"/cloud-service/image/queryImageSynchList.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal(),
		postData :{'vmSmId':vmSmId},
		autowidth : true, // 是否自动调整宽度
		multiselect:true,
		multiboxonly: true,
		//postData: {"userIdchoose" :$("#userId").val()},
		colModel : [ {
			name : "imageId",
			index : "imageId",
			label : "imageId",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},{
			name : "imageName",
			index : "imageName",
			label : i18nShow('image_name'),
			width : 200,
			sortable : true,
			align : 'left'
		},{
			name : "remark",
			index : "remark",
			label : i18nShow('com_remark'),
			width : 238,
			sortable : true,
			align : 'left'
		},{
			name : "synchStatus",
			label : i18nShow('image_synch_status'),
			width : 95,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rewObject){
            	var result = "<font style='color:red'>"+i18nShow('image_synch_status_N')+"</font>";
            	if(rewObject.synchStatus == 1){
            		result = "<font style='color:red'>"+i18nShow('image_synch_status_I')+"</font>";
            	}
            	if(rewObject.synchStatus == 2){
            		result = "<font style='color:green'>"+i18nShow('image_synch_status_Y')+"</font>";
            	}
			/*var result = "<font style='color:red'>未授予</font>";
			if(cellValue>0)
				  result = "<font style='color:green'>已授予</font>";*/
			return result;
      }
	}],
		
		viewrecords : true,
		sortname : "imageId",
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
		pager : "#gridPagerImageList",
		hidegrid : false
	});
}

//自适应宽度
$("#gridTableImageList").setGridWidth($("#gridTableImageList").width());
$(window).resize(function() {
	$("#gridTableImageList").setGridWidth($("#gridTableImageList").width());
	$("#gridTableImageList").setGridHeight(heightTotal() + 85);
});	


function openInstanceDiv(vmMsId){
	if(""==$("#gridTableInstance").text()){
		imageSynchInstanceList(vmMsId);
	}else{
		jqGridReload("gridTableInstance", {});
	}
	$("#imageSynchInstanceListDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 470,
		width : 667,
		title :i18nShow('image_synch_flow'),
		close:function(){
			 $( "#openstackSynckTable" ).jqGrid().trigger("reloadGrid" );
		}
	});
}

function showInstance(instanceId,wfModelId) {
	window.location.href = ctx+"/workflow/instance/bpmInstance_processInstance.action?state=instance&instanceId="+instanceId;
}


/**
 * 镜像同步流程列表
 * @return
 */
function imageSynchInstanceList(vmMsId) {
	$("#gridTableInstance").jqGrid({
		url : ctx+"/cloud-service/image/queryImageSynchInstanceList.action",
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : 300,
		autowidth : true, // 是否自动调整宽度
		//multiselect:true,
		//multiboxonly: false,
		postData: {"vmMsId" :vmMsId},
		colModel : [ {
			name : "instanceId",
			index : "instanceId",
			label : "instanceId",
			width : 0,
			sortable : true,
			align : 'left',
			hidden:true	
		},{
			name : "serverName",
			index : "serverName",
			label : i18nShow('rm_server_name'),
			width : 200,
			sortable : true,
			align : 'left'
		},{
			name : "imageName",
			index : "imageName",
			label : i18nShow('image_name'),
			width : 238,
			sortable : true,
			align : 'left'
		},{
			name : "synchStatus",
			label : i18nShow('image_synch_status'),
			width : 120,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rewObject){
            	var result = "<font style='color:red'>"+i18nShow('image_synch_status_N')+"</font>";
            	if(rewObject.synchStatus == 1){
            		result = "<font style='color:red'>"+i18nShow('image_synch_status_I')+"</font>";
            	}
            	if(rewObject.synchStatus == 2){
            		result = "<font style='color:green'>"+i18nShow('image_synch_status_Y')+"</font>";
            	}
            	result += "&nbsp;&nbsp;&nbsp;&nbsp;<a style='margin-right: 10px;text-decoration:none;' href='javascript:#' onclick=showInstance('"+rewObject.instanceId+"')>"+i18nShow('image_synch_flow')+"</a>";
			return result;
      }
	}],
		
		viewrecords : true,
		sortname : "imageId",
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
		pager : "#gridPagerInstance",
		hidegrid : false
	});
}