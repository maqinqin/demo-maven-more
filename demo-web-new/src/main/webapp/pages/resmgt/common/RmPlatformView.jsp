<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>平台类型管理</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript">
$(function() {
	
	$("#rmPlatfromGridTable").jqGrid({
		url : ctx+"/resmgt-common/platform/getPlatformList.action", 
		rownumbers : true, 
		datatype : "json", 
		mtype : "post", 
		height : heightTotal() + 88,
		autowidth : true,
		//multiselect:true,
//		multiselect:true,
		colNames:['platformId',i18nShow('rm_platform_code'),i18nShow('rm_platform_name'),i18nShow('com_operate')],
		colModel : [ 
		            {name : "platformId",index : "platformId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "platformCode",index : "platformCode",	width : 120,sortable : true,align : 'left'},
		            {name : "platformName",index : "platformName",	width : 120,sortable : true,align : 'left'},
		            {name:"option",index:"option",width:120,sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							var updateflag = $("#updateFlag").val();
							var deleteFlag = $("#deleteFlag").val();
							var showFlag=$("#showFlag").val();
							var s ="";
							var ret="";
							if(updateflag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdate('"+rowObject.platformId+"') >"+i18nShow('com_update')+"</a>";
							}
							if(deleteFlag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"+rowObject.platformId+"') >"+i18nShow('com_delete')+"</a>";
							}
							if(showFlag=="1"){
								s += "<option value='1' >"+i18nShow('rm_platform_virture_type')+"</option >";
							}
							if(showFlag=="1"){
								ret += "<select onchange=\"selMeched(this,'"+rowObject.platformId+"')\" style=' margin-right: 10px;text-decoration:none;width:90px;'title=''><option vallue='' select='selected'>"+i18nShow('com_select_defalt')+"</option>'"+s+"'</select>" ;  
							}
						return 	ret;
						}
		            }
		            ],
		            viewrecords : true,
		    		sortname : "platformId",
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
		    		pager : "#rmPlatfromPager",
//		    		caption : "设备信息记录",
		    		hidegrid : false
	});
	//表单中platform_code不允许重复验证
	jQuery.validator.addMethod("platformCodeTrim", function(value, element) { 
		var validateValue=true;
		var rmPlatformMethod=$("#rmPlatformMethod").val();
		var validateplatformCode = $("#validateplatformCode").val();
		if(value!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmPlatformPo.platformCode":value},
			url:ctx+"/resmgt-common/platform/selectRmPlatformForTrim.action",
			async : false,
			success:(function(data){
				if(rmPlatformMethod=="update"){
					if(data==null||data.platformCode==validateplatformCode){
						validateValue=true;
//						alert(validateValue+"1");
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
		}
		return this.optional(element) || validateValue;
		},
	"平台编码不能重复"); 
	jQuery.validator.addMethod("platformNameTrim", function(value, element) { 
		var validateValue=true;
		var rmPlatformMethod=$("#rmPlatformMethod").val();
		var validateplatformName = $("#validateplatformName").val();
		if(value!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmPlatformPo.platformName":value},
			url:ctx+"/resmgt-common/platform/selectRmPlatformNameForTrim.action",
			async : false,
			success:(function(data){
				if(rmPlatformMethod=="update"){
					if(data==null||data.platformName==validateplatformName){
						validateValue=true;
//						alert(validateValue+"1");
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
		}
		return this.optional(element) || validateValue;
		},
	"平台名称不能重复"); 
	$("#updataRmPlatformForm").validate({
		rules: {
			platformCode1:{required: true,maxlength:1,platformCodeTrim:true},
			platformName1:{required: true,platformNameTrim:true}
		},
		messages: {
			platformCode1:{required: i18nShow('validate_data_required'),maxlength:i18nShow('validate_rm_platform_code_length'),platformCodeTrim:i18nShow('validate_data_remote')},
			platformName1:{required: i18nShow('validate_data_required'),platformNameTrim:i18nShow('validate_data_remote')}
		},
		
		submitHandler: function() {
			updateOrSaveRmPlatform();
		}
	});
	//自适应宽度
	$("#rmPlatfromGridTable").setGridWidth($("#rmPlatfromGridTable_div").width());
	$(window).resize(function() {
		$("#rmPlatfromGridTable").setGridWidth($("#rmPlatfromGridTable_div").width());
		$("#rmPlatfromGridTable").setGridHeight(heightTotal() + 88);
    });
	
});	

function selMeched(element,id){
	var val = element.value;
	if(val == "1"){
		showRmVirtualType(id);
	}
}
//查询
   function search() {
		$("#rmPlatfromGridTable").jqGrid('setGridParam', {
			url : ctx + "/resmgt-common/platform/getPlatformList.action",//你的搜索程序地址
			postData : {
				"platformName" : $("#platformName").val().replace(/(^\s*)|(\s*$)/g, ""),
				"platformCode" : $("#platformCode").val().replace(/(^\s*)|(\s*$)/g, "")
			}, //发送搜索条件
			pager : "#rmPlatfromPager"
		}).trigger("reloadGrid"); //重新载入
	}
//弹出添加页面
   function createData(){
		  $("label.error").remove();
		  $("#updataRmPlatform").dialog({
			autoOpen : true,
			modal:true,
			height:210,
			width:350,
			title:i18nShow('rm_platform_save'),
			//resizable:false
	 });
	    $("#platformCode1").val("");
	    $("#platformName1").val("");
	    $("#rmPlatformMethod").val("save");
	}
	  //关闭窗口
	  function closePlatformrView(){
		  
		  $("#updataRmPlatform").dialog("close");
	  }
	//提交表单
	function saveOrUpdatePlatformBtn(){
		$("#updataRmPlatformForm").submit();  
	}
	
	function updateOrSaveRmPlatform(){
		var platformCode=$("#platformCode1").val();
		var platformName=$("#platformName1").val();
 		var platformId=$("#platformId").val();
		var rmPlatformMethod=$("#rmPlatformMethod").val();
		var url;
			if(rmPlatformMethod=="update"){
				url= ctx+"/resmgt-common/platform/updateRmPlatform.action"
			}else{
				url= ctx+"/resmgt-common/platform/savaRmPlatform.action"
			}
			var data = {
					'rmPlatformPo.platformId':platformId,
					'rmPlatformPo.platformCode':platformCode,
					'rmPlatformPo.platformName':platformName
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
					$("#updataRmPlatform").dialog("close");
					$("#rmPlatfromGridTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('tip_save_fail'));   
				} 
			});	
	}
	 function showUpdate(id){
		   $("label.error").remove();
			$("#updataRmPlatform").dialog({
					autoOpen : true,
					modal:true,
					height:220,
					width:350,
					title:i18nShow('rm_platform_update'),
//					draggable: false,selectCmtDatastoreVoById
			       // resizable:false
			})
			$.post(ctx+"/resmgt-common/platform/selectRmPlatform.action",{"rmPlatformPo.platformId" : id},function(data){
				$("#platformCode1").val(data.platformCode);
				$("#platformName1").val(data.platformName);
				$("#platformId").val(id);
				$("#validateplatformCode").val(data.platformCode);
				$("#validateplatformName").val(data.platformName);
				$("#rmPlatformMethod").val("update");
			})
	   }
	 function deleteData(dataId){
		 if(dataId){
				showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-common/platform/deleteRmPlatform.action",
					async : false,
					data:{"rmPlatformPo.platformId":dataId},
					success:(function(data){
						showTip(data.meg);
						$("#rmPlatfromGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('tip_delete_fail'));
					} 
				});
				});
			}else{
				var ids = jQuery("#rmPlatfromGridTable").jqGrid('getGridParam','selarrrow');

				if(ids.length == 0){
					showError(i18nShow('error_select_one_data'));
					return;
				}
				var list = [];
				$(ids).each(function (index,id2){
					var rowData = $("#rmPlatfromGridTable").getRowData(id2);
					list[list.length] = rowData.platformId;
					})
			showTip(i18nShow('tip_delete_confirm'),function(){
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-common/platform/deleteRmPlatform.action",
					async : false,
					data:{"rmPlatformPo.platformId":list.join(",")},
					success:(function(data){
						showTip(data.meg);
						$("#rmPlatfromGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('tip_delete_fail'));
					} 
				});
			});
			};
			
		}
	 //以下为虚拟化类型操作
	 function showRmVirtualType(platformId){
            $("#platformId").val(platformId);
             $("#RmVirtualTypeGridTable").jqGrid().GridUnload("RmVirtualTypeGridTable");
			$("label.error").remove();
			$("#RmVirtualTypeGridTable").jqGrid({
				url : ctx+"/resmgt-common/platform/getRmVirtualTypeList.action?platformId="+platformId, 
				rownumbers : true, 
				datatype : "json", 
				mtype : "post", 
				height : 370,
				autowidth : true,
//				multiselect:true,
				colNames:['virtualTypeId',i18nShow('rm_platform_vm_type_code'),i18nShow('rm_platform_vm_type_name'),i18nShow('com_operate')],
				colModel : [ 
				            {name : "virtualTypeId",index : "virtualTypeId",width : 120,sortable : true,align : 'left',hidden:true},
				            {name : "virtualTypeCode",index : "virtualTypeCode",	width : 120,sortable : true,align : 'left'},
				            {name : "virtualTypeName",index : "virtualTypeName",	width : 120,sortable : true,align : 'left'},
				            {name:"option",index:"option",width:120,sortable:false,align:"left",
								formatter:function(cellVall,options,rowObject){
		 							var updateflag = $("#updateFlag2").val();
		 							var deleteFlag = $("#deleteFlag2").val();
		 							var ret = "";
		 							if(updateflag=="1"){
		 								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=showVirtualTypeUpdate('"+rowObject.virtualTypeId+"')>"+i18nShow('com_update')+"</a>";
		 							}
		 							if(deleteFlag=="1"){
		 								ret +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteVirtualTypeData('"+rowObject.virtualTypeId+"')>"+i18nShow('com_delete')+"</a>";
		 							}
		 							return 	ret;
								}
				            }
				            ],
				            viewrecords : true,
				    		sortname : "virtualTypeId",
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
				    		pager : "#RmVirtualTypeGridPager",
//				    		caption : "设备信息记录",
				    		hidegrid : false
			});
			//展示属性信息dialog
			$("#RmVirtualType").dialog({ 
				autoOpen : true,
				modal : true,
				height : 550,
				title:i18nShow('rm_platform_virture_type'),
				width : 1050,
				close:function(){
					 $( "#rmPlatfromGridTable" ).jqGrid().trigger("reloadGrid" );
				}	
			});
			//自适应
			$("#RmVirtualTypeGridTable").setGridWidth($("#RmVirtualTypeGridDiv").width());
			$(window).resize(function() {
				$("#RmVirtualTypeGridTable").setGridWidth($("#RmVirtualTypeGridDiv").width());
		    });
			//表单验证
	   jQuery.validator.addMethod("virtualTypeCodeTrim", function(value, element) { 
		var validateValue=true;
		var rmVirtualTypeMethod=$("#rmVirtualTypeMethod").val();
		var validatevirtualTypeCode = $("#validatevirtualTypeCode").val();
		if(value!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmVirtualTypePo.virtualTypeCode":value},
			url:ctx+"/resmgt-common/platform/selectRmVirtualTypeForTrim.action",
			async : false,
			success:(function(data){
				if(rmVirtualTypeMethod=="update"){
					if(data==null||data.virtualTypeCode==validatevirtualTypeCode){
						validateValue=true;
//						alert(validateValue+"1");
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
		}
		return this.optional(element) || validateValue;
		},
	"数据中心名称不能重复"); 
			$("#updataRmVirtualTypeForm").validate({
				rules: {
					virtualTypeCode:{required: true,maxlength:2,virtualTypeCodeTrim: true},
					virtualTypeName:{required: true}
				},
				messages: {
					virtualTypeCode:{required: i18nShow('validate_data_required'),maxlength:i18nShow('validate_rm_platform_virtualTypeCode_length'),virtualTypeCodeTrim:i18nShow('validate_data_remote')},
					virtualTypeName:{required: i18nShow('validate_data_required')}
				},
				
				submitHandler: function() {
					updateOrSaveRmVirtualType();
				}
			});
	 }
	//弹出添加页面
	   function createRmVirtualTypeData(){
			  $("label.error").remove();
			  $("#updataRmVirtualType").dialog({
				autoOpen : true,
				modal:true,
				height:240,
				width:350,
				title:i18nShow('rm_platform_virture_type_save'),
				//resizable:false
		 });
		    $("#virtualTypeCode").val("");
		    $("#virtualTypeName").val("");
		    $("#rmVirtualTypeMethod").val("save");
		}
		  //关闭窗口
		  function closeVirtualTypeView(){
			  
			  $("#updataRmVirtualType").dialog("close");
		  }
		//提交表单
		function saveOrUpdateVirtualTypeBtn(){
			$("#updataRmVirtualTypeForm").submit();  
		}
		
		function updateOrSaveRmVirtualType(){
			var virtualTypeCode=$("#virtualTypeCode").val();
			var virtualTypeName=$("#virtualTypeName").val();
	 		var platformId=$("#platformId").val();
	 		var virtualTypeId=$("#virtualTypeId").val();
			var rmVirtualTypeMethod=$("#rmVirtualTypeMethod").val();
			var url;
				if(rmVirtualTypeMethod=="update"){
					url= ctx+"/resmgt-common/platform/updateRmVirtualType.action"
				}else{
					url= ctx+"/resmgt-common/platform/savaVirtualType.action"
				}
				var data = {
						'rmVirtualTypePo.platformId':platformId,
						'rmVirtualTypePo.virtualTypeCode':virtualTypeCode,
						'rmVirtualTypePo.virtualTypeName':virtualTypeName,
						'rmVirtualTypePo.virtualTypeId':virtualTypeId
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
						$("#updataRmVirtualType").dialog("close");
						$("#RmVirtualTypeGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('tip_save_fail'));   
					} 
				});	
		}
		function showVirtualTypeUpdate(id){
			  $("label.error").remove();
				$("#updataRmVirtualType").dialog({
						autoOpen : true,
						modal:true,
						height:240,
						width:470,
						title:i18nShow('rm_platform_virture_type_update'),
//						draggable: false,selectCmtDatastoreVoById
				        //resizable:false
				});
				$.post(ctx+"/resmgt-common/platform/selectRmVirtualType.action",{"rmVirtualTypePo.virtualTypeId" : id},function(data){
					$("#virtualTypeCode").val(data.virtualTypeCode);
					$("#virtualTypeName").val(data.virtualTypeName);
					$("#platformId").val(data.platformId);
					$("#virtualTypeId").val(data.virtualTypeId);
					$("#validatevirtualTypeCode").val(data.virtualTypeCode);
					$("#rmVirtualTypeMethod").val("update");
				});
			
		}
		 function deleteVirtualTypeData(dataId){
			 if(dataId){
					showTip(i18nShow('tip_delete_confirm'),function(){
					$.ajax({
						type:'post',
						datatype : "json",
						url:ctx+"/resmgt-common/platform/deleteVirtualType.action",
						async : false,
						data:{"rmVirtualTypePo.virtualTypeId":dataId},
						success:(function(data){
							showTip(data.meg);
							$("#RmVirtualTypeGridTable").jqGrid().trigger("reloadGrid");
						}),
						error:function(XMLHttpRequest, textStatus, errorThrown){
							showError(i18nShow('tip_delete_fail'));
						} 
					});
					});
				}
				
			}
</script>
<style>
#updataRmPlatform p{width:280px; padding:0; padding-left:16px;}
#updataRmPlatform p i{width:100px; text-align:left;}
#updataRmPlatform label{display:block; padding-left:100px;}

#updataRmVirtualType p{width:280px; padding:0; padding-left:16px;}
#updataRmVirtualType p i{width:100px; text-align:left;}
#updataRmVirtualType label{display:block; padding-left:100px;}
</style>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_plat"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:55px;"><icms-i18n:label name="bim_l_platCode"></icms-i18n:label>：</td>
						<td class="tabCon"><input name="platformCode" id="platformCode"
							type="text" class="textInput readonly" /></td>
						<td class="tabBt" ><icms-i18n:label name="bim_l_platName"></icms-i18n:label>：</td>
						<td ><input name="platformName"
							id="platformName" type="text" class="textInput readonly" /></td>	
						<td class="tabBt" ></td>
						<td class="tabCon"></td>
						
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
							<shiro:hasPermission name="rmPlatform_sava">
							<a href="javascript:void(0)" type="button" class="btn" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' onclick="createData()">
								<span class="icon iconfont icon-icon-add"></span>
								<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="rmPlatform_delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="rmPlatform_update">
									<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="rmPlatform_show">
									<input type="hidden" id="showFlag" name="showFlag" value="1" />
							</shiro:hasPermission>
						</td>
						</tr>
					</table>
		</div>
	</div>
			</form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="rmPlatfromGridTable_div">
			<table id="rmPlatfromGridTable"></table>
			<table id="rmPlatfromPager"></table>
		</div>
		</div>

		</div>
	<div id="updataRmPlatform" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataRmPlatformForm">
				<input type="hidden" id="rmPlatformMethod" name="rmPlatformMethod" />
				<input type="hidden" id="platformId" name="platformId" />
				<input type="hidden" id="validateplatformCode" name="validateplatformCode" />
				<input type="hidden" id="validateplatformName" name="validateplatformName" />
				<p>
					<i><font color="red">*</font><icms-i18n:label name="bim_l_platTypeCode"></icms-i18n:label>：</i>
					<input type="text" name="platformCode1" id="platformCode1" style="width: 140px;" class="textInput" />
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="bim_l_platTypeName"></icms-i18n:label>：</i>
					<input type="text" name="platformName1" id="platformName1" style="width: 140px;" class="textInput" />
				</p>
			<p class="winbtnbar btnbar1" style="text-align:right; width:265px; margin-bottom:20px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closePlatformrView()" style="margin-left: 0px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdatePlatformBtn()" style="margin-right: 5px">
			</p>
		</form>
	</div>
	<div id="RmVirtualType" style="display: none;" > 
	<table width="100%">
		<tr width="100%">
			<td align="right"></td>
		</tr>
		<tr width="100%">
			<td align="right">
			<shiro:hasPermission name="RmVirtualType_sava">
					<a href="javascript:void(0)" type="button" class="btn" onclick="createRmVirtualTypeData()" title='<icms-i18n:label name="com_btn_create"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' style="margin-left:930px; color:#fff;">
						<span class="icon iconfont icon-icon-add"></span>
	  					<span>添加</span>
					</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="RmVirtualType_update">
					<input type="hidden" id="updateFlag2" name="updateFlag2" value="1" />
			</shiro:hasPermission>
			<shiro:hasPermission name="RmVirtualType_delete">
					<input type="hidden" id="deleteFlag2" name="deleteFlag2" value="1" />
			</shiro:hasPermission>
			</td>
		</tr>
		<tr width="100%" height="10px">
		</tr>
	</table>

	<div class="panel clear" id="RmVirtualTypeGridDiv">
		<table id="RmVirtualTypeGridTable"></table>
		<div id="RmVirtualTypeGridPager"></div>
	</div>
</div>
<div id="updataRmVirtualType" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataRmVirtualTypeForm">
				<input type="hidden" id="rmVirtualTypeMethod" name="rmVirtualTypeMethod" />
				<input type="hidden" id="validatevirtualTypeCode" name="validatevirtualTypeCode" />
				<input type="hidden" id="virtualTypeId" name="virtualTypeId" />
				<p>
					<i><font color="red">*</font><icms-i18n:label name="bim_l_virCode"></icms-i18n:label>：</i>
					<input type="text" name="virtualTypeCode" id="virtualTypeCode" style="width: 140px;" class="textInput" />
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="bim_l_virName"></icms-i18n:label>：</i>
					<input type="text" name="virtualTypeName" id="virtualTypeName" style="width: 140px;" class="textInput" />
				</p>
			<p class="winbtnbar btnbar1" style="width:270px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeVirtualTypeView()" style="margin-left: 0px; margin-right:5px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateVirtualTypeBtn()" style="margin-right: 5px; margin-left:0;">
			</p>
		</form>
	</div>
</body>
</html>