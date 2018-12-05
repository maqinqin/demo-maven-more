function init() {
	var queryData = {};
	$("#gridTable").jqGrid({
		url : ctx + "/cloud-service/servicedef/search.action",
		rownumbers : true,
		datatype : "json",
		postData : queryData,
		mtype : "post",
		height : heightTotal() + 40,
		autowidth : true,
		colNames : [ 'serviceId', i18nShow('cloud_service_name'), '服务类型CODE',i18nShow('cloud_service_type'), '高可用类型CODE', '平台类型CODE',i18nShow('cloud_service_platform'), i18nShow('cloud_service_vm_type'),i18nShow('cloud_service_ha_type'), /*i18nShow('cloud_service_host_type'),*/ i18nShow('cloud_service_status'), i18nShow('cloud_service_operate') ],
		colModel : [ {
			name : "serviceId",
			index : "serviceId",
			sortable : true,
			align : 'left',
			hidden : true
		}, {
			name : "serviceName",
			index : "serviceName",
			sortable : false,
			width: 145,
			align : 'left',
			formatter : function(cellValue,options,rowObject) {
				return '<a href="javascript:#" style=" text-decoration:none" onclick="viewDef(\''+rowObject.serviceId+'\')">'+rowObject.serviceName+'</a>';
			}
		}, {
			name : "serviceType",
			index : "serviceType",
			sortable : false,
			align : 'left',
			hidden : true
		}, {
			name : "serviceTypeName",
			index : "serviceTypeName",
			sortable : false,
			width: 75,
			align : 'left'	
		}, {
			name : "haType",
			index : "haType",
			sortable : false,
			align : 'left',
			hidden : true
		}, {
			name : "platformType",
			index : "platformType",
			sortable : false,
			align : 'left',
			hidden : true
		},  {
			name : "platformTypeName",
			index : "platformTypeName",
			sortable : false,
			align : 'left',//SERVER'STORAGE
			width: 85,
			
			formatter:function(cellVall,options,rowObject){
				if(rowObject.serviceType=='SERVER' || rowObject.serviceType=='SERVERAUTO'){//<span class="tip_green">已激活</span>
//					cellVall="启用";
					return cellVall;
				}else{
//					cellVall="禁用";
					return rowObject.systemTypeName;
				}
				return cellVall;
			}
		},{
			name : "vmTypeName",
			index : "vmTypeName",
			sortable : false,
			width: 90,
			align : 'left'
		},{
			name : "haTypeName",
			index : "haTypeName",
			sortable : false,
			width: 90,
			align : 'left'
		}, /*{
			name : "hostTypeName",
			index : "hostTypeName",
			sortable : false,
			width: 45,
			align : 'left'
		},*/{
			name : "serviceStatus",
			index : "serviceStatus",
			sortable : false,
			align : 'left',
			width: 50,
			formatter:function(cellVall,options,rowObject){
				if(cellVall=='Y'){//<span class="tip_green">已激活</span>
					return "<span class='tip_green'>"+i18nShow('cloud_service_status_Y')+"</span>";
				}else{
					return "<span class='tip_red'>"+i18nShow('cloud_service_status_N')+"</span>";
				}
				return cellVall;
			}
		}, {
			name : "option",
			index : "option",
			width : 120,
			sortable : false,
			align : "left",
			formatter : function(cellVall, options, rowObject) {
				var updateFlag = $('#updateFlag').val();
				var deleteFlag = $('#deleteFlag').val();
				var startFlag = $('#startFlag').val();
				var stopFlag = $('#stopFlag').val();
				var viewModelFlag = $('#viewModelFlag').val();
				var viewAttrFlag = $('#viewAttrFlag').val();
				var ret = "　　";
				var s = "";
				if (updateFlag){
	                    ret += '<a  style=" margin-right: 10px;margin-left: -25px;text-decoration:none;" href="javascript:#"  title="" onclick=viewService("' + rowObject.serviceId + '") >'+i18nShow('cloud_service_edit')+'</a>' ; 
	               }
				if (rowObject.serviceStatus == 'Y' ){
                        if (stopFlag){
                            ret += '<a  style=" margin-right: 10px;text-decoration:none;" href="javascript:#" title="" onclick=stops("' + rowObject.serviceId + '") >'+i18nShow('cloud_service_stop')+'</a>' ;   
                       }
                  } else {
                        if (startFlag){
                            ret += '<a  style=" margin-right: 10px;text-decoration:none;" href="javascript:#" title="" onclick=starts("' + rowObject.serviceId + '") >'+i18nShow('cloud_service_start')+'</a>' ;
                       }    
                  }
				if(viewModelFlag == "1"){
					s  += "<option value='1' >"+i18nShow('cloud_service_op_model')+"</option>";
				}
				if(deleteFlag == "1"){
					s  += "<option value='2'>"+i18nShow('cloud_service_delete')+"</option >";
				}
				if(viewAttrFlag == "1"){
					s += "<option  value='3'>"+i18nShow('cloud_service_para')+"</option>";
				}
				/*if(viewAttrFlag == "1"){
					s += "<option  value='4'>"+i18nShow('cloud_service_lead')+"</option>";
				}*/
				if(viewModelFlag=="1" || deleteFlag=="1" || viewAttrFlag=="1"){
					ret += "<select onchange=\"selMeched(this,'"+rowObject.serviceId+"')\" style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"+i18nShow('cloud_service_option_defalt')+"</option>'"+s+"'</select>" ;
				}
				return ret;
			}
		} ],
		viewrecords : true,
		sortname : "serviceName",
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
		pager : "#gridPager",
		hidegrid : false
	});	
	
	$(window).resize(function() {
		$("#appdu_Table").setGridWidth($("#supply_div1").width());
    });
	
	$("#serviceForm").validate({
		errorPlacement:function(error, element) {
			if(element.attr("id") == "vmBase"){
				error.appendTo( element.parent().parent() );
			}else{
				error.appendTo( element.parent());
			}
		},	
		rules: {
			serviceName: {required:true,
		        remote:{                
		               type:"POST",
		               url:ctx+"/cloud-service/servicedef/checkCloudServicesAct.action",
		               data:{
		            	   "cloudServicePo.serviceId":function(){return $("#serviceId").val()},"cloudServicePo.serviceName":function(){return $("#serviceName").val();}
		               } 
		              } 
		            },
			serviceStatus: "required",
			cloudTypeCode: {required:false,
		        remote:{                
		               type:"POST",
		               url:ctx+"/cloud-service/servicedef/checkCloudTypeCode.action",
		               data:{
		            	   "cloudServicePo.serviceId":function(){return $("#serviceId").val()},"cloudServicePo.cloudTypeCode":function(){return $("#cloud_type_code").val();}
		               } 
		              } 
		            },
			serviceType: "required",
			haType: "required",
			vmBase:  {
				digits:true ,
				required:true,
				min:1
			},
			platformType: "required",
			imageId: "required",
			vmType:"required",
			hostType:"required",
			systemType: "required",
			storageDataType:"required"
		},
		messages: {
			serviceName: {required:i18nShow('validate_service_name_required'),remote:i18nShow('validate_service_name_remote')},
			serviceStatus: i18nShow('validate_serviceStatus'),
			cloudTypeCode:{remote:i18nShow('validate_cloudTypeCode')},
			serviceType: i18nShow('validate_serviceType'),
			haType: i18nShow('validate_haType'),
			vmBase:  {
				digits:i18nShow('validate_vmBase_digits') ,
				required:i18nShow('validate_vmBase_required'),
				min:i18nShow('validate_vmBase_min')
			},
			platformType: i18nShow('validate_service_platformType'),
			imageId: i18nShow('validate_service_imageId'),
			vmType:i18nShow('validate_service_hostType'),
			hostType:i18nShow('validate_service_hostType'),
			systemType: i18nShow('validate_service_systemType'),
			storageDataType:i18nShow('validate_service_storageDataType')
		},
		submitHandler: function() {
			save();
		}
	});
	$("#serviceType").change(function(){
		initServiceType(this.value,"");
	});
	
	$("#haType").change(function(){
		initvmBase(this.value,"")
	});
	$("#platformType").change(function(){
		initPlatFormType(this.value);
	});
	$("#vmType").change(function(){
		var val = $("#vmType").val();
		if(val == "8"){$("#hostType").val("2");}
		else{$("#hostType").val("1");}
	});
	
	/*$('#isVm').click(function(){
		initIsVm('isVm',null);
	});*/
}
function selMeched(element,serviceId){
	var val = element.value;
	if(val == "1"){
		showOptionModel(serviceId);
	}else if(val == "2"){
		deletes(serviceId);
	}else if(val == "3"){
		showAttribute(serviceId);
	}else if(val == "4"){
		lead(serviceId)
	}
}

