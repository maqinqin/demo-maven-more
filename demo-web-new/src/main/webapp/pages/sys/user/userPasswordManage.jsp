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

<script type="text/javascript" src="${ctx}/common/sha1.js"></script>
<script type="text/javascript" src="${ctx}/pages/sys/user/js/userPasswordManage.js"></script>
<style>
.searchInWrap_top{
	left: 25%;
	position: relative;
}
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_l_ModifyPassword"></icms-i18n:label></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<input type="hidden" id="oldPwd">
						<input type="hidden" id="userId">
						<table id = "demo" height="22%" width="100%" align="center">
							<tr style=" height:55px; ">
							    <td class="tabBt" style="width:70px;"><icms-i18n:label name="sys_l_loginName"></icms-i18n:label>：</td>
								<td class="tabCon">
									<input type="text" disabled="disabled"  id="loginName_labl" name="loginName_labl" class="textInput"/>
								</td>
						
							</tr>
							<tr style=" height:55px; ">
							    <td class="tabBt" style="width:70px;"><icms-i18n:label name="sys_l_loginPwd"></icms-i18n:label>：</td>
								<td class="tabCon">
									<input type="password" id="sys_l_loginPwd" name="loginPassword_labl" class="textInput" onblur="return contrast();"/>
									<span id="userloginpwd"></span>
								</td>
							</tr>
							<tr style=" height:55px; ">
							    <td class="tabBt" style="width:70px;"><icms-i18n:label name="sys_l_ModifyNewPwd"></icms-i18n:label>：</td>
								<td class="tabCon">
									<input type="password" id="loginNewPwd_labl" name="loginNewPwd_labl" class="textInput"/>
									<span ></span>
								</td>
							</tr>
							<tr style=" height:55px; ">
							    <td class="tabBt" style="width:70px;"><icms-i18n:label name="sys_l_ModifyConNewPwd"></icms-i18n:label>：</td>
								<td class="tabCon">
									<input type="password" id="loginNewPwd2_labl" name="loginNewPwd2_labl" class="textInput" onblur="return checkPass();"/>
									<span id="usermsg"></span>
								</td>
							</tr>
							<tr style=" height:105px; " >
							<td></td>
								<td class="tabBt" style="width:90px;" align="center">
								<a href="javascript:void(0)" class="btn"  id="saveUser" name="saveUser" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' onclick="return saveUserBtn()"><span class="fa fa-save"></span> <icms-i18n:label name="com_btn_save"></icms-i18n:label></a>
									<button class="btnDel" type="reset" id="cencelUser" name="cencelUser" title='<icms-i18n:label name="common_btn_reset"></icms-i18n:label>'  value='<icms-i18n:label name="common_btn_reset"></icms-i18n:label>'  onclick="formReset()"><span class="fa fa-reply"></span> <span><icms-i18n:label name="common_btn_reset"></icms-i18n:label></span></button>
								</td>
							</tr>
							
						</table>
					</div>
				</div>
	
			</div>
		</div>
		
	</div>

</body>
</html>