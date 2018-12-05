<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<!-- 引入全局变量及上下文 -->
    <%@ include file="/common/taglibs.jsp"%>
    <!-- 引入全局样式及公用函数 -->
	<%@ include file="/common/meta.jsp"%>
	<!-- 引入jquery公用文件 -->
	<%@ include file="/common/jquery_common.jsp"%>
	<script type="text/javascript" src="${ctx}/pages/resource/js/displayMachineResource.js"></script>
  </head>
  
  <body class="main1">
    <div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="info_host_res"/></div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
				<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td class="tabBt" style="width:76px;" ><icms-i18n:label name="info_vm_datacenter"/>：</td>
						<td class="tabCon" ><icms-ui:dic id="queryDataCenter" name="queryDataCenter" showType="select"
								sql = "SELECT ID VALUE, DATACENTER_CNAME NAME FROM RM_DATACENTER WHERE IS_ACTIVE ='Y' ORDER BY DATACENTER_CNAME DESC " 
								attr="class='selInput'" /></td>
						<td class="tabBt" style="width:84px;"><icms-i18n:label name="info_vm_res_pool"/>：</td>
						<td class="tabCon" >
							<select id="poolIdSel" class="selInput" >
								<option  id="" value=""><icms-i18n:label name="info_vm_select"/>...</option>
							</select>
						</td>
						<td class="tabBt" style="width:55px;"><icms-i18n:label name="info_vm_cluster"/>：</td>
						<td class="tabCon">
							<select id="clusterIdSel" class="selInput" >
								<option  id="" value=""><icms-i18n:label name="info_vm_select"/>...</option>
							</select>
						</td>
					<tr/>
					<tr>
						<td class="tabBt" style="width:76px;" ><icms-i18n:label name="info_vm_platform"/>：</td>
						<td class="tabCon"><icms-ui:dic id="platFormIdSel" name="platFormIdSel" showType="select"
								sql = "SELECT PLAY.PLATFORM_ID AS value,PLAY.PLATFORM_NAME AS name  FROM RM_PLATFORM PLAY WHERE PLAY.IS_ACTIVE='Y' " 
								attr="class='selInput'" /></td>
						<td class="tabBt" style="width:80px;"><icms-i18n:label name="info_vm_vir_type"/>：</td>
						<td class="tabCon">
							<select id="vmTypeSel" class="selInput" >
								<option  id="" value=""><icms-i18n:label name="info_vm_select"/>...</option>
							</select>
						</td>
						<td class="tabBt" style="width:55px;"><icms-i18n:label name="info_host_name"/>：</td>
						<td  class="tabCon"><input  name="selectHostName" class="textInput" id="selectHostName"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="searchBtn_right" style="width:28%; float:left" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td align="left" colspan="2" width="45%" style="line-height:60px;">
							<a href="javascript:search()" type="button" class="btn" onclick="search();return false;" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' style="vertical-align:middle">
								<span class="icon iconfont icon-icon-search"></span>
								<span>查询</span>
							</a>
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel">
								<span class="icon iconfont icon-icon-clear1"></span>
								<span>清空</span>
							</button>
						</td>	
						</tr>
					</table>
	    </div>
	</div>
			</form>
		</div>
		<div class="pageTableBg">
			<div class="panel clear" id="gridTableDiv">
			<table id="gridTable" ></table>
			<div id="gridPager"></div>
		</div>	
		</div>
		
	</div>	
	<div id="viewDiv" style="display: none; padding-top:10px;" class="pageFormContent modo-pageFormContent">
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_vm_datacenter"/>：</i>
			<input id="dataCenter" class="readonly"  size="30" readonly="readonly"/>
		</p>
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_vm_res_pool"/>：</i>
			<input id="poolName" class="readonly" readonly="readonly" size="30">
		</p>
		<!-- <p style="width: 350px;">
			<i>CDP：</i>
			<input id="cdpName" class="readonly" readonly="readonly" size="30">
		</p> -->
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_vm_cluster"/>：</i>
			<input id="clusterName" class="readonly" readonly="readonly" size="30">
		</p>
		<p style="width:80%;">
			<i><icms-i18n:label name="info_host_name"/>：</i>
			<input id="hostName" class="readonly" readonly="readonly" size="30" style="width:80%;">
		</p>
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_vm_platform"/>：</i>
			<input id="platForm" class="readonly" readonly="readonly" size="30">
		</p>
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_vm_vir_type"/>：</i>
			<input id="virtualName" class="readonly" readonly="readonly" size="30">
		</p>
		<p style="width: 300px;">
			<i>CPU：</i>
			<input id="cpu" class="readonly" readonly="readonly" size="30">
		</p>
			<p style="width: 300px;">
			<i><icms-i18n:label name="info_vm_mem"/>：</i>
			<input id="mem" class="readonly"  size="30" readonly="readonly"/>
		</p>
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_vm_status"/>：</i>
			<input id="status" class="readonly" readonly="readonly" size="30">
		</p>
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_host_controlTime"/>：</i>
			<input id="controlTime" class="readonly" readonly="readonly" size="30">
		</p>
		<p style="width: 300px;">
			<i>SN：</i>
			<input id="sn" class="readonly" readonly="readonly" size="30">
		</p>
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_host_factory"/>：</i>
			<input id="manufacturer" class="readonly" readonly="readonly" size="30">
		</p>
		<p style="width: 300px;">
			<i><icms-i18n:label name="info_host_model"/>：</i>
			<input id="model" class="readonly" readonly="readonly" size="30">
		</p>
		<p style="width: 320px;">
			<i>IP：</i>
			<i id="ips" style="color:#858585;width:200px;"></i>
		</p>
	</div>						
  </body>
</html>
