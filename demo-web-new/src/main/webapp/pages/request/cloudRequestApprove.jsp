<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.git.cloud.request.model.vo.BmSrVo"%>
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
		<title><icms-i18n:label name="bm_l_service_approval"/></title>
		<script type="text/javascript">
         var baseUrl="<%=path%>";
         var srType = "${bmSrVo.srTypeMark}";
         var srId = "${bmSrVo.srId}";
       </script>
		<script type="text/javascript"
			src="<%=path%>/pages/request/js/cloudRequestApprove.js"></script>
	</head>
	<body class="main1">
		<div class="main">
			<jsp:include page="includeInfo.jsp" />
			<div class="panel" style="width: 96%;margin-top: 20px">
				<h1 >
					<icms-i18n:label name="bm_l_service_request"/>（<label id="titlePage"></label>）
				</h1>
			</div>
			<div id="gridMain" class="gridMain" style="width: 96%;">
				<table id="gridTable"></table>
				<table id="gridPager"></table>
			</div>
			<div class="panel" style="width: 96%;margin-top: 20px; background:#fff;">
				<form id='form_approve' name="form_approve" action="" method='post'>
					<input id="srId" name="bmApproveVo.srId" type="hidden"
						class="form_input" value="${bmSrVo.srId }" />
					<input id="toDoId" name="bmApproveVo.todoId" type="hidden"
						class="form_input" value="${todoId}" />
					<input id="approveMark" name="bmApproveVo.approveMark"
						type="hidden" class="form_input" value="${bmSrVo.approveMark }" />
					<h1>
						<icms-i18n:label name="bm_l_approval_details"/>
					</h1>
					<table border="0" cellpadding="0" cellspacing="0" width="96%"
						align="center" >
						<tr>
							<td align="right" style="height: 50px; width: 47%;">
								<icms-i18n:label name="bm_approval_result"/>：
							</td>
							<td align="left" style="height: 50px; width: 68%;">
								<select id="approveResult" name="bmApproveVo.approveResult" class="selInput">
									<option value="1">
										<icms-i18n:label name="bm_approval_yes"/>
									</option>
									<option value="0">
										<icms-i18n:label name="bm_approval_no"/>
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" style="height: 50px; width: 10%;">
								<icms-i18n:label name="bm_approval_approveRemark"/>：
							</td>
							<td align="left" style="height: 30px; width: 90%;">
								<textarea id="approveRemark" name="bmApproveVo.approveRemark"
									rows="4" cols="80" class="textInput" style="width:147px;"></textarea>
							</td>
						</tr>
						<tr>
							<td align="center" style="height:50px; text-align:right; margin-bottom:20px;" colspan="2" class="btnbar1"> 
								<input type="button" class="btn btn_dd2 btn_dd3" onclick="javascript:history.go(-1);" value='<icms-i18n:label name="common_btn_return"/>' />
								<input type="button" class="btn btn_dd2 btn_dd3" id="submit_btn" value='<icms-i18n:label name="common_btn_ensure"/>' onclick='submitFrm()' />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="showCategoryInfo" style="width: 96%;">
				<table id="gridTablePriceApproval"></table>
				<!-- <table id="gridPagerPriceApproval"></table> -->
			</div>
	</body>
</html>
