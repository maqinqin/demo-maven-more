var saveOrModify = '';
var datacenterId = '';
var roomParentCode = '';
var cabinetParentCode = '';
var uParentCode = '';
var roomId = '';
var cabinetId = '';
var uId = '';
var myClickFun;

$(function(){
		
	/*单击节点后，右侧DIV业务表现的方法*/
	myClickFun = function(){
		if(bizType == "xd"){
			showDiv("xd_div","位置编码管理向导");
		}
		if(bizType == "dc"){
			roomParentCode='';
			
			ajaxCall("/resmgt-common/datacenter/getDataCenterById.action",{"dataCenterId":nodeId},
					function(){
						showDiv("dc_div","数据中心详细信息");
						$("#lab_dcname").html(ajaxResult.datacenterCname);
						$("#lab_dcEname").html(ajaxResult.ename);
						$("#lab_address").html(ajaxResult.address);
						if(ajaxResult.isActive == "Y"){
							$("#lab_isActive").html("<span class='tip_green'>已激活</span>");
						}else{
							$("#lab_isActive").html("<span class='tip_red'>未激活</span>");	
						}	
					}
				)
				
		}else if(bizType == "room"){
			//保存机房id。
			roomId=nodeId;
			ajaxCall("/resmgt-common/cmseat/getComputerRoomByRoomId.action",{"roomId":roomId},
				function(){
					showDiv("room_div","机房详细信息");
					$("#room_seatName").html(ajaxResult.seatName);
					$("#room_seatCode").html(ajaxResult.seatCode);
					$("#room_description").html(ajaxResult.description);
					$("#room_createUser").html(ajaxResult.createUser);
					$("#room_createDatetime").html(ajaxResult.createDateTime);
					$("#room_updateUser").html(ajaxResult.updateUser);
					$("#room_updateDatetime").html(ajaxResult.updateDateTime);
					//保存父节点code
					cabinetParentCode=ajaxResult.seatCode;
				}
			)
		}else if(bizType == "cabinet"){
			//保存机柜id
			cabinetId=nodeId;
			ajaxCall("/resmgt-common/cmseat/getComputerCabinetByCabinetId.action",{"cabinetId":cabinetId},
					function(){
						showDiv("cabinet_div","机柜详细信息");
						$("#cabinet_seatName").html(ajaxResult.seatName);
						$("#cabinet_seatCode").html(ajaxResult.seatCode);
						$("#cabinet_description").html(ajaxResult.description);
						$("#cabinet_createUser").html(ajaxResult.createUser);
						$("#cabinet_createDatetime").html(ajaxResult.createDateTime);
						$("#cabinet_updateUser").html(ajaxResult.updateUser);
						$("#cabinet_updateDatetime").html(ajaxResult.updateDateTime);
						//保存父节点code
						uParentCode=ajaxResult.seatCode;
					}
				)
		}else if(bizType == "u"){
			//保存U位id
			uId=nodeId;
			ajaxCall("/resmgt-common/cmseat/getUByUId.action",{"uId":uId},
					function(){
						showDiv("u_div","U位详细信息");
						$("#u_seatName").html(ajaxResult.seatName);
						$("#u_seatCode").html(ajaxResult.seatCode);
						$("#u_uheight").html(ajaxResult.uheight);
						if(ajaxResult.deviceName != ""){
							$("#u_status").html("已关联："+ajaxResult.deviceName);
						}else{
							$("#u_status").html("未关联");
						}
						$("#u_description").html(ajaxResult.description);
						$("#u_createUser").html(ajaxResult.createUser);
						$("#u_createDatetime").html(ajaxResult.createDateTime);
						$("#u_updateUser").html(ajaxResult.updateUser);
						$("#u_updateDatetime").html(ajaxResult.updateDateTime);
					}
			)
		}
};	

/**
 * 异步加载节点定义方法
 */
var myExpandFun = function(){
	if(bizType == "dc"){
		//保存数据中心id。
		datacenterId=nodeId;
		ajaxCall("/resmgt-common/cmseat/getComputerRoomByDcId.action",{"dcId":datacenterId},
				asyncAddNode
		)
	}else if(bizType == "room"){
		//保存机房id。
		roomId=nodeId;
		ajaxCall("/resmgt-common/cmseat/getComputerCabinetByRoomCode.action",{"roomCode":roomId},
				asyncAddNode
			)
	}else if(bizType == "cabinet"){
		//保存机柜id
		cabinetId=nodeId;
		ajaxCall("/resmgt-common/cmseat/getUByCabinetCode.action",{"cabinetCode":cabinetId},
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
        url: ctx+"/resmgt-common/cmseat/getCmSeatTreeList.action",//请求的action路径   
        beforeSend: function () {
        	showTip("load");
        },
        error: function () {//请求失败处理函数   
            showError("树加载失败");
        },
        success:function(data){ //请求成功后处理函数。     
        	zTreeInit("treeRm",data);
        	zTree.selectNode(zTree.getNodeByParam("id","-1",null));
        	bizType = "xd";
        	myClickFun();
        	closeTip();
        }
    });
    
	$("#room_form").validate({
		rules: {
			room_seatName_inout: { required: true },
			room_seatCode_inout: { required: true }
		},
		messages: {
			room_seatName_inout: {required: "[机房名称]不能为空"},
			 room_seatCode_inout: {required: "[机房编码]不能为空"}
		},
		submitHandler: function() {
			if(saveOrModify=='save'){
				saveRoomInfo();
			}else{
				updateRoomInfo();
			}
		}
	});

	$("#cabinet_form").validate({
		rules: {
			cabinet_seatName_inout: { required: true },
			cabinet_seatCode_inout: { required: true }
		},
		messages: {
			cabinet_seatName_inout: {required: "[机柜名称]不能为空"},
			cabinet_seatCode_inout: {required: "[机柜编码]不能为空"}
		},
		submitHandler: function() {
			if(saveOrModify=='save'){
				saveCabinetInfo();
			}else{
				updateCabinetInfo();
			}
		}
	});
	
	$("#u_form").validate({
		rules: {
			u_seatName_inout: { required: true },
			u_uheight_inout: { required: true },
			u_seatCode_inout: { required: true }
		},
		messages: {
			 u_seatName_inout: {required: "[U位名称]不能为空"},
			 u_uheight_inout: {required: "[U高]不能为空"},
			 u_seatCode_inout: {required: "[U位编码]不能为空"}
		},
		submitHandler: function() {
			if(saveOrModify=='save'){
				saveUInfo();
			}else{
				updateUInfo();
			}
		}
	});
	
});

//新建或修改机房信息
function saveOrupdateRoomInfo(oper){
	$("label.error").remove();
	$("#hid_icon").val(iconPath+'room.png');
	
	$("#add_room_div").dialog({
			autoOpen : true,
			modal:true,
			height:280,
			width:450,
			title:'机房信息',
			//resizable:false
	});
	clearTab();
	
	selectByValue("room_seatName_inout","");
	selectByValue("room_seatCode_inout","");
	$("#room_description_inout").val("");
	
	if(oper=="update"){
		$("#btn_mod_room").show();
		$("#btn_add_room").hide();
		
		$.post(ctx+"/resmgt-common/cmseat/getComputerRoomByRoomId.action",
				{"roomId":roomId},
				function(data){
					$("#room_seatName_inout").val(data.seatName);
					$("#room_seatCode_inout").val(data.seatCode);
					$("#room_description_inout").val(data.description);
				}
			)
	}else{
		$("#btn_mod_room").hide();
		$("#btn_add_room").show();
	}
	
}

//新建或修改机柜信息
function saveOrupdateCabinetInfo(oper){
	$("label.error").remove();
	$("#hid_icon").val(iconPath+'cabinet.png');
	
	$("#add_cabinet_div").dialog({
			autoOpen : true,
			modal:true,
			height:280,
			width:450,
			title:'机柜信息',
			//resizable:false
	});
	clearTab();
	
	selectByValue("cabinet_seatName_inout","");
	selectByValue("cabinet_seatCode_inout","");
	$("#cabinet_description_inout").val("");
	
	if(oper=="update"){
		$("#btn_mod_cabinet").show();
		$("#btn_add_cabinet").hide();
		
		$.post(ctx+"/resmgt-common/cmseat/getComputerCabinetByCabinetId.action",
				{"cabinetId":cabinetId},
				function(data){
					$("#cabinet_seatName_inout").val(data.seatName);
					$("#cabinet_seatCode_inout").val(data.seatCode);
					$("#cabinet_description_inout").val(data.description);
				}
			)
	}else{
		$("#btn_mod_cabinet").hide();
		$("#btn_add_cabinet").show();
	}
	
}

//新建或修改U位信息
function saveOrupdateUInfo(oper){
	$("label.error").remove();
	$("#hid_icon").val(iconPath+'u.png');
	
	$("#add_u_div").dialog({
			autoOpen : true,
			modal:true,
			height:350,
			width:450,
			title:'U位信息',
			//resizable:false
	});
	clearTab();
	
	selectByValue("u_seatName_inout","");
	selectByValue("u_seatCode_inout","");
	selectByValue("u_uheight_inout","");
	$("#u_description_inout").val("");
	
	if(oper=="update"){
		$("#btn_mod_u").show();
		$("#btn_add_u").hide();
		
		$.post(ctx+"/resmgt-common/cmseat/getUByUId.action",
				{"uId":uId},
				function(data){
					$("#u_seatName_inout").val(data.seatName);
					$("#u_seatCode_inout").val(data.seatCode);
					$("#u_uheight_inout").val(data.uheight);
					$("#u_description_inout").val(data.description);
				}
			);
	}else{
		$("#btn_mod_u").hide();
		$("#btn_add_u").show();
	}
	
}

function clearTab(){
	 //var tab = document.getElementById("updateTab") ;
	 var inputs = document.getElementsByTagName("input"); 
	 for(var k=0;k<inputs.length;k++) 
	 { 
		 if(inputs[k].type!='button'&&inputs[k].type!='hidden'){
			 inputs[k].value=""; 
		 }
	 } 
}

//新建机房信息。
function saveRoomInfo(){

	var room_seatName_inout=$("#room_seatName_inout").val();//机房名称
	var room_seatCode_inout=$("#room_seatCode_inout").val();//机房编码
	var room_description_inout=$("#room_description_inout").val();//位置编码描述

	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/resmgt-common/cmseat/insertSeatInfoOfRoom.action",
		async : false,
		data:{"cmSeatPo.seatName":room_seatName_inout,
			"cmSeatPo.seatCode":room_seatCode_inout,
			"cmSeatPo.parentCode":roomParentCode,
			"cmSeatPo.description":room_description_inout,
			"cmSeatPo.datacenterId":datacenterId},
		success:(function(data){
			if(data.rtnMsg!="" && data.rtnMsg!=undefined){
				showTip(data.rtnMsg);
			}else{
				addNode(data.id,room_seatName_inout,$("#hid_icon").val(),null,false,"room",false,true);
				closeView("add_room_div");
				showTip("新建机房信息成功。");
			}
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showError("新建机房信息失败。");
		} 
	});
}

