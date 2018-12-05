var actionType = "";
var attrListMap = new Object(); //属性值临时保存
var serviceObjMapList = new Array();
var duList = new Object();
var delRrinfoIdStr = "";//删除资源ID临时存放变量
var editclass='0';//参数修改类别，0 全部修改  1=计算资源参数    3=存储参数修改
var editType='all'; // all 表示全部修改
var appChanged = false;
var datacenterChanged = false;
var initFlag = false;
var isHiddenFlag = false;
var attrNeededFlag = true;  //是否需要显示属性
var checkFlag = "";
var cloud_view_btn = {
	edit : false,
	add : false,
	del : false,
	search : false,
	refresh : false
};

function init() {
	
	var userId = $("#userId").val();
	$.ajax({
		type : 'post',
		datatype : "json",
		url : ctx + "/sys/user/findUserRoleResult.action",
		async : true,
		data : {
			"userId" :userId
		},
		success : (function(data) {
			for(var i=0 ; i<data.length ; i++) {
				if(data[i].cname !="") {
					$("#appId_list").append('<option value="' + data[i].appId + '">' + data[i].cname + '</option>');
					$("#appId").append('<option value="' + data[i].appId + '">' + data[i].cname + '</option>');
				}
			}
		}),
		error : function() {
		}
	});
	
	$("#btn_reset").on("click",function(){
		document.forms[0].reset();
	});//触发reset按钮
	//自适应宽度
	$("#gridTable").setGridWidth($("#div_list1").width());
	$(window).resize(function() {
		$("#gridTable").setGridWidth($("#div_list1").width());
		$("#gridTable").setGridHeight(heightTotal()+ 45);
    });
	//如果是从别的页面跳转到此页面
	if(actType !="" && actType == "update"){
		initAttrTable2();
		initApproveResult();
		$("#attrTable").setGridWidth($("#div_list1").width()-200);
		update(srId,srCode,todoId);
	}else{
		initCloudRequestList();
	}
	//$("#srId").val(srId);
	//申请类型下拉值改变时触发事件
	$("#srTypeMark").change(function(){
		applyTypeChanged();
	});	
	//应用系统下拉值改变时触发事件
	$("#appId").change(function(){
		changeApp();
	});	
	//数据中心下拉值改变时触发事件
	$("#datacenterId").change(function(){
		changeDatacenter();
	});

	$("#requestForm").validate({
		rules: {
			appId: "required",
			datacenterId: "required",
			srTypeMark : "required"
			//summary : "required"
			//describe : "required"
		},
		messages: {
			appId: i18nShow('validate_app'),
			datacenterId: i18nShow('validate_datacenter'),
			srTypeMark : i18nShow('validate_sr_type')
			//summary:"概要不能为空"
			//describe:"详细描述不能为空"
			
		},
		submitHandler: function() {
			var srTypeMark = $("#srTypeMark").val();
			if(checkFlag == "first"){
				//点击第一个选择
				if(srTypeMark == "VS" || srTypeMark == "PS") { // 虚拟机供给,物理机供给
					var _start = $("#serviceBeginTimes").val();
					var _end = $("#serviceEndtimes").val();
					if(_start > _end) {
						showTip(i18nShow('validate_sr_time'));
						return false;
					} else {
						addAppDuRequest();
					}
				} else if(srTypeMark == "VE") { // 虚拟机扩容
					extendAppDuRequest();
				}else if(srTypeMark == "VR" || srTypeMark == 'PR') { // 虚拟机回收 和 物理机回收
					selectDevice();
				}else if(srTypeMark == "SA") { //服务自动化
					selectVmDevice();
				}
			}else if(checkFlag =="second"){
				if((srTypeMark == "VS") || (srTypeMark == 'PS')) { // 虚拟机供给
					var _start = $("#serviceBeginTimes").val();
					var _end = $("#serviceEndtimes").val();
					if(_start > _end) {
						showTip(i18nShow('validate_sr_time'));
						return false;
					} else {
						submitRequest();
					}
				} else if(srTypeMark == "VE") { // 虚拟机扩容
					submitExtendRequest();
				}else if(srTypeMark == "VR" || srTypeMark == 'PR') { // 虚拟机回收 和物理机回收
					submitRecycleDevice();
				}else if(srTypeMark == "SA"){ //服务自动化
					submitServiceAutoRequest();
				}					
			}

		}
	});
}

