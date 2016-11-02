<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/resources/common/taglibs.jsp" %>
<!DOCTYPE html>
<html  >
<head>
  <meta charset="utf-8"/>
  <title>403</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1" name="viewport"/>
  <meta content="" name="description"/>
  <meta content="" name="author"/>
  <%@ include file="/resources/common/required_style.jsp" %>
</head>

<body  >
	   <!-- Main content -->
    <section class="content">

      <div class="error-page">
        <h2 class="headline text-red">403</h2>

        <div class="error-content">
          <h3><i class="fa fa-warning text-red"></i> 您无权访问该地址！</h3>

          <p>
            请联系管理员给您分配适当的权限
             <a href="${ctx}/admin/index">返回首页</a>
          </p>

        </div>
      </div>
      <!-- /.error-page -->

    </section>
    <!-- /.content -->
        <%@ include file="/resources/common/required_js.jsp" %>
</body>

</html>