//新建机柜信息。
function saveCabinetInfo(){

	var cabinet_seatName_inout=$("#cabinet_seatName_inout").val();//机柜名称
	var cabinet_seatCode_inout=$("#cabinet_seatCode_inout").val();//机柜编码
	var cabinet_description_inout=$("#cabinet_description_inout").val();//位置编码描述

	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/resmgt-common/cmseat/insertSeatInfoOfCabinet.action",
		async : false,
		data:{"cmSeatPo.seatName":cabinet_seatName_inout,
			"cmSeatPo.seatCode":cabinet_seatCode_inout,
			"cmSeatPo.parentCode":cabinetParentCode,
			"cmSeatPo.description":cabinet_description_inout,
			"cmSeatPo.datacenterId":datacenterId},
		success:(function(data){
			if(data.rtnMsg!="" && data.rtnMsg!=undefined){
				showTip(data.rtnMsg);
			}else{
				addNode(data.id,cabinet_seatName_inout,$("#hid_icon").val(),null,false,"cabinet",false,true);
				closeView("add_cabinet_div");
				showTip("新建机柜信息成功。");
			}
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showError("新建机柜信息失败。");
		} 
	});
}

//新建U位信息。
function saveUInfo(){

	var u_seatName_inout=$("#u_seatName_inout").val();//U位名称
	var u_seatCode_inout=$("#u_seatCode_inout").val();//U位编码
	var u_uheight_inout=$("#u_uheight_inout").val();//U高
	var u_description_inout=$("#u_description_inout").val();//位置编码描述

	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/resmgt-common/cmseat/insertSeatInfoOfU.action",
		async : false,
		data:{"cmSeatPo.seatName":u_seatName_inout,
			"cmSeatPo.seatCode":u_seatCode_inout,
			"cmSeatPo.parentCode":uParentCode,
			"cmSeatPo.description":u_description_inout,
			"cmSeatPo.uheight":u_uheight_inout,
			"cmSeatPo.datacenterId":datacenterId},
		success:(function(data){
			if(data.rtnMsg!="" && data.rtnMsg!=undefined){
				showTip(data.rtnMsg);
			}else{
				addNode(data.id,u_seatName_inout,$("#hid_icon").val(),null,false,"u",false);
				closeView("add_u_div");
				showTip("新建U位信息成功。");
			}
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showError("新建U位信息失败。");
		} 
	});
}

