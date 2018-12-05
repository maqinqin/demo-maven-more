<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<!-- 引入全局变量及上下文 -->
    <%@ include file="/common/taglibs.jsp"%>
    <!-- 引入全局样式及公用函数 -->
	<%@ include file="/common/meta.jsp"%>
	<!-- 引入jquery公用文件 -->
	<%@ include file="/common/jquery_common.jsp"%>
	<title>通用服务器信息维护</title>
	<script type="text/javascript" src="${ctx}/pages/resmgt/common/js/rmGeneralServer.js"></script>
	<style type="text/css">

	#updateDiv i {text-align:left; padding-right:3px;}
    #updateDiv p{ height:50px;  margin-bottom:5px;  margin-left:18px; float:left }
	/* #updateDiv p .updateDiv_list{display:inline-block; width:296px; float:left;} */
	#updateDiv label{display:block; padding-left:100px;}
	#serviceForm span{width: 290px;float: left;height: 53px;}
	</style>
  </head>
 <body class="main1">
    <div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="bim_title_uniServe"></icms-i18n:label></div>
					<div class="WorkSmallBtBg">
						<small>
					 		<icms-i18n:label name="bim_title_uniDescribe"></icms-i18n:label>
						</small>
					</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<form>
				<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
						<td  class="tabBt" style="width:74px;"><icms-i18n:label name="com_l_serveName"></icms-i18n:label>：</td>
						 <td class="tabCon">
							<input type="text" id="queryServerName" name="queryServerName"  class="textInput"/>
						</td>
						<td class="tabBt" style="width:80px;"><icms-i18n:label name="com_l_serveType"></icms-i18n:label>：</td>
						<td class="tabCon"><icms-ui:dic id="serverTypeSel" name="serverTypeSel" showType="select"
								kind="GANERAL_SERVER_TYPE"	attr="class='selInput'" /></td>
								<td  class="tabBt" style="width:50px;"><icms-i18n:label name="com_l_ip"></icms-i18n:label>：</td>
						<td class="tabCon">
							<input type="text" id="queryIP" name="queryIP"  class="textInput"/>
						</td>
					</tr>
					<tr>			
						
						<td  class="tabBt" style="width:74px;"><icms-i18n:label name="com_l_dataCenter"></icms-i18n:label>：</td>
						<td class="tabCon"><icms-ui:dic id="queryDataCenter" name="queryDataCenter" showType="select"
							sql = "SELECT d.ID AS value, d.DATACENTER_CNAME AS name FROM RM_DATACENTER d WHERE d.IS_ACTIVE='Y'"	
							attr="class='selInput'" /></td>
						
					</tr>
				</table>
			</div>
		</div>
		<div class="searchBtn_right" style="width:31%; float:left; padding-left:2%;" >
					<table height="12%" width="100%" align="center">
						<tr style=" height:52px;">
							<td>
							<a href="javascript:void(0)" type="button" class="btn" onclick="reloadGrid()" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' style="vertical-align:middle">
								<span class="icon iconfont icon-icon-search"></span>
								<span>查询</span>
							</a>
							<button type="reset" title='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_clear"></icms-i18n:label>' class="btnDel">
								<span class="icon iconfont icon-icon-clear1"></span>
								<span>清空</span>
							</button> 
							<shiro:hasPermission name="server:add">
								<a href="javascript:void(0)" type="button" class="btn" onclick="preUpdateOrSaveData()" title='<icms-i18n:label name="com_btn_append"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_add"></icms-i18n:label>'>
									<span class="icon iconfont icon-icon-add"></span>
									<span>添加</span>
								</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="server:delete">
								<input type="hidden" name="deleteFlag" id="deleteFlag" value='1'/>
							</shiro:hasPermission>
							<shiro:hasPermission name="server:updateFlag">
								<input type="hidden" name="updateFlag" id="updateFlag" value='1'/>
							</shiro:hasPermission>
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
			<div id="pageGrid"></div>
		</div>	
		</div>
		
	</div>	
	<div id="updateDiv" style="display: none;" class="pageFormContent">
		<form action="" method="post" id="serviceForm">
		<div id='serviceDiv'>
			<input type="hidden" id="serverId" name="rmGeneralServerVo.id" />	
			<input type="hidden" id="serviceMethod" name="serviceMethod" />
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_serveName"></icms-i18n:label>：</i>
				<input id="serverName" name="rmGeneralServerVo.serverName" class="textInput"/>
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_serveType"></icms-i18n:label>：</i>
				<icms-ui:dic id="type" name="rmGeneralServerVo.type" showType="select"
								kind="GANERAL_SERVER_TYPE"	attr="style='width: 170px;'class='selInput'" />
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_ip"></icms-i18n:label>：</i>
				<input id="serverIp" name="rmGeneralServerVo.serverIp" class="textInput" type="text">
			</span>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="com_l_subnetMask"></icms-i18n:label>：</i>
				<input id="subMask" name="rmGeneralServerVo.subMask" class="textInput" style="width:158px;">
			</span>
			</p>
			<p>
			<span class="updateDiv_list">
				<i><icms-i18n:label name="com_l_gateway"></icms-i18n:label>：</i>
				<input id="gateway" name="rmGeneralServerVo.gateway" class="textInput">
			</span>
			<span class="updateDiv_list">
				<i><font color="red">*</font><icms-i18n:label name="com_l_dataCenter"></icms-i18n:label>：</i>
				<icms-ui:dic id="datacenterId" name="rmGeneralServerVo.datacenterId" showType="select"
							sql = "SELECT d.ID AS value, d.DATACENTER_CNAME AS name FROM RM_DATACENTER d WHERE d.IS_ACTIVE='Y'"	
							attr="style='width: 170px;'class='selInput'" />
			</span>
			</p>
			<p  id="p_username" style="width:270px;">
				<i><font color="red">*</font><icms-i18n:label name="com_l_username"></icms-i18n:label>：</i>
				<input id="userName" name="rmGeneralServerVo.userName" class="textInput">
			</p>
			<p id="p_pwd">
				<i><font color="red">*</font><icms-i18n:label name="com_l_password"></icms-i18n:label>:</i>
				<input class="textInput" type="password" id="password" name="rmGeneralServerVo.password" style="width:160px;"/>
			</p>
		</div>
		</form>
		<div class="winbtnbar btnbar1" style="text-align:right; width:579px; margin-bottom:20px;">
			<label id="sp_error_tip"></label>
			<input type="button" id="btnAddSp" title='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_cancel"></icms-i18n:label>' class="btn btn_dd2 btn_dd3" onclick="closeDiv()" style="margin-right: 5px; margin-left:0;" />
			<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="updateOrSaveForm()" title='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_save"></icms-i18n:label>' style="margin-right: 5px;margin-left: 0px;" />
		</div>
	</div>
	<div id="viewDiv" style="display: none;"  class="pageFormContent">
		<span style="width: 350px;">
			<i><icms-i18n:label name="com_l_serveName"></icms-i18n:label>：</i>
			<input id="viewServerName" class="readonly"  readonly="readonly"/>
		</span>
		<span style="width: 350px;">
			<i><icms-i18n:label name="com_l_serveType"></icms-i18n:label>：</i>
			<input id="typeName" class="readonly" readonly="readonly" >
		</span>
		<span style="width: 350px;">
			<i><icms-i18n:label name="com_l_ip"></icms-i18n:label>：</i>
			<input id="serverIpView" class="readonly" readonly="readonly" >
		</span>
		<span style="width: 350px;">
			<i><icms-i18n:label name="com_l_subnetMask"></icms-i18n:label>：</i>
			<input id="subMaskView" class="readonly" readonly="readonly">
		</span>
		<span>
			<i><icms-i18n:label name="com_l_gateway"></icms-i18n:label>：</i>
			<input id="gatewayView" class="readonly" readonly="readonly" size="30">
		</span>
			<p style="width: 350px;">
			<i><icms-i18n:label name="com_l_dataCenter"></icms-i18n:label>：</i>
			<input id="datacenterName" class="readonly"  size="30" readonly="readonly"/>
		</span>
		<span style="width: 350px;">
			<i><icms-i18n:label name="com_l_username"></icms-i18n:label>：</i>
			<input id="userNameView" class="readonly" readonly="readonly" size="30">
		</span>
	</div>
					
  </body>
</html>