/**
 *文件的导出
 * @param serviceId
 * @returns
 */
function lead(serviceId){
	location.href = ctx + "/cloud-service/servicedef/cloudleading.action?serverId="+serviceId;
}


/**
 * 文件的导入
 * @returns
 */
function writeToTxt(){
	$.post(ctx + "/cloud-service/servicedef/wirte.action",function(data) {
		location.reload();
	});
	
}

//设置高可用类型事件
function initvmBase(value,vmBase){
	$("label.error").remove();
	if(value=='SINGLE'){
		$('#vmBase').val('1');
		$("#vmBase").rules("remove");
		$("#vmBase").rules("add",{required:true,	messages: {required: i18nShow('validate_vmBase_required')}} );
		$("#vmBase").attr({"disabled":true});
	}else if(value=='CLUSTER' && (vmBase==null|| vmBase=="")){
		$('#vmBase').val('2');
		$("#vmBase").rules("remove");
		$("#vmBase").rules("add", { required: true,digits:true,min:2, messages: { required: i18nShow('validate_vmBase_required'),digits:i18nShow('validate_vmBase_digits') ,min:i18nShow('validate_vmBase_min_1')} });
		$("#vmBase").attr({"disabled":false});
	}else if(value=='CLUSTER' && (vmBase!=null)){
		$('#vmBase').val(vmBase);
		$("#vmBase").rules("remove");
		$("#vmBase").rules("add", { required: true,digits:true,min:2, messages: { required: i18nShow('validate_vmBase_required'),digits:i18nShow('validate_vmBase_digits') ,min:i18nShow('validate_vmBase_min_1')} });
		$("#vmBase").attr({"disabled":false});
	}else{
		$('#vmBase').val(vmBase);
		$("#vmBase").attr({"disabled":false});
		$("#vmBase").rules("remove");
		$("#vmBase").rules("add", { required: true,digits:true,min:2, messages: { required: i18nShow('validate_vmBase_required'),digits:i18nShow('validate_vmBase_digits') ,min:i18nShow('validate_vmBase_min_1')}});
			
	}
}
//设置服务类型事件
function initServiceType(value,title){
	if(value=='SERVER'){
		$('#storeInfoDiv').hide();
		$('#hostInfoDiv').show();
		$('#imageSpan').show();
		$('#haTypeSpan').show();
		$('#vmSpan').show();
		if(title){
			$('#updateDiv').dialog({ autoOpen : true,title :title,height: 520,width :700,modal : true }); 
		}else{
			$('#updateDiv').dialog({ autoOpen : true,height:520,width : 700,modal : true }); 
		}
		
	}else if(value=='STORAGE'){
		$('#hostInfoDiv').hide();
		$('#imageSpan').hide();
		$('#haTypeSpan').hide();
		$('#storeInfoDiv').show();
		if(title){
			$('#updateDiv').dialog({autoOpen : true, title :title,height: 380,width : 654,modal : true }); 
		}else{
			$('#updateDiv').dialog({autoOpen : true,height: 380,width : 654,modal : true }); 
		}
	}else if (value=='SERVERAUTO'){
		$('#hostInfoDiv').show();
		$('#imageSpan').hide();
		$('#haTypeSpan').hide();
		$('#storeInfoDiv').hide();
		$('#vmSpan').hide();
		if(title){
			$('#updateDiv').dialog({ autoOpen : true,title :title,height: 530,width :700,modal : true }); 
		}else{
			$('#updateDiv').dialog({ autoOpen : true,height: 530,width : 700,modal : true }); 
		}
	}
}

