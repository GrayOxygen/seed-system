$(function () {
  // 全选&全不选
  $("#selAll").click(function () {
    var cbox = document.getElementById("selAll");
    var boxes = document.getElementsByName("sel");
    for (var i = 0; i < boxes.length; i++) {
      boxes[i].checked = cbox.checked;
    }
  });
  $("#selAll1").click(function () {
    var cbox = document.getElementById("selAll1");
    var boxes = document.getElementsByName("sel1");
    for (var i = 0; i < boxes.length; i++) {
      boxes[i].checked = cbox.checked;
    }
  });
  $("#selAll2").click(function () {
    var cbox = document.getElementById("selAll2");
    var boxes = document.getElementsByName("sel2");
    for (var i = 0; i < boxes.length; i++) {
      boxes[i].checked = cbox.checked;
    }
  });
  $("#selAll3").click(function () {
    var cbox = document.getElementById("selAll3");
    var boxes = document.getElementsByName("sel3");
    for (var i = 0; i < boxes.length; i++) {
      boxes[i].checked = cbox.checked;
    }
  });
});
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

function reqByIds(url, obj) {
  var ids = "";
  var boxes = document.getElementsByName(obj);
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