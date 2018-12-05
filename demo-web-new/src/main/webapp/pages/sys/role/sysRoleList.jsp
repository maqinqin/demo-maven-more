<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/pages/sys/role/js/sysRoleList.js"></script>
<script type="text/javascript" src="${ctx}/pages/sys/role/js/authorization.js"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-slider.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/slider.css"></link>
<script type="text/javascript">
	$(function() {
		initSysRoleList();
		// 自适应宽度
		$("#gridTable").setGridWidth($("#gridMain").width());
		$(window).resize(function() {
			$("#gridTable").setGridWidth($("#gridMain").width());
		});
	});
</script>
<style type="text/css">
#sysRoleUpdateDiv p{width:280px; padding:0; padding-left:16px;}
#sysRoleUpdateDiv p i{width:80px; text-align:left;}
#sysRoleUpdateDiv label{display:block; padding-left:100px;}
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_role"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:62%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:55px;"><icms-i18n:label name="sys_l_roleName"></icms-i18n:label>：</td>
					<td class="tabCon">
						<input type="text" id="roleName" name="roleName" class="textInput"/>
					</td>
					 <td class="tabBt" style="width:30px;"></td>
					 <td class="tabCon"></td>
					 <td class="tabBt"></td>
					 <td class="tabCon"></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:31%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td>
					        <a href="javascript:search()" type="button" class="btn" onclick="search();return false;" title='<icms-i18n:label name="sys_btn_queryRole"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
					        	<span class="icon iconfont icon-icon-search"></span>
								<span>查询</span>
					        </a>
					        <button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick="clearAll()">
					        	<span class="icon iconfont icon-icon-clear1"></span>
								<span>清空</span>
					        </button>
			            <shiro:hasPermission name="role:save">  
							<a href="javascript:void(0)" type="button"  onclick="createSysRole();" class="btn" title='<icms-i18n:label name="com_btn_add"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'>
								<span class="icon iconfont icon-icon-add"></span>
								<span>添加</span>
							</a>
						</shiro:hasPermission> 
						<shiro:hasPermission name="role:authorization">
						<input type="hidden" id="roleAuthorizationFlag" name="roleAuthorizationFlag" value="1" />
						</shiro:hasPermission> 
						<shiro:hasPermission name="role:delete">
								<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
						</shiro:hasPermission> 
						
						<shiro:hasPermission name="role:update">
								<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
						</shiro:hasPermission> 
						<shiro:hasPermission name="role:userList">
								<input type="hidden" id="userListFlag" name="userListFlag" value="1" />
						</shiro:hasPermission>
					</td>
						</tr>
					</table>
	</div>
</div>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="gridMain">
			<div>
			<table id="gridTable" ></table>
					<div id="gridPager"></div></div>
		</div>
		</div>
				
	</div>
	
	<div id="sysRoleUpdateDiv" style="display: none;">
		<form action="" method="post" id="updateForm">
		<div id="sysRole" class="pageFormContent" >
			<input type="hidden" id="roleMethod" name="roleMethod" />
			<input type="hidden" id="roleIsActive" name="roleIsActive" />
				<p>
					<i><font color="red">*</font><icms-i18n:label name="sys_l_roleName"></icms-i18n:label>：</i>
					<input type="text" id="roleNameInput" name="roleNameInput" class="textInput"></input>
				</p>
				<p>
				<i><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</i>
					<textarea  id="remarkInput" name="remarkInput" align="left" style="hegiht:35px; "class="textInput" ></textarea>
				</p>
			<input type="hidden" id="roleId" name="roleId"  />
		</div>
	  	<p class="winbtnbar btnbar1" style="text-align:right; margin-bottom:20px; width:280px;">
	  	<input type="button"  title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3"  onclick="closeView('sysRoleUpdateDiv')" style="margin-right: 25px; margin-left:0;" />
	  	<input type="button" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="saveOrUpdateBtn()" style="margin-right: 5px; margin-left:0;" /></p>
        </form>
	</div>
	
	<div id="SysRoleShowDiv" class="div_center" style="display: none;">
		<form action="" method="post" id="showForm">
		<div id="sysRoleShow">
			<table class="pagelist">
			   <tr>
					<th><icms-i18n:label name="sys_l_roleName"></icms-i18n:label>：</th>
					<td><label id="roleNameShow"></label></td>
				</tr>
				<tr>
					<th><icms-i18n:label name="com_l_remark"></icms-i18n:label>：</th>
					<td><label id="remarkShow"></label></td>
				</tr>
			</table>
		</div>
	  	<p class="btnbar btnbar1"><input type="button" class="btn btn_dd2 btn_dd3"  onclick="closeView('SysRoleShowDiv')" style="margin-right: 5px;" /></p>
        </form>
	</div>
		<!-- 查询指定角色下的用户信息 -->
	<div id="userAuthorizationDiv" class="div_center" style="display: none;">
	  <div class="gridMain" >
				<table id="gridTableUser"></table>
				<table id="gridPagerUser"></table>
	 </div>
	 <p class="btnbar btnbar1" style="text-align:right; margin-right:13px;">
			<input type="button" class="btn btn_dd2 btn_dd3"  value='<icms-i18n:label name="com_btn_closeWin"></icms-i18n:label>' onclick="closeView('userAuthorizationDiv')" style="margin-right: 5px; margin-left:0;" />
	 </p>
	</div>
	<jsp:include page="authorization.jsp"></jsp:include>
</body>
</html>