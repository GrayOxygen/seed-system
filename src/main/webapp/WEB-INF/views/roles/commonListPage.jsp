<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resources/common/taglibs.jsp"%>
<!DOCTYPE html  >
<html>
<head> 
	  <%@ include file="/resources/common/required_style.jsp" %>
      <title>角色管理</title>
      <!-- DataTables -->
	  <link rel="stylesheet" href="${static}/plugins/datatables/dataTables.bootstrap.css">
	  <style>
	  			td{
	  				text-align:left
	  			}
	  </style>
</head>

<body>
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
            <!-- 
                <div class="box-header">
                    <h3 class="box-title">角色列表</h3>
                </div>
             -->
                <!-- /.box-header -->
                <div class="box-body">
                    <form class="form-horizontal " action="javascript: $('#searchBtnID').click();"  method="post" id="pageForm">
                        <div class="box-body">
                            <div class="form-group  ">
                                <label for="name" class="col-sm-1 control-label    ">角色名</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="name" placeholder="角色名">
                                </div>
                                <div class="col-sm-2">
                                	<button type="button" id="searchBtnID" class="btn btn-primary">查询</button>
                                </div>
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
                                <th  >角色名</th>
                                <th>创建时间</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
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
	<!-- DataTables -->
	<script src="${static}/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${static}/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${static}/common/js/bootstrapDatatableUtil.js"></script>
    <script>
	var $table=$("#tableID");
	var _table ;
    $(function() {
 	 	_table = $("#tableID").DataTable($.extend(true,{},BD_CONSTANT.DATA_TABLES.DEFAULT_OPTION,{
            lengthChange: true,
            order: [
                [1, 'asc'],
                [2, 'desc']
            ],
            ajax : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
                //封装请求参数
                var param = userManage.pageSortFormData(data);
                //组装查询参数
   	         	param.append("filters[name]", $("#name").val());
   	         	param.append("filters[ctime]", $("#name").val());
                $.ajax({
                        type: "POST",
                        url: "${ctx}/roles/commonList" ,
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
            drawCallback: function( settings ) {//渲染完事件
              //设置选中项
                var idArray = new Array();
                <c:forEach items="${ids}" var="id">
                     idArray.push("${id}");
                </c:forEach>
                
               $("tbody :checkbox",$("#tableID")).each(function(index,ele){
	       			 if(-1!=$.inArray( $(ele).val(), idArray ) ){
	       				 $(ele).prop("checked",true);
	       			 }
       		 	});
               
            },
            columns: [ 
            	BD_CONSTANT.DATA_TABLES.COLUMN.CHECKBOX, 
	            {
	                data: "name",
	            },{
                    width: "50px",
                    data: "ctime",
                    render:BD_CONSTANT.DATA_TABLES.RENDER.YMDDate
                } 
	         ]
        }));
		
        //绑定到指定元素
        $("#searchBtnID").click(function() {
            _table.ajax.reload();
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
        });
        
        
    });
    
    
	
		// 当该页面为iframe，供iframe父元素获取当前选中的节点
		function getCheckedFromIFrame(){
			var  itemArray = [];
	        $("tbody :checkbox:checked",$("#tableID")).each(function(i) {
	                var item = _table.row($(this).closest('tr')).data();
	                itemArray.push(item.id);
	         });
			return itemArray;
		}
	
    </script>
</body>

</html>
