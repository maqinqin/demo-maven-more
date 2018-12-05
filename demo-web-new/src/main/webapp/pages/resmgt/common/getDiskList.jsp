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
#updataProjectVpcForm p{width:300px; padding:0; padding-left:16px;}
#updataProjectVpcForm p i{width:100px; text-align:left;}
#updataProjectVpcForm label{display:block; padding-left:100px;}
</style>
<script type="text/javascript">
$(function(){
$("#projectVpcTable").jqGrid({
	url : ctx+"/resmgt-common/datacenter/getDiskList.action",
// 	rownumbers : true, 
	datatype : "json", 
	mtype : "post", 
	height : 345,
	autowidth : true,
	//multiselect:true,
//	multiselect:true,
	colNames:['ID','USER_ID','长度','状态','SHAREABLE','创建日期','操作'],
	colModel : [ 
	            {name : "id",index : "id",width : 120,sortable : false,align : 'left'},
	            {name : "user_id",index : "user_id",	width : 120,sortable : true,align : 'left'},
	            {name : "size",index : "size",	sortable : true,align : 'left',editor:"text"},
	            {name : "status",index : "status",	sortable : true,align : 'left',editor:"text"},
	            {name : "shareable",index : "shareable",sortable : true,align : 'left',editor:"text"},
	            {name : "created_at",index : "created_at",	width : 120,sortable : true,align : 'left'},
	            {name:"option",index:"option",width:120,sortable:false,align:"left",
					formatter:function(cellVall,options,rowObject){
						var updateflag = $("#getListFlag").val();
						var s ="";
						var ret="";
						if(updateflag=="1"){
							ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdate('"+rowObject.id+"') >查看详细</a>";
						}
					return 	ret;
					}
	            }
	            ],
	            viewrecords : true,
	    		sortname : "ID",
	    		rowNum : 10,
	    		rowList : [ 5, 10, 15, 20, 30 ],
	    		prmNames : {
	    			search : "search"
	    		},
	    		jsonReader : {
	    			root : "volumes",
	    			records : "record",
	    			repeatitems : false
	    		},
	    		pager : "#projectVpcPager",
	    		hidegrid : false
	});

//自适应宽度
$("#projectVpcTable").setGridWidth($("#projectVpcGridTable_div").width());
$(window).resize(function() {
	$("#projectVpcTable").setGridWidth($("#projectVpcGridTable_div").width());
});	
	
/**************表单验证开始****************/
/* jQuery.validator.addMethod("checkVpcName", function() { 
	var validateValue=true;
	var projectVpcMethod = $("#projectVpcMethod").val();
	var addIsActive = "Y";
	var projectVpcName1 = $("#projectVpcName1").val();
	var projectVpcId = $("#projectVpcId").val();
	var addDatacenterId=$("#addDatacenterId").val();
	var addVmMsId=$("#addVmMsId").val();
	var validateProjectVpcName=$("#validateProjectVpcName").val();
	var vpcId;
	if (projectVpcMethod != "update")
	{
		vpcId = "";
	}
	$.ajax({
		type:'post',
		datatype : "json",
		data : {'projectVpcPo.vpcId':projectVpcId,'projectVpcPo.vpcName':projectVpcName1,'projectVpcPo.datacenterId':addDatacenterId,'projectVpcPo.vmMsId':addVmMsId,'projectVpcPo.idActive':addIsActive},
		url:ctx+"/network/checkVpcName.action",
		async : false,
		success:(function(data){
//			alert(data.result);
			if(projectVpcMethod=="update"){
				if(data==null||data.vpcName==validateProjectVpcName){
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
"vpc名称已存在！");
$("#updataProjectVpcForm").validate({
	rules: {
		projectVpcName1:{required: true,checkVpcName:true},
		datacenterId:{required: true},
		vmMsId:{required: true}
	},
	messages: {
		projectVpcName1:{required: "vpc名称不能为空",checkVpcName:"vpc名称已存在！"},
		datacenterId:{required: "请选择数据中心"},
		vmMsId:{required: "请选择服务器"}
	},
	
	submitHandler: function() {
		updateOrSaveProjectVpc();
	}
}); */

initDatacenterSelect();
initServerNameSelect();
});

