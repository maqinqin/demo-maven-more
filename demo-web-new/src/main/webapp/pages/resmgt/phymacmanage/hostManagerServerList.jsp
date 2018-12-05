<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<script type="text/javascript" src="${ctx}/pages/resmgt/phymacmanage/js/hostManagerList.js"></script>
<script type="text/javascript">
	$(function() {
		initPhyHostList();
	});
</script>
<style type="text/css">
#UNamePasswdUpdateDiv i {text-align:left; padding-right:3px;}
#UNamePasswdUpdateDiv p{width:300px; margin-bottom:5px; margin-left:18px;  }
#UNamePasswdUpdateDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#UNamePasswdUpdateDiv label{display:block; padding-left:100px; width:190px;}
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				基础信息维护/物理机管理
					<small>
					给指定物理机修改用户名,密码
					</small>
			</h1>
		</div>
		<div id="topDiv" class="pageFormContent">
		<form>
		<div class="searchInWrap_top">
				<div class="searchIn_left" style="width:66%; float:left;">
					<div class="searchInOne" > 
						<table height="12%" width="100%" align="left">
				<tr style=" height:55px; ">
					<td class="tabBt" style="width:0px;">主机名称：</td>
					<td class="tabCon"><input name="hostName" id="hostName" type="text" class="textInput"/> </td>
				</td>
			</tr>
		 </table>
	 </div>
  </div>
  <div class="searchBtn_right" style="width:28%; " >
					<table height="12%" width="100%" >
						<tr style=" height:52px;">
							<td>
								<input type="button" title="查询" onclick="search();" class="Btn_Serch"/>
				      			<input type="reset" title="清空" class="Btn_Empty" style="text-indent:-999px;"/>
								<shiro:hasPermission name="xjglfwq_delete">
								<input type="hidden" id="deleteFlag" name="deleteFlag" value="1" />
								</shiro:hasPermission>
			    				<shiro:hasPermission name="xjglfwq_update">
								<input type="hidden" id="updateFlag" name="updateFlag" value="1" />
				</shiro:hasPermission> 
							</td>
						</tr>
					</table>
 </div>
</div>
		</form>
		</div>
		
		<!-- 显示虚机管理服务器的信息用列表 -->
		<div class="panel clear" id="gridTable_div">
			<table id="gridTable"></table>
			<table id="gridPager"></table>
		</div>
	</div>
	
	<div id="UNamePasswdUpdateDiv" style="display: none;">
		<form action="" method="post" id="updateForm">
		<div id="deviceVMServer"  class="pageFormContent">			
				<p>
				<span>
					<i><!-- <font color="red">*</font> -->用户名：</i>					
					<input type="text" id="userName" name="userName" class="textInput" />
				</span>
				
				</p>
				<p>
				<span>
					<i><font color="red"></font>新密码：</i>
					<input type="text" id="newPasswd" name="newPasswd" class="textInput" />
				</span>
				</p>
			
			<input type="hidden" id="hostId" value="" />
			  
	  	   <p style="text-align:right;">
		  	<input type="button" title="保存" value="保存" class="btn_gray" onclick="saveOrUpdateBtn()" style="margin-right: 5px; margin-left:0;" />
		  	<input type="button" class="btn_gray"  title="取消" value="取消" onclick="closeView('UNamePasswdUpdateDiv')" style="margin-right: 25px; margin-left:0;"  />
	  	   </p>
		</div>
        </form>
	</div>
	
	
</body>
</html>
