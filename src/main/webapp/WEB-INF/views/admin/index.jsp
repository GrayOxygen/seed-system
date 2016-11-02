<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resources/common/taglibs.jsp"%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
<title>首页</title>
  <%@ include file="/resources/common/required_style.jsp" %>
</head>
<body>
    <section class="content">

      <!-- Default box -->
      <div class="box">
        <div class="box-header with-border">
          <h3 class="box-title">欢迎来到${company_name}</h3>

          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
              <i class="fa fa-minus"></i></button>
            <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
              <i class="fa fa-times"></i></button>
          </div>
        </div>
        <div class="box-body">
           我们致力于向商家，用户提供便捷高效的服务
        </div>
        <!-- /.box-body
        <div class="box-footer">
          Footer
        </div>
         -->
        <!-- /.box-footer-->
      </div>
      <!-- /.box -->

    </section>
    <%@ include file="/resources/common/required_js.jsp"%>
</body>
</html>
