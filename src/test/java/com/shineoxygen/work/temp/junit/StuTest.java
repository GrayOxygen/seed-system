package com.shineoxygen.work.temp.junit;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shineoxygen.work.temp.config.DSConfig;
import com.shineoxygen.work.temp.config.TempBeanConfig;
import com.shineoxygen.work.temp.dao.StudentDao;
import com.shineoxygen.work.temp.pojo.Student;

/**
 * 为所有repository提供自定义方法：
 *
 * 一方面定义一个接口
 * 
 * a，继承MongoRepository等spring
 * data的repository接口，获取通用的数据库操作功能，同时具有根据接口方法名自动生成语句的功能，如findByName就会得到以name为条件的查询语句。
 * 接口用@NoRepositoryBean注解表明不会被spring声明为一个repository bean，仅起到声明作用，具体bean是其实现
 * 
 * b，并给出实现且继承SimpleMongoRepository，这样一来，spring会通过注解@EnableMongoRepositories扫描到我们定义的repository接口
 * 并通过该注解提供的repositoryBaseClass属性值找到对应实现，而继承SimpleMongoRepository让我们的接口实现不用重写MongoRepository的接口方法
 * 我们在接口和该实现中给出自定义方法。实现类是普通的bean可注入其他bean，如MongoOperations/MongoTemplate推荐用MongoOperations，是后者的接口，spring以后也会扩展
 * 
 * 另一方面我们的业务dao接口，继承上面定义的接口，从而有了springdata的repository功能又增加了自定义方法
 * 
 * 普通接口的实现默认是接口名+Impl的命名，java配置类中，@EnableMongoRepositories的repositoryImplementationPostfix属性可进行更换后缀
 * 
 * 
 * @author 王辉阳
 * @date 2016年10月22日 下午9:26:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DSConfig.class, TempBeanConfig.class })
public class StuTest {
	private static final Logger logger = LogManager.getLogger(StuTest.class);

	@Autowired
	private StudentDao dao;

	@Test
	public void test() {
		dao.deleteAll();
		Student user1 = new Student();
		Student user2 = new Student();
		user1.setId("1");
		user1.setName("wanghuiyang");
		user2.setId(UUID.randomUUID().toString());
		user2.setName("wanghuiyang");
		dao.save(user1);
		dao.save(user2);

		// 自定义方法
		dao.del("1", Student.class);

		List<Student> list = dao.findAll();

		for (Student user : list) {
			System.out.println(user.toString());
		}

	}

}
