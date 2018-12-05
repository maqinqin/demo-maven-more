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
#updataVirtualRouterForm p{width:300px; padding:0; padding-left:16px;}
#updataVirtualRouterForm p i{width:111px; text-align:left;}
#updataVirtualRouterForm label{display:block; padding-left:100px;}
</style>
<script type="text/javascript">
$(function(){
	$('#vr_vpcName').click(function(){
		var appIdSel = $('#vr_externalNetworkName').find("option:selected").text();
		if(appIdSel == i18nShow('com_select_defalt')+'...'){
			showTip(i18nShow('tip_type'));
		}
	});
	$("#VirtualRouterTable").jqGrid({
		url : ctx+"/network/getVirtualRouterList.action",
		rownumbers : true, 
		
		datatype : "json", 
		mtype : "post", 
		height :heightTotal() + 89,
		autowidth : true,
		//multiselect:true,
//		multiselect:true,
		colNames:['virtualRouterId',i18nShow('rm_nw_router_name'),i18nShow('rm_nw_router_external_net_name'),i18nShow('project_name'),i18nShow('available_zone_status'),i18nShow('rm_nw_router_snat_status'),i18nShow('com_remark'),i18nShow('com_operate')],
		colModel : [ 
		            {name : "virtualRouterId",index : "virtualRouterId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "virtualRouterName",index : "virtualRouterName",	width : 120,sortable : true,align : 'left'},
		            {name : "externalNetworkName",index : "externalNetworkName",	width : 120,sortable : true,align : 'left'},
		            {name : "vpcName",index : "vpcName",	width : 120,sortable : true,align : 'left'},
		            {name : "isActive",index : "isActive",sortable : true,width: 120,align : 'left',
						formatter:function(cellVall,options,rowObject){
							if(cellVall=='Y'){// <span class="tip_green">已激活</span>
								return "<span class='tip_green'>"+i18nShow('com_status_Y')+"</span>";
							}else{
								return "<span class='tip_red'>"+i18nShow('com_status_N')+"</span>";
							}
							return cellVall;
						}
					},
					{name : "snatStatus",index : "snatStatus",sortable : true,width: 120,align : 'left',
						formatter:function(cellVall,options,rowObject){
							if(cellVall=='1'){// <span class="tip_green">已激活</span>
								return "<span class='tip_green'>"+i18nShow('com_status_Y')+"</span>";
							}else{
								return "<span class='tip_red'>"+i18nShow('com_status_N')+"</span>";
							}
							return cellVall;
						}
					},
		            {name : "remark",index : "remark",	width : 120,sortable : true,align : 'left'},
		            {name:"option",index:"option",width:120,sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							var updateflag = $("#updateFlag").val();
							var deleteFlag = $("#deleteFlag").val();
							var s ="";
							var ret="";
							if(updateflag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdate('"+rowObject.virtualRouterId+"') >"+i18nShow('com_update')+"</a>";
							}
							if(deleteFlag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"+rowObject.virtualRouterId+"') >"+i18nShow('com_delete')+"</a>";
							}
							if(rowObject.snatStatus=="0"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=snap('"+rowObject.virtualRouterId+"','"+rowObject.snatStatus+"') >"+i18nShow('rm_nw_router_snat_start')+"</a>";
							}
							if(rowObject.snatStatus=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=snap('"+rowObject.virtualRouterId+"','"+rowObject.snatStatus+"') >"+i18nShow('rm_nw_router_snat_stop')+"</a>";
							}
						return 	ret;
						}
		            }
		            ],
		            viewrecords : true,
		    		sortname : "virtualRouterId",
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
		    		pager : "#VirtualRouterPager",
//		    		caption : "设备信息记录",
		    		hidegrid : false
	});
	/**************表单验证开始****************/	
	//验证是否有子网
	jQuery.validator.addMethod("validateHasSubnet", function(value, element) {
        var validateValue=true;
        var externalNetworkId = $("#addExternalNetworkId").val();
        $.ajax({
             type:'post',
             datatype : "json",
             data:{"externalNetworkId":externalNetworkId},
             url:ctx+"/network/hasSubnet.action",
             async : false,
             success:(function(data){
                  if(data.length > 0){
                	  validateValue = true;
                  }else{
                	  validateValue = false;
                  }
             }),
        });
        return this.optional(element) || validateValue;
        },
   "未创建子网");
	$("#updataVirtualRouterForm").validate({
		rules: {
			virtualRouterName1:{required: true},
			externalNetworkId:{required: true,validateHasSubnet :true},
			vpcId:{required: true},
		},
		messages: {
			virtualRouterName1:{required: i18nShow('validate_router_name_required')},
			externalNetworkId:{required: i18nShow('validate_router_external_net_required'),validateHasSubnet:i18nShow('validate_router_external_net_has_sub')},
			vpcId:{required: i18nShow('validate_router_vpc_required')},
		},
		
		submitHandler: function() {
			updateOrSaveVirtualRouter();
		}
	});
	
	
	
	//自适应宽度
	$("#VirtualRouterTable").setGridWidth($("#VirtualRouterGridTable_div").width());
	$(window).resize(function() {
		$("#VirtualRouterTable").setGridWidth($("#VirtualRouterGridTable_div").width());
		$("#VirtualRouterTable").setGridHeight(heightTotal() + 89);
    });	
	$('#vr_externalNetworkName').change(function(){
		changePlatFormType($(this).children('option:selected').val());
	});
	//changePlatFormType($("$vr_externalNetworkName").val());

});
//关联VRM和Project
function changePlatFormType(value){
	if(value){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/network/getENVpcName.action",
			async : false,
			data:{"virtualRouterPo.externalNetworkId":value},
			success:(function(data){
				$('#vr_vpcName').empty();
				if(data.length>1){
					$('#vr_vpcName').prepend("<option value='' selected>"+i18nShow('com_select_defalt')+"...</option>");
				}
				$(data).each(function(i,item){
					$('#vr_vpcName').append("<option value='"+item.vpcId+"'>"+item.vpcName+"</option>");
				});			
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('tip_external_vpc_error'));
			} 
		});
	}else{
		$('#vr_vpcName').empty;
		$('#vr_vpcName').prepend("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	}
	
}

// 显示 新增与修改  的下拉框  
function initaddExternalNetworkSelect()
{
	$("#addExternalNetworkId").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/getAllExternalNetworkName.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#addExternalNetworkId").append("<option value='" + this.externalNetworkId + "'>" + this.externalNetworkName + "</option>");
		});
	});	
}
function selectTierByArea1(){
	var externalNetworkId=$("#addExternalNetworkId").val();
	var virtualRouterId=$("#virtualRouterId").val();
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/network/limitVpcName.action",
			async : false,
			data:{"virtualRouterPo.externalNetworkId":externalNetworkId,"virtualRouterPo.virtualRouterId":virtualRouterId},
			success:(function(data){
				
				$("#addVpcId").empty();
				$("#addVpcId").append("<option value='' selected>"+i18nShow('com_select_defalt')+"...</option>");
				$(data).each(function(i,item){
					$("#addVpcId").append("<option value='"+item.vpcId+"'>"+item.vpcName+"</option>");
				});			
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('tip_external_vpc_error'));
			} 
		});
}
function selectTierByArea2(externalNetworkId,virtualRouterId){
		$.ajax({
			type:'post',
			datatype : "json",
			url:ctx+"/network/limitVpcName.action",
			async : false,
			data:{"virtualRouterPo.externalNetworkId":externalNetworkId,"virtualRouterPo.virtualRouterId":virtualRouterId},
			success:(function(data){
				
				$("#addVpcId").empty();
				$("#addVpcId").append("<option value='' selected>"+i18nShow('com_select_defalt')+"...</option>");
				$(data).each(function(i,item){
					$("#addVpcId").append("<option value='"+item.vpcId+"'>"+item.vpcName+"</option>");
				});			
			}),
			error:function(XMLHttpRequest, textStatus, errorThrown){
				showTip(i18nShow('tip_external_vpc_error'));
			} 
		});
}
//查询
function search() {
		$("#VirtualRouterTable").jqGrid('setGridParam', {
			url : ctx + "/network/getVirtualRouterList.action",//你的搜索程序地址
			postData : {
				"virtualRouterName" : $("#virtualRouterName").val().replace(/(^\s*)|(\s*$)/g, ""),
				"externalNetworkId" : $("#vr_externalNetworkName").val(),
				"vpcId" : $("#vr_vpcName").val(),
			}, //发送搜索条件
			pager : "#VirtualRouterPager"
		}).trigger("reloadGrid"); //重新载入
}
//弹出新增页面
function createData(){
	  $("label.error").remove();
	  $("#updataVirtualRouter").dialog({
		autoOpen : true,
		modal:true,
		height:300,
		width:400,
		position: "middle",
		title:i18nShow('rm_nw_router_save'),
		//resizable:false
 });
    $("#virtualRouterName1").val("");
    $("#remark").val("");
    $("#addExternalNetworkId").val("");
    $("#virtualRouterId").val("");
    $("#addVpcId").val("");
    initaddExternalNetworkSelect();
    $("#virtualRouterMethod").val("save");
}
//关闭窗口
function closeVirtualRouterView(){	  
	  $("#updataVirtualRouter").dialog("close");
}
//保存
function saveOrUpdateVirtualRouterBtn(){
	$("#updataVirtualRouterForm").submit();  
}
function updateOrSaveVirtualRouter(){
	var virtualRouterMethod = $("#virtualRouterMethod").val();
	var virtualRouterId=$("#virtualRouterId").val();
	var virtualRouterName1 = $("#virtualRouterName1").val();
	var addremark=$("#remark").val();
	var snatStatus = $("#snatStatus").val();
	var addExternalNetworkId=$("#addExternalNetworkId").val();
	var addVpcId=$("#addVpcId").val();
	var url;
	if(virtualRouterMethod=="update"){
		url= ctx+"/network/updateVirtualRouter.action"
	}else{
		url= ctx+"/network/saveVirtualRouter.action"
		
	}
	var data = {'virtualRouterPo.virtualRouterName':virtualRouterName1,
				'virtualRouterPo.virtualRouterId':virtualRouterId,
				'virtualRouterPo.remark':addremark,
				'virtualRouterPo.externalNetworkId':addExternalNetworkId,
				'virtualRouterPo.snatStatus':snatStatus,
				'virtualRouterPo.vpcId':addVpcId
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
			$("#updataVirtualRouter").dialog("close");
			$("#VirtualRouterTable").jqGrid().trigger("reloadGrid");
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			closeTip();
			showError(i18nShow('tip_save_fail'));
		} 
	});
}
//弹出修改窗口
function showUpdate(virtualRouterId){
	   $("label.error").remove();
	   $("#updataVirtualRouter").dialog({
			autoOpen : true,
			modal:true,
			height:300,
			width:400,
			position: "middle",
			title:i18nShow('rm_nw_router_update'),
//				draggable: false,selectCmtDatastoreVoById
		       // resizable:false
		})                                     
		$.post(ctx+"/network/selectVirtualRouter.action",{"virtualRouterPo.virtualRouterId" : virtualRouterId},function(data){
			initaddExternalNetworkSelect();
			var enid=data.externalNetworkId
			var virtualRouterId=data.virtualRouterId;
			
			$("#virtualRouterId").val(data.virtualRouterId);
			selectTierByArea2(enid,virtualRouterId);
			$("#virtualRouterName1").val(data.virtualRouterName);
			$("#addExternalNetworkId").val(data.externalNetworkId);
			$("#remark").val(data.remark);
			$("#addVpcId").val(data.vpcId);
			$("#snatStatus").val(data.snatStatus);
			$("#virtualRouterMethod").val("update");
		})	
}
function deleteData(dataId){
	if(dataId){
		
			showTip(i18nShow('tip_delete_confirm'),function(){
				
				if(virtualRouterValidate(dataId)){
					$.post(ctx+"/network/deleteVirtualRouter.action",{
						"virtualRouterPo.virtualRouterId":dataId
					},function(data){ 
						search();
						showTip(i18nShow('tip_delete_success'));
					});
				}else{
	        		return false;
		        }
			
			});
		}else{
			var ids = jQuery("#VirtualRouterTable").jqGrid('getGridParam','selarrrow');

			if(ids.length == 0){
				showError(i18nShow('error_select_one_data'));
				return;
			}
			var list = [];
			$(ids).each(function (index,id2){
				var rowData = $("#VirtualRouterTable").getRowData(id2);
				list[list.length] = rowData.virtualRouterId;
				})
		showTip(i18nShow('tip_delete_confirm'),function(){
			if(virtualRouterValidate(list.join(","))){
				$.post(ctx+"/network/deleteVirtualRouter.action",{
					"virtualRouterPo.virtualRouterId":list.join(",")
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
function virtualRouterValidate(id) {
		var flag = false;
	    $.ajax({
			type : 'post',
			datatype : "json",
			url : ctx+"/network/deleteVirtualRouterCheck.action",
			async : false,
	        data : {// 获得表单数据
	        	"virtualRouterPo.virtualRouterId" : id
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
					var rs=new Array(1);
					 reason="";
					for(var i=0;i<temp.length;i++){
						 if(temp[i]=="wl"){
							reason += "、"+i18nShow('error_private_net_use1');
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

function snap(routerId,snatStatus){
	showTip("load");
	 $.ajax({
			type : 'post',
			datatype : "json",
			url : ctx+"/network/snap.action",
			async : false,
	        data : {// 获得表单数据
	        	"routerId" : routerId,"flag":snatStatus
			},
			beforeSend: function () {
	        	
	        },
			success : function(data) {
				closeTip();
				$("#VirtualRouterTable").jqGrid().trigger("reloadGrid");
				showTip(i18nShow('tip_op_success'));
			},
			error : function() {
				closeTip();
				showTip(i18nShow('tip_error'));
			}
			
		});
}
</script>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="rm_title_vm"/></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
		<div class="searchInWrap_top">
		<div class="searchIn_left" style="width:70%; float:left;">
			<div class="searchInOne" > 
				<table height="12%" width="100%" align="center">
					<tr style=" height:55px; ">
						<td class="tabBt"><icms-i18n:label name="rm_l_routerName"/>：</td>
						<td class="tabCon"><input name="virtualRouterName" id="virtualRouterName"
							type="text" class="textInput readonly" /></td>
						<td class="tabBt" ><icms-i18n:label name="rm_l_exnetName"/>：</td>
						<td class="tabCon"><icms-ui:dic id="vr_externalNetworkName" name="vr_externalNetworkName" showType="select"
							sql = "SELECT ID AS value, NAME AS name FROM RM_NW_OPENSTACK_EXTERNAL_NETWORK WHERE IS_ACTIVE='Y' " 
							attr="class='selInput'" /></td>			
						<td class="tabBt"><icms-i18n:label name="rm_l_openstack_Name"/>：	
						</td>
						<td class="tabCon">
							<select id="vr_vpcName" class="selInput">
								<option  id="" value=""><icms-i18n:label name="info_vm_select"/>...</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<div class="searchBtn_right" style="width:26%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
											
						<td>
							<a href="javascript:search()" type="button" class="btn" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' onclick="search();return false;">
								<span class="icon iconfont icon-icon-search"></span>
	  							<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
							</a>
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel"  >
								<span class="icon iconfont icon-icon-clear1"></span>
	  							<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
							</button>
							<a href="javascript:void(0)" type="button" class="btn" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' onclick="createData()">
								<span class="icon iconfont icon-icon-add"></span>
	  							<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							<input type="hidden" id="showFlag" name="showFlag" value="1" />
							<!-- 
							<shiro:hasPermission name="virtualRouter_sava">
							<input type="button" class="Btn_Add" title="添加" onclick="createData()" />
							</shiro:hasPermission>
							<shiro:hasPermission name="virtualRouter_delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="virtualRouter_update">
									<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="virtualRouter_show">
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
			<div class="panel clear" id="VirtualRouterGridTable_div">
			<table id="VirtualRouterTable"></table>
			<table id="VirtualRouterPager"></table>
		</div>
		</div>
		
		</div>	

	<!-- 添加 修改-->
	<div id="updataVirtualRouter" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataVirtualRouterForm">
				<input type="hidden" id="virtualRouterMethod" name="virtualRouterMethod" />
				<input type="hidden" id="virtualRouterId" name="virtualRouterId" />
				<input type="hidden" id="snatStatus" name="snatStatus" />
				<input type="hidden" id="validateVirtualRouterName" name="validateVirtualRouterName" />
				<p>
					<i><font color="red">*</font><icms-i18n:label name="rm_l_vmRoutName"/>：</i>
					<input type="text" name="virtualRouterName1" id="virtualRouterName1" style="vertical-align:middle;" class="textInput" />
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="rm_l_exnetName"/>：</i>
					<select id="addExternalNetworkId" class="selInput" name="externalNetworkId" 
					onchange="selectTierByArea1(this.value)">
						<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
					</select>
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="rm_l_openstack_Name"/>：</i>
					<select id="addVpcId" class="selInput" name="vpcId">
						<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
					</select>
				</p>
				<p>
					<i><icms-i18n:label name="com_l_remark"/>：</i>
					<input type="text" name="remark" id="remark" style="vertical-align:middle;" class="textInput" />
				</p>
			<p class="winbtnbar btnbar1" style="text-align:right; width:289px; margin-bottom:20px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeVirtualRouterView()" style="margin-left: 0px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateVirtualRouterBtn()" style="margin-right: 5px">
			</p>
		</form>
	</div>	
</body>
</html>
