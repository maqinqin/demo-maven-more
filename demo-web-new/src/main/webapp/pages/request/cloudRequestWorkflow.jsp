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

		<title>流程连接</title>
		<style type="text/css">
.ui-tabs {
	width: 94%;
	border: 0px none #FFF;
	background: none;
}

.ui-tabs-nav {
	border: 0px none #FFF;
	background: #a5cbd4;
}

.ui-tabs .ui-tabs-panel {
	background-color: #FFF;
}

.ui-tabs .ui-tabs-nav li {
	margin-bottom: 0px;
}

.gridMain_ {
	margin: 0 auto;
	width: 100%;
	border: solid 1px #abc6d1;
}
</style>
		<script type="text/javascript">
         var baseUrl="<%=path%>";
         var srType = "${bmSrVo.srTypeMark}";
         var srId = "${bmSrVo.srId}";
         var todoId = "${todoId}";
         var actionType = 'detail';
         var attrRrinfoIds = "${bmSrVo.attrRrinfoIds}";
         
         $(function() {
           $("#tabs").tabs();
         });
         function changeTab(tabsShow,tabsHide){
             $('#'+tabsShow).show();
             $('#'+tabsHide).hide();
         }
       </script>
		<script type="text/javascript"
			src="<%=path%>/pages/request/js/cloudRequestParam.js"></script>
		<script type="text/javascript"
			src="<%=path%>/pages/request/js/cloudRequestWorkflow.js"></script>
	</head>
	<body class="main1">
		<div class="main" align=center>
			<jsp:include page="includeInfo.jsp" />
			<div class="panel" style="width: 96%;margin-top: 2px">
				<h1>
					<icms-i18n:label name="bm_l_resApplication_info"/>（<label id="titlePage"></label>）
				</h1>
			</div>
			<div id="gridMain" class="gridMain" style="width:96%;">
				<table id="gridTable"></table>
				<table id="gridPager"></table>
			</div>
			<div id="title3" class="panel" style="width: 96%;margin-top: 20px;">
				<h1 style="text-align:center;">
					<icms-i18n:label name="bm_l_start_chileProcess"/>
				</h1>
			</div>
			<div id="gridMainDiv1" class="gridMain" style="width:96%;">
				<table id="wfLinkTable"></table>
			</div>
			<form id='form' name="form" action="" method='post'>
				<table border="0" cellpadding="0" cellspacing="0" width="96%"
					align="center">
					<tr>
						<td align="center" style="height: 50px; text-align:right; " colspan="2" class="btnbar1">
							<input type="button" class="btn btn_dd2 btn_dd3" id="overOperateBtn" onclick="interface_submit();" value='<icms-i18n:label name="common_btn_overOperate"/>' style="margin-right:5px; margin-left:0;"/>
							<input type="button" class="btn btn_dd2 btn_dd3" id="submit_btn" onclick="javascript:history.go(-1);" value='<icms-i18n:label name="common_btn_return"/>' style="margin-right:5px; margin-left:0;"/>
						</td>
					</tr>
				</table>
			</form>
			
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
				<table border="0" cellpadding="0" cellspacing="0" width="96%"
					align="center">
					<tr>
						<td align="center" style="height: 50px">
							<input type="button"  id="pre_attr" value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn" />
							<input type="button" id="save_service" value='<icms-i18n:label name="common_btn_ensure"/>' class="btn" /> 
							<input 	type="button" id="cancel_attr_btn" value='<icms-i18n:label name="commom_btn_cancel"/>' class="btn" /> <br>
							<input type="button"  id="save_attr_btn" value='<icms-i18n:label name="common_btn_save"/>' class="btn" /> 
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
