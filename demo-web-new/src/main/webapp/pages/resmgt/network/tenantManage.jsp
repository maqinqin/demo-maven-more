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
ul,li{padding:0; margin:0; list-style:none;}
#updataProjectVpcForm p {
	width: 300px;
	padding: 0;
	padding-left: 16px;
}

#updataProjectVpcForm p i {
	width: 100px;
	text-align: right;
}

#updataProjectVpcForm label {
	display: block;
	padding-left: 115px;
}
.tabs {
    height: 28px;
	padding: 0 24px;
	margin: 1px 0 0 0px;  
}
.tabs li {
	width: 100px;
	text-align: center;
	/* background: url(../images/biaoqian02.png) no-repeat; */
	border-radius:3px;
	font-size: 14px;
	font-weight: normal;
	cursor: pointer;
	float: left;
	margin-left:4px;
}
#tabInfo{
	width: 95%;
    height: auto;
    margin-left: 10px;
    padding-bottom: 20px;
    float: left;
    min-height: 100px;
    margin-top:20px;
}
#tabs .default_color{
 /* background: url(${ctx}/images/biaoqian02.png); */
	color: #666;
	height: 26px;
	line-height: 26px;
}
#tabs .chosen_color{
	margin-top: 1px;
	height: 26px;
	line-height: 26px;
	font-weight: normal;
}
.quotaManage{
	margin-left: 40px;
	margin-top: 18px;
    margin-bottom: 10px;
}
#openstackLeft,#openstackRight,#powervcLeft,#powervcRight{
	list-style: none;
	float:left;
	
}
#openstackLeft,#powervcLeft{
	width: 30%;
    height: 100%;
    /* background:#5F6273;  */
   /*  border-top:2px solid #F3F3F3; */
}

#openstackLeft li,#powervcLeft li{	
    font-size: 12px;
    border-bottom: 1px solid #fff;
    color: #3e3e3e;
	padding:2px 10px 4px; 
	border:1px solid #1ab394;
    border-radius:4px;
    margin-top:10px;
    text-align:center;
    
	 
}

#openstackRight,#powervcRight{
	float:left;
	display: inline-block;
	width: 69%;
	height: auto;
	float:left;
	margin-bottom: -20px;
}

.quotaManage input {
	width: 154px;
	border: 1px solid #d5d5d5;
	height: 22px;
	line-height: 22px;
	color: #615d70;
	background-color: #f5f5f5;
	padding: 1px 4px 0px 5px;
	font-size: 12px;
	box-shadow: 1px 1px 2px #ededed;
	margin-right:10px; 
	margin-left:10px;
}
.lable_sapn{
	width: 100px;
	display: inline-block;
	text-align: right;
}
.hidden{
	display:none;
}
.red{
	color:red;
	margin-left: 20px;
}

</style>
<script type="text/javascript"
	src="${ctx}/pages/resmgt/network/js/tenantManage.js"></script>
<script type="text/javascript" src="${ctx}/scripts/moment.js"></script> 