function initPlatFormType(value){
	if(value == "1"){
		$("#hostType_span").css("display","");
		$("#hostType").val("1");
	}else if(value == "4"){
		$("#hostType_span").css("display","");
	}else{
		$("#hostType_span").css("display","none");
	}
	$.ajax({
		type:"post",
		url:ctx+"/cloud-service/servicedef/queryVmType.action",
		async:false,
		datatype : "json",
		data:{"platFormId":value},
		success:(function(data){
			$('#vmType').empty();
			if(data.length>=1){
				$('#vmType').prepend("<option value=''>"+i18nShow('cloud_service_option_defalt')+"...</option>");
			}
			$.each(data,function(index,obj){
				$('#vmType').append("<option value='"+obj.VALUE+"'>"+obj.NAME+"</option>");
			});
		})
	});
}
/**
 * 重新加载云服务定义
 */
function search() {
	var queryData = {
		serviceName : $("#queryServiceName").val().replace(/(^\s*)|(\s*$)/g, ""),
		serviceStatic : $("#queryServiceStatic").val(),
		haType : $("#queryhaType").val(),
		platformType : $("#queryplatformType").val(),
		vmType : $("#queryvmType").val()
	};
	jqGridReload("gridTable", queryData);
}
/**
 * 新增或者修改云服务form提交
 */
