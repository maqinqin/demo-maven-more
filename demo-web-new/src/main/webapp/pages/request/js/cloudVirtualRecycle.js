$(function() {
	// 点击回收按钮
	$("#recycle_select_btn").click(function() {
		checkFlag = "first";
		validateBaseInfo();
	});
	// 点击提交按钮
	$("#recycle_submit_btn").click(function() {
		checkFlag = "second";
		validateBaseInfo();
	});
})
var treeIdArr = new Array();
function showDiv(divId, title) {
	$("#rightContentDiv div").hide();
	$("#" + divId).show();
	$("#lab_title").html(title);
}
function openDialog(divId, title, width, height) {
	$("#" + divId).dialog({
		autoOpen : true,
		modal : true,
		height : height,
		width : width,
		title : title,
		//resizable : false
	});
}
function closeView(divId) {
	$("#" + divId).dialog("close");
}
function clearTreeId() {
	treeIdArr.length = 0;
}
function initDeviceList() {
	if (!initFlag) {
		initFlag = true;
		$("#selectDeviceTable").jqGrid({
			datatype : 'local',
			height : 180,
			autowidth : true,
			colNames : [ i18nShow('my_req_sr_duId'), i18nShow('my_req_sr_vmdeviceId'), i18nShow('my_req_sr_deviceType'), i18nShow('my_req_sr_haType'), i18nShow('my_req_sr_duName'), i18nShow('my_req_sr_vmName'), i18nShow('my_req_sr_vm_conf'), i18nShow('my_req_recycle_server_conf'), 'IP' ],
			colModel : [ {
				name : 'duId',
				index : 'duId',
				width : 0,
				hidden : true,
				align : "left"
			}, {
				name : 'deviceId',
				index : 'deviceId',
				width : 0,
				hidden : true,
				align : "left"
			}, {
				name : 'deviceTypeCode',
				index : 'deviceTypeCode',
				width : 0,
				hidden : true,
				align : "left"
			}, {
				name : 'haTypeCode',
				index : 'haTypeCode',
				width : 0,
				hidden : true,
				align : "left"
			}, {
				name : 'du',
				index : 'du',
				width : 3,
				sortable : false,
				align : "left",
				formatter : function(cellValue, options, rowObject) {
					return cellValue;
				}
			}, {
				name : 'device',
				index : 'device',
				width : 3,
				sortable : false,
				align : "left"
			}, {
				name : 'deviceParamDef',
				index : 'deviceParamDef',
				width : 3,
				sortable : false,
				align : "left"
			}, {
				name : 'deviceModel',
				hidden : true
			}, {
				name : 'deviceIps',
				index : 'deviceIps',
				width : 5,
				sortable : false,
				align : "left"
			} ],
			rowNum : 10,
			rowList : [ 5, 10, 15, 20, 30 ],
			pager : '#pageNav_recycle',
			rownumbers : true,
			viewrecords : true,
			jsonReader : {
				root : "dataList",
				records : "record",
				repeatitems : false
			}
		});
		if (srId) {
			if (actionType == 'update' || actionType == 'detail') {
				$.post(ctx + '/request/recycle/getVirtualRecycleVoBySrId.action', {
					"_fw_service_id" : "getCloudRecoverVoBySrIdSrv",
					srId : srId
				}, function(result) {
					var requestObj = result;
					var deviceVoArr = requestObj.cmVmList;
					for ( var i = 0; i < deviceVoArr.length; i++) {
						var parameter_def = "cpu=" + deviceVoArr[i].cpu + " "+i18nShow('my_req_sr_memery')+"=" + deviceVoArr[i].mem + "M "+i18nShow('my_req_sr_disk')+"=" + deviceVoArr[i].disk + "G";
						var deviceModel = deviceVoArr[i].cpu + "," + deviceVoArr[i].mem + "," + deviceVoArr[i].disk;
						var deviceVoObj = {
							duId : 'DU:' + deviceVoArr[i].duId,
							deviceId : deviceVoArr[i].id,
							// deviceTypeCode :
							// deviceVoArr[i].deviceTypeCode,
							// haTypeCode : deviceVoArr[i].haTypeCode,
							deviceTypeCode : "",
							haTypeCode : "",
							du : deviceVoArr[i].duName,
							device : deviceVoArr[i].deviceName,
							deviceParamDef : parameter_def,
							deviceModel : deviceModel,
							deviceIps : deviceVoArr[i].cmVmIps
						};
						$("#selectDeviceTable").jqGrid("addRowData", i, deviceVoObj);
					}
				});
				if (actionType == 'detail') {
					$("#recycle_submit_btn").css("visibility", "hidden");
					$("#recycle_cancel_btn").css("visibility", "hidden");
					$("#recycle_select_btn").css("visibility", "hidden");
					$("#serviceBeginTimes").attr('onClick', '');
					$("#serviceEndtimes").attr('onClick', '');
				} else {
					$("#recycle_delete_btn").show();
					$("#recycle_goback_btn").show();
				}
			}
		}
	}
}

