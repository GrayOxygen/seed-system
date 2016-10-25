package com.shineoxygen.work.admin.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.service.AdminUserService;
import com.shineoxygen.work.base.controller.BaseAdminController;
import com.shineoxygen.work.base.controller.BaseController;
import com.shineoxygen.work.temp.HomeController;

/**
 * 后台用户登录
 *
 * @author
 */
@Controller
public class AdminUserLoginController extends BaseAdminController {

	private static final Logger logger = LogManager.getLogger(AdminUserLoginController.class);

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
	public String login(String userName, String pwd, String captcha, String autoLoginFlag, HttpServletRequest req, HttpServletResponse res) throws Exception {
		boolean result = true;
		Cookie[] cookies = req.getCookies();
		Cookie destCookie = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("autoLoginSession".equals(cookie.getName())) {
					destCookie = cookie;
					break;
				}
			}
		}
		if (destCookie != null) {// 存在cookie，自动登录
			// List<AdminUser> list =
			// adminUserSrv.findByProperty("autoLoginSession",
			// destCookie.getValue());
			// if (list.size() > 0) {
			// AdminUser user = list.get(0);
			// BaseAdminController.setAdminUserSession(req, user);
			// } else {
			// return "admin/login";
			// }
		} else {

			if (StringUtils.isAnyBlank(userName, pwd ) ) {
				return "admin/login";
			}

			AdminUser user = adminUserSrv.findByUserNameAndPwd(userName, pwd);
			if (null == user) {
				logger.warn("登陆失败，" + userName + " 用户不存在，ip：" + BaseController.getReqIp(req));
			}

			BaseAdminController.setAdminUserSession(req, user);
			return "redirect:/admin/index.do";
		}
		return "admin/login";
	}

	/**
	 * 取回密码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/getPwdPage")
	public String getPwdPage(Model model) {
		return "admin/getPwd";
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
		return "admin/login";
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
