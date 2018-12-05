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
#updataAvailableZoneForm p{width:300px; padding:0; padding-left:16px;}
#updataAvailableZoneForm p i{width:100px; text-align:left;}
#updataAvailableZoneForm label{display:block; padding-left:115px;}
</style>
<script type="text/javascript">
$(function(){
	$("#AvailableZoneTable").jqGrid({
		url : ctx+"/resmgt-compute/az/getAvailableZoneList.action",
		rownumbers : true, 
		datatype : "json", 
		mtype : "post", 
		height : heightTotal() + 88,
		autowidth : true,
		//multiselect:true,
//		multiselect:true,
		colNames:['availableZoneId',i18nShow('available_zone_name'),i18nShow('rm_openstack_server_name'),'平台类型',i18nShow('available_zone_status'),i18nShow('com_remark'),i18nShow('com_operate')],
		colModel : [ 
		            {name : "availableZoneId",index : "availableZoneId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "availableZoneName",index : "availableZoneName",	width : 120,sortable : true,align : 'left'},
		            {name : "serverName",index : "serverName",	width : 120,sortable : true,align : 'left'},
		            {name : "platformName",index : "platformName",	width : 120,sortable : true,align : 'left'},
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
		            {name : "remark",index : "remark",	width : 120,sortable : true,align : 'left'},
		            {name:"option",index:"option",width:120,sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							var updateflag = $("#updateFlag").val();
							var deleteFlag = $("#deleteFlag").val();
							var s ="";
							var ret="";
							if(updateflag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdate('"+rowObject.availableZoneId+"') >"+i18nShow('com_update')+"</a>";
							}
							if(deleteFlag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=deleteData('"+rowObject.availableZoneId+"') >"+i18nShow('com_delete')+"</a>";
							}
						return 	ret;
						}
		            }
		            ],
		            viewrecords : true,
		    		sortname : "availableZoneId",
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
		    		pager : "#AvailableZonePager",
//		    		caption : "设备信息记录",
		    		hidegrid : false
	});
	
	//自适应宽度
	$("#AvailableZoneTable").setGridWidth($("#AvailableZoneGridTable_div").width());
	$(window).resize(function() {
		$("#AvailableZoneTable").setGridWidth($("#AvailableZoneGridTable_div").width());
		$("#AvailableZoneTable").setGridHeight(heightTotal() + 88);
    });	
	
	//可用分区名称自定义验证 ：同一虚拟管理服务器下的可用分区名称不能重复
	jQuery.validator.addMethod("availableZoneCodeTrim", function() { 
		var validateValue=true;
		var availableZoneMethod=$("#availableZoneMethod").val();
		var validateAvailableZoneName = $("#validateAvailableZoneName").val();
		var addAvailableZoneName=$("#availableZoneName1").val();
		var serverName=$("#addServerName").val();
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"availableZonePo.availableZoneName":addAvailableZoneName,"serverName":serverName},
			url:ctx+"/resmgt-compute/az/selectAvailableZoneNameForTrim.action",
			async : false,
			success:(function(data){
				if(availableZoneMethod=="update"){
					if(data==null||data.availableZoneName==validateAvailableZoneName){
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
		return validateValue;
		},
	"可用分区名称不能重复使用"); 
	jQuery.validator.addMethod("stringCheck", function(value, element) { 
		return this.optional(element) || /^[a-zA-Z0-9_.]*$/g.test(value);     
		},
	"只能包括英文字母"); 
	$("#updataAvailableZoneForm").validate({
		rules: {
			availableZoneName1:{required: true,availableZoneCodeTrim:true,stringCheck:true},
			serverName:{required: true}
		},
		messages: {
			availableZoneName1:{required: i18nShow('validate_availableZoneName_required'),availableZoneCodeTrim:i18nShow('validate_availableZoneName_remote'),stringCheck: i18nShow('validate_availableZoneName_stringCheck')},
			serverName:{required: i18nShow('validate_availableZone_server_required')}
		},
		
		submitHandler: function() {
			updateOrSaveAvailableZone();
		}
	});
	
});

//查询
function search() {
		$("#AvailableZoneTable").jqGrid('setGridParam', {
			url : ctx + "/resmgt-compute/az/getAvailableZoneList.action",//你的搜索程序地址
			postData : {
				"availableZoneName" : $("#availableZoneName").val().replace(/(^\s*)|(\s*$)/g, ""),
				"serverName" : $("#serverName").val().replace(/(^\s*)|(\s*$)/g, ""),
				"platformId" : $("#platformId").val()
			}, //发送搜索条件
			pager : "#AvailableZonePager"
		}).trigger("reloadGrid"); //重新载入
	}

//弹出添加页面
function createData(){
		  $("label.error").remove();
		  $("#updataAvailableZone").dialog({
			autoOpen : true,
			modal:true,
			height:230,
			width:370,
			position: "middle",
			title:i18nShow('available_zone_save'),
			//resizable:false
	 });
	    $("#availableZoneName1").val("");
	    $("#remark").val("");
	    $("#availableZoneMethod").val("save");
	    initAddDatacenterSelect()
}
//关闭窗口
function closePlatformrView(){	  
	  $("#updataAvailableZone").dialog("close");
}
//初始化新增修改窗口网络汇聚名称下拉框
function initAddDatacenterSelect()
{
	$("#addServerName").html("<option value=''>"+i18nShow('com_select_defalt')+"...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/resmgt-compute/az/getAllServerName.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#addServerName").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}
//点击保存按钮提交
function saveOrUpdateAvailableZoneBtn(){
	$("#updataAvailableZoneForm").submit();  
}

function updateOrSaveAvailableZone(){
	var availableZoneMethod = $("#availableZoneMethod").val();
	var addIsActive = "Y";
	var addavailableZoneId=$("#availableZoneId").val();
	var availableZoneName1 = $("#availableZoneName1").val();
	var addServerName = $("#addServerName").val();
	var addremark=$("#remark").val();
	var url;
	if(availableZoneMethod=="update"){
		url= ctx+"/resmgt-compute/az/updateAvailableZone.action"
	}else{
		url= ctx+"/resmgt-compute/az/saveAvailableZone.action"
	}
	var data = {'availableZonePo.availableZoneName':availableZoneName1,
				'availableZonePo.availableZoneId':addavailableZoneId,
				'availableZonePo.isActive':addIsActive,
				'availableZonePo.remark':addremark,
				'serverName':addServerName};
	$.ajax({
		type:'post',
		datatype : "json",
		url:url,
		async : false,
		data:data,
		/*beforeSend:(function(data){
			return validate(datas);
		}),*/
		beforeSend: function () {
        	showTip("load");
        },
		success:(function(data){
			closeTip();
			showTip(i18nShow('tip_save_success'));   
			$("#updataAvailableZone").dialog("close");
			$("#AvailableZoneTable").jqGrid().trigger("reloadGrid");
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			closeTip();
			showTip(i18nShow('tip_save_fail')+XMLHttpRequest.responseText);
		} 
	});
}
function showUpdate(id){
	   $("label.error").remove();
	   $("#updataAvailableZone").dialog({
			autoOpen : true,
			modal:true,
			height:230,
			width:370,
			position: "middle",
			title:i18nShow('available_zone_update'),
//				draggable: false,selectCmtDatastoreVoById
		       // resizable:false
		})
		$.post(ctx+"/resmgt-compute/az/selectAvailableZone.action",{"availableZonePo.availableZoneId" : id},function(data){
			initAddDatacenterSelect()
			$("#availableZoneId").val(data.availableZoneId);
			$("#availableZoneName1").val(data.availableZoneName);
			$("#validateAvailableZoneName").val(data.availableZoneName);
			$("#addServerName").val(data.vmServerId);
			$("#remark").val(data.remark);
			$("#availableZoneMethod").val("update");
		})
}
/* function deleteData(dataId){
	 if(dataId){
			showTip("确定删除数据?",function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/az/deleteAvailableZone.action",
				async : false,
				data:{"availableZonePo.availableZoneId":dataId},
				success:(function(data){
					showTip(data.meg);
					$("#AvailableZoneTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError("删除失败!");
				} 
			});
			});
		}else{
			var ids = jQuery("#AvailableZoneTable").jqGrid('getGridParam','selarrrow');

			if(ids.length == 0){
				showError("至少选择一条数据!");
				return;
			}
			var list = [];
			$(ids).each(function (index,id2){
				var rowData = $("#AvailableZoneTable").getRowData(id2);
				list[list.length] = rowData.availableZoneId;
				})
		showTip("确定删除数据?",function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-compute/az/deleteAvailableZone.action",
				async : false,
				data:{"availableZonePo.availableZoneId":list.join(",")},
				success:(function(data){
					showTip(data.meg);
					$("#AvailableZoneTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError("删除失败!");
				} 
			});
		});
		};
} */
function deleteData(dataId){
if(dataId){
	
		showTip(i18nShow('tip_delete_confirm'),function(){
			
			if(availableZoneUseValidate(dataId)){
				$.post(ctx+"/resmgt-compute/az/deleteAvailableZone.action",{
					"availableZonePo.availableZoneId":dataId
				},function(data){ 
					search();
					showTip(i18nShow('tip_delete_success'));
				});
			}else{
        		return false;
	        }
		
		});
	}else{
		var ids = jQuery("#AvailableZoneTable").jqGrid('getGridParam','selarrrow');

		if(ids.length == 0){
			showError(i18nShow('error_select_one_data'));
			return;
		}
		var list = [];
		$(ids).each(function (index,id2){
			var rowData = $("#AvailableZoneTable").getRowData(id2);
			list[list.length] = rowData.availableZoneId;
			})
	showTip(i18nShow('tip_delete_confirm'),function(){
		if(availableZoneUseValidate(list.join(","))){
			$.post(ctx+"/resmgt-compute/az/deleteAvailableZone.action",{
				"availableZonePo.availableZoneId":list.join(",")
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
function availableZoneUseValidate(id) {
	var flag = false;
    $.ajax({
		type : 'post',
		datatype : "json",
		url : ctx+"/resmgt-compute/az/deleteAvailableZoneCheck.action",
		async : false,
        data : {// 获得表单数据
        	"availableZonePo.availableZoneId" : id
		}, success : function(data) {
			var cs=""
			var reason="";
		    var result = "";
			for(var key in data){
				if(data[key] == "") {
					continue;
		      }
			 result += ""+i18nShow('tip_delete_fail')+"（";
				var temps=data[key];
				var temp=temps.split(",");
				var rs=new Array(1);
				 reason="";
				for(var i=0;i<temp.length;i++){
					 if(temp[i]=="az"){
						reason += "、"+i18nShow('error_res_pool_use');
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

</script>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				
				<div class="WorkBtBg"><icms-i18n:label name="rm_title_ap"/></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
		<div class="searchInWrap_top">
		<div class="searchIn_left" style="width:66%; float:left;">
			<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:55px;"><icms-i18n:label name="rm_l_visiAreaName"/>：</td>
						<td class="tabCon"><input name="availableZoneName" id="availableZoneName"
							type="text" class="textInput readonly" /></td>
						<td class="tabBt" ><icms-i18n:label name="rm_l_serve_Name"/>：</td>
						<td ><input name="serverName"
							id="serverName" type="text" class="textInput readonly" /></td>	
						<td class="tabBt" ><label>平台类型：</label></td>
						<td class="tabCon">
							<icms-ui:dic name="platformId" id="platformId" sql="SELECT '1' as id, 
                               PLAY.PLATFORM_ID AS value,PLAY.PLATFORM_NAME AS name,'1' AS kind  FROM RM_PLATFORM PLAY WHERE 
                               PLAY.IS_ACTIVE='Y'" showType="select" attr="class='selInput'" />
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
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel">
								<span class="icon iconfont icon-icon-clear1"></span>
								<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
							</button>
							<shiro:hasPermission name="availableZone_sava">
							<a href="javascript:void(0)" type="button" class="btn" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' onclick="createData()">
								<span class="icon iconfont icon-icon-add"></span>
								<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="availableZone_delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="availableZone_update">
									<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="availableZone_show">
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
			<div class="panel clear" id="AvailableZoneGridTable_div">
				<table id="AvailableZoneTable"></table>
				<table id="AvailableZonePager"></table>
			</div>
		</div>
		</div>	

	<!-- 添加 修改-->
	<div id="updataAvailableZone" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataAvailableZoneForm">
				<input type="hidden" id="availableZoneMethod" name="availableZoneMethod" />
				<input type="hidden" id="availableZoneId" name="availableZoneId" />
				<input type="hidden" id="validateAvailableZoneName" name="validateAvailableZoneName" />
				<p>
					<i><font color="red">*</font><icms-i18n:label name="rm_l_visiAreaName"/>：</i>
					<input type="text" name="availableZoneName1" id="availableZoneName1" style="vertical-align:middle;" class="textInput" />
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="rm_l_serve_Name"/>：</i>
					<select id="addServerName" name="serverName" class="selInput" style="vertical-align:middle;"></select>
				</p>
				<p>
					<i><icms-i18n:label name="com_l_remark"/>：</i>
					<input type="text" name="remark" id="remark" style="vertical-align:middle;" class="textInput" />
				</p>
			<p class="winbtnbar btnbar1" style="text-align:right; width:278px; margin-bottom:20px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closePlatformrView()" style="margin-right: 15px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateAvailableZoneBtn()" style="margin-right: 5px">
			</p>
		</form>
	</div>	
</body>
</html>
