package com.shineoxygen.work.admin.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.service.AdminUserService;
import com.shineoxygen.work.base.controller.BaseAdminController;
import com.shineoxygen.work.base.controller.BaseController;

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
	public String login(String userName, String pwd, String captcha, String autoLoginFlag, String message, ModelMap modelMap, HttpServletRequest req) throws Exception {
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

		if (StringUtils.isAnyBlank(userName, pwd )||null!=message) {
			return "admin/login";
		}
		UsernamePasswordToken token = new UsernamePasswordToken(userName,  pwd);
		// token.setRememberMe(true); //暂时不需要
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			logger.info("管理用户：{} 登录失败", userName);
			modelMap.addAttribute("message", "账号或者密码错误");
		}

		if (subject.isAuthenticated()) {
			AdminUser user = adminUserSrv.findByUserNameAndPwd(userName,  pwd);
			if (null != user) {
				BaseAdminController.setAdminUserSession(req, user);
				return "redirect:/admin/index.do";
			}
			modelMap.addAttribute("message", "用户不存在");
			logger.warn("登陆失败，" + userName + " 用户不存在，ip：" + BaseController.getReqIp(req));

		}
		return "/admin/login";
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
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		logger.info("用户:{}已注销", subject.getPrincipal());
		return "redirect:login";
	}

	/**
	 * Index 页面
	 *
	 * @return
	 */
	@RequestMapping("/admin/index")
	public String toIndex(HttpServletRequest req, Model model) {
		return "/admin/index";
	}

	@RequestMapping("/admin/unauthorized")
	public String toUnauthorized() {
		return "/error/unauthorized";
	}

}