function updateOrSave(){
	$("#serviceForm").submit();  
}
/**
 * 新增或者修改云服务
 */
function save() {
	var isVm;
	var serviceType = $("#serviceType").val();
	var haType = $("#haType").val();
	var platformType = $("#platformType").val()
	var imageId=$("#imageId").val();
	var vmBase = $("#vmBase").val();
	var vmType=$("#vmType").val();
	var hostType=$("#hostType").val();
	var systemType =$("#systemType").val();
	var storageDataType=$("#storageDataType").val();
	
	var mehtod = $('#serviceMethod').val('update');
	if(platformType == "1"){
		vmType = "1";
	}else if(platformType == "2"){
		vmType = "4";
	}else if(platformType == "3"){
		vmType = "5";
	}
	if(serviceType=='SERVER'){
		isVm='Y';
		systemType=null;
		storageDataType=null;
	}else if(serviceType=='STORAGE'){
		imageId =null; 
		vmBase=null;
		vmType=null;
		isVm =null;
		platformType=null;
		haType=null;
	}else if(serviceType=='SERVERAUTO'){
		systemType=null;
		storageDataType=null;
		imageId =null; 
		isVm =null;
		haType=null;
	}
	
	$.post(ctx + "/cloud-service/servicedef/save.action", {
		"cloudServicePo.serviceId" : $("#serviceId").val(),
		"cloudServicePo.serviceName" : $("#serviceName").val(),
		"cloudServicePo.managerId" : $("#managerId").val(),
		"cloudServicePo.serviceStatus" : $("#serviceStatus").val(),
		"cloudServicePo.serviceType" : serviceType,
		"cloudServicePo.haType" : haType,
		"cloudServicePo.platformType" : platformType,
		"cloudServicePo.isVm" : isVm,
		"cloudServicePo.imageId" : imageId,
		"cloudServicePo.vmBase" : vmBase,
		"cloudServicePo.vmType" : vmType,
		"cloudServicePo.hostType" : hostType,
		"cloudServicePo.funcRemark" : $("#funcRemark").val(),
		"cloudServicePo.unfuncRemark" : $("#unfuncRemark").val(),
		"cloudServicePo.systemType" : systemType,
		"cloudServicePo.storageDataType" : storageDataType,
		/*"cloudServicePo.cloudType" : $("#cloud_type").val(),*/
		"cloudServicePo.cloudTypeCode" : $("#cloud_type_code").val()
	}, function(data) {
		$("#updateDiv").dialog("close");
		search();
	});
}
function closeView() {
	$("#updateDiv").dialog("close");
}
//查看云服务
function viewDef(serviceId){
	$.post(ctx + "/cloud-service/servicedef/loadAll.action",{"cloudServicePo.serviceId" : serviceId},function(data){
		var serviceType = data.cloudServiceVo.serviceType;
		if(serviceType=='SERVER'){
			$('#viewStoreInfoDiv').hide();
			$('#viewStore').hide();
			$('#viewHost').show();
			$('#viewHostInfoDiv').hide();
			$('#isVmP').show();
			$('#vmSpan2').show();
			$('#imageP').show();
		}else if(serviceType=='STORAGE'){
			$('#viewHostInfoDiv').hide();
			$('#isVmP').hide();
			$('#vmSpan2').hide();
			$('#imageP').hide();
			$('#viewHost').hide();
			$('#viewStore').show();
			$('#viewStoreInfoDiv').show();
			$('#vmSpan').hide();
		}else if(serviceType=='SERVERAUTO'){
			$('#viewStoreInfoDiv').hide();
			$('#viewStore').hide();
			$('#viewHost').show();
			$('#viewHostInfoDiv').show();
			
			$('#isVmP').hide();
			$('#vmSpan2').hide();
			$('#imageP').hide();
			$('#viewHost').hide();
		}else{
			$('#viewHostInfoDiv').hide();
			$('#viewStoreInfoDiv').hide();
			$('#viewHost').hide();
			$('#viewStore').hide();
		}
		
		$("#serviceName2").val(data.cloudServiceVo.serviceName);
		$("#cloudType2").val(data.cloudServiceVo.serviceTypeName);
		$("#cloudTypeCode2").val(data.cloudServiceVo.cloudTypeCode);
		var status=data.cloudServiceVo.serviceStatus;
		if(status=='Y'){
			status = i18nShow('cloud_service_start');
		}else{
			status = i18nShow('cloud_service_stop1');
		}
		$("#serviceStatus2").val(status);
		
		$("#platformType3").val(data.cloudServiceVo.platformTypeName);
        $("#hostType2").val(data.cloudServiceVo.vmTypeName);
		$("#haType2").val(data.cloudServiceVo.haTypeName);
        $("#systemImage2").val(data.cloudServiceVo.imageName);
		$("#platformType2").val(data.cloudServiceVo.platformTypeName);
		if (data.cloudServiceVo.isVm == 'Y'){
			$("#isVm2")[0].checked=true;
		}else{
			$("#isVm2")[0].checked=false;
		}
		
		if($('#isVm2').attr('checked')=="checked"){
			$('#vmSpan2').show();
		}else{
			$('#vmSpan2').hide();
		}
		$("#vmBase2").val(data.cloudServiceVo.vmBase);
		
		$("#vmType2").val(data.cloudServiceVo.vmTypeName);
		$("#funcRemark2").val(data.cloudServiceVo.funcRemark);
		$("#unfuncRemark2").val(data.cloudServiceVo.unfuncRemark);
		$("#imageId2").val(data.cloudServiceVo.imageName);
		$("#systemType2").val(data.cloudServiceVo.systemTypeName);
		$("#storageDataType2").val(data.cloudServiceVo.storageDataTypeName);
        $("#vmBase3").val(data.cloudServiceVo.vmBase);
	});
	$("#viewDiv").dialog({
		autoOpen : true,
		modal : true,
		height : 450,
		width : 500,
		title :i18nShow('cloud_service_info')
	});
}