//初始化服务器名称下拉框
/* function initServerNameSelect()
{
	$("#vmMsId").html("<option value=''>请选择...</option>");
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
	$("#datacenterId").html("<option value=''>请选择...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/converge/queryDatacenterList.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#datacenterId").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}

//初始化新增  下拉框
function initAddDatacenterSelect()
{
	$("#addDatacenterId").html("<option value=''>请选择...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/network/converge/queryDatacenterList.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#addDatacenterId").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}
function initAddServerNameSelect()
{
	$("#addVmMsId").html("<option value=''>请选择...</option>");
	$.ajaxSettings.async = false;
	$.getJSON(ctx+"/resmgt-compute/az/getAllServerName.action", {"_":new Date().getTime()}, function(data) {
		$.each(data, function() {
			$("#addVmMsId").append("<option value='" + this.id + "'>" + this.name + "</option>");
		});
	});	
}
//查询
function search() {
		$("#projectVpcTable").jqGrid('setGridParam', {
			url : ctx + "/network/getProjectVpcList.action",//你的搜索程序地址
			postData : {
				"vpcName" : $("#vpcName").val(),
				"datacenterId" : $("#datacenterId").val(),
				"vmMsId" : $("#vmMsId").val(),
			}, //发送搜索条件
			pager : "#projectVpcPager"
		}).trigger("reloadGrid"); //重新载入
} */

