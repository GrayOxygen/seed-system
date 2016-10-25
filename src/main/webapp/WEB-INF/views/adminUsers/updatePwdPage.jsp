<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="robots" content="noindex,nofollow">
<title>${title_prefix}后台用户登录</title>
<%@ include file="/common/commonJS.jsp"%>
<link rel="shortcut icon" type="image/x-icon" href="${app_static}/favicon.ico?t=1" />
<script type="text/javascript" src="${app_static}/common/js/md5.js"></script>
  

<script type="text/javascript" src="${app_static}/js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
<link rel="stylesheet" href="${app_static}/js/Validform_v5.3.2/Validform_v5.3.2/demo/css/style.css" />
<script type="text/javascript">
					var pass=false;
$(function(){
	//提示框
	var dialog = $( "#dialog" ).dialog({
        autoOpen: false,
        draggable:false,
        closeOnEscape: false,
        resizable: true,
        height:150,
		buttons:{"关闭":function() { $(this).dialog("close"); }},
        title:"提示",
        modal:true,
        close: function(event, ui) {
        	location.href="${ctx}/admin/login.do";
        }
    });
	
	//ajax提交表单
	 $("#submitForm").Validform({
			ajaxPost:true,
			datatype:{//自定义校验:密码是否存在
				"existPwd":function(gets,obj,curform,regxp){
					/*参数gets是获取到的表单元素值，
					  obj为当前表单元素，
					  curform为当前验证的表单，
					  regxp为内置的一些正则表达式的引用。*/
					$.ajax({
						url:"${ctx}/admin/sys/existPwd.do",
						type:"post",
						data:{"pwd":gets},
						success:function(result){
								if(result.success==false){//验证失败
									obj.siblings(".Validform_checktip").text(result.message);
									obj.siblings(".Validform_checktip").addClass("Validform_right Validform_wrong Validform_checktip");
									pass=false;
								}else{//success
									obj.text('');
									obj.removeClass("Validform_error");
									obj.siblings(".Validform_checktip").removeClass("Validform_right Validform_wrong Validform_loading").addClass("Validform_checktip");
									obj.siblings(".Validform_checktip").text('');
									pass=true;
								}
							}
					});
					return pass;
				}
			},
			callback:function(resultObj){//回调方法
				if(resultObj){
					$("#msgId").text(resultObj.message);
					dialog.dialog("open");
				}
			},tiptype:function(msg,o,cssctl){
				//msg：提示信息;
				//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
				//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
				if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;   ajax前调用的校验信息
					var objtip=o.obj.siblings(".Validform_checktip");
					//第三个参数在源码基础上加的，true表示校验通过移除消息提示控件所有样式，false表示显示默认绿色勾图标
						
					cssctl(objtip,o.type,true);
					objtip.text(msg);
				} 
			}
	});
	
	
	
	 
});
</script>
</head>
<body >
<div id="dialog"  >
  <p id="msgId"> </p>
</div>

 <div class="oper_right">
  <div class="oper_rtit" style="padding-top: 15px;padding-left: 15px;padding-bottom: -15px">
	<span style="font-size: medium;">【修改密码】</span>
  </div>
  <div class="oper_rwrp"></div>
  <table border="0">
	<tr>
	  <td width="70%">
		<form action="${ctx}/admin/sys/updatePwd.do" modelAttribute="module"   id="submitForm">
			  	<div class="ma_fr">
			<span id="msgMsg" style="color:red">${message}</span>
			<div class="ma_line fl">
				<div class="ma_info fl">&nbsp</div>
				<div>
				 <input id="oldPwd" type="password" value=""  class="ma_def textfoc" placeholder="请输入旧密码" datatype="*6-16,existPwd" nullmsg="请设置密码！" errormsg="请输入6到16位长度的密码!" />
				  <a href="${ctx}/admin/getPwdPage.do"  class="btn61"  style="position: absolute;">忘记密码</a>
				 <span id="tipId1" class="Validform_checktip"></span>
				</div>
			</div>
			<div class="ma_line fl">
				<div class="ma_info fl">&nbsp</div>
				<div>
				<input type="password" id="newPwd1"  name="newPwd1" class="ma_def textfoc" value="" placeholder="请输入新密码" datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！"  /><span class="Validform_checktip"></span>
				</div>
			</div>
			<div class="ma_line fl">
				<div class="ma_info fl">&nbsp</div>
				<div>
				<input type="password" id="newPwd2" name="newPwd2" class="ma_def textfoc" value="" placeholder="请再次输入新密码" datatype="*6-16" nullmsg="请设置密码！" recheck="newPwd1" errormsg="密码不一致！" nullmsg="密码范围在6~16位之间！" /><span class="Validform_checktip"></span>
				</div>
			</div>
			<div class="ml160 mb10 fl" >
				<a href="javascript:save();" class="btn61">确定</a>
			</div>
			<div class="clr"></div>
		</div>
		</form>
	  </td>
	</tr>
  </table>
</div>


</body>
</html>