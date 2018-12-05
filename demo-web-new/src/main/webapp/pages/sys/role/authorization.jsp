<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/pages/sys/role/js/sysRoleList.js"></script>
<script type="text/javascript" src="${ctx}/pages/sys/role/js/authorization.js"></script>
</head>
<div id="roleAuthorizationDiv" class="div_center" style="display: none;">
		<form action="" method="post" id="authorizationForm">
		<input type="hidden" id="roleAuthorId" name="roleAuthorId"  />
		<input type="hidden" id="roleMenus" name="roleMenus" />
        <table class="pagelist" style="margin-top:0;">
       		 <tr>
	       		 <td>
	       		 	<div id="treeDiv"
					style="overflow: auto;min-height:306px;height:100%;width:107%;background-color:white;float:left;clear:right;">
					<ul id="menuTree" class="ztree" style="width: 332px; overflow: auto;"></ul>
					</div>
				 </td>
			 </tr>
		</table>
		<p class="btnbar btnbar1" style="margin-left: 130px; width:255px;">
		<input type="button" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeView('roleAuthorizationDiv')" style="margin-right: 5px;" />
		<input type="button" title='<icms-i18n:label name="com_btn_confirm"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_confirm"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="saveAuthorization()" style="margin-right: 5px;" />
		</p>
        </form>
		
</div>