function initApproveResult() {
	$.post(ctx + '/request/base/findApproveResult.action', {srId:srId}, function (data) {
		for(var i=0 ; i<data.length ; i++) {
			if(data[i].approveResult == "0") {
				$("#approveP").css("display", "block");
				$("#approveResult").val(data[i].approveRemark);
				break;
			}
		}
	});
}

function setPageRight(){
	if(editType=='other'){
  	  $("#service-add-btn").hide();
	  $("#selectAppBtn").attr('onClick', '');
	}
}

// 取消服务请求
function cancelRequest() {
	showTip(i18nShow('my_req_cancal_sr'),function(){
		$("#list").show();
		$("#nav").hide();
		$("#extend_main_div").hide();
		$("#supply_main_div").hide();
		$("#recycle_main_div").hide();
		$("#serviceAuto_main_div").hide();
	});
}

// 查看服务请求
function showRequest(srId) {
	showRequestInfo(srId);
	
	setFormReadonly("requestForm", true, false);
	$("#serviceBeginTimes").attr('onClick', '');
	$("#serviceEndtimes").attr('onClick', '');
}

//查看扩容服务请求
function showExtendRequest(srId) {

	showExtendRequestInfo(srId);
	setFormReadonly("requestForm", true, false);
	$("#serviceBeginTimes").attr('onClick', '');
	$("#serviceEndtimes").attr('onClick', '');
}

function getLocalTime(obj) {
	if(obj) {
 		return obj.length > 10 ? obj.substr(0, 10) : obj;
	}else {
		return "";
	}
}

function initPageInfo() {
	if(srId && srId != '') {
		// 获取服务申请详细信息
		$.post(ctx + '/request/base/getBmSrVo.action', { "srId":srId}, function (data) {
			var bmSr = data;
			$("#srId").val(srId);
			$("#srCode").val(bmSr.srCode);
			$("#srTypeMark").val(bmSr.srTypeMark);
			$("#appId").val(bmSr.appId);
			$("#datacenterId").val(bmSr.datacenterId);
			if(bmSr.startTimeStr!=null && bmSr.startTimeStr!="" &&  bmSr.startTimeStr!="null"){
				$("#serviceBeginTimes").val(bmSr.startTimeStr.length >10 ? bmSr.startTimeStr.substring(0,10) : bmSr.startTimeStr);
			}else{
				$("#serviceBeginTimes").val("");
			}
			if(bmSr.endTimeStr!=null && bmSr.endTimeStr!="" &&  bmSr.endTimeStr!="null"){
				$("#serviceEndtimes").val(bmSr.endTimeStr.length >10 ? bmSr.endTimeStr.substring(0,10):bmSr.endTimeStr);
			}else{
				$("#serviceEndtimes").val("");
			}			
			$("#summary").val(bmSr.summary);
			//$("#describe").val(bmSr.remark);
			if(actionType == 'detail'||actionType == 'view'){
				setFormReadonly("requestForm", true, false);
				//setFormReadClass();
			}
			//根据申请类型确定显示内容
			applyTypeChanged();
		});
	} else {
		applyTypeChanged();
	}
}

function resTypeChanged() {
	if(serviceObjMapList.length > 0 && showTip(i18nShow('my_req_change_sr'))){
		serviceObjMapList.splice(0,serviceObjMapList.length);
        duList =new Object();
        $("#serviceAppliedTable").clearGridData();
	}
    var srResourceTypeCode = $("#srResourceTypeCode").val();
    jqGridReload("catalogTable", "getServiceListSrv", {"srResourceTypeCode" : srResourceTypeCode});
}

