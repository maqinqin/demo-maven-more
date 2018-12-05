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
<script type="text/javascript" src="${ctx}/pages/log/js/notification.js"></script>
<style type="text/css">
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_notice_head"/></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:55px;"><icms-i18n:label name="sys_notice_status"/>：</td>
					<td class="tabCon">
						<icms-ui:dic id="state"
								name="state" kind="STATE" showType="select"
								attr="style='width: 150px;'class='selInput'" />
					</td>
				    <td class="tabBt"><icms-i18n:label name="sys_notice_op_type"/>：</td>
					<td class="tabCon">
						<icms-ui:dic id="operation_Type"
								name="operation_Type" kind="OPERATIONTYPE" showType="select"
								attr="style='width: 150px;'class='selInput'" />
					</td>
				    <td class="tabBt"><icms-i18n:label name="sys_notice_type"/>：</td>
					<td class="tabCon">
					<icms-ui:dic id="type"
								name="type" kind="TYPE" showType="select"
								attr="style='width: 150px;'class='selInput'" />
					</td>
					
				</tr>
			</table>
		</div>
	</div>
	<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td   width="10%" align="center" colspan="2">
					<shiro:hasPermission name="user:list">  
						<a href="javascript:void(0)" type="button" class="btn" onclick="searchNotificationLog();" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'  style="vertical-align:middle;">
							<span class="icon iconfont icon-icon-search"></span>
							<span>查询</span>
						</a>
					</shiro:hasPermission>
					<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel" onclick="clearAll()" >
						<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
					</button> 
			        <shiro:hasPermission name="user:save"> 
			            <input type="hidden" id="updateFlag" name="updateFlag" value="1" />
			        </shiro:hasPermission>
			        <shiro:hasPermission name="user:delete"> 
			            <input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
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
		
	

</body>
</html>
	