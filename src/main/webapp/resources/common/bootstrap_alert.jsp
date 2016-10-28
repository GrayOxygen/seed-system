<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>
<!-- bootstrap 样式，提示信息模态框，前提要引入bootstrap的css与js -->
<!-- 模态框（Modal） -->
<div class="modal modal-primary" id="alertModal" tabindex="-1" role="dialog" aria-hidden="true" style="z-index:8899" >
 		 <div class="modal-dialog">
          <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                      <h4 class="modal-title">提示</h4>
                  </div>
                  <div class="modal-body"> </div>
                  <div class="modal-footer">
                      <button type="button" class="btn dark btn-outline" data-dismiss="modal" id="closeAlert">关闭</button>
                      <!--
                      <button type="button" class="btn green" id="okBtn">确定</button>
                      -->
                  </div>
              </div>
              <!-- /.modal-content -->
          </div>
      <!-- /.modal-dialog -->
</div>

<script>
	//信息提示窗
	function openAlert(msg,closeFunc) {
	$('#alertModal'+' .modal-body ').text(msg);
	$('#alertModal').modal("show");
	
	if(closeFunc){
	    $('#alertModal').on('hidden.bs.modal',closeFunc);
	}
}
</script>