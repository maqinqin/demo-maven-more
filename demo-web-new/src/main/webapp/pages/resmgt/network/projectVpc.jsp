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
#updataProjectVpcForm label{display:block; padding-left:115px;}
</style>
<script type="text/javascript" src="${ctx}/pages/resmgt/network/js/projectVpc.js"></script>

</head>
<body class="main1">
<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="rm_title_pm"/></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
		<div class="searchInWrap_top">
		<div class="searchIn_left" style="width:69%; float:left;">
			<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:106px;"><icms-i18n:label name="rm_l_vpcName"/>：</td>
						<td class="tabCon"><input name="vpcName" id="vpcName"
							type="text" class="textInput readonly" /></td>
						<td class="tabBt" style="width:200px;"><icms-i18n:label name="com_l_dataCenter"/>：</td>
						<td class="tabCon">
							<select id="datacenterId" class="selInput">
								<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
							</select>
						</td>
						<td class="tabBt" style="width:216px;"><icms-i18n:label name="rm_l_serve_Name"/>：</td>
						<td class="tabCon">
							<select id="vmMsId" class="selInput">
								<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
							</select>
						</td>
						
					</tr>
					<tr>
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
							
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel">
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
							<input type="button" class="Btn_Serch" title="查询" onclick="search()" />
							<input type="reset" title="清空" class="Btn_Empty" style="text-indent:-999px;" />
							<shiro:hasPermission name="projectVpc_sava">
							<input type="button" class="Btn_Add" title="添加" onclick="createData()" />
							</shiro:hasPermission>
							<shiro:hasPermission name="projectVpc_delete">
							<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="projectVpc_update">
									<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
							</shiro:hasPermission>
							<shiro:hasPermission name="projectVpc_show">
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
			<div class="panel clear" id="projectVpcGridTable_div">
				<table id="projectVpcTable"></table>
				<table id="projectVpcPager"></table>
		</div>
		</div>
		</div>	

	<!-- 添加 修改-->
	<div id="updataProjectVpc" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataProjectVpcForm">
				<input type="hidden" id="projectVpcMethod" name="projectVpcMethod" />
				<input type="hidden" id="projectVpcId" name="projectVpcId" />
				<input type="hidden" id="validateProjectVpcName" name="validateProjectVpcName" />
				<p>
					<i><font color="red">*</font><icms-i18n:label name="res_l_network_vpcName"/>：</i>
					<input type="text" name="projectVpcName1" id="projectVpcName1" style="vertical-align:middle; width:152px; " class="textInput" />
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="com_l_dataCenter"/>：</i>
					<select id="addDatacenterId" class="selInput" name="datacenterId">
						<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
					</select>
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="rm_l_serve_Name"/>：</i>
					<select id="addVmMsId" class="selInput" name="vmMsId">
						<option  id="" value=""><icms-i18n:label name="com_l_choose"/></option>
					</select>
				</p>
				<p>
					<i><font color="red">*</font><icms-i18n:label name="租户"/>：</i>
					<select id="selTenant" class="selInput" name="tenant">
						<option value="0"><icms-i18n:label name="com_l_choose"/></option>
					</select>
				</p>
				<p>
					<i><icms-i18n:label name="com_l_remark"/>：</i>
					<input type="text" name="remark" id="remark" style="vertical-align:middle; width:152px;" class="textInput" />
				</p>				
			<p class="winbtnbar btnbar1" style="text-align:right; width:276px; margin-bottom:20px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closePlatformrView()" style="margin-right: 10px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveOrUpdateProjectVpcBtn()" style="margin-right: 5px">
			</p>
		</form>
	</div>	
</body>
</html>