function applyTypeChanged() {
	// 需求尚未定义
	var srType = $("#srTypeMark").val();
	hideAllMainDiv();
	if(srType == 'VS' || srType == 'PS') { // 供给
		$("#serviceSelectTime").show();
		$("#small_title").html(i18nShow('my_req_service_supply'));
		//申请的云服务汇总表初始化表头
		$("#supply_main_div").show();
		$("#serviceAppliedTable").setGridWidth($("#div_list2").width());
		ServiceAppliedTableInit();
		//初始化参数表格（查看/修改服务请求需要先初始化）
		//attrTableInit();
		if(actionType == 'update' && !initFlag) {
			document.getElementById("srTypeMark").disabled = true;
			$("#srId").val(srId);
			updateRequest(srId);
			initFlag = true;
		} else if((actionType == 'detail'||actionType == 'view') && !initFlag) {
			isHiddenFlag = true;
			$("#service-add-btn").css("visibility","hidden");
			$("#supply_btnbar_div").css("visibility","hidden");
			$("#submit_btn").css("visibility","hidden");
			$("#srId").val(srId);
			showRequest(srId);
			initFlag = true;
		}
		
	} else if (srType == 'VE') { // 扩容
		$("#serviceSelectTime").hide();
		$("#extend_main_div").show();
		$("#small_title").html(i18nShow('my_req_service_expand'));
		$("#serviceExtendedTable").setGridWidth($("#div_list3").width());
		ServiceExtendedTableInit();
		if(actionType == 'update' && !initFlag) {
			$("#srId").val(srId);
			updateExtendRequest(srId);
			initFlag = true;
		} else if((actionType == 'detail'||actionType == 'view') && !initFlag) {
			// 隐藏操作按钮
			$("#service-extend-btn").css("visibility","hidden");
			$("#extend_submit_btn").css("visibility","hidden");
			$("#extend_cancel_btn").css("visibility","hidden");
			$("#srId").val(srId);
			showExtendRequest(srId);
			initFlag = true;
		}
	} else if (srType == 'VR' || srType == 'PR') { // 回收
		$("#recycle_delete_btn").hide(); // 隐藏关单按钮
		$("#recycle_goback_btn").hide(); // 隐藏返回按钮
		$("#serviceSelectTime").hide();
		$("#recycle_main_div").show();
		$("#small_title").html(i18nShow('my_req_service_recovery'));
		// 回收的虚拟机列表初始化表头
		$("#selectDeviceTable").setGridWidth($("#div_list4").width());
		initDeviceList();
		
	}else if(srType == 'SA'){
		$("#serviceSelectTime").show();
		$("#small_title").html(i18nShow('my_req_service_auto'));
		$("#serviceAuto_main_div").show();
		$("#serviceSelectTime").hide();
		$("#serviceAutoTable").setGridWidth($("#serAuto_div1").width());
		initServiceAutoMain();
	} else { // 负载均衡
	}
}
function changeServiceType(){
	var srTypeMark = $("#srTypeMark").val();
	hideAllMainDiv();
	if(srTypeMark == 'VS' || srTypeMark == 'PS'){//供给
		$("#supply_main_div").show();
	}else if (srTypeMark == 'VE'){//扩容
		$("#extend_main_div").show();
		ServiceExtendedTableInit();
	}else if (srTypeMark == 'VR' || srTypeMark == 'PR'){//回收
		$("#recycle_delete_btn").hide(); // 隐藏关单按钮
		$("#recycle_goback_btn").hide(); // 隐藏返回按钮
		// 回收的虚拟机列表初始化表头
		$("#recycle_main_div").show();
		initDeviceList();
	}
}

