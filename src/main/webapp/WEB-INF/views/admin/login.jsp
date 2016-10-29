<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resources/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>${system_name } | 登陆</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${static}/bootstrap-3.3.6/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${static}/AdminLTE-2.3.7/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${static}/plugins/iCheck/square/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="${ctx}/admin/index"><b>Admin</b>LTE</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Sign in to start your session</p>

    <form action="${ctx}/admin/login" method="post"  id="loginForm">
   	    <input type="hidden" id="msg" value="${message}">
       <div class="form-group has-feedback">
	       <span id="msgMsg" style="color:red">${message}</span> 
      </div>
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="用户名" name="userName" id="user"  value="${userName }">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="密码" name="pwd"  id="pwd" value="${pwd }">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox">记住我
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat"  id="loginID">登陆</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <div class="social-auth-links text-center">
      <p>- OR -</p>
      <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> QQ登陆</a>
      <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i>微信登陆</a>
    </div>
    <!-- /.social-auth-links -->

    <a href="#">I forgot my password</a><br>
    <a href="register.jsp" class="text-center">Register a new membership</a>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="${static}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${static}/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${static}/plugins/iCheck/icheck.min.js"></script>

<script type="text/javascript" src="${static}/common/js/md5.js"></script>
<script>
  $(function () {
	    $('input').iCheck({
	      checkboxClass: 'icheckbox_square-blue',
	      radioClass: 'iradio_square-blue',
	      increaseArea: '20%' // optional
	    });
    
		var user = $("#user");
		var pwd = $("#pwd");
		var code = $("#code");
		var msgMsg = $("#msgMsg");
		var msg = $("#msg").val();
		//var msg = '${message}';
		if(msg != ""){
				msgMsg.html(msg);
		}
		user.focus(function() {
			$(this).select();
			//msgMsg.hide();
		});
		user.blur(function() {
			usercheck();
		});
		pwd.focus(function() {
			$(this).select();
			//msgMsg.hide();
		});
		pwd.blur(function() {
			pwdcheck();
		});
		code.focus(function() {
			$(this).select();
			//msgMsg.hide();
		});
		code.blur(function() {
			codecheck();
		});
		//$("#codeImg").attr("src", "../Kaptcha.jpg?t=" + new Date().getTime());
	});
	function changeCode() {
		$("#codeImg").attr("src", "../Kaptcha.jpg?t=" + new Date().getTime());
	}
	function checkSubmit() {
		if (usercheck() && pwdcheck()  ) {
			var pwd = $("#pwd");
			pwd.val(hex_md5(pwd.val()));
			document.getElementById("loginForm").submit();
		}
	}
	//用户名检查
	function usercheck() {
		var checkOk = false;
		var user = $("#user");
		var msgMsg = $("#msgMsg");
		if (user.val() == '') {
			msgMsg.html("用户名不能为空！");
			//msgMsg.show();
		} else {
			msgMsg.html("");
			//msgMsg.hide();
			checkOk = true;
		}
		return checkOk;
	}
	//密码检查
	function pwdcheck() {
		var checkOk = false;
		var pwd = $("#pwd");
		var msgMsg = $("#msgMsg");
		if (pwd.val() == '') {
			msgMsg.html("密码不能为空！");
			//msgMsg.show();
		} else {
			msgMsg.html("");
			//msgMsg.hide();
			checkOk = true;
		}
		return checkOk;
	}
	//验证码检查
	function codecheck() {
		var code = $("#code");
		var msgMsg = $("#msgMsg");
		if (code.val() == '') {
			msgMsg.html("验证码不能为空！");
			//msgMsg.show();
			return false;
		}
		return true;
	}
	document.onkeydown = function(e) { //传入 event
		var e = e || window.event; //兼容IE和FF
		if (e.keyCode == 13){
			checkSubmit();
		}
	}
	$("#loginForm").submit(function(){
			if(!checkSubmit()){
				return false;
			}
	})
</script>
  </body>
</html>
