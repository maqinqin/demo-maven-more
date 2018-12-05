<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ include file="/common/taglibs.jsp"%>
  <%@ include file="/common/meta.jsp"%>
  <%@ include file="/common/jquery_common.jsp"%>
  <script type="text/javascript" src="${ctx}/pages/vmrc/js/vmrc.js"></script>
  <script type="text/javascript" src="${ctx}/pages/vmrc/js/vmrc_common.js"></script>
  <title>VMware 远程控制台</title>
  
</head>
<body onload="initialize()">
  <div>
   <!-- 控制台 -->
   <div style="border: solid 2px blue; width: 100%; height: 800px; overflow: auto;" id="pluginPanel"></div>
   <!-- Log -->
   <div style="overflow: auto; height: 400px; width: 100%; " id="msgBox"></div>
  </div>
</body>
</html>