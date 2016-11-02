<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/resources/common/taglibs.jsp" %>
<!DOCTYPE html>
<html  >
<head>
  <meta charset="utf-8"/>
  <title>404</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta content="width=device-width, initial-scale=1" name="viewport"/>
  <meta content="" name="description"/>
  <meta content="" name="author"/>
  <%@ include file="/resources/common/required_style.jsp" %>
</head>

<body  >
  <section class="content">
	<div class="error-page">
        <h2 class="headline text-yellow"> 404</h2>

        <div class="error-content">
          <h3><i class="fa fa-warning text-yellow"></i> 页面未找到 </h3>

          <p>
            我们找不到你所访问的页面
            请联系管理员 <a href="${ctx}/admin/index.do"  > 返回首页 </a>
          </p>

        </div>
        <!-- /.error-content -->
      </div>
      <!-- /.error-page -->
      </section>
        <%@ include file="/resources/common/required_js.jsp" %>
</body>

</html>