function changeApp(){
	var srTypeMark = $("#srTypeMark").val();
	var rrinfoId = '';
	if(serviceObjMapList.length > 0 ) {
		showTip(i18nShow('my_req_change_app'),function(){
		    
		    //根据rrinfoId删除资源
		    for(var i=0;i<serviceObjMapList.length;i++){
			  //rrinfoId = rrinfoId + "," + serviceObjMapList[i].rrinfoId;
			  if(serviceObjMapList[i].rrinfoId!="" &&  serviceObjMapList[i].rrinfoId != "undefined" && typeof(serviceObjMapList[i].rrinfoId) != "undefined" && delRrinfoIdStr.indexOf(serviceObjMapList[i].rrinfoId) == -1){
		        delRrinfoIdStr = delRrinfoIdStr + "," + serviceObjMapList[i].rrinfoId;
		      }
			}
			 /**rrinfoId = rrinfoId.substring(1,rrinfoId.length);
			$.post(ctx+"/request/supply/deleteRrinfoByIds.action", {rrinfoIds:rrinfoId}, function (data) {
		    });*/
			serviceObjMapList.splice(0,serviceObjMapList.length);
	           duList =new Object();
	        $("#serviceAppliedTable").clearGridData();	
		});
	}
	//扩容
	if(extendServiceObjMapList.length > 0 ) {
		showTip(i18nShow('my_req_change_app'),function(){
		    //根据rrinfoId删除资源
		    for(var i=0;i<extendServiceObjMapList.length;i++){
			  //rrinfoId = rrinfoId + "," + extendServiceObjMapList[i].rrinfoId;
			  if(extendServiceObjMapList[i].rrinfoId!="" &&  extendServiceObjMapList[i].rrinfoId != "undefined" && typeof(extendServiceObjMapList[i].rrinfoId) != "undefined" && delRrinfoIdStr.indexOf(extendServiceObjMapList[i].rrinfoId) == -1){
		        delRrinfoIdStr = delRrinfoIdStr + "," + extendServiceObjMapList[i].rrinfoId;
		      }
			}
		    /**rrinfoId = rrinfoId.substring(1,rrinfoId.length);
			$.post(ctx+"/request/supply/deleteRrinfoByIds.action", {rrinfoIds:rrinfoId}, function (data) {
		    });	*/	
		
			extendServiceObjMapList.splice(0,extendServiceObjMapList.length);
	           duList =new Object();
	         $("#serviceExtendedTable").clearGridData();			
		});
	}
}
function changeDatacenter(){
    var rrinfoId = '';
	if(serviceObjMapList.length > 0 ) {
		showTip(i18nShow('my_req_change_datacenter'),function(){
		    //根据rrinfoId删除资源
		    for(var i=0;i<serviceObjMapList.length;i++){
			  //rrinfoId = rrinfoId + "," + serviceObjMapList[i].rrinfoId;
			  if(serviceObjMapList[i].rrinfoId!="" &&  serviceObjMapList[i].rrinfoId != "undefined" && typeof(serviceObjMapList[i].rrinfoId) != "undefined" && delRrinfoIdStr.indexOf(serviceObjMapList[i].rrinfoId) == -1){
		        delRrinfoIdStr = delRrinfoIdStr + "," + serviceObjMapList[i].rrinfoId;
		      }
			}
			/**rrinfoId = rrinfoId.substring(1,rrinfoId.length);
			$.post(ctx+"/request/supply/deleteRrinfoByIds.action", {rrinfoIds:rrinfoId}, function (data) {
		    });		*/
			serviceObjMapList.splice(0,serviceObjMapList.length);
	           duList =new Object();
	     $("#serviceAppliedTable").clearGridData();			
		});
	}	
	//扩容
	if(extendServiceObjMapList.length > 0 ) {
		showTip(i18nShow('my_req_change_datacenter'),function(){
		//根据rrinfoId删除资源
	    for(var i=0;i<extendServiceObjMapList.length;i++){
		  //rrinfoId = rrinfoId + "," + extendServiceObjMapList[i].rrinfoId;
		  if(extendServiceObjMapList[i].rrinfoId!="" &&  extendServiceObjMapList[i].rrinfoId != "undefined" && typeof(extendServiceObjMapList[i].rrinfoId) != "undefined" && delRrinfoIdStr.indexOf(extendServiceObjMapList[i].rrinfoId) == -1){
		     delRrinfoIdStr = delRrinfoIdStr + "," + extendServiceObjMapList[i].rrinfoId;
		  }
		}
		
		 /** rrinfoId = rrinfoId.substring(1,rrinfoId.length);
		$.post(ctx+"/request/supply/deleteRrinfoByIds.action", {rrinfoIds:rrinfoId}, function (data) {
	    });	*/	
		
		extendServiceObjMapList.splice(0,extendServiceObjMapList.length);
	           duList =new Object();
	    $("#serviceExtendedTable").clearGridData();			
		});
	}
}

