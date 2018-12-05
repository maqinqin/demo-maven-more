
//创建时间的方法
function formatTime(ns){
	if(ns){
		var date = new Date(parseInt(ns));
		
		Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		
       return Y+M+D+h+m+s;
	}
}
var _width = 0;
function initCloudRequestList() {
	$("#gridTable").jqGrid({
		url : ctx + "/request/base/getCloudReqeustList.action", // 提交的action地址
		rownumbers : true, // 是否显示前面的行号
		datatype : "json", // 返回的数据类型
		mtype : "post", // 提交方式
		postData:{"superAdmin":"true"},
		height : heightTotal() + 62,
		autowidth : true,
		colModel : [ {
			name : "srCode",
			index : "s.sr_code",
			label : i18nShow('my_req_sr_code'),
			width : 120,
			sortable : true,
			align : 'left',
            formatter: function(cellValue,options,rewObject){
				return '<a href="javascript:;" style=" text-decoration:none" onclick="view(\''+rewObject.srId+'\',\''+rewObject.srCode+'\')">'+rewObject.srCode+'</a>';
            }
		}, {
			name : "appName",
			index : "a.cname",
			label : i18nShow('my_req_appName'),
			width : 120,
			sortable : true,
			align : 'left'
		}, {
			name : "datacenterName",
			index : "d.datacenter_cname",
			label : i18nShow('my_req_rm_datacenter'),
			width : 100,
			sortable : true,
			align : 'left'
		}, {
			name : "srTypeName",
			index : "st.sr_type_name",
			label : i18nShow('my_req_sr_type'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "srStatus",
			index : "dic.dic_name",
			label : i18nShow('my_req_sr_status'),
			width : 80,
			sortable : true,
			align : 'left'
		}, {
			name : "creator",
			label : i18nShow('my_req_creator'),
			width : 80,
			sortable : false,
			align : 'left'
		}, {
			name : "createTime",
			label : i18nShow('my_req_create_time'),
			width : 120,
			sortable : false,
			align : 'left',
              formatter: function(cellValue,options,rewObject){
					return formatTime(cellValue);
              }
		}, {
			name : "summary",
			label : i18nShow('my_req_summary'),
			width : 160,
			sortable : false,
			align : 'left'
		},{
			name:i18nShow('my_req_operate'),index:"option",sortable:false,align:"left",width : 60,
			formatter:function(cellVall,options,rowObject){
				//return "<input type='button' class='btn_apply_s' title='申请进度' onclick=lookupWorkflow('"+rowObject.srId+"') />";
				return "<a  style='margin-right: 10px;text-decoration:none;' href='javascript:#' title=''  onclick=lookupWorkflow('"+rowObject.srId+"')>"+i18nShow('my_req_sch')+"</a>";
			}
        }],
		viewrecords : true,
		sortname : "s.create_time",
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
	_width = $("#div_list1").width()+16;
	
	
}
//自适应宽度
$(window).resize(function() {
	$("#gridTable").setGridHeight(heightTotal() + 62);
	
})

function search() {
	jqGridReload("gridTable", {
		"srCode" : $("#srCode").val().replace(/(^\s*)|(\s*$)/g, ""),
		"appId" : $("#appId").val(),
		"datacenterId" : $("#datacenterId").val(),
		"srStatusCode" : $("#srStatusCode").val(),
		"srTypeMark" : $("#srTypeMark").val()
	});
}
function clearAll(){
	$("#srCode").val("");
	$("#appId").get(0).selectedIndex=0; 
	$("#datacenterId").get(0).selectedIndex=0; 
	$("#srStatusCode").get(0).selectedIndex=0; 
	$("#srTypeMark").get(0).selectedIndex=0; 

}
function view(srId,srCode) {
	window.location.href = ctx+"/request/base/getCloudReqeust.action?pageType=detail&srId="+srId;
}

function lookupWorkflow(srId) {
	$.post(ctx+"/request/base/findInstanceIdBySrId.action", {srId:srId}, function(data) {
		window.location.href = ctx+"/pages/tankflowNew/processInstance.jsp?state=view&instanceId="+data.result;
	});
}