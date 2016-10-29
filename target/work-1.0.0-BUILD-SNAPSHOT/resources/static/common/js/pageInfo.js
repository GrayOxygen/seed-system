// 上下页
function pageInfo(pageNo) {
  $("#pageNo").val(pageNo);
  $("#pageForm").submit();
}

//分页确定
function inputPageNo(pageNo) {
  var num = $.trim($("#numberSize").val());
  num = parseInt(num);
  if (isNaN(num)) {
    return;
  } else if (num == "") {
    return;
  } else if (num < 1) {
    return;
  } else if (num > pageNo) {
    return;
  } else {
    $("#pageNo").val(num);
    $("#pageForm").submit();
  }
}

//每页显示条数
function pageSize(size) {
  $("#pageNo").val(1);
  $("#pageSize").val(size);
  var key = "pageSize";
 // $.cookie(key, $("#pageSize").val());
  addCookie(key, $("#pageSize").val());
  $("#pageForm").submit();
}


//设置Cookie
function addCookie(name, value) {
    var exp = new Date();
    exp.setTime(exp.getTime() + 3 * 24 * 60 * 60 * 1000); //3天过期
    document.cookie = name + "=" + encodeURIComponent(value) + ";expires=" + exp.toGMTString();
    return true;
};