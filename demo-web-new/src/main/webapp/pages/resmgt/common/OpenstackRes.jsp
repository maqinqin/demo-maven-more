<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>存储卷页面</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript">


$(function() {
	$("#openstackResGridTable").jqGrid({
		url : ctx+"/resmgt-common/openstackRes/getOpenstackResList.action", 
		rownumbers : true, 
		datatype : "json", 
		mtype : "post", 
		height : heightTotal() + 85,
		autowidth : true,
		colNames:['id',i18nShow('openstack_volume_type'),i18nShow('openstack_back_storage_name1'),'平台类型',i18nShow('rm_datacenter'),i18nShow('rm_openstack_server_name')],
		colModel : [ 
		            {name : "id",index : "id",width : 120,sortable : true,align : 'left',hidden:true},
		            {name : "volumeType",index : "volumeType",	width : 300,sortable : true,align : 'left'},
		            {name : "backStorage",index : "backStorage",	width : 300,sortable : true,align : 'left'},
		            {name : "platformName",index : "platformName",	width : 300,sortable : true,align : 'left'},
		            {name : "datacenterCname",index : "datacenterCname",	width : 300,sortable : true,align : 'left'},
		            {name : "serverName",index : "serverName",	width : 300,sortable : true,align : 'left'},
		            /* {name:"option",index:"option",width:120,sortable:false,align:"left",
						formatter:function(cellVall,options,rowObject){
							var updateflag = $("#updateFlag").val();
							var deleteFlag = $("#deleteFlag").val();
							var ret = "";
							if(updateflag=="1"){
								//ret += "<input type='button' class='btn_edit_s' onclick=showUpdate('"+rowObject.id+"')  title='修改' />&nbsp;&nbsp;"
								ret +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title='' onclick=showUpdate('"+rowObject.id+"') >修改</a>";
							}
							if(deleteFlag=="1"){
								//ret += "<input type='button' style='margin-left:5px;' class='btn_del_s' onclick=deleteData('"+rowObject.id+"') title='删除'/>&nbsp;&nbsp;"
								ret +="<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=deleteData('"+rowObject.id+"')>删除</a>";
							}
							return 	ret;
							
// 							return 	"<input type='button' style=\" margin-right: 10px;\" class='btn_edit_s' onclick=showUpdate('"+rowObject.id+"')  title='修改' /><input type='button' style=\" margin-right: 10px;\" class='btn_del_s' onclick=deleteData('"+rowObject.id+"')  title='删除' />";
							
						}
		            } */
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
		    		pager : "#openstackResPager",
//		    		caption : "设备信息记录",
		    		hidegrid : false
	});
	
	//自适应宽度
	$("#openstackResGridTable").setGridHeight(heightTotal() + 85);
		$(window).resize(function() {
			$("#openstackResGridTable").setGridHeight(heightTotal() + 85);
		});
}
)


//查询
function search(){
	$("#openstackResGridTable").jqGrid('setGridParam', {
		url : ctx + "/resmgt-common/openstackRes/getOpenstackResList.action",//你的搜索程序地址
		postData : {
			"datacenterId" : $("#datacenter").val(),
			"serverName" : $("#Vms").val().replace(/(^\s*)|(\s*$)/g, ""),
			"platformId" : $("#platformId").val()
		}, //发送搜索条件
		pager : "#openstackResPager"
	}).trigger("reloadGrid"); //重新载入
}
</script>
<style type="text/css">
#updataRmDatacenter i {text-align:left; padding-right:3px;}
#updataRmDatacenter p{width:600px; margin-bottom:5px; margin-left:18px;  }
#updataRmDatacenter p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#updataRmDatacenter label{display:block; padding-left:100px;}
</style>
</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_volume"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
				<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:247px;"><icms-i18n:label name="com_l_dataCenName"></icms-i18n:label>：</td>
						<td class="tabCon">
						<icms-ui:dic id="datacenter" name="datacenter" attr="class='selInput'" showType="select" 
						sql="SELECT ID AS value , DATACENTER_CNAME AS name FROM RM_DATACENTER WHERE IS_ACTIVE = 'Y'" />
						</td>
						<td class="tabBt" style="width:268px;" ><icms-i18n:label name="rm_l_serve_Name"></icms-i18n:label>：</td>
						<td class="tabCon"><input name="Vms" id="Vms"
							type="text" class="textInput readonly" />
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
					<table height="12%" width="100%">
						<tr style=" height:52px;">
							<td>
								<a href="javascript:search()" type="button" class="btn" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' onclick="search();return false;" >
									<span class="icon iconfont icon-icon-search"></span>
	  								<span>查询</span>
								</a>
<%-- 								<input type="reset" title="清空" class="Btn_Empty" style="text-indent:-999px;"/>
								<shiro:hasPermission name="rmDatacenter_sava">
								<input type="button" class="Btn_Add" title="添加" onclick="createData()" />
								</shiro:hasPermission>
								<shiro:hasPermission name="rmDatacenter_delete">
								<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
								</shiro:hasPermission> --%>
						  </td>
<%-- 								<shiro:hasPermission name="rmDatacenter_update">
										<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
								</shiro:hasPermission> --%>
						</tr>
					</table>
		</div>
	</div>
			</form>
	</div>
	<div class="pageTableBg">
		<div class="panel clear" id="openstackResGridTable_div">
			<table id="openstackResGridTable"></table>
			<table id="openstackResPager"></table>
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
					<i><font color="red">*</font>数据中心编码：</i>
					<input type="text" name="datacenterCode" id="datacenterCode" style="width: 140px;" class="textInput" />
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font>名称：</i>
					<input type="text" name="datacenterCname1" id="datacenterCname1" style="width: 140px;" class="textInput" />
				</span>
				</p>
				<p>
				<span class="updateDiv_list">
					<i><font color="red">*</font>英文名称：</i>
					<input type="text" name="ename" id="ename" style="width: 140px;"class="textInput"  />
				</span>
				<span class="updateDiv_list">
					<i><font color="red">*</font>数据中心地址：</i>
					<input type="text" name="address" id="address" style="width: 140px;"class="textInput"  />
				</span>
				</p>
				<p>
				<span class="updateDiv_list">
					<i><font color="red">*</font>数据中心标识：</i>
					<input type="text" name="queueIden1" id="queueIden1" style="width: 140px;"class="textInput"  />
				</span>
				<span class="updateDiv_list">
					<i>备注：</i>
					<input type="text" name="remark" id="remark" style="width: 140px;"class="textInput"  />
				</span>
				</p>
			<p class="winbtnbar btnbar1" style="margin-bottom:20px; width:555px; padding-right:49px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title="取消" value="取消" onclick="closeDatacenterView()" style="margin-left: 0px; margin-right:5px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title="保存" value="保存" onclick="saveOrUpdateDatacenterBtn()" style="margin-right: 5px; margin-left:0;">
			</p>
		</form>
	</div>
</body>
</html>
