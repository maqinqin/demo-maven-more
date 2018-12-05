<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<html>
<head>
<title>组件管理</title>
<script type="text/javascript" src="${ctx}/scripts/My97DatePicker/WdatePicker.js"></script>
<script type='text/javascript' src='${ctx}/scripts/json.js?t=${sfx}'></script>
<script type="text/javascript" src="${ctx}/common/javascript/main.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/jquery/js/jquery-jiajian.js"></script>
<script type="text/javascript" src="${ctx}/pages/tankflow/component/js/componentList.js?t=${sfx}"></script>
<script type="text/javascript" src="${ctx}/pages/ui/componentList.js?t=${sfx}"></script>
<style type="text/css">
html,body {
	height: 100%;
}
</style>
<link rel="stylesheet"
	href="${ctx}/common/css/search_new.css?t=${sfx}" type="text/css"></link>

</head>
<body class="main1">
	<div class="content-main clear">
		<div class="mainSeach3_top">
		<div class="newsBt">组件管理</div>
			<div  class="mainSeach3_top_btnWrap" style="width:97%"><input type="button" id="add_btn" title="添加" class="btn_add" style="float:right;" onclick="showDialog('新建组件')" /></div>
		 </div>
			<form>
				<!--新添加的搜索标题-->
<!-- 				<div class="mainSearchBt" style="width:96%; margin:0 auto;"> -->
<!-- 					<div class="searchBtn_Wrap"> -->
<!-- 						<input type="button" id="add_btn" title="添加" class="btn_add" style="float:right;" onclick="showDialog('新建组件')" /> -->
<!-- 					</div> -->
				
<!-- 				</div> -->
		
		</form>
		<div class="panel clear" id="gridMain" style="margin-top:48px;">
			<div>
			<table id="componentTable" ></table>
			<table id="pageNav" ></table>
			</div>
		</div>	
   
</div>
	<div id="component_div" style="display: none">
			<form id="comInfoForm" action="" method="post">
			<div class="pageFormContent">
				<input type="hidden" id="componentId" name="componentId" />
				<input type="hidden" id="isActive" name="isActive" />				
					<p>
						<i><font color="red">*</font>组件名称:</i>
						<input type="text" id="componentName" class="textInput"
							name="componentName" />
					</p>
					<p>
						<i><font color="red">*</font>组件类型:</i>
						<icms-ui:dic id="componentType" name="componentType"
								kind="BPM_COMPONENT_TYPE" showType="select"
								attr="style='border: 1px solid #9a9a9a;height: 30px;line-height: 30px;
											 color:#858585;background-color: #fff;border: 1px solid #d5d5d5;
											 padding: 3px 4px 6px;font-size: 14px;'" />
					</p>
					<p style="width:100%">
						<i><font color="red">*</font>页面路径:</i>
						<input type="text" id="pagePathName" class="textInput"
							name="pagePathName" style="width: 75%" />
					</p>
					<p style="width:100%">
						<i><font color="red">*</font>接口路径:</i>
						<input type="text" id="infPathName" class="textInput"
							name="infPathName" style="width: 75%" />
					</p>
					<p style="width:100%">
						<i>备注说明:</i>
						<textarea id="remark" name="remark" rows="3"
								style="width: 76%;5px;color:#858585;background-color: #fff;border: 1px solid #d5d5d5;font-size: 14px;"></textarea>
					</p>
				<p class="btnbar" style="width:100%">
					<input type="button" id="post_button" class="btn_ok"
						onclick="saveOrUpdateBtn()" title="保存"
						style="margin-right: 5px; margin-left: 5px;" /> <input
						type="button" id="cencal_btn" class="btn_cancel" title="取消" onclick="closeViews('component_div')" 
						style="margin-right: 5px;" />
				</p>
			  </div>
			</form>
		</div>

</body>
</html>
