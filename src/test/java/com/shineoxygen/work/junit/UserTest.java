package com.shineoxygen.work.junit;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shineoxygen.work.config.DSConfig;
import com.shineoxygen.work.config.TempBeanConfig;
import com.shineoxygen.work.dao.UserDao;
import com.shineoxygen.work.pojo.User;

/**
 * 为一个repository提供自定义方法：
 * 
 * 一方面业务dao继承MongoRepository或者其他Repository接口可实现通用的crud等操作，同时具有根据接口（业务dao接口）方法名自动生成查询语句的功能
 * 
 * 另一方面自定义一个普通接口并给出实现，来增加自定义方法(MongoOperations/MongoTemplate实现数据库操作，推荐用前者)给我们的业务dao即我们的repository
 * 
 * 普通接口的实现默认是接口名+Impl的命名，java配置类中，@EnableMongoRepositories的repositoryImplementationPostfix属性可进行更换后缀
 * 
 * 
 * @author 王辉阳
 * @date 2016年10月22日 下午9:26:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DSConfig.class, TempBeanConfig.class })
public class UserTest {
	@Autowired
	private UserDao userDao;

	@Test
	public void test() {
		userDao.deleteAll();
		User user1 = new User();
		User user2 = new User();
		user1.setId("1");
		user1.setJob("java developer111");
		user1.setName("wanghuiyang");
		user2.setId(UUID.randomUUID().toString());
		user2.setJob("library boss");
		user2.setName("wanghuiyang");
		userDao.save(user1);
		userDao.save(user2);

		List<User> users = userDao.findByName("wanghuiyang");
		for (User user : users) {
			System.out.println(user);
		}
		User user3 = new User();
		user3.setName("yangling");
		user3.setJob("hello");
		userDao.someCustomMethod(user3);

		List<User> list = userDao.findAll();

		for (User user : list) {
			System.out.println(user.toString());
		}

		// userDao.del("1", User.class);
	}

}