function hideAllMainDiv() {
	$("#recycle_main_div").hide();
	$("#supply_main_div").hide();
	$("#extend_main_div").hide();
	$("#fzjh_main_div").hide();
	$("#serviceAuto_main_div").hide();
}
function appSelectChange() {
	appChanged = true;
	clearTreeId();
	$.fn.zTree.destroy("deviceTree1");
	$.fn.zTree.destroy("deviceTree2");
}
function datacenterSelectChange() {
	datacenterChanged = true;
	clearTreeId();
	$.fn.zTree.destroy("deviceTree1");
	$.fn.zTree.destroy("deviceTree2");
}
//删除服务请求
function deleteRequest() {
	showTip(i18nShow('my_req_delete_sr')+srCode+"?", function () {
		var srid = srId;
		var todoid=todoId;
		$("#delete_btn").attr("disabled", "disabled");
		$("#extend_delete_btn").attr("disabled", "disabled");
		$("#recycle_delete_btn").attr("disabled", "disabled");
		$.post(ctx+"/request/base/deleteRequest.action", {
			 'srId' : srid,
			 'todoId':todoid
		}, function(data) {
			if (data.result == 'success') {
				showTip(i18nShow('my_req_close_success'),function(){
					history.go(-1);
				});
			} else {
				showTip(i18nShow('my_req_close_fail')+data.result);
				$("#delete_btn").attr("disabled", false);
				$("#extend_delete_btn").attr("disabled", false);
				$("#recycle_delete_btn").attr("disabled", false);
			} 
		});
	});
}
function validateBaseInfo() {
	$("#requestForm").submit(); 
}

/**
 * 设置表单状态为只读或者可编辑
 * 
 * @param formId
 *            表单Id
 * @param readOnly
 *            true:只读，false：可编辑
 * @param hideBtn
 *            是否隐藏表单的操作按钮,true:隐藏，false:不隐藏
 */
