<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<div class="oper_left">
  <div class="oper_lwrp_new">
    <a href="${ctx}/admin/index.do"> <img id="indexPic" src="${app_static}/admin/images/leftMenu/index.png"/>

      <div id="indexText" class="oper_lwrp_new_text" style="color: #ADB0B7;">首页</div>
    </a>
  </div>
  
  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/sys/updatePwdPage.do">
        <img src="${app_static}/admin/images/leftMenu/content.png"
             id="updatePwdPicId"/> <font class="oper_lwrp_new_text" style="color: #ADB0B7;" id="updatePwdTextId">修改密码</font>
      </a>
  </div>
  <div class="oper_lwrp_new">
      <a href="${ctx}//admin/sys/updatePhonePage.do">
        <img src="${app_static}/admin/images/leftMenu/content.png"
             id="updatePhonePicId"/> <font class="oper_lwrp_new_text" style="color: #ADB0B7;" id="updatePhoneTextId">修改手机号</font>
      </a>
  </div>
  
  	 <!-- 仅仅网点没有该权限 -->
     <c:if test="${ADMINUSER.buildin==true||ADMINUSER_NODE_LEVEL==1||ADMINUSER_NODE_LEVEL==2||ADMINUSER_NODE_LEVEL==3 }">
		  <div class="oper_lwrp_new">
		      <a href="${ctx}/admin/sys/adminUserList.do">
		        <img src="${app_static}/admin/images/leftMenu/content.png"
		             id="subUserPicId"/> <font class="oper_lwrp_new_text" style="color: #ADB0B7;" id="subUserTextId">下级用户管理</font>
		      </a>
		  </div>
		  
	 </c:if>
	 
	 <c:if test="${ADMINUSER.buildin==true || ADMINUSER_NODE_LEVEL==1 }">
		  
		  <div class="oper_lwrp_new">
		      <a href="${ctx}/admin/orgNode/list.do">
		        <img src="${app_static}/admin/images/leftMenu/content.png"
		             id="orgNodePicId"/> <font class="oper_lwrp_new_text" style="color: #ADB0B7;" id="orgNodeTextId">组织结构管理</font>
		      </a>
		  </div>
		  
	 </c:if>
	 
		  
	  <div class="oper_lwrp_new">
	      <a href="${ctx}/admin/beaconGroup/beaconGroupList.do">
	        <img src="${app_static}/admin/images/leftMenu/content.png"
	             id="beaconGroupPicId"/> <font class="oper_lwrp_new_text" style="color: #ADB0B7;" id="beaconGroupTextId">信标分组管理</font>
	      </a>
	  </div>
	  
	  
	  <div class="oper_lwrp_new">
	      <a href="${ctx}/admin/information/informationList.do">
	        <img src="${app_static}/admin/images/leftMenu/content.png"
	             id="informationPicId"/> <font class="oper_lwrp_new_text" style="color: #ADB0B7;" id="informationTextId">推送内容管理</font>
	      </a>
	  </div>
	  
	 
    
    <c:if test="${ADMINUSER.buildin==true || ADMINUSER_NODE_LEVEL==1}">
		 <div class="oper_lwrp_new">
		      <a href="${ctx}/admin/wx/company_list.do">
		        <img src="${app_static}/admin/images/leftMenu/content.png"
		             id="weixinPic"/> <font class="oper_lwrp_new_text"
		                                    style="color: #ADB0B7;" id="weixinTextId">公众号管理</font></a>
		  </div>
		  
		  <div class="oper_lwrp_new">
		    <a href="${ctx}/admin/queueBusiTypeManage/queueBusiType_list.do"><img
		      src="${app_static}/admin/images/leftMenu/content.png"
		      id="queueBusiTypePic"/> <font class="oper_lwrp_new_text"
		                                    style="color: #ADB0B7;" id="queueBusiTypeTextId">排队业务类型</font></a>
		  </div>
		  
  	</c:if>
		  
	<c:if test="${ADMINUSER.buildin==true ||ADMINUSER_NODE_LEVEL==1||ADMINUSER_NODE_LEVEL==2||ADMINUSER_NODE_LEVEL==3}">
	 
		  <div class="oper_lwrp_new">
		      <a href="${ctx}/admin/structureManage/companyBranch_list.do">
		        <img src="${app_static}/admin/images/leftMenu/content.png"
		             id="branchPicId"/> <font class="oper_lwrp_new_text" style="color: #ADB0B7;" id="branchTextId">网点维护管理</font>
		      </a>
		  </div>
		  
	</c:if>
	
  
    <div class="oper_lwrp_new">
      <a href="${ctx}/admin/beacon/beaconList.do">
        <img src="${app_static}/admin/images/leftMenu/content.png"
             id="theBeaconPicId"/> <font class="oper_lwrp_new_text"
                                      style="color: #ADB0B7;" id="theBeaconTextId">信标信息管理</font>
      </a>
  </div>

  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/queueBeacon/beaconList.do">
        <img src="${app_static}/admin/images/leftMenu/content.png"
             id="queueBeaconPicId"/> <font class="oper_lwrp_new_text"
                                      style="color: #ADB0B7;" id="queueBeaconTextId">排队信标</font>
      </a>
  </div>
  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/sendBeacon/beaconList.do">
        <img src="${app_static}/admin/images/leftMenu/content.png"
             id="sendBeaconPicId"/> <font class="oper_lwrp_new_text"
                                      style="color: #ADB0B7;" id="sendBeaconTextId">推送信标</font>
      </a>
  </div>
  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/weiBeacon/beaconList.do">
        <img src="${app_static}/admin/images/leftMenu/content.png"
             id="weiBeaconPicId"/> <font class="oper_lwrp_new_text"
                                      style="color: #ADB0B7;" id="weiBeaconTextId">微标</font>
      </a>
  </div>
  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/wx/wxViewList.do">
        <img src="${app_static}/admin/images/leftMenu/content.png"
             id="wxPagePicId"/> <font class="oper_lwrp_new_text"
                                      style="color: #ADB0B7;" id="wxPageTextId">信标发布管理</font>
      </a>
  </div>
  
  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/beaconReport/totalBeaconReportPage.do"><img
        src="${app_static}/admin/images/leftMenu/content.png"
        id="beaconReportPic"/> <font class="oper_lwrp_new_text"
                                style="color: #ADB0B7;" id="beaconReportTextId">门店统计报表</font></a>
  </div>
  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/beaconReport/queueBeaconReportPage.do"><img
        src="${app_static}/admin/images/leftMenu/content.png"
        id="queueBeaconReportPic"/> <font class="oper_lwrp_new_text"
                                style="color: #ADB0B7;" id="queueBeaconReportTextId">排队统计报表</font></a>
  </div>
  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/beaconReport/sendBeaconReportPage.do"><img
        src="${app_static}/admin/images/leftMenu/content.png"
        id="sendBeaconReportPic"/> <font class="oper_lwrp_new_text"
                                style="color: #ADB0B7;" id="sendBeaconReportTextId">推送统计报表</font></a>
  </div>
  <div class="oper_lwrp_new">
      <a href="${ctx}/admin/beaconReport/weiBeaconReportPage.do"><img
      src="${app_static}/admin/images/leftMenu/content.png"
        id="weiBeaconReportPic"/> <font class="oper_lwrp_new_text"
                                style="color: #ADB0B7;" id="weiBeaconReportTextId">微标统计报表</font></a>
  </div>

 <c:if test="${ADMINUSER.buildin==true  }">
	 
	  <div class="oper_lwrp_new">
	      <a href="${ctx}/admin/sys/maintenancePage.do?branchId=${ADMINUSER.branchId}"><img   src="${app_static}/admin/images/leftMenu/content.png"  id="testPic"/> 
	      <font class="oper_lwrp_new_text"
	                                style="color: #ADB0B7;" id="testTextId">接口维护</font></a>
	  </div>
	  
  </c:if>
  
  <div class="clr"></div>