</head>
<body class="main1">
	<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="tenantManage_jsp_title" /></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
				<div class="searchInWrap_top">
					<div class="searchIn_left" style="width: 69%; float: left;">
						<div class="searchInOne">
							<table height="12%" width="100%" align="center">
								<tr style="height: 55px;">
									<td class="tabBt" style="width: 55px;"><icms-i18n:label
											name="tenantManage_jsp_tenantName" />：</td>
									<td class="tabCon"><input name="tenantName"
										id="tenantNameSearch" type="text" class="textInput"/></td>
										<td class="bt"></td>
										<td class="tabCon"></td>
										<td class="bt"></td>
										<td class="tabCon"></td>
								</tr>
							</table>
						</div>
					</div>

					<div class="searchBtn_right" style="width: 28%; float: left">
						<table height="12%" width="100%" align="center">
							<tr style="height: 52px;">
								<td>
								<a href="javascript:search()" type="button" class="btn" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'onclick="search();return false;">
									<span class="icon iconfont icon-icon-search"></span>
									<span>查询</span>
								</a>
							    <button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>'value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>'class="btnDel">
							    	<span class="icon iconfont icon-icon-clear1"></span>
									<span>清空</span>
							    </button>  
							    <a href="javascript:void(0)"t type="button" class="btn"title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'onclick="addTenantDialog()">
							    	<span class="icon iconfont icon-icon-add"></span>
									<span>添加</span>
							    </a> 
							    <input type="hidden" id="deleteFlag"name="deleteFlag" value="1" /> 
							    <input type="hidden"id="updateFlag" name="updateFlag" value="0" />
							     <input type="hidden" id="showFlag" name="showFlag" value="1" /> 
									</td>
							</tr>
						</table>
					</div>
				</div>
			</form>
		</div>
		<!-- 主页面数据列表  -->
		<div class="pageTableBg">
			<div class="panel clear" id="tenantManageGridTable_div" >
			<table id="tenantManageTable"></table>
			<table id="tenantManagePager"></table>
		</div>
		</div>
		
	</div>
	<!-- 租户授权数据列表  -->
		<div  id="tenantAuthoGridTable_div" style="display:none;background-color:white;"  class="pageFormContent">
			<input type="hidden" id="authoTenantId" name="tenantId" />
			<table id="tenantAuthoTable"></table>
			<table id="tenantAuthoPager"></table>
			<div class="winbtnbar btnbar1" style="text-align:right; width:408px; margin-left: 200px;margin-top: 20px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closePlatformrView('tenantAuthoGridTable_div')">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="userAuthoSaveBtn()" >
			</div>
		</div>
		
		<!-- 资源池授权列表  -->
		<div  id="resourcePoolGridTable_div" style="display:none;background-color:white;"  class="pageFormContent">
			<input type="hidden" id="tenantIdRes" name="tenantIdRes" />
			<table id="resourcePoolTable"></table>
			<table id="resourcePoolPager"></table>
			 <div class="winbtnbar btnbar1" style="text-align:right; width:408px; margin-left: 200px;margin-top: 20px">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closePlatformrView('resourcePoolGridTable_div')">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="tenantRsePoolSaveBtn()" >
			</div>  
		</div> 
		
		
	<!-- 配额管理数据列表  -->
	<div  id="quotaGridTable_div" style="display:none;background-color:white;height:auto;">
		<input type="hidden" id="quotaTenantId" name="tenantId" />
		<div style="width:100%;height:auto;">
			<ul id="tabs" class="tabs">
		        <li id="tab_windows"  class="default_color" onclick="showTabInfo('windows')">VMware</li>
		        <!--<li id="tab_power" class="default_color" onclick="showTabInfo('power')">Power</li> -->
		        <li id="tab_openstack" class="default_color" onclick="showTabInfo('openstack')">OpenStack</li>
		        <!--<li id="tab_powervc" class="default_color" onclick="showTabInfo('powervc')">PowerVC</li>-->	    	
		    </ul>
		    <div id="tabInfo" class="tabInfo">
		    	<div id="windows" style="display:none;width:100%;height:100%">
		    		<ul id="windowsLeft">
	    			</ul>		    		
		    		<ul id="windowsRight">
		    		</ul>		    		
		    	</div>
		    	<div id="power" style="display:none;width:100%;height:100%">			    	
		    	</div>
		    	<div id="openstack" style="display:none;width:100%;height:100%">		    		    			    	
	    			<ul id="openstackLeft">
	    			</ul>		    		
		    		<ul id="openstackRight">
		    		</ul>			    		    	
		    	</div>
		    	<div id="powervc" style="display:none;width:100%;height:100%">		    		    			    	
	    			<ul id="powervcLeft">
	    			</ul>		    		
		    		<ul id="powervcRight">
		    		</ul>			    		    	
		    	</div>		    	
		    </div>
			<div class="winbtnbar btnbar1" style="text-align:center;width:432px;height:30px; margin-bottom:20px;float:left">
					<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closePlatformrView('quotaGridTable_div')">
					<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="quotaManageSaveBtn()" >
			</div>
		</div>
	</div>
	<!-- 添加 修改-->
	<div id="tenantAddAndUpdateDiv" style="display:none;background-color:white;"  class="pageFormContent">
		<form action="" method="post" id="updataProjectVpcForm">
				<input type="hidden" id="tenantMethod" name="tenantMethod" />
				<input type="hidden" id="tenantId" name="tenantId" />						
				<p>
					<i><font><span style="color: red;">*</span><icms-i18n:label name="tenantManage_jsp_tenantName"/>：</font></i>
					<input type="text" name="tenantName" id="tenantName" style="vertical-align:middle;" class="textInput" />
				</p>
				<p>
					<i><font><icms-i18n:label name="tenantManage_jsp_remark"/>：</font></i>
					<input type="text" name="tenantRemark" id="tenantRemark" style="vertical-align:middle;" class="textInput" />
				</p>					
			<p class="winbtnbar btnbar1" style="text-align:right; width:238px; margin-left: 40px;margin-top: 20px;">
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closePlatformrView('tenantAddAndUpdateDiv')" style="" >
				<input type="button" class="btn btn_dd2 btn_dd3" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="addOrUpdateTenantBtn()" >
			</p>
		</form>
	</div>	
	
</body>
</html>
