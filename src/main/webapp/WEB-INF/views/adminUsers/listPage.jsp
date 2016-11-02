<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resources/common/taglibs.jsp"%>
<!DOCTYPE html  >
<html>
<head>
      <title>用户管理</title>
      <!-- DataTables -->
	  <link rel="stylesheet" href="${static}/plugins/datatables/dataTables.bootstrap.css">
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
                    <form class="form-horizontal " action="javascript: $('#searchBtnID').click();"  method="post" id="pageForm">
                        <div class="box-body">
                            <div class="form-group  ">
                                <label for="userName" class="col-sm-1 control-label    ">用户名</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="userName" placeholder="用户名">
                                </div>
                                <button type="button" id="searchBtnID" class="btn btn-primary">查询</button>
                                <button type="button" id="addBtnID" class="btn btn-primary">添加</button>
                                <button type="button" id="batchDelBtnID" class="btn btn-primary">删除</button>
                            </div>
                        </div>
                        <!-- /.box-body -->
                    </form>
                    <table id="tableID" class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th width="10px">
                                     <input type="checkbox"    name="cb-check-all"  value="0" id="selAll"  >  
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
	<%@ include file="/resources/common/required_js.jsp"%>
	<%@ include file="/resources/common/bootstrap_alert.jsp"%>
	<%@ include file="/resources/common/bootstrap_confirm.jsp"%>
	<!-- DataTables -->
	<script src="${static}/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${static}/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${static}/common/js/bootstrapDatatableUtil.js"></script>
    <script>
    $(function() {
		var $table=$("#tableID");
        var _table = $("#tableID").DataTable($.extend(true,{},BD_CONSTANT.DATA_TABLES.DEFAULT_OPTION,{
            lengthChange: true,
            order: [
                [1, 'asc'],
                [2, 'desc']
            ],
            ajax : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                //封装请求参数
                var param = userManage.pageSortFormData(data);
                //组装查询参数
   	         	param.append("filters[userName]", $("#userName").val());
   	         	param.append("filters[ctime]", $("#userName").val());
                $.ajax({
                        type: "POST",
                        url: "${ctx}/adminUsers/list" ,
                        cache : false,  //禁用缓存
                        data: param,    //传入已封装的参数
						processData: false,  // 告诉jQuery不要去处理发送的数据
						contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
                        success: function(result) {
                                //异常判断与处理
                                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                                callback(result);
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            openAlert("查询失败");
                        }
                    });
            },
            "createdRow": function ( row, data, index ) {
	                //行渲染回调,这里可对该行dom元素进行任何操作
	                //不使用render，改用jquery文档操作呈现单元格
	                var $btnEdit = $("<button type='button'  id='editBtnID"+data.id+"' class='btn btn-primary  btn-edit'>编辑</button>");
	                var $btnDel = $("<button type='button'  id='delBtnID"+data.id+"' class='btn btn-primary  btn-del'>删除</button>");
	                var $tds=$('td', row);
	                $tds.eq($tds.length-1).append($btnEdit).append("  ").append($btnDel);
            },
            drawCallback: function( settings ) {
                //渲染完毕后的回调
                //清空全选状态
                $(":checkbox[name='cb-check-all']",$table).prop('checked', false);
            },
            columns: [ 
            	BD_CONSTANT.DATA_TABLES.COLUMN.CHECKBOX, 
	            {
	                data: "userName",
	            }, {
	                data: "ctime",
	                render:BD_CONSTANT.DATA_TABLES.RENDER.YMDDate
	            },{
	            	data:null,
	            	defaultContent:"",
	            	width: "120px"
	            } 
	         ]
        }));
		
        //绑定到指定元素
        $("#searchBtnID").click(function() {
            _table.ajax.reload();
        });
        $("#addBtnID").click(function() {
             window.location="${ctx}/adminUsers/addPage";
        });
        $("#batchDelBtnID").click(function() {
    			var  itemArray = [];
    	        $("tbody :checkbox:checked",$("#tableID")).each(function(i) {
    	                var item = _table.row($(this).closest('tr')).data();
    	                itemArray.push(item);
    	         });
    	        userManage.deleteItem("${ctx}/adminUsers/delete?ids=", itemArray , "${ctx}/adminUsers/listPage");
        });
        
        //绑定事件，on对动态新增元素也有效
        $table.on("change",":checkbox",function() {
            if ($(this).is("[name='cb-check-all']")) {
                $(":checkbox",$table).prop("checked",$(this).prop("checked"));
            }else{
                //一般复选
                var checkbox = $("tbody :checkbox",$table);
                $(":checkbox[name='cb-check-all']",$table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        }).on("click",".td-checkbox",function(event) {
            //点击单元格即点击复选框
            !$(event.target).is(":checkbox") && $(":checkbox",this).trigger("click");
        }).on("click",".btn-edit",function() {
            //点击编辑按钮
            var item = _table.row($(this).closest('tr')).data();
            location.href="${ctx}/adminUsers/editPage?id="+item.id
        }).on("click",".btn-del",function(e){
        	var item = _table.row($(this).closest('tr')).data();
            userManage.deleteItem("${ctx}/adminUsers/delete?ids=", [item] , "${ctx}/adminUsers/listPage");
        })
        
        
        
    });
    </script>
    
</body>

</html>
