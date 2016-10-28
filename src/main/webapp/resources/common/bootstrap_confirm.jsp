<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>
<!-- bootstrap 样式，提示信息模态框，前提要引入bootstrap的css与js -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog"  aria-labelledby="modalTitle" aria-hidden="true">
	   <div class="modal-dialog">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="modalTitle">
	               	请选择
	            </h4>
	         </div>
	         <div class="modal-body" id="msgID">
	         		确定删除吗？
	         </div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
	            </button>
	            <button type="button" class="btn btn-primary" id="okBtn">
	               				确定
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
<script>
//确认窗口
function openConfirm(sumbitEventFunc) {
	  //保存层级确认模态框
      $('#confirmModal').on('shown.bs.modal', function () {
	 	  // 执行一些动作...
	      $("#okBtn").click(function(){
	    	  sumbitEventFunc();
	      });
	  });
	  
      $('#confirmModal').modal("show");
}
</script>