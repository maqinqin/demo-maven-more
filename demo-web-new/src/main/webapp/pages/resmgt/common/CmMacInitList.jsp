<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>



<script type="text/javascript" src="${ctx}/pages/resmgt/common/js/CmMacInitList.js"></script>
<script type="text/javascript">
	$(function() {
		initCmMacList();
	});
</script>
</head>
<body class="main1">
	<div id="list" class="content-main clear">
		<div class="page-header">
			<h1>
				资源管理/裸机安装 <small>  查询待安装的裸机，发起安装，查看安装进度。</small>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent">
			<form>
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
					<table height="12%" width="100%" align="center">
					<tr style=" height:55px; ">
						<td class="tabBt" style="width:70px;">SN地址：</td>
						<td class="tabCon">
						<input name="serialNo" id="serialNo" type="text" class="textInput readonly" /></td>
						<td class="tabBt">MAC地址：</td>
						<td class="tabCon">
						<input name="macAddr" id="macAddr" type="text" class="textInput readonly" /></td>
						<td class="tabBt">状态：</td>
						<td class="tabCon"><select id="tm_istState" name="tm_istState"
							class="selInput" style="width: 100%;">
								<option selected="selected" value=""><icms-i18n:label name="zxc2"/></option>
								<option value="I">等待初始化</option>
								<option value="P">未初始化</option>
								<option value="C">初始化完成</option>
						</select>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td align="center" style="width:30%">
						<input type="button" class="Btn_Serch"title="查询" onclick="search()" /> 
						<input type="reset" title="清空"class="Btn_Empty" style="text-indent:-999px;"/> 
						<shiro:hasPermission name="sbxxgl_delete">
						<input type="button" class="Btn_Initiallization"  title="初始化"onclick="initMacSystem()" />
						<input type="hidden" id="initFlag" name="initFlag" value="1" />
						</shiro:hasPermission> 
						<shiro:hasPermission name="sbxxgl_update">
						<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
						</shiro:hasPermission></td>
						</tr>
					</table>
		</div>
	</div>
			</form>
		</div>
	</div>
	<div class="panel clear" id="deviceGridTable_div">
		<table id="macGridTable"></table>
		<table id="gridPager"></table>
	</div>


</body>
</html>
