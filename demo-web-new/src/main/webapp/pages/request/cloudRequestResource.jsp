<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title><icms-i18n:label name="bm_l_res_distribution_confirm"/></title>
		<link href="${ctx}/css/new.css" rel="stylesheet" type="text/css" />
		  <style>
		  .ui-autocomplete {
		    max-height: 100px;
		    overflow-y: auto;
		    /* 防止水平滚动条 */
		    overflow-x: hidden;
		  }
		  /* IE 6 不支持 max-height
		   * 我们使用 height 代替，但是这会强制菜单总是显示为那个高度
		   */
		  * html .ui-autocomplete {
		    height: 100px;
		  }
		  #updateVmDiv label{display:block; padding-left:10px;}
		  #updateVmTab p{width:350px;}
		  #updateVmTab{text-align:center;}
		  </style>
		<script type="text/javascript" src="js/jquery.bigautocomplete.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			var baseUrl="<%=path%>";
			var srType = "${bmSrVo.srTypeMark}";
			var srId = "${bmSrVo.srId}";
			var todoId = "${todoId}";
			var actionType = 'detail';
			var attrRrinfoIds = "${bmSrVo.attrRrinfoIds}";
		</script>
		<script type="text/javascript"
			src="<%=path%>/pages/request/js/cloudRequestParam.js"></script>
		<script type="text/javascript" src="${ctx}/pages/request/js/cloudRequestResource.js"></script>
	</head>
	<body class="main1">
		<div class="main">
			<jsp:include page="includeInfo.jsp" />
			<div class="panel" style="width:96%;margin-top: 2px">
				<h1>
					<icms-i18n:label name="bm_l_res_distribution_confirm"/>（<label id="titlePage"></label>）
				</h1>
			</div>
			<div id="gridMain" class="gridMain" style="width:96%;">
				<table id="gridTable"></table>
				<table id="gridPager"></table>
			</div>
			<form id='form' name="form" action="" method='post'>
				<table border="0" cellpadding="0" cellspacing="0" width="96%"
					align="center" style="margin-top: 5px">
					<tr>
						<td align="center" colspan="2" style="height: 50px; text-align:right; " class="btnbar1">
							<input type="button" class="btn btn_dd2 btn_dd3" id="submit_btn" onclick="javascript:history.go(-1);" value='<icms-i18n:label name="common_btn_return"/>' />
							<c:if test="${bmSrVo.srTypeMark == 'VS' or bmSrVo.srTypeMark == 'PS'}">
								<c:choose>
									<c:when test="${bmSrVo.srStatusCode == 'REQUEST_ASSIGN_SUCCESS'}">
										<input id="resourceBtn" type="button" class="btn btn_dd2 btn_dd3"  onclick="_submit1();" value='<icms-i18n:label name="common_btn_submit"/>' />
									</c:when>
									<c:otherwise>
										<input id="reAssign" type="button" class="btn btn_dd2 btn_dd3" onclick="resourceAssign();" value='<icms-i18n:label name="common_btn_redistribute"/>' />
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${bmSrVo.srTypeMark == 'VE'}">
								<input id="resourceBtn" type="button" class="btn btn_dd2 btn_dd3"  onclick="_submit1();" value='<icms-i18n:label name="common_btn_submit"/>' />
							</c:if>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 选择物理机 -->
		<div id="devicePageDiv" class="form_dialog_div" style="display: none">
		<div class="form_dialog_content_div" style="width: 100%; height: 85%">
			<table id="deviceTable"></table>
			<div id="pageNavHostPage"></div>
			<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
			<tr>
				<td align="center">
					<input type="button" class="btn" value='<icms-i18n:label name="commom_btn_cancel"/>' onclick="closeView('devicePageDiv');" />
					<input type="button" class="btn" value='<icms-i18n:label name="common_btn_save"/>' onclick="updateDeviceInfo();"/>
				</td>
			</tr>
		    </table>
		</div>
		<div id="select_attr_div" class="div_center" style="display:none">
				<input id="duId_attr" name="duId_attr" type="hidden"
					class="textInput" /> </br>
					<div style="width: 500px; height: 330px" >
						<table >
							<tr>
								<td style="width: 100px"></td>
								<td align="center" style="height: 40px">
									<div style="margin: 0 30px 20px 35px;border: 1px solid #a6caca;">
										<table id="attrTable" ></table>
									</div>
								</td>
							</tr>
						</table>
					</div>
				<table border="0" cellpadding="0" cellspacing="0" width="100%"
					align="center">
					<tr>
						<td align="center" style="height: 50px">
							<input type="button" id="pre_attr" value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn" /> 
							<input type="button" id="save_service" value='<icms-i18n:label name="common_btn_ensure"/>' class="btn" /> 
							<input type="button" id="cancel_attr_btn" value='<icms-i18n:label name="commom_btn_cancel"/>' class="btn" /> <br>
							<input type="button" id="save_attr_btn" value='<icms-i18n:label name="common_btn_save"/>' class="btn" />
						</td>
					</tr>
				</table>
			</div>
			<div id="updateVmDiv" style="display: none">
				<form action="" method="post" id="updateVmForm">
					<div id="updateVmTab"  class="pageFormContent">
						<input type="hidden" id="oldVmName" name="oldVmName" />
						<input type="hidden" id="vmId" name="vmId" />
						<input type="hidden" id="oldVmVmName" name="oldvmVmName" />
						<input type="hidden" id="oldVmVpName" name="oldvmVpName" />
						<input type="hidden" id="rrinfoId" name="rrinfoId" />
						<input type="hidden" id="platFormCode" name="platFormCode" />
						<p style="width:350px;  margin-bottom:0;">
						<i><font color="red">*</font><icms-i18n:label name="bm_l_vmName"/>:</i>
						<input class="textInput" type="text" id="vmName" name="vmName" />
						</p>
						<%-- <p style="width:350px;  margin-bottom:0;">
						<i><font color="red">*</font><icms-i18n:label name="bm_l_lparNamePrefix"/>:</i>
						<input class="textInput" type="text" id="lparNamePrefix" name="lparNamePrefix" />
						</p> --%>
						<!-- <p style="width:350px;  margin-bottom:0;display: none" id="vmManage" >
						<i><font color="red">*</font>虚拟机管理:</i>
						<input class="textInput" type="text" id="vmVmName" name="vmVmName" />
						</p>
						<p style="width:350px;  margin-bottom:0;display: none" id="vmProduct">
						<i><font color="red">*</font>虚拟机生产:</i>
						<input class="textInput" type="text" id="vmVpName" name="vmVpName" />
						</p>	 -->		
						<p style="width:350px;  margin-bottom:0;" id="p_hostName">
						<i><font color="red">*</font><icms-i18n:label name="bm_l_belongHost"/>:</i>
						<input class="textInput" type="text" id="hostName" name="hostName" onclick="showHostList()" style="cursor:pointer" />
						 <input type="hidden" id="hostId" name="hostId"/>
						</p>
					<div class="nowrap right"></div>   
				</div>
				<div class="winbtnbar btnbar1" style="text-align:right; margin-right:54px; margin-bottom:20px;">
					<label id="sp_error_tip"></label>
					<input type="button" id="btnAddSp" value='<icms-i18n:label name="commom_btn_cancel"/>' title='<icms-i18n:label name="commom_btn_cancel"/>' class="btn btn_dd2 btn_dd3" onclick="closeView('updateVmDiv')" style="margin-right: 5px; margin-left:0;" />
					<input type="button" id="btn_add_sp" class="btn btn_dd2 btn_dd3" onclick="submitForm()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px;margin-left: 0px;" />
				</div>
				</form>		
			</div>
			<div id="updateVmIpDiv" style="display: none">
				<form action="" method="post" id="updateVmIpForm">
					<div id="updateVmIpTab"  class="pageFormContent">
						<input type="hidden" id="oldVmIp" name="oldVmIp" />
						<p style="width:350px;  margin-bottom:0;">
						<i><font color="red">*</font>
						<label id="vmIpName"></label></i>
						<input class="textInput" type="text" id="vmIp" name="vmIp" />
						</p>
					</div>
					<div class="winbtnbar" style="text-align:right; margin-right:54px; margin-top:35px;">
						<label id="sp_error_tip"></label>
						<input type="button" id="btn_add_sp" class="btn_gray" onclick="submitIpForm()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px;margin-left: 0px;" />
						<input type="button" id="btnAddSp" value='<icms-i18n:label name="commom_btn_cancel"/>' title='<icms-i18n:label name="commom_btn_cancel"/>' class="btn_gray" onclick="closeView('updateVmIpDiv')" style="margin-right: 5px; margin-left:0;" />
					</div>
					</form>
			</div>
			<div id="updateVmFloatingIpDiv" style="display: none">
				<form action="" method="post" id="updateVmFloatingIpForm">
					<div id="updateVmFloatingIpTab"  class="pageFormContent">
						<input type="hidden" id="oldVmFloatingIp" name="oldVmFloatingIp" />
						<p style="width:350px;  margin-bottom:0;">
						<i><font color="red">*</font>
						<label id="vmFloatingIpName">弹性IP</label></i>
						<select class="textInput" id="vmFloatingIp" name="vmFloatingIp" >
							<option><icms-i18n:label name="l_common_select"/>...</option>
						</select>
						</p>
					</div>
					<div class="winbtnbar" style="text-align:right; margin-right:54px; margin-top:35px;">
						<label id="sp_error_tip"></label>
						<input type="button" id="btn_add_sp" class="btn_gray" onclick="updateVmFloatingIp()" title='<icms-i18n:label name="common_btn_save"/>' value='<icms-i18n:label name="common_btn_save"/>' style="margin-right: 5px;margin-left: 0px;" />
						<input type="button" id="btnAddSp" value='<icms-i18n:label name="commom_btn_cancel"/>' title='<icms-i18n:label name="commom_btn_cancel"/>' class="btn_gray" onclick="closeView('updateVmFloatingIpDiv')" style="margin-right: 5px; margin-left:0;" />
					</div>
					</form>
			</div>
			<div id="gridTable_alternativeHost_div" style="display: none" >
 			<div id="gridTable_alternativeHostButtonDiv" class="panel clear" style="margin-left: 17px;margin-top: px;"> 
			<table id="gridTable_alternativeHost"></table>
 			<table id="gridPager_alternativeHost"></table> 
 			</div>
 			</div>
 			<div id="getVolumeType_div" style="display: none" >
 			<div id="getVolumeTypeDiv" class="panel clear"> 
			<table id="gridTable_getVolumeType"></table>
 			<table id="gridPager_getVolumeType"></table> 
 			</div>
 			</div> 
 			
 			<div id="nwPool_div" style="display: none" >
 			<div id="nwPoolDiv" class="panel clear"> 
			<table id="gridTable_nwPool"></table>
 			<table id="gridPager_nwPool"></table> 
 			</div>
 			</div>
 			
 			<div id="ip_div" style="display: none" >
 			<div id="ipDiv" class="panel clear"> 
			<table id="gridTable_ip"></table>
 			<table id="gridPager_ip"></table> 
 			</div>
 			</div>  
	
	
	</body>
</html>
