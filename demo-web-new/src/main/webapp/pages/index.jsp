<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="icms-ui" uri="/WEB-INF/tld/icms-ui.tld"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>

<title><icms-i18n:label name="show_title"/></title>
  <script type="text/javascript">
       /*  window.onload = function () {
            if (top.location.href != location.href) {
                top.location.href = location.href;
            }
        }
 */
    </script>
</head>
    <frameset rows="55,*,26" frameborder="0"  framespacing="0" border="0" >
        <frame src="<%=path%>/index/pages/topAct.action" name="top" id="top" scrolling="no"/>
        <frameset cols="201,*" frameborder="0" >
            <frame src="<%=path%>/pages/menu.jsp" name="menu" id="menu" />
            <%--<frame src="<%=path%>/pages/btn.htm" name="btn" /> --%>
            <frame src="" name="main" id="main"/>
        </frameset>
        <framsest frameborder="0" framespacing="0" border="0">
        	  <frame src="<%=path%>/pages/foot.jsp" name="foot" scrolling="no"/>
        </frameset>
    </frameset>

    <noframes>
    	您的浏览器不支持frame框架，请使用IE浏览器！
    </noframes>
</html>
