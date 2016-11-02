<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>
<%@ include file="/resources/common/taglibs.jsp"%>
<!DOCTYPE html  >
<html >
<head>
<title>修改权限菜单</title>
    <!-- bootstrapValidator -->
	  <link rel="stylesheet" href="${static}/plugins/projectSpecific/bootstrapValidator/css/bootstrapValidator.min.css">
<style>
#currentBodyContent {
	width: 60%
}
</style>
</head>

<body>
           <div  id="currentBodyContent" class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title"></h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form class="form-horizontal"  action="javascript:; "  id="submitForm"  method="POST">
            <input type="hidden" value="${model.id }"  name="id"  />
               <div class="box-body">
              	 <div class="form-group">
                  <label for="name" class="col-sm-2 control-label">权限菜单名</label>

                  <div class="col-sm-5">
                    <input type="text" class="form-control" id="name"  name="name" placeholder="权限菜单名"  value="${model.name }">
                  </div>
                </div>
                
                	 <div class="form-group">
                  <label for="name" class="col-sm-2 control-label">权限菜单url</label>

                  <div class="col-sm-5">
                    <input type="text" class="form-control" id="url"  name="url"  placeholder="权限菜单url"  value="${model.url }">
                  </div>
                </div>
                
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="button" class="btn btn-default" id="cancelBtn">取消</button>
                <button type="button" class="btn btn-info pull-right " id="submitBtn">提交</button>
              </div>
              <!-- /.box-footer -->
            </form>
          </div>
          

          <!-- /.box -->
          
          <%@ include file="/resources/common/required_js.jsp"%>
          <!-- 提示框 -->
          <%@ include file="/resources/common/bootstrap_alert.jsp"%>
		  <script type="text/javascript" src="${static}/plugins/projectSpecific/bootstrapValidator/js/bootstrapValidator.min.js"></script>
          <script>
		$(function(){
			
			$("#cancelBtn").click(function(){
				window.location.href="${ctx}/permissions/listPage.do"
			})
		  	// 点击之前聚焦校验
		  	$("#submitBtn").focus(function(){
					 $("#submitForm").data('bootstrapValidator').validate();
			});
		  	
			// 提交点击事件
			$("#submitBtn").click(function(){
        		// 校验通过
				if($("#submitForm").data('bootstrapValidator').isValid()){
				
				   var formData = new FormData($("#submitForm")[0]);
					$.ajax({
						url:"${ctx}/permissions/edit" ,
						type:"POST",
						data:formData ,
						processData: false,  // 告诉jQuery不要去处理发送的数据
						contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
						success:function(result){
							if(result&&result.success==true){
								openAlert(result.message);
								setTimeout(function(){
									top.location.href="${ctx}/permissions/listPage";
								},1000);
							}else{
								openAlert(result.message);
							}
						}
					});
				} 
			});
			 
			
			// 加载校验器				    
		    $('#submitForm').bootstrapValidator({
		        fields: {
		        	name: {
		                message: '当前值无效',
		                validators: {
		                    notEmpty: {
		                        message: '权限菜单名不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 12,
		                        message: '权限菜单名长度不能大于12个字符'
		                    }
		                }
		            },
		            url: {
		            	 message: '当前值无效',
			                validators: {
			                    notEmpty: {
			                        message: '权限菜单名不能为空'
			                    },
			                    stringLength: {
			                        min: 1,
			                        max: 60,
			                        message: '权限菜单url长度不能大于60个字符'
			                    }
			                }
		            }
		            
		        }
		    });
			
		
		})
		
</script>
</body>
</html>