package com.shineoxygen.work.base.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.shineoxygen.work.base.utils.JsonUtils;
import com.shineoxygen.work.base.utils.URLUtils;
import com.shineoxygen.work.base.web.support.CustomTimestampEditor;

@Controller
public class BaseController {
	private static final Logger logger = LogManager.getLogger(BaseController.class);
	public static final String X_FORWARDED_FOR = "X-Forwarded-For";
	public static final String USER_AGENT = "user-agent";
	public static final String REFERER = "referer";
	public static final String CHARSET = "UTF-8";
	public static final String RESPONSE_CONTENT_TYPE_JSON = "application/json";
	public static final String RESPONSE_CONTENT_TYPE_TXT = "text/plain";
	public static final String HEADER_KEY_ACAO = "Access-Control-Allow-Origin";
	public static final String HEADER_VAL_ACAO = "*";
	public static final String ERR_500 = "/error/500";
	public static final String ERR_404 = "/error/404";

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(Timestamp.class, new CustomTimestampEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	public static void response(HttpServletResponse res, String text, String charset) {
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.setContentType("application/json");
		res.setCharacterEncoding(charset);
		try {
			res.getOutputStream().write(text.getBytes(charset));
		} catch (Exception e) {
			logger.error("Error", e);
		}
	}

	public static void response(HttpServletResponse res, String text) {
		response(res, text, "UTF-8");
	}

	public static void responseJson(HttpServletResponse res, Object obj) {
		response(res, JsonUtils.toJson(obj, true));
	}

	public static void printRequestHeaders(HttpServletRequest req) {
		if (req == null)
			return;
		Enumeration names = req.getHeaderNames();
		System.out.println("=========Request Headers=========");
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			System.out.println(name + ": " + req.getHeader(name));
		}
		System.out.println("=========Request Headers=========");
	}

	public static void printRequestAttribute(HttpServletRequest req) {
		if (req == null)
			return;
		Enumeration names = req.getAttributeNames();
		System.out.println("=========Request Attribute=========");
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			System.out.println(name + ": " + req.getAttribute(name));
		}
		System.out.println("=========Request Attribute=========");
	}

	public static void printRequestCookie(HttpServletRequest req) {
		if (req == null) {
			return;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				System.out.println(JsonUtils.toJson(cookie));
	}

	public static void printRequestBrief(HttpServletRequest req) {
		if (logger.isInfoEnabled()) {
			System.out.println("Invoke: " + req.getRequestURL());
			System.out.println("Params: ");

			Enumeration names = req.getParameterNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				System.out.println("\t" + name + ": [" + req.getParameter(name) + "]");
			}
			printRequestCookie(req);
			System.out.println("Req Ip:   " + req.getRemoteAddr());
			System.out.println("Fwd Ip:   " + getReqIp(req));
		}
	}

	public static String getReqIp(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(ip))
			return ip;
		return req.getRemoteAddr();
	}

	public static String getUserAgent(HttpServletRequest req) {
		return req.getHeader("user-agent");
	}

	public static String getReferer(HttpServletRequest req) {
		return req.getHeader("referer");
	}

	public static void clearSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
	}

	public static String getCookieDomain(HttpServletRequest req) {
		String hostname = req.getServerName();
		String cookieDomain = null;
		if ((!(hostname.equals("localhost"))) && (!(URLUtils.isIpAddress(hostname)))) {
			cookieDomain = "." + URLUtils.getDomainName(hostname);
		}
		return cookieDomain;
	}
}