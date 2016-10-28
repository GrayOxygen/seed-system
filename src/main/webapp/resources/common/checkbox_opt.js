$(function(){
	 // 选中或取消全部checkbox
	 $("#selAll").click(function () {
	    var cbox = $("#selAll");
	    var boxes = $("input[name='sel']");
	    for (var i = 0; i < boxes.length; i++) {
	        // 全选按钮之前选中，则取消所有，之前是没选中现在选中了，就选中所有
	    	if($("#selAll").closest("span").attr("class")=="checked"){
		    	$(boxes[i]).closest("span").addClass("checked");
	    	}else{
		    	$(boxes[i]).closest("span").removeClass("checked");
	    	}
	    	$(boxes[i]).attr("checked",cbox.checked);
	    }
	  });
})
 //获取所有选中项的value数组
function getCheckedValAarray(name){
	return $.makeArray($("input[name='"+name+"']:checked")).map(function(x){
			return x.value;
	});
}
// 获取当前选中的checkbox，将value组成逗号隔开的字符串，如"xxx,123,idxxx"
function getAllSels() {
	var values = "";
	$("input[name='sel']").each(function(index,ele){
		if($(ele).closest("span").attr("class")=="checked"){
			values += $(ele).val()+",";
		}
	})
	if(values.length>0){
		values = values.substring(0,values.length-1);
	}
	return values;
}

//获取当前选中的checkbox，将value组成的数组
function getAllSelsArray() {
	var values =  new Array();
	$("input[name='sel']").each(function(index,ele){
		if($(ele).closest("span").attr("class")=="checked"){
			values.push($(ele).val());
		}
	})
	return values;
}


// ID多选处理请求提交
function reqByIds(url) {
  var ids = "";
  var boxes = document.getElementsByName("sel");
  for (var i = 0; i < boxes.length; i++) {
    if (boxes[i].checked)
      ids += boxes[i].value + ",";
  }
  if (ids != "") {
    if (confirm("确定要执行该操作吗?"))
      if (url.indexOf("?") > 0)
        window.location.href = url + "&ids=" + ids;
      else
        window.location.href = url + "?ids=" + ids;
  } else
    alert("请勾选要操作的记录!");
}
// ID单选处理请求提交
function confirmOperation(url) {
  if (url != "" && confirm("确定要执行该操作吗?"))
    window.location.href = url;
}