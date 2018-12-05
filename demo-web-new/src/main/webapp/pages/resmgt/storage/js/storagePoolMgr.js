var saveOrModify = ''; 

$(function(){	
	
//	showTip("删除记录吗？",function(){		
//		showTip("删除成功");return;		
//	});
	
	//showTip("load");
	//return;
	//$.jBox.close(true,'tip');
	//return;
	//showError("这儿地方错误了啊");
	
	/*单击节点后，右侧DIV业务表现的方法*/
	var myClickFun = function(innerBizType,innerNodeId){
		if(typeof(innerNodeId)=="undefined" && typeof(innerBizType)=="undefined"){
			innerNodeId = nodeId;
			innerBizType = bizType;
			$("#hid_nodeId").val(nodeId);
			$("#hid_pnodeId").val(parentNodeId);
		}else{
			$("#hid_nodeId").val(innerNodeId);
			var this_node = zTree.getNodeByParam("id", innerNodeId, null);
			var this_parentNode = this_node.getParentNode();
			$("#hid_pnodeId").val(this_parentNode.id);
		}
		
		if(innerBizType == "xd"){
			showDiv("xd_div","存储资源池管理向导");
		}
		if(innerBizType == "sp"){					
			ajaxCall("/resmgt-storage/pool/getStoragePoolById.action",{"storagePoolId":innerNodeId},
				function(){
					showDiv("sp_div","存储资源池详细信息");
					$("#lab_dc").html(ajaxResult.dataCenterName);
					$("#lab_name").html(ajaxResult.poolName);
					$("#lab_ename").html(ajaxResult.ename);
					$("#lab_pooltype").html(ajaxResult.poolType);
					if(ajaxResult.serviceLevelCode == "PLATINUM"){
						$("#lab_serlev").html(i18nShow('storage_res_pool_PLATINUM'));
					}else if(ajaxResult.serviceLevelCode == "GOLD"){
						$("#lab_serlev").html(i18nShow('storage_res_pool_GOLD'));
					}else if(ajaxResult.serviceLevelCode == "SILVER"){
						$("#lab_serlev").html(i18nShow('storage_res_pool_SILVER'));
					}					
					$("#lab_remark").html(ajaxResult.remark);		
				}
			)
		}else if(innerBizType == "dc"){
			ajaxCall("/resmgt-common/datacenter/getDataCenterById.action",{"dataCenterId":innerNodeId},
					function(){
						showDiv("dc_div","数据中心详细信息");
						$("#lab_dcname").html(ajaxResult.datacenterCname);
						$("#lab_dcEname").html(ajaxResult.ename);
						$("#lab_address").html(ajaxResult.address);
						if(ajaxResult.isActive == "Y"){
							$("#lab_isActive").html("<span class='tip_green'>"+i18nShow('openstack_res_show_active_Y')+"</span>");
						}else{
							$("#lab_isActive").html("<span class='tip_red'>"+i18nShow('openstack_res_show_active_N')+"</span>");	
						}						
					}
				)
		}else if(innerBizType == "scp"){
			ajaxCall("/resmgt-storage/pool/getStorageChildPoolById.action",{"storageChildPoolId":innerNodeId},
					function(){
						showDiv("scp_div","存储资源子池详细信息");
						$("#btn_refDev").show();
						var parentNode = zTree.getNodeByParam("id",$("#hid_pnodeId").val(),null);
						$("#lab_sp").html(parentNode.name);
						$("#lab_scpname").html(ajaxResult.name);
						$("#lab_scpdevtype").html(ajaxResult.storageDevModel);
						$("#lab_scpapptype").html(ajaxResult.storageAppTypeCode);
						$("#childPoolId").val(ajaxResult.storageChildResPoolId);
						showStorageDeviceList(ajaxResult.storageChildResPoolId);
						var innerNodeId = ajaxResult.resPoolId;
						ajaxCall("/resmgt-storage/pool/getStoragePoolById.action",{"storagePoolId":innerNodeId},
							function(){
							$("#hid_scpapptype").val(ajaxResult.poolType);
							}
						)
						$("#lab_scpremark").html(ajaxResult.remark);
					}
			)
		}else if(innerBizType == "sd"){
			ajaxCall("/resmgt-storage/device/getStorageDeviceById.action",{"storageDeviceId":innerNodeId},
					function(){
						showDiv("dev_div","存储设备详细信息");						
						$("#lab_sdname").html(ajaxResult.deviceName);$("#lab_sdsn").html(ajaxResult.sn);
						$("#lab_sddevtype").html(ajaxResult.deviceType);$("#lab_sdcs").html(ajaxResult.deviceManufacturer);
						$("#lab_sdwm").html(ajaxResult.microCode);$("#lab_sdip").html(ajaxResult.mgrIp);
						$("#lab_sdhc").html(ajaxResult.cacheCapacity);$("#lab_sdcprl").html(ajaxResult.diskCapacity);
						$("#lab_cpxh").html(ajaxResult.diskSize);$("#lab_sdrpm").html(ajaxResult.diskRpm);
						$("#lab_sddksl").html(ajaxResult.portCount);$("#lab_sddksd").html(ajaxResult.portSpeed);
						$("#lab_sdsfky").html(ajaxResult.isActive);$("#lab_sdxh").html(ajaxResult.deviceModel);
					}
			)
			showDataStore();
		}
};	

/**
 * 异步加载节点定义方法
 */
var myExpandFun = function(){
	if(bizType == "sp"){					
		ajaxCall("/resmgt-storage/pool/getStorageChildPoolByStoragePoolId.action",{"storagePoolId":nodeId},
				asyncAddNode
		)
	}else if(bizType == "dc"){
		ajaxCall("/resmgt-storage/pool/getStoragePoolByDcId.action",{"dcId":nodeId},
				asyncAddNode
			)
	}else if(bizType == "scp"){
		ajaxCall("/resmgt-storage/device/getReferencedStorageDevice.action",{"storageChildPoolId":nodeId},
				asyncAddNode
		)
	}
};	
	
	/*注册方法*/
	regFunction(myClickFun,myExpandFun);
	
    $.ajax({   
        async:false,   
        cache:false,   
        type: 'POST',   
        url: ctx+"/resmgt-storage/tree/getStorageTreeList.action",//请求的action路径   
        beforeSend: function () {
        	showTip("load");
        },
        error: function () {//请求失败处理函数   
            showError(i18nShow('tip_req_fail'));   
        },   
        success:function(data){ //请求成功后处理函数。     
        	zTreeInit("treeRm",data);
        	zTree.selectNode(zTree.getNodeByParam("id","-1",null));
        	bizType = "xd";
        	myClickFun();
        	closeTip();
        }   
    });
    
    /**************各表单验证开始****************/
	jQuery.validator.addMethod("poolNameCheck", function(value, element) { 
		var validateValue=true;
		var hid_poolName = $("#hid_poolName").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"sp.poolName":value,"dataCenterId":$("#hid_dc").val()},
			url:ctx+"/resmgt-storage/pool/selectResPoolNameCheck.action",
			async : false,
			success:(function(data){
				if(saveOrModify=="modify"){
					if(data==null||data.poolName==hid_poolName){
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
		return this.optional(element) || validateValue;
		},
	"资源池名称不能重复"); 
    
	jQuery.validator.addMethod("stringCheck", function(value, element) { 
		return this.optional(element) || /^[a-zA-Z0-9-_]*$/g.test(value);     
		},
	"只能包括英文和数字");
    $("#sp_form").validate({
    	rules: {
    		"sp.poolName": {required:true,poolNameCheck:true},
    		"sp.ename": {required: false,stringCheck:true}
    	},
    	messages: {
    		"sp.poolName":{required: i18nShow('validate_data_required'),poolNameCheck: i18nShow('validate_data_remote')},
    		"sp.ename": {stringCheck: i18nShow('validate_availableZoneName_stringCheck')}
    	},
//    	errorPlacement:function(error,element){
//    		error.insertAfter("#sp_error_tip");
//    	},
    	submitHandler: function() {
    		var zTree = $.fn.zTree.getZTreeObj("treeRm");
    		var sNodes = zTree.getSelectedNodes();
    		if(saveOrModify == "save"){
	    		AjaxSubmitForm("sp_form",ctx+"/resmgt-storage/pool/saveOrUpdateStoragePool.action",function(){
	    			closeView('add_sp_div');
	    			showTip(i18nShow('tip_save_success'));
	    			if (sNodes && sNodes.length>0) {
						refreshZTreeAfterAdd(zTree,sNodes[0]);
					}
	    			//addNode(ajaxResult.id,ajaxResult.poolName,$("#hid_icon").val(),null,false,"sp");
	    			
	    		});
    		}else if(saveOrModify == "modify"){
    			AjaxSubmitForm("sp_form",ctx+"/resmgt-storage/pool/saveOrUpdateStoragePool.action",function(){
	    			closeView('add_sp_div');
	    			showTip(i18nShow('tip_save_success'));
	    			modifyNode($("#hid_nodeId").val(),$("#txt_name").val());
	    			myClickFun("sp",$("#hid_nodeId").val());	    			
	    		});
    		}
    	}
    });
    
    jQuery.validator.addMethod("childPoolNameCheck", function(value, element) { 
		var validateValue=true;
		var hid_childPoolName = $("#hid_childPoolName").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"scp.name":value,"scp.resPoolId":$("#hid_sp").val()},
			url:ctx+"/resmgt-storage/pool/selectResChildPoolNameCheck.action",
			async : false,
			success:(function(data){
				if(saveOrModify=="modify"){
					if(data==null||data.name==hid_childPoolName){
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
		return this.optional(element) || validateValue;
		},
	"资源子池名称不能重复"); 
    
    $("#scp_form").validate({
    	rules: {
    		"scp.name": {required:true,childPoolNameCheck:true}
    	},
    	messages: {
    		"scp.name": {required: i18nShow('validate_data_required'),poolNameCheck: i18nShow('validate_data_remote')}	
    	},
//    	errorPlacement:function(error,element){
//    		error.insertAfter("#scp_error_tip");
//    	},
    	submitHandler: function() {
    		var zTree = $.fn.zTree.getZTreeObj("treeRm");
    		var sNodes = zTree.getSelectedNodes();
    		if($("#sel_sp").text() == $.trim($("#txt_scpname").val())){
				showTip(i18nShow('tip_storage_res_pool_name_same'));
				return;
			}
    		
    		if(saveOrModify == "save"){
	    		AjaxSubmitForm("scp_form",ctx+"/resmgt-storage/pool/saveOrUpdateStorageChildPool.action",function(){
	    			closeView('add_scp_div');
	    			showTip(i18nShow('tip_save_success'));
	    			if (sNodes && sNodes.length>0) {
						refreshZTreeAfterAdd(zTree,sNodes[0]);
					}
	    			//addNode(ajaxResult.storageChildResPoolId,ajaxResult.name,$("#hid_icon").val(),null,false,"scp");
	    			
	    		});
    		}else if(saveOrModify == "modify"){
    			AjaxSubmitForm("scp_form",ctx+"/resmgt-storage/pool/saveOrUpdateStorageChildPool.action",function(){
	    			closeView('add_scp_div');
	    			showTip(i18nShow('tip_save_success'));
	    			modifyNode($("#hid_nodeId").val(),$("#txt_scpname").val());
	    			myClickFun("scp",$("#hid_nodeId").val());
	    		});
    		}
    	}
    });
    /**************各表单验证结束****************/
});

function showDiv(divId,title){
	$("#rightContentDiv div").hide();
	$("#"+divId).fadeIn('fast');
}

function openDialog(divId,title,width,height){
		$("#"+divId).dialog({
				autoOpen : true,
				modal:true,
				height:height,
				width:width,
				title:title,
//				draggable: false,
		       // resizable:false
		})
}

function closeView(divId){
	$("#"+divId).dialog("close");
}

function showStorageDeviceList(storageChildPoolId){
	$("#showStorageDeviceList").show();
	$("#showStorageDeviceListDiv").show();
	$("#gridPagerDl").show();
	//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#gridTableDl").jqGrid().GridUnload("gridTableDl");
		$("#gridTableDl").jqGrid({
			url : ctx+"/resmgt-storage/device/getStoragDeviceList.action", 
			postData:{"storageChildPoolId":storageChildPoolId},
			rownumbers : true, 
			datatype : "json", 
			mtype : "post", 
			height : 230,
			autowidth : true, 
			multiselect:false,
			colNames:['id',i18nShow('storage_res_pool_device_name'),'SN',i18nShow('storage_res_pool_device_model'),i18nShow('storage_res_pool_device_fac'),i18nShow('storage_res_pool_location_code'),i18nShow('storage_res_pool_ip')],
			colModel : [ 
			            {name : "id",index : "id",width : 120,sortable : true,align : 'left',hidden:true},
			            {name : "deviceName",index : "deviceName",	width : 120,sortable : true,align : 'left'},
			            {name : "sn",index : "sn",	width : 120,sortable : true,align : 'left'},
			            {name : "deviceModel",index : "deviceModel",	width : 130,sortable : true,align : 'left'},
			            {name : "deviceManufacturer",index : "deviceManufacturer",	width : 130,sortable : true,align : 'left'},
			            {name : "seatName",index : "seatName",	width : 130,sortable : true,align : 'left'},
			            {name : "mgrIp",index : "mgrIp",	width : 130,sortable : true,align : 'left'}],
			            viewrecords : true,
						sortname : "id",
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
						pager : "#gridPagerDl",
						//caption : $("#lab_sdname").text()+"_DataStore数据",
						hidegrid : false
		});
}
function search() {
	var queryData = {
			storageChildPoolId : $("#childPoolId").val()
	};
	jqGridReload("gridTableDl", queryData);
}

function showDataStore(){
	$("#showDataStoreList").show();
	$("#showDataStoreListDiv").show();
	$("#gridPager").show();
	//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
	$("#gridTable").jqGrid().GridUnload("gridTable");
		$("#gridTable").jqGrid({
			url : ctx+"/resmgt-storage/device/getStorageDataStoresById.action", 
			postData:{"storageDeviceId":$("#hid_nodeId").val()},
			rownumbers : false, 
			datatype : "json", 
			mtype : "post", 
			height : 230,
			autowidth : true, 
			multiselect:false,
			colNames:['id',i18nShow('storage_res_pool_order'),i18nShow('storage_res_pool_name'),i18nShow('storage_res_pool_path')],
			colModel : [ 
			            {name : "id",index : "id",width : 50,sortable : true,align : 'left',hidden:true},
			            {name : "orderNum",index : "orderNum",	width : 60,sortable : true,align : 'left'},
			            {name : "name",index : "name",	width : 80,sortable : true,align : 'left'},
			            {name : "path",index : "path",	width : 100,sortable : true,align : 'left'}],
			viewrecords : true,
			sortname : "orderNum",
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
}

function newStoragePool(){
	$("#hid_icon").val(iconPath+'respoolstor.png');
	$("#hid_sp_id").val('');
	$("#txt_name").val('');
	$("#txt_ename").val('');
	$("#texta_remark").val('');
	$("#btn_add_sp").show();
	$("#btn_mod_sp").hide();
	$("#hid_dc").val($("#hid_nodeId").val());
	var currentNode = zTree.getNodeByParam("id",$("#hid_nodeId").val(),null);
	$("#sel_dc").empty();
	$("#sel_dc").append("<option value='"+$("#hid_nodeId").val()+"'>"+currentNode.name+"</option>");
	flatSelectByValue("sp.poolType","NAS");
	flatSelectByValue("sp.serviceLevelCode","PLATINUM");
	saveOrModify = "save";
	$("label.error").remove();
	openDialog('add_sp_div',i18nShow('storage_res_pool_save'),650,390);
}

function modStoragePool(){
	$("#hid_sp_id").val($("#hid_nodeId").val());
	$("#btn_add_sp").hide();
	$("#btn_mod_sp").show();
	$("#hid_dc").val($("#hid_pnodeId").val());
	var currentNode = zTree.getNodeByParam("id",$("#hid_pnodeId").val(),null);
	$("#sel_dc").empty();
	$("#sel_dc").append("<option value='"+currentNode.id+"'>"+currentNode.name+"</option>");
	$("#txt_name").val($("#lab_name").html());
	$("#hid_poolName").val($("#lab_name").html());
	$("#txt_ename").val($("#lab_ename").html());
	$("#sel_sl").val($("#lab_serlev").html());
	$("#texta_remark").val($("#lab_remark").html());
	flatSelectByValue("sp.poolType",$("#lab_pooltype").html());
	var serviceLevelCode = "";
	if($("#lab_serlev").html() == i18nShow('storage_res_pool_PLATINUM')){
		serviceLevelCode = "PLATINUM";
	}else if($("#lab_serlev").html() == i18nShow('storage_res_pool_GOLD')){
		serviceLevelCode = "GOLD";
	}else if($("#lab_serlev").html() == i18nShow('storage_res_pool_SILVER')){
		serviceLevelCode = "SILVER";
	}
	flatSelectByValue("sp.serviceLevelCode",serviceLevelCode);
	saveOrModify = "modify";
	$("label.error").remove();
	openDialog('add_sp_div',i18nShow('storage_res_pool_update'),650,280);
}

function delStoragePool(){
	ajaxCall("/resmgt-storage/pool/getStorageChildPoolByStoragePoolId.action",{"storagePoolId":$("#hid_nodeId").val()},function(){});
	
	if($.trim(ajaxResult)!=""){
		 showError(i18nShow('tip_storage_res_pool_delete'));
		 return;
	}
	
	showTip(i18nShow('tip_delete_confirm'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-storage/pool/delStoragePool.action",
			async : false,
			data:{"storagePoolId":$("#hid_nodeId").val()},
			success:(function(data){
				deleteTreeNode($("#hid_nodeId").val());
				showTip(i18nShow('tip_delete_success'));
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_delete_fail'));
			} 
		});
	});
}

function newStorageChildPool(){
	$("#hid_icon").val(iconPath+'respoolstor.png');
	$("#hid_scp_id").val('');
	$("#hid_sp").val($("#hid_nodeId").val());
	$("#btn_add_scp").show();
	$("#btnAddSp").show();
	$("#btn_mod_scp").hide();
	$("#texta_scpremark").val('');
	$("#txt_scpname").val('');
	var currentNode = zTree.getNodeByParam("id",$("#hid_nodeId").val(),null);
	$("#sel_sp").empty();
	$("#sel_sp").append("<option value='"+$("#hid_nodeId").val()+"'>"+currentNode.name+"</option>");
	/*调用下面的2个方法，做新按钮样式的初始化*/
	var defaultSelect = "HDS";
	manuFlatSelectByValue("sel_manufacturer",defaultSelect,"selectDevModelByManuf");
	selectDevModelByManuf(defaultSelect);
	flatSelectByValue("scp.storageAppTypeCode","DB");	
	saveOrModify = "save";
	openDialog('add_scp_div',i18nShow('storage_res_pool_son_save'),650,370);
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

/*本方法暂时弃用*/
function initManufacturer(){
	//初始化厂商信息
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/resmgt-storage/device/selectDeviceManufacturerByDeviceType.action",
		async : false,
		data:{"deviceType":"STORAGE"},//当前功能的设备类型就是STORAGE
		success:(function(data){
			$("#sel_manufacturer").empty();
			$("#sel_manufacturer").append("<option value=''>----------"+i18nShow('com_select_defalt')+"----------</option>");
			$("#sel_devModel").empty();
			$("#sel_devModel").append("<option value=''>----------"+i18nShow('com_select_defalt')+"----------</option>");
			$(data).each(function(i,item){
				$("#sel_manufacturer").append("<option value='"+item.deviceManufacturer+"'>"+item.deviceManufacturer+"</option>");
			});			
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showError(i18nShow('tip_req_fail'));
		} 
	});
}

function selectDevModelByManuf(changeValue){
		var deviceModel = "";
		$("#span_device_model").html('');
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-storage/device/selectDeviceModelByManuf.action",
			async : false,
			data:{"deviceManufacturer":changeValue,"deviceType":"STORAGE"},
			success:(function(data){
				$(data).each(function(i,item){
					if(i==0)
						deviceModel = item.deviceModel;
					$("#span_device_model").append("<a id=\"sec_"+i+"\" href=\"javascript:;\" onclick=\"selectObj(this,'hid_storageDevModel');\" target=\"_self\" value='"+deviceModel+"' class=\"unit\" >"+deviceModel+"</a>");
				});	
				selectObj(document.getElementById("sec_0"),"hid_storageDevModel");
				flatSelectByValue("sec_0",deviceModel);
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_req_fail'));
			} 
		});
}

function modStorageChildPool(){
	$("#hid_scp_id").val($("#hid_nodeId").val());
	$("#hid_sp").val($("#hid_pnodeId").val());
	$("#btn_add_scp").hide();
	$("#btn_mod_scp").show();
	var currentNode = zTree.getNodeByParam("id",$("#hid_pnodeId").val(),null);
	$("#sel_sp").empty();
	$("#sel_sp").append("<option value='"+currentNode.id+"'>"+currentNode.name+"</option>");
	$("#txt_scpname").val($("#lab_scpname").html());
	$("#hid_childPoolName").val($("#lab_scpname").html());
	flatSelectByValue("scp.storageAppTypeCode",$("#lab_scpapptype").html());	
	$("#texta_scpremark").val($("#lab_scpremark").html());
	var flag;
	$.post(ctx+ "/resmgt-storage/device/selectDevices.action", {childPoolID:$("#hid_scp_id").val()}, function(data){
		//flag为true，表示当前子池下面有设备，否则没有。
		flag = data.result;
       $.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-storage/device/selectDeviceManufacturerByDeviceModel.action",
			async : false,
			data:{"deviceType":"STORAGE","deviceModel":$("#lab_scpdevtype").text()},//当前功能的设备类型就是STORAGE
			success:(function(data){
				if(flag=='true'){
					flatSelectForUnitVt("sel_manufacturer",data.deviceManufacturer);//设置样式不可选功能
					selectDevModelByManuf(data.deviceManufacturer);	
				}else{
					var defaultSelect = data.deviceManufacturer;
                    manuFlatSelectByValue("sel_manufacturer",defaultSelect,"selectDevModelByManuf");
                    selectDevModelByManuf(defaultSelect);
				}
				saveOrModify = "modify";
				openDialog('add_scp_div',i18nShow('storage_res_pool_son_update'),650,350);
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_req_fail'));
			} 
		});
		
       
       
	});
}

function delStorageChildPool(){
	ajaxCall("/resmgt-storage/device/getReferencedStorageDevice.action",{"storageChildPoolId":$("#hid_nodeId").val()},function(){});
			
	if($.trim(ajaxResult)!=""){
		 showError(i18nShow('message1'));
		 return;
	}
	
	showTip(i18nShow('message4')+"?",function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-storage/pool/delStorageChildPool.action",
			async : false,
			data:{"storageChildPoolId":$("#hid_nodeId").val()},		
			success:(function(data){
				deleteTreeNode($("#hid_nodeId").val());
				showTip(i18nShow('message2'));
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('message3'));
			} 
		});
	});	
}

function unRefStorageDevice(){
	showTip(i18nShow('message5'),function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-storage/device/unRefStorageDevices.action",
			async : false,
			data:{"storageDeviceId":$("#hid_nodeId").val()},
			success:(function(data){
				if(data.result!=""){
					showTip(data.result);
				}else{
					deleteTreeNode($("#hid_nodeId").val());
					showTip(i18nShow('message6'));
					search();
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('message7'));
			} 
		});
	});
	
}

