package com.shineoxygen.work.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shineoxygen.work.config.DSConfig;
import com.shineoxygen.work.config.TempBeanConfig;
import com.shineoxygen.work.dao.PersonDao;
import com.shineoxygen.work.pojo.Person;

import junit.framework.Assert;

/**
 * 演示针对mongodb特性的MongoOperations(MongoTemplate的超类)接口使用，完成数据库操作
 * 
 * @author 王辉阳
 * @date 2016年10月23日 上午11:04:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DSConfig.class, TempBeanConfig.class })
public class PersonTest {
	@Autowired
	private PersonDao personDao;

	@Test
	public void create() {
		Person person = new Person();
		person.setAddress("shenzhen");
		person.setId("1");
		person.setName("wanghy");
		personDao.create(person);
	}

	@Test
	public void read() {
		Person person = personDao.read("1");
		System.out.println(person);
	}

	@Test
	public void update() {
		Person person = personDao.read("1");
		System.out.println(person);
		person.setAddress("galaxy");
		personDao.update(person);
		System.out.println(personDao.read("1"));
	}

	@Test
	public void delete() {
		personDao.delete("1");
		Assert.assertNull(personDao.read("1"));
	}

}
