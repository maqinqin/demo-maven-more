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
<script type="text/javascript" src="${ctx}/pages/bill/js/bill.js"></script>
<style type="text/css">
</style>
</head>
<body class="main1">
<script  type="text/javascript">  
            
            var last_year_month = function() {  
                var d = new Date();  
                var result = [];  
                
                for(var i = 0; i <= 5 ; i++) {  
                    var m = d.getMonth()+1;  
                    m = m < 10 ? "0" + m : m;  
                    //在这里可以自定义输出的日期格式  
                    result.push(d.getFullYear() +"" + m);  
                    //result.push(d.getFullYear() + "年" + m + '月');  
                    d.setMonth(d.getMonth() - 1);  
                }  
                
                return result;  
            }  
            
            $(document).ready(function() {  
                //生成前6个月日期下拉框  
                for(var allinfo = last_year_month(), i = 0; i < allinfo.length; i++) {  
                    $("#billMonth").append("<option value='" + allinfo[i] + "'>" + allinfo[i] + "</option>");  
                }  
            });  
  
            
        </script> 
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg">账单查询</div>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent searchBg">
			<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="center">
				<tr style=" height:55px; ">
				    <td class="tabBt" style="width:80px;">租户名称：</td>
					<td class="tabCon">
						<select id="selTenant" class="selInput" name="tenant">
							<option value="0">{<icms-i18n:label name="com_l_choose"/>}</option>
						</select>
					</td>
				    <td class="tabBt">账单月：</td>
					<td class="tabCon">
						<select id="billMonth" name="appId_list" class="selInput">
						</select>
					</td>
				    <td class="tabBt"></td>
					<td class="tabCon">
					</td>
					
				</tr>
			</table>
		</div>
	</div>
		<div class="searchBtn_right" style="width:28%; float:left" >
			<table height="12%" width="100%" align="center">
				<tr style=" height:52px;">
					<td   width="10%" align="center" colspan="2">
			<shiro:hasPermission name="user:list">  
				<a href="javascript:void(0)" type="button" class="btn" onclick="searchBill();" title='<icms-i18n:label name="com_btn_query"></icms-i18n:label>' value='<icms-i18n:label name="com_btn_query"></icms-i18n:label>'>
					<span class="icon iconfont icon-icon-search"></span>
					<span>查询</span>
				</a>
			</shiro:hasPermission>
	       <button id="btn_reset"  type="reset" title='<icms-i18n:label name="common_btn_clear"/>' class="btnDel" onclick="clearAll();" value='<icms-i18n:label name="common_btn_clear"/>'>
				<span class="icon iconfont icon-icon-clear1"></span>
						<span>清空</span>
			</button>
			</td>
				</tr>
		   </table>
	   </div>
	</div>
</div>
		<div class="pageTableBg">
			<div class="panel clear" id="gridMain">
				<table id="gridTable" ></table>
				<div id="gridPager"></div>
			</div>
		</div>
	</div>
</body>

</html>
	