//新增或者修改云服务
function viewService(serviceId) {
	$("label.error").remove();
	clearTab('updateDiv');
	addSwitch();
	var title;
	emptyValue("serviceName");
	if (serviceId) {
		title = i18nShow('cloud_service_update');
		$.post(ctx + "/cloud-service/servicedef/load.action", {
			"cloudServicePo.serviceId" : serviceId
		}, function(data) {
			if (data.cloudServicePo != null) {
				$("#serviceId").val(data.cloudServicePo.serviceId);
				$("#serviceName").val(data.cloudServicePo.serviceName);
				/*$("#cloud_type").val(data.cloudServicePo.cloudType);*/
				$("#cloud_type_code").val(data.cloudServicePo.cloudTypeCode);
				$("#managerId").val(data.cloudServicePo.managerId);
				
				//flatSelectByValue("serviceStatus",data.cloudServicePo.serviceStatus);
				//初始化服务器状态
				var temp = data.cloudServicePo.serviceStatus;
				$("#serviceStatus").val(temp);
				$("#switchStatus").remove();
				$("#switch_span").append('<span class="switch-off" id="switchStatus"style="top:6px;"></span>');
				if(temp == "Y"){
					$("#switchStatus").removeClass("switch-off").addClass("switch-on");
				}else{
					$("#switchStatus").removeClass("switch-on").addClass("switch-off");
				}
				honeySwitch.init();
				
				flatSelectForUnitVt("serviceType",data.cloudServicePo.serviceType);//设置样式不可选功能
				initServiceType(data.cloudServicePo.serviceType,title);	
				
				$("#haType").val(data.cloudServicePo.haType);
				$("#hostType").val(data.cloudServicePo.hostType);
				initPlatFormType(data.cloudServicePo.platformType);
				$("#platformType").val(data.cloudServicePo.platformType);
				
				
				initvmBase(data.cloudServicePo.haType,data.cloudServicePo.vmBase);
				$("#vmType").val(data.cloudServicePo.vmType);
				$("#funcRemark").val(data.cloudServicePo.funcRemark);
				$("#unfuncRemark").val(data.cloudServicePo.unfuncRemark);
				$("#imageId").val(data.cloudServicePo.imageId);
				$("#serviceIsAvtice").val(data.cloudServicePo.isActive);
				$("#systemType").val(data.cloudServicePo.systemType);
				$("#storageDataType").val(data.cloudServicePo.storageDataType);
			}
		});
		$('#serviceMethod').val('update');
	}else{
		title = i18nShow('cloud_service_save');
		selectByValue("haType","");
		selectByValue("vmType","");
		selectByValue("hostType","");
		selectByValue("platformType","");
		selectByValue("imageId","");
		selectByValue("systemType","");
		selectByValue("storageDataType","");
		$('#serviceMethod').val('create');
		/*$("#cloud_type").val("");*/
		$("#cloud_type_code").val("");
		selectServiceByValue("serviceType","SERVER","initServiceType");
		flatSelectByValue("serviceStatus",'Y');
		initServiceType("SERVER",title);
		initvmBase($("#haType").val(),"");
		initPlatFormType($("#platformType").val());
	}
}
function  addSwitch(){
	$("#serviceStatus").val("N");
	$("#switchStatus").remove();
	$("#switch_span").append('<span class="switch-off" id="switchStatus" style="top:6px;"></span>');
	honeySwitch.init();
}


function selectServiceByValue(id,value,fun){
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





//删除云服务定义
function deletes(serviceId) {
	if(serviceId){
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.post(ctx + "/cloud-service/servicedef/delete.action", {
				"cloudServicePo.serviceId" : serviceId
			}, function(data) {
				if(data.result=='SUCCESS'){
					search();
					showTip(i18nShow('tip_delete_success'));
				}else{
					showError(data.result);
				}
				
			});
		});
		//wmy重新加载页面
		$("#gridTable").jqGrid().trigger("reloadGrid");
	}
}
function stops(serviceId) {
	$.post(ctx + "/cloud-service/servicedef/stop.action", {
		"cloudServicePo.serviceId" : serviceId
	}, function(data) {
		search();
	});
}

function starts(serviceId) {
	$.post(ctx + "/cloud-service/servicedef/start.action", {
		"cloudServicePo.serviceId" : serviceId
	}, function(data) {
		search();
	});
}