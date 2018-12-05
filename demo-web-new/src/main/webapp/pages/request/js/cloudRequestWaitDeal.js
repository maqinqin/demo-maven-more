function formatTime(ns){
	if(ns){
		var date = new Date(ns);
		
		Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		
       return Y+M+D+h+m+s;
	}
}
function initCloudRequestList() {
	$("#gridTable").jqGrid({
		url : ctx + "/request/base/getCloudRequestWaitDeal.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		height : heightTotal() + 50,
		//postData:{"srCode":"SRVS201409110001"},
		autowidth : true, // 是否自动调整宽度
		colModel : [ {
			name : "srCode",
			index : "s.sr_code",
			label : i18nShow('bm_sr_code'), // 申请单号
			width : 120,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rewObject){
				return '<a href="javascript:;" style=" text-decoration:none" onclick="view(\''+rewObject.srId+'\',\''+rewObject.srCode+'\')">'+rewObject.srCode+'</a>';
            }
		}, {
			name : "currentStep",
			index : "t.current_step",
			label : i18nShow('bm_sr_status'), // "申请状态"
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "appName",
			index : "a.cname",
			label : i18nShow('app_info'), // "应用系统"
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : "datacenterName",
			index : "d.datacenter_cname",
			label : i18nShow('rm_datacenter'), //"数据中心"
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "srTypeName",
			index : "st.sr_type_name",
			label : i18nShow('bm_sr_type'), // "申请类型"
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "creator",
			index : "creator",
			label : i18nShow('request_user'), // "申请人"
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "requestTime",
			index : "t.create_time",
			label : i18nShow('request_time'), // "申请时间"
			width : 120,
			sortable : true,
			align : 'left',
              formatter: function(cellValue,options,rewObject){           	  
					return formatTime(cellValue);
              }
		},{
			name:i18nShow('com_operate'), // "操作"
			index:"option",
			sortable:false,
			align:"left",
			width : 60,
			formatter:function(cellVall,options,rowObject){
				//var deal = "<input type='button' class='btn_config_s' title='处理' onclick=dealRequest('"+rowObject.pageUrl+"','"+rowObject.todoId+"') />";
				//wmy,修改操作的样式
				var deal = "<a  style=' margin-right: 10px;text-decoration:none;' href='javascript:#'  title='' onclick=dealRequest('"+rowObject.pageUrl+"','"+rowObject.todoId+"') >"+i18nShow('my_req_deal')+"</a>" ; 
				return deal;
			}
        }],
		viewrecords : true,
		sortname : "t.create_time",
		sortorder : "desc",
		rowNum : 10,
		rowList : [ 5, 10, 15, 20, 30 ],
		prmNames : {
			search : "search"
		},
		jsonReader : {
			root : "dataList",
			records : "record",
			repeatitems : false
		},
		loadComplete : function(data) {
			pushPaginationParamToHistory(data,"page","rows")
		},
		page: pagin == null || pagin == undefined ? 1 : pagin.page,
		rowNum : pagin == null || pagin == undefined ? 10 : pagin.rows,
		pager : "#gridPager",
		hidegrid : false
	});
}
function dealRequest(url, todoId) {
	$.post(ctx+"/request/base/todoStartDeal.action", {todoId:todoId}, function (data) {
		if(data.result == "success") {
			var targetUrl = "";
			if(url.indexOf("?") > 0) {
				targetUrl = ctx + "/" + url + "&todoId=" + todoId;
			} else {
				targetUrl = ctx + "/" + url + "?todoId=" + todoId;
			}
			window.location.href = targetUrl;
//			window.open(targetUrl);
		}
	});
}
function search() {
	var queryData = {
		"srCode" : $("#srCode").val().replace(/(^\s*)|(\s*$)/g, ""),
		"appId" : $("#appId").val(),
		"datacenterId" : $("#datacenterId").val(),
		"srStatusCode" : $("#srStatusCode").val(),
		"srTypeMark" : $("#srTypeMark").val()
	};
	jqGridReload("gridTable", queryData);
//	$("#gridTable").jqGrid("setGridParam", {postData : queryData}).trigger("reloadGrid");
}
function add() {
	window.open("cloudRequestInfo.jsp");
}
function view(srId,srCode) {
	window.location.href = ctx+"/request/base/getCloudReqeust.action?pageType=detail&srId="+srId;
}