function setFormReadonly(formId, readOnly, hideBtn) {

	var _formId = "#" + formId;

	// 需要设置为只读或者可编辑的表单元素集合
	var inputs = $(_formId + " input");// input输入框
	var radios = $(_formId + " input:radio");// radio输入框
	var checkboxs = $(_formId + " input:checkbox");// checkbox输入框
	var textareas = $(_formId + " textarea");// 文本域
	var selects = $(_formId + " select");// 下拉选择

	// 设置表单元素为，同时更改表单样式
	if (readOnly) {
		inputs.each(function(i) {
			$(this).attr("readonly", "readonly").addClass("readonly");
		});

		textareas.each(function(i) {
			$(this).attr("readonly", "readonly").addClass("readonly");
		});

		selects.each(function(i) {
			$(this).attr("disabled", "disabled");
		});

		radios.each(function(i) {
			$(this).attr("disabled", "disabled");
		});

		checkboxs.each(function(i) {
			$(this).attr("disabled", "disabled");
		});

		if (typeof hideBtn == "undefined")
			hideBtn = true;

		if (hideBtn) {
			$(_formId + " .form_table_bottom").css("display", "none");
			$(".form_btn_bar").css("display", "none");
		}

	} else {

		inputs = $(_formId + " input.readonly");
		textareas = $(_formId + " textarea.readonly");

		inputs.each(function(i) {
			$(this).removeAttr("readonly").removeClass("readonly");

		});

		textareas.each(function(i) {
			$(this).removeAttr("readonly").removeClass("readonly");
		});

		selects.each(function(i) {
			$(this).attr("disabled", false);
		});

		radios.each(function(i) {
			$(this).attr("disabled", false);
		});

		checkboxs.each(function(i) {
			$(this).attr("disabled", false);
		});
		$(_formId + " .form_table_bottom").css("display", "block");
		$(" .form_btn_bar").css("display", "block");
	}
}
//修改服务请求
function updateRequest(srId) {
	  if(actionType=='update'){
		  $("#submit_btn").val(i18nShow('my_req_sr_resubmit'));
		  $("#delete_btn").show();
		  $("#goback_btn").show();
		  $("#cancel_btn").hide();
	    }
	  showRequestInfo(srId);
}

//修改扩容服务请求
function updateExtendRequest(srId) {
	  if(actionType=='update'){
		  $("#extend_submit_btn").val(i18nShow('my_req_sr_resubmit'));
		  $("#extend_delete_btn").show();
		  $("#extend_goback_btn").show();
		  $("#extend_cancel_btn").hide();
	    }
	  showExtendRequestInfo(srId);
}

function showRequestInfo(srId){
	// 获取服务申请详细信息
	$.post(ctx + '/request/supply/findVirtualSupplyById.action', { "srId":srId}, function (data) {
	var rrinfoList = data.rrinfoList;
	serviceObjMapList = rrinfoList;
	for(var j = 0; j < rrinfoList.length; j++){
		var parametersJson = rrinfoList[j].parametersJson;	
		cellValue = JSON.parse(parametersJson);		
		var cpu = cellValue.cpu;			
		var mem = cellValue.mem;
		var sysdisk = cellValue.sysDisk;
		var vm_num = cellValue.vmNum;
		var duid = rrinfoList[j].duId;
		var parameter_def = "cpu=" + cpu + " "+i18nShow('my_req_sr_memery')+"=" + mem + "M "+i18nShow('my_req_sr_disk')+"=" + sysdisk + "G";
		var parameterStr = cpu+","+mem+","+sysdisk+","+vm_num;
		var jsobj = {
				rrinfoId : rrinfoList[j].rrinfoId,
				duId : rrinfoList[j].duId,
				serviceId : rrinfoList[j].serviceId,
				duName : rrinfoList[j].duName,
				duEname : rrinfoList[j].duEname,
				serviceName : rrinfoList[j].serviceName,
				serviceSetName : rrinfoList[j].serviceSetName,
				parameterSelfDef : parameter_def,
				parameterStr : parameterStr,
				serviceNum : rrinfoList[j].serviceNum,
				vmNum : vm_num

			};
			//给属性赋值		
			attrListMap[duid] =rrinfoList[j].attrValList;
			// 往参数表中插入记录
			$("#serviceAppliedTable").jqGrid("addRowData", j, jsobj);
	}
	
	});	
	$("serviceAppliedTable").trigger("reloadGrid");
}

//创建时间的方法
function formatTime(ns){
	if(ns){
		var date = new Date(ns);
		
		Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		
       return Y+M+D+h+m+s;
	}
}

function GoBackToMain(){
	$("#select_du_div").fadeOut(function(){
		$("#step_s").hide();
		$("#step").hide();
		$("#step_auto").hide();
		$("#div_baseCase").show();
		$("#supply_main_div").show();
	});
}

