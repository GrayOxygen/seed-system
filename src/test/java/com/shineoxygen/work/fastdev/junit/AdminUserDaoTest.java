package com.shineoxygen.work.fastdev.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shineoxygen.work.admin.dao.AdminUserDao;
import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.fastdev.config.MongodbConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongodbConfig.class })
public class AdminUserDaoTest {
	@Autowired
	private AdminUserDao adminUserdao;

	@Test
	public void save() {
		AdminUser user = new AdminUser();
		user.setUserName("wanghy");
		user.setPwd("111111");
		user.setId("1");
		adminUserdao.save(user);
	}

	@Test
	public void saveMulti() {
		AdminUser user = new AdminUser();
		int index = 10;
		while (index < 60) {
			user.setUserName("wanghy" + index++);
			user.setPwd("111111");
			user.setId("" + (index++));
			adminUserdao.save(user);
			user = new AdminUser();
		}
	}

	@Test
	public void findByUserNameAndPwd() {
		System.out.println(adminUserdao.findByUserNameAndPwd("wanghy", "111111"));
	}

}