function refStorageDevice(){
		openDialog('showRefDevice',i18nShow('message8'),750,400);
		//$("#showRefDevice").show();
		$("#showRefDeviceButtonDiv").show();
		//$("#btn_refDev").hide();
		$("#gridPagerRef").show();
		//先卸载jqgrid，这里可以重复使用jqGrid方法进行加载，而不必使用reload方法
		$("#gridTableRef").jqGrid().GridUnload("gridTableRef");
			var s = $("#hid_scpapptype").val();
			$("#gridTableRef").jqGrid({
				url : ctx+"/resmgt-storage/device/getNonRefStorageDevices.action", 
				postData:{"deviceModel":$("#lab_scpdevtype").text(),"appType":s},
				rownumbers : true, 
				datatype : "json", 
				mtype : "post", 
				height : 200,
				autowidth : true, 
				multiselect:true,
				colNames:['id',i18nShow('k1'),'SN',i18nShow('k2'),i18nShow('k3'),i18nShow('k4')],
				colModel : [ 
				            {name : "id",index : "id",width : 120,sortable : true,align : 'center',hidden:true},
				            {name : "deviceName",index : "deviceName",	width : 120,sortable : true,align : 'center'},
				            {name : "sn",index : "sn",	width : 130,sortable : true,align : 'center'},
				            {name : "deviceManufacturer",index : "deviceManufacturer",	width : 120,sortable : true,align : 'center'},
				            {name : "deviceModel",index : "deviceModel",	width : 120,sortable : true,align : 'center'},
				            {name : "diskCapacity",index : "diskCapacity",	width : 120,sortable : true,align : 'center'}],
				viewrecords : false,
				sortname : "createDateTime",
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
				pager : "#gridPagerRef",
				//caption : "请选择要关联到"+$("#lab_scpname").text()+"的存储设备",
				hidegrid : false
			});
//			$("#showRefDevice").removeClass("ui-dialog-content");
//			$("#showRefDevice").removeClass(" ui-widget-content");
//			$("#showRefDevice").addClass("panel clear");
}