function GoBackToMain1(){
	$("#select_extendDu_div").fadeOut(function(){
		$("#step_s").hide();
		$("#step").hide();
		$("#step_auto").hide();
		$("#div_baseCase").slideDown('slow');
		$("#extend_main_div").slideDown('slow');
		$("#service-extend-div").slideDown('slow');		
	});
}

function BackToList(){
	$("#step").hide();//默认不显示向导导航菜单step
	$("#step_s").hide();
	$("#step_auto").hide();
	hideAllMainDiv();
	$("#nav").hide();
	$("#list").show();

}

var _width = 0;
function initCloudRequestList() {
	$("#step").hide();//默认不显示向导导航菜单step
	$("#step_s").hide();
	$("#step_auto").hide();
	hideAllMainDiv();
	$("#nav").hide();
	$("#list").show();
	serviceObjMapList.splice(0,serviceObjMapList.length);
	extendServiceObjMapList.splice(0,extendServiceObjMapList.length);
	$("#gridTable").jqGrid().GridUnload("gridTable");	
	$("#gridTable").jqGrid({
		url : ctx + "/request/base/getCloudReqeustList.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height :heightTotal()+ 45,
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : "srCode",
			index : "s.sr_code",
			label : i18nShow('my_req_sr_code'),
			width : 120,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rewObject){
				return '<a href="javascript:;" style=" text-decoration:none" onclick="view(\''+rewObject.srId+'\',\''+rewObject.srCode+'\')">'+rewObject.srCode+'</a>';
            }
		}, {
			name : "appName",
			index : "a.cname",
			label : i18nShow('my_req_appName'),
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : "datacenterName",
			index : "d.datacenter_cname",
			label : i18nShow('my_req_rm_datacenter'),
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "srTypeName",
			index : "st.sr_type_name",
			label : i18nShow('my_req_sr_type'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "srStatus",
			index : "dic.dic_name",
			label : i18nShow('my_req_sr_status'),
			width : 80,
			sortable : true,
			align : 'left',
			formatter: function(cellValue,options,rowObject){
				/*if(rowObject.srStatusCode == "REQUEST_ASSIGN_SUCCESS"){//分配成功
					return '<span class="tip_green">'+cellValue+'</span>';
				}else if(rowObject.srStatusCode == "REQUEST_WAIT_SUBMIT"){//等待提交
					return '<span class="tip_red">'+cellValue+'</span>';
				}else if(rowObject.srStatusCode == "REQUEST_DELETE" || rowObject.srStatusCode == "REQUEST_CLOSED"){//关单和作废
					return '<span class="tip_gray">'+cellValue+'</span>';
				}else{
					return cellValue;//其他
				}*/
				return cellValue;
          }
		}, {
			name : "creator",
			label : i18nShow('my_req_creator'),
			width : 80,
			sortable : false,
			align : 'left'
		}, {
			name : "createTime",
			label : i18nShow('my_req_create_time'),
			width : 120,
			sortable : false,
			align : 'left',
              formatter: function(cellValue,options,rewObject){
					return formatTime(cellValue);
              }
		}, {
			name : "summary",
			label : i18nShow('my_req_summary'),
			width : 160,
			sortable : false,
			align : 'left'
		},{
			name : "srStatusCode",
			index : "s.sr_status_code",
			label : "状态码",
			width : 80,
			sortable : false,
			align : 'left',
			hidden : true
		},
		{
			name:i18nShow('my_req_operate'),
			index:"option",
			sortable:false,
			align:"left",
			formatter:function(cellVall,options,rowObject){
				//return "<input type='button' class='btn_apply_s' title='申请进度' onclick=('"+rowObject.srId+"') />";
				var s = "";
				if(rowObject.srStatusCode == "REQUEST_WAIT_SUBMIT"){
					s += "<a href='javascript:#' style='margin-right: 10px; text-decoration:none;' onclick=resubmitVirtualSupply('"+rowObject.srId+"','"+rowObject.srCode+"')>"+"重新提交"+"</a>";
				}else{
					s += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=lookupWorkflow('"+rowObject.srId+"')>"+i18nShow('my_req_sch')+"</a>";
				}
				return s;
			}
        }],
		viewrecords : true,
		sortname : "s.create_time",
		sortorder : "desc",
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
		loadComplete : function(data) {
			pushPaginationParamToHistory(data,"page","rows")
		},
		page: pagin == null || pagin == undefined ? 1 : pagin.page,
		rowNum : pagin == null || pagin == undefined ? 10 : pagin.rows,
		pager : "#gridPager",
		hidegrid : false
	});
	_width = $("#div_list1").width()+16;
}
function search() {
	jqGridReload("gridTable", {
		"srCode" : $("#srCode_list").val().replace(/(^\s*)|(\s*$)/g, ""),
		"appId" : $("#appId_list").val(),
		"datacenterId" : $("#datacenterId_list").val(),
		"srStatusCode" : $("#srStatusCode").val(),
		"srTypeMark" : $("#srTypeMark_list").val()
	});
}

