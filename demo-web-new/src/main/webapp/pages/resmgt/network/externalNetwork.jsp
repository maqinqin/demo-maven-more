<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>	
<html>
<head>
<title>云平台管理系统</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<style>
#updataExternalNetworkForm p{width:600px; padding:0; padding-left:16px;}
#updataExternalSubnetForm p{width:600px; padding:0; padding-left:16px;}
#updataExternalNetworkForm p i{width:100px; text-align:left;}
#updataExternalSubnetForm p i{width:100px; text-align:left;}
#updataExternalNetworkForm label{display:block; padding-left:115px;}
#updataExternalSubnetForm label{display:block; padding-left:115px;}
.updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
</style>
<script type="text/javascript">
$(function(){
	$("#ExternalNetworkTable").jqGrid({
		url : ctx+"/network/getExternalNetworkList.action",
		rownumbers : true, 
		datatype : "json", 
		mtype : "post", 
		height : heightTotal() + 65,
		autowidth : true,
		//multiselect:true,
//		multiselect:true,
		colNames:['externalNetworkId',i18nShow('rm_nw_external_net_name'),i18nShow('rm_nw_external_net_type'),'平台类型',i18nShow('rm_nw_external_net_phynet'),'VLANID',i18nShow('rm_datacenter'),i18nShow('rm_openstack_server_name'),i18nShow('com_status'),i18nShow('com_remark'),i18nShow('com_operate')],
		colModel : [ 
		            {name : "externalNetworkId",index : "externalNetworkId",width : 170,sortable : false,align : 'left',hidden:true},
		            {name : "externalNetworkName",index : "externalNetworkName",	width : 130,sortable : true,align : 'left'},
		            {name : "networkType",index : "networkType",	width : 100,sortable : true,align : 'left'},
		            {name : "platformName",index : "platformName",	width : 120,sortable : true,align : 'left'},
		            {name : "physicalNetwork",index : "physicalNetwork",	width : 100,sortable : true,align : 'left'},
		            {name : "vlanId",index : "vlanId",	width : 100,sortable : true,align : 'left'},
		            {name : "datacenterName",index : "datacenterName",	sortable : true,align : 'left',editor:"text"},
		            {name : "serverName",index : "serverName",	sortable : true,align : 'left',editor:"text"},
		            {name : "isActive",index : "isActive",sortable : true,width: 60,align : 'left',
						formatter:function(cellVall,options,rowObject){
							if(cellVall=='Y'){// <span class="tip_green">已激活</span>
								return "<span class='tip_green'>"+i18nShow('com_status_Y')+"</span>";
							}else{
								return "<span class='tip_red'>"+i18nShow('com_status_N')+"</span>";
							}
							return cellVall;
						}
					},
		            {name : "remark",index : "remark",	width : 50,sortable : true,align : 'left'},
		            {name:"option",index:"option",width:200,sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							var updateflag = $("#updateFlag").val();
							var deleteFlag = $("#deleteFlag").val();
							var showFlag=$("#showFlag").val();
							var s ="";
							var ret="";
							if(updateflag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdate('"+rowObject.externalNetworkId+"') >"+i18nShow('com_update')+"</a>";
							}
							if(deleteFlag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"+rowObject.externalNetworkId+"') >"+i18nShow('com_delete')+"</a>";
							}if(showFlag=="1"){
								s += "<option onclick=showExternalSubnet('"+rowObject.externalNetworkId+"')><icms-i18n:label name="manage_external_sub"/></option >";
							}
							if(showFlag=="1"){
								ret += "<select style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"+i18nShow('com_select_defalt')+"</option>'"+s+"'</select>" ;  
							}
						return 	ret;
						}
		            }
		            ],
		            viewrecords : true,
		    		sortname : "externalNetworkId",
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
		    		pager : "#ExternalNetworkPager",
		    		hidegrid : false
		});

	//自适应宽度
	$("#ExternalNetworkTable").setGridWidth($("#ExternalNetworkGridTable_div").width());
	$(window).resize(function() {
		$("#ExternalNetworkTable").setGridWidth($("#ExternalNetworkGridTable_div").width());
		$("#ExternalNetworkTable").setGridHeight(heightTotal() + 65);
	});	
/**************表单验证开始****************/	
	$("#updataExternalNetworkForm").validate({
		rules: {
			externalNetworkName1:{required: true},
			datacenterId:{required: true},
			vmMsId:{required: true},
			networkType:{required: true},
			physicalNetwork:{required: true},
			vlanId:{required: true}
		},
		messages: {
			externalNetworkName1:{required: i18nShow('validate_external_net_name_required')},
			datacenterId:{required: i18nShow('validate_external_net_datacenter_required')},
			vmMsId:{required: i18nShow('validate_external_net_server_required')},
			networkType:{required: i18nShow('validate_external_net_type_required')},
			physicalNetwork:{required: i18nShow('validate_external_net_phynet_required')},
			vlanId:{required: i18nShow('validate_external_net_vlanid_required')}
		},
		
		submitHandler: function() {
			updateOrSaveExternalNetwork();
		}
	});
	
	initDatacenterSelect();
	initServerNameSelect();
});
//初始化服务器名称下拉框
function initServerNameSelect()
{
	$("#vmMsId").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/resmgt-compute/az/getAllServerName.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#vmMsId").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}

//初始化数据中心名称下拉框
function initDatacenterSelect()
{
	$("#datacenterId").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/converge/queryDatacenterList.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#datacenterId").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}
//初始化新增 修改  下拉框
function initAddDatacenterSelect()
{
	$("#addDatacenterId").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/converge/queryDatacenterList.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#addDatacenterId").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}
function initAddServerNameSelect()
{
	$("#addVmMsId").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/resmgt-compute/az/getAllServerName.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#addVmMsId").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}
//查询
function search() {
		$("#ExternalNetworkTable").jqGrid('setGridParam', {
			url : ctx + "/network/getExternalNetworkList.action",//你的搜索程序地址
			postData : {
				"externalNetworkName" : $("#externalNetworkName").val().replace(/(^\s*)|(\s*$)/g, ""),
				"datacenterId" : $("#datacenterId").val(),
				"vmMsId" : $("#vmMsId").val(),
				"networkType":$("#networkType").val(),
				"platformId":$("#platformId").val()
			}, //发送搜索条件
			pager : "#ExternalNetworkPager"
		}).trigger("reloadGrid"); //重新载入
}
//弹出新增页面
function createData(){
	  $("label.error").remove();
	  $("#updataExternalNetwork").dialog({
		autoOpen : true,
		modal:true,
		height:280,
		width:700,
		position: "middle",
		title:i18nShow('rm_nw_external_net_save'),
		//resizable:false
 });
    $("#externalNetworkName1").val("");
    $("#remark").val("");
    $("#addNetworkType").val("");
    $("#addPhysicalNetwork").empty();
    var op0 = "<option value=''>"+i18nShow('com_select_defalt')+"...</option>";
	$("#addPhysicalNetwork").html(op0);
    //$("#addPhysicalNetwork").val("");
    $("#vlanId").val("");
    initAddDatacenterSelect();
    initAddServerNameSelect();
    $("#externalNetworkMethod").val("save");
    
	$("#addDatacenterId").attr("disabled",false);
	$("#addVmMsId").attr("disabled",false);
	$("#addNetworkType").attr("disabled",false);
	$("#addPhysicalNetwork").attr("disabled",false);
	$("#vlanId").attr("readonly",false);
	$("#remark").attr("readonly",false);
	$("#addNetworkType").change(function(){
		getPhyNet();});
}
//关闭窗口
function closePlatformrView(){	  
	  $("#updataExternalNetwork").dialog("close");
}
//点击保存按钮提交
function saveOrUpdateExternalNetworkBtn(){
	$("#updataExternalNetworkForm").submit();  
}
function updateOrSaveExternalNetwork(){
	var externalNetworkMethod = $("#externalNetworkMethod").val();
	var externalNetworkId=$("#externalNetworkId").val();
	var externalNetworkName1 = $("#externalNetworkName1").val();
	var addremark=$("#remark").val();
	
	var addDatacenterId=$("#addDatacenterId").val();
	var addVmMsId=$("#addVmMsId").val();
	var addNetworkType=$("#addNetworkType").val();
	var addPhysicalNetwork=$("#addPhysicalNetwork").val();
	var vlanId=$("#vlanId").val();
	var url;
	if(externalNetworkMethod=="update"){
		url= ctx+"/network/updateExternalNetwork.action"
	}else{
		url= ctx+"/network/saveExternalNetwork.action"
		
	}
	var data = {'externalNetworkPo.externalNetworkName':externalNetworkName1,
				'externalNetworkPo.externalNetworkId':externalNetworkId,
				'externalNetworkPo.remark':addremark,
				'externalNetworkPo.datacenterId':addDatacenterId,
				'externalNetworkPo.vmMsId':addVmMsId,
				'externalNetworkPo.vlanId':vlanId,
				'externalNetworkPo.physicalNetwork':addPhysicalNetwork,
				'externalNetworkPo.networkType':addNetworkType
				};
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
			showTip(i18nShow('tip_save_success'));   
			$("#updataExternalNetwork").dialog("close");
			$("#ExternalNetworkTable").jqGrid().trigger("reloadGrid");
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			closeTip();
			showTip(i18nShow('tip_save_fail')+XMLHttpRequest.responseText);
		} 
	});
}
//弹出修改窗口
function showUpdate(externalNetworkId){
	   $("label.error").remove();
	   $("#updataExternalNetwork").dialog({
			autoOpen : true,
			modal:true,
			height:280,
			width:700,
			position: "middle",
			title:i18nShow('rm_nw_external_net_update'),
//				draggable: false,selectCmtDatastoreVoById
		       // resizable:false
		})                                                 
		$.post(ctx+"/network/selectExternalNetwork.action",{"externalNetworkPo.externalNetworkId" : externalNetworkId},function(data){
			
		    initAddDatacenterSelect();
		    initAddServerNameSelect();
			$("#externalNetworkId").val(data.externalNetworkId);
			$("#externalNetworkName1").val(data.externalNetworkName);
			$("#validateProjectVpcName").val(data.externalNetworkName);
			$("#remark").val(data.remark);
			$("#addDatacenterId").val(data.datacenterId);
			$("#addVmMsId").val(data.vmMsId);
			$("#addNetworkType").val(data.networkType);
			$("#addPhysicalNetwork").val(data.physicalNetwork);
			$("#vlanId").val(data.vlanId);
			$("#externalNetworkMethod").val("update");
		})	
		$("#addDatacenterId").attr("disabled",true);
		$("#addVmMsId").attr("disabled",true);
		$("#addNetworkType").attr("disabled",true);
		$("#addPhysicalNetwork").attr("disabled",true);
		$("#vlanId").attr("readonly",true);
		$("#remark").attr("readonly",true);
}
function deleteData(dataId){
	if(dataId){
		
			showTip(i18nShow('tip_delete_confirm'),function(){
				
				if(externalNetworkValidate(dataId)){
					$.post(ctx+"/network/deleteExternalNetwork.action",{
						"externalNetworkPo.externalNetworkId":dataId
					},function(data){ 
						search();
						showTip(i18nShow('tip_delete_success'));
					});
				}else{
	        		return false;
		        }
			
			});
		}else{
			var ids = jQuery("#ExternalNetworkTable").jqGrid('getGridParam','selarrrow');

			if(ids.length == 0){
				showError(i18nShow('error_select_one_data'));
				return;
			}
			var list = [];
			$(ids).each(function (index,id2){
				var rowData = $("#ExternalNetworkTable").getRowData(id2);
				list[list.length] = rowData.externalNetworkId;
				})
		showTip(i18nShow('tip_delete_confirm'),function(){
			if(externalNetworkValidate(list.join(","))){
				$.post(ctx+"/network/deleteExternalNetwork.action",{
					"externalNetworkPo.externalNetworkId":list.join(",")
				},function(data){ 
					search();
					showTip(i18nShow('tip_delete_success'));
				});
	        }else{
	        	//showTip("无法删除!");
	        	return false;
	        }
			});
		}
	}
	function externalNetworkValidate(id) {
		var flag = false;
	    $.ajax({
			type : 'post',
			datatype : "json",
			url : ctx+"/network/deleteExternalNetworkCheck.action",
			async : false,
	        data : {// 获得表单数据
	        	"externalNetworkPo.externalNetworkId" : id
			}, success : function(data) {
				var cs=""
				var reason="";
			    var result = "";
				for(var key in data){
					if(data[key] == "") {
						continue;
			      }
				 result += i18nShow('tip_delete_fail')+"（";
					var temps=data[key];
					var temp=temps.split(",");
					var rs=new Array(2);
					 reason="";
					for(var i=0;i<temp.length;i++){
						 if(temp[i]=="zw"){
							reason += "、"+i18nShow('error_subnet_use');
						}if(temp[i]=="vr"){
							reason += "、"+i18nShow('error_router_use');
						}
						cs+=reason;
						
						}
					result += reason.substring(1) + "）\n";
				}
				 showTip(result);
				if(cs.length>0){
					 flag =false;
				}else{
					flag=true;
				}
			},
			error : function() {
				showTip(i18nShow('tip_error'),null,"red");
			}
			
		});
	     return flag;
	}
	
//以下为子网管理操作
function showExternalSubnet(externalNetworkId){
       $("#externalNetworkIdSub").val(externalNetworkId);
        $("#ExternalSubnetGridTable").jqGrid().GridUnload("ExternalSubnetGridTable");
		$("label.error").remove();
		$("#ExternalSubnetGridTable").jqGrid({
			url : ctx+"/network/getExternalSubnetList.action?externalNetworkId="+externalNetworkId, 
			rownumbers : true, 
			datatype : "json", 
			mtype : "post", 
			height : 370,
			autowidth : true,
//			multiselect:true,
			colNames:['externalSubnetId',i18nShow('rm_nw_external_sub_name'),i18nShow('rm_nw_external_sub_ip_version'),i18nShow('rm_nw_external_sub_ip_start'),i18nShow('rm_nw_external_sub_ip_end'),i18nShow('rm_nw_external_sub_mask'),i18nShow('rm_nw_external_sub_gateway'),i18nShow('com_remark'),i18nShow('com_operate')],
			colModel : [ 
			            {name : "externalSubnetId",index : "externalSubnetId",width : 120,sortable : true,align : 'left',hidden:true},
			            {name : "externalSubnetName",index : "externalSubnetName",	width : 120,sortable : true,align : 'left'},
			            {name : "ipVersion",index : "ipVersion",	width : 120,sortable : true,align : 'left',
			    			formatter : function(cellVall, options, rowObject){
			    				if(cellVall == "4" ){
			    					return "<span>ipv4</span>";
			    				}else if(cellVall == "6"){
			    					return "<span>ipv6</span>";
			    				}else{
			    					return "<span></span>";
			    				}
			    			}
			            },
			            {name : "startIp",index : "startIp",	width : 120,sortable : true,align : 'left'},
			            {name : "endIp",index : "endIp",	width : 120,sortable : true,align : 'left'},
			            {name : "subnetMask",index : "subnetMask",	width : 120,sortable : true,align : 'left'},
			            {name : "gateway",index : "gateway",	width : 120,sortable : true,align : 'left'},
			            {name : "remark",index : "remark",	width : 120,sortable : true,align : 'left'},
			            {name:"option",index:"option",width:120,sortable:false,align:"left",
							formatter:function(cellVall,options,rowObject){
	 							var updateflag = $("#updateFlag2").val();
	 							var deleteFlag = $("#deleteFlag2").val();
	 							var ret = "";
	 							if(updateflag=="1"){
	 								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=showExternalSubnetUpdate('"+rowObject.externalSubnetId+"')>"+i18nShow('com_update')+"</a>";
	 							}
	 							if(deleteFlag=="1"){
	 								ret +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteExternalSubnetData('"+rowObject.externalSubnetId+"')>"+i18nShow('com_delete')+"</a>";
	 							}
	 							return 	ret;
							}
			            }
			            ],
			            viewrecords : true,
			    		sortname : "externalSubnetId",
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
			    		pager : "#ExternalSubnetGridPager",
			    		hidegrid : false
		});
		//展示属性信息dialog
		$("#ExternalSubnet").dialog({ 
			autoOpen : true,
			modal : true,
			height : 550,
			title:i18nShow('rm_nw_external_sub'),
			width : 1050,
			close:function(){
				 $( "#ExternalSubnetGridTable" ).jqGrid().trigger("reloadGrid" );
				 $( "#ExternalNetworkTable" ).jqGrid().trigger("reloadGrid" );
			}	
		});
		//自适应
		$("#ExternalSubnetGridTable").setGridWidth($("#ExternalSubnetGridDiv").width());
		$(window).resize(function() {
			$("#ExternalSubnetGridTable").setGridWidth($("#ExternalSubnetGridDiv").width());
	    });
		
		$("#updataExternalSubnetForm").validate({
			rules: {
				externalSubnetName:{required: true},
				ipVersion:{required: true},
				startIp:{required: true},
				endIp:{required: true},
				subnetMask:{required: true},
				gateway:{required: true}
			},
			messages: {
				externalSubnetName:{required: i18nShow('validate_external_sub_name_required')},
				ipVersion:{required: i18nShow('validate_external_sub_ip_version_required')},
				startIp:{required: i18nShow('validate_external_sub_ip_start_required')},
				endIp:{required: i18nShow('validate_external_sub_ip_end_required')},
				subnetMask:{required: i18nShow('validate_external_sub_mask_required')},
				gateway:{required: i18nShow('validate_external_sub_gateway_required')}
			},
			
			submitHandler: function() {
				updateOrSaveRmVirtualType();
			}
		});
}
//弹出新增子网窗口
function createExternalSubnetData(){
	  $("label.error").remove();
	  $("#updataExternalSubnet").dialog({
		autoOpen : true,
		modal:true,
		height:280,
		width:650,
		position: "middle",
		title:i18nShow('rm_nw_external_sub_save'),
		//resizable:false
 });
    $("#externalSubnetName").val("");
    $("#addRemark").val("");
    $("#addIpVersion").val("");
    $("#startIp").val("");
    $("#endIp").val("");
    $("#subnetMask").val("");
    $("#gateway").val("");
    $("#ExternalSubnetMethod").val("save");
    
//	修改子网窗口，除子网名称外其余都不可以修改
	$("#addIpVersion").attr("disabled",false);
	$("#startIp").attr("readonly",false);
	$("#endIp").attr("readonly",false);
	$("#subnetMask").attr("readonly",false);
	$("#gateway").attr("readonly",false);
	$("#addRemark").attr("readonly",false);
}
//关闭窗口
function closeExternalSubnetView(){
	$("#updataExternalSubnet").dialog("close");
}
//提交表单
function saveOrUpdateExternalSubnetBtn(){
	$("#updataExternalSubnetForm").submit();
}
//点击保存
function updateOrSaveRmVirtualType(){
//	var virtualTypeCode=$("#virtualTypeCode").val();
//	var virtualTypeName=$("#virtualTypeName").val();
	var externalNetworkId=$("#externalNetworkIdSub").val();
	var externalSubnetId=$("#externalSubnetId").val();
	var externalSubnetName=$("#externalSubnetName").val();
	var addIpVersion=$("#addIpVersion").val();
	var startIp=$("#startIp").val();
	var endIp=$("#endIp").val();
	var subnetMask=$("#subnetMask").val();
	var gateway=$("#gateway").val();
	var addRemark=$("#addRemark").val();
	var ExternalSubnetMethod=$("#ExternalSubnetMethod").val();
	var url;
		if(ExternalSubnetMethod=="update"){
			url= ctx+"/network/updateExternalSubnet.action"
		}else{
			url= ctx+"/network/saveExternalSubnet.action"
		}
		var data = {
				'externalSubnetPo.externalNetworkId':externalNetworkId,
				'externalSubnetPo.externalSubnetId':externalSubnetId,
				'externalSubnetPo.externalSubnetName':externalSubnetName,
				'externalSubnetPo.ipVersion':addIpVersion,
				'externalSubnetPo.subnetMask':subnetMask,
				'externalSubnetPo.startIp':startIp,
				'externalSubnetPo.endIp':endIp,
				'externalSubnetPo.gateway':gateway,
				'externalSubnetPo.remark':addRemark
				};
		
		$.ajax({
			type:'post',
			datatype : "json",
			url:url,
			async : false,
			data:data,
			/*beforeSend:(function(data){
				return validate(datas);
			}),*/
			success:(function(data){
				showTip(i18nShow('tip_save_success'));
				$("#updataExternalSubnet").dialog("close");
				$("#ExternalSubnetGridTable").jqGrid().trigger("reloadGrid");
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showError(i18nShow('tip_save_fail'));   
			} 
		});	
}
//弹出修改窗口
function showExternalSubnetUpdate(id){
	  $("label.error").remove();
		$("#updataExternalSubnet").dialog({
				autoOpen : true,
				modal:true,
				height:280,
				width:650,
				title:i18nShow('rm_nw_external_sub_update'),
//				draggable: false,selectCmtDatastoreVoById
		        //resizable:false
		});
		$.post(ctx+"/network/selectExternalSubnet.action",
			{"externalSubnetPo.externalSubnetId" : id},
			function(data){
			$("#externalNetworkIdSub").val(data.externalNetworkId);
			$("#externalSubnetId").val(data.externalSubnetId);
			$("#externalSubnetName").val(data.externalSubnetName);
			$("#addIpVersion").val(data.ipVersion);
			$("#subnetMask").val(data.subnetMask);
			$("#startIp").val(data.startIp);
			$("#endIp").val(data.endIp);
			$("#gateway").val(data.gateway);
			$("#addRemark").val(data.remark);
			$("#ExternalSubnetMethod").val("update");
		});
//		修改子网窗口，除子网名称外其余都不可以修改
		$("#addIpVersion").attr("disabled",true);
		$("#startIp").attr("readonly",true);
		$("#endIp").attr("readonly",true);
		$("#subnetMask").attr("readonly",true);
		$("#gateway").attr("readonly",true);
		$("#addRemark").attr("readonly",true);
}

//删除子网
function deleteExternalSubnetData(dataId){
	 if(dataId){
			showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/network/deleteExternalSubnet.action",
				async : false,
				data:{"externalSubnetPo.externalSubnetId":dataId},
				success:(function(data){
					showTip(data.meg);
					$("#ExternalSubnetGridTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('tip_delete_fail'));
				} 
			});
			});
		}
	 }
