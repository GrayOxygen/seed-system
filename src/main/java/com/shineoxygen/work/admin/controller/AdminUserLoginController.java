package com.shineoxygen.work.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shineoxygen.work.admin.service.AdminUserService;
import com.shineoxygen.work.base.controller.BaseAdminController;

/**
 * 后台用户登录
 *
 * @author
 */
@Controller
public class AdminUserLoginController extends BaseAdminController {

	private final static Logger log = LoggerFactory.getLogger(AdminUserLoginController.class);

	@Autowired
	private AdminUserService adminUserSrv;

	/**
	 * Login
	 *
	 * @param req
	 * @param res
	 * @param username
	 * @param password
	 * @param captcha
	 * @return
	 */
	@RequestMapping("/admin/login")
	public String login(String username, String pwd, String captcha, String autoLoginFlag, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "";
	}

	/**
	 * 取回密码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/getPwdPage")
	public String getPwdPage(Model model) {
		return "/admin/getPwd";
	}

	@RequestMapping("/admin/resetPwd")
	public String resetPwd(Model model, String phoneNum, String defaultPwd) {
		// 重置密码,提示重置成功
		return "forward:/admin/login.do";
	}

	/**
	 * Logout
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/admin/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		return "";
	}

	/**
	 * Index统计页面
	 *
	 * @return
	 */
	@RequestMapping("/admin/index")
	public String toIndex(HttpServletRequest req, Model model) {
		return "admin/index";
	}

}
