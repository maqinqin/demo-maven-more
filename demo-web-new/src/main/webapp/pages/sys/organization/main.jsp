<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="icms-i18n" uri="/WEB-INF/tld/icms-i18n.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jquery_common.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/zTree_load.jsp"%>
<title><icms-i18n:label name="derta1"/></title>
<script type="text/javascript">
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPId : ""
			}
		},
		callback : {
			onMouseDown : onMouseDown
		},
		view:{
			showLine:false
		}
	};

	$(document).ready(function() {

		jQuery.validator.addMethod("groupNameCheck", function(value, element) {
			var validateValue = true;
			$.ajax({
				type : 'post',
				datatype : "json",
				data : {
					"sysOrganizationPo.orgName" : value,
					"sysOrganizationPo.orgId" : $("#sysOrganizationPo\\.orgId").val(),
					'sysOrganizationPo.parent.orgId' : $("#sysOrganizationPo\\.parent\\.orgId").val()
				},
				url : ctx + "/sys/organization/isrepeat.action",
				async : false,
				success : (function(data) {
					validateValue = data.result.data;
				}),
			});
			return validateValue;
		}, i18nShow('validate_data_remote'));
		$("#forms1").validate({
			rules : {
				"sysOrganizationPo.orgName" : {
					required : true,
					groupNameCheck : true
				}
			},
			messages : {
				"sysOrganizationPo.orgName" : {
					required : i18nShow('validate_data_required')
				}
			}
		});
		var t = $("#tree");
		// 加载左侧树
		$.ajax({
			type : "post",
			url : ctx + "/sys/organization/loadtree.do",
			data : {},
			beforeSend : function(XMLHttpRequest) {
			},
			success : function(data, textStatus) {
				var zNodes = data.list;
				

				var root = {
					'id' : '0',
					'name' : i18nShow('sys_org'),
					'pid' : '',
					'open' : true
				};
				//zNodes[zNodes.length] = root;
				zNodes.unshift(root);
				t = $.fn.zTree.init(t, setting, zNodes);
				return true;
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				showTip(i18nShow('tip_save_fail'));
				flage = false;
			}
		});
	});
	function onMouseDown(event, treeId, treeNode) {
		if (treeNode.id != '0') {
			load(treeNode.id);
		} else {
			$("#showRoot").show();
			$("#show").hide();
			$("#edit").hide();
		}
	}
	function load(nodeId) {
		$("#sysOrganizationPo_orgName").text("");
		$("#sysOrganizationPo_remark").text("");
		$("#sysOrganizationPo_orgId").text("");
		$("#sysOrganizationPo_parent_orgName").text("");
		$("#sysOrganizationPo_parent_orgName2").text("");
		$("#sysOrganizationPo\\.orgId").val("");
		$("#sysOrganizationPo\\.orgName").val("");
		$("#sysOrganizationPo\\.remark").val("");
		$("#sysOrganizationPo\\.orgId").val("");
		$("#sysOrganizationPo\\.parent\\.orgId").val("");
		$.ajax({
			type : "post",
			url : ctx + "/sys/organization/load.do",
			data : {
				"sysOrganizationPo.orgId" : nodeId
			},
			beforeSend : function(XMLHttpRequest) {
			},
			success : function(data, textStatus) {
				$("#showRoot").hide();
				$("#show").show();
				var zTree = $.fn.zTree.getZTreeObj("tree");
				$("#sysOrganizationPo_orgName").text(data.sysOrganizationPo.orgName);
				$("#sysOrganizationPo_remark").text(data.sysOrganizationPo.remark);
				$("#sysOrganizationPo_orgId").text(data.sysOrganizationPo.orgId);

				var pnode = null;
				if (data.sysOrganizationPo.parent != null) {
					pnode = zTree.getNodeByParam("id", data.sysOrganizationPo.parent.orgId, null);
					$("#sysOrganizationPo\\.parent\\.orgId").val(data.sysOrganizationPo.parent.orgId);
				}
				if (pnode != null) {
					$("#sysOrganizationPo_parent_orgName").text(pnode.name);
					$("#sysOrganizationPo_parent_orgName2").text(pnode.name);
				} else {
					$("#sysOrganizationPo_parent_orgName").text("");
					$("#sysOrganizationPo_parent_orgName2").text("");
				}
				$("#sysOrganizationPo\\.orgId").val(data.sysOrganizationPo.orgId);

				$("#sysOrganizationPo\\.orgName").val(data.sysOrganizationPo.orgName);
				$("#sysOrganizationPo\\.remark").val(data.sysOrganizationPo.remark);
				$("#sysOrganizationPo\\.orgId").val(data.sysOrganizationPo.orgId);
				$('#forms1').valid();
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function() {
				flage = false;
			}
		});
	}
	function save() {
		if (!$('#forms1').valid()) {
			return false;
		} else {
			var params = {
				'sysOrganizationPo.orgName' : $("#sysOrganizationPo\\.orgName").val(),
				'sysOrganizationPo.remark' : $("#sysOrganizationPo\\.remark").val(),
				'sysOrganizationPo.orgId' : $("#sysOrganizationPo\\.orgId").val(),
				'sysOrganizationPo.parent.orgId' : $("#sysOrganizationPo\\.parent\\.orgId").val()
			};
			$.ajax({
				type : "post",
				url : ctx + "/sys/organization/save.do",
				data : params,
				beforeSend : function(XMLHttpRequest) {
				},
				success : function(data, textStatus) {
					closeView("edit");
					showTip(i18nShow('tip_save_success'));
					var zTree = $.fn.zTree.getZTreeObj("tree");
					var node = zTree.getNodeByParam("id", data.sysOrganizationPo.orgId, null);
					if (!node) {
						var pnode = null;
						node = {};
						node.name = data.sysOrganizationPo.orgName;
						node.id = data.sysOrganizationPo.orgId;
						node.pid = data.sysOrganizationPo.parent.orgId;
						node.icon = ctx + "/css/zTreeStyle/img/icons/organ.png";
						pnode = zTree.getNodeByParam("id", data.sysOrganizationPo.parent.orgId, null);
						pnode.isParent = true;
						zTree.addNodes(pnode, node, true);
					} else {
						node.name = data.sysOrganizationPo.orgName;
					}
					zTree.updateNode(node);
					$("#show").hide();
					closeView("edit");
					$("#showRoot").show();
					$.ajax({
						type : "post",
						url : ctx + "/sys/organization/loadtree.do",
						data : {},
						beforeSend : function(XMLHttpRequest) {
						},
						success : function(data, textStatus) {
							var zNodes = data.list;

							var root = {
								'id' : '0',
								'name' : i18nShow('sys_org'),
								'pid' : '-1',
								'open' : true
							};
							//zNodes[zNodes.length] = root;
							t = $.fn.zTree.init(t, setting, zNodes);
							return true;
						},
						complete : function(XMLHttpRequest, textStatus) {
						},
						error : function() {
							showTip(i18nShow('tip_save_fail'));
							flage = false;
						}
					});
					//load(node.id);
				},
				complete : function(XMLHttpRequest, textStatus) {
				},
				error : function() {
					showTip("提交失败！");
					flage = false;
				}
			});
		}
	}
	function deletes() {
		var params = {
			'sysOrganizationPo.orgId' : $("#sysOrganizationPo\\.orgId").val()
		};
		showTip(i18nShow('tip_delete_confirm'), function() {
			$.ajax({
				type : "post",
				url : ctx + "/sys/organization/delete.do",
				data : params,
				beforeSend : function(XMLHttpRequest) {
				},
				success : function(data, textStatus) {
					var zTree = $.fn.zTree.getZTreeObj("tree");
					var node = zTree.getNodeByParam("id", $("#sysOrganizationPo\\.orgId").val(), null);
					zTree.removeNode(node, false);
				},
				complete : function(XMLHttpRequest, textStatus) {
					showTip(i18nShow('tip_delete_success'));
					setTimeout("location.reload()",800);		
				},
				error : function() {
					showTip(i18nShow('tip_delete_fail'));
					flage = false;
				}
			});
		});
	}
	function show(type) {
		$('#forms1').valid();
		var zTree = $.fn.zTree.getZTreeObj("tree");
		var nodes = zTree.getSelectedNodes();
		if (type == "1") {
			for (var i = 0; i < nodes.length; i++) {
				load(nodes[i].id);
			}
			openDialog("edit", i18nShow('sys_org_update'), 500, 300);
		} else if (type == "0") {
			$("#sysOrganizationPo\\.orgId").val("");
			$("#sysOrganizationPo\\.orgName").val("");
			$("#sysOrganizationPo\\.remark").val("");
			$("#sysOrganizationPo\\.parent\\.orgId").val("");
			$("#sysOrganizationPo_parent_name").text("");
			$("#sysOrganizationPo_parent_orgName2").text(i18nShow('sys_org'));	
			$("#sysOrganizationPo_parent_name2").text("");
			openDialog("edit", i18nShow('sys_org_save'), 350, 250);
		} else if (type == "2") {
			for (var i = 0; i < nodes.length; i++) {
				$("#sysOrganizationPo_parent_orgName2").text(nodes[i].name);
				$("#sysOrganizationPo\\.parent\\.orgId").val(nodes[i].id);
			}
			$("#sysOrganizationPo\\.orgId").val("");
			$("#sysOrganizationPo\\.orgName").val("");
			$("#sysOrganizationPo\\.remark").val("");
			openDialog("edit", i18nShow('sys_org_save_son'), 350, 250);
		}
	}
	function closeView(divId) {
		$("#" + divId).dialog("close");
	}
	function openDialog(divId, title, width, height) {
		$("#" + divId).dialog({
			autoOpen : true,
			modal : true,
			height : height,
			width : width,
			title : title,
			//resizable : false
		});
	}
</script>
</head>
<body class="main1">
		<div class="page-header">
			<h1>
				<div class="WorkBtBg"><icms-i18n:label name="sys_title_org"></icms-i18n:label></div>
			</h1>
		</div>

	<div id="content" class="main">
		<div id="treeDiv"
			class="tree-div tree-div1" style="height:499px;overflow-x:auto;">
			<ul id="tree" class="ztree" style="width: 260px; overflow: auto;"></ul>
		</div>
		<div id="rightContentDiv" class="right-div right-div1" style="margin-top:10px;">
			<div id="showRoot" style="display: block">
			<div style="width:100%">
					<table border="0" cellpadding="0" cellspacing="0" class="pagelist" style="float:left;width:40%" >
						<tr>
						<th><img src="../../css/zTreeStyle/img/icons/organ.png" /></th><td><label style="font-size: 14px;"><icms-i18n:label name="sys_l_orgIcon"></icms-i18n:label></label></td>
						</tr>
					</table>
				</div>
				<div style="width:40%">
					<p class="btnbar1" style="margin-left:8px;">
						 <a a href="javascript:void(0)" type="button" class="btn" id="btn_add_scp" onclick="show('0')"
					value=<icms-i18n:label name="derta2"/> style="margin-right:5px; margin-left: 0px;" >
						<span>组织机构管理</span>
					</a>
					</p>
				</div>
			</div>
			<jsp:include page="update.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>