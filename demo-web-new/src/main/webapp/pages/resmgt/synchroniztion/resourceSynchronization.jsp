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
<script type="text/javascript">
$(function(){
	$("#synchronizationTable").jqGrid({
		url : ctx+"/resmgt-synchronization/os/getReoSynck.action",
		rownumbers : true, 
		datatype : "json", 
		mtype : "post", 
		height : heightTotal() + 65,
		autowidth : true,
		//multiselect:true,
//		multiselect:true,
		colNames:['vmSmId','datacenterId',i18nShow('rm_datacenter'),i18nShow('rm_vm_ms_name'),'平台类型',i18nShow('rm_vm_ms_ip'),i18nShow('com_operate')],
		colModel : [ 
		            {name : "vmSmId",index : "vmSmId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "datacenterId",index : "datacenterId",width : 120,sortable : false,align : 'left',hidden:true},
		            {name : "datacenterName",index : "datacenterName",	width : 120,sortable : true,align : 'left'},
		            {name : "serverName",index : "serverName",	width : 120,sortable : true,align : 'left'},
		            {name : "platformName",index : "platformName",	width : 120,sortable : true,align : 'left'},
		            {name : "manageIp",index : "manageIp",	width : 120,sortable : true,align : 'left'},
		            {name:"option",index:"option",width:120,sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							var computeSynckFlag = $("#computeSynckFlag").val();
							//var volumeSynckFlag = $("#volumeSynckFlag").val();
							//var s ="";
							var ret="";
							if(computeSynckFlag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showComputeSynck('"+rowObject.manageIp+","+rowObject.vmSmId+"') >"+i18nShow('synchronous_distributed_switches')+"</a>";
							
							}
										
						return 	ret;
						}
		            }
		            ],
		            viewrecords : true,
		    		sortname : "vmSmId",
		    		rowNum : 10,
		    		rowList : [ 5, 10, 15, 20, 30 ],
		    		prmNames : {
		    			search : "search"
		    		},
		    		jsonReader : {
		    			root : "dataList",
		    			records : "record"-1,
		    			repeatitems : false
		    		},
		    		
		    		pager : "#synchronizationPager",
//		    		caption : "设备信息记录",
		    		hidegrid : false
	});
	
	//自适应宽度
	$("#synchronizationTable").setGridWidth($("#openstackSynckGridTable_div").width());
	$(window).resize(function() {
		$("#synchronizationTable").setGridWidth($("#openstackSynckGridTable_div").width());
		$("#synchronizationTable").setGridHeight(heightTotal() + 65);
    });	
});

//查询	
function search(){
	$("#synchronizationTable").jqGrid('setGridParam', {
		url : ctx + "/resmgt-synchronization/os/getReoSynck.action",//你的搜索程序地址
		postData : {
			"serverName" : $("#searchValue_Name").val(),	
			"manageIp":$("#searchValue_IP").val(),
		}, //发送搜索条件
		pager : "#synchronizationPager"
	}).trigger("reloadGrid"); //重新载入
}	
	
//同步分布式交换机
function showComputeSynck(str) {
		var arr = str.split(',');
		var manageIp = arr[0];//获取IP
		var vmSmId = arr[1];//获取ID
		showTip(i18nShow('synchronize_distributed_switches_confirm'),function() {
					$.post(ctx + "/resmgt-synchronization/os/SynckDistributed.action",{
										"synchronizationVo.vmSmId" : vmSmId,
									}, function(data) {
										showTip(i18nShow('synchronous_distributed_switch_successful'));
									})

				});

		
	}
</script>
</head>
<body class="main1">
<div id="list" clasrs="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="res_title_resource_resource_synchronization"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
		<div class="searchInWrap_top">
		<div class="searchIn_left" style="width:55%; float:left;">
			<div class="searchInOne" >
							<table height="12%" width="100%" align="center">
								<tr style="height: 55px" align="right">								
									<td class="tabBt" style="width: 650px; text-align:left;">
									<label name="rm_l_serve_Name" style="margin-left:80px">IP：</label>									
									<input type="text" id="searchValue_IP" style="text-align:left; width:180px; height:25px;margin-left:20px;" >
								
									<label name="rm_l_serve_Name" style="margin-left:60px">服务器名称：</label>
									<input type="text" id="searchValue_Name" style="text-align:left; width:180px; height:25px;margin-left:20px;" ></td>
									
								</tr>

							</table>
						</div>
		</div>
		
		<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="left">
						<tr style=" height:52px;">
						<td style="text-algin:left;margin-right:300px">
							<a href="javascript:search()" type="button"  style="margin-left:50px;"class="btn" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' onclick="search();return false;">
								<span class="icon iconfont icon-icon-search"></span>
	  							<span>查询</span>
							</a>
							<button style="margin-left:80px;" type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel"  >
								<span class="icon iconfont icon-icon-clear1"></span>
	  							<span>清空</span>
							</button>
							<shiro:hasPermission name="openstackSynck_show">
									<input type="hidden" id="showFlag" name="showFlag" value="1" />
							</shiro:hasPermission>
							
							<shiro:hasPermission name="openstackComputeSynck_synch">
								<input type="hidden" id="computeSynckFlag" name="computeSynckFlag" value="1" />
							</shiro:hasPermission>
							
						</td>
						</tr>
					</table>
		</div>
		</div>
			</form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="openstackSynckGridTable_div">
				<table id="synchronizationTable"></table>
				<table id="synchronizationPager"></table>
			</div>
		</div>
		
		</div>	
</body>
</html>
