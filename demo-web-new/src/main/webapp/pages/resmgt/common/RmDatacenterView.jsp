<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>数据中心管理</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript">
$(function() {
	
	$("#datacenterGridTable").jqGrid({
		url : ctx+"/resmgt-common/datacenter/getDatacenterList.action", 
		rownumbers : true, 
		datatype : "json", 
		mtype : "post", 
		height : heightTotal() + 90,
		autowidth : true,
		//multiselect:true,
//		multiselect:true,
		colNames:['id',i18nShow('rm_datacenter_code'),i18nShow('rm_datacenter_name'),i18nShow('rm_datacenter_ename'),i18nShow('rm_datacenter_addr'),i18nShow('rm_datacenter_queueIden'),i18nShow('com_remark'),i18nShow('com_operate')],
		colModel : [ 
		            {name : "id",index : "id",width : 120,sortable : true,align : 'left',hidden:true},
		            {name : "datacenterCode",index : "datacenterCode",	width : 120,sortable : true,align : 'left'},
		            {name : "datacenterCname",index : "datacenterCname",	width : 120,sortable : true,align : 'left'},
		            {name : "ename",index : "ename",	width : 120,sortable : true,align : 'left'},
		            {name : "address",index : "address",	width : 120,sortable : true,align : 'left'},
		            {name : "queueIden",index : "queueIden",	width : 120,sortable : true,align : 'left'},
		            {name : "remark",index : "remark",	width : 120,sortable : false,align : 'left'},
		            {name:"option",index:"option",width:120,sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							var updateflag = $("#updateFlag").val();
							var deleteFlag = $("#deleteFlag").val();
							var ret = "";
							if(updateflag=="1"){
								//ret += "<input type='button' class='btn_edit_s' onclick=showUpdate('"+rowObject.id+"')  title='修改' />&nbsp;&nbsp;"
								ret +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdate('"+rowObject.id+"') >"+i18nShow('com_update')+"</a>";
							}
							if(deleteFlag=="1"){
								//ret += "<input type='button' style='margin-left:5px;' class='btn_del_s' onclick=deleteData('"+rowObject.id+"') title='删除'/>&nbsp;&nbsp;"
								ret +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteData('"+rowObject.id+"')>"+i18nShow('com_delete')+"</a>";
							}
							
							return 	ret;
							
// 							return 	"<input type='button' style=\" margin-right: 10px;\" class='btn_edit_s' onclick=showUpdate('"+rowObject.id+"')  title='修改' /><input type='button' style=\" margin-right: 10px;\" class='btn_del_s' onclick=deleteData('"+rowObject.id+"')  title='删除' />";
							
						}
		            }
		            /*<a href='#' style=' text-decoration:none' onclick=deleteData('"+rowObject.rulesId+"')>删除</a>*/
		            ],
		            viewrecords : true,
		    		sortname : "id",
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
		    		pager : "#datacenterPager",
//		    		caption : "设备信息记录",
		    		hidegrid : false
	});
	jQuery.validator.addMethod("queueIdenTrim", function(value, element) { 
		var validateValue=true;
		var datacenterMethod=$("#datacenterMethod").val();
		var validateQueueIden = $("#validateQueueIden").val();
		if(value!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmDatacenterPo.queueIden":value},
			url:ctx+"/resmgt-common/datacenter/selectQueueIdenfortrim.action",
			async : false,
			success:(function(data){
				if(datacenterMethod=="update"){
					if(data==null||data.queueIden==validateQueueIden){
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
	//英文名称不重复验证
	jQuery.validator.addMethod("DCenameTrim", function(value, element) { 
		var validateValue=true;
		var datacenterMethod=$("#datacenterMethod").val();
		var validateEname = $("#validateEname").val();
		if(value!=""){
		$.ajax({
			type:'post',
			datatype : "json",
			data:{"rmDatacenterPo.ename":value},
			url:ctx+"/resmgt-common/datacenter/selectDCenamefortrim.action",
			async : false,
			success:(function(data){
				if(datacenterMethod=="update"){
					if(data==null||data.ename==validateEname){
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
	"数据中心英文名称不能重复"); 
	jQuery.validator.addMethod("queueIdenCheck", function(value, element) { 
		return this.optional(element) || /^[a-zA-Z0-9]*$/g.test(value);     
		},
	"只能包括英文字母数字");
	$("#updateDatacenterForm").validate({
		rules: {
			datacenterCode:{required: true},
			datacenterCname1:{required: true},
			ename:{required: true,DCenameTrim:true},
			address:{required: true},
			queueIden1:{required: true,maxlength:4,queueIdenCheck:true,queueIdenTrim:true}
		},
		messages: {
			datacenterCode:{required: i18nShow('validate_data_required')},
			datacenterCname1:{required: i18nShow('validate_data_required')},
			ename:{required: i18nShow('validate_data_required'),DCenameTrim:i18nShow('validate_data_remote')},
			address:{required: i18nShow('validate_data_required')},
			queueIden1:{required: i18nShow('validate_data_required'),maxlength:i18nShow('validate_rm_datacenter_length'),queueIdenCheck:i18nShow('validate_rm_datacenter_stringCheck'),queueIdenTrim:i18nShow('validate_data_remote')}
		},
// 		errorPlacement:function(error,element){
//    		error.insertAfter("#device_error_tip");
//    	},
		submitHandler: function() {
			updateOrSaveDatacenterData();
		}
	});
	//自适应宽度
	$("#datacenterGridTable").setGridWidth($("#datacenterGridTable_div").width());
	$(window).resize(function() {
		$("#datacenterGridTable").setGridWidth($("#datacenterGridTable_div").width());
		$("#datacenterGridTable").setGridHeight(heightTotal() + 90);
    });
	
});	
  function createData(){
	  $("label.error").remove();
	  $("#updataRmDatacenter").dialog({
		autoOpen : true,
		modal:true,
		height:270,
		width:654,
		title:i18nShow('rm_datacenter_save'),
		//resizable:false
 });
    $("#datacenterCode").val("");
    $("#datacenterCname1").val("");
    $("#ename").val("");
    $("#address").val("");
    $("#queueIden1").val("");
    $("#remark").val("");
    $("#datacenterMethod").val("save");
}
  //关闭窗口
  function closeDatacenterView(){
	  
	  $("#updataRmDatacenter").dialog("close");
  }
//提交表单
function saveOrUpdateDatacenterBtn(){
	$("#updateDatacenterForm").submit();  
}
//将数据提交到后台
 function updateOrSaveDatacenterData(){
			var datacenterMethod=$("#datacenterMethod").val();
			var datacenterCode=$("#datacenterCode").val();
			var datacenterCname=$("#datacenterCname1").val();
			var ename = $("#ename").val();
			var address=$("#address").val();
			var queueIden=$("#queueIden1").val();
			var remark=$("#remark").val();
			var datacenterid=$("#datacenterid").val();
		    var url;
				if(datacenterMethod=="update"){
					url= ctx+"/resmgt-common/datacenter/updateRmDatacenter.action"
				}else{
					url= ctx+"/resmgt-common/datacenter/saveRmDatacenter.action"
				}
				var data = {
						'rmDatacenterPo.datacenterCode':datacenterCode,
						'rmDatacenterPo.datacenterCname':datacenterCname,
						'rmDatacenterPo.ename':ename,
						'rmDatacenterPo.address':address,
						'rmDatacenterPo.queueIden':queueIden,
						'rmDatacenterPo.remark':remark,
						'rmDatacenterPo.id':datacenterid
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
						$("#updataRmDatacenter").dialog("close");
						$("#datacenterGridTable").jqGrid().trigger("reloadGrid");
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('tip_save_fail'));   
					} 
				});
			}
   function showUpdate(id){
	   $("label.error").remove();
		$("#updataRmDatacenter").dialog({
				autoOpen : true,
				modal:true,
				height:260,
				width:650,
				title:i18nShow('rm_datacenter_update'),
//				draggable: false,selectCmtDatastoreVoById
		        //resizable:false
		})
		$.post(ctx+"/resmgt-common/datacenter/getDataCenterById.action",{"dataCenterId" : id},function(data){
			$("#datacenterCode").val(data.datacenterCode);
			$("#datacenterCname1").val(data.datacenterCname);
			$("#ename").val(data.ename);
			$("#address").val(data.address);
			$("#queueIden1").val(data.queueIden);
			$("#remark").val(data.remark);
			$("#datacenterid").val(data.id);
			$("#datacenterMethod").val("update");
			$("#validateQueueIden").val(data.queueIden);
			$("#validateEname").val(data.ename);
		})
	   
	   
   }
   function deleteData(dataId){
	   var count=0;
	   var datacenterName="";
	   var flag=false;
		if(dataId){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-common/datacenter/selectPoolByDatacenterId.action",
				async : false,
				data:{"dataCenterId":dataId},
				success:(function(data){
					count=data.count;
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('tip_req_fail'));
				} 
			});
			
			if(count==0){
			showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-common/datacenter/deleteDatacenter.action",
				async : false,
				data:{"rmDatacenterPo.id":dataId},
				success:(function(data){
					showTip(i18nShow('tip_delete_success'));
					$("#datacenterGridTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('tip_delete_fail'));
				} 
			});
			});
//                    showTip("可以删除"); 
			}else{
				showTip(i18nShow('tip_rm_datacenter_use'));
			}
		}else{
			var ids = jQuery("#datacenterGridTable").jqGrid('getGridParam','selarrrow');
			
			if(ids.length == 0){
				showError(i18nShow('error_select_one_data'));
				return;
			}
			$(ids).each(function (index,id2){//查看所有选中的数据中心下是否有资源池
				$.ajax({
					type:'post',
					datatype : "json",
					url:ctx+"/resmgt-common/datacenter/selectPoolByDatacenterId.action",
					async : false,
					data:{"dataCenterId":id2},
					success:(function(data){
						count=data.count;
						datacenterName=data.datacenterName;
					}),
					error:function(XMLHttpRequest, textStatus, errorThrown){
						showError(i18nShow('tip_req_fail'));
					} 
				});
				if(count!=0){
					flag=true;
					return false;
				}
				});
				if(flag){
					showTip(i18nShow('tip_rm_datacenter_use1')+datacenterName+i18nShow('tip_rm_datacenter_use2'));
					return;
				}

			var list = [];
			$(ids).each(function (index,id2){
				var rowData = $("#datacenterGridTable").getRowData(id2);
				list[list.length] = rowData.id;
				})
		showTip(i18nShow('tip_delete_confirm'),function(){
			$.ajax({
				type:'post',
				datatype : "json",
				url:ctx+"/resmgt-common/datacenter/deleteDatacenter.action",
				async : false,
				data:{"rmDatacenterPo.id":list.join(",")},
				success:(function(data){
					showTip(i18nShow('tip_delete_success'));
					$("#datacenterGridTable").jqGrid().trigger("reloadGrid");
				}),
				error:function(XMLHttpRequest, textStatus, errorThrown){
					showError(i18nShow('tip_delete_fail'));
				} 
			});
		});
		};
		
	}
//查询
   function search() {
		$("#datacenterGridTable").jqGrid('setGridParam', {
			url : ctx + "/resmgt-common/datacenter/getDatacenterList.action",//你的搜索程序地址
			postData : {
				"datacenterCname" : $("#datacenterCname").val().replace(/(^\s*)|(\s*$)/g, ""),
				"queueIden" : $("#queueIden").val().replace(/(^\s*)|(\s*$)/g, "")
			}, //发送搜索条件
			pager : "#datacenterPager"
		}).trigger("reloadGrid"); //重新载入
	}

</script>
<style type="text/css">
#updataRmDatacenter i {text-align:left; padding-right:3px;}
#updataRmDatacenter p{width:600px; margin-bottom:5px; margin-left:18px;  }
#updataRmDatacenter p .updateDiv_list{ display:inline-block; width:296px; float:left;}
#updataRmDatacenter label{display:block; padding-left:100px;}
</style>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_datacenMan"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
				<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:72px;"><icms-i18n:label name="com_l_name"></icms-i18n:label>：</td>
						<td class="tabCon"><input name="datacenterCname"
							id="datacenterCname" type="text" class="textInput readonly" /></td>
						<td class="tabBt" style="width:90px;" ><icms-i18n:label name="bim_l_datacenIdenti"></icms-i18n:label>：</td>
						<td class="tabCon"><input name="queueIden" id="queueIden"
							type="text" class="textInput readonly" /></td>
						<td class="tabBt" style="width:50px;" ></td>
						<td class="tabCon" style="width:130px;"></td>
				</tr>
				</table>
			</div>
		</div>
		<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%">
						<tr style=" height:52px;">
							<td>
								<a href="javascript:search()" type="button" class="btn" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' onclick="search();return false;" >
									<span class="icon iconfont icon-icon-search"></span>
									<span><icms-i18n:label name="com_btn_query"></icms-i18n:label></span>
								</a>
								<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" >
									<span class="icon iconfont icon-icon-clear1"></span>
									<span><icms-i18n:label name="com_btn_clear"></icms-i18n:label></span>
								</button>
								<!-- style="text-indent:-999px;" -->
								<shiro:hasPermission name="rmDatacenter_sava">
								<a href="javascript:void(0)" type="button" class="btn" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' onclick="createData()" >
									<span class="icon iconfont icon-icon-add"></span>
									<span><icms-i18n:label name="com_btn_append"></icms-i18n:label></span>
								</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="rmDatacenter_delete">
								<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
								</shiro:hasPermission>
						  </td>
								<shiro:hasPermission name="rmDatacenter_update">
										<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
								</shiro:hasPermission>
						</tr>
					</table>
		</div>
	</div>
			</form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="datacenterGridTable_div">
			<table id="datacenterGridTable"></table>
			<table id="datacenterPager"></table>
		</div>
		</div>

		</div>
	<div id="updataRmDatacenter" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updateDatacenterForm">
				<input type="hidden" id="datacenterMethod" name="datastoreMethod" />
				<input type="hidden" id="datacenterid" name="datacenterid" />
				<input type="hidden" id="validateQueueIden" name="validateQueueIden" />
				<input type="hidden" id="validateEname" name="validateEname" />
				<p>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="bim_l_datacenCode"></icms-i18n:label>：</i>
					<input type="text" name="datacenterCode" id="datacenterCode" style="width: 140px;" class="textInput" />
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="com_l_name"></icms-i18n:label>：</i>
					<input type="text" name="datacenterCname1" id="datacenterCname1" style="width: 140px;" class="textInput" />
				</span>
				
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="bim_l_enName"></icms-i18n:label>：</i>
					<input type="text" name="ename" id="ename" style="width: 140px;"class="textInput"  />
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="bim_l_datacenAddr"></icms-i18n:label>：</i>
					<input type="text" name="address" id="address" style="width: 140px;"class="textInput"  />
				</span>
			
				<span class="updateDiv_list">
					<i><font color="red">*</font><icms-i18n:label name="bim_l_datacenIdenti"></icms-i18n:label>：</i>
					<input type="text" name="queueIden1" id="queueIden1" style="width: 140px;"class="textInput"  />
				</span>
				<span class="updateDiv_list">
					<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</i>
					<input type="text" name="remark" id="remark" style="width: 140px;"class="textInput"  />
				</span>
				</p>
			<p class="winbtnbar btnbar1" style="margin-bottom:20px; width:555px; padding-right:49px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeDatacenterView()" style="margin-left: 0px; margin-right:5px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateDatacenterBtn()" style="margin-right: 5px; margin-left:0;">
			</p>
		</form>
	</div>
</body>
</html>