function selectDevice() {
	$("#recycle_select_div").dialog({
		autoOpen : true,
		modal : true,
		width : 700
	});
	$("#recycle_select_div").dialog("option", "title", i18nShow('my_req_recycle_select_vm'));
//	if (!$.fn.zTree.getZTreeObj("deviceTree1") || appChanged || datacenterChanged) {
	initDeviceTree();
	appChanged = false;
	datacenterChanged = false;
//	}
}
// 初始化树
function initDeviceTree() {
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
			onAsyncSuccess : zTreeOnAsyncSuccess,
			//beforeExpand:deleteTree1
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
	$.fn.zTree.init($("#deviceTree1"), setting1);
	$.fn.zTree.init($("#deviceTree2"), setting2);
	/*var selectedRowIds =   
		$("#selectDeviceTable").jqGrid("getGridParam","selarrrow");  
		var len = selectedRowIds.length;  
		for(var i = 0;i < len;i++ ) {  
			$("#selectDeviceTable").jqGrid("delRowData", selectedRowIds[0]);  
		} */ 
	$( "#selectDeviceTable" ).clearGridData(); 
	/*if (!appChanged && !datacenterChanged) {
		initSelectedDeviceTree();
	}*/	
}
/*function deleteTree1(){
	var treeObj1 = $.fn.zTree.getZTreeObj("deviceTree1");
	var treeObj2 = $.fn.zTree.getZTreeObj("deviceTree2");
	var nodes1 = treeObj1.getNodes(); 
	var nodes2 = treeObj2.getNodes();
	for(var i = 0;i<nodes1.length;i++){
		for(var j=0;j<nodes2.length;j++){
			if(nodes1[i].title==nodes2[j].title){
				treeObj1.expandNode(nodes1[i],true);
				alert(nodes1[i].children.length);
				for(var t=0;t<nodes1[i].children.length;t++){
					alert(nodes2[j].children.length);
					for(var l=0;l<nodes2[j].children.length;l++){
						if(nodes1[i].children[t].name==nodes2[j].children[l].name){
							treeObj1.removeNode(nodes1[i].children[t]);
						}
					}
				}
				if(nodes1[i].children.length==0){
					treeObj1.removeNode(nodes1[i]);
				}
			}
		}
	}
}*/
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
}
// 获取已选的虚拟机Id
function getSelectedDeviceIds() {
	var deviceIdArr = new Array();
	var dataIds = $("#selectDeviceTable").jqGrid('getDataIDs');
	for ( var i = 0; i < dataIds.length; i++) {
		var deviceId = jQuery('#selectDeviceTable').getCell(dataIds[i], "deviceId");
		deviceIdArr.push(deviceId);
	}
	return deviceIdArr;
}
// 获取已选的虚拟机Id
function getSelectedDeviceArr() {
	var deviceArr = new Array();
	var dataIds = $("#selectDeviceTable").jqGrid('getDataIDs');
	for ( var i = 0; i < dataIds.length; i++) {
		var deviceId = jQuery('#selectDeviceTable').getCell(dataIds[i], "deviceId");
		var deviceName = jQuery('#selectDeviceTable').getCell(dataIds[i], "device");
		var deviceTypeCode = jQuery('#selectDeviceTable').getCell(dataIds[i], "deviceTypeCode");
		var duId = jQuery('#selectDeviceTable').getCell(dataIds[i], "duId").substring(3);
		var device = {
			id : deviceId,
			deviceName : deviceName,
			deviceTypeCode : deviceTypeCode,
			duId : duId
		};
		deviceArr.push(device);
	}
	return deviceArr;
}
// 初始化已回收树上的节点
function initSelectedDeviceTree() {
	var dataIds = $("#selectDeviceTable").jqGrid('getDataIDs');
	var treeObj = $.fn.zTree.getZTreeObj("deviceTree2");
	for ( var i = 0; i < dataIds.length; i++) {
		var duId = jQuery('#selectDeviceTable').getCell(dataIds[i], "duId");
		var du = jQuery('#selectDeviceTable').getCell(dataIds[i], "du");
		var node = {id : duId, upId : 0, name : du, title : du, check : true,icon :"/icms-web/css/zTreeStyle/img/icons/appsys.png"};
		var parentObj = addTreeNode(treeObj, node, null);
		parentObj.isParent = true;
		var deviceTypeCode = jQuery('#selectDeviceTable').getCell(dataIds[i], "deviceTypeCode");
		var haTypeCode = jQuery('#selectDeviceTable').getCell(dataIds[i], "haTypeCode");
		var deviceId = jQuery('#selectDeviceTable').getCell(dataIds[i], "deviceId");
		var device = jQuery('#selectDeviceTable').getCell(dataIds[i], "device");
		var deviceModel = jQuery('#selectDeviceTable').getCell(dataIds[i], "deviceModel");
		var deviceIps = "生产IP：\n" + jQuery('#selectDeviceTable').getCell(dataIds[i], "deviceIps");
		var modelArr = deviceModel.split(",");
		node = {id : deviceId, upId : duId, name : device, title : deviceIps.replace(/, /g, '\n'),icon:"/icms-web/css/zTreeStyle/img/icons/vm.png",
			params : {deviceTypeCode : deviceTypeCode, haTypeCode : haTypeCode, vmCpu : modelArr[0], vmMem : modelArr[1], vmDisk : modelArr[2]}
		};
		parentObj = addTreeNode(treeObj, node, parentObj);
	}
}
// 初始化deviceTree2时，添加树节点
function addTreeNode(treeObj, node, parentNode) {
	var nodeObj = null;
	if (treeObj) {
		nodeObj = treeObj.getNodeByParam("id", node.id, null);
		if (!nodeObj) {
			treeObj.addNodes(parentNode, node, false);
			nodeObj = treeObj.getNodeByParam("id", node.id, null);
		}
	}
	return nodeObj;
}
// 对所选择的节点操作进行验证
function checkSelectNode(treeObj,treeId1,treeId2) {
	var nodeArr = treeObj.getCheckedNodes(true);
	if (nodeArr.length == 0) {
		showError(i18nShow('tip_my_req_recycle_select_vm'));
		return false;
	}
	// 左边树节点判断，节点不能多选
	if("deviceTree1_auto" == treeId1 && nodeArr.length > 2){
		showError(i18nShow('tip_my_req_recycle_mutilselect_left'));
		return false;
	}
	// 右边树判断，节点不存在才能添加
	if("deviceTree1_auto" == treeId1){
		var treeObj1 = $.fn.zTree.getZTreeObj(treeId2);
		var nodeArr1 = treeObj1.getNodes();
		if (nodeArr1.length > 0) {
			showError(i18nShow('tip_my_req_recycle_mutilselect_right'));
			return false;
		}
	}
	var confirmResult = false; // 操作人员对弹出的确认框的点击结果
	var flag = true; // 是否需要弹出确认框来让用户确认标示
	var duNames = "";
	for ( var i = 0; i < nodeArr.length; i++) {
		if (nodeArr[i].id.indexOf("DU:") == 0) {
			var deviceArr = treeObj.getNodesByParam("upId", nodeArr[i].id, nodeArr[i]);
			if (deviceArr.length == 0) {
				duNames += "," + nodeArr[i].name;
				treeObj.checkNode(nodeArr[i], false, true);
			}
			continue;
		}
		// 验证设备类型 10111是虚拟机类型 并且验证高可用类型是否是RAC或HA
		if (nodeArr[i].params.deviceTypeCode == '10111' && (nodeArr[i].params.haTypeCode == 'HA' || nodeArr[i].params.haTypeCode == 'RAC')) {
			var subArr = treeObj.getNodesByParam("upId", nodeArr[i].upId, null);
			for ( var j = 0; j < subArr.length; j++) {
				if (!subArr[j].checked) {
					if (!confirmResult && flag) {
						confirmResult = showTip(i18nShow('tip_my_req_recycle_msg'));
						flag = false; // 已经确认后将无需再让用户进行确认
					}
					if (confirmResult) {
						subArr[j].checked = true;
					} else {
						return confirmResult;
					}
				}
			}
		}
	}
	if (duNames.length > 0) {
		showError(duNames.substring(1) + i18nShow('tip_my_req_recycle_no_vm'));
	}
	return confirmResult || flag;
}
// 将treeId1树上选择的节点移动到treeId2树上
function setSelectNode(treeId1, treeId2) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId1);
	if (checkSelectNode(treeObj,treeId1,treeId2)) { // 在验证过程中，通过验证则进行移动树节点的操作
		var nodeArr = treeObj.getCheckedNodes(true);
		for ( var i = 0; i < nodeArr.length; i++) {
			if (nodeArr[i].id.indexOf("DU:") < 0) {
				moveTreeNode(treeId2, treeId1, nodeArr[i].id, false);
			}
		}
	}
}
// 将treeId2树种的节点nodeId移动到treeId1树中
function moveTreeNode(treeId1, treeId2, nodeId, isSilent) {
	var treeObj1 = $.fn.zTree.getZTreeObj(treeId1);
	var treeObj2 = $.fn.zTree.getZTreeObj(treeId2);
	var nodeObj = treeObj2.getNodeByParam("id", nodeId, null);
	var obj = treeObj1.getNodeByParam("id", nodeId, null);
	var upId = nodeObj.upId;
	if (!obj) { // 当此节点不存在此树中才进行添加
		var parentObj = addParentNode(treeObj1, treeObj2, upId, isSilent);
		//确保可向parentNode下添加子节点。
		parentObj.isParent = true;
		nodeObj.checked = false;
		treeObj1.addNodes(parentObj, nodeObj, isSilent);
	}
	removeNode(treeObj2, nodeObj);
}
// 递归添加上级节点
function addParentNode(treeObj1, treeObj2, upId, isSilent) {
	if (!upId) {
		return null;
	}
	var parentObj = treeObj1.getNodeByParam("id", upId, null);
	if (!parentObj) {
		var obj = treeObj2.getNodeByParam("id", upId, null);
		parentObj = addParentNode(treeObj1, treeObj2, obj.upId);
		var node = {
			id : obj.id,
			upId : obj.upId,
			name : obj.name,
			title : obj.name,
			icon: "/icms-web/css/zTreeStyle/img/icons/appsys.png",
			open : true,
			nocheck : (obj.id.indexOf("DU:") == 0) ? false : true
		};
		treeObj1.addNodes(parentObj, node, isSilent);
		parentObj = treeObj1.getNodeByParam("id", obj.id, null);
	}
	return parentObj;
}
// 递归移除上级节点
function removeNode(treeObj, nodeObj) {
	var upId = nodeObj.upId;
	treeObj.removeNode(nodeObj);
	var nodeArr = treeObj.getNodesByParam("upId", upId);
	if (nodeArr && nodeArr.length == 0) {
		var parentNode = treeObj.getNodeByParam("id", upId, null);
		if (parentNode) {
			removeNode(treeObj, parentNode);
		}
	}
}
// 将选择好回收的虚拟机加入列表
function selectRecycleDevice() {
	$("#recycle_select_div").dialog("close");
	$("#selectDeviceTable").clearGridData();
	var treeObj = $.fn.zTree.getZTreeObj("deviceTree2");
	var nodeArr = treeObj.transformToArray(treeObj.getNodes());
	var n = 0;
	for ( var i = 0; i < nodeArr.length; i++) {
		if (nodeArr[i].isParent) {
			continue;
		}
		var duObj = treeObj.getNodeByParam("id", nodeArr[i].upId, null);
		var cpu = nodeArr[i].params.vmCpu;
		var mem = nodeArr[i].params.vmMem;
		var disk = nodeArr[i].params.vmDisk;
		var parameter_def = "cpu=" + cpu + " "+i18nShow('my_req_sr_memery')+"=" + mem + "M "+i18nShow('my_req_sr_disk')+"=" + disk + "G";
		var deviceModel = cpu + "," + mem + "," + disk;
		var nodeObj = {
			duId : duObj.id,
			deviceId : nodeArr[i].id,
			deviceTypeCode : nodeArr[i].params.deviceTypeCode,
			haTypeCode : nodeArr[i].params.haTypeCode,
			du : duObj.name,
			device : nodeArr[i].name,
			deviceParamDef : parameter_def,
			deviceModel : deviceModel,
			deviceIps : nodeArr[i].title.replace(/\n/g, ', ').replace('生产IP：, ', '')
		};
		$("#selectDeviceTable").jqGrid("addRowData", n++, nodeObj);
	}
}
function submitRecycleDevice() {
	var deviceArr = getSelectedDeviceArr();
	if (deviceArr.length == 0) {
		showError(i18nShow('tip_my_req_recycle_msg1'));
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
		"creatorId" : $("#userId").val()
	};
	var virtualRecycleVo = {
		"bmSr" : bmSr,
		"cmVmList" : deviceArr
	};

	$("#recycle_submit_btn").attr("disabled", "disabled");
	$("#recycle_submit_btn").removeClass("btn").addClass('btn_disabled');
	if (actionType == 'new') {
		showTip("load");
		$.post(ctx + "/request/recycle/saveVirtualRecycle.action", {
			jsonData : JSON.stringify(virtualRecycleVo),
			jsonClass : "com.git.cloud.request.model.vo.VirtualRecycleVo"
		}, function(data) {
			if (data.result != null && data.result == "success") {
				showTip(i18nShow('tip_my_req_recycle_msg3')+"：" + data.message, function() {
					closeTip();
					initCloudRequestList();
					$("#recycle_submit_btn").attr("disabled", true);
					$("#recycle_submit_btn").removeClass("btn_disabled").addClass('btn');
				});
			} else {
				showError(i18nShow('tip_save_fail') + data.result);
				$("#recycle_submit_btn").attr("disabled", true);
				$("#recycle_submit_btn").removeClass("btn_disabled").addClass('btn');
			}
		}).error(function() {
			showError(i18nShow('tip_error'), null, "red");
			$("#recycle_submit_btn").attr("disabled", true);
			$("#recycle_submit_btn").removeClass("btn_disabled").addClass('btn');
		});
	} else if (actionType == 'update') {
		showTip("load");
		$.post(ctx + "/request/recycle/saveVirtualRecycle.action", {
			jsonData : JSON.stringify(virtualRecycleVo),
			jsonClass : "com.git.cloud.request.model.vo.VirtualRecycleVo",
			todoId : todoId
		}, function(data) {
			if (data.result != null && data.result == "success") {
				showTip(i18nShow('tip_op_success'), function() {
					history.go(-1);
				});
			} else {
				showError(i18nShow('tip_save_fail') + data.result);
				$("#recycle_submit_btn").attr("disabled", true);
				$("#recycle_submit_btn").removeClass("btn_disabled").addClass('btn');
			}
		}).error(function() {
			showError(i18nShow('tip_error'), null, "red");
			$("#recycle_submit_btn").attr("disabled", true);
			$("#recycle_submit_btn").removeClass("btn_disabled").addClass('btn');
		});
	}
}