//修改机房信息。
function updateRoomInfo(){
	
	var room_seatName_inout=$("#room_seatName_inout").val();//机房名称
	var room_seatCode_inout=$("#room_seatCode_inout").val();//机房编码
	var room_description_inout=$("#room_description_inout").val();//位置编码描述
	
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/resmgt-common/cmseat/updateSeatInfo.action",
		async : false,
		data:{"cmSeatPo.id":roomId,
			"cmSeatPo.seatName":room_seatName_inout,
			"cmSeatPo.seatCode":room_seatCode_inout,
			"cmSeatPo.description":room_description_inout},
		success:(function(data){
			if(data.rtnMsg!="" && data.rtnMsg!=undefined){
				showTip(data.rtnMsg);
			}else{
				modifyNode(roomId,room_seatName_inout);
				showTip("修改机房信息成功。");
				zTree.selectNode(zTree.getNodeByParam("id",roomId,null));
				bizType = "room";
				myClickFun();
			}
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showError("修改机房信息失败。");
		} 
	});

}

//修改机柜信息。
function updateCabinetInfo(){
	
	var cabinet_seatName_inout=$("#cabinet_seatName_inout").val();//机柜名称
	var cabinet_seatCode_inout=$("#cabinet_seatCode_inout").val();//机柜编码
	var cabinet_description_inout=$("#cabinet_description_inout").val();//位置编码描述
	
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/resmgt-common/cmseat/updateSeatInfo.action",
		async : false,
		data:{"cmSeatPo.id":cabinetId,
			"cmSeatPo.seatName":cabinet_seatName_inout,
			"cmSeatPo.seatCode":cabinet_seatCode_inout,
			"cmSeatPo.description":cabinet_description_inout},
		success:(function(data){
			if(data.rtnMsg!="" && data.rtnMsg!=undefined){
				showTip(data.rtnMsg);
			}else{
				modifyNode(cabinetId,cabinet_seatName_inout);
				showTip("修改机柜信息成功。");
				zTree.selectNode(zTree.getNodeByParam("id",cabinetId,null));
				bizType = "cabinet";
				myClickFun();
			}
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showError("修改机柜信息失败。");
		} 
	});

}

