<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="robots" content="noindex,nofollow">
<%@ include file="/common/commonJS.jsp"%>
<title>${title_prefix}后台用户登录</title>
<link rel="shortcut icon" type="image/x-icon" href="${app_static}/favicon.ico?t=1" />
  
<script type="text/javascript" src="${app_static}/js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
<link rel="stylesheet" href="${app_static}/js/Validform_v5.3.2/Validform_v5.3.2/demo/css/style.css" />
<script type="text/javascript">

var dialog ;
var passPwd=false;
var passPhone=false;
function submitTheForm(){
	var flag=true;//true为通过
	//校验
	if(!$("#checkCode").val()){
		flag=false;
		$("#checkCodeMsg").text("校验码不能为空");
		$("#checkCode").siblings(".Validform_checktip").addClass("Validform_right Validform_wrong Validform_checktip");
		return ;
	}
	
	//校验验证码是否有效
	 $.ajax({
            type:"POST",
            async:false,
            url:"${ctx}/admin/sys/phoneCheckCode.do",
            data:{"phoneNum":$("#phoneNum").val(),"checkCode":$("#checkCode").val()},
            success:function(msg){
                if(msg){
                }else{
                	flag=false;
                }
            }
     });
	
	if(!flag){
		$("#checkCodeMsg").text("校验码有误");
		$("#checkCode").siblings(".Validform_checktip").addClass("Validform_right Validform_wrong Validform_checktip");
	} else{
		$("#checkCode").removeClass("Validform_error");
		$("#checkCode").siblings(".Validform_checktip").removeClass("Validform_right Validform_wrong Validform_loading").addClass("Validform_checktip");
		$("#checkCode").siblings(".Validform_checktip").text('');
	}
	
	//提交
	$("#submitForm").submit();
}

$(function(){
	
	//-------------------------------
	//提示框
	  dialog = weiweiDialog("dialog",{height:"150",closeOnEscape :true},true);
	
	//加载校验插件
	//ajax提交表单
	 $("#submitForm").Validform({
			ajaxPost:true,
			datatype:{//自定义校验:密码是否存在
				"existPwd":function(gets,obj,curform,regxp){
					/*参数gets是获取到的表单元素值，
					  obj为当前表单元素，
					  curform为当前验证的表单，
					  regxp为内置的一些正则表达式的引用。*/
					var pass=false;
					$.ajax({
						url:"${ctx}/admin/sys/existPwd.do",
						type:"post",
						data:{"pwd":gets},
						success:function(result){
							if(result&&result.success==false){//验证失败
								$("#tipId1").text(result.message);
								obj.siblings(".Validform_checktip").addClass("Validform_right Validform_wrong Validform_checktip");
							
								passPwd=false;
								disableSendCheckCode();
							}else{ 
								obj.removeClass("Validform_error");
								obj.siblings(".Validform_checktip").removeClass("Validform_right Validform_wrong Validform_loading").addClass("Validform_checktip");
								obj.siblings(".Validform_checktip").text('');
								passPwd=true;
								enableSendCheckCode();
							}
						}
					});
					return passPwd;
				},
				"existPhone":function(gets,obj,curform,regxp){
			        $.ajax({
			            type:"POST",
			            url:"${ctx}/admin/sys/existPhoneNumExist.do",
			            data:"phoneNum="+$("#phoneNum").val(),
			            success:function(result){
								if(result.success==true){//验证失败  手机号存在
									$("#tipId2").text(result.message);
									
				        			  passPhone=false;
				        			  disableSendCheckCode();
				        			  
								}else{
									$("#tipId2").text('');
									obj.removeClass("Validform_error");
									$("#tipId2").removeClass("Validform_right Validform_wrong Validform_loading").addClass("Validform_checktip");
									//启用
				        			passPhone=true;
									enableSendCheckCode();
								}
			            }
			        });
			        return passPhone;
			    } 
			},callback:function(resultObj){//提交表单后的回调方法
				if(resultObj){
					$("#msgId").text(resultObj.message);
					dialog.dialog({close: function(event, ui) {
			        	location.href="${ctx}/admin/logout.do";
			        }});
					dialog.dialog("open");
				}
			},tiptype:function(msg,o,cssctl){
				//msg：提示信息;
				//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
				//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
				if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;   ajax前调用的校验信息
					var objtip=o.obj.siblings(".Validform_checktip");
					
					if(o.obj.attr("id")=="pwd"){
						if(o.type==3){
							passPwd=false;
							disableSendCheckCode();
						}else{
							if(o.type==2){
								passPwd=true;
							}
							enableSendCheckCode();
						}
					}
					if(o.obj.attr("id")=="phoneNum"){
						if(o.type==3){
							passPhone=false;
							disableSendCheckCode();
						}else{
							if(o.type==2){
								passPhone=true;
							}
							enableSendCheckCode();
						}
					}
					
					//第三个参数在源码基础上加的，true表示校验通过移除消息提示控件所有样式，false表示显示默认绿色勾图标
					cssctl(objtip,o.type,true);
					objtip.text(msg);
				} 
			}
	});
	//密码和手机验证都ok后才启用发送按钮
	function enableSendCheckCode(){
		if(passPwd&&passPhone){
			  //验证成功启用a标签按钮
      	 	  $("#sendPhoneCheckCode").click(clickFunc); 
			  $('#sendPhoneCheckCode').addClass("btn61");
			  $('#sendPhoneCheckCode').removeClass("Disabled");
			  passPhone=true;
		}
	}
	//密码和手机验证其中一者未通过禁用发送按钮
	function disableSendCheckCode(){
			//验证失败禁用
  	  	  $('#sendPhoneCheckCode').unbind("click");
		  $('#sendPhoneCheckCode').removeClass("btn61");
		  $('#sendPhoneCheckCode').addClass("Disabled");
	}
	
	//发送机校验
	var clickFunc = function(){
			$.ajax({
				type:"POST",
				url:"${ctx}/admin/sys/sendPhoneCheckCode.do",
				data:"phoneNum="+$("#phoneNum").val(),
				success:function(result){
					if(result){//enable input
						//窗口提示发送成功
						dialog.dialog( "open" );
					//禁填
						$("#checkCode").attr("disabled",false);
						disableSendCheckCode();
						
					setTimeout(function(){
						//COUNT DOWN
						$('#countdown').countDown({
							startNumber: 60,
							startFontSize: '20px',
							endFontSize: '10px',
							duration:1000,
							callBack: function(me) {
								$(me).text('').css('color','#090');
					//可填
								$("#checkCode").attr("disabled",true);
								enableSendCheckCode();
								
							}
						});
					},500);
							//TODO  校验码发送校验为每分钟只能发一次
						
					}else{
						//send error TODO  错误信息
						$("#checkCode").attr("disabled",true);
						
					}
				}
			})
	};
	 
	 //绑定发送按钮点击事件
	 $("#sendPhoneCheckCode").click(clickFunc); 
	
	 //禁用a标签按钮
	 $('#sendPhoneCheckCode').unbind("click");
	 $('#sendPhoneCheckCode').removeClass("btn61");
	 $('#sendPhoneCheckCode').addClass("Disabled");
	  
	  
	  
});


