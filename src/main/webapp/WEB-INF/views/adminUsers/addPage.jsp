<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>集约平台-添加下级用户</title>

<%@ include file="/common/commonJS.jsp"%>

<script type="text/javascript" src="${app_static}/js/date/WdatePicker.js"></script>

<script type="text/javascript" src="${app_static}/js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
<link rel="stylesheet" href="${app_static}/js/Validform_v5.3.2/Validform_v5.3.2/demo/css/style.css" />

<link rel="stylesheet" type="text/css" href="${app_static}/js/jquery-easyui-1.4.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${app_static}/js/jquery-easyui-1.4.3/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${app_static}/js/jquery-easyui-1.4.3/demo/demo.css">
<script type="text/javascript" src="${app_static}/js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>

<script>
	 function submitTheFm(){
		 if(!$("#nodeId").combotree("getValue")){
			    var dialog = weiweiDefinedDialog("dialog",{},true);
				$("#msgId").text("请选择所属组织！");
				dialog.dialog("open");
		 }else{
			 $("#submitForm").submit();
		 }
	 }
$(function(){
	 //层级树change事件
    $("#nodeId").combotree({
      url:'${ctx}/admin/orgNode/getSelfAndSonTree.do',
  	  "onChange":function(newValue,oldValue){
  		  		$("#selectedNodeId").val(newValue);
    	   }
    });
	//ajax提交表单
	 $("#submitForm").Validform({
			ajaxPost:true,
			callback:function(resultObj){//回调方法
				if(resultObj&&resultObj.success==false){
					var dialog = weiweiDefinedDialog("dialog",{},true);
					$("#msgId").text(resultObj.message);
					dialog.dialog("open");
				}else{
					top.location.href="${ctx}/admin/sys/adminUserList.do";
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

<body>
<div id="dialog"  >
  <p id="msgId"> </p>
</div>

<div class="oper_right">
		<div class="oper_rtit" style="padding-top: 15px;padding-left: 15px;padding-bottom: -15px">
			<span style="font-size: medium;">【添加下级用户】</span>
		</div>
		<div class="oper_rwrp"></div>
		<form action="${ctx}/admin/sys/addAdminUser.do" modelAttribute="module"  method="post" id="submitForm">
		<div class="ma_fr">
			<span id="msgMsg" style="color:red">${message}</span>
			<div class="ma_line fl">
				<div class="ma_line fl mt10">
			        <div class="ma_info fl">所属组织：</div>
					<!-- 组织层级树 -->
					<input class="easyui-combotree"  id="nodeId"    style="width:200px;">
					<input   id="selectedNodeId" name="nodeId" hidden="true"  nullmsg="请输入手机号！" errormsg="请选择所属组织！"/>
					<label   id="nodeMSGID" class="error" style="display: none;" >请选择层级节点</label>
			      </div>
				<div>
			        <span class="Validform_checktip"></span>
				</div>
			</div>
			<div class="ma_line fl">
				<div class="ma_info fl">管理员名称：</div>
				<div>
				<input type="text" name="username" class="ma_def textfoc"   /> 
				</div>
			</div>
			<div class="ma_line fl">
				<div class="ma_info fl">管理员手机号：</div>
				<div>
				<input type="text" name="phoneNum" class="ma_def textfoc"  placeholder="请输入手机号"  datatype="m" nullmsg="请输入手机号！" errormsg="手好号码格式有误！" /><span class="Validform_checktip"></span>
				</div>
			</div>
			<div class="ma_line fl">
				<div class="ma_info fl">默认密码：</div>
				<div>
				<input type="password" name="defaultPwd" class="ma_def textfoc"  placeholder="默认密码"  datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！"  /><span class="Validform_checktip"></span>
				</div>
			</div>
			<div class="ml160 mb10 fl">
				<a href="javascript:submitTheFm();" class="btn61">确定</a>
				<a href="javascript:cancel();" class="btn61">取消</a>
			</div>
			<div class="clr"></div>
		</div>
		</form>
		<div class="clr"></div>
	</div>
</body>
</html>