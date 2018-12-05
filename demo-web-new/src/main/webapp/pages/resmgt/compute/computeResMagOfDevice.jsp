<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>可关联的物理机信息</title>

</head>
<body class="main1">
<div id="div_tip"></div>
<div id="content" class="main">

<div  id="rightTopDiv" class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">
    <span class="ui-jqgrid-title">
		<label id="lab_title" style="font-weight:bold;font-size: 14px;"></label>
    </span>
</div>
<input type="hidden" id="cluster_id"></input>

	<div id="deviceDiv">
  		<table id="tab" border="0">
			<tr style="height: 40px;">
				<td width="120px" align="right">物理机名称：</td>
				<td><input type="text" id="deviceName2"></input> </td>
				<td width="120px" align="right">序列号：</td>
				<td><input type="text" id="sn2"></input></td>
				<td width="250px" align="right">
				<table>
				<tr style="text-align:right;">
				<td><input type="button" value="返回" onclick="rtnCanRelevanceInfo()" class="btn_gray" style="margin-right: 5px; margin-left;0;"/></td>
				<td><input type="button" value="查询" onclick="search2();" class="btn_gray" style="margin-right: 5px; margin-left;0;"></input></td>
				<td><input type="button" value="关联" onclick="relevance();" class="btn_gray" style="margin-right: 5px; margin-left;0;"></input></td>
				</tr>
				</table>
				</td>
			</tr>
  		</table>
	 	<table id="deviceTable"></table>
  		<table id="devicePager"></table>
	</div>
	
	<div id="treeDivNAS" style="display: none">
	    <div style="text-align: center;height:90%;width:336px;overflow:auto;background-color:white;float:left;clear:right;border-right: solid #EEEEEE 1PX;">
			<ul id="treeRmNAS" class="ztree" ></ul>
	    </div>
	    
	    <input type="hidden" id="objectIdTemp"></input>
	    <input type="hidden" id="nodeIdTemp"></input>
	    <input type="hidden" id="nodeNameTemp"></input>
		<p style="text-align:right;height: 10%;width: 100%;"><input type="button" value="确定" class="btn_gray" onclick="saveView()" style="margin-right: 5px; margin-left:0;" /><input type="button" class="btn_gray"  value="取消" onclick="closeView()" style="margin-right: 5px; margin-left:0;" /></p>
    </div>

	<div id="setingDiv" class="div_center" style="display: none">
		<input type="hidden" id="objectId" name="objectId" />
		<table id="updateTab" class="pagelist" style="width:100%;float:left;">
			<tr>
				<th><icms-i18n:label name="res_l_comput_userName"/>:</th><td><input type="text" id="username" /><font color="#FF0000">*</font></td>
			</tr>
			<tr>
				<th><icms-i18n:label name="res_l_comput_password"/>:</th><td><input type="password" id="password" /><font color="#FF0000">*</font></td>
			</tr>
		</table>
		<p class="btnbar">
			<input type="button" class="btn"  value='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeSetingDiv()" style="margin-right: 5px;" />
			<input type="button" value='<icms-i18n:label name="common_btn_save"/>' class="btn" onclick="setingBtn()" style="margin-right: 5px;" />
		</p>
	</div>
	
</div>
</body>
</html>