</div>
<script language="javascript" type="text/javascript">
  highlight();
  function highlight() {
    var url1 = document.URL;
    if (url1.indexOf("index") != -1) {
      changeIndexPic('${app_static}/admin/images/leftMenu/index_s.png', '#43AC83', 'indexPic', 'indexText');
    } else if (url1.indexOf("updatePwdPage") != -1) {//修改密码
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'updatePwdPicId', 'updatePwdTextId');
    } else if (url1.indexOf("updatePhonePage") != -1) {//修改手机号
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'updatePhonePicId', 'updatePhoneTextId');
    } else if (url1.indexOf("adminUserList") != -1) {//下级用户
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'subUserPicId', 'subUserTextId');
    } else if (url1.indexOf("companyBranch_list") != -1) {//网点维护
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'branchPicId', 'branchTextId');
    } else if (url1.indexOf("queueBeacon/beaconList") != -1) {//排队信标
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'queueBeaconPicId', 'queueBeaconTextId');
    } else if (url1.indexOf("sendBeacon/beaconList") != -1) {//推送信标
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'sendBeaconPicId', 'sendBeaconTextId');
    } else if (url1.indexOf("weiBeacon/beaconList") != -1) {//微标
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'weiBeaconPicId', 'weiBeaconTextId');
    } else if (url1.indexOf("wxViewList") != -1) {//微信页面
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'wxPagePicId', 'wxPageTextId');
    } else if (url1.indexOf("totalBeaconReportPage") != -1) {// 门店统计
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'beaconReportPic', 'beaconReportTextId');
    } else if (url1.indexOf("beaconReport/queueBeaconReportPage") != -1) {//排队信标统计
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'queueBeaconReportPic', 'queueBeaconReportTextId');
    } else if(url1.indexOf("beaconReport/sendBeaconReportPage") != -1) {//推送消息统计
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'sendBeaconReportPic', 'sendBeaconReportTextId');
    } else if(url1.indexOf("beaconReport/weiBeaconReportPage")!= -1) {//微标统计
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'weiBeaconReportPic', 'weiBeaconReportTextId');
    } else if(url1.indexOf("company_list") != -1) {//公众号
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'weixinPic', 'weixinTextId');
    } else if(url1.indexOf("queueBusiType_list") != -1) {//排队业务类型
      changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'queueBusiTypePic', 'queueBusiTypeTextId');
    } else if(url1.indexOf("wxViBeaconList") != -1) {//店长小工具微信活动
        changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'wxViBeaconPagePicId', 'wxViBeaconPageTextId');
    } else if(url1.indexOf("beacon/beaconList") != -1) { 
        changeIndexPic('${app_static}/admin/images/leftMenu/choose_s.png', '#5BA284', 'theBeaconPicId', 'theBeaconTextId');
    } else if (url1.indexOf("index") != -1) {
        changeIndexPic('${app_static}/admin/images/leftMenu/index_s.png', '#43AC83', 'orgNodePicId', 'orgNodeTextId');
    } else if (url1.indexOf("index") != -1) {
        changeIndexPic('${app_static}/admin/images/leftMenu/index_s.png', '#43AC83', 'beaconGroupPicId', 'beaconGroupTextId');
    } else if (url1.indexOf("index") != -1) {
        changeIndexPic('${app_static}/admin/images/leftMenu/index_s.png', '#43AC83', 'informationPicId', 'informationTextId');
    }
    
    
    
  }

  function gin$(id) {
    return document.getElementById(id);
  }
  
  //修改样式
  function changeIndexPic(url, color, picId, textId) {
	  if(url&&gin$(picId)){
	    gin$(picId).src = url;
	  }
	  if(textId&&gin$(textId)){
	    gin$(textId).style.color = color;
	  }
  }
</script>