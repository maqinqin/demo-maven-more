$(function() {
	//主页面选择虚拟机
	$("#service-auto-btn").click(function() {
		checkFlag = "first";
		selectVmDevice();
	});
	//选择虚拟机页面，下一步按钮
	$("#next_vm").click(function() {
		nextCloudService();
	});
	//选择云服务页面，点击下一步
	$("#next_setAttr").click(function() {
		nextSetAttr();
	});
	
	//选择云服务页面，点击返回
	$("#dev_set").click(function(){
		devSet();
	});
	//参数详情页，点击确定按钮
	$("#next_saveServiceAuto").click(function() {
		saveServiceAuto();
	});
	//step3，填写服务参数页面，返回按钮
	$("#pre_attr_auto").click(function(){
		cloudSet();
	});
	
	
	//点击保存按钮，保存云服务参数
	$("#auto_save_attr").click(function(){
		saveCloudAttrAuto();
	});
	//点击取消按钮，取消云服务参数修改
	$("#auto_cancel_attr").click(function(){
		cancelCloudAttrAuto();
	});
	//主页面点击提交按钮
	$("#submit_btn_auto").click(function(){
		checkFlag = "second";
		validateBaseInfo();
	});
	//主页面点击取消按钮
	$("#cancel_btn_auto").click(function(){
		cancelRequest();
	});
})

//最后的提交页面
function submitServiceAutoRequest(){
	if(serviceObjMapList.length == 0) {
		showError(i18nShow('my_req_auto_addInfo'));
		return;
	}
	
	var bmSr = {
			"srId" : $("#srId").val(),
			"srCode" : $("#srCode").val(),
			"srTypeMark" : $("#srTypeMark").val(),
			"appId" : $("#appId").val(),
			"datacenterId" : $("#datacenterId").val(),
			"startTimeStr" : $("#serviceBeginTimes").val(),
			"endTimeStr" : $("#serviceEndtimes").val(),
			"summary" : $("#summary").val(),
			"remark" : $("#describe").val(),
			"creatorId" : $("#userId").val()
		};
	var serviceAutoVo = {
			"bmSr" : bmSr,
			"rrinfoList" : getRrinfoAutoList()
		};
	showTip("load");
	$.post(ctx + "/request/serviceauto/saveServiceAuto.action", {
		jsonData : JSON.stringify(serviceAutoVo),
		jsonClass : "com.git.cloud.request.model.vo.ServiceAutoVo"
	}, function(data) {
		if(data.result!=null && data.result=="success"){
			showTip(i18nShow('my_req_save_successTip') + data.message, function () {
				closeTip();
				initCloudRequestList();
				$("#submit_btn_auto").attr("disabled", true);
				$("#submit_btn_auto").removeClass("btn_disabled").addClass('btn');
			});  
		}else{
			closeTip();
			showError(i18nShow('my_req_save_failTip')+data.result);
			$("#submit_btn_auto").attr("disabled", true);
			$("#submit_btn_auto").removeClass("btn_disabled").addClass('btn');
		}
	}).error(function() {
		closeTip();
		showError(i18nShow('compute_res_error'),null,"red");
		$("#submit_btn_auto").attr("disabled", true);
		$("#submit_btn_auto").removeClass("btn_disabled").addClass('btn');
	});
	
}

//获取最终显示的列表信息
function getRrinfoAutoList(){
	var rrinfoList = new Array();
	var ids = $("#serviceAutoTable").jqGrid('getDataIDs');
	//alert(ids);
	var rrinfo_id,service_id,du_id,cpu,mem,sysDisk,vmNum,deviceId,deviceParamDef,deviceId;
	var parameterStr;
	var bmsrAttrVals;
	for ( var i = 0; i < ids.length; i++) {
		//设备ID
		deviceId = jQuery('#serviceAutoTable').getCell(ids[i], "deviceId");
		rrinfo_id = jQuery('#serviceAutoTable').getCell(ids[i], "rrinfoId");
		service_id = jQuery('#serviceAutoTable').getCell(ids[i], "serviceId");
		du_id = jQuery('#serviceAutoTable').getCell(ids[i], "duId");
		var du = du_id.split(":");
		parameterStr = jQuery('#serviceAutoTable').getCell(ids[i], "deviceModel");
		var paramList = parameterStr.split(",");
		cpu = paramList[0];
		mem = paramList[1];
		sysDisk = paramList[2];
		//vmNum = paramList[3];
		if(attrListMap[deviceId]!=null){
			var attrValList = attrAutoListParse(attrListMap[deviceId]);
		}else{
			var attrValList = new Array(); // getAttrValList();没有参数的就不需要取得参数list了 by wangdy
		}
				
		var serviceObj = {
			"rrinfoId" : rrinfo_id,
			"serviceId" : service_id,
			"duId" : du[1],
			"cpu" : cpu,
			"mem" : mem,
			"sysDisk" : sysDisk,
			"vmNum" : vmNum,
			"deviceId" : deviceId,
			"attrValList" : attrValList
		};
		rrinfoList.push(serviceObj);			
		}
	return rrinfoList;

}

