<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

		<title>确认实施</title>
		<script type="text/javascript">
         var baseUrl="<%=path%>";
         var srType = "${bmSrVo.srTypeMark}";
         var srId = "${bmSrVo.srId}";
         var todoId = "${todoId}";
         var actionType = 'detail';
         var attrRrinfoIds = "${bmSrVo.attrRrinfoIds}";
       </script>
        <script type='text/javascript' src='${ctx}/scripts/json.js'></script>
        <script type="text/javascript" src="<%=path%>/pages/request/js/cloudRequestParam.js"></script>
		<script type="text/javascript" src="<%=path%>/pages/request/js/cloudRequestIndex.js"></script>

	</head>
	<body class="main1">
		<div class="main">
			<jsp:include page="includeInfo.jsp" />
			<!-- 申请类型：供给Start -->
			<div class="panel" style="width: 96%;margin-top: 20px">
				<h1>
					<icms-i18n:label name="bm_l_resApplication_info"/>（<label id="titlePage"></label>）
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
						<td align="center" colspan="2" style="text-align:right; margin-bottom:20px;" class="btnbar1">
							<c:if test="${bmSrVo.srStatusCode == 'REQUEST_WAIT_OPERATE'}">
								<input type="button" class="btn btn_dd2 btn_dd3" id="beginOperateBtn"
									onclick="beginOperate();" value='<icms-i18n:label name="common_btn_beginOperate"/>' />
							</c:if>
							<c:if test="${bmSrVo.srStatusCode == 'REQUEST_OPERATING'}">
								<input type="button" class="btn btn_dd2 btn_dd3" id="overOperateBtn"
									onclick="overOperate();" value='<icms-i18n:label name="common_btn_overOperate"/>' />
							</c:if>
							<input type="button" class="btn btn_dd2 btn_dd3" id="submit_btn" onclick="javascript:history.go(-1);" value='<icms-i18n:label name="common_btn_return"/>' />
						</td>
					</tr>
				</table>
			</form>
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
							<input type="button" id="cancel_attr_btn" value='<icms-i18n:label name="commom_btn_cancel"/>' class="btn" /> <br>
							<input type="button"  id="save_attr_btn" value='<icms-i18n:label name="common_btn_save"/>' class="btn" /> 
							<input type="button"  id="save_service" value='<icms-i18n:label name="common_btn_ensure"/>' class="btn" /> 
							<input type="button"  id="pre_attr" value='<icms-i18n:label name="common_btn_previous_step"/>' class="btn" /> 
						</td>
					</tr>
				</table>
			</div>
	</body>
</html>