//获取物理网络
	 function getPhyNet(){
		var vmmsId = $("#addVmMsId").val(); 
		if(vmmsId == null || vmmsId == ''){
			showTip(i18nShow('tip_choose_vmms'));
			return;
		}
		$.ajax({
				type:'get',
				datatype : "json",
				url:ctx+"/network/getPhyNet.action",
				async : true,
				data:{"externalNetworkPo.vmMsId":vmmsId},
				/* beforeSend:(function(data){
					showTip("load");
			       }), */
				success:(function(data){
					$("#addPhysicalNetwork").empty();
					//var op0 = "<option value=''>请选择...</option>";
					//$("#addPhysicalNetwork").append(op0);
					for(var i=0;i<data.length;i++){
						var op1 = "<option value='"+data[i]+"'>"+data[i]+"</option>"
						$("#addPhysicalNetwork").append(op1);
					}
					//closeTip();
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					//alert(2);
					//showError("删除失败!");
				} 
			}); 
	 }
</script>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="rm_title_enw"/></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
		<div class="searchInWrap_top">
		<div class="searchIn_left" style="width:66%; float:left;">
			<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:154px;"><icms-i18n:label name="rm_l_netName"/>：</td>
						<td class="tabCon"><input name="externalNetworkName" id="externalNetworkName"
							type="text" class="textInput readonly" /></td>
						<td class="tabBt" style="width:200px;"><icms-i18n:label name="com_l_dataCenter"/>：</td>
						<td class="tabCon">
							<select id="datacenterId" class="selInput">
								<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
							</select>
						</td>
						<td class="tabBt" style="width:250px;"><icms-i18n:label name="res_l_openstack_serverName"/>：</td>
						<td class="tabCon">
							<select id="vmMsId" class="selInput">
								<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
							</select>
						</td>
				</tr>
				<tr>
						<td class="tabBt" style="width:154px;"><icms-i18n:label name="rm_l_netType"/>：</td>
						<td class="tabCon">
						<icms-ui:dic id="networkType"
								 kind="NETWORK_TYPE_CODE" showType="select"
								attr="class='selInput'" />
						</td>
						<td class="tabBt" style="width:216px;"><label name="rm_l_serve_Name">平台类型：</label></td>
						<td class="tabCon">
							<icms-ui:dic name="platformId" id="platformId" sql="SELECT '1' as id, PLAY.PLATFORM_ID AS value,PLAY.PLATFORM_NAME AS name,'1' AS kind  FROM RM_PLATFORM PLAY WHERE PLAY.IS_ACTIVE='Y'" showType="select" attr="class='selInput'" />
						</td>
				</tr>	
				</table>
			</div>
		</div>
		
		<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
											
						<td>
							<a href="javascript:search()" type="button" class="btn" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' onclick="search();return false;">
								<span class="icon iconfont icon-icon-search"></span>
								<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
							</a>
							
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" >
								<span class="icon iconfont icon-icon-clear1"></span>
								<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
							</button>
							<a href="javascript:void(0)" type="button" class="btn" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' onclick="createData()" >
								<span class="icon iconfont icon-icon-add"></span>
								<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							<input type="hidden" id="showFlag" name="showFlag" value="1" />
							<!-- 
							<shiro:hasPermission name="externalNetwork_sava">
							<input type="button" class="Btn_Add" title="添加" onclick="createData()" />
							</shiro:hasPermission>
							<shiro:hasPermission name="externalNetwork_delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="externalNetwork_update">
									<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="externalNetwork_show">
									<input type="hidden" id="showFlag" name="showFlag" value="1" />
							</shiro:hasPermission>
							 -->
						</td>
						</tr>
					</table>
		</div>
		</div>
			</form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="ExternalNetworkGridTable_div">
			<table id="ExternalNetworkTable"></table>
			<table id="ExternalNetworkPager"></table>
		</div>
		</div>
		
		</div>	

	<!-- 添加 修改-->
	<div id="updataExternalNetwork" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataExternalNetworkForm">
				<input type="hidden" id="externalNetworkMethod" name="externalNetworkMethod" />
				<input type="hidden" id="externalNetworkId" name="externalNetworkId" />
				<input type="hidden" id="externalSubnetId" name="externalSubnetId" />
				<input type="hidden" id="validateExternalNetworkName" name="validateExternalNetworkName" />
				<p>
					<span class="updateDiv_list">	
						<i><font color="red">*</font><icms-i18n:label name="rm_l_netName"/>：</i>
						<input type="text" name="externalNetworkName1" id="externalNetworkName1" style="vertical-align:middle;" class="textInput" />
					</span>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="com_l_dataCenter"/>：</i>
						<select id="addDatacenterId" class="selInput" name="datacenterId">
							<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
						</select>
					</span>
				</p>
				
				<p>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="res_l_openstack_serverName"/>：</i>
						<select id="addVmMsId" class="selInput" name="vmMsId">
							<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
						</select>
					</span>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="rm_l_netType"/>：</i>
						<icms-ui:dic id="addNetworkType" name="networkType"
						kind="NETWORK_TYPE_CODE" showType="select"
						attr="class='selInput'" />
					</span>
				</p>
				
				<p>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="rm_l_physicNet"/>：</i>
				        <select id="addPhysicalNetwork" class="selInput" name="physicalNetwork">
							<option  id="" value="">请选择...</option> 
						</select> 
					</span>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="rm_l_vlanid"/>：</i>
						<input type="text" name="vlanId" id="vlanId"  class="textInput" />
					</span>
				</p>
				
				<p>
					<span class="updateDiv_list">
						<i><icms-i18n:label name="com_l_remark"/>：</i>
						<input type="text" name="remark" id="remark" style="vertical-align:middle;" class="textInput" />
					</span>
				</p>
			<p class="winbtnbar btnbar1" style="text-align:right; width:265px; margin-bottom:20px;float:right;margin-right:84px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closePlatformrView()" style="margin-left: 0px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateExternalNetworkBtn()" style="margin-right: 5px">
			</p>
		</form>
	</div>	
	
	
<div id="ExternalSubnet" style="display: none;" > 
	<table width="100%">
		<tr width="100%">
			<td align="right"></td>
		</tr>
		<tr width="100%">
			<td align="right" class="btnbar1">
			<input type="button" class="btn btn_dd2 btn_dd3" onclick="createExternalSubnetData()"	title='<icms-i18n:label name="com_btn_create"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' style="margin-left: 922px;"/>
			<input type="hidden" id="updateFlag2" name="updateFlag2" value="1" />
			<input type="hidden" id="deleteFlag2" name="deleteFlag2" value="1" />
			<!-- 
			<shiro:hasPermission name="ExternalSubnet_sava">
					<input type="button" class="Btn_Add" onclick="createExternalSubnetData()"	title="新建" style="margin-left: 930px;"/>
			</shiro:hasPermission>
			<shiro:hasPermission name="ExternalSubnet_update">
					<input type="hidden" id="updateFlag2" name="updateFlag2" value="1" />
			</shiro:hasPermission>
			<shiro:hasPermission name="ExternalSubnet_delete">
					<input type="hidden" id="deleteFlag2" name="deleteFlag2" value="1" />
			</shiro:hasPermission>
			 -->
			</td>
		</tr>
		<tr width="100%" height="10px">
		</tr>
	</table>

	<div class="panel clear" id="ExternalSubnetGridDiv">
		<table id="ExternalSubnetGridTable"></table>
		<div id="ExternalSubnetGridPager"></div>
	</div>
</div>
<div id="updataExternalSubnet" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataExternalSubnetForm">
				<input type="hidden" id="ExternalSubnetMethod" name="ExternalSubnetMethod" />
				<input type="hidden" id="externalNetworkIdSub" name="externalNetworkId" />
				<input type="hidden" id="validateExternalSubnetCode" name="validateExternalSubnetCode" />
				<input type="hidden" id="externalSubnetId" name="externalSubnetId" />
				<p>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="rm_l_subnetName"/>：</i>
						<input type="text" name="externalSubnetName" id="externalSubnetName" style="width: 140px;" class="textInput" />
					</span>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="rm_l_ipType"/>：</i>
						<icms-ui:dic id="addIpVersion" name="ipVersion"
						kind="IP_VERSION" showType="select"
						attr="style='width: 155px;'class='selInput'" />
					</span>
				</p>
				
				<p>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="rm_l_startIp"/>：</i>
						<input type="text" name="startIp" id="startIp" style="width: 140px;" class="textInput" />
					</span>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="rm_l_endIp"/>：</i>
						<input type="text" name="endIp" id="endIp" style="width: 140px;" class="textInput" />
					</span>
				</p>
				
				<p>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="com_l_subnetMask"/>：</i>
						<input type="text" name="subnetMask" id="subnetMask" style="width: 140px;" class="textInput" />
					</span>
					<span class="updateDiv_list">
						<i><font color="red">*</font><icms-i18n:label name="com_l_gateway"/>：</i>
						<input type="text" name="gateway" id="gateway" style="width: 140px;" class="textInput" />
					</span>
				</p>
				
				<p>
					<span class="updateDiv_list">
						<i><icms-i18n:label name="com_l_remark"/>：</i>
						<input type="text" name="addRemark" id="addRemark" style="width: 140px;" class="textInput" />
					</span>
				</p>
			<p class="winbtnbar btnbar1" style="width:270px;float:right;margin-right:45px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeExternalSubnetView()" style="margin-left: 0px; margin-right:5px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateExternalSubnetBtn()" style="margin-right: 5px; margin-left:0;">
			</p>
		</form>
	</div>
</body>
</html>
