package com.shineoxygen.work.tdd.junit.repo;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.querydsl.core.types.Predicate;
import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.model.QAdminUser;
import com.shineoxygen.work.admin.repo.AdminUserRepo;
import com.shineoxygen.work.tdd.config.MongodbConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongodbConfig.class })
@SuppressWarnings("all")
public class AdminUserRepoTest {
	@Autowired
	private AdminUserRepo adminUserRepo;

	@Test
	public void save() {
		AdminUser user = new AdminUser();
		user.setUserName("readPerm");
		user.setPwd(DigestUtils.md5Hex("111111"));
		user.setBuildin(false);
		adminUserRepo.save(user);
	}

	@Test
	public void saveMulti() {
		AdminUser user = new AdminUser();
		int index = 10;
		while (index < 60) {
			user.setUserName("yangling" + index++);
			user.setPwd("111111");
			user.setId("" + (index++));
			adminUserRepo.save(user);
			user = new AdminUser();
		}
	}

	@Test
	public void findByUserNameAndPwd() {
		System.out.println(adminUserRepo.findByUserNameAndPwd("wanghy", "111111"));
	}

	@Test
	public void predicate() {
		QAdminUser user = new QAdminUser("atime");
		Predicate predicate = user.userName.eq("wanghy").and(user.pwd.eq("111111"));
		System.out.println(adminUserRepo.findAll(predicate));
	}

	@Test
	public void exist() {
		AdminUser user = new AdminUser();
		user.setUserName("wanghy");
		user.setCtime(null);
		user.setUserStatus(null);

		AdminUser user2 = new AdminUser();
		user2.setCtime(null);
		user2.setUserStatus(null);
		user2.setId("1");
		System.out.println(adminUserRepo.existBeside(user, user2));
	}

//	@Test
//	public void tst() {
//		QAdminUser user = new QAdminUser("adminUser");
//		Predicate predicate = user.deleted.ne(true).and(user.userName.like("%wanghy%"));
//		BooleanExpression predicate2 = user.deleted.ne(true);
//		predicate2 =predicate2.and(user.userName.like("%wanghy%"));
//		predicate2.as("adminUser").and( user.userName.like("%wanghy%") );
//		System.out.println(123);
//	}
}