//弹出添加页面
/* function createData(){
		  $("label.error").remove();
		  $("#updataProjectVpc").dialog({
			autoOpen : true,
			modal:true,
			height:320,
			width:400,
			position: "middle",
			title:'新增vpc',
			//resizable:false
	 });
	    $("#projectVpcName1").val("");
	    $("#remark").val("");
// 	    initAddDatacenterSelect();
// 	    initAddServerNameSelect();
	    $("#projectVpcMethod").val("save");
}
//关闭窗口
function closePlatformrView(){	  
	  $("#updataProjectVpc").dialog("close");
}
//点击保存按钮提交
function saveOrUpdateProjectVpcBtn(){
	$("#updataProjectVpcForm").submit();  
}
function updateOrSaveProjectVpc(){
	var projectVpcMethod = $("#projectVpcMethod").val();
	var projectVpcId=$("#projectVpcId").val();
	var projectVpcName1 = $("#projectVpcName1").val();
	var addremark=$("#remark").val();
	var addDatacenterId=$("#addDatacenterId").val();
	var addVmMsId=$("#addVmMsId").val();
	var url;
	if(projectVpcMethod=="update"){
		url= ctx+"/network/updateProjectVpc.action"
	}else{
		url= ctx+"/network/saveProjectVpc.action"
		
	}
	var data = {'projectVpcPo.vpcName':projectVpcName1,
				'projectVpcPo.vpcId':projectVpcId,
				'projectVpcPo.remark':addremark,
				'projectVpcPo.datacenterId':addDatacenterId,
				'projectVpcPo.vmMsId':addVmMsId
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
			showTip('保存成功');   
			$("#updataProjectVpc").dialog("close");
			$("#projectVpcTable").jqGrid().trigger("reloadGrid");
		}),
		error:function(XMLHttpRequest, textStatus, errorThrown){
			closeTip();
			showTip("保存失败:"+XMLHttpRequest.responseText);
		} 
	});
} */
//弹出修改vpc窗口
function showUpdate(id){
	$("#showDiskDiv").dialog({
		autoOpen : true,
		modal:true,
		height:500,
		title:'查看镜像',
		width:950
	});
	
	$.post(ctx+"/resmgt-common/datacenter/getDiskDetailed.action",{"id" : id},function(data){
// 		alert(data.volume.id);
		$("#imageNameLab").val(data.volume.user_id);
		$("#imagePathLab").val(data.volume.updated_at);
		$("#imageUrlLab").val(data.volume.bootable);
		$("#imageSizeLab").val(data.volume.availability_zone);
		$("#remarkLab").val(data.volume.status);
		$("#managerLab").val(data.volume.size);
	});
	/* 	   $("label.error").remove();
	   $("#updataProjectVpc").dialog({
			autoOpen : true,
			modal:true,
			height:320,
			width:400,
			position: "middle",
			title:'更新vpc信息',
//				draggable: false,selectCmtDatastoreVoById
		       // resizable:false
		})
		$.post(ctx+"/network/selectProjectVpc.action",{"projectVpcPo.vpcId" : id},function(data){
// 		    initAddDatacenterSelect();
// 		    initAddServerNameSelect();
			$("#projectVpcId").val(data.vpcId);
			$("#projectVpcName1").val(data.vpcName);
			$("#validateProjectVpcName").val(data.vpcName);
			$("#remark").val(data.remark);
			$("#addDatacenterId").val(data.datacenterId);
			$("#addVmMsId").val(data.vmMsId);
			$("#projectVpcMethod").val("update");
		})	*/
} 
//删除vpc
/* function deleteData(dataId){
	if(dataId){
		
			showTip("确定删除数据?",function(){
				
				if(projectVpcUseValidate(dataId)){
					$.post(ctx+"/network/deleteProjectVpc.action",{
						"projectVpcPo.vpcId":dataId
					},function(data){ 
						search();
						showTip("删除成功!");
					});
				}else{
	        		return false;
		        }
			
			});
		}else{
			var ids = jQuery("#projectVpcTable").jqGrid('getGridParam','selarrrow');

			if(ids.length == 0){
				showError("至少选择一条数据!");
				return;
			}
			var list = [];
			$(ids).each(function (index,id2){
				var rowData = $("#projectVpcTable").getRowData(id2);
				list[list.length] = rowData.vpcId;
				})
		showTip("确定删除数据?",function(){
			if(projectVpcUseValidate(list.join(","))){
				$.post(ctx+"/network/deleteProjectVpc.action",{
					"projectVpcPo.vpcId":list.join(",")
				},function(data){ 
					search();
					showTip("删除成功!");
				});
	        }else{
	        	//showTip("无法删除!");
	        	return false;
	        }
			});
		}
} */
/* function projectVpcUseValidate(id) {
	var flag = false;
    $.ajax({
		type : 'post',
		datatype : "json",
		url : ctx+"/network/deleteProjectVpcCheck.action",
		async : false,
        data : {// 获得表单数据
        	"projectVpcPo.vpcId" : id
		}, success : function(data) {
			var cs=""
			var reason="";
		    var result = "";
			for(var key in data){
				if(data[key] == "") {
					continue;
		      }
			 result += "删除失败（";
				var temps=data[key];
				var temp=temps.split(",");
				var rs=new Array(1);
				 reason="";
				for(var i=0;i<temp.length;i++){
					 if(temp[i]=="pv"){
						reason += "被网络资源池占用";
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
			showTip("发生错误",null,"red");
		}
		
	});
     return flag;
} */
</script>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				查看卷列表
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent">
			<form>
		<div class="searchInWrap_top">
		<!-- <div class="searchIn_left" style="width:66%; float:left;">
			<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:200px;">卷名称：</td>
						<td class="tabCon"><input name="vpcName" id="vpcName"
							type="text" class="textInput readonly" /></td>
						<td class="tabBt" style="width:200px;">数据中心：</td>
						<td class="tabCon">
							<select id="datacenterId" class="selInput">
								<option  id="" value="">请选择...</option>
							</select>
						</td>
						<td class="tabBt" style="width:250px;">服务器名称：</td>
						<td class="tabCon">
							<select id="vmMsId" class="selInput">
								<option  id="" value="">请选择...</option>
							</select>
						</td>
						<td class="tabBt" ></td>
						<td class="tabCon"></td>
						
					</tr>
					
				</table>
			</div>
		</div> -->
		
		<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
											
						<td>
							<!-- <input type="button" class="Btn_Serch" title="查询" onclick="search()" />
							
							<input type="reset" title="清空" class="Btn_Empty" style="text-indent:-999px;" />
							<input type="button" class="Btn_Add" title="添加" onclick="createData()" /> -->
							<input type="hidden" id="getListFlag" name="getListFlag" value="1" />
