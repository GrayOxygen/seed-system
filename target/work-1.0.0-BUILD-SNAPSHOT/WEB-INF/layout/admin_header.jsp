
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<div class="hd">
  <div class="logo" style="font-size:30px;font-family:KaiTi;">
    <a href="#"><span style="margin: 10px 10px 50px 0px;"><img src="${app_static}/admin/images/logo.png" width="202" height="72"
                                                               style="margin-left:-3px;"/></span></a>
  </div>
  <!-- 
  <div class="logo" style="line-height:92px;font-size:30px;font-family:KaiTi;color:black"  valign="center">代理商平台</div>
  -->
  <!-- 
  <div class="topfunction">
    <span class="f1"><a href="#"></a></span>
    <span class="f2"><a href="#"></a></span>
	<span class="f3"><a href="#"></a></span>
  </div> 
  -->
  <div class="time">
    <span>时间:</span><span class="fcgray">8:40:08</span>&nbsp;&nbsp;<span>今天是2011年5月1日 星期日</span>
  </div>

  <div class="userinfo">
    <div class="userinfoc" style="margin-top:4px;">
      <div style="margin-top:2px;">
        <span>当前用户 </span>
        <span class="f1">${ADMINUSER_PHONE}【${ADMINUSER_NODE.name}】</span>
        <span class="f2"><a href="${ctx}/admin/logout.do">【安全退出】</a></span>
      </div>
    </div>
    <div class="userinfotime"></div>
  </div>
</div>