//封装参数列表信息
function attrAutoListParse(obj){ 
	var attrList = new Array();
	var device_id,attr_id, attr_input, attr_name,attr_cname,attr_class,attrTypeCode,attrType,remark,attr_need, attr_def,attrVal_id,rrInfo_id;
	for (var j = 0; j < obj.length ; j++) {
		attr_id = obj[j].attrId;
		attr_need = obj[j].isRequire;
		attr_def = obj[j].defVal;
		attr_input = obj[j].attrValue;
		attr_name = obj[j].attrName;
		attr_cname = obj[j].attrCname;
		attr_class = obj[j].attrClass;
		attrTypeCode = obj[j].attrTypeCode;
		attrType = obj[j].attType;
		remark = obj[j].remark;
		attrVal_id = obj[j].attrValId;
		rrInfo_id = obj[j].rrinfoId;
		device_id = obj[j].deviceId;
		var attrObj = {
			"attrValue" : attr_input,
			"attrId" : attr_id,
			"attrName" : attr_name,
			"attrCname" : attr_cname,
			"attrClass" : attr_class,
			"attrTypeCode" : attrTypeCode,
			"isRequire" : attr_need,
			"defVal" : attr_def,
			"srAttrValId" : attrVal_id,
			"rrinfoId" : rrInfo_id,
			"attrType":attrType,
			"remark" : remark,
			"deviceId" : device_id
		};
		attrList.push(attrObj);
	};	
	return attrList;
}

//选择云服务页面关闭，显示填写参数页面
function nextSetAttr(){ 
	// 校验必须选择云服务
	var set_row_id = getCheckedRadio("radioService");
	if (set_row_id == -1) {
		showError(i18nShow('my_req_auto_selectCs'));
		return;
	}
	//校验之前未提交该云服务
	var cloudSer_id = jQuery('#cloudSerlistTable').getCell(set_row_id, "serviceId");
	$("#cloudSer_id").val(cloudSer_id);
	// 关闭选择云服务页面
	$("#select_cloudSer_div").hide();
	//读入需要填写的参数页面
	$("#select_attrAuto_div").show();
	$("#step_auto1").attr("class","last");
	$("#step_auto2").attr("class","last");
	$("#step_auto3").attr("class","now");
	$("#auto_save_attr").hide();
	$("#auto_cancel_attr").hide();
	$("#autoCloudattrTable").setGridWidth($("#autoCloud_attr_div").width()-1);
	$(window).resize(function() {
	  $("#autoCloudattrTable").setGridWidth($("#autoCloud_attr_div").width()-1);
	});
	autoAttrTableInit(cloudSer_id);
}