<!-- 							<input type="hidden" id="showFlag" name="showFlag" value="1" /> -->
							
							
							<%-- <input type="button" class="Btn_Serch" title="查询" onclick="search()" />
							<input type="reset" title="清空" class="Btn_Empty" style="text-indent:-999px;" />
							<shiro:hasPermission name="projectVpc_sava">
							<input type="button" class="Btn_Add" title="添加" onclick="createData()" />
							</shiro:hasPermission> --%>
							<%-- <shiro:hasPermission name="projectVpc_delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="projectVpc_update">
									<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="projectVpc_show">
									<input type="hidden" id="showFlag" name="showFlag" value="1" />
							</shiro:hasPermission> --%>
							
						</td>
						</tr>
					</table>
		</div>
		</div>
			</form>
		</div>
		<div class="panel clear" id="projectVpcGridTable_div">
			<table id="projectVpcTable"></table>
			<table id="projectVpcPager"></table>
		</div>
		</div>	

	<!-- 添加 修改-->
  	<!-- <div id="updataProjectVpc" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataProjectVpcForm">
				<input type="hidden" id="projectVpcMethod" name="projectVpcMethod" />
				<input type="hidden" id="projectVpcId" name="projectVpcId" />
				<input type="hidden" id="validateProjectVpcName" name="validateProjectVpcName" />
				<p>
					<i><font color="red">*</font>vpc名称：</i>
					<input type="text" name="projectVpcName1" id="projectVpcName1" style="vertical-align:middle;" class="textInput" />
				</p>
				<p>
					<i><font color="red">*</font>数据中心：</i>
					<select id="addDatacenterId" class="selInput" name="datacenterId">
						<option  id="" value="">请选择...</option>
					</select>
				</p>
				<p>
					<i><font color="red">*</font>服务器名称：</i>
					<select id="addVmMsId" class="selInput" name="vmMsId">
						<option  id="" value="">请选择...</option>
					</select>
				</p>
				<p>
					<i>备注：</i>
					<input type="text" name="remark" id="remark" style="vertical-align:middle;" class="textInput" />
				</p>
			<p class="winbtnbar" style="text-align:right; width:265px; margin-bottom:20px;">
				<input type="button" class="btn_gray" title="保存" value="保存" onclick="saveOrUpdateProjectVpcBtn()" style="margin-right: 0px">
				<input type="button" class="btn_gray" title="取消" value="取消" onclick="closePlatformrView()" style="margin-left: 0px">
			</p>
		</form>
	</div>	 -->
	<!--显示卷详细  -->
	<div id="showDiskDiv"style="display: none;" >
		<div  id="showTab"  class="pageFormContent">	
			<p>
				<i>镜像名称:</i><input name="imageNameLab" id="imageNameLab" type="text" class="readonly"  size="30" readonly="readonly">
			</p>
			<p>
				<i>最小容量:</i><input name="imageSizeLab" id="imageSizeLab" type="text" class="readonly"  size="30" readonly="readonly">
			</p>
			<p>
				<i>磁盘容量(G):</i><input name="diskCapacity" id="diskCapacity" type="text" class="readonly"  size="30" readonly="readonly">
			</p>
			<p>
				<i>用户名:</i><input name="managerLab" id="managerLab" type="text" class="readonly"  size="30" readonly="readonly"> 
			</p>
			<p>
				<i>镜像存储路径:</i><input name="imagePathLab" id="imagePathLab" type="text" class="readonly"  size="30" readonly="readonly">
			</p>
			<p style="width: 900px">
				<i>镜像存储URL:</i><input name="imageUrlLab" id="imageUrlLab" type="text" class="readonly" style="width: 770px; "   size="30" readonly="readonly">
			</p>
			<p  style="width: 900px">
				<i>状态:</i><input name="remarkLab" id="remarkLab" type="text" class="readonly"  size="30"style="width: 770px;"  readonly="readonly"> 
			</p>
		</div>
	</div>
</body>
</html>
