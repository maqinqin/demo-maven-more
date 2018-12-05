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
	$("#openstackSynckTable").jqGrid({
		url : ctx+"/resmgt-compute/os/getOpenstackSynckList.action",
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
							var volumeSynckFlag = $("#volumeSynckFlag").val();
							var s ="";
							var ret="";
							if(computeSynckFlag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showComputeSynck('"+rowObject.serverName+","+rowObject.manageIp+","+rowObject.datacenterId+","+rowObject.vmSmId+","+rowObject.domainName+","+rowObject.platformCode+"') >"+i18nShow('openstack_synch_com_res_pool')+"</a>";
							}
							if(volumeSynckFlag=="1"){
								ret += "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showVolumeSynck('"+rowObject.serverName+","+rowObject.manageIp+","+rowObject.datacenterId+","+rowObject.vmSmId+","+rowObject.domainName+","+rowObject.platformCode+"') >"+i18nShow('openstack_synch_volume_res_pool')+"</a>";
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
		    			records : "record",
		    			repeatitems : false
		    		},
		    		pager : "#openstackSynckPager",
//		    		caption : "设备信息记录",
		    		hidegrid : false
	});
	
	//自适应宽度
	$("#openstackSynckTable").setGridWidth($("#openstackSynckGridTable_div").width());
	$(window).resize(function() {
		$("#openstackSynckTable").setGridWidth($("#openstackSynckGridTable_div").width());
		$("#openstackSynckTable").setGridHeight(heightTotal() + 65);
    });	
});
//查询
function search(){
	$("#openstackSynckTable").jqGrid('setGridParam', {
		url : ctx + "/resmgt-compute/os/getOpenstackSynckList.action",//你的搜索程序地址
		postData : {
			"datacenterId" : $("#datacenter").val(),
			"serverName" : $("#vmMs").val(),
			"platformId" : $("#platformId").val()
		}, //发送搜索条件
		pager : "#openstackSynckPager"
	}).trigger("reloadGrid"); //重新载入
}
//同步计算资源池
function showComputeSynck(str){
	var arr = str.split(',');
	var serverName=arr[0];
	var manageIp=arr[1];
	var datacenterId=arr[2];
	var vmSmId=arr[3];
	var domainName=arr[4];
	var platForm = arr[5];
	showTip(i18nShow('openstack_synch_com_res_pool_confirm'),function(){
		$.post(ctx+"/resmgt-compute/os/SynckCompute.action",
			{"openstackSynckVo.serverName" : serverName,
			"openstackSynckVo.manageIp" : manageIp,
			"openstackSynckVo.domainName" : domainName,
			"openstackSynckVo.platForm" : platForm,
			"openstackSynckVo.datacenterId" : datacenterId,
			"openstackSynckVo.vmSmId" : vmSmId},
		function(data){
			showTip(i18nShow('openstack_synch_com_res_pool_succ'));
		})
	});
}
//同步存储资源池
function showVolumeSynck(str){
	var arr = str.split(',');
	var serverName=arr[0];
	var manageIp=arr[1];
	var datacenterId=arr[2];
	var vmSmId=arr[3];
	var domainName=arr[4];
	var platForm = arr[5];
	showTip(i18nShow('openstack_synch_volume_res_pool_confirm'),function(){
		$.post(ctx+"/resmgt-compute/os/SynckVolume.action",
			{"openstackSynckVo.serverName" : serverName,
			"openstackSynckVo.manageIp" : manageIp,
			"openstackSynckVo.domainName" : domainName,
			"openstackSynckVo.platForm" : platForm,
			"openstackSynckVo.datacenterId" : datacenterId,
			"openstackSynckVo.vmSmId" : vmSmId},
		function(data){
			search();
			showTip(i18nShow('openstack_synch_volume_res_pool_succ'));
		})
	});
}
</script>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_openstack"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
		<div class="searchInWrap_top">
		<div class="searchIn_left" style="width:66%; float:left;">
			<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:248px;"><icms-i18n:label name="com_l_dataCenName"></icms-i18n:label>：</td>
						<td class="tabCon">
							<icms-ui:dic id="datacenter" name="datacenter" attr="class='selInput'" showType="select" 
					sql="SELECT ID AS value , DATACENTER_CNAME AS name FROM RM_DATACENTER WHERE IS_ACTIVE = 'Y'" />
						</td>
						<td class="tabBt" style="width:267px;"><icms-i18n:label name="res_l_bl_openstackServer"></icms-i18n:label>：</td>
						<td class="tabCon">
						<icms-ui:dic id="vmMs" name="vmMs" attr="class='selInput'" showType="select" 
						sql="SELECT ID AS value , SERVER_NAME AS name FROM RM_VM_MANAGE_SERVER WHERE IS_ACTIVE = 'Y' AND (PLATFORM_TYPE = '4' or PLATFORM_TYPE = '5')" />
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
	  							<span>查询</span>
							</a>
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel"  >
								<span class="icon iconfont icon-icon-clear1"></span>
	  							<span>清空</span>
							</button>
							<shiro:hasPermission name="openstackSynck_show">
									<input type="hidden" id="showFlag" name="showFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="openstackComputeSynck_synch">
							<input type="hidden" id="computeSynckFlag" name="computeSynckFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="openstackVolumeSynck_synch">
							<input type="hidden" id="volumeSynckFlag" name="volumeSynckFlag" value="1" />
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
				<table id="openstackSynckTable"></table>
				<table id="openstackSynckPager"></table>
			</div>
		</div>
		
		</div>	
</body>
</html>