</script>
</head>
<body >
<div id="dialog"  >
	<span id="msgId">验证码已发送到您手机上，请及时查收</span>
</div>

 <div class="oper_right">
  <div class="oper_rtit" style="padding-top: 15px;padding-left: 15px;padding-bottom: -15px">
	<span style="font-size: medium;">【修改手机号】</span>
  </div>
  <div class="oper_rwrp"></div>
  <table border="0">
	<tr>
	  <td width="70%">
		<form action="${ctx}/admin/sys/updatePhone.do" modelAttribute="module" method="post"
			  id="submitForm">
			  
			  	<div class="ma_fr">
			<span id="msgMsg" style="color:red">${message}</span>
			<div class="ma_line fl">
				<div class="ma_info fl">登录密码&nbsp</div>
				<div>
				 <input id="pwd" type="password" value="" name="pwd" class="ma_def textfoc" placeholder="请输入登录密码"  datatype="*6-16,existPwd" nullmsg="请设置密码！" errormsg="请输入6到16位长度的密码！">
				 <span id="tipId1" class="Validform_checktip"></span>
				</div>
			</div>
			<div class="ma_line fl">
				<div class="ma_info fl">新手机号&nbsp</div>
				<div style="position:relative"> 
				<input type="text"  name="phoneNum" style="float:left" id="phoneNum" class="ma_def textfoc"    value=""  placeholder="请输入手机号"  datatype="m,existPhone" nullmsg="请设置手机号！" errormsg="手机号格式有误！"/>
				<span id="tipId2" class="Validform_checktip"></span> 
				
				<a href="javascript:void(0)"  style="position:relative;left:10px"   id="sendPhoneCheckCode"  class="btn61" >获取验证码</a>
				<span style="color:red">每分钟只能发送一次</span>
			  	<!-- 倒计时插件 -->
				<span id="countdown"  style="color: #0099CC;position:absolute;font-weight:bold"></span> 
				</div>
			</div>
			<div class="ma_line fl">
				<div class="ma_info fl">校验码&nbsp</div>
				<div>
				<input type="text" name="checkCode" id="checkCode" class="ma_def textfoc" value="" placeholder="请输入验证码" disabled   nullmsg="请设置校验码！" />
				<span class="Validform_checktip"  id="checkCodeMsg"></span>
				</div>
			</div>
			<div class="ml160 mb10 fl" >
				<a href="javascript:submitTheForm();" class="btn61">确定</a>
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