function saveRefStorageDevice(){
	$("#hid_icon").val(iconPath+'storagedev.png');
	var ids = jQuery("#gridTableRef").jqGrid('getGridParam','selarrrow');
	if(ids.length == 0){
		showError(i18nShow('message9'));
		return;
	}	
	showTip(i18nShow('message10'),function(){
		var list = [];
		$(ids).each(function (index,id){
			var rowData = $("#gridTableRef").getRowData(id);
			list[list.length] = rowData.id;
		});
				
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-storage/device/refStorageDevices.action",
			async : false,
			data:{"refStorageDeviceIds":list.join(","),"refResChildPoolId":$("#hid_nodeId").val(),"refResPoolId":$("#hid_pnodeId").val()},
			success:(function(data){
				$(ids).each(function (index,id){
					var rowData = $("#gridTableRef").getRowData(id);
					addNode(rowData.id,rowData.deviceName,$("#hid_icon").val(),null,false,"sd",false);
				});
				
				$("#gridTableRef").jqGrid().trigger("reloadGrid");
				closeView("showRefDevice");
				showTip("关联成功");
				search();
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_req_fail'));
			} 
		});
	});
	
}

function cancelRefStorageDevice(){
	closeView("showRefDevice");
	//$("#btn_refDev").show();
	$("#gridTableRef").jqGrid().GridUnload("gridTableRef");
}

function saveOrModifyStoragePool(oper){
	saveOrModify = oper;
	$("#sp_form").submit();
}

function saveOrUpdateStorageChildPool(oper){
	saveOrModify = oper;
	$("#scp_form").submit();	
}

function searchTreeByCname() {
	var searchForName = $.trim($("#txt_search").val());
	var url;
	if(searchForName == ""){
		url = ctx+"/resmgt-storage/tree/getStorageTreeList.action";
	}else{
		url = ctx+"/resmgt-storage/tree/getStorageTreeListByNodeName.action";
	}
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,  
		data : {"nodeName" : searchForName,"searchType":$("#sel_type").val()},
		error : function() {//请求失败处理函数   
			showError(i18nShow('tip_req_fail'));
		},
		success : function(data) { //请求成功后处理函数。     
			zTreeInit("treeRm", data);
		}
	});
}
