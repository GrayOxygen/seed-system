<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>
<%@ include file="/resources/common/taglibs.jsp"%>
<!DOCTYPE html  >
<html >
<head>
<title>添加角色</title>
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
              <div class="box-body">
              	 <div class="form-group">
                  <label for="name" class="col-sm-2 control-label">角色名</label>

                  <div class="col-sm-5">
                    <input type="text" class="form-control" id="name"  name="name" placeholder="角色名">
                  </div>
                </div>
                
                <input type="hidden"  id="permsID"  name="permsID" />
                 <div class="form-group">
	                <label for="pwd" class="col-sm-2 control-label">权限菜单</label>

          			<div class="col-sm-10" >
						<div class="input-icon right">
							<div class="panel panel-info"  >
							   <div class="panel-heading">
							      <h3 class="panel-title">勾选菜单</h3>
							   </div>
							   <div class="panel-body">
								  <iframe src="${ctx}/roles/permListPage"   id="iFrame"   style="height: 43em;width: 100%;border:0" ></iframe>
							   </div>
						    </div>   
						 </div>                     	
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
				window.location.href="${ctx}/roles/listPage.do"
			})
		  	// 点击之前聚焦校验
		  	$("#submitBtn").focus(function(){
					 $("#submitForm").data('bootstrapValidator').validate();
			});
		  	
			 // 提交点击事件
			$("#submitBtn").click(function(){
				// 校验 iframe 选择项  
				var iFrame = document.getElementById('iFrame');
				// 兼容：获取窗口对象
				var win = iFrame.window || iFrame.contentWindow;
				var checked = win.getCheckedFromIFrame();
				if( !(checked && checked.length>0) ){
					openAlert("请选择权限菜单!");
					return ;
				}
				$("#permsID").val(checked);
				
        		// 校验通过
				if($("#submitForm").data('bootstrapValidator').isValid()){
				
				   var formData = new FormData($("#submitForm")[0]);
					$.ajax({
						url:"${ctx}/roles/add" ,
						type:"POST",
						data:formData ,
						processData: false,  // 告诉jQuery不要去处理发送的数据
						contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
						success:function(result){
							if(result&&result.success==true){
								openAlert(result.message);
								setTimeout(function(){
									top.location.href="${ctx}/roles/listPage";
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
		                        message: '角色名不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 25,
		                        message: '角色名长度不能大于12个字符'
		                    }
		                }
		            }
		        }
		    });
			
		
		})
		
</script>
</body>
</html>