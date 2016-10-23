package com.shineoxygen.work.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shineoxygen.work.dao.PersonDao;
import com.shineoxygen.work.dao.impl.PersonDaoImpl;
import com.shineoxygen.work.pojo.Person;

/**
 * 临时bean配置，如临时用来测试的bean
 * 
 * @author 王辉阳
 * @date 2016年10月21日 下午4:56:05
 */
@Configuration
public class TempBeanConfig {

	@Bean
	public Person person() {
		return new Person();
	}

	@Bean
	public PersonDao personDao() {
		return new PersonDaoImpl();
	}
	

}
