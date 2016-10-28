package com.shineoxygen.work.base.utils;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author 王辉阳
 * 
 * @date 2015年7月23日 下午1:40:42
 * 
 * @Description 请求响应工具类
 */
public class RequestResponseUtils {
	private static Logger log = LogManager.getLogger(RequestResponseUtils.class);

	public static void printRequestBrief(HttpServletRequest req) {
		if (log.isInfoEnabled()) {
			log.info("Invoke " + req.getRequestURL() + " had begin");
			log.info("Request Params : ");

			Enumeration<String> names = req.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				log.info("\t" + name + ": [" + req.getParameter(name) + "]");
			}
			printRequestCookie(req);
			log.info("Request IP : " + req.getRemoteAddr());
			log.info("X-Forwarded-IP : " + getReqIp(req));
		}
	}

	public static void printRequestCookie(HttpServletRequest req) {
		if (req == null) {
			return;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				log.info(JSON.toJSONString(cookie, true));
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

}