//点击保存按钮，保存云服务参数
function saveCloudAttrAuto(){
	var deviceId = $("#hidDeviceId").val();
	// 校验保存输入填写的参数
		var serviceObjMap = new Object();
		for ( var k = 0; k < serviceObjMapList.length; k++) {
			if (serviceObjMapList[k].deviceId == deviceId) {
				serviceObjMap = serviceObjMapList[k];
				break;
			}
		}
		// 校验保存输入填写的参数
		var attrList = new Array();
		var ids = $("#autoCloudattrTable").jqGrid('getDataIDs');
		var attr_id, attr_input, attr_need, attr_def, attrVal_id, rrInfo_id;
		for ( var j = 0; j < ids.length; j++) {
			attr_id = jQuery('#autoCloudattrTable').getCell(ids[j], "attrId");
			attr_need = jQuery('#autoCloudattrTable').getCell(ids[j], "isRequire");
			attr_def = jQuery('#autoCloudattrTable').getCell(ids[j], "defVal");
			attrVal_id = jQuery('#autoCloudattrTable').getCell(ids[j], "attrValId");
			rrInfo_id = jQuery('#autoCloudattrTable').getCell(ids[j], "rrinfoId");
			attr_input = $.trim($("#attr_" + attr_id).val());
			attr_name = jQuery('#autoCloudattrTable').getCell(ids[j], "attrName");
			attr_cname = jQuery('#autoCloudattrTable').getCell(ids[j], "attrCname");
			attr_class = jQuery('#autoCloudattrTable').getCell(ids[j], "attrClass");
			attrType=jQuery('#autoCloudattrTable').getCell(ids[j], "attrType");
			var attrSelList = eval(jQuery('#autoCloudattrTable').getCell(ids[j], "attrSelList"));
			attrTypeCode = jQuery('#autoCloudattrTable').getCell(ids[j], "attrTypeCode");
			remark = jQuery('#autoCloudattrTable').getCell(ids[j], "remark");
			if (attr_need == 'Y' && attr_input == "") {
				attrList = null;
				showError(i18nShow('error_all_requre'));
				return;
			}
			var attrObj = {
				"attrValue" : attr_input,
				"attrId" : attr_id,
				"attrName" : attr_name,
				"attrCname" : attr_cname,
				"attrClass" : attr_class,
				"attrTypeCode" : attrTypeCode,
				"isRequire" : attr_need,
				"defVal" : attr_def,
				"attrValId" : attrVal_id,
				"rrinfoId" : rrInfo_id,
				"attrType" : attrType,
				"attrSelList" : attrSelList,
				"remark" : remark
			};
			attrList.push(attrObj);
		}
		serviceObjMap.attrValList = null;
		serviceObjMap.attrValList = attrList;
		attrListMap[deviceId] = attrList;
		serviceObjMapList[k] = serviceObjMap;
		// 关闭填写参数页面
		$("#autoCloud_attr_div").dialog("close");
		showTip(i18nShow('tip_save_success'));
}

//关闭修改云服务参数
function cancelCloudAttrAuto(){
	 $("#autoCloud_attr_div").dialog("close");
}

//最后保存时，返回主页面
function GoBackToMainAuto(){
	$("#select_attrAuto_div").hide();
	$("#select_cloudSer_div").hide();
	$("#step_s").hide();
	$("#step").hide();
	$("#step_auto").hide();
	$("#div_baseCase").slideDown('slow');
	$("#serviceAuto_main_div").slideDown('slow');
}

//选择云服务页面，点击返回
function devSet(){
	// 关闭选择云服务页面
	$("#select_cloudSer_div").hide();
	$("#step_s").hide();
	$("#step").hide();
	$("#step_auto").hide();
	$("#div_baseCase").slideDown('slow');
	$("#serviceAuto_main_div").slideDown('slow');
}

//选择虚机页面，返回服务自动化主页
function GoBackToMainAutoDevice(){
	$("#vm_select_div").slideUp('slow',function(){
		$("#vm_select_div").dialog("close");
	});
}

//选择虚拟机页面，点击确定选择云服务
function nextCloudService(){
	//判断是否选择虚拟机，未选择将返回主页
	var treeObj = $.fn.zTree.getZTreeObj("deviceTree2_auto");
	var nodeArr = treeObj.transformToArray(treeObj.getNodes());
	if(nodeArr.length>0){
		//关闭选择虚拟机的对话框
		$("#vm_select_div").dialog("close");
		//显示服务自动化步骤
		$("#step_auto").show();	
		//slideUp以滑动方式隐藏
		$("#div_baseCase").slideUp('slow');
		//主界面隐藏
		$("#serviceAuto_main_div").hide();	
		$("#select_cloudSer_div").show();
		$("#cloudSerlistTable").setGridWidth($("#serAuto_div2").width()-1);
		$(window).resize(function() {
			$("#cloudSerlistTable").setGridWidth($("#serAuto_div2").width()-1);
	    });
		//加载云服务列表
		cloudServiceList();
		//弹出选择虚拟机列表页面
		$("#vm_select_div").show();
		$("#step_auto1").attr("class","last");
		$("#step_auto2").attr("class","now");
	}else{
		GoBackToMainAutoDevice();
	}
	
}





//-----------------------选择虚拟机页面-----------------------------------------------------------------------

function selectVmDevice() {
	
	$("#vm_select_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#vm_select_div").dialog("option", "title", i18nShow('my_req_auto_selectVm'));
	initVmDeviceTree();
	appChanged = false;
	datacenterChanged = false;
}