//修改U位信息。
function updateUInfo(){
	
	var u_seatName_inout=$("#u_seatName_inout").val();//U位名称
	var u_seatCode_inout=$("#u_seatCode_inout").val();//U位编码
	var u_uheight_inout=$("#u_uheight_inout").val();//U高
	var u_description_inout=$("#u_description_inout").val();//位置编码描述
	
	$.ajax({
		type:'post',
		datatype : "json",
		url:ctx+"/resmgt-common/cmseat/updateSeatInfo.action",
		async : false,
		data:{"cmSeatPo.id":uId,
			"cmSeatPo.seatName":u_seatName_inout,
			"cmSeatPo.seatCode":u_seatCode_inout,
			"cmSeatPo.uheight":u_uheight_inout,
			"cmSeatPo.description":u_description_inout},
		success:(function(data){
			if(data.rtnMsg!="" && data.rtnMsg!=undefined){
				showTip(data.rtnMsg);
			}else{
				modifyNode(uId,u_seatName_inout);
				showTip("修改U位信息成功。");
				zTree.selectNode(zTree.getNodeByParam("id",uId,null));
				bizType = "u";
				myClickFun();
			}
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			showError("修改U位信息失败。");
		} 
	});

}

//删除机房信息。
function delRoomInfo(){
	
	showTip("确定删除"+$("#room_seatName").text()+"吗?",function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-common/cmseat/deleteRoomInfo.action",
			async : false,
			data:{"roomId":roomId},
			success:(function(data){
				if(data.rtnMsg!=""){
					showTip(data.rtnMsg);
				}else{
					deleteTreeNode(roomId);
					showTip("删除机房成功。");
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip("删除机房失败。");
			}
		});
	});
	
}

//删除机柜信息。
function delCabinetInfo(){
	
	showTip("确定删除"+$("#cabinet_seatName").text()+"吗?",function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-common/cmseat/deleteCabinetInfo.action",
			async : false,
			data:{"cabinetId":cabinetId},
			success:(function(data){
				if(data.rtnMsg!=""){
					showTip(data.rtnMsg);
				}else{
					deleteTreeNode(cabinetId);
					showTip("删除机柜成功。");
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip("删除机柜失败。");
			}
		});
	});
	
}

//删除U位信息。
function delUInfo(){
	
	showTip("确定删除"+$("#u_seatName").text()+"吗?",function(){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/resmgt-common/cmseat/deleteUInfo.action",
			async : false,
			data:{"uId":uId},
			success:(function(data){
				if(data.rtnMsg!=""){
					showTip(data.rtnMsg);
				}else{
					deleteTreeNode(uId);
					showTip("删除U位成功。");
				}
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip("删除U位失败。");
			}
		});
	});
	
}

function showDiv(divId,title){
	$("#rightContentDiv div").hide();
	$("#"+divId).show();
}

function closeView(divId){
	$("#"+divId).dialog("close");
}

function saveOrUpdateBtnRoom(oper){
	saveOrModify = oper;
	$("#room_form").submit();
}

function saveOrUpdateBtnCabinet(oper){
	saveOrModify = oper;
	$("#cabinet_form").submit();
}

function saveOrUpdateBtnU(oper){
	saveOrModify = oper;
	$("#u_form").submit();
}
