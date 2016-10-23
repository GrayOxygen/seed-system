package com.shineoxygen.work.temp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 演示controller
 * 
 * 1，完成一次页面请求，删除数据
 * 
 * 2，sitemesh3重用页面布局
 * 
 * @author 王辉阳
 * @date 2016年10月23日 上午11:03:46
 */
@Controller
@RequestMapping("/tempTest")
public class HomeController {
	private static final Logger logger = LogManager.getLogger(HomeController.class);
	@Autowired
	private StudentDao dao;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		// 用户collection测试
		dao.deleteAll();
		Student user1 = new Student();
		Student user2 = new Student();
		user1.setId("1");
		user1.setName("wanghuiyang");
		user2.setId(UUID.randomUUID().toString());
		user2.setName("wanghuiyang");
		dao.save(user1);
		dao.save(user2);
		dao.del("1", Student.class);

		List<Student> list = dao.findAll();

		for (Student user : list) {
			System.out.println(user.toString());
		}
		// 操作成功
		return "tempTest/home";
	}

	@RequestMapping(value = "/sitemesh3Test", method = RequestMethod.GET)
	public String sitemesh3Test(Locale locale, Model model) {
		logger.info("Welcome sitemesh3Test! The client locale is {}.", locale);

		return "tempTest/main";
	}

}
