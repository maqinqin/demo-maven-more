<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>

	<style type="text/css">
	.top_table {
	    width: 94%;
		border-collapse: collapse;
		/**border:red 1px solid;*/
	}  /* 按需修改border宽度 */
	.top_table td {
		padding-top: 5px;
		padding-bottom: 5px;
		/**border:red 1px solid;*/
	} /* 按需修改border宽度 */
    </style>
	</head>
<body class="main1">
<table width="100%">
  <tr>
    <td align=center>
		<table border="0" cellpadding="0" cellspacing="0" class="top_table" style="width:96%; margin:0 auto; background:#fff;">
			<tr>
				<td align="right" width="8%" nowrap>
					<icms-i18n:label name="bm_l_service_number"/>：
				</td>
				<td align="left" width="25%" nowrap>
					<input type="text" class="readonly readonlybg" readonly value="${bmSrVo.srCode}"/>
				</td>

				<td align="right" width="8%" nowrap>
					<icms-i18n:label name="bm_l_request_people"/>：
				</td>
				<td align="left" width="25%" nowrap>
					<input type="text" class="readonly readonlybg" readonly value="${bmSrVo.creator}"/>
				</td>
				<td align="right" width="8%" nowrap>
					<icms-i18n:label name="bm_l_request_time"/>：
				</td>
				<td align="left" width="25%" nowrap>
					<input type="text" class="readonly readonlybg" readonly value="${bmSrVo.createTimeStr}" style="width:200px"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					<icms-i18n:label name="bm_l_appName"/>：
				</td>
				<td align="left">
					<input type="text" class="readonly readonlybg" readonly value="${bmSrVo.appName}"/>
				</td>
				<td align="right">
					<icms-i18n:label name="bm_l_belongDc"/>：
				</td>
				<td align="left">
					<input type="text" class="readonly readonlybg" readonly value="${bmSrVo.datacenterName}"/>
				</td>
				<td align="right" width="8%">
					<icms-i18n:label name="bm_request_outline"/>：	
				</td>
				<td colspan="5" align="left">
					<input type="text" class="readonly readonlybg" readonly value="${bmSrVo.summary}" style="width:80%"/>
				</td>
			</tr>
			<tr>
				<c:if test="${bmSrVo.srTypeMark == 'VS' or bmSrVo.srTypeMark == 'PS'}">
					<td align="right">
						<icms-i18n:label name="bm_request_term"/>：
					</td>
					<td align="left">
						<input type="text" class="readonly readonlybg" readonly value="${bmSrVo.startTimeStr}${bmSrVo.startTimeStr == null ?'':'至'}${bmSrVo.endTimeStr}" style="width:200px"/>
					</td>
				</c:if>
			</tr>
			<c:if test="${bmSrVo.srStatusCode == 'REQUEST_ASSIGN_FAILURE'}">
				<c:if test="${bmSrVo.assignResult != null}">
				<tr>
					<td align="right" width="8%" nowrap>
						<icms-i18n:label name="bm_l_request_failReason"/>：
					</td>
					<td colspan="5" align="left">
						<textarea style="width:100%;height: 30px" readonly disabled>${bmSrVo.assignResult}</textarea>
					</td>
				</tr>
				</c:if>
			</c:if>
		</table>
 	 </td>
  </tr>
</table>
</body>
</html>