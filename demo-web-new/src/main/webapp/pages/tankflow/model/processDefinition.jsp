<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>

<html>
<head>
<title>流程模板</title>
<script type="text/javascript" src="${ctx}/pages/tankflow/model/js/swfobject.js"></script>

<script type="text/javascript" charset="UTF-8">
	$(document).ready(function() {
		/** 初始化流程模板信息 */
		getModelContent();
	});

	/** 初始化流程模板信息 */
	function getModelContent() {
		$.ajax({
			url : ctx
					+ "/workflow/model/model_detail.action?processDefinitionId="
					+ $('#processDefinitionID').val(),
			async : false,
			dataType : "json",
			success : function(data) {
				$("#view_modelName").val(data.modelName);
				$("#tm_instanceType").val(data.typeId);
				$("#typeCode").val(data.typeId);
				$("#view_modelId").val(data.modelId);
				if (data.isActive == 'Y') {
					$("#view_isActive").prop("checked", true);
				} else {
					$("#view_isActive").prop("checked", false);
				}
				$("#view_remark").val(data.remark);
			}
		});
	}

	/**
	 * 初始化流程图信息
	 */
	function getModelDiagram() {
		$.ajax({
			url : ctx + "/workflow/model/model_myh.action?processDefinitionId="
					+ $('#processDefinitionID').val(),
			async : false,
			dataType : "json",
			success : function(data) {
				/** 初始化流程图 */
				initFormXML(data.diagramXml);
			}
		});
	}

	/** 通过XML数据初始化graph */
	function initFormXML(xmlData) {
		var graph = getGraph();

		if(graph != null){
			graph.importTemplate(xmlData,"model");
		}
	}

	/**
	 * 创建流程实例
	 */
	function newInstance() {
		$.ajax({
			url : ctx
					+ "/workflow/model/model_newProcessInstanceAct.action?processDefinitionId="
					+ $('#processDefinitionID').val(),
			async : false,
			dataType : "json",
			beforeSend : function() {
				showTip("load");
			},
			success : function(data) {
				closeTip();
				showTip(data.msg);
			},
			error : function(e) {
			    showError("error");
			}
		});
	}
	
    // 最低版本检测
    var swfVersionStr = "11.1.0";
    // flashplayer安装引导对象 
    var xiSwfUrlStr = ctx+"/pages/tankflow/model/playerProductInstall.swf";
    var flashvars = {};
    var params = {};
    params.quality = "high";
    params.bgcolor = "#ffffff";
    params.allowscriptaccess = "sameDomain";
    params.allowfullscreen = "true";
    params.wmode = "opaque";
    var attributes = {};
    attributes.id = "model";
    attributes.name = "model";
    attributes.align = "middle";
    swfobject.embedSWF(
    	ctx+"/pages/tankflow/model/model.swf", "flashContent", 
        "100%", "100%", 
        swfVersionStr, xiSwfUrlStr, 
        flashvars, params, attributes);
    // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
   	swfobject.createCSS("#flashContent", "display:block;text-align:left;");
	
    //获取画布对象
    function getGraph(){
    	//获取画布对象
    	graph = (navigator.appName.indexOf ("Microsoft") !=-1) ? window["model"]:document["model"];
    	if(graph == null){
    		showTip("获取画布对象失败!");
    	}
    	return graph;
    }
</script>
<style type="text/css">
.instanceTable tr td { 
}
.instanceTable tr td input{font-size:12px; padding:6px 4px;}
.descript {
	padding-left: 10px;
}
</style>
</head>
<body style="overflow: auto" class="main1">
	<input type="hidden" value="<%=basePath%>" id="path" />
	<input type="hidden" value="${ctx}" id="url" />
	<input id="processDefinitionID" type="hidden"
		value='<c:out value="${param.processDefinitionID}"></c:out>' />
	<div class="mainSeach3_top">
		<div class="newsBt">模板详情</div>
	</div>
	<div id="topDiv" style="height: 7%;padding-top:8px;background:none;"class="pageFormContent">
		<form id="modelForm">
			<table cellpadding="0" cellspacing="0" class="instanceTable"
			align="left" style="margin-left: 25px; line-height:43px;width: 90%;">
				<tr style="padding-top:30px; overflow:hidden;">
					<td style="width:70px;padding-top: 3px;">模板名称：</td>
					<td><input type="hidden" id="view_modelId" /> <input
						type="text" id="view_modelName" name="view_modelName" style="margin-right: 5px;"
						class="textInput1" readonly="readonly"/></td>
					<td>模板类型：</td>
					<td>
						<div class="select_border">
							<div class="select_container" disabled>
								<icms-ui:dic id="tm_instanceType" name="tm_instanceType"
									value=""
									sql="select TYPE_NAME name,TYPE_ID value from BPM_TEMPLATE_TYPE WHERE PARENT_ID != '0'"
									showType="select" attr="class='selInput'" />
							</div>
						</div>
					</td>
					<td class="descript" colspan="4" style="text-align:right;"><input type="button" id="new_instance" onclick="newInstance()"  class="btn_Generate" style="vertical-align: middle;"/>
				<input type="button" class="btn_return" id="submit_btn" onclick="javascript:history.go(-1);" style="vertical-align: middle;" /></td>
				</tr>
			</table>
		</form>
	</div>

	<!--画布容器-->
	<div id="container" style="position: absolute; width: 66%;top:175px; bottom: 0px; left: 0px; right: 0px; overflow: auto; left:20px;border-radius:4px">
		<div id="flashContent">
            <p>
               	 请确保FlashPlayer版本高于11.1.0
            </p>
            <script type="text/javascript"> 
                var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://"); 
                document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
                                + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
            </script> 
        </div>
	</div>
</body>
</html>