function add() {
	$("#submit_btn").attr("disabled", false);//每次新建申请时，将按钮置为可用
	$("#list").hide();
	$("#nav").show();
	$("#supply_main_div").show();
	$("#srTypeMark").val('');
	$("#small_title").html(i18nShow('my_req_service_supply'));
	actionType="new";
	$("#service-add-btn").css("visibility","display");
	$("#supply_btnbar_div").css("visibility","display");
	$("#submit_btn").css("visibility","display");
	//把基本信息清空
	resetBaseCase();
	ServiceAppliedTableInit();
}

function view(srid,srcode) {
    window.location.href = ctx+"/request/base/getCloudReqeust.action?pageType=detail&srId="+srid;
}
//重新提交服务请求
function resubmitVirtualSupply(srId,srCode){
	var url= ctx+"/request/supply/resubmitVirtualSupply.action";
	showTip("确定重新提交申请", function(){
		showTip("load");
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,
			data : {srId:srId,srCode:srCode},
			error : function() {  // 请求失败处理函数
				closeTip();
				showError("提交失败");
			},
			success : function(data) {
				closeTip();
				if(data.result!=null && data.result=="success"){
					closeTip();
					showTip(i18nShow('my_req_save_successTip') + data.message);
					search()
				}else{
					closeTip();
					showTip(data.result);
				}
			}
		});
  });
}


function update(srid,srcode,todoid) {
	$("#list").hide();
	$("#step").hide();//默认不显示向导导航菜单step
	$("#step_s").hide();
	$("#step_auto").hide();
	$("#nav").show();
	actionType="update";
	srId = srid;
	srCode = srcode;
	todoId = todoid;
	initPageInfo();
}
function affirmRequest(srId) {
	window.open(ctx+"/request/base/getCloudReqeust.action?pageType=affirm&srId="+srId);
}
function lookupWorkflow(srId) {
	$.post(ctx+"/request/base/findInstanceIdBySrId.action", {srId:srId}, function(data) {
		window.location.href = ctx+"/pages/tankflowNew/processInstance.jsp?state=view&instanceId="+data.result;
	});
}
function resetBaseCase(){
	$("#srCode").val("");
	$("#srTypeMark").val("");
	$("#appId").val("");
	$("#datacenterId").val("");
	$("#serviceBeginTimes").val("");
	$("#serviceEndtimes").val("");
	$("#summary").val("");
}
function setFormReadClass(){
	$("#srCode").removeClass("selInput").addClass('readonly');
	$("#srTypeMark").removeClass("selInput").addClass('readonly');
	$("#appId").removeClass("selInput").addClass('readonly');
	$("#datacenterId").removeClass("selInput").addClass('readonly');
	$("#serviceBeginTimes").removeClass("textInput").addClass('readonly');
	$("#serviceEndtimes").removeClass("textInput").addClass('readonly');
	$("#summary").removeClass("textInput").addClass('readonly');
}