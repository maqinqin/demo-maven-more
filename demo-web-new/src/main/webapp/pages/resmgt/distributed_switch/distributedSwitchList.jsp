<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<script type="text/javascript" src="${ctx}/pages/resmgt/distributed_switch/js/distributeSwitchList.js"></script>
<script type="text/javascript">
	$(function() {
		initDistributeSwitchList();
	});
</script>
<style type="text/css">
#DistributeSwitchUpdateDiv p i{width:100px; text-align:left; margin-bottom:20px;}
#DistributeSwitchUpdateDiv p{width:280px; padding:0;}
#DistributeSwitchUpdateDiv p .updateDiv_list{overflow:hidden; display:inline-block; width:296px; float:left;}
#DistributeSwitchUpdateDiv label{display:block; padding-left:110px;}
#softWareDiv p{width:280px; padding:0;}
</style>
</head>
<body class="main1">
	<div class="content-main clear">
		<div class="page-header">
			<h1>
				基础信息维护/分布式交换机管理
					<small>
					分布式交换机可以添加多个ESXI。
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
					<td class="tabBt" style="width:75px;">分布式交换机名称：</td>
					<td class="tabCon"><input name="switchName22" id="switchName22" type="text" class="textInput"/> </td>
				<td class="tabBt">数据中心：</td>
				<td class="tabCon">
					<icms-ui:dic id="dataCenterId" name="dataCenterId" showType="select" attr="class='selInput'" sql="SELECT ID AS value, DATACENTER_CNAME AS name FROM RM_DATACENTER WHERE IS_ACTIVE = 'Y'"/>
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
								<shiro:hasPermission name="xjglfwq_sava"><input type="button" title="添加" onclick="createDistributeSwitchView();" class="Btn_Add"/></shiro:hasPermission>
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
	<!-- 端口组页面 -->
	
	<div id="softwareVersDiv"  style="display: none;" class="content-main clear" >
		<div class="pageFormContent">
		<table width="100%" >
			<tr>
				<td width="100%" align="right">
					<input type="button" title="添加" class="Btn_Add" onclick="createPortGroup()" style="margin-left: 505px;"/>
		            <input type="hidden" id="updateSoftWareFlag" name="updateSoftWareFlag" value="1" />
		            <input type="hidden" id="deleteSoftWareFlag" name="deleteSoftWareFlag" value="1" />
				&emsp;
				</td>
			</tr>
		</table>
		</div>
		<div  class="panel clear" id="softwareVerTableDiv">
			<table id="softwareVerTable"></table>
		</div>
	</div>
	
	<!-- 新增端口组 -->
	<div id="softWareDiv"   style="display: none; height: 100px ">
		<form action="" method="post" id="softWareForm">
		<div  id="softWareTab"  class="pageFormContent">
			
			<p>
				<i><font color="red">*</font>端口组名称:</i><input  class="textInput" style="width: 140px;" id="portGroupName" name="portGroupName" >	 
			</p>
			<p>
				<i><font color="red">*</font>VlanId:</i><input  class="textInput" style="width: 140px;" id="vlanId" name="vlanId">
			</p>
			<div class="nowrap right"></div>  
		            <input type="hidden" id="switchId" name="switchId" value="" />
		</div>
			<div class="winbtnbar" style="width:282px;">
				<label id="sp_error_tip"></label>
				<input type="button" id="btnAddSp" title="取消" value="取消" class="btn_gray" onclick="closePortGroupDiv()" style="margin-right: 5px; margin-left:0;" />
				<input type="button" id="btn_add_sp" class="btn_gray" onclick="addPortGroupBtn()" title="保存" value="保存" style="margin-right: 5px;margin-left: 0px;" />
			</div>
		</form>
	</div>
	
	<!-- 选择物理机  -->
	
	
	<div id="recycle_select_div" class="panel"
				style="width: 720px; display: none; padding-top:10px; padding-bottom:10px;">
				<div class="choosecontent">
					<h2>可选物理机:</h2>
					<input type="hidden" id="switchIdTree" value="">
					<h2 style="padding-left: 110px; width: 100px;">已选物理机:</h2>
					<div class="choose clear ztree" id="deviceTree1" style="width:260px;overflow-y:scroll;"></div>
					<div class="choosebtn">
						<input type="button" class="btn" value=">>"
							onclick="setSelectNode('deviceTree1', 'deviceTree2');" /> <input
							type="button" class="btn" value="&lt;&lt;"
							onclick="setSelectNode('deviceTree2', 'deviceTree1');" />
					</div>
					<div class="choose ztree" id="deviceTree2" style="width:260px;overflow-y:scroll;"></div>
					<div class="btnbar clear" style="margin-right:16px;">
						<input type="button" class="btn_gray" value="确 认"
							onclick="selectRecycleDevice();" />
							<input type="button" class="btn_gray" value="取消" onclick="closeView('recycle_select_div')" style="margin-left:0; margin-right:5px;"/>
					</div>
				</div>
			</div>	
	
	<!-- 分布式交换机添加或修改 -->
	<div id="DistributeSwitchUpdateDiv" style="display: none;">
		<form action="" method="post" id="updateForm">
		<div id="deviceVMServer"  class="pageFormContent">			
				<p>
					<i>交换机名称：</i>					
					<input type="text" id="switchName" name="switchName" class="textInput" />
				</p>
				<p>
					<i>所属数据中心：</i>
     				<icms-ui:dic id="dataCenterName" name="dataCenterName" showType="select" attr="class='selInput'" sql="SELECT ID AS value, DATACENTER_CNAME AS name FROM RM_DATACENTER WHERE IS_ACTIVE = 'Y'"/>
				</p>
				<p>
					<i><font color="red"></font>备注：</i>
					<textarea id="remark" name="remark" style="width:164px; height:40px; border: 1px solid #d5d5d5; background-color: #f5f5f5;"></textarea>
				</p>
			
			<input type="hidden" id="switch_id" value="" />
			  
        <p class="" style="text-align:right;">
				<input type="button"  class="btn_gray"  id="saveDistributeSwitch" name="saveDistributeSwitch" title="保存" value="保存" style="margin-right: 5px;margin-left: 0px;"  onclick="saveOrUpdateBtn()">
				<input type="button"  class="btn_gray"  id="cencelDic" name="cencelDic" title="取消" value="取消" style="margin-right: 5px; margin-left:0;"  onclick="closeView('DistributeSwitchUpdateDiv')">
	    </p>
		</div>
        </form>
	</div>
	
</body>
</html>
