package com.shineoxygen.work.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.service.AdminUserService;

public class BaseAdminController extends BaseController {

	public final static String ADMINUSER_NAME = "ADMINUSER_NAME";
	public final static String ADMINUSER_PHONE = "ADMINUSER_PHONE";
	public final static String ADMINUSER = "ADMINUSER";
	public final static String ADMINUSER_ROLE_TYPE = "ADMINUSER_ROLE_TYPE";
	public final static String ADMINUSER_ROLE_NAME = "ADMINUSER_ROLE_NAME";
	public final static String STRUCTURE_MANAGE_NAME = "STRUCTURE_MANAGE_NAME";
	public final static String ADMINUSER_NODE = "ADMINUSER_NODE";
	public final static String ADMINUSER_NODE_LEVEL = "ADMINUSER_NODE_LEVEL";

	@Autowired
	private AdminUserService adminMgr;

	/**
	 * 准备分页数据以及分页页码js
	 * 
	 * @author 王辉阳
	 * @date 2015年9月1日 上午11:06:05
	 * @param page
	 * @param model
	 */
	protected <T> void pageModel(Page<T> page, Model model) {
		// 准备参数
		model.addAttribute("page", page);
//		model.addAttribute("pageInfo", PageInfo.getAdminPagingNavigation(page));
	}

	/**
	 * 返回当前登录的管理员
	 *
	 * @param req
	 * @return
	 */
	public static String getCurrentAdminUserName(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute(BaseAdminController.ADMINUSER_NAME);
		return (obj != null) ? (String) obj : null;
	}

	/**
	 * 获取当前登录的管理员用户
	 *
	 * @return
	 */
	public static AdminUser getCurrentAdminUser(HttpServletRequest req) {
		AdminUser obj = (AdminUser) req.getSession().getAttribute(BaseAdminController.ADMINUSER);
		return (obj != null) ? obj : null;
	}

	/**
	 * 设置管理员登录后的session值
	 *
	 * @param req
	 * @param adminUser
	 */
	public static void setAdminUserSession(HttpServletRequest req, AdminUser adminUser) {
		req.getSession().setAttribute(BaseAdminController.ADMINUSER_NAME, adminUser.getUsername());
		req.getSession().setAttribute(BaseAdminController.ADMINUSER, adminUser);
	}

}
