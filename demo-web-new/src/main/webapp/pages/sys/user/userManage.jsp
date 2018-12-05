<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<html>
<head>
<title>云平台管理系统</title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/jquery.base64.js"></script>
<script type="text/javascript" src="${ctx}/pages/sys/user/js/userManage.js"></script>
<style type="text/css">
#add_update_User_Div i {text-align:left; padding-right:3px;}
#add_update_User_Div p{width:600px; margin-bottom:5px; margin-left:18px;  }
#add_update_User_Div p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;height: 51px;}
#add_update_User_Div label{padding-left:108px;}
#set_limit_Div a:link,#set_limit_Div a:visited{color:#fff; text-decoration:none;}
#set_limit_Div a:hover{color:#fff; text-decoration:none;}
#cloudUser,#tenantUser{position:relative;top: 5px;}
</style>
<script type="text/javascript" src="${ctx}/common/javascript/honeySwitch.js"></script>
<script type="text/javascript" src="${ctx}/common/sha1.js"></script>
<link href="${ctx}/css/honeySwitch.css" rel="stylesheet">
<style type="text/css">
    	
		.bs-docs-header h1{
			font-size: 1.5em;
			font-family: "Segoe UI", "Lucida Grande", Helvetica, Arial, "Microsoft YaHei", FreeSans, Arimo, "Droid Sans", "wenquanyi micro hei", "Hiragino Sans GB", "Hiragino Sans GB W3", "FontAwesome", sans-serif;
		}
		.bs-docs-header h1 span{
			display: block;
			font-size: 60%;
			font-weight: 400;
			padding: 0.8em 0 0.5em 0;
			color: #c3c8cd;
		}
		.htmleaf-icon{color: #fff;}
    </style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_user"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:70px;"><icms-i18n:label name="sys_l_loginName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="loginName_labl" name="loginName_labl" class="textInput"/>
					</td>
					<td class="tabBt"><icms-i18n:label name="sys_l_userType"></icms-i18n:label>：</td>
					<td class="tabCon">
                       <icms-ui:dic id = "userType_labl" name="userType_labl"
									kind="USER_TYPE" showType="select"  attr="class='selInput'"/>
					</td>
					<td class="tabBt"></td>
					<td class="tabCon"></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td align="center" colspan="2">
					<shiro:hasPermission name="user:list">  
						<a href="javascript:void(0)" type="button" class="btn" onclick="searchUser();" title='<icms-i18n:label name="sys_btn_queryUser"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
							<span class="icon iconfont icon-icon-search"></span>
							<span>查询</span>
						</a>
					</shiro:hasPermission>
					<button title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick="clearAll()">
						<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
					</button>
					<shiro:hasPermission name="user:save">
			            <a href="javascript:void(0)" class="btn"onclick="addUser('insert');"  title='<icms-i18n:label name="sys_btn_createUser"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' >
			            	<span class="icon iconfont icon-icon-add"></span>
							<span>添加</span>
			            </a>
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:update"> 
			            <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:delete"> 
			            <input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:authorization"> 
			            <input type="hidden" id="authorizationFlag" name="authorizationFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:list"> 
			            <input type="hidden" id="sysRoleFlag" name="sysRoleFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:cloud"> 
			            <input type="hidden" id="userCloudFlag" name="userCloudFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:system"> 
			            <input type="hidden" id="userSystemFlag" name="userSystemFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:limit"> 
			            <input type="hidden" id="userLimitFlag" name="userLimitFlag" value="1" />
			        </shiro:hasPermission>
					</td>
						</tr>
					</table>
	</div>
</div>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="gridMain">
				<table id="gridTable" ></table>
			<div id="gridPager"></div>
		</div>
		</div>
		
				
	   </div>
		
		<div id="add_update_User_Div" style="display: none">
			<form action="" method="post" id="add_update_User_Form">
			<div  id="updateTab"  class="pageFormContent">
			 <p>
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_userSurName"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="firstName" name="firstName" />
			 </span>
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_username"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="lastName" name="lastName" />
			 </span>
			
			
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_loginName"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="loginName" name="loginName" />
			 </span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_loginPwd"></icms-i18n:label>:</i>
				<input class="textInput"  type="password" id="loginPassword" name="loginPassword" />
			</span>
			 
			
			 <span class="updateDiv_list">
				<i><icms-i18n:label name="sys_l_mail"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="email" name="email" />
			 </span>
			 <span class="updateDiv_list">
				<i><icms-i18n:label name="com_l_ipAddr"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="ipAddress" name="ipAddress" />
			 </span>
			
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_userType"></icms-i18n:label>:</i>
				<icms-ui:dic id = "userType" name="userType"
									kind="USER_TYPE" showType="select" attr="class='selInput'" />
			 </span>
			 <span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="sys_l_blogsO"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="orgName" name="orgName"  onclick="showOrgTree()" style="cursor:pointer" />
			 </span>
			
			 			 
			 <span id="cloudUserLabel" class="updateDiv_list" style="margin-top:5px;">
			 <!-- "xy" xy可选值是0，1,x是云平台身份，y是租户管理平台身份 -->
				<i><icms-i18n:label name="sys_l_cloudUser"></icms-i18n:label>:</i>
			</span>
			<span id="tenantUserLabel" class="updateDiv_list" style="margin-top:5px;">
			  <i><icms-i18n:label name="sys_l_tenantUser"></icms-i18n:label>:</i>
			 </span>
			 
			 	<span class="updateDiv_list">
				<i><icms-i18n:label name="sys_l_tel"></icms-i18n:label>:</i>
				<input class="textInput"  type="text" id="phone" name="phone" />
			 </span>
			 </p>
			</div>
		</form>
		<div class="winbtnbar clear btnbar1" style="text-align:right; margin-bottom:20px; width:600px;">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="cencelUser" name="cencelUser" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>'  value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' style="margin-right: 5px; margin-left:0;"  onclick="closeView('add_update_User_Div')">
				<input type="button"  class="btn btn_dd2 btn_dd3"  id="saveUser" name="saveUser" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>'  value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;"  onclick="saveUserBtn()">
	</div>
	</div> 
	
	<!-- 角色授予 -->
<div id="roleAuthorizationDiv" class="div_center" style="display: none;">
  <div class="gridMain">
			<table id="gridTableRole"></table>
			<table id="gridPagerRole"></table>
 </div>
 <p class="btnbar btnbar1" style="text-align:right; margin-right:16px;">
		<input type="button" class="btn btn_dd2 btn_dd3"  value='<icms-i18n:label name="com_btn_closeWin"></icms-i18n:label>' onclick="closeView('roleAuthorizationDiv')" style="margin-right: 5px; margin-left:0;" />
       	<input type="button" value='<icms-i18n:label name="com_btn_cancelAward"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="cancleAuthRole()" style="margin-right: 5px; margin-left:0;"/>
        <input type="button" value='<icms-i18n:label name="com_btn_awardRole"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="chooseAuthRole()" style="margin-right: 5px; margin-left:0;" />
 </p>
</div>

<!-- 所属机构 -->
<div id="orgAuthorizationDiv" class="div_center" style="display: none;">
		<form action="" method="post" id="authorizationForm">
		<input type="hidden" id="roleAuthorId" name="roleAuthorId"  />
		<input type="hidden" id="roleMenus" name="roleMenus" />
        <table class="pagelist">
       		 <tr>
	       		 <td>
	       		 	<div id="treeDiv"
					style="overflow: auto;min-height:306px;height:100%;width:100%;background-color:white;float:left;clear:right;">
					<ul id="orgTree" class="ztree" style="width: 288px; overflow: auto;"></ul>
					</div>
				 </td>
			 </tr>
		</table>
		<p class="winbtnbar clear btnbar1">
		  <input type="button" class="btn btn_dd2 btn_dd3"  title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('orgAuthorizationDiv')" style="margin-right: 5px;" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>'/>
		  <input type="button" class="btn btn_dd2 btn_dd3"  title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="chooseAuthOrg()" style="margin-right: 5px;" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>'/>
		</p>
        </form>
</div>

	<div id="add_sysRole_Div" style="display: none; padding-top:18px;">
		<div id="btn_sel_script" class="logpanel" style="border:none;">
			<div class="logchoosecontent" style="margin-left:10px; margin-top:0; padding-bottom:20px;" >
			&nbsp;<input type="radio" name="chooseSys" id="radio1" value="1" onchange="setSysMoveNodeDisable()">所有业务系统
			&nbsp;&nbsp;<input type="radio" name="chooseSys" id="radio2" value="2" onchange="setSysMoveNodeUnDisable()">指定业务系统
				<div id="device_sel" style="float: left; ">
					<div style="float: left;">
						<input type="checkbox" id="checkAll_roleTree1" onchange="checkAll('roleTree1')" style="margin-left: 5px">
						可选用业务系统:
					</div>
					<div style="margin-left: 408px; width: 200px;">
						<input type="checkbox" id="checkAll_roleTree2" onchange="checkAll('roleTree2')">
						<font style="size: 18px;">已选用业务系统:</font>
					</div>
					<div class="logchoose clear ztree" id="roleTree1"
						style="width: 320px; overflow-y: scroll;"></div>
					<div class="logchoosebtn"> 
						<input type="button" class="btn" style="height:24px;" value="&gt;&gt;" id="SysLtoRaddBtn" onclick="setDeviceSelectNode('roleTree1', 'roleTree2');" />
						<input type="button" class="btn" style="height:24px;" value="&lt;&lt;" id="SysRtoLaddBtn" onclick="setDeviceSelectNode('roleTree2', 'roleTree1');" />
					</div>
					<div class="logchoose ztree" id="roleTree2" style="width: 320px; overflow-y: scroll;"></div>
				</div>
				<div class="btnbar clear btnbar1" style="text-align:right; margin-right:5px;">
					<input type="hidden" id="newGatherLogTaskId" /> 
					<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('add_sysRole_Div')" style="margin-left:0; margin-right:5px;"/>
					<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveRoleTree()" style="margin-left:0; margin-right:5px;"/>
				</div>
			</div>
		</div>
	</div>
	<div id="add_CloudService_Div" style="display: none;height: 440px;width:530px; padding-top:0; padding-left:0; padding-right:0;">
		<div id="add_CloudService_Div1" class="logpanel" style="width: 660px; border: 1px solid #EEEEEE;">
			<div class="logchoosecontent" style="float: left;margin-left:19px; margin-top:19px; padding-bottom:20px;">
				&nbsp;<input type="radio" name="chooseCloud" id="radio3" value="3" onchange="setCloudMoveNodeDisable()"><icms-i18n:label name="sys_l_allCS"></icms-i18n:label>
				&nbsp;&nbsp;<input type="radio" name="chooseCloud" id="radio4" value="4" onchange="setCloudMoveNodeUnDisable()"><icms-i18n:label name="sys_l_assignCS"></icms-i18n:label>
				<div id="CloudService" style="float: left;margin-left: 0px">
					<div style="float: left;margin-left:5px">
						<input type="checkbox" id="checkAll_roleTree3" onchange="checkAll('roleTree3')"><icms-i18n:label name="sys_l_canChoseCS"></icms-i18n:label>:
					</div>
					<div style="padding-left: 408px; width: 100px;">
						<input type="checkbox" id="checkAll_roleTree4" onchange="checkAll('roleTree4')"><icms-i18n:label name="sys_l_choseCS"></icms-i18n:label>:
					</div>
					
					<div class="logchoose clear ztree" id="roleTree3" style="width: 320px; overflow-y: scroll;"></div>
					<div class="logchoosebtn">
						<input type="button" class="btn" value="&gt;&gt;" style="padding-bottom: 20px;" id="CloudLtoRaddBtn" onclick="setDeviceSelectNode('roleTree3', 'roleTree4');" />
						<input type="button" class="btn" value="&lt;&lt;" style="padding-bottom: 20px;" id="CloudRtoLaddBtn" onclick="setDeviceSelectNode('roleTree4', 'roleTree3');" />
					</div>
					<div class="logchoose ztree" id="roleTree4" style="width: 320px; overflow-y: scroll;"></div>
				</div>
				<div class="btnbar clear btnbar1" style="text-align:right; margin-right:5px;">
					<input type="hidden" id="newGatherLogTaskId" /> 
					<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('add_CloudService_Div')" style="margin-left:0; margin-right:5px;"/>
					<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveCloudTree()" style="margin-left:0; margin-right:5px;"/>
				</div>
			</div>
		</div>
	</div>

	<div id="set_limit_Div" style="display: none;">
		<div id="set_limit_Div1" class="pageFormContent">
			<table border="0" cellpadding="0" bordercolor="#0099ff" cellspacing="1" width="100%">
				<tr height="30px">
					<td><icms-i18n:label name="sys_l_CPUQuota"></icms-i18n:label>：</td>
				</tr>
				<tr height="30px">
					<td>
						<icms-ui:dic name="limitCpu" id="limitCpu" kind="LIMIT_USER_CPU" showType="flatSelect" attr="style='width: 85px;'"/>
					</td>
				</tr>
				<tr height="30px">
					<td><icms-i18n:label name="sys_l_memoryQuota"></icms-i18n:label>：</td>
				</tr>
				<tr height="30px">
					<td>
						<icms-ui:dic name="limitMem" id="limitMem" kind="LIMIT_USER_MEM" showType="flatSelect" attr="style='width: 85px;'"/>
					</td>
				</tr>
			</table>
			<div class="btnbar clear btnbar1" style="text-align:right; margin-right:19px; margin-top:30px">
				<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' onclick="closeView('set_limit_Div')" style="margin-left:0; margin-right:5px;"/>
				<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_delQuota"></icms-i18n:label>' onclick="clearUserLimit()" style="margin-left:0; margin-right:5px;"/>
				<input type="button" class="btn btn_dd2 btn_dd3" value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="saveUserLimit()" style="margin-left:0; margin-right:5px;"/>
			</div>
		</div>
	</div>

<input type="hidden" id="userId" name="userId" value="">
<input type="hidden" id="loginNameOld" name="loginNameOld" value="">
<input type="hidden" id="loginPassOld" name="loginPassOld" value=""> 
</body>
</html>