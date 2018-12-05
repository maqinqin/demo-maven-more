<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用系统</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/pages/appmgt/js/appSysMag.js"></script>
<script type="text/javascript" src="${ctx}/common/javascript/honeySwitch1.js"></script>
<script type="text/javascript">
	$(function(){
		
		$(window).scroll(function(){
			//滚动105px，左侧导航固定定位
			if ($(window).scrollTop()>45) {
				$('.tree-div1').css({'position':'fixed','top':0,'height':'100%'})
			}else{
				$('.tree-div1').css({'position':'absolute','top':45})
			};
		});
		//树菜单伸缩
		$('.tree-div1').css('left','20px');
		var expanded = true;
		$('.tree-bar').click(function(){
			if(expanded){
				$('.tree-div1').animate({left:'-25%'},500);
				$('.tree-bar i').addClass('fa-caret-right');
				$('.right-div1').css({'width':'auto','margin-left':0});
			}else{
				$('.tree-div1').animate({left:'20'},500);
				$('.tree-bar i').removeClass('fa-caret-right');
				$('.right-div1').animate({'width':'74%','margin-left':'26%'},500);
			}
			expanded = !expanded;
		});
	
	});
</script> 
<link href="${ctx}/css/honeySwitch.css" rel="stylesheet">
<style type="text/css">
#update_AppInfo_Div .pageFormContent {padding-left:0; padding-right:0; padding-bottom:0;}
#update_AppInfo_Div i {text-align:left; padding-right:3px;}
#update_AppInfo_Div p{width:600px; margin-bottom:5px; margin-left:18px;height:25px}
#update_AppInfo_Div p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#update_AppInfo_Div label{display:block; padding-left:110px;}

#add_AppInfo_Div i {text-align:left; padding-right:3px;}
#add_AppInfo_Div p{width:600px; margin-bottom:5px; margin-left:18px;height:25px}
#add_AppInfo_Div p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#add_AppInfo_Div label{display:block; padding-left:110px;}


