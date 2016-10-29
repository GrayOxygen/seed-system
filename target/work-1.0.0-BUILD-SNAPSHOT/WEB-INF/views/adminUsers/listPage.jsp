<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resources/common/taglibs.jsp"%>
<!DOCTYPE html  >
<html>
<head>
	  <meta charset="utf-8">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title>用户管理</title>
</head>

<body>
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">用户列表</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <form class="form-horizontal " action="" method="post" id="pageForm">
                        <div class="box-body">
                            <div class="form-group  ">
                                <label for="userName" class="col-sm-1 control-label    ">用户名</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="userName" placeholder="用户名">
                                </div>
                                <button type="button" id="searchBtnID" class="btn btn-primary">查询</button>
                                <button type="button" class="btn btn-primary">删除</button>
                            </div>
                        </div>
                        <!-- /.box-body -->
                    </form>
                    <table id="tableID" class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" name="selectAll" value="0" id="selectAllID" width="3%">
                                </th>
                                <th>用户名</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->
    <script>
    $(function() {

        var table = $("#tableID").DataTable({
            "lengthChange": true,
            "searching": false, //禁用全局搜索,mongodb不支持中文全文搜索就暂不用此功能
            "paging": true,
            "order": [
                [1, 'asc'],
                [2, 'desc']
            ],
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "${ctx}/adminUsers/list",
                "type": "POST",
                "data": function(data) {
                    //添加额外的查询参数传给服务器
                    return $.extend({}, data, {
                        "1": "userName",
                        "2": "ctime",
                        "filters": {
                            "userName": $("#userName").val(),
                            "id": $("#id").val()
                        }
                    });
                }
            },
            "columns": [{
                "data": null
            }, {
                "data": "userName"
            }, {
                "data": "ctime"
            }, {
                "data": null
            }],
            "columnDefs": [{
                "targets": -1,
                "orderable": false,
                "defaultContent": "<a>编辑</a> | <a>删除</a>"
            }, {
                "targets": 0,
                "orderable": false,
                "render": function(data, type, full, meta) {
                    return "<td><input type='checkbox' name='id' value='" + data.id + "'></td>";
                }
            }]
        });

        //提交方法
        $("#searchBtnID").click(function() {
            table.ajax.reload();
        });
        
		//全选事件
        $('#selectAllID').on('click', function() {
            // Get all rows with search applied
            var rows = table.rows({
                'search': 'applied'
            }).nodes();
            // Check/uncheck checkboxes for all rows in the table
            $('input[type="checkbox"]', rows).prop('checked', this.checked);
        });

        // Handle click on checkbox to set state of "Select all" control
        $("#tableID tbody").on('change', 'input[type="checkbox"]', function() {
            // If checkbox is not checked
            if (!this.checked) {
                var el = $('#selectAll').get(0);
                // If "Select all" control is checked and has 'indeterminate' property
                if (el && el.checked && ('indeterminate' in el)) {
                    // Set visual state of "Select all" control 
                    // as 'indeterminate'
                    el.indeterminate = true;
                }
            }
        });

    });
    </script>
</body>

</html>
