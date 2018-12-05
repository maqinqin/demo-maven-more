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

<script type="text/javascript" src="${ctx}/common/sha1.js"></script>
<script type="text/javascript"
	src="${ctx}/pages/sys/uploadLogo/js/uploadLogo.js"></script>
<style>

</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg">
					<icms-i18n:label name="sys_l_UpdateLogo"></icms-i18n:label>
				</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg" style="height:450px">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width: 100%; float: left;">
					<div id="addParameterDiv" style="background-color:;"
						class="pageFormContent">
						<input type="hidden" id="myParamName" name="myParamName" value="">
						<form action="" method="post" id="addParameterForm">
							<table width="50%" cellpadding="0" cellspacing="0"
								style="line-height: 50px;">
								<h3 style="font-size:20px;">云管平台管理控制台Logo / 云管平台租户控制台Logo</h3>
								<tr>
									<td style="width: 100px;"><font color="red">*</font>参数名称：</td>
									<td>
									  <input type="radio" id="paramName" name="paramName" class="textInput"  value="CloudTubeLogo" checked="checked" style="width:10px;position: relative;top: 7px;"/> <span>云管平台管理控制台Logo</span>
									  <input type="radio" id="paramName" name="paramName" class="textInput"  value="TenantLogo"  style="width:10px;position: relative;top: 7px;left:10px;" /><span style="margin-left: 13px;">云管平台租户控制台Logo</span>
									</td>
								</tr>

								<tr id="uploadImg" >
									<td style="width: 100px;"><font color="red">*</font>上传图片：</td>
									<td ><input id="paramValueImg" name="paramValue" type="file" onchange="selectImage(this);" style="width: 210px;"/></td>
								</tr>

							</table>
							<table width="100%" cellspacing="0" cellpadding="0" border="0">
                                <tr>
                                    <td align="center" style="padding-top: 15px;">
                                     <a href="javascript:void(0)" class="btn" title="保存" id="saveParameter"
                                        name="saveParameter" onclick="updateOrSaveForParam();return false;">
				                        <span class="icon iconfont icon-icon-save"></span>
				                        <span class="text">保存</span>
				                     </a>  
                                        <input type="hidden" id="updateFlag" name="updateFlag" value="1" /> 
                                        <input type="hidden"id="deleteFlag" value="1" /></td>
                                </tr>
                            </table>
						</form>
					</div>		    
				</div>
			</div>
			 <div style="width: 333px;height: 107px;position: absolute;top:30px;left: 600px;border: solid;border-color: #eee;background: #f5f5f5;">
                <img id="img" alt="" src="" style="width: 331px;height:104px;">
             </div>
		</div>
	</div>
</body>
</html>