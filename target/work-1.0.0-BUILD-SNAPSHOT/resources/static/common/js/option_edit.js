$(function() {

	$("#submitForm").validate({
		onkeyup : false,
		rules : {
			name: {
                required: true
            },
            type: {
            	required: true
            },
            val: {
            	required: true
            },
            showOrder: {
            	required: true,
            	number:true
            }
		}
	});
});

// 提交更新
function save() {
	$("#submitForm").submit();
}

// 取消更新
function cancel() {
	$("#submitForm").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	history.back();
}