<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/resources/common/taglibs.jsp"%>
<!DOCTYPE html  >
<html >
<head>
<title>修改密码</title>
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
            <form class="form-horizontal"  action="javascript:;"  id="submitForm"  method="POST">
              <input type="hidden"   name="id" value="${adminUserId }">
              <div class="box-body">
                <div class="form-group">
                  <label for="pwd" class="col-sm-2 control-label">旧密码</label>

                  <div class="col-sm-5">
                    <input type="password" class="form-control" id="pwd"  name="pwd"   placeholder="旧密码"  >
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="newPwd" class="col-sm-2 control-label">新密码</label>

                  <div class="col-sm-5">
                    <input type="password" class="form-control" id="newPwd"  name="newPwd"   placeholder="新密码"   >
                  </div>
                </div>
                
              	 <div class="form-group">
                  <label for="checkPwd" class="col-sm-2 control-label">再次输入新密码</label>

                  <div class="col-sm-5">
                    <input type="password" class="form-control" id="checkPwd"  name="checkPwd" placeholder="确认密码"   >
                  </div>
                </div>
           		
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="button" class="btn btn-info pull-right " id="submitBtn">提交</button>
              </div>
              <!-- /.box-footer -->
            </form>
          </div>
          
          
          
          
          <!-- /.box -->
          <%@ include file="/resources/common/required_js.jsp"%>
		 <%@ include file="/resources/common/bootstrap_alert.jsp"%>
		  <script type="text/javascript" src="${static}/plugins/projectSpecific/bootstrapValidator/js/bootstrapValidator.min.js"></script>
          <script>
		$(function(){
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
						url:"${ctx}/adminUsers/updatePwd" ,
						type:"POST",
						data:formData ,
						processData: false,  // 告诉jQuery不要去处理发送的数据
						contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
						success:function(result){
							if(result&&result.success==true){
								openAlert(result.message);
								setTimeout(function(){
									top.location.href="${ctx}/admin/index";
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
		        	 pwd: {
			                message: '当前值无效',
			                validators: {
			                    notEmpty: {
			                        message: '密码不能为空'
			                    },
			                    stringLength: {
			                        min: 6,
			                        max: 12,
			                        message: '密码长度6到12位'
			                    }
			                }
		       		 },
		       		newPwd: {
		            	validators: {
			            	notEmpty: {
			            		message: '新密码不能为空'
			            	},
			            	stringLength: {
		                        min: 6,
		                        max: 12,
		                        message: '密码长度6到12位'
		                    }
		            	}
		           	},
		            checkPwd: {
		            	validators: {
			            	notEmpty: {
			            		message: '请再次输入新密码'
			            	},
			            	stringLength: {
		                        min: 6,
		                        max: 12,
		                        message: '密码长度6到12位'
		                    },
			            	identical: {
				            	field: 'newPwd',
				            	message: '两次输入密码不一致'
			            	}
		            	}
		            }
		           
		        }
		    });
		})
		
</script>
</body>
</html>