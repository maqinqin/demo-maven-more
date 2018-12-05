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
<title>存储资源池-可用性级别</title>
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
	<input type="hidden" id="ruleType" value="AVAILABILITY_TYPE" />
	<div id='ruleDiv' class="content-main clear">
		<div class="page-header">
			<h1>
				服务自动化设置/存储可用性级别
					
			</h1>
		</div>
	<div class="pageFormContent">
		<table class="pageTable">
 			 <tr>
   			  <th>应用级别</th><th>生产数据</th><th>应急数据</th><th>历史数据</th><th>日志数据</th><th>归档数据</th>
    		</tr>
    		 <tr>
    		 	<td><strong>A+</strong></td>
    		 	<td><select id="11" style="width:100"><option value="HIGH">高</option><option value="MIDDLE">中</option><option value="LOW">低</option><option value="-">-</option></select></td>
    		 	<td><select id="12" style="width:100"><option value="HIGH">高</option><option value="MIDDLE">中</option><option value="LOW">低</option><option value="-">-</option></select></td>
	            <td><select id="13" style="width:100"><option value="HIGH">高</option><option value="MIDDLE">中</option><option value="LOW">低</option><option value="-">-</option></select></td>
	            <td><select id="14" style="width:100"><option value="HIGH">高</option><option value="MIDDLE">中</option><option value="LOW">低</option><option value="-">-</option></select></td>
        		<td><select id="15" style="width:100"><option value="HIGH">高</option><option value="MIDDLE">中</option><option value="LOW">低</option><option value="-">-</option></select></td>
    		 </tr>
    		 
    		  <tr>
          <td><strong>A</strong></td>
          <td>
            <select id="21" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
          <td>
            <select id="22" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
          <td>
            <select id="23" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
         </td>
          <td>
            <select id="24" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
         </td>
          <td>
            <select id="25" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
         </td>
        </tr>
          <tr>
          <td><strong>B</strong></td>
          <td>
            <select id="31" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
          <td>
            <select id="32" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
          <td>
            <select id="33" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
          <td>
            <select id="34" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
          <td>
            <select id="35" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
        </tr>
        <tr>
          <td><strong>C</strong></td>
          <td>
            <select id="41" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
         </td>
          <td>
            <select id="42" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
          <td>
            <select id="43" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
          <td>
            <select id="44" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
         </td>
          <td>
            <select id="45" style="width:100">
              <option value="HIGH">高</option>
              <option value="MIDDLE">中</option>
              <option value="LOW">低</option>
              <option value="-">-</option>
            </select>
          </td>
        </tr>
        <tr>
        	<td colspan="6" style="line-height: 50px;text-align: right;"> <input type="button" onclick="saveRule();" class="btn_gray" title="保 存" value="保 存" style="margin-left:0; margin-right:5px;"/>  
	    <input type="button" onclick="changeInfo();" class="btn_gray" title="修 改" value="修 改" style="margin-left:0px; margin-right:5px;"/></td>
        </tr>
    </table>
    </div>   
</div>
</body>
</html>