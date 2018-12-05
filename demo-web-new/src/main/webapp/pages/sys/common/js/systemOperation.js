function cancelServiceRequest() {
	var srCode = $("#csrTable_srCode").val();
	if (srCode) {
		showTip('是否确认作废工单号[' + srCode + ']吗？', function() {
			$.post(ctx + "/request/base/cancelServiceRequest.action", {"srCode" : srCode}, function(data) {
				if(data && data.result) {
					showTip(data.result);
				} else {
					showTip('作废成功！');
				}
			});
		});
	} else {
		showTip('请输入工单编号！');
	}
}
function backServiceRequest() {
	var srCode = $("#back_srCode").val();
	if (srCode) {
		showTip('是否确认回退并作废工单号[' + srCode + ']吗？', function() {
			$.post(ctx + "/request/base/backServiceRequest.action", {"srCode" : srCode}, function(data) {
				if(data && data.result) {
					showTip(data.result);
				} else {
					showTip('回退资源并作废成功！');
				}
			});
		});
	} else {
		showTip('请输入工单编号！');
	}
}