//初始化树
function initVmDeviceTree() {
	var appId = $("#appId").val();
	var datacenterId = $("#datacenterId").val();
	var deviceIdArr = getSelectedDeviceIds(); // 获取已选的虚拟机Id
	var setting1 = {
		async : {
			enable : true,
			url : ctx + "/request/recycle/findDeviceTreeForRec.action",
			autoParam : [ "id" ],
			otherParam : {
				appId : appId,
				datacenterId : datacenterId,
				deviceIdArr : deviceIdArr
			}
		},
		check : {
			enable : true,
			chkType : "checkbox"
		},
		data : {
			key : {
				title : "title"
			}
		},
		callback : {
			beforeCheck : zTreeBeforeCheck,
			onAsyncSuccess : zTreeOnAsyncSuccess
		}
	};
	var setting2 = {
		check : {
			enable : true,
			chkType : "checkbox"
		},
		data : {
			key : {
				title : "title"
			}
		}
	};
	// 初始化左侧树，默认只站看到资源池，下级节点默认隐藏
	$.fn.zTree.init($("#deviceTree1_auto"), setting1);
	$.fn.zTree.init($("#deviceTree2_auto"), setting2);
	//清空之前选择的数据
	//$( "#selectDeviceTable" ).clearGridData(); 
	
	
	
}

//初始化树
var nodeStr = "";
function zTreeBeforeCheck(treeId, treeNode) {
	if (treeNode && treeNode.id && treeNode.id.indexOf("DU:") == 0) {
		if (treeIdArr.indexOf(treeNode.id) < 0) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			nodeStr += treeNode.id + ";";
			treeObj.reAsyncChildNodes(treeNode, "refresh", false);
			treeIdArr.push(treeNode.id);
		}
	}
}
//初始化树
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	if (treeNode && treeNode.id && treeNode.id.indexOf("DU:") == 0) {
		if (nodeStr.indexOf(treeNode.id + ';') >= 0) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodeArr = treeObj.getNodesByParam("upId", treeNode.id, null);
			for ( var i = 0; i < nodeArr.length; i++) {
				treeObj.checkNode(nodeArr[i], true, true);
			}
			nodeStr.replace(treeNode.id + ';', '');
		}
	}	
	var data1 = $("#serviceAutoTable").jqGrid('getRowData');
	var selectData1 = [];
	if(data1){
		for(var i = 0,l=data1.length;i<l;i++){
			var treeObj1 = $.fn.zTree.getZTreeObj("deviceTree1_auto");
			var nodes1 = treeObj1.transformToArray(treeObj1.getNodes());
			if(nodes1){
				for(var j = 0,k=nodes1.length;j<k;j++){
					if(nodes1[j].name == data1[i].device){
						treeObj1.removeNode(nodes1[j]);
						break;
					}
				}
			}
		}
	}
}



//----------------------------------------------------------------------------------------------