#add_update_AppDu_Div i {text-align:left; padding-right:3px;}
#add_update_AppDu_Div p{width:600px; margin-bottom:5px; margin-left:18px; height:40px;}
#add_update_AppDu_Div p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#add_update_AppDu_Div label{display:block; padding-left:110px;}
</style>
</head>
<body class="main1">
<input id="hidNodeId" type="hidden"/>
<input id="hidNodeIV" type="hidden"/>
			<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="app_title_manage"/></div>
				<div class="WorkSmallBtBg">
					<small>
                    	<icms-i18n:label name="app_title_describe"/>
					</small>
				</div>
					
			</h1>
		</div>
	
		<div id="treeDiv" class="tree-div tree-div1">
		<a href="javascript:" class="tree-bar"><i class="fa fa-caret-left"></i></a>
			<div class="pageFormContent tree-search">
	  	  		<p style="margin: 0px;" class="btnbar1">
					<input name="name" type="text" id="name" size="30" class="textInput" style="width: 80%;float: left;">
					<shiro:hasPermission name="yyxt_query">  
					<span class="input-group-btn"><a href="#" class="btn-default"><i class="fa fa-search" style="line-height:15px;width:auto;" onclick="searchTreeByCname()"></i></a></span>
						<!-- <input type="button" class="btn_search_s" style="margin-top:3px;float: left; margin-left:5px;" onclick="searchTreeByCname()" /> -->
					</shiro:hasPermission> 
				</p>
	  		</div>
			
			<ul id="treeRm" class="ztree" style="padding:0 0 0 5px;"></ul>
			
		</div>
		<div id="rightTopDiv" class="right-div right-div1" style=" width:74%;">
		  	
		  <div id="app_mag_tab" class="sumWrap sumWrap2" style="display: none; margin:0;">
		    <ul id="app_mag_tabAc" class="tb">
		        <li id="app_tab_act" class="BtCur" onclick="appMag_tab('appMag_tab_t',$('#hidNodeId').val(),$('#hidNodeIV').val())"><icms-i18n:label name="l_app_abstract"/></li>
		        <li id="app_tab_tr" class="BtRCur" onclick="appMag_tab('appMag_tab_tr',$('#hidNodeId').val(),$('#hidNodeIV').val())"><icms-i18n:label name="l_app_server"/></li>
		    </ul>
		    <div id="app_tab_inf" style="display:block" class="sumCon">
		    	<jsp:include page="appMagAct.jsp" flush="true"/>
		    </div>
		    <div id="app_tab_body_tr" style="display:none" class="sumCon">
		    	<jsp:include page="../resmgt/compute/vIRware.jsp" flush="true"/>
		    </div>
		  </div>

				<div id="update_AppInfo_Div" style="display: none;background-color:white;" class="pageFormContent">
					<form action="" method="post" id="update_AppInfo_Form">
						<p style="display: none;">
							<i><font color="red">*</font><icms-i18n:label name="app_l_belogsSys"/>：</i>
							<icms-ui:dic id = "fatherId" name="fatherId"
									sql="select '0' id, '0' value,'应用系统' name,'0' kind from APP_INFO union select APP_ID id, APP_ID value,CNAME name,APP_ID kind from APP_INFO where is_active = 'Y' "
									showType="select" attr="class='selInput'" />
						</p>
						<p>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_shortZhName"/>：</i>
							<input type="text" id="cname" name="cname"
								class="textInput" />
								<input type="hidden" name="cnameHidden" id ="cnameHidden">
						</span>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_longZhName"/>：</i>
							<input type="text" id="fullCname"
								name="fullCname" class="textInput" />
						</span>
						</p>
						<p>
							<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_shortEnName"/>：</i>
							<input type="text" id="ename" name="ename"
								class="textInput" />
								<input type="hidden" name="enameHidden" id ="enameHidden">
						</span>
						<span class="updateDiv_list">
							<i><icms-i18n:label name="app_l_longEnName"/>：</i>
							<input type="text" id="fullEname"
								name="fullEname" class="textInput" />
						</span>
						</p>
						<p>
						<span class="updateDiv_list">
							<i><icms-i18n:label name="app_l_appPrincapal"/>：</i>
							<input type="text" id="manager"
								name="manager" class="textInput" />
						</span>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_sysRank"/>：</i>
							<icms-ui:dic id = "sysLevelCode" name="sysLevelCode"
									kind="APP_LEVEL" showType="select" attr="class='selInput'" />
						</span>
						</p>
						<p>
							<i><icms-i18n:label name="com_l_remark"/>：</i>
							<textarea id="remark" name="remark"
									rows="5" cols="40" class="textInput"></textarea>
						</p>
					<p class="btnbar btnbar1" style="text-align:right; margin-bottom:20px; padding-right:20px; width:570px;">
						<input type="button" class="btn btn_dd2 btn_dd3" id="cencelAppInfo" name="cencelAppInfo" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>'  value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('update_AppInfo_Div')" style="margin-left:0; margin-right:5px;">
						<shiro:hasPermission name="yyxt_update"> 
							<input type="button" class="btn btn_dd2 btn_dd3" id="saveAppInfo" name="saveAppInfo" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="updateBtn()" style="margin-left:0; margin-right:5px;">
						</shiro:hasPermission> 
					</p>
					</form>
				</div>
				
				<div id="add_AppInfo_Div" style="display: none;background-color:white;" class="pageFormContent">
					<form action="" method="post" id="add_AppInfo_Form">
						<p style="display: none;">
							<i><font color="red">*</font><icms-i18n:label name="app_l_belogsSys"/>：</i>
							<icms-ui:dic id = "newfatherId" name="newfatherId" value = "0"
									sql="select '0' id, '0' value,'应用系统' name,'0' kind from APP_INFO union select APP_ID id, APP_ID value,CNAME name,APP_ID kind from APP_INFO where is_active = 'Y' "
									showType="select" attr="class='selInput'" />
						</p>
						<p>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_shortZhName"/>：</i>
							<input type="text" id="newcname" name="newcname"
								class="textInput" />
						</span>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_longZhName"/>：</i>
							<input type="text" id="newfullCname"
								name="newfullCname" class="textInput" />
						</span>
						</p>
						<p>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_shortEnName"/>：</i>
							<input type="text" id="newename" name="newename"
								class="textInput" />
						</span>
						<span class="updateDiv_list">
							<i><icms-i18n:label name="app_l_longEnName"/>：</i>
							<input type="text" id="newfullEname"
								name="newfullEname" class="textInput" />
						</span>
						</p>
						<p>
						<span class="updateDiv_list">
							<i><icms-i18n:label name="app_l_appPrincapal"/>：</i>
							<input type="text" id="newmanager"
								name="newmanager" class="textInput" />
						</span>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_sysRank"/>：</i>
							<icms-ui:dic id = "newsysLevelCode" name="newsysLevelCode"
									kind="APP_LEVEL" showType="select" attr="class='selInput'" />
						</span>	
						</p>
						<p>
						<span class="updateDiv_list">
							<i><icms-i18n:label name="com_l_remark"/>：</i>
							<textarea id="newremark" name="newremark"
								 class="textInput" style="width:145px; height:30px;"></textarea>
						</span>
					<p class="btnbar btnbar1" style="text-align:right; margin-bottom:20px; padding-left:20px; width:546px;">
						<input type="button" class="btn btn_dd2 btn_dd3" id="cencelAppInfo" name="cencelAppInfo" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('add_AppInfo_Div')" style="margin-left:0; margin-right:5px;">
						<shiro:hasPermission name="yyxt_add"> 
							<input type="button" class="btn btn_dd2 btn_dd3" id="saveAppInfo" name="saveAppInfo" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="savedateBtn()" style="margin-left:0; margin-right:5px;">
						</shiro:hasPermission> 
					</p>
					</form>
				</div>


				<div id="add_update_AppDu_Div" style="display: none;background-color:white;" class="pageFormContent">
				  <form action="" method="post" id="add_update_AppDu_Form">
				  <input type="hidden" name='duMethod' id='duMethod'/>
						<p>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="com_l_dataCenter"/>：</i>
							<icms-ui:dic id="du_datacenterId" name="du_datacenterId"
									sql="select ID id, ID value,DATACENTER_CNAME name,ID kind from RM_DATACENTER WHERE IS_ACTIVE='Y'"
									showType="select" attr="class='selInput'" />
						</span>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_belogsSys"/>：</i>
							<icms-ui:dic id="du_appId" name="du_appId"
									sql="select APP_ID id, APP_ID value,CNAME name,APP_ID kind from APP_INFO WHERE IS_ACTIVE='Y'"
									showType="select" attr="class='selInput'" />
						</span>
						</p>
						<p style="display:none">
						<span class="updateDiv_list" >
							<i><font color="red">*</font><icms-i18n:label name="app_l_shortZhName"/>：</i>
							<input type="text" id="du_cname" name="du_cname"
								class="textInput" />
						</span>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_shortEnName"/>：</i>
							<input type="text" id="du_ename" name="du_ename"
								class="textInput" />
						</span>
						
						</p>
						<p >
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_longZhName"/>：</i>
							<input type="text" id="du_fullCname"
								name="du_fullCname" class="textInput" />
						</span>		
						
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_longEnName"/>：</i>
							<input type="text" id="du_fullEname"
								name="du_fullEname" class="textInput" />
						</span>		
						</p>
						<p>
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="app_l_securArea"/>：</i>
							
								<icms-ui:dic id="du_secureAreaCode" name="du_secureAreaCode"
									sql="select SECURE_AREA_ID id, SECURE_AREA_ID value,SECURE_AREA_NAME name,SECURE_AREA_ID kind from RM_NW_SECURE_AREA WHERE IS_ACTIVE='Y'"
									showType="select" attr="class='selInput'" />
							
						</span>
						<span class="updateDiv_list" id ="du_statusSpan">
							<i><font color="red">*</font><icms-i18n:label name="com_l_status"/>：</i>
							<%-- <icms-ui:dic id="du_status" name="du_status" kind="USE_STATUS"
									showType="select" attr="class='selInput'" /> --%>
							<input type="hidden" id="du_status" />
						</span>
						</p>
						<p style="display:none">
						<span class="updateDiv_list">
							<i><font color="red">*</font><icms-i18n:label name="com_l_serveType1"/>：</i>
							<icms-ui:dic id="du_serviceTypeCode" name="du_serviceTypeCode"
									kind="DU_SEV_TYPE" showType="select" attr="class='selInput'" />
							
						</span>
						
						<span class="updateDiv_list">
							<i><icms-i18n:label name="app_l_securTier"/>：</i>
							
								<select id="du_sevureTierCode" name="du_sevureTierCode" class="selInput">						
								</select>
						</span>	
						</p>
						<p>
						<span class="updateDiv_list">
							<i><icms-i18n:label name="com_l_remark"/>：</i>
							<textarea id="du_remark" name="du_remark"
									rows="5" cols="60" class="textInput"></textarea>
						</span>
					<p class="btnbar btnbar1" style="margin-bottom:20px; margin-right:20px; width:565px; text-align:right;"> 
						<input type="button" class="btn btn_dd2 btn_dd3" id="cancelAppDu" name="cancelAppDu" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('add_update_AppDu_Div')" style="margin-left:0; margin-right:5px;">
						<shiro:hasPermission name="bsdy_add"> 
							<input type="button" class="btn btn_dd2 btn_dd3" id="saveAppDu" name="saveAppDu" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveUpdateAppDu()" style="margin-left:0; margin-right:5px;">
						</shiro:hasPermission> 
					</p>
				  </form>
				</div>
			
		</div>
	<input type="hidden" id="hidenAppSysId" name="hidenAppSysId" value="0">
	<input type="hidden" id="hidenfatherId" name="hidenfatherId" value="">
	<input type="hidden" id="isSubAppSys" name="isSubAppSys" value="">
	<input type="hidden" id="isAppDu" name="isAppDu" value="">
	<input type="hidden" id="isBeUse" name="isBeUse" value="">
	<input type="hidden" id="hid_icon" value = ""/>
	<input type="hidden" id="du_oldAppId" name="du_oldAppId">
	<input type="hidden" id="du_id" name="du_id">
	<div id="div_tip"></div>
</body>
</html>
