<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>  
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<script type="text/javascript" src="${ctx}/pages/appmgt/js/appMagAct.js"></script>
<script type="text/javascript" src="${ctx}/common/echart/echarts.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/count.css"></link>
<style>
#view_AppDu_Div p{margin-bottom:0;}
</style>
<div id="View_AppInfo_ActDiv" >
  	<div id="View_AppInfo_ActTop" class="sumCon_top">
      	<p class="tip2"><icms-i18n:label name="app_l_statisInfo"/></p><span class="tip2b"></span>
      	<br><br>
          <table class="sumTable sumTable2">
            <tr>
                <th id="systemNumThId"><icms-i18n:label name="app_l_sysNum"/>:</th>
      			<td><input id="systemNum"/></td>
                <th id="deplUnitNID"><icms-i18n:label name="app_l_deployUnitNum"/>:</th>
      			<td><input id="deplUnitNum"/></td>
                <th id="cloundServith"><icms-i18n:label name="app_l_cloudServe"/>:</th>
      			<td><input id="cloudServiceNum"/></td>
                <!-- <th id="cmVmDConfig">配置:</th>
      			<td><input id="cvmConfig"/></td> -->
                <th><icms-i18n:label name="app_l_vmNum"/>:</th>
      			<td><input id="vmNum"/></td>
      			<th><icms-i18n:label name="app_l_cpu"/>:</th>
      			<td><input id="cpuNum"/></td>
      			<th><icms-i18n:label name="app_l_memory"/>(GB):</th>
      			<td><input id="memNum"/></td>
            </tr>
          </table>
          
       </div>
       
     <div id="View_AppInfo_ActvChart" class="sumCon_top">
		<p class="tip2"><icms-i18n:label name="app_l_graphShow"/></p><span class="tip2b"></span><br/><br/>
   		<div id="View_AppInfo_ActEChart" style="height:256px"></div> 
	   </div>
	   
       <div id="View_AppInfo_ActOperation" class="sumCon_top">
       	<p class="tip2"><icms-i18n:label name="app_l_detailInfo"/></p><span class="tip2b"></span>
   			<div id="view_AppInfo_Div" style="display: none">
					<table border="0" cellpadding="0" cellspacing="0" class="report" width="97%">
						<tr>
							<th style="display: none;"><icms-i18n:label name="app_l_belogsSys"/>：</th>
							<td style="display: none;"><label id="lbl_AppInfo_Father"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="app_l_shortZhName"/>：</th>
							<td width="30%"><label id="lbl_AppInfo_Cname"></label></td>					
							<th><icms-i18n:label name="app_l_longZhName"/>：</th>
							<td><label id="lbl_AppInfo_Full_Cname"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="app_l_shortEnName"/>：</th>
							<td><label id="lbl_AppInfo_Ename"></label></td>
							<th><icms-i18n:label name="app_l_longEnName"/>：</th>
							<td><label id="lbl_AppInfo_Full_Ename"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="app_l_appPrincapal"/>：</th>
							<td><label id="lbl_AppInfo_Manager"></label></td>
							<th><icms-i18n:label name="app_l_sysRank"/>：</th>
							<td><label id="lbl_AppInfo_SysLevel"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="com_l_remark"/>：</th>
							<td><label id="lbl_AppInfo_Remark"></label></td>
						</tr>
					</table>
					<p class="btnbar1">
						<shiro:hasPermission name="yyxt_update">  
							<input type="button" class="btn btn_dd2" id="modifyAppInfo" name="modifyAppInfo" value='<icms-i18n:label name="app_btn_modifyAppInfo"/>' onclick="modifyAppInfo()" style="margin-left:0; margin-right:5px;">
						</shiro:hasPermission> 
						<shiro:hasPermission name="yyxt_delete">  
							<input type="button" class="btn btn_dd2" id="deleteAppInfo" name="deleteAppInfo" value='<icms-i18n:label name="app_btn_deleteAppInfo"/>' onclick="checkDelAppInfo()" style="margin-left:0; margin-right:5px;"> 
						</shiro:hasPermission> 
						<shiro:hasPermission name="bsdy_add">  
							<input type="button" class="btn btn_dd2" id="addAppDu" name="addAppDu" value='<icms-i18n:label name="app_btn_addAppDu"/>' onclick="addAppDu()" style="margin-left:0; margin-right:5px;">
						</shiro:hasPermission> 
					</p>
				</div>
				
				<br/><br/>
				<div id="view_AppDu_Div" style="display: none">
					<table border="0" cellpadding="0" cellspacing="0" class="report" width="97%">
						<tr>
							<th><icms-i18n:label name="com_l_dataCenter"/>：</th>
							<td width="30%"><label id="lbl_AppDu_DataCenter"></label></td>
							<th><icms-i18n:label name="app_l_belogsSys"/>：</th>
							<td><label id="lbl_AppDu_App"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="app_l_shortZhName"/>：</th>
							<td><label id="lbl_AppDu_Cname"></label></td>
							<th><icms-i18n:label name="app_l_longZhName"/>：</th>
							<td><label id="lbl_AppDu_Full_Cname"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="app_l_shortEnName"/>：</th>
							<td><label id="lbl_AppDu_Ename"></label></td>
							<th><icms-i18n:label name="app_l_longEnName"/>：</th>
							<td><label id="lbl_AppDu_Full_Ename"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="com_l_serveType1"/>：</th>
							<td><label id="lbl_AppDu_Service_Type"></label></td>
							<th><icms-i18n:label name="com_l_status"/>：</th>
							<td><label id="lbl_AppDu_Status"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="app_l_securArea"/>：</th>
							<td><label id="lbl_AppDu_SecureArea"></label></td>
							<th><icms-i18n:label name="app_l_securTier"/>：</th>
							<td><label id="lbl_AppDu_SevureTier"></label></td>
						</tr>
						<tr>
							<th><icms-i18n:label name="com_l_remark"/>：</th>
							<td><label id="lbl_AppDu_Remark"></label></td>
						</tr>
					</table>
					<p class="btnbar1" style="padding-left:40px;">
						<shiro:hasPermission name="bsdy_update"> 
							<input type="button" class="btn btn_dd2" style="margin-left:0; margin-right:12px;" id="modifyAppDu" name="modifyAppDu" value='<icms-i18n:label name="app_btn_modifyAppDu"/>' onclick="modifyAppDu()">
						</shiro:hasPermission> 
						<shiro:hasPermission name="bsdy_delete"> 
							<input type="button" class="btn btn_dd2" style="margin-left:0; margin-right:12px;" id="deleteAppDu" name="deleteAppDu" value='<icms-i18n:label name="app_btn_deleteAppDu"/>' onclick="deleteAppDu()">
						</shiro:hasPermission> 
					</p>
				</div>
				
				<div id="icon_Div" style="font-size:18px;">
				 <div style="width:100%">
			  		<table border="0" cellpadding="0" cellspacing="0" class="pagelist" style="float:left;width:40%" >
						<tr>
						<th><img src="../../css/zTreeStyle/img/icons/appsys.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="app_l_appSysIcon"></icms-i18n:label></label></td>
						</tr>
						<tr>
						<th><img src="../../css/zTreeStyle/img/icons/du.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="app_l_deployIcon"></icms-i18n:label></label></td>
						</tr>			
					</table>
				</div> 
				 <div style="width:40%">
					<p class="btnbar1" style="padding-left:20px;">
					<input type="hidden" name="addAppInfoMethod" id="addAppInfoMethod">
						<shiro:hasPermission name="yyxt_add">  
							<input type="button" class="btn btn_dd2" id="addAppInfo_new" style="margin-left:0; margin-right:5px;" name="addAppInfo_new" value='<icms-i18n:label name="app_btn_add_appInfo"/>' onclick="addAppInfo()">
						</shiro:hasPermission> 
					</p>
				</div> 
		  	</div> 
	   </div>
	   
</div>