//服务自动化主页面
function initServiceAutoMain() {
	$("#serviceAutoTable").jqGrid().GridUnload("serviceAutoTable");
	$("#serviceAutoTable").jqGrid({
		datatype : 'local',
		height : 180,
		autowidth : true,
		colModel : [
		{
			name : 'serviceName',
			index : 'serviceName',
			label : i18nShow('my_req_auto_cloudServices_name'),
			width : 9,
			hidden : false,
			align : "left"
		},
		  {
			name : 'serviceId',
			index : 'serviceId',
			label :'云服务ID',
			width : 0,
			hidden : true,
			align : "left"
		 },{
			name : 'duId',
			index : 'duId',
			label :'服务器角色Id',
			width : 0,
			hidden : true,
			align : "left"
		}, {
			name : 'deviceId',
			index : 'deviceId',
			label :'虚拟机Id',
			width : 10,
			hidden : true,
			key : true,
			align : "left"
		}, { 
			name : 'deviceTypeCode',
			index : 'deviceTypeCode',
			label :'设备类型',
			width : 0,
			hidden : true,
			align : "left"
		}, {
			name : 'haTypeCode',
			index : 'haTypeCode',
			label :'高可用类型',
			width : 0,
			hidden : true,
			align : "left"
		}, { 
			name : 'du',
			index : 'du',
			label :i18nShow('compute_res_duName'),
			width : 5,
			sortable : false,
			align : "left",
			formatter : function(cellValue, options, rowObject) {
				return cellValue;
			}
		}, {
			name : 'device',
			index : 'device',
			label :i18nShow('compute_res_vmName'),
			width : 6,
			sortable : false,
			align : "left"
		}, { 
			name : 'deviceParamDef',
			index : 'deviceParamDef',
			label :i18nShow('my_req_sr_vm_conf'),
			width : 6,
			sortable : false,
			align : "left"
		}, {
			name : 'deviceModel',
			label :'配置信息',
			hidden : true
		}, {
			name : 'deviceIps',
			index : 'deviceIps',
			label :'IP',
			width : 10,
			sortable : false,
			align : "left"
		},{
			name : 'attr_operate',
			index : 'attr_operate',
			label :i18nShow('sg_rule_operate'),
			width : 3,
			sortable : false,
			align : "left",
			hidden : false,
			formatter : function(cellValue, options, rowObject) {
				return"<a title='' href='javascript:void(0)' style='margin-right: 10px;text-decoration:none;' onclick=\"detailFromTableArrt('" + rowObject.serviceId + "','" + rowObject.deviceId + "')\" >"+i18nShow('my_req_device_attrDatil')+"</a>";
			}
		}],
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		pager : '#pageNav_auto',
		sortname : 'deviceId',
		sortorder : "asc",
		rownumbers : true,
		viewrecords : true,
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		}
	});
	
}

//
function autoAttrTableInit(serviceId){
	var paramObj = {"serviceId":serviceId,"SERVICE_ID":serviceId};
	$("#autoCloudattrTable").jqGrid().GridUnload("autoCloudattrTable");		
	$("#autoCloudattrTable").jqGrid(
			{
				url : ctx + '/request/supply/getServiceAttrList.action',
				datatype : "json",
				postData:paramObj,
				height : 300,
				autowidth : true,
				colModel : [
				{
					name : 'attrValId',
					index : 'attrValId',
					label : '参数值ID',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'rrinfoId',
					index : 'rrinfoId',
					label : '资源请求ID',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrId',
					index : 'attrId',
					label : '参数ID',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrTypeCode',
					index : 'attrTypeCode',
					label : '数据类型',
					hidden : true,
					width : 5,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						return "A";
					}
				},
				
				{
					name : 'attrName',
					index : 'attrName',
					label : '参数名称英文',
					width : 0,
					hidden : true,
					align : "left"
				},{
					name : 'attrCname',
					index : 'attrCname',
					label : i18nShow('cloud_service_attr_name'),
					sortable : false,
					width : 20,
					align : "left"
				},{
					name : 'attrClass',
					index : 'attrClass',
					label : '属性类型',
					sortable : false,
					hidden : true,
					width : 20,
					align : "left"
				},{
					name : 'isRequire',
					index : 'isRequire',
					label : '是否必填隐藏值',
					sortable : false,
					hidden : true,
					width : 20,
					align : "left"
				},{
					name : 'option',
					index : 'option',
					label : i18nShow('cloud_service_model_isRequire'),
					sortable : false,
					hidden : false,
					width : 10,
					align : "left",
					formatter : function(cellValue,options,rowObject){
						if(rowObject.isRequire == 'N'){
							return i18nShow('cloud_service_require_N');
						}else if(rowObject.isRequire == 'Y'){
							return i18nShow('cloud_service_require_Y');
						}
					}
				},{ 
					name : 'defVal',
					label :'默认值',
					sortable : false,
					hidden : true,
					index : 'defVal',
					width : 10,
					align : "left"
				},{
					name : 'attrSelList',
					label :'选项',
					sortable : false,
					hidden : true,
					index : 'attrSelList',
					width : 10,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						return JSON.stringify(cellValue);
					}
				},
				{
					name : 'attrType',
					label :'分类',
					sortable : false,
					hidden : true,
					index : 'attrType',
					width : 10,
					align : "left"
		      	},
				{
					name : 'Classes',
					index : 'Classes',
					label : i18nShow('my_req_parameter_class'),
					width : 10,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						      var className= "";
						         if(rowObject.attrType=='COMPUTE') {className='计算资源';}else
						          if (rowObject.attrType=='NET') {className='网络';}else
						        	  if(rowObject.attrType=='STORAGE') {className='存储资源';}
						         
						
						return  className;
					}
				},{
					name : 'inputVal',
					index : 'inputVal',
					label : i18nShow('my_req_fillIn'),
					sortable : false,
					width : 45,
					align : "left",
					formatter : function(cellValue, options, rowObject) {
						if (actionType == 'detail') {
							return retValInput(cellValue, rowObject, true);
						}
						  if(editclass==rowObject.attrClass||editclass=='0'){
						      return retValInput(cellValue, rowObject, false);
						   }
						  else {
							  return retValInput(cellValue, rowObject, true);
							  
						  }
					}
				},{
					name : 'remark',
					index : 'remark',
					label : i18nShow('my_req_fillIn_rule'),
					sortable : false,
					width : 50,
					hidden : false/*,
					formatter : function(cellValue, options, rowObject) {
						if (cellValue != undefined) {
							return "<textarea id='textarea_"+options.rowId+"'  style='width: 300px;height:35px;overflow-y:auto' cols='60' rows='1' disabled='disabled' class='textInput'>" + rowObject.remark + "</textarea>";
						} else {
							return '';
						}
					}*/
				}],
				rowNum : 100,
				rowList : [ 100, 20, 30, 50 ],
				sortname : 'attrId',
				sortorder : "asc",
				// pager : '#pageNav_attr_seau',
				viewrecords : true,
				jsonReader : {
					root : "dataList",
					records : "record",
					repeatitems : false
				}

			});
	var ids = $("#autoCloudattrTable").jqGrid('getDataIDs');
	if(ids.length>=0){
		attrNeededFlag = false;
	}

}


