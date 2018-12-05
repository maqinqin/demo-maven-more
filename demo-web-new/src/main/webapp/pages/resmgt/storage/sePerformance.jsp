<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery_common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${ctx}/pages/resmgt/storage/js/se_rule.js"></script>
<script type="text/javascript" src="${ctx}/pages/resmgt/storage/js/json2.js"></script>
<title>存储资源池-性能级别</title>
</head>
<style>
 .pageTable {
	margin-right: 27px;
	margin-left: 27px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-bottom-style: solid;
	border-left-style: solid;
	border-bottom-color: #E1E1E1;
	border-left-color: #E1E1E1;
}
.pageTable tr th {
	line-height: 35px;
	height: 35px;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-top-color: #E1E1E1;
	border-right-color: #E1E1E1;
	border-bottom-color: #E1E1E1;
	font-weight: bold;
	text-align: center;
	padding-right: 25px;
	padding-left: 25px;
	background-image: url(${ctx}/images/th_bg.png);
	background-repeat: repeat-x;
}

.pageTable tr th:hover {
		background:#eff4f7;
}

.pageTable tr td {
	border-top-width: 1px;
	border-right-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-top-color: #E1E1E1;
	border-right-color: #E1E1E1;
	line-height: 35px;
	height: 35px;
	padding-right: 25px;
	padding-left: 25px;
	text-align: center;
	color: #393939;
}
.pageTable tr .left {
	text-align: left;
}


.pageTable tr td a {
	color: #364c4a;
	text-decoration: none;
	text-align: left;
}

.pageTable tr td a:hover {
	text-decoration: underline;
}
</style>
<body id="right" class="main1">
	<input type="hidden" id="ruleType" value="PERFORMANCE_TYPE" />
	<div id='ruleDiv' style="width:100%;align:center" class="content-main clear">
		<div class="page-header">
			<h1>
				服务自动化设置/存储数据性能级别
					
			</h1>
		</div>
		<div class="pageFormContent">
			<table class="pageTable">
 		 <tr>
          <th>性能级别</th>
          <th>高+</th>
          <th>高</th>
          <th>中</th>
          <th>低</th>
        </tr>
        <tr>
          <td><strong>Response Time</strong></td>
          <td>
            <input type="text" id="11" name="11" style="text-align:center;width:100"/>
          </td>
          <td>
            <input type="text" id="12" name="12" style="text-align:center;width:100"/>
          </td>
          <td>
            <input type="text" id="13" name="13" style="text-align:center;width:100"/>
         </td>
          <td>
            <input type="text" id="14" name="14" style="text-align:center;width:100"/>
          </td>
        </tr>
        <tr>
          <td><strong>IOPS</strong></td>
          <td>
            <input type="text" id="21" name="21" style="text-align:center;width:100"/>
          </td>
          <td>
            <input type="text" id="22" name="22" style="text-align:center;width:100"/>
          </td>
          <td>
            <input type="text" id="23" name="23" style="text-align:center;width:100"/>
         </td>
          <td>
            <input type="text" id="24" name="24" style="text-align:center;width:100"/>
          </td>
        </tr>
        <tr>
          <td><strong>MBPS</strong></td>
          <td>
            <input type="text" id="31" name="31" style="text-align:center;width:100"/>
          </td>
          <td>
            <input type="text" id="32" name="32" style="text-align:center;width:100"/>
          </td>
          <td>
            <input type="text" id="33" name="33" style="text-align:center;width:100"/>
          </td>
          <td>
            <input type="text" id="34" name="34" style="text-align:center;width:100"/>
          </td>
        </tr>
        <tr>
        	<td colspan="6" style="line-height: 50px;text-align: right;"> <input type="button" onclick="saveRule();" class="btn_gray" title="保 存" value="保 存" style="margin-left:0; margin-right:5px;" /> <input type="button" onclick="changeInfo();" class="btn_gray" title="修 改" value="修 改" style="margin-left:0; margin-right:5px;" /></td>
        </tr>
      </table>       
    </div>
</div>
</body>
</html>