$(function () {
  $(".js-allCheckbox").click(function () {
    var $this = $(this);
    var listCheckBox = $(".js-listCheckbox");
    if ($this.prop("checked")) {
      listCheckBox.prop("checked", true);
    } else {
      listCheckBox.prop("checked", false);
    }
  });

  $(".js-listCheckbox").click(function () {
    var listCheckBox = $(".js-listCheckbox");
    var allCheckBox = $(".js-allCheckbox");
    var isAll = true;
    listCheckBox.each(function (i) {
      var $this = $(this);
      if (!$this.prop("checked")) {
        isAll = false;
        return false;
      }
    });
    if (isAll) {
      allCheckBox.prop("checked", true);
    } else {
      allCheckBox.prop("checked", false);
    }
  });
});

function getAllCheckValues() {
  var ids = [];
  $(".js-listCheckbox").each(function (i) {
    var $this = $(this);
    if ($this.prop("checked")) {
      ids.push($this.val());
    }
  });
  return ids.join(",");
}