function detailFromTableArrt(serviceId,deviceId){
	$("#hidDeviceId").val(deviceId);
	$("#autoCloud_attr_div").dialog({
		autoOpen : true,
		modal : true,
		width : 1000,
		height:480
	});
	$("#autoCloud_attr_div").dialog("option", "title", i18nShow('cloud_service_attr_update'));
//	//显示保存和取消按钮
	$("#auto_save_attr").show();
	$("#auto_cancel_attr").show();
//	// 清空参数表
//	$("#autoCloudattrTable").clearGridData();
//	$("#autoCloudattrTable").setGridWidth(950);
//	$("#autoCloud_attr_div").attr("style","");//清除margin-top:100px
//	
//	var serviceObjMap = new Object();
//	for ( var k = 0; k < serviceObjMapList.length; k++) {
//		if (serviceObjMapList[k].deviceId == deviceId) {
//			serviceObjMap = serviceObjMapList[k];
//			break;
//		}
//	}
//	
//	var attrList = attrListMap[deviceId];
//	// var length = $("#attrTable").jqGrid('getDataIDs').length;
	$("#pre_attr").hide();
	$("#save_service").hide();
	if (actionType == 'new' || actionType == 'update') {
		$("#save_attr_btn").show();
		$("#cancel_attr_btn").show();
	} else if (actionType == 'detail'||actionType == 'view') {
		$("#save_attr_btn").hide();
		$("#cancel_attr_btn").hide();
		// attrTableInit(service_id);
	}
	// 清空参数表
	$("#autoCloudattrTable").clearGridData();
	$("#autoCloudattrTable").setGridWidth(950);
	$("#supply_div4").attr("style","");//清除margin-top:100px
	
	var serviceObjMap = new Object();
	for ( var k = 0; k < serviceObjMapList.length; k++) {
		if (serviceObjMapList[k].deviceId == deviceId) {
			serviceObjMap = serviceObjMapList[k];
			break;
		}
	}
	
	var attrList = attrListMap[deviceId];
	// 往参数表中插入记录
	for ( var j = 0; j < attrList.length; j++) {
		var storeObj = {
			"attrValId" : attrList[j].srAttrValId,
			"rrinfoId" : attrList[j].rrinfoId,
			"attrTypeCode" : attrList[j].attrTypeCode,
			"remark" : attrList[j].remark,
			"attrId" : attrList[j].attrId,
			"attrName" : attrList[j].attrname,
			"attrCname" : attrList[j].attrCname,
			"attrClass" : attrList[j].attrClass,
			"attrType" : attrList[j].attrType,
			"isRequire" : attrList[j].isRequire,
			"defVal" : attrList[j].defVal,
			"attrSelList" : attrList[j].attrSelList,
			"inputVal" : retValInputForTheDetail(attrList[j].attrValue, attrList[j], true)
		};
		// 往参数表中插入记录
		$("#autoCloudattrTable").jqGrid("addRowData", j, storeObj);
		//直接写字符串里的selected 不起作用，故重新用js设置
		if(attrList[j].attrClass == 'SELECT'){
		    var selectLable = document.getElementById("attr_"+attrList[j].attrId).options;
			for(var i=0;i<selectLable.length;i++){
			    if(selectLable[i].value == attrList[j].attrValue){
			       selectLable[i].selected="selected";
			    }
		    }
	    }
		//当类型为LIST时需要为隐藏域和显示文本重新赋值
		if(attrList[j].attrClass == 'LIST'){
//			alert(attrList[j].attrValue);
			 document.getElementById("attr_"+attrList[j].attrId).value = attrList[j].attrValue;
			 document.getElementById(attrList[j].attrId).value = attrList[j].attrValue;
		}
	}
}



// 读取所有可用的云服务目录
function cloudServiceList() {
	$("#cloudSerlistTable").jqGrid().GridUnload("cloudSerlistTable");
	$("#cloudSerlistTable").jqGrid({
		url : ctx + '/request/supply/getServiceList.action',
		datatype : "json",
		postData : {"serviceId":csId,"serviceStatic":_status,"serviceType":"SERVERAUTO"},
		height : 220,
		autowidth : true,
		colModel : [
		{
			name : 'rrinfoId',
			index : 'rrinfoId',
			label : 'rrinfoId',
			width : 0,
			hidden : true,
			align : "left"
		},{
			name : 'serviceId',
			index : 'serviceId',
			label : '服务目录ID',
			width : 0,
			hidden : true,
			align : "left"
		},{
			name : 'select',
			index : 'select',
			label : i18nShow('my_req_select'),
			width : 5,
			sortable : false,
			align : "center",
			formatter : function(cellValue, options, rowObject) {
				return "<input type='radio' name='radioService' value='" + options.rowId + "'" + ">";
				}
		},{ 
			name : 'serviceName',
			index : 'serviceName',
			label : i18nShow('my_req_sr_service_name'),
			width : 20,
			sortable : false,
			align : "left",
			formatter : function(cellValue, options, rowObject) {
				var ops = "";
				ops += "<a href='#' style='' ";
				ops += "id='href_" + options.rowId + "' ";
				ops += "onclick='viewCatalogdetail(" + rowObject.serviceId + ")'" + ">" + cellValue + "</a>";
				return cellValue;
			}
		},{ 
			name : 'funcRemark',
			index : 'funcRemark',
			label : i18nShow('my_req_summary'),
			width : 40,
			sortable : false,
			align : "left"
		},{
			name : 'platformTypeName',
			index : 'platformTypeName',
			label : i18nShow('cloud_service_platform'),
			width : 10,
			sortable : false,
			align : "left"
		},{
			name : 'vmBase',
			index : 'vmBase',
			label : i18nShow('my_req_vmNum'),
			width : 10,
			sortable : false,
			align : "left"
		}],
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		sortname : 'serviceId',
		sortorder : "asc",
		pager : '#pageNavList',
		viewrecords : true,
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		}
	});
};

function saveServiceAuto(){
	// 获取云服务目录相关信息
	var service_row_id = getCheckedRadio("radioService");
	var serviceId = jQuery('#cloudSerlistTable').getCell(service_row_id, "serviceId");
	var serviceName = jQuery('#cloudSerlistTable').getCell(service_row_id, "serviceName");
	
	
	var serviceObjMap = new Object();
	for ( var k = 0; k < serviceObjMapList.length; k++) {
		if (serviceObjMapList[k].duId == du_id) {
			serviceObjMap = serviceObjMapList[k];
			break;
		}
	}
	
	
	//获取选择的虚拟机信息
	var treeObj = $.fn.zTree.getZTreeObj("deviceTree2_auto");
	var nodeArr = treeObj.transformToArray(treeObj.getNodes());
	var n = $("#serviceAutoTable").jqGrid('getRowData').length;
	for ( var i = 0; i < nodeArr.length; i++) {
		if (nodeArr[i].isParent) {
			continue;
		}
		//-------------------------------------------------------------
		// 校验保存输入填写的参数
		var attrList = new Array();
		var ids = $("#autoCloudattrTable").jqGrid('getDataIDs');
		var attr_id, attr_input, attr_need, attr_def, attrVal_id, rrInfo_id;
		for ( var j = 0; j < ids.length; j++) {
			attr_id = jQuery('#autoCloudattrTable').getCell(ids[j], "attrId");
			attr_need = jQuery('#autoCloudattrTable').getCell(ids[j], "isRequire");
			attr_def = jQuery('#autoCloudattrTable').getCell(ids[j], "defVal");
			attrVal_id = jQuery('#autoCloudattrTable').getCell(ids[j], "attrValId");
			rrInfo_id = jQuery('#autoCloudattrTable').getCell(ids[j], "rrinfoId");
			attr_input = $.trim($("#attr_" + attr_id).val());
			attr_name = jQuery('#autoCloudattrTable').getCell(ids[j], "attrName");
			attr_cname = jQuery('#autoCloudattrTable').getCell(ids[j], "attrCname");
			attr_class = jQuery('#autoCloudattrTable').getCell(ids[j], "attrClass");
			attrType=jQuery('#autoCloudattrTable').getCell(ids[j], "attrType");
			var attrSelList = eval(jQuery('#autoCloudattrTable').getCell(ids[j], "attrSelList"));
			attrTypeCode = jQuery('#autoCloudattrTable').getCell(ids[j], "attrTypeCode");
			remark = jQuery('#autoCloudattrTable').getCell(ids[j], "remark");
			//隐藏区域的规则
			if (attr_need == 'Y' && attr_input == "") {
				attrList = null;
				showError(i18nShow('error_all_requre'));
				return;
			}
			var attrObj = {
				"attrValue" : attr_input,
				"attrId" : attr_id,
				"attrName" : attr_name,
				"attrCname" : attr_cname,
				"attrClass" : attr_class,
				"attrTypeCode" : attrTypeCode,
				"isRequire" : attr_need,
				"defVal" : attr_def,
				"attrValId" : attrVal_id,
				"rrinfoId" : rrInfo_id,
				"attrType" : attrType,
				"attrSelList" : attrSelList,
				"remark" : remark
			};
			attrList.push(attrObj);
		}
		serviceObjMap.attrValList = null;
		serviceObjMap.attrValList = attrList;
		var deviceId = nodeArr[i].id;
		attrListMap[deviceId] = attrList;
		serviceObjMapList[k] = serviceObjMap;
		
		//-------------------------------------------------------------
		
		var duObj = treeObj.getNodeByParam("id", nodeArr[i].upId, null);
		var cpu = nodeArr[i].params.vmCpu;
		var mem = nodeArr[i].params.vmMem;
		var disk = nodeArr[i].params.vmDisk;
		var parameter_def = i18nShow('my_req_auto_cpu') + cpu + i18nShow('my_req_auto_mem') + mem + i18nShow('my_req_auto_disk') + disk + i18nShow('my_req_auto_g');
		var deviceModel = cpu + "," + mem + "," + disk;
		var nodeObj = {
			serviceId:serviceId,
			serviceName:serviceName,
			duId : duObj.id,
			deviceId : nodeArr[i].id,
			deviceTypeCode : nodeArr[i].params.deviceTypeCode,
			haTypeCode : nodeArr[i].params.haTypeCode,
			du : duObj.name,
			device : nodeArr[i].name,
			deviceParamDef : parameter_def,
			deviceModel : deviceModel,
			deviceIps : nodeArr[i].title.replace(/\n/g, ', ').replace(i18nShow('my_req_auto_ip'), '')
		};
		//最开始的jqGride中写入数据
		$("#serviceAutoTable").jqGrid("addRowData", n++, nodeObj);
	}	
	GoBackToMainAuto();
}
 function cloudSet(){
	 //隐藏填写参数页面
	 $("#select_attrAuto_div").hide();
	 //显示云服务页面
	 $("#select_cloudSer_div").show();
	 $("#cloudSerlistTable").setGridWidth($("#serAuto_div2").width()-1);
		$(window).resize(function() {
			$("#cloudSerlistTable").setGridWidth($("#serAuto_div2").width()-1);
	    });
	//加载云服务列表
	cloudServiceList();
	switchStepAuto(2);
 }
//设置向导导航的选中样式
 function switchStepAuto(nowId){
 	$("#step_auto li").each(function(i,item){
 		if((i+1)< nowId){
 			$(item).attr("class","last");
 		}else if((i+1) == nowId){
 			$(item).attr("class","now");
 		}else{
 			$(item).attr("class","");
